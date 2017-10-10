package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

public class CustomerExists extends AppCompatActivity {
    Intent intent;
    long OrderId;
    long CustomerId;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_exists);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        Log.i("OrderId in CstmrExsts: ", Long.toString(OrderId));
        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);
    }
    public void newCustomer(View view) {
        Intent intent = new Intent(this, EnterName.class);
        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", "inFlow");
        intent.putExtra("source11", "newCustomer");

        try{

            long time= System.currentTimeMillis();

            database.execSQL("INSERT INTO customers(name) values('test1234');");
            Cursor c = database.rawQuery("SELECT MIN(CustomerId) from customers", null);
            int customerIdindex = c.getColumnIndex("MIN(CustomerId)");
            c.moveToFirst();
            CustomerId = c.getLong(customerIdindex);


            String sql2 = "UPDATE customers SET CustomerId = "+time+" WHERE CustomerId = "+CustomerId;
            database.execSQL(sql2);

            CustomerId = time;

            String sql = "UPDATE orders SET CustomerId = '"+CustomerId+"' WHERE OrderId = "+OrderId;
            database.execSQL(sql);

            Log.i("CstmrId from CstmrExsts", Long.toString(CustomerId));
            intent.putExtra("CustomerId1", CustomerId);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        startActivity(intent);
    }
    public void selectCustomer(View view) {
        Intent intent = new Intent(this, CustomersList.class);
        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", "inFlow");
        startActivity(intent);
    }

}



