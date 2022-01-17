package com.olympiandroids.jonopod.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.olympiandroids.jonopod.databinding.ItemPersonListBinding;
import com.olympiandroids.jonopod.model.ServiceProfile;

public class ServiceProfileAdapterAll extends FirestoreRecyclerAdapter<ServiceProfile, ServiceProfileAdapterAll.ProfileHolder> {

    public ServiceProfileAdapterAll(@NonNull FirestoreRecyclerOptions<ServiceProfile> options) {
        super(options);
        Log.d("ASD", "Constructor");
    }

    @Override
    protected void onBindViewHolder(@NonNull ServiceProfileAdapterAll.ProfileHolder holder, int position, @NonNull ServiceProfile model) {

        Glide.with(holder.binding.getRoot())
                .load(model.getImageLink())
                .centerCrop()
                .into(holder.binding.imageView7);
        holder.binding.tvName.setText(model.getName());
        holder.binding.tvSector.setText(model.getSector());
        holder.binding.tvDesignation.setText(model.getDesignation());
    }

    @NonNull
    @Override
    public ServiceProfileAdapterAll.ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemPersonListBinding binding = ItemPersonListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProfileHolder(binding);
    }

    public static class ProfileHolder extends RecyclerView.ViewHolder {
        ItemPersonListBinding binding;
        public ProfileHolder(@NonNull ItemPersonListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
