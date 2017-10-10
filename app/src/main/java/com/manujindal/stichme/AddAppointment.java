package com.manujindal.stichme;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddAppointment extends AppCompatActivity {

    Intent intent;
    int AppointmentId;
    SQLiteDatabase database;

    Spinner slot;
    Spinner designer;

    String[] slots = {"9AM-10AM", "10AM-11AM", "11AM-12PM", "12PM-1PM", "1PM-2PM", "2PM-3PM", "3PM-4PM", "4PM-5PM", "5PM-6PM", "6PM-7PM", "7PM-8PM"};
    String[] employees;
    ArrayList<String> employeeNameList = new ArrayList<>();
    ArrayList<String> employeeIdList = new ArrayList<>();
    ArrayList<String> employeePhoneList = new ArrayList<>();

    EditText remarkEditText;
    TextView appointmentDate;
    String appointmentDate2;

    String employeeName;
    String employeeId;
    String employeePhone;
    String customerPhone;
    String customerName;
    String address;
    String slot1;
    String remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        appointmentDate = (TextView) findViewById(R.id.appointmentDate);
        remarkEditText = (EditText) findViewById(R.id.remarks);

        intent = getIntent();
        AppointmentId = intent.getIntExtra("AppointmentId1", -1);
        Log.i("AppointmentId in AdAp1:", Integer.toString(AppointmentId));

        database = openOrCreateDatabase("testdatabase1", MODE_PRIVATE, null);

        slot = (Spinner) findViewById(R.id.slot);
        designer = (Spinner) findViewById(R.id.designer);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AddAppointment.this,
                android.R.layout.simple_spinner_item,slots);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        try
        {
            Cursor c0 = database.rawQuery("SELECT * FROM appointments, customers WHERE appointments.CustomerId = customers.CustomerId AND AppointmentId = "+AppointmentId, null);
            if(c0.moveToFirst()) {
                customerPhone = c0.getString(c0.getColumnIndex("Phone"));
                customerName = c0.getString(c0.getColumnIndex("Name"));
                address = c0.getString(c0.getColumnIndex("Address"));
            }

            Cursor c = database.rawQuery("SELECT * FROM employees", null);
            if(c.moveToFirst()) {
                do {
                    employeeNameList.add(c.getString(c.getColumnIndex("Name")));
                    employeeIdList.add(c.getString(c.getColumnIndex("EmployeeId")));
                    employeePhoneList.add(c.getString(c.getColumnIndex("Phone")));
                } while (c.moveToNext());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        employees = new String[employeeNameList.size()];
        employees = employeeNameList.toArray(employees);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AddAppointment.this,
                android.R.layout.simple_spinner_item,employees);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        slot.setAdapter(adapter1);
        designer.setAdapter(adapter2);

        slot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                slot1 = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        designer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                employeeName = (String) parent.getItemAtPosition(position);
                employeeId = employeeIdList.get(position);
                employeePhone = employeePhoneList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                appointmentDate.setText(sdf.format(myCalendar.getTime()));
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                appointmentDate2 = sdf1.format(myCalendar.getTime());
            }
        };

        appointmentDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddAppointment.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void bookAppointment(View view)
    {

        Intent intent = new Intent(this, AllAppointments.class);
        remark = remarkEditText.getText().toString();

        String appointmentDate1 = appointmentDate.getText().toString();
        if(appointmentDate1.equals("Date"))
        {
            Toast.makeText(this, "Enter Appointment Date!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try
            {
                database.execSQL("UPDATE appointments SET AppointmentDate = '"+appointmentDate1+"' WHERE AppointmentId = "+AppointmentId);
                Log.i("AppointmentDate: ",appointmentDate1);
                database.execSQL("UPDATE appointments SET Remarks = '"+remark+"' WHERE AppointmentId = "+AppointmentId);
                Log.i("Remarks: ",remark);
                database.execSQL("UPDATE appointments SET Slot = '"+slot1+"' WHERE AppointmentId = "+AppointmentId);
                Log.i("Slot: ",slot1);
                database.execSQL("UPDATE appointments SET EmployeeId = "+employeeId+" WHERE AppointmentId = "+AppointmentId);
                Log.i("EmployeeId: ",employeeId);

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage((employeePhone), null, "New appointment scheduled!\n Date and time:"+appointmentDate2+" ("+slot1+"). \nClient Name: "+customerName+"\nClient Phone: "+customerPhone+"\nClient Address: "+address+"\n\nRemarks: "+remark, null, null);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again later!",
                            Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage((customerPhone), null, "Hi "+customerName+"!\nYour appointment is confirmed for "+appointmentDate2+" ("+slot1+"). The designer appointed to you is "+employeeName+"("+employeePhone+").\nLet's get you Stitched!\nTeam StitchMe.", null, null);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again later!",
                            Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                startActivity(intent);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

    }
}
