package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Suit5 extends AppCompatActivity {

    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    long SuitId;

    String source1;

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suit5);



        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();

        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");
        SuitId = intent.getLongExtra("SuitId1", -1);

        source1 = intent.getStringExtra("source11");

        Log.i("OrderId in Suit5: ", Long.toString(OrderId));
        Log.i("CustomerId in Suit5: ", Long.toString(CustomerId));
        Log.i("SuitId in Suit5: ", Long.toString(SuitId));
        Log.i("Source in Suit5: ", source);


        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

    }
    public void nextActFromMain(View view) {

        Intent intent = new Intent(this, Suit6.class);

        String Suit_VestType;
        Suit_VestType = view.getTag().toString();


        try {

            String sql = "UPDATE suits SET Suit_VestType = '"+Suit_VestType+"' WHERE SuitId = "+SuitId;
            database.execSQL(sql);

            double FabricPrice = 0.0;
            double totalbill = 0.0;
            try
            {
                Cursor c = database.rawQuery("SELECT * from suits, fabrics WHERE suits.FabricId = fabrics.FabricId AND SuitId ="+SuitId, null);
                if(c.moveToFirst())
                {
                    FabricPrice = c.getDouble(c.getColumnIndex("FabricPrice"));
                }
                Cursor c1 = database.rawQuery("SELECT * FROM orders WHERE OrderId ="+ OrderId, null);
                if(c1.moveToFirst())
                {
                    totalbill = c1.getDouble(c1.getColumnIndex("BillAmount"));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            double vestprice = FabricPrice* 0.30;
            if(Suit_VestType.equals("Yes"))
            {

                try
                {
                    String sql1 = "UPDATE suits SET Suit_TypeOfVestPrice = '"+Math.round(vestprice)+"' WHERE SuitId = "+SuitId;
                    database.execSQL(sql1);

                    double updatedbill = Math.round((totalbill+vestprice));
                    String sql2 = "UPDATE orders SET BillAmount = '"+updatedbill+"' WHERE OrderId = "+OrderId;
                    database.execSQL(sql2);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }


            }

            Log.i("OrderId in Suit5: ", Long.toString(OrderId));
            Log.i("CustomerId in Suit5: ", Long.toString(CustomerId));
            Log.i("SuitId in Suit5: ", Long.toString(SuitId));
            Log.i("Source in Suit5: ", source);



            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);
            intent.putExtra("SuitId1", SuitId);

            intent.putExtra("source11", source1);


            Cursor c = database.rawQuery("SELECT * FROM suits WHERE SuitID = "+SuitId, null);
            c.moveToFirst();
            while(c!=null)
            {
                Log.i("Result Suit_VestType:", c.getString(c.getColumnIndex("Suit_VestType")));
                Log.i("Result TypeOfVestPrice:", c.getString(c.getColumnIndex("Suit_TypeOfVestPrice")));
                c.moveToNext();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        startActivity(intent);


    }

}