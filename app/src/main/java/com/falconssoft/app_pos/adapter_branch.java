package com.falconssoft.app_pos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.falconssoft.app_pos.models.Branches;
import java.util.List;

public class adapter_branch extends RecyclerView.Adapter<adapter_branch.ViewHolder> {
    @NonNull
    Context context;
    List<String> list;

    public adapter_branch(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        String ds="";
//        db=new DatabaseHandler(this.context);
    }

    @Override
    public adapter_branch.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e("onCreateViewHolder","");
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_branch, viewGroup, false);

        return new adapter_branch.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_branch.ViewHolder cViewItemHolder, int i) {
        cViewItemHolder.name.setText(list.get(i));
        cViewItemHolder.adress.setText("Amman , Jabal Alhusain");
        cViewItemHolder.time_work.setText("start from 8:00 Am to 5:00 pm");
        cViewItemHolder.tele.setText("Call");

        Log.e("onBindViewHolder","ViewHolder const");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public  class  ViewHolder extends  RecyclerView.ViewHolder
    {

        TextView name, adress,time_work,tele,lat,longtu;
        public ViewHolder(View itemView)
        {
            super(itemView);
//
            name = itemView.findViewById(R.id.textView_branchName);
            adress = itemView.findViewById(R.id.textView_adress);
            time_work = itemView.findViewById(R.id.textView_workTime);
            tele=itemView.findViewById(R.id.textView_tele);
            tele=itemView.findViewById(R.id.textView_tele);

            Log.e("itemrecipt","ViewHolder const");

        }
    }

}


//DatabaseHandler db;




