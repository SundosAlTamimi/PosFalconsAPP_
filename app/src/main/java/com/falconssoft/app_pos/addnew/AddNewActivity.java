package com.falconssoft.app_pos.addnew;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.falconssoft.app_pos.DatabaseHandler;
import com.falconssoft.app_pos.R;
import com.falconssoft.app_pos.category.CategoryActivity;
import com.falconssoft.app_pos.models.CategoryModel;
import com.falconssoft.app_pos.models.Items;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddNewActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private DatabaseHandler databaseHandler;
    private List<CategoryModel> categoryItemsList = new ArrayList<>();
    private ImageView saveNewCategory, takePhoto;
    private EditText addCategoryName;
    private AddNewAdapter adapter;
    private final int PICK_IMAGE = 10;
    private Bitmap bitmap = null;
    private TextView categoryImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        recyclerView = new RecyclerView(this);
        databaseHandler = new DatabaseHandler(this);
//        categoryItemsList = databaseHandler.getAllCategory();
        recyclerView = findViewById(R.id.add_new_recycler);
        saveNewCategory = findViewById(R.id.add_new_save);
        takePhoto = findViewById(R.id.add_new_image_row);
        addCategoryName = findViewById(R.id.add_new_name_row);
        categoryImage = findViewById(R.id.add_new_category_image_row);

        Log.e("size 1", "" + categoryItemsList.size());

        fillAdapter();
//        adapter = new AddNewAdapter(this, categoryItemsList);/
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);

        saveNewCategory.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
    }

    void fillAdapter() {
        categoryItemsList = databaseHandler.getAllCategoryTable();
        adapter = new AddNewAdapter(this, categoryItemsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_new_image_row:
//                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                getIntent.setType("image/*");
//
//                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                pickIntent.setType("image/*");
//
//                Intent chooserIntent = Intent.createChooser(getIntent, "choose");
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
//
//                startActivityForResult(chooserIntent, PICK_IMAGE);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "choose"), PICK_IMAGE);
                break;
            case R.id.add_new_save:
                if (!TextUtils.isEmpty(addCategoryName.getText())) {
                    String image = bitmapToString(bitmap);
                    CategoryModel categoryModel=new CategoryModel(addCategoryName.getText().toString(),image);
                    databaseHandler.addCategory(categoryModel);
                    fillAdapter();
                }

                addCategoryName.setText("");
                categoryImage.setBackground(getResources().getDrawable(R.drawable.ice_4));
                bitmap = null;
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Uri uriImage = data.getData();

//            if (data != null) {
                //Get image
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(AddNewActivity.this.getContentResolver(), uriImage);//extras.getParcelable("data");
                    Drawable drawable = new BitmapDrawable(getResources(),bitmap);
                    categoryImage.setBackground(drawable);
//                    categoryImage.setImageURI(uriImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Uri uriImage = data.getData();

//            }
        }
    }

    public String bitmapToString(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] arr = baos.toByteArray();
            String result = Base64.encodeToString(arr, Base64.DEFAULT);
            return result;
        }
        return "";
    }

    public Bitmap stringToBitmap(String image) {
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddNewActivity.this, CategoryActivity.class);
        startActivity(intent);
//        new CategoryActivity().refreshTestAdapter(AddNewActivity.this);
        Log.e("on","5");
    }
}
