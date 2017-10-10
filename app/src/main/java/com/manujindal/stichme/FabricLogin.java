package com.manujindal.stichme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FabricLogin extends AppCompatActivity {
    
    String username;
    String password;
    EditText username1;
    EditText password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabric_login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.element);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        username1 = (EditText) findViewById(R.id.username);
        password1 = (EditText) findViewById(R.id.password);
    }
    
    public void login(View view) 
    {
        username = username1.getText().toString();
        password = password1.getText().toString();
        if(username.equals("manu") && password.equals("aaa"))
        {
            Intent intent = new Intent(this, AdminPanel.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(FabricLogin.this,"Please enter correct username and password", Toast.LENGTH_SHORT).show();

        }
    }

}
