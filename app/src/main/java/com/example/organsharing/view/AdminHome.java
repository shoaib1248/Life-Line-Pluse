package com.example.organsharing.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.organsharing.MainActivity;
import com.example.organsharing.R;
import com.example.organsharing.util.Constants;
import com.example.organsharing.util.Session;

public class AdminHome extends AppCompatActivity {

    Button b1,b2;
    Button adminLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        final Session s = new Session(getApplicationContext());

        b1=(Button) findViewById(R.id.adminlistdonors);
        b2=(Button) findViewById(R.id.adminlistpatients);
        adminLogout=(Button) findViewById(R.id.adminlogout);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminListUser.class);
                i.putExtra("usertype", Constants.USER_DB);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminListUser.class);
                i.putExtra("usertype", Constants.PATIENT_DB);
                startActivity(i);
            }
        });

        adminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.loggingOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
