package com.csis3175group6.bookapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;
import com.csis3175group6.bookapp.entities.Request;
import com.csis3175group6.bookapp.entities.User;

import java.util.ArrayList;
import java.util.Date;

public class BookAdapter extends RecyclerView.Adapter {

    private ArrayList<Book> books;
    private User user;
    private ArrayList<Request> requests;
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
        DatabaseOpenHelper db = new DatabaseOpenHelper(context);
        Book book = books.get(position);
        viewHolder.TitleTextView.setText(book.Title);
        viewHolder.AuthorTextView.setText("Author: " + book.Author);
        viewHolder.YearTextView.setText("Publication year: " + book.PublicationYear);
        viewHolder.StatusTextView.setText("Status: " + book.Status);
        viewHolder.PageCountTextView.setText("Page count: " + book.PageCount);
        requests = db.getActiveRequestsByBookId(book.Id);
        double total = book.RentDuration * book.RentPrice;
        switch (mode){
            case BORROW:
                user = db.getUser(book.OwnerId);
                viewHolder.OwnerNameTextView.setText("Owner: " + user.Name);
                if(total > 0){
                    viewHolder.TotalPriceTextView.setText("Total rent price is $" + total);
                }else{
                    viewHolder.TotalPriceTextView.setText("You can borrow this book for free <3");
                }
                //Update book status if the book available for rent and display rent information
                if(book.Status.equals(Book.STATUS_FOR_RENT)){
                    viewHolder.StatusTextView.setTextColor(Color.GREEN);
                    viewHolder.StatusTextView.setText("This book is available for rent.");
                    viewHolder.RentDurationTextView.setText("You can rent the book for " + book.RentDuration + " days.");
                    if(book.RentPrice > 0) viewHolder.RentPriceTextView.setText("Price: $" + book.RentPrice + "/day(s)");

                }
                //Check if book has been requested then change the color and status of the book
                for (Request request : requests) {
                    if(request.RequesterId == App.getInstance().User.Id)
                        viewHolder.StatusTextView.setTextColor(Color.MAGENTA);
                    viewHolder.StatusTextView.setText("You requested this book.\nWaiting for owner.");
                }
                break;
            case SHARE:
                if(book.Status.equals(Book.STATUS_FOR_RENT)) {
                    viewHolder.StatusTextView.setTextColor(requests.size() > 0 ? Color.MAGENTA : Color.GREEN);
                    viewHolder.StatusTextView.setText("You are sharing this book\nThere are " + requests.size()+ " request(s)!");
                    viewHolder.RentDurationTextView.setText("You are sharing the book for " + book.RentDuration + " days.");
                    if(book.RentPrice > 0) viewHolder.RentPriceTextView.setText("Price: $" + book.RentPrice + "/day(s)");
                } else if (book.Status.equals(Book.STATUS_GIVEAWAY)) {
                    viewHolder.StatusTextView.setTextColor(requests.size() > 0 ? Color.MAGENTA : Color.GREEN);
                    viewHolder.StatusTextView.setText("You are giving away this book\nThere are " + requests.size()+ " request(s)!");
                } else if (book.Status.equals(Book.STATUS_ACTIVE)) {
                    viewHolder.StatusTextView.setTextColor(Color.GREEN);
                    viewHolder.StatusTextView.setText("You can share this book.");
                } else if(book.Status.equals(Book.STATUS_RENTED)) {
                    User bookHolder = db.getUser(book.HolderId);
                    viewHolder.StatusTextView.setTextColor(Color.BLUE);
                    viewHolder.StatusTextView.setText("This book is shared to " + bookHolder.Name);
                }
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

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView TitleTextView, OwnerNameTextView, AuthorTextView, YearTextView, StatusTextView, PageCountTextView, RentPriceTextView, RentDurationTextView, RentTimeTextView, TotalPriceTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TitleTextView = itemView.findViewById(R.id.title_textview);
            OwnerNameTextView = itemView.findViewById(R.id.owner_name_textview);
            AuthorTextView = itemView.findViewById(R.id.author_textview);
            YearTextView = itemView.findViewById(R.id.year_textview);
            StatusTextView = itemView.findViewById(R.id.status_textview);
            PageCountTextView = itemView.findViewById(R.id.page_count_textview);
            RentPriceTextView = itemView.findViewById(R.id.rent_price_textview);
            RentDurationTextView = itemView.findViewById(R.id.rent_duration_textview);
            RentTimeTextView = itemView.findViewById(R.id.rent_time_textview);
            TotalPriceTextView = itemView.findViewById(R.id.total_price_textview);
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
