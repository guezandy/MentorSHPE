package com.lunadeveloper.mentorshpe.models;

/**
 * Created by andrewrodriguez on 11/20/15.
 */


import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/*
 * An extension of ParseUser so we can distinguish
 * different kinds of users
 *
 */

@ParseClassName("Mentee")
public class Mentee extends ParseUser {

    public Mentee() {
        // A default constructor is required.
        this.put("isMentor", false);
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
    //school (No need to make full relation to school object we only need name)
    public void setSchool(College school) {
        this.put("school", school.getName());
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
    //hometown
    public void setHometown(String town) {
        this.put("hometown", town);
    }

    //goal (school + major) //should be a direct relation
    public void setGoal(Goal goal) {
        this.put("goal", goal);
    }
    //TODO: finsih this
    public int getGoal(){
        return 0;
    }

    public void setIsMentor() {
        this.put("isMentor", false);
    }

    public void setCollegeGoal(String school) {
        this.put("goal_college", school);
    }

    public String getCollegeGoal() {
        return this.getString("goal_college");
    }

    public void setMajorGoal(String major) {
        this.put("goal_major", major);
    }

    public String getMajorGoal() {
        return this.getString("goal_major");
    }

    public void setMentorship(Mentorship[] men) {
        ParseRelation relation = this.getRelation("mentorships");
        for(Mentorship m : men) {
            relation.add(m);
        }
    }
}