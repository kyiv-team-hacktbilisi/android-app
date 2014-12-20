package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.utils.EmailValidator;


public class LoginActivity extends Activity implements View.OnClickListener {

    private Context context;

    private Button bSignUp;
    private Button bSignIn;
    private EditText pass;
    private EditText email;

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
        email = (EditText) findViewById(R.id.et_email);
        pass = (EditText) findViewById(R.id.et_password);

    }

    private void setupViews() {
        bSignUp.setOnClickListener(this);
        bSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_sign_up:
                if (!new EmailValidator().validate(email.getText().toString())) {
                    YoYo.with(Techniques.Shake).duration(700).playOn(email);
                    YoYo.with(Techniques.Shake).duration(700).playOn(pass);
                } else {
                    ParseUser user = new ParseUser();
                    user.setUsername(email.getText().toString());
                    user.setPassword(pass.getText().toString());
                    user.setEmail(email.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            Intent intent = new Intent(context, ChooseUniversityActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                break;
            case R.id.b_sign_in:
                if (checkPass()) {
                    YoYo.with(Techniques.Shake).duration(700).playOn(email);
                    YoYo.with(Techniques.Shake).duration(700).playOn(pass);
                } else {
                    Intent intent = new Intent(context, ChooseUniversityActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    private boolean checkPass() {
        //fixme write real check!
        return false;
    }

}
