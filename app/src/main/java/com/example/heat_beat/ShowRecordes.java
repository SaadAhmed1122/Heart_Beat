package com.example.heat_beat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ShowRecordes extends AppCompatActivity {
    private List<Person_data> listData;
    private RecyclerView rv;
    ImageView showreord;
    Person_data aa;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AdaptorShowRec adapter;
    DatabaseReference nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recordes);
        showreord = findViewById(R.id.refresh);
  //      mSwipeRefreshLayout = findViewById(R.id.swaplayout);
        rv = findViewById(R.id.recyshow);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        listData=new ArrayList<>();
        if(!isConnected(this)){
            ShowCustomDialog();
        }
        else {
            ProgressDialog Dialog = new ProgressDialog(ShowRecordes.this);
            Dialog.setMessage("Data Fatching...");
            Dialog.show();
            Query query = FirebaseDatabase.getInstance()
                    .getReference("Person_Records")
                    .child("Data");
            FirebaseRecyclerOptions<Person_data> options =
                    new FirebaseRecyclerOptions.Builder<Person_data>()
                            .setQuery(query, Person_data.class)
                            .build();
            adapter = new AdaptorShowRec(options, ShowRecordes.this);
            rv.setAdapter(adapter);
            Dialog.dismiss();

        }
//        showData();
        }

    private void ShowCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowRecordes.this);

        builder.setMessage("Please Check Your Internet Connectionn").setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           startActivity(new Intent(getApplicationContext(),MainActivity.class));
           finish();
            }
        });
        builder.show();
    }

    //    private void showData() {
//        nm= FirebaseDatabase.getInstance().getReference("Person_Records").child("Data");
//        nm.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
//                        Person_data l=npsnapshot.getValue(Person_data.class);
//                        listData.add(l);
//                    }
//                    adapter=new AdaptorShowRec(listData,ShowRecordes.this);
//                    rv.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//
//                    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                        @Override
//                        public void onRefresh() {
////                            listData.clear();
////                            for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
////                                Person_data l=npsnapshot.getValue(Person_data.class);
////                                listData.add(l);
////                            }
////                            adapter=new AdaptorShowRec(listData,ShowRecordes.this);
//                            rv.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                            mSwipeRefreshLayout.setRefreshing(false);
//
//                        }
//                    });
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
@Override
protected void onStart() {
    super.onStart();
    if(!isConnected(this)){
    }
    else {

        adapter.startListening();
    }

}

    @Override
    protected void onStop() {
        super.onStop();
        if(!isConnected(this)){
        }
        else {
            adapter.stopListening();
    }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processsearch(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String query) {
        Query query2 = FirebaseDatabase.getInstance()
                .getReference("Person_Records")
                .child("Data").orderByChild("name").startAt(query).endAt(query+"\uf8ff");
        FirebaseRecyclerOptions<Person_data> options =
                new FirebaseRecyclerOptions.Builder<Person_data>()
                        .setQuery(query2, Person_data.class)
                        .build();
        adapter = new AdaptorShowRec(options,ShowRecordes.this);
        adapter.startListening();
        rv.setAdapter(adapter);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
