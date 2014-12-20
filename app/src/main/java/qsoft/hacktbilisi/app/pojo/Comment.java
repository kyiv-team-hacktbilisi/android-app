package qsoft.hacktbilisi.app.pojo;

import java.util.Date;

/**
 * Created by andrii on 20.12.14.
 */
public class Comment {
    private String authorID;
    private String text;
    private String lessonID;
    private Date time;

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
