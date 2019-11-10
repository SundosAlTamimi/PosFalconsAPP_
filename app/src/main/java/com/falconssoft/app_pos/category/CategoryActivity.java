package com.falconssoft.app_pos.category;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.app_pos.DatabaseHandler;
import com.falconssoft.app_pos.LocaleAppUtils;
import com.falconssoft.app_pos.R;
import com.falconssoft.app_pos.SendSocket;
import com.falconssoft.app_pos.SettingOrder;
import com.falconssoft.app_pos.email.SendMailTask;
import com.falconssoft.app_pos.models.CustomerInformation;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import cdflynn.android.library.turn.TurnLayoutManager;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static com.falconssoft.app_pos.models.ShareValues.emailTitle;
import static com.falconssoft.app_pos.models.ShareValues.recipientName;
import static com.falconssoft.app_pos.models.ShareValues.senderName;
import static com.falconssoft.app_pos.models.ShareValues.senderPassword;

public class CategoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView english, arabic, emailMessage;
    private Button send;
    private ImageButton facebook, twitter, instagram, whatsApp;

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
    DatabaseHandler databaseHandler;

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
        databaseHandler = new DatabaseHandler(CategoryActivity.this);
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
                false);


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
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

//                SendSocket sendSocket = new SendSocket(CategoryActivity.this);
//                sendSocket.sendMessage();
//            }
//        });

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
                break;
            case R.id.menu_contact_with_us:
                contactUsDialog();
                break;
            case R.id.menu_language:
                languageDialog();
                break;
            case R.id.menu_app_developers:
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.app_developers_dialog_layout);
                dialog.show();
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

    /**
     * Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
     * String contentsToEncode = contents;
     * if (contentsToEncode == null) {
     * return null;
     * }
     * Map<EncodeHintType, Object> hints = null;
     * String encoding = guessAppropriateEncoding(contentsToEncode);
     * if (encoding != null) {
     * hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
     * hints.put(EncodeHintType.CHARACTER_SET, encoding);
     * }
     * MultiFormatWriter writer = new MultiFormatWriter();
     * BitMatrix result;
     * try {
     * result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
     * } catch (IllegalArgumentException iae) {
     * // Unsupported format
     * return null;
     * }
     * int width = result.getWidth();
     * int height = result.getHeight();
     * int[] pixels = new int[width * height];
     * for (int y = 0; y < height; y++) {
     * int offset = y * width;
     * for (int x = 0; x < width; x++) {
     * pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
     * }
     * }
     * <p>
     * Bitmap bitmap = Bitmap.createBitmap(width, height,
     * Bitmap.Config.ARGB_8888);
     * bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
     * return bitmap;
     * }
     * <p>
     * private static String guessAppropriateEncoding(CharSequence contents) {
     * // Very crude at the moment
     * for (int i = 0; i < contents.length(); i++) {
     * if (contents.charAt(i) > 0xFF) {
     * return "UTF-8";
     * }
     * }
     * return null;
     * }
     */
    void profileDialog() {

        final Dialog dialog = new Dialog(CategoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.customer_register);
        dialog.setCanceledOnTouchOutside(true);

        TextView cusName, cusno, email;
        ImageView barcode;
        ImageView cancel;
        LinearLayout moreDetali = dialog.findViewById(R.id.moreDetali);
        List<CustomerInformation> customerInformations = databaseHandler.getAllInformation();

        cusName = (TextView) dialog.findViewById(R.id.cusName);
        cusno = (TextView) dialog.findViewById(R.id.cusno);
        email = (TextView) dialog.findViewById(R.id.email);

        cancel = (ImageView) dialog.findViewById(R.id.cancel);

        if (customerInformations.size() != 0) {
            cusName.setText(customerInformations.get(0).getCustomerName());
            cusno.setText(customerInformations.get(0).getPhoneNo());
            email.setText(customerInformations.get(0).getEmail());

        } else {
            Toast.makeText(this, "no customer ", Toast.LENGTH_SHORT).show();
        }
        barcode = (ImageView) findViewById(R.id.barcode);


        Bitmap bitmap = null;//  AZTEC -->QR

//        try {
//            bitmap = encodeAsBitmap(barcode_data, BarcodeFormat.CODE_128, 1100, 200);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }

        moreDetali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogDetail = new Dialog(CategoryActivity.this);
                dialogDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogDetail.setCancelable(false);
                dialogDetail.setContentView(R.layout.detali);
                dialogDetail.setCanceledOnTouchOutside(true);
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

    void languageDialog() {
        Dialog dialog = new Dialog(this);
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

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.show();
    }

}