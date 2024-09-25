package com.example.organsharing.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organsharing.MainActivity;
import com.example.organsharing.form.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.example.organsharing.dao.DAO;
import com.example.organsharing.form.User;
import com.example.organsharing.util.Constants;
import com.example.organsharing.util.Session;
import com.example.organsharing.R;

public class PatientLogin extends AppCompatActivity {

    private Session session;
    EditText e1,e2;
    Button b1,b2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getApplicationContext());

        setContentView(R.layout.activity_patient_login);

        e1=(EditText)findViewById(R.id.loginPatientPhone);
        e2=(EditText)findViewById(R.id.loginPatientPass);
        b1=(Button)findViewById(R.id.loginPatientConfirm);
        b2=(Button)findViewById(R.id.patlogiback);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username=e1.getText().toString();
                final String password=e2.getText().toString();

                if(username==null|| password==null || username.length()<=0|| password.length()<=0)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter UserName and Password",Toast.LENGTH_SHORT).show();
                }
                else {

                    DAO d = new DAO();
                    d.getDBReference(Constants.PATIENT_DB).child(username).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Patient patient =(Patient) dataSnapshot.getValue(Patient.class);

                            if (patient == null) {
                                Toast.makeText(getApplicationContext(), "Invalid UserName ", Toast.LENGTH_SHORT).show();
                            } else if (patient != null) {

                                Toast.makeText(getApplicationContext(), password+"_"+patient.getPassword(), Toast.LENGTH_SHORT).show();

                                if (patient.getPassword().equals(password)) {

                                    session.setusername(patient.getUsername());
                                    session.setRole("patient");

                                    Intent i = new Intent(getApplicationContext(), PatientHome.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "In valid Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
