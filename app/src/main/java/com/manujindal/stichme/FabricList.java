package com.manujindal.stichme;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FabricList extends AppCompatActivity {


    Intent intent;

    int FabricId;

    SQLiteDatabase database;

    ListView itemDetailsList;
    ListViewAdapter listViewAdapter;
    ArrayList<String> FabricCodelList = new ArrayList<String>();
    ArrayList<String> FabricCodelList1 = new ArrayList<String>();
    String[] FabricCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_list);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);


        try {
            Cursor c = database.rawQuery("SELECT * FROM fabrics WHERE FabricType = 'Shirt' OR  FabricType = 'Suit' OR FabricType = 'Trouser'", null);
            if(c.moveToFirst())
            {
                FabricCodelList.clear();
                FabricCodelList1.clear();

                do {
                    FabricCodelList.add(c.getString(c.getColumnIndex("FabricCode")));
                    FabricCodelList1.add(c.getString(c.getColumnIndex("FabricPrice")));
                    Log.i("FabricCode:", c.getString(c.getColumnIndex("FabricCode")));
                    Log.i("FabricPrice:", c.getString(c.getColumnIndex("FabricPrice")));


                } while (c.moveToNext());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        itemDetailsList = (ListView) findViewById(R.id.fabricList);
        itemDetailsList.setNestedScrollingEnabled(true);

        listViewAdapter = new ListViewAdapter(this, FabricCodelList, FabricCodelList1);

        itemDetailsList.setAdapter(listViewAdapter);

        itemDetailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                Cursor c = database.rawQuery("SELECT * FROM fabrics WHERE FabricType = 'Shirt' OR  FabricType = 'Suit' OR FabricType = 'Trouser'", null);
                c.moveToFirst();
                int i = 0;
                while (c != null && i != position) {
                    i++;
                    c.moveToNext();
                }
                int idSelected = c.getInt((c.getColumnIndex("FabricId")));

                Log.i("idSelected1:", Integer.toString(idSelected));

                Intent intent = new Intent(getApplicationContext(), FabricDetails.class);

                intent.putExtra("idSelected1", idSelected);

                startActivity(intent);
            }
        });

        itemDetailsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {


                final int itemSelected = i;
                Cursor c2 = database.rawQuery("SELECT * FROM fabrics WHERE FabricType = 'Shirt' OR  FabricType = 'Suit' OR FabricType = 'Trouser'", null);
                int j=0;
                c2.moveToFirst();
                while(c2!=null && j!=itemSelected)
                {
                    j++;
                    c2.moveToNext();
                }
                final int FabricidSelected = c2.getInt(c2.getColumnIndex("FabricId"));
                Log.i("FabricId Selected:", Integer.toString(FabricidSelected));
                Log.i("i result:", Integer.toString(i));

                new AlertDialog.Builder(FabricList.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int FabricidToDelete = FabricidSelected;
                                Log.i("Fabricid to delete:", Integer.toString(FabricidSelected));
                                String sql = "DELETE FROM fabrics WHERE FabricId = "+FabricidToDelete;
                                database.execSQL(sql);
                                FabricCodelList.remove(itemSelected);
                                listViewAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true ;
            }
        });


        listViewAdapter.notifyDataSetChanged();
    }

    public void goBack(View view) {

        Intent intent = new Intent(this, AdminPanel.class);
        startActivity(intent);
    }
    public void newFabric(View view) {

        Intent intent = new Intent(this, AddFabric.class);
        try{

            database.execSQL("INSERT INTO fabrics(FabricCode) values('code1234')");

            Cursor c = database.rawQuery("SELECT MAX(FabricId) from fabrics", null);
            c.moveToFirst();
            FabricId = c.getInt(c.getColumnIndex("MAX(FabricId)"));
            Log.i("Result: FabricId", Integer.toString(FabricId));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        intent.putExtra("FabricId1", FabricId);

        startActivity(intent);
    }
}
