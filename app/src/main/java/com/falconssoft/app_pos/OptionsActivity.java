package com.falconssoft.app_pos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.app_pos.category.CategoryActivity;
import com.falconssoft.app_pos.models.Tables;

import java.util.ArrayList;
import java.util.List;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private TableLayout floorNoTableLayout, tableNoTableLayout;
    private EditText editTextNoOfSeats;
    private TextView arrowAddFloor, arrowAddTable, textViewUserName, textViewFloorNo, textViewTableNo;
    private ScrollView floorNoScroll, tableNoScroll;
    private Button saveOptions;
    private static int flagFloor = 0; // up
    private static int flagTable = 0; // up

    private List<Tables> tablesList = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    String users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        databaseHandler = new DatabaseHandler(this);
        filList();

        floorNoTableLayout = findViewById(R.id.options_floorNo_layout);
        tableNoTableLayout = findViewById(R.id.options_tableNo_layout);
        editTextNoOfSeats = findViewById(R.id.options_noOfSeats_ET);
        arrowAddFloor = findViewById(R.id.options_add_floor);
        arrowAddTable = findViewById(R.id.options_add_table);
        floorNoScroll = findViewById(R.id.options_floorNo_scroll);
        tableNoScroll = findViewById(R.id.options_tableNo_scroll);
        textViewFloorNo = findViewById(R.id.options_floorNo_tv);
        textViewTableNo = findViewById(R.id.options_tableNo_tv);
        saveOptions = findViewById(R.id.options_done);
        textViewUserName = (TextView) findViewById(R.id.userName);

        Intent userName = getIntent();
        users = userName.getStringExtra("userName");
        textViewUserName.setText(users);

        arrowAddFloor.setOnClickListener(this);
        arrowAddTable.setOnClickListener(this);
        saveOptions.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.options_done:
                if (!TextUtils.isEmpty(editTextNoOfSeats.getText().toString())) {
                    if ((SettingOrder.staticTableNo != 0) && (SettingOrder.staticNoOfSeits != 0))  //(!SettingOrder.staticSectionNo.equals("")) &&
                    SettingOrder.staticNoOfSeits = Integer.parseInt(editTextNoOfSeats.getText().toString());
                    Log.e("", "table " + SettingOrder.staticTableNo + " section " + SettingOrder.staticSectionNo + " seits " + SettingOrder.staticNoOfSeits);
                    Intent categoryIntent = new Intent(OptionsActivity.this, CategoryActivity.class);
                    categoryIntent.putExtra("userName", users);
                    startActivity(categoryIntent);
                } else {
                    Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.options_add_floor:
//                if (flagTable == 1)
                tableNoScroll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                arrowAddTable.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_keyboard_arrow_down_yellow_24dp));
                fillAdapters(1);
                break;
            case R.id.options_add_table:
                floorNoScroll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                arrowAddFloor.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_keyboard_arrow_down_yellow_24dp));
                fillAdapters(2);
                break;
        }

    }

    void filList() {
//        Tables tables = new Tables();
//        tables.setSectionNo("Gf");
//        tables.setTableNo(1);
//        tables.setNoOfSeits(2);
//        tablesList.add(tables);
//        databaseHandler.addTable(tables);

//        for (int i = 0; i < tablesList.size(); i++)
//            databaseHandler.addTable(tablesList.get(i));

        tablesList = databaseHandler.getTablesInfo();

        Log.e("size", "" + tablesList.size());

    }

    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void fillAdapters(int type) {
        floorNoTableLayout.removeAllViews();
        tableNoTableLayout.removeAllViews();

        if (type == 1) {
            if (flagFloor == 0) {
                flagFloor = 1;
                arrowAddFloor.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_keyboard_arrow_up_yeloow_24dp));
                floorNoScroll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
                for (int i = 0; i < tablesList.size(); i++) {
                    final TableRow row = new TableRow(this);
                    final TextView floorNoTV = new TextView(this);
                    floorNoTV.setText(tablesList.get(i).getSectionNo());
                    floorNoTV.setTextSize(18);
                    floorNoTV.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_light));
                    floorNoTV.setGravity(Gravity.CENTER);

                    TableRow.LayoutParams tvParams = new TableRow.LayoutParams(565, TableRow.LayoutParams.WRAP_CONTENT);
                    tvParams.setMargins(20, 0, 20, 2);
                    floorNoTV.setLayoutParams(tvParams);

                    row.addView(floorNoTV);
                    row.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {

//                                row.setBackgroundResource(R.color.exit);//ContextCompat.getColor(OptionsActivity.this, R.color.exit)
//                                Toast.makeText(OptionsActivity.this, "row click"+v.getId(), Toast.LENGTH_SHORT).show();
                            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                                SettingOrder.staticSectionNo = floorNoTV.getText().toString();
                                textViewFloorNo.setText(floorNoTV.getText().toString());
//                                row.setBackgroundResource(R.color.gray_light);//ContextCompat.getColor(OptionsActivity.this, R.color.exit)
//                                Toast.makeText(OptionsActivity.this, "row click"+v.getId(), Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                    });
                    floorNoTableLayout.addView(row);
                }
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.down_from_bottom);
                floorNoTableLayout.startAnimation(animation);

            } else {
                arrowAddFloor.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_keyboard_arrow_down_yellow_24dp));
                floorNoScroll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                flagFloor = 0;
            }
        }

        if (type == 2) {
            if (flagTable == 0) {
                arrowAddTable.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_keyboard_arrow_up_yeloow_24dp));
                flagTable = 1;
                tableNoScroll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));

                for (int i = 0; i < tablesList.size(); i++) {
                    TableRow row = new TableRow(this);
                    final TextView tableNoTV = new TextView(this);
                    tableNoTV.setText("" + tablesList.get(i).getTableNo());
                    tableNoTV.setTextSize(16);
                    tableNoTV.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_light));
                    tableNoTV.setGravity(Gravity.CENTER);

                    TableRow.LayoutParams tvParams = new TableRow.LayoutParams(565, TableRow.LayoutParams.WRAP_CONTENT);
                    tvParams.setMargins(20, 0, 20, 2);
                    tableNoTV.setLayoutParams(tvParams);

                    row.addView(tableNoTV);
                    row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SettingOrder.staticTableNo = Integer.parseInt(tableNoTV.getText().toString());
                            textViewTableNo.setText(tableNoTV.getText().toString());

                        }
                    });
                    tableNoTableLayout.addView(row);
                }
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.down_from_bottom);
                tableNoTableLayout.startAnimation(animation);


            } else {
                tableNoScroll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                arrowAddTable.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_keyboard_arrow_down_yellow_24dp));
//                animation.cancel();
                flagTable = 0;

            }
        }
    }


}
