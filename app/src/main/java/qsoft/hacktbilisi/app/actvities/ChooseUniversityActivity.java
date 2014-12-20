package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.melnykov.fab.FloatingActionButton;
import com.parse.*;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.pojo.University;
import qsoft.hacktbilisi.app.pojo.User;
import qsoft.hacktbilisi.app.utils.Logger;

import java.util.List;

/**
 * Created by andrii on 20.12.14.
 */
public class ChooseUniversityActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Context context;

    private AutoCompleteTextView autoCompleteTextView;
    private FloatingActionButton bNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_university);
        setTitle("Creating university");

        context = this;

        User user = (User) User.getCurrentUser();
        if (user.getUniversity() != null && user.getUniversity().length() > 0) {
            Intent intent = new Intent(context, ChooseGroupActivity.class);
            startActivity(intent);
            finish();
        }

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViews();
    }

    private void initViews() {
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.actv_choose_university);
        bNext = (FloatingActionButton) findViewById(R.id.b_next_to_choose_group);
    }

    private void setupViews() {
        loadUniversities();

        autoCompleteTextView.setOnItemClickListener(this);
        bNext.setOnClickListener(this);
        bNext.show(false);
//        YoYo.with(Techniques.ZoomInUp)
//                .duration(400)
//                .delay(100)
//                .interpolate(new DecelerateInterpolator())
//                .playOn(bNext);
    }

    private void loadUniversities() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_next_to_choose_group:
                final String u = autoCompleteTextView.getText().toString();
                if (u != null && u.length() > 0) {

                    final User user = (User) User.getCurrentUser();

                    ParseQuery<University> query = ParseQuery.getQuery("University");
                    query.whereEqualTo("name", u);
                    query.getFirstInBackground(new GetCallback<University>() {
                        public void done(University result, ParseException e) {
                            if (e == null) {
                                user.setUniversity(result.getObjectId());
                                user.saveEventually();

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
                                            user.saveEventually();
                                        } else {
                                            Logger.d("Creating the object failed");
                                        }
                                    }
                                });
                                Logger.e("The getFirst request failed.");
                            }
                        }
                    });


                    Intent intent = new Intent(context, ChooseGroupActivity.class);
                    startActivity(intent);
                } else {
                    YoYo.with(Techniques.Shake).duration(700).playOn(autoCompleteTextView);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        autoCompleteTextView.clearFocus();
    }
}
