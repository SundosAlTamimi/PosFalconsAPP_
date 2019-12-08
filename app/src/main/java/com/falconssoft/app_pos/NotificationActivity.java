package com.falconssoft.app_pos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.falconssoft.app_pos.models.Items;
import com.falconssoft.app_pos.models.NotificationModel;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.VERTICAL;


public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerViews;
    Button rewardGallary;
DatabaseHandler databaseHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_dialog);
        recyclerViews = (RecyclerView) findViewById(R.id.recycle_notification);
        databaseHandler=new DatabaseHandler(NotificationActivity.this);

        CarouselLayoutManagerForPoint();


    }
    void CarouselLayoutManagerForPoint(){


       List <NotificationModel>picforbar= new ArrayList<>();

        picforbar=databaseHandler.getAllNotification();

//        picforbar.add("Reward");
//        picforbar.add("Notification");
//        picforbar.add("Point");
//        picforbar.add("Bar code");
//        picforbar.add("Branch");
//        picforbar.add("Reward");
//        picforbar.add("Notification");
//        picforbar.add("Point");
//        picforbar.add("Bar code");
//        picforbar.add("Branch");


        final CarouselLayoutManager layoutManagerd = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);


        recyclerViews.setLayoutManager(layoutManagerd);
        recyclerViews.setHasFixedSize(true);
        recyclerViews.addOnScrollListener(new CenterScrollListener());
        layoutManagerd.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        recyclerViews.setAdapter(new TestAdapterForPoint(this, picforbar));

        recyclerViews.requestFocus();
        recyclerViews.scrollToPosition( (picforbar.size()/2));
        recyclerViews.requestFocus();


    }

//    void openRewardGallaryDialog(){
//
//
//       List <String>listOfOrder=new ArrayList<>();
////        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
////        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
////        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
////        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
////        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
////        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
////        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
////        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
////        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
////        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,2));
////        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
////        listOfOrder.add(new Items("potato", "potato", 1222, null, "from", 1, null, 1, 1, 1, 10,0));
//
//        listOfOrder.add("potato");
//        listOfOrder.add("potato"  );
//        listOfOrder.add("potato"  );
//        listOfOrder.add("potato"  );
//        listOfOrder.add("potato"  );
//        listOfOrder.add( "potato"  );
//        listOfOrder.add( "potato"  );
//        listOfOrder.add( "potato"  );
//        listOfOrder.add( "potato"  );
//        listOfOrder.add( "potato");
//        listOfOrder.add( "potato"  );
//        listOfOrder.add( "potato"  );
//
//        final LinearLayoutManager layoutManager;
//        layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(VERTICAL);
//
//        recyclerViews.setLayoutManager(layoutManager);
//        recyclerViews.setAdapter(new TestAdapterForPoint(this, listOfOrder));
//
//        recyclerViews.setItemViewCacheSize(SettingOrder.Item.size());
//
//
//    }


    static class CViewHolderForPoint extends RecyclerView.ViewHolder {

        TextView noPoint,description,ExpiryDate,fromDateTime;
//        ImageView itemImage;
//        LinearLayout layBar;

        public CViewHolderForPoint(@NonNull View itemView) {
            super(itemView);
            noPoint = itemView.findViewById(R.id.noPoint);
            description = itemView.findViewById(R.id.description);
            fromDateTime = itemView.findViewById(R.id.fromDateTime);
            ExpiryDate = itemView.findViewById(R.id.ExpiryDate);
//            layBar=itemView.findViewById(R.id.layBar);
//            itemImage = itemView.findViewById(R.id.imgbar);
        }
    }

    class TestAdapterForPoint extends RecyclerView.Adapter<CViewHolderForPoint> {
        Context context;
        List<NotificationModel> list;
//DatabaseHandler db;

        public TestAdapterForPoint(Context context, List<NotificationModel> list) {
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
            cViewHolder.noPoint.setText("+ " + list.get(i).getPoint()+" Point   "+list.get(i).getNotificationName());
            cViewHolder.description.setText("" + list.get(i).getDescription());
            cViewHolder.fromDateTime.setText("" + list.get(i).getDate()+"      " +list.get(i).getTime());
//            cViewHolder.ExpiryDate.setText("" + list.get(i).getNotificationName());

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++



        }

        @Override
        public int getItemCount() {
            return list.size();
//            return Integer.MAX_VALUE;
        }
    }



//    static class CViewHolderForOrder extends RecyclerView.ViewHolder {
//
//        TextView ItemName, point, Qty, price;
//        ImageView itemImage;
//
//        public CViewHolderForOrder(@NonNull View itemView) {
//            super(itemView);
////            ItemName = itemView.findViewById(R.id.itemName);
////            Qty = itemView.findViewById(R.id.Qty);
////            price = itemView.findViewById(R.id.price);
////            point = itemView.findViewById(R.id.point);
////            itemImage = itemView.findViewById(R.id.itemImage);
//        }
//    }
//
//    class TestAdapterForOrder extends RecyclerView.Adapter<NotificationActivity.CViewHolderForOrder> {
//        Context context;
//        List<Items> list;
////DatabaseHandler db;
//
//        public TestAdapterForOrder(Context context, List<Items> list) {
//            this.context = context;
//            this.list = list;
////        db=new DatabaseHandler(this.context);
//        }
//
//        @NonNull
//        @Override
//        public NotificationActivity.CViewHolderForOrder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            View view = LayoutInflater.from(context).inflate(R.layout.gallary_layout_for_list, viewGroup, false);
//            return new NotificationActivity.CViewHolderForOrder(view);
//        }
//
//        @SuppressLint("SetTextI18n")
//        @Override
//        public void onBindViewHolder(@NonNull final NotificationActivity.CViewHolderForOrder cViewHolder, final int i) {
////            cViewHolder.ItemName.setText(list.get(i).getItemName());
//////            cViewHolder.itemImage.setBackgroundResource(getImage(list.get(i).getDescription()));
////            cViewHolder.Qty.setText("" + list.get(i).getQTY());
////            cViewHolder.price.setText("" + list.get(i).getPrice());
//
//
////
//
//            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return list.size();
////            return Integer.MAX_VALUE;
//        }
//    }
//




}
