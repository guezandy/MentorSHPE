package com.lunadeveloper.mentorshpe.models;

/**
 * Created by andrewrodriguez on 11/20/15.
 */


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

/*
 * An extension of ParseUser so we can distinguish
 * different kinds of users
 *
 */

@ParseClassName("Mentor")
public class Mentor extends ParseUser {

    public Mentor() {
        // A default constructor is required.
    }

    public ParseFile getImage() {
        return this.getParseFile("img");
    }

    public void setImage(ParseFile image) {
        put("img", image);
    }

    //name
    public void setName(String name){
        this.put("name", name);
    }
    public String getName() {
        return this.getString("name");
    }
    //school (TODO: Need to make full relation to college)
    public void setSchool(College school) {
        //this.put("school", school.getName());
    }
    public String getSchool() {
        //return this.getString("school");
        return "";
    }
    //year fresh, soph, junir, senior etc
    public void setYear(int year) {
        this.put("year", year);
    }
    public int getYear() {
        return this.getInt("year");
    }
    //hometown
    public void setHometown(String town) {
        this.put("hometown", town);
    }

    public void setMajor(Major major){
        this.put("major", major);
    }

    public int getMajor(){
        //TODO: Finish this out;
        return 0;
    }

    //mentorship available SAT, ACT, MATH, etc etc

}