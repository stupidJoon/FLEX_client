package com.example.flex.Views;

import android.support.annotation.NonNull;
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

import com.example.flex.Models.Estate;
import com.example.flex.Models.HTTPHelper;
import com.example.flex.R;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
        fragments.add(new DashboardFragment());
        fragments.add(new SettingsFragment());
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
                public void positive(String title, String money) {
                    HTTPHelper.addEstate(new Estate("income", title, money), new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Toast.makeText(getApplicationContext(), "Add Estate Error", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            Gson gson = new Gson();
                            AddEstateVO data = gson.fromJson(response.body().string(), AddEstateVO.class);
                            if (data.status == true) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "데이터 추가에 성공했습니다", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "데이터 추가에 실패했습니다", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }).show();
        }
        else if (item.getItemId() == R.id.outcome) {
            new CustomDialog(MainActivity.this, getResources().getDisplayMetrics(), CustomDialog.IncomeOutcome.OUTCOME, new CustomDialog.CustomCallBack() {
                @Override
                public void positive(String title, String money) {
                    HTTPHelper.addEstate(new Estate("outcome", title, money), new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Toast.makeText(getApplicationContext(), "Add Estate Error", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            Gson gson = new Gson();
                            AddEstateVO data = gson.fromJson(response.body().string(), AddEstateVO.class);
                            if (data.status == true) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "데이터 추가에 성공했습니다", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "데이터 추가에 실패했습니다", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    class MainPagerAdapter extends FragmentPagerAdapter {
        String[] titles = new String[]{"수입/지출 대시보드", "설정"};

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

    class AddEstateVO {
        boolean status;
        String message;
    }
}
