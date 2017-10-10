package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ChooseItem extends AppCompatActivity {
    Intent intent;
    long OrderId;
    long CustomerId;
    String source;

    String source1;

    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_item);



        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();

        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");

        source1 = intent.getStringExtra("source11");

        Log.i("OrderId in ChseItem: ", Long.toString(OrderId));
        Log.i("CustomerId in ChseItm: ", Long.toString(CustomerId));
        Log.i("Source in ChseItem: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        double billAmount=0;

        Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId ="+OrderId, null);
        if(cursor1.moveToFirst()) {
            billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
            Log.i("Curr BillAmount:", Double.toString(billAmount));
        }
    }
    public void shirtselected(View view) {

        Intent intent = new Intent(this, SelectFabric0.class);
        try {
            database.execSQL("INSERT INTO shirts(OrderId) values("+OrderId+")");

            Cursor c = database.rawQuery("SELECT MIN(ShirtId) from shirts", null);
            int ShirtIdindex = c.getColumnIndex("MIN(ShirtId)");
            c.moveToFirst();
            long ShirtId = c.getLong(ShirtIdindex);

            long time= System.currentTimeMillis();
            String sql = "UPDATE shirts SET ShirtId = "+time+" WHERE ShirtId = "+ShirtId;
            database.execSQL(sql);

            ShirtId = time;

            Log.i("Result: ShirtId", Long.toString(ShirtId));
            
            Log.i("OrderId in ChseItem: ", Long.toString(OrderId));
            Log.i("CustomerId in ChseItm: ", Long.toString(CustomerId));
            Log.i("ShirtId in ChseItm: ", Long.toString(ShirtId));
            Log.i("Source in ChseItem: ", source);
            


            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);
            intent.putExtra("ShirtId1", ShirtId);

            intent.putExtra("source11", source1);

            intent.putExtra("selected1", "shirt");

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        startActivity(intent);


    }
    public void suitselected(View view) {



        Intent intent = new Intent(this, SelectFabric0.class);
        try {
            database.execSQL("INSERT INTO suits(OrderId) values("+OrderId+")");

            Cursor c = database.rawQuery("SELECT MIN(SuitId) from suits", null);
            int SuitIdindex = c.getColumnIndex("MIN(SuitId)");
            c.moveToFirst();
            long SuitId = c.getLong(SuitIdindex);

            long time= System.currentTimeMillis();
            String sql = "UPDATE suits SET SuitId = "+time+" WHERE SuitId = "+SuitId;
            database.execSQL(sql);

            SuitId = time;

            Log.i("Result: SuitId", Long.toString(SuitId));

            Log.i("OrderId in ChseItem: ", Long.toString(OrderId));
            Log.i("CustomerId in ChseItm: ", Long.toString(CustomerId));
            Log.i("SuitId in ChseItm: ", Long.toString(SuitId));
            Log.i("Source in ChseItem: ", source);

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);
            intent.putExtra("SuitId1", SuitId);

            intent.putExtra("source11", source1);

            intent.putExtra("selected1", "suit");

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        startActivity(intent);


    }
    public void trouserselected(View view) {

        Intent intent = new Intent(this, SelectFabric0.class);
        try {
            database.execSQL("INSERT INTO trousers(OrderId) values("+OrderId+")");

            Cursor c = database.rawQuery("SELECT MIN(TrouserId) from trousers", null);
            int TrouserIdindex = c.getColumnIndex("MIN(TrouserId)");
            c.moveToFirst();
            long TrouserId = c.getLong(TrouserIdindex);

            long time= System.currentTimeMillis();
            String sql = "UPDATE trousers SET TrouserId = "+time+" WHERE TrouserId = "+TrouserId;
            database.execSQL(sql);

            TrouserId = time;


            Log.i("Result: TrouserId", Long.toString(TrouserId));

            Log.i("OrderId in ChseItem: ", Long.toString(OrderId));
            Log.i("CustomerId in ChseItm: ", Long.toString(CustomerId));
            Log.i("TrouserId in ChseItm: ", Long.toString(TrouserId));
            Log.i("Source in ChseItem: ", source);


            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);
            intent.putExtra("TrouserId1", TrouserId);

            intent.putExtra("source11", source1);

            intent.putExtra("selected1", "trouser");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        startActivity(intent);

    }
    public void otherselected(View view) {

        Intent intent = new Intent(this, Others.class);
        try {
            database.execSQL("INSERT INTO others(OrderId) values("+OrderId+")");

            Cursor c = database.rawQuery("SELECT MIN(OtherId) from others", null);
            int OtherIdindex = c.getColumnIndex("MIN(OtherId)");
            c.moveToFirst();
            long OtherId = c.getLong(OtherIdindex);

            long time= System.currentTimeMillis();
            String sql = "UPDATE others SET OtherId = "+time+" WHERE OtherId = "+OtherId;
            database.execSQL(sql);

            OtherId = time;

            Log.i("Result: OtherId", Long.toString(OtherId));

            Log.i("OrderId in ChseItem: ", Long.toString(OrderId));
            Log.i("CustomerId in ChseItm: ", Long.toString(CustomerId));
            Log.i("OtherId in ChseItm: ", Long.toString(OtherId));
            Log.i("Source in ChseItem: ", source);

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);
            intent.putExtra("OtherId1", OtherId);

            intent.putExtra("source11", source1);

            intent.putExtra("selected1", "other");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        startActivity(intent);

    }
}
