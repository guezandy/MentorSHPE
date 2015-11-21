package com.lunadeveloper.mentorshpe.fragment;


import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lunadeveloper.mentorshpe.R;
import com.lunadeveloper.mentorshpe.service.IParseCallback;
import com.lunadeveloper.mentorshpe.service.ParseService;
import com.parse.ParseObject;



public class FragmentMentorListView extends Fragment {
    public String TAG = FragmentMentorListView.class.getSimpleName();
    private ParseService mParseService;
    private LinearLayout mView;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = (LinearLayout) inflater.inflate(R.layout.fragment_mentor_listview, container, false);
        return mView;
    }
}