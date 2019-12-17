package com.falconssoft.app_pos.category;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.falconssoft.app_pos.DatabaseHandler;
import com.falconssoft.app_pos.LocaleAppUtils;
import com.falconssoft.app_pos.NotificationActivity;
import com.falconssoft.app_pos.PointViewActivity;
import com.falconssoft.app_pos.R;
import com.falconssoft.app_pos.ReportActivity;
import com.falconssoft.app_pos.RewardActivity;
import com.falconssoft.app_pos.SettingOrder;
import com.falconssoft.app_pos.SoldReportActivity;
import com.falconssoft.app_pos.add_item;
import com.falconssoft.app_pos.addnew.AddNewActivity;
import com.falconssoft.app_pos.email.SendMailTask;
import com.falconssoft.app_pos.itemsReciptAdapter;
import com.falconssoft.app_pos.adapter_branch;
import com.falconssoft.app_pos.models.CategoryModel;
import com.falconssoft.app_pos.models.CustomerInformation;
import com.falconssoft.app_pos.models.Items;
import com.falconssoft.app_pos.models.Order;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cdflynn.android.library.turn.TurnLayoutManager;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static android.widget.LinearLayout.VERTICAL;
import static com.falconssoft.app_pos.models.ShareValues.emailTitle;
import static com.falconssoft.app_pos.models.ShareValues.recipientName;
import static com.falconssoft.app_pos.models.ShareValues.senderName;
import static com.falconssoft.app_pos.models.ShareValues.senderPassword;

public class CategoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Intent callIntent;
    private TextView english, arabic, emailMessage;
    private Button send, makeOrder;
    private ImageButton facebook, twitter, instagram, whatsApp;
    ImageView barcode, orderList;
    ArrayList<String> picforbar, pic2, branches_list;
    public static Bitmap categoryImage=null;

    //    private TextView UserNameText;
    private LinearLayout swipeRefresh;
    private Toolbar mTopToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TurnLayoutManager layoutManager = null;
    private RecyclerView recyclerView, recyclerViews;
    private MediaPlayer mediaPlayer;
    private List<Order> listOfOrder = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private List<String> pic = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    boolean isPay = false;
    CustomerInformation customerInformation;
    String phoneNo;
    double points = 0;

    private LinearLayoutManager linearLayoutManager;
    private CarouselLayoutManager layoutManagerd;
    private List<Items> viewCaterogyList = new ArrayList<>();
    private List<CategoryModel> Categorys = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_listview);

        databaseHandler = new DatabaseHandler(CategoryActivity.this);
        picforbar = new ArrayList<>();
        callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:0797788880"));
        orderList = (ImageView) findViewById(R.id.orderlist);
        pic2 = new ArrayList<>();
        branches_list = new ArrayList<>();

//        FillCategory();

        branches_list.add("Branch Resturant 1");
        branches_list.add("Branch Resturant 2");
        branches_list.add("Branch Resturant 3");
        branches_list.add("Branch Resturant 4");
//        branches_list.add("31,125415");
//        branches_list.add("33.215487");
        picforbar.add("My Reward");
        picforbar.add("Notification");
        picforbar.add("Point");
        picforbar.add("Bar code");
        picforbar.add("Branch");
        picforbar.add("Add New");

        pic2.add("rewardimg");
        pic2.add("notification");
        pic2.add("gift");
        pic2.add("barcode");
        pic2.add("branch");
        pic2.add("plus_sign");

        recyclerView = (RecyclerView) findViewById(R.id.categoryRecycler);
        makeOrder = findViewById(R.id.makeOrder);
        mTopToolbar = (Toolbar) findViewById(R.id.category_toolbar);
        drawerLayout = findViewById(R.id.category_drawer);
        navigationView = findViewById(R.id.category_navigation);
        barcode = findViewById(R.id.barcodes);
        customerInformation = new CustomerInformation();
        makeOrder.setVisibility(View.GONE);
        if (databaseHandler.getAllInformation().size() != 0) {
            customerInformation = databaseHandler.getAllInformation().get(0);

        }

        phoneNo = customerInformation.getPhoneNo();


        setSupportActionBar(mTopToolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Enable the drawer to open and close
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.setNavigationItemSelectedListener(this);
        BarcodeDialog();
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarcodeDialog();

            }
        });
        orderList.setVisibility(View.GONE);
        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListDialog();
            }
        });

        //????????????????????????????????????????????????????????????????????????????

        layoutManagerd = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        recyclerViews = (RecyclerView) findViewById(R.id.res);
        recyclerViews.setLayoutManager(layoutManagerd);
        recyclerViews.setHasFixedSize(true);
        recyclerViews.addOnScrollListener(new CenterScrollListener());
        layoutManagerd.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerViews.setAdapter(new TestAdapterForbar(this, picforbar));
        recyclerViews.requestFocus();
        recyclerViews.scrollToPosition(2);
        recyclerViews.requestFocus();

