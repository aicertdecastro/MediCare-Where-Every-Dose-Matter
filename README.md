## MediCare - Personal Medication Management and Reminder System

## 1. Project Title

**MediCare** — Personal Medication Management and Reminder System

---

## 2. Description / Overview

MediCare is a Java console-based application designed to help users manage their medications effectively. It allows users to add, edit, and delete medications with details like name, dosage, instructions, and multiple scheduled date-time entries. The app features a background thread that provides real-time console reminders for upcoming doses, enables logging of medication intake (marking specific schedules as taken), and tracks a history of events including reminders, intake logs, and wellness feedback. This addresses the issue of missed medication doses by offering automated notifications and a simple record-keeping system. It is intended for educational purposes and personal use, not as a replacement for professional medical tools.

**Key Features**
- **CRUD Operations**: Add, edit, delete medications with validation (e.g., no past schedules).
- **Multi-Schedule Support**: Each medication can have multiple date-time schedules entered as comma-separated values.
- **Automated Reminders**: A daemon thread checks every 10 seconds and prints reminders in the console when a schedule is due and not yet taken or reminded.
- **Intake Logging**: Log when a medication is taken for a specific schedule, marking it as taken (prevents future reminders for that schedule).
- **History and Feedback**: View all records (reminders, intake, edits/deletes, feedback) and add wellness feedback with feelings and symptoms.
- **Error Handling**: Custom exceptions for invalid inputs (e.g., empty fields, malformed schedules).

---

## 3. OOP Concepts Applied

### Encapsulation
- Data in classes like `Medication` (e.g., `name`, `dosage`, `schedules`, `instructions`) and `Record` subclasses is private, with public getters/setters for controlled access. This protects internal state and ensures changes are validated (e.g., schedules are parsed and checked against the current time).
- `MediCareManager` encapsulates arrays for medications and records, exposing only necessary methods like `add()` and `viewHistory()`.

### Inheritance
- The abstract `Record` class serves as a base, providing common fields (`dateTime`, `description`) and an abstract `displayRecord()` method.
- Subclasses `MedicationRecord`, `ReminderRecord`, and `FeedbackRecord` inherit from `Record`, adding specific fields (e.g., `Medication` in `MedicationRecord`) and overriding `displayRecord()` for customized output.

### Polymorphism
- `MediCareManager` uses a `Record[]` array to store different record types. When displaying history, it calls `displayRecord()` polymorphically—the correct overridden method executes based on the object's runtime type (e.g., a `MedicationRecord` shows intake details, while a `ReminderRecord` shows reminder info).
- The `Manageable` interface allows `MediCareManager` to be treated as a generic manager, enabling flexible method calls like `manager.add()`.

### Abstraction
- The `Record` abstract class hides the specifics of how different record types are displayed, requiring subclasses to implement `displayRecord()`.
- The `Manageable` interface abstracts the CRUD operations, defining a contract (`add()`, `edit()`, `delete()`) without implementation details, allowing `MediCareManager` to focus on medication-specific logic.

### Exception Handling
- A custom `InvalidMedicationException` is used for validation errors (e.g., empty names, invalid schedule formats, or past dates).
- Try-catch blocks in methods like `add()` and `logMedicationIntake()` handle parsing errors and user input issues gracefully, printing error messages without crashing the app.

---

## 4. Program Structure

The project follows a modular structure with separate files for each class. Below is the folder layout and a text-based class diagram showing relationships.

```
MediCare/
├─ bin/                      
├─ src/                      (source files)
│  ├─ Main.java              (entry point with menu loop)
│  ├─ MediCareManager.java   (core logic, implements Manageable)
│  ├─ Medication.java        (entity for medication data)
│  ├─ Record.java            (abstract base for records)
│  ├─ MedicationRecord.java  (subclass for intake logs)
│  ├─ ReminderRecord.java    (subclass for reminder events)
│  ├─ FeedbackRecord.java    (subclass for wellness feedback)
│  ├─ Manageable.java        (interface for CRUD operations)
│  └─ InvalidMedicationException.java (custom exception)
└─ README.md                 (this file)
```

### Class Relationships (Text Diagram)
```
Manageable (interface)
    ↑ implements
MediCareManager
    ├─ manages → Medication[] (array of medication objects)
    ├─ manages → Record[]     (array of history records)
    ├─ uses → Scanner         (for user input)
    ├─ uses → DateTimeFormatter (for parsing/displaying dates)
    └─ starts → Thread        (daemon for reminders)

Record (abstract)
    ↑ extends
├─ MedicationRecord (links to → Medication)
├─ ReminderRecord
└─ FeedbackRecord

Medication
    ├─ fields: name, dosage, instructions
    ├─ schedules → List<LocalDateTime> (multiple date-times)
    ├─ remindedSchedules → Set<LocalDateTime> (tracks reminded times)
    └─ takenSchedules → Set<LocalDateTime> (tracks taken times)
```

