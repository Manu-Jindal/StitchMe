package com.manujindal.stichme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

public class AddEmployee extends AppCompatActivity {


    Intent intent;

    int EmployeeId;

    SQLiteDatabase database;

    String EmployeeName1;
    Long EmployeePhone1;

    EditText EmployeeName;
    EditText EmployeePhone;



    String[] FabricTypes = {"Shirt", "Suit", "Trouser"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intent = getIntent();

        EmployeeId = intent.getIntExtra("EmployeeId1", -1);

        Log.i("EmployeeId in AddEmp: ", Integer.toString(EmployeeId));

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        EmployeeName = (EditText) findViewById(R.id.employeename);
        EmployeePhone= (EditText) findViewById(R.id.employeephone);

    }

    public void addEmployee(View view) {


        Intent intent = new Intent(this, EmployeeList.class);
        EmployeeName1 = EmployeeName.getText().toString();
        EmployeePhone1 = (StringUtils.isNotBlank(EmployeePhone.getText().toString()) ? Long.parseLong(EmployeePhone.getText().toString()): 0);

        try {
            String sql = "UPDATE employees SET Name = '"+EmployeeName1+"' WHERE EmployeeId = "+EmployeeId;
            database.execSQL(sql);
            String sql1 = "UPDATE employees SET Phone = '"+EmployeePhone1+"' WHERE EmployeeId = "+EmployeeId;
            database.execSQL(sql1);

            Log.i("EmployeeId in AddEmp: ", Integer.toString(EmployeeId));
            Log.i("Empame1 in AddEmp:", EmployeeName1);
            Log.i("EmpPhone1 in AddEmp:", Long.toString(EmployeePhone1));

            Cursor c = database.rawQuery("SELECT * FROM employees", null);
            c.moveToFirst();
            /*while(c!=null)
            {
                Log.i("Result Email: ", c.getString(c.getColumnIndex("Email")));
                Log.i("Result Phone: ", c.getString(c.getColumnIndex("Phone")));
                c.moveToNext();
            }*/

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        startActivity(intent);

    }

}
