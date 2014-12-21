package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.pojo.Group;
import qsoft.hacktbilisi.app.pojo.University;
import qsoft.hacktbilisi.app.pojo.User;
import qsoft.hacktbilisi.app.utils.Logger;

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

    private TextView tvPrefUserNameLabel;
    private TextView tvPrefLessonDurationLabel;
    private TextView tvPrefUniversityLabel;
    private TextView tvPrefGroupLabel;

    private TextView tvPrefUserName;
    private TextView tvPrefLessonDuration;
    private TextView tvPrefUniversity;
    private TextView tvPrefGroup;

    private User user;

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
        user.saveInBackground();
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

        tvPrefUserNameLabel = (TextView) findViewById(R.id.pref_username_label);
        tvPrefLessonDurationLabel = (TextView) findViewById(R.id.pref_lesson_duration_label);
        tvPrefUniversityLabel = (TextView) findViewById(R.id.pref_university_label);
        tvPrefGroupLabel = (TextView) findViewById(R.id.pref_group_label);

        tvPrefUserName = (TextView) findViewById(R.id.pref_username);
        tvPrefLessonDuration = (TextView) findViewById(R.id.pref_lesson_duration);
        tvPrefUniversity = (TextView) findViewById(R.id.pref_university);
        tvPrefGroup = (TextView) findViewById(R.id.pref_group);
    }

    private void setupViews() {
        //todo load values from current user
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
                        if (userInput.getText().toString().length() > 0) {
                            prefOption.setText(userInput.getText().toString());
                            if (optId == R.id.pref_university_container) {
                                user.setUniversity(userInput.getText().toString());
                            } else if (optId == R.id.pref_group_container) {
                                user.setGroup(userInput.getText().toString());
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
