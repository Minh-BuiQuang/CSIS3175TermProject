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

public class UserAdapter extends RecyclerView.Adapter {
    private User[] users;
    private LayoutInflater inflater;
    private IClickEvents iClickEvents;
    public UserAdapter (Context context, User[] users) {
        inflater = LayoutInflater.from(context);
        this.users = users;
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
        viewHolder.NameTextView.setText(users[position].Name);
        viewHolder.PhoneTextView.setText(users[position].Phone);
        viewHolder.AddressTextView.setText(users[position].Address);
    }

    @Override
    public int getItemCount() {
        return users.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button EditButton, MessageButton;
        TextView NameTextView, PhoneTextView, AddressTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NameTextView = itemView.findViewById(R.id.textview_username);
            PhoneTextView = itemView.findViewById(R.id.textview_phone);
            AddressTextView = itemView.findViewById(R.id.textview_address);
            EditButton = itemView.findViewById(R.id.edit_btn);
            MessageButton = itemView.findViewById(R.id.message_btn);

            EditButton.setOnClickListener(view -> {
                if(iClickEvents != null) {
                    iClickEvents.onItemEditButtonClick(view, getAdapterPosition());
                }
            });

            MessageButton.setOnClickListener(view -> {
                if(iClickEvents != null) {
                    iClickEvents.onItemMessageButtonClick(view, getAdapterPosition());
                }
            });
        }
    }

    public interface IClickEvents {
        void onItemEditButtonClick(View view, int position);
        void onItemMessageButtonClick(View view, int position);
    }
}
