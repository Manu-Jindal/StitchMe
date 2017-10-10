package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import org.apache.commons.lang3.StringUtils;


public class EnterAddress extends AppCompatActivity {

    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    String source1;
    SQLiteDatabase database;

    EditText address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_address);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        source1 = intent.getStringExtra("source11");

        Log.i("OrderId in Email: ", Long.toString(OrderId));
        Log.i("CustomerId in Email: ", Long.toString(CustomerId));
        Log.i("Source in Email: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        address = (EditText) findViewById(R.id.Address);

        try
        {
            Cursor c = database.rawQuery("SELECT * FROM customers WHERE CustomerId ="+CustomerId, null);
            if(c.moveToFirst())
            {
                address.setText(c.getString(c.getColumnIndex("Address")));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(source1.equals("editCustomer"))
        {
            Cursor c0 = database.rawQuery("SELECT * from customers WHERE CustomerId = "+CustomerId, null);
            if(c0.moveToFirst())
            {
                if(!c0.getString(c0.getColumnIndex("Address")).equals(""))
                {
                    Intent intent1 = new Intent(this, KYC2.class);

                    intent1.putExtra("OrderId1", OrderId);
                    intent1.putExtra("source1", source);
                    intent1.putExtra("source11", source1);
                    intent1.putExtra("CustomerId1", CustomerId);

                    startActivity(intent1);
                }
            }
        }

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


        try
        {
            Log.i("Source in DOB: ", source);
            Log.i("Source1 in DOB: ", source1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, KYC2.class);
        String address1 = address.getText().toString();

        try {
            String sql = "UPDATE customers SET Address = '"+address1+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql);

            Log.i("OrderId in Address: ", Long.toString(OrderId));
            Log.i("CustomerId in Address: ", Long.toString(CustomerId));
            Log.i("Source in Address: ", source);

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("source11", source1);
            intent.putExtra("CustomerId1", CustomerId);

            Cursor c = database.rawQuery("SELECT * FROM customers", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result Address: ", c.getString(c.getColumnIndex("Address")));
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
