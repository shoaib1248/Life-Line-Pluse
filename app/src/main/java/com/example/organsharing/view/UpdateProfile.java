package com.example.organsharing.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.organsharing.R;
import com.example.organsharing.dao.DAO;
import com.example.organsharing.form.User;
import com.example.organsharing.util.Constants;
import com.example.organsharing.util.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText address;
    EditText weight;

    String[] userStatus={"--select--","yes","no"};
    String[] organs={"--select--","Liver","Kidney","Pancreas"};
    String bloodtype;
    String organtype;
    String organ;
    String organafterdeathtype;

    Button updateBloodSubmit;
    Button updateBloodCancel;

    private EditText dateOfDonation;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_profile);

        final Session session=new Session(getApplicationContext());

        address = (EditText) findViewById(R.id.updateaddress);
        weight = (EditText) findViewById(R.id.updateweight);

        updateBloodSubmit = (Button) findViewById(R.id.updateBloodSubmit);
        updateBloodCancel = (Button) findViewById(R.id.updateBloodCancel);


        // calender setting start

        myCalendar = Calendar.getInstance();

        dateOfDonation= (EditText) findViewById(R.id.dateOfDonation);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateOfDonation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UpdateProfile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Spinner spin = (Spinner) findViewById(R.id.bloodspinner);
        spin.setOnItemSelectedListener(this);


        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,userStatus);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        spin = (Spinner) findViewById(R.id.organsspin);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa2 = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,organs);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa2);


        spin = (Spinner) findViewById(R.id.organspinner);
        spin.setOnItemSelectedListener(this);

        aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,userStatus);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        //========================================================================

        spin = (Spinner) findViewById(R.id.organafterdeathspinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,userStatus);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        DAO d=new DAO();
        d.getDBReference(Constants.USER_DB).child(session.getusername()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user=dataSnapshot.getValue(User.class);

                if(user!=null)
                {
                    address.setText(user.getAddress());
                    weight.setText(user.getWeight());
                    dateOfDonation.setText(user.getDod());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        updateBloodSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userAddress = address.getText().toString();
                String userWeight = weight.getText().toString();
                String dod=dateOfDonation.getText().toString();

                if (userAddress == null || userWeight ==null) {
                    Toast.makeText(getApplicationContext(), "Please Valid Values", Toast.LENGTH_SHORT).show();
                } else {

                    DAO dao = new DAO();
                    dao.getDBReference(Constants.USER_DB).child(session.getusername()).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            User user=dataSnapshot.getValue(User.class);

                            if(user!=null)
                            {
                                user.setAddress(userAddress);
                                user.setWeight(userWeight);

                                if(!bloodtype.equals("--select--"))
                                {
                                    user.setBloodStatus(bloodtype);
                                }

                                if(!organtype.equals("--select--"))
                                {
                                    user.setOrganStatus(organtype);
                                }

                                if(!organ.equals("--select--"))
                                {
                                    user.setOrgan(organ);
                                }

                                if(!organafterdeathtype.equals("--select--"))
                                {
                                    user.setOrganStatusAfterDeath(organafterdeathtype);
                                }

                                user.setDod(dod);

                                dao.addObject(Constants.USER_DB,user,session.getusername());
                                Intent i = new Intent(getApplicationContext(),UserHome.class);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        updateBloodCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),UserHome.class);
                startActivity(i);
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateOfDonation.setText(sdf.format(myCalendar.getTime()));
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {

        Toast.makeText(getApplicationContext(),parent.getId()+"",Toast.LENGTH_LONG).show();
        switch(parent.getId()) {

            case R.id.bloodspinner: bloodtype=userStatus[position];
                break;

            case R.id.organspinner: organtype=userStatus[position];
                break;

            case R.id.organsspin: organ=organs[position];
                break;

            case R.id.organafterdeathspinner: organafterdeathtype=userStatus[position];
                break;
        }


    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}