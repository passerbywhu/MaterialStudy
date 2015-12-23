package com.passerbywhu.materialstudy.behavior.follow;

import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.passerbywhu.materialstudy.R;

/**
 * Created by hzwuwenchao on 2015/12/23.
 */
public class FollowActivity extends AppCompatActivity {
    private View mRootLayout;
    private View mFirst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        mRootLayout = findViewById(R.id.rootLayout);
        mFirst = findViewById(R.id.first);
        mRootLayout.setOnTouchListener(new View.OnTouchListener() {
            private int downId;
            private float lastY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                switch(action) {
                    case MotionEvent.ACTION_DOWN: {
                        int actionIndex = MotionEventCompat.getActionIndex(event);
                        downId = MotionEventCompat.getPointerId(event, actionIndex);
                        lastY = MotionEventCompat.getY(event, actionIndex);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        int actionIndex = MotionEventCompat.getActionIndex(event);
                        int id = MotionEventCompat.getPointerId(event, actionIndex);
                        if (id != downId) {  //换过手指了
                            downId = id;
                            lastY = MotionEventCompat.getY(event, actionIndex);
                        } else {
                            float curY = MotionEventCompat.getY(event, actionIndex);
                            float deltaY = curY - lastY;
                            lastY = curY;
                            mFirst.setY(mFirst.getY() + deltaY);
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
