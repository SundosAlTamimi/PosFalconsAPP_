package com.falconssoft.app_pos.category;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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
import com.falconssoft.app_pos.models.CategoryModel;
import com.falconssoft.app_pos.models.CustomerInformation;
import com.falconssoft.app_pos.models.Items;
import com.falconssoft.app_pos.models.NotificationModel;
import com.falconssoft.app_pos.models.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;
import static com.falconssoft.app_pos.category.CategoryActivity.categoryImage;


public class ItemActivaty extends AppCompatActivity {

    private static TextView catName;
    private ImageView  orderImage;
   CircleImageView addToOrder;
    private LinearLayout swipeRefresh;
    private RecyclerView recyclerView;
    private List<Items> itemList;
    CustomerInformation customerInformation;
    DatabaseHandler databaseHandler;
    LinearLayout catPic;
    NotificationManager notificationManager;
    static int id=1;
    String  today="",time="";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activaty);
        Intent categoryIntent = getIntent();
        String categoryName = categoryIntent.getStringExtra("categoryName");
//        String CatPic = categoryIntent.getStringExtra("catPic");

        catName = (TextView) findViewById(R.id.catName);
        catPic = (LinearLayout) findViewById(R.id.catImage);
        orderImage = (ImageView) findViewById(R.id.orderIcon);
        addToOrder = (CircleImageView) findViewById(R.id.items_btn_addToOrder);

        Calendar calendar=Calendar.getInstance();
        Date date=Calendar.getInstance().getTime();
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        String myFormattime = "HH:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(myFormat, Locale.US);
        java.text.SimpleDateFormat sdf2=new SimpleDateFormat(myFormattime, Locale.US);

        time = sdf2.format(calendar.getTime());
        today = sdf.format(calendar.getTime());

        itemList = new ArrayList<>();
        catName.setText(categoryName);
        if(categoryImage==null){

            catPic.setBackgroundResource(getImage("ice_4"));
        }else {
            Drawable drawable = new BitmapDrawable(getResources(),categoryImage);
            catPic.setBackground(drawable);
        }

//        baseHandler=new DatabaseHandler(CategoryActivity.this);
//        SettingOrder.Item=baseHandler.getAllItems();
//        swipeRefresh = findViewById(R.id.swipeRefresh);
//
//        for (int i = 0; i < 10; i++) {
////            itemList.clear();
////            itemList.add(new Items("wafel1", "wafel1", -1, null, "wafel1", 2.0, null, -1, -1, 0, 0, 0));
////            itemList.add(new Items("wafel2", "wafel2", -1, null, "wafel2", 2.50, null, -1, -1, 0, 0, 1));
////            itemList.add(new Items("wafel3", "wafel3", -1, null, "wafel3", 1.0, null, -1, -1, 0, 0, 2));
////            itemList.add(new Items("wafel4", "wafel4", -1, null, "wafel4", 1.0, null, -1, -1, 0, 0, 2));
////            itemList.add(new Items("wafel5", "wafel5", -1, null, "wafel5", 1.0, null, -1, -1, 0, 0, 0));
////            itemList.add(new Items("wafel6", "wafel6", -1, null, "wafel6", 0.5, null, -1, -1, 0, 0, 1));
////            itemList.add(new Items("wafel7", "wafel7", -1, null, "wafel7", 0.25, null, -1, -1, 0, 0, 0));
////            itemList.add(new Items("wafel8", "wafel8", -1, null, "wafel8", 1.0, null, -1, -1, 0, 0, 0));
////            itemList.add(new Items("wafel9", "wafel9", -1, null, "wafel9", 1.0, null, -1, -1, 0, 0, 0));
////            itemList.add(new Items("wafel10", "wafel10", -1, null, "wafel10", 1.0, null, -1, -1, 0, 0, 4));
//
////            SettingOrder.Item.add(i, itemList);
//        }

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

        Log.e("SettingOrder.Item",""+SettingOrder.Item.size());

        if(SettingOrder.Item.get(SettingOrder.indexCat).size()!=0) {
            recyclerView.setAdapter(new TestAdapter(this, SettingOrder.Item.get(SettingOrder.indexCat)));
        }else {
            Toast.makeText(this, "No Item For This Category", Toast.LENGTH_SHORT).show();
        }
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
        LinearLayout itemImage;
        //        LinearLayout pointLinear;
