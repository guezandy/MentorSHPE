package com.lunadeveloper.mentorshpe.models;

/**
 * Created by andrewrodriguez on 11/20/15.
 */


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/*
 * An extension of ParseObject so we can distinguish
 * to easily access information in the class
 *
 */

@ParseClassName("Goal")
public class Goal extends ParseObject {

    public Goal() {
        // A default constructor is required.
    }
    //the user
    public void setUser(Mentee mentee) {
        this.put("user", mentee);
    }

    public ParseUser getUser() {
        return this.getParseUser("user");
    }
    //the college goal
    public void setCollege(College college) {
        this.put("college", college.getName());
    }

    public String getCollege(){
        return this.getString("college");
    }

    //the major goal
    public void setMajor(Major major) {
        this.put("major", major.getName());
    }
    public String getMajor() {
        return this.getString("major");
    }
}
