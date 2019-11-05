package com.falconssoft.app_pos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.falconssoft.app_pos.models.Items;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

public class ListOfOrder extends AppCompatActivity {

    TextView catName;
    ImageView catPic;

    //public List<String> list = new ArrayList<>();
    //    public List<Items> categoryList = new ArrayList<>();
    List<String> pic = new ArrayList<>();


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list_activaty);


        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(VERTICAL);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.orderRecycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new TestItemAdapter(this, SettingOrder.ItemsOrder));
        recyclerView.setItemViewCacheSize(SettingOrder.ItemsOrder.size());


    }


    class CViewItemHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView balance,Qty;
        ImageView itemPic;
        ImageButton delete;

//        List<Items>ListOrder=new ArrayList<>();

        public CViewItemHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            balance = itemView.findViewById(R.id.TotalPricre);
            Qty = itemView.findViewById(R.id.Qty);
            itemPic = itemView.findViewById(R.id.itemPic);
            delete=itemView.findViewById(R.id.delete);
        }
    }



    class TestItemAdapter extends RecyclerView.Adapter<CViewItemHolder> {
        Context context;
        List<Items> list;

        public TestItemAdapter(Context context, List<Items> list) {
            this.context = context;
            this.list = list;
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
            cViewItemHolder.Qty.setText(""+list.get(i).getPrice());

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            cViewItemHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("size before ",""+SettingOrder.ItemsOrder.size() +"    "+i);
                    list.remove(i);
                    Log.e("size after ",""+SettingOrder.ItemsOrder.size()+"    "+i);
                    SettingOrder.index=SettingOrder.ItemsOrder.size();
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

        int drawableResourceId = ListOfOrder.this.getResources().getIdentifier(imageName, "drawable",ListOfOrder.this.getPackageName());

        return drawableResourceId;
    }

}