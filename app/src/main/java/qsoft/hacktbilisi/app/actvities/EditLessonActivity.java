package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.utils.Utils;
import qsoft.hacktbilisi.app.utils.it.gmariotti.android.example.colorpicker.calendarstock.ColorPickerDialog;
import qsoft.hacktbilisi.app.utils.it.gmariotti.android.example.colorpicker.calendarstock.ColorPickerSwatch;

/**
 * Created by andrii on 20.12.14.
 */
public class EditLessonActivity extends Activity implements View.OnClickListener {

    private Context context;

    private FrameLayout flNewLessonColor;
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
        flNewLessonColor = (FrameLayout) findViewById(R.id.fl_new_lesson_color);
    }

    private void setupViews() {
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
        }
    }
}
