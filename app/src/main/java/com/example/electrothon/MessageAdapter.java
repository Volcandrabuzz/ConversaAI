package com.example.electrothon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{
    List<Message> msglist;
    private MessageAdapter.MyViewHolder MyViewHolder;

    public MessageAdapter(List<Message> msglist) {
        this.msglist = msglist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View chatView= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat,null);
        MyViewHolder myViewHolder= new MyViewHolder(chatView);
        return MyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message=msglist.get(position);
        if(message.getSendby().equals(Message.SENT_BY_ME)){
            holder.leftchat.setVisibility(View.GONE);
            holder.rightchat.setVisibility(View.VISIBLE);
            holder.rightchattext.setText(message.getMessage());
        }
        else{
            holder.rightchat.setVisibility(View.GONE);
            holder.leftchat.setVisibility(View.VISIBLE);
            holder.leftchattext.setText(message.getMessage());

        }

    }

    @Override
    public int getItemCount() {
        return msglist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftchat,rightchat;
        TextView leftchattext,rightchattext;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            leftchat=itemView.findViewById(R.id.left_chatView);
            rightchat=itemView.findViewById(R.id.right_chatView);
            leftchattext=itemView.findViewById(R.id.left_chat_text_view);
            rightchattext=itemView.findViewById(R.id.right_chat_text_view);
        }

    }
}
