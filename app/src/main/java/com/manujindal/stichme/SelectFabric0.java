package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectFabric0 extends AppCompatActivity {
    Intent intent;
    long OrderId;
    long CustomerId;
    String source;

    String source1;

    SQLiteDatabase database;
    long ShirtId;
    long SuitId;
    long TrouserId;
    String selected;

    int itemClicked = 0;

    double updatedBill;

    ListView selectFabricList;
    ListViewAdapter listViewAdapter;
    ArrayList<String> FabricCodelList = new ArrayList<String>();
    ArrayList<String> FabricPricelList = new ArrayList<String>();
    String[] FabricCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_fabric0);



        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();

        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");

        source1 = intent.getStringExtra("source11");

        selectFabricList = (ListView) findViewById(R.id.selectFabricList);

        selected = intent.getStringExtra("selected1");


        Log.i("OrderId in SlctFbrc: ", Long.toString(OrderId));
        Log.i("CustomerId in SlctFbrc:", Long.toString(CustomerId));
        Log.i("Source in SlctFbrc: ", source);

        Log.i("Selected in slctfbrc", selected);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        if(selected.equals("shirt"))
        {
            ShirtId = intent.getLongExtra("ShirtId1", -1);
            Log.i("ShirtId in SlctFbrc: ", Long.toString(ShirtId));

            try {
                Cursor c = database.rawQuery("SELECT * FROM fabrics WHERE FabricType = 'Shirt' ", null);
                if(c.moveToFirst())
                {
                    FabricCodelList.clear();
                    FabricPricelList.clear();

                    do {
                        FabricCodelList.add(c.getString(c.getColumnIndex("FabricCode")));
                        FabricPricelList.add(c.getString(c.getColumnIndex("FabricPrice")));
                        Log.i("FabricCode:", c.getString(c.getColumnIndex("FabricCode")));
                        Log.i("FabricPrice:", c.getString(c.getColumnIndex("FabricPrice")));

                    } while (c.moveToNext());
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            selectFabricList = (ListView) findViewById(R.id.selectFabricList);
            selectFabricList.setNestedScrollingEnabled(true);


            listViewAdapter = new ListViewAdapter(this, FabricCodelList, FabricPricelList);

            selectFabricList.setAdapter(listViewAdapter);

            selectFabricList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                    if(itemClicked == 0)
                    {
                        itemClicked = 1;
                    }

                    Cursor c = database.rawQuery("SELECT * FROM fabrics WHERE FabricType = 'Shirt' ", null);
                    c.moveToFirst();
                    int i = 0;
                    while (c != null && i != position) {
                        i++;
                        c.moveToNext();
                    }

                    Cursor c1 = database.rawQuery("SELECT * FROM orders WHERE OrderId ="+OrderId, null);
                    c1.moveToFirst();

                    double currBill = c1.getDouble(c1.getColumnIndex("BillAmount"));
                    Log.i("currBill:", Double.toString(currBill));

                    int idSelected = c.getInt((c.getColumnIndex("FabricId")));
                    int FabricPrice = c.getInt(c.getColumnIndex("FabricPrice"));

                    updatedBill = currBill + (double)FabricPrice;

                    Log.i("idSelected1:", Integer.toString(idSelected));

                    try {

                        String sql = "UPDATE shirts SET FabricId = '"+idSelected+"' WHERE ShirtId = "+ShirtId;
                        database.execSQL(sql);

                        Log.i("FabricId in slctfbrc: ", Integer.toString(idSelected));
                        Log.i("BillAmnt in slctfbrc: ", Double.toString(updatedBill));

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            listViewAdapter.notifyDataSetChanged();
        }
        if(selected.equals("suit"))
        {
            SuitId = intent.getLongExtra("SuitId1", -1);
            Log.i("SuitId in SlctFbrc: ", Long.toString(SuitId));

            try {
                Cursor c = database.rawQuery("SELECT * FROM fabrics WHERE FabricType = 'Suit' ", null);
                if(c.moveToFirst())
                {
                    FabricCodelList.clear();
                    FabricPricelList.clear();

                    do {

                        FabricCodelList.add(c.getString(c.getColumnIndex("FabricCode")));
                        FabricPricelList.add(c.getString(c.getColumnIndex("FabricPrice")));
                        Log.i("FabricCode:", c.getString(c.getColumnIndex("FabricCode")));
                        Log.i("FabricPrice:", c.getString(c.getColumnIndex("FabricPrice")));

                    } while (c.moveToNext());
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            selectFabricList = (ListView) findViewById(R.id.selectFabricList);
            selectFabricList.setNestedScrollingEnabled(true);

            listViewAdapter = new ListViewAdapter(this, FabricCodelList, FabricPricelList);

            selectFabricList.setAdapter(listViewAdapter);

            selectFabricList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                    if(itemClicked == 0)
                    {
                        itemClicked = 1;
                    }


                    Cursor c = database.rawQuery("SELECT * FROM fabrics WHERE FabricType = 'Suit' ", null);
                    c.moveToFirst();
                    int i = 0;
                    while (c != null && i != position) {
                        i++;
                        c.moveToNext();
                    }

                    Cursor c1 = database.rawQuery("SELECT * FROM orders WHERE OrderId ="+OrderId, null);
                    c1.moveToFirst();

                    double currBill = c1.getDouble(c1.getColumnIndex("BillAmount"));

                    int idSelected = c.getInt((c.getColumnIndex("FabricId")));
                    int FabricPrice = c.getInt(c.getColumnIndex("FabricPrice"));

                    updatedBill = currBill + (double)FabricPrice;

                    Log.i("idSelected1:", Integer.toString(idSelected));

                    Log.i("currBill:", Double.toString(currBill));
                    Log.i("FabricPrice:", Double.toString(FabricPrice));
                    Log.i("updatedBill:", Double.toString(updatedBill));

                    try {

                        String sql = "UPDATE suits SET FabricId = '"+idSelected+"' WHERE SuitId = "+SuitId;
                        database.execSQL(sql);

                        Log.i("FabricId in slctfbrc: ", Integer.toString(idSelected));
                        Log.i("BillAmnt in slctfbrc: ", Double.toString(updatedBill));

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            listViewAdapter.notifyDataSetChanged();
        }
        if(selected.equals("trouser"))
        {
            TrouserId = intent.getLongExtra("TrouserId1", -1);
            Log.i("TrouserId in SlctFbrc: ", Long.toString(TrouserId));

            try {
                Cursor c = database.rawQuery("SELECT * FROM fabrics WHERE FabricType = 'Trouser' ", null);
                if(c.moveToFirst())
                {
                    FabricCodelList.clear();
                    FabricPricelList.clear();

                    do {

                        FabricCodelList.add(c.getString(c.getColumnIndex("FabricCode")));
                        FabricPricelList.add(c.getString(c.getColumnIndex("FabricPrice")));
                        Log.i("FabricCode:", c.getString(c.getColumnIndex("FabricCode")));
                        Log.i("FabricPrice:", c.getString(c.getColumnIndex("FabricPrice")));

                    } while (c.moveToNext());
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            selectFabricList = (ListView) findViewById(R.id.selectFabricList);
            selectFabricList.setNestedScrollingEnabled(true);

            listViewAdapter = new ListViewAdapter(this, FabricCodelList, FabricPricelList);

            selectFabricList.setAdapter(listViewAdapter);

            selectFabricList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                    if(itemClicked == 0)
                    {
                        itemClicked = 1;
                    }


                    Cursor c = database.rawQuery("SELECT * FROM fabrics WHERE FabricType = 'Trouser' ", null);
                    c.moveToFirst();
                    int i = 0;
                    while (c != null && i != position) {
                        i++;
                        c.moveToNext();
                    }

                    Cursor c1 = database.rawQuery("SELECT * FROM orders WHERE OrderId ="+OrderId, null);
                    c1.moveToFirst();

                    double currBill = c1.getDouble(c1.getColumnIndex("BillAmount"));

                    int idSelected = c.getInt((c.getColumnIndex("FabricId")));
                    int FabricPrice = c.getInt(c.getColumnIndex("FabricPrice"));

                    updatedBill = currBill + (double)FabricPrice;

                    Log.i("idSelected1:", Integer.toString(idSelected));

                    Log.i("currBill:", Double.toString(currBill));
                    Log.i("FabricPrice:", Double.toString(FabricPrice));
                    Log.i("updatedBill:", Double.toString(updatedBill));

                    try {

                        String sql = "UPDATE trousers SET FabricId = '"+idSelected+"' WHERE TrouserId = "+TrouserId;
                        database.execSQL(sql);

                        Log.i("FabricId in slctfbrc: ", Integer.toString(idSelected));
                        Log.i("BillAmnt in slctfbrc: ", Double.toString(updatedBill));

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            listViewAdapter.notifyDataSetChanged();

        }

    }

    public void nextActFromMain(View view) {

        if(itemClicked == 0)
        {
            Toast.makeText(SelectFabric0.this,"Please select fabric type!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {

                String sql1 = "UPDATE orders SET BillAmount = '"+updatedBill+"' WHERE OrderId = "+OrderId;
                database.execSQL(sql1);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }


            if(selected.equals("shirt"))
            {
                Intent intent = new Intent(this, Shirt1.class);

                intent.putExtra("OrderId1", OrderId);
                intent.putExtra("source1", source);
                intent.putExtra("CustomerId1", CustomerId);
                intent.putExtra("ShirtId1", ShirtId);

                intent.putExtra("source11", source1);

                startActivity(intent);
            }
            if(selected.equals("suit"))
            {
                Intent intent = new Intent(this, Suit1.class);

                intent.putExtra("OrderId1", OrderId);
                intent.putExtra("source1", source);
                intent.putExtra("CustomerId1", CustomerId);
                intent.putExtra("SuitId1", SuitId);

                intent.putExtra("source11", source1);

                startActivity(intent);
            }
            if(selected.equals("trouser"))
            {
                Intent intent = new Intent(this, Trouser1.class);

                intent.putExtra("OrderId1", OrderId);
                intent.putExtra("source1", source);
                intent.putExtra("CustomerId1", CustomerId);
                intent.putExtra("TrouserId1", TrouserId);

                intent.putExtra("source11", source1);

                startActivity(intent);
            }
        }


    }
}
