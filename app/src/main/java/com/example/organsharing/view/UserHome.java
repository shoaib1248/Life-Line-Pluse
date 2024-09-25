package com.example.organsharing.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.organsharing.MainActivity;
import com.example.organsharing.R;
import com.example.organsharing.util.Session;

public class UserHome extends AppCompatActivity {

    Button userlogout;
    Button userviewmessages;
    Button updateprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        userlogout=(Button) findViewById(R.id.userlogout);
        userviewmessages=(Button) findViewById(R.id.userviewmessages);
        updateprofile=(Button) findViewById(R.id.userupdateprofile);

        final Session s = new Session(getApplicationContext());

        userviewmessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),ListMessages.class);
                startActivity(i);
            }
        });

        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),UpdateProfile.class);
                startActivity(i);
            }
        });

        userlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.setusername("");
                s.loggingOut();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
}
