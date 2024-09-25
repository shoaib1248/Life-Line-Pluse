package com.example.organsharing.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.example.organsharing.R;
import com.example.organsharing.dao.DAO;
import com.example.organsharing.form.ChatMessage;
import com.example.organsharing.util.Constants;
import com.example.organsharing.util.MapUtil;
import com.example.organsharing.util.Session;

public class ListMessages extends AppCompatActivity {

    ListView listView;

    Button b1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_messages);

        listView=(ListView) findViewById(R.id.MessagesList);
        b1 = (Button) findViewById(R.id.msgback);

        final Session s=new Session(getApplicationContext());

        final DAO dao=new DAO();
        dao.setChatDataToAdapterList(listView, ChatMessage.class, Constants.MESSAGES_DB);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String item = listView.getItemAtPosition(i).toString();

                Intent intent =new Intent(getApplicationContext(),PostMessage.class);
                intent.putExtra("userid",item.split(":-")[0]);
                startActivity(intent);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String role=s.getRole();

                if(role.equals("patient"))
                {
                    Intent i = new Intent(getApplicationContext(),PatientHome.class);
                    startActivity(i);
                }
                else if(role.equals("user"))
                {
                    Intent i = new Intent(getApplicationContext(),UserHome.class);
                    startActivity(i);
                }
                else if(role.equals("admin")){
                    Intent i = new Intent(getApplicationContext(),ViewUser.class);
                    startActivity(i);
                }
            }
        });
    }
}
