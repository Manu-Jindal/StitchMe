package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EnterTrialDate extends AppCompatActivity {

    Intent intent;
    long OrderId;
    long CustomerId;
    String source;

    String source1;
    SQLiteDatabase database;

    DatePicker datePicker1;
    int year, month, date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_trial_date);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");

        Log.i("OrderId in TrialDate: ", Long.toString(OrderId));
        Log.i("CustomerId in TrialDte:", Long.toString(CustomerId));
        Log.i("Source in TrialDate: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);


        datePicker1 = (DatePicker) findViewById(R.id.datePicker1);

    }
    public void nextActFromMain(View view) {


        Intent intent = new Intent(this, EnterDeliveryDate.class);
        int year = datePicker1.getYear();
        int month = datePicker1.getMonth();
        month++;
        int date = datePicker1.getDayOfMonth();



        Calendar now = Calendar.getInstance();
        int yeartoday = now.get(Calendar.YEAR);
        int monthtoday = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int daytoday = now.get(Calendar.DAY_OF_MONTH);

        Date trialdate = new Date();
        Date orderdate = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            trialdate = sdf.parse(year+"-"+month+"-"+date);
            orderdate = sdf.parse(yeartoday+"-"+monthtoday+"-"+daytoday);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(trialdate.compareTo(orderdate) > 0) {

            try {
                String sql = "UPDATE orders SET TrialDate = '" + year + "-" + month + "-" + date + "' WHERE OrderId = " + OrderId;
                database.execSQL(sql);

                Log.i("OrderId in Email: ", Long.toString(OrderId));
                Log.i("CustomerId in Email: ", Long.toString(CustomerId));
                Log.i("Source in Email: ", source);

                intent.putExtra("OrderId1", OrderId);
                intent.putExtra("source1", source);
                intent.putExtra("CustomerId1", CustomerId);


            } catch (Exception e) {
                e.printStackTrace();
            }
            intent.putExtra("trialyear1", year);
            intent.putExtra("trialmonth1", month);
            intent.putExtra("trialdate1", date);

            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Trial Date should be ahead of the order date, ie "+yeartoday+"-"+monthtoday+"-"+daytoday+"!", Toast.LENGTH_LONG).show();
        }

    }
}