package com.lunadeveloper.mentorshpe;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lunadeveloper.mentorshpe.models.College;
import com.lunadeveloper.mentorshpe.models.Major;
import com.lunadeveloper.mentorshpe.models.Mentorship;
import com.lunadeveloper.mentorshpe.service.IParseCallback;
import com.lunadeveloper.mentorshpe.service.ParseService;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class RegisterNewMenteeActivity extends Activity {
    private final String TAG = RegisterNewMenteeActivity.class.getSimpleName();

    protected EditText mEditUsername;
    protected EditText mEditFullName;
    protected EditText mEditPassword;
    protected EditText mEditPasswordConfirm;
    protected Spinner mYear;
    protected EditText mHomeTown;
    protected EditText mSchoolName;
    protected TextView mRegisterAccount;
    private ParseService mParseService;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parse_register_mentee);
        mLayout = (LinearLayout) findViewById(R.id.mentee_layout);
        mEditUsername = (EditText) findViewById(R.id.mentee_username);
        mEditFullName = (EditText) findViewById(R.id.mentee_fullname);
        mEditPassword = (EditText) findViewById(R.id.mentee_password);
        mSchoolName = (EditText) findViewById(R.id.mentee_highschool);
        mEditPasswordConfirm = (EditText) findViewById(R.id.mentee_confirmpassword);
        mHomeTown = (EditText) findViewById(R.id.mentee_hometown);

        mRegisterAccount = (TextView) findViewById(R.id.mentee_register_button);
        mParseService = new ParseService(this.getApplicationContext());

        //mentor year options
        mYear = (Spinner) findViewById(R.id.mentee_year_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mentor_year, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mYear.setAdapter(adapter);

        //mentee major goal option
        final Spinner mentee_major = (Spinner) findViewById(R.id.mentee_major_spinner);
        final List<String> majors = new ArrayList<String>();
        System.out.println("CALLING PARSE SERVICE");
        mParseService.getMajors(new IParseCallback<List<Major>>() {
            @Override
            public void onSuccess(List<Major> items) {
                for (Major m : items) {
                    majors.add(m.getString("name"));
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, majors); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mentee_major.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onFail(String message) {

            }
        });

        //mentee major goal option
        final Spinner mentee_college = (Spinner) findViewById(R.id.mentee_college_spinner);
        final List<String> colleges = new ArrayList<String>();
        mParseService.getColleges(new IParseCallback<List<College>>() {
            @Override
            public void onSuccess(List<College> items) {
                for (College m : items) {
                    colleges.add(m.getString("name"));
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, colleges); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mentee_college.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onFail(String message) {

            }
        });

        mParseService.getMentorship(new IParseCallback<List<Mentorship>>() {
            @Override
            public void onSuccess(List<Mentorship> items) {
                for (Mentorship m : items) {
                    CheckBox checkBox = new CheckBox(getApplicationContext());
                    //checkBox.setId(m.getName());
                    checkBox.setText(m.getName());
                    //checkBox.setOnClickListener();
                    mLayout.addView(checkBox);
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
        mRegisterAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount(v);
            }
        });

    }

    public List<String> getUserInformation() {
        final List<String> registerDetails = new ArrayList<String>();
        registerDetails.add(0, mEditUsername.getText().toString());
        registerDetails.add(1, mEditPassword.getText().toString());
        registerDetails.add(2, mEditFullName.getText().toString());

        return registerDetails;
    }
    public void registerAccount(View view) {
        if (validateFields()) {
            if (validatePasswordMatch()) {
                //processSignup(view);
                mParseService = new ParseService(view.getContext());
                mParseService.registerNewMentor(view.getContext(), getUserInformation());
            } else {
                Toast.makeText(this, "Password doesn't match",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Fields not filled in", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private boolean validateFields() {
        if (mEditFullName.getText().length() > 0
                && mEditPassword.getText().length() > 0
                && mEditPasswordConfirm.getText().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validatePasswordMatch() {
        if (mEditPassword.getText().toString()
                .equals(mEditPasswordConfirm.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
}