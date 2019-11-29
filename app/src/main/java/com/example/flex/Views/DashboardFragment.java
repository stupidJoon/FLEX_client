package com.example.flex.Views;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flex.R;

public class DashboardFragment extends Fragment {
    RecyclerView recycler;

    public DashboardFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dash_board, container, false);
    }

    class DashboardViewHolder extends RecyclerView.ViewHolder {
        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    class DashboardAdapter extends RecyclerView.Adapter<DashboardViewHolder> {
        @NonNull
        @Override
        public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return null;
        }
        @Override
        public void onBindViewHolder(@NonNull DashboardViewHolder dashboardViewHolder, int i) {

        }
        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
