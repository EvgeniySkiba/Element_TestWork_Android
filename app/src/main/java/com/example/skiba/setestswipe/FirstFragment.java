package com.example.skiba.setestswipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class FirstFragment extends Fragment {

    ViewPager viewPager;

    ListView listView;
    EditText editText;
    CustomSearchAdapter adapter;
    View header1;
    View rootView;
    final String LOG_TAG = "log";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] names = getResources().getStringArray(R.array.namesArray);
        rootView = inflater.inflate(R.layout.fragment_one, container,
                false);

        header1 = inflater.inflate(R.layout.header, null);
        TextView htv = (TextView) header1.findViewById(R.id.tvTitle);
        htv.setText(getResources().getString(R.string.headerText));

        listView = (ListView) rootView.findViewById(R.id.lv);
        editText = (EditText) rootView.findViewById(R.id.editText);

        ArrayList<String> values = new ArrayList<String>(Arrays.asList(names));

        adapter = new CustomSearchAdapter(getActivity(), values);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });

        fillList();

        return rootView;
    }

    void fillList() {

        try {
            listView.addHeaderView(header1);
            listView.setAdapter(adapter);

        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }
}
