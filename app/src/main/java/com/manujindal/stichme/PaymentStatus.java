package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PaymentStatus extends AppCompatActivity {


    SQLiteDatabase database;

    ListView orderstatuseslistview;
    ListView ordernumberslistview;
    ArrayAdapter arrayAdapter;
    ListViewAdapterCascadedThree listViewAdapter;
    ArrayList<String> OrderNumberslist = new ArrayList<>();
    ArrayList<String> OrderNameList = new ArrayList<>();
    ArrayList<String> PaymentDueList = new ArrayList<>();
    ArrayList<String> OrderIdList = new ArrayList<>();
    final String[] orderStatuses = {"Order Initiated", "Order Recieved","Order Confirmed","Order Form Ready", "Order With Fabricator(I)",
            "Order Ready For Trial", "Order Trial Done", "Order With Fabricator(II)","Order Ready for Delivery","Order Delivered"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        final int[] recordPresent = new int[10];

        for(int i=0; i<orderStatuses.length; i++)
        {
            String currentStatus = orderStatuses[i];
            Cursor c = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId = customers.CustomerId AND OrderStatus ='"+currentStatus+"' AND PaymentDue > 0", null);
            if(c.moveToFirst())
            {
                Log.i("Order Dtls", orderStatuses[i]+": "+c.getString(c.getColumnIndex("OrderNumber"))+" "+c.getString(c.getColumnIndex("Name")));
                recordPresent[i] = 1;
            }
        }

        for(int i=0; i<orderStatuses.length; i++)
        {
            Log.i(orderStatuses[i], Integer.toString(recordPresent[i]));
        }

        orderstatuseslistview = (ListView) findViewById(R.id.orderstatuseslist);
        ordernumberslistview = (ListView) findViewById(R.id.ordernumberslist);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, orderStatuses){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                if(recordPresent[position] == 1)
                {
                    Log.i("Color changed at:", orderStatuses[position]);
                    // Set a background color for ListView regular row/item
                    view.setBackgroundResource(R.drawable.selecteditemlistbackground);
                }
                else
                {
                    Log.i("Color not changed at:", orderStatuses[position]);
                    view.setBackgroundResource(R.drawable.notselecteditembackground);
                }
                return view;
            }
        };
        orderstatuseslistview.setAdapter(arrayAdapter);

        listViewAdapter = new ListViewAdapterCascadedThree(this, OrderNumberslist, OrderNameList, PaymentDueList);
        ordernumberslistview.setAdapter(listViewAdapter);

        orderstatuseslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentStatus = orderStatuses[position];
                Cursor c = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId = customers.CustomerId AND OrderStatus ='"+currentStatus+"' AND PaymentDue > 0", null);
                OrderNumberslist.clear();
                OrderNameList.clear();
                OrderIdList.clear();
                PaymentDueList.clear();
                if(c.moveToFirst())
                {
                    do {
                        OrderNumberslist.add(c.getString(c.getColumnIndex("OrderNumber")));
                        OrderNameList.add(c.getString(c.getColumnIndex("Name")));
                        PaymentDueList.add("Due: Rs."+c.getString(c.getColumnIndex("PaymentDue")));
                        OrderIdList.add(c.getString(c.getColumnIndex("OrderId")));
                    }while(c.moveToNext());
                }
                listViewAdapter.notifyDataSetChanged();
            }
        });

        ordernumberslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int OrderId = Integer.parseInt(OrderIdList.get(position));
                Intent intent = new Intent(getApplicationContext(), DetailedRecord.class);
                intent.putExtra("OrderId1", OrderId);
                intent.putExtra("source1", "paymentStatus");
                startActivity(intent);

            }
        });


    }

}
