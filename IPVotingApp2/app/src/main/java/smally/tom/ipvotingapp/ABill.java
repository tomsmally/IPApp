package smally.tom.ipvotingapp;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Thomas on 29/02/2016.
 */

public class ABill {
    Integer billRefNum = null;
    String billName;
    String billCategory;
    String billStance = "none";
    Boolean billFavourited;
    Drawable billImg;
    Drawable billBackImg;
    Comment billComments = new Comment();
    String billDescription;

    ABill() {
        //initialiseComments();
    }
    public void initialiseComments(){
        billComments.setUserName("");
        billComments.setCommentText("");
        billComments.setDateTimeString("");
    }

}
