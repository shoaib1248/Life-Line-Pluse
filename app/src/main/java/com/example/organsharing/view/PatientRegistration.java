package com.example.organsharing.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organsharing.MainActivity;
import com.example.organsharing.dao.DAO;
import com.example.organsharing.form.Patient;
import com.example.organsharing.util.Constants;
import com.example.organsharing.R;

public class PatientRegistration extends AppCompatActivity{

    EditText e1,e2,e3,e4,e5,e6,e7;
    Button b1,b2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_patient_registration);

        e1=(EditText)findViewById(R.id.registerPatientUserName);
        e2=(EditText)findViewById(R.id.registerPatientPassword);
        e3=(EditText)findViewById(R.id.registerPatientConPass);
        e4=(EditText)findViewById(R.id.registerPatientMobile);
        e5=(EditText)findViewById(R.id.registerPatientName);
        e6=(EditText)findViewById(R.id.registerPatientemail);
        e7=(EditText)findViewById(R.id.registerPatientaddress);

        b1=(Button)findViewById(R.id.registerButton);
        b2=(Button)findViewById(R.id.patientback);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String patientname=e1.getText().toString();
                String password=e2.getText().toString();
                String conformPassword=e3.getText().toString();
                String mobile=e4.getText().toString();
                String name=e5.getText().toString();
                String email=e6.getText().toString();
                String address=e7.getText().toString();

                if(patientname==null|| password==null|| conformPassword==null|| mobile==null|| name==null)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Data",Toast.LENGTH_SHORT).show();
                }
                else if(mobile.length()!=10)
                {
                    Toast.makeText(getApplicationContext(),"Invalid Mobile",Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(conformPassword))
                {
                    Toast.makeText(getApplicationContext(),"Password Mismatch",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Patient patient=new Patient();

                    patient.setUsername(patientname);
                    patient.setPassword(password);
                    patient.setMobile(mobile);
                    patient.setName(name);
                    patient.setEmail(email);
                    patient.setAddress(address);

                    DAO dao=new DAO();

                    try
                    {
                        dao.addObject(Constants.PATIENT_DB,patient,patient.getUsername());

                        Toast.makeText(getApplicationContext(),"Register Success",Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getApplicationContext(),"Register Error",Toast.LENGTH_SHORT).show();
                        Log.v("Patient Registration Ex", ex.toString());
                        ex.printStackTrace();
                    }

                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
}
