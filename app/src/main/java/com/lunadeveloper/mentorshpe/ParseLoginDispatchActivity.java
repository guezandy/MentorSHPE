package com.lunadeveloper.mentorshpe;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


/**
 * Placeholder for ParseLoginActivity.  Will we be using this or rolling our own?
 */
public class ParseLoginDispatchActivity extends Activity {

    private final String TAG = ParseLoginDispatchActivity.class.getSimpleName();
    private Button fbLoginButton;
    private Button loginButton;
    private TextView mentor_register_button;
    private TextView mentee_register_button;
    private Dialog progressDialog;

    private EditText username;
    private EditText password;
    private String mUsername;
    private String mPassword;
    private TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_login);

        username = (EditText) findViewById(R.id .loginUsername);
        password = (EditText) findViewById(R.id.loginPassword);
        mErrorMessage = (TextView) findViewById(R.id.errorMessage);


        mentor_register_button = (TextView) findViewById(R.id.mentor_register_button);
        mentor_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent i = new Intent(ParseLoginDispatchActivity.this,
                        RegisterNewMentorActivity.class);
                startActivity(i);
            }
        });

        mentee_register_button = (TextView) findViewById(R.id.mentee_register_button);
        mentee_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent i = new Intent(ParseLoginDispatchActivity.this,
                        RegisterNewMenteeActivity.class);
                startActivity(i);
            }
        });

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                onLoginButtonClicked();
            }
        });

        //TODO: remove this debugging code set to true to skip login
        boolean debugMode = true;
        if (debugMode) {
            username.setText("a");
            password.setText("a");
            loginButton.callOnClick();
        }
    }


    /**
     * Handles regular Login
     */
    private void onLoginButtonClicked() {
        Log.i(TAG, "onLoginButtonClicked");
        ParseLoginDispatchActivity.this.progressDialog = ProgressDialog.show(
                ParseLoginDispatchActivity.this, "", "Logging in...", true);
        if (validateFields()) {
            mUsername = username.getText().toString();
            mPassword = password.getText().toString();
            if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
                mErrorMessage
                        .setText("Please enter a valid username and password.");
                this.progressDialog.dismiss();
            } else {
                // showProgress();
                userLogin();
            }
        } else {
            this.progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),
                    "Please Insert valid Username and Password",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // TODO: set the limits on sizes of username length and password length and more login rules
    private boolean validateFields() {
        Log.i(TAG, "validateFields");
        if (username.length() > 0 && password.getText().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Handles parse user login
     */
    public void userLogin() {
        Log.i(TAG, "userLogin");
        ParseUser.logInInBackground(mUsername, mPassword, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(),
                            "Welcome, " + user.getString("first_name"),
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ParseLoginDispatchActivity.this,
                            MainActivity.class);
                    startActivity(i);
                } else {
                    // Signup failed. Look at the ParseException to see what
                    // happened.
                    Log.e(TAG, "Login failed: " + e.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Login failed please try again", Toast.LENGTH_SHORT)
                            .show();
                    ParseLoginDispatchActivity.this.progressDialog.dismiss();
                }
            }
        });
    }

    /**
     * Starts the Main Activity.
     */
    public void startMainActivity() {
        Log.i(TAG, "starting Main Activity");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //@Override
    protected Class<?> getTargetClass() {
        return MainActivity.class;
    }
}