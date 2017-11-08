package com.wzy.leftswiperemove;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    SwipeMenuListView lvSuggestions;
    MainActivity context;
    List<MyBean> suggestions = new ArrayList<>();
    CollectorListAdapter aaSuggestions;
    EditText etSuggestions;
    Button btnAdd, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlebar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffa500")));
        context = this;
        lvSuggestions = (SwipeMenuListView) findViewById(R.id.listViewSuggestions);
        etSuggestions = (EditText) findViewById(R.id.editTextSuggestions);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnSubmit = (Button) findViewById(R.id.buttonSubmit);




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), resultPage.class);
                ArrayList<String> strArraySugg = new ArrayList<String>();
                for (int i = 0; i < suggestions.size(); i++) {
                    strArraySugg.add(suggestions.get(i).getName());

                }
                intent.putExtra("numOfSugg", suggestions.size());
                intent.putStringArrayListExtra("suggestions", strArraySugg);
                startActivity(intent);
            }
        });


    }


    private void initData() {

        String suggestion = etSuggestions.getText().toString();
        if (suggestion.isEmpty() || suggestion.trim().equals("")) {
            Toast.makeText(getBaseContext(), "Please input a suggestion", Toast.LENGTH_LONG).show();
        } else {
            suggestions.add(new MyBean(suggestion));

            etSuggestions.setText("");
        }

        aaSuggestions = new CollectorListAdapter(context, suggestions);
        lvSuggestions.setAdapter(aaSuggestions);
        lvSuggestions.setEmptyView(context.findViewById(R.id.listViewTexts));




        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(context.getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(255, 74, 109)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setTitle("Delete");
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        lvSuggestions.setMenuCreator(creator);

        // step 2. listener item click event
        lvSuggestions.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                MyBean device = suggestions.get(position);

                switch (index) {
                    case 0:
                        // delete
                        Log.e(TAG, "onMenuItemClick: delete on click" + position);
                        delete(device);
                        suggestions.remove(position);
                        aaSuggestions.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        lvSuggestions.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        lvSuggestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                MyBean device = suggestions.get(position);
                open(device);
            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    private void open(MyBean item) {

    }

    private void delete(MyBean item) {

    }
}

