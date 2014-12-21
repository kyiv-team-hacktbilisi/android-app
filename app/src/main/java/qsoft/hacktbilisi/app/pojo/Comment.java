package qsoft.hacktbilisi.app.pojo;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by andrii on 20.12.14.
 */
@ParseClassName("Comment")
public class Comment extends ParseObject {

    public String getAuthorID() {
        return getString("authorID");
    }

    public void setAuthorID(String authorID) {
        put("authorID", authorID);
    }

    public String getText() {
        return getString("text");
    }

    public void setText(String text) {
        put("text", text);
    }

    public String getLessonID() {
        return getString("lessonID");
    }

    public void setLessonID(String lessonID) {
        put("lessonID", lessonID);
    }

    public Date getTime() {
        return getDate("time");
    }

    public void setTime(Date time) {
        put("time", time);
    }
}
