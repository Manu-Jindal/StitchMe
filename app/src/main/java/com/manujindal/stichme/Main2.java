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

public class Main2 extends AppCompatActivity {

    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    String source1;
    SQLiteDatabase database;
    DatePicker datePicker;
    int year, month, date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        source1 = intent.getStringExtra("source11");
        try {
            Log.i("OrderId in DOB: ", Long.toString(OrderId));
            Log.i("CustomerId in DOB: ", Long.toString(CustomerId));
            Log.i("Source in DOB: ", source);
            Log.i("Source1 in DOB: ", source1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        datePicker = (DatePicker) findViewById(R.id.datePicker);

        datePicker.setNestedScrollingEnabled(true);

        try {
            Log.i("Source in DOB: ", source);
            Log.i("Source1 in DOB: ", source1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void nextActFromMain(View view) {

        try {
            Log.i("Source in DOB: ", source);
            Log.i("Source1 in DOB: ", source1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, EnterEmail.class);

        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        month++;
        int date = datePicker.getDayOfMonth();

        try {
            String sql = "UPDATE customers SET Dob = '"+year+"-"+month+"-"+date+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql);

            Log.i("OrderId in DOB: ", Long.toString(OrderId));
            Log.i("CustomerId in DOB: ", Long.toString(CustomerId));
            Log.i("Source in DOB: ", source);

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("source11", source1);
            intent.putExtra("CustomerId1", CustomerId);

            /*--
            Cursor c = database.rawQuery("SELECT * FROM customers", null);
            c.moveToFirst();
            while(c!=null)
            {
               Log.i("Result Name: ", c.getString(c.getColumnIndex("Dob")));
                c.moveToNext();
            }
            --*/

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        startActivity(intent);
    }
}