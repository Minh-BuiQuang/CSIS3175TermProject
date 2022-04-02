package com.csis3175group6.bookapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;
import com.csis3175group6.bookapp.entities.User;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter {

    private ArrayList<Book> books;
    private ArrayList<User> users;
    private User user;
    private LayoutInflater inflater;
    private Mode mode;
    private Context context;
    private ItemClickListener itemClickListener;


    public enum Mode{
        SHARE,
        EDIT,
        BORROW,
        TRACK,
        UPDATE
    }
    IShareButtonClickListener shareButtonClickListener;
    public BookAdapter (Context context, ArrayList<Book> books, Mode mode) {
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BookAdapter.ViewHolder viewHolder = (BookAdapter.ViewHolder)holder;
        viewHolder.TitleTextView.setText(books.get(position).Title);
        DatabaseOpenHelper db = new DatabaseOpenHelper(context);

        switch (mode){
            case BORROW:
                viewHolder.ShareButton.setVisibility(View.INVISIBLE);
                viewHolder.TitleTextView.setText(books.get(position).Title);
                user = db.getUser(books.get(position).OwnerId);
                viewHolder.OwnerNameTextView.setText("Owner: " + user.Name);
                viewHolder.AuthorTextView.setText("Author: " + books.get(position).Author);
                viewHolder.YearTextView.setText("Publication year: " + books.get(position).PublicationYear);
                viewHolder.StatusTextView.setText("Status: " + books.get(position).Status);
            break;
//            case SHARE:
//                viewHolder.TitleTextView.setText(books.get(position).Title);
//                viewHolder.AuthorTextView.setText("Author: " + books.get(position).Author);
//                viewHolder.YearTextView.setText("Publication year: " + books.get(position).PublicationYear);
//                viewHolder.StatusTextView.setText("Status: " + books.get(position).Status);
//                break;
            case SHARE:
                viewHolder.TitleTextView.setText(books.get(position).Title);
                viewHolder.AuthorTextView.setText("Author: " + books.get(position).Author);
                viewHolder.YearTextView.setText("Publication year: " + books.get(position).PublicationYear);
                viewHolder.StatusTextView.setText("Status: " + books.get(position).Status);
                viewHolder.ShareButton.setText("Update");
                viewHolder .ShareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, UpdateActivity.class);
                        i.putExtra("bookid", books.get(position).Id);
                        i.putExtra("title", books.get(position).Title);
                        i.putExtra("holderid", books.get(position).HolderId);
                        i.putExtra("author", books.get(position).Author);
                        i.putExtra("year", books.get(position).PublicationYear);
                        i.putExtra("status", books.get(position).Status);
                        i.putExtra("rentprice", books.get(position).RentPrice);
                        i.putExtra("pagecount", books.get(position).PageCount);
                        i.putExtra("isbn", books.get(position).Isbn);
                        context.startActivity(i);
                    }
                });
                break;


        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public Book getItem(int id){
        return books.get(id);
    }

    public void setShareButtonClickListener(IShareButtonClickListener shareButtonClickListener) {
        this.shareButtonClickListener = shareButtonClickListener;
    }

    public interface IShareButtonClickListener {
        void onShareButtonClickListener(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View
            .OnClickListener{
        Button ShareButton;
        TextView TitleTextView, OwnerNameTextView, AuthorTextView, YearTextView, StatusTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ShareButton = itemView.findViewById(R.id.share_btn);
            TitleTextView = itemView.findViewById(R.id.title_textview);
            OwnerNameTextView = itemView.findViewById(R.id.owner_name_textview);
            AuthorTextView = itemView.findViewById(R.id.author_textview);
            YearTextView = itemView.findViewById(R.id.year_textview);
            StatusTextView = itemView.findViewById(R.id.status_textview);
            TitleTextView.setOnClickListener(view -> {
                if(shareButtonClickListener != null) {
                    shareButtonClickListener.onShareButtonClickListener(view, getAdapterPosition());
                }
            });
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if(itemClickListener!=null)
                itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }



    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
