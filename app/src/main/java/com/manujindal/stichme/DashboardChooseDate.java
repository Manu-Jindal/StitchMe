package com.manujindal.stichme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

public class DashboardChooseDate extends AppCompatActivity {

    DatePicker datePicker1;
    int year, month, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_choose_date);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        datePicker1 = (DatePicker) findViewById(R.id.datePicker1);

    }
    public void nextActFromMain(View view) {



        Intent intent = new Intent(this, DashboardDate.class);
        int year = datePicker1.getYear();
        int month = datePicker1.getMonth();
        month++;
        int date = datePicker1.getDayOfMonth();

        try {
            intent.putExtra("year1", year);
            intent.putExtra("month1", month);
            intent.putExtra("date1", date);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        startActivity(intent);
    }

}
