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

public class BillAmount extends AppCompatActivity {

    Intent intent;
    long OrderId;
    long CustomerId;
    String source;

    String source1;
    SQLiteDatabase database;

    TextView billamount;
    double billamountValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_amount);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");

        Log.i("OrderId in TrialDate: ", Long.toString(OrderId));
        Log.i("CustomerId in TrialDte:", Long.toString(CustomerId));
        Log.i("Source in TrialDate: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        billamount = (TextView) findViewById(R.id.billamount);

        try {
            Cursor c = database.rawQuery("SELECT * FROM orders WHERE OrderId ="+OrderId, null);
            c.moveToFirst();

            Log.i("OrderId in Email: ", Long.toString(OrderId));
            Log.i("CustomerId in Email: ", Long.toString(CustomerId));
            Log.i("Source in Email: ", source);

            billamountValue = c.getDouble(c.getColumnIndex("FinalBillAmount"));

            billamount.setText("Rs."+Double.toString(billamountValue));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void payNow(View view) {


        Intent intent = new Intent(this, Payment.class);

        Log.i("OrderId in BillAmt: ", Long.toString(OrderId));
        Log.i("CustomerId in BillAmt: ", Long.toString(CustomerId));
        Log.i("Source in BillAmt: ", source);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);
        intent.putExtra("billamountValue1", billamountValue);


        try {
            String sql1 = "UPDATE orders SET OrderStatus = 'Order Recieved' WHERE OrderId = "+OrderId;
            database.execSQL(sql1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        startActivity(intent);
    }
}
