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
import com.lunadeveloper.mentorshpe.models.Goal;
import com.lunadeveloper.mentorshpe.models.Major;
import com.lunadeveloper.mentorshpe.models.Mentee;
import com.lunadeveloper.mentorshpe.models.Mentorship;
import com.lunadeveloper.mentorshpe.service.IParseCallback;
import com.lunadeveloper.mentorshpe.service.ParseService;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.HashMap;
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
        final List<Major> majorsList = new ArrayList<Major>();
        System.out.println("CALLING PARSE SERVICE");
        mParseService.getMajors(new IParseCallback<List<Major>>() {
            @Override
            public void onSuccess(List<Major> items) {
                for (Major m : items) {
                    majors.add(m.getString("name"));
                    majorsList.add(m);
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
        final List<College> collegeList = new ArrayList<College>();
        mParseService.getColleges(new IParseCallback<List<College>>() {
            @Override
            public void onSuccess(final List<College> items) {
                for (College m : items) {
                    collegeList.add(m);
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

        final HashMap<String, Mentorship> map = new HashMap<String,Mentorship>();
        mParseService.getMentorship(new IParseCallback<List<Mentorship>>() {
            @Override
            public void onSuccess(List<Mentorship> items) {
                for (final Mentorship m : items) {
                    CheckBox checkBox = new CheckBox(getApplicationContext());
                    //checkBox.setId(m.getName());
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
                        final Mentee newMentee = new Mentee();
                        /*
                        final Goal goal = new Goal();
                        goal.setCollege(collegeList.get(mentee_college.getSelectedItemPosition()));
                        goal.setMajor(majorsList.get(mentee_major.getSelectedItemPosition()));
                        goal.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                newMentee.setGoal(goal);
                            }
                        });*/

                        newMentee.setUsername(mEditUsername.getText().toString());
                        newMentee.setPassword(mEditPassword.getText().toString());
                        newMentee.setName(mEditFullName.getText().toString());
                        newMentee.setCollegeGoal(mentee_college.getSelectedItem().toString());
                        newMentee.setMajorGoal(mentee_major.getSelectedItem().toString());
                        newMentee.setYear(mYear.getSelectedItem().toString());
                        newMentee.setHometown(mHomeTown.getText().toString());
                        newMentee.setIsMentor();
                        Mentorship[] men = new Mentorship[map.size()];
                        map.values().toArray(men);
                        newMentee.setMentorship(men);
                        System.out.println("CALLING SAVE");
                        newMentee.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                System.out.println("SAVE COMPELTE");
                                Intent i = new Intent(RegisterNewMenteeActivity.this, ParseLoginDispatchActivity.class);
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