package com.falconssoft.app_pos.category;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.app_pos.DatabaseHandler;
import com.falconssoft.app_pos.LoginActivity;
import com.falconssoft.app_pos.R;
import com.falconssoft.app_pos.SettingOrder;
import com.falconssoft.app_pos.models.CustomerInformation;
import com.falconssoft.app_pos.models.Items;
import com.falconssoft.app_pos.models.NotificationModel;
import com.falconssoft.app_pos.models.Order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;


public class ItemActivaty extends AppCompatActivity {

    private static TextView catName;
    private ImageView catPic, orderImage, addToOrder;
    private LinearLayout swipeRefresh;
    private RecyclerView recyclerView;
    private List<Items> itemList;
    CustomerInformation customerInformation;
    DatabaseHandler databaseHandler;

    NotificationManager notificationManager;
    static int id=1;
    String  today="",time="";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activaty);
        Intent userName = getIntent();
        String categoryName = userName.getStringExtra("categoryName");
        String CatPic = userName.getStringExtra("catPic");

        catName = (TextView) findViewById(R.id.catName);
        catPic = (ImageView) findViewById(R.id.catImage);
        orderImage = (ImageView) findViewById(R.id.orderIcon);
        addToOrder = (ImageView) findViewById(R.id.items_btn_addToOrder);

        Calendar calendar=Calendar.getInstance();
        Date date=Calendar.getInstance().getTime();
        SimpleDateFormat simpleFormatter=new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat simpleFormatters=new SimpleDateFormat("HH:mm:ss");
        today = simpleFormatter.format(date);
        time = simpleFormatters.format(calendar.getTime());

        itemList = new ArrayList<>();

        catName.setText(categoryName);
        catPic.setBackgroundResource(getImage(CatPic));
//        baseHandler=new DatabaseHandler(CategoryActivity.this);
//        SettingOrder.Item=baseHandler.getAllItems();
//        swipeRefresh = findViewById(R.id.swipeRefresh);

        for (int i = 0; i < 10; i++) {
            itemList.clear();
            itemList.add(new Items("wafel1", "wafel1", -1, null, "wafel1", 2.0, null, -1, -1, 0, 0, 0));
            itemList.add(new Items("wafel2", "wafel2", -1, null, "wafel2", 2.50, null, -1, -1, 0, 0, 1));
            itemList.add(new Items("wafel3", "wafel3", -1, null, "wafel3", 1.0, null, -1, -1, 0, 0, 2));
            itemList.add(new Items("wafel4", "wafel4", -1, null, "wafel4", 1.0, null, -1, -1, 0, 0, 2));
            itemList.add(new Items("wafel5", "wafel5", -1, null, "wafel5", 1.0, null, -1, -1, 0, 0, 0));
            itemList.add(new Items("wafel6", "wafel6", -1, null, "wafel6", 0.5, null, -1, -1, 0, 0, 1));
            itemList.add(new Items("wafel7", "wafel7", -1, null, "wafel7", 0.25, null, -1, -1, 0, 0, 0));
            itemList.add(new Items("wafel8", "wafel8", -1, null, "wafel8", 1.0, null, -1, -1, 0, 0, 0));
            itemList.add(new Items("wafel9", "wafel9", -1, null, "wafel9", 1.0, null, -1, -1, 0, 0, 0));
            itemList.add(new Items("wafel10", "wafel10", -1, null, "wafel10", 1.0, null, -1, -1, 0, 0, 4));

            SettingOrder.Item.add(i, itemList);
        }

        orderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListDialog();
            }
        });

        for (int x = 0; x < SettingOrder.ItemsOrder.size(); x++) {
            if (SettingOrder.ItemsOrder.get(x).getIndexOfItem() != -1) {
                if (SettingOrder.indexCat == SettingOrder.ItemsOrder.get(x).getIndexOfCat()) {//this for ----
                    SettingOrder.Item.get(SettingOrder.indexCat).get(SettingOrder.ItemsOrder.get(x).getIndexOfItem()).setQTY(SettingOrder.ItemsOrder.get(x).getQTY());
                    SettingOrder.Item.get(SettingOrder.indexCat).get(SettingOrder.ItemsOrder.get(x).getIndexOfItem()).setTotal(SettingOrder.ItemsOrder.get(x).getTotal());

                }
            }
        }

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(HORIZONTAL);
        recyclerView = (RecyclerView) findViewById(R.id.itemRecycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new TestAdapter(this, SettingOrder.Item.get(SettingOrder.indexCat)));

        recyclerView.setItemViewCacheSize(SettingOrder.Item.size());
        databaseHandler = new DatabaseHandler(ItemActivaty.this);
        if (databaseHandler.getAllInformation().size() != 0) {
            customerInformation = databaseHandler.getAllInformation().get(0);

        }

