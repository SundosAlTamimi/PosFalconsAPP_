package com.falconssoft.app_pos;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.app_pos.category.CategoryActivity;
import com.falconssoft.app_pos.category.ItemActivaty;
import com.falconssoft.app_pos.models.CustomerInformation;
import com.falconssoft.app_pos.category.CategoryActivity;
import com.falconssoft.app_pos.models.NotificationModel;
import com.falconssoft.app_pos.models.Tables;
import com.falconssoft.app_pos.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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

    JSONObject jsonObject=new JSONObject();
    private DatabaseHandler databaseHandler;
    private List<Users> users = new ArrayList<>();
    private List<Tables> tables = new ArrayList<>();
    private Calendar calendar;
    NotificationManager notificationManager;
    static int id=1;
     String today="",time="";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHandler = new DatabaseHandler(this);

         calendar=Calendar.getInstance();
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
        singUpDialog();
//        if(customerInformations.size()==0){
//            singUpDialog();
//
//
//        }else{
//
//            Intent categoryIntent = new Intent(LoginActivity.this, CategoryActivity.class);
////                    categoryIntent.putExtra("userName", usernameText);
//            startActivity(categoryIntent);
//            finish();
//
//
//        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_button:
//                if (password.getText().toString().equals("1234")) {
//                            if (passwordText.equals("1")) {
                    List<CustomerInformation> customerInformation=new ArrayList<>();
                    customerInformation=databaseHandler.getAllInformation();
                    if(customerInformation.size()!=0){
                       String code= customerInformation.get(0).getEmail();
                        if(password.getText().toString().equals(code)){
                            Intent categoryIntent = new Intent(LoginActivity.this, CategoryActivity.class);
//                    categoryIntent.putExtra("userName", usernameText);
                            startActivity(categoryIntent);
                        }else {
                            Toast.makeText(this, "Wrong in username or password!", Toast.LENGTH_SHORT).show();
                        }
                    }


