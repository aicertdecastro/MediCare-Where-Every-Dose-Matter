# 🌿 **MediCare**

### *Your Personal Medication Management & Reminder System*

---

## 📌 **1. Project Title**

**MediCare — Personal Medication Management and Reminder System**
*A Java console application built to promote healthier, more mindful medication habits.*

---

## 💡 **2. Description / Overview**

MediCare is a fully interactive Java console-based system designed to help individuals stay on track with their medications. Whether you’re managing daily vitamins, prescriptions, or long-term treatment schedules, MediCare makes it easier—through structured management, smart reminders, and detailed logging.

The system allows you to:

* Add, update, or remove medication entries
* Attach multiple date–time schedules
* Receive real-time reminders through a background thread
* Log medication intake
* Track wellness feedback
* Maintain a complete history of medication-related events

This tool’s purpose is simple: **to help reduce missed doses and promote mindful medication management**, especially for personal, educational, or learning use.

---

## 🌟 **Key Features at a Glance**

✔️ **Medication CRUD**
Create, edit, and delete medications—validated so you don’t accidentally schedule doses in the past.

✔️ **Multi-Schedule Support**
Add multiple date–time values in one line (comma-separated).

✔️ **Smart Reminders**
A background daemon thread scans every 10 seconds and notifies you when a scheduled dose is due.

✔️ **Intake Logging**
Mark specific schedules as “taken” and attach personal notes (e.g., *“taken after breakfast”*).

✔️ **Wellness Feedback**
Record how you feel, symptoms noticed, or any medication effects.

✔️ **Comprehensive History Tracking**
See reminders, intakes, edits, feedback—everything in one place.

✔️ **Error-Resistant Design**
Custom exceptions ensure all inputs are clean, valid, and safe.

---

## 🧬 **3. OOP Concepts Applied**

MediCare is built with clean, modular, and well-structured Object-Oriented Programming principles:

### 🔐 **Encapsulation**

All fields in classes (like `Medication` and `Record`) are private. Getters, setters, and validated updates protect internal data and prevent inconsistent states.

### 🧬 **Inheritance**

A core `Record` abstract class is extended by specialized record types:

* `MedicationRecord`
* `ReminderRecord`
* `FeedbackRecord`

Each one adds its own unique data and behaviors.

### 🎭 **Polymorphism**

The `Record[]` array holds *all* record types.
When you view history, `displayRecord()` triggers the correct version depending on the object's real type—clean, flexible, and powerful.

### 🧼 **Abstraction**

The `Manageable` interface defines the contract for CRUD operations.
The `Record` abstract class handles shared logic but requires subclasses to implement their own display formatting.

### ⚠️ **Exception Handling**

With a custom `InvalidMedicationException`, the app prevents errors like:

* Empty names
* Invalid schedules
* Incorrect date formats
* Past date entries

No crashes. Just clear, user-friendly messages.

---

## 🗂️ **4. Program Structure**

Here’s how the project is organized:

```
MediCare/
├─ src/
│  ├─ Main.java
│  ├─ MediCareManager.java
│  ├─ Medication.java
│  ├─ Record.java
│  ├─ MedicationRecord.java
│  ├─ ReminderRecord.java
│  ├─ FeedbackRecord.java
│  ├─ Manageable.java
│  └─ InvalidMedicationException.java
└─ README.md
```

### 🧩 **Class Relationship Overview**

```
Manageable (interface)
        ↑
MediCareManager
   ├─ manages → Medication[]
   ├─ manages → Record[]
   ├─ uses → Thread (daemon for reminders)

Record (abstract)
   ↑           ↑             ↑
MedicationRecord   ReminderRecord   FeedbackRecord

Medication
   ├─ name, dosage, instructions
   ├─ schedules → List<LocalDateTime>
   ├─ remindedSchedules → Set<LocalDateTime>
   └─ takenSchedules → Set<LocalDateTime>
```

---

## ▶️ **5. How to Run the Program**

### 🧰 **Files Required**

Place these `.java` files in the same folder:

```
Main.java  
MediCareManager.java  
Medication.java  
Record.java  
MedicationRecord.java  
ReminderRecord.java  
FeedbackRecord.java  
Manageable.java  
InvalidMedicationException.java  
```

