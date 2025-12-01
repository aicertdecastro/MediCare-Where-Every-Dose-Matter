import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MediCareManager manager = new MediCareManager(scanner);


while (true) {
    showMenu();
    System.out.print("Choose an option: ");
    String input = scanner.nextLine().trim();
    
    switch (input) {
        case "1":
        manager.add();
        break;
        case "2":
        manager.edit();
        break;
        case "3":
        manager.delete();
        break;
        case "4":
        manager.listMedications();
        break;
        case "5":
        manager.showReminders();
        break;
        case "6":
        manager.logMedicationIntake();
        break;
        case "7":
        manager.viewHistory();
        break;
        case "8":
        manager.addFeedback();
        break;
        case "9":
        System.out.println("Exiting MediCare. Stay healthy!");
        scanner.close();
        return;
        default:
        System.out.println("Invalid option. Please enter a number from the menu.");
        }
    }
}


private static void showMenu() {
    final String RESET = "\u001B[0m";
    final String CYAN = "\u001B[36m";
    final String GREEN = "\u001B[32m";
    final String YELLOW = "\u001B[33m";
    final String WHITE_BOLD = "\033[1;37m";

    System.out.println("\n" + CYAN + "==========================================" + RESET);
    System.out.println(WHITE_BOLD + "            MEDICARE MAIN MENU          " + RESET);
    System.out.println(CYAN + "==========================================" + RESET);
    
    System.out.println(YELLOW + "  INVENTORY" + RESET);
    System.out.println("  1. Add Medication");
    System.out.println("  2. Edit Medication");
    System.out.println("  3. Delete Medication");
    System.out.println("  4. View Medication List");

    System.out.println(YELLOW + "\n  PATIENT CARE" + RESET);
    System.out.println("  5. View Reminders");
    System.out.println("  6. Log Intake");
    System.out.println("  7. View History");
    System.out.println("  8. Add Wellness Feedback");

    System.out.println(CYAN + "------------------------------------------" + RESET);
    System.out.println(GREEN + "  9. Exit" + RESET);
    System.out.println(CYAN + "==========================================" + RESET);
    System.out.print("  Choose an option: ");
}
}

