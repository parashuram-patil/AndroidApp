package com.example.psp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.psp.notimportant.ContentItem;
import com.example.psp.notimportant.MyAdapter;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        setTitle("MPAndroidChart Example");

        Utils.init(this);

        ArrayList<ContentItem> objects = new ArrayList<>();

        objects.add(0, new ContentItem("Line Charts"));

        objects.add(1, new ContentItem("Basic", "Simple line chart."));
        objects.add(2, new ContentItem("Multiple", "Show multiple data sets."));

        objects.add(3, new ContentItem("Bar Charts"));

        objects.add(4, new ContentItem("Basic", "Simple bar chart."));
        objects.add(5, new ContentItem("Basic 2", "Variation of the simple bar chart."));

        objects.add(6, new ContentItem("Pie Charts"));

        objects.add(7, new ContentItem("Basic", "Simple pie chart."));
        objects.add(8, new ContentItem("Value Lines", "Stylish lines drawn outward from slices."));

        MyAdapter adapter = new MyAdapter(this, objects);

        ListView lv = findViewById(R.id.listView1);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int pos, long arg3) {

        Intent i = null;

        switch (pos) {
            case 1:
                i = new Intent(this, LineChartActivity.class);
                break;
        }

        if (i != null) startActivity(i);

        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

    }

    public void onClick(View view) {

    }

    /*@Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pieChartButton:
                Pie pie = getPie();
                populateChart(pie);
                break;

            case R.id.columnChartButton:
                Cartesian cartesian = getCartesian();
                populateChart(cartesian);
                break;

            case R.id.waterfallChartButton:
                Waterfall waterfall = AnyChart.waterfall();
                populateChart(waterfall);
        }
    }

    private Cartesian getCartesian() {
        Cartesian cartesian = AnyChart.column();
        List<DataEntry> columData = new ArrayList<>();
        columData.add(new ValueDataEntry("Rouge", 80540));
        columData.add(new ValueDataEntry("Foundation", 94190));
        columData.add(new ValueDataEntry("Mascara", 102610));
        columData.add(new ValueDataEntry("Lip gloss", 110430));
        columData.add(new ValueDataEntry("Lipstick", 128000));

        Column column = cartesian.column(columData);
        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Top 10 Cosmetic Products by Revenue");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Product");
        cartesian.yAxis(0).title("Revenue");
        return cartesian;
    }

    private Pie getPie() {
        Pie pie = AnyChart.pie();

        List<DataEntry> pieData = new ArrayList<>();
        pieData.add(new ValueDataEntry("John", 10000));
        pieData.add(new ValueDataEntry("Jake", 12000));
        pieData.add(new ValueDataEntry("Peter", 18000));

        pie.data(pieData);
        return pie;
    }

    private void populateChart(Chart chart) {
        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        anyChartView.setChart(chart);
    }*/
}
