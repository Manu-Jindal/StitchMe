package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

public class Payment extends AppCompatActivity {

    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    long phoneNo;
    int orderNumber;

    SQLiteDatabase database;

    EditText advancePayment;

    int advanceAmount;
    double billamountValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        billamountValue = intent.getDoubleExtra("billamountValue1", -1);

        Log.i("OrderId in Payment: ", Long.toString(OrderId));
        Log.i("CustomerId in Payment:", Long.toString(CustomerId));
        Log.i("Source in Payment: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        advancePayment = (EditText) findViewById(R.id.advancePaymentTextField);

        Cursor c = database.rawQuery("SELECT * FROM customers WHERE CustomerId = "+CustomerId, null);
        if(c.moveToFirst())
        {
            phoneNo = c.getLong(c.getColumnIndex("Phone"));
        }
        Cursor c1 = database.rawQuery("SELECT * FROM orders WHERE OrderId = "+OrderId, null);
        if(c1.moveToFirst())
        {
            orderNumber = c1.getInt(c1.getColumnIndex("OrderNumber"));
        }

    }
    public void cashPaid(View view) {

        int advanceAmount = (StringUtils.isNotBlank(advancePayment.getText().toString()) ? Integer.parseInt(advancePayment.getText().toString()) : 0);

        Double paymentDue = billamountValue - advanceAmount;

        Intent intent = new Intent(this, DetailedRecord.class);

        Log.i("OrderId in Payment: ", Long.toString(OrderId));
        Log.i("CustomerId in Payment: ", Long.toString(CustomerId));
        Log.i("Source in Payment: ", source);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);

        try {

            String sql = "UPDATE orders SET OrderStatus = 'Order Confirmed' WHERE OrderId = "+OrderId;
            database.execSQL(sql);

            String sql1 = "UPDATE orders SET AdvancePaid ="+advanceAmount+" WHERE OrderId = "+OrderId;
            database.execSQL(sql1);

            if(advanceAmount <= 0)
            {
                String sql2 = "UPDATE orders SET AdvanceRecieved ='No' WHERE OrderId = "+OrderId;
                database.execSQL(sql2);
            }
            else
            {
                String sql2 = "UPDATE orders SET AdvanceRecieved ='Yes' WHERE OrderId = "+OrderId;
                database.execSQL(sql2);
            }

            String sql3 = "UPDATE orders SET AdvancePaymentMethod = 'Cash' WHERE OrderId = "+OrderId;
            database.execSQL(sql3);

            String sql4 = "UPDATE orders SET PaymentDue ="+paymentDue+" WHERE OrderId = "+OrderId;
            database.execSQL(sql4);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        String sms = "Thanks for placing the order with us. We have recieved an cash of Rs."+advanceAmount+" for your order number "+orderNumber+". Let's get you Stitched!\nTeam StitchMe";

        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Long.toString(phoneNo), null, sms, null, null);
            Toast.makeText(getApplicationContext(), "Your Order has been Confirmed!", Toast.LENGTH_LONG).show();
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS failed, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void onlinePaid(View view) {

        int advanceAmount = (StringUtils.isNotBlank(advancePayment.getText().toString()) ? Integer.parseInt(advancePayment.getText().toString()) : 0);

        Intent intent = new Intent(this, DetailedRecord.class);

        Log.i("OrderId in Payment: ", Long.toString(OrderId));
        Log.i("CustomerId in Payment: ", Long.toString(CustomerId));
        Log.i("Source in Payment: ", source);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);

        try {

            String sql = "UPDATE orders SET OrderStatus = 'Order Recieved' WHERE OrderId = "+OrderId;
            database.execSQL(sql);

            String sql1 = "UPDATE orders SET AdvancePaid ="+advanceAmount+" WHERE OrderId = "+OrderId;
            database.execSQL(sql1);

            String sql2 = "UPDATE orders SET AdvanceRecieved ='No' WHERE OrderId = "+OrderId;
            database.execSQL(sql2);

            String sql3 = "UPDATE orders SET AdvancePaymentMethod = 'Online' WHERE OrderId = "+OrderId;
            database.execSQL(sql3);

            String sql4 = "UPDATE orders SET PaymentDue ="+billamountValue+" WHERE OrderId = "+OrderId;
            database.execSQL(sql4);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        String sms = "Thanks for placing the order. Please pay Rs."+advanceAmount+" using this link:\nhttps://www.payumoney.com/paybypayumoney/#/395DBE0F4B267D47C7DE64EEC5B0CD9C\nTeam StitchMe";


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Long.toString(phoneNo), null, sms, null, null);
            Toast.makeText(getApplicationContext(), "Your Order has been Recieved! Please pay the advance at the earliest", Toast.LENGTH_LONG).show();
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS failed, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void walletPaid(View view) {

        int advanceAmount = (StringUtils.isNotBlank(advancePayment.getText().toString()) ? Integer.parseInt(advancePayment.getText().toString()) : 0);

        Intent intent = new Intent(this, PayTMQRCode.class);

        Log.i("OrderId in Payment: ", Long.toString(OrderId));
        Log.i("CustomerId in Payment: ", Long.toString(CustomerId));
        Log.i("Source in Payment: ", source);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);
        intent.putExtra("advanceAmount1", advanceAmount);
        intent.putExtra("orderNumber1", orderNumber);
        intent.putExtra("phoneNo1", phoneNo);
        intent.putExtra("billamountValue1", billamountValue);

        try {

            String sql = "UPDATE orders SET OrderStatus = 'Order Recieved' WHERE OrderId = "+OrderId;
            database.execSQL(sql);

            String sql1 = "UPDATE orders SET AdvancePaid ="+advanceAmount+" WHERE OrderId = "+OrderId;
            database.execSQL(sql1);

            String sql2 = "UPDATE orders SET AdvanceRecieved ='No' WHERE OrderId = "+OrderId;
            database.execSQL(sql2);

            String sql3 = "UPDATE orders SET AdvancePaymentMethod = 'PayTM' WHERE OrderId = "+OrderId;
            database.execSQL(sql3);

            String sql4 = "UPDATE orders SET PaymentDue ="+billamountValue+" WHERE OrderId = "+OrderId;
            database.execSQL(sql4);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        startActivity(intent);
    }


}
