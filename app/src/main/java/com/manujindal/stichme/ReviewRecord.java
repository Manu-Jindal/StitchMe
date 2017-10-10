package com.manujindal.stichme;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReviewRecord extends AppCompatActivity {
    Intent intent;
    long OrderId;
    long CustomerId;
    String source;

    String source1;
    SQLiteDatabase database;

    ListView itemDetailsList;
    ListViewAdapter listViewAdapter;
    ArrayList<String> ItemLabelList = new ArrayList<String>();
    ArrayList<String> ItemOutputList = new ArrayList<String>();
    ArrayList<String> tableNameList = new ArrayList<String>();
    ArrayList<String> columnNameList = new ArrayList<String>();
    ArrayList<String> ItemIdTypeList = new ArrayList<String>();
    ArrayList<String> ItemIdList = new ArrayList<String>();

    public void addToList(String ItemLabel, String ItemOutput, String tableName, String columnName, String ItemIdType, String ItemId){
        ItemLabelList.add(ItemLabel);
        ItemOutputList.add(ItemOutput);
        tableNameList.add(tableName);
        columnNameList.add(columnName);
        ItemIdTypeList.add(ItemIdType);
        ItemIdList.add(ItemId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_record);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        
        Log.i("OrderId in RvwRcrd: ", Long.toString(OrderId));
        Log.i("CustomerId in RvwRcrd: ", Long.toString(CustomerId));
        Log.i("Source in RvwRcrd: ", source);
        
        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);


        //populate the arrays

        try {
            Cursor c = database.rawQuery("SELECT * FROM customers WHERE CustomerId = "+CustomerId, null);
            if(c.moveToFirst())
            {
                {
                    addToList("Name", c.getString(c.getColumnIndex("Name")), "customers", "Name","CustomerId" , Long.toString(CustomerId));
                    Log.i("Name:", c.getString(c.getColumnIndex("Name")));

                    addToList("DOB", c.getString(c.getColumnIndex("Dob")), "customers", "Dob","CustomerId" , Long.toString(CustomerId));
                    Log.i("DOB:", c.getString(c.getColumnIndex("Dob")));

                    addToList("Email", c.getString(c.getColumnIndex("Email")), "customers", "Email","CustomerId" , Long.toString(CustomerId));
                    Log.i("Email:", c.getString(c.getColumnIndex("Email")));

                    addToList("Phone", c.getString(c.getColumnIndex("Phone")), "customers", "Phone","CustomerId" , Long.toString(CustomerId));
                    Log.i("Phone:", c.getString(c.getColumnIndex("Phone")));

                    addToList("Address", c.getString(c.getColumnIndex("Address")), "customers", "Address","CustomerId" , Long.toString(CustomerId));
                    Log.i("Address:", c.getString(c.getColumnIndex("Address")));

                    addToList("Occupation", c.getString(c.getColumnIndex("Occupation")), "customers", "Occupation","CustomerId" , Long.toString(CustomerId));
                    Log.i("Occupation:", c.getString(c.getColumnIndex("Occupation")));

                    addToList("Wear Preference", c.getString(c.getColumnIndex("TypePreference")), "customers", "TypePreference","CustomerId" , Long.toString(CustomerId));
                    Log.i("Wear Preference:", c.getString(c.getColumnIndex("TypePreference")));

                    addToList("Shirt Color\nPreference", c.getString(c.getColumnIndex("ShirtColorPreference")), "customers", "ShirtColorPreference","CustomerId" ,
                            Long.toString(CustomerId));
                    Log.i("ShirtColorPreference:", c.getString(c.getColumnIndex("ShirtColorPreference")));

                    addToList("Shirt Pattern\nPreference", c.getString(c.getColumnIndex("ShirtPatternPreference")), "customers", "ShirtPatternPreference","CustomerId" ,
                            Long.toString(CustomerId));
                    Log.i("ShirtPatternPreference:", c.getString(c.getColumnIndex("ShirtPatternPreference")));

                    addToList("Suit Color\nPreference", c.getString(c.getColumnIndex("SuitColorPreference")), "customers", "SuitColorPreference","CustomerId" , 
                            Long.toString(CustomerId));
                    Log.i("SuitColorPreference:", c.getString(c.getColumnIndex("SuitColorPreference")));

                    addToList("Suit Pattern\nPreference", c.getString(c.getColumnIndex("SuitPatternPreference")), "customers", "SuitPatternPreference","CustomerId" ,
                            Long.toString(CustomerId));
                    Log.i("SuitPatternPreference:", c.getString(c.getColumnIndex("SuitPatternPreference")));

                    addToList("Trouser Color\nPreference", c.getString(c.getColumnIndex("TrouserColorPreference")), "customers", "TrouserColorPreference","CustomerId" ,
                            Long.toString(CustomerId));
                    Log.i("TrouserColorPreference:", c.getString(c.getColumnIndex("TrouserColorPreference")));

                    addToList("Trouser Pattern\nPreference", c.getString(c.getColumnIndex("TrouserPatternPreference")), "customers", "TrouserPatternPreference","CustomerId" ,
                            Long.toString(CustomerId));
                    Log.i("TrouserPatternPref:", c.getString(c.getColumnIndex("TrouserPatternPreference")));
                }
            }

            Cursor c1 = database.rawQuery("SELECT * FROM shirts where OrderId ="+OrderId, null);
            Cursor cA1 = database.rawQuery("SELECT * FROM shirts, fabrics WHERE shirts.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c1.moveToFirst() && cA1.moveToFirst())
            {
                int i=1;
                do {
                    long ShirtId = c1.getLong(c1.getColumnIndex("ShirtId"));
                    
                    addToList("\nShirt"+i+"\n", "", "", "","" ,"");
                    i++;

                    addToList("Fabric Code", cA1.getString(cA1.getColumnIndex("FabricCode")), "", "","" , "");
                    Log.i("Shirt Fabric Code:", cA1.getString(cA1.getColumnIndex("FabricCode")));

                    addToList("Fabric Price", cA1.getString(cA1.getColumnIndex("FabricPrice")), "", "","" , "");
                    Log.i("Shirt Fabric Price:", cA1.getString(cA1.getColumnIndex("FabricPrice")));

                    addToList("Sleeve Type", c1.getString(c1.getColumnIndex("Shirt_SleeveType")), "shirts", "Shirt_SleeveType","ShirtId" , Long.toString(ShirtId));
                    Log.i("Shirt_SleeveType:", c1.getString(c1.getColumnIndex("Shirt_SleeveType")));
                   
                    addToList("Pocket Type", c1.getString(c1.getColumnIndex("Shirt_PocketType")), "shirts", "Shirt_PocketType","ShirtId" , Long.toString(ShirtId));
                    Log.i("Shirt_PocketType:", c1.getString(c1.getColumnIndex("Shirt_PocketType")));
                    
                    addToList("Cuff Type", c1.getString(c1.getColumnIndex("Shirt_CuffType")), "shirts", "Shirt_CuffType","ShirtId" , Long.toString(ShirtId));
                    Log.i("Shirt_CuffType:", c1.getString(c1.getColumnIndex("Shirt_CuffType")));

                    addToList("Fit Type", c1.getString(c1.getColumnIndex("Shirt_FitType")), "shirts", "Shirt_FitType","ShirtId" , Long.toString(ShirtId));
                    Log.i("Shirt_FitType:", c1.getString(c1.getColumnIndex("Shirt_FitType")));

                    addToList("Collar Type", c1.getString(c1.getColumnIndex("Shirt_CollarType")), "shirts", "Shirt_CollarType","ShirtId" , Long.toString(ShirtId));
                    Log.i("Shirt_CollarType:", c1.getString(c1.getColumnIndex("Shirt_CollarType")));

                    addToList("Placket Type", c1.getString(c1.getColumnIndex("Shirt_PlacketType")), "shirts", "Shirt_PlacketType","ShirtId" , Long.toString(ShirtId));
                    Log.i("Shirt_PlacketType:", c1.getString(c1.getColumnIndex("Shirt_PlacketType")));

                } while (c1.moveToNext() && cA1.moveToNext());
            }

            Cursor c2 = database.rawQuery("SELECT * FROM suits WHERE OrderId = "+OrderId, null);
            Cursor cA2 = database.rawQuery("SELECT * FROM suits, fabrics WHERE suits.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c2.moveToFirst() && cA2.moveToFirst())
            {
                int i=1;
                do {

                    long SuitId = c2.getLong(c2.getColumnIndex("SuitId"));
                    
                    addToList("\nSuit"+i+"\n", "", "", "","" ,"");
                    i++;
                    
                    addToList("Fabric Code", cA2.getString(cA2.getColumnIndex("FabricCode")), "", "","" , "");
                    Log.i("Suit Fabric Code:", cA2.getString(cA2.getColumnIndex("FabricCode")));

                    addToList("Fabric Price", cA2.getString(cA2.getColumnIndex("FabricPrice")), "", "","" , "");
                    Log.i("Suit Fabric Price:", cA2.getString(cA2.getColumnIndex("FabricPrice")));

                    addToList("Fit Type",           c2.getString(c2.getColumnIndex("Suit_FitType")),          "suits", "Suit_FitType",          "SuitId" , Long.toString(SuitId));
                    addToList("Jacket Type",        c2.getString(c2.getColumnIndex("Suit_JacketType")),       "suits", "Suit_JacketType",       "SuitId" , Long.toString(SuitId));
                    addToList("Lapel Type",         c2.getString(c2.getColumnIndex("Suit_LapelType")),        "suits", "Suit_LapelType",        "SuitId" , Long.toString(SuitId));
                    addToList("Bottom Cut Type",    c2.getString(c2.getColumnIndex("Suit_BottomCutType")),    "suits", "Suit_BottomCutType",    "SuitId" , Long.toString(SuitId));
                    addToList("Vest Type",          c2.getString(c2.getColumnIndex("Suit_VestType")),         "suits", "Suit_VestType",         "SuitId" , Long.toString(SuitId));
                    addToList("Pocket Type",        c2.getString(c2.getColumnIndex("Suit_PocketType")),       "suits", "Suit_PocketType",       "SuitId" , Long.toString(SuitId));
                    addToList("Sleeve Button Type", c2.getString(c2.getColumnIndex("Suit_SleeveButtonType")), "suits", "Suit_SleeveButtonType", "SuitId" , Long.toString(SuitId));
                    addToList("Vent Type",          c2.getString(c2.getColumnIndex("Suit_VentType")),         "suits", "Suit_VentType",         "SuitId" , Long.toString(SuitId));
                    
                    if(c2.getString(c2.getColumnIndex("Suit_VestType")).equals("Yes")) {
                        
                        addToList("Type Of Vest Type", c2.getString(c2.getColumnIndex("Suit_TypeOfVest")), "suits", "Suit_TypeOfVest","SuitId" , Long.toString(SuitId));
                    }
                } while (c2.moveToNext() && cA2.moveToNext());
            }

            Cursor c3 = database.rawQuery("SELECT * FROM trousers WHERE OrderId = "+OrderId, null);
            Cursor cA3 = database.rawQuery("SELECT * FROM trousers, fabrics WHERE trousers.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c3.moveToFirst() && cA3.moveToFirst())
            {
                int i=1;
                do {

                    long TrouserId = c3.getLong(c3.getColumnIndex("TrouserId"));
                    
                    addToList("\nTrouser"+i+"\n", "", "", "","" ,"");
                    i++;
                    
                    addToList("Fabric Code", cA3.getString(cA3.getColumnIndex("FabricCode")), "", "","" , "");
                    Log.i("trouser Fabric Code:", cA3.getString(cA3.getColumnIndex("FabricCode")));

                    addToList("Fabric Price", cA3.getString(cA3.getColumnIndex("FabricPrice")), "", "","" , "");
                    Log.i("trouser Fabric Price:", cA3.getString(cA3.getColumnIndex("FabricPrice")));

                    addToList("Fit Type",          c3.getString(c3.getColumnIndex("Trouser_FitType")),             "trousers", "Trouser_FitType",              "TrouserId" , Long.toString(TrouserId));
                    addToList("Pant Pleats Type",  c3.getString(c3.getColumnIndex("Trouser_PantPleatsType")),      "trousers", "Trouser_PantPleatsType",       "TrouserId" , Long.toString(TrouserId));
                    addToList("Pant Pocket Type",  c3.getString(c3.getColumnIndex("Trouser_PantPocketType")),      "trousers", "Trouser_PantPocketType",       "TrouserId" , Long.toString(TrouserId));
                    addToList("Back Pocket Style", c3.getString(c3.getColumnIndex("Trouser_BackPocketStyleType")), "trousers", "Trouser_BackPocketStyleType",  "TrouserId" , Long.toString(TrouserId));
                    addToList("Back Pocket Type",  c3.getString(c3.getColumnIndex("Trouser_BackPocketType")),      "trousers", "Trouser_BackPocketType",       "TrouserId" , Long.toString(TrouserId));
                    addToList("Belt Loops Type",   c3.getString(c3.getColumnIndex("Trouser_BeltLoopsType")),       "trousers", "Trouser_BeltLoopsType",        "TrouserId" , Long.toString(TrouserId));

                } while (c3.moveToNext() && cA3.moveToNext());
            }


            Cursor c5 = database.rawQuery("SELECT * FROM others WHERE OrderId = "+OrderId, null);
            Cursor cA5 = database.rawQuery("SELECT * FROM others, fabrics WHERE others.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c5.moveToFirst() && cA5.moveToFirst())
            {
                int i=1;
                do {

                    long OtherId = c5.getLong(c5.getColumnIndex("OtherId"));

                    addToList("\nOther"+i+"\n", "", "", "","" ,"");
                    i++;

                    addToList("Type", cA5.getString(cA5.getColumnIndex("FabricCode")), "", "","" , "");
                    Log.i("Other Fabric Code:", cA5.getString(cA5.getColumnIndex("FabricCode")));

                    addToList("Fabric Price", cA5.getString(cA5.getColumnIndex("FabricPrice")), "", "","" , "");
                    Log.i("trouser Fabric Price:", cA5.getString(cA5.getColumnIndex("FabricPrice")));

                    addToList("Description", c5.getString(c5.getColumnIndex("Description")), "others", "Description", "OtherId" , Long.toString(OtherId));

                } while (c5.moveToNext() && cA5.moveToNext());
            }

            Cursor c4 = database.rawQuery("SELECT * FROM measurements WHERE CustomerId= "+CustomerId, null);
            if(c4.moveToFirst())
            {
                long MeasurementsId = c4.getLong(c4.getColumnIndex("MeasurementsId"));

                addToList("\nMeasurement\nUpper Garment\n" , "", "", "", "" , "");
                addToList("Upper Chest" ,        c4.getString(c4.getColumnIndex("Measurement1")),  "measurements", "Measurement1",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Chest" ,        c4.getString(c4.getColumnIndex("Measurement2")),  "measurements", "Measurement2",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Lower Chest" ,        c4.getString(c4.getColumnIndex("Measurement3")),  "measurements", "Measurement3",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Length Suit" ,        c4.getString(c4.getColumnIndex("Measurement4")),  "measurements", "Measurement4",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Length Shirt",        c4.getString(c4.getColumnIndex("Measurement5")),  "measurements", "Measurement5",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Stomach" ,            c4.getString(c4.getColumnIndex("Measurement6")),  "measurements", "Measurement6",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Hip" ,          c4.getString(c4.getColumnIndex("Measurement7")),  "measurements", "Measurement7",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Across Front",        c4.getString(c4.getColumnIndex("Measurement8")),  "measurements", "Measurement8",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Across Back" ,        c4.getString(c4.getColumnIndex("Measurement9")),  "measurements", "Measurement9",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Across Shoulder" ,    c4.getString(c4.getColumnIndex("Measurement10")), "measurements", "Measurement10", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Side Shoulder" ,      c4.getString(c4.getColumnIndex("Measurement11")), "measurements", "Measurement11", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Neck (Collar)", c4.getString(c4.getColumnIndex("Measurement12")), "measurements", "Measurement12", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Lapel" ,              c4.getString(c4.getColumnIndex("Measurement13")), "measurements", "Measurement13", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Sleeve Length" ,      c4.getString(c4.getColumnIndex("Measurement14")), "measurements", "Measurement14", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Armhole",             c4.getString(c4.getColumnIndex("Measurement15")), "measurements", "Measurement15", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Biceps" ,             c4.getString(c4.getColumnIndex("Measurement16")), "measurements", "Measurement16", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Elbow" ,              c4.getString(c4.getColumnIndex("Measurement17")), "measurements", "Measurement17", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Sleeve Bottom",       c4.getString(c4.getColumnIndex("Measurement18")), "measurements", "Measurement18", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Cuff Length" ,        c4.getString(c4.getColumnIndex("Measurement19")), "measurements", "Measurement19", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Cuff Width" ,         c4.getString(c4.getColumnIndex("Measurement20")), "measurements", "Measurement20", "MeasurementsId" , Long.toString(MeasurementsId));

                addToList("\nMeasurement\nLower Garment\n" , "", "", "", "" , "");
                addToList("Length" ,       c4.getString(c4.getColumnIndex("Measurement21")), "measurements", "Measurement21", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Waist",   c4.getString(c4.getColumnIndex("Measurement22")), "measurements", "Measurement22", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Hip" ,    c4.getString(c4.getColumnIndex("Measurement23")), "measurements", "Measurement23", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Thigh" ,  c4.getString(c4.getColumnIndex("Measurement24")), "measurements", "Measurement24", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Knee",    c4.getString(c4.getColumnIndex("Measurement25")), "measurements", "Measurement25", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Calf" ,         c4.getString(c4.getColumnIndex("Measurement26")), "measurements", "Measurement26", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Crotch" ,       c4.getString(c4.getColumnIndex("Measurement27")), "measurements", "Measurement27", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Fog",           c4.getString(c4.getColumnIndex("Measurement28")), "measurements", "Measurement28", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("In-Seam" ,      c4.getString(c4.getColumnIndex("Measurement29")), "measurements", "Measurement29", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Bottom (Mori)", c4.getString(c4.getColumnIndex("Measurement30")), "measurements", "Measurement30", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Belt Width" ,   c4.getString(c4.getColumnIndex("Measurement31")), "measurements", "Measurement31", "MeasurementsId" , Long.toString(MeasurementsId));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
        /*
        try {
            Cursor c = database.rawQuery("SELECT * FROM customers WHERE CustomerId = "+CustomerId, null);
            if(c.moveToFirst())
            {
                {

                    ItemLabelList.add("Name");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Name")));
                    Log.i("Name:", c.getString(c.getColumnIndex("Name")));
                    tableNameList.add("customers");
                    columnNameList.add("Name");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));

                    ItemLabelList.add("DOB");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Dob")));
                    Log.i("DOB:", c.getString(c.getColumnIndex("Dob")));
                    tableNameList.add("customers");
                    columnNameList.add("Dob");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));

                    ItemLabelList.add("Email");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Email")));
                    Log.i("Email:", c.getString(c.getColumnIndex("Email")));
                    tableNameList.add("customers");
                    columnNameList.add("Email");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));


                    ItemLabelList.add("Phone");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Phone")));
                    Log.i("Phone:", c.getString(c.getColumnIndex("Phone")));
                    tableNameList.add("customers");
                    columnNameList.add("Phone");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));

                    ItemLabelList.add("Occupation");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Occupation")));
                    Log.i("Occupation:", c.getString(c.getColumnIndex("Occupation")));
                    tableNameList.add("customers");
                    columnNameList.add("Occupation");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));

                    ItemLabelList.add("Wear Preference");
                    ItemOutputList.add(c.getString(c.getColumnIndex("TypePreference")));
                    Log.i("Wear Preference:", c.getString(c.getColumnIndex("TypePreference")));
                    tableNameList.add("customers");
                    columnNameList.add("TypePreference");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));

                    ItemLabelList.add("Shirt Color\nPreference");
                    ItemOutputList.add(c.getString(c.getColumnIndex("ShirtColorPreference")));
                    Log.i("ShirtColorPreference:", c.getString(c.getColumnIndex("ShirtColorPreference")));
                    tableNameList.add("customers");
                    columnNameList.add("ShirtColorPreference");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));

                    ItemLabelList.add("Shirt Pattern\nPreference");
                    ItemOutputList.add(c.getString(c.getColumnIndex("ShirtPatternPreference")));
                    Log.i("ShirtPatternPreference:", c.getString(c.getColumnIndex("ShirtPatternPreference")));
                    tableNameList.add("customers");
                    columnNameList.add("ShirtPatternPreference");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));

                    ItemLabelList.add("Suit Color\nPreference");
                    ItemOutputList.add(c.getString(c.getColumnIndex("SuitColorPreference")));
                    Log.i("SuitColorPreference:", c.getString(c.getColumnIndex("SuitColorPreference")));
                    tableNameList.add("customers");
                    columnNameList.add("SuitColorPreference");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));

                    ItemLabelList.add("Suit Pattern\nPreference");
                    ItemOutputList.add(c.getString(c.getColumnIndex("SuitPatternPreference")));
                    Log.i("SuitPatternPreference:", c.getString(c.getColumnIndex("SuitPatternPreference")));
                    tableNameList.add("customers");
                    columnNameList.add("SuitPatternPreference");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));

                    ItemLabelList.add("Trouser Color\nPreference");
                    ItemOutputList.add(c.getString(c.getColumnIndex("TrouserColorPreference")));
                    Log.i("TrouserColorPreference:", c.getString(c.getColumnIndex("TrouserColorPreference")));
                    tableNameList.add("customers");
                    columnNameList.add("TrouserColorPreference");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));

                    ItemLabelList.add("Trouser Pattern\nPreference");
                    ItemOutputList.add(c.getString(c.getColumnIndex("TrouserPatternPreference")));
                    Log.i("TrouserPatternPref:", c.getString(c.getColumnIndex("TrouserPatternPreference")));
                    tableNameList.add("customers");
                    columnNameList.add("TrouserPatternPreference");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));
                }
            }

            Cursor c1 = database.rawQuery("SELECT * FROM shirts where OrderId ="+OrderId, null);
            Cursor cA1 = database.rawQuery("SELECT * FROM shirts, fabrics WHERE shirts.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c1.moveToFirst() && cA1.moveToFirst())
            {
                int i=1;
                do {
                    int ShirtId = c1.getInt(c1.getColumnIndex("ShirtId"));
                    ItemLabelList.add("\nShirt"+i+"\n");
                    ItemOutputList.add("");
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    i++;


                    ItemLabelList.add("Fabric Code");
                    ItemOutputList.add(cA1.getString(cA1.getColumnIndex("FabricCode")));
                    Log.i("Shirt Fabric Code:", cA1.getString(cA1.getColumnIndex("FabricCode")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");

                    ItemLabelList.add("Fabric Price");
                    ItemOutputList.add(cA1.getString(cA1.getColumnIndex("FabricPrice")));
                    Log.i("Shirt Fabric Price:", cA1.getString(cA1.getColumnIndex("FabricPrice")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");

                    ItemLabelList.add("Sleeve Type");
                    ItemOutputList.add(c1.getString(c1.getColumnIndex("Shirt_SleeveType")));
                    Log.i("Shirt_SleeveType:", c1.getString(c1.getColumnIndex("Shirt_SleeveType")));
                    tableNameList.add("shirts");
                    columnNameList.add("Shirt_SleeveType");
                    ItemIdTypeList.add("ShirtId");
                    ItemIdList.add(Integer.toString(ShirtId));

                    ItemLabelList.add("Pocket Type");
                    ItemOutputList.add(c1.getString(c1.getColumnIndex("Shirt_PocketType")));
                    Log.i("Shirt_PocketType:", c1.getString(c1.getColumnIndex("Shirt_PocketType")));
                    tableNameList.add("shirts");
                    columnNameList.add("Shirt_PocketType");
                    ItemIdTypeList.add("ShirtId");
                    ItemIdList.add(Integer.toString(ShirtId));

                    ItemLabelList.add("Cuff Type");
                    ItemOutputList.add(c1.getString(c1.getColumnIndex("Shirt_CuffType")));
                    Log.i("Shirt_CuffType:", c1.getString(c1.getColumnIndex("Shirt_CuffType")));
                    tableNameList.add("shirts");
                    columnNameList.add("Shirt_CuffType");
                    ItemIdTypeList.add("ShirtId");
                    ItemIdList.add(Integer.toString(ShirtId));

                    ItemLabelList.add("Fit Type");
                    ItemOutputList.add(c1.getString(c1.getColumnIndex("Shirt_FitType")));
                    Log.i("Shirt_FitType:", c1.getString(c1.getColumnIndex("Shirt_FitType")));
                    tableNameList.add("shirts");
                    columnNameList.add("Shirt_FitType");
                    ItemIdTypeList.add("ShirtId");
                    ItemIdList.add(Integer.toString(ShirtId));

                    ItemLabelList.add("Collar Type");
                    ItemOutputList.add(c1.getString(c1.getColumnIndex("Shirt_CollarType")));
                    Log.i("Shirt_CollarType:", c1.getString(c1.getColumnIndex("Shirt_CollarType")));
                    tableNameList.add("shirts");
                    columnNameList.add("Shirt_CollarType");
                    ItemIdTypeList.add("ShirtId");
                    ItemIdList.add(Integer.toString(ShirtId));


                    ItemLabelList.add("Placket Type");
                    ItemOutputList.add(c1.getString(c1.getColumnIndex("Shirt_PlacketType")));
                    Log.i("Shirt_PlacketType:", c1.getString(c1.getColumnIndex("Shirt_PlacketType")));
                    tableNameList.add("shirts");
                    columnNameList.add("Shirt_PlacketType");
                    ItemIdTypeList.add("ShirtId");
                    ItemIdList.add(Integer.toString(ShirtId));

                } while (c1.moveToNext() && cA1.moveToNext());
            }

            Cursor c2 = database.rawQuery("SELECT * FROM suits WHERE OrderId = "+OrderId, null);
            Cursor cA2 = database.rawQuery("SELECT * FROM suits, fabrics WHERE suits.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c2.moveToFirst() && cA2.moveToFirst())
            {
                int i=1;
                do {

                    int SuitId = c2.getInt(c2.getColumnIndex("SuitId"));
                    ItemLabelList.add("\nSuit"+i+"\n");
                    ItemOutputList.add("");
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    i++;

                    ItemLabelList.add("Fabric Code");
                    ItemOutputList.add(cA2.getString(cA2.getColumnIndex("FabricCode")));
                    Log.i("Suit Fabric Code:", cA2.getString(cA2.getColumnIndex("FabricCode")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");

                    ItemLabelList.add("Fabric Price");
                    ItemOutputList.add(cA2.getString(cA2.getColumnIndex("FabricPrice")));
                    Log.i("Suit Fabric Price:", cA2.getString(cA2.getColumnIndex("FabricPrice")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");

                    ItemLabelList.add("Fit Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_FitType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_FitType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));

                    ItemLabelList.add("Jacket Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_JacketType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_JacketType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));

                    ItemLabelList.add("Lapel Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_LapelType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_LapelType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));

                    ItemLabelList.add("Bottom Cut Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_BottomCutType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_BottomCutType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));

                    ItemLabelList.add("Vest Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_VestType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_VestType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));

                    ItemLabelList.add("Pocket Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_PocketType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_PocketType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));

                    ItemLabelList.add("Sleeve Button Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_SleeveButtonType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_SleeveButtonType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));

                    ItemLabelList.add("Vent Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_VentType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_VentType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));

                    if(c2.getString(c2.getColumnIndex("Suit_VestType")).equals("Yes")) {
                        ItemLabelList.add("Type Of Vest Type");
                        ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_TypeOfVest")));
                        tableNameList.add("suits");
                        columnNameList.add("Suit_TypeOfVest");
                        ItemIdTypeList.add("SuitId");
                        ItemIdList.add(Integer.toString(SuitId));
                    }
                } while (c2.moveToNext() && cA2.moveToNext());
            }

            Cursor c3 = database.rawQuery("SELECT * FROM trousers WHERE OrderId = "+OrderId, null);
            Cursor cA3 = database.rawQuery("SELECT * FROM trousers, fabrics WHERE trousers.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c3.moveToFirst() && cA3.moveToFirst())
            {
                int i=1;
                do {

                    int TrouserId = c3.getInt(c3.getColumnIndex("TrouserId"));
                    ItemLabelList.add("\nTrouser"+i+"\n");
                    ItemOutputList.add("");
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    i++;

                    ItemLabelList.add("Fabric Code");
                    ItemOutputList.add(cA3.getString(cA3.getColumnIndex("FabricCode")));
                    Log.i("trouser Fabric Code:", cA3.getString(cA3.getColumnIndex("FabricCode")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");

                    ItemLabelList.add("Fabric Price");
                    ItemOutputList.add(cA3.getString(cA3.getColumnIndex("FabricPrice")));
                    Log.i("trouser Fabric Price:", cA3.getString(cA3.getColumnIndex("FabricPrice")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");

                    ItemLabelList.add("Fit Type");
                    ItemOutputList.add(c3.getString(c3.getColumnIndex("Trouser_FitType")));
                    tableNameList.add("trousers");
                    columnNameList.add("Trouser_FitType");
                    ItemIdTypeList.add("TrouserId");
                    ItemIdList.add(Integer.toString(TrouserId));

                    ItemLabelList.add("Pant Pleats Type");
                    ItemOutputList.add(c3.getString(c3.getColumnIndex("Trouser_PantPleatsType")));
                    tableNameList.add("trousers");
                    columnNameList.add("Trouser_PantPleatsType");
                    ItemIdTypeList.add("TrouserId");
                    ItemIdList.add(Integer.toString(TrouserId));

                    ItemLabelList.add("Pant Pocket Type");
                    ItemOutputList.add(c3.getString(c3.getColumnIndex("Trouser_PantPocketType")));
                    tableNameList.add("trousers");
                    columnNameList.add("Trouser_PantPocketType");
                    ItemIdTypeList.add("TrouserId");
                    ItemIdList.add(Integer.toString(TrouserId));

                    ItemLabelList.add("Back Pocket Style");
                    ItemOutputList.add(c3.getString(c3.getColumnIndex("Trouser_BackPocketStyleType")));
                    tableNameList.add("trousers");
                    columnNameList.add("Trouser_BackPocketStyleType");
                    ItemIdTypeList.add("TrouserId");
                    ItemIdList.add(Integer.toString(TrouserId));

                    ItemLabelList.add("Back Pocket Type");
                    ItemOutputList.add(c3.getString(c3.getColumnIndex("Trouser_BackPocketType")));
                    tableNameList.add("trousers");
                    columnNameList.add("Trouser_BackPocketType");
                    ItemIdTypeList.add("TrouserId");
                    ItemIdList.add(Integer.toString(TrouserId));

                    ItemLabelList.add("Belt Loops Type");
                    ItemOutputList.add(c3.getString(c3.getColumnIndex("Trouser_BeltLoopsType")));
                    tableNameList.add("trousers");
                    columnNameList.add("Trouser_BeltLoopsType");
                    ItemIdTypeList.add("TrouserId");
                    ItemIdList.add(Integer.toString(TrouserId));

                } while (c3.moveToNext() && cA3.moveToNext());
            }


            Cursor c5 = database.rawQuery("SELECT * FROM others WHERE OrderId = "+OrderId, null);
            Cursor cA5 = database.rawQuery("SELECT * FROM others, fabrics WHERE others.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c5.moveToFirst() && cA5.moveToFirst())
            {
                int i=1;
                do {

                    int OtherId = c5.getInt(c5.getColumnIndex("OtherId"));
                    ItemLabelList.add("\nOther"+i+"\n");
                    ItemOutputList.add("");
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    i++;

                    ItemLabelList.add("Type");
                    ItemOutputList.add(cA5.getString(cA5.getColumnIndex("FabricCode")));
                    Log.i("Other Fabric Code:", cA5.getString(cA5.getColumnIndex("FabricCode")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");

                    ItemLabelList.add("Fabric Price");
                    ItemOutputList.add(cA5.getString(cA5.getColumnIndex("FabricPrice")));
                    Log.i("Other Fabric Price:", cA5.getString(cA5.getColumnIndex("FabricPrice")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");

                    ItemLabelList.add("Description");
                    ItemOutputList.add(c5.getString(c5.getColumnIndex("Description")));
                    tableNameList.add("others");
                    columnNameList.add("Description");
                    ItemIdTypeList.add("OtherId");
                    ItemIdList.add(Integer.toString(OtherId));

                } while (c5.moveToNext() && cA5.moveToNext());
            }

            Cursor c4 = database.rawQuery("SELECT * FROM measurements WHERE CustomerId= "+CustomerId, null);
            if(c4.moveToFirst())
            {
                int MeasurementsId = c4.getInt(c4.getColumnIndex("MeasurementsId"));
                ItemLabelList.add("\nMeasurement\nUpper Garment\n");
                ItemOutputList.add("");
                tableNameList.add("");
                columnNameList.add("");
                ItemIdTypeList.add("");
                ItemIdList.add("");

                ItemLabelList.add("Upper Chest");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement1")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement1");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Round Chest");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement2")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement2");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Lower Chest");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement3")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement3");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Length Suit");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement4")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement4");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Length Shirt");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement5")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement5");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Stomach");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement6")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement6");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Round Hip");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement7")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement7");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Across Front");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement8")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement8");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Across Back");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement9")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement9");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Across Shoulder");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement10")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement10");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Side Shoulder");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement11")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement11");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Round Neck (Collar)");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement12")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement12");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Lapel");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement13")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement13");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Sleeve Length");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement14")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement14");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Armhole");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement15")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement15");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Biceps");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement16")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement16");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Elbow");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement17")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement17");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Sleeve Bottom");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement18")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement18");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Cuff Length");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement19")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement19");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Cuff Width");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement20")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement20");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("\nMeasurement\nLower Garment\n");
                ItemOutputList.add("");
                tableNameList.add("");
                columnNameList.add("");
                ItemIdTypeList.add("");
                ItemIdList.add("");

                ItemLabelList.add("Length");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement21")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement21");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Round Waist");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement22")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement22");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Round Hip");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement23")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement23");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Round Thigh");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement24")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement24");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Round Knee");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement25")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement25");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Calf");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement26")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement26");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Crotch");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement27")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement27");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Fog");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement28")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement28");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("In-Seam");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement29")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement29");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Bottom (Mori)");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement30")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement30");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));

                ItemLabelList.add("Belt Width");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement31")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement31");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }*/


        itemDetailsList = (ListView) findViewById(R.id.reviewRecordList);
        itemDetailsList.setNestedScrollingEnabled(true);

        listViewAdapter = new ListViewAdapter(this, ItemLabelList, ItemOutputList);


        itemDetailsList.setAdapter(listViewAdapter);

        itemDetailsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                if(ItemOutputList.get(position) != "")
                {
                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(getApplicationContext());
                    View promptsView = li.inflate(R.layout.popup_input, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReviewRecord.this,  R.style.MyDialogTheme);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);

                    final TextView header = (TextView) promptsView
                            .findViewById(R.id.popupdialogtitle);


                    userInput.setText(ItemOutputList.get(position));
                    header.setText(ItemLabelList.get(position));

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // get user input and set it to result
                                            // edit text

                                            try
                                            {
                                                database.execSQL("UPDATE "+tableNameList.get(position)+" SET "+columnNameList.get(position)+" = '"+userInput.getText().toString()+"' WHERE "+
                                                        ItemIdTypeList.get(position)+" = "+ItemIdList.get(position));
                                                ItemOutputList.set(position, userInput.getText().toString());
                                                Toast.makeText(ReviewRecord.this,ItemLabelList.get(position)+" changed to "+userInput.getText().toString(), Toast.LENGTH_SHORT).show();
                                                listViewAdapter.notifyDataSetChanged();
                                            }
                                            catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }

                // Toast.makeText(MainActivity.this,"Description => "+month[position]+"=> n Title"+number[position], Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        listViewAdapter.notifyDataSetChanged();

    }
    
    
    public void confirmDetails(View view) {



        Intent intent = new Intent(this, EnterTrialDate.class);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);

        Log.i("OrderId in AddItem: ", Long.toString(OrderId));
        Log.i("CustomerId in AddItem: ", Long.toString(CustomerId));
        Log.i("Source in AddItem: ", source);

        startActivity(intent);
        
    }
}
