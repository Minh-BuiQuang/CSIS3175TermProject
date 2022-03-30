package com.csis3175group6.bookapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csis3175group6.bookapp.R;
import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;
import com.csis3175group6.bookapp.entities.User;

public class BookAdapter extends RecyclerView.Adapter {

    private Book[] books;
    private User[] users;
    private User user;
    private LayoutInflater inflater;
    private Mode mode;
    private Context context;
    public enum Mode{
        SHARE,
        EDIT,
        BORROW,
        TRACK
    }
    IShareButtonClickListener shareButtonClickListener;
    public BookAdapter (Context context, Book[] books, Mode mode) {
        inflater = LayoutInflater.from(context);
        this.books = books;
        this.mode = mode;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.customized_layout, parent, false);
        ViewHolder viewHolder = new BookAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookAdapter.ViewHolder viewHolder = (BookAdapter.ViewHolder)holder;
        viewHolder.TitleTextView.setText(books[position].Title);
        DatabaseOpenHelper db = new DatabaseOpenHelper(context);
        user = db.getUser(books[position].OwnerId);
        viewHolder.OwnerNameTextView.setText(user.Name);
        switch (mode){
            case BORROW:
            viewHolder.ShareButton.setVisibility(View.INVISIBLE);
            break;
        }
    }

    @Override
    public int getItemCount() {
        return books.length;
    }

    public void setShareButtonClickListener(IShareButtonClickListener shareButtonClickListener) {
        this.shareButtonClickListener = shareButtonClickListener;
    }

    public interface IShareButtonClickListener {
        void onShareButtonClickListener(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button ShareButton;
        TextView TitleTextView;
        TextView OwnerNameTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ShareButton = itemView.findViewById(R.id.share_btn);
            TitleTextView = itemView.findViewById(R.id.title_textview);
            OwnerNameTextView = itemView.findViewById(R.id.owner_name_textview);

            TitleTextView.setOnClickListener(view -> {
                if(shareButtonClickListener != null) {
                    shareButtonClickListener.onShareButtonClickListener(view, getAdapterPosition());
                }
            });


        }
    }
}