### 🚀 **Steps to Run**

1. Open the folder in **VS Code** (or any Java IDE).
2. Open all `.java` files to avoid dependency issues.
3. Inside **Main.java**, run the program through the terminal or code runner.
4. Use the number-based menu to navigate through the app:

   * Add medications
   * Log intake
   * Record feedback
   * View history
   * And more

---

## 🖥️ **6. Sample Console Output**

*(User inputs shown in **bold**)*

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
Dosage: 100mg
Schedules: 2025-11-30 08:00, 2025-11-30 20:00
Instructions: Take with food
Medication added successfully.
```

⏳ *Then at the scheduled time...*

```
Reminder: Time to take Aspirin (100mg) at 2025-11-30 08:00
```

📝 **Logging Intake Example**

```
Choose an option: 5
-----Medication List-----
1) Aspirin | 100mg
Select schedule: 1
Additional note: Taken after breakfast
Medication intake logged.
```

📚 **History Example**

```
-- Medication History --
1) Reminder: Aspirin at 2025-11-30 08:00
2) Medication Taken: Aspirin (Taken after breakfast)
```

---

## 👥 **7. Authors & Acknowledgements**

<table>
  <tr>
    <th></th>
    <th>Name</th>
  </tr>

  <tr>
    <td><img src="https://github.com/aicertdecastro/MediCare-Where-Every-Dose-Matter/blob/main/creators/Aicert.jpg" width="100" height="100"></td>
    <td>
      <strong>De Castro, Aicert Reimiel</strong><br/>
      <a href="https://github.com/aicertdecastro" target="_blank">
        <img src="https://img.shields.io/badge/GitHub-%23121011.svg?logo=github&logoColor=white">
      </a>
    </td>
  </tr>

  <tr>
    <td><img src="https://github.com/aicertdecastro/MediCare-Where-Every-Dose-Matter/blob/main/creators/Cole.jpg" width="100" height="100"></td>
    <td>
      <strong>Bulaon, Vhenise Rich Cole</strong><br/>
      <a href="https://github.com/Ghost-910" target="_blank">
        <img src="https://img.shields.io/badge/GitHub-%23121011.svg?logo=github&logoColor=white">
      </a>
    </td>
  </tr>

  <tr>
    <td><img src="https://github.com/aicertdecastro/MediCare-Where-Every-Dose-Matter/blob/main/creators/Nicko.jpg" width="100" height="100"></td>
    <td>
      <strong>Palicpic, Nicko</strong><br/>
      <a href="https://github.com/nickopalicpic" target="_blank">
        <img src="https://img.shields.io/badge/GitHub-%23121011.svg?logo=github&logoColor=white">
      </a>
    </td>
  </tr>

  <tr>
    <td><img src="https://github.com/aicertdecastro/MediCare-Where-Every-Dose-Matter/blob/main/creators/Jeff.jpg" width="100" height="100"></td>
    <td><img src="" width="100" height="100"></td>
    <td>
      <strong>Magtibay, Daiñell Jheff</strong><br/>
      <a href="https://github.com/Aizishi" target="_blank">
        <img src="https://img.shields.io/badge/GitHub-%23121011.svg?logo=github&logoColor=white">
      </a>
    </td>
  </tr>

</table>


##  ‧₊˚ ┊ Acknowledgment
We sincerely express our gratitude to our instructors at CICS – Batangas State University, Alangilan Campus,
for their continuous guidance and support throughout the development of this project. We also extend our appreciation 
to our classmates and peers for their cooperation, encouragement, and valuable insights.

## Special Thanks To:
- Java’s threading and date-time API
- Object-Oriented Programming principles
- Healthcare communities promoting medication adherence

---

## 🚀 **8. Future Enhancements**

Here’s what MediCare could evolve into:

* 🗃️ Database support for saving data
* 📱 Mobile app with push notifications
* ✉️ Email or SMS reminders
* 💊 Drug interaction alerts
* 📊 PDF or CSV data exports
* 🔐 Multi-user accounts with login
* 🧠 AI-assisted medication insights
* 🖥️ GUI version with dashboards

---

## 📚 **References**

* Oracle Java Time API
* Java Collections Framework
* OOP Design Patterns
* Exception Handling Concepts
* Java Modules

---
