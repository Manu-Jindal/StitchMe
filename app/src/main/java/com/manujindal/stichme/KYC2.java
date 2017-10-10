package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class KYC2 extends AppCompatActivity {
    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    String source1;
    SQLiteDatabase database;

    RadioButton occupation1;
    RadioButton occupation2;
    RadioButton occupation3;
    RadioButton occupation4;
    RadioButton preference1;
    RadioButton preference2;
    RadioButton preference3;
    RadioButton preference4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc2);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        source1 = intent.getStringExtra("source11");
        Log.i("OrderId in KYC2: ", Long.toString(OrderId));
        Log.i("CustomerId in KYC2: ", Long.toString(CustomerId));
        Log.i("Source in KYC2: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        occupation1 = (RadioButton) findViewById(R.id.occupation1);
        occupation2 = (RadioButton) findViewById(R.id.occupation2);
        occupation3 = (RadioButton) findViewById(R.id.occupation3);
        occupation4 = (RadioButton) findViewById(R.id.occupation4);
        preference1 = (RadioButton) findViewById(R.id.preference1);
        preference2 = (RadioButton) findViewById(R.id.preference2);
        preference3 = (RadioButton) findViewById(R.id.preference3);
        preference4 = (RadioButton) findViewById(R.id.preference4);

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


        Intent intent = new Intent(this, ClothType.class);
        String occupationFinal;
        if(occupation1.isChecked())
        {
            occupationFinal = occupation1.getText().toString();
        }
        else if(occupation2.isChecked())
        {
            occupationFinal = occupation2.getText().toString();
        }
        else if(occupation3.isChecked())
        {
            occupationFinal = occupation3.getText().toString();
        }
        else if(occupation4.isChecked())
        {
            occupationFinal = occupation4.getText().toString();
        }
        else
        {
            occupationFinal = "Other";
        }
        String preferenceFinal;
        if(preference1.isChecked())
        {
            preferenceFinal = preference1.getText().toString();
        }
        else if(preference2.isChecked())
        {
            preferenceFinal = preference2.getText().toString();
        }
        else if(preference3.isChecked())
        {
            preferenceFinal = preference3.getText().toString();
        }
        else if(preference4.isChecked())
        {
            preferenceFinal = preference4.getText().toString();
        }
        else
        {
            preferenceFinal = "Others";
        }

        try {
            String sql = "UPDATE customers SET Occupation = '"+occupationFinal+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql);
            String sql1 = "UPDATE customers SET TypePreference = '"+preferenceFinal+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql1);

            Log.i("OrderId in KYC2: ", Long.toString(OrderId));
            Log.i("CustomerId in KYC2: ", Long.toString(CustomerId));
            Log.i("Source in KYC2: ", source);

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("source11", source1);
            intent.putExtra("CustomerId1", CustomerId);

            Cursor c = database.rawQuery("SELECT * FROM customers", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result Occupation: ", c.getString(c.getColumnIndex("Occupation")));
                Log.i("Result Preference: ", c.getString(c.getColumnIndex("TypePreference")));
                c.moveToNext();
            }

            String sql2 = "UPDATE orders SET CustomerId = '"+CustomerId+"' WHERE OrderId = "+OrderId;
            database.execSQL(sql);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        startActivity(intent);

        try {
            Log.i("Source in DOB: ", source);
            Log.i("Source1 in DOB: ", source1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
