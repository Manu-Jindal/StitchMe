package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DashboardDate extends AppCompatActivity {
    
    TextView tableheadtoday;
    TextView tableheadtomorrow;
    TextView tableheadthedayafter;


    SQLiteDatabase database;
    
    ExpandableListView tablelist11;
    ExpandableListView tablelist12;
    ExpandableListView tablelist13;
    ExpandableListView tablelist21;
    ExpandableListView tablelist22;
    ExpandableListView tablelist23;
    ExpandableListView tablelist31;
    ExpandableListView tablelist32;
    ExpandableListView tablelist33;

    ExpandableListAdapter expandableListAdapter;

    List<String> Header11;
    HashMap<String, List<String>> OrderNumberMap11;
    HashMap<String, List<String>> OrderIdMap11;
    HashMap<String, List<String>> OrderNameMap11;
    HashMap<String, List<String>> OrderStatusMap11;
    List<String> OrderNumberList11;
    List<String> OrderIdList11;
    List<String> OrderNameList11;
    List<String> OrderStatusList11;


    List<String> Header21;
    HashMap<String, List<String>> OrderNumberMap21;
    HashMap<String, List<String>> OrderIdMap21;
    HashMap<String, List<String>> OrderNameMap21;
    HashMap<String, List<String>> OrderStatusMap21;
    List<String> OrderNumberList21;
    List<String> OrderIdList21;
    List<String> OrderNameList21;
    List<String> OrderStatusList21;


    List<String> Header31;
    HashMap<String, List<String>> OrderNumberMap31;
    HashMap<String, List<String>> OrderIdMap31;
    HashMap<String, List<String>> OrderNameMap31;
    HashMap<String, List<String>> OrderStatusMap31;
    List<String> OrderNumberList31;
    List<String> OrderIdList31;
    List<String> OrderNameList31;
    List<String> OrderStatusList31;



    List<String> Header12;
    HashMap<String, List<String>> OrderNumberMap12;
    HashMap<String, List<String>> OrderIdMap12;
    HashMap<String, List<String>> OrderNameMap12;
    HashMap<String, List<String>> OrderStatusMap12;
    List<String> OrderNumberList12;
    List<String> OrderIdList12;
    List<String> OrderNameList12;
    List<String> OrderStatusList12;


    List<String> Header22;
    HashMap<String, List<String>> OrderNumberMap22;
    HashMap<String, List<String>> OrderIdMap22;
    HashMap<String, List<String>> OrderNameMap22;
    HashMap<String, List<String>> OrderStatusMap22;
    List<String> OrderNumberList22;
    List<String> OrderIdList22;
    List<String> OrderNameList22;
    List<String> OrderStatusList22;


    List<String> Header32;
    HashMap<String, List<String>> OrderNumberMap32;
    HashMap<String, List<String>> OrderIdMap32;
    HashMap<String, List<String>> OrderNameMap32;
    HashMap<String, List<String>> OrderStatusMap32;
    List<String> OrderNumberList32;
    List<String> OrderIdList32;
    List<String> OrderNameList32;
    List<String> OrderStatusList32;


    List<String> Header13;
    HashMap<String, List<String>> OrderNumberMap13;
    HashMap<String, List<String>> OrderIdMap13;
    HashMap<String, List<String>> OrderNameMap13;
    HashMap<String, List<String>> OrderStatusMap13;
    List<String> OrderNumberList13;
    List<String> OrderIdList13;
    List<String> OrderNameList13;
    List<String> OrderStatusList13;


    List<String> Header23;
    HashMap<String, List<String>> OrderNumberMap23;
    HashMap<String, List<String>> OrderIdMap23;
    HashMap<String, List<String>> OrderNameMap23;
    HashMap<String, List<String>> OrderStatusMap23;
    List<String> OrderNumberList23;
    List<String> OrderIdList23;
    List<String> OrderNameList23;
    List<String> OrderStatusList23;


    List<String> Header33;
    HashMap<String, List<String>> OrderNumberMap33;
    HashMap<String, List<String>> OrderIdMap33;
    HashMap<String, List<String>> OrderNameMap33;
    HashMap<String, List<String>> OrderStatusMap33;
    List<String> OrderNumberList33;
    List<String> OrderIdList33;
    List<String> OrderNameList33;
    List<String> OrderStatusList33;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_date);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        Header11 = new ArrayList<>();
        OrderNumberMap11 = new HashMap<>();
        OrderIdMap11 = new HashMap<>();
        OrderNameMap11 = new HashMap<>();
        OrderStatusMap11 = new HashMap<>();
        OrderNumberList11 = new ArrayList<>();
        OrderIdList11 = new ArrayList<>();
        OrderNameList11 = new ArrayList<>();
        OrderStatusList11 = new ArrayList<>();


        Header21 = new ArrayList<>();
        OrderNumberMap21 = new HashMap<>();
        OrderIdMap21 = new HashMap<>();
        OrderNameMap21 = new HashMap<>();
        OrderStatusMap21 = new HashMap<>();
        OrderNumberList21 = new ArrayList<>();
        OrderIdList21 = new ArrayList<>();
        OrderNameList21 = new ArrayList<>();
        OrderStatusList21 = new ArrayList<>();


        Header31 = new ArrayList<>();
        OrderNumberMap31 = new HashMap<>();
        OrderStatusMap31 = new HashMap<>();
        OrderIdMap31 = new HashMap<>();
        OrderNameMap31 = new HashMap<>();
        OrderNumberList31 = new ArrayList<>();
        OrderIdList31 = new ArrayList<>();
        OrderNameList31 = new ArrayList<>();
        OrderStatusList31 = new ArrayList<>();



        Header12 = new ArrayList<>();
        OrderNumberMap12 = new HashMap<>();
        OrderIdMap12 = new HashMap<>();
        OrderNameMap12 = new HashMap<>();
        OrderStatusMap12 = new HashMap<>();
        OrderNumberList12 = new ArrayList<>();
        OrderIdList12 = new ArrayList<>();
        OrderNameList12 = new ArrayList<>();
        OrderStatusList12 = new ArrayList<>();


        Header22 = new ArrayList<>();
        OrderNumberMap22 = new HashMap<>();
        OrderIdMap22 = new HashMap<>();
        OrderNameMap22 = new HashMap<>();
        OrderStatusMap22 = new HashMap<>();
        OrderNumberList22 = new ArrayList<>();
        OrderIdList22 = new ArrayList<>();
        OrderNameList22 = new ArrayList<>();
        OrderStatusList22 = new ArrayList<>();


        Header32 = new ArrayList<>();
        OrderNumberMap32 = new HashMap<>();
        OrderIdMap32 = new HashMap<>();
        OrderNameMap32 = new HashMap<>();
        OrderStatusMap32 = new HashMap<>();
        OrderNumberList32 = new ArrayList<>();
        OrderIdList32 = new ArrayList<>();
        OrderNameList32 = new ArrayList<>();
        OrderStatusList32 = new ArrayList<>();



        Header13 = new ArrayList<>();
        OrderNumberMap13 = new HashMap<>();
        OrderIdMap13 = new HashMap<>();
        OrderNameMap13 = new HashMap<>();
        OrderStatusMap13 = new HashMap<>();
        OrderNumberList13 = new ArrayList<>();
        OrderIdList13 = new ArrayList<>();
        OrderNameList13 = new ArrayList<>();
        OrderStatusList13 = new ArrayList<>();


        Header23 = new ArrayList<>();
        OrderNumberMap23 = new HashMap<>();
        OrderIdMap23 = new HashMap<>();
        OrderNameMap23 = new HashMap<>();
        OrderStatusMap23 = new HashMap<>();
        OrderNumberList23 = new ArrayList<>();
        OrderIdList23 = new ArrayList<>();
        OrderNameList23 = new ArrayList<>();
        OrderStatusList23 = new ArrayList<>();


        Header33 = new ArrayList<>();
        OrderNumberMap33 = new HashMap<>();
        OrderIdMap33 = new HashMap<>();
        OrderNameMap33 = new HashMap<>();
        OrderStatusMap33 = new HashMap<>();
        OrderNumberList33 = new ArrayList<>();
        OrderIdList33 = new ArrayList<>();
        OrderNameList33 = new ArrayList<>();
        OrderStatusList33 = new ArrayList<>();


        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);
        
        tableheadtoday = (TextView) findViewById(R.id.tablehead01);
        tableheadtomorrow = (TextView) findViewById(R.id.tablehead02);
        tableheadthedayafter = (TextView) findViewById(R.id.tablehead03);
        
        tablelist11 = (ExpandableListView) findViewById(R.id.tablelist11);
        tablelist12 = (ExpandableListView) findViewById(R.id.tablelist12);
        tablelist13 = (ExpandableListView) findViewById(R.id.tablelist13);
        tablelist21 = (ExpandableListView) findViewById(R.id.tablelist21);
        tablelist22 = (ExpandableListView) findViewById(R.id.tablelist22);
        tablelist23 = (ExpandableListView) findViewById(R.id.tablelist23);
        tablelist31 = (ExpandableListView) findViewById(R.id.tablelist31);
        tablelist32 = (ExpandableListView) findViewById(R.id.tablelist32);
        tablelist33 = (ExpandableListView) findViewById(R.id.tablelist33);
        
        Intent intent1 = getIntent();
        int year = intent1.getIntExtra("year1", -1);
        int month = intent1.getIntExtra("month1", -1);
        int date = intent1.getIntExtra("date1", -1);

        //today dates
        Calendar now = Calendar.getInstance();

        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, month);
        now.set(Calendar.DAY_OF_MONTH, date);

        int yeartoday = now.get(Calendar.YEAR);
        int monthtoday = now.get(Calendar.MONTH);
        int daytoday = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        //tomorrow dates
        now.add(Calendar.DATE, 1);
        int  yeartomorrow= now.get(Calendar.YEAR);
        int monthtomorrow = now.get(Calendar.MONTH);
        int  daytomorrow= now.get(Calendar.DAY_OF_MONTH);
        //the day after dates
        now.add(Calendar.DATE, 1);
        int yearthedayafter = now.get(Calendar.YEAR);
        int monththedayafter = now.get(Calendar.MONTH);
        int daythedayafter= now.get(Calendar.DAY_OF_MONTH);


        //the day after dates
        now.add(Calendar.DATE, 1);
        int year3daysafter = now.get(Calendar.YEAR);
        int month3daysafter = now.get(Calendar.MONTH);
        int day3daysafter= now.get(Calendar.DAY_OF_MONTH);

        Log.i("Date today:", daytoday+"/"+monthtoday+"/"+yeartoday);
        Log.i("Date tomorrow:", daytomorrow+"/"+monthtomorrow+"/"+yeartomorrow);
        Log.i("Date thedayafter:", daythedayafter+"/"+monththedayafter+"/"+yearthedayafter);
        Log.i("Date 3daysafter:", day3daysafter+"/"+month3daysafter+"/"+year3daysafter);


        tableheadtoday.setText(daytoday+"/"+monthtoday+"/"+yeartoday);
        tableheadtomorrow.setText(daytomorrow+"/"+monthtomorrow+"/"+yeartomorrow);
        tableheadthedayafter.setText(daythedayafter+"/"+monththedayafter+"/"+yearthedayafter);


        //listview11
            Header11.add("Order");
            try {
                Log.i("Header11:", "Yes");
                Cursor c = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId =  customers.CustomerId", null);
                Log.i("Header11:", "Yes");
                if(c.moveToFirst())
                {
                    Log.i("Header11:", "Yes");
                    do{
                        String orderNumber = c.getString(c.getColumnIndex("OrderNumber"));
                        String OrderId = c.getString(c.getColumnIndex("OrderId"));
                        String name = c.getString(c.getColumnIndex("Name"));
                        String OrderDate = c.getString(c.getColumnIndex("OrderDate"));
                        Log.i("Order Date:", OrderDate);

                        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(OrderDate);
                        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(yeartoday+"-"+monthtoday+"-"+daytoday+" 00:00:00");
                        Date date3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(yeartomorrow+"-"+monthtomorrow+"-"+daytomorrow+" 00:00:00");
                        if(date1.compareTo(date2)>0 && date1.compareTo(date3)<0)
                        {

                            Log.i("Order Id:", OrderId);
                            Log.i("Order Number:", orderNumber);
                            Log.i("Name:", name);
                            String currorderStatus = c.getString(c.getColumnIndex("OrderStatus"));
                            Log.i("currorderStatus:", currorderStatus);

                            OrderStatusList11.add(currorderStatus);
                            OrderNumberList11.add(orderNumber);
                            OrderIdList11.add(OrderId);
                            OrderNameList11.add(name);
                        }

                    }while(c.moveToNext());
                }
                c.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            OrderNumberMap11.put(Header11.get(0), OrderNumberList11);
            OrderIdMap11.put(Header11.get(0), OrderIdList11);
            OrderNameMap11.put(Header11.get(0), OrderNameList11);
            OrderStatusMap11.put(Header11.get(0), OrderStatusList11);

            expandableListAdapter = new ExpandableListAdapter(this, Header11, OrderNumberMap11, OrderIdMap11, OrderNameMap11, OrderStatusMap11);
            tablelist11.setAdapter(expandableListAdapter);

            tablelist11.setNestedScrollingEnabled(true);

            tablelist11.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    int orderIdselected = Integer.parseInt(OrderIdList11.get(childPosition));
                    int orderNumberSelected = Integer.parseInt(OrderNumberList11.get(childPosition));
                    Log.i("orderIdselected:", Integer.toString(orderIdselected));
                    Log.i("orderNumberSelected:", Integer.toString(orderNumberSelected));
                    Intent intent = new Intent(getApplicationContext(), DetailedRecord.class);
                    intent.putExtra("OrderId1", orderIdselected);
                    intent.putExtra("source1", "dashboardDate");
                    startActivity(intent);
                    return false;
                }
            });
            /*

            tablelist11.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    int height = 0;
                    for (int i = 0; i < tablelist11.getChildCount(); i++) {
                        height += tablelist11.getChildAt(i).getMeasuredHeight();
                        height += tablelist11.getDividerHeight();
                    }
                    tablelist11.getLayoutParams().height = (height)*10;
                }
            });

            // Listview Group collapsed listener
            tablelist11.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    tablelist11.getLayoutParams().height = 160;
                }
            }); */


        //listview21
            Header21.add("Trial");
            try {
                Cursor c = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId =  customers.CustomerId AND TrialDate = '"+yeartoday+"-"+monthtoday+"-"+daytoday+"'", null);
                if(c.moveToFirst())
                {
                    do{
                        String orderNumber = c.getString(c.getColumnIndex("OrderNumber"));
                        String OrderId = c.getString(c.getColumnIndex("OrderId"));
                        String name = c.getString(c.getColumnIndex("Name"));
                        Log.i("Order Number:", orderNumber);
                        Log.i("Order Id:", OrderId);
                        Log.i("Name:", name);
                        String currorderStatus = c.getString(c.getColumnIndex("OrderStatus"));
                        Log.i("currorderStatus:", currorderStatus);
                        OrderStatusList21.add(currorderStatus);
                        OrderNumberList21.add(orderNumber);
                        OrderIdList21.add(OrderId);
                        OrderNameList21.add(name);
                    }while(c.moveToNext());
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            OrderNumberMap21.put(Header21.get(0), OrderNumberList21);
            OrderIdMap21.put(Header21.get(0), OrderIdList21);
            OrderNameMap21.put(Header21.get(0), OrderNameList21);
            OrderStatusMap21.put(Header21.get(0), OrderStatusList21);

            expandableListAdapter = new ExpandableListAdapter(this, Header21, OrderNumberMap21, OrderIdMap21, OrderNameMap21, OrderStatusMap21);
            tablelist21.setAdapter(expandableListAdapter);

            tablelist21.setNestedScrollingEnabled(true);

            tablelist21.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    int orderIdselected = Integer.parseInt(OrderIdList21.get(childPosition));
                    int orderNumberSelected = Integer.parseInt(OrderNumberList21.get(childPosition));
                    Log.i("orderIdselected:", Integer.toString(orderIdselected));
                    Log.i("orderNumberSelected:", Integer.toString(orderNumberSelected));
                    Intent intent = new Intent(getApplicationContext(), DetailedRecord.class);
                    intent.putExtra("OrderId1", orderIdselected);
                    intent.putExtra("source1", "dashboardDate");
                    startActivity(intent);
                    return false;
                }
            });
            /*
            tablelist21.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    int height = 0;
                    for (int i = 0; i < tablelist21.getChildCount(); i++) {
                        height += tablelist21.getChildAt(i).getMeasuredHeight();
                        height += tablelist21.getDividerHeight();
                    }
                    tablelist21.getLayoutParams().height = (height)*10;
                }
            });

            // Listview Group collapsed listener
            tablelist21.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    tablelist21.getLayoutParams().height = 160;
                }
            });
            */

        //listview31
            Header31.add("Delivery");
            try {
                Cursor c = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId =  customers.CustomerId AND DeliveryDate = '"+yeartoday+"-"+monthtoday+"-"+daytoday+"'", null);
                if(c.moveToFirst())
                {
                    do{
                        String orderNumber = c.getString(c.getColumnIndex("OrderNumber"));
                        String OrderId = c.getString(c.getColumnIndex("OrderId"));
                        String name = c.getString(c.getColumnIndex("Name"));
                        Log.i("Order Number:", orderNumber);
                        Log.i("Order Id:", OrderId);
                        Log.i("Name:", name);
                        String currorderStatus = c.getString(c.getColumnIndex("OrderStatus"));
                        Log.i("currorderStatus:", currorderStatus);
                        OrderStatusList31.add(currorderStatus);
                        OrderNumberList31.add(orderNumber);
                        OrderIdList31.add(OrderId);
                        OrderNameList31.add(name);
                    }while(c.moveToNext());
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            OrderNumberMap31.put(Header31.get(0), OrderNumberList31);
            OrderIdMap31.put(Header31.get(0), OrderIdList31);
            OrderNameMap31.put(Header31.get(0), OrderNameList31);
            OrderStatusMap31.put(Header31.get(0), OrderStatusList31);

            expandableListAdapter = new ExpandableListAdapter(this, Header31, OrderNumberMap31, OrderIdMap31, OrderNameMap31, OrderStatusMap31);
            tablelist31.setAdapter(expandableListAdapter);

            tablelist31.setNestedScrollingEnabled(true);

            tablelist31.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    int orderIdselected = Integer.parseInt(OrderIdList31.get(childPosition));
                    int orderNumberSelected = Integer.parseInt(OrderNumberList31.get(childPosition));
                    Log.i("orderIdselected:", Integer.toString(orderIdselected));
                    Log.i("orderNumberSelected:", Integer.toString(orderNumberSelected));
                    Intent intent = new Intent(getApplicationContext(), DetailedRecord.class);
                    intent.putExtra("OrderId1", orderIdselected);
                    intent.putExtra("source1", "dashboardDate");
                    startActivity(intent);
                    return false;
                }
            });

            /*

            tablelist31.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    int height = 0;
                    for (int i = 0; i < tablelist31.getChildCount(); i++) {
                        height += tablelist31.getChildAt(i).getMeasuredHeight();
                        height += tablelist31.getDividerHeight();
                    }
                    tablelist31.getLayoutParams().height = (height)*10;
                }
            });

            // Listview Group collapsed listener
            tablelist31.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    tablelist31.getLayoutParams().height = 160;
                }
            });
            */

        //listview12
        Header12.add("Order");
        try {
            Cursor c = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId =  customers.CustomerId", null);
            if(c.moveToFirst())
            {
                do{
                    String orderNumber = c.getString(c.getColumnIndex("OrderNumber"));
                    String OrderId = c.getString(c.getColumnIndex("OrderId"));
                    String name = c.getString(c.getColumnIndex("Name"));
                    String OrderDate = c.getString(c.getColumnIndex("OrderDate"));
                    Log.i("Order Date:", OrderDate);
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(OrderDate);
                    Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(yeartomorrow+"-"+monthtomorrow+"-"+daytomorrow+" 00:00:00");
                    Date date3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(yearthedayafter+"-"+monththedayafter+"-"+daythedayafter+" 00:00:00");

                    if(date1.compareTo(date2)>0 && date1.compareTo(date3)<0) {
                        Log.i("Order Number:", orderNumber);
                        Log.i("Order Id:", OrderId);
                        Log.i("Name:", name);
                        String currorderStatus = c.getString(c.getColumnIndex("OrderStatus"));
                        Log.i("currorderStatus:", currorderStatus);
                        OrderStatusList12.add(currorderStatus);
                        OrderNumberList12.add(orderNumber);
                        OrderIdList12.add(OrderId);
                        OrderNameList12.add(name);
                    }
                }while(c.moveToNext());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        OrderNumberMap12.put(Header12.get(0), OrderNumberList12);
        OrderIdMap12.put(Header12.get(0), OrderIdList12);
        OrderNameMap12.put(Header12.get(0), OrderNameList12);
        OrderStatusMap12.put(Header12.get(0), OrderStatusList12);

        expandableListAdapter = new ExpandableListAdapter(this, Header12, OrderNumberMap12, OrderIdMap12, OrderNameMap12, OrderStatusMap12);
        tablelist12.setAdapter(expandableListAdapter);

        tablelist12.setNestedScrollingEnabled(true);

        tablelist12.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int orderIdselected = Integer.parseInt(OrderIdList12.get(childPosition));
                int orderNumberSelected = Integer.parseInt(OrderNumberList12.get(childPosition));
                Log.i("orderIdselected:", Integer.toString(orderIdselected));
                Log.i("orderNumberSelected:", Integer.toString(orderNumberSelected));
                Intent intent = new Intent(getApplicationContext(), DetailedRecord.class);
                intent.putExtra("OrderId1", orderIdselected);
                intent.putExtra("source1", "dashboardDate");
                startActivity(intent);
                return false;
            }
        });
            /*

            tablelist12.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    int height = 0;
                    for (int i = 0; i < tablelist12.getChildCount(); i++) {
                        height += tablelist12.getChildAt(i).getMeasuredHeight();
                        height += tablelist12.getDividerHeight();
                    }
                    tablelist12.getLayoutParams().height = (height)*10;
                }
            });

            // Listview Group collapsed listener
            tablelist12.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    tablelist12.getLayoutParams().height = 160;
                }
            }); */


        //listview22
        Header22.add("Trial");
        try {
            Cursor c = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId =  customers.CustomerId AND TrialDate = '"+yeartomorrow+"-"+monthtomorrow+"-"+daytomorrow+"'", null);
            if(c.moveToFirst())
            {
                do{
                    String orderNumber = c.getString(c.getColumnIndex("OrderNumber"));
                    String OrderId = c.getString(c.getColumnIndex("OrderId"));
                    String name = c.getString(c.getColumnIndex("Name"));
                    Log.i("Order Number:", orderNumber);
                    Log.i("Order Id:", OrderId);
                    Log.i("Name:", name);
                    String currorderStatus = c.getString(c.getColumnIndex("OrderStatus"));
                    Log.i("currorderStatus:", currorderStatus);
                    OrderStatusList22.add(currorderStatus);
                    OrderNumberList22.add(orderNumber);
                    OrderIdList22.add(OrderId);
                    OrderNameList22.add(name);
                }while(c.moveToNext());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        OrderNumberMap22.put(Header22.get(0), OrderNumberList22);
        OrderIdMap22.put(Header22.get(0), OrderIdList22);
        OrderNameMap22.put(Header22.get(0), OrderNameList22);
        OrderStatusMap22.put(Header22.get(0), OrderStatusList22);

        expandableListAdapter = new ExpandableListAdapter(this, Header22, OrderNumberMap22, OrderIdMap22, OrderNameMap22, OrderStatusMap22);
        tablelist22.setAdapter(expandableListAdapter);

        tablelist22.setNestedScrollingEnabled(true);

        tablelist22.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int orderIdselected = Integer.parseInt(OrderIdList22.get(childPosition));
                int orderNumberSelected = Integer.parseInt(OrderNumberList22.get(childPosition));
                Log.i("orderIdselected:", Integer.toString(orderIdselected));
                Log.i("orderNumberSelected:", Integer.toString(orderNumberSelected));
                Intent intent = new Intent(getApplicationContext(), DetailedRecord.class);
                intent.putExtra("OrderId1", orderIdselected);
                intent.putExtra("source1", "dashboardDate");
                startActivity(intent);
                return false;
            }
        });
            /*
            tablelist22.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    int height = 0;
                    for (int i = 0; i < tablelist22.getChildCount(); i++) {
                        height += tablelist22.getChildAt(i).getMeasuredHeight();
                        height += tablelist22.getDividerHeight();
                    }
                    tablelist22.getLayoutParams().height = (height)*10;
                }
            });

            // Listview Group collapsed listener
            tablelist22.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    tablelist22.getLayoutParams().height = 160;
                }
            });
            */


        //listview32
        Header32.add("Delivery");
        try {
            Cursor c = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId =  customers.CustomerId AND DeliveryDate = '"+yeartomorrow+"-"+monthtomorrow+"-"+daytomorrow+"'", null);
            if(c.moveToFirst())
            {
                do{
                    String orderNumber = c.getString(c.getColumnIndex("OrderNumber"));
                    String OrderId = c.getString(c.getColumnIndex("OrderId"));
                    String name = c.getString(c.getColumnIndex("Name"));
                    Log.i("Order Number:", orderNumber);
                    Log.i("Order Id:", OrderId);
                    Log.i("Name:", name);
                    String currorderStatus = c.getString(c.getColumnIndex("OrderStatus"));
                    Log.i("currorderStatus:", currorderStatus);
                    OrderStatusList32.add(currorderStatus);
                    OrderNumberList32.add(orderNumber);
                    OrderIdList32.add(OrderId);
                    OrderNameList32.add(name);
                }while(c.moveToNext());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        OrderNumberMap32.put(Header32.get(0), OrderNumberList32);
        OrderIdMap32.put(Header32.get(0), OrderIdList32);
        OrderNameMap32.put(Header32.get(0), OrderNameList32);
        OrderStatusMap32.put(Header32.get(0), OrderStatusList32);

        expandableListAdapter = new ExpandableListAdapter(this, Header32, OrderNumberMap32, OrderIdMap32, OrderNameMap32, OrderStatusMap32);
        tablelist32.setAdapter(expandableListAdapter);

        tablelist32.setNestedScrollingEnabled(true);

        tablelist32.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int orderIdselected = Integer.parseInt(OrderIdList32.get(childPosition));
                int orderNumberSelected = Integer.parseInt(OrderNumberList32.get(childPosition));
                Log.i("orderIdselected:", Integer.toString(orderIdselected));
                Log.i("orderNumberSelected:", Integer.toString(orderNumberSelected));
                Intent intent = new Intent(getApplicationContext(), DetailedRecord.class);
                intent.putExtra("OrderId1", orderIdselected);
                intent.putExtra("source1", "dashboardDate");
                startActivity(intent);
                return false;
            }
        });

            /*

            tablelist32.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    int height = 0;
                    for (int i = 0; i < tablelist32.getChildCount(); i++) {
                        height += tablelist32.getChildAt(i).getMeasuredHeight();
                        height += tablelist32.getDividerHeight();
                    }
                    tablelist32.getLayoutParams().height = (height)*10;
                }
            });

            // Listview Group collapsed listener
            tablelist32.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    tablelist32.getLayoutParams().height = 160;
                }
            });
            */


        //listview13
        Header13.add("Order");
        try {
            Cursor c = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId =  customers.CustomerId", null);
            if(c.moveToFirst())
            {
                do{
                    String orderNumber = c.getString(c.getColumnIndex("OrderNumber"));
                    String OrderId = c.getString(c.getColumnIndex("OrderId"));
                    String name = c.getString(c.getColumnIndex("Name"));
                    String OrderDate = c.getString(c.getColumnIndex("OrderDate"));
                    Log.i("Order Date:", OrderDate);
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(OrderDate);
                    Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(yearthedayafter+"-"+monththedayafter+"-"+daythedayafter+" 00:00:00");
                    Date date3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(year3daysafter+"-"+month3daysafter+"-"+day3daysafter+" 00:00:00");

                    if(date1.compareTo(date2)>0 && date1.compareTo(date3)<0) {
                        Log.i("Order Number:", orderNumber);
                        Log.i("Order Id:", OrderId);
                        Log.i("Name:", name);
                        String currorderStatus = c.getString(c.getColumnIndex("OrderStatus"));
                        Log.i("currorderStatus:", currorderStatus);
                        OrderStatusList13.add(currorderStatus);
                        OrderNumberList13.add(orderNumber);
                        OrderIdList13.add(OrderId);
                        OrderNameList13.add(name);
                    }
                }while(c.moveToNext());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        OrderNumberMap13.put(Header13.get(0), OrderNumberList13);
        OrderIdMap13.put(Header13.get(0), OrderIdList13);
        OrderNameMap13.put(Header13.get(0), OrderNameList13);
        OrderStatusMap13.put(Header13.get(0), OrderStatusList13);

        expandableListAdapter = new ExpandableListAdapter(this, Header13, OrderNumberMap13, OrderIdMap13, OrderNameMap13, OrderStatusMap13);
        tablelist13.setAdapter(expandableListAdapter);

        tablelist13.setNestedScrollingEnabled(true);

        tablelist13.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int orderIdselected = Integer.parseInt(OrderIdList13.get(childPosition));
                int orderNumberSelected = Integer.parseInt(OrderNumberList13.get(childPosition));
                Log.i("orderIdselected:", Integer.toString(orderIdselected));
                Log.i("orderNumberSelected:", Integer.toString(orderNumberSelected));
                Intent intent = new Intent(getApplicationContext(), DetailedRecord.class);
                intent.putExtra("OrderId1", orderIdselected);
                intent.putExtra("source1", "dashboardDate");
                startActivity(intent);
                return false;
            }
        });
            /*

            tablelist13.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    int height = 0;
                    for (int i = 0; i < tablelist13.getChildCount(); i++) {
                        height += tablelist13.getChildAt(i).getMeasuredHeight();
                        height += tablelist13.getDividerHeight();
                    }
                    tablelist13.getLayoutParams().height = (height)*10;
                }
            });

            // Listview Group collapsed listener
            tablelist13.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    tablelist13.getLayoutParams().height = 160;
                }
            }); */


        //listview23
        Header23.add("Trial");
        try {
            Cursor c = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId =  customers.CustomerId AND TrialDate = '"+yearthedayafter+"-"+monththedayafter+"-"+daythedayafter+"'", null);
            if(c.moveToFirst())
            {
                do{
                    String orderNumber = c.getString(c.getColumnIndex("OrderNumber"));
                    String OrderId = c.getString(c.getColumnIndex("OrderId"));
                    String name = c.getString(c.getColumnIndex("Name"));
                    Log.i("Order Number:", orderNumber);
                    Log.i("Order Id:", OrderId);
                    Log.i("Name:", name);
                    String currorderStatus = c.getString(c.getColumnIndex("OrderStatus"));
                    Log.i("currorderStatus:", currorderStatus);
                    OrderStatusList23.add(currorderStatus);
                    OrderNumberList23.add(orderNumber);
                    OrderIdList23.add(OrderId);
                    OrderNameList23.add(name);
                }while(c.moveToNext());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        OrderNumberMap23.put(Header23.get(0), OrderNumberList23);
        OrderIdMap23.put(Header23.get(0), OrderIdList23);
        OrderNameMap23.put(Header23.get(0), OrderNameList23);
        OrderStatusMap23.put(Header23.get(0), OrderStatusList23);

        expandableListAdapter = new ExpandableListAdapter(this, Header23, OrderNumberMap23, OrderIdMap23, OrderNameMap23, OrderStatusMap23);
        tablelist23.setAdapter(expandableListAdapter);

        tablelist23.setNestedScrollingEnabled(true);

        tablelist23.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int orderIdselected = Integer.parseInt(OrderIdList23.get(childPosition));
                int orderNumberSelected = Integer.parseInt(OrderNumberList23.get(childPosition));
                Log.i("orderIdselected:", Integer.toString(orderIdselected));
                Log.i("orderNumberSelected:", Integer.toString(orderNumberSelected));
                Intent intent = new Intent(getApplicationContext(), DetailedRecord.class);
                intent.putExtra("OrderId1", orderIdselected);
                intent.putExtra("source1", "dashboardDate");
                startActivity(intent);
                return false;
            }
        });
            /*
            tablelist23.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    int height = 0;
                    for (int i = 0; i < tablelist23.getChildCount(); i++) {
                        height += tablelist23.getChildAt(i).getMeasuredHeight();
                        height += tablelist23.getDividerHeight();
                    }
                    tablelist23.getLayoutParams().height = (height)*10;
                }
            });

            // Listview Group collapsed listener
            tablelist23.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    tablelist23.getLayoutParams().height = 160;
                }
            });
            */


        //listview33
        Header33.add("Delivery");
        try {
            Cursor c = database.rawQuery("SELECT * FROM orders, customers WHERE orders.CustomerId =  customers.CustomerId AND DeliveryDate = '"+yearthedayafter+"-"+monththedayafter+"-"+daythedayafter+"'", null);
            if(c.moveToFirst())
            {
                do{
                    String orderNumber = c.getString(c.getColumnIndex("OrderNumber"));
                    String OrderId = c.getString(c.getColumnIndex("OrderId"));
                    String name = c.getString(c.getColumnIndex("Name"));
                    Log.i("Order Number:", orderNumber);
                    Log.i("Order Id:", OrderId);
                    Log.i("Name:", name);
                    String currorderStatus = c.getString(c.getColumnIndex("OrderStatus"));
                    Log.i("currorderStatus:", currorderStatus);
                    OrderStatusList33.add(currorderStatus);
                    OrderNumberList33.add(orderNumber);
                    OrderIdList33.add(OrderId);
                    OrderNameList33.add(name);
                }while(c.moveToNext());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        OrderNumberMap33.put(Header33.get(0), OrderNumberList33);
        OrderIdMap33.put(Header33.get(0), OrderIdList33);
        OrderNameMap33.put(Header33.get(0), OrderNameList33);
        OrderStatusMap33.put(Header33.get(0), OrderStatusList33);

        expandableListAdapter = new ExpandableListAdapter(this, Header33, OrderNumberMap33, OrderIdMap33, OrderNameMap33, OrderStatusMap33);
        tablelist33.setAdapter(expandableListAdapter);

        tablelist33.setNestedScrollingEnabled(true);

        tablelist33.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int orderIdselected = Integer.parseInt(OrderIdList33.get(childPosition));
                int orderNumberSelected = Integer.parseInt(OrderNumberList33.get(childPosition));
                Log.i("orderIdselected:", Integer.toString(orderIdselected));
                Log.i("orderNumberSelected:", Integer.toString(orderNumberSelected));
                Intent intent = new Intent(getApplicationContext(), DetailedRecord.class);
                intent.putExtra("OrderId1", orderIdselected);
                intent.putExtra("source1", "dashboardDate");
                startActivity(intent);
                return false;
            }
        });

            /*

            tablelist33.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    int height = 0;
                    for (int i = 0; i < tablelist33.getChildCount(); i++) {
                        height += tablelist33.getChildAt(i).getMeasuredHeight();
                        height += tablelist33.getDividerHeight();
                    }
                    tablelist33.getLayoutParams().height = (height)*10;
                }
            });

            // Listview Group collapsed listener
            tablelist33.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    tablelist33.getLayoutParams().height = 160;
                }
            });
            */

    }
}
