package com.example.heat_beat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView valueheart;
    SimpleDateFormat simpleDateFormat;
    Button savebtn,getdata;
    String format;
    DatabaseReference myRef,myRef2;
    EditText username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        valueheart= findViewById(R.id.valuex);
        username = findViewById(R.id.username);
        getdata = findViewById(R.id.getdata);
        savebtn = findViewById(R.id.savebtn);
        valueheart.setVisibility(View.INVISIBLE);
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        format = simpleDateFormat.format(new Date());
       // Toast.makeText(this, "Current Timestamp: "+format, Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("heartbeat");
        myRef2 = database.getReference("Person_Records");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int carid = snapshot.child("data").getValue(Integer.class);
                //Toast.makeText(MainActivity.this, ""+carid, Toast.LENGTH_SHORT).show();
                valueheart.setText(String.valueOf(carid));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String a= username.getText().toString();
//                Map<String, User> users = new HashMap<>();
//                myRef.child("data").setValue()
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        valueheart.setVisibility(View.VISIBLE);
                    }
                }, 5000);
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= username.getText().toString();
                String stressvalue= valueheart.getText().toString();
                String key = myRef2.push().getKey().toString();

                Person_data dd= new Person_data(format,name,stressvalue,key);
                myRef2.child("Data").child(key).setValue(dd).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Data Inserted Successfull", Toast.LENGTH_SHORT).show();
                        clearbox();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }




    private void clearbox() {
    valueheart.setVisibility(View.INVISIBLE);
    username.setText("");
    }

    public void showrec(View view) {
        startActivity(new Intent(MainActivity.this,ShowRecordes.class));
    }
    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo wificon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobilecon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wificon != null && wificon.isConnected()) || (mobilecon != null && mobilecon.isConnected())){
            return true;
        }
        else {
            return false;
        }
    }
    private void ShowCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Please Check Your Internet Connectionn").setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),ShowRecordes.class));
                finish();
            }
        });
        builder.show();
    }
}