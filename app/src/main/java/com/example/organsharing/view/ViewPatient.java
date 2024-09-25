package com.example.organsharing.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.organsharing.MainActivity;
import com.example.organsharing.form.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.example.organsharing.R;
import com.example.organsharing.dao.DAO;
import com.example.organsharing.form.Patient;
import com.example.organsharing.util.Constants;
import com.example.organsharing.util.Session;

public class ViewPatient extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5;
    Button cancel;
    Button deletepatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);

        cancel=(Button) findViewById(R.id.viewPatientCanel);
        deletepatient=(Button) findViewById(R.id.patientdeleteaccount);

        final Session session=new Session(getApplicationContext());

        if(!session.getRole().equals("admin"))
        {
            deletepatient.setEnabled(false);
        }

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        final String patientId=savedInstanceState.getString("patientid");

        t1=(TextView) findViewById(R.id.textviewname);
        t2=(TextView)findViewById(R.id.textviewpatientname);
        t3=(TextView)findViewById(R.id.textviewmobile);
        t4=(TextView) findViewById(R.id.textviewemail);
        t5=(TextView)findViewById(R.id.textviewaddress);;

        DAO d=new DAO();
        d.getDBReference(Constants.PATIENT_DB).child(patientId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Patient patient=dataSnapshot.getValue(Patient.class);

                if(patient!=null)
                {
                    t1.setText("Name:"+patient.getName());
                    t2.setText("Patient ID:"+patient.getUsername());
                    t3.setText("Mobile:"+patient.getMobile());
                    t4.setText("Email:"+patient.getEmail());
                    t5.setText("Address:"+patient.getAddress());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        deletepatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DAO d=new DAO();
                d.deleteObject(Constants.PATIENT_DB,patientId);

                Intent i = new Intent(getApplicationContext(),AdminListUser.class);
                startActivity(i);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String role=session.getRole();

                if(role.equals("patient"))
                {
                    Intent i = new Intent(getApplicationContext(),PatientHome.class);
                    startActivity(i);
                }
                else if(role.equals("user"))
                {
                    Intent i = new Intent(getApplicationContext(), UserHome.class);
                    startActivity(i);
                }
                else if(role.equals("admin"))
                {
                    Intent i = new Intent(getApplicationContext(), AdminListUser.class);
                    startActivity(i);
                }
            }
        });
    }
}
