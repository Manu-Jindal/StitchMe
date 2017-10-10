package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditCustomer extends AppCompatActivity {

    Intent intent;
    SQLiteDatabase database;
    int CustomerId;
    int OrderId;
    String columnName;
    EditText display;
    int position;
    String source;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        CustomerId = intent.getIntExtra("CustomerId1", -1);
        source = intent.getStringExtra("source1");
        columnName = intent.getStringExtra("columnName1");
        if(source.equals("inFlow"))
        {
            OrderId = intent.getIntExtra("OrderId1", -1);
        }

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        display = (EditText) findViewById(R.id.display);

        Cursor c = database.rawQuery("SELECT "+columnName+" FROM customers WHERE CustomerId="+CustomerId, null);
        c.moveToFirst();
        display.setText(c.getString(c.getColumnIndex(columnName)));
    }
    public void saveChanges(View view) {
        Intent intent = new Intent(this, CustomerDetails.class);
        String display1 = display.getText().toString();
        try {
            String sql = "UPDATE customers SET "+columnName+" = '"+display1+"' WHERE CustomerId = "+CustomerId;
            database.execSQL(sql);
            if(source.equals("inFlow"))
            {
                intent.putExtra("OrderId1", OrderId);
            }
            intent.putExtra("CustomerId1", CustomerId);
            intent.putExtra("source1", source);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        startActivity(intent);
    }

}
