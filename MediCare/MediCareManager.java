import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MediCareManager implements Manageable {
    private Medication[] medications;
    private int medCount;

    private Record[] records;
    private int recCount;

    private final int MAX_MED = 50;
    private final int MAX_REC = 200;

    private Scanner input;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MediCareManager(Scanner input) {
        this.input = input;
        medications = new Medication[MAX_MED];
        medCount = 0;
        records = new Record[MAX_REC];
        recCount = 0;

        Thread reminderThread = new Thread(() -> {
            while (true) {
                try {
                    LocalDateTime now = LocalDateTime.now();
                    for (int i = 0; i < medCount; i++) {
                        Medication m = medications[i];
                        if (m != null) {  
                            for (LocalDateTime schedule : m.getSchedules()) {
                                if (!m.getRemindedSchedules().contains(schedule) && !m.getTakenSchedules().contains(schedule) && now.isAfter(schedule)) {
                                    System.out.println("\n Reminder: Time to take " + m.getName()
                                            + " (" + m.getDosage() + ") at " + schedule.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                                    addRecord(new ReminderRecord(now(), "Medication reminder for " + m.getName() + " at " + schedule.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
                                    m.addRemindedSchedule(schedule);
                                }
                            }
                        }
                    }
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        reminderThread.setDaemon(true);
        reminderThread.start();
    }


    public void add() {
        try {
            System.out.print("Medication name: ");
            String name = input.nextLine().trim();
            if (name.isEmpty()) throw new InvalidMedicationException("Name cannot be empty");

            System.out.print("Dosage (e.g. 500mg or 1 tablet): ");
            String dosage = input.nextLine().trim();
            if (dosage.isEmpty()) throw new InvalidMedicationException("Dosage cannot be empty");

            System.out.print("Schedules (comma-separated, e.g., 2023-10-01 08:00, 2023-10-01 14:00): ");
            String scheduleInput = input.nextLine().trim();
            if (scheduleInput.isEmpty()) throw new InvalidMedicationException("At least one schedule is required");
            List<LocalDateTime> schedules = parseSchedules(scheduleInput);
            if (schedules.isEmpty()) throw new InvalidMedicationException("Invalid schedule format");

            System.out.print("Instructions: ");
            String instructions = input.nextLine().trim();
            if (instructions.isEmpty()) throw new InvalidMedicationException("Follow the doctor's instructions");

            if (medCount >= MAX_MED) {
                System.out.println("Medication storage is full!!!");
                return;
            }

            Medication med = new Medication(name, dosage, schedules, instructions);
            medications[medCount++] = med;

            System.out.println("Medication added successfully.");
        } catch (InvalidMedicationException ime) {
            System.out.println("[ERROR] " + ime.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred while adding medication: " + e.getMessage());
        }
    }

    public void edit() {
        try {
            if (medCount == 0) {
                System.out.println("No medications to edit.");
                return;
            }

            listMedications();
            System.out.print("Enter medication index to edit (starting at 1): ");
            int idx = Integer.parseInt(input.nextLine()) - 1;
            if (idx < 0 || idx >= medCount) {
                System.out.println("Invalid index.");
                return;
            }

            Medication med = medications[idx];
            System.out.println("Editing: " + med.getName());
            System.out.print("New name (leave blank to keep): ");
            String name = input.nextLine().trim();
            if (!name.isEmpty()) med.setName(name);

            System.out.print("New dosage (leave blank to keep): ");
            String dosage = input.nextLine().trim();
            if (!dosage.isEmpty()) med.setDosage(dosage);

            System.out.print("New schedules (comma-separated, leave blank to keep): ");
            String scheduleInput = input.nextLine().trim();
            if (!scheduleInput.isEmpty()) {
                List<LocalDateTime> schedules = parseSchedules(scheduleInput);
                if (!schedules.isEmpty()) med.setSchedules(schedules);
            }

            System.out.print("New instructions (leave blank to keep): ");
            String instructions = input.nextLine().trim();
            if (!instructions.isEmpty()) med.setInstructions(instructions);

            System.out.println("Medication has been updated.");
            addRecord(new ReminderRecord(now(), "Edited medication: " + med.getName()));
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid number input.");
        } catch (Exception e) {
            System.out.println("Error editing medication: " + e.getMessage());
        }
    }

    public void delete() {
        try {
            if (medCount == 0) {
                System.out.println("No medications to delete.");
                return;
            }
            listMedications();
            System.out.print("Enter medication index to delete (starting at 1): ");
            int idx = Integer.parseInt(input.nextLine()) - 1;
            if (idx < 0 || idx >= medCount) {
                System.out.println("Invalid index.");
                return;
            }

            String removeName = medications[idx].getName();

            for (int i = idx; i < medCount - 1; i++) {
                medications[i] = medications[i + 1];
            }

            medications[--medCount] = null;
            System.out.println("Medication has been deleted.");
            addRecord(new ReminderRecord(now(), "Deleted medication: " + removeName));
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid number input.");
        } catch (Exception e) {
            System.out.println("Error deleting medication: " + e.getMessage());
        }
    }

    public void listMedications() {
        System.out.println("-----Medication List-----");
        if (medCount == 0) {
            System.out.println("No medications stored.");
            return;
        }
        for (int i = 0; i < medCount; i++) {
            System.out.printf("%d) %s\n", i + 1, medications[i].toString());
        }
    }

    public void showReminders() {
        System.out.println("-----Reminders-----");
        if (medCount == 0) {
            System.out.println("No medications to remind.");
            return;
        }
        boolean hasReminders = false;
        for (int i = 0; i < medCount; i++) {
            Medication m = medications[i];
            if (m != null) {
                List<LocalDateTime> untakenSchedules = new ArrayList<>();
                for (LocalDateTime schedule : m.getSchedules()) {
                    if (!m.getTakenSchedules().contains(schedule)) {
                        untakenSchedules.add(schedule);
                    }
                }
                if (!untakenSchedules.isEmpty()) {
                    hasReminders = true;
                    System.out.println((i + 1) + ") Take " + m.getName() + " - " + m.getDosage());
                    System.out.println("   Upcoming Schedules: " + formatSchedules(untakenSchedules));
                    System.out.println("   Instructions: " + m.getInstructions());
                }
            }
        }
        if (!hasReminders) {
            System.out.println("No upcoming reminders.");
        }
        addRecord(new ReminderRecord(now(), "Viewed reminders."));
    }

    public void logMedicationIntake() {
        try {
            if (medCount == 0) {
                System.out.println("No medications to log.");
                return;
            }
            listMedications();
            System.out.print("Enter medication index you already took (starting at 1): ");
            int idx = Integer.parseInt(input.nextLine()) - 1;
            if (idx < 0 || idx >= medCount) {
                System.out.println("Invalid index.");
                return;
            }
            Medication med = medications[idx];
            List<LocalDateTime> schedules = med.getSchedules();
            if (schedules.isEmpty()) {
                System.out.println("No schedules for this medication.");
                return;
            }
            System.out.println("Select the schedule you took it for:");
            for (int i = 0; i < schedules.size(); i++) {
                System.out.println((i + 1) + ") " + schedules.get(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            }
            System.out.print("Enter schedule index (starting at 1): ");
            int schedIdx = Integer.parseInt(input.nextLine()) - 1;
            if (schedIdx < 0 || schedIdx >= schedules.size()) {
                System.out.println("Invalid schedule index.");
                return;
            }
            LocalDateTime selectedSchedule = schedules.get(schedIdx);
            System.out.print("Additional note for medication intake: ");
            String note = input.nextLine().trim();
            String desc = note.isEmpty() ? "Taken" : note;
            MedicationRecord mrec = new MedicationRecord(now(), desc + " at " + selectedSchedule.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), med);
            addRecord(mrec);
            med.addTakenSchedule(selectedSchedule);  
            System.out.println("Medication intake logged for " + med.getName() + " at " + selectedSchedule.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid number input.");
        } catch (Exception e) {
            System.out.println("Error logging intake: " + e.getMessage());
        }
    }



    public void addFeedback() {
        viewFeedback();  
        try {
            System.out.print("How are you feeling after taking the medication? (brief): ");
            String feeling = input.nextLine().trim();
            System.out.print("Any symptoms or side effects? (brief): ");
            String symptoms = input.nextLine().trim();

            String notes = "Feeling: " + (feeling.isEmpty() ? "Not specified" : feeling);
            if (!symptoms.isEmpty()) notes += " | Symptoms: " + symptoms;

            FeedbackRecord frec = new FeedbackRecord(now(), "Patient wellness feedback", notes);
            addRecord(frec);
            System.out.println("Feedback recorded. Thank you for reporting your wellness.");
        } catch (Exception e) {
            System.out.println("Error recording feedback: " + e.getMessage());
        }
    }

    public void viewFeedback() {
        System.out.println("\n-- Wellness Feedback History --");
        boolean hasFeedback = false;
        for (int i = 0; i < recCount; i++) {
            if (records[i] instanceof FeedbackRecord) {
                System.out.print((i + 1) + ") ");
                records[i].displayRecord();
                hasFeedback = true;
            }
        }
        if (!hasFeedback) {
            System.out.println("No feedback records available.");
        }
    }

    public void viewHistory() {
        System.out.println("\n-- Medication History / Records --");
        if (recCount == 0) {
            System.out.println("No records available.");
            return;
        }
        for (int i = 0; i < recCount; i++) {
            System.out.print((i + 1) + ") ");
            records[i].displayRecord();
        }
    }

    private void addRecord(Record r) {
        if (recCount >= MAX_REC) {
            System.out.println("Record storage full. Oldest record will be removed.");
            for (int i = 0; i < recCount - 1; i++) records[i] = records[i + 1];
            records[recCount - 1] = r;
        } else {
            records[recCount++] = r;
        }
    }

    private String now() {
        return LocalDateTime.now().format(dtf);
    }

    private List<LocalDateTime> parseSchedules(String input) throws InvalidMedicationException {
        List<LocalDateTime> schedules = new ArrayList<>();
        String[] parts = input.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        for (String part : parts) {
            try {
                LocalDateTime schedule = LocalDateTime.parse(part.trim(), formatter);
                if (schedule.isBefore(now)) {
                    throw new InvalidMedicationException("Schedule cannot be in the past: " + part.trim());
                }
                schedules.add(schedule);
            } catch (Exception e) {
                throw new InvalidMedicationException("Invalid schedule format: " + part.trim());
            }
        }
        return schedules;
    }

    private String formatSchedules(List<LocalDateTime> schedules) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (LocalDateTime schedule : schedules) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(schedule.format(formatter));
        }
        return sb.toString();
    }
}

