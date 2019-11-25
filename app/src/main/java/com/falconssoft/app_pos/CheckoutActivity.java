//package com.falconssoft.app_pos;
//
//import android.app.Dialog;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.falconssoft.app_pos.category.CategoryActivity;
//
//public class CheckoutActivity extends AppCompatActivity {
//    TextView textView_qty, point_text, total_price_text;
//    double points=0;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_dialog);
//
//
//        final LinearLayoutManager layoutManager;
//        layoutManager = new LinearLayoutManager(this);
//
//        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.items_detail_Recycler);
//        recyclerView.setLayoutManager(layoutManager);
//        itemsReciptAdapter adapter_recipt = new itemsReciptAdapter(this, SettingOrder.ItemsOrder);
//        recyclerView.setAdapter(adapter_recipt);
////        TextView textView_qty, point_text, total_price_text;
//        ImageView cancel_image;
//        Button cash_button;
//        double qty = 0, pric = 0,order_point=0;
//        textView_qty = findViewById(R.id.textView_qty);
//        point_text =findViewById(R.id.textView_point);
//        total_price_text = findViewById(R.id.textView_total);
//        cash_button = findViewById(R.id.cash_pay);
//        cancel_image = findViewById(R.id.cancel);
//
//        cash_button.setOnClickListener(onClickListener);
//        for (int i = 0; i < SettingOrder.ItemsOrder.size(); i++) {
//            qty += SettingOrder.ItemsOrder.get(i).getQTY();
//            pric += SettingOrder.ItemsOrder.get(i).getTotal();
//            order_point += SettingOrder.ItemsOrder.get(i).getPoint();
//
//        }
//
//        points=order_point;
//        textView_qty.setText(qty + "");
//        point_text.setText(points + "");
//        total_price_text.setText(pric + "");
//    }
//
//
//
//
//    private View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.cash_pay:
//                    textView_qty.setText( "");
//                    point_text.setText("");
//                    total_price_text.setText("");
//                    reciveReciptMony_Cash();
//                    break;
//            }
//        }
//    };
//
//    private void reciveReciptMony_Cash() {
//        final Dialog dialog_cash = new Dialog(CheckoutActivity.this);
//        dialog_cash.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_cash.setCancelable(false);
//        dialog_cash.setContentView(R.layout.recive_money_cash_dialog);
//        dialog_cash.setCanceledOnTouchOutside(true);
//
//        final LinearLayoutManager layoutManager;
//        layoutManager = new LinearLayoutManager(this);
//
//        final TextView total_money, remaining_money;
//        final EditText receved_money;
//        Button save_button, cancel_button;
//        double pric = 0, recived = 0, remain = 0;
//
//        total_money = dialog_cash.findViewById(R.id.textView_total_money);
//        receved_money = dialog_cash.findViewById(R.id.recceved_money_editText);
//        remaining_money = dialog_cash.findViewById(R.id.remaining_Textview);
//        save_button = dialog_cash.findViewById(R.id.save_button);
//        cancel_button = dialog_cash.findViewById(R.id.cancel_button);
////        save_button.setOnClickListener(onClickListener);
//
//        cancel_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog_cash.dismiss();
//            }
//        });
//        for (int i = 0; i < SettingOrder.ItemsOrder.size(); i++) {
//
//            pric += SettingOrder.ItemsOrder.get(i).getTotal();
//
//
//        }
//        total_money.setText(pric + "");
//        Log.e("pric", "" + pric);
////        if (!receved_money.getText().toString().equals(""))
////        {
////            recived = Double.parseDouble(receved_money.getText().toString());
////
////    }
////        final double finalRecived = recived;
//        final double finalPric = pric;
//        final boolean[] isPay = {false};
//        receved_money.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                double recived = 0;
//                try {
//
//                    if (!s.equals("")) {
//                        recived = Double.parseDouble(s + "");
//
//                        if (recived >= finalPric) {
//                            isPay[0] =true;
//                            remaining_money.setText((recived - finalPric) + "");
//                        } else {
//                            isPay[0] =false;
//                            remaining_money.setText("0");
//
//                        }
//
//                    } else {
//                        remaining_money.setText("0");
//                    }
//                } catch (NumberFormatException e) {
//                    recived = 0;
//                    Log.e("Exception", "recived");
//
//                }
//
//
//            }
//        });
//        save_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isPay[0])
//                {
//                    double current_point= customerInformation.getPoint();
//                    customerInformation.setPoint(current_point+points);
//                    databaseHandler.updateCustomerPoint(phoneNo,points+current_point);
//
//                }
//                dialog_cash.dismiss();
//            }
//        });
//
//        SettingOrder.Item.clear();
//        SettingOrder.ItemsOrder.clear();
//        SettingOrder.index = 0;
//
//        dialog_cash.show();
//
//
//    }
//
//
//
//
//}
