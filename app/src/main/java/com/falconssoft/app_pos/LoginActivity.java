package com.falconssoft.app_pos;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.app_pos.category.CategoryActivity;
import com.falconssoft.app_pos.category.ItemActivaty;
import com.falconssoft.app_pos.models.CustomerInformation;
import com.falconssoft.app_pos.category.CategoryActivity;
import com.falconssoft.app_pos.models.NotificationModel;
import com.falconssoft.app_pos.models.Tables;
import com.falconssoft.app_pos.models.Users;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username, password;
    private TextView english, arabic;
    private Button login, singup;


    private DatabaseHandler databaseHandler;
    private List<Users> users = new ArrayList<>();
    private List<Tables> tables = new ArrayList<>();

    NotificationManager notificationManager;
    static int id=1;
     String today="",time="";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHandler = new DatabaseHandler(this);

        Calendar calendar=Calendar.getInstance();
        Date date=Calendar.getInstance().getTime();
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        String myFormattime = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf2=new SimpleDateFormat(myFormattime, Locale.US);

        time = sdf2.format(calendar.getTime());
        today = sdf.format(calendar.getTime());

//        SimpleDateFormat simpleFormatter=new SimpleDateFormat("dd-MM-yyyy");
//        SimpleDateFormat simpleFormatters=new SimpleDateFormat("HH:mm:ss");

//        time = simpleFormatters.format(calendar.getTime());

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        english = findViewById(R.id.login_language_english);
        arabic = findViewById(R.id.login_language_arabic);
        login = findViewById(R.id.login_button);
        singup = findViewById(R.id.singup_button);

        login.setOnClickListener(this);
        english.setOnClickListener(this);
        arabic.setOnClickListener(this);
        singup.setOnClickListener(this);

       List <CustomerInformation>customerInformations= databaseHandler.getAllInformation();

        if(customerInformations.size()==0){
            singUpDialog();
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_button:
//                SendSocket send =new SendSocket(LoginActivity.this);
//                send.sendMessage();
                users = databaseHandler.getAllUSER();
                boolean found = false;
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
//                if (!usernameText.equals("") && !passwordText.equals("")) {
                    for (int i = 0; i < users.size(); i++)
                        if (usernameText.equals(users.get(i).getUsername()))
                            if (passwordText.equals(users.get(i).getPassword())) {
                                found = true;
                                Intent categoryIntent = new Intent(LoginActivity.this, CategoryActivity.class);
                                categoryIntent.putExtra("userName", usernameText);
                                startActivity(categoryIntent);
                            }

//                    if (found == false) {
//                        if (usernameText.equals("1")) {
//                            if (passwordText.equals("1")) {
                                Intent categoryIntent = new Intent(LoginActivity.this, CategoryActivity.class);
                                categoryIntent.putExtra("userName", usernameText);
                                startActivity(categoryIntent);
//                            } else {
//                                Toast.makeText(this, "Wrong in username or password!", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(this, "Wrong in username or password!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                } else {
//                    username.setError("Required field!");
//                    password.setError("Required field!");
//                }
                break;
            case R.id.login_language_english:
                LocaleAppUtils.setLocale(new Locale("en"));
                LocaleAppUtils.setConfigChange(this);
                finish();
                startActivity(getIntent());
                break;
            case R.id.login_language_arabic:
                LocaleAppUtils.setLocale(new Locale("ar"));
                LocaleAppUtils.setConfigChange(this);
                finish();
                startActivity(getIntent());
                break;
            case R.id.singup_button:

                singUpDialog();

                break;

        }
    }


    void singUpDialog() {

        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.singup);
        dialog.setCanceledOnTouchOutside(false);

        final EditText cusName, cusno, email;

        Button done;
        ImageView cancel;

        cusName = (EditText) dialog.findViewById(R.id.cusName);
        cusno = (EditText) dialog.findViewById(R.id.cusno);
        email = (EditText) dialog.findViewById(R.id.email);

        done = (Button) dialog.findViewById(R.id.dones);
        cancel = (ImageView) dialog.findViewById(R.id.cancel);


        done.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (!cusName.getText().toString().equals("")) {
                    if (!cusno.getText().toString().equals("")) {
                        if (!email.getText().toString().equals("")) {


                            databaseHandler.deleteAllInformation();
                            CustomerInformation customerInformation=new CustomerInformation(cusName.getText().toString(),cusno.getText().toString(),
                                    email.getText().toString());

                            databaseHandler.addCustomer(customerInformation);

                            cusName.setText("");
                            cusno.setText("");
                            email.setText("");


                            NotificationModel notificationModel=new NotificationModel("Thank you for downloading the Points app, so we'd like to add 30 free points to your account"
                                    ,today,"Registration Gift",time,"30");

                            List <CustomerInformation>customerInformations=new ArrayList<>();
                            String phoneNo="";
                            double point = 0;
                            customerInformations=databaseHandler.getAllInformation();
                            if(customerInformations.size()!=0){
                                phoneNo=customerInformations.get(0).getPhoneNo();
                                point=customerInformations.get(0).getPoint()+30;
                            }
                            databaseHandler.updateCustomerPoint(phoneNo, point);
                            databaseHandler.AddNotification(notificationModel);

//                            notification("Thank you for downloading the Points app, so we'd like to add 30 free points to your account");
                            String currentapiVersion = Build.VERSION.RELEASE;

                            if (Double.parseDouble(currentapiVersion.substring(0,1) )>=8) {
                                // Do something for 14 and above versions

                                show_Notification("Thank you for downloading the Points app, so we'd like to add 30 free points to your account");

                            } else {

                                // do something for phones running an SDK before 14
                                notification("Thank you for downloading the Points app, so we'd like to add 30 free points to your account");

                            }

                            Toast.makeText(LoginActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            email.setError("Required field!");
                        }
                    } else {
                        cusno.setError("Required field!");
                    }
                } else {
                    cusName.setError("Required field!");

                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        dialog.show();


    }

    private void notification (String detail){

        NotificationCompat.Builder nbuilder=new NotificationCompat.Builder(LoginActivity.this)
                .setContentTitle("POINT APP Notification ......")
                .setContentText("Point Gift For Register App  ")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.gift)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(detail)
                .setBigContentTitle("Point ").setSummaryText("Detail"))
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);

        notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id,nbuilder.build());
        id++;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(String detail){

        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        String CHANNEL_ID="MYCHANNEL";

        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_HIGH);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,intent,0);
        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText("POINT APP Notification ......")
                .setContentTitle("Point Gift From Point App")
                .setStyle(new Notification.BigTextStyle()
                        .bigText(detail)
                        .setBigContentTitle("Point ")
                        .setSummaryText("Gift"))
//                .setContentIntent(pendingIntent)
//                .addAction(android.R.drawable.sym_action_chat,"Title",pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.gift)
                .build();


        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);


    }
}
