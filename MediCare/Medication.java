import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Medication {
    private String name;
    private String dosage;
    private List<LocalDateTime> schedules;
    private String instructions;
    private Set<LocalDateTime> remindedSchedules = new HashSet<>();
    private Set<LocalDateTime> takenSchedules = new HashSet<>();  

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Medication(String name, String dosage, List<LocalDateTime> schedules, String instructions) {
        this.name = name;
        this.dosage = dosage;
        this.schedules = schedules;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public List<LocalDateTime> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<LocalDateTime> schedules) {
        this.schedules = schedules;
    }

    public String getSchedulesString() {
        StringBuilder sb = new StringBuilder();
        for (LocalDateTime schedule : schedules) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(schedule.format(FORMATTER));
        }
        return sb.toString();
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Set<LocalDateTime> getRemindedSchedules() {
        return remindedSchedules;
    }
    public void addRemindedSchedule(LocalDateTime schedule) {
        remindedSchedules.add(schedule);
    }
    public Set<LocalDateTime> getTakenSchedules() {
        return takenSchedules;
    }
    public void addTakenSchedule(LocalDateTime schedule) {
        takenSchedules.add(schedule);
    }

    public String toString() {
        return String.format("Name: %s | Dosage: %s | Schedules: %s | Instructions: %s",
                name, dosage, getSchedulesString(), instructions);
    }
}