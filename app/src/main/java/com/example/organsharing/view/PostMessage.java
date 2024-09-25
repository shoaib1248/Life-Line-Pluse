package com.example.organsharing.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import com.example.organsharing.R;
import com.example.organsharing.form.ChatMessage;
import com.example.organsharing.util.Session;
import com.example.organsharing.dao.DAO;
import com.example.organsharing.util.Constants;

public class PostMessage extends AppCompatActivity {

    EditText sendTextMessage;

    Button postMessageSubmit;
    Button postMessageCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_message);

        final Session s = new Session(getApplicationContext());

        sendTextMessage = (EditText) findViewById(R.id.sendTextMessage);

        postMessageSubmit = (Button) findViewById(R.id.postMessageSubmit);
        postMessageCancel = (Button) findViewById(R.id.postMessageCancel);

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        final String userId=savedInstanceState.getString("userid");

        postMessageSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message=sendTextMessage.getText().toString();

                if (message == null ) {
                    Toast.makeText(getApplicationContext(), "Please Enter message", Toast.LENGTH_SHORT).show();
                } else {


                    DAO dao = new DAO();

                    ChatMessage chatMessage=new ChatMessage();
                    chatMessage.setMessageId(dao.getUnicKey(Constants.MESSAGES_DB));
                    chatMessage.setSender(s.getusername());
                    chatMessage.setReceiver(userId);
                    chatMessage.setDate(new Date().toString());
                    chatMessage.setMessage(message);

                    try {

                        dao.addObject(Constants.MESSAGES_DB,chatMessage, chatMessage.getMessageId());

                        String role=s.getRole();

                        if(role.equals("patient"))
                        {
                            Intent i = new Intent(getApplicationContext(),ListMessages.class);
                            startActivity(i);
                        }
                        else if(role.equals("user"))
                        {
                            Intent i = new Intent(getApplicationContext(),ListMessages.class);
                            startActivity(i);
                        }
                        else if(role.equals("admin")){
                            Intent i = new Intent(getApplicationContext(),ViewUser.class);
                            startActivity(i);
                        }

                        Toast.makeText(getApplicationContext(), "Message Sent Successfully", Toast.LENGTH_SHORT).show();

                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Sending Failed", Toast.LENGTH_SHORT).show();
                        Log.v("complaint failed", ex.toString());
                        ex.printStackTrace();
                    }
                }
            }
        });

        postMessageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String role=s.getRole();

                if(role.equals("patient"))
                {
                    Intent i = new Intent(getApplicationContext(),ListMessages.class);
                    startActivity(i);
                }
                else if(role.equals("user"))
                {
                    Intent i = new Intent(getApplicationContext(),ListMessages.class);
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
