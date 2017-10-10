package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Trouser4 extends AppCompatActivity {

    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    long TrouserId;

    String source1;

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouser4);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();

        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        TrouserId = intent.getLongExtra("TrouserId1", -1);

        source1 = intent.getStringExtra("source11");


        Log.i("OrderId in Trouser4: ", Long.toString(OrderId));
        Log.i("CustomerId in Trsr4: ", Long.toString(CustomerId));
        Log.i("TrouserId in Trouser4: ", Long.toString(TrouserId));
        Log.i("Source in Trouser4: ", source);


        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

    }
    public void nextActFromMain(View view) {

        Intent intent = new Intent(this, Trouser5.class);

        String Trouser_BackPocketStyleType;
        Trouser_BackPocketStyleType = view.getTag().toString();


        try {

            String sql = "UPDATE trousers SET Trouser_BackPocketStyleType = '"+Trouser_BackPocketStyleType+"' WHERE TrouserId = "+TrouserId;
            database.execSQL(sql);

            Log.i("OrderId in Trouser4: ", Long.toString(OrderId));
            Log.i("CustomerId in Trsr4: ", Long.toString(CustomerId));
            Log.i("TrouserId in Trouser4: ", Long.toString(TrouserId));
            Log.i("Source in Trouser4: ", source);

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);
            intent.putExtra("TrouserId1", TrouserId);

            intent.putExtra("source11", source1);


            Cursor c = database.rawQuery("SELECT * FROM trousers", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result TrsrBckPcktStyl:", c.getString(c.getColumnIndex("Trouser_BackPocketStyleType")));
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