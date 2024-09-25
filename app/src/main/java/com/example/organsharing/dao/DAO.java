package com.example.organsharing.dao;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.organsharing.form.BloodRequest;
import com.example.organsharing.form.Patient;
import com.example.organsharing.form.User;
import com.example.organsharing.util.MapUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.example.organsharing.form.ChatMessage;
import com.example.organsharing.util.Constants;
import com.example.organsharing.util.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DAO
{
        public static DatabaseReference getDBReference(String dbName)
        {
            return GetFireBaseConnection.getConnection(dbName);
        }

        public static String getUnicKey(String dbName)
        {
            return getDBReference(dbName).push().getKey();
        }

        public int addObject(String dbName,Object obj,String key) {

            int result=0;

            try {

                getDBReference(dbName).child(key).setValue(obj);

                result=1;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return result;
        }

        public int deleteObject(String dbName, String key) {

            int result=0;

            try {

                getDBReference(dbName).child(key).removeValue();

                result=1;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return 0;
        }

    public void setDataToAdapterList(final View view, final Class c, final String dbname) {

        final Map<String,Object> map=new HashMap<>();
        final Map<String,String> viewMap=new HashMap<String,String>();
        Session s=new Session(view.getContext());

        getDBReference(dbname).addValueEventListener(new ValueEventListener() {
            int i=1;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {

                    String id=snapshotNode.getKey();

                    Object object=snapshotNode.getValue(c);

                    if(dbname.equals(Constants.USER_DB)) {

                        User user = (User) object;
                        viewMap.put(user.getName(),user.getUsername());
                    }

                    if(dbname.equals(Constants.PATIENT_DB)) {
                        Patient patient = (Patient) object;
                        viewMap.put(patient.getName(),patient.getUsername());
                    }

                    if(dbname.equals(Constants.REQUESTS_DB)) {
                        BloodRequest request = (BloodRequest) object;
                        viewMap.put(i+")"+request.getBloodgroup()+"--"+request.getLocation(),request.getId());
                        i++;
                    }

                    map.put(id,object);
                    i++;
                }

                ArrayList<String> al=new ArrayList<String>(viewMap.keySet());

                if(view instanceof ListView) {

                    final ListView myView=(ListView)view;

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(myView.getContext(),
                            android.R.layout.simple_list_item_1, (al.toArray(new String[al.size()])));

                    myView.setAdapter(adapter);
                }
                s.setViewMap(MapUtil.mapToString(viewMap));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void setChatDataToAdapterList(final View view, final Class c, final String dbname) {

        final ArrayList<String> al=new ArrayList<String>();

        final Session session=new Session(view.getContext());

        getDBReference(dbname).addValueEventListener(new ValueEventListener() {
            int i=1;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {

                    String id=snapshotNode.getKey();

                    Object object=snapshotNode.getValue(c);

                    if(dbname.equals(Constants.MESSAGES_DB))
                    {
                        ChatMessage chatMessage=(ChatMessage) object;
                        if(session.getusername().equals(chatMessage.getReceiver())) {
                            al.add(chatMessage.getSender()+":-"+chatMessage.getMessage());
                        }
                    }
                }

                if(view instanceof ListView) {

                    final ListView myView=(ListView)view;

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(myView.getContext(),
                            android.R.layout.simple_list_item_1, (al.toArray(new String[al.size()])));

                    myView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}


