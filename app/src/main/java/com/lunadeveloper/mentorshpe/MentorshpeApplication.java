package com.lunadeveloper.mentorshpe;

import com.lunadeveloper.mentorshpe.MainActivity;
//import com.reflection.model.RModel;

import  com.lunadeveloper.mentorshpe.R;
import com.parse.Parse;
import com.parse.ParseACL;
//import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.ParseAnalytics;

import android.content.Context;
import android.content.SharedPreferences;

import android.app.Application;
import android.util.Log;

public class MentorshpeApplication extends Application {
    private final static String TAG = MentorshpeApplication.class.getSimpleName();
    public static final boolean APPDEBUG = false;

    // Debugging tag for the application
    public static final String APPTAG = "Mentorship";

    private static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"Application onCreate");
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Yofo9gCiphLKyi1WxAXDGEu89rVrenrHk5rEIfom", "LS5PjjwfYRST4vc9pXKbnqc61CotwpNbelrIeCGx");
    }
}