public class ReminderRecord extends Record{

    public ReminderRecord(String dateTime, String description){
        super(dateTime, description);
    }

    public void displayRecord(){
        System.out.println(dateTime + " - Reminder: " + description);
    }
}