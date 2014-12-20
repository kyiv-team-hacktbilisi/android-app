package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.melnykov.fab.FloatingActionButton;
import com.parse.*;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.pojo.Group;
import qsoft.hacktbilisi.app.pojo.User;
import qsoft.hacktbilisi.app.utils.Logger;

import java.util.List;

/**
 * Created by andrii on 20.12.14.
 */
public class ChooseGroupActivity extends Activity implements View.OnClickListener {

    private Context context;

    private AutoCompleteTextView autoCompleteTextView;
    private FloatingActionButton bNextCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_group);
        setTitle("Creating group");

        context = this;

        User user = (User) User.getCurrentUser();
        if (user.getGroup() != null && user.getGroup().length() > 0) {
            Intent intent = new Intent(context, ScheduleDayActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setupViews();
    }

    private void initViews() {
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.actv_choose_group);
        bNextCreate = (FloatingActionButton) findViewById(R.id.b_next_create);
        bNextCreate.show(false);
//        YoYo.with(Techniques.ZoomInUp)
//                .duration(400)
//                .interpolate(new DecelerateInterpolator())
//                .delay(100)
//                .playOn(bNextCreate);

    }

    private void setupViews() {
        loadGroups();

        bNextCreate.setOnClickListener(this);
    }


    private void loadGroups() {
        User user = (User) User.getCurrentUser();
        ParseQuery<Group> query = ParseQuery.getQuery("Group");
        query.whereEqualTo("universityID", user.getUniversity());
        query.findInBackground(new FindCallback<Group>() {
            public void done(List<Group> eventList, ParseException e) {
                if (e == null) {
                    String[] groups = new String[eventList.size()];
                    for (int i = 0; i < groups.length; i++) {
                        groups[i] = eventList.get(i).getName();
                        Logger.d(groups[i]);
                    }
                    ArrayAdapter adapter = new ArrayAdapter
                            (context, android.R.layout.simple_list_item_1, groups);
                    autoCompleteTextView.setAdapter(adapter);
                } else {
                    Logger.d("Error: " + e.getMessage());
                    //todo show error screen or message
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_next_create:
                final String group = autoCompleteTextView.getText().toString();
                if (group != null && group.length() > 0) {

                    final User user = (User) User.getCurrentUser();

                    ParseQuery<Group> query = ParseQuery.getQuery("Group");
                    query.whereEqualTo("name", group);
                    query.getFirstInBackground(new GetCallback<Group>() {
                        public void done(Group result, ParseException e) {
                            if (e == null) {
                                user.setGroup(result.getObjectId());
                                user.saveInBackground();

                                Logger.d("Retrieved the object.");
                            } else {
                                final Group group1 = new Group();
                                group1.setName(group);
                                group1.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Logger.d("new gid=" + group1.getObjectId());
                                            user.setGroup(group1.getObjectId());
                                            user.saveInBackground();
                                        } else {
                                            Logger.d("Creating the object failed");
                                        }
                                    }
                                });
                                Logger.e("The getFirst group request failed.");
                            }
                        }
                    });

                    Intent intent = new Intent(context, ScheduleDayActivity.class);
                    startActivity(intent);
                } else {
                    YoYo.with(Techniques.Shake).duration(700).playOn(autoCompleteTextView);
                }
                break;
        }
    }
}
