package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TimePicker;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lesson);

        context = this;
    }


    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setupViews();
    }

    private void initViews() {
        bStartTime = (Button) findViewById(R.id.b_start_time);
        flNewLessonColor = (FrameLayout) findViewById(R.id.fl_new_lesson_color);
    }

    private void setupViews() {
        bStartTime.setOnClickListener(this);
        flNewLessonColor.setOnClickListener(this);
        selectedColor = Utils.colorChoice(this)[0];
        stringSelectedColor = Utils.colorToString(selectedColor);
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
        }
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
