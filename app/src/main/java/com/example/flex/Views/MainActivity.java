package com.example.flex.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.flex.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        fragments.add(new SettingsFragment());
        fragments.add(new DashboardFragment());
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.income) {
            new CustomDialog(MainActivity.this, getResources().getDisplayMetrics(), CustomDialog.IncomeOutcome.INCOME, new CustomDialog.CustomCallBack() {
                @Override
                public void positive(int money) {
                    Toast.makeText(MainActivity.this, "Positive", Toast.LENGTH_SHORT).show();
                }
            }).show();
        }
        else if (item.getItemId() == R.id.outcome) {
            new CustomDialog(MainActivity.this, getResources().getDisplayMetrics(), CustomDialog.IncomeOutcome.OUTCOME, new CustomDialog.CustomCallBack() {
                @Override
                public void positive(int money) {
                    Toast.makeText(MainActivity.this, "Positive", Toast.LENGTH_SHORT).show();
                }
            }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    class MainPagerAdapter extends FragmentPagerAdapter {
        String[] titles = new String[]{"설정", "수입/지출 대시보드"};

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
