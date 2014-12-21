package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.melnykov.fab.FloatingActionButton;
import com.parse.*;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.pojo.Group;
import qsoft.hacktbilisi.app.pojo.University;
import qsoft.hacktbilisi.app.pojo.User;
import qsoft.hacktbilisi.app.utils.Logger;
import qsoft.hacktbilisi.app.utils.Utils;

import java.util.List;

/**
 * Created by andrii on 20.12.14.
 */
public class SettingsActivity extends Activity implements View.OnClickListener {//}, TextWatcher {

    private Context context;

    private LinearLayout llPrefUserName;
    private LinearLayout llPrefLessonDuration;
    private LinearLayout llPrefUniversity;
    private LinearLayout llPrefGroup;
    private LinearLayout llPrefLogOut;

    private TextView tvPrefUserNameLabel;
    private TextView tvPrefLessonDurationLabel;
    private TextView tvPrefUniversityLabel;
    private TextView tvPrefGroupLabel;

    private TextView tvPrefUserName;
    private TextView tvPrefLessonDuration;
    private TextView tvPrefUniversity;
    private TextView tvPrefGroup;

    private User user;
    private FloatingActionButton bSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = this;
        user = (User) User.getCurrentUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentUser();
        initViews();
        setupViews();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getCurrentUser() {
        //todo load curr user
        user = (User) User.getCurrentUser();
    }

