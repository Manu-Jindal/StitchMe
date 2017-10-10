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

import org.apache.commons.lang3.StringUtils;

public class EnterName extends AppCompatActivity {
    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    String source1;
    SQLiteDatabase database;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        source1 = intent.getStringExtra("source11");
        Log.i("OrderId in Name: ", Long.toString(OrderId));
        Log.i("CustomerId in Name: ", Long.toString(CustomerId));
        Log.i("Source in Name: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        name = (EditText) findViewById(R.id.Name);
    }

    public void nextActFromMain(View view) {


        Intent intent = new Intent(this, Main2.class);
        String name1 = name.getText().toString();
        try {
            String sql = "UPDATE customers SET Name = '"+name1+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql);

            Log.i("OrderId in Name: ", Long.toString(OrderId));
            Log.i("CustomerId in Name: ", Long.toString(CustomerId));
            Log.i("Source in Name: ", source);

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("source11", source1);
            intent.putExtra("CustomerId1", CustomerId);

            Cursor c = database.rawQuery("SELECT * FROM customers", null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result Name: ", c.getString(c.getColumnIndex("Name")));
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