**Program Responsibilities**
- `Main`: Handles the console menu loop, reads user input, and delegates to `MediCareManager`.
- `MediCareManager`: Implements the main logic, including CRUD for medications, reminder thread, record management, and input validation.
- `Medication`: Represents a medication with its details and schedule tracking.
- `Record` Hierarchy: Logs events; each subclass specializes in displaying its type (e.g., intake vs. reminder).

---

## 5. How to Run the Program

**Files Required**
Place all the following `.java` files in the same directory:
- `Main.java`
- `MediCareManager.java`
- `Medication.java`
- `Record.java`
- `MedicationRecord.java`
- `ReminderRecord.java`
- `FeedbackRecord.java`
- `Manageable.java`
- `InvalidMedicationException.java`

**Step-by-Step Instructions**
1. **Compile the Program**:
   - Open a VS Code and open all the files inside the folder.
   - Ensure that all the files are open as all of them are connected to each other and to make sure the program run accordingly.

2. **Run the Program**:
   - In the Main class which is the entry point of our program, run the program using the terminal.
   - The console menu will appear, and you can interact by entering numbers (1-9) with each number having their own function.
     
---

## 6. Sample Output

Below is a simulated short run of the program (console output). User inputs are shown in **bold**. Note: Reminders appear asynchronously when the thread detects a due schedule.

```
=== MediCare Menu ===
1. Add Medication
2. Edit Medication
3. Delete Medication
4. View Reminders
5. Log Medication Intake
6. View Medication History
7. Add Wellness Feedback
8. View Medication List
9. Exit

Choose an option: 1
Medication name: Aspirin
Dosage (e.g. 500mg or 1 tablet): 100mg
Schedules (comma-separated, e.g., 2023-10-01 08:00, 2023-10-01 14:00): 2025-11-30 08:00, 2025-11-30 20:00
Instructions: Take with food
Medication added successfully.

... (background thread checks every 10 seconds; when time reaches 2025-11-30 08:00)
Reminder: Time to take Aspirin (100mg) at 2025-11-30 08:00

=== MediCare Menu ===
1. Add Medication
2. Edit Medication
3. Delete Medication
4. View Reminders
5. Log Medication Intake
6. View Medication History
7. Add Wellness Feedback
8. View Medication List
9. Exit

Choose an option: 5
-----Medication List-----
1) Name: Aspirin | Dosage: 100mg | Schedules: 2025-11-30 08:00, 2025-11-30 20:00 | Instructions: Take with food
Enter medication index you already took (starting at 1): 1
Select the schedule you took it for:
1) 2025-11-30 08:00
2) 2025-11-30 20:00
Enter schedule index (starting at 1): 1
Additional note for medication intake: Taken after breakfast
Medication intake logged for Aspirin at 2025-11-30 08:00

=== MediCare Menu ===
1. Add Medication
2. Edit Medication
3. Delete Medication
4. View Reminders
5. Log Medication Intake
6. View Medication History
7. Add Wellness Feedback
8. View Medication List
9. Exit

Choose an option: 6
-- Medication History / Records --
1) 2025-11-30 08:00:01 - Reminder: Medication reminder for Aspirin at 2025-11-30 08:00
2) 2025-11-30 08:05:22 - Medication Taken: Aspirin (100mg) Taken after breakfast at 2025-11-30 08:00

=== MediCare Menu ===
1. Add Medication
2. Edit Medication
3. Delete Medication
4. View Reminders
5. Log Medication Intake
6. View Medication History
7. Add Wellness Feedback
8. View Medication List
9. Exit

Choose an option: 9
Exiting MediCare. Stay healthy!
```

**Notes on Output**
- Reminders print automatically in the console when due.
- Logging intake marks the specific schedule as taken, preventing further reminders for it.
- History shows all events with timestamps.

---

## 7. Author and Acknowledgement

**Author:** De Castro, Aicert Reimiel, Bulaon, Vhenise Rich Cole, Palicpic, Nicko, and Magtibay, Daiñell Jheff 

**Institution:** CICS Batangas State University - Alangilan Campus  

**Date:** 2025-12-02  

**Acknowledgements**  
- Java Platform for robust exception handling and threading capabilities
- Object-Oriented Programming principles that guided the architecture
- Modern date/time APIs that simplified scheduling functionality
- The healthcare community for inspiring medication adherence solutions
  
---

## 8. Other Sections

### a) Future Enhancements
- Database integration for persistent storage
- Mobile application with push notifications
- Email/SMS reminder capabilities
- Drug interaction checking
- Prescription refill tracking
- Graphical user interface
- Data export functionality (PDF reports)
- Multi-user support with authentication
- Integration with healthcare provider systems

### b) References
- Java Time API (java.time) for date/time management
- Java Collections Framework concepts applied to custom arrays
- Object-Oriented Programming principles and design patterns
- Exception handling best practices

---

