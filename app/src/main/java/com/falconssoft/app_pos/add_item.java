package com.falconssoft.app_pos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.app_pos.addnew.AddNewActivity;
import com.falconssoft.app_pos.category.ItemActivaty;
import com.falconssoft.app_pos.models.Items;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;
import static com.falconssoft.app_pos.category.ItemActivaty.updateIfInList;

public class add_item extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    List<Items> items;
    FloatingActionButton save, cancel;
    EditText item_name, description, price, qty, barcode, tax;
    ImageView item_pic;
    String name_str, description_str, price_str, qty_str, barcode_str, tax_str;
    double price_value, tax_value, qty_value;
    int barcode_value;
    private DatabaseHandler databaseHandler;
    private  final  int requestCode_image=1;
    private Bitmap bitmap = null;
    String categoryName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        databaseHandler = new DatabaseHandler(this);

        Intent userName = getIntent();
         categoryName = userName.getStringExtra("categoryNames");

//
        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(HORIZONTAL);
        recyclerView = (RecyclerView) findViewById(R.id.allitem_recycler);
        items = new ArrayList<>();
        items = databaseHandler.getAllItemCategory(categoryName);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new Adapter_add_items(this, items));

        item_name = (EditText) findViewById(R.id.item_name_text);
        description = (EditText) findViewById(R.id.desicription_text);

        price = (EditText) findViewById(R.id.price_text);

        qty = (EditText) findViewById(R.id.qty_text);

        barcode = (EditText) findViewById(R.id.barcode_text);

        tax = (EditText) findViewById(R.id.tax_text);
        item_pic = (ImageView) findViewById(R.id.item_imge);

        item_pic.setOnClickListener(this);

        save = (FloatingActionButton) findViewById(R.id.fab_save);
        save.setOnClickListener(this);
        cancel = (FloatingActionButton) findViewById(R.id.fab_cancel);
        cancel.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab_save:
                String r="";
                addItemToCategory();
                break;
            case R.id.fab_cancel:
                clearData();
                break;
            case R.id.item_imge:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "choose item image"), requestCode_image);
                break;


        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode_image == requestCode && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Uri uriImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(add_item.this.getContentResolver(), uriImage);//extras.getParcelable("data");
                Drawable drawable = new BitmapDrawable(getResources(),bitmap);
                item_pic.setBackground(drawable);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void clearData() {
        item_name.setText("");
        description.setText("");
        price.setText("");
        qty.setText("");
        barcode.setText("");
        tax.setText("");
//        databaseHandler.deleteAllItems();
        item_pic.setBackground(getResources().getDrawable(R.drawable.ic_add_a_photo_24dp));


    }

    private void addItemToCategory() {

        if(CheckInformationEntered()) {

            boolean  barcodeFound=databaseHandler.IfBarCodeIsFound(String.valueOf(barcode_value));
            if(!barcodeFound){
                Items one_item = new Items();
                one_item.setCategoryName(categoryName);
                one_item.setItemPic(bitmapToString(bitmap));
                one_item.setQTY(qty_value);
                one_item.setItemName(name_str);
                one_item.setPrice(price_value);
                one_item.setTax(tax_value);
                one_item.setDescription(description_str);
                one_item.setItemBarcode(barcode_value);
                items.add(one_item);
                databaseHandler.addItem(one_item);
                recyclerView.setAdapter(new Adapter_add_items(this, items));
                bitmap = null;
                clearData();
                Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "This Bar Code Add Before", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this, "please fill required filed", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean CheckInformationEntered() {

        description_str=description.getText().toString();
        name_str=item_name.getText().toString().trim();
        barcode_str=barcode.getText().toString().trim();
        if (TextUtils.isEmpty(name_str)){
            item_name.setError(R.string.required_filed+"");

        }
        if (TextUtils.isEmpty(barcode_str)){
            barcode.setError(R.string.required_filed+"");

        }
        else{
            barcode_value=Integer.parseInt(barcode_str);
        }
        tax_str=tax.getText().toString().trim();
        if (TextUtils.isEmpty(tax_str)){
            tax.setError(R.string.required_filed+"");

        }
        else {
            tax_value=Double.parseDouble(tax_str);
        }


        //******************************************
        qty_str=qty.getText().toString();
        if(!qty_str.equals(""))
        {
            qty_value=Double.parseDouble(qty_str);
        }
        else{qty_value=0;}
        price_str=price.getText().toString();
        if(!price_str.equals(""))
        {
            price_value=Double.parseDouble(price_str);
        }
        else{price_value=0;}
        if(item_pic==null)
        {
            item_pic.setBackground(getResources().getDrawable(R.drawable.item_world));
        }
        Log.e("checkdata",""+price_value+"\t"+qty_value);

        if(!name_str.equals("")&&!barcode_str.equals("")&&!tax_str.equals(""))
        {return  true;}



        return false;

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

    //        class TestAdapter extends RecyclerView.Adapter<add_item.CViewHolder> {
//            Context context;
//            List<Items> list;
////DatabaseHandler db;
//
//            public TestAdapter(Context context, List<Items> list) {
//                this.context = context;
//                this.list = list;
////        db=new DatabaseHandler(this.context);
//            }
//
//            @NonNull
//            @Override
//            public CViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                View view = LayoutInflater.from(context).inflate(R.layout.iteam_row, viewGroup, false);
//                return new CViewHolder(view);
//            }
//
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onBindViewHolder(@NonNull final CViewHolder cViewHolder, final int i) {
//                cViewHolder.ItemName.setText(list.get(i).getItemName());
//                cViewHolder.itemImage.setBackgroundResource(getImage(list.get(i).getDescription()));
//                cViewHolder.Qty.setText("" + SettingOrder.Item.get(SettingOrder.indexCat).get(i).getQTY());
//                cViewHolder.balance.setText("JD " + SettingOrder.Item.get(SettingOrder.indexCat).get(i).getTotal());
//                cViewHolder.price.setText("" + SettingOrder.Item.get(SettingOrder.indexCat).get(i).getPrice());
////            if(SettingOrder.Item.get(SettingOrder.indexCat).get(i).getPoint()==0)
////            {
////                cViewHolder.pointLinear.setVisibility(View.INVISIBLE);
////                cViewHolder.imageOffer.setVisibility(View.INVISIBLE);
////                cViewHolder.point.setText( list.get(i).getPoint()+"");
////            }
////            else {
////                cViewHolder.point.setText(list.get(i).getPoint()+"");
////            }
//
//
//                cViewHolder.addOrder.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Items item = new Items();
//                        boolean isFound = updateIfInList(cViewHolder.ItemName.getText().toString(), Double.parseDouble(cViewHolder.Qty.getText().toString()), i, Double.parseDouble(cViewHolder.balance.getText().toString().replace("JD", "")));
//                        if (Double.parseDouble(cViewHolder.Qty.getText().toString()) != 0) {
//                            if (!isFound) {
//                                item.setItemName(cViewHolder.ItemName.getText().toString());
////                    item.setItemPic(pic.get(i));
////                                item.setCategoryName(catName.getText().toString());
//                                item.setQTY(Double.parseDouble(cViewHolder.Qty.getText().toString()));
//                                item.setDescription(list.get(i).getDescription());
//                                item.setIndexOfItem(i);
//                                item.setIndexOfCat(SettingOrder.indexCat);
//                                item.setTotal(Double.parseDouble(cViewHolder.balance.getText().toString().replace("JD", "")));
////                            item.setPoint(Integer.parseInt(cViewHolder.point.getText().toString()));
//
//                                SettingOrder.Item.get(SettingOrder.indexCat).get(i).setQTY(Double.parseDouble(cViewHolder.Qty.getText().toString()));
//                                SettingOrder.Item.get(SettingOrder.indexCat).get(i).setTotal(Double.parseDouble(cViewHolder.balance.getText().toString().replace("JD", "")));
//
//                                SettingOrder.ItemsOrder.add(item);
//
//                                Log.e("List" + i, "= " + SettingOrder.ItemsOrder.get(SettingOrder.index).getItemName()
//                                        + "\n" + SettingOrder.ItemsOrder.get(SettingOrder.index).getItemBarcode() + "\n" +
//                                        SettingOrder.ItemsOrder.get(SettingOrder.index).getCategoryName());
//
//                                SettingOrder.index += 1;
//
////                                motionEvent(list.get(i).getDescription());
//
//                            } else {
//                                Toast.makeText(context, " Update ", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(context, "Can't Add  the QTY = 0 ", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//
////            cViewHolder.layMain.setOnClickListener(new View.OnClickListener() {
////                @RequiresApi(api = Build.VERSION_CODES.N)
////                @Override
////                public void onClick(View v) {
////                    Log.e("item ...", "i" + v.getId() + "-->" + i + "===>" + list.get(i));
////
//////                Intent itemIntent=new Intent(context,ItemsActivity.class);
//////                itemIntent.putExtra("categoryName",list.get(i));
//////                context.startActivity(itemIntent);
//////                CustomIntent.customType(context,"left-to-right");
//////             //   bottom-to-up "left-to-right"
//////                /**left-to-right
//////                 *right-to-left
//////                 *bottom-to-up
//////                 *up-to-bottom
//////                 *fadein-to-fadeout
//////                 *rotateout-to-rotatein*/
////                }
////            });
//
//                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//                cViewHolder.addQty.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        double newQty = 0;
//                        double oldQty = Double.parseDouble(cViewHolder.Qty.getText().toString());
//
//                        Log.e("oldQty = ", "" + oldQty);
//
//                        newQty = oldQty;
//                        newQty += 1;
//
//
//                        cViewHolder.Qty.setText("" + newQty);
//                        cViewHolder.balance.setText("JD " + (newQty * Double.parseDouble(cViewHolder.price.getText().toString())));
//
//                    }
//                });
//
//
//                cViewHolder.subQty.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        double newQty = 0;
//                        double oldQty = Double.parseDouble(cViewHolder.Qty.getText().toString());
//
//                        Log.e("oldQty = ", "" + oldQty);
//                        if (oldQty > 0) {
//                            newQty = oldQty;
//                            cViewHolder.Qty.setText("0");
//                            newQty -= 1;
//                            cViewHolder.Qty.setText("" + newQty);
//                            cViewHolder.balance.setText("JD " + (newQty * Double.parseDouble(cViewHolder.price.getText().toString())));
//                        } else {
//                            Toast.makeText(context, "The Qty Value = 0 ", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//                });
//
//
//            }
//
//            @Override
//            public int getItemCount() {
//                return list.size();
////            return Integer.MAX_VALUE;
//            }
//        }
//    public static class CViewHolder extends RecyclerView.ViewHolder {
//
//         TextView ItemName;
//        TextView itemDescription;
//        TextView addQty;
//        TextView subQty;
//        TextView balance;
//        TextView Qty;
//        TextView price;//,point;
//         ImageView itemImage;
//        //        LinearLayout pointLinear;
////        CircleImageView imageOffer;
//        ImageView imageOffer;
//        public static Button addOrder;
//
//        public CViewHolder(@NonNull View itemView) {
//            super(itemView);
//            ItemName = itemView.findViewById(R.id.item_text);
//            itemDescription = itemView.findViewById(R.id.desicription);
//            addQty = itemView.findViewById(R.id.addQty);
//            subQty = itemView.findViewById(R.id.subQty);
//            balance = itemView.findViewById(R.id.TotalPricre);
//            Qty = itemView.findViewById(R.id.Qty);
//            itemImage = itemView.findViewById(R.id.item_imge);
//            addOrder = itemView.findViewById(R.id.addToOrder);
//            price = itemView.findViewById(R.id.price);
////            point=itemView.findViewById(R.id.point_text);
////            pointLinear=itemView.findViewById(R.id.points);
//            imageOffer = itemView.findViewById(R.id.imageView2);
//
//        }
//    }
    public int getImage(String imageName) {

        int drawableResourceId = add_item.this.getResources().getIdentifier(imageName, "drawable", add_item.this.getPackageName());

        return drawableResourceId;
    }

    }