//????????????????????????????????????????????????????????????????????????????

//        pic.add("ice_cream_");
//        pic.add("fraze_");
//        pic.add("ice_cream_sundae");
//        pic.add("limeice_cream");
//        pic.add("ice_cream_chocolate");
//        pic.add("zemenu_saldejuma");
//        pic.add("coupe_glace");
//        pic.add("coupe_glace_png");
//        pic.add("frazeicecream");
//        pic.add("freaze_icecream");

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new TestAdapter(this, Categorys));
        recyclerView.setItemViewCacheSize(SettingOrder.Item.size());

//        refreshTestAdapter();
        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderReciptDialog();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // to activate burger icon
        int itemId = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            // Android home
//            case android.R.id.home:
//                drawerLayout.openDrawer(GravityCompat.START);
//                return true;
            case R.id.menu_profile:
                profileDialog();
                break;
            case R.id.menu_orders:
                makeOrderDialog();
                Toast.makeText(this, "orders", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_invite_friends:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            case R.id.menu_call_us:
                callUsDialog();
                break;
            case R.id.menu_transfer_points:
                transferPointDialog();
                break;
            case R.id.menu_contact_with_us:
                contactUsDialog();
                break;
            case R.id.menu_language:
                languageDialog();
                break;
            case R.id.menu_app_developers:
                Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.app_developers_dialog_layout);
                dialog.show();
                break;

            case R.id.report:
                Intent reportIntent = new Intent(CategoryActivity.this, ReportActivity.class);
                startActivity(reportIntent);
                break;
            case R.id.detail:
                Intent detailIntent = new Intent(CategoryActivity.this, SoldReportActivity.class);
                startActivity(detailIntent);
                break;

        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.login_language_english:
