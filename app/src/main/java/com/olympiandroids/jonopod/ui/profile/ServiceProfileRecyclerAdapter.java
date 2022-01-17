package com.olympiandroids.jonopod.ui.profile;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.olympiandroids.jonopod.databinding.ItemServiceProfileBinding;
import com.olympiandroids.jonopod.model.ServiceProfile;

public class ServiceProfileRecyclerAdapter extends FirestoreRecyclerAdapter<ServiceProfile, ServiceProfileRecyclerAdapter.ProfileHolder> {

    private final ServiceProfileClickListener mClickListener;
    public ServiceProfileRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ServiceProfile> options,ServiceProfileClickListener clickListener ) {
        super(options);
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("ASD", "01. onCreateViewHolder");
        ItemServiceProfileBinding binding = ItemServiceProfileBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProfileHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProfileHolder holder, int position, @NonNull ServiceProfile model) {
        Log.d("ASD", "03. onBindViewHolder");
        Glide.with(holder.binding.getRoot())
                .load(model.getImageLink())
                .centerCrop()
                .into(holder.binding.imageView7);

        holder.binding.tvName.setText(model.getName());
        holder.binding.tvSector.setText(model.getSector());
        holder.binding.tvDesignation.setText(model.getDesignation());

    }

    class ProfileHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemServiceProfileBinding binding;
        public ProfileHolder(@NonNull ItemServiceProfileBinding serviceProfileBinding){
        super(serviceProfileBinding.getRoot());
        binding = serviceProfileBinding;

        Log.d("ASD", "02. Profile ViewHolder Constructor");

        binding.ibEditProfile.setOnClickListener(this);
        binding.ibDelete.setOnClickListener(this);
    }

        @Override
        public void onClick(View view) {
            if (view.getId()==binding.ibDelete.getId()){
                mClickListener.onDeleteButtonClick(getSnapshots().getSnapshot(getBindingAdapterPosition()), getBindingAdapterPosition());
            }
            if (view.getId()==binding.ibEditProfile.getId()){
                mClickListener.onEditButtonClick(getSnapshots().getSnapshot(getBindingAdapterPosition()), getBindingAdapterPosition() );
            }
        }
    }

    public interface ServiceProfileClickListener{
        void onEditButtonClick(DocumentSnapshot documentSnapshot, int position);
        void onDeleteButtonClick(DocumentSnapshot documentSnapshot, int position);
    }
}
