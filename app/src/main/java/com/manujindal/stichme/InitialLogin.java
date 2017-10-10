package com.manujindal.stichme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InitialLogin extends AppCompatActivity {

    EditText username1;
    EditText password1;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        username1 = (EditText) findViewById(R.id.usernameinitial);
        password1 = (EditText) findViewById(R.id.passwordinitial);
    }

    public void onBackPressed() {
        Log.i("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(getApplicationContext(), InitialLogin.class);
        startActivity(setIntent);
    }


    public void login(View view )
    {
        username = username1.getText().toString();
        password = password1.getText().toString();

        if(username.equals("StitchMe") && password.equals("SM@1234567890"))
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("loginsuccess", 1);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please enter the correct credentials to activate the app", Toast.LENGTH_LONG).show();
        }

    }
}
