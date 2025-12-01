public abstract class Record{
    protected String dateTime;
    protected String description;

    public Record(String dateTime, String description){
        this.dateTime = dateTime;
        this.description = description;
    }

    public String getDateTime(){
        return dateTime;
    }

    public String getDescription(){
        return description;
    }

    public abstract void displayRecord();
}