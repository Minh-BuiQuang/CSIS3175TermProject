package com.csis3175group6.bookapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    String mTitle[] = {"ADD A BOOK", "MY BOOK", "UPDATE BOOK", "BORROW BOOK", "TRACKING BOOK"};
    String mDescription[] = {"Add your favourite books to library", "View book list", "Update book information",
                            "Let's broaden your horizon", "Tracking your activities"};
    int images[] = {R.drawable.add_img, R.drawable.view_img, R.drawable.update_img, R.drawable.borrow_img1, R.drawable.track_img};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);

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
                    intent.putExtra("title", mTitle[1]);
                    intent.putExtra("description", mDescription[1]);
                    intent.putExtra("position", ""+1);
                    startActivity(intent);
                }

                if (position == 2) {
                    Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.option_menu, menu);
//
//        //Hide admin options if user is not an admin
//        if(App.getInstance().User.Role == User.UserRole.User) {
//            MenuItem item = menu.findItem(R.id.itemUserList);
//            item.setVisible(false);
//        }
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.itemUserInfo:
//                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
//                intent.putExtra(getString(R.string.stringUserId), App.getInstance().User.Id);
//                startActivity(intent);
//                break;
//            case R.id.itemLogOut:
//                //Set user to null and how login screen
//                App.getInstance().User = null;
//                finish();
//                startActivity(new Intent(MainActivity.this, SignInActivity.class));
//        }
//        return true;
//    }

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