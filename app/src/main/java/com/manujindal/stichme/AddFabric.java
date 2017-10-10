package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

public class AddFabric extends AppCompatActivity {


    Intent intent;

    int FabricId;

    SQLiteDatabase database;
    Spinner FabricType;

    String FabricTypeSelected;
    String FabricCode;
    int FabricPrice;

    EditText FabricCode1;
    EditText FabricPrice1;



    String[] FabricTypes = {"Shirt", "Suit", "Trouser"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fabric);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();

        FabricId = intent.getIntExtra("FabricId1", -1);

        Log.i("FabricId in AddFabric: ", Integer.toString(FabricId));

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        FabricCode1 = (EditText) findViewById(R.id.fabricCode);
        FabricPrice1 = (EditText) findViewById(R.id.fabricPrice);

        FabricType = (Spinner) findViewById(R.id.fabricType);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AddFabric.this,
                android.R.layout.simple_spinner_item,FabricTypes);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FabricType.setAdapter(adapter1);
        FabricType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                FabricTypeSelected = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void addFabric(View view) {


        Intent intent = new Intent(this, FabricList.class);
        FabricCode = FabricCode1.getText().toString();
        int FabricPrice = (StringUtils.isNotBlank(FabricPrice1.getText().toString()) ? Integer.parseInt(FabricPrice1.getText().toString()): 0);

        try {
            String sql = "UPDATE fabrics SET FabricType = '"+FabricTypeSelected+"' WHERE FabricId = "+FabricId;
            database.execSQL(sql);
            String sql1 = "UPDATE fabrics SET FabricCode = '"+FabricCode+"' WHERE FabricId = "+FabricId;
            database.execSQL(sql1);
            String sql2 = "UPDATE fabrics SET FabricPrice = '"+FabricPrice+"' WHERE FabricId = "+FabricId;
            database.execSQL(sql2);

            Log.i("FabricId in AddFabric: ", Integer.toString(FabricId));
            Log.i("FabricType in AddFabrc:", FabricTypeSelected);
            Log.i("FabricCode in AddFabrc:", FabricCode);
            Log.i("FabricPrice in AddFbrc:", Integer.toString(FabricPrice));

            Cursor c = database.rawQuery("SELECT * FROM fabrics", null);
            c.moveToFirst();
            /*while(c!=null)
            {
                Log.i("Result Email: ", c.getString(c.getColumnIndex("Email")));
                Log.i("Result Phone: ", c.getString(c.getColumnIndex("Phone")));
                c.moveToNext();
            }*/

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        startActivity(intent);

    }

}
