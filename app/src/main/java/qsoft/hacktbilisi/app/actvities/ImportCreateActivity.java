package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import qsoft.hacktbilisi.app.R;

/**
 * Created by andrii on 20.12.14.
 */
public class ImportCreateActivity extends Activity implements View.OnClickListener {

    private Context context;

    private LinearLayout llImport;
    private LinearLayout llCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_create);

        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setupViews();
    }

    private void initViews() {
        llImport = (LinearLayout) findViewById(R.id.ll_import);
        llCreate = (LinearLayout) findViewById(R.id.ll_create);
    }

    private void setupViews() {
        llImport.setOnClickListener(this);
        llCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_import:
                //todo import request
                break;
            case R.id.ll_create:
                Intent intent = new Intent(context, ChooseUniversityActivity.class);
                startActivity(intent);
                break;
        }
    }
}
