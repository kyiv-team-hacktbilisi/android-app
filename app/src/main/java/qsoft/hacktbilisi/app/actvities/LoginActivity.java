package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import qsoft.hacktbilisi.app.R;


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
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .interpolate(new DecelerateInterpolator())
                .playOn(bSignIn);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .interpolate(new DecelerateInterpolator())
                .playOn(bSignUp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_sign_up:
                if (checkUserExisting()) {
                    YoYo.with(Techniques.Shake).duration(700).playOn(email);
                    YoYo.with(Techniques.Shake).duration(700).playOn(pass);
                } else {
                    // todo sign up request
                    Intent intent = new Intent(context, ChooseUniversityActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.b_sign_in:
                // todo sign in request
                if (checkPass()) {
                    YoYo.with(Techniques.Shake).duration(700).playOn(email);
                    YoYo.with(Techniques.Shake).duration(700).playOn(pass);
                } else {
                    // todo sign up request
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

    private boolean checkUserExisting() {
        //fixme write real check!
        return false;
    }
}
