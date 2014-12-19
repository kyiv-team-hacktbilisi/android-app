package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import qsoft.hacktbilisi.app.R;

/**
 * Created by andrii on 20.12.14.
 */
public class ChooseGroupActivity extends Activity implements View.OnClickListener {

    private Context context;

    private AutoCompleteTextView autoCompleteTextView;
    private Button bNextCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_group);

        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setupViews();
    }

    private void initViews() {
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.actv_choose_group);
        bNextCreate = (Button) findViewById(R.id.b_next_create);
    }

    private void setupViews() {
        String[] groups = {"group1", "IO-31m", "123456", "IP-21"};
        ArrayAdapter adapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, groups);
        autoCompleteTextView.setAdapter(adapter);

        bNextCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_next_create:
                Intent intent = new Intent(context, ChooseTermActivity.class);
                startActivity(intent);
                break;
        }
    }
}
