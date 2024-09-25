package com.example.organsharing.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.organsharing.form.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.example.organsharing.dao.DAO;
import com.example.organsharing.util.Constants;
import com.example.organsharing.R;
import com.example.organsharing.util.MapUtil;
import com.example.organsharing.util.Session;

public class SearchBlood extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText e2;
    Button b1,b2;
    ListView listView;

    String[] bloodTypes={"A+","A-","B+","B-","AB+","AB-","O+","O-"};
    String   bloodtype;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_blood);

        e2=(EditText)findViewById(R.id.searchlocation);
        listView=(ListView) findViewById(R.id.SearchUsersList);
        b1=(Button)findViewById(R.id.searchsubmit);
        b2=(Button)findViewById(R.id.bloodback);

        final Session s = new Session(getApplicationContext());

        Spinner spinblood = (Spinner) findViewById(R.id.spinnerbloodgroup1);
        spinblood.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,bloodTypes);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinblood.setAdapter(aa);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String searchtype=bloodtype;
                Toast.makeText(getApplicationContext(),"Selected Blood Type:"+bloodtype,Toast.LENGTH_LONG).show();
                final String location=e2.getText().toString();

                final ArrayList<String> al=new ArrayList<String>();

                new DAO().getDBReference(Constants.USER_DB).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {

                            String id=snapshotNode.getKey();

                            Object object=snapshotNode.getValue(User.class);

                            User user = (User) object;

                            if(user.getBloodStatus().equals("yes"))
                            {
                                if(user.getBloodgroup().equals(searchtype))
                                {
                                    if(user.getAddress().toLowerCase().contains(location.toLowerCase()))
                                    {
                                        Toast.makeText(getApplicationContext(),"3",Toast.LENGTH_LONG).show();

                                        String sd1=user.getDod();

                                        String[] tokens=sd1.split("/");

                                        Date d1=new Date();
                                        d1.setDate(Integer.parseInt(tokens[0]));
                                        d1.setMonth(Integer.parseInt(tokens[1])-1);
                                        d1.setYear(Integer.parseInt(tokens[2])+100);

                                        Date d2=new Date();

                                        long duration  = d1.getTime()-d2.getTime();
                                        long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);

                                        Toast.makeText(getApplicationContext(),"Diff Days:"+diffInDays,Toast.LENGTH_LONG).show();

                                        if(diffInDays<-90)
                                        {
                                            al.add(user.getUsername());
                                        }
                                    }
                                }
                            }
                        }

                        if(al.size()==0)
                        {
                            Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_LONG).show();
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(listView.getContext(),
                                android.R.layout.simple_list_item_1, (al.toArray(new String[al.size()])));

                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),PatientHome.class);
                startActivity(i);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String item = listView.getItemAtPosition(i).toString();

                Intent intent=new Intent(getApplicationContext(),ViewUser.class);
                intent.putExtra("userid",item);

                startActivity(intent);
            }
        });
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
