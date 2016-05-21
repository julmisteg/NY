package com.example.gaetanejulmiste.nytimes.Fragments;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.Fragment;


import com.example.gaetanejulmiste.nytimes.Fragments.DatePickerFragment;
import com.example.gaetanejulmiste.nytimes.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaetanejulmiste on 5/20/16.
 */
public class SettingsFragment extends //FragmentActivity
        DialogFragment
{
    private TextView selectedDate;
    private CheckBox cbShowArts;
    private CheckBox cbShowFashion;
    private CheckBox cbShowSports;
    private Button btnSave;
    public Spinner sSortOrder;

    String sortOrder;
    String beginDate;
    String search;

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_settings, container );
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void setupViews (View view){
        selectedDate = (TextView) view.findViewById(R.id.tvSelectedDate);
        sSortOrder = (Spinner) view.findViewById(R.id.sSortOrder);
        cbShowArts = (CheckBox) view.findViewById(R.id.cbArts);
        cbShowFashion = (CheckBox) view.findViewById(R.id.cbFashion);
        cbShowSports = (CheckBox) view.findViewById(R.id.cbSports);
        btnSave = (Button) view.findViewById(R.id.btnSave);

        selectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment dateFragment = new DatePickerFragment();
               // dateFragment.show(getActivity().getFragmentManager(),"DatePickerFragment");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupStringSearch();
                dismiss();
            }
        });

        final List<String> lSpinner = new ArrayList<>();
        lSpinner.add("oldest");
        lSpinner.add("newest");

        ArrayAdapter<String> aSpinner = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,lSpinner);
        sSortOrder.setAdapter(aSpinner);
        sSortOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sortOrder =lSpinner.get(position);
            }
        });

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       setupViews(view);
        String title = getArguments().getString("title","enter name");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }



    private void setupStringSearch() {
        beginDate = selectedDate.getText().toString();
        if (cbShowArts.isChecked()) {
           search =cbShowArts.getText().toString();
            if(cbShowFashion.isChecked()){
                search=cbShowFashion.getText().toString();
            }
            if(cbShowArts.isChecked()){
                search=cbShowArts.getText().toString();
            }
        }
    }

    private static SettingsFragment newInstance(String title){

         SettingsFragment sfrag= new SettingsFragment();
         Bundle args = new Bundle();
         args.putString("title",title);
         sfrag.setArguments(args);
         return sfrag;
    }
}
