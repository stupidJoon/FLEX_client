package com.example.flex.Views;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.flex.Models.DataSingleton;
import com.example.flex.Models.Estate;
import com.example.flex.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

public class DashboardFragment extends Fragment {
    RecyclerView recycler;
    DashboardAdapter adapter;
    LinkedHashMap<String, ArrayList<Estate>> datas = new LinkedHashMap<>();

    public DashboardFragment() { }

    public void notifyRecycler() {
        for (Estate estate : DataSingleton.getInstance().estates) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = null;
            Calendar cal = Calendar.getInstance();
            try {
                date = format.parse(estate.created_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal.setTime(date);
            String key = Integer.toString(cal.get(Calendar.YEAR)) + "년 " + Integer.toString(cal.get(Calendar.MONTH) + 1) + "월";
            if (datas.containsKey(key)) {
                datas.get(key).add(estate);
            }
            else {
                datas.put(key, new ArrayList<Estate>(Arrays.asList(estate)));
            }
        }
        if (adapter != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        recycler = view.findViewById(R.id.recycler);
        adapter = new DashboardAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    class DashboardViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        PieChart pieChart;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            pieChart = itemView.findViewById(R.id.pie_chart);
        }
    }
    class DashboardAdapter extends RecyclerView.Adapter<DashboardViewHolder> {
        @NonNull
        @Override
        public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dash_board_item, viewGroup, false);
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            Point point = new Point();
            ((WindowManager)(getContext().getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getSize(point);
            lp.height = Math.round(point.y * 0.8f);
            view.setLayoutParams(lp);
            return new DashboardViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull final DashboardViewHolder dashboardViewHolder, int i) {
            String key = (String)datas.keySet().toArray()[i];
            dashboardViewHolder.title.setText(key + " 총 수입/지출");

            ArrayList<Estate> estates = datas.get(key);
            ArrayList<PieEntry> incomeEntries = new ArrayList<>();
            ArrayList<PieEntry> outcomeEntries = new ArrayList<>();
            for (Estate estate : estates) {
                if (estate.type.equals("income")) {
                    incomeEntries.add(new PieEntry(Integer.valueOf(estate.money), estate.created_date));
                }
                else {
                    outcomeEntries.add(new PieEntry(Integer.valueOf(estate.money), estate.created_date));
                }
            }

            PieDataSet incomeDataSet = new PieDataSet(incomeEntries, "수입");
            PieDataSet outcomeDataSet = new PieDataSet(outcomeEntries, "지출");
            ArrayList<Integer> colors = new ArrayList<>();
            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);
            colors.add(ColorTemplate.getHoloBlue());
            incomeDataSet.setColors(colors);
            outcomeDataSet.setColors(colors);

            PieData data = new PieData();
            data.addDataSet(incomeDataSet);
//            data.addDataSet(outcomeDataSet);
            data.setValueTextSize(14.0f);
            data.setValueTextColor(Color.BLACK);

            dashboardViewHolder.pieChart.setEntryLabelColor(Color.BLACK);
            dashboardViewHolder.pieChart.setEntryLabelTextSize(11.0f);
            dashboardViewHolder.pieChart.setData(data);

            dashboardViewHolder.pieChart.invalidate();
        }
        @Override
        public int getItemCount() {
            return datas.size();
        }
    }
}
