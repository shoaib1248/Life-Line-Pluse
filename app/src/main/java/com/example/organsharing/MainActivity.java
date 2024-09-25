package com.example.organsharing;

import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.organsharing.view.LoginActivity;
import com.example.organsharing.view.PatientLogin;
import com.example.organsharing.view.PatientRegistration;
import com.example.organsharing.view.RegisterActivity;
import android.os.Handler;
import android.widget.ImageView;



public class MainActivity extends AppCompatActivity {

    Button b1,b2,b4,b5,b6;
    private static final int HIDE_DELAY = 2000; // 2 seconds
    private ImageView logoImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b6 = (Button) findViewById(R.id.adminloginbutton);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (permissionAlreadyGranted()) {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
                requestPermission();
            }
        });

        b1 = (Button) findViewById(R.id.bonorloginButton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (permissionAlreadyGranted()) {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
                requestPermission();
            }
        });
        b2 = (Button) findViewById(R.id.donorregisterButton);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);

            }
        });

        b4 = (Button) findViewById(R.id.patientregisterButton);

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (permissionAlreadyGranted()) {
                    Intent i = new Intent(getApplicationContext(), PatientRegistration.class);
                    startActivity(i);
                }
                requestPermission();
            }
        });

        b5 = (Button) findViewById(R.id.patientloginButton);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), PatientLogin.class);
                startActivity(i);

            }
        });
    }

    private boolean permissionAlreadyGranted() {

        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int result3 = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        List l=new ArrayList();
        l.add(result1);
        l.add(result2);
        l.add(result3);

        Log.v("results :",l.toString());
        Log.v("Permission Granted :",PackageManager.PERMISSION_GRANTED+"");

        if (result1 == PackageManager.PERMISSION_GRANTED
                && result2==PackageManager.PERMISSION_GRANTED
                && result3==PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.SEND_SMS
        },1);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.v("requesat code:", requestCode + "");

        for (String s : permissions) {
            Log.v("permission:", s);
        }

        for (int i : grantResults) {
            Log.v("result:", i + "");
        }

        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show();
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
                if (!showRationale) {
                    openSettingsDialog();
                }
            }
        }
    }

    private void openSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Required Permissions");
        builder.setMessage("This app require permission to use awesome feature. Grant them in app settings.");
        builder.setPositiveButton("Take Me To SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
