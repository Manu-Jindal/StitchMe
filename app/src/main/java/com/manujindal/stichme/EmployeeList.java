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

public class EmployeeList extends AppCompatActivity {


    Intent intent;

    int EmployeeId;

    SQLiteDatabase database;

    ListView itemDetailsList;
    ListViewAdapter listViewAdapter;
    ArrayList<String> EmployeeNameList = new ArrayList<String>();
    ArrayList<String> EmployeePhoneList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);


        try {
            Cursor c = database.rawQuery("SELECT * FROM employees", null);
            if(c.moveToFirst())
            {
                EmployeeNameList.clear();
                EmployeePhoneList.clear();

                do {
                    EmployeeNameList.add(c.getString(c.getColumnIndex("Name")));
                    EmployeePhoneList.add(c.getString(c.getColumnIndex("Phone")));
                    Log.i("Name:", c.getString(c.getColumnIndex("Name")));
                    Log.i("Phone:", c.getString(c.getColumnIndex("Phone")));

                } while (c.moveToNext());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        itemDetailsList = (ListView) findViewById(R.id.employeeList);
        itemDetailsList.setNestedScrollingEnabled(true);

        listViewAdapter = new ListViewAdapter(this, EmployeeNameList, EmployeePhoneList);

        itemDetailsList.setAdapter(listViewAdapter);

        itemDetailsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {


                final int itemSelected = i;
                Cursor c2 = database.rawQuery("SELECT * FROM employees", null);
                int j=0;
                c2.moveToFirst();
                while(c2!=null && j!=itemSelected)
                {
                    j++;
                    c2.moveToNext();
                }
                final int EmployeeidSelected = c2.getInt(c2.getColumnIndex("EmployeeId"));
                Log.i("Employeeid Selected:", Integer.toString(EmployeeidSelected));
                Log.i("i result:", Integer.toString(i));

                new AlertDialog.Builder(EmployeeList.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int EmployeeidToDelete = EmployeeidSelected;
                                Log.i("Employeeid to delete:", Integer.toString(EmployeeidSelected));
                                String sql = "DELETE FROM employees WHERE EmployeeId = "+EmployeeidToDelete;
                                database.execSQL(sql);
                                EmployeeNameList.remove(itemSelected);
                                EmployeePhoneList.remove(itemSelected);
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
    public void newEmployee(View view) {

        Intent intent = new Intent(this, AddEmployee.class);
        try{

            database.execSQL("INSERT INTO employees(Name) values('test1234')");

            Cursor c = database.rawQuery("SELECT MAX(EmployeeId) from employees", null);
            c.moveToFirst();
            EmployeeId = c.getInt(c.getColumnIndex("MAX(EmployeeId)"));
            Log.i("Result: EmployeeId", Integer.toString(EmployeeId));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        intent.putExtra("EmployeeId1", EmployeeId);

        startActivity(intent);
    }
}