//
//                break;
//            case R.id.login_language_arabic:
//
//                break;
        }
    }

    void transferPointDialog() {

        final Dialog dialog = new Dialog(CategoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.transfer_point);
        dialog.setCanceledOnTouchOutside(true);

        final EditText phone, pointToSend;
        final TextView pointTotal;
        Button send;
        phone = (EditText) dialog.findViewById(R.id.phone);
        pointToSend = (EditText) dialog.findViewById(R.id.pointToSend);

        pointTotal = (TextView) dialog.findViewById(R.id.pointTotal);
        send = (Button) dialog.findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!phone.getText().toString().equals("") && !pointToSend.getText().toString().equals("")) {
                    int totalPoint = Integer.parseInt(pointTotal.getText().toString()) - Integer.parseInt(pointToSend.getText().toString());
                    if (Integer.parseInt(pointTotal.getText().toString()) != 0) {
                        if (totalPoint >= 0) {


                        } else {
                            Toast.makeText(CategoryActivity.this, "You don't have enough points to send", Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
                    Toast.makeText(CategoryActivity.this, "Please Enter All Information ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();
    }

    void makeOrderDialog() {

        final Dialog dialog = new Dialog(CategoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.my_order_dialog);
        dialog.setCanceledOnTouchOutside(true);

        listOfOrder = databaseHandler.getAllOrder();

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(VERTICAL);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.itemRecycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CategoryActivity.TestAdapterForOrder(this, listOfOrder));

        recyclerView.setItemViewCacheSize(SettingOrder.Item.size());

        dialog.show();
    }

    TextView textView_qty, point_text, total_price_text;

    public void orderReciptDialog() {
        final Dialog dialog = new Dialog(CategoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.pay_dialog);
        dialog.setCanceledOnTouchOutside(false);

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);

        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.items_detail_Recycler);
        recyclerView.setLayoutManager(layoutManager);
        itemsReciptAdapter adapter_recipt = new itemsReciptAdapter(this, SettingOrder.ItemsOrder);
        recyclerView.setAdapter(adapter_recipt);
//        TextView textView_qty, point_text, total_price_text;
        ImageView cancel_image;
        Button cash_button;
        double qty = 0, pric = 0, order_point = 0;
        textView_qty = dialog.findViewById(R.id.textView_qty);
        point_text = dialog.findViewById(R.id.textView_point);
        total_price_text = dialog.findViewById(R.id.textView_total);
        cash_button = dialog.findViewById(R.id.cash_pay);
        cancel_image = dialog.findViewById(R.id.cancel);
        cancel_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cash_button.setOnClickListener(onClickListener);
        for (int i = 0; i < SettingOrder.ItemsOrder.size(); i++) {
            qty += SettingOrder.ItemsOrder.get(i).getQTY();
            pric += SettingOrder.ItemsOrder.get(i).getTotal();
            order_point += SettingOrder.ItemsOrder.get(i).getPoint();

        }

        points = order_point;
        textView_qty.setText(qty + "");
        point_text.setText(points + "");
        total_price_text.setText(pric + "");
        dialog.show();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cash_pay:
                    textView_qty.setText("");
                    point_text.setText("");
                    total_price_text.setText("");
                    reciveReciptMony_Cash();
                    break;
            }
        }
    };

    private void reciveReciptMony_Cash() {
        final Dialog dialog_cash = new Dialog(CategoryActivity.this);
        dialog_cash.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cash.setCancelable(false);
        dialog_cash.setContentView(R.layout.recive_money_cash_dialog);
        dialog_cash.setCanceledOnTouchOutside(true);

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);

        final TextView total_money, remaining_money;
        final EditText receved_money;
        Button save_button, cancel_button;
        double pric = 0, recived = 0, remain = 0;

        total_money = dialog_cash.findViewById(R.id.textView_total_money);
        receved_money = dialog_cash.findViewById(R.id.recceved_money_editText);
        remaining_money = dialog_cash.findViewById(R.id.remaining_Textview);
        save_button = dialog_cash.findViewById(R.id.save_button);
        cancel_button = dialog_cash.findViewById(R.id.cancel_button);
