package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

public class Measurement14 extends AppCompatActivity {
    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    long MeasurementsId;

    int MeasurementStatus;

    SQLiteDatabase database;


    NumberPicker numberpicker;
    NumberPicker numberpicker1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement14);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();

        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        MeasurementsId = intent.getLongExtra("MeasurementsId1", -1);

        MeasurementStatus = intent.getIntExtra("MeasurementStatus1", -1);

        Log.i("OrderId in Msrmnt14: ", Long.toString(OrderId));
        Log.i("CustomerId in Msrmnt14:", Long.toString(CustomerId));
        Log.i("MsrmtsId in Msrmnt14: ", Long.toString(MeasurementsId));
        Log.i("Source in Msrmnt14: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);


        int start = 0;
        String[] numbers = new String[4];
        for(int i =0 ; i < 4 ; i++) {
            numbers[i] = start + "";
            start = start + 25;
        }
        numberpicker = (NumberPicker)findViewById(R.id.numberPicker);
        numberpicker1 = (NumberPicker) findViewById(R.id.numberPicker1);

        numberpicker.setMinValue(0);
        numberpicker.setMaxValue(100);

        numberpicker1.setMinValue(0);
        numberpicker1.setMaxValue(3);
        numberpicker1.setDisplayedValues(numbers);
    }

    public void nextActivity(View view) {

        Intent intent = new Intent(this, Measurement15.class);
        int measurement00 = numberpicker.getValue();
        int measurement01 = numberpicker1.getValue();
        double measurement0 = (double) measurement00 + (double)measurement01*25*0.01;
        try {
            String sql = "UPDATE measurements SET Measurement14 = '"+measurement0+"' WHERE MeasurementsId = "+MeasurementsId;
            database.execSQL(sql);

            Log.i("OrderId in Msrmnt14: ", Long.toString(OrderId));
            Log.i("CustomerId in Msrmnt14:", Long.toString(CustomerId));
            Log.i("MsrmtsId in Msrmnt14: ", Long.toString(MeasurementsId));
            Log.i("Source in Msrmnt14: ", source);

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);
            intent.putExtra("MeasurementsId1", MeasurementsId);

            intent.putExtra("MeasurementStatus1", MeasurementStatus);

            Cursor c = database.rawQuery("SELECT * FROM measurements", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result Measurement14: ", c.getString(c.getColumnIndex("Measurement14")));
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
