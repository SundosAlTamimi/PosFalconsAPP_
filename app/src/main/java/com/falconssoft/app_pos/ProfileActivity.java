package com.falconssoft.app_pos;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.app_pos.category.CategoryActivity;
import com.falconssoft.app_pos.models.CustomerInformation;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class ProfileActivity extends AppCompatActivity {


    TextView cusName, cusno, email, point,birthday;
    ImageView barcode;
    ImageView cancel;
    String barcode_data = null;
    LinearLayout moreDetali;
    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_register);
        databaseHandler=new DatabaseHandler(ProfileActivity.this);


        cusName = (TextView) findViewById(R.id.cusName);
        cusno = (TextView) findViewById(R.id.cusno);
        email = (TextView) findViewById(R.id.email);
        barcode = (ImageView) findViewById(R.id.barcode);
        cancel = (ImageView) findViewById(R.id.cancel);
        cusName = (TextView) findViewById(R.id.cusName);
        cusno = (TextView) findViewById(R.id.cusno);
        email = (TextView) findViewById(R.id.email);
        point = (TextView) findViewById(R.id.textView_point);
        cancel = (ImageView) findViewById(R.id.cancel);
        birthday= (TextView) findViewById(R.id.birthday);
        moreDetali = findViewById(R.id.moreDetali);

        final List<CustomerInformation> customerInformations = databaseHandler.getAllInformation();


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
                    final Dialog dialogDetail = new Dialog(ProfileActivity.this);
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




            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   finish();
                }
            });

        }



    }

    public  Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
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

}
