package qsoft.hacktbilisi.app.app;

import android.app.Application;
import com.parse.Parse;

/**
 * Created by p_val on 21.12.14.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "ti6w15PJjCOcdmOOy9Yxo6P25ZcSTFMxSbtBJULx", "rPhiEYbAfar6lomtMuoveMuDJvQCQ3bBML5sNU9w");
    }
}
