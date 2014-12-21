package qsoft.hacktbilisi.app.pojo;

import com.parse.ParseClassName;
import com.parse.ParseUser;

@ParseClassName("_User")
public class User extends ParseUser {

    public String getName() {
        return getString("username");
    }

    public void setName(String name) {
        put("username", name);
    }

    public String getLessonDuration() {
        return getString("lessonDuration");
    }

    public void setLessonDuration(String lessonDuration) {
        put("lessonDuration", lessonDuration);
    }

    public String getUniversity() {
        return getString("university");
    }

    public void setUniversity(String university) {
        put("university", university);
    }

    public String getGroup() {
        return getString("group");
    }

    public void setGroup(String group) {
        put("group", group);
    }
}
