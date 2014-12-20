package qsoft.hacktbilisi.app.actvities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import qsoft.hacktbilisi.app.R;
import qsoft.hacktbilisi.app.utils.EmailValidator;
import qsoft.hacktbilisi.app.pojo.User;
import qsoft.hacktbilisi.app.utils.Logger;
import qsoft.hacktbilisi.app.utils.Utils;


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
                validateCredentials(R.id.b_sign_up);
                break;
            case R.id.b_sign_in:
                validateCredentials(R.id.b_sign_in);
                break;
        }
    }

    private void validateCredentials(int id) {
        final String usernameEmail = email.getText().toString().trim();
        final String password = pass.getText().toString().trim();

        // Validate the log in data
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
        if (usernameEmail.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_username));
        }
        if (password.length() == 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_password));
        }
        validationErrorMessage.append(getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            YoYo.with(Techniques.Shake).duration(700).playOn(email);
            YoYo.with(Techniques.Shake).duration(700).playOn(pass);
//            Toast.makeText(LoginActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
//                    .show();
            return;
        }

        User user = new User();
        user.setUsername(usernameEmail);
        user.setPassword(password);
        user.setEmail(usernameEmail);

        if (id == R.id.b_sign_in) {
            sigIn(usernameEmail, password);
        } else if (id == R.id.b_sign_up) {
            signUp(user);
        }

    }

    private void sigIn(String usernameEmail, String password) {
        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage(getString(R.string.progress_login));
        dialog.show();

        User.logInInBackground(usernameEmail, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                dialog.dismiss();
                if (e != null) {
                    Logger.d("error = " + e.getMessage() + "; " + e.getCode());
//                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Shake).duration(700).playOn(email);
                    YoYo.with(Techniques.Shake).duration(700).playOn(pass);
                } else {
                    Logger.d("logged as user=" + user);
                    Utils.saveLoginState(user.getSessionToken(), context);

                    Intent intent = new Intent(context, ChooseUniversityActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    finish();
                }
            }
        });
    }

    private void signUp(User user) {
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
//                dialog.dismiss();
                if (e != null) {
                    if (e.getCode() == 202) {
                        YoYo.with(Techniques.Shake).duration(700).playOn(email);
                        YoYo.with(Techniques.Shake).duration(700).playOn(pass);
                    }
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Logger.d("signed up with id = " + User.getCurrentUser().getObjectId());
                    Intent intent = new Intent(context, ChooseUniversityActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
