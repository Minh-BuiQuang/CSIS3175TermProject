package com.csis3175group6.bookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csis3175group6.bookapp.entities.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter {
    private  ArrayList<User> users;
    private LayoutInflater inflater;
    private boolean isRequestMode;
    private IClickEvents iClickEvents;
    public UserAdapter (Context context, ArrayList<User> users, boolean isRequestMode) {
        inflater = LayoutInflater.from(context);
        this.users = users;
        this.isRequestMode = isRequestMode;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_recycler_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    void setClickEvents(IClickEvents iClickEvents) {
        this.iClickEvents = iClickEvents;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.NameTextView.setText(users.get(position).Name);
        viewHolder.PhoneTextView.setText(users.get(position).Phone);
        viewHolder.AddressTextView.setText(users.get(position).Address);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button EditButton, MessageButton, AcceptButton;
        TextView NameTextView, PhoneTextView, AddressTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NameTextView = itemView.findViewById(R.id.textview_username);
            PhoneTextView = itemView.findViewById(R.id.textview_phone);
            AddressTextView = itemView.findViewById(R.id.textview_address);
            EditButton = itemView.findViewById(R.id.edit_btn);
            MessageButton = itemView.findViewById(R.id.message_btn);
            AcceptButton = itemView.findViewById(R.id.accept_btn);
            EditButton.setOnClickListener(view -> {
                if(iClickEvents != null) iClickEvents.onItemEditButtonClick(view, getAdapterPosition());
            });
            MessageButton.setOnClickListener(view -> {
                if(iClickEvents != null) iClickEvents.onItemMessageButtonClick(view, getAdapterPosition());
            });
            AcceptButton.setOnClickListener(view -> {
                if(iClickEvents != null) iClickEvents.onItemAcceptButtonClick(view, getAdapterPosition());
            });
            if(isRequestMode) {
                AcceptButton.setVisibility(View.VISIBLE);
                EditButton.setVisibility(View.GONE);
            }
        }
    }

    public interface IClickEvents {
        void onItemEditButtonClick(View view, int position);
        void onItemMessageButtonClick(View view, int position);
        void onItemAcceptButtonClick(View view, int position);
    }
}
