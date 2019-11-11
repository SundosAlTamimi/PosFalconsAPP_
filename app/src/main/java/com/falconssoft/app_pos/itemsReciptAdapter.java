package com.falconssoft.app_pos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.falconssoft.app_pos.models.Items;

import java.util.List;

public class itemsReciptAdapter extends RecyclerView.Adapter<itemsReciptAdapter.ViewHolder> {
    @NonNull
    Context context;
    List<Items> list;

//DatabaseHandler db;

    public itemsReciptAdapter(Context context, List<Items> list) {
        this.context = context;
        this.list = list;
        String ds="";
//        db=new DatabaseHandler(this.context);
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e("onCreateViewHolder","");
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_recipt, viewGroup, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder cViewItemHolder, int i) {
        cViewItemHolder.itemName.setText(list.get(i).getItemName());
        cViewItemHolder.qty.setText("" + list.get(i).getQTY());
        cViewItemHolder.price.setText("" + list.get(i).getTotal() + " JD");
        cViewItemHolder.point.setText(list.get(i).getPoint()+"");
        Log.e("onBindViewHolder","ViewHolder const");

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public  class  ViewHolder extends  RecyclerView.ViewHolder
    {

        TextView qty, itemName,price,point;
        public ViewHolder(View itemView)
        {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            qty = itemView.findViewById(R.id.Qty);
            price = itemView.findViewById(R.id.TotalPricre);
            point=itemView.findViewById(R.id.point_item);
            Log.e("itemrecipt","ViewHolder const");

        }
    }
}
