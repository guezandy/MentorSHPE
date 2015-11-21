package com.lunadeveloper.mentorshpe;


import android.app.Activity;
import android.content.Intent;
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
import com.lunadeveloper.mentorshpe.models.Mentor;
import com.lunadeveloper.mentorshpe.models.Mentorship;
import com.lunadeveloper.mentorshpe.service.IParseCallback;
import com.lunadeveloper.mentorshpe.service.ParseService;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RegisterNewMentorActivity extends Activity {
    private final String TAG = RegisterNewMentorActivity.class.getSimpleName();

    protected EditText mEditUsername;
    protected EditText mEditFullName;
    protected EditText mEditPassword;
    protected EditText mEditPasswordConfirm;
    protected EditText mHomeTown;
    protected TextView mRegisterAccount;
    private ParseService mParseService;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parse_register_mentor);
        mLayout = (LinearLayout) findViewById(R.id.mentor_layout);
        mEditUsername = (EditText) findViewById(R.id.mentor_username);
        mEditFullName = (EditText) findViewById(R.id.mentor_fullname);
        mEditPassword = (EditText) findViewById(R.id.mentor_password);
        mEditPasswordConfirm = (EditText) findViewById(R.id.mentor_confirmpassword);
        mHomeTown = (EditText) findViewById(R.id.mentor_hometown);
        mRegisterAccount = (TextView) findViewById(R.id.mentor_register_button);
        mParseService = new ParseService(this.getApplicationContext());

        //mentor year options
        final Spinner mYear = (Spinner) findViewById(R.id.mentor_year_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mentor_year, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mYear.setAdapter(adapter);

        //mentor major option
        final Spinner mentor_major = (Spinner) findViewById(R.id.mentor_major_spinner);
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
                mentor_major.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onFail(String message) {

            }
        });

        //mentor major option
        final Spinner mentor_college = (Spinner) findViewById(R.id.mentor_college_spinner);
        final List<String> colleges = new ArrayList<String>();
        mParseService.getColleges(new IParseCallback<List<College>>() {
            @Override
            public void onSuccess(List<College> items) {
                for (College m : items) {
                    colleges.add(m.getString("name"));
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, colleges); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mentor_college.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onFail(String message) {

            }
        });

        final HashMap<String,Mentorship> map = new HashMap<String, Mentorship>();
        mParseService.getMentorship(new IParseCallback<List<Mentorship>>() {
            @Override
            public void onSuccess(final List<Mentorship> items) {
                for (final Mentorship m : items) {
                    CheckBox checkBox = new CheckBox(getApplicationContext());
                    //checkBox.setId(i);
                    checkBox.setText(m.getName());
                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println("Selected "+m.getName());
                            if(map.containsKey(m.getObjectId())) { //delete if it's in the map
                                map.remove(m.getObjectId());
                            } else { //add if its not in the map
                                map.put(m.getObjectId(), m);
                            }
                            System.out.println("SIZE: "+ map.size());
                        }
                    });
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
                if (validateFields()) {
                    if (validatePasswordMatch()) {
                        //final String[] men = new String[map.size()];
                        final Mentorship[] men = new Mentorship[map.size()];
                        map.values().toArray(men);
                        Mentor newMentor = new Mentor();
                        newMentor.setUsername(mEditUsername.getText().toString());
                        newMentor.setPassword(mEditPassword.getText().toString());
                        newMentor.setName(mEditFullName.getText().toString());
                        newMentor.setSchool(mentor_college.getSelectedItem().toString());
                        newMentor.setMajor(mentor_major.getSelectedItem().toString());
                        newMentor.setYear(mYear.getSelectedItem().toString());
                        newMentor.setHometown(mHomeTown.getText().toString());
                        newMentor.setIsMentor();
                        newMentor.setMentorship(men);
                        System.out.println("CALLING SAVE");
                        newMentor.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                System.out.println("SAVE COMPELTE");
                                Intent i = new Intent(RegisterNewMentorActivity.this, ParseLoginDispatchActivity.class);
                                startActivity(i);
                            }
                        });
                    } else {
                        Toast.makeText(v.getContext(), "Password doesn't match",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(v.getContext(), "Fields not filled in", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

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