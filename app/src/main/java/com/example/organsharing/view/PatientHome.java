package com.example.organsharing.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.organsharing.MainActivity;
import com.example.organsharing.R;
import com.example.organsharing.util.Session;

public class PatientHome extends AppCompatActivity {

    Button searchbloods;
    Button searchorgan;
    Button userlogout;
    Button userviewmessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        searchbloods=(Button) findViewById(R.id.searchblood);
        searchorgan=(Button) findViewById(R.id.searchorgan);

        userlogout=(Button) findViewById(R.id.patientlogout);
        userviewmessages=(Button) findViewById(R.id.patientviewmessages);

        final Session s = new Session(getApplicationContext());

        searchbloods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),SearchBlood.class);
                startActivity(i);
            }
        });

        searchorgan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),SearchOrgan.class);
                startActivity(i);
            }
        });

        userviewmessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),ListMessages.class);
                startActivity(i);
            }
        });

        userlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.setusername("");
                s.loggingOut();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
}
