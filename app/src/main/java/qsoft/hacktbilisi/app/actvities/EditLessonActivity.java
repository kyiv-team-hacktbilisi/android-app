package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.melnykov.fab.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.utils.Utils;
import qsoft.hacktbilisi.app.utils.it.gmariotti.android.example.colorpicker.calendarstock.ColorPickerDialog;
import qsoft.hacktbilisi.app.utils.it.gmariotti.android.example.colorpicker.calendarstock.ColorPickerSwatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by andrii on 20.12.14.
 */
public class EditLessonActivity extends Activity implements View.OnClickListener {

    private Context context;

    private FrameLayout flNewLessonColor;
    private Button bStartTime;

    private int selectedColor;
    private String stringSelectedColor;
    private FloatingActionButton bCreate;
    private EditText lessonName;
    private EditText audiance;
    private EditText teacher;
    private RadioButton rbLecture;
    private RadioButton rbLabs;
    private RadioButton rbPractice;
    private Switch swIsPrivate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lesson);
        initViews();

        context = this;
    }


    @Override
    protected void onResume() {
        super.onResume();
        setupViews();
    }

    private void initViews() {
        bStartTime = (Button) findViewById(R.id.b_start_time);
        bCreate = (FloatingActionButton) findViewById(R.id.b_create);
        flNewLessonColor = (FrameLayout) findViewById(R.id.fl_new_lesson_color);

        lessonName = (EditText) findViewById(R.id.actv_lesson_name);
        audiance = (EditText) findViewById(R.id.actv_audience);
        teacher = (EditText) findViewById(R.id.actv_teacher);
        rbLecture = (RadioButton) findViewById(R.id.rb_lecture);
        rbLabs = (RadioButton) findViewById(R.id.rb_labs);
        rbPractice = (RadioButton) findViewById(R.id.rb_practice);
        swIsPrivate = (Switch) findViewById(R.id.sw_isPrivate);
    }

    private void setupViews() {
        bStartTime.setOnClickListener(this);
        flNewLessonColor.setOnClickListener(this);
        selectedColor = Utils.colorChoice(this)[0];
        stringSelectedColor = Utils.colorToString(selectedColor);
        bCreate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_new_lesson_color:
                ColorPickerDialog colorcalendar = ColorPickerDialog.newInstance(
                        R.string.dialog_color_picker_title,
                        Utils.colorChoice(this), selectedColor, 5,
                        ColorPickerDialog.SIZE_SMALL);

                colorcalendar.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        selectedColor = color;
                        stringSelectedColor = Utils.colorToString(selectedColor);
                        flNewLessonColor.setBackgroundColor(Color.parseColor(stringSelectedColor));
                    }

                });

                colorcalendar.show(getFragmentManager(), "cal");
                break;
            case R.id.b_start_time:
                timePickerDialog(bStartTime);
                break;
            case R.id.b_create:
                save();
                break;
        }
    }

    private void save() {
        ParseObject gameScore = new ParseObject("Lesson");
        gameScore.put("name", lessonName.getText().toString());
        gameScore.put("audience", Integer.parseInt(audiance.getText().toString()));
        gameScore.put("teacher", teacher.getText().toString());
        if (rbLabs.isSelected()) {
            gameScore.put("type", "laboratory");
        } else if (rbLecture.isSelected()) {
            gameScore.put("type", "lection");
        } else if (rbPractice.isSelected()) {
            gameScore.put("type", "practice");
        }
        gameScore.put("start_time", bStartTime.getText().toString());
        gameScore.put("color", stringSelectedColor == null ? "#ffffff" : stringSelectedColor);
        gameScore.put("start_time", bStartTime.getText().toString());
        gameScore.put("private", swIsPrivate.isChecked());
        gameScore.put("group_id", ParseUser.getCurrentUser().get("group"));
        gameScore.put("day", getIntent().getIntExtra("day", 0));
        gameScore.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                finish();
            }
        });
    }

    private void timePickerDialog(final Button button) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hour, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);

                DateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String formattedDate = df.format(calendar.getTime());
                button.setText(formattedDate);
            }
        }, hour, minute, true);
        tpd.setTitle("Choose Time");
        tpd.show();
    }
}
