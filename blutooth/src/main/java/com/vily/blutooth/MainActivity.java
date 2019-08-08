package com.vily.blutooth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mBtn_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn_click = findViewById(R.id.btn_click);

        mBtn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,DialogActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_bottom_in,0);
            }
        });
    }
}
