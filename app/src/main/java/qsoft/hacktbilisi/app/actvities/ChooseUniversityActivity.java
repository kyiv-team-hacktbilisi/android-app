package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import qsoft.hacktbilisi.app.R;

/**
 * Created by andrii on 20.12.14.
 */
public class ChooseUniversityActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Context context;

    private AutoCompleteTextView autoCompleteTextView;
    private Button bNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_university);

        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setupViews();
    }

    private void initViews() {
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.actv_choose_university);
        bNext = (Button) findViewById(R.id.b_next_to_choose_group);
    }

    private void setupViews() {
        String[] universities = {"KPI", "Not KPI", "NAU", "Georgian University"};
        ArrayAdapter adapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, universities);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(this);
        bNext.setOnClickListener(this);
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