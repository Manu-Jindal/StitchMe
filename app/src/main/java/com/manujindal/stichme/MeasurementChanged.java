package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MeasurementChanged extends AppCompatActivity {
    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    long MeasurementsId;
    
    /*
    int MeasurementStatus;
    0-None selected
    1-Only Upper Garment Selected
    2-Only Lower Garment Selected
    3-Both Upper and Lower Garment Selected
     */

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_changed);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        intent = getIntent();

        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        MeasurementsId = intent.getLongExtra("MeasurementsId1", -1);

        Log.i("OrderId in MsmtCtgrs: ", Long.toString(OrderId));
        Log.i("CustomerId in MsmtCtgr:", Long.toString(CustomerId));
        Log.i("MsrmtsId in MsmtCtgrs: ", Long.toString(MeasurementsId));
        Log.i("Source in MsmtCtgrs: ", source);
        
        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

    }

    public void measurementChanged(View view) {

        Intent intent = new Intent(this, MeasurementCategories.class);

        Log.i("OrderId in MsrmntChng: ", Long.toString(OrderId));
        Log.i("CustomerId in MsmtChng:", Long.toString(CustomerId));
        Log.i("MsrmntsId in MsmtChng: ", Long.toString(MeasurementsId));
        Log.i("Source in MsrmntChng: ", source);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);
        intent.putExtra("MeasurementsId1", MeasurementsId);

        intent.putExtra("MeasurementStatus1", "0");

        startActivity(intent);
    }
    public void measurementNotChanged(View view) {

        Intent intent = new Intent(this, AddItem.class);

        Log.i("OrderId in MsmtCtgrs: ", Long.toString(OrderId));
        Log.i("CustomerId in MsmtCtgr:", Long.toString(CustomerId));
        Log.i("MsrmtsId in MsmtCtgrs: ", Long.toString(MeasurementsId));
        Log.i("Source in MsmtCtgrs: ", source);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);

        intent.putExtra("source11", "cart");

        startActivity(intent);
    }

}
