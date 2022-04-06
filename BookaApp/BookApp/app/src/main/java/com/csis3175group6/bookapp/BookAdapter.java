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

        viewHolder.TitleTextView.setText(books.get(position).Title);
        viewHolder.AuthorTextView.setText("Author: " + books.get(position).Author);
        viewHolder.YearTextView.setText("Publication year: " + books.get(position).PublicationYear);
        viewHolder.StatusTextView.setText("Status: " + books.get(position).Status);
        viewHolder.PageCountTextView.setText("Page count: " + books.get(position).PageCount);
//        viewHolder.DescriptionTextView.setText("Description: " + books.get(position).Description);
        //Update book status if the book available for rent and display rent information
        if(books.get(position).Status.equals(Book.STATUS_FOR_RENT)){
//            viewHolder.BookRequestLayout.setBackgroundColor(Color.GREEN);
            viewHolder.StatusTextView.setTextColor(Color.GREEN);
            viewHolder.StatusTextView.setText("This book is available for rent.");
            viewHolder.RentTimeTextView.setText("Rent start at " + books.get(position).RentedTime);
            viewHolder.RentDurationTextView.setText("You can rent the book for " + books.get(position).RentDuration + " days.");
            viewHolder.RentPriceTextView.setText("Rent price is $ " + books.get(position).RentPrice);
        }
        //Check if book has been requested then change the color and status of the book
        requests = db.getActiveRequestsByBookId(books.get(position).Id);
        for (Request request : requests) {
            if(request.HasCompleted == false && request.RequesterId == App.getInstance().User.Id)
                viewHolder.StatusTextView.setTextColor(Color.MAGENTA);
                viewHolder.StatusTextView.setText("Request in progress");
        }
        switch (mode){
            case BORROW:
                user = db.getUser(books.get(position).OwnerId);
                viewHolder.OwnerNameTextView.setText("Owner: " + user.Name);
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

    public interface IShareButtonClickListener {
        void onShareButtonClickListener(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView TitleTextView, OwnerNameTextView, AuthorTextView, YearTextView, StatusTextView, PageCountTextView, RentPriceTextView, RentDurationTextView, RentTimeTextView, DescriptionTextView;
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
