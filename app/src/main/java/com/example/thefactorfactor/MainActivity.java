package com.example.thefactorfactor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);


        TextView head=(TextView)findViewById(R.id.header);
        head.setText(R.string.app_name);
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,inputPage.class);
                startActivity(intent);

            }
        });

        Intent second=getIntent();
        int points=second.getIntExtra("points",0);
        int storedPoints=sharedPreferences.getInt("High Score",0);
        if(points>storedPoints) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("High Score", points);
            editor.commit();
        }

            TextView textView=(TextView)findViewById(R.id.PointsTab);
            textView.setText("High Score: "+String.valueOf(sharedPreferences.getInt("High Score",0)));






    };
}
