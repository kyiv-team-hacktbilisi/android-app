package qsoft.hacktbilisi.app.pojo;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by andrii on 20.12.14.
 */
@ParseClassName("Lesson")
public class Lesson extends ParseObject {

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getTeacher() {
        return getString("teacher");
    }

    public void setTeacher(String teacher) {
        put("teacher", teacher);
    }

    public String getType() {
        return getString("type");
    }

    public void setType(String type) {
        put("type", type);
    }

    public String getColor() {
        return getString("color");
    }

    public void setColor(String color) {
        put("color", color);
    }

    public String getAudience() {
        return getString("audience");
    }

    public void setAudience(String audience) {
        put("audience", audience);
    }

    public int getDay() {
        return getInt("day");
    }

    public void setDay(int day) {
        put("day", day);
    }

    public String getGroupId() {
        return getString("group_id");
    }

    public void setGroupId(String groupId) {
        put("group_id", groupId);
    }

    public boolean getPrivate() {
        return getBoolean("private");
    }

    public void setPrivate(boolean b) {
        put("private", b);
    }

    public String getStartTime() {
        return getString("startTime");
    }

    public void setStartTime(String startTime) {
        put("startTime", startTime);
    }

}
