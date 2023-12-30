package com.example.signupsignin.ChatsActivity;

import static com.example.signupsignin.Constants.Constants.NOTIFICATION_URL;
import static com.example.signupsignin.Constants.Constants.SERVER_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.signupsignin.Adapters.MessageAdapter;
import com.example.signupsignin.MainActivity;
import com.example.signupsignin.Model.Message;
import com.example.signupsignin.Model.Users;
import com.example.signupsignin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public
class ChatsActivityDetails extends AppCompatActivity {
    private final String TAG = "fhdskj";
    String chat_mate_email, chat_mate_name, chat_mate_pic, chat_mate_token, user_name, user_email, user_pic;
    FirebaseAuth mAuth;
    private String chat_RoomID;
    EditText edit_txt_button;
    TextView name, sirnamr;

    ImageView backbtn, usersinglpic, callbtn, videocalbtn;
    FloatingActionButton sendButton;
    RecyclerView recyclerView;
    ArrayList<Message> messages;
    FirebaseDatabase firebaseDatabase;
    MessageAdapter messageAdapter;



    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        initVariable();
        getRoomMateDetail();
        upperTopBtnClick();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                onSendButtonClick(chat_RoomID, chat_mate_token, chat_mate_name);

            }
        });


    }

    private void upperTopBtnClick() {
        callbtn.setOnClickListener(view -> {
            Toast.makeText(ChatsActivityDetails.this, "clicked Not avalilable", Toast.LENGTH_SHORT).show();

        });

        backbtn.setOnClickListener(view -> {
            startActivity(new Intent(ChatsActivityDetails.this, MainActivity.class));

        });
        videocalbtn.setOnClickListener(view -> {
            callbtn.performClick();
        });
    }

    private void initVariable() {
        backbtn = findViewById(R.id.backBTN);
        usersinglpic = findViewById(R.id.userSinglePic);
        callbtn = findViewById(R.id.callbtn);
        videocalbtn = findViewById(R.id.videocallbtn);
        name = findViewById(R.id.UserNamesingle);
        sendButton = findViewById(R.id.sendButtonMSG);

    }


    private
    void onSendButtonClick(String chat_room_ID, String chat_mate_token, String senderName) {
        String txt_message = edit_txt_button.getText().toString();
        if (txt_message.isEmpty()) {
            Toast.makeText(this, "can not send empty message", Toast.LENGTH_SHORT).show();
            return;
        }
        Message message = new Message(txt_message, mAuth.getCurrentUser().getEmail(), chat_mate_email);
        edit_txt_button.setText("");
        FirebaseDatabase.getInstance().getReference("message/" + chat_room_ID).push().setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public
            void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    messageAdapter.notifyDataSetChanged();


                    recyclerView.scrollToPosition(messages.size() - 1);
                    prepareforNotification(senderName, txt_message, chat_mate_token);


                } else {
                    Log.e(TAG, "onComplete: " + task.getException().getMessage());
                    Toast.makeText(ChatsActivityDetails.this, "Message can not be send : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private
    void prepareforNotification(String titleTxt, String message, String token) {
        JSONObject to = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("title", titleTxt);
            data.put("message", message);
            to.put("data", data);
            to.put("to", token);
            sendNotification(to);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private
    void sendNotification(JSONObject to) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, NOTIFICATION_URL,to, response -> {
            Log.d("notification", "sendNotification: " + response);
        }, error -> {
            Log.d("notification", "sendNotification: " + error);
        }) {
            @Override
            public
            Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "key=" + SERVER_KEY);
                map.put("Content-Type", "application/json");
                return map;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

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
        if (chat_mate_name.compareTo(user_name) > 0) {
            chat_RoomID = user_name + chat_mate_name;
        } else {
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
        chat_mate_token = intent.getStringExtra("chat_mate_token");
        edit_txt_button = findViewById(R.id.edit_text_message);
        Glide.with(getApplicationContext()).load(chat_mate_pic).error(R.drawable.pic_demo).placeholder(R.drawable.download).into(usersinglpic);
        name.setText(chat_mate_name);


        messages = new ArrayList<>();

        messageAdapter = new MessageAdapter(messages, this, user_pic, chat_mate_pic);
        recyclerView = findViewById(R.id.chatsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(messageAdapter);
//
        Log.e(TAG, "getRoomMateDetail: " + "solve or not");
        firebaseGetCurrentUserDetail();

    }
}