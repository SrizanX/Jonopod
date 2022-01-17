package com.olympiandroids.jonopod.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.olympiandroids.jonopod.databinding.ItemAnnouncementBinding;
import com.olympiandroids.jonopod.model.Announcement;

public class AnnouncementAdapter extends FirestoreRecyclerAdapter<Announcement, AnnouncementAdapter.AnnouncementHolder> {

    public AnnouncementAdapter(@NonNull FirestoreRecyclerOptions<Announcement> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AnnouncementHolder holder, int position, @NonNull Announcement model) {
        
    }

    @NonNull
    @Override
    public AnnouncementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAnnouncementBinding binding = ItemAnnouncementBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AnnouncementHolder(binding);
    }

    public static class AnnouncementHolder extends RecyclerView.ViewHolder {
        ItemAnnouncementBinding binding;
        public AnnouncementHolder(@NonNull ItemAnnouncementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
