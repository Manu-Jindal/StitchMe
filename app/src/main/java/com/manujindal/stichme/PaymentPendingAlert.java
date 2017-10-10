package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Text;

public class PaymentPendingAlert extends AppCompatActivity {

    Intent intent;
    int OrderId;
    int CustomerId;
    String source;

    String source1;
    SQLiteDatabase database;

    TextView paymentPending;
    double paymentPendingValue;
    double billAmountValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_pending_alert);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);



        intent = getIntent();
        OrderId = intent.getIntExtra("OrderId1", -1);
        CustomerId = intent.getIntExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        billAmountValue = intent.getDoubleExtra("billAmountValue1", -1);


        Log.i("OrderId in TrialDate: ", Integer.toString(OrderId));
        Log.i("CustomerId in TrialDte:", Integer.toString(CustomerId));
        Log.i("Source in TrialDate: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        paymentPending = (TextView) findViewById(R.id.pendingamount);

        try {
            Cursor c = database.rawQuery("SELECT * FROM orders WHERE OrderId ="+OrderId, null);
            c.moveToFirst();

            Log.i("OrderId in PymntAlrt: ", Integer.toString(OrderId));
            Log.i("CustomerId in PymtAlrt:", Integer.toString(CustomerId));
            Log.i("Source in PymntAlrt: ", source);

            paymentPendingValue = c.getDouble(c.getColumnIndex("PaymentDue"));

            paymentPending.setText("Rs."+Double.toString(paymentPendingValue));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void payNow(View view) {


        Intent intent = new Intent(this, PaymentPendingPay.class);

        Log.i("OrderId in PymntAlrt: ", Integer.toString(OrderId));
        Log.i("CustomerId in PmntAlrt:", Integer.toString(CustomerId));
        Log.i("Source in PymntAlrt: ", source);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);
        intent.putExtra("billamountValue1", billAmountValue);
        intent.putExtra("paymentPending1", paymentPendingValue);

        startActivity(intent);

    }
}
