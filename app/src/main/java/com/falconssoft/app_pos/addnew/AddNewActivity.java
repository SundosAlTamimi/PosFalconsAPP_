package com.falconssoft.app_pos.addnew;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.falconssoft.app_pos.DatabaseHandler;
import com.falconssoft.app_pos.R;
import com.falconssoft.app_pos.models.Items;

import java.util.ArrayList;
import java.util.List;

public class AddNewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseHandler databaseHandler;
    private List<Items> categoryItemsList = new ArrayList<>();
    private ImageView addNewCategory;
    private AddNewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        recyclerView = new RecyclerView(this);
        databaseHandler = new DatabaseHandler(this);
        categoryItemsList = databaseHandler.getAllCategory();
        recyclerView = findViewById(R.id.add_new_recycler);
        addNewCategory = findViewById(R.id.add_new_button);

        Log.e("size 1", "" + categoryItemsList.size());

        adapter = new AddNewAdapter(this, categoryItemsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
