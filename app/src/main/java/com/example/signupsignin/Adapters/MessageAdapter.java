package com.example.signupsignin.Adapters;



import android.content.Context;

import android.media.MediaPlayer;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.signupsignin.Model.Message;
import com.example.signupsignin.Model.Users;
import com.example.signupsignin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public
class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private ArrayList<Message> messages;
    private Context context;
    private String userIMG,chats_mateIMG;

    public
    MessageAdapter(ArrayList<Message> messages, Context context, String userIMG, String chats_mateIMG) {
        this.messages = messages;
        this.context = context;
        this.userIMG = userIMG;
        this.chats_mateIMG = chats_mateIMG;
    }

    @NonNull
    @Override
    public
    MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_holder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public
    void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        ConstraintLayout constraintLayout = holder.ccll;

        holder.textMessage.setText(messages.get(position).getContent());
        if (messages.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.clear(R.id.small_profile_img,ConstraintSet.LEFT);
            constraintSet.clear(R.id.txt_cardView,ConstraintSet.LEFT);
            constraintSet.connect(R.id.small_profile_img,ConstraintSet.RIGHT,R.id.ccll,ConstraintSet.RIGHT,0);
            constraintSet.connect(R.id.txt_cardView,ConstraintSet.RIGHT,R.id.small_profile_img,ConstraintSet.LEFT,0);
            constraintSet.applyTo(constraintLayout);
//            Log.e("messageAd", "onBindViewHolder: 1111"+userIMG);
//            Log.e("messageAd", "onBindViewHolder: 1111"+chats_mateIMG);
            Glide.with(context).load(userIMG).error(R.drawable.pic_demo).placeholder(R.drawable.download).into(holder.imageView);

        }
        else {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.clear(R.id.small_profile_img,ConstraintSet.RIGHT);
            constraintSet.clear(R.id.txt_cardView,ConstraintSet.RIGHT);
            constraintSet.connect(R.id.small_profile_img,ConstraintSet.LEFT,R.id.ccll,ConstraintSet.LEFT,0);
            constraintSet.connect(R.id.txt_cardView,ConstraintSet.LEFT,R.id.small_profile_img,ConstraintSet.RIGHT,0);
            constraintSet.applyTo(constraintLayout);
            Glide.with(context).load(chats_mateIMG).error(R.drawable.pic_demo).placeholder(R.drawable.download).into(holder.imageView);


        }
    }

    @Override
    public
    int getItemCount() {
        return messages.size();
    }

    public
    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout ccll;
        ImageView imageView;
        TextView textMessage;
//        MediaPlayer mp;
        public
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ccll = itemView.findViewById(R.id.ccll);
            imageView =itemView.findViewById(R.id.small_profile_img);
            textMessage = itemView.findViewById(R.id.txt_message);
            firebaseuser();
        }
    }
    private
    void firebaseuser() {

       FirebaseAuth mAuth = FirebaseAuth.getInstance();
       FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("user/" + mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public
            void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                userIMG = users.getProfilePic();
            }


            @Override
            public
            void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
