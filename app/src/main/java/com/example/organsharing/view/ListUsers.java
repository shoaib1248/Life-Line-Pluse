package com.example.organsharing.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.organsharing.R;
import com.example.organsharing.dao.DAO;
import com.example.organsharing.form.User;
import com.example.organsharing.util.Constants;
import com.example.organsharing.util.Session;
import com.example.organsharing.util.MapUtil;

public class ListUsers extends AppCompatActivity {

    ListView listView;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        listView=(ListView) findViewById(R.id.UsersList);

        final Session s=new Session(getApplicationContext());

        DAO dao=new DAO();
        dao.setDataToAdapterList(listView,User.class, Constants.USER_DB);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String item = listView.getItemAtPosition(i).toString();
                item= MapUtil.stringToMap(s.getViewMap()).get(item);

                Intent intent=new Intent(getApplicationContext(),ViewUser.class);
                intent.putExtra("userid", item);
                startActivity(intent);
            }
        });
    }
}
