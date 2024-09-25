package com.example.organsharing.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.organsharing.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.example.organsharing.R;
import com.example.organsharing.dao.DAO;
import com.example.organsharing.form.User;
import com.example.organsharing.util.Constants;
import com.example.organsharing.util.Session;

public class ViewUser extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
    Button cancel;
    Button userSendMessge;
    Button deleteuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        cancel=(Button) findViewById(R.id.viewUserCanel);
        deleteuser=(Button) findViewById(R.id.userdeleteaccount);
        userSendMessge=(Button) findViewById(R.id.usersendmessge);

        final Session session=new Session(getApplicationContext());

        if(!session.getRole().equals("admin"))
        {
            deleteuser.setEnabled(false);
        }

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        final String userId=savedInstanceState.getString("userid");

        t1=(TextView) findViewById(R.id.textviewname);
        t2=(TextView)findViewById(R.id.textviewusername);
        t3=(TextView)findViewById(R.id.textviewmobile);
        t4=(TextView) findViewById(R.id.textviewemail);
        t5=(TextView)findViewById(R.id.textviewaddress);
        t6=(TextView)findViewById(R.id.textviewgender);

        t7=(TextView) findViewById(R.id.textviewblood);
        t8=(TextView)findViewById(R.id.textvieworgan);
        t9=(TextView)findViewById(R.id.textvieworganafterblood);
        t10=(TextView)findViewById(R.id.vieworgan);

        if(session.getusername()==null || session.getusername().equals(""))
        {
            userSendMessge.setEnabled(false);
        }

        DAO d=new DAO();
        d.getDBReference(Constants.USER_DB).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user=dataSnapshot.getValue(User.class);

                if(user!=null)
                {
                    t1.setText("Name:"+user.getName());
                    t2.setText("User ID:"+user.getUsername());
                    t3.setText("Mobile:"+user.getMobile());
                    t4.setText("Email:"+user.getEmail());
                    t5.setText("Address:"+user.getAddress());
                    t6.setText("Gender:"+user.getGender());

                    t7.setText("Blood Donation Status:"+user.getBloodStatus());
                    t8.setText("Organ Donation:"+user.getOrganStatus());
                    t10.setText("Organ willing to donate:"+user.getOrgan());
                    t9.setText("Organ Donation After Death:"+user.getOrganStatusAfterDeath());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userSendMessge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(getApplicationContext(),PostMessage.class);
                i.putExtra("userid",userId);
                startActivity(i);
            }
        });

        deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DAO d=new DAO();
                d.deleteObject(Constants.USER_DB,userId);

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