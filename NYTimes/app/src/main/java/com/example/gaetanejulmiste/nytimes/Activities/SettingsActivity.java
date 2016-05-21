package com.example.gaetanejulmiste.nytimes.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gaetanejulmiste.nytimes.Fragments.DatePickerFragment;
import com.example.gaetanejulmiste.nytimes.Models.Settings;
import com.example.gaetanejulmiste.nytimes.R;

import java.util.Calendar;

import static android.R.layout.simple_spinner_dropdown_item;

public class SettingsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
     Settings settings;
    private Button datePicker;

    private TextView selectedDate;
    private CheckBox cbShowArts;
    private CheckBox cbShowFashion;
    private CheckBox cbShowSports;
    private Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        selectedDate =(TextView)findViewById(R.id.tvSelectedDate);

        datePicker = (Button) findViewById(R.id.btnOpenDatePicker);
        cbShowArts = (CheckBox) findViewById(R.id.cbArts);
        cbShowFashion = (CheckBox) findViewById(R.id.cbFashion);
        cbShowSports = (CheckBox) findViewById(R.id.cbSports);



        btnSave = (Button) findViewById(R.id.btnSave);



    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        selectedDate.setText(
                new StringBuilder()
                        .append(monthOfYear + 1)
                        .append("/")
                        .append(dayOfMonth)
                        .append("/")
                        .append(year)
                        .append(" ")
        );


    }


    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }


    private void setupSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.sSortOrder);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Set the initial value.
        spinner.setSelection(settings.sortOrder);

        // Attach the onClick listener of the activity.
        spinner.setOnItemSelectedListener(this);
    }

    public void onSettingsSave(View v) {
        boolean isArts=cbShowArts.isChecked();
        boolean isSports=cbShowSports.isChecked();
        boolean isFashion=cbShowFashion.isChecked();
        Intent i = new Intent();
        //i.putExtra();

        finish();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        settings.sortOrder = position;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

