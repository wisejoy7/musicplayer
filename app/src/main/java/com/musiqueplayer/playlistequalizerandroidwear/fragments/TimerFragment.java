package com.musiqueplayer.playlistequalizerandroidwear.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.appthemeengine.ATE;
import com.musiqueplayer.playlistequalizerandroidwear.R;
import com.musiqueplayer.playlistequalizerandroidwear.TimeService;
import com.musiqueplayer.playlistequalizerandroidwear.activities.BaseActivity;
import com.musiqueplayer.playlistequalizerandroidwear.listeners.MusicStateListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class TimerFragment extends Fragment implements MusicStateListener {
    Button button;
    ListView listView;SharedPreferences.Editor editor;
    ArrayAdapter<String> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_timer, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final ArrayList<String> selectedItems = new ArrayList<String>();
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.playing_timer);

        editor  = getContext().getSharedPreferences("sleepmode", MODE_PRIVATE).edit();

        listView = (ListView) rootView.findViewById(R.id.list);

        String[] sports = getResources().getStringArray(R.array.sports_array);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_single_choice, sports);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, int position, long id) {
                //editor.clear();

                getContext().stopService(new Intent(getContext(), TimeService.class));

                switch (position){
                    case 0:
                            editor.putInt("timerend", -1);
                            editor.commit();
                        Toast.makeText(getContext(), "Sleep mode turned off", Toast.LENGTH_SHORT).show();
                          break;

                    case 1:
                            editor.putInt("timerend", 600); // 10 mins
                            editor.commit();


                          break;

                    case 2:

                        editor.putInt("timerend", 1200);  // 30 mins = 20 * 60
                        editor.commit();

                        break;

                    case 3:

                            editor.putInt("timerend", 1800);  // 30 mins = 30 * 60
                            editor.commit();

                         break;

                    case 4:

                            editor.putInt("timerend", 3600);   // 60 mins = 60*60
                            editor.commit();

                          break;

                    case 5:
                            editor.putInt("timerend", 5400);   // 90 mins = 90*60
                            editor.commit();

                         break;

                    case 6:
                            editor.putInt("timerend", 60*120);   // 120 mins = 90*120
                            editor.commit();

                         break;

                    case 7:
                            editor.putInt("timerend", 3*60*60);   // 120 mins = 90*120
                            editor.commit();

                         break;

                    case 8:
                            editor.putInt("timerend", 5*60*60);   // 120 mins = 90*120
                            editor.commit();

                       break;

                    case 9:
                            editor.putInt("timerend", 6*60*60);   // 6*60*60
                            editor.commit();

                         break;

                    case 10:
                            editor.putInt("timerend", 9*60*60);   // 120 mins = 90*120
                            editor.commit();

                        break;

                    case 11:

                            editor.putInt("timerend", 12*60*60);   // 120 mins = 90*120
                            editor.commit();
                        break;

                }
                getContext().startService(new Intent(getContext(), TimeService.class));
                 }



                                   });



/*
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(null);

        new QueueFragment.loadQueueSongs().execute("");*/

        SharedPreferences prefs = getContext().getSharedPreferences("sleepmode", MODE_PRIVATE);

        int NOTIFY_INTERVAL = prefs.getInt("timerend", -1); //0 is the default value.

        switch (NOTIFY_INTERVAL){
            case -1 : listView.setItemChecked(0,true); break;
            case 10 : listView.setItemChecked(1,true); break;
            case 1200 : listView.setItemChecked(0,true); break;
            case 1800 : listView.setItemChecked(2,true); break;
            case 3600 : listView.setItemChecked(3,true); break;
            case 5400 : listView.setItemChecked(4,true); break;
            case 7200 : listView.setItemChecked(5,true); break;
            case 10800 : listView.setItemChecked(6,true); break;
            case 18000 : listView.setItemChecked(7,true); break;
            case 21600 : listView.setItemChecked(8,true); break;
            case 32400 : listView.setItemChecked(9,true); break;
            case 43200 : listView.setItemChecked(10,true); break;
        }

        ((BaseActivity) getActivity()).setMusicStateListenerListener(this);

        return rootView;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("dark_theme", true)) {
            ATE.apply(this, "dark_theme");
        } else {
            ATE.apply(this, "light_theme");
        }
    }

    public void restartLoader() {

    }

    public void onPlaylistChanged() {

    }

    public void onMetaChanged() {
    }

    }

