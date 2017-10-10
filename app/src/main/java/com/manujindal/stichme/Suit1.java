package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Suit1 extends AppCompatActivity {
    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    long SuitId;

    String source1;

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suit1);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();

        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        SuitId = intent.getLongExtra("SuitId1", -1);

        source1 = intent.getStringExtra("source11");


        Log.i("OrderId in Suit1: ", Long.toString(OrderId));
        Log.i("CustomerId in Suit1: ", Long.toString(CustomerId));
        Log.i("SuitId in Suit1: ", Long.toString(SuitId));
        Log.i("Source in Suit1: ", source);


        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);


        double billAmount=0;

        Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId ="+OrderId, null);
        if(cursor1.moveToFirst()) {
            billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
            Log.i("Curr BillAmount:", Double.toString(billAmount));
        }


    }
    public void nextActFromMain(View view) {

        Intent intent = new Intent(this, Suit2.class);

        String Suit_FitType;
        Suit_FitType = view.getTag().toString();


        try {

            String sql = "UPDATE suits SET Suit_FitType = '"+Suit_FitType+"' WHERE SuitId = "+SuitId;
            database.execSQL(sql);

            Log.i("OrderId in Suit1: ", Long.toString(OrderId));
            Log.i("CustomerId in Suit1: ", Long.toString(CustomerId));
            Log.i("SuitId in Suit1: ", Long.toString(SuitId));
            Log.i("Source in Suit1: ", source);



            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);
            intent.putExtra("SuitId1", SuitId);

            intent.putExtra("source11", source1);


            Cursor c = database.rawQuery("SELECT * FROM suits", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result Suit_FitType: ", c.getString(c.getColumnIndex("Suit_FitType")));
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