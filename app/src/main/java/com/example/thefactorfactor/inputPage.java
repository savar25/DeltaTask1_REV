package com.example.thefactorfactor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class inputPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_page);
        TextView start=(TextView)findViewById(R.id.textView);
        final EditText editText=(EditText)findViewById(R.id.numberInput);


        final ImageButton imageButton=(ImageButton)findViewById(R.id.imageButton);
        int resource=getResources().getIdentifier("@drawable/ic_action_name",null,this.getPackageName());
        imageButton.setImageResource(resource);


        Intent intent1=getIntent();
        final int point=intent1.getIntExtra("points",0);

        TextView textView=(TextView)findViewById(R.id.currentPoints);
        textView.setText("Current Points: \n"+String.valueOf(point));




        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int input=Integer.parseInt(editText.getText().toString());

                Intent intent=new Intent(inputPage.this,actualTest.class);
                intent.putExtra("number",input);
                intent.putExtra("points",point);
                startActivity(intent);

            }
        });
    }



}
