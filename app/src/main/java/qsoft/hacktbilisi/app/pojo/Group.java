package qsoft.hacktbilisi.app.pojo;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by andrii on 20.12.14.
 */
@ParseClassName("Group")
public class Group extends ParseObject{

    public String getUniversityID() {
        return getString("universityID");
    }

    public void setUniversityID(String universityID) {
        put("universityID", universityID);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }
}
