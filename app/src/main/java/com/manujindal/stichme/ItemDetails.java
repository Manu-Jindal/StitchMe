package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemDetails extends AppCompatActivity {

    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    String idSelected;
    String itemTypeSelected;
    String itemSelected;
    String source1;
    SQLiteDatabase database;


    ListView itemDetailsList;
    ListViewAdapter listViewAdapter;
    ArrayList<String> TypeOfItemTypeList = new ArrayList<>();
    ArrayList<String> ItemOutputList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");


        idSelected = intent.getStringExtra("idSelected1");
        itemTypeSelected = intent.getStringExtra("itemTypeSelected1");
        itemSelected = intent.getStringExtra("itemSelected1");
        source1 = intent.getStringExtra("source11");

        TextView itemTypeLabel = (TextView) findViewById(R.id.itemType);
        itemTypeLabel.setText(itemSelected);

        Log.i("OrderId in ItmDtls: ", Long.toString(OrderId));
        Log.i("CustomerId in ItmDtls: ", Long.toString(CustomerId));
        Log.i("Source in ItmDtls: ", source);


        Log.i("idSelected ItmDtls:", idSelected);
        Log.i("itemTypeSlctd ItmDtls:", itemTypeSelected);
        Log.i("source1 ItmDtls:", source1);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        if(itemTypeSelected.equals("shirt"))
        {
            TypeOfItemTypeList.add("Fabric Code");
            TypeOfItemTypeList.add("Fabric Price");
            TypeOfItemTypeList.add("Sleeve Type");
            TypeOfItemTypeList.add("Pocket Type");
            TypeOfItemTypeList.add("Cuff Type");
            TypeOfItemTypeList.add("Fit Type");
            TypeOfItemTypeList.add("Collar Type");
            TypeOfItemTypeList.add("Placket Type");

            try {
                Cursor c = database.rawQuery("SELECT * FROM shirts WHERE ShirtId = "+idSelected, null);
                if(c.moveToFirst())
                {

                    Cursor c1 = database.rawQuery("SELECT * FROM shirts, fabrics WHERE shirts.FabricId = fabrics.FabricId AND ShirtId = "+idSelected, null);
                    if(c1.moveToFirst()){
                        ItemOutputList.add(c1.getString(c1.getColumnIndex("FabricCode")));
                        Log.i("ColumnAccessed Shirt: ", Integer.toString(0));
                        ItemOutputList.add(c1.getString(c1.getColumnIndex("FabricPrice")));
                        Log.i("ColumnAccessed Shirt: ", Double.toString(0.5));
                    }

                    ItemOutputList.add(c.getString(c.getColumnIndex("Shirt_SleeveType")));
                    Log.i("ColumnAccessed Shirt: ", Integer.toString(3));
                    ItemOutputList.add(c.getString(c.getColumnIndex("Shirt_PocketType")));
                    Log.i("ColumnAccessed Shirt: ", Integer.toString(4));
                    ItemOutputList.add(c.getString(c.getColumnIndex("Shirt_CuffType")));
                    Log.i("ColumnAccessed Shirt: ", Integer.toString(5));
                    ItemOutputList.add(c.getString(c.getColumnIndex("Shirt_FitType")));
                    Log.i("ColumnAccessed Shirt: ", Integer.toString(6));
                    ItemOutputList.add(c.getString(c.getColumnIndex("Shirt_CollarType")));
                    Log.i("ColumnAccessed Shirt: ", Integer.toString(7));
                    ItemOutputList.add(c.getString(c.getColumnIndex("Shirt_PlacketType")));
                    Log.i("ColumnAccessed Shirt: ", Integer.toString(8));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(itemTypeSelected.equals("suit"))
        {

            try {
                Cursor c = database.rawQuery("SELECT * FROM suits WHERE SuitId = "+idSelected, null);
                if(c.moveToFirst())
                {
                    Cursor c1 = database.rawQuery("SELECT * FROM suits, fabrics WHERE suits.FabricId = fabrics.FabricId AND SuitId = "+idSelected, null);
                    if(c1.moveToFirst()){
                        TypeOfItemTypeList.add("Fabric Code");
                        ItemOutputList.add(c1.getString(c1.getColumnIndex("FabricCode")));
                        Log.i("ColumnAccessed Suit: ", Integer.toString(0));
                        TypeOfItemTypeList.add("Fabric Price");
                        ItemOutputList.add(c1.getString(c1.getColumnIndex("FabricPrice")));
                        Log.i("ColumnAccessed Suit: ", Double.toString(0.5));
                    }

                    TypeOfItemTypeList.add("Fit Type");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Suit_FitType")));
                    TypeOfItemTypeList.add("Jacket Type");
                    Log.i("ColumnAccessed Suit: ", Integer.toString(3));
                    ItemOutputList.add(c.getString(c.getColumnIndex("Suit_JacketType")));
                    Log.i("ColumnAccessed Suit: ", Integer.toString(4));
                    TypeOfItemTypeList.add("Lapel Type");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Suit_LapelType")));
                    Log.i("ColumnAccessed Suit: ", Integer.toString(5));
                    TypeOfItemTypeList.add("Bottom Cut Type");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Suit_BottomCutType")));
                    Log.i("ColumnAccessed Suit: ", Integer.toString(6));
                    TypeOfItemTypeList.add("Vest Type");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Suit_VestType")));
                    Log.i("ColumnAccessed Suit: ", Integer.toString(7));
                    if(c.getString(c.getColumnIndex("Suit_VestType")).equals("Yes"))
                    {
                        TypeOfItemTypeList.add("Vest Style");
                        ItemOutputList.add(c.getString(c.getColumnIndex("Suit_TypeOfVest")));
                        Log.i("ColumnAccessed Suit: ", Double.toString(7.5));

                        TypeOfItemTypeList.add("Vest Price");
                        ItemOutputList.add(c.getString(c.getColumnIndex("Suit_TypeOfVestPrice")));
                        Log.i("ColumnAccessed Suit: ", Double.toString(7.75));
                    }
                    TypeOfItemTypeList.add("Pocket Type");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Suit_PocketType")));
                    Log.i("ColumnAccessed Suit: ", Integer.toString(8));
                    TypeOfItemTypeList.add("Sleeve Button Type");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Suit_SleeveButtonType")));
                    Log.i("ColumnAccessed Suit: ", Integer.toString(9));
                    TypeOfItemTypeList.add("Vent Type");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Suit_VentType")));
                    Log.i("ColumnAccessed Suit: ", Integer.toString(10));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(itemTypeSelected.equals("trouser"))
        {
            TypeOfItemTypeList.add("Fabric Code");
            TypeOfItemTypeList.add("Fabric Price");
            TypeOfItemTypeList.add("Fit Type");
            TypeOfItemTypeList.add("Pant Pleats Type");
            TypeOfItemTypeList.add("Pant Pocket Type");
            TypeOfItemTypeList.add("Back Pocket Style Type");
            TypeOfItemTypeList.add("Back Pocket Type");
            TypeOfItemTypeList.add("Belt Loops Type");

            try {
                Cursor c = database.rawQuery("SELECT * FROM trousers WHERE TrouserId = "+idSelected, null);
                if(c.moveToFirst())
                {
                    Cursor c1 = database.rawQuery("SELECT * FROM trousers, fabrics WHERE trousers.FabricId = fabrics.FabricId AND TrouserId = "+idSelected, null);
                    if(c1.moveToFirst()){
                        ItemOutputList.add(c1.getString(c1.getColumnIndex("FabricCode")));
                        Log.i("ColumnAccessed trouser:", Integer.toString(0));
                        ItemOutputList.add(c1.getString(c1.getColumnIndex("FabricPrice")));
                        Log.i("ColumnAccessed trouser:", Double.toString(0.5));
                    }
                    ItemOutputList.add(c.getString(c.getColumnIndex("Trouser_FitType")));
                    Log.i("ColumnAccessed Trsr: ", Integer.toString(3));
                    ItemOutputList.add(c.getString(c.getColumnIndex("Trouser_PantPleatsType")));
                    Log.i("ColumnAccessed Trsr: ", Integer.toString(4));
                    ItemOutputList.add(c.getString(c.getColumnIndex("Trouser_PantPocketType")));
                    Log.i("ColumnAccessed Trsr: ", Integer.toString(5));
                    ItemOutputList.add(c.getString(c.getColumnIndex("Trouser_BackPocketStyleType")));
                    Log.i("ColumnAccessed Trsr: ", Integer.toString(6));
                    ItemOutputList.add(c.getString(c.getColumnIndex("Trouser_BackPocketType")));
                    Log.i("ColumnAccessed Trsr: ", Integer.toString(7));
                    ItemOutputList.add(c.getString(c.getColumnIndex("Trouser_BeltLoopsType")));
                    Log.i("ColumnAccessed Trsr: ", Integer.toString(8));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(itemTypeSelected.equals("other"))
        {
            TypeOfItemTypeList.add("Type");
            TypeOfItemTypeList.add("Fabric Price");
            TypeOfItemTypeList.add("Description");

            try {
                Cursor c = database.rawQuery("SELECT * FROM others WHERE OtherId = "+idSelected, null);
                if(c.moveToFirst())
                {
                    Cursor c1 = database.rawQuery("SELECT * FROM others, fabrics WHERE others.FabricId = fabrics.FabricId AND OtherId = "+idSelected, null);
                    if(c1.moveToFirst()){
                        ItemOutputList.add(c1.getString(c1.getColumnIndex("FabricCode")));
                        Log.i("ColumnAccessed other:", Integer.toString(0));
                        ItemOutputList.add(c1.getString(c1.getColumnIndex("FabricPrice")));
                        Log.i("ColumnAccessed other:", Double.toString(0.5));
                    }
                    ItemOutputList.add(c.getString(c.getColumnIndex("Description")));
                    Log.i("ColumnAccessed other: ", Integer.toString(1));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        itemDetailsList = (ListView) findViewById(R.id.itemDetailsListId);
        listViewAdapter = new ListViewAdapter(this, TypeOfItemTypeList, ItemOutputList);


        itemDetailsList.setAdapter(listViewAdapter);

        itemDetailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
               // Toast.makeText(MainActivity.this,"Description => "+month[position]+"=> n Title"+number[position], Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void goBack(View view) {

        Intent intent = new Intent(this, AddItem.class);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);

        intent.putExtra("source11", source1);

        Log.i("source1 in ItmDtls: ", source1);

        Log.i("OrderId in ItmDtls: ", Long.toString(OrderId));
        Log.i("CustomerId in ItmDtls: ", Long.toString(CustomerId));
        Log.i("Source in ItmDtls: ", source);

        startActivity(intent);
    }
}
