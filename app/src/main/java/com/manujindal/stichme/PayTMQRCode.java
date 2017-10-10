package com.manujindal.stichme;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PayTMQRCode extends AppCompatActivity {

    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    long phoneNo;
    int orderNumber;

    SQLiteDatabase database;


    int advanceAmount;
    double billamountValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_tmqrcode);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        advanceAmount = intent.getIntExtra("advanceAmount1", -1);
        orderNumber = intent.getIntExtra("orderNumber1", -1);
        phoneNo = intent.getLongExtra("phoneNo1", -1);
        billamountValue = intent.getDoubleExtra("billamountValue1", -1);

        Log.i("OrderId in PayTM: ", Long.toString(OrderId));
        Log.i("CustomerId in PayTM:", Long.toString(CustomerId));
        Log.i("Source in PayTM: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

    }

    public void paymentConfirmed(View view) {

        Intent intent = new Intent(this, DetailedRecord.class);

        Log.i("OrderId in PayTM: ", Long.toString(OrderId));
        Log.i("CustomerId in PayTM: ", Long.toString(CustomerId));
        Log.i("Source in PayTM: ", source);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);

        Double paymentDue = billamountValue - advanceAmount;

        try {

            String sql = "UPDATE orders SET OrderStatus = 'Order Confirmed' WHERE OrderId = "+OrderId;
            database.execSQL(sql);

            if(advanceAmount > 0)
            {
                String sql2 = "UPDATE orders SET AdvanceRecieved ='Yes' WHERE OrderId = "+OrderId;
                database.execSQL(sql2);
            }
            else
            {
                String sql2 = "UPDATE orders SET AdvanceRecieved ='No' WHERE OrderId = "+OrderId;
                database.execSQL(sql2);
            }

            String sql4 = "UPDATE orders SET PaymentDue ="+paymentDue+" WHERE OrderId = "+OrderId;
            database.execSQL(sql4);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        String sms = "Thanks for placing the order with us. We have recieved an PayTM cash of Rs."+advanceAmount+" for your order number "+orderNumber+". Let's get you Stitched!\nTeam StitchMe";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Long.toString(phoneNo), null, sms, null, null);

            Toast.makeText(getApplicationContext(), "Your Order has been Confirmed!", Toast.LENGTH_LONG).show();

            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void paymentPending(View view) {

        Intent intent = new Intent(this, DetailedRecord.class);

        Log.i("OrderId in PayTM: ", Long.toString(OrderId));
        Log.i("CustomerId in PayTM: ", Long.toString(CustomerId));
        Log.i("Source in PayTM: ", source);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);

        String sql = "UPDATE orders SET OrderStatus = 'Order Recieved' WHERE OrderId = "+OrderId;
        database.execSQL(sql);

        String sms = "Thanks for placing the order with us. Please pay Rs."+advanceAmount+" using the PayTM Number: +919818020151 to process your order"+orderNumber+"\nTeam StitchMe";


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
    
    
}
