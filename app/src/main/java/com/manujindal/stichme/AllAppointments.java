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
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class AllAppointments extends AppCompatActivity {

    SQLiteDatabase database;
    ArrayList<String> appointments = new ArrayList<>();
    ListView listview;
    ArrayAdapter arrayAdapter;
    String source;
    int OrderId;
    Cursor c;
    Button backToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_appointments);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        listview = (ListView) findViewById(R.id.appointmentsListVIew);
        listview.setNestedScrollingEnabled(true);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, appointments);
        listview.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(getApplicationContext(), AppointmentDetails.class);
                Cursor c1 = database.rawQuery("SELECT * FROM appointments, customers WHERE appointments.CustomerId = customers.CustomerId", null);
                c1.moveToLast();
                int i = 0;
                while (c1 != null && i != position) {
                    i++;
                    c1.moveToPrevious();
                }
                int AppointmentId = c1.getInt((c1.getColumnIndex("AppointmentId")));
                intent.putExtra("AppointmentId1", AppointmentId);
                Log.i("AppointmentId Selected:", Integer.toString(AppointmentId));
                startActivity(intent);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {


                final int itemSelected = i;
                Cursor c2 = database.rawQuery("SELECT * FROM appointments, customers WHERE appointments.CustomerId = customers.CustomerId", null);
                int j=0;
                c2.moveToLast();
                while(c2!=null && j!=itemSelected)
                {
                    j++;
                    c2.moveToPrevious();
                }
                final int AppointmentIdSelected = c2.getInt(c2.getColumnIndex("AppointmentId"));
                Log.i("AppointmentId Selected:", Integer.toString(AppointmentIdSelected));
                Log.i("i result:", Integer.toString(i));

                new AlertDialog.Builder(AllAppointments.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int AppointmentIdToDelete = AppointmentIdSelected;
                                Log.i("ApntmntId to delete:", Integer.toString(AppointmentIdToDelete));
                                String sql = "DELETE FROM appointments WHERE AppointmentId = "+AppointmentIdToDelete;
                                database.execSQL(sql);
                                appointments.remove(itemSelected);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true ;
            }
        });

        try {
            Cursor c = database.rawQuery("SELECT * FROM appointments, customers WHERE appointments.CustomerId = customers.CustomerId", null);
            if(c.moveToLast()) {
                appointments.clear();
                do {
                    String namecurrent = c.getString(c.getColumnIndex("Name"));
                    if(namecurrent.equals("test1234"))
                    {
                        int idTodelete = c.getInt(c.getColumnIndex("AppointmentId"));
                        database.execSQL("DELETE FROM appointments WHERE AppointmentId = "+idTodelete);
                        Log.i("Item dltd with AptmntId", Integer.toString(idTodelete));
                        continue;
                    }
                    appointments.add(c.getString(c.getColumnIndex("Name")));
                } while (c.moveToPrevious());

                arrayAdapter.notifyDataSetChanged();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void newAppointment(View view) {

        Intent intent = new Intent(this, AppointmentCustomerDetails.class);

        database.execSQL("INSERT INTO customers(Name) values('test1234');");
        Cursor c0 = database.rawQuery("SELECT MAX(CustomerId) from customers", null);
        int customerIdindex = c0.getColumnIndex("MAX(CustomerId)");
        c0.moveToFirst();
        int CustomerId = c0.getInt(customerIdindex);
        Log.i("CstmrId from AllApntmnt", Integer.toString(CustomerId));
        intent.putExtra("CustomerId1", CustomerId);
        
        database.execSQL("INSERT INTO appointments(CustomerId) values("+CustomerId+");");
        Cursor c = database.rawQuery("SELECT MAX(AppointmentId) from appointments", null);
        c.moveToFirst();
        int AppointmentId = c.getInt(c.getColumnIndex("MAX(AppointmentId)"));
        Log.i("AptmntId frm AllApntmnt", Integer.toString(AppointmentId));
        intent.putExtra("AppointmentId1", AppointmentId);

        startActivity(intent);
    }

    public void backHome(View view){
        Intent intent = new Intent(this, AdminPanel.class);
        startActivity(intent);
    }
}
