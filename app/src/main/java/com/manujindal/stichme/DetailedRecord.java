package com.manujindal.stichme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DetailedRecord extends AppCompatActivity {

    Intent intent;
    long OrderId;
    long CustomerId;
    SQLiteDatabase database;
    String source;

    ListView itemDetailsList;
    ListViewAdapter listViewAdapter;
    ArrayList<String> ItemLabelList = new ArrayList<String>();
    ArrayList<String> ItemOutputList = new ArrayList<String>();
    ArrayList<String> tableNameList = new ArrayList<String>();
    ArrayList<String> columnNameList = new ArrayList<String>();
    ArrayList<String> ItemIdTypeList = new ArrayList<String>();
    ArrayList<String> ItemIdList = new ArrayList<String>();


    Long phoneNo;
    String CustomerName;
    int orderNumber;
    int advanceAmount;
    double billAmount;
    double paymentPending;

    String message;
    String trialDate;
    String deliveryDate;


    public void addToList(String ItemLabel, String ItemOutput, String tableName, String columnName, String ItemIdType, String ItemId){
        ItemLabelList.add(ItemLabel);
        ItemOutputList.add(ItemOutput);
        tableNameList.add(tableName);
        columnNameList.add(columnName);
        ItemIdTypeList.add(ItemIdType);
        ItemIdList.add(ItemId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_record);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();
        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");

        Log.i("OrderId in DtldRcrd: ", Long.toString(OrderId));
        Log.i("CustomerId in DtldRcrd:", Long.toString(CustomerId));

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        message = "Order Details\n";

        try {

            Cursor c0 = database.rawQuery("SELECT * FROM orders WHERE OrderId = "+OrderId, null);
            if(c0.moveToFirst())
            {
                if(CustomerId == -1)
                {
                    CustomerId = c0.getInt(c0.getColumnIndex("CustomerId"));
                }

                ItemLabelList.clear();
                ItemOutputList.clear();
                tableNameList.clear();
                columnNameList.clear();
                ItemIdTypeList.clear();;
                ItemIdList.clear();

                addToList("Order Number", c0.getString(c0.getColumnIndex("OrderNumber")), "orders", "OrderNumber","OrderId" , Long.toString(OrderId));
                Log.i("Order Number:", c0.getString(c0.getColumnIndex("OrderNumber")));
                orderNumber = c0.getInt(c0.getColumnIndex("OrderNumber"));
                message = message.concat("Order Number: ");
                message = message.concat(c0.getString(c0.getColumnIndex("OrderNumber"))+"\n");

                addToList("Order Status", c0.getString(c0.getColumnIndex("OrderStatus")), "orders", "OrderStatus","OrderId" , Long.toString(OrderId));
                Log.i("OrderStatus:", c0.getString(c0.getColumnIndex("OrderStatus")));
                message = message.concat("Order Status: ");
                message = message.concat(c0.getString(c0.getColumnIndex("OrderStatus"))+"\n");

                addToList("Order Date", c0.getString(c0.getColumnIndex("OrderDate")), "orders", "OrderDate","OrderId" , Long.toString(OrderId));
                Log.i("OrderDate:", c0.getString(c0.getColumnIndex("OrderDate")));
                message = message.concat("Order Date: ");
                message = message.concat(c0.getString(c0.getColumnIndex("OrderDate"))+"\n");

                addToList("Trial Date", c0.getString(c0.getColumnIndex("TrialDate")), "orders", "TrialDate","OrderId" , Long.toString(OrderId));
                Log.i("TrialDate:", c0.getString(c0.getColumnIndex("TrialDate")));
                trialDate = c0.getString(c0.getColumnIndex("TrialDate"));
                message = message.concat("Trial Date: ");
                message = message.concat(c0.getString(c0.getColumnIndex("TrialDate"))+"\n");

                addToList("Delivery Date", c0.getString(c0.getColumnIndex("DeliveryDate")), "orders", "DeliveryDate","OrderId" , Long.toString(OrderId));
                Log.i("DeliveryDate:", c0.getString(c0.getColumnIndex("DeliveryDate")));
                message = message.concat("Delivery Date: ");
                message = message.concat(c0.getString(c0.getColumnIndex("DeliveryDate"))+"\n");
                deliveryDate = c0.getString(c0.getColumnIndex("DeliveryDate"));

                addToList("Final Bill Amount: ", c0.getString(c0.getColumnIndex("FinalBillAmount")), "", "","" , "");
                Log.i("FinalBillAmount:", c0.getString(c0.getColumnIndex("FinalBillAmount")));
                billAmount = c0.getDouble(c0.getColumnIndex("FinalBillAmount"));
                message = message.concat("Final Bill Amount: ");
                message = message.concat(c0.getString(c0.getColumnIndex("FinalBillAmount"))+"\n");

                addToList("Bill Amount", c0.getString(c0.getColumnIndex("BillAmount")), "", "","" , "");
                Log.i("BillAmount:", c0.getString(c0.getColumnIndex("BillAmount")));
                message = message.concat("Bill Amount: ");
                message = message.concat(c0.getString(c0.getColumnIndex("BillAmount"))+"\n");


                addToList("Payment Recieved", c0.getString(c0.getColumnIndex("AdvancePaid")), "orders", "AdvancePaid","OrderId" , Long.toString(OrderId));
                Log.i("AdvancePaid:", c0.getString(c0.getColumnIndex("AdvancePaid")));
                advanceAmount = c0.getInt(c0.getColumnIndex("AdvancePaid"));
                message = message.concat("Payment Recieved: ");
                message = message.concat(c0.getString(c0.getColumnIndex("AdvancePaid"))+"\n");

                addToList("Payment Due", c0.getString(c0.getColumnIndex("PaymentDue")), "", "","" , "");
                Log.i("PaymentDue:", c0.getString(c0.getColumnIndex("PaymentDue")));
                paymentPending = c0.getDouble(c0.getColumnIndex("PaymentDue"));
                message = message.concat("Payment Due: ");
                message = message.concat(c0.getString(c0.getColumnIndex("PaymentDue"))+"\n");

                addToList("Advance Recieved", c0.getString(c0.getColumnIndex("AdvanceRecieved")), "orders", "AdvanceRecieved","OrderId" , Long.toString(OrderId));
                Log.i("AdvanceRecieved:", c0.getString(c0.getColumnIndex("AdvanceRecieved")));
                message = message.concat("Advance Recieved: ");
                message = message.concat(c0.getString(c0.getColumnIndex("AdvanceRecieved"))+"\n");

                addToList("Advance Payment Method", c0.getString(c0.getColumnIndex("AdvancePaymentMethod")), "orders", "AdvancePaymentMethod","OrderId" , Long.toString(OrderId));
                Log.i("AdvancePaymentMethod:", c0.getString(c0.getColumnIndex("AdvancePaymentMethod")));
                message = message.concat("Advance Payment Method: ");
                message = message.concat(c0.getString(c0.getColumnIndex("AdvancePaymentMethod"))+"\n");

                addToList("Express Charge", c0.getString(c0.getColumnIndex("ExpressCharge")), "orders", "ExpressCharge","OrderId" , Long.toString(OrderId));
                Log.i("ExpressCharge:", c0.getString(c0.getColumnIndex("ExpressCharge")));
                message = message.concat("Express Charge: ");
                message = message.concat(c0.getString(c0.getColumnIndex("ExpressCharge"))+"\n");

                addToList("Discount", c0.getString(c0.getColumnIndex("Discount")), "", "","" , "");
                Log.i("Discount:", c0.getString(c0.getColumnIndex("Discount")));
                message = message.concat("Discount: ");
                message = message.concat(c0.getString(c0.getColumnIndex("Discount"))+"\n");
            }

            Cursor c = database.rawQuery("SELECT * FROM customers WHERE CustomerId = "+CustomerId, null);
            if(c.moveToFirst())
            {

                message = message.concat("\nCustomer Details:\n ");
                addToList("Name", c.getString(c.getColumnIndex("Name")), "customers", "Name","CustomerId" , Long.toString(CustomerId));
                Log.i("Name:", c.getString(c.getColumnIndex("Name")));
                CustomerName = c.getString(c.getColumnIndex("Name"));
                message = message.concat("Name: ");
                message = message.concat(c.getString(c.getColumnIndex("Name"))+"\n");

                addToList("DOB", c.getString(c.getColumnIndex("Dob")), "customers", "Dob","CustomerId" , Long.toString(CustomerId));
                Log.i("DOB:", c.getString(c.getColumnIndex("Dob")));
                message = message.concat("DOB: ");
                message = message.concat(c.getString(c.getColumnIndex("Dob"))+"\n");

                addToList("Email", c.getString(c.getColumnIndex("Email")), "customers", "Email","CustomerId" , Long.toString(CustomerId));
                Log.i("Email:", c.getString(c.getColumnIndex("Email")));
                message = message.concat("Email: ");
                message = message.concat(c.getString(c.getColumnIndex("Email"))+"\n");

                addToList("Phone", c.getString(c.getColumnIndex("Phone")), "customers", "Phone","CustomerId" , Long.toString(CustomerId));
                Log.i("Phone:", c.getString(c.getColumnIndex("Phone")));
                phoneNo = c.getLong(c.getColumnIndex("Phone"));
                message = message.concat("Phone: ");
                message = message.concat(c.getString(c.getColumnIndex("Phone"))+"\n");

                addToList("Address", c.getString(c.getColumnIndex("Address")), "customers", "Address","CustomerId" , Long.toString(CustomerId));
                Log.i("Address:", c.getString(c.getColumnIndex("Address")));
                message = message.concat("Address: ");
                message = message.concat(c.getString(c.getColumnIndex("Address"))+"\n");

                addToList("Occupation", c.getString(c.getColumnIndex("Occupation")), "customers", "Occupation","CustomerId" , Long.toString(CustomerId));
                Log.i("Occupation:", c.getString(c.getColumnIndex("Occupation")));
                message = message.concat("Occupation: ");
                message = message.concat(c.getString(c.getColumnIndex("Occupation"))+"\n");

                addToList("Wear Preference", c.getString(c.getColumnIndex("TypePreference")), "customers", "TypePreference","CustomerId" , Long.toString(CustomerId));
                Log.i("Wear Preference:", c.getString(c.getColumnIndex("TypePreference")));
                message = message.concat("Wear Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("TypePreference"))+"\n");

                addToList("Shirt Color\nPreference", c.getString(c.getColumnIndex("ShirtColorPreference")), "customers", "ShirtColorPreference","CustomerId" ,
                        Long.toString(CustomerId));
                Log.i("ShirtColorPreference:", c.getString(c.getColumnIndex("ShirtColorPreference")));
                message = message.concat("Shirt Color Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("ShirtColorPreference"))+"\n");

                addToList("Shirt Pattern\nPreference", c.getString(c.getColumnIndex("ShirtPatternPreference")), "customers", "ShirtPatternPreference","CustomerId" ,
                        Long.toString(CustomerId));
                Log.i("ShirtPatternPreference:", c.getString(c.getColumnIndex("ShirtPatternPreference")));
                message = message.concat("Shirt Pattern Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("ShirtPatternPreference"))+"\n");

                addToList("Suit Color\nPreference", c.getString(c.getColumnIndex("SuitColorPreference")), "customers", "SuitColorPreference","CustomerId" , Long.toString(CustomerId));
                Log.i("SuitColorPreference:", c.getString(c.getColumnIndex("SuitColorPreference")));
                message = message.concat("Suit Color Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("SuitColorPreference"))+"\n");

                addToList("Suit Pattern\nPreference", c.getString(c.getColumnIndex("SuitPatternPreference")), "customers", "SuitPatternPreference","CustomerId" ,
                        Long.toString(CustomerId));
                Log.i("SuitPatternPreference:", c.getString(c.getColumnIndex("SuitPatternPreference")));
                message = message.concat("Suit Pattern Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("SuitPatternPreference"))+"\n");

                addToList("Trouser Color\nPreference", c.getString(c.getColumnIndex("TrouserColorPreference")), "customers", "TrouserColorPreference","CustomerId" ,
                        Long.toString(CustomerId));
                Log.i("TrouserColorPreference:", c.getString(c.getColumnIndex("TrouserColorPreference")));
                message = message.concat("Trouser Color Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("TrouserColorPreference"))+"\n");

                addToList("Trouser Pattern\nPreference", c.getString(c.getColumnIndex("TrouserPatternPreference")), "customers", "TrouserPatternPreference","CustomerId" ,
                        Long.toString(CustomerId));
                Log.i("TrouserPatternPref:", c.getString(c.getColumnIndex("TrouserPatternPreference")));
                message = message.concat("Trouser Pattern Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("TrouserPatternPreference"))+"\n");

            }

            Cursor c1 = database.rawQuery("SELECT * FROM shirts where OrderId ="+OrderId, null);
            Log.i("check", "1");
            Cursor cA1 = database.rawQuery("SELECT * FROM shirts, fabrics WHERE shirts.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            Log.i("check", "2");
            if(c1.moveToFirst() && cA1.moveToFirst())
            {
                int i=1;
                do {
                    long ShirtId = c1.getLong(c1.getColumnIndex("ShirtId"));

                    addToList("\nShirt"+i+"\n", "", "", "","" ,"");
                    message = message.concat("\nShirt"+i+" Details:\n");
                    i++;

                    addToList("Fabric Code", cA1.getString(cA1.getColumnIndex("FabricCode")), "", "","" , "");
                    Log.i("Shirt Fabric Code:", cA1.getString(cA1.getColumnIndex("FabricCode")));
                    message = message.concat("Fabric Code: ");
                    message = message.concat(cA1.getString(cA1.getColumnIndex("FabricCode"))+"\n");

                    addToList("Fabric Price", cA1.getString(cA1.getColumnIndex("FabricPrice")), "", "","" , "");
                    Log.i("Shirt Fabric Price:", cA1.getString(cA1.getColumnIndex("FabricPrice")));
                    message = message.concat("Fabric Price: ");
                    message = message.concat(cA1.getString(cA1.getColumnIndex("FabricPrice"))+"\n");

                    addToList("Sleeve Type", c1.getString(c1.getColumnIndex("Shirt_SleeveType")), "shirts", "Shirt_SleeveType","ShirtId" , Long.toString(ShirtId));
                    Log.i("Shirt_SleeveType:", c1.getString(c1.getColumnIndex("Shirt_SleeveType")));
                    message = message.concat("Sleeve Type: ");
                    message = message.concat(c1.getString(c1.getColumnIndex("Shirt_SleeveType"))+"\n");

                    addToList("Pocket Type", c1.getString(c1.getColumnIndex("Shirt_PocketType")), "shirts", "Shirt_PocketType","ShirtId" , Long.toString(ShirtId));
                    Log.i("Shirt_PocketType:", c1.getString(c1.getColumnIndex("Shirt_PocketType")));
                    message = message.concat("Pocket Type: ");
                    message = message.concat(c1.getString(c1.getColumnIndex("Shirt_PocketType"))+"\n");

                    addToList("Cuff Type", c1.getString(c1.getColumnIndex("Shirt_CuffType")), "shirts", "Shirt_CuffType","ShirtId" , Long.toString(ShirtId));
                    Log.i("Shirt_CuffType:", c1.getString(c1.getColumnIndex("Shirt_CuffType")));
                    message = message.concat("Cuff Type: ");
                    message = message.concat(c1.getString(c1.getColumnIndex("Shirt_CuffType"))+"\n");

                    addToList("Fit Type", c1.getString(c1.getColumnIndex("Shirt_FitType")), "shirts", "Shirt_FitType","ShirtId" , Long.toString(ShirtId));
                    Log.i("Shirt_FitType:", c1.getString(c1.getColumnIndex("Shirt_FitType")));
                    message = message.concat("Fit Type: ");
                    message = message.concat(c1.getString(c1.getColumnIndex("Shirt_FitType"))+"\n");

                    addToList("Collar Type", c1.getString(c1.getColumnIndex("Shirt_CollarType")), "shirts", "Shirt_CollarType","ShirtId" , Long.toString(ShirtId));
                    Log.i("Shirt_CollarType:", c1.getString(c1.getColumnIndex("Shirt_CollarType")));
                    message = message.concat("Collar Type: ");
                    message = message.concat(c1.getString(c1.getColumnIndex("Shirt_CollarType"))+"\n");

                    addToList("Placket Type", c1.getString(c1.getColumnIndex("Shirt_PlacketType")), "shirts", "Shirt_PlacketType","ShirtId" , Long.toString(ShirtId));
                    Log.i("Shirt_PlacketType:", c1.getString(c1.getColumnIndex("Shirt_PlacketType")));
                    message = message.concat("Placket Type: ");
                    message = message.concat(c1.getString(c1.getColumnIndex("Shirt_PlacketType"))+"\n");

                } while (c1.moveToNext() && cA1.moveToNext());
            }

            Cursor c2 = database.rawQuery("SELECT * FROM suits WHERE OrderId = "+OrderId, null);
            Cursor cA2 = database.rawQuery("SELECT * FROM suits, fabrics WHERE suits.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c2.moveToFirst() && cA2.moveToFirst())
            {
                int i=1;
                do {

                    long SuitId = c2.getLong(c2.getColumnIndex("SuitId"));

                    addToList("\nSuit"+i+"\n", "", "", "","" ,"");
                    message = message.concat("\nSuit"+i+" Details:\n");
                    i++;

                    addToList("Fabric Code", cA2.getString(cA2.getColumnIndex("FabricCode")), "", "","" , "");
                    Log.i("Suit Fabric Code:", cA2.getString(cA2.getColumnIndex("FabricCode")));
                    message = message.concat("Fabric Code: ");
                    message = message.concat(cA2.getString(cA2.getColumnIndex("FabricCode"))+"\n");

                    addToList("Fabric Price", cA2.getString(cA2.getColumnIndex("FabricPrice")), "", "","" , "");
                    Log.i("Suit Fabric Price:", cA2.getString(cA2.getColumnIndex("FabricPrice")));
                    message = message.concat("Fabric Price: ");
                    message = message.concat(cA2.getString(cA2.getColumnIndex("FabricPrice"))+"\n");

                    addToList("Fit Type",           c2.getString(c2.getColumnIndex("Suit_FitType")),          "suits", "Suit_FitType",          "SuitId" , Long.toString(SuitId));
                    message = message.concat("Fit Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_FitType"))+"\n");

                    addToList("Jacket Type",        c2.getString(c2.getColumnIndex("Suit_JacketType")),       "suits", "Suit_JacketType",       "SuitId" , Long.toString(SuitId));
                    message = message.concat("Jacket Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_JacketType"))+"\n");

                    addToList("Lapel Type",         c2.getString(c2.getColumnIndex("Suit_LapelType")),        "suits", "Suit_LapelType",        "SuitId" , Long.toString(SuitId));
                    message = message.concat("Lapel Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_LapelType"))+"\n");

                    addToList("Bottom Cut Type",    c2.getString(c2.getColumnIndex("Suit_BottomCutType")),    "suits", "Suit_BottomCutType",    "SuitId" , Long.toString(SuitId));
                    message = message.concat("Bottom Cut Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_BottomCutType"))+"\n");

                    addToList("Vest Type",          c2.getString(c2.getColumnIndex("Suit_VestType")),         "suits", "Suit_VestType",         "SuitId" , Long.toString(SuitId));
                    message = message.concat("Vest Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_VestType"))+"\n");

                    addToList("Pocket Type",        c2.getString(c2.getColumnIndex("Suit_PocketType")),       "suits", "Suit_PocketType",       "SuitId" , Long.toString(SuitId));
                    message = message.concat("Pocket Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_PocketType"))+"\n");

                    addToList("Sleeve Button Type", c2.getString(c2.getColumnIndex("Suit_SleeveButtonType")), "suits", "Suit_SleeveButtonType", "SuitId" , Long.toString(SuitId));
                    message = message.concat("Sleeve Button Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_SleeveButtonType"))+"\n");

                    addToList("Vent Type",          c2.getString(c2.getColumnIndex("Suit_VentType")),         "suits", "Suit_VentType",         "SuitId" , Long.toString(SuitId));
                    message = message.concat("Vent Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_VentType"))+"\n");

                    if(c2.getString(c2.getColumnIndex("Suit_VestType")).equals("Yes")) {
                        addToList("Type Of Vest Type", c2.getString(c2.getColumnIndex("Suit_TypeOfVest")), "suits", "Suit_TypeOfVest","SuitId" , Long.toString(SuitId));
                        message = message.concat("Type Of Vest Type: ");
                        message = message.concat(c2.getString(c2.getColumnIndex("Suit_TypeOfVest"))+"\n");
                    }

                } while (c2.moveToNext() && cA2.moveToNext());
            }

            Cursor c3 = database.rawQuery("SELECT * FROM trousers WHERE OrderId = "+OrderId, null);
            Cursor cA3 = database.rawQuery("SELECT * FROM trousers, fabrics WHERE trousers.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c3.moveToFirst() && cA3.moveToFirst())
            {
                int i=1;
                do {
                    long TrouserId = c3.getLong(c3.getColumnIndex("TrouserId"));

                    addToList("\nTrouser"+i+"\n", "", "", "","" ,"");
                    message = message.concat("\nTrouser"+i+" Details:\n");
                    i++;

                    addToList("Fabric Code", cA3.getString(cA3.getColumnIndex("FabricCode")), "", "","" , "");
                    Log.i("trouser Fabric Code:", cA3.getString(cA3.getColumnIndex("FabricCode")));
                    message = message.concat("Fabric Code: ");
                    message = message.concat(cA3.getString(cA3.getColumnIndex("FabricCode"))+"\n");

                    addToList("Fabric Price", cA3.getString(cA3.getColumnIndex("FabricPrice")), "", "","" , "");
                    Log.i("trouser Fabric Price:", cA3.getString(cA3.getColumnIndex("FabricPrice")));
                    message = message.concat("Fabric Price: ");
                    message = message.concat(cA3.getString(cA3.getColumnIndex("FabricPrice"))+"\n");

                    addToList("Fit Type",          c3.getString(c3.getColumnIndex("Trouser_FitType")),             "trousers", "Trouser_FitType",              "TrouserId" , Long.toString(TrouserId));
                    message = message.concat("Fit Type: ");
                    message = message.concat(c3.getString(c3.getColumnIndex("Trouser_FitType"))+"\n");

                    addToList("Pant Pleats Type",  c3.getString(c3.getColumnIndex("Trouser_PantPleatsType")),      "trousers", "Trouser_PantPleatsType",       "TrouserId" , Long.toString(TrouserId));
                    message = message.concat("Pant Pleats Type: ");
                    message = message.concat(c3.getString(c3.getColumnIndex("Trouser_PantPleatsType"))+"\n");

                    addToList("Pant Pocket Type",  c3.getString(c3.getColumnIndex("Trouser_PantPocketType")),      "trousers", "Trouser_PantPocketType",       "TrouserId" , Long.toString(TrouserId));
                    message = message.concat("Pant Pocket Type: ");
                    message = message.concat(c3.getString(c3.getColumnIndex("Trouser_PantPocketType"))+"\n");

                    addToList("Back Pocket Style", c3.getString(c3.getColumnIndex("Trouser_BackPocketStyleType")), "trousers", "Trouser_BackPocketStyleType",  "TrouserId" , Long.toString(TrouserId));
                    message = message.concat("Back Pocket Style: ");
                    message = message.concat(c3.getString(c3.getColumnIndex("Trouser_BackPocketStyleType"))+"\n");

                    addToList("Back Pocket Type",  c3.getString(c3.getColumnIndex("Trouser_BackPocketType")),      "trousers", "Trouser_BackPocketType",       "TrouserId" , Long.toString(TrouserId));
                    message = message.concat("Back Pocket Type: ");
                    message = message.concat(c3.getString(c3.getColumnIndex("Trouser_BackPocketType"))+"\n");

                    addToList("Belt Loops Type",   c3.getString(c3.getColumnIndex("Trouser_BeltLoopsType")),       "trousers", "Trouser_BeltLoopsType",        "TrouserId" , Long.toString(TrouserId));
                    message = message.concat("Belt Loops Type: ");
                    message = message.concat(c3.getString(c3.getColumnIndex("Trouser_BeltLoopsType"))+"\n");

                } while (c3.moveToNext() && cA3.moveToNext());
            }

            Cursor c5 = database.rawQuery("SELECT * FROM others WHERE OrderId = "+OrderId, null);
            Cursor cA5 = database.rawQuery("SELECT * FROM others, fabrics WHERE others.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c5.moveToFirst() && cA5.moveToFirst())
            {
                int i=1;
                do {

                    long OtherId = c5.getLong(c5.getColumnIndex("OtherId"));

                    addToList("\nOther"+i+"\n", "", "", "","" ,"");
                    message = message.concat("\nOther"+i+" Details:\n");
                    i++;

                    addToList("Type", cA5.getString(cA5.getColumnIndex("FabricCode")), "", "","" , "");
                    Log.i("Other Fabric Code:", cA5.getString(cA5.getColumnIndex("FabricCode")));
                    message = message.concat("Type: ");
                    message = message.concat(cA5.getString(cA5.getColumnIndex("FabricCode"))+"\n");

                    addToList("Fabric Price", cA5.getString(cA5.getColumnIndex("FabricPrice")), "", "","" , "");
                    Log.i("trouser Fabric Price:", cA5.getString(cA5.getColumnIndex("FabricPrice")));
                    message = message.concat("Fabric Price: ");
                    message = message.concat(cA5.getString(cA5.getColumnIndex("FabricPrice"))+"\n");

                    addToList("Description", c5.getString(c5.getColumnIndex("Description")), "others", "Description", "OtherId" , Long.toString(OtherId));
                    message = message.concat("Description: ");
                    message = message.concat(c5.getString(c5.getColumnIndex("Description"))+"\n");

                } while (c5.moveToNext() && cA5.moveToNext());
            }


            Cursor c4 = database.rawQuery("SELECT * FROM measurements WHERE CustomerId= "+CustomerId, null);
            if(c4.moveToFirst())
            {
                long MeasurementsId = c4.getLong(c4.getColumnIndex("MeasurementsId"));

                addToList("\nMeasurement\nUpper Garment\n" , "", "", "", "" , "");
                addToList("Upper Chest" ,        c4.getString(c4.getColumnIndex("Measurement1")),  "measurements", "Measurement1",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Chest" ,        c4.getString(c4.getColumnIndex("Measurement2")),  "measurements", "Measurement2",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Lower Chest" ,        c4.getString(c4.getColumnIndex("Measurement3")),  "measurements", "Measurement3",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Length Suit" ,        c4.getString(c4.getColumnIndex("Measurement4")),  "measurements", "Measurement4",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Length Shirt",        c4.getString(c4.getColumnIndex("Measurement5")),  "measurements", "Measurement5",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Stomach" ,            c4.getString(c4.getColumnIndex("Measurement6")),  "measurements", "Measurement6",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Hip" ,          c4.getString(c4.getColumnIndex("Measurement7")),  "measurements", "Measurement7",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Across Front",        c4.getString(c4.getColumnIndex("Measurement8")),  "measurements", "Measurement8",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Across Back" ,        c4.getString(c4.getColumnIndex("Measurement9")),  "measurements", "Measurement9",  "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Across Shoulder" ,    c4.getString(c4.getColumnIndex("Measurement10")), "measurements", "Measurement10", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Side Shoulder" ,      c4.getString(c4.getColumnIndex("Measurement11")), "measurements", "Measurement11", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Neck (Collar)", c4.getString(c4.getColumnIndex("Measurement12")), "measurements", "Measurement12", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Lapel" ,              c4.getString(c4.getColumnIndex("Measurement13")), "measurements", "Measurement13", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Sleeve Length" ,      c4.getString(c4.getColumnIndex("Measurement14")), "measurements", "Measurement14", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Armhole",             c4.getString(c4.getColumnIndex("Measurement15")), "measurements", "Measurement15", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Biceps" ,             c4.getString(c4.getColumnIndex("Measurement16")), "measurements", "Measurement16", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Elbow" ,              c4.getString(c4.getColumnIndex("Measurement17")), "measurements", "Measurement17", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Sleeve Bottom",       c4.getString(c4.getColumnIndex("Measurement18")), "measurements", "Measurement18", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Cuff Length" ,        c4.getString(c4.getColumnIndex("Measurement19")), "measurements", "Measurement19", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Cuff Width" ,         c4.getString(c4.getColumnIndex("Measurement20")), "measurements", "Measurement20", "MeasurementsId" , Long.toString(MeasurementsId));

                addToList("\nMeasurement\nLower Garment\n" , "", "", "", "" , "");
                addToList("Length" ,       c4.getString(c4.getColumnIndex("Measurement21")), "measurements", "Measurement21", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Waist",   c4.getString(c4.getColumnIndex("Measurement22")), "measurements", "Measurement22", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Hip" ,    c4.getString(c4.getColumnIndex("Measurement23")), "measurements", "Measurement23", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Thigh" ,  c4.getString(c4.getColumnIndex("Measurement24")), "measurements", "Measurement24", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Round Knee",    c4.getString(c4.getColumnIndex("Measurement25")), "measurements", "Measurement25", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Calf" ,         c4.getString(c4.getColumnIndex("Measurement26")), "measurements", "Measurement26", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Crotch" ,       c4.getString(c4.getColumnIndex("Measurement27")), "measurements", "Measurement27", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Fog",           c4.getString(c4.getColumnIndex("Measurement28")), "measurements", "Measurement28", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("In-Seam" ,      c4.getString(c4.getColumnIndex("Measurement29")), "measurements", "Measurement29", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Bottom (Mori)", c4.getString(c4.getColumnIndex("Measurement30")), "measurements", "Measurement30", "MeasurementsId" , Long.toString(MeasurementsId));
                addToList("Belt Width" ,   c4.getString(c4.getColumnIndex("Measurement31")), "measurements", "Measurement31", "MeasurementsId" , Long.toString(MeasurementsId));

                message = message.concat("\nMeasurement:\nUpper Garment:\n");

                message = message.concat("Upper Chest: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement1"))+"\n");

                message = message.concat("Round Chest: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement2"))+"\n");

                message = message.concat("Lower Chest: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement3"))+"\n");

                message = message.concat("Length Suit: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement4"))+"\n");

                message = message.concat("Length Shirt: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement5"))+"\n");

                message = message.concat("Stomach: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement6"))+"\n");

                message = message.concat("Round Hip: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement7"))+"\n");

                message = message.concat("Across Front: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement8"))+"\n");

                message = message.concat("Across Back: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement9"))+"\n");

                message = message.concat("Across Shoulder: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement10"))+"\n");

                message = message.concat("Side Shoulder: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement11"))+"\n");

                message = message.concat("Round Neck (Collar): ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement12"))+"\n");

                message = message.concat("Lapel: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement13"))+"\n");

                message = message.concat("Sleeve Length: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement14"))+"\n");

                message = message.concat("Armhole: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement15"))+"\n");

                message = message.concat("Biceps: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement16"))+"\n");

                message = message.concat("Elbow: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement17"))+"\n");

                message = message.concat("Sleeve Bottom: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement18"))+"\n");

                message = message.concat("Cuff Length: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement19"))+"\n");

                message = message.concat("Cuff Width: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement20"))+"\n");

                message = message.concat("\nMeasurement:\nLower Garment:\n");

                message = message.concat("Length: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement21"))+"\n");

                message = message.concat("Round Waist: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement22"))+"\n");

                message = message.concat("Round Hip: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement23"))+"\n");

                message = message.concat("Round Thigh: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement24"))+"\n");

                message = message.concat("Round Knee: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement25"))+"\n");

                message = message.concat("Calf: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement26"))+"\n");

                message = message.concat("Crotch: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement27"))+"\n");

                message = message.concat("Fog: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement28"))+"\n");

                message = message.concat("In-Seam: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement29"))+"\n");

                message = message.concat("Bottom (Mori): ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement30"))+"\n");

                message = message.concat("Belt Width: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement31"))+"\n");
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



        //populate the arrays

        /*

        try {

            Cursor c0 = database.rawQuery("SELECT * FROM orders WHERE OrderId = "+OrderId, null);
            if(c0.moveToFirst())
            {
                if(CustomerId == -1)
                {
                    CustomerId = c0.getInt(c0.getColumnIndex("CustomerId"));
                }

                ItemLabelList.clear();
                ItemOutputList.clear();
                tableNameList.clear();
                columnNameList.clear();
                ItemIdTypeList.clear();;
                ItemIdList.clear();

                ItemLabelList.add("Order Number");
                ItemOutputList.add(c0.getString(c0.getColumnIndex("OrderNumber")));
                Log.i("Order Number:", c0.getString(c0.getColumnIndex("OrderNumber")));
                tableNameList.add("orders");
                columnNameList.add("OrderNumber");
                orderNumber = c0.getInt(c0.getColumnIndex("OrderNumber"));
                ItemIdTypeList.add("OrderId");
                ItemIdList.add(Integer.toString(OrderId));
                message = message.concat("Order Number: ");
                message = message.concat(c0.getString(c0.getColumnIndex("OrderNumber"))+"\n");

                ItemLabelList.add("Order Status");
                ItemOutputList.add(c0.getString(c0.getColumnIndex("OrderStatus")));
                Log.i("OrderStatus:", c0.getString(c0.getColumnIndex("OrderStatus")));
                tableNameList.add("orders");
                columnNameList.add("OrderStatus");
                ItemIdTypeList.add("OrderId");
                ItemIdList.add(Integer.toString(OrderId));
                message = message.concat("Order Status: ");
                message = message.concat(c0.getString(c0.getColumnIndex("OrderStatus"))+"\n");

                ItemLabelList.add("Order Date");
                ItemOutputList.add(c0.getString(c0.getColumnIndex("OrderDate")));
                Log.i("OrderDate:", c0.getString(c0.getColumnIndex("OrderDate")));
                tableNameList.add("orders");
                columnNameList.add("OrderDate");
                ItemIdTypeList.add("OrderId");
                ItemIdList.add(Integer.toString(OrderId));
                message = message.concat("Order Date: ");
                message = message.concat(c0.getString(c0.getColumnIndex("OrderDate"))+"\n");

                ItemLabelList.add("Trial Date");
                ItemOutputList.add(c0.getString(c0.getColumnIndex("TrialDate")));
                Log.i("TrialDate:", c0.getString(c0.getColumnIndex("TrialDate")));
                trialDate = c0.getString(c0.getColumnIndex("TrialDate"));
                tableNameList.add("orders");
                columnNameList.add("TrialDate");
                ItemIdTypeList.add("OrderId");
                ItemIdList.add(Integer.toString(OrderId));
                message = message.concat("Trial Date: ");
                message = message.concat(c0.getString(c0.getColumnIndex("TrialDate"))+"\n");

                ItemLabelList.add("Delivery Date");
                ItemOutputList.add(c0.getString(c0.getColumnIndex("DeliveryDate")));
                Log.i("DeliveryDate:", c0.getString(c0.getColumnIndex("DeliveryDate")));
                deliveryDate = c0.getString(c0.getColumnIndex("DeliveryDate"));
                tableNameList.add("orders");
                columnNameList.add("DeliveryDate");
                ItemIdTypeList.add("OrderId");
                ItemIdList.add(Integer.toString(OrderId));
                message = message.concat("Delivery Date: ");
                message = message.concat(c0.getString(c0.getColumnIndex("DeliveryDate"))+"\n");

                ItemLabelList.add("Final Bill Amount");
                ItemOutputList.add(c0.getString(c0.getColumnIndex("FinalBillAmount")));
                Log.i("FinalBillAmount:", c0.getString(c0.getColumnIndex("FinalBillAmount")));
                tableNameList.add("");
                columnNameList.add("");
                ItemIdTypeList.add("");
                ItemIdList.add("");
                billAmount = c0.getDouble(c0.getColumnIndex("FinalBillAmount"));
                message = message.concat("Final Bill Amount: ");
                message = message.concat(c0.getString(c0.getColumnIndex("FinalBillAmount"))+"\n");

                ItemLabelList.add("Bill Amount");
                ItemOutputList.add(c0.getString(c0.getColumnIndex("BillAmount")));
                Log.i("BillAmount:", c0.getString(c0.getColumnIndex("BillAmount")));
                tableNameList.add("");
                columnNameList.add("");
                ItemIdTypeList.add("");
                ItemIdList.add("");
                message = message.concat("Bill Amount: ");
                message = message.concat(c0.getString(c0.getColumnIndex("BillAmount"))+"\n");

                ItemLabelList.add("Payment Recieved");
                ItemOutputList.add(c0.getString(c0.getColumnIndex("AdvancePaid")));
                Log.i("AdvancePaid:", c0.getString(c0.getColumnIndex("AdvancePaid")));
                tableNameList.add("orders");
                columnNameList.add("AdvancePaid");
                ItemIdTypeList.add("OrderId");
                advanceAmount = c0.getInt(c0.getColumnIndex("AdvancePaid"));
                ItemIdList.add(Integer.toString(OrderId));
                message = message.concat("Payment Recieved: ");
                message = message.concat(c0.getString(c0.getColumnIndex("AdvancePaid"))+"\n");

                ItemLabelList.add("Payment Due");
                ItemOutputList.add(c0.getString(c0.getColumnIndex("PaymentDue")));
                Log.i("PaymentDue:", c0.getString(c0.getColumnIndex("PaymentDue")));
                tableNameList.add("");
                columnNameList.add("");
                ItemIdTypeList.add("");
                ItemIdList.add("");
                paymentPending = c0.getDouble(c0.getColumnIndex("PaymentDue"));
                message = message.concat("Payment Due: ");
                message = message.concat(c0.getString(c0.getColumnIndex("PaymentDue"))+"\n");

                ItemLabelList.add("Advance Recieved");
                ItemOutputList.add(c0.getString(c0.getColumnIndex("AdvanceRecieved")));
                Log.i("AdvanceRecieved:", c0.getString(c0.getColumnIndex("AdvanceRecieved")));
                tableNameList.add("orders");
                columnNameList.add("AdvanceRecieved");
                ItemIdTypeList.add("OrderId");
                ItemIdList.add(Integer.toString(OrderId));
                message = message.concat("Advance Recieved: ");
                message = message.concat(c0.getString(c0.getColumnIndex("AdvanceRecieved"))+"\n");

                ItemLabelList.add("Advance Payment Method");
                ItemOutputList.add(c0.getString(c0.getColumnIndex("AdvancePaymentMethod")));
                Log.i("AdvancePaymentMethod:", c0.getString(c0.getColumnIndex("AdvancePaymentMethod")));
                tableNameList.add("orders");
                columnNameList.add("AdvancePaymentMethod");
                ItemIdTypeList.add("OrderId");
                ItemIdList.add(Integer.toString(OrderId));
                message = message.concat("Advance Payment Method: ");
                message = message.concat(c0.getString(c0.getColumnIndex("AdvancePaymentMethod"))+"\n");

                ItemLabelList.add("Express Charge");
                ItemOutputList.add(c0.getString(c0.getColumnIndex("ExpressCharge")));
                Log.i("ExpressCharge:", c0.getString(c0.getColumnIndex("ExpressCharge")));
                tableNameList.add("orders");
                columnNameList.add("ExpressCharge");
                ItemIdTypeList.add("OrderId");
                ItemIdList.add(Integer.toString(OrderId));
                message = message.concat("Express Charge: ");
                message = message.concat(c0.getString(c0.getColumnIndex("ExpressCharge"))+"\n");

                ItemLabelList.add("Discount");
                ItemOutputList.add(c0.getString(c0.getColumnIndex("Discount")));
                Log.i("Discount:", c0.getString(c0.getColumnIndex("Discount")));
                tableNameList.add("");
                columnNameList.add("");
                ItemIdTypeList.add("");
                ItemIdList.add("");
                message = message.concat("Discount: ");
                message = message.concat(c0.getString(c0.getColumnIndex("Discount"))+"\n");
            }

            Cursor c = database.rawQuery("SELECT * FROM customers WHERE CustomerId = "+CustomerId, null);
            if(c.moveToFirst())
            {

                message = message.concat("\nCustomer Details:\n ");
                ItemLabelList.add("Name");
                ItemOutputList.add(c.getString(c.getColumnIndex("Name")));
                Log.i("Name:", c.getString(c.getColumnIndex("Name")));
                tableNameList.add("customers");
                columnNameList.add("Name");
                CustomerName = c.getString(c.getColumnIndex("Name"));
                ItemIdTypeList.add("CustomerId");
                ItemIdList.add(Integer.toString(CustomerId));
                message = message.concat("Name: ");
                message = message.concat(c.getString(c.getColumnIndex("Name"))+"\n");

                ItemLabelList.add("DOB");
                ItemOutputList.add(c.getString(c.getColumnIndex("Dob")));
                Log.i("DOB:", c.getString(c.getColumnIndex("Dob")));
                tableNameList.add("customers");
                columnNameList.add("Dob");
                ItemIdTypeList.add("CustomerId");
                ItemIdList.add(Integer.toString(CustomerId));
                message = message.concat("DOB: ");
                message = message.concat(c.getString(c.getColumnIndex("Dob"))+"\n");

                ItemLabelList.add("Email");
                ItemOutputList.add(c.getString(c.getColumnIndex("Email")));
                Log.i("Email:", c.getString(c.getColumnIndex("Email")));
                tableNameList.add("customers");
                columnNameList.add("Email");
                ItemIdTypeList.add("CustomerId");
                ItemIdList.add(Integer.toString(CustomerId));
                message = message.concat("Email: ");
                message = message.concat(c.getString(c.getColumnIndex("Email"))+"\n");


                ItemLabelList.add("Phone");
                ItemOutputList.add(c.getString(c.getColumnIndex("Phone")));
                Log.i("Phone:", c.getString(c.getColumnIndex("Phone")));
                tableNameList.add("customers");
                columnNameList.add("Phone");
                ItemIdTypeList.add("CustomerId");
                phoneNo = c.getLong(c.getColumnIndex("Phone"));
                ItemIdList.add(Integer.toString(CustomerId));
                message = message.concat("Phone: ");
                message = message.concat(c.getString(c.getColumnIndex("Phone"))+"\n");

                ItemLabelList.add("Occupation");
                ItemOutputList.add(c.getString(c.getColumnIndex("Occupation")));
                Log.i("Occupation:", c.getString(c.getColumnIndex("Occupation")));
                tableNameList.add("customers");
                columnNameList.add("Occupation");
                ItemIdTypeList.add("CustomerId");
                ItemIdList.add(Integer.toString(CustomerId));
                message = message.concat("Occupation: ");
                message = message.concat(c.getString(c.getColumnIndex("Occupation"))+"\n");

                ItemLabelList.add("Wear Preference");
                ItemOutputList.add(c.getString(c.getColumnIndex("TypePreference")));
                Log.i("Wear Preference:", c.getString(c.getColumnIndex("TypePreference")));
                tableNameList.add("customers");
                columnNameList.add("TypePreference");
                ItemIdTypeList.add("CustomerId");
                ItemIdList.add(Integer.toString(CustomerId));
                message = message.concat("Wear Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("TypePreference"))+"\n");
                
                ItemLabelList.add("Shirt Color\nPreference");
                ItemOutputList.add(c.getString(c.getColumnIndex("ShirtColorPreference")));
                Log.i("ShirtColorPreference:", c.getString(c.getColumnIndex("ShirtColorPreference")));
                tableNameList.add("customers");
                columnNameList.add("ShirtColorPreference");
                ItemIdTypeList.add("CustomerId");
                ItemIdList.add(Integer.toString(CustomerId));
                message = message.concat("Shirt Color Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("ShirtColorPreference"))+"\n");

                ItemLabelList.add("Shirt Pattern\nPreference");
                ItemOutputList.add(c.getString(c.getColumnIndex("ShirtPatternPreference")));
                Log.i("ShirtPatternPreference:", c.getString(c.getColumnIndex("ShirtPatternPreference")));
                tableNameList.add("customers");
                columnNameList.add("ShirtPatternPreference");
                ItemIdTypeList.add("CustomerId");
                ItemIdList.add(Integer.toString(CustomerId));
                message = message.concat("Shirt Pattern Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("ShirtPatternPreference"))+"\n");

                ItemLabelList.add("Suit Color\nPreference");
                ItemOutputList.add(c.getString(c.getColumnIndex("SuitColorPreference")));
                Log.i("SuitColorPreference:", c.getString(c.getColumnIndex("SuitColorPreference")));
                tableNameList.add("customers");
                columnNameList.add("SuitColorPreference");
                ItemIdTypeList.add("CustomerId");
                ItemIdList.add(Integer.toString(CustomerId));
                message = message.concat("Suit Color Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("SuitColorPreference"))+"\n");

                ItemLabelList.add("Suit Pattern\nPreference");
                ItemOutputList.add(c.getString(c.getColumnIndex("SuitPatternPreference")));
                Log.i("SuitPatternPreference:", c.getString(c.getColumnIndex("SuitPatternPreference")));
                tableNameList.add("customers");
                columnNameList.add("SuitPatternPreference");
                ItemIdTypeList.add("CustomerId");
                ItemIdList.add(Integer.toString(CustomerId));
                message = message.concat("Suit Pattern Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("SuitPatternPreference"))+"\n");

                ItemLabelList.add("Trouser Color\nPreference");
                ItemOutputList.add(c.getString(c.getColumnIndex("TrouserColorPreference")));
                Log.i("TrouserColorPreference:", c.getString(c.getColumnIndex("TrouserColorPreference")));
                tableNameList.add("customers");
                columnNameList.add("TrouserColorPreference");
                ItemIdTypeList.add("CustomerId");
                ItemIdList.add(Integer.toString(CustomerId));
                message = message.concat("Trouser Color Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("TrouserColorPreference"))+"\n");

                ItemLabelList.add("Trouser Pattern\nPreference");
                ItemOutputList.add(c.getString(c.getColumnIndex("TrouserPatternPreference")));
                Log.i("TrouserPatternPref:", c.getString(c.getColumnIndex("TrouserPatternPreference")));
                tableNameList.add("customers");
                columnNameList.add("TrouserPatternPreference");
                ItemIdTypeList.add("CustomerId");
                ItemIdList.add(Integer.toString(CustomerId));
                message = message.concat("Trouser Pattern Preference: ");
                message = message.concat(c.getString(c.getColumnIndex("TrouserPatternPreference"))+"\n");





            }

            Cursor c1 = database.rawQuery("SELECT * FROM shirts where OrderId ="+OrderId, null);
            Log.i("check", "1");
            Cursor cA1 = database.rawQuery("SELECT * FROM shirts, fabrics WHERE shirts.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            Log.i("check", "2");
            if(c1.moveToFirst() && cA1.moveToFirst())
            {
                int i=1;
                do {
                    int ShirtId = c1.getInt(c1.getColumnIndex("ShirtId"));
                    ItemLabelList.add("\nShirt"+i+"\n");
                    ItemOutputList.add("");
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    message = message.concat("\nShirt"+i+" Details:\n");
                    i++;

                    ItemLabelList.add("Fabric Code");
                    ItemOutputList.add(cA1.getString(cA1.getColumnIndex("FabricCode")));
                    Log.i("Shirt Fabric Code:", cA1.getString(cA1.getColumnIndex("FabricCode")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    message = message.concat("Fabric Code: ");
                    message = message.concat(cA1.getString(cA1.getColumnIndex("FabricCode"))+"\n");

                    ItemLabelList.add("Fabric Price");
                    ItemOutputList.add(cA1.getString(cA1.getColumnIndex("FabricPrice")));
                    Log.i("Shirt Fabric Price:", cA1.getString(cA1.getColumnIndex("FabricPrice")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    message = message.concat("Fabric Price: ");
                    message = message.concat(cA1.getString(cA1.getColumnIndex("FabricPrice"))+"\n");

                    ItemLabelList.add("Sleeve Type");
                    ItemOutputList.add(c1.getString(c1.getColumnIndex("Shirt_SleeveType")));
                    Log.i("Shirt_SleeveType:", c1.getString(c1.getColumnIndex("Shirt_SleeveType")));
                    tableNameList.add("shirts");
                    columnNameList.add("Shirt_SleeveType");
                    ItemIdTypeList.add("ShirtId");
                    ItemIdList.add(Integer.toString(ShirtId));
                    message = message.concat("Sleeve Type: ");
                    message = message.concat(c1.getString(c1.getColumnIndex("Shirt_SleeveType"))+"\n");

                    ItemLabelList.add("Pocket Type");
                    ItemOutputList.add(c1.getString(c1.getColumnIndex("Shirt_PocketType")));
                    Log.i("Shirt_PocketType:", c1.getString(c1.getColumnIndex("Shirt_PocketType")));
                    tableNameList.add("shirts");
                    columnNameList.add("Shirt_PocketType");
                    ItemIdTypeList.add("ShirtId");
                    ItemIdList.add(Integer.toString(ShirtId));
                    message = message.concat("Pocket Type: ");
                    message = message.concat(c1.getString(c1.getColumnIndex("Shirt_PocketType"))+"\n");

                    ItemLabelList.add("Cuff Type");
                    ItemOutputList.add(c1.getString(c1.getColumnIndex("Shirt_CuffType")));
                    Log.i("Shirt_CuffType:", c1.getString(c1.getColumnIndex("Shirt_CuffType")));
                    tableNameList.add("shirts");
                    columnNameList.add("Shirt_CuffType");
                    ItemIdTypeList.add("ShirtId");
                    ItemIdList.add(Integer.toString(ShirtId));
                    message = message.concat("Cuff Type: ");
                    message = message.concat(c1.getString(c1.getColumnIndex("Shirt_CuffType"))+"\n");

                    ItemLabelList.add("Fit Type");
                    ItemOutputList.add(c1.getString(c1.getColumnIndex("Shirt_FitType")));
                    Log.i("Shirt_FitType:", c1.getString(c1.getColumnIndex("Shirt_FitType")));
                    tableNameList.add("shirts");
                    columnNameList.add("Shirt_FitType");
                    ItemIdTypeList.add("ShirtId");
                    ItemIdList.add(Integer.toString(ShirtId));
                    message = message.concat("Fit Type: ");
                    message = message.concat(c1.getString(c1.getColumnIndex("Shirt_FitType"))+"\n");

                    ItemLabelList.add("Collar Type");
                    ItemOutputList.add(c1.getString(c1.getColumnIndex("Shirt_CollarType")));
                    Log.i("Shirt_CollarType:", c1.getString(c1.getColumnIndex("Shirt_CollarType")));
                    tableNameList.add("shirts");
                    columnNameList.add("Shirt_CollarType");
                    ItemIdTypeList.add("ShirtId");
                    ItemIdList.add(Integer.toString(ShirtId));
                    message = message.concat("Collar Type: ");
                    message = message.concat(c1.getString(c1.getColumnIndex("Shirt_CollarType"))+"\n");


                    ItemLabelList.add("Placket Type");
                    ItemOutputList.add(c1.getString(c1.getColumnIndex("Shirt_PlacketType")));
                    Log.i("Shirt_PlacketType:", c1.getString(c1.getColumnIndex("Shirt_PlacketType")));
                    tableNameList.add("shirts");
                    columnNameList.add("Shirt_PlacketType");
                    ItemIdTypeList.add("ShirtId");
                    ItemIdList.add(Integer.toString(ShirtId));
                    message = message.concat("Placket Type: ");
                    message = message.concat(c1.getString(c1.getColumnIndex("Shirt_PlacketType"))+"\n");

                } while (c1.moveToNext() && cA1.moveToNext());
            }

            Cursor c2 = database.rawQuery("SELECT * FROM suits WHERE OrderId = "+OrderId, null);
            Cursor cA2 = database.rawQuery("SELECT * FROM suits, fabrics WHERE suits.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c2.moveToFirst() && cA2.moveToFirst())
            {
                int i=1;
                do {

                    int SuitId = c2.getInt(c2.getColumnIndex("SuitId"));

                    ItemLabelList.add("\nSuit"+i+"\n");
                    ItemOutputList.add("");
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    message = message.concat("\nSuit"+i+" Details:\n");
                    i++;

                    ItemLabelList.add("Fabric Code");
                    ItemOutputList.add(cA2.getString(cA2.getColumnIndex("FabricCode")));
                    Log.i("Suit Fabric Code:", cA2.getString(cA2.getColumnIndex("FabricCode")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    message = message.concat("Fabric Code: ");
                    message = message.concat(cA2.getString(cA2.getColumnIndex("FabricCode"))+"\n");

                    ItemLabelList.add("Fabric Price");
                    ItemOutputList.add(cA2.getString(cA2.getColumnIndex("FabricPrice")));
                    Log.i("Suit Fabric Price:", cA2.getString(cA2.getColumnIndex("FabricPrice")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    message = message.concat("Fabric Price: ");
                    message = message.concat(cA2.getString(cA2.getColumnIndex("FabricPrice"))+"\n");

                    ItemLabelList.add("Fit Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_FitType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_FitType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));
                    message = message.concat("Fit Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_FitType"))+"\n");

                    ItemLabelList.add("Jacket Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_JacketType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_JacketType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));
                    message = message.concat("Jacket Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_JacketType"))+"\n");

                    ItemLabelList.add("Lapel Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_LapelType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_LapelType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));
                    message = message.concat("Lapel Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_LapelType"))+"\n");

                    ItemLabelList.add("Bottom Cut Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_BottomCutType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_BottomCutType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));
                    message = message.concat("Bottom Cut Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_BottomCutType"))+"\n");

                    ItemLabelList.add("Vest Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_VestType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_VestType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));
                    message = message.concat("Vest Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_VestType"))+"\n");

                    ItemLabelList.add("Pocket Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_PocketType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_PocketType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));
                    message = message.concat("Pocket Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_PocketType"))+"\n");

                    ItemLabelList.add("Sleeve Button Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_SleeveButtonType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_SleeveButtonType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));
                    message = message.concat("Sleeve Button Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_SleeveButtonType"))+"\n");

                    ItemLabelList.add("Vent Type");
                    ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_VentType")));
                    tableNameList.add("suits");
                    columnNameList.add("Suit_VentType");
                    ItemIdTypeList.add("SuitId");
                    ItemIdList.add(Integer.toString(SuitId));
                    message = message.concat("Vent Type: ");
                    message = message.concat(c2.getString(c2.getColumnIndex("Suit_VentType"))+"\n");

                    if(c2.getString(c2.getColumnIndex("Suit_VestType")).equals("Yes")) {
                        ItemLabelList.add("Type Of Vest Type");
                        ItemOutputList.add(c2.getString(c2.getColumnIndex("Suit_TypeOfVest")));
                        tableNameList.add("suits");
                        columnNameList.add("Suit_TypeOfVest");
                        ItemIdTypeList.add("SuitId");
                        ItemIdList.add(Integer.toString(SuitId));
                        message = message.concat("Type Of Vest Type: ");
                        message = message.concat(c2.getString(c2.getColumnIndex("Suit_TypeOfVest"))+"\n");
                    }
                } while (c2.moveToNext() && cA2.moveToNext());
            }

            Cursor c3 = database.rawQuery("SELECT * FROM trousers WHERE OrderId = "+OrderId, null);
            Cursor cA3 = database.rawQuery("SELECT * FROM trousers, fabrics WHERE trousers.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c3.moveToFirst() && cA3.moveToFirst())
            {
                int i=1;
                do {

                    int TrouserId = c3.getInt(c3.getColumnIndex("TrouserId"));
                    ItemLabelList.add("\nTrouser"+i+"\n");
                    ItemOutputList.add("");
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    message = message.concat("\nTrouser"+i+" Details:\n");
                    i++;

                    ItemLabelList.add("Fabric Code");
                    ItemOutputList.add(cA3.getString(cA3.getColumnIndex("FabricCode")));
                    Log.i("trouser Fabric Code:", cA3.getString(cA3.getColumnIndex("FabricCode")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    message = message.concat("Fabric Code: ");
                    message = message.concat(cA3.getString(cA3.getColumnIndex("FabricCode"))+"\n");

                    ItemLabelList.add("Fabric Price");
                    ItemOutputList.add(cA3.getString(cA3.getColumnIndex("FabricPrice")));
                    Log.i("trouser Fabric Price:", cA3.getString(cA3.getColumnIndex("FabricPrice")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    message = message.concat("Fabric Price: ");
                    message = message.concat(cA3.getString(cA3.getColumnIndex("FabricPrice"))+"\n");

                    ItemLabelList.add("Fit Type");
                    ItemOutputList.add(c3.getString(c3.getColumnIndex("Trouser_FitType")));
                    tableNameList.add("trousers");
                    columnNameList.add("Trouser_FitType");
                    ItemIdTypeList.add("TrouserId");
                    ItemIdList.add(Integer.toString(TrouserId));
                    message = message.concat("Fit Type: ");
                    message = message.concat(c3.getString(c3.getColumnIndex("Trouser_FitType"))+"\n");

                    ItemLabelList.add("Pant Pleats Type");
                    ItemOutputList.add(c3.getString(c3.getColumnIndex("Trouser_PantPleatsType")));
                    tableNameList.add("trousers");
                    columnNameList.add("Trouser_PantPleatsType");
                    ItemIdTypeList.add("TrouserId");
                    ItemIdList.add(Integer.toString(TrouserId));
                    message = message.concat("Pant Pleats Type: ");
                    message = message.concat(c3.getString(c3.getColumnIndex("Trouser_PantPleatsType"))+"\n");

                    ItemLabelList.add("Pant Pocket Type");
                    ItemOutputList.add(c3.getString(c3.getColumnIndex("Trouser_PantPocketType")));
                    tableNameList.add("trousers");
                    columnNameList.add("Trouser_PantPocketType");
                    ItemIdTypeList.add("TrouserId");
                    ItemIdList.add(Integer.toString(TrouserId));
                    message = message.concat("Pant Pocket Type: ");
                    message = message.concat(c3.getString(c3.getColumnIndex("Trouser_PantPocketType"))+"\n");

                    ItemLabelList.add("Back Pocket Style");
                    ItemOutputList.add(c3.getString(c3.getColumnIndex("Trouser_BackPocketStyleType")));
                    tableNameList.add("trousers");
                    columnNameList.add("Trouser_BackPocketStyleType");
                    ItemIdTypeList.add("TrouserId");
                    ItemIdList.add(Integer.toString(TrouserId));
                    message = message.concat("Back Pocket Style: ");
                    message = message.concat(c3.getString(c3.getColumnIndex("Trouser_BackPocketStyleType"))+"\n");

                    ItemLabelList.add("Back Pocket Type");
                    ItemOutputList.add(c3.getString(c3.getColumnIndex("Trouser_BackPocketType")));
                    tableNameList.add("trousers");
                    columnNameList.add("Trouser_BackPocketType");
                    ItemIdTypeList.add("TrouserId");
                    ItemIdList.add(Integer.toString(TrouserId));
                    message = message.concat("Back Pocket Type: ");
                    message = message.concat(c3.getString(c3.getColumnIndex("Trouser_BackPocketType"))+"\n");

                    ItemLabelList.add("Belt Loops Type");
                    ItemOutputList.add(c3.getString(c3.getColumnIndex("Trouser_BeltLoopsType")));
                    tableNameList.add("trousers");
                    columnNameList.add("Trouser_BeltLoopsType");
                    ItemIdTypeList.add("TrouserId");
                    ItemIdList.add(Integer.toString(TrouserId));
                    message = message.concat("Belt Loops Type: ");
                    message = message.concat(c3.getString(c3.getColumnIndex("Trouser_BeltLoopsType"))+"\n");

                } while (c3.moveToNext() && cA3.moveToNext());
            }

            Cursor c5 = database.rawQuery("SELECT * FROM others WHERE OrderId = "+OrderId, null);
            Cursor cA5 = database.rawQuery("SELECT * FROM others, fabrics WHERE others.FabricId = fabrics.FabricId AND OrderId = "+OrderId, null);
            if(c5.moveToFirst() && cA5.moveToFirst())
            {
                int i=1;
                do {

                    int OtherId = c5.getInt(c5.getColumnIndex("OtherId"));
                    ItemLabelList.add("\nOther"+i+"\n");
                    ItemOutputList.add("");
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    message = message.concat("\nOther"+i+" Details:\n");
                    i++;

                    ItemLabelList.add("Type:");
                    ItemOutputList.add(cA5.getString(cA5.getColumnIndex("FabricCode")));
                    Log.i("Other Fabric Code:", cA5.getString(cA5.getColumnIndex("FabricCode")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    message = message.concat("Type: ");
                    message = message.concat(cA5.getString(cA5.getColumnIndex("FabricCode"))+"\n");

                    ItemLabelList.add("Fabric Price");
                    ItemOutputList.add(cA5.getString(cA5.getColumnIndex("FabricPrice")));
                    Log.i("Other Fabric Price:", cA5.getString(cA5.getColumnIndex("FabricPrice")));
                    tableNameList.add("");
                    columnNameList.add("");
                    ItemIdTypeList.add("");
                    ItemIdList.add("");
                    message = message.concat("Fabric Price: ");
                    message = message.concat(cA5.getString(cA5.getColumnIndex("FabricPrice"))+"\n");

                    ItemLabelList.add("Description");
                    ItemOutputList.add(c5.getString(c5.getColumnIndex("Description")));
                    tableNameList.add("others");
                    columnNameList.add("Description");
                    ItemIdTypeList.add("OtherId");
                    ItemIdList.add(Integer.toString(OtherId));
                    message = message.concat("Description: ");
                    message = message.concat(c5.getString(c5.getColumnIndex("Description"))+"\n");

                } while (c5.moveToNext() && cA5.moveToNext());
            }
            

            Cursor c4 = database.rawQuery("SELECT * FROM measurements WHERE CustomerId= "+CustomerId, null);
            if(c4.moveToFirst())
            {
                int MeasurementsId = c4.getInt(c4.getColumnIndex("MeasurementsId"));
                ItemLabelList.add("\nMeasurement\nUpper Garment\n");
                ItemOutputList.add("");
                tableNameList.add("");
                columnNameList.add("");
                ItemIdTypeList.add("");
                ItemIdList.add("");
                message = message.concat("\nMeasurement:\nUpper Garment:\n");

                ItemLabelList.add("Upper Chest");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement1")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement1");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Upper Chest: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement1"))+"\n");

                ItemLabelList.add("Round Chest");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement2")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement2");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Round Chest: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement2"))+"\n");

                ItemLabelList.add("Lower Chest");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement3")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement3");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Lower Chest: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement3"))+"\n");

                ItemLabelList.add("Length Suit");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement4")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement4");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Length Suit: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement4"))+"\n");

                ItemLabelList.add("Length Shirt");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement5")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement5");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Length Shirt: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement5"))+"\n");

                ItemLabelList.add("Stomach");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement6")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement6");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Stomach: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement6"))+"\n");

                ItemLabelList.add("Round Hip");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement7")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement7");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Round Hip: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement7"))+"\n");

                ItemLabelList.add("Across Front");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement8")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement8");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Across Front: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement8"))+"\n");

                ItemLabelList.add("Across Back");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement9")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement9");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Across Back: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement9"))+"\n");

                ItemLabelList.add("Across Shoulder");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement10")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement10");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Across Shoulder: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement10"))+"\n");

                ItemLabelList.add("Side Shoulder");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement11")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement11");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Side Shoulder: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement11"))+"\n");

                ItemLabelList.add("Round Neck (Collar)");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement12")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement12");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Round Neck (Collar): ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement12"))+"\n");

                ItemLabelList.add("Lapel");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement13")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement13");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Lapel: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement13"))+"\n");

                ItemLabelList.add("Sleeve Length");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement14")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement14");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Sleeve Length: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement14"))+"\n");

                ItemLabelList.add("Armhole");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement15")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement15");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Armhole: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement15"))+"\n");

                ItemLabelList.add("Biceps");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement16")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement16");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Biceps: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement16"))+"\n");

                ItemLabelList.add("Elbow");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement17")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement17");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Elbow: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement17"))+"\n");

                ItemLabelList.add("Sleeve Bottom");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement18")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement18");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Sleeve Bottom: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement18"))+"\n");

                ItemLabelList.add("Cuff Length");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement19")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement19");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Cuff Length: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement19"))+"\n");

                ItemLabelList.add("Cuff Width");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement20")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement20");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Cuff Width: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement20"))+"\n");

                ItemLabelList.add("\nMeasurement\nLower Garment\n");
                ItemOutputList.add("");
                tableNameList.add("");
                columnNameList.add("");
                ItemIdTypeList.add("");
                ItemIdList.add("");
                message = message.concat("\nMeasurement:\nLower Garment:\n");

                ItemLabelList.add("Length");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement21")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement21");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Length: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement21"))+"\n");

                ItemLabelList.add("Round Waist");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement22")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement22");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Round Waist: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement22"))+"\n");

                ItemLabelList.add("Round Hip");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement23")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement23");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Round Hip: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement23"))+"\n");

                ItemLabelList.add("Round Thigh");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement24")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement24");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Round Thigh: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement24"))+"\n");

                ItemLabelList.add("Round Knee");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement25")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement25");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Round Knee: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement25"))+"\n");

                ItemLabelList.add("Calf");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement26")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement26");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Calf: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement26"))+"\n");

                ItemLabelList.add("Crotch");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement27")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement27");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Crotch: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement27"))+"\n");

                ItemLabelList.add("Fog");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement28")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement28");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Fog: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement28"))+"\n");

                ItemLabelList.add("In-Seam");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement29")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement29");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("In-Seam: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement29"))+"\n");

                ItemLabelList.add("Bottom (Mori)");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement30")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement30");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Bottom (Mori): ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement30"))+"\n");

                ItemLabelList.add("Belt Width");
                ItemOutputList.add(c4.getString(c4.getColumnIndex("Measurement31")));
                tableNameList.add("measurements");
                columnNameList.add("Measurement31");
                ItemIdTypeList.add("MeasurementsId");
                ItemIdList.add(Integer.toString(MeasurementsId));
                message = message.concat("Belt Width: ");
                message = message.concat(c4.getString(c4.getColumnIndex("Measurement31"))+"\n");
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }*/

        if(source.equals("inFlow"))
        {


            Date trial;
            Date delivery;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try{
                trial = sdf.parse(trialDate);
                delivery = sdf.parse(deliveryDate);
                Calendar caltrial = Calendar.getInstance();
                Calendar caldelivery = Calendar.getInstance();
                caltrial.setTime(trial);
                caldelivery.setTime(delivery);
                int trialyear = caltrial.get(Calendar.YEAR);
                int trialmonth = caltrial.get(Calendar.MONTH);
                int trialday = caltrial.get(Calendar.DAY_OF_MONTH);
                int deliveryyear = caldelivery.get(Calendar.YEAR);
                int deliverymonth = caldelivery.get(Calendar.MONTH);
                int deliveryday = caldelivery.get(Calendar.DAY_OF_MONTH);
                Log.i("Trial Year: " ,Integer.toString(trialyear));
                Log.i("Trial Month: " ,Integer.toString(trialmonth));
                Log.i("Trial Day: " ,Integer.toString(trialday));
                Log.i("delivery Year: " ,Integer.toString(deliveryyear));
                Log.i("delivery Month: " ,Integer.toString(deliverymonth));
                Log.i("delivery Day: " ,Integer.toString(deliveryday));
                caltrial.add(Calendar.DAY_OF_YEAR, -1);
                caldelivery.add(Calendar.DAY_OF_YEAR, -1);
                int trialyearbefore = caltrial.get(Calendar.YEAR);
                int trialmonthbefore = caltrial.get(Calendar.MONTH);
                int trialdaybefore = caltrial.get(Calendar.DAY_OF_MONTH);
                int deliveryyearbefore = caldelivery.get(Calendar.YEAR);
                int deliverymonthbefore = caldelivery.get(Calendar.MONTH);
                int deliverydaybefore = caldelivery.get(Calendar.DAY_OF_MONTH);
                Log.i("Trial Year before: " ,Integer.toString(trialyearbefore));
                Log.i("Trial Month before: " ,Integer.toString(trialmonthbefore));
                Log.i("Trial Day before: " ,Integer.toString(trialdaybefore));
                Log.i("delivery Year before: " ,Integer.toString(deliveryyearbefore));
                Log.i("delivery Month before: " ,Integer.toString(deliverymonthbefore));
                Log.i("delivery Day before: " ,Integer.toString(deliverydaybefore));

                //On TrialDate Notification
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
                notificationIntent.addCategory("android.intent.category.DEFAULT");
                notificationIntent.putExtra("type1", "trial");
                notificationIntent.putExtra("OrderId1", OrderId);
                notificationIntent.putExtra("OrderNumber1", Integer.toString(orderNumber));
                Log.i("OrderNumber1", Integer.toString(orderNumber));
                notificationIntent.putExtra("CustomerName1", CustomerName);
                notificationIntent.putExtra("day", "today");


                Calendar now = Calendar.getInstance();
                //now.set(int year, int month, int date, int hourOfDay, int minute, 0)

                PendingIntent broadcast = PendingIntent.getBroadcast(this, OrderId+100000000, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar cal = Calendar.getInstance();
                //cal.setTimeInMillis(System.currentTimeMillis());
                //cal.clear();
                //cal.set(trialyear,trialmonth,trialday,10,00);
                cal.add(Calendar.SECOND, 25);


                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);

                //One Day Before TrialDate Notification
                AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent notificationIntent2 = new Intent("android.media.action.DISPLAY_NOTIFICATION");
                notificationIntent2.addCategory("android.intent.category.DEFAULT");
                notificationIntent2.putExtra("type1", "trial");
                notificationIntent2.putExtra("OrderId1", OrderId);
                notificationIntent2.putExtra("OrderNumber1", Integer.toString(orderNumber));
                Log.i("OrderNumber1", Integer.toString(orderNumber));
                notificationIntent2.putExtra("CustomerName1", CustomerName);
                notificationIntent2.putExtra("day", "tomorrow");

                PendingIntent broadcast2 = PendingIntent.getBroadcast(this, OrderId+200000000, notificationIntent2, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar cal2 = Calendar.getInstance();
                //cal2.setTimeInMillis(System.currentTimeMillis());
                //cal2.clear();
                //cal2.set(trialyearbefore,trialmonthbefore,trialdaybefore,10,00);
                cal2.add(Calendar.SECOND, 5);

                alarmManager2.set(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis(), broadcast2);

                //On DeliveryDate Notification
                AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent notificationIntent1 = new Intent("android.media.action.DISPLAY_NOTIFICATION");
                notificationIntent1.addCategory("android.intent.category.DEFAULT");
                notificationIntent1.putExtra("type1", "delivery");
                notificationIntent1.putExtra("OrderId1", OrderId);
                notificationIntent1.putExtra("OrderNumber1", Integer.toString(orderNumber));
                Log.i("OrderNumber1", Integer.toString(orderNumber));
                notificationIntent1.putExtra("CustomerName1", CustomerName);
                notificationIntent1.putExtra("day", "today");

                PendingIntent broadcast1 = PendingIntent.getBroadcast(this, OrderId+1000000000, notificationIntent1, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar ca2 = Calendar.getInstance();
                //ca2.setTimeInMillis(System.currentTimeMillis());
                //ca2.clear();
                //ca2.set(deliveryyear,deliverymonth,deliveryday,10,00);
                ca2.add(Calendar.SECOND, 35);

                alarmManager1.set(AlarmManager.RTC_WAKEUP, ca2.getTimeInMillis(), broadcast1);

                //On day before DeliveryDate Notification
                AlarmManager alarmManager3 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent notificationIntent3 = new Intent("android.media.action.DISPLAY_NOTIFICATION");
                notificationIntent3.addCategory("android.intent.category.DEFAULT");
                notificationIntent3.putExtra("type1", "delivery");
                notificationIntent3.putExtra("OrderId1", OrderId);
                notificationIntent3.putExtra("OrderNumber1", Integer.toString(orderNumber));
                Log.i("OrderNumber1", Integer.toString(orderNumber));
                notificationIntent3.putExtra("CustomerName1", CustomerName);
                notificationIntent3.putExtra("day", "tomorrow");

                PendingIntent broadcast3 = PendingIntent.getBroadcast(this, OrderId+2000000000, notificationIntent3, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar ca3 = Calendar.getInstance();
                //ca3.setTimeInMillis(System.currentTimeMillis());
                //ca3.clear();
                //ca3.set(deliveryyear,deliverymonth,deliveryday,10,00);
                ca3.add(Calendar.SECOND, 15);

                alarmManager3.set(AlarmManager.RTC_WAKEUP, ca3.getTimeInMillis(), broadcast3);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            String toNumber = "+91 9818020151"; // contains spaces.
            toNumber = toNumber.replace("+", "").replace(" ", "");

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setPackage("com.whatsapp");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        itemDetailsList = (ListView) findViewById(R.id.detailedRecordList);
        itemDetailsList.setNestedScrollingEnabled(true);

        listViewAdapter = new ListViewAdapter(this, ItemLabelList, ItemOutputList);


        itemDetailsList.setAdapter(listViewAdapter);


        itemDetailsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                if(ItemOutputList.get(position) != "" && tableNameList.get(position) != ""  && columnNameList.get(position) != "" && ItemIdTypeList.get(position) != ""  &&
                        ItemIdList.get(position) != "") {

                    if (ItemLabelList.get(position).equals("Order Status")) {
                        final String[] orderStatuses = {"Order Initiated", "Order Recieved","Order Confirmed","Order Form Ready", "Order With Fabricator(I)",
                                "Order Ready For Trial", "Order Trial Done", "Order With Fabricator(II)","Order Ready for Delivery","Order Delivered"};


                        AlertDialog.Builder builder1 = new AlertDialog.Builder(DetailedRecord.this);
                        builder1.setTitle("Update Status:");
                        builder1.setItems(orderStatuses, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item1) {

                                final String newStatus = orderStatuses[item1];
                                String sql = "UPDATE orders SET OrderStatus = '"+newStatus+"' WHERE OrderId = "+OrderId;
                                database.execSQL(sql);



                                listViewAdapter.notifyDataSetChanged();
                                new AlertDialog.Builder(DetailedRecord.this)
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

                                                        String sql2 = "UPDATE orders SET AdvanceRecieved ='Yes' WHERE OrderId = "+OrderId;
                                                        database.execSQL(sql2);

                                                        ItemOutputList.set(9, "Yes");

                                                        SmsManager smsManager = SmsManager.getDefault();
                                                        smsManager.sendTextMessage(Long.toString(phoneNo), null, sms, null, null);

                                                    } catch (Exception e) {
                                                        Toast.makeText(getApplicationContext(),
                                                                "SMS faild, please try again later!",
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
                                                        intent.putExtra("OrderId1", OrderId);
                                                        intent.putExtra("CustomerId1", CustomerId);
                                                        intent.putExtra("source1", "delivery");
                                                        intent.putExtra("billAmountValue1", billAmount);
                                                        startActivity(intent);
                                                    }
                                                }
                                                if(newStatus.equals("Order Trial Done")){

                                                    if(paymentPending > 0)
                                                    {
                                                        Intent intent = new Intent(getApplicationContext(), PaymentPendingAlert.class);
                                                        intent.putExtra("OrderId1", OrderId);
                                                        intent.putExtra("CustomerId1", CustomerId);
                                                        intent.putExtra("source1", "trial");
                                                        intent.putExtra("billAmountValue1", billAmount);
                                                        startActivity(intent);
                                                    }
                                                }
                                                if(newStatus.equals("Order Delivered")){

                                                    if(paymentPending > 0)
                                                    {
                                                        Intent intent = new Intent(getApplicationContext(), PaymentPendingAlert.class);
                                                        intent.putExtra("OrderId1", OrderId);
                                                        intent.putExtra("CustomerId1", CustomerId);
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
                                                        intent.putExtra("OrderId1", OrderId);
                                                        intent.putExtra("CustomerId1", CustomerId);
                                                        intent.putExtra("source1", "trial");
                                                        intent.putExtra("billAmountValue1", billAmount);
                                                        startActivity(intent);
                                                    }
                                                }
                                                if(newStatus.equals("Order Delivered")){

                                                    if(paymentPending > 0)
                                                    {
                                                        Intent intent = new Intent(getApplicationContext(), PaymentPendingAlert.class);
                                                        intent.putExtra("OrderId1", OrderId);
                                                        intent.putExtra("CustomerId1", CustomerId);
                                                        intent.putExtra("source1", "delivery");
                                                        intent.putExtra("billAmountValue1", billAmount);
                                                        startActivity(intent);
                                                    }
                                                }
                                            }
                                        })
                                        .show();

                                ItemOutputList.set(1, newStatus);

                                Toast.makeText(getApplicationContext(), "Status Changed to: "+newStatus, Toast.LENGTH_LONG).show();



                            }
                        });
                        AlertDialog alert1 = builder1.create();
                        alert1.show();



                    }
                    else if (ItemLabelList.get(position).equals("Payment Recieved")) {

                        LayoutInflater li = LayoutInflater.from(getApplicationContext());
                        View promptsView = li.inflate(R.layout.popup_input, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailedRecord.this, R.style.MyDialogTheme);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        final EditText userInput = (EditText) promptsView
                                .findViewById(R.id.editTextDialogUserInput);
                        userInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);


                        final TextView header = (TextView) promptsView
                                .findViewById(R.id.popupdialogtitle);


                        userInput.setText("");
                        header.setText("Add Payment");

                        // set dialog message
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // get user input and set it to result
                                                // edit text

                                                try {
                                                    Cursor c = database.rawQuery("SELECT * FROM orders WHERE OrderId = "+OrderId, null);
                                                    c.moveToFirst();
                                                    int paymentrecievedtillnow = c.getInt(c.getColumnIndex("AdvancePaid"));
                                                    int paymentduetillnow = c.getInt(c.getColumnIndex("PaymentDue"));
                                                    int totalpayment = Integer.parseInt(userInput.getText().toString()) + paymentrecievedtillnow;
                                                    int totaldue = paymentduetillnow - Integer.parseInt(userInput.getText().toString());
                                                    database.execSQL("UPDATE orders SET AdvancePaid = "+totalpayment+" WHERE OrderId = "+OrderId);
                                                    database.execSQL("UPDATE orders SET PaymentDue = "+totaldue+" WHERE OrderId = "+OrderId);
                                                    ItemOutputList.set(position, Integer.toString(totalpayment));
                                                    ItemOutputList.set(position+1, Integer.toString(totaldue));
                                                    Toast.makeText(DetailedRecord.this, "Payment Recieved changed to Rs."+totalpayment+" and total dues are now Rs."+totaldue, Toast.LENGTH_SHORT).show();
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
                    else {

                        // get prompts.xml view
                        LayoutInflater li = LayoutInflater.from(getApplicationContext());
                        View promptsView = li.inflate(R.layout.popup_input, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailedRecord.this, R.style.MyDialogTheme);

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
                                            public void onClick(DialogInterface dialog, int id) {
                                                // get user input and set it to result
                                                // edit text

                                                try {
                                                    database.execSQL("UPDATE " + tableNameList.get(position) + " SET " + columnNameList.get(position) + " = '" + userInput.getText().toString() + "' WHERE " +
                                                            ItemIdTypeList.get(position) + " = " + ItemIdList.get(position));
                                                    ItemOutputList.set(position, userInput.getText().toString());
                                                    Toast.makeText(DetailedRecord.this, ItemLabelList.get(position) + " changed to " + userInput.getText().toString(), Toast.LENGTH_SHORT).show();
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
                }


                listViewAdapter.notifyDataSetChanged();

                // Toast.makeText(MainActivity.this,"Description => "+month[position]+"=> n Title"+number[position], Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        listViewAdapter.notifyDataSetChanged();



    }
    public void goBack(View view) {


        Intent intent = new Intent(this, AllRecords.class);
        startActivity(intent);

    }

    public void backHome(View view) {


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void shareDetails(View view) {

        new AlertDialog.Builder(DetailedRecord.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Select Recipient?")
                .setMessage("Whom do you want to share with?")
                .setPositiveButton("Whatsapp Group", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long OrderIdToShare = OrderId;
                        Log.i("OrderId to share:", Long.toString(OrderIdToShare));

                        String toNumber = "+91 9818020151"; // contains spaces.
                        toNumber = toNumber.replace("+", "").replace(" ", "");

                        Intent sendIntent = new Intent("android.intent.action.MAIN");
                        sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
                        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.setPackage("com.whatsapp");
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                })
                .setNegativeButton("Employees", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long OrderIdToShare = OrderId;
                        Log.i("OrderId to share:", Long.toString(OrderIdToShare));
                        Intent intent = new Intent(DetailedRecord.this, SendDetailsEmployee.class);
                        intent.putExtra("OrderId1", OrderId);
                        startActivity(intent);
                    }
                })
                .show();


    }

    public void test(View view) {

        CharSequence colors[] = new CharSequence[] {"red", "green", "blue", "black"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a color");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
            }
        });
        builder.show();
    }
}
