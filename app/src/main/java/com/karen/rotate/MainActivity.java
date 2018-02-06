package com.karen.rotate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView img;
    private TextView txt;
    private float initialX, finalX;
    private float coefficient;
    private float startDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        decideCoefficient();
    }

    private void initViews() {
        img = findViewById(R.id.triangleImg);
        txt = findViewById(R.id.txtAngle);
    }

    private void decideCoefficient() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        coefficient = (float)width/180;
    }

    private void rotateImg(float fromDegrees, float toDegrees) {
        RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatCount(0);
        rotate.setFillAfter(true);
        rotate.setInterpolator(this, android.R.interpolator.accelerate_decelerate);
        img.startAnimation(rotate);
    }

    private float getDegree(float fromX, float toX) {
        return (toX - fromX) / coefficient;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                finalX = event.getX();
                rotateImg(startDegree, startDegree + getDegree(initialX, finalX));
                startDegree += getDegree(initialX, finalX);
                if (startDegree >= 360) {
                    startDegree -= 360;
                }
                if (startDegree <=-360) {
                    startDegree += 360;
                }
                txt.setText("From " + startDegree + " to " + (startDegree + getDegree(initialX, finalX)));
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
