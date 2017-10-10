package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AppointmentCustomerDetails extends AppCompatActivity {
    Intent intent;
    int AppointmentId;
    int CustomerId;
    SQLiteDatabase database;
    EditText name;
    EditText phoneNo;
    EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_customer_details);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        AppointmentId = intent.getIntExtra("AppointmentId1", -1);
        CustomerId = intent.getIntExtra("CustomerId1",-1);
        Log.i("AppointmentId in AdAp: ", Integer.toString(AppointmentId));
        Log.i("CustomerId in AdAp: ", Integer.toString(CustomerId));

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        name = (EditText) findViewById(R.id.Name);
        phoneNo = (EditText) findViewById(R.id.PhoneNumber);
        address = (EditText) findViewById(R.id.address);

    }
    public void nextActFromMain(View view) {


        Intent intent = new Intent(this, AddAppointment.class);
        String name1 = name.getText().toString();
        String phoneNo1 = phoneNo.getText().toString();
        String address1 = address.getText().toString();
        try {
            String sql = "UPDATE customers SET Name = '"+name1+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql);

            String sql1 = "UPDATE customers SET Phone = '"+phoneNo1+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql1);

            String sql2 = "UPDATE customers SET Address = '"+address1+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql2);

            Log.i("AppointmentId in AdAp: ", Integer.toString(AppointmentId));
            Log.i("CustomerId in AdAp: ", Integer.toString(CustomerId));

            intent.putExtra("AppointmentId1", AppointmentId);

            Cursor c = database.rawQuery("SELECT * FROM appointments", null);
            c.moveToFirst();
            /*
            while(c!=null)
            {
                Log.i("Result Name: ", c.getString(c.getColumnIndex("Name")));
                c.moveToNext();
            }*/

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        startActivity(intent);

    }
}
