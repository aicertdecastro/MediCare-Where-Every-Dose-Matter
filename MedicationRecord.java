public class MedicationRecord extends Record{
    private Medication medication;

    public MedicationRecord(String dateTime, String description, Medication medication){
        super(dateTime, description);
        this.medication = medication;
    }

    public Medication getMedication(){
        return medication;
    }

    public void displayRecord(){
        System.out.println(dateTime + " - Medication Taken: " + medication.getName() + " (" + medication.getDosage() + ") " + description); 
    }
}