//        save_button.setOnClickListener(onClickListener);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cash.dismiss();
            }
        });
        for (int i = 0; i < SettingOrder.ItemsOrder.size(); i++) {

            pric += SettingOrder.ItemsOrder.get(i).getTotal();


        }
        total_money.setText(pric + "");
        Log.e("pric", "" + pric);

        final double finalPric = pric;
        receved_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double recived = 0;
                try {

                    if (!s.equals("")) {
                        recived = Double.parseDouble(s + "");

                        if (recived >= finalPric) {
                            isPay = true;
                            remaining_money.setText((recived - finalPric) + "");
                        } else {
                            isPay = false;
                            remaining_money.setText("0");

                        }

                    } else {
                        remaining_money.setText("0");
                    }
                } catch (NumberFormatException e) {
                    recived = 0;
                    Log.e("Exception", "recived");

                }


            }
        });
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPay) {
                    double current_point = customerInformation.getPoint();
                    customerInformation.setPoint(current_point + points);
                    databaseHandler.updateCustomerPoint(phoneNo, points + current_point);

                }
                dialog_cash.dismiss();
            }
        });

        SettingOrder.Item.clear();
        SettingOrder.ItemsOrder.clear();
        SettingOrder.index = 0;

        dialog_cash.show();


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

    public class TestAdapter extends RecyclerView.Adapter<CViewHolder> {
        CategoryActivity context;
        List<CategoryModel> list;
//DatabaseHandler db;

        public TestAdapter() {
        }

        public TestAdapter(CategoryActivity context, List<CategoryModel> list) {
            this.context = context;
            this.list = list;
//        db=new DatabaseHandler(this.context);
        }

        @NonNull
        @Override
        public CViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.categoty_layout2, viewGroup, false);
            return new CViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final CViewHolder cViewHolder, final int i) {
            cViewHolder.categoryName.setText(list.get(i).getCategoryName());
            String image = list.get(i).getCategoryPic();
            if (image == null || (image.equals(""))) {
                cViewHolder.categoryImage.setBackgroundResource(R.drawable.ice_4);
            } else {
                Drawable drawable = new BitmapDrawable(context.getResources(), context.stringToBitmap(image));
                cViewHolder.categoryImage.setBackground(drawable);
            }
//            cViewHolder.layMain.setId(i);
//        cViewHolder.categoryName.setText(list.get(i).getCategoryName());
//            cViewHolder.categoryImage.setBackgroundResource(getImage(pic.get(i)));

            cViewHolder.layMain.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    Log.e("item ...", "i" + v.getId() + "-->" + i + "===>" + list.get(i));

                    Intent itemIntent = new Intent(context, ItemActivaty.class);
                    itemIntent.putExtra("categoryName", list.get(i).getCategoryName());
//                    itemIntent.putExtra("catPic",  stringToBitmap(list.get(i).getCategoryPic()));
                    categoryImage=stringToBitmap(list.get(i).getCategoryPic());
                    context.startActivity(itemIntent);
                    SettingOrder.indexCat = i;
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
//            return Integer.MAX_VALUE;
        }
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


    public int getImage(String imageName) {

        int drawableResourceId = CategoryActivity.this.getResources().getIdentifier(imageName, "drawable", CategoryActivity.this.getPackageName());
        return drawableResourceId;
    }

    void profileDialog() {

        final Dialog dialog = new Dialog(CategoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.customer_register);
        dialog.setCanceledOnTouchOutside(true);

        TextView cusName, cusno, email, point;
        ImageView barcode;
        ImageView cancel;
        String barcode_data = null;

        LinearLayout moreDetali = dialog.findViewById(R.id.moreDetali);
        final List<CustomerInformation> customerInformations = databaseHandler.getAllInformation();

        cusName = (TextView) dialog.findViewById(R.id.cusName);
        cusno = (TextView) dialog.findViewById(R.id.cusno);
        email = (TextView) dialog.findViewById(R.id.email);
        barcode = (ImageView) dialog.findViewById(R.id.barcode);
        cancel = (ImageView) dialog.findViewById(R.id.cancel);
        cusName = (TextView) dialog.findViewById(R.id.cusName);
        cusno = (TextView) dialog.findViewById(R.id.cusno);
        email = (TextView) dialog.findViewById(R.id.email);
        point = (TextView) dialog.findViewById(R.id.textView_point);


        cancel = (ImageView) dialog.findViewById(R.id.cancel);

        Bitmap bitmap = null;//  AZTEC -->QR

        if (customerInformations.size() != 0) {
            if (customerInformations.size() != 0) {
                cusName.setText(customerInformations.get(0).getCustomerName());
                cusno.setText(customerInformations.get(0).getPhoneNo());
                email.setText(customerInformations.get(0).getEmail());
                point.setText("" + customerInformations.get(0).getPoint());

                barcode_data = customerInformations.get(0).getPhoneNo();
                try {
                    bitmap = encodeAsBitmap(barcode_data, BarcodeFormat.QR_CODE, 1100, 200);
                    barcode.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(this, "no customer ", Toast.LENGTH_SHORT).show();
            }
            moreDetali.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialogDetail = new Dialog(CategoryActivity.this);
                    dialogDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogDetail.setCancelable(false);
                    dialogDetail.setContentView(R.layout.detali);
                    dialogDetail.setCanceledOnTouchOutside(true);
                    TextView noJD = (TextView) dialogDetail.findViewById(R.id.nojd);
                    TextView nopoint = (TextView) dialogDetail.findViewById(R.id.nopoint);

                    if (customerInformations.size() != 0) {
                        double NoJD = (customerInformations.get(0).getPoint()) / 10;
                        nopoint.setText(customerInformations.get(0).getPoint() + " Point");
                        noJD.setText(NoJD + " JD");
                    } else {

                        nopoint.setText("0.0 Point");
                        noJD.setText("0.0 JD");
                    }


                    dialogDetail.show();
                }
            });


            dialog.show();

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        }

        dialog.show();
    }

    void BarcodeDialog() {

        final Dialog dialog = new Dialog(CategoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.barcode_dialog);
        dialog.setCanceledOnTouchOutside(true);


        ImageView barcode;

        String barcode_data = null;

        LinearLayout moreDetali = dialog.findViewById(R.id.moreDetali);
        List<CustomerInformation> customerInformations = databaseHandler.getAllInformation();





        barcode = (ImageView) dialog.findViewById(R.id.barcodeQr);

        Bitmap bitmap = null;//  AZTEC -->QR

        if (customerInformations.size() != 0) {
            if (customerInformations.size() != 0) {
                barcode_data = customerInformations.get(0).getPhoneNo();
                JSONObject obj = new JSONObject() ;
                try {
                    obj.put("customerName", customerInformations.get(0).getCustomerName());
                    obj.put("customerNo", customerInformations.get(0).getPhoneNo());
                    obj.put("balance", customerInformations.get(0).getPoint());
                    obj.put("secureCode", customerInformations.get(0).getEmail());
                    obj.put("CompanyID", "2019");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

Log.e("JSONObject",""+obj.toString());
                try {
                    bitmap = encodeAsBitmap(obj.toString(), BarcodeFormat.QR_CODE, 100, 100);
                    barcode.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(this, "no customer ", Toast.LENGTH_SHORT).show();
            }


            dialog.show();


        }

        dialog.show();
    }

    void languageDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.language_layout);
        english = dialog.findViewById(R.id.login_language_english);
        arabic = dialog.findViewById(R.id.login_language_arabic);
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleAppUtils.setLocale(new Locale("en"));
                LocaleAppUtils.setConfigChange(CategoryActivity.this);
                finish();
                startActivity(getIntent());
            }
        });
        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleAppUtils.setLocale(new Locale("ar"));
                LocaleAppUtils.setConfigChange(CategoryActivity.this);
                finish();
                startActivity(getIntent());
            }
        });

        dialog.show();

    }

    void callUsDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.call_us_dialog_layout);
        emailMessage = dialog.findViewById(R.id.call_us_message);
        send = dialog.findViewById(R.id.call_us_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(emailMessage.getText().toString())) {
                    new SendMailTask(CategoryActivity.this).execute(senderName, senderPassword
                            , recipientName, emailTitle, emailMessage.getText().toString());
                    Toast.makeText(CategoryActivity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    void contactUsDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.contact_us_dialog);
        whatsApp = dialog.findViewById(R.id.contact_us_whats_app);
        facebook = dialog.findViewById(R.id.contact_us_facebook);
        twitter = dialog.findViewById(R.id.contact_us_twitter);
        instagram = dialog.findViewById(R.id.contact_us_instagram);

        whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent whatsAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Falconssoft/"));
//                startActivity(whatsAppIntent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Falconssoft/"));
                startActivity(whatsAppIntent);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        dialog.show();
    }

    static class CViewHolderForOrder extends RecyclerView.ViewHolder {

        TextView vocherNo, point, Qty, price;
        ImageView itemImage;

        public CViewHolderForOrder(@NonNull View itemView) {
            super(itemView);
            vocherNo = itemView.findViewById(R.id.vocherNo);
            Qty = itemView.findViewById(R.id.Qty);
            price = itemView.findViewById(R.id.price);
            point = itemView.findViewById(R.id.point);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }

    class TestAdapterForOrder extends RecyclerView.Adapter<CategoryActivity.CViewHolderForOrder> {
        Context context;
        List<Order> list;
//DatabaseHandler db;

        public TestAdapterForOrder(Context context, List<Order> list) {
            this.context = context;
            this.list = list;
//        db=new DatabaseHandler(this.context);
        }

        @NonNull
        @Override
        public CategoryActivity.CViewHolderForOrder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.order_layout_for_list, viewGroup, false);
            return new CategoryActivity.CViewHolderForOrder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final CategoryActivity.CViewHolderForOrder cViewHolder, final int i) {
            cViewHolder.vocherNo.setText(list.get(i).getVhNo());
//            cViewHolder.itemImage.setBackgroundResource(getImage(list.get(i).getDescription()));
            cViewHolder.Qty.setText("" + list.get(i).getQty());
            cViewHolder.price.setText("" + list.get(i).getTotal() + " JD ");
            cViewHolder.point.setText("" + list.get(i).getNoPoint() + " Point ");


//

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        }

        @Override
        public int getItemCount() {
            return list.size();
//            return Integer.MAX_VALUE;
        }
    }

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }


    //    void profileDialog() {
