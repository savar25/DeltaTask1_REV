package com.example.thefactorfactor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class actualTest extends AppCompatActivity {
    ArrayList<Integer> nonfactors=new ArrayList<>();
    ArrayList<Integer> factors=new ArrayList<>();
    public int counter=10;
    int points=0;
    CountDownTimer countDownTimer;
    ArrayList<Integer> testList=new ArrayList<>();
    int checker;
    ListView listView;
    int chosen=0;
    TextView timer;
    boolean flag=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_test);


        final ConstraintLayout constraintLayout=(ConstraintLayout)findViewById(R.id.layoutTest);
        final ConstraintLayout landscape=findViewById(R.id.land);
        timer=(TextView)findViewById(R.id.timer);



        Intent intent=getIntent();
        int num=intent.getIntExtra("number",0);
        points=intent.getIntExtra("points",0);

        TextView inputvalue=(TextView)findViewById(R.id.inputShow);
        inputvalue.setText("FIND FACTOR OF: "+String.valueOf(num));


        listView=findViewById(R.id.testlist);
        if(savedInstanceState!=null)
        {
            testList=(ArrayList<Integer>)savedInstanceState.getSerializable("list");
            checker=savedInstanceState.getInt("checker");
            counter=savedInstanceState.getInt("counter");
            flag=savedInstanceState.getBoolean("click");
            chosen=savedInstanceState.getInt("chosen");
            points=savedInstanceState.getInt("points");
           Timersetup(counter);
        }else {
            separateNumbers(num);
            checker = getRandomElement(factors);
            testList.add(checker);
            testList.add(threadRandom(nonfactors));
            testList.add(threadRandom(nonfactors));
            Collections.shuffle(testList);
            Timersetup(counter);
        }

        ArrayAdapter adapter = new ArrayAdapter(actualTest.this, android.R.layout.simple_list_item_1, testList);
        listView.setAdapter(adapter);


        //next set of actions happens after checking wether an earlier click has happened

        if(flag==false) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    flag = true;
                    countDownTimer.cancel();
                    chosen=testList.get(i);
                    if (chosen == checker) {
                        points += 50;
                        alertDialog("Correct Answer", "Continue With the Game", R.drawable.ic_correct);
                        if (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                            constraintLayout.setBackgroundColor(Color.GREEN);
                        } else if (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                            landscape.setBackgroundColor(Color.GREEN);
                        }


                    } else {
                        alertDialog("Wrong Answer", "Show Final Score?\nThe Correct answer is " + checker, R.drawable.ic_wrong);
                        if (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                            constraintLayout.setBackgroundColor(Color.RED);
                        } else if ((getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) || (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE)) {
                            landscape.setBackgroundColor(Color.RED);
                        }
                        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        if (Build.VERSION.SDK_INT > 26) {
                            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            vibrator.vibrate(200);
                        }
                    }
                }
            });
        }else if(flag==true){
            countDownTimer.cancel();
            if (chosen == checker) {
                alertDialog("Correct Answer", "Continue With the Game", R.drawable.ic_correct);
                if (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    constraintLayout.setBackgroundColor(Color.GREEN);
                } else if (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    landscape.setBackgroundColor(Color.GREEN);
                }


            } else {
                alertDialog("Wrong Answer", "Show Final Score?\nThe Correct answer is " + checker, R.drawable.ic_wrong);
                if (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    constraintLayout.setBackgroundColor(Color.RED);
                } else if ((getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) || (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE)) {
                    landscape.setBackgroundColor(Color.RED);
                }
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT > 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(200);
                }
            }
        }


    }



    public void separateNumbers(int num){
        for(int i=1;i<num;i++){
            if(num%i==0 && i!=1){
                factors.add(i);
            }else if(i!=1){
                nonfactors.add(i);
            }
        }
        if(checkprime(num)){
            factors.add(num);
        }
    }



    public boolean checkprime(int num){
        for(int i=2 ;i<num;i++){
            if(num%i==0){
                return false;
            }
        }
        return true;
    }


    public int getRandomElement(ArrayList<Integer> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public int threadRandom(ArrayList<Integer> list){
                    int randomIndex
                    = ThreadLocalRandom.current().nextInt(list.size());
            return list.get(randomIndex);

    }




    public void alertDialog (String title,String message,int ID){
        AlertDialog.Builder builder=new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setIcon(ID);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        if(ID==R.drawable.ic_correct) {
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent positive = new Intent(actualTest.this, inputPage.class);
                    positive.putExtra("points",points);
                    startActivity(positive);

                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog("Game Closed","Do you want to see the points?",R.drawable.q1);


                }
            });

            builder.show();
        }else if(ID==R.drawable.ic_wrong){
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent positive = new Intent(actualTest.this, levelPoints.class);
                    positive.putExtra("points",points);
                    startActivity(positive);

                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent negative = new Intent(actualTest.this, MainActivity.class);
                    negative.putExtra("points",points);
                    startActivity(negative);

                }
            });

            builder.show();
        }else{
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent positive = new Intent(actualTest.this, levelPoints.class);
                    positive.putExtra("points",points);
                    startActivity(positive);

                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent negative = new Intent(actualTest.this, MainActivity.class);
                    negative.putExtra("points",points);
                    startActivity(negative);
                }
            });

            builder.show();
        }
    }


    public int getScreenOrientation() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0
                || rotation == Surface.ROTATION_180) && height > width ||
                (rotation == Surface.ROTATION_90
                        || rotation == Surface.ROTATION_270) && width > height) {
            switch(rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        }
        // if the device's natural orientation is landscape or if the device
        // is square:
        else {
            switch(rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }

        return orientation;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        countDownTimer.cancel();
        Intent pa = new Intent(actualTest.this, inputPage.class);
        pa.putExtra("points",points);
        startActivity(pa);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("list",testList);
        outState.putInt("checker",checker);
        outState.putInt("counter",counter);
        outState.putBoolean("click",flag);
        outState.putInt("chosen",chosen);
        outState.putInt("points",points);
        countDownTimer.cancel();
    }

    public void Timersetup(int val){

        countDownTimer=new CountDownTimer(val*1000, 1000){
            public void onTick(long millisUntilFinished){
                timer.setText(String.valueOf(counter));
                if (counter >= 7) {
                    timer.setTextColor(getResources().getColor(R.color.Green));
                }else if(counter>=3) {
                    timer.setTextColor(getResources().getColor(R.color.Orange));
                }else if(counter>=0){
                    timer.setTextColor(getResources().getColor(R.color.Red));
                }counter--;
            }
            public  void onFinish(){
                alertDialog("Time up!","Show Final Score?",R.drawable.ic_wrong);
                Vibrator vibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT > 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.DEFAULT_AMPLITUDE));
                }else {
                    vibrator.vibrate(200);
                }

            }

        };
        countDownTimer.start();
    }



}

