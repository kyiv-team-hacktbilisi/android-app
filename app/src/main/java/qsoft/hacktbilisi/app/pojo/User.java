package qsoft.hacktbilisi.app.pojo;

/**
 * Created by andrii on 20.12.14.
 */

public class User {
    private String name;
    private String lessonDuration;
    private String university;
    private String group;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLessonDuration() {
        return lessonDuration;
    }

    public void setLessonDuration(String lessonDuration) {
        this.lessonDuration = lessonDuration;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