//          swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                           @Override
//                         public void onRefresh() {
//
//                               Toast.makeText(CategoryActivity.this, "refresh ..", Toast.LENGTH_SHORT).show();
//                              swipeRefresh.setRefreshing(false);
//                           }
//        swipeRefresh.setRefreshing(false);
//
//
//    }


    }


    static class CViewHolder extends RecyclerView.ViewHolder {

        TextView ItemName, itemDescription, addQty, subQty, balance, Qty, price;//,point;
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


    class TestAdapter extends RecyclerView.Adapter<CViewHolder> {
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
                            item.setCategoryName(catName.getText().toString());
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

                            motionEvent(list.get(i).getDescription());

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

    public void orderListDialog() {
        Dialog dialog = new Dialog(ItemActivaty.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.order_list_activaty);
        dialog.setCanceledOnTouchOutside(true);
        final TextView totalQ, total, point, change;
        change = (TextView) dialog.findViewById(R.id.change);
        totalQ = (TextView) dialog.findViewById(R.id.totalQ);
        total = (TextView) dialog.findViewById(R.id.total);
        point = (TextView) dialog.findViewById(R.id.point_text);
        Button checkout = (Button) dialog.findViewById(R.id.checkout);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Double.parseDouble(total.getText().toString()) != 0) {
                    reciveReciptMony_Cash(Double.parseDouble(point.getText().toString()),Double.parseDouble(totalQ.getText().toString()));
                } else {
                    Toast.makeText(ItemActivaty.this, "The Total Equal 0.0 ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(VERTICAL);
        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.orderRecycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new TestItemAdapter(this, SettingOrder.ItemsOrder, change));
        recyclerView.setItemViewCacheSize(SettingOrder.ItemsOrder.size());
        CalculateDialog(recyclerView, point, total, totalQ);

        change.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                CalculateDialog(recyclerView, point, total, totalQ);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.show();
    }


    void CalculateDialog(RecyclerView recyclerView, TextView point, TextView total, TextView totalQ) {
        double points = 0, totals = 0, totalQty = 0;
        for (int i = 0; i < SettingOrder.ItemsOrder.size(); i++) {
            totals += SettingOrder.ItemsOrder.get(i).getTotal();
            totalQty += SettingOrder.ItemsOrder.get(i).getQTY();
        }

        points = totals;
        point.setText("" + points);
        total.setText("" + totals);
        totalQ.setText("" + totalQty);


    }

    private void reciveReciptMony_Cash(final double points,final double totalQ) {
        final Dialog dialog_cash = new Dialog(ItemActivaty.this);
        dialog_cash.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cash.setCancelable(false);
        dialog_cash.setContentView(R.layout.recive_money_cash_dialog);
        dialog_cash.setCanceledOnTouchOutside(true);

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        final boolean[] isPay = {false};
        final TextView total_money, remaining_money;
        final EditText receved_money;
        Button save_button, cancel_button;
        double pric = 0, recived = 0, remain = 0;

        total_money = dialog_cash.findViewById(R.id.textView_total_money);
        receved_money = dialog_cash.findViewById(R.id.recceved_money_editText);
        remaining_money = dialog_cash.findViewById(R.id.remaining_Textview);
        save_button = dialog_cash.findViewById(R.id.save_button);
        cancel_button = dialog_cash.findViewById(R.id.cancel_button);
//        save_button.setOnClickListener(onClickListener);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cash.dismiss();
            }
        });
        for (int i = 0; i < SettingOrder.ItemsOrder.size(); i++) {

            pric += SettingOrder.ItemsOrder.get(i).getTotal();

        }
        total_money.setText(pric + "");

        final double finalPric = pric;
        receved_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double recived = 0;
                try {

                    if (!s.equals("")) {
                        recived = Double.parseDouble(s + "");

                        if (recived >= finalPric) {
                            isPay[0] = true;
                            remaining_money.setText((recived - finalPric) + "");
                        } else {
                            isPay[0] = false;
                            remaining_money.setText("0");

                        }

                    } else {
                        remaining_money.setText("0");
                    }
                } catch (NumberFormatException e) {
                    recived = 0;
                    Log.e("Exception", "recived");

                }


            }
        });
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPay[0]) {
                    double current_point = customerInformation.getPoint();
                    String phoneNo = customerInformation.getPhoneNo();
                    customerInformation.setPoint(current_point + points);
                    databaseHandler.updateCustomerPoint(phoneNo, points + current_point);
                    double acount=current_point+points;

                        Order order=new Order("4124564",customerInformation.getCustomerName(),customerInformation.getPhoneNo(),
                                totalQ,points,Double.parseDouble(total_money.getText().toString()),today );
                        databaseHandler.AddOrdre(order);

                    NotificationModel notificationModel=new NotificationModel("You have earned "+points+" points, and they will be added  to your account for "+(acount)+" points"
                            ,today,"Sales Gift",time,""+points);
                    databaseHandler.AddNotification(notificationModel);
                    SettingOrder.Item.clear();
                    SettingOrder.ItemsOrder.clear();
                    SettingOrder.index = 0;
                    dialog_cash.dismiss();
                    finish();
//                    Intent cateItem=new Intent(ItemActivaty.this,CategoryActivity.class);
//                    startActivity(cateItem);

                    notification("You have earned "+points+" points, and they will be added to your account for "+acount+" points");


                }else{
                    Toast.makeText(ItemActivaty.this, "Please Enter all filed ", Toast.LENGTH_SHORT).show();
                }

            }
        });


        dialog_cash.show();


    }


    private void notification (String detail){

        NotificationCompat.Builder nbuilder=new NotificationCompat.Builder(ItemActivaty.this)
                .setContentTitle("POINT APP Notification ......")
                .setContentText("Point Gift From Point App")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(detail)
                        .setBigContentTitle("Point ")
                        .setSummaryText("Gift"))
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.gift);

        notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id,nbuilder.build());
        id++;

    }

    void deleteItem() {
//        Log.e("size before ", "" + SettingOrder.ItemsOrder.size() + "    " + i + "     " + list.get(i).getIndexOfItem());

//        SettingOrder.Item.get(SettingOrder.indexCat).get(SettingOrder.ItemsOrder.get(inDelete).getIndexOfItem()).setQTY(0.0);
//        SettingOrder.Item.get(SettingOrder.indexCat).get(SettingOrder.ItemsOrder.get(inDelete).getIndexOfItem()).setTotal(0.0);
//
//        TestAdapter Ad = new TestAdapter(ItemActivaty.this, SettingOrder.Item.get(SettingOrder.indexCat));
//        recyclerView.setAdapter(Ad);


//        Ad.notifyDataSetChanged();
    }


    class CViewItemHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView balance, Qty;
        ImageView itemPic;
        ImageButton delete;
