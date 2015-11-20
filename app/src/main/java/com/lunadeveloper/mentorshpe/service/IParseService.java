package com.lunadeveloper.mentorshpe.service;


import android.content.Context;
import android.database.DataSetObserver;
import android.widget.BaseAdapter;


import java.util.List;


public interface IParseService {

    public void registerNewUser(final Context context, List<String> registerDetails);

}