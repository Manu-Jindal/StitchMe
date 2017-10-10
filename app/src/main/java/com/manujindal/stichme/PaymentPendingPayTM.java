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
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

public class PaymentPendingPayTM extends AppCompatActivity {

    Intent intent;
    int OrderId;
    int CustomerId;
    String source;
    long phoneNo;
    int orderNumber;
    int advancePaid;

    SQLiteDatabase database;


    int payment;
    double billamountValue;
    double paymentPending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_pending_pay_tm);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getIntExtra("OrderId1", -1);
        CustomerId = intent.getIntExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        payment = intent.getIntExtra("payment1", -1);
        orderNumber = intent.getIntExtra("orderNumber1", -1);
        phoneNo = intent.getLongExtra("phoneNo1", -1);
        billamountValue = intent.getDoubleExtra("billamountValue1", -1);
        paymentPending = intent.getDoubleExtra("paymentPending1", -1);

        Log.i("OrderId in PayTM: ", Integer.toString(OrderId));
        Log.i("CustomerId in PayTM:", Integer.toString(CustomerId));
        Log.i("Source in PayTM: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        Cursor c1 = database.rawQuery("SELECT * FROM orders WHERE OrderId = "+OrderId, null);
        if(c1.moveToFirst())
        {
            advancePaid  = c1.getInt(c1.getColumnIndex("AdvancePaid"));
        }
    }

    public void paymentConfirmed(View view) {


        double paymentDue = paymentPending - payment;

        double paymentReceivedtotal = advancePaid + payment;

        Intent intent = new Intent(this, DetailedRecord.class);

        Log.i("OrderId in Payment: ", Integer.toString(OrderId));
        Log.i("CustomerId in Payment: ", Integer.toString(CustomerId));
        Log.i("Source in Payment: ", source);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);

        try {
            String sql3 = "UPDATE orders SET AdvancePaid ="+paymentReceivedtotal+" WHERE OrderId = "+OrderId;
            database.execSQL(sql3);

            String sql4 = "UPDATE orders SET PaymentDue ="+paymentDue+" WHERE OrderId = "+OrderId;
            database.execSQL(sql4);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        String sms = "Thanks for placing the order with us. We have recieved an cash of Rs."+payment+" for your order number "+orderNumber+". Let's get you Stitched!\nTeam StitchMe";

        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Long.toString(phoneNo), null, sms, null, null);
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS failed, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void paymentPending(View view) {

        Intent intent = new Intent(this, DetailedRecord.class);

        Log.i("OrderId in Payment: ", Integer.toString(OrderId));
        Log.i("CustomerId in Payment: ", Integer.toString(CustomerId));
        Log.i("Source in Payment: ", source);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);


        String sms = "Thanks for placing the order. Please pay Rs."+payment+" using this link:\nhttps://www.payumoney.com/paybypayumoney/#/395DBE0F4B267D47C7DE64EEC5B0CD9C\nTeam StitchMe";


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
