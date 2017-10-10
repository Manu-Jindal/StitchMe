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


public class EnterEmail extends AppCompatActivity {

    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    String source1;
    SQLiteDatabase database;

    EditText email;
    EditText phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_email);

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

        email = (EditText) findViewById(R.id.Email);
        phone = (EditText) findViewById(R.id.phoneNumber);

        if(source1.equals("editCustomer"))
        {
            try
            {
                Cursor c = database.rawQuery("SELECT * FROM customers WHERE CustomerId ="+CustomerId, null);
                if(c.moveToFirst())
                {
                    email.setText(c.getString(c.getColumnIndex("Email")));
                    phone.setText(c.getString(c.getColumnIndex("Phone")));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            Cursor c0 = database.rawQuery("SELECT * from customers WHERE CustomerId = "+CustomerId, null);
            if(c0.moveToFirst())
            {
                if(!c0.getString(c0.getColumnIndex("Email")).equals(""))
                {
                    Intent intent1 = new Intent(this, EnterAddress.class);

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


        try {
            Log.i("Source in DOB: ", source);
            Log.i("Source1 in DOB: ", source1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, EnterAddress.class);
        String email1 = email.getText().toString();
        Long phone1 = (StringUtils.isNotBlank(phone.getText().toString())) ? Long.parseLong(phone.getText().toString()) : 0;

        try {
            String sql = "UPDATE customers SET Email = '"+email1+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql);
            String sql1 = "UPDATE customers SET Phone = '"+phone1+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql1);

            Log.i("OrderId in Email: ", Long.toString(OrderId));
            Log.i("CustomerId in Email: ", Long.toString(CustomerId));
            Log.i("Source in Email: ", source);

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("source11", source1);
            intent.putExtra("CustomerId1", CustomerId);

            Cursor c = database.rawQuery("SELECT * FROM customers", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result Email: ", c.getString(c.getColumnIndex("Email")));
                Log.i("Result Phone: ", c.getString(c.getColumnIndex("Phone")));
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
