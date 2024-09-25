package com.example.organsharing.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.organsharing.MainActivity;
import com.example.organsharing.dao.DAO;
import com.example.organsharing.form.User;
import com.example.organsharing.util.Constants;
import com.example.organsharing.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText e1,e2,e3,e4,e5,e6,e7,e8;
    Button b1,b2;

    String[] bloodTypes={"A+","A-","B+","B-","AB+","AB-","O+","O-"};
    String bloodtype;

    RadioGroup radioGroup;
    RadioButton radioButton;

    private EditText dateOfBirth;
    Calendar myCalendar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        e1=(EditText)findViewById(R.id.registerUserName);
        e2=(EditText)findViewById(R.id.registerPassword);
        e3=(EditText)findViewById(R.id.registerConPass);
        e4=(EditText)findViewById(R.id.registerMobile);
        e5=(EditText)findViewById(R.id.registerName);
        e6=(EditText)findViewById(R.id.registeremail);
        e7=(EditText)findViewById(R.id.registeraddress);
        e8=(EditText)findViewById(R.id.registerweight);

        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);

        b1=(Button)findViewById(R.id.registerButton);
        b2=(Button)findViewById((R.id.donorback));

        // calender setting start

        myCalendar = Calendar.getInstance();

        dateOfBirth= (EditText) findViewById(R.id.dateOfBirth);
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

        dateOfBirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Spinner spinblood = (Spinner) findViewById(R.id.spinnerbloodgroup);
        spinblood.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,bloodTypes);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinblood.setAdapter(aa);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username=e1.getText().toString();
                String password=e2.getText().toString();
                String conformPassword=e3.getText().toString();
                String mobile=e4.getText().toString();
                String name=e5.getText().toString();
                String email=e6.getText().toString();
                String address=e7.getText().toString();
                String weight=e8.getText().toString();
                String dob=dateOfBirth.getText().toString();

                int selectedType=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectedType);
                final String gender=radioButton.getText().toString();

                if(username==null|| password==null|| conformPassword==null|| mobile==null|| name==null)
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
                else if(address==null)
                {
                    Toast.makeText(getApplicationContext(),"please enter address",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    User user=new User();

                    user.setUsername(username);
                    user.setPassword(password);
                    user.setMobile(mobile);
                    user.setName(name);
                    user.setEmail(email);
                    user.setAddress(address);
                    user.setGender(gender);
                    user.setBloodgroup(bloodtype);
                    user.setBloodStatus("No");
                    user.setOrganStatus("No");
                    user.setOrgan("");
                    user.setOrganStatusAfterDeath("No");
                    user.setWeight(weight);
                    user.setDob(dob);
                    user.setDod("");

                    DAO dao=new DAO();

                    try
                    {
                        dao.addObject(Constants.USER_DB,user,user.getUsername());

                        Toast.makeText(getApplicationContext(),"Register Success",Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getApplicationContext(),"Register Error",Toast.LENGTH_SHORT).show();
                        Log.v("User Registration Ex", ex.toString());
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

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateOfBirth.setText(sdf.format(myCalendar.getTime()));
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        bloodtype=bloodTypes[position];
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}