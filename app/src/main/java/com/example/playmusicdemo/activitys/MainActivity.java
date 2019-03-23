package com.example.playmusicdemo.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.playmusicdemo.R;

public class MainActivity extends AppCompatActivity {

    private  static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toRegister(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
       // overridePendingTransition(R.anim.open_enter, R.anim.open_exit);

    }
}
