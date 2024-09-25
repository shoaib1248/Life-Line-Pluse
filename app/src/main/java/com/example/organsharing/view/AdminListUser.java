package com.example.organsharing.view;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.organsharing.R;
import com.example.organsharing.dao.DAO;
import com.example.organsharing.form.Patient;
import com.example.organsharing.form.User;
import com.example.organsharing.util.Constants;
import com.example.organsharing.util.MapUtil;
import com.example.organsharing.util.Session;

public class AdminListUser extends AppCompatActivity {

    Button b1;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_user);

        listView=(ListView) findViewById(R.id.AdminUsersList);
        b1 = (Button) findViewById(R.id.adminListUserCancel);

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        final String usertype=savedInstanceState.getString("usertype");

        DAO dao=new DAO();

        if(usertype.equals(Constants.USER_DB))
        {
            dao.setDataToAdapterList(listView,User.class, usertype);
        }
        else if(usertype.equals(Constants.PATIENT_DB)) {
            dao.setDataToAdapterList(listView, Patient.class, usertype);
        }

        final Session s=new Session(getApplicationContext());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.v("in list action perform ","in list action perform");

                String item = listView.getItemAtPosition(i).toString();
                item= MapUtil.stringToMap(s.getViewMap()).get(item);

                if(usertype.equals(Constants.USER_DB))
                {
                    Intent intent= new Intent(getApplicationContext(),ViewUser.class);
                    intent.putExtra("userid",item);
                    startActivity(intent);
                }
                else if(usertype.equals(Constants.PATIENT_DB)){
                    Intent intent= new Intent(getApplicationContext(),ViewPatient.class);
                    intent.putExtra("patientid",item);
                    startActivity(intent);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),AdminHome.class);
                startActivity(i);
            }
        });
    }
}
