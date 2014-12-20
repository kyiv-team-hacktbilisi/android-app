package qsoft.hacktbilisi.app.pojo;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by andrii on 20.12.14.
 */
@ParseClassName("University")
public class University extends ParseObject {
    public String getName() {
        return getString("firstName");
    }

    public void setName(String name) {
        put("firstName", name);
    }
}
