package com.falconssoft.app_pos;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.falconssoft.app_pos.models.Items;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.VERTICAL;


public class RewardActivity extends AppCompatActivity {

    RecyclerView recyclerViews;
    Button rewardGallary;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reward_gallary_dialog);
        recyclerViews = (RecyclerView) findViewById(R.id.recycle_gallary);


        openRewardGallaryDialog();


    }

    void openRewardGallaryDialog(){


       List <Items>listOfOrder=new ArrayList<>();
        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,2));
        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(VERTICAL);

        recyclerViews.setLayoutManager(layoutManager);
        recyclerViews.setAdapter(new TestAdapterForOrder(this, listOfOrder));

        recyclerViews.setItemViewCacheSize(SettingOrder.Item.size());


    }


    static class CViewHolderForPoint extends RecyclerView.ViewHolder {

        TextView ItemName;
        ImageView itemImage;
        LinearLayout layBar;

        public CViewHolderForPoint(@NonNull View itemView) {
            super(itemView);
//            ItemName = itemView.findViewById(R.id.textbar);
//            layBar=itemView.findViewById(R.id.layBar);
//            itemImage = itemView.findViewById(R.id.imgbar);
        }
    }

    class TestAdapterForPoint extends RecyclerView.Adapter<CViewHolderForPoint> {
        Context context;
        List<String> list;
//DatabaseHandler db;

        public TestAdapterForPoint(Context context, List<String> list) {
            this.context = context;
            this.list = list;
//        db=new DatabaseHandler(this.context);
        }

        @NonNull
        @Override
        public CViewHolderForPoint onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.description_list, viewGroup, false);
            return new CViewHolderForPoint(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final CViewHolderForPoint cViewHolder, final int i) {
//            cViewHolder.Qty.setText("" + list.get(i).getQTY());
//            cViewHolder.price.setText("" + list.get(i).getPrice());
//            cViewHolder.layBar.setTag(""+i);

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++



        }

        @Override
        public int getItemCount() {
            return list.size();
//            return Integer.MAX_VALUE;
        }
    }



    static class CViewHolderForOrder extends RecyclerView.ViewHolder {

        TextView ItemName, point, Qty, price;
        ImageView itemImage;

        public CViewHolderForOrder(@NonNull View itemView) {
            super(itemView);
//            ItemName = itemView.findViewById(R.id.itemName);
//            Qty = itemView.findViewById(R.id.Qty);
//            price = itemView.findViewById(R.id.price);
//            point = itemView.findViewById(R.id.point);
//            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }

    class TestAdapterForOrder extends RecyclerView.Adapter<RewardActivity.CViewHolderForOrder> {
        Context context;
        List<Items> list;
//DatabaseHandler db;

        public TestAdapterForOrder(Context context, List<Items> list) {
            this.context = context;
            this.list = list;
//        db=new DatabaseHandler(this.context);
        }

        @NonNull
        @Override
        public RewardActivity.CViewHolderForOrder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.gallary_layout_for_list, viewGroup, false);
            return new RewardActivity.CViewHolderForOrder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final RewardActivity.CViewHolderForOrder cViewHolder, final int i) {
//            cViewHolder.ItemName.setText(list.get(i).getItemName());
////            cViewHolder.itemImage.setBackgroundResource(getImage(list.get(i).getDescription()));
//            cViewHolder.Qty.setText("" + list.get(i).getQTY());
//            cViewHolder.price.setText("" + list.get(i).getPrice());


//

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        }

        @Override
        public int getItemCount() {
            return list.size();
//            return Integer.MAX_VALUE;
        }
    }





}
