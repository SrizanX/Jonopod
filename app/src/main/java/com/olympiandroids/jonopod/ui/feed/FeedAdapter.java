package com.olympiandroids.jonopod.ui.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.olympiandroids.jonopod.R;
import com.olympiandroids.jonopod.databinding.ItemFeedBinding;
import com.olympiandroids.jonopod.model.Report;
import com.olympiandroids.jonopod.model.User;

public class FeedAdapter extends FirestoreRecyclerAdapter<Report, FeedAdapter.ViewHolder> {

    private final FeedClickListener mClickListener;

    private final String TAG = FeedAdapter.class.getName();

    public FeedAdapter(@NonNull FirestoreRecyclerOptions<Report> options, FeedClickListener clickListener) {
        super(options);
        mClickListener = clickListener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFeedBinding binding = ItemFeedBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FeedAdapter.ViewHolder(binding);
    }



    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Report model) {


        if(model.isAnonymous()) {
            holder.binding.textViewName.setText("Anonymous");
            Glide.with(holder.binding.getRoot())
                    .load(R.drawable.ic_account)
                    .centerCrop()
                    .into(holder.binding.imageViewProfilePicture);
        }
        else {
            getName(model.getUserUID());
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            DocumentReference userRef = firestore.collection("users").document(model.getUserUID());
            userRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User user = documentSnapshot.toObject(User.class);
                            assert user != null;
                            holder.binding.textViewName.setText(user.getName());

                        }
                    });


        }
        Glide.with(holder.binding.getRoot())
                .load(model.getImageUrl())
                .centerCrop()
                .into(holder.binding.imageViewImage1);
        holder.binding.textViewPostText.setText(model.getStatement());
    }

    private void getName(String userUID) {


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemFeedBinding binding;
        public ViewHolder(@NonNull ItemFeedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.imageViewImage1.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onFeedClick(getSnapshots().getSnapshot(getBindingAdapterPosition()),getBindingAdapterPosition());
        }
    }

    public interface FeedClickListener{
        void onFeedClick(DocumentSnapshot documentSnapshot, int position);
    }
}
