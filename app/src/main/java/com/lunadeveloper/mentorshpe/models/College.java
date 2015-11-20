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

@ParseClassName("College")
public class College extends ParseObject {

    public College() {
        // A default constructor is required.
    }

    public ParseFile getImage() {
        return this.getParseFile("img");
    }

    public void setImage(ParseFile image) {
        put("img", image);
    }
    //name
    public void setName(String name) {
        this.put("name", name);
    }

    public String getName() {
        return this.getString("name");
    }
    //city
    public void setCity(String city) {
        this.put("city", city);
    }
    public String getCity() {
        return this.getString("city");
    }
    //state
    public void setState(String state) {
        this.put("state", state);
    }

    public String getState() {
        return this.getString("state");
    }
}