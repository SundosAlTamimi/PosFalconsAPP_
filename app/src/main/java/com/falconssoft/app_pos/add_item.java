package com.falconssoft.app_pos;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.app_pos.category.ItemActivaty;
import com.falconssoft.app_pos.models.Items;

import java.util.List;

import static android.widget.LinearLayout.HORIZONTAL;
import static com.falconssoft.app_pos.category.ItemActivaty.updateIfInList;

public class add_item extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(HORIZONTAL);
        recyclerView = (RecyclerView) findViewById(R.id.itemRecycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new TestAdapter(this, SettingOrder.Item.get(SettingOrder.indexCat)));

        recyclerView.setItemViewCacheSize(SettingOrder.Item.size());
//        databaseHandler = new DatabaseHandler(ItemActivaty.this);
//        if (databaseHandler.getAllInformation().size() != 0) {
//            customerInformation = databaseHandler.getAllInformation().get(0);
//
//        }
    }
        class TestAdapter extends RecyclerView.Adapter<add_item.CViewHolder> {
            Context context;
            List<Items> list;
//DatabaseHandler db;

            public TestAdapter(Context context, List<Items> list) {
                this.context = context;
                this.list = list;
//        db=new DatabaseHandler(this.context);
            }

            @NonNull
            @Override
            public CViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(context).inflate(R.layout.iteam_row, viewGroup, false);
                return new CViewHolder(view);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onBindViewHolder(@NonNull final CViewHolder cViewHolder, final int i) {
                cViewHolder.ItemName.setText(list.get(i).getItemName());
                cViewHolder.itemImage.setBackgroundResource(getImage(list.get(i).getDescription()));
                cViewHolder.Qty.setText("" + SettingOrder.Item.get(SettingOrder.indexCat).get(i).getQTY());
                cViewHolder.balance.setText("JD " + SettingOrder.Item.get(SettingOrder.indexCat).get(i).getTotal());
                cViewHolder.price.setText("" + SettingOrder.Item.get(SettingOrder.indexCat).get(i).getPrice());
//            if(SettingOrder.Item.get(SettingOrder.indexCat).get(i).getPoint()==0)
//            {
//                cViewHolder.pointLinear.setVisibility(View.INVISIBLE);
//                cViewHolder.imageOffer.setVisibility(View.INVISIBLE);
//                cViewHolder.point.setText( list.get(i).getPoint()+"");
//            }
//            else {
//                cViewHolder.point.setText(list.get(i).getPoint()+"");
//            }


                cViewHolder.addOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Items item = new Items();
                        boolean isFound = updateIfInList(cViewHolder.ItemName.getText().toString(), Double.parseDouble(cViewHolder.Qty.getText().toString()), i, Double.parseDouble(cViewHolder.balance.getText().toString().replace("JD", "")));
                        if (Double.parseDouble(cViewHolder.Qty.getText().toString()) != 0) {
                            if (!isFound) {
                                item.setItemName(cViewHolder.ItemName.getText().toString());
//                    item.setItemPic(pic.get(i));
//                                item.setCategoryName(catName.getText().toString());
                                item.setQTY(Double.parseDouble(cViewHolder.Qty.getText().toString()));
                                item.setDescription(list.get(i).getDescription());
                                item.setIndexOfItem(i);
                                item.setIndexOfCat(SettingOrder.indexCat);
                                item.setTotal(Double.parseDouble(cViewHolder.balance.getText().toString().replace("JD", "")));
//                            item.setPoint(Integer.parseInt(cViewHolder.point.getText().toString()));

                                SettingOrder.Item.get(SettingOrder.indexCat).get(i).setQTY(Double.parseDouble(cViewHolder.Qty.getText().toString()));
                                SettingOrder.Item.get(SettingOrder.indexCat).get(i).setTotal(Double.parseDouble(cViewHolder.balance.getText().toString().replace("JD", "")));

                                SettingOrder.ItemsOrder.add(item);

                                Log.e("List" + i, "= " + SettingOrder.ItemsOrder.get(SettingOrder.index).getItemName()
                                        + "\n" + SettingOrder.ItemsOrder.get(SettingOrder.index).getItemBarcode() + "\n" +
                                        SettingOrder.ItemsOrder.get(SettingOrder.index).getCategoryName());

                                SettingOrder.index += 1;

//                                motionEvent(list.get(i).getDescription());

                            } else {
                                Toast.makeText(context, " Update ", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Can't Add  the QTY = 0 ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

//            cViewHolder.layMain.setOnClickListener(new View.OnClickListener() {
//                @RequiresApi(api = Build.VERSION_CODES.N)
//                @Override
//                public void onClick(View v) {
//                    Log.e("item ...", "i" + v.getId() + "-->" + i + "===>" + list.get(i));
//
////                Intent itemIntent=new Intent(context,ItemsActivity.class);
////                itemIntent.putExtra("categoryName",list.get(i));
////                context.startActivity(itemIntent);
////                CustomIntent.customType(context,"left-to-right");
////             //   bottom-to-up "left-to-right"
////                /**left-to-right
////                 *right-to-left
////                 *bottom-to-up
////                 *up-to-bottom
////                 *fadein-to-fadeout
////                 *rotateout-to-rotatein*/
//                }
//            });

                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                cViewHolder.addQty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double newQty = 0;
                        double oldQty = Double.parseDouble(cViewHolder.Qty.getText().toString());

                        Log.e("oldQty = ", "" + oldQty);

                        newQty = oldQty;
                        newQty += 1;


                        cViewHolder.Qty.setText("" + newQty);
                        cViewHolder.balance.setText("JD " + (newQty * Double.parseDouble(cViewHolder.price.getText().toString())));

                    }
                });


                cViewHolder.subQty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double newQty = 0;
                        double oldQty = Double.parseDouble(cViewHolder.Qty.getText().toString());

                        Log.e("oldQty = ", "" + oldQty);
                        if (oldQty > 0) {
                            newQty = oldQty;
                            cViewHolder.Qty.setText("0");
                            newQty -= 1;
                            cViewHolder.Qty.setText("" + newQty);
                            cViewHolder.balance.setText("JD " + (newQty * Double.parseDouble(cViewHolder.price.getText().toString())));
                        } else {
                            Toast.makeText(context, "The Qty Value = 0 ", Toast.LENGTH_SHORT).show();
                        }


                    }
                });


            }

            @Override
            public int getItemCount() {
                return list.size();
//            return Integer.MAX_VALUE;
            }
        }
    public static class CViewHolder extends RecyclerView.ViewHolder {

         TextView ItemName;
        TextView itemDescription;
        TextView addQty;
        TextView subQty;
        TextView balance;
        TextView Qty;
        TextView price;//,point;
         ImageView itemImage;
        //        LinearLayout pointLinear;
//        CircleImageView imageOffer;
        ImageView imageOffer;
        public static Button addOrder;

        public CViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemName = itemView.findViewById(R.id.item_text);
            itemDescription = itemView.findViewById(R.id.desicription);
            addQty = itemView.findViewById(R.id.addQty);
            subQty = itemView.findViewById(R.id.subQty);
            balance = itemView.findViewById(R.id.TotalPricre);
            Qty = itemView.findViewById(R.id.Qty);
            itemImage = itemView.findViewById(R.id.item_imge);
            addOrder = itemView.findViewById(R.id.addToOrder);
            price = itemView.findViewById(R.id.price);
//            point=itemView.findViewById(R.id.point_text);
//            pointLinear=itemView.findViewById(R.id.points);
            imageOffer = itemView.findViewById(R.id.imageView2);

        }
    }
    public int getImage(String imageName) {

        int drawableResourceId = add_item.this.getResources().getIdentifier(imageName, "drawable", add_item.this.getPackageName());

        return drawableResourceId;
    }

    }

