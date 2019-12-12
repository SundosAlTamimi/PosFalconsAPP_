package com.falconssoft.app_pos;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.falconssoft.app_pos.models.Order;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {
    PieChart pieChart;
    BarChart chart;
    TextView date,totalCash;
    Button Done;
    private Calendar myCalendar;
    DatabaseHandler databaseHandler;
    List<Order> orderList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        chart = findViewById(R.id.barchart);
        pieChart = findViewById(R.id.piechart);
        date = findViewById(R.id.date);
        Done=findViewById(R.id.done);
        totalCash=findViewById(R.id.total);

        myCalendar = Calendar.getInstance();
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime())); 
        databaseHandler=new DatabaseHandler(ReportActivity.this);
         orderList=new ArrayList<>();
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

                date.setText(sdf.format(myCalendar.getTime()));
            }

        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReportActivity.this, dater, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!date.getText().toString().equals("")){

               String dateT =date.getText().toString();

               orderList=databaseHandler.getOrderByDate(dateT);
               double total =0;
               for(int i=0;i<orderList.size();i++){
                   total=+orderList.get(i).getTotal();
               }
                totalCash.setText(""+total);
            }
            }
        });

        //barChart();
        pieChart();
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
//        year.add("2014");
//        year.add("2015");
//        year.add("2016");
//        year.add("2017");

        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
        chart.animateY(5000);
        BarData data = new BarData(bardataset);
        bardataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        chart.setData(data);

    }

    void pieChart() {
        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new PieEntry(945f, 0));
        NoOfEmp.add(new PieEntry(1040f, 1));
        NoOfEmp.add(new PieEntry(1133f, 2));
        NoOfEmp.add(new PieEntry(1240f, 3));
        NoOfEmp.add(new PieEntry(1369f, 4));
        NoOfEmp.add(new PieEntry(1487f, 5));
        NoOfEmp.add(new PieEntry(1501f, 6));
        NoOfEmp.add(new PieEntry(1645f, 7));
        NoOfEmp.add(new PieEntry(1578f, 8));
        NoOfEmp.add(new PieEntry(1695f, 9));
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");

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
        PieData data2 = new PieData(dataSet);
        pieChart.setData(data2);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieChart.animateXY(1500, 1500);
    }

}
