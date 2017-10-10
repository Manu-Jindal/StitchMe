package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.lang3.StringUtils;

public class Others extends AppCompatActivity {
    Intent intent;
    long OrderId;
    long CustomerId;
    String source;

    String source1;

    SQLiteDatabase database;
    long OtherId;
    long FabricId;
    
    EditText Type1;
    EditText Price1;
    EditText Description1;
    
    String Type;
    String Price;
    String Description;

    double updatedBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Type1 = (EditText) findViewById(R.id.OthersType);
        Price1 = (EditText) findViewById(R.id.OthersPrice);
        Description1 = (EditText) findViewById(R.id.OthersDescription);
        
        intent = getIntent();

        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");

        source1 = intent.getStringExtra("source11");

        Log.i("OrderId in Others: ", Long.toString(OrderId));
        Log.i("CustomerId in Others:", Long.toString(CustomerId));
        Log.i("Source in Others: ", source);

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        OtherId = intent.getLongExtra("OtherId1", -1);
        Log.i("OtherId in Others: ", Long.toString(OtherId));
    }
    public void createothers(View view)
    {
        Intent intent = new Intent(this, AddItem.class);

        Type = Type1.getText().toString();
        Description = Description1.getText().toString();
        int Price = (StringUtils.isNotBlank(Price1.getText().toString())) ? Integer.parseInt(Price1.getText().toString()) : 0;
        Log.i("Type in Others: ", Type);
        Log.i("Description in Others: ", Description);
        Log.i("Price in Others: ", Integer.toString(Price));


        Cursor c1 = database.rawQuery("SELECT * FROM orders WHERE OrderId ="+OrderId, null);
        c1.moveToFirst();

        double currBill = c1.getDouble(c1.getColumnIndex("BillAmount"));

        updatedBill = currBill + Price;

        try {

            String sql1 = "UPDATE orders SET BillAmount = '"+updatedBill+"' WHERE OrderId = "+OrderId;
            database.execSQL(sql1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try{

            database.execSQL("INSERT INTO fabrics(FabricType, FabricCode, FabricPrice) values('Others', '"+Type+"',"+Price+")");

            Cursor c = database.rawQuery("SELECT MIN(FabricId) from fabrics", null);
            c.moveToFirst();
            FabricId = c.getLong(c.getColumnIndex("MIN(FabricId)"));
            long time= System.currentTimeMillis();

            String sql1 = "UPDATE fabrics SET FabricId = "+time+" WHERE FabricId = "+FabricId;
            database.execSQL(sql1);

            Log.i("Result: FabricId", Long.toString(FabricId));

            database.execSQL("UPDATE others SET FabricId ="+FabricId+" WHERE OtherId ="+OtherId);

            database.execSQL("UPDATE others SET Description = '"+Description+"' WHERE OtherId ="+OtherId);

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);

            intent.putExtra("source11", source1);

            Cursor c4 = database.rawQuery("SELECT * FROM fabrics where FabricId = " + FabricId, null);
            c4.moveToFirst();
            Log.i("Inside Others", c4.getString(c4.getColumnIndex("FabricCode")));


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        startActivity(intent);

    }
}
