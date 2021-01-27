package com.example.heat_beat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

class AdaptorShowRec extends FirebaseRecyclerAdapter<Person_data, AdaptorShowRec.ViewHolderPer>{
    private FirebaseRecyclerOptions<Person_data> listData;
    Context context;

    public AdaptorShowRec(@NonNull FirebaseRecyclerOptions<Person_data> options,Context context) {
        super(options);
        this.context = context;
    }

//    public AdaptorShowRec(FirebaseRecyclerOptions<Person_data> listData, Context context) {
//        this.listData = listData;
//        this.context = context;
//
//    }

    @NonNull
    @Override
    public AdaptorShowRec.ViewHolderPer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolderPer(view);
    }



//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position,Person_data model) {
////        Person_data ld=listData.(position);
//        holder.txtname.setText(model.getName());
//        holder.stresstxt.setText(model.getStresslevel());
//        holder.datetxt.setText(model.getDate());
//        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Person_Records").child("Data").child(ld.getId());
//                databaseReference.removeValue();
//                Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    protected void onBindViewHolder(@NonNull AdaptorShowRec.ViewHolderPer holder, int i, @NonNull Person_data model) {
        holder.txtname.setText(model.getName());
        holder.stresstxt.setText(model.getStresslevel());
        holder.datetxt.setText(model.getDate());
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Person_Records").child("Data")
                        .child(getRef(i).getKey())
                        .setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

//
//    @Override
//    public int getItemCount() {
//        return listData.size();
//    }




    public class ViewHolderPer extends RecyclerView.ViewHolder{
        private TextView txtname,datetxt,stresstxt;
        ImageView deletebtn;
        public ViewHolderPer(View itemView) {
            super(itemView);
            txtname=(TextView)itemView.findViewById(R.id.nametxt);
            datetxt=(TextView)itemView.findViewById(R.id.datetxt);
            stresstxt =(TextView) itemView.findViewById(R.id.stressvaluetxt);
            deletebtn = (ImageView) itemView.findViewById(R.id.deleteicon);

        }
    }

}
