package com.angus.mygestures;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GestureOverlayView GOV;
    TextView tv1;
    GestureLibrary library;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView)findViewById(R.id.textView);
        GOV = (GestureOverlayView)findViewById(R.id.GestureOverlayView);
        library = GestureLibraries.fromRawResource(MainActivity.this, R.raw.gestures);

        if(!library.load())
            finish();

        GOV.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture usr) {
                ArrayList<Prediction> list = library.recognize(usr);
                StringBuilder sb = new StringBuilder("資料庫內容比對如下: \n");
                for(int i=0; i < list.size(); i++){
                    sb.append("名稱:" + list.get(i).name + "\t");
                    sb.append("分數:" + list.get(i).score + "\n");
                }

                if(list.size()>0 && list.get(0).score > 2.0){
                    String name = list.get(0).name;
                    double score = list.get(0).score;
                    sb.append(String.format("最佳結果: \n name =%s  score = %.2f",name, score ));
                }else{
                    sb.append("找不到相符手勢");
                }
                tv1.setText(sb);
            }
        });

    }
}
