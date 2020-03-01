package com.example.fayoUM;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fayoUM.util.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RecyclerViewAdapter extends FirestoreRecyclerAdapter<User, RecyclerViewAdapter.ViewHolder> {
    Context context;

    public RecyclerViewAdapter(@NonNull FirestoreRecyclerOptions options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull final User model) {
        holder.tvName.setText(model.getFullName());
        holder.tvEmail.setText(model.getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, UdetailA.class);
                intent.putExtra("user", model);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getSnapshots().getSnapshot(holder.getAdapterPosition()).getReference().delete();
                return true;
            }
        });
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvEmail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail=itemView.findViewById(R.id.tvEmail);
            tvName=itemView.findViewById(R.id.tvName);

        }
    }
}
