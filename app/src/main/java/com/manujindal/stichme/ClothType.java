package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.commons.lang3.StringUtils;

public class ClothType extends AppCompatActivity {

    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    String source1;
    SQLiteDatabase database;

    Spinner shirtColourType;
    Spinner shirtPatternType;
    Spinner suitColourType;
    Spinner suitPatternType;
    Spinner trouserColourType;
    Spinner trouserPatternType;

    String[] ColourTypes = {"White", "Black", "Grey", "Blue", "Navy Blue", "Yellow", "Pink", "Red", "N.A."};
    String[] PatternTypes = {"Striped", "Check", "Solid", "Dotted", "Printed", "N.A."};
    String shirtColour = "",
            shirtPattern = "",
            suitColour = "",
            suitPattern = "",
            trouserColour = "",
            trouserPattern = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_type);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        source1 = intent.getStringExtra("source11");
        Log.i("OrderId in Name: ", Long.toString(OrderId));
        Log.i("CustomerId in Name: ", Long.toString(CustomerId));
        Log.i("Source in Name: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        shirtColourType = (Spinner) findViewById(R.id.tablerow01);
        shirtPatternType = (Spinner) findViewById(R.id.tablerow02);
        suitColourType = (Spinner) findViewById(R.id.tablerow11);
        suitPatternType = (Spinner) findViewById(R.id.tablerow12);
        trouserColourType = (Spinner) findViewById(R.id.tablerow21);
        trouserPatternType = (Spinner) findViewById(R.id.tablerow22);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ClothType.this,
                android.R.layout.simple_spinner_item,ColourTypes);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ClothType.this,
                android.R.layout.simple_spinner_item,PatternTypes);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shirtColourType.setAdapter(adapter1);
        shirtPatternType.setAdapter(adapter2);
        suitColourType.setAdapter(adapter1);
        suitPatternType.setAdapter(adapter2);
        trouserColourType.setAdapter(adapter1);
        trouserPatternType.setAdapter(adapter2);

        shirtColourType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                shirtColour = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        shirtPatternType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                shirtPattern = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        suitColourType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                suitColour = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        suitPatternType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                suitPattern = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        trouserColourType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                trouserColour = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        trouserPatternType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                trouserPattern = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        try {
            Log.i("Source in DOB: ", source);
            Log.i("Source1 in DOB: ", source1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void nextActFromMain(View view) {


        try {
            Log.i("Source in DOB: ", source);
            Log.i("Source1 in DOB: ", source1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Intent intent;
        if(source.equals("inFlow")) {
            if (source1.equals("newCustomer"))
                intent = new Intent(this, ChooseItem.class);
            else if (source1.equals("inFlowCustomersList"))
                intent = new Intent(this, CustomersList.class);
            else if (source1.equals("editCustomer"))
                intent = new Intent(this, CustomerDetails.class);
            else //redundant
                intent = new Intent(this, MainActivity.class);
        }
        else if(source.equals("customerList")) {
            if (source1.equals("editCustomer"))
                intent = new Intent(this, ChooseItem.class);
            else
                intent = new Intent(this, CustomersList.class);
        }
        else {  //redundant
                intent = new Intent(this, MainActivity.class);
        }

        try {
            String sql = "UPDATE customers SET  ShirtColorPreference= '"+shirtColour+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql);
            String sql1 = "UPDATE customers SET  ShirtPatternPreference= '"+shirtPattern+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql1);


            String sql2 = "UPDATE customers SET  SuitColorPreference= '"+suitColour+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql2);
            String sql3 = "UPDATE customers SET  SuitPatternPreference= '"+suitPattern+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql3);


            String sql4 = "UPDATE customers SET  TrouserColorPreference= '"+trouserColour+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql4);
            String sql5 = "UPDATE customers SET  TrouserPatternPreference= '"+trouserPattern+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql5);

            Log.i("OrderId in ClothType:", Long.toString(OrderId));
            Log.i("CustomerId in ClthType:", Long.toString(CustomerId));
            Log.i("Source in ClothType:", source);
            Log.i("Source1 in ClothType:", source1);

            Log.i("shirtColour ClthType:", shirtColour);
            Log.i("shirtPattern ClthType:", shirtPattern);
            Log.i("suitColour ClthType:", suitColour);
            Log.i("suitPattern ClthType:", suitPattern);
            Log.i("trouserColour ClthType:", trouserColour);
            Log.i("shirtColour ClthType:", shirtColour);

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);

            if(source1.equals("newCustomer"))
                intent.putExtra("source11", "checkout");

            Cursor c = database.rawQuery("SELECT * FROM customers", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result Occupation: ", c.getString(c.getColumnIndex("Occupation")));
                Log.i("Result Preference: ", c.getString(c.getColumnIndex("TypePreference")));
                c.moveToNext();
            }

            String sql6 = "UPDATE orders SET CustomerId = '"+CustomerId+"' WHERE OrderId = "+OrderId;
            database.execSQL(sql);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        startActivity(intent);

        /*
        if(source.equals("inFlow"))
        {

            if(source1.equals("newCustomer"))
            {
                Intent intent = new Intent(this, ChooseItem.class);

                try {
                    String sql = "UPDATE customers SET  ShirtColorPreference= '"+shirtColour+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql);
                    String sql1 = "UPDATE customers SET  ShirtPatternPreference= '"+shirtPattern+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql1);


                    String sql2 = "UPDATE customers SET  SuitColorPreference= '"+suitColour+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql2);
                    String sql3 = "UPDATE customers SET  SuitPatternPreference= '"+suitPattern+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql3);


                    String sql4 = "UPDATE customers SET  TrouserColorPreference= '"+trouserColour+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql4);
                    String sql5 = "UPDATE customers SET  TrouserPatternPreference= '"+trouserPattern+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql5);

                    Log.i("OrderId in ClothType:", Long.toString(OrderId));
                    Log.i("CustomerId in ClthType:", Long.toString(CustomerId));
                    Log.i("Source in ClothType:", source);
                    Log.i("Source1 in ClothType:", source1);

                    Log.i("shirtColour ClthType:", shirtColour);
                    Log.i("shirtPattern ClthType:", shirtPattern);
                    Log.i("suitColour ClthType:", suitColour);
                    Log.i("suitPattern ClthType:", suitPattern);
                    Log.i("trouserColour ClthType:", trouserColour);
                    Log.i("shirtColour ClthType:", shirtColour);

                    intent.putExtra("OrderId1", OrderId);
                    intent.putExtra("source1", source);
                    intent.putExtra("CustomerId1", CustomerId);

                    intent.putExtra("source11", "checkout");

                    Cursor c = database.rawQuery("SELECT * FROM customers", null);
                    c.moveToFirst();
                    while(c!=null)
                    {
                        Log.i("Result Occupation: ", c.getString(c.getColumnIndex("Occupation")));
                        Log.i("Result Preference: ", c.getString(c.getColumnIndex("TypePreference")));
                        c.moveToNext();
                    }

                    String sql6 = "UPDATE orders SET CustomerId = '"+CustomerId+"' WHERE OrderId = "+OrderId;
                    database.execSQL(sql);

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                startActivity(intent);


            }
            if(source1.equals("inFlowCustomersList"))
            {
                Intent intent = new Intent(this, CustomersList.class);

                try {
                    String sql = "UPDATE customers SET  ShirtColorPreference= '"+shirtColour+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql);
                    String sql1 = "UPDATE customers SET  ShirtPatternPreference= '"+shirtPattern+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql1);


                    String sql2 = "UPDATE customers SET  SuitColorPreference= '"+suitColour+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql2);
                    String sql3 = "UPDATE customers SET  SuitPatternPreference= '"+suitPattern+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql3);


                    String sql4 = "UPDATE customers SET  TrouserColorPreference= '"+trouserColour+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql4);
                    String sql5 = "UPDATE customers SET  TrouserPatternPreference= '"+trouserPattern+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql5);

                    Log.i("OrderId in ClothType:", Long.toString(OrderId));
                    Log.i("CustomerId in ClthType:", Long.toString(CustomerId));
                    Log.i("Source in ClothType:", source);
                    Log.i("Source1 in ClothType:", source1);

                    Log.i("shirtColour ClthType:", shirtColour);
                    Log.i("shirtPattern ClthType:", shirtPattern);
                    Log.i("suitColour ClthType:", suitColour);
                    Log.i("suitPattern ClthType:", suitPattern);
                    Log.i("trouserColour ClthType:", trouserColour);
                    Log.i("shirtColour ClthType:", shirtColour);

                    intent.putExtra("OrderId1", OrderId);
                    intent.putExtra("source1", source);
                    intent.putExtra("CustomerId1", CustomerId);

                    Cursor c = database.rawQuery("SELECT * FROM customers", null);
                    c.moveToFirst();
                    while(c!=null)
                    {
                        Log.i("Result Occupation: ", c.getString(c.getColumnIndex("Occupation")));
                        Log.i("Result Preference: ", c.getString(c.getColumnIndex("TypePreference")));
                        c.moveToNext();
                    }

                    String sql6 = "UPDATE orders SET CustomerId = '"+CustomerId+"' WHERE OrderId = "+OrderId;
                    database.execSQL(sql);

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                startActivity(intent);
            }


            if(source1.equals("editCustomer"))
            {
                Intent intent = new Intent(this, CustomerDetails.class);

                try {
                    String sql = "UPDATE customers SET  ShirtColorPreference= '"+shirtColour+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql);
                    String sql1 = "UPDATE customers SET  ShirtPatternPreference= '"+shirtPattern+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql1);

                    String sql2 = "UPDATE customers SET  SuitColorPreference= '"+suitColour+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql2);
                    String sql3 = "UPDATE customers SET  SuitPatternPreference= '"+suitPattern+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql3);

                    String sql4 = "UPDATE customers SET  TrouserColorPreference= '"+trouserColour+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql4);
                    String sql5 = "UPDATE customers SET  TrouserPatternPreference= '"+trouserPattern+"' WHERE CustomerId = "+CustomerId;
                    database.execSQL(sql5);

                    Log.i("OrderId in ClothType:", Long.toString(OrderId));
                    Log.i("CustomerId in ClthType:", Long.toString(CustomerId));
                    Log.i("Source in ClothType:", source);
                    Log.i("Source1 in ClothType:", source1);

                    Log.i("shirtColour ClthType:", shirtColour);
                    Log.i("shirtPattern ClthType:", shirtPattern);
                    Log.i("suitColour ClthType:", suitColour);
                    Log.i("suitPattern ClthType:", suitPattern);
                    Log.i("trouserColour ClthType:", trouserColour);
                    Log.i("shirtColour ClthType:", shirtColour);

                    intent.putExtra("OrderId1", OrderId);
                    intent.putExtra("source1", source);
                    intent.putExtra("CustomerId1", CustomerId);

                    Cursor c = database.rawQuery("SELECT * FROM customers", null);
                    c.moveToFirst();
                    while(c!=null)
                    {
                        Log.i("Result Occupation: ", c.getString(c.getColumnIndex("Occupation")));
                        Log.i("Result Preference: ", c.getString(c.getColumnIndex("TypePreference")));
                        c.moveToNext();
                    }

                    String sql6 = "UPDATE orders SET CustomerId = '"+CustomerId+"' WHERE OrderId = "+OrderId;
                    database.execSQL(sql);

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                startActivity(intent);
            }

        }
        if(source.equals("customerList")) {
            if (source1.equals("editCustomer")) {
                Intent intent = new Intent(this, CustomerDetails.class);

                try {
                    String sql = "UPDATE customers SET  ShirtColorPreference= '" + shirtColour + "' WHERE CustomerId = " + CustomerId;
                    database.execSQL(sql);
                    String sql1 = "UPDATE customers SET  ShirtPatternPreference= '" + shirtPattern + "' WHERE CustomerId = " + CustomerId;
                    database.execSQL(sql1);

                    String sql2 = "UPDATE customers SET  SuitColorPreference= '" + suitColour + "' WHERE CustomerId = " + CustomerId;
                    database.execSQL(sql2);
                    String sql3 = "UPDATE customers SET  SuitPatternPreference= '" + suitPattern + "' WHERE CustomerId = " + CustomerId;
                    database.execSQL(sql3);

                    String sql4 = "UPDATE customers SET  TrouserColorPreference= '" + trouserColour + "' WHERE CustomerId = " + CustomerId;
                    database.execSQL(sql4);
                    String sql5 = "UPDATE customers SET  TrouserPatternPreference= '" + trouserPattern + "' WHERE CustomerId = " + CustomerId;
                    database.execSQL(sql5);

                    Log.i("OrderId in ClothType:", Long.toString(OrderId));
                    Log.i("CustomerId in ClthType:", Long.toString(CustomerId));
                    Log.i("Source in ClothType:", source);
                    Log.i("Source1 in ClothType:", source1);

                    Log.i("shirtColour ClthType:", shirtColour);
                    Log.i("shirtPattern ClthType:", shirtPattern);
                    Log.i("suitColour ClthType:", suitColour);
                    Log.i("suitPattern ClthType:", suitPattern);
                    Log.i("trouserColour ClthType:", trouserColour);
                    Log.i("shirtColour ClthType:", shirtColour);

                    intent.putExtra("OrderId1", OrderId);
                    intent.putExtra("source1", source);
                    intent.putExtra("CustomerId1", CustomerId);

                    Cursor c = database.rawQuery("SELECT * FROM customers", null);
                    c.moveToFirst();
                    while (c != null) {
                        Log.i("Result Occupation: ", c.getString(c.getColumnIndex("Occupation")));
                        Log.i("Result Preference: ", c.getString(c.getColumnIndex("TypePreference")));
                        c.moveToNext();
                    }

                    String sql6 = "UPDATE orders SET CustomerId = '" + CustomerId + "' WHERE OrderId = " + OrderId;
                    database.execSQL(sql);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                startActivity(intent);

            }
            else
                {
                Intent intent = new Intent(this, CustomersList.class);
                intent.putExtra("source1", source);

                try {
                    String sql = "UPDATE customers SET  ShirtColorPreference= '" + shirtColour + "' WHERE CustomerId = " + CustomerId;
                    database.execSQL(sql);
                    String sql1 = "UPDATE customers SET  ShirtPatternPreference= '" + shirtPattern + "' WHERE CustomerId = " + CustomerId;
                    database.execSQL(sql1);


                    String sql2 = "UPDATE customers SET  SuitColorPreference= '" + suitColour + "' WHERE CustomerId = " + CustomerId;
                    database.execSQL(sql2);
                    String sql3 = "UPDATE customers SET  SuitPatternPreference= '" + suitPattern + "' WHERE CustomerId = " + CustomerId;
                    database.execSQL(sql3);


                    String sql4 = "UPDATE customers SET  TrouserColorPreference= '" + trouserColour + "' WHERE CustomerId = " + CustomerId;
                    database.execSQL(sql4);
                    String sql5 = "UPDATE customers SET  TrouserPatternPreference= '" + trouserPattern + "' WHERE CustomerId = " + CustomerId;
                    database.execSQL(sql5);

                    Log.i("OrderId in ClothType:", Long.toString(OrderId));
                    Log.i("CustomerId in ClthType:", Long.toString(CustomerId));
                    Log.i("Source in ClothType:", source);
                    Log.i("Source1 in ClothType:", source1);

                    Log.i("shirtColour ClthType:", shirtColour);
                    Log.i("shirtPattern ClthType:", shirtPattern);
                    Log.i("suitColour ClthType:", suitColour);
                    Log.i("suitPattern ClthType:", suitPattern);
                    Log.i("trouserColour ClthType:", trouserColour);
                    Log.i("shirtColour ClthType:", shirtColour);

                    intent.putExtra("OrderId1", OrderId);
                    intent.putExtra("source1", source);
                    intent.putExtra("CustomerId1", CustomerId);

                    Cursor c = database.rawQuery("SELECT * FROM customers", null);
                    c.moveToFirst();
                    while (c != null) {
                        Log.i("Result Occupation: ", c.getString(c.getColumnIndex("Occupation")));
                        Log.i("Result Preference: ", c.getString(c.getColumnIndex("TypePreference")));
                        c.moveToNext();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        }
        */
    }
}
