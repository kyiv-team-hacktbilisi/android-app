package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.os.Bundle;
import qsoft.hacktbilisi.app.R;

/**
 * Created by andrii on 20.12.14.
 */
public class ChooseTermActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_term);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setupViews();
    }

    private void initViews() {

    }

    private void setupViews() {

    }
}
