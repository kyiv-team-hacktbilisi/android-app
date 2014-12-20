package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.objects.PrefAutoCompleteTextView;
import qsoft.hacktbilisi.app.pojo.User;

/**
 * Created by andrii on 20.12.14.
 */
public class SettingsActivity extends Activity implements View.OnClickListener, TextWatcher {

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
        savePrefs();
        super.onBackPressed();
    }

    private void getCurrentUser() {
        //todo load curr user
        user = new User();
        restorePrefs();
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

        if (user.getUniversity() != null)
            tvPrefUniversity.setText(user.getUniversity());

        if (user.getGroup() != null)
            tvPrefGroup.setText(user.getGroup());

        llPrefUserName.setOnClickListener(this);
        llPrefLessonDuration.setOnClickListener(this);
        llPrefUniversity.setOnClickListener(this);
        llPrefGroup.setOnClickListener(this);
    }

    private void savePrefs() {
        //todo save
        SharedPreferences prefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("uName", user.getName());
        editor.putString("lDuration", user.getLessonDuration());
        editor.putString("univer", user.getUniversity());
        editor.putString("group", user.getGroup());
        editor.commit();
    }


    private void restorePrefs() {
        user = new User();
        SharedPreferences prefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        user.setName(prefs.getString("uName", null));
        user.setLessonDuration(prefs.getString("lDuration", null));
        user.setUniversity(prefs.getString("univer", null));
        user.setGroup(prefs.getString("group", null));
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

        final PrefAutoCompleteTextView userInput = (PrefAutoCompleteTextView) promptsView.findViewById(R.id.actvDialogUserInput);
        userInput.setThreshold(2);
        userInput.addTextChangedListener(this);
        String[] universities = {"KPI", "Not KPI", "NAU", "Georgian University"};
        ArrayAdapter adapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, universities);
        userInput.setAdapter(adapter);
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //todo request univers or groups
//        if (s.length() >= 2) {
//            String[] params = {s + ""};
//            if (citiesAsync == null) {
//                startAsync(params);
//                citiesAsync = null;
//            } else {
//                citiesAsync.cancel(true);
//                citiesAsync = null;
//                startAsync(params);
//            }
//        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
