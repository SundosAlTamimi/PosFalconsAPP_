package com.falconssoft.app_pos.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.app_pos.R;
import com.falconssoft.app_pos.SendSocket;
import com.falconssoft.app_pos.SettingOrder;

import java.util.ArrayList;
import java.util.List;

import cdflynn.android.library.turn.TurnLayoutManager;

public class CategoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //    private TextView UserNameText;
    private Toolbar mTopToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private LinearLayout swipeRefresh;
    private Button CallCaptain, makeOrder;

    private List<String> list = new ArrayList<>();
    //    public List<Items> categoryList = new ArrayList<>();
    private List<String> pic = new ArrayList<>();
    private TurnLayoutManager layoutManager = null;
    private RecyclerView recyclerView;
    private MediaPlayer mediaPlayer;

    int position;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_listview);

        mTopToolbar = (Toolbar) findViewById(R.id.category_toolbar);
        drawerLayout = findViewById(R.id.category_drawer);
        navigationView = findViewById(R.id.category_navigation);

        setSupportActionBar(mTopToolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);
        //Enable the drawer to open and close
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        Intent userName = getIntent();
        String users = userName.getStringExtra("userName");

//        UserNameText = (TextView) findViewById(R.id.userName);

//        UserNameText.setText(users);
//        baseHandler=new DatabaseHandler(CategoryActivity.this);
//        categoryList=baseHandler.getAllItems();
//        swipeRefresh = findViewById(R.id.swipeRefresh);
//        CallCaptain = findViewById(R.id.call);
        makeOrder = findViewById(R.id.makeOrder);

//        list.add("");
        list.add("Barbecue");
        list.add("Chips");
        list.add("Fish finger");
        list.add("Chips");
        list.add("Cookies");
        list.add("Barbecue");
        list.add("Turkey Sandwich");
        list.add("Fried Potato");
        list.add("Burger");
        list.add("Fried Potato");
//        list.add("");

//        pic.add("");
        pic.add("fw");
        pic.add("der");
        pic.add("mozaral");
        pic.add("der");
        pic.add("coc");
        pic.add("fe");
        pic.add("san");
        pic.add("botato");
        pic.add("burger");
        pic.add("botato");
//        pic.add("");

        // vertical and cycle layout
        layoutManager = new TurnLayoutManager(this,
                TurnLayoutManager.Gravity.START,
                TurnLayoutManager.Orientation.HORIZONTAL,
                200,
                200,
                true);


        recyclerView = (RecyclerView) findViewById(R.id.categoryRecycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new TestAdapter(this, list));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("itemRec", "");
            }
        });

        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingOrder.Item.clear();
                SettingOrder.ItemsOrder.clear();
                SettingOrder.index = 0;
            }
        });

//          swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                           @Override
//                         public void onRefresh() {
//
//                               Toast.makeText(CategoryActivity.this, "refresh ..", Toast.LENGTH_SHORT).show();
//                              swipeRefresh.setRefreshing(false);
//                           }
//        swipeRefresh.setRefreshing(false);
//    }

//        CallCaptain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               mediaPlayer = MediaPlayer.create(CategoryActivity.this, R.raw.bell);
//                mediaPlayer.start();
//
//                SendSocket sendSocket = new SendSocket(CategoryActivity.this);
//                sendSocket.sendMessage();
//            }
//        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.navigation_menu, menu);
//        return true;
//    }
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (item.getItemId() == android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            // Android home
//            case android.R.id.home:
//                drawerLayout.openDrawer(GravityCompat.START);
//                return true;
            case R.id.menu_profile:
                Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_orders:
                Toast.makeText(this, "orders", Toast.LENGTH_SHORT).show();

                break;
            case R.id.menu_invite_friends:
                Toast.makeText(this, "invite_friends", Toast.LENGTH_SHORT).show();

                break;
            case R.id.menu_call_us:
                break;
            case R.id.menu_transfer_points:
                break;
            case R.id.menu_contact_with_us:
                break;
            case R.id.menu_language:
                break;
            case R.id.menu_app_developers:
                break;

        }
        return false;
    }

    class CViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;
        LinearLayout layMain;

        public CViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_text);
            categoryImage = itemView.findViewById(R.id.category_imge);
            layMain = itemView.findViewById(R.id.layMain);
        }
    }

    class TestAdapter extends RecyclerView.Adapter<CViewHolder> {
        Context context;
        List<String> list;
//DatabaseHandler db;

        public TestAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
//        db=new DatabaseHandler(this.context);
        }

        @NonNull
        @Override
        public CViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.categoty_layout, viewGroup, false);
            return new CViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final CViewHolder cViewHolder, final int i) {
            cViewHolder.categoryName.setText(list.get(i));
//            cViewHolder.layMain.setId(i);
//        cViewHolder.categoryName.setText(list.get(i).getCategoryName());
            cViewHolder.categoryImage.setBackgroundResource(getImage(pic.get(i)));


            cViewHolder.layMain.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    Log.e("item ...", "i" + v.getId() + "-->" + i + "===>" + list.get(i));

                    Intent itemIntent = new Intent(context, ItemActivaty.class);
                    itemIntent.putExtra("categoryName", list.get(i));
                    itemIntent.putExtra("catPic", pic.get(i));
                    context.startActivity(itemIntent);
                    SettingOrder.indexCat = i;
//                CustomIntent.customType(context,"left-to-right");
//             //   bottom-to-up "left-to-right"
//                /**left-to-right
//                 *right-to-left
//                 *bottom-to-up
//                 *up-to-bottom
//                 *fadein-to-fadeout
//                 *rotateout-to-rotatein*/
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
//            return Integer.MAX_VALUE;
        }
    }

    public int getImage(String imageName) {

        int drawableResourceId = CategoryActivity.this.getResources().getIdentifier(imageName, "drawable", CategoryActivity.this.getPackageName());
        return drawableResourceId;
    }

}