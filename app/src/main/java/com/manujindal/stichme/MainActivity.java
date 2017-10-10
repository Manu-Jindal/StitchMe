package com.manujindal.stichme;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.SupportActionModeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;
    long OrderId;
    int PrevOrderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        database.execSQL("CREATE TABLE IF NOT EXISTS orders(OrderId BIGINT PRIMARY KEY AUTOINCREMENT, " +
                "CustomerId INTEGER DEFAULT -1, " +
                "OrderNumber INT DEFAULT -1 , " +
                "OrderDate DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "TrialDate DATE DEFAULT '1900-1-1', " +
                "DeliveryDate DATE DEFAULT '1900-1-1', " +
                "BillAmount DECIMAL(10,2) DEFAULT 0, " +
                "FinalBillAmount DECIMAL(10,2) DEFAULT 0, " +
                "AdvancePaid DECIMAL(10,2) DEFAULT 0.0, " +
                "PaymentDue DECIMAL(10,2) DEFAULT 0.0, " +
                "AdvanceRecieved VARCHAR(100) DEFAULT '', " +
                "AdvancePaymentMethod VARCHAR(100) DEFAULT '', " +
                "ExpressCharge INT DEFAULT 0 , " +
                "Discount INT DEFAULT 0 , " +
                "OrderStatus VARCHAR(100) DEFAULT 'Order Initiated');");

        database.execSQL("CREATE TABLE IF NOT EXISTS customers(CustomerId BIGINT PRIMARY KEY AUTOINCREMENT, " +
                "Name VARCHAR(100) DEFAULT '' , " +
                "Dob DATE DEFAULT '1900-1-1', " +
                "Email VARCHAR(100) DEFAULT '', " +
                "Phone BIGINT DEFAULT 0, " +
                "Address VARCHAR(500) DEFAULT '', " +
                "Occupation VARCHAR(100) DEFAULT '', " +
                "TypePreference VARCHAR(100) DEFAULT ''," +
                "ShirtColorPreference VARCHAR(100) DEFAULT '', " +
                "ShirtPatternPreference VARCHAR(100) DEFAULT '', " +
                "SuitColorPreference VARCHAR(100) DEFAULT '', " +
                "SuitPatternPreference VARCHAR(100) DEFAULT '', " +
                "TrouserColorPreference VARCHAR(100) DEFAULT '', " +
                "TrouserPatternPreference VARCHAR(100) DEFAULT '');");

        database.execSQL("CREATE TABLE IF NOT EXISTS appointments(AppointmentId BIGINT PRIMARY KEY AUTOINCREMENT, " +
                "CustomerId INTEGER DEFAULT -1, " +
                "AppointmentDate DATE DEFAULT '1900-1-1', " +
                "Remarks VARCHAR(200) DEFAULT '', " +
                "Slot VARCHAR DEFAULT '', " +
                "EmployeeId INTEGER DEFAULT -1);");

        database.execSQL("CREATE TABLE IF NOT EXISTS employees(EmployeeId BIGINT PRIMARY KEY AUTOINCREMENT, " +
                "Name VARCHAR(100) DEFAULT '' , " +
                "Phone BIGINT DEFAULT 0);");

        database.execSQL("CREATE TABLE IF NOT EXISTS shirts(ShirtId BIGINT PRIMARY KEY AUTOINCREMENT, " +
                "FabricId INTEGER DEFAULT -1, " +
                "OrderId INTEGER DEFAULT -1, " +
                "Shirt_SleeveType VARCHAR(100) DEFAULT '', " +
                "Shirt_PocketType VARCHAR(100) DEFAULT '', " +
                "Shirt_CuffType VARCHAR(100) DEFAULT '', " +
                "Shirt_FitType VARCHAR(100) DEFAULT '', " +
                "Shirt_CollarType VARCHAR(100) DEFAULT '', " +
                "Shirt_PlacketType VARCHAR(100) DEFAULT '');");

        database.execSQL("CREATE TABLE IF NOT EXISTS suits(SuitId BIGINT PRIMARY KEY AUTOINCREMENT, " +
                "FabricId INTEGER DEFAULT -1, " +
                "OrderId INTEGER DEFAULT -1, " +
                "Suit_FitType VARCHAR(100) DEFAULT '', " +
                "Suit_JacketType VARCHAR(100) DEFAULT '', " +
                "Suit_LapelType VARCHAR(100) DEFAULT '', " +
                "Suit_BottomCutType VARCHAR(100) DEFAULT '', " +
                "Suit_VestType VARCHAR(100) DEFAULT '', " +
                "Suit_PocketType VARCHAR(100) DEFAULT '', " +
                "Suit_SleeveButtonType VARCHAR(100) DEFAULT '', " +
                "Suit_VentType VARCHAR(100) DEFAULT '', " +
                "Suit_TypeOfVestPrice INT DEFAULT 0, " +
                "Suit_TypeOfVest VARCHAR(100) DEFAULT '');");

        database.execSQL("CREATE TABLE IF NOT EXISTS trousers(TrouserId BIGINT PRIMARY KEY AUTOINCREMENT, " +
                "FabricId INTEGER DEFAULT -1, " +
                "OrderId INTEGER DEFAULT -1, " +
                "Trouser_FitType VARCHAR(100) DEFAULT '', " +
                "Trouser_PantPleatsType VARCHAR(100) DEFAULT '', " +
                "Trouser_PantPocketType VARCHAR(100) DEFAULT '', " +
                "Trouser_BackPocketStyleType VARCHAR(100) DEFAULT '', " +
                "Trouser_BackPocketType VARCHAR(100) DEFAULT '', " +
                "Trouser_BeltLoopsType VARCHAR(100) DEFAULT '');");


        database.execSQL("CREATE TABLE IF NOT EXISTS others(OtherId BIGINT PRIMARY KEY AUTOINCREMENT, " +
                "FabricId INTEGER DEFAULT -1, " +
                "OrderId INTEGER DEFAULT -1, " +
                "Description VARCHAR(500) DEFAULT '');");

        database.execSQL("CREATE TABLE IF NOT EXISTS measurements(MeasurementsId BIGINT PRIMARY KEY AUTOINCREMENT, " +
                "CustomerId INTEGER DEFAULT -1, " +
                "Measurement1 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement2 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement3 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement4 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement5 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement6 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement7 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement8 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement9 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement10 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement11 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement12 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement13 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement14 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement15 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement16 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement17 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement18 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement19 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement20 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement21 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement22 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement23 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement24 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement25 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement26 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement27 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement28 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement29 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement30 DECIMAL(6,2) DEFAULT 0.0, " +
                "Measurement31 DECIMAL(6,2) DEFAULT 0.0);");

        database.execSQL("CREATE TABLE IF NOT EXISTS fabrics(FabricId BIGINT PRIMARY KEY AUTOINCREMENT, " +
                "FabricType VARCHAR(100) DEFAULT '', " +
                "FabricCode VARCHAR(100) DEFAULT '', " +
                "FabricPrice INTEGER DEFAULT 0);");


        if (getPreferences(MODE_PRIVATE).getBoolean("is_first_run", true)) {

            Intent intent1 = getIntent();
            int loginsuccess = intent1.getIntExtra("loginsuccess", -1);
            Log.i("loginsucess", Integer.toString(loginsuccess));
            if (loginsuccess != 1)
            {
                Intent intent = new Intent(getApplicationContext(), InitialLogin.class);
                startActivity(intent);
            }
            if(loginsuccess == 1)
            {
                database.execSQL("INSERT INTO employees (Name,Phone)  Values ('Manu', 9540509324)");
                database.execSQL("INSERT INTO employees (Name,Phone)  Values ('Amber', 9818020151)");
                database.execSQL("INSERT INTO employees (Name,Phone)  Values ('Punit', 8700580771)");
                database.execSQL("INSERT INTO employees (Name,Phone)  Values ('Kabir', 8700588229)");
                database.execSQL("INSERT INTO employees (Name,Phone)  Values ('Deepanshu', 8383966053)");
                database.execSQL("INSERT INTO employees (Name,Phone)  Values ('Jitender', 8076664806)");
                database.execSQL("INSERT INTO employees (Name,Phone)  Values ('Riyaz', 9891322632)");
                database.execSQL("INSERT INTO employees (Name,Phone)  Values ('Atik', 9870118543)");
                database.execSQL("INSERT INTO employees (Name,Phone)  Values ('Meherbaan', 7982873303)");

                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Stitch',650)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_0',0)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_1',1199)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_2',1599)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_3',1399)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_4',1599)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_5',1399)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_6',1399)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_7',1199)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_8',1199)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_9',1899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_10',1499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_11',1899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_12',1599)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_13',1199)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_14',1399)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_15',1399)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_16',1599)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_17',1899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_18',1599)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_19',1499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_20',1499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_21',1499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_22',1899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_23',1899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_24',1599)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_25',1599)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_26',1699)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_27',2199)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_28',1599)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_29',1399)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_30',1399)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_31',1799)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_32',1850)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_33',1799)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_34',1599)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_35',1399)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_36',1499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_37',1599)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_38',3200)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_39',1799)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_40',1750)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_41',2199)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_42',1750)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_43',1750)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_ABHIGAIL',2300)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_AIDA',2200)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_AIDA PLUS',2300)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_ALTA',2100)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_BISHOP',2500)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_CRAVEN',2150)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_DALTA',1900)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_DAYTONA',2050)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_ESSEX',2150)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_FULHAM',2300)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_GLENDON',2350)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_HARLOW',2100)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_JIGGER',2300)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_JUMPER',2300)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_KARNIK',2200)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_LOGAN',2100)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_LUTON',1950)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_LUXOR',2199)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_LUXOR DB',2199)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_NEWLUXOR',2199)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_NORWICH',1999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_SIMBEL',2200)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_STIRLING',2200)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_SWORD',1899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_CELTIC',2750)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_COBALT',5450)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_DUKE',3250)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_EARL',3450)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_ESQUIRE',2800)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_KING PD',4800)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_LORD',2900)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_MAESTRO',2550)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_MASTER',3000)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_RED CAR',5450)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_ROYAL LUX',2250)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_ROYALWHITE',2200)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_TRILEY',2600)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_TUDOR',2800)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Shirt','Sh_VISCOUNT',2600)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Stitch',750)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_0',0)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_1',2429)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_2',2099)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_3',1899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_4',2490)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_5',2299)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_6',2449)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_7',2099)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_8',1849)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_9',1999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_10',2449)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_11',1849)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_12',2299)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_13',1599)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_14',1999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_15',1999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_16',1899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_17',1899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_18',2299)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_19',2099)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_20',1999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_21',2099)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_22',1949)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_23',1899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_24',1899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_25',1899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_26',2099)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_27',2499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_28',2099)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_29',2299)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_30',2499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_31',2399)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_32',2199)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_33',2490)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_34',1750)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_35',1950)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_36',1950)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_37',1699)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_38',1850)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_39',4199)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Trouser','Tr_40',5599)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Stitch',4500)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_0',0)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_1',8999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_2',7299)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_3',6899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_4',8999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_5',7999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_6',8499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_7',7299)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_8',6499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_9',6999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_10',8499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_11',6499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_12',7999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_13',5899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_14',6799)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_15',6699)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_16',6699)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_17',6599)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_18',7999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_19',7299)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_20',6999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_21',7399)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_22',6899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_23',6699)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_24',6699)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_45',6499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_26',7499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_27',8499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_28',7499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_29',8099)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_30',8499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_31',8499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_32',7499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_33',8499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_34',6499)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_35',6999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_36',6999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_37',5899)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_38',6999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_39',13999)");
                database.execSQL("INSERT INTO fabrics (FabricType,FabricCode,FabricPrice)  Values ('Suit','Su_40',19999)");

                getPreferences(MODE_PRIVATE).edit().putBoolean("is_first_run", false).commit();
            }

        }

    }
    public void newRecord(View view) {

        Intent intent = new Intent(this, EnterOrderNumber.class);
        try{

            Cursor c = database.rawQuery("SELECT MAX(OrderId) from orders", null);
            long PrevOrderNumberId;
            if(c.moveToFirst())
            {

                PrevOrderNumberId = c.getLong(c.getColumnIndex("MAX(OrderId)"));
                Cursor c1 = database.rawQuery("SELECT * from orders WHERE OrderId ="+ PrevOrderNumberId, null);
                if(c1.moveToFirst()){
                    PrevOrderNumber = c1.getInt(c1.getColumnIndex("OrderNumber"));
                }
                else
                {
                    PrevOrderNumber = -1;
                }
            }
            else
            {
                PrevOrderNumber = -1;
            }

            database.execSQL("INSERT INTO orders(OrderNumber) values(-1)");

            Cursor c2 = database.rawQuery("SELECT MIN(OrderId) from orders", null);
            int idindex = c2.getColumnIndex("MIN(OrderId)");
            c2.moveToFirst();
            OrderId = c2.getLong(idindex);
            Log.i("Result: OrderId", Long.toString(OrderId));



            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
            int day = now.get(Calendar.DAY_OF_MONTH);
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);
            int second = now.get(Calendar.SECOND);
            long time= System.currentTimeMillis();

            try {
                String sql = "UPDATE orders SET OrderId = "+time+" WHERE OrderId = "+OrderId;
                database.execSQL(sql);

                OrderId = time;

                String sql1 = "UPDATE orders SET OrderDate = '"+year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+"' WHERE OrderId = "+OrderId;
                database.execSQL(sql1);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("PrevOrderNumber1", PrevOrderNumber);

        startActivity(intent);
    }

    public void allRecords(View view) {

        Intent intent = new Intent(this, AllRecords.class);
        startActivity(intent);
    }
    public void viewAdminPanel(View view) {

        Intent intent = new Intent(this, FabricLogin.class);
        startActivity(intent);
    }
}


