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
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class CustomersList extends AppCompatActivity {

    SQLiteDatabase database;
    ArrayList<String> customers = new ArrayList<>();
    ListView listview;
    ArrayAdapter arrayAdapter;
    String source;
    long OrderId;
    Cursor c;
    Button backToHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_list);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        Intent intent = getIntent();

        source = intent.getStringExtra("source1");
        if(source.equals("inFlow"))
        {
            OrderId = intent.getLongExtra("OrderId1", -1);
        }

        backToHome = (Button) findViewById(R.id.backHomeCustomersList);
        if(source.equals("inFlow")){
            backToHome.setVisibility(View.GONE);
        }
        if(source.equals("customerList")){
            backToHome.setVisibility(View.VISIBLE);
        }

        listview = (ListView) findViewById(R.id.customerListVIew);
        listview.setNestedScrollingEnabled(true);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, customers);
        listview.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(getApplicationContext(), CustomerDetails.class);
                Cursor c1 = database.rawQuery("SELECT * FROM customers", null);
                c1.moveToLast();
                int i = 0;
                while (c1 != null && i != position) {
                    i++;
                    c1.moveToPrevious();
                }
                long CustomerId = c1.getInt((c1.getColumnIndex("CustomerId")));
                intent.putExtra("CustomerId1", CustomerId);
                if(source.equals("inFlow"))
                {
                    intent.putExtra("OrderId1", OrderId);
                    intent.putExtra("source1", "inFlow");
                }
                if(source.equals("customerList"))
                {
                    intent.putExtra("source1", "customerList");
                }
                Log.i("Customerid Selected:", Long.toString(CustomerId));
                startActivity(intent);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {


                final int itemSelected = i;
                Cursor c2 = database.rawQuery("SELECT * FROM customers", null);
                int j=0;
                c2.moveToLast();
                while(c2!=null && j!=itemSelected)
                {
                    j++;
                    c2.moveToPrevious();
                }
                final long CustomeridSelected = c2.getInt(c2.getColumnIndex("CustomerId"));
                Log.i("Customerid Selected:", Long.toString(CustomeridSelected));
                Log.i("i result:", Integer.toString(i));

                new AlertDialog.Builder(CustomersList.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                long CustomeridToDelete = CustomeridSelected;
                                Log.i("Customerid to delete:", Long.toString(CustomeridToDelete));
                                String sql = "DELETE FROM customers WHERE CustomerId = "+CustomeridToDelete;
                                database.execSQL(sql);
                                customers.remove(itemSelected);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true ;
            }
        });

        try {
            Cursor c = database.rawQuery("SELECT * FROM customers", null);
            if(c.moveToLast()) {
                customers.clear();
                do {
                    String namecurrent = c.getString(c.getColumnIndex("Name"));
                    if(namecurrent.equals("test1234"))
                    {
                        long idTodelete = c.getInt(c.getColumnIndex("CustomerId"));
                        database.execSQL("DELETE FROM customers WHERE CustomerId = "+idTodelete);
                        Log.i("Item dltd with CustId", Long.toString(idTodelete));
                        continue;
                    }
                    customers.add(c.getString(c.getColumnIndex("Name")));
                } while (c.moveToPrevious());

                arrayAdapter.notifyDataSetChanged();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void newCustomer(View view) {

        Intent intent = new Intent(this, EnterName.class);
        database.execSQL("INSERT INTO customers(Name) values('test1234');");
        Cursor c = database.rawQuery("SELECT MAX(CustomerId) from customers", null);
        int customerIdindex = c.getColumnIndex("MAX(CustomerId)");
        c.moveToFirst();
        long CustomerId = c.getInt(customerIdindex);

        long time= System.currentTimeMillis();
        String sql2 = "UPDATE customers SET CustomerId = "+time+" WHERE CustomerId = "+CustomerId;
        database.execSQL(sql2);

        CustomerId = time;

        Log.i("CstmrId from CstmrExsts", Long.toString(CustomerId));

        intent.putExtra("CustomerId1", CustomerId);

        if(source.equals("inFlow")){

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", "inFlow");
            intent.putExtra("source11", "inFlowCustomersList");
        }

        if(source.equals("customerList")){

            intent.putExtra("source1", "customerList");
            intent.putExtra("source11", "newCustomer");

        }

        startActivity(intent);
    }

    public void backHome(View view){
        Intent intent = new Intent(this, AdminPanel.class);
        startActivity(intent);
    }
}
