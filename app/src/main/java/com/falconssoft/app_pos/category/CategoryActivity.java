package com.falconssoft.app_pos.category;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.falconssoft.app_pos.R;
import com.falconssoft.app_pos.SendSocket;
import com.falconssoft.app_pos.SettingOrder;
import com.falconssoft.app_pos.itemsReciptAdapter;

import java.util.ArrayList;
import java.util.List;

import cdflynn.android.library.turn.TurnLayoutManager;

import static android.widget.LinearLayout.VERTICAL;

public class CategoryActivity extends AppCompatActivity {

    //    private TextView UserNameText;
    private LinearLayout swipeRefresh;
    private Button CallCaptain, makeOrder;

    private List<String> list = new ArrayList<>();
    //    public List<Items> categoryList = new ArrayList<>();
    private List<String> pic = new ArrayList<>();
    private TurnLayoutManager layoutManager = null;
    private RecyclerView recyclerView;
    private MediaPlayer mediaPlayer;

    int position;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_listview);
        Intent userName = getIntent();
        String users = userName.getStringExtra("userName");

//        UserNameText = (TextView) findViewById(R.id.userName);

//        UserNameText.setText(users);
//        baseHandler=new DatabaseHandler(CategoryActivity.this);
//        categoryList=baseHandler.getAllItems();
        swipeRefresh = findViewById(R.id.swipeRefresh);
        CallCaptain = findViewById(R.id.call);
        makeOrder = findViewById(R.id.makeOrder);

//        list.add("");
        list.add("Barbecue");
        list.add("Chips");
        list.add("Fish finger");
        list.add("Chips");
        list.add("Cookies");
        list.add("Barbecue");
        list.add("Turkey Sandwich");
        list.add("Fried Potato");
        list.add("Burger");
        list.add("Fried Potato");
//        list.add("");

//        pic.add("");
        pic.add("fw");
        pic.add("der");
        pic.add("mozaral");
        pic.add("der");
        pic.add("coc");
        pic.add("fe");
        pic.add("san");
        pic.add("botato");
        pic.add("burger");
        pic.add("botato");
//        pic.add("");

        // vertical and cycle layout
        layoutManager = new TurnLayoutManager(this,
                TurnLayoutManager.Gravity.START,
                TurnLayoutManager.Orientation.HORIZONTAL,
                200,
                200,
                true);


        recyclerView = (RecyclerView) findViewById(R.id.categoryRecycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new TestAdapter(this, list));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("itemRec", "");
            }
        });

        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d="";
                orderReciptDialog();

//                SettingOrder.Item.clear();
//                SettingOrder.ItemsOrder.clear();
//                SettingOrder.index = 0;
            }
        });

//          swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                           @Override
//                         public void onRefresh() {
//
//                               Toast.makeText(CategoryActivity.this, "refresh ..", Toast.LENGTH_SHORT).show();
//                              swipeRefresh.setRefreshing(false);
//                           }
//        swipeRefresh.setRefreshing(false);
//    }

        CallCaptain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mediaPlayer = MediaPlayer.create(CategoryActivity.this, R.raw.bell);
                mediaPlayer.start();

                SendSocket sendSocket = new SendSocket(CategoryActivity.this);
                sendSocket.sendMessage();
            }
        });
    }

    public void orderReciptDialog() {
        Dialog dialog = new Dialog(CategoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.pay_dialog);
        dialog.setCanceledOnTouchOutside(true);

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(VERTICAL);
        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.items_detail_Recycler);
        recyclerView.setLayoutManager(layoutManager);
        itemsReciptAdapter adapter_recipt = new itemsReciptAdapter(this, SettingOrder.ItemsOrder);
        recyclerView.setAdapter(adapter_recipt);
        TextView textView_qty, point_text, total_price_text;
        Button cash_button;
        double qty = 0, pric = 0, points = 0;
        textView_qty = dialog.findViewById(R.id.textView_qty);
        point_text = dialog.findViewById(R.id.textView_point);
        total_price_text = dialog.findViewById(R.id.textView_total);
        cash_button = dialog.findViewById(R.id.cash_pay);
        cash_button.setOnClickListener(onClickListener);
        for (int i = 0; i < SettingOrder.ItemsOrder.size(); i++) {
            qty += SettingOrder.ItemsOrder.get(i).getQTY();
            pric += SettingOrder.ItemsOrder.get(i).getTotal();
            points += SettingOrder.ItemsOrder.get(i).getPoint();

        }
        textView_qty.setText(qty + "");
        point_text.setText(points + "");
        total_price_text.setText(pric + "");


        dialog.show();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cash_pay:
                    reciveReciptMony_Cash();

                    break;