    private void initViews() {
        llPrefUserName = (LinearLayout) findViewById(R.id.pref_username_container);
        llPrefLessonDuration = (LinearLayout) findViewById(R.id.pref_lesson_duration_container);
        llPrefUniversity = (LinearLayout) findViewById(R.id.pref_university_container);
        llPrefGroup = (LinearLayout) findViewById(R.id.pref_group_container);
        llPrefLogOut = (LinearLayout) findViewById(R.id.pref_logout_container);

        tvPrefUserNameLabel = (TextView) findViewById(R.id.pref_username_label);
        tvPrefLessonDurationLabel = (TextView) findViewById(R.id.pref_lesson_duration_label);
        tvPrefUniversityLabel = (TextView) findViewById(R.id.pref_university_label);
        tvPrefGroupLabel = (TextView) findViewById(R.id.pref_group_label);

        tvPrefUserName = (TextView) findViewById(R.id.pref_username);
        tvPrefLessonDuration = (TextView) findViewById(R.id.pref_lesson_duration);
        tvPrefUniversity = (TextView) findViewById(R.id.pref_university);
        tvPrefGroup = (TextView) findViewById(R.id.pref_group);
        bSave = (FloatingActionButton) findViewById(R.id.b_save);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.saveInBackground();
                finish();
            }
        });
    }

    private void setupViews() {
        //todo load values from current user
        setTitle("Settings");
        if (user.getName() != null)
            tvPrefUserName.setText(user.getName());

        if (user.getLessonDuration() != null)
            tvPrefLessonDuration.setText(user.getLessonDuration());

        if (user.getUniversity() != null) {
            ParseQuery<University> query = ParseQuery.getQuery("University");
            query.whereEqualTo("objectId", user.getUniversity());
            query.getFirstInBackground(new GetCallback<University>() {
                public void done(University result, ParseException e) {
                    if (e == null) {
                        tvPrefUniversity.setText(result.getName());
                    } else {
                        Logger.e("The getFirst request failed.");
                    }
                }
            });
        }

        if (user.getGroup() != null) {
            ParseQuery<Group> query = ParseQuery.getQuery("Group");
            query.whereEqualTo("objectId", user.getGroup());
            query.getFirstInBackground(new GetCallback<Group>() {
                public void done(Group result, ParseException e) {
                    if (e == null) {
                        tvPrefGroup.setText(result.getName());
                    } else {
                        Logger.e("The getFirst request failed.");
                    }
                }
            });
        }

        llPrefUserName.setOnClickListener(this);
        llPrefLessonDuration.setOnClickListener(this);
        llPrefUniversity.setOnClickListener(this);
        llPrefGroup.setOnClickListener(this);
        llPrefLogOut.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pref_username_container:
                showSimpleDialog(tvPrefUserNameLabel, tvPrefUserName, v.getId());
                break;
            case R.id.pref_lesson_duration_container:
                showSimpleDialog(tvPrefLessonDurationLabel, tvPrefLessonDuration, v.getId());
                break;
            case R.id.pref_university_container:
                showAutoCompleteDialog(tvPrefUniversityLabel, tvPrefUniversity, v.getId());
                break;
            case R.id.pref_group_container:
                showAutoCompleteDialog(tvPrefGroupLabel, tvPrefGroup, v.getId());
                break;
            case R.id.pref_logout_container:
                User.logOut();
                Utils.clearLoginState(context);
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void showSimpleDialog(final TextView prefLabel, final TextView prefOption, final int optId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(prefLabel.getText().toString());

        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().length() > 0) {
                    prefOption.setText(input.getText().toString());
                    if (optId == R.id.pref_username_container) {
                        user.setName(input.getText().toString());
                    } else if (optId == R.id.pref_lesson_duration_container) {
                        user.setLessonDuration(input.getText().toString());
                    }
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void showAutoCompleteDialog(final TextView prefLabel, final TextView prefOption, final int optId) {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_preference, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptsView);

        final AutoCompleteTextView userInput = (AutoCompleteTextView) promptsView.findViewById(R.id.actvDialogUserInput);

        if (optId == R.id.pref_university_container) {
            loadUniversities(userInput);
        } else if (optId == R.id.pref_group_container) {
            loadGroups(userInput);
        }

        userInput.setEnabled(true);

        alertDialogBuilder
                .setTitle(prefLabel.getText().toString())
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = userInput.getText().toString();
                        if (s.length() > 0) {
                            prefOption.setText(s);
                            if (optId == R.id.pref_university_container) {
                                setUniversity(s);
                            } else if (optId == R.id.pref_group_container) {
                                setGroup(s);
                            }
                            //todo if item doesn't exists do smth
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setUniversity(final String u) {
        ParseQuery<University> query = ParseQuery.getQuery("University");
        query.whereEqualTo("name", u);
        query.getFirstInBackground(new GetCallback<University>() {
            public void done(University result, ParseException e) {
                if (e == null) {
                    user.setUniversity(result.getObjectId());
                    user.saveInBackground();
                    Logger.d("Retrieved the object.");
                } else {
                    final University university = new University();
                    university.setName(u);
                    university.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Logger.d("new uid=" + university.getObjectId());
                                user.setUniversity(university.getObjectId());
                                user.saveInBackground();
                            } else {
                                Logger.d("Creating the object failed");
                            }
                        }
                    });
                    Logger.e("The getFirst request failed.");
                }
            }
        });
    }

    private void setGroup(final String group) {
        ParseQuery<Group> query = ParseQuery.getQuery("Group");
        query.whereEqualTo("name", group);
        query.getFirstInBackground(new GetCallback<Group>() {
            public void done(Group result, ParseException e) {
                if (e == null) {
                    user.setGroup(result.getObjectId());
                    Utils.savePrefs(context, user);
                    Logger.d("Retrieved the object.");
                } else {
                    final Group group1 = new Group();
                    group1.setName(group);
                    group1.setUniversityID(user.getUniversity());
                    group1.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Logger.d("new gid=" + group1.getObjectId());
                                user.setGroup(group1.getObjectId());
                                Utils.savePrefs(context, user);
                            } else {
                                Logger.d("Creating the object failed");
                            }
                        }
                    });
                    Logger.e("The getFirst group request failed.");
                }
            }
        });
    }

    private void loadUniversities(final AutoCompleteTextView autoCompleteTextView) {
        ParseQuery<University> query = ParseQuery.getQuery("University");
        query.findInBackground(new FindCallback<University>() {
            public void done(List<University> eventList, ParseException e) {
                if (e == null) {
                    String[] universities = new String[eventList.size()];
                    for (int i = 0; i < universities.length; i++) {
                        universities[i] = eventList.get(i).getName();
                        Logger.d(universities[i]);
                    }
                    ArrayAdapter adapter = new ArrayAdapter
                            (context, android.R.layout.simple_list_item_1, universities);
                    autoCompleteTextView.setAdapter(adapter);
                } else {
                    Logger.d("Error while load univers: " + e.getMessage());
                    //todo show error screen or message
                }
            }
        });
    }

    private void loadGroups(final AutoCompleteTextView autoCompleteTextView) {
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

}