//                SendSocket send =new SendSocket(LoginActivity.this);
//                send.sendMessage();
//                users = databaseHandler.getAllUSER();
//                boolean found = false;
//                String usernameText = username.getText().toString();
//                String passwordText = password.getText().toString();
////                if (!usernameText.equals("") && !passwordText.equals("")) {
//                    for (int i = 0; i < users.size(); i++)
//                        if (usernameText.equals(users.get(i).getUsername()))
//                            if (passwordText.equals(users.get(i).getPassword())) {
//                                found = true;
//                                Intent categoryIntent = new Intent(LoginActivity.this, CategoryActivity.class);
//                                categoryIntent.putExtra("userName", usernameText);
//                                startActivity(categoryIntent);
//                            }
//
////                    if (found == false) {
////                        if (usernameText.equals("1")) {
////                            if (passwordText.equals("1")) {
//                                Intent categoryIntent = new Intent(LoginActivity.this, CategoryActivity.class);
//                                categoryIntent.putExtra("userName", usernameText);
//                                startActivity(categoryIntent);
////                            } else {
////                                Toast.makeText(this, "Wrong in username or password!", Toast.LENGTH_SHORT).show();
////                            }
////                        } else {
////                            Toast.makeText(this, "Wrong in username or password!", Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                } else {
////                    username.setError("Required field!");
////                    password.setError("Required field!");
////                }
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
final TextView birthday;
        Button done;
        ImageView cancel;

        cusName = (EditText) dialog.findViewById(R.id.cusName);
        cusno = (EditText) dialog.findViewById(R.id.cusno);
        email = (EditText) dialog.findViewById(R.id.email);

        done = (Button) dialog.findViewById(R.id.dones);
        cancel = (ImageView) dialog.findViewById(R.id.cancel);
        birthday= (TextView) dialog.findViewById(R.id.birthday);
        birthday.setText(today);

        final DatePickerDialog.OnDateSetListener dater = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                birthday.setText(sdf.format(calendar.getTime()));
            }

        };

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LoginActivity.this, dater, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (!cusName.getText().toString().equals("")) {
                    if (!cusno.getText().toString().equals("")) {
                        if (!email.getText().toString().equals("")) {


                            databaseHandler.deleteAllInformation();
                            CustomerInformation customerInformation=new CustomerInformation(cusName.getText().toString(),cusno.getText().toString(),
                                    email.getText().toString(),0,birthday.getText().toString());

                            databaseHandler.addCustomer(customerInformation);

                            cusName.setText("");
                            cusno.setText("");
                            email.setText("");


                            NotificationModel notificationModel=new NotificationModel("Thank you for downloading the Points app, so we'd like to add 30 free points to your account"
                                    ,today,"Registration Gift",time,"30");
                            jsonObject=customerInformation.getJSONObject();
                            try {
                                Log.e("jsonObject ",""+customerInformation.getJSONObject().get("CustomerName"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            new JSONTaskOrder().execute();

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



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notificationManager(String code){

        String currentapiVersion = Build.VERSION.RELEASE;

        if (Double.parseDouble(currentapiVersion.substring(0,1) )>=8) {
            // Do something for 14 and above versions

//                                show_Notification("Thank you for downloading the Points app, so we'd like to add 30 free points to your account");
            show_Notification("Thank you for downloading the Points app, Authentication code is "+code);


        } else {

            // do something for phones running an SDK before 14
//                                notification("Thank you for downloading the Points app, so we'd like to add 30 free points to your account");
            notification("Thank you for downloading the Points app, Authentication code is "+code);

        }


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

    public String BitMapToString(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] arr = baos.toByteArray();
            String result = Base64.encodeToString(arr, Base64.DEFAULT);
            return result;
        }

        return "";
    }


    public Bitmap StringToBitMap(String image) {
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
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
    private class JSONTaskOrder extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        String vhfNo, POSNO, orderKind;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
//                String link = "http://10.0.0.16:8080/WSKitchenScreen/FSAppServiceDLL.dll/RestSaveOrder";
                JSONArray jsonArray=new JSONArray();
                jsonArray.put(jsonObject);
                Log.e("jsonObject ",""+jsonArray.toString());

                String link = "http://10.0.0.86:82/WebService1.asmx/POintFunction";
//               String link = "http://10.0.0.16:8081/RestSaveOrder";
                String data = "flag=" + URLEncoder.encode("1", "UTF-8") + "&" +
                        "h=" + URLEncoder.encode(jsonArray.toString(), "UTF-8") ;


//                try {
//                    JSONObject jo = obj.getJSONObject("ORDERHEADER");
//                    vhfNo = jo.getString("VHFNO");
//                    POSNO = jo.getString("POSNO");
//                    orderKind = jo.getString("ORDERKIND");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                URL url = new URL(link);
                Log.e("url con ", "" + url.toString());

//                Log.e("data_order ", "--> " + obj.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag_order", "" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("vhf Success___", "= " + s);
            if (s != null && s.contains("Save Succsesful")) {
                try {
                    JSONObject jo = new JSONObject(s);
                    String ff=jo.getString("Response");
                    Log.e("vhf Success___", "= " + ff);
                    notificationManager(ff);
                   databaseHandler. updateCustomerAuth(ff);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

////                Toast.makeText(ExportJason.this , "Success" , Toast.LENGTH_SHORT).show();
//                Log.e("tag", "****Success");
//                Log.e("vhf Success___", "= " + vhfNo);
//
//                dbHandler.updateOrderTablesIsPost(vhfNo, POSNO, orderKind);
//                dbHandler.updateOrderTablesIsPost2(vhfNo, POSNO, orderKind);
//                dbHandler.updateOrderTablesIsPost3(vhfNo, POSNO, orderKind);
//
//            } else if (s != null && s.contains("ErrorCode : 6")) {
////
////                dbHandler.updateOrderTablesIsPost(vhfNo,POSNO,orderKind);
////                dbHandler.updateOrderTablesIsPost2(vhfNo,POSNO,orderKind);
////                dbHandler.updateOrderTablesIsPost3(vhfNo,POSNO,orderKind);
//
//            } else {
////                Toast.makeText(ExportJason.this, "Failed to export data", Toast.LENGTH_SHORT).show();
//                Log.e("tag ORDER", "****Failed to export data");
//                Log.e("vhf failed ___2", "= " + vhfNo + "POSNO = " + POSNO);
//            }
//            progressDialog.dismiss();
        }
    }

}
