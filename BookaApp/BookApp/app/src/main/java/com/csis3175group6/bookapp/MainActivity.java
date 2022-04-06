package com.csis3175group6.bookapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.csis3175group6.bookapp.dataaccess.DatabaseOpenHelper;
import com.csis3175group6.bookapp.entities.Book;
import com.csis3175group6.bookapp.entities.User;

public class MainActivity extends AppCompatActivity {
    DatabaseOpenHelper db;
    ListView listView;

    String mTitle[] = {"ADD A BOOK", "MY BOOK", "UPDATE BOOK", "BORROW BOOK", "TRACKING BOOK"};
    String mDescription[] = {"Add your favourite books to library", "View book list", "Update book information",
                            "Let's broaden your horizon", "Tracking your activities"};
    int images[] = {R.drawable.add_img, R.drawable.view_img, R.drawable.update_img, R.drawable.borrow_img1, R.drawable.track_img};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Welcome, " + App.getInstance().User.Name + "!");
        listView = findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);
        db = new DatabaseOpenHelper(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                    // this intent put our 0 index image to another activity
                    Bundle bundle = new Bundle();
                    bundle.putInt("image", images[0]);
                    intent.putExtras(bundle);
                    intent.putExtra("title", mTitle[0]);
                    intent.putExtra("description", mDescription[0]);
                    intent.putExtra("position", ""+0);
                    startActivity(intent);

                }
                if (position == 1) {
                    Intent intent = new Intent(getApplicationContext(), ViewBookActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("image", images[1]);
                    intent.putExtras(bundle);
                   long ownerId = App.getInstance().User.Id;
                    Cursor c = db.getBookById(ownerId);
                    if(c != null) {
                        String[] myBooks = new String[c.getCount()];
                        int index = 0;
                        while (c.moveToNext()) {
                            String bName = "Title: " + c.getString(1);
                            String bAuthor = "Author: " + c.getString(5);
                            String bYear = "Publication year: " + c.getString(6);
                            String bStatus = "Status: " + c.getString(9);
                            String bIsnb = "Isbn: " + c.getString(4);
                            String bDesctiption = "Description: " + c.getString(7);
                            String bPageCount = "Page count: " + c.getString(8);
                            myBooks[index] = bName + "\n" + bAuthor + "\n" + bYear + "\n" + bStatus + "\n" + bIsnb + "\n" + bDesctiption + "\n" + bPageCount + "\n";
                            index++;
                        }
                        intent.putExtra("book-array", myBooks);
                        //startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this,
                                "nothing to read", Toast.LENGTH_SHORT).show();
                    }
                    intent.putExtra("title", mTitle[1]);
                    intent.putExtra("description", mDescription[1]);
                    intent.putExtra("position", ""+1);
                    startActivity(intent);
                }

                if (position == 2) {
                    Intent intent = new Intent(getApplicationContext(), UpdateBookViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("image", images[2]);
                    intent.putExtras(bundle);
                    intent.putExtra("title", mTitle[2]);
                    intent.putExtra("description", mDescription[2]);
                    intent.putExtra("position", ""+2);
                    startActivity(intent);
                }
                
                if (position == 3) {
                    Intent intent = new Intent(getApplicationContext(), BorrowActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("image", images[3]);
                    intent.putExtras(bundle);
                    intent.putExtra("title", mTitle[3]);
                    intent.putExtra("description", mDescription[3]);
                    intent.putExtra("position", ""+3);
                    startActivity(intent);
                }

                if (position == 4) {
                    Intent intent = new Intent(getApplicationContext(), TrackingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("image", images[4]);
                    intent.putExtras(bundle);
                    intent.putExtra("title", mTitle[4]);
                    intent.putExtra("description", mDescription[4]);
                    intent.putExtra("position", ""+4);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

        //Hide admin options if user is not an admin
        User user = App.getInstance().User;
        if(user!= null && user.Role.equals(User.ROLE_USER)) {
            MenuItem item = menu.findItem(R.id.itemUserList);
            item.setVisible(false);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemUserInfo:
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                intent.putExtra(getString(R.string.stringUserId), App.getInstance().User.Id);
                startActivity(intent);
                break;
            case R.id.itemUserList:
                startActivity(new Intent(MainActivity.this, UserActivity.class));
                break;
            case R.id.itemMessageList:
                startActivity(new Intent(MainActivity.this, ViewMessageActivity.class));
                break;
            case R.id.itemLogOut:
                //Set user to null and how login screen
                App.getInstance().User = null;
                finish();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
        }
        return true;
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImages[];

        MyAdapter(Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImages = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.row, parent, false);

            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);

            images.setImageResource(rImages[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            return row;
        }
    }
}