//
//        Bitmap encodeAsBitmap (String contents, BarcodeFormat format,int img_width, int img_height) throws
//        WriterException {
//            String contentsToEncode = contents;
//            if (contentsToEncode == null) {
//                return null;
//            }
//            Map<EncodeHintType, Object> hints = null;
//            String encoding = guessAppropriateEncoding(contentsToEncode);
//            if (encoding != null) {
//                hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
//                hints.put(EncodeHintType.CHARACTER_SET, encoding);
//            }
//            MultiFormatWriter writer = new MultiFormatWriter();
//            BitMatrix result;
//            try {
//                result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
//            } catch (IllegalArgumentException iae) {
//                // Unsupported format
//                return null;
//            }
//            int width = result.getWidth();
//            int height = result.getHeight();
//            int[] pixels = new int[width * height];
//            for (int y = 0; y < height; y++) {
//                int offset = y * width;
//                for (int x = 0; x < width; x++) {
//                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//                }
//            }
//
//            Bitmap bitmap = Bitmap.createBitmap(width, height,
//                    Bitmap.Config.ARGB_8888);
//            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//            return bitmap;
//        }
//
//        private static String guessAppropriateEncoding (CharSequence contents){
//            // Very crude at the moment
//            for (int i = 0; i < contents.length(); i++) {
//                if (contents.charAt(i) > 0xFF) {
//                    return "UTF-8";
//                }
//            }
//            return null;
//        }
//
//
//    }
    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    static class CViewHolderForbar extends RecyclerView.ViewHolder {

        TextView ItemName;
        ImageView itemImage;
        LinearLayout layBar;

        public CViewHolderForbar(@NonNull View itemView) {
            super(itemView);
            ItemName = itemView.findViewById(R.id.textbar);
            layBar = itemView.findViewById(R.id.layBar);
            itemImage = itemView.findViewById(R.id.imgbar);
        }
    }

    class TestAdapterForbar extends RecyclerView.Adapter<CategoryActivity.CViewHolderForbar> {
        Context context;
        List<String> list;
//DatabaseHandler db;

        public TestAdapterForbar(Context context, List<String> list) {
            this.context = context;
            this.list = list;
//        db=new DatabaseHandler(this.context);
        }

        @NonNull
        @Override
        public CategoryActivity.CViewHolderForbar onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.bar_item, viewGroup, false);
            return new CategoryActivity.CViewHolderForbar(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final CategoryActivity.CViewHolderForbar cViewHolder, final int i) {
            cViewHolder.ItemName.setText(list.get(i));
            cViewHolder.itemImage.setBackgroundResource(getImage(pic2.get(i)));
            cViewHolder.layBar.setTag("" + i);

            cViewHolder.layBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "id = " + v.getTag(), Toast.LENGTH_SHORT).show();

                    switch (Integer.parseInt(v.getTag().toString())) {
                        case 0:
                            Intent intents = new Intent(CategoryActivity.this, RewardActivity.class);
                            startActivity(intents);
                            break;
                        case 1:
                            Intent intentN = new Intent(CategoryActivity.this, NotificationActivity.class);
                            startActivity(intentN);
                            break;
                        case 2:
                            Intent intent = new Intent(CategoryActivity.this, PointViewActivity.class);
                            startActivity(intent);
                            break;
                        case 3:
                            BarcodeDialog();
                            break;
                        case 4:
//                            BranchesDialog();
                            sendSMS("0786812709","point app 12234");
                            break;

                        case 5:
                            Intent addNewIntent = new Intent(CategoryActivity.this, AddNewActivity.class);
                            startActivity(addNewIntent);
                            finish();
                            break;
                    }

                }
            });

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        }

        @Override
        public int getItemCount() {
            return list.size();
//            return Integer.MAX_VALUE;
        }
    }

    private void BranchesDialog() {


        final Dialog dialog = new Dialog(CategoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.branches_dialog);
        dialog.setCanceledOnTouchOutside(true);

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);

        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_branches);
        recyclerView.setLayoutManager(layoutManager);

        adapter_branch adapterBranch = new adapter_branch(this, branches_list);

        recyclerView.setAdapter(adapterBranch);


        dialog.show();


    }

    public  void FillCategory(){
        Categorys=databaseHandler.getAllCategoryTable();
        SettingOrder.Item.clear();
        for(int i=0;i<Categorys.size();i++){
            SettingOrder.Item.add(databaseHandler.getAllItemCategory(Categorys.get(i).getCategoryName()));
        }
    }

    public  void FillCategoryReturn(DatabaseHandler db){

       List<CategoryModel> Categorys1=db.getAllCategoryTable();
        SettingOrder.Item.clear();
        for(int i=0;i<Categorys1.size();i++){
            SettingOrder.Item.add(db.getAllItemCategory(Categorys1.get(i).getCategoryName()));
        }
    }

    public void orderListDialog() {
        Dialog dialog = new Dialog(CategoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.order_list_activaty);
        dialog.setCanceledOnTouchOutside(true);

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(VERTICAL);
        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.orderRecycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new TestItemAdapter(this, SettingOrder.ItemsOrder));
        recyclerView.setItemViewCacheSize(SettingOrder.ItemsOrder.size());

        dialog.show();
    }


    class CViewItemHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView balance, Qty;
        ImageView itemPic;
        ImageButton delete;
