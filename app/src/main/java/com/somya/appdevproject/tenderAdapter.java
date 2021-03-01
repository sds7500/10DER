package com.somya.appdevproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class tenderAdapter extends FirestoreRecyclerAdapter<activeTender,tenderAdapter.tenderHolder> {
    public tenderAdapter(@NonNull FirestoreRecyclerOptions<activeTender> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull tenderHolder holder, int position, @NonNull activeTender model) {
        holder.TenderTitle.setText(model.getTitle());
        holder.TenderDescription.setText(model.getDescription());
        holder.TenderTandc.setText(model.getTandc());
    }

    @NonNull
    @Override
    public tenderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.active_tender_item,parent,false);
        return new tenderHolder(v);
    }

    class tenderHolder extends RecyclerView.ViewHolder{

        TextView TenderTitle,TenderDescription,TenderTandc;

        public tenderHolder(@NonNull View itemView) {
            super(itemView);
            TenderTitle=itemView.findViewById(R.id.activetitle);
            TenderDescription=itemView.findViewById(R.id.activedesc);
            TenderTandc=itemView.findViewById(R.id.activetandc);
        }
    }
}
