package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MeasurementCategories extends AppCompatActivity {
    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    long MeasurementsId;

    Button UpperGarment;
    Button LowerGarment;
    int MeasurementStatus;
    /*
    0-None selected
    1-Only Upper Garment Selected
    2-Only Lower Garment Selected
    3-Both Upper and Lower Garment Selected
     */

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_categories);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        intent = getIntent();

        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        MeasurementsId = intent.getLongExtra("MeasurementsId1", -1);

        MeasurementStatus = intent.getIntExtra("MeasurementStatus1", 0);

        Log.i("OrderId in MsmtCtgrs: ", Long.toString(OrderId));
        Log.i("CustomerId in MsmtCtgr:", Long.toString(CustomerId));
        Log.i("MsrmtsId in MsmtCtgrs: ", Long.toString(MeasurementsId));
        Log.i("Source in MsmtCtgrs: ", source);

        Log.i("MsrmntSts in MsmtCtgrs:", Integer.toString(MeasurementStatus));

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        UpperGarment = (Button) findViewById(R.id.upperGarmentId);
        LowerGarment = (Button) findViewById(R.id.lowerGarmentId);;

        if(MeasurementStatus==0)
        {

        }
        if(MeasurementStatus==1)
        {
            UpperGarment.setBackgroundResource(R.drawable.button6);
            UpperGarment.setEnabled(false);
        }
        if(MeasurementStatus==2)
        {
            UpperGarment.setBackgroundResource(R.drawable.button6);
            LowerGarment.setEnabled(false);
        }
        if(MeasurementStatus>=3)
        {
            UpperGarment.setBackgroundResource(R.drawable.button6);
            UpperGarment.setEnabled(false);
            LowerGarment.setBackgroundResource(R.drawable.button6);
            LowerGarment.setEnabled(false);
        }


    }

    public void upperGarment(View view) {

        Intent intent = new Intent(this, Measurement01.class);


        Log.i("OrderId in MsmtCtgrs: ", Long.toString(OrderId));
        Log.i("CustomerId in MsmtCtgr:", Long.toString(CustomerId));
        Log.i("MsrmtsId in MsmtCtgrs: ", Long.toString(MeasurementsId));
        Log.i("Source in MsmtCtgrs: ", source);

        Log.i("MsrmntSts in MsmtCtgrs:", Integer.toString(MeasurementStatus));

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);
        intent.putExtra("MeasurementsId1", MeasurementsId);

        intent.putExtra("MeasurementStatus1", MeasurementStatus);

        startActivity(intent);
    }

    public void lowerGarment(View view) {

        Intent intent = new Intent(this, Measurement21.class);

        Log.i("OrderId in MsmtCtgrs: ", Long.toString(OrderId));
        Log.i("CustomerId in MsmtCtgr:", Long.toString(CustomerId));
        Log.i("MsrmtsId in MsmtCtgrs: ", Long.toString(MeasurementsId));
        Log.i("Source in MsmtCtgrs: ", source);

        Log.i("MsrmntSts in MsmtCtgrs:", Integer.toString(MeasurementStatus));

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);
        intent.putExtra("MeasurementsId1", MeasurementsId);

        intent.putExtra("MeasurementStatus1", MeasurementStatus);

        startActivity(intent);
    }

    public void moveToCart(View view) {

        Intent intent = new Intent(this, AddItem.class);

        Log.i("OrderId in MsmtCtgrs: ", Long.toString(OrderId));
        Log.i("CustomerId in MsmtCtgr:", Long.toString(CustomerId));
        Log.i("MsrmtsId in MsmtCtgrs: ", Long.toString(MeasurementsId));
        Log.i("Source in MsmtCtgrs: ", source);

        Log.i("MsrmntSts in MsmtCtgrs:", Integer.toString(MeasurementStatus));

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);

        intent.putExtra("source11", "cart");

        startActivity(intent);
    }
}
