package com.lunadeveloper.mentorshpe.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lunadeveloper.mentorshpe.ParseLoginDispatchActivity;
import com.lunadeveloper.mentorshpe.models.College;
import com.lunadeveloper.mentorshpe.models.Goal;
import com.lunadeveloper.mentorshpe.models.Major;
import com.lunadeveloper.mentorshpe.models.Mentee;
import com.lunadeveloper.mentorshpe.models.Mentor;
import com.lunadeveloper.mentorshpe.models.Mentorship;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Service class to handle interactions with Parse.
 * <p/>
 * Note: This class does not spawn new Threads.
 * <p/>
 */
public class ParseService {
    private final String TAG = ParseService.class.getSimpleName();
    private Context context;
    public boolean APPDEBUG = false;

    public ParseService(Context context) {
        this.context = context;
    }


    public void registerNewMentor(final Context context, List<String> registerDetails) {
        final Mentee user = new Mentee();
        // username is set to email
        user.setUsername(registerDetails.get(0));
        user.setPassword(registerDetails.get(1));
        user.setName(registerDetails.get(2));
        //user.setEarly(registerDetails.get(3));

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(
                            context,
                            "Registration Successful\n",
                            Toast.LENGTH_SHORT).show();
                    // Hooray! Let them use the app now.
                    Intent i = new Intent(context, ParseLoginDispatchActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(i);
                } else {
                    Toast.makeText(context, "Registration Failed: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG, "Login failed: " + e.getMessage());
                }
            }
        });
    }

    //get a list of all majors
    public void getMajors(final IParseCallback<List<Major>> eventsCallback) {
        ParseQuery<Major> majorQuery = ParseQuery.getQuery("Major");
        majorQuery.findInBackground(new FindCallback<Major>() {
            @Override
            public void done(List<Major> results, ParseException e) {
                if (e != null) {
                    // There was an error
                    Log.i(TAG, "QUERY NOT: " + e.getMessage());

                } else {
                    Log.i(TAG, "QUERY SUCECESSFUL");
                    //add results to the callback
                    eventsCallback.onSuccess(results);
                }
            }
        });
    }

    //get a list of all majors
    public void getColleges(final IParseCallback<List<College>> eventsCallback) {
        ParseQuery<College> collegeQuery = ParseQuery.getQuery("College");
        collegeQuery.findInBackground(new FindCallback<College>() {
            @Override
            public void done(List<College> results, ParseException e) {
                if (e != null) {
                    // There was an error
                } else {
                    Log.i(TAG, "QUERY SUCECESSFUL");
                    //add results to the callback
                    eventsCallback.onSuccess(results);
                }
            }
        });
    }

    //get a list of all mentorships
    public void getMentorship(final IParseCallback<List<Mentorship>> eventsCallback) {
        ParseQuery<Mentorship> mentorshipQuery = ParseQuery.getQuery("Mentorship");
        mentorshipQuery.findInBackground(new FindCallback<Mentorship>() {
            @Override
            public void done(List<Mentorship> results, ParseException e) {
                if (e != null) {
                    // There was an error
                } else {
                    Log.i(TAG, "QUERY SUCECESSFUL");
                    //add results to the callback
                    eventsCallback.onSuccess(results);
                }
            }
        });
    }
}