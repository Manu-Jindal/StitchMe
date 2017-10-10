package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

public class GetMeasured5 extends AppCompatActivity {
    Intent intent;
    int id;
    SQLiteDatabase database;


    NumberPicker numberpicker;
    NumberPicker numberpicker1;
    NumberPicker numberpicker2;
    NumberPicker numberpicker3;
    NumberPicker numberpicker4;
    NumberPicker numberpicker5;
    NumberPicker numberpicker6;
    NumberPicker numberpicker7;
    NumberPicker numberpicker8;
    NumberPicker numberpicker9;
    NumberPicker numberpicker10;
    NumberPicker numberpicker11;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_measured5);
        intent = getIntent();
        id = intent.getIntExtra("id1", -1);
        Log.i("ID in GetMsrd5 result: ", Integer.toString(id));
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
        numberpicker4 = (NumberPicker)findViewById(R.id.numberPicker4);
        numberpicker5 = (NumberPicker)findViewById(R.id.numberPicker5);
        numberpicker6 = (NumberPicker)findViewById(R.id.numberPicker6);
        numberpicker7 = (NumberPicker)findViewById(R.id.numberPicker7);
        numberpicker8 = (NumberPicker)findViewById(R.id.numberPicker8);
        numberpicker9 = (NumberPicker)findViewById(R.id.numberPicker9);
        numberpicker10 = (NumberPicker)findViewById(R.id.numberPicker10);
        numberpicker11 = (NumberPicker)findViewById(R.id.numberPicker11);

        numberpicker.setMinValue(0);
        numberpicker.setMaxValue(100);

        numberpicker1.setMinValue(0);
        numberpicker1.setMaxValue(3);
        numberpicker1.setDisplayedValues(numbers);

        numberpicker2.setMinValue(0);
        numberpicker2.setMaxValue(100);

        numberpicker3.setMinValue(0);
        numberpicker3.setMaxValue(3);
        numberpicker3.setDisplayedValues(numbers);

        numberpicker4.setMinValue(0);
        numberpicker4.setMaxValue(100);

        numberpicker5.setMinValue(0);
        numberpicker5.setMaxValue(3);
        numberpicker5.setDisplayedValues(numbers);

        numberpicker6.setMinValue(0);
        numberpicker6.setMaxValue(100);

        numberpicker7.setMinValue(0);
        numberpicker7.setMaxValue(3);
        numberpicker7.setDisplayedValues(numbers);

        numberpicker8.setMinValue(0);
        numberpicker8.setMaxValue(100);

        numberpicker9.setMinValue(0);
        numberpicker9.setMaxValue(3);
        numberpicker9.setDisplayedValues(numbers);

        numberpicker10.setMinValue(0);
        numberpicker10.setMaxValue(100);

        numberpicker11.setMinValue(0);
        numberpicker11.setMaxValue(3);
        numberpicker11.setDisplayedValues(numbers);
    }
    public void nextActivity(View view) {

        Intent intent = new Intent(this, GetMeasured6.class);
        int measurement00 = numberpicker.getValue();
        int measurement01 = numberpicker1.getValue();
        int measurement10 = numberpicker2.getValue();
        int measurement11 = numberpicker3.getValue();
        int measurement20 = numberpicker4.getValue();
        int measurement21 = numberpicker5.getValue();
        int measurement30 = numberpicker6.getValue();
        int measurement31 = numberpicker7.getValue();
        int measurement40 = numberpicker8.getValue();
        int measurement41 = numberpicker9.getValue();
        int measurement50 = numberpicker10.getValue();
        int measurement51 = numberpicker11.getValue();
        double measurement0 = (double) measurement00 + (double)measurement01*25*0.01;
        double measurement1 = (double) measurement10 + (double)measurement11*25*0.01;
        double measurement2 = (double) measurement20 + (double)measurement21*25*0.01;
        double measurement3 = (double) measurement30 + (double)measurement31*25*0.01;
        double measurement4 = (double) measurement40 + (double)measurement41*25*0.01;
        double measurement5 = (double) measurement50 + (double)measurement51*25*0.01;

        try {
            String sql = "UPDATE abcd11 SET Measurement21 = '"+measurement0+"' WHERE id = "+id;
            database.execSQL(sql);
            String sql1 = "UPDATE abcd11 SET Measurement22 = '"+measurement1+"' WHERE id = "+id;
            database.execSQL(sql1);
            String sql2 = "UPDATE abcd11 SET Measurement23 = '"+measurement2+"' WHERE id = "+id;
            database.execSQL(sql2);
            String sql3 = "UPDATE abcd11 SET Measurement24 = '"+measurement3+"' WHERE id = "+id;
            database.execSQL(sql3);
            String sql4 = "UPDATE abcd11 SET Measurement25 = '"+measurement4+"' WHERE id = "+id;
            database.execSQL(sql4);
            String sql5 = "UPDATE abcd11 SET Measurement26 = '"+measurement5+"' WHERE id = "+id;
            database.execSQL(sql5);

            Log.i("ID inGetMsrd5 result1: ", Integer.toString(id));
            intent.putExtra("id1", id);


            Cursor c = database.rawQuery("SELECT * FROM abcd11", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result111", ""+c.getColumnIndex("Email"));
                Log.i("Result Measurement21: ", c.getString(c.getColumnIndex("Measurement21")));
                Log.i("Result Measurement22: ", c.getString(c.getColumnIndex("Measurement22")));
                Log.i("Result Measurement23: ", c.getString(c.getColumnIndex("Measurement23")));
                Log.i("Result Measurement24: ", c.getString(c.getColumnIndex("Measurement24")));
                Log.i("Result Measurement25: ", c.getString(c.getColumnIndex("Measurement25")));
                Log.i("Result Measurement26: ", c.getString(c.getColumnIndex("Measurement26")));
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

