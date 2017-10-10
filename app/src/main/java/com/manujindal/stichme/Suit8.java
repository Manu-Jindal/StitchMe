package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Suit8 extends AppCompatActivity {

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
        setContentView(R.layout.activity_suit8);




        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        intent = getIntent();

        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        SuitId = intent.getLongExtra("SuitId1", -1);

        source1 = intent.getStringExtra("source11");

        Log.i("OrderId in Suit8: ", Long.toString(OrderId));
        Log.i("CustomerId in Suit8: ", Long.toString(CustomerId));
        Log.i("SuitId in Suit8: ", Long.toString(SuitId));
        Log.i("Source in Suit8: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

    }
    public void nextActFromMain(View view) {

        Intent intent;

        String Suit_VentType;
        Suit_VentType = view.getTag().toString();


        try {

            String sql = "UPDATE suits SET Suit_VentType = '"+Suit_VentType+"' WHERE SuitId = "+SuitId;
            database.execSQL(sql);

            Log.i("OrderId in Suit8: ", Long.toString(OrderId));
            Log.i("CustomerId in Suit8: ", Long.toString(CustomerId));
            Log.i("SuitId in Suit8: ", Long.toString(SuitId));
            Log.i("Source in Suit8: ", source);
            Cursor c = database.rawQuery("SELECT * FROM suits WHERE SuitId = "+SuitId, null);
            c.moveToFirst();
            Log.i("Result Suit_VentType:", c.getString(c.getColumnIndex("Suit_VentType")));
            Log.i("Result Suit_VestType:", Integer.toString(c.getColumnIndex("Suit_VestType")));
            String Suit_VestType = c.getString((c.getColumnIndex("Suit_VestType")));

            if(Suit_VestType.equals("Yes"))
            {

                intent = new Intent(this, Suit9.class);


                intent.putExtra("OrderId1", OrderId);
                intent.putExtra("source1", source);
                intent.putExtra("CustomerId1", CustomerId);
                intent.putExtra("SuitId1", SuitId);

                intent.putExtra("source11", source1);

                startActivity(intent);

            }
            else
            {
                intent = new Intent(this, AddItem.class);


                intent.putExtra("OrderId1", OrderId);
                intent.putExtra("source1", source);
                intent.putExtra("CustomerId1", CustomerId);
                intent.putExtra("SuitId1", SuitId);

                intent.putExtra("source11", source1);

                startActivity(intent);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}