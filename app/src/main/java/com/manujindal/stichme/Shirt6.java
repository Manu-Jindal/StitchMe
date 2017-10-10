package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

public class Shirt6 extends AppCompatActivity {
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
        setContentView(R.layout.activity_shirt6);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        ShirtId = intent.getLongExtra("ShirtId1", -1);

        source1 = intent.getStringExtra("source11");


        Log.i("OrderId in Shirt6: ", Long.toString(OrderId));
        Log.i("CustomerId in Shirt6: ", Long.toString(CustomerId));
        Log.i("ShirtId in Shirt6: ", Long.toString(ShirtId));
        Log.i("Source in Shirt6: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);


    }
    public void nextActFromMain(View view) {

        Intent intent = new Intent(this, AddItem.class);

        String Shirt_PlacketType;
        Shirt_PlacketType = view.getTag().toString();

        try {

            String sql = "UPDATE shirts SET Shirt_PlacketType = '"+Shirt_PlacketType+"' WHERE ShirtId = "+ShirtId;
            database.execSQL(sql);

            Log.i("OrderId in Shirt6: ", Long.toString(OrderId));
            Log.i("CustomerId in Shirt6: ", Long.toString(CustomerId));
            Log.i("ShirtId in Shirt6: ", Long.toString(ShirtId));
            Log.i("Source in Shirt6: ", source);


            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);

            intent.putExtra("source11", source1);

            Cursor c = database.rawQuery("SELECT * FROM shirts", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result ShirtPlcktType: ", c.getString(c.getColumnIndex("Shirt_PlacketType")));
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
