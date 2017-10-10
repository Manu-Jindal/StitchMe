package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FabricDetails extends AppCompatActivity {

    Intent intent;

    int idSelected;
    SQLiteDatabase database;


    ListView itemDetailsList;
    ListViewAdapter listViewAdapter;
    ArrayList<String> TypeOfItemTypeList = new ArrayList<>();
    ArrayList<String> ItemOutputList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_details);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();

        idSelected = intent.getIntExtra("idSelected1", -1);

        Log.i("idSelected FbrcDtls:", Integer.toString(idSelected));

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        TypeOfItemTypeList.add("Fabric Type");
        TypeOfItemTypeList.add("Fabric Code");
        TypeOfItemTypeList.add("Fabric Price");

        try {
            Cursor c = database.rawQuery("SELECT * FROM fabrics WHERE FabricId = "+idSelected, null);
            if(c.moveToFirst())
            {

                ItemOutputList.add(c.getString(c.getColumnIndex("FabricType")));
                Log.i("ColumnAccessed Fabric: ", Integer.toString(1));
                ItemOutputList.add(c.getString(c.getColumnIndex("FabricCode")));
                Log.i("ColumnAccessed Fabric: ", Integer.toString(2));
                ItemOutputList.add(c.getString(c.getColumnIndex("FabricPrice")));
                Log.i("ColumnAccessed Fabric: ", Integer.toString(3));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        listViewAdapter = new ListViewAdapter(this, TypeOfItemTypeList, ItemOutputList);

        itemDetailsList = (ListView) findViewById(R.id.fabricDetailsList);

        itemDetailsList.setAdapter(listViewAdapter);

        itemDetailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                // Toast.makeText(MainActivity.this,"Description => "+month[position]+"=> n Title"+number[position], Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void viewAllFabrics(View view) {
        Intent intent = new Intent(this, FabricList.class);
        startActivity(intent);
    }
}
