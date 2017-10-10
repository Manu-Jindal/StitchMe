package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

public class GetMeasured extends AppCompatActivity {
    Intent intent;
    int id;
    SQLiteDatabase database;


    NumberPicker numberpicker;
    NumberPicker numberpicker1;
    NumberPicker numberpicker2;
    NumberPicker numberpicker3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_measured);


        intent = getIntent();
        id = intent.getIntExtra("id1", -1);
        Log.i("ID in GetMsrd result: ", Integer.toString(id));
        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        int start = 0;
        String[] numbers = new String[4];
        for(int i =0 ; i < 4 ; i++) {
            numbers[i] = start + "";
            start = start + 25;
        }
        numberpicker = (NumberPicker)findViewById(R.id.numberPicker);
        numberpicker1 = (NumberPicker) findViewById(R.id.numberPicker1);
        numberpicker2 = (NumberPicker)findViewById(R.id.numberPicker2);
        numberpicker3 = (NumberPicker)findViewById(R.id.numberPicker3);

        numberpicker.setMinValue(0);
        numberpicker.setMaxValue(100);

        numberpicker2.setMinValue(0);
        numberpicker2.setMaxValue(100);

        numberpicker1.setMinValue(0);
        numberpicker1.setMaxValue(3);
        numberpicker1.setDisplayedValues(numbers);


        numberpicker3.setMinValue(0);
        numberpicker3.setMaxValue(3);
        numberpicker3.setDisplayedValues(numbers);
    }
    public void nextActivity(View view) {

        Intent intent = new Intent(this, GetMeasured1.class);
        int measurement00 = numberpicker.getValue();
        int measurement01 = numberpicker1.getValue();
        int measurement10 = numberpicker2.getValue();
        int measurement11 = numberpicker3.getValue();
        double measurement0 = (double) measurement00 + (double)measurement01*25*0.01;
        double measurement1 = (double) measurement10 + (double)measurement11*25*0.01;
        try {
            String sql = "UPDATE abcd11 SET Measurement1 = '"+measurement0+"' WHERE id = "+id;
            database.execSQL(sql);
            String sql1 = "UPDATE abcd11 SET Measurement2 = '"+measurement1+"' WHERE id = "+id;
            database.execSQL(sql1);
            Log.i("ID in GetMsrd result1: ", Integer.toString(id));
            intent.putExtra("id1", id);


            Cursor c = database.rawQuery("SELECT * FROM abcd11", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result111", ""+c.getColumnIndex("Email"));
                Log.i("Result Measurement1: ", c.getString(c.getColumnIndex("Measurement1")));
                Log.i("Result Measurement2: ", c.getString(c.getColumnIndex("Measurement2")));
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
