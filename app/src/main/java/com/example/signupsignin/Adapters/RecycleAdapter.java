package com.example.signupsignin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.signupsignin.ChatsActivity.ChatsActivityDetails;
import com.example.signupsignin.Model.Users;
import com.example.signupsignin.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public
class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    Context context;
    ArrayList<Users> arrayList;
    FirebaseAuth mAuth;
String current_user_email;

    public
    RecycleAdapter(Context context, ArrayList<Users> arrayList) {
        this.context = context;
        this.arrayList = arrayList;


    }


    @Override
    public
    ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_single_block,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public
    void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = arrayList.get(position);
        if(user.getUserEmail()!= current_user_email){

            holder.UserName.setText(user.getUsername());
            holder.LastMessage.setText(arrayList.get(position).getUserEmail());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public
                void onClick(View v) {
                    Intent intent = new Intent(context, ChatsActivityDetails.class);
//                Log.e("w", "intentActivity: "+"hogya" );
                    intent.putExtra("chat_mate_name",user.getUsername());
                    intent.putExtra("chat_mate_email",user.getUserEmail());
                    intent.putExtra("chat_mate_pic",user.getProfilePic());
                    intent.putExtra("chat_mate_token",user.getToken());
                    Toast.makeText(context, "hiii", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                }
            });
            // error  in placeholder no error but image is not fetch
            Glide.with(context).load(arrayList.get(position).getProfilePic()).error(R.drawable.pic_demo).placeholder(R.drawable.download).into(holder.imageView);

        }



    }

    @Override
    public
    int getItemCount() {
        return arrayList.size();

    }

    public
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView UserName,LastMessage;
        LinearLayout linearLayout;
        ImageView imageView;
        public
        ViewHolder(@NonNull View itemView) {
            super(itemView);

//current_user_email = mAuth.getCurrentUser().getEmail();
            UserName = itemView.findViewById(R.id.userName);
            LastMessage = itemView.findViewById(R.id.lastMessage);
            linearLayout = itemView.findViewById(R.id.userBlock);
            imageView = itemView.findViewById(R.id.userPic);
        }
    }
}
