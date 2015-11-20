package com.lunadeveloper.mentorshpe.service;

/**
 * Callback interface to allow us to asyncronously retrieve data from Parse into our system.
 * Created by andrew on 11/20/2015
 */
public interface IParseCallback<T> {

    /**
     * Called if we successfully got the elements.
     * @param items
     */
    public void onSuccess(T items);

    /**
     * Called if something goes wrong.
     * @param message
     */
    public void onFail(String message);
}