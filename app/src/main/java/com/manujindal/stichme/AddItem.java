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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AddItem extends AppCompatActivity {
    Intent intent;
    long OrderId;
    long CustomerId;
    String source;
    String source1;

    TextView Heading;

    SQLiteDatabase database;

    ListView checkoutlist;
    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> itemsId = new ArrayList<>();
    ArrayList<String> itemsType = new ArrayList<>();
    ArrayList<String> itemPrice = new ArrayList<>();
    ListViewAdapter arrayAdapter;
    Cursor c;
    TextView totalBillAmount;
    TextView GST;
    TextView expressChargeLabel;
    TextView expressChargeRs;
    TextView expressCharge;
    TextView finalBillAmountTextView;
    double expresschargepercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();

        OrderId = intent.getLongExtra("OrderId1", -1);
        CustomerId = intent.getLongExtra("CustomerId1",-1);
        source = intent.getStringExtra("source1");

        source1 = intent.getStringExtra("source11");

        Log.i("OrderId in AddItems: ", Long.toString(OrderId));
        Log.i("CustomerId in AddItms: ", Long.toString(CustomerId));
        Log.i("Source in AddItems: ", source);

        Log.i("Source1 in AddItems: ", source1);


        Heading = (TextView) findViewById(R.id.AddItemHeading);

        if(source1.equals("checkout"))
        {
            Heading.setText("Add More Items?");
        }
        if(source1.equals("cart"))
        {
            Heading.setText("Your Cart");
        }
        if(source1.equals("finalcart"))
        {
            Heading.setText("Final Cart");
        }

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        totalBillAmount = (TextView) findViewById(R.id.totalBillAmount);
        GST = (TextView) findViewById(R.id.GST);
        expressChargeLabel = (TextView) findViewById(R.id.ExpressChargeLabel);
        expressChargeRs = (TextView) findViewById(R.id.ExpressChargeRs);
        expressCharge = (TextView) findViewById(R.id.ExpressCharge);
        finalBillAmountTextView = (TextView) findViewById(R.id.FinalBillAmount);

        Cursor cursor2 = database.rawQuery("SELECT * FROM orders where OrderId ="+OrderId, null);

        Double billAmountValue = 0.0;
        if(cursor2.moveToFirst()) {
            billAmountValue = cursor2.getDouble(cursor2.getColumnIndex("BillAmount"));
            expresschargepercent = cursor2.getDouble(cursor2.getColumnIndex("ExpressCharge"));
            totalBillAmount.setText(Double.toString(billAmountValue));
            GST.setText(Double.toString(Math.round(0.12*billAmountValue)));
            Log.i("totalBillAmount:" , Double.toString(billAmountValue));
        }
        if(source1.equals("checkout") || source1.equals("cart"))
        {
            expressChargeLabel.setVisibility(View.GONE);
            expressChargeRs.setVisibility(View.GONE);
            expressCharge.setVisibility(View.GONE);

        }
        if(source1.equals("finalcart"))
        {
            expressChargeLabel.setVisibility(View.VISIBLE);
            expressChargeRs.setVisibility(View.VISIBLE);
            expressCharge.setVisibility(View.VISIBLE);
            expressCharge.setText(Double.toString(Math.round(expresschargepercent*billAmountValue*0.01)));
        }
        double finalBillAmount = 1.12* billAmountValue + expresschargepercent*0.01*billAmountValue;
        finalBillAmount = Math.round(finalBillAmount);
        finalBillAmountTextView.setText(Double.toString(Math.round(finalBillAmount)));

        try
        {
            String sqlA1 = "UPDATE orders SET FinalBillAmount = '"+finalBillAmount+"' WHERE OrderId = "+OrderId;
            database.execSQL(sqlA1);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        checkoutlist = (ListView) findViewById(R.id.checkoutList);
        checkoutlist.setNestedScrollingEnabled(true);
        arrayAdapter = new ListViewAdapter(this, items, itemPrice);
        checkoutlist.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        checkoutlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                String idSelected = itemsId.get(position);
                String itemTypeSelected = itemsType.get(position);
                String itemSelected = items.get(position);

                Log.i("idSelected1:", idSelected);
                Log.i("itemTypeSelected1:", itemTypeSelected);
                Log.i("itemSelected1:", itemSelected);

                Intent intent = new Intent(getApplicationContext(), ItemDetails.class);
                
                
                intent.putExtra("idSelected1", idSelected);
                intent.putExtra("itemTypeSelected1", itemTypeSelected);
                intent.putExtra("itemSelected1", itemSelected);

                intent.putExtra("OrderId1", OrderId);
                intent.putExtra("source1", source);
                intent.putExtra("CustomerId1", CustomerId);

                intent.putExtra("source11", source1);

                Log.i("source1 AddItem:", source1);

                Log.i("OrderId in AddItem: ", Long.toString(OrderId));
                Log.i("CustomerId in AddItem: ", Long.toString(CustomerId));
                Log.i("Source in AddItem: ", source);


                startActivity(intent);
            }
        });
        checkoutlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {

                final int itemSelected = i;
                final String idSelected = itemsId.get(i);
                final String itemTypeSelected = itemsType.get(i);

                Log.i("idSelected1:", idSelected);
                Log.i("itemTypeSelected1:", itemTypeSelected);

                new AlertDialog.Builder(AddItem.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Long idToDelete = Long.parseLong(idSelected);
                                Log.i("idToDelete to delete:", Long.toString(idToDelete));
                                if(itemTypeSelected.equals("shirt"))
                                {
                                    String sql = "DELETE FROM shirts WHERE ShirtId = "+idToDelete;
                                    database.execSQL(sql);

                                    items.remove(itemSelected);
                                    itemsId.remove(itemSelected);
                                    itemsType.remove(itemSelected);


                                    double itemPriceSelected = Double.parseDouble(itemPrice.get(itemSelected));
                                    itemPrice.remove(itemSelected);

                                    double billAmount=0;

                                    Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId ="+OrderId, null);
                                    if(cursor1.moveToFirst()) {
                                        billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
                                        Log.i("Curr BillAmount:", Double.toString(billAmount));
                                    }

                                    double updatedBill = billAmount - itemPriceSelected;

                                    try {
                                        String sqlA1 = "UPDATE orders SET BillAmount = '"+updatedBill+"' WHERE OrderId = "+OrderId;
                                        database.execSQL(sqlA1);
                                        totalBillAmount.setText(Double.toString(updatedBill));

                                    }
                                    catch(Exception e)
                                    {
                                        e.printStackTrace();
                                    }


                                    arrayAdapter.notifyDataSetChanged();
                                }
                                if(itemTypeSelected.equals("suit"))
                                {
                                    String sql = "DELETE FROM suits WHERE SuitId = "+idToDelete;
                                    database.execSQL(sql);

                                    items.remove(itemSelected);
                                    itemsId.remove(itemSelected);
                                    itemsType.remove(itemSelected);

                                    double itemPriceSelected = Double.parseDouble(itemPrice.get(itemSelected));
                                    itemPrice.remove(itemSelected);

                                    double billAmount=0;

                                    Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId ="+OrderId, null);
                                    if(cursor1.moveToFirst()) {
                                        billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
                                        Log.i("Curr BillAmount:", Double.toString(billAmount));
                                    }

                                    double updatedBill = billAmount - itemPriceSelected;

                                    try {
                                        String sqlA1 = "UPDATE orders SET BillAmount = '"+updatedBill+"' WHERE OrderId = "+OrderId;
                                        database.execSQL(sqlA1);
                                        totalBillAmount.setText(Double.toString(updatedBill));

                                    }
                                    catch(Exception e)
                                    {
                                        e.printStackTrace();
                                    }


                                    arrayAdapter.notifyDataSetChanged();
                                }
                                if(itemTypeSelected.equals("trouser"))
                                {
                                    String sql = "DELETE FROM trousers WHERE TrouserId = "+idToDelete;
                                    database.execSQL(sql);

                                    items.remove(itemSelected);
                                    itemsId.remove(itemSelected);
                                    itemsType.remove(itemSelected);

                                    double itemPriceSelected = Double.parseDouble(itemPrice.get(itemSelected));
                                    itemPrice.remove(itemSelected);

                                    double billAmount=0;

                                    Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId ="+OrderId, null);
                                    if(cursor1.moveToFirst()) {
                                        billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
                                        Log.i("Curr BillAmount:", Double.toString(billAmount));
                                    }

                                    double updatedBill = billAmount - itemPriceSelected;

                                    try {
                                        String sqlA1 = "UPDATE orders SET BillAmount = '"+updatedBill+"' WHERE OrderId = "+OrderId;
                                        database.execSQL(sqlA1);
                                        totalBillAmount.setText(Double.toString(updatedBill));

                                    }
                                    catch(Exception e)
                                    {
                                        e.printStackTrace();
                                    }

                                    arrayAdapter.notifyDataSetChanged();
                                }
                                if(itemTypeSelected.equals("other"))
                                {
                                    String sql = "DELETE FROM others WHERE OtherId = "+idToDelete;
                                    database.execSQL(sql);

                                    items.remove(itemSelected);
                                    itemsId.remove(itemSelected);
                                    itemsType.remove(itemSelected);


                                    double itemPriceSelected = Double.parseDouble(itemPrice.get(itemSelected));
                                    itemPrice.remove(itemSelected);

                                    double billAmount=0;

                                    Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId ="+OrderId, null);
                                    if(cursor1.moveToFirst()) {
                                        billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
                                        Log.i("Curr BillAmount:", Double.toString(billAmount));
                                    }

                                    double updatedBill = billAmount - itemPriceSelected;

                                    try {
                                        String sqlA1 = "UPDATE orders SET BillAmount = '"+updatedBill+"' WHERE OrderId = "+OrderId;
                                        database.execSQL(sqlA1);
                                        totalBillAmount.setText(Double.toString(updatedBill));

                                    }
                                    catch(Exception e)
                                    {
                                        e.printStackTrace();
                                    }

                                    arrayAdapter.notifyDataSetChanged();
                                }

                                double newGST=0.0, newbillValue=0.0;
                                try{
                                    Cursor cursor11 = database.rawQuery("SELECT * FROM orders WHERE OrderId =" +OrderId, null);
                                    if(cursor11.moveToFirst())
                                    {
                                        double newBill, expresscharge;
                                        newBill = cursor11.getDouble(cursor11.getColumnIndex("BillAmount"));
                                        expresscharge = cursor11.getDouble(cursor11.getColumnIndex("ExpressCharge"));
                                        newGST = Math.round(newBill*0.12);
                                        newbillValue = Math.round(newBill*(1.12 + 0.01*expresscharge));
                                        Log.i("NewGST:", newGST+"");
                                        Log.i("NewbillValue:", newbillValue+"");
                                    }
                                    GST.setText(Double.toString(newGST));
                                    finalBillAmountTextView.setText(Double.toString(newbillValue));

                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true ;
            }
        });


        try {
            Cursor c = database.rawQuery("SELECT * FROM shirts where OrderId =" + OrderId, null);
            int reset = 0;
            int reset1 = 0;
            if (c.moveToFirst()) {
                if (reset == 0) {
                    itemsId.clear();
                    itemsType.clear();
                    reset = 1;
                }
                do {
                    long shirtIdcurrent = c.getLong(c.getColumnIndex("ShirtId"));
                    long fabricId = c.getLong(c.getColumnIndex("FabricId"));
                    if (fabricId != -1) {

                        if (c.getString(c.getColumnIndex("Shirt_SleeveType")).equals("") ||
                                c.getString(c.getColumnIndex("Shirt_PocketType")).equals("") ||
                                c.getString(c.getColumnIndex("Shirt_CuffType")).equals("") ||
                                c.getString(c.getColumnIndex("Shirt_FitType")).equals("") ||
                                c.getString(c.getColumnIndex("Shirt_CollarType")).equals("") ||
                                c.getString(c.getColumnIndex("Shirt_PlacketType")).equals("")) {
                            try {
                                double itemPricetoDelete = 0;

                                double billAmount = 0;

                                Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId =" + OrderId, null);
                                if (cursor1.moveToFirst()) {
                                    billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
                                    Log.i("Curr BillAmount:", Double.toString(billAmount));
                                }

                                Cursor ca1 = database.rawQuery("SELECT * FROM fabrics where FabricId =" + fabricId, null);
                                if (ca1.moveToFirst()) {
                                    itemPricetoDelete = ca1.getDouble(ca1.getColumnIndex("FabricPrice"));
                                    Log.i("itemPricetoDeleted:", Double.toString(itemPricetoDelete));
                                }

                                double updatedBill = billAmount - itemPricetoDelete;

                                try {
                                    String sqlA1 = "UPDATE orders SET BillAmount = '" + updatedBill + "' WHERE OrderId = " + OrderId;
                                    database.execSQL(sqlA1);
                                    totalBillAmount.setText(Double.toString(updatedBill));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                database.execSQL("DELETE FROM shirts where ShirtId = " + shirtIdcurrent);
                                Log.i("Shirt deleted:", Long.toString(shirtIdcurrent));
                                Log.i("Updated Bill:", Double.toString(updatedBill));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            continue;
                        }

                        itemsId.add(c.getString(c.getColumnIndex("ShirtId")));
                        Log.i("fabricId Selected", Long.toString(fabricId));
                        Cursor ca1 = database.rawQuery("SELECT * FROM fabrics where FabricId =" + fabricId, null);
                        if (ca1.moveToFirst()) {
                            String itemPrice1 = ca1.getString(ca1.getColumnIndex("FabricPrice"));
                            itemPrice.add(itemPrice1);
                            Log.i("itemPrice:", itemPrice1);
                        }
                        itemsType.add("shirt");
                    } else {
                        try {

                            database.execSQL("DELETE FROM shirts where ShirtId = " + shirtIdcurrent);
                            Log.i("Shirt deleted:", Long.toString(shirtIdcurrent));

                            double billAmount = 0;

                            Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId =" + OrderId, null);
                            if (cursor1.moveToFirst()) {
                                billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
                                Log.i("Curr BillAmount:", Double.toString(billAmount));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } while (c.moveToNext());

                Cursor crsr = database.rawQuery("SELECT count(ShirtId) FROM shirts where OrderId =" + OrderId, null);
                if (crsr.moveToFirst()) {
                    if (reset1 == 0) {
                        items.clear();
                        reset1 = 1;
                    }
                    int count = Integer.parseInt(crsr.getString(crsr.getColumnIndex("count(ShirtId)")));
                    for (int i = 1; i <= count; i++) {
                        items.add("Shirt" + i);
                    }
                }

                arrayAdapter.notifyDataSetChanged();
            }

            Cursor c1 = database.rawQuery("SELECT * FROM suits where OrderId=" + OrderId, null);
            Log.i("Inside Suit", "No");
            if (c1.moveToFirst()) {
                if (reset == 0) {
                    itemsId.clear();
                    itemsType.clear();
                    reset = 1;
                }
                do {
                    long suitIdcurrent = c1.getLong(c1.getColumnIndex("SuitId"));
                    long fabricId = c1.getLong(c1.getColumnIndex("FabricId"));
                    if (fabricId != -1) {

                        if (c1.getString(c1.getColumnIndex("Suit_FitType")).equals("") ||
                                c1.getString(c1.getColumnIndex("Suit_JacketType")).equals("") ||
                                c1.getString(c1.getColumnIndex("Suit_LapelType")).equals("") ||
                                c1.getString(c1.getColumnIndex("Suit_BottomCutType")).equals("") ||
                                c1.getString(c1.getColumnIndex("Suit_VestType")).equals("") ||
                                c1.getString(c1.getColumnIndex("Suit_PocketType")).equals("") ||
                                c1.getString(c1.getColumnIndex("Suit_SleeveButtonType")).equals("") ||
                                c1.getString(c1.getColumnIndex("Suit_VentType")).equals("") ||
                                (c1.getString(c1.getColumnIndex("Suit_VestType")).equals("Yes") && c1.getString(c1.getColumnIndex("Suit_TypeOfVest")).equals(""))) {
                            try {
                                double billAmount = 0;

                                double itemPricetoDelete = 0;

                                Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId =" + OrderId, null);
                                if (cursor1.moveToFirst()) {
                                    billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
                                    Log.i("Curr BillAmount:", Double.toString(billAmount));
                                }

                                Cursor ca1 = database.rawQuery("SELECT * FROM fabrics where FabricId =" + fabricId, null);
                                if (ca1.moveToFirst()) {
                                    itemPricetoDelete = ca1.getDouble(ca1.getColumnIndex("FabricPrice"));
                                    Log.i("itemPricetoDeleted:", Double.toString(itemPricetoDelete));
                                }

                                if (c1.getString(c1.getColumnIndex("Suit_VestType")).equals("Yes")) {
                                    itemPricetoDelete += c1.getDouble(c1.getColumnIndex("Suit_TypeOfVestPrice"));
                                }

                                double updatedBill = billAmount - itemPricetoDelete;

                                try {
                                    String sqlA1 = "UPDATE orders SET BillAmount = '" + updatedBill + "' WHERE OrderId = " + OrderId;
                                    database.execSQL(sqlA1);
                                    totalBillAmount.setText(Double.toString(updatedBill));
                                    Log.i("Updated Bill:", Double.toString(updatedBill));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                database.execSQL("DELETE FROM suits where SuitId = " + suitIdcurrent);
                                Log.i("Suit deleted:", Long.toString(suitIdcurrent));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            continue;
                        }

                        itemsId.add(c1.getString(c1.getColumnIndex("SuitId")));
                        Log.i("Inside Suit", "Yes");
                        Log.i("fabricId Selected", Long.toString(fabricId));
                        Cursor ca2 = database.rawQuery("SELECT * FROM fabrics where FabricId =" + fabricId, null);
                        Log.i("Inside Suit", "Yes");
                        if (ca2.moveToFirst()) {
                            String itemPrice1 = ca2.getString(ca2.getColumnIndex("FabricPrice"));
                            if (c1.getString(c1.getColumnIndex("Suit_VestType")).equals("Yes")) {
                                itemPrice.add(Integer.toString(ca2.getInt(ca2.getColumnIndex("FabricPrice")) + c1.getInt(c1.getColumnIndex("Suit_TypeOfVestPrice"))));
                                Log.i("itemPrice2:", Integer.toString(ca2.getInt(ca2.getColumnIndex("FabricPrice")) + c1.getInt(c1.getColumnIndex("Suit_TypeOfVestPrice"))));
                            } else {
                                itemPrice.add(itemPrice1);
                                Log.i("itemPrice2:", itemPrice1);
                            }
                        }
                        itemsType.add("suit");
                    } else {
                        try {
                            database.execSQL("DELETE FROM suits where SuitId = " + suitIdcurrent);
                            Log.i("Suit deleted:", Long.toString(suitIdcurrent));

                            double billAmount = 0;

                            Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId =" + OrderId, null);
                            if (cursor1.moveToFirst()) {
                                billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
                                Log.i("Curr BillAmount:", Double.toString(billAmount));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } while (c1.moveToNext());

                Cursor crsr = database.rawQuery("SELECT count(SuitId) FROM suits where OrderId=" + OrderId, null);
                if (crsr.moveToFirst()) {
                    if (reset1 == 0) {
                        items.clear();
                        reset1 = 1;
                    }
                    int count = Integer.parseInt(crsr.getString(crsr.getColumnIndex("count(SuitId)")));
                    for (int i = 1; i <= count; i++) {
                        items.add("Suit" + i);
                    }
                }

                arrayAdapter.notifyDataSetChanged();
            }

            Cursor c2 = database.rawQuery("SELECT * FROM trousers where OrderId=" + OrderId, null);
            if (c2.moveToFirst()) {
                if (reset == 0) {
                    itemsId.clear();
                    itemsType.clear();
                    reset = 1;
                }
                do {
                    long trouserIdcurrent = c2.getLong(c2.getColumnIndex("TrouserId"));
                    long fabricId = c2.getLong(c2.getColumnIndex("FabricId"));
                    if (fabricId != -1) {

                        if (c2.getString(c2.getColumnIndex("Trouser_FitType")).equals("") ||
                                c2.getString(c2.getColumnIndex("Trouser_PantPleatsType")).equals("") ||
                                c2.getString(c2.getColumnIndex("Trouser_PantPocketType")).equals("") ||
                                c2.getString(c2.getColumnIndex("Trouser_BackPocketStyleType")).equals("") ||
                                c2.getString(c2.getColumnIndex("Trouser_BackPocketType")).equals("") ||
                                c2.getString(c2.getColumnIndex("Trouser_BeltLoopsType")).equals("")) {
                            try {
                                double billAmount = 0;

                                double itemPricetoDelete = 0;

                                Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId =" + OrderId, null);
                                if (cursor1.moveToFirst()) {
                                    billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
                                    Log.i("Curr BillAmount:", Double.toString(billAmount));
                                }

                                Cursor ca1 = database.rawQuery("SELECT * FROM fabrics where FabricId =" + fabricId, null);
                                if (ca1.moveToFirst()) {
                                    itemPricetoDelete = ca1.getDouble(ca1.getColumnIndex("FabricPrice"));
                                    Log.i("itemPricetoDeleted:", Double.toString(itemPricetoDelete));
                                }

                                double updatedBill = billAmount - itemPricetoDelete;

                                try {
                                    String sqlA1 = "UPDATE orders SET BillAmount = '" + updatedBill + "' WHERE OrderId = " + OrderId;
                                    database.execSQL(sqlA1);
                                    totalBillAmount.setText(Double.toString(updatedBill));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                database.execSQL("DELETE FROM trousers where TrouserId = " + trouserIdcurrent);
                                Log.i("Trouser deleted:", Long.toString(trouserIdcurrent));
                                Log.i("Updated Bill:", Double.toString(updatedBill));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            continue;
                        }

                        itemsId.add(c2.getString(c2.getColumnIndex("TrouserId")));
                        Cursor ca2 = database.rawQuery("SELECT * FROM fabrics where FabricId =" + fabricId, null);
                        if (ca2.moveToFirst()) {
                            String itemPrice1 = ca2.getString(ca2.getColumnIndex("FabricPrice"));
                            itemPrice.add(itemPrice1);
                            Log.i("itemPrice3:", itemPrice1);
                        }
                        itemsType.add("trouser");
                    } else {
                        try {
                            database.execSQL("DELETE FROM trousers where TrouserId = " + trouserIdcurrent);
                            Log.i("Trouser deleted:", Long.toString(trouserIdcurrent));

                            double billAmount = 0;

                            Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId =" + OrderId, null);
                            if (cursor1.moveToFirst()) {
                                billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
                                Log.i("Curr BillAmount:", Double.toString(billAmount));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } while (c2.moveToNext());

                Cursor crsr = database.rawQuery("SELECT count(TrouserId) FROM trousers where OrderId=" + OrderId, null);
                if (crsr.moveToFirst()) {
                    if (reset1 == 0) {
                        items.clear();
                        reset1 = 1;
                    }
                    int count = Integer.parseInt(crsr.getString(crsr.getColumnIndex("count(TrouserId)")));
                    for (int i = 1; i <= count; i++) {
                        items.add("Trouser" + i);
                    }
                }

                arrayAdapter.notifyDataSetChanged();
            }

            Cursor c3 = database.rawQuery("SELECT * FROM others where OrderId = " + OrderId, null);
            Log.i("Inside Others", "No");
            if (c3.moveToFirst()) {
                if (reset == 0) {
                    itemsId.clear();
                    itemsType.clear();
                    reset = 1;
                }
                do {
                    Log.i("Inside Others", "Yes");
                    long otherIdcurrent = c3.getLong(c3.getColumnIndex("OtherId"));
                    long fabricId = c3.getLong(c3.getColumnIndex("FabricId"));
                    Log.i("FabricId:", Long.toString(fabricId));
                    Cursor c4 = database.rawQuery("SELECT * FROM fabrics where FabricId = " + fabricId, null);
                    Log.i("Inside Others", "Yes1");
                    c4.moveToFirst();

                    if (fabricId != -1) {
                        Log.i("Inside Others", "Yes11");
                        Log.i("Inside Others", c4.getString(c4.getColumnIndex("FabricCode")));
                        Log.i("Inside Others", c3.getString(c3.getColumnIndex("Description")));

                        if (c4.getString(c4.getColumnIndex("FabricCode")).equals("")) {
                            try {
                                Log.i("Inside Others", "Yes12");
                                double billAmount = 0;

                                double itemPricetoDelete = 0;

                                Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId =" + OrderId, null);
                                if (cursor1.moveToFirst()) {
                                    billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
                                    Log.i("Curr BillAmount:", Double.toString(billAmount));
                                }

                                Cursor ca1 = database.rawQuery("SELECT * FROM fabrics where FabricId =" + fabricId, null);
                                if (ca1.moveToFirst()) {
                                    itemPricetoDelete = ca1.getDouble(ca1.getColumnIndex("FabricPrice"));
                                    Log.i("itemPricetoDeleted:", Double.toString(itemPricetoDelete));
                                }

                                double updatedBill = billAmount - itemPricetoDelete;

                                try {
                                    String sqlA1 = "UPDATE orders SET BillAmount = '" + updatedBill + "' WHERE OrderId = " + OrderId;
                                    database.execSQL(sqlA1);
                                    totalBillAmount.setText(Double.toString(updatedBill));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                database.execSQL("DELETE FROM others where OtherId = " + otherIdcurrent);
                                Log.i("Others deleted:", Long.toString(otherIdcurrent));
                                Log.i("Updated Bill:", Double.toString(updatedBill));
                            } catch (Exception e) {
                                Log.i("ERROR","1");
                                e.printStackTrace();
                            }
                            continue;
                        }
                        Log.i("Inside Others", "Yes12");
                        itemsId.add(c3.getString(c3.getColumnIndex("OtherId")));
                        Cursor ca2 = database.rawQuery("SELECT * FROM fabrics where FabricId =" + fabricId, null);
                        if (ca2.moveToFirst()) {
                            String itemPrice1 = ca2.getString(ca2.getColumnIndex("FabricPrice"));
                            itemPrice.add(itemPrice1);
                            Log.i("itemPrice3:", itemPrice1);
                        }
                        itemsType.add("other");
                    } else {

                        Log.i("Inside Others", "Yes111");
                        try {
                            database.execSQL("DELETE FROM others where OtherId = " + otherIdcurrent);
                            Log.i("Others deleted:", Long.toString(otherIdcurrent));

                            double billAmount = 0;

                            Cursor cursor1 = database.rawQuery("SELECT * FROM orders where OrderId =" + OrderId, null);
                            if (cursor1.moveToFirst()) {
                                billAmount = cursor1.getDouble(cursor1.getColumnIndex("BillAmount"));
                                Log.i("Curr BillAmount:", Double.toString(billAmount));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } while (c3.moveToNext());

                Cursor crsr = database.rawQuery("SELECT count(OtherId) FROM others where OrderId=" + OrderId, null);
                if (crsr.moveToFirst()) {
                    if (reset1 == 0) {
                        items.clear();
                        reset1 = 1;
                    }
                    int count = Integer.parseInt(crsr.getString(crsr.getColumnIndex("count(OtherId)")));
                    for (int i = 1; i <= count; i++) {
                        items.add("Other" + i);
                    }
                }

            }
        }
        catch(Exception e)
        {
            Log.i("ERROR","2");
            e.printStackTrace();
        }
    }

    public void addItem(View view) {

        Intent intent = new Intent(this, ChooseItem.class);

        intent.putExtra("OrderId1", OrderId);
        intent.putExtra("source1", source);
        intent.putExtra("CustomerId1", CustomerId);

        intent.putExtra("source11", source1);


        Log.i("OrderId in AddItem: ", Long.toString(OrderId));
        Log.i("CustomerId in AddItem: ", Long.toString(CustomerId));
        Log.i("Source in AddItem: ", source);

        startActivity(intent);
    }

    public void checkout(View view) {


            if (source1.equals("checkout")) {

                Cursor c = database.rawQuery("SELECT * FROM measurements WHERE CustomerId =" + CustomerId, null);

                if (c.moveToFirst()) {

                    Intent intent = new Intent(this, MeasurementChanged.class);

                    long MeasurementsId = c.getLong(c.getColumnIndex("MeasurementsId"));

                    intent.putExtra("OrderId1", OrderId);
                    intent.putExtra("source1", source);
                    intent.putExtra("CustomerId1", CustomerId);
                    intent.putExtra("MeasurementsId1", MeasurementsId);

                    Log.i("OrderId in AddItem: ", Long.toString(OrderId));
                    Log.i("CustomerId in AddItem: ", Long.toString(CustomerId));
                    Log.i("MsrmntsId in AddItem: ", Long.toString(MeasurementsId));
                    Log.i("Source in AddItem: ", source);

                    startActivity(intent);

                } else {

                    Intent intent = new Intent(this, MeasurementCategories.class);

                    try {
                        database.execSQL("INSERT INTO measurements(CustomerId) values(" + CustomerId + ")");

                        Cursor c2 = database.rawQuery("SELECT MIN(MeasurementsId) from measurements", null);
                        int measurementsIdindex = c2.getColumnIndex("MIN(MeasurementsId)");
                        c2.moveToFirst();
                        long MeasurementsId = c2.getLong(measurementsIdindex);
                        long time= System.currentTimeMillis();

                        String sql = "UPDATE measurements SET MeasurementsId = "+time+" WHERE MeasurementsId = "+MeasurementsId;
                        database.execSQL(sql);

                        OrderId = time;


                        Log.i("Result: MeasurementsId", Long.toString(MeasurementsId));

                        intent.putExtra("OrderId1", OrderId);
                        intent.putExtra("source1", source);
                        intent.putExtra("CustomerId1", CustomerId);
                        intent.putExtra("MeasurementsId1", MeasurementsId);

                        intent.putExtra("MeasurementStatus1", 0);

                        Log.i("OrderId in AddItem: ", Long.toString(OrderId));
                        Log.i("CustomerId in AddItem: ", Long.toString(CustomerId));
                        Log.i("MsrmntsId in AddItem: ", Long.toString(MeasurementsId));
                        Log.i("Source in AddItem: ", source);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    startActivity(intent);

                }
            }

            if (source1.equals("cart")) {

                Intent intent = new Intent(this, ReviewRecord.class);

                intent.putExtra("OrderId1", OrderId);
                intent.putExtra("source1", source);
                intent.putExtra("CustomerId1", CustomerId);

                Log.i("OrderId in AddItem: ", Long.toString(OrderId));
                Log.i("CustomerId in AddItem: ", Long.toString(CustomerId));
                Log.i("Source in AddItem: ", source);

                startActivity(intent);
            }
            if (source1.equals("finalcart")) {

            Intent intent = new Intent(this, BillAmount.class);

            intent.putExtra("OrderId1", OrderId);
            intent.putExtra("source1", source);
            intent.putExtra("CustomerId1", CustomerId);

            startActivity(intent);
        }
    }
}
