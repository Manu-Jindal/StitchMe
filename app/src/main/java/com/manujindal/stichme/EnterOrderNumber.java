package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

public class EnterOrderNumber extends AppCompatActivity {

    Intent intent;
    long OrderId;
    String source;
    SQLiteDatabase database;
    EditText ordernumber;
    int PrevOrderNumber;
    TextView PrevOrderNumberText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_order_number);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        PrevOrderNumber = intent.getIntExtra("PrevOrderNumber1", -1);

        Log.i("OrderId in EnterOrder#:", Long.toString(OrderId));

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        ordernumber = (EditText) findViewById(R.id.ordernumber);

        PrevOrderNumberText = (TextView) findViewById(R.id.prevOrderNumber);
        PrevOrderNumberText.setText("Previous Order Number: "+Integer.toString(PrevOrderNumber));
    }

    public void nextAct(View view) {


        Intent intent = new Intent(this, CustomerExists.class);
        int ordernumber1 = (StringUtils.isNotBlank(ordernumber.getText().toString())) ? Integer.parseInt(ordernumber.getText().toString()) : -1;
        try {
            String sql = "UPDATE orders SET OrderNumber = '"+ordernumber1+"' WHERE OrderId = "+OrderId;
            database.execSQL(sql);

            Log.i("OrderId in EnterOrdr#1:", Long.toString(OrderId));
            intent.putExtra("OrderId1", OrderId);

            Cursor c = database.rawQuery("SELECT * FROM orders", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result OrderNumber: ", c.getString(c.getColumnIndex("OrderNumber")));
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