//        CircleImageView imageOffer;
        ImageView imageOffer;
        public static Button addOrder,changePrice;

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
            changePrice= itemView.findViewById(R.id.changePrice);
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
            Drawable drawable = new BitmapDrawable(getResources(),stringToBitmap(list.get(i).getItemPic()));
            cViewHolder.itemImage.setBackground(drawable);
            cViewHolder.Qty.setText("" + SettingOrder.Item.get(SettingOrder.indexCat).get(i).getQTY());
            cViewHolder.balance.setText("JD " + SettingOrder.Item.get(SettingOrder.indexCat).get(i).getTotal());
            cViewHolder.price.setText("" + SettingOrder.Item.get(SettingOrder.indexCat).get(i).getPrice());
            cViewHolder.itemDescription.setText(list.get(i).getDescription());
//            if(SettingOrder.Item.get(SettingOrder.indexCat).get(i).getPoint()==0)
//            {
//                cViewHolder.pointLinear.setVisibility(View.INVISIBLE);
//                cViewHolder.imageOffer.setVisibility(View.INVISIBLE);
//                cViewHolder.point.setText( list.get(i).getPoint()+"");
//            }
//            else {
//                cViewHolder.point.setText(list.get(i).getPoint()+"");
//            }


            cViewHolder.changePrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.change_price_dialog);
                    dialog.setCanceledOnTouchOutside(false);

                    Button Done,cancel;
                    TextView oldPrice;
                    final EditText newPrice;
                    oldPrice=dialog.findViewById(R.id.ChangePRICEOldText);
                    newPrice=dialog.findViewById(R.id.ChangePRICENewEdit);

                    Done=dialog.findViewById(R.id.ChangePRICEDoneButton);
                    cancel=dialog.findViewById(R.id.ChangePRICECancelButton);

                    oldPrice.setText(list.get(i).getPrice()+"");
                    Done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!newPrice.getText().toString().equals("")&&Double.parseDouble(newPrice.getText().toString())!=0) {
                                SettingOrder.Item.get(SettingOrder.indexCat).get(i).setPrice(Double.parseDouble(newPrice.getText().toString()));
                                cViewHolder.price.setText(newPrice.getText().toString());

                                dialog.dismiss();
                            }else {
                                Toast.makeText(ItemActivaty.this, "Please add all data", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
            });


            cViewHolder.addOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Items item = new Items();
                    boolean isFound = updateIfInList(String.valueOf(list.get(i).getItemBarcode()), Double.parseDouble(cViewHolder.Qty.getText().toString()), i, Double.parseDouble(cViewHolder.balance.getText().toString().replace("JD", "")));
                    if (Double.parseDouble(cViewHolder.Qty.getText().toString()) != 0) {
                        if (!isFound) {
                            item.setItemName(cViewHolder.ItemName.getText().toString());
//                    item.setItemPic(pic.get(i));
                            item.setCategoryName(catName.getText().toString());
                            item.setItemBarcode(list.get(i).getItemBarcode());
                            item.setPrice(SettingOrder.Item.get(SettingOrder.indexCat).get(i).getPrice());
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

                            motionEvent(list.get(i).getItemPic());

                        } else {
//                            new AlertDialog.Builder(context)
//                                    .setTitle("Update Qty")
//                                    .setMessage("Are you sure you want to Update  this "+cViewHolder.ItemName.getText().toString() +"Qty ="++" To New Qty = "+cViewHolder.Qty.getText().toString()+"?" +
//                                            "\n \n \n Note : \"when Delete any Category the item for this category will be delete  \"")
//
//
//                                    // Specifying a listener allows you to take an action before dismissing the dialog.
//                                    // The dialog is automatically dismissed when a dialog button is clicked.
//                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            // Continue with delete operation
//                                        }
//                                    })
//
//                                    // A null listener allows the button to dismiss the dialog and take no further action.
//                                    .setNegativeButton(android.R.string.no, null)
//                                    .setIcon(R.drawable.ic_delete_black_24dp)
//                                    .show();

                            Toast.makeText(context, " Update Order ", Toast.LENGTH_SHORT).show();//Alert message for update
                        }
                    } else {
                        Toast.makeText(context, "Can't Add  the QTY = 0 ", Toast.LENGTH_SHORT).show();
                    }

                }
            });

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
                    for(int i=0;i<SettingOrder.ItemsOrder.size();i++){
                        double totalBeforTax=SettingOrder.ItemsOrder.get(i).getPrice()*SettingOrder.ItemsOrder.get(i).getQTY();
//                        double tax=SettingOrder.ItemsOrder.get(i).get()

                        Order order=new Order("4124564",customerInformation.getCustomerName(),customerInformation.getPhoneNo(),
                                SettingOrder.ItemsOrder.get(i).getQTY(),-1,totalBeforTax,today,totalBeforTax,totalBeforTax,
                                0 ,SettingOrder.ItemsOrder.get(i).getItemName(),""+SettingOrder.ItemsOrder.get(i).getItemBarcode(),SettingOrder.ItemsOrder.get(i).getPrice());
                        databaseHandler.AddOrdre(order);

                    }

                    NotificationModel notificationModel=new NotificationModel("You have earned "+points+" points, and they will be added  to your account for "+(acount)+" points"
                            ,today,"Sales Gift",time,""+points);
                    databaseHandler.AddNotification(notificationModel);
                    SettingOrder.Item.clear();
                    SettingOrder.ItemsOrder.clear();
                    SettingOrder.index = 0;
                    dialog_cash.dismiss();
                    finish();
                    CategoryActivity vw=new CategoryActivity();
                    vw.FillCategoryReturn(databaseHandler);
                    String currentapiVersion = Build.VERSION.RELEASE;

                    Log.e("show_Notification",""+currentapiVersion.substring(0,currentapiVersion.indexOf(".")));


                    if (Double.parseDouble(currentapiVersion.substring(0,1) )>=8) {
                        // Do something for 14 and above versions
                        Log.e("show_Notification",""+currentapiVersion);

                        show_Notification("You have earned "+points+" points, and they will be added to your account for "+acount+" points");

                    } else {

                        // do something for phones running an SDK before 14
                    notification("You have earned "+points+" points, and they will be added to your account for "+acount+" points");
                        Log.e("notification",""+currentapiVersion);
                    }



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


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    public void show_Notification(String detail){

        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        String CHANNEL_ID="MYCHANNEL";

        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_HIGH);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,intent,0);
        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText("POINT APP Notification ......")
                .setContentTitle("Point Gift From Point App")
                .setStyle(new Notification.BigTextStyle()
                        .bigText(detail)
                        .setBigContentTitle("Point ")
                        .setSummaryText("Gift"))
//                .setContentIntent(pendingIntent)
//                .addAction(android.R.drawable.sym_action_chat,"Title",pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.gift)
                .build();


        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);


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
                    SettingOrder.Item.get(list.get(i).getIndexOfCat()).get(list.get(i).getIndexOfItem()).setQTY(0.0);
                    SettingOrder.Item.get(list.get(i).getIndexOfCat()).get(list.get(i).getIndexOfItem()).setTotal(0.0);

                    if(list.get(i).getIndexOfCat()==SettingOrder.indexCat) {
                        TestAdapter Ad = new TestAdapter(ItemActivaty.this, SettingOrder.Item.get(SettingOrder.indexCat));
                        recyclerView.setAdapter(Ad);
                    }

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

    public static boolean updateIfInList(String barCodePointer, double itemQty, int pointer, double itemTotal) {
        boolean isFound = false;
        for (int i = 0; i < SettingOrder.ItemsOrder.size(); i++) {

            if (barCodePointer.equals(SettingOrder.ItemsOrder.get(i).getItemBarcode())) {
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
        addToOrder.setImageBitmap(stringToBitmap(image));
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
    protected void onDestroy() {
        super.onDestroy();

    }
}