//        List<Items>ListOrder=new ArrayList<>();

        public CViewItemHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            balance = itemView.findViewById(R.id.TotalPricre);
            Qty = itemView.findViewById(R.id.Qty);
            itemPic = itemView.findViewById(R.id.itemPic);
            delete = itemView.findViewById(R.id.delete);
        }
    }


    class TestItemAdapter extends RecyclerView.Adapter<CViewItemHolder> {
        Context context;
        List<Items> list;
        TextView change;

        public TestItemAdapter(Context context, List<Items> list, TextView change) {
            this.context = context;
            this.list = list;
            this.change = change;
        }

        @NonNull
        @Override
        public CViewItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_of_list_order, viewGroup, false);
            return new CViewItemHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final CViewItemHolder cViewItemHolder, final int i) {
            cViewItemHolder.itemName.setText(list.get(i).getItemName());
            cViewItemHolder.itemPic.setBackgroundResource(getImage(list.get(i).getDescription()));
            cViewItemHolder.Qty.setText("" + list.get(i).getQTY());
            cViewItemHolder.balance.setText("" + list.get(i).getTotal() + " JD");

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            cViewItemHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("size before ", "" + SettingOrder.ItemsOrder.size() + "    " + i + "     " + list.get(i).getIndexOfItem());
                    SettingOrder.Item.get(SettingOrder.indexCat).get(list.get(i).getIndexOfItem()).setQTY(0.0);
                    SettingOrder.Item.get(SettingOrder.indexCat).get(list.get(i).getIndexOfItem()).setTotal(0.0);

                    TestAdapter Ad = new TestAdapter(ItemActivaty.this, SettingOrder.Item.get(SettingOrder.indexCat));
                    recyclerView.setAdapter(Ad);

                    list.remove(i);
                    Log.e("size after ", "" + SettingOrder.ItemsOrder.size() + "    " + i);
                    SettingOrder.index = SettingOrder.ItemsOrder.size();
                    change.setText("2");
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }


    public int getImage(String imageName) {

        int drawableResourceId = ItemActivaty.this.getResources().getIdentifier(imageName, "drawable", ItemActivaty.this.getPackageName());

        return drawableResourceId;
    }

    public static boolean updateIfInList(String namePointer, double itemQty, int pointer, double itemTotal) {
        boolean isFound = false;
        for (int i = 0; i < SettingOrder.ItemsOrder.size(); i++) {

            if (namePointer.equals(SettingOrder.ItemsOrder.get(i).getItemName())) {
                SettingOrder.Item.get(SettingOrder.indexCat).get(pointer).setQTY(itemQty);
                SettingOrder.ItemsOrder.get(i).setQTY(itemQty);

                SettingOrder.Item.get(SettingOrder.indexCat).get(pointer).setTotal(itemTotal);
                SettingOrder.ItemsOrder.get(i).setTotal(itemTotal);

                isFound = true;
                break;
            }

        }
        return isFound;
    }


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void motionEvent(String image) {

        addToOrder.setVisibility(View.VISIBLE);
        addToOrder.setBackgroundResource(getImage(image));
        int p1[] = new int[2];
        int p2[] = new int[2];
        orderImage.getLocationInWindow(p1);
        addToOrder.getLocationInWindow(p2);

        TranslateAnimation animation = new TranslateAnimation(0, orderImage.getX() - addToOrder.getX(), 0, orderImage.getY() - addToOrder.getY());
        animation.setRepeatMode(0);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        addToOrder.startAnimation(animation);
        addToOrder.setVisibility(View.INVISIBLE);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        SettingOrder.Item.clear();
    }
}