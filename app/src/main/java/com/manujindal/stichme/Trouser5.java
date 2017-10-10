package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Trouser5 extends AppCompatActivity {

    Intent intent;
    int OrderId;
    int CustomerId;
    String source;
    int TrouserId;

    String source1;

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouser5);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();

        OrderId = intent.getIntExtra("OrderId1", -1);
        CustomerId = intent.getIntExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        TrouserId = intent.getIntExtra("TrouserId1", -1);

        source1 = intent.getStringExtra("source11");

        Log.i("OrderId in Trouser5: ", Integer.toString(OrderId));
        Log.i("CustomerId in Trsr5: ", Integer.toString(CustomerId));
        Log.i("TrouserId in Trouser5: ", Integer.toString(TrouserId));
        Log.i("Source in Trouser5: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        
        /*--
        intent = getIntent();
        id = intent.getIntExtra("id1", -1);
        Log.i("ID in Trouser5 result: ", Integer.toString(id));
        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);
        --*/

    }
    public void nextActFromMain(View view) {

        Intent intent = new Intent(this, Trouser6.class);

        String Trouser_BackPocketType;
        Trouser_BackPocketType = view.getTag().toString();


        try {

            String sql = "UPDATE trousers SET Trouser_BackPocketType = '"+Trouser_BackPocketType+"' WHERE TrouserId = "+TrouserId;
            database.execSQL(sql);

            Log.i("OrderId in Trouser5: ", Integer.toString(OrderId));
            Log.i("CustomerId in Trsr5: ", Integer.toString(CustomerId));
            Log.i("TrouserId in Trouser5: ", Integer.toString(TrouserId));
            Log.i("Source in Trouser5: ", source);


            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);
            intent.putExtra("TrouserId1", TrouserId);

            intent.putExtra("source11", source1);


            Cursor c = database.rawQuery("SELECT * FROM trousers", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result TrsrBckPcktTyp:", c.getString(c.getColumnIndex("Trouser_BackPocketType")));
                c.moveToNext();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        startActivity(intent);

        
        /*--

        Intent intent = new Intent(this, Trouser6.class);
        String Trouser_BackPocketType;
        Trouser_BackPocketType = view.getTag().toString();

        try {
            String sql = "UPDATE abcd11 SET Trouser_BackPocketType = '"+Trouser_BackPocketType+"' WHERE id = "+id;
            database.execSQL(sql);
            Log.i("IDin Trouser5 result1: ", Integer.toString(id));
            intent.putExtra("id1", id);


            Cursor c = database.rawQuery("SELECT * FROM abcd11", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result111", "111");
                Log.i("Result Tsr_BckPcktType:", c.getString(c.getColumnIndex("Trouser_BackPocketType")));
                c.moveToNext();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



        startActivity(intent);
        --*/
    }

}