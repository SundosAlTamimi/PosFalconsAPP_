package com.falconssoft.app_pos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.app_pos.category.CategoryActivity;
import com.falconssoft.app_pos.models.Branches;
import java.util.List;

public class adapter_branch extends RecyclerView.Adapter<adapter_branch.ViewHolder> {
    @NonNull
    Context context;
    List<String> list;
    Intent callIntent;
    private static final int REQUEST_PHONE_CALL = 1;

    public adapter_branch(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        String ds = "";
//        db=new DatabaseHandler(this.context);
    }

    @Override
    public adapter_branch.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e("onCreateViewHolder", "");
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_branch, viewGroup, false);

        return new adapter_branch.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_branch.ViewHolder cViewItemHolder, int i) {
        cViewItemHolder.name.setText(list.get(i));
        cViewItemHolder.adress.setText("Amman , Jabal Alhusain");
        cViewItemHolder.time_work.setText("start from 8:00 Am to 5:00 pm");
        cViewItemHolder.tele.setImageResource(R.drawable.ic_call_white_24dp);
        cViewItemHolder.location.setImageResource(R.drawable.ic_place_white_24dp);
        cViewItemHolder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, location_branch.class);
                context.startActivity(i);

            }
        });
        cViewItemHolder.tele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("cal", "clicked");
                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0797788880"));
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    context.startActivity(callIntent);
                }


            }


        });

        Log.e("onBindViewHolder", "ViewHolder const");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, adress, time_work,  lat, longtu;
        ImageView location,tele;

        public ViewHolder(View itemView) {
            super(itemView);
//
            name = itemView.findViewById(R.id.textView_branchName);
            adress = itemView.findViewById(R.id.textView_adress);
            time_work = itemView.findViewById(R.id.textView_workTime);
            tele = itemView.findViewById(R.id.image_tele);
            location = itemView.findViewById(R.id.imageViewlocation);


            Log.e("itemrecipt", "ViewHolder const");

        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_PHONE_CALL: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    context.startActivity(callIntent);
//                }
//                else
//                {
//                    Toast.makeText(context, "check permission call ", Toast.LENGTH_SHORT).show();
//
//                }
//                return;
//            }
//        }
//    }

}



//DatabaseHandler db;




