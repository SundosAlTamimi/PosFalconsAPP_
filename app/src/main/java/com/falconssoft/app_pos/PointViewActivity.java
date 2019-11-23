package com.falconssoft.app_pos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import java.util.ArrayList;
import java.util.List;


public class PointViewActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point_view);

        ArrayList <String>picforbar= new ArrayList<>();

        picforbar.add("Reward");
        picforbar.add("Notification");
        picforbar.add("Point");
        picforbar.add("Bar code");
        picforbar.add("Branch");
        picforbar.add("Reward");
        picforbar.add("Notification");
        picforbar.add("Point");
        picforbar.add("Bar code");
        picforbar.add("Branch");


        final CarouselLayoutManager layoutManagerd = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);

        final RecyclerView recyclerViews = (RecyclerView) findViewById(R.id.master);
        recyclerViews.setLayoutManager(layoutManagerd);
        recyclerViews.setHasFixedSize(true);
        recyclerViews.addOnScrollListener(new CenterScrollListener());
        layoutManagerd.setPostLayoutListener(new CarouselZoomPostLayoutListener());



        recyclerViews.setLayoutManager(layoutManagerd);
        recyclerViews.setHasFixedSize(true);
        recyclerViews.setAdapter(new TestAdapterForPoint(this, picforbar));
        recyclerViews.addOnScrollListener(new CenterScrollListener());
//
//        recyclerViews.requestFocus();
//        recyclerViews.scrollToPosition(2);
//        recyclerViews.requestFocus();

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





}
