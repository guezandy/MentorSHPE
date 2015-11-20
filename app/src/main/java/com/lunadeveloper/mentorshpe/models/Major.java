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

@ParseClassName("Major")
public class Major extends ParseObject {

    public Major() {
        // A default constructor is required.
    }

    @Override
    public String toString() {
        return this.getName(); //what spinner displays
    }

    //name
    public void setName(String name) {
        this.put("name", name);
    }

    public String getName() {
        return this.getString("name");
    }
}