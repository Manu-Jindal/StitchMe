package com.manujindal.stichme;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SendDetailsEmployee extends AppCompatActivity {

    int OrderId;
    SQLiteDatabase database;

    ListView itemDetailsList;
    ArrayAdapter listViewAdapter;
    ArrayList<String> EmployeeNameList = new ArrayList<String>();
    ArrayList<String> EmployeePhoneList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_details_employee);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Intent intent = getIntent();
        OrderId = intent.getIntExtra("OrderId1", -1);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);


        try {
            Cursor c = database.rawQuery("SELECT * FROM employees", null);
            if(c.moveToFirst())
            {
                EmployeeNameList.clear();
                EmployeePhoneList.clear();

                do {
                    EmployeeNameList.add(c.getString(c.getColumnIndex("Name")));
                    EmployeePhoneList.add(c.getString(c.getColumnIndex("Phone")));
                    Log.i("Name:", c.getString(c.getColumnIndex("Name")));
                    Log.i("Phone:", c.getString(c.getColumnIndex("Phone")));

                } while (c.moveToNext());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        itemDetailsList = (ListView) findViewById(R.id.sendEmployeeList);
        itemDetailsList.setNestedScrollingEnabled(true);
        itemDetailsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        String[] EmployeeNameArray = new String[EmployeeNameList.size()];
        EmployeeNameList.toArray(EmployeeNameArray);

        listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, EmployeeNameArray);
        itemDetailsList.setAdapter(listViewAdapter);

        listViewAdapter.notifyDataSetChanged();
    }

    public void sendDetails(View view)
    {
        SparseBooleanArray sparseBooleanArray = itemDetailsList.getCheckedItemPositions();
        for(int i = 0; i < itemDetailsList.getCount(); i++)
        {

            if(sparseBooleanArray.get(i) == true)
            {
                String sms;
                String name = "", phoneNumber = "", orderNo = "", address = "", finalbillamount = "", paymentDue = "";
                try
                {
                    Cursor c = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId = customers.CustomerId AND OrderId = "+OrderId, null);
                    if(c.moveToFirst())
                    {
                        name = c.getString(c.getColumnIndex("Name"));
                        phoneNumber = c.getString(c.getColumnIndex("Phone"));
                        orderNo = c.getString(c.getColumnIndex("OrderNumber"));
                        address = c.getString(c.getColumnIndex("Address"));
                        finalbillamount = c.getString(c.getColumnIndex("FinalBillAmount"));
                        paymentDue = c.getString(c.getColumnIndex("PaymentDue"));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                sms = "Order#:"+orderNo+"\nName:"+name+"\nPhone:"+phoneNumber+"\nAddress:"+address+"\nBillAmt:"+finalbillamount+"\nDue:"+paymentDue;
                String empPhone = EmployeePhoneList.get(i);
                try {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(empPhone, null, sms, null, null);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

        }

    }

    public void allRecords(View view)
    {
        Intent intent = new Intent(this, AllRecords.class);
        startActivity(intent);
    }
}
