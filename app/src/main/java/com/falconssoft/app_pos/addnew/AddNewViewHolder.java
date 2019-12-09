package com.falconssoft.app_pos.addnew;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.falconssoft.app_pos.R;

public class AddNewViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    ImageView categoryImage, newItem, deleteCategory;

    public AddNewViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.view_name_row);
        categoryImage = itemView.findViewById(R.id.view_image_row);
        newItem = itemView.findViewById(R.id.view_add_new_item_row);
        deleteCategory = itemView.findViewById(R.id.view_add_new_delete_row);

    }
}
