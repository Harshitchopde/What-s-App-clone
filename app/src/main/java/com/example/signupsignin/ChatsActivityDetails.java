package com.example.signupsignin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.signupsignin.Adapters.MessageAdapter;
import com.example.signupsignin.Model.Message;
import com.example.signupsignin.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public
class ChatsActivityDetails extends AppCompatActivity {
    private final String TAG = "fhdskj";
    String chat_mate_email, chat_mate_name, chat_mate_pic, user_name, user_email, user_pic;
    FirebaseAuth mAuth;
    private String chat_RoomID;
    EditText  edit_txt_button;
    FloatingActionButton sendButton;
    RecyclerView recyclerView;
    ArrayList<Message> messages;
    FirebaseDatabase firebaseDatabase;
    MessageAdapter messageAdapter;
    MediaPlayer mp;


    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        getRoomMateDetail();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                onSendButtonClick(chat_RoomID);

            }
        });


    }

    private
    void onSendButtonClick(String chat_room_ID) {
        String txt_message = edit_txt_button.getText().toString();
        if (txt_message.isEmpty()){
            Toast.makeText(this, "can not send empty message", Toast.LENGTH_SHORT).show();
            return;
        }
        Message message = new Message(txt_message,mAuth.getCurrentUser().getEmail(),chat_mate_email);
        FirebaseDatabase.getInstance().getReference("message/" + chat_room_ID).push().setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public
            void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    messageAdapter.notifyDataSetChanged();
                    edit_txt_button.setText("");
                    recyclerView.scrollToPosition(messages.size()-1);

                }
                else {
                    Log.e(TAG, "onComplete: "+task.getException().getMessage() );
                }
            }
        });


        }

    private
    void firebaseGetCurrentUserDetail() {
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference("user/" + mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public
            void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                user_name = users.getUsername();
                user_email = users.getUserEmail();
                user_pic = users.getProfilePic();
//                Log.e(TAG, "onDataChange: " +user_pic);
                setUPchatRoom();

            }

            @Override
            public
            void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private
    void setUPchatRoom() {
        if(chat_mate_name.compareTo(user_name)>0){
            chat_RoomID = user_name+chat_mate_name;
        }
        else {
        chat_RoomID = chat_mate_name + user_name;
        }
        attachMessageListnear(chat_RoomID);
    }

    private
    void attachMessageListnear(String chat_room_ID) {
        FirebaseDatabase.getInstance().getReference("message/" + chat_room_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public
            void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    messages.add(dataSnapshot.getValue(Message.class));
                }
                if(!messages.isEmpty()) {
                    if (!(messages.get(messages.size() - 1).getSender().equals(mAuth.getCurrentUser().getEmail()))) {


                        mp.start();
                    }
                }
                messageAdapter.notifyDataSetChanged();

            }

            @Override
            public
            void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private
    void getRoomMateDetail() {
        Intent intent = getIntent();
        chat_mate_email = intent.getStringExtra("chat_mate_email");
        chat_mate_name = intent.getStringExtra("chat_mate_name");
        chat_mate_pic = intent.getStringExtra("chat_mate_pic");
        sendButton = findViewById(R.id.sendButtonMSG);
        edit_txt_button = findViewById(R.id.edit_text_message);
        mp =MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI);

        messages = new ArrayList<>();

        messageAdapter = new MessageAdapter(messages, this, user_pic, chat_mate_pic);
        recyclerView = findViewById(R.id.chatsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(messageAdapter);
//
        firebaseGetCurrentUserDetail();

    }
}