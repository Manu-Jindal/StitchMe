package com.manujindal.stichme;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AppointmentDetails extends AppCompatActivity {
    SQLiteDatabase database;
    int position;
    Intent intent;
    Cursor c;
    int AppointmentId;
    String source;
    int OrderId;
    ListView itemDetailsList;
    ListViewAdapterWhite listViewAdapter;
    ArrayList<String> ItemLabelList = new ArrayList<String>();
    ArrayList<String> ItemOutputList = new ArrayList<String>();
    ArrayList<String> tableNameList = new ArrayList<String>();
    ArrayList<String> columnNameList = new ArrayList<String>();
    ArrayList<String> ItemIdTypeList = new ArrayList<String>();
    ArrayList<String> ItemIdList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        intent = getIntent();
        AppointmentId = intent.getIntExtra("AppointmentId1", -1);
        Log.i("AppointmentId: ", Integer.toString(AppointmentId));

        try
        {
            int CustomerId = -1;
            int EmployeeId = -1;
            Cursor c0 = database.rawQuery("SELECT * FROM appointments WHERE AppointmentId ="+AppointmentId, null);

            if(c0.moveToFirst())
            {
                CustomerId = c0.getInt(c0.getColumnIndex("CustomerId"));
                Log.i("CustomerId: ", Integer.toString(CustomerId));
                EmployeeId = c0.getInt(c0.getColumnIndex("EmployeeId"));
                Log.i("EmployeeId: ", Integer.toString(EmployeeId));
            }

            Cursor c = database.rawQuery("SELECT * FROM appointments, customers WHERE appointments.CustomerId = customers.CustomerId AND AppointmentId ="+AppointmentId, null);
            if(c.moveToFirst())
            {
                {

                    ItemLabelList.add("Name");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Name")));
                    Log.i("Name:", c.getString(c.getColumnIndex("Name")));
                    tableNameList.add("customers");
                    columnNameList.add("Name");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));

                    ItemLabelList.add("Phone");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Phone")));
                    Log.i("Phone:", c.getString(c.getColumnIndex("Phone")));
                    tableNameList.add("customers");
                    columnNameList.add("Phone");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));

                    ItemLabelList.add("Address");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Address")));
                    Log.i("Address:", c.getString(c.getColumnIndex("Address")));
                    tableNameList.add("customers");
                    columnNameList.add("Address");
                    ItemIdTypeList.add("CustomerId");
                    ItemIdList.add(Integer.toString(CustomerId));

                    ItemLabelList.add("Appointment Date");
                    ItemOutputList.add(c.getString(c.getColumnIndex("AppointmentDate")));
                    Log.i("AppointmentDate:", c.getString(c.getColumnIndex("AppointmentDate")));
                    tableNameList.add("appointments");
                    columnNameList.add("AppointmentDate");
                    ItemIdTypeList.add("AppointmentId");
                    ItemIdList.add(Integer.toString(AppointmentId));

                    ItemLabelList.add("Slot");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Slot")));
                    Log.i("Slot:", c.getString(c.getColumnIndex("Slot")));
                    tableNameList.add("appointments");
                    columnNameList.add("Slot");
                    ItemIdTypeList.add("AppointmentId");
                    ItemIdList.add(Integer.toString(AppointmentId));
                    
                    ItemLabelList.add("Remarks");
                    ItemOutputList.add(c.getString(c.getColumnIndex("Remarks")));
                    Log.i("Remarks:", c.getString(c.getColumnIndex("Remarks")));
                    tableNameList.add("appointments");
                    columnNameList.add("Remarks");
                    ItemIdTypeList.add("AppointmentId");
                    ItemIdList.add(Integer.toString(AppointmentId));
                }
            }

            Cursor c1 = database.rawQuery("SELECT * FROM appointments, employees WHERE appointments.EmployeeId = employees.EmployeeId AND AppointmentId ="+AppointmentId, null);
            if(c1.moveToFirst())
            {
                {

                    ItemLabelList.add("Employee: ");
                    ItemOutputList.add(c1.getString(c1.getColumnIndex("Name")));
                    Log.i("Name:", c1.getString(c1.getColumnIndex("Name")));
                    tableNameList.add("employees");
                    columnNameList.add("Name");
                    ItemIdTypeList.add("EmployeeId");
                    ItemIdList.add(Integer.toString(EmployeeId));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        itemDetailsList = (ListView) findViewById(R.id.appointmentDetailsListview);
        itemDetailsList.setNestedScrollingEnabled(true);

        listViewAdapter = new ListViewAdapterWhite(this, ItemLabelList, ItemOutputList);


        itemDetailsList.setAdapter(listViewAdapter);

        itemDetailsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                if(ItemOutputList.get(position) != "")
                {
                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(getApplicationContext());
                    View promptsView = li.inflate(R.layout.popup_input, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AppointmentDetails.this,  R.style.MyDialogTheme);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);

                    final TextView header = (TextView) promptsView
                            .findViewById(R.id.popupdialogtitle);


                    userInput.setText(ItemOutputList.get(position));
                    header.setText(ItemLabelList.get(position));

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // get user input and set it to result
                                            // edit text

                                            try
                                            {
                                                database.execSQL("UPDATE "+tableNameList.get(position)+" SET "+columnNameList.get(position)+" = '"+userInput.getText().toString()+"' WHERE "+
                                                        ItemIdTypeList.get(position)+" = "+ItemIdList.get(position));
                                                ItemOutputList.set(position, userInput.getText().toString());
                                                Toast.makeText(AppointmentDetails.this,ItemLabelList.get(position)+" changed to "+userInput.getText().toString(), Toast.LENGTH_SHORT).show();
                                                listViewAdapter.notifyDataSetChanged();
                                            }
                                            catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }

                // Toast.makeText(MainActivity.this,"Description => "+month[position]+"=> n Title"+number[position], Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        listViewAdapter.notifyDataSetChanged();
    }

    public void goBack(View view)
    {
        Intent intent = new Intent(this, AllAppointments.class);
        startActivity(intent);
    }
}
