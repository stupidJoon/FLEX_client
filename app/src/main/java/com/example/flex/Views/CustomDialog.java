package com.example.flex.Views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.flex.R;

public class CustomDialog extends Dialog {
    SeekBar moneyBar;
    EditText moneyEdit, titleEdit;
    Button positiveBtn, negativeBtn;

    int money = 0;

    public CustomDialog(final Context context, DisplayMetrics displayMetrics, final IncomeOutcome incomeOutcome, final CustomCallBack customCallBack) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        if (incomeOutcome == IncomeOutcome.INCOME) {
            setContentView(R.layout.income_dialog);
        }
        else {
            setContentView(R.layout.outcome_dialog);
        }
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.copyFrom(getWindow().getAttributes());
        layoutParams.width = (int)(displayMetrics.widthPixels / 1.5);
        layoutParams.height = displayMetrics.heightPixels / 2;

        moneyBar = findViewById(R.id.money_bar);
        moneyEdit = findViewById(R.id.money_edit);
        titleEdit = findViewById(R.id.title);
        positiveBtn = findViewById(R.id.positive);
        negativeBtn = findViewById(R.id.negative);
        moneyBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                moneyEdit.setText(Integer.toString(i * 1000));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleEdit.getText().toString().trim().equals("")) {
                    if (incomeOutcome == IncomeOutcome.INCOME) {
                        Toast.makeText(context, "수입 제목을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "지출 제목을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    customCallBack.positive(money);
                    dismiss();
                }
            }
        });
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    interface CustomCallBack {
        void positive(int money);
    }
    enum IncomeOutcome {
        INCOME, OUTCOME
    }
}