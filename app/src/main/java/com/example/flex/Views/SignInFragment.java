package com.example.flex.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flex.Models.HTTPHelper;
import com.example.flex.R;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignInFragment extends Fragment {

    public SignInFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView signInText = view.findViewById(R.id.signin_text);
        Button signInBtn = view.findViewById(R.id.signin_btn);
        final EditText emailEdit = view.findViewById(R.id.email_edit);
        final EditText pwEdit = view.findViewById(R.id.pw_edit);
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.sign_container, new SignUpFragment());
                fragmentTransaction.commit();
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HTTPHelper.signIn(emailEdit.getText().toString().trim(), pwEdit.getText().toString().trim(), new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Toast.makeText(getActivity(), "Sign In Error!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Gson gson = new Gson();
                        SignInVO data = gson.fromJson(response.body().string(), SignInVO.class);
                        if (data.status == true) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Sign In Succeeded", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getActivity(), MainActivity.class);;
                                    startActivity(i);
                                }
                            });
                        }
                        else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Sign In Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    class SignInVO {
        boolean status;
        String id;
        String message;
    }
}
