package com.pm1.exercise13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btningreso= (Button) findViewById(R.id.btnIngreso);
        btningreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityIngresar.class);
                startActivity(intent);
            }
        });

        Button btnver= (Button) findViewById(R.id.btnVer);
        btnver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),ActivityListView.class);
                startActivity(intent1);
            }
        });
    }
}