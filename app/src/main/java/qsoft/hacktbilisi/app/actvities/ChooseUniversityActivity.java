package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.melnykov.fab.FloatingActionButton;
import qsoft.hacktbilisi.app.R;

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
        initViews();
        context = this;
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
        String[] universities = {"KPI", "Not KPI", "NAU", "Georgian University"};
        ArrayAdapter adapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, universities);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(this);
        bNext.setOnClickListener(this);
        bNext.show(false);
        YoYo.with(Techniques.ZoomInUp)
                .duration(700)
                .interpolate(new DecelerateInterpolator())
                .playOn(bNext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_next_to_choose_group:
                Intent intent = new Intent(context, ChooseGroupActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        autoCompleteTextView.clearFocus();
    }
}
