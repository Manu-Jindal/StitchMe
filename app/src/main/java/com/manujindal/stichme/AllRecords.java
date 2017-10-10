package com.manujindal.stichme;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllRecords extends AppCompatActivity {

    Intent intent;
    SQLiteDatabase database;

    int advanceAmount;
    int orderNumber;
    long phoneNo;
    String CustomerName;
    double paymentPending;
    double billAmount;


    ListView itemDetailsList;
    ListViewAdapterWhite listViewAdapter;
    ArrayList<String> TypeOfItemTypeList = new ArrayList<>();
    ArrayList<String> ItemOutputList = new ArrayList<>();

    /*--
    SQLiteDatabase database;
    ArrayList<String> orderNumbers = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ListView listview;
    ArrayAdapter arrayAdapter;
    Cursor c;
    --*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_records);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        try {
            Cursor c = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId = customers.CustomerId", null);
            if(c.moveToLast())
            {
                do{
                    Log.i("OrderNumber:", c.getString(c.getColumnIndex("OrderNumber")));
                    TypeOfItemTypeList.add(c.getString(c.getColumnIndex("OrderNumber")));
                    Log.i("Name:", c.getString(c.getColumnIndex("Name")));
                    ItemOutputList.add(c.getString(c.getColumnIndex("Name")));


                }while(c.moveToPrevious());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        itemDetailsList = (ListView) findViewById(R.id.listview);
        listViewAdapter = new ListViewAdapterWhite(this, TypeOfItemTypeList, ItemOutputList);


        itemDetailsList.setAdapter(listViewAdapter);

        itemDetailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                Intent intent = new Intent(getApplicationContext(), DetailedRecord.class);
                Cursor c1 = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId = customers.CustomerId", null);
                c1.moveToLast();
                int i = 0;
                while (c1 != null && i != position) {
                    i++;
                    c1.moveToPrevious();
                }
                int CustomerId = c1.getInt((c1.getColumnIndex("CustomerId")));
                int OrderId = c1.getInt((c1.getColumnIndex("OrderId")));
                intent.putExtra("OrderId1", OrderId);
                intent.putExtra("CustomerId1", CustomerId);
                intent.putExtra("source1", "allRecords");
                Log.i("Customerid Selected:", Integer.toString(CustomerId));
                Log.i("OrderId Selected:", Integer.toString(OrderId));
                startActivity(intent);


                // Toast.makeText(MainActivity.this,"Description => "+month[position]+"=> n Title"+number[position], Toast.LENGTH_SHORT).show();
            }
        });


        itemDetailsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {

                final int itemSelected = i;
                Cursor c2 = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId = customers.CustomerId", null);
                int j=0;
                c2.moveToLast();
                while(c2!=null && j!=itemSelected)
                {
                    j++;
                    c2.moveToPrevious();
                }
                final int CustomeridSelected = c2.getInt(c2.getColumnIndex("orders.CustomerId"));
                final int OrderidSelected = c2.getInt(c2.getColumnIndex("OrderId"));

                Log.i("Customerid Selected:", Integer.toString(CustomeridSelected));
                Log.i("OrderId Selected:", Integer.toString(OrderidSelected));
                Log.i("i result:", Integer.toString(i));

                final String[] options = {"Change Status", "Add Discount", "Remove Discount", "Delete Record"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AllRecords.this);
                String currentStatus = c2.getString(c2.getColumnIndex("OrderStatus"));
                builder.setTitle(currentStatus);
                Cursor c = database.rawQuery("SELECT * FROM customers WHERE CustomerId = "+CustomeridSelected, null);
                if(c.moveToFirst())
                {
                    phoneNo = c.getLong(c.getColumnIndex("Phone"));
                    CustomerName = c.getString(c.getColumnIndex("Name"));
                }
                Cursor c1 = database.rawQuery("SELECT * FROM orders WHERE OrderId = "+OrderidSelected, null);
                if(c1.moveToFirst())
                {
                    orderNumber = c1.getInt(c1.getColumnIndex("OrderNumber"));
                    advanceAmount = c1.getInt(c1.getColumnIndex("AdvancePaid"));
                    paymentPending = c1.getDouble(c1.getColumnIndex("PaymentDue"));
                    Log.i("Payment Due", paymentPending+"");
                    billAmount = c1.getDouble(c1.getColumnIndex("FinalBillAmount"));
                }

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if(options[item].equals("Change Status"))
                        {
                            final String[] orderStatuses = {"Order Initiated", "Order Recieved","Order Confirmed","Order Form Ready", "Order With Fabricator(I)",
                                    "Order Ready For Trial", "Order Trial Done", "Order With Fabricator(II)","Order Ready for Delivery","Order Delivered"};


                            AlertDialog.Builder builder1 = new AlertDialog.Builder(AllRecords.this);
                            builder1.setTitle("Update Status:");
                            builder1.setItems(orderStatuses, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item1) {



                                    final String newStatus = orderStatuses[item1];
                                    String sql = "UPDATE orders SET OrderStatus = '"+newStatus+"' WHERE OrderId = "+OrderidSelected;
                                    database.execSQL(sql);

                                    try {

                                        String rootphone = "9818020151";

                                        SmsManager smsManager = SmsManager.getDefault();
                                        smsManager.sendTextMessage((rootphone), null, "Status updated to "+newStatus+" for "+CustomerName+" (#"+orderNumber+")", null, null);

                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(),
                                                "SMS failed, please try again later!",
                                                Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }

                                    listViewAdapter.notifyDataSetChanged();
                                    new AlertDialog.Builder(AllRecords.this)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setTitle("Change Status")
                                            .setMessage("Do you want to notify the customer?")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String sms;
                                                    if(newStatus.equals("Order Confirmed")) {
                                                        sms = "Thanks for placing the order with us. We have recieved an amount of Rs." + advanceAmount + " for your order number " + orderNumber + ". Let's get you Stitched!\nTeam StitchMe";
                                                        try {

                                                            String sql2 = "UPDATE orders SET AdvanceRecieved ='Yes' WHERE OrderId = "+OrderidSelected;
                                                            database.execSQL(sql2);

                                                            SmsManager smsManager = SmsManager.getDefault();
                                                            smsManager.sendTextMessage(Long.toString(phoneNo), null, sms, null, null);

                                                        } catch (Exception e) {
                                                            Toast.makeText(getApplicationContext(),
                                                                    "SMS failed, please try again later!",
                                                                    Toast.LENGTH_LONG).show();
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    if(newStatus.equals("Order With Fabricator(I)")){
                                                        sms = "Hi "+CustomerName+", your order number "+orderNumber+" is in progress. We will contact you soon to give the next update.\nTeam StitchMe";
                                                        try {
                                                            SmsManager smsManager = SmsManager.getDefault();
                                                            smsManager.sendTextMessage(Long.toString(phoneNo), null, sms, null, null);

                                                        } catch (Exception e) {
                                                            Toast.makeText(getApplicationContext(),
                                                                    "SMS faild, please try again later!",
                                                                    Toast.LENGTH_LONG).show();
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    if(newStatus.equals("Order Ready For Trial")){
                                                        sms = "Hi "+CustomerName+", your order number "+orderNumber+" is ready for the trial, please suggest us a suitable time to meet you tomorrow.\nTeam StitchMe";
                                                        try {
                                                            SmsManager smsManager = SmsManager.getDefault();
                                                            smsManager.sendTextMessage(Long.toString(phoneNo), null, sms, null, null);

                                                        } catch (Exception e) {
                                                            Toast.makeText(getApplicationContext(),
                                                                    "SMS faild, please try again later!",
                                                                    Toast.LENGTH_LONG).show();
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                    if(newStatus.equals("Order for Delivery")){
                                                        sms = "Hi "+CustomerName+", your order number "+orderNumber+" is ready for delivery, please suggest us a suitable time to meet you tomorrow.\nTeam StitchMe";
                                                        try {
                                                            SmsManager smsManager = SmsManager.getDefault();
                                                            smsManager.sendTextMessage(Long.toString(phoneNo), null, sms, null, null);

                                                        } catch (Exception e) {
                                                            Toast.makeText(getApplicationContext(),
                                                                    "SMS faild, please try again later!",
                                                                    Toast.LENGTH_LONG).show();
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    if(newStatus.equals("Order Delivered")){

                                                        sms = "Hi "+CustomerName+", your order number "+orderNumber+" is delivered. Feedback Form: .\n" +
                                                                "https://form.jotform.me/StitchMe/feedback-form .\nTeam StitchMe";
                                                        try {
                                                            SmsManager smsManager = SmsManager.getDefault();
                                                            smsManager.sendTextMessage(Long.toString(phoneNo), null, sms, null, null);

                                                        } catch (Exception e) {
                                                            Toast.makeText(getApplicationContext(),
                                                                    "SMS faild, please try again later!",
                                                                    Toast.LENGTH_LONG).show();
                                                            e.printStackTrace();
                                                        }

                                                        if(paymentPending > 0)
                                                        {
                                                            Intent intent = new Intent(getApplicationContext(), PaymentPendingAlert.class);
                                                            intent.putExtra("OrderId1", OrderidSelected);
                                                            intent.putExtra("CustomerId1", CustomeridSelected);
                                                            intent.putExtra("source1", "delivery");
                                                            intent.putExtra("billAmountValue1", billAmount);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                    if(newStatus.equals("Order Trial Done")){

                                                        if(paymentPending > 0)
                                                        {
                                                            Intent intent = new Intent(getApplicationContext(), PaymentPendingAlert.class);
                                                            intent.putExtra("OrderId1", OrderidSelected);
                                                            intent.putExtra("CustomerId1", CustomeridSelected);
                                                            intent.putExtra("source1", "trial");
                                                            intent.putExtra("billAmountValue1", billAmount);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                    if(newStatus.equals("Order Delivered")){

                                                        if(paymentPending > 0)
                                                        {
                                                            Intent intent = new Intent(getApplicationContext(), PaymentPendingAlert.class);
                                                            intent.putExtra("OrderId1", OrderidSelected);
                                                            intent.putExtra("CustomerId1", CustomeridSelected);
                                                            intent.putExtra("source1", "delivery");
                                                            intent.putExtra("billAmountValue1", billAmount);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    if(newStatus.equals("Order Trial Done")){

                                                        if(paymentPending > 0)
                                                        {
                                                            Intent intent = new Intent(getApplicationContext(), PaymentPendingAlert.class);
                                                            intent.putExtra("OrderId1", OrderidSelected);
                                                            intent.putExtra("CustomerId1", CustomeridSelected);
                                                            intent.putExtra("source1", "trial");
                                                            intent.putExtra("billAmountValue1", billAmount);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                    if(newStatus.equals("Order Delivered")){

                                                        if(paymentPending > 0)
                                                        {
                                                            Intent intent = new Intent(getApplicationContext(), PaymentPendingAlert.class);
                                                            intent.putExtra("OrderId1", OrderidSelected);
                                                            intent.putExtra("CustomerId1", CustomeridSelected);
                                                            intent.putExtra("source1", "delivery");
                                                            intent.putExtra("billAmountValue1", billAmount);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                }
                                            })
                                            .show();

                                    Toast.makeText(getApplicationContext(), "Status Changed to: "+newStatus, Toast.LENGTH_LONG).show();



                                }
                            });
                            AlertDialog alert1 = builder1.create();
                            alert1.show();



                        }
                        if(options[item].equals("Delete Record"))
                        {
                            new AlertDialog.Builder(AllRecords.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Are you sure?")
                                    .setMessage("Do you want to delete this entry?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int OrderidToDelete = OrderidSelected;
                                            Log.i("OrderId to delete:", Integer.toString(OrderidToDelete));
                                            String sql = "DELETE FROM orders WHERE OrderId = "+OrderidToDelete;
                                            database.execSQL(sql);
                                            TypeOfItemTypeList.remove(itemSelected);
                                            ItemOutputList.remove(itemSelected);
                                            listViewAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                        if(options[item].equals("Add Discount"))
                        {

                            // get prompts.xml view
                            LayoutInflater li = LayoutInflater.from(getApplicationContext());
                            View promptsView = li.inflate(R.layout.popup_input, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AllRecords.this, R.style.MyDialogTheme);

                            // set prompts.xml to alertdialog builder
                            alertDialogBuilder.setView(promptsView);

                            final EditText userInput = (EditText) promptsView
                                    .findViewById(R.id.editTextDialogUserInput);

                            final TextView header = (TextView) promptsView
                                    .findViewById(R.id.popupdialogtitle);

                            header.setText("Enter Discount %age");
                            userInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                            // set dialog message
                            alertDialogBuilder
                                    .setCancelable(false)
                                    .setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // get user input and set it to result
                                                    // edit text

                                                    try {
                                                        String sql = "UPDATE orders SET Discount = '"+userInput.getText().toString()+"' WHERE OrderId = "+OrderidSelected;
                                                        database.execSQL(sql);

                                                        Cursor c = database.rawQuery("SELECT * FROM orders WHERE OrderId = "+OrderidSelected, null);
                                                        if(c.moveToFirst())
                                                        {

                                                            double finalbill = c.getDouble(c.getColumnIndex("FinalBillAmount"));
                                                            finalbill = Math.round(finalbill * (1- 0.01*Double.parseDouble(userInput.getText().toString())));
                                                            double paymentrecieved = c.getDouble(c.getColumnIndex("AdvancePaid"));
                                                            double paymentDue = finalbill - paymentrecieved;

                                                            String sql1 = "UPDATE orders SET FinalBillAmount = '"+finalbill+"' WHERE OrderId = "+OrderidSelected;
                                                            database.execSQL(sql1);

                                                            String sql2 = "UPDATE orders SET PaymentDue = '"+paymentDue+"' WHERE OrderId = "+OrderidSelected;
                                                            database.execSQL(sql2);

                                                        }
                                                        listViewAdapter.notifyDataSetChanged();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                        if(options[item].equals("Remove Discount"))
                        {

                            try {
                                double expressCharge;
                                double billValue;
                                Cursor c = database.rawQuery("SELECT * FROM orders WHERE OrderId = "+OrderidSelected, null);
                                if(c.moveToFirst())
                                {

                                    billValue = c.getDouble(c.getColumnIndex("BillAmount"));
                                    expressCharge = c.getDouble(c.getColumnIndex("ExpressCharge"));

                                    double finalbill = Math.round(billValue*(1.12 + expressCharge*0.01));
                                    double paymentrecieved = c.getDouble(c.getColumnIndex("AdvancePaid"));
                                    double paymentDue = finalbill - paymentrecieved;

                                    String sql1 = "UPDATE orders SET FinalBillAmount = '"+finalbill+"' WHERE OrderId = "+OrderidSelected;
                                    database.execSQL(sql1);

                                    String sql2 = "UPDATE orders SET PaymentDue = '"+paymentDue+"' WHERE OrderId = "+OrderidSelected;
                                    database.execSQL(sql2);
                                }

                                String sql = "UPDATE orders SET Discount = '"+0+"' WHERE OrderId = "+OrderidSelected;
                                database.execSQL(sql);

                                listViewAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


                return true ;
            }
        });

        /*--

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        listview = (ListView) findViewById(R.id.listview);
        listview.setNestedScrollingEnabled(true);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        listview.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(getApplicationContext(), DetailedRecord.class);
                intent.putExtra("position1", position);
                intent.putExtra("source1", "allRecords");
                startActivity(intent);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {


                final int itemSelected = i;
                c = database.rawQuery("SELECT * FROM abcd11", null);
                int j=0;
                c.moveToFirst();
                while(c!=null && j!=itemSelected)
                {
                    j++;
                    c.moveToNext();
                }
                final int idSelected = c.getInt(c.getColumnIndex("Id"));
                Log.i("idSelected result:", Integer.toString(idSelected));
                Log.i("i result:", Integer.toString(i));

                final String[] options = {"Change Status", "Delete Record"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AllRecords.this);
                String currentStatus = c.getString(c.getColumnIndex("OrderStatus"));
                builder.setTitle(currentStatus);
                //builder.setIcon(android.R.drawable.);
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), options[item], Toast.LENGTH_SHORT).show();
                        if(options[item].equals("Change Status"))
                        {
                            final String[] orderStatuses = {"Order Initiated", "Order Recieved","Order Confirmed","Order Form Ready", "Order With Fabricator(I)",
            "Order Ready For Trial", "Order Trial Done", "Order With Fabricator(II)","Order Ready for Delivery","Order Delivered"};

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(AllRecords.this);
                            builder1.setTitle("Update Status:");
                            builder1.setItems(orderStatuses, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item1) {
                                    String newStatus = orderStatuses[item1];
                                    String sql = "UPDATE abcd11 SET OrderStatus = '"+newStatus+"' WHERE id = "+idSelected;
                                    database.execSQL(sql);
                                    arrayAdapter.notifyDataSetChanged();
                                }
                            });
                            AlertDialog alert1 = builder1.create();
                            alert1.show();
                        }
                        if(options[item].equals("Delete Record"))
                        {
                            new AlertDialog.Builder(AllRecords.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Are you sure?")
                                    .setMessage("Do you want to delete this entry?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int idToDelete = idSelected;
                                            Log.i("idtodelete result:", Integer.toString(idToDelete));
                                            String sql = "DELETE FROM abcd11 WHERE id = "+idToDelete;
                                            database.execSQL(sql);
                                            names.remove(itemSelected);
                                            arrayAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


                return true ;
            }
        });

        try {
            Cursor c = database.rawQuery("SELECT * FROM abcd11", null);
            if(c.moveToFirst()) {
                names.clear();
                orderNumbers.clear();
                do {
                    names.add(c.getString(c.getColumnIndex("Name")));
                } while (c.moveToNext());

                arrayAdapter.notifyDataSetChanged();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        --*/

    }

    public void goBack(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
