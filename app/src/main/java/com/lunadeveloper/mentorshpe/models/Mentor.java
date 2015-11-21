package com.lunadeveloper.mentorshpe.models;

/**
 * Created by andrewrodriguez on 11/20/15.
 */


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
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
        this.put("isMentor", true);
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

    public void setSchool(String school) {
        this.put("school", school);
    }

    public String getSchool() {
        return this.getString("school");
    }
    //year fresh, soph, junir, senior etc
    public void setYear(String year) {
        this.put("year", year);
    }
    public String getYear() {
        return this.getString("year");
    }
    public void setHometown(String town) {
        this.put("hometown", town);
    }
    public void setMajor(String major){
        this.put("major", major);
    }
    public String getMajor() {
        return this.getString("major");
    }

    public void setMentorshipKeyArray(String[] keys) {
        this.put("mentorshipKeys", keys);
    }

    /* We dont need a join table just use ParseRelation
    public void setMentorship(Mentorship[] men){
        for(Mentorship m : men) {
            ParseObject join = new ParseObject("MentorshipUserjoin");
            join.put("mentorshipId",m.getObjectId());
            join.put("user", ParseUser.getCurrentUser());
            join.put("mentorshipLabel", m.getName());
            join.saveInBackground();
        }
    }*/

    public void setMentorship(Mentorship[] men) {
        ParseRelation relation = this.getRelation("mentorships");
        for(Mentorship m : men) {
            relation.add(m);
        }
    }

    public void setIsMentor(){
        this.put("isMentor", true);
    }
    //mentorship available SAT, ACT, MATH, etc etc

}