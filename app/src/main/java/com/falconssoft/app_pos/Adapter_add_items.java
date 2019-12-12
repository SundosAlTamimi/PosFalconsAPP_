package com.falconssoft.app_pos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.falconssoft.app_pos.models.Items;

import java.util.List;

public class Adapter_add_items extends RecyclerView.Adapter<Adapter_add_items.ViewHolder> {
    @NonNull
    Context context;
    List<Items> list;

//DatabaseHandler db;

    public Adapter_add_items(Context context, List<Items> list) {
        this.context = context;
        this.list = list;
        String ds="";
//        db=new DatabaseHandler(this.context);
    }
    @Override
    public Adapter_add_items.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e("onCreateViewHolder","");
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_added, viewGroup, false);

        return new Adapter_add_items.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_add_items.ViewHolder cViewItemHolder, int i) {
        cViewItemHolder.itemName.setText(list.get(i).getItemName());
        cViewItemHolder.qty.setText("" + list.get(i).getQTY());
        cViewItemHolder.price.setText("" + list.get(i).getPrice() + "");
        cViewItemHolder.tax.setText(list.get(i).getTax()+"");
        String item_image = list.get(i).getItemPic();
        if (item_image == null || (item_image.equals(""))) {
            cViewItemHolder.itemImage.setBackgroundResource(R.drawable.item_world);
        }
        else {
            Drawable drawable = new BitmapDrawable(context.getResources(), stringToBitmap(item_image));
            cViewItemHolder.itemImage.setBackground(drawable);
        }




        Log.e("onBindViewHolder","ViewHo=qty"+list.get(i).getQTY());
        Log.e("onBindViewHolder","pri="+list.get(i).getPrice());
        Log.e("onBindViewHolder","getTax()="+list.get(i).getTax());

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
    public int getItemCount() {
        return list.size();
    }
    public  class  ViewHolder extends  RecyclerView.ViewHolder
    {

        TextView qty, itemName,price,tax;
        LinearLayout itemImage;
        public ViewHolder(View itemView)
        {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name_text);
            qty = itemView.findViewById(R.id.text_qty);
            price = itemView.findViewById(R.id.price_text);
            tax=itemView.findViewById(R.id.tax_textview);
            itemImage=itemView.findViewById(R.id.item_imge);
            Log.e("itemrecipt","ViewHolder const");

        }
    }

}
