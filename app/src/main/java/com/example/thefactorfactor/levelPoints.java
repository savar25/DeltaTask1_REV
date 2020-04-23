package com.example.thefactorfactor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class levelPoints extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_points);

        final Intent intent=getIntent();
        final int points=intent.getIntExtra("points",0);


        TextView showPoints=(TextView)findViewById(R.id.spText);
        showPoints.setText(String.valueOf(points));


        Button backbtn=(Button)findViewById(R.id.homeButton);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(levelPoints.this,MainActivity.class);
                intent1.putExtra("points",points);
                startActivity(intent1);
            }
        });
    }
}