//        List<Items>ListOrder=new ArrayList<>();

        public CViewItemHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            balance = itemView.findViewById(R.id.TotalPricre);
            Qty = itemView.findViewById(R.id.Qty);
            itemPic = itemView.findViewById(R.id.itemPic);
            delete = itemView.findViewById(R.id.delete);
        }
    }


    class TestItemAdapter extends RecyclerView.Adapter<CategoryActivity.CViewItemHolder> {
        Context context;
        List<Items> list;

        public TestItemAdapter(Context context, List<Items> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public CategoryActivity.CViewItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_of_list_order, viewGroup, false);
            return new CategoryActivity.CViewItemHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final CategoryActivity.CViewItemHolder cViewItemHolder, final int i) {
            cViewItemHolder.itemName.setText(list.get(i).getItemName());
            cViewItemHolder.itemPic.setBackgroundResource(getImage(list.get(i).getDescription()));
            cViewItemHolder.Qty.setText("" + list.get(i).getQTY());
            cViewItemHolder.balance.setText("" + list.get(i).getTotal() + " JD");

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            cViewItemHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Log.e("size before ", "" + SettingOrder.ItemsOrder.size() + "    " + i + "     " + list.get(i).getIndexOfItem());
//                    SettingOrder.Item.get(SettingOrder.indexCat).get(list.get(i).getIndexOfItem()).setQTY(0.0);
//                    SettingOrder.Item.get(SettingOrder.indexCat).get(list.get(i).getIndexOfItem()).setTotal(0.0);
//
//                    ItemActivaty.TestAdapter Ad = new TestAdapter(CategoryActivity.this, SettingOrder.Item.get(SettingOrder.indexCat));
//                    recyclerView.setAdapter(Ad);
//
//                    list.remove(i);
//                    Log.e("size after ", "" + SettingOrder.ItemsOrder.size() + "    " + i);
//                    SettingOrder.index = SettingOrder.ItemsOrder.size();
//                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

    private static final int REQUEST_PHONE_CALL = 1;


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startActivity(callIntent);
                } else {
                    Toast.makeText(CategoryActivity.this, "check permission call ", Toast.LENGTH_SHORT).show();

                }
                return;
            }
        }
    }



}