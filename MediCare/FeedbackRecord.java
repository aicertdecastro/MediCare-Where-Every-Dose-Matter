public class FeedbackRecord extends Record{
    private String feedback;

    public FeedbackRecord(String dateTime, String description, String feedback){
        super(dateTime, description);
        this.feedback = feedback;
    }

    public String getFeedback(){
        return feedback;
    }

    public void displayRecord(){
        System.out.println(dateTime + " - Feedback: " + feedback + " (notes: " + description + ")");
    }
}