package com.csis3175group6.bookapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csis3175group6.bookapp.entities.Message;
import com.csis3175group6.bookapp.entities.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MessageAdapter extends RecyclerView.Adapter {

    private Context Context;
    private ArrayList<Message> Messages;
    private Mode Mode;
    private LayoutInflater inflater;
    private Context context;
    public enum Mode {
        Send,
        Receive,
        System
    }

    public MessageAdapter (Context context, ArrayList<Message> messages) {
        inflater = LayoutInflater.from(context);
        Context = context;
        Collections.sort(messages, (x,y) -> x.TimeStamp.compareTo(y.TimeStamp));
        Messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.message_recycler_view_item, parent, false);
        ViewHolder viewHolder = new MessageAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageAdapter.ViewHolder viewHolder = (MessageAdapter.ViewHolder)holder;
        Message message = Messages.get(position);
        viewHolder.MessageTextView.setText(message.Content);

        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM");
        viewHolder.TimeTextView.setText(timeFormat.format(message.TimeStamp.getTime()));
        viewHolder.DateTextView.setText(dateFormat.format(message.TimeStamp.getTime()));
        viewHolder.MessageTextView.setText(message.Content);

        User user = App.getInstance().User;
        //Format views for system message
        if(message.FromSystem) {
            viewHolder.MessageLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            viewHolder.MessageLayout.setBackgroundColor(Color.parseColor("#00000000"));
            viewHolder.TimeTextView.setVisibility(View.INVISIBLE);
            viewHolder.DateTextView.setVisibility(View.INVISIBLE);
            viewHolder.MessageTextView.setTextColor(Color.GREEN);
        }
        //Format Sent message
        else if(message.SenderId == user.Id) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.RIGHT;
            viewHolder.MessageLayout.setLayoutParams(params);
            viewHolder.MessageLayout.setBackground(Context.getDrawable(R.drawable.round_edittext_green));
        }
        //Format Received message
        else if(message.ReceiverId == user.Id) {
            viewHolder.MessageLayout.setGravity(Gravity.LEFT);

        }
    }

    @Override
    public int getItemCount() {
        return Messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout MessageLayout;
        TextView MessageTextView, TimeTextView, DateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MessageLayout = itemView.findViewById(R.id.message_layout);
            MessageTextView = itemView.findViewById(R.id.message_textview);
            TimeTextView = itemView.findViewById(R.id.time_textview);
            DateTextView = itemView.findViewById(R.id.time_textview);
        }
    }
}
