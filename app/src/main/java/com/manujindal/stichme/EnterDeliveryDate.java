package com.manujindal.stichme;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EnterDeliveryDate extends AppCompatActivity {

    Intent intent;
    long OrderId;
    long CustomerId;
    String source;

    String source1;
    SQLiteDatabase database;

    DatePicker datePicker2;
    int year, month, date;
    int trialyear;
    int trialmonth;
    int trialday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_delivery_date);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        trialyear = intent.getIntExtra("trialyear1",-1);
        trialmonth = intent.getIntExtra("trialmonth1",-1);
        trialday= intent.getIntExtra("trialdate1",-1);

        Log.i("OrderId in TrialDate: ", Long.toString(OrderId));
        Log.i("CustomerId in TrialDte:", Long.toString(CustomerId));
        Log.i("Source in TrialDate: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);


        datePicker2 = (DatePicker) findViewById(R.id.datePicker2);

    }
    public void nextActFromMain(View view) {


        final Intent intent = new Intent(this, AddItem.class);

        int year = datePicker2.getYear();
        int month = datePicker2.getMonth();
        month++;
        int date = datePicker2.getDayOfMonth();

        Date trialdate = new Date();
        Date deliverydate = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            trialdate = sdf.parse(trialyear+"-"+trialmonth+"-"+trialday);
            deliverydate = sdf.parse(year+"-"+month+"-"+date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(deliverydate.compareTo(trialdate) > 0) {

            try {
                String sql = "UPDATE orders SET DeliveryDate = '" + year + "-" + month + "-" + date + "' WHERE OrderId = " + OrderId;
                database.execSQL(sql);

                Log.i("OrderId in Email: ", Long.toString(OrderId));
                Log.i("CustomerId in Email: ", Long.toString(CustomerId));
                Log.i("Source in Email: ", source);

                intent.putExtra("OrderId1", OrderId);
                intent.putExtra("source1", source);
                intent.putExtra("CustomerId1", CustomerId);

                intent.putExtra("source11", "finalcart");

                Calendar now = Calendar.getInstance();
                int yeartoday = now.get(Calendar.YEAR);
                int monthtoday = now.get(Calendar.MONTH) + 1;
                int daytoday = now.get(Calendar.DAY_OF_MONTH);

                int dateDifference = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), daytoday+"/"+monthtoday+"/"+yeartoday, date+"/"+month+"/"+year);
                if(dateDifference <= 7)
                {
                    {
                        final String[] expresscharges = {"0", "20", "30", "50"};
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(EnterDeliveryDate.this);
                        builder1.setTitle("Express Charge is Applicable:");
                        builder1.setItems(expresscharges, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item1) {
                                final String expresscharge = expresscharges[item1];
                                try
                                {
                                    String sql = "UPDATE orders SET ExpressCharge = '"+expresscharge+"' WHERE OrderId = "+OrderId;
                                    database.execSQL(sql);
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                            }
                        });
                        AlertDialog alert1 = builder1.create();
                        alert1.show();
                    }
                }
                else
                {
                    startActivity(intent);
                }
                

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Trial Date should be ahead of the order date, ie "+trialyear+"-"+trialmonth+"-"+trialday+"!", Toast.LENGTH_LONG).show();

        }

    }

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}