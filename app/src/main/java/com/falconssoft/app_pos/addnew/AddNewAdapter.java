package com.falconssoft.app_pos.addnew;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.falconssoft.app_pos.DatabaseHandler;
import com.falconssoft.app_pos.R;
import com.falconssoft.app_pos.add_item;
import com.falconssoft.app_pos.category.CategoryActivity;
import com.falconssoft.app_pos.models.CategoryModel;
import com.falconssoft.app_pos.models.Items;

import java.util.List;

public class AddNewAdapter extends RecyclerView.Adapter<AddNewViewHolder> {

    private List<CategoryModel> categoryList;
    private AddNewActivity context;
    private DatabaseHandler databaseHandler;

    public AddNewAdapter(AddNewActivity context, List<CategoryModel> categoryList) {
        this.categoryList = categoryList;
        this.context = context;
        databaseHandler = new DatabaseHandler(context);
//        Log.e("size 2", "");
    }

    @NonNull
    @Override
    public AddNewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        Log.e("size 3", "");
        View view = LayoutInflater.from(context).inflate(R.layout.add_new_row, viewGroup, false);
        return new AddNewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddNewViewHolder addNewViewHolder, final int i) {
//        Log.e("size 4", "" + i);

        addNewViewHolder.name.setText(categoryList.get(i).getCategoryName());
        String image = categoryList.get(i).getCategoryPic();
        if (image == null || (image.equals(""))) {
            addNewViewHolder.categoryImage.setBackgroundResource(R.drawable.ice_4);
        }
        else {
            Drawable drawable = new BitmapDrawable(context.getResources(), context.stringToBitmap(image));
            addNewViewHolder.categoryImage.setBackground(drawable);
        }
//        addNewViewHolder.categoryImage.setBackground(context.stringToBitmap(categoryList.get(i).getCategoryPic()));

        addNewViewHolder.deleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete Category")
                        .setMessage("Are you sure you want to delete this entry?" +
                                "\n \n \n Note : \"when Delete any Category the item for this category will be delete  \"")


                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                databaseHandler.deleteCategory(categoryList.get(i).getCategoryName());
                                databaseHandler.deleteItemForCategory(categoryList.get(i).getCategoryName());

                                context.fillAdapter();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_delete_black_24dp)
                        .show();

            }
        });

        addNewViewHolder.newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent additem = new Intent(context, add_item.class);
                additem.putExtra("categoryNames", addNewViewHolder.name.getText());
                context.startActivity(additem);

            }
        });
    }

    @Override
    public int getItemCount() {
//        Log.e("size 5", "");
        return categoryList.size();
    }


}