//                case R.id.save_button:
//                    break;


            }

        }
    };

    private void reciveReciptMony_Cash() {
        final Dialog dialog_cash = new Dialog(CategoryActivity.this);
        dialog_cash.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cash.setCancelable(false);
        dialog_cash.setContentView(R.layout.recive_money_cash_dialog);
        dialog_cash.setCanceledOnTouchOutside(true);

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);

        final TextView total_money, remaining_money;
        EditText receved_money;
        Button save_button, cancel_button;
        double pric = 0, recived = 0, remain = 0;
        total_money = dialog_cash.findViewById(R.id.textView_total);
        receved_money = dialog_cash.findViewById(R.id.recceved_money_editText);
        remaining_money = dialog_cash.findViewById(R.id.remaining_Textview);
        save_button = dialog_cash.findViewById(R.id.save_button);
        cancel_button = dialog_cash.findViewById(R.id.cancel_button);
//        save_button.setOnClickListener(onClickListener);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cash.dismiss();
            }
        });
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
        Log.e("pric", "" + pric);
//        if (!receved_money.getText().toString().equals(""))
//        {
//            recived = Double.parseDouble(receved_money.getText().toString());
//
//    }
//        final double finalRecived = recived;
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
                            remaining_money.setText((recived - finalPric) + "");
                        } else {
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

        dialog_cash.show();


    }

    class CViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;
        LinearLayout layMain;

        public CViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_text);
            categoryImage = itemView.findViewById(R.id.category_imge);
            layMain = itemView.findViewById(R.id.layMain);
        }
    }

    class TestAdapter extends RecyclerView.Adapter<CViewHolder> {
        Context context;
        List<String> list;
//DatabaseHandler db;

        public TestAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
//        db=new DatabaseHandler(this.context);
        }

        @NonNull
        @Override
        public CViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.categoty_layout, viewGroup, false);
            return new CViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final CViewHolder cViewHolder, final int i) {
            cViewHolder.categoryName.setText(list.get(i));
//            cViewHolder.layMain.setId(i);
//        cViewHolder.categoryName.setText(list.get(i).getCategoryName());
            cViewHolder.categoryImage.setBackgroundResource(getImage(pic.get(i)));


            cViewHolder.layMain.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    Log.e("item ...", "i" + v.getId() + "-->" + i + "===>" + list.get(i));

                    Intent itemIntent = new Intent(context, ItemActivaty.class);
                    itemIntent.putExtra("categoryName", list.get(i));
                    itemIntent.putExtra("catPic", pic.get(i));
                    context.startActivity(itemIntent);
                    SettingOrder.indexCat = i;
//                CustomIntent.customType(context,"left-to-right");
//             //   bottom-to-up "left-to-right"
//                /**left-to-right
//                 *right-to-left
//                 *bottom-to-up
//                 *up-to-bottom
//                 *fadein-to-fadeout
//                 *rotateout-to-rotatein*/
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
//            return Integer.MAX_VALUE;
        }
    }

    public int getImage(String imageName) {

        int drawableResourceId = CategoryActivity.this.getResources().getIdentifier(imageName, "drawable", CategoryActivity.this.getPackageName());
        return drawableResourceId;
    }

}