package qsoft.hacktbilisi.app.app;

import android.app.Application;
import android.content.Context;
import com.parse.Parse;
import com.parse.ParseObject;
import qsoft.hacktbilisi.app.pojo.*;

/**
 * Created by andrii on 20.12.14.
 */
public class App extends Application {
    public static final String TAG = App.class.getSimpleName();

    private static App app;
    private static Context appContext;
//    private ArrayList<Comment> commentsOfEvent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        this.setAppContext(getApplicationContext());
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(University.class);
        ParseObject.registerSubclass(Group.class);
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(Lesson.class);
        Parse.initialize(this, "ti6w15PJjCOcdmOOy9Yxo6P25ZcSTFMxSbtBJULx", "rPhiEYbAfar6lomtMuoveMuDJvQCQ3bBML5sNU9w");
    }

    public static App getInstance() {
        return app;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context mAppContext) {
        this.appContext = mAppContext;
    }
}
