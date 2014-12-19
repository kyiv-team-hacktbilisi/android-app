package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import qsoft.hacktbilisi.app.R;


public class LoginActivity extends Activity implements View.OnClickListener {

    private Context context;

    private Button bSignUp;
    private Button bSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setupViews();
    }

    private void initViews() {
        bSignUp = (Button) findViewById(R.id.b_sign_up);
        bSignIn = (Button) findViewById(R.id.b_sign_in);
    }

    private void setupViews() {
        bSignUp.setOnClickListener(this);
        bSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_sign_up:
                // todo sign up request
                Intent intent = new Intent(context, ChooseUniversityActivity.class);
                startActivity(intent);
                break;
            case R.id.b_sign_in:
                // todo sign in request
//                Intent intent2 = new Intent(context, ChooseUniversityActivity.class);
//                startActivity(intent2);
                break;
        }
    }
}
