package smally.tom.ipvotingapp;

/**
 * Created by Thomas on 21/03/2016.
 */
public class Comment {
    private String userName = "";
    private String commentText = "";
    private String dateTimeString = "";

    public String getUserName(){
        return userName;
    }
    public String getCommentText(){
        return commentText;
    }
    public String getDateTimeString(){
        return dateTimeString;
    }
    public void setUserName(String newUsername){
        userName = newUsername;
    }
    public void setCommentText(String newComment){
        commentText = newComment;
    }
    public void setDateTimeString(String newDateTime){
        dateTimeString = newDateTime;
    }
}
