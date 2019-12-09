package com.falconssoft.app_pos;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.falconssoft.app_pos.models.Order;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SoldReportActivity extends AppCompatActivity {
    PieChart pieChart;
    BarChart chart;
    TextView date,todate;
    Button Done;
    private Calendar myCalendar;
    DatabaseHandler databaseHandler;
    List<Order> orderList;
    TableLayout soldItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_detail_activity);

        chart = findViewById(R.id.barchart);
        pieChart = findViewById(R.id.piechart);
        date = findViewById(R.id.date);
        Done=findViewById(R.id.done);
        todate=findViewById(R.id.todate);
        soldItem=findViewById(R.id.soldItem);

        myCalendar = Calendar.getInstance();
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime()));
        todate.setText(sdf.format(myCalendar.getTime()));

        databaseHandler=new DatabaseHandler(SoldReportActivity.this);
         orderList=new ArrayList<>();


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SoldReportActivity.this, datePicker(date), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SoldReportActivity.this, datePicker(todate), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!date.getText().toString().equals("")){
                List<Order>orderListChart=new ArrayList<>();
               String datefrom =date.getText().toString();
                String dateTo =todate.getText().toString();
               orderList=databaseHandler.getAllOrderGroupByItemName();



                soldItem.removeAllViews();
               for(int i=0;i<orderList.size();i++) {



                   String getDate = orderList.get(i).getDate();
                   String cou_total = orderList.get(i).getItemBarcode();
                   String cou_qty = orderList.get(i).getCustomerName();
                   String cou_price = orderList.get(i).getCustomerNo();
                   String cou_tax = orderList.get(i).getVhNo();

                   String itemName = orderList.get(i).getItemName();
                   String[] arrayDate = getDate.split(",");
                   String[] arrayTotal = cou_total.split(",");
                   String[] arrayQty = cou_qty.split(",");
                   String[] arrayPrice = cou_price.split(",");
                   String[] arrayTax = cou_tax.split(",");

                   double Total=0,qty=0,tax=0;
                   boolean noIn=false;
                   for(int k=0;k<arrayDate.length;k++){
                   if (filtersDate(datefrom, dateTo, arrayDate[k])) {
                       Total+=Double.parseDouble(arrayTotal[k]);
                       qty+=Double.parseDouble(arrayQty[k]);
                       tax+=Double.parseDouble(arrayTax[k]);
                       noIn=true;
                   }
               }
                   Order order=new Order();
                   order.setQty(qty);
                   orderListChart.add(order);
                   if(noIn) {
                       insertRowForSoldReport(soldItem, itemName, "" + qty,
                               "" + Double.parseDouble(arrayPrice[0]), "" + tax, "" + Total);
                   }
               }

                pieChart(orderListChart);

            }
            }
        });

        //barChart();


    }

    DatePickerDialog.OnDateSetListener datePicker(final TextView text){

        final DatePickerDialog.OnDateSetListener dater = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                text.setText(sdf.format(myCalendar.getTime()));
            }

        };

        return dater;

    }




    void barChart() {

        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new BarEntry(945f, 0));
        NoOfEmp.add(new BarEntry(1040f, 1));
        NoOfEmp.add(new BarEntry(1133f, 2));
        NoOfEmp.add(new BarEntry(1240f, 3));
        NoOfEmp.add(new BarEntry(1369f, 4));
        NoOfEmp.add(new BarEntry(1487f, 5));
//        NoOfEmp.add(new BarEntry(1501f, 6));
//        NoOfEmp.add(new BarEntry(1645f, 7));
//        NoOfEmp.add(new BarEntry(1578f, 8));
//        NoOfEmp.add(new BarEntry(1695f, 9));

        ArrayList year = new ArrayList();

        year.add("2008");
        year.add("2009");
        year.add("2010");
        year.add("2011");
        year.add("2012");
        year.add("2013");


        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
        chart.animateY(5000);
        BarData data = new BarData(bardataset);
        bardataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        chart.setData(data);

    }

    void pieChart(List<Order> orderListCharts) {
        ArrayList NoOfEmp = new ArrayList();

        for(int i=0;i<orderListCharts.size();i++){
            NoOfEmp.add(new PieEntry(Float.parseFloat(""+orderListCharts.get(i).getQty()),orderListCharts.get(i).getQty()));

        }

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Item Qty ");

        ArrayList year2 = new ArrayList();

        year2.add("2008");
        year2.add("2009");
        year2.add("2010");
        year2.add("2011");
        year2.add("2012");
        year2.add("2013");
        year2.add("2014");
        year2.add("2015");
        year2.add("2016");
        year2.add("2017");
        pieChart.setCenterTextRadiusPercent(0);
        Description v=new Description();
        v.setText("");
        pieChart.setDescription(v);
        PieData data2 = new PieData(dataSet);

        pieChart.setData(data2);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieChart.animateXY(1500, 1500);
    }

    void insertRowForSoldReport(TableLayout tableLayout, String itemname, String itemqty, String price, String tax, String net) {
        final TableRow row = new TableRow(SoldReportActivity.this);

        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
//        lp.setMargins(2, 2, 2, 2);
        row.setLayoutParams(lp);

        for (int i = 0; i <5; i++) {
            TextView textView = new TextView(SoldReportActivity.this);

            switch (i) {
                case 0:
                    textView.setText(itemname);
                    break;
                case 1:
                    textView.setText(itemqty);

                    break;
                case 2:
                    textView.setText(price);
                    break;
                case 3:
                    textView.setText(tax);
                    break;
                case 4:
                    textView.setText(net);
                    break;



            }

            textView.setTextColor(ContextCompat.getColor(SoldReportActivity.this, R.color.purple));
//            textView.setBackgroundColor(ContextCompat.getColor(SoldReportActivity.this, R.color.jeans_blue));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(18);

            TableRow.LayoutParams lp2 = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
            lp2.setMargins(2, 2, 2, 2);
            textView.setLayoutParams(lp2);

            row.addView(textView);

        }

        tableLayout.addView(row);

    }

    public boolean filtersDate(String fromDate, String toDate, String date) {
        try {
            if ((formatDate(date).after(formatDate(fromDate)) || formatDate(date).equals(formatDate(fromDate))) &&
                    (formatDate(date).before(formatDate(toDate)) || formatDate(date).equals(formatDate(toDate))))
                return true;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Date formatDate(String date) throws ParseException {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date d = sdf.parse(date);
        return d;
    }

}
