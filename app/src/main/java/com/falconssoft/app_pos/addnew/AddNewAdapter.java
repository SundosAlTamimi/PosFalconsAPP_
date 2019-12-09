package com.falconssoft.app_pos.addnew;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.falconssoft.app_pos.R;
import com.falconssoft.app_pos.models.Items;

import java.util.List;

public class AddNewAdapter extends RecyclerView.Adapter<AddNewViewHolder> {

    private List<Items> categoryList;
    private AddNewActivity context;

    public AddNewAdapter(AddNewActivity context, List<Items> categoryList) {
        this.categoryList = categoryList;
        this.context = context;
//        Log.e("size 2", "");
    }

    @NonNull
    @Override
    public AddNewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        Log.e("size 3", "");
        View view = LayoutInflater.from(context).inflate(R.layout.add_new_row,viewGroup, false);
        return new AddNewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddNewViewHolder addNewViewHolder, int i) {
//        Log.e("size 4", "" + i);

        addNewViewHolder.name.setText(categoryList.get(i).getCategoryName());
        addNewViewHolder.categoryImage.setImageBitmap(context.stringToBitmap(categoryList.get(i).getCategoryPic()));

        addNewViewHolder.deleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addNewViewHolder.newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
//        Log.e("size 5", "");
        return categoryList.size();
    }


}
