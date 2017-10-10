package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Shirt4 extends AppCompatActivity {
    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    long ShirtId;

    String source1;

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shirt4);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        ShirtId = intent.getLongExtra("ShirtId1", -1);

        source1 = intent.getStringExtra("source11");


        Log.i("OrderId in Shirt4: ", Long.toString(OrderId));
        Log.i("CustomerId in Shirt4: ", Long.toString(CustomerId));
        Log.i("ShirtId in Shirt4: ", Long.toString(ShirtId));
        Log.i("Source in Shirt4: ", source);


        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

    }
    public void nextActFromMain(View view) {


        Intent intent = new Intent(this, Shirt5.class);

        String Shirt_FitType;
        Shirt_FitType = view.getTag().toString();

        try {

            String sql = "UPDATE shirts SET Shirt_FitType = '"+Shirt_FitType+"' WHERE ShirtId = "+ShirtId;
            database.execSQL(sql);

            Log.i("OrderId in Shirt4: ", Long.toString(OrderId));
            Log.i("CustomerId in Shirt4: ", Long.toString(CustomerId));
            Log.i("ShirtId in Shirt4: ", Long.toString(ShirtId));
            Log.i("Source in Shirt4: ", source);

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);
            intent.putExtra("ShirtId1", ShirtId);

            intent.putExtra("source11", source1);


            Cursor c = database.rawQuery("SELECT * FROM shirts", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result ShirtFitType: ", c.getString(c.getColumnIndex("Shirt_FitType")));
                c.moveToNext();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        startActivity(intent);
    }

}