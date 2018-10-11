package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;


public class SCOSEntry extends Activity {
    private GestureDetector gestureDetector; 					//手势检测
    private OnGestureListener onSlideGestureListener = null;	//左右滑动手势检测监听器

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        //定义左右滑动手势监听器,将监听器传入手势识别器
        onSlideGestureListener = new OnSlideGestureListener();
        gestureDetector = new GestureDetector(this, onSlideGestureListener);
    }

    //将touch动作事件交由手势检测监听器来处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    private class OnSlideGestureListener implements OnGestureListener
    {
        @Override
        public boolean onDown(MotionEvent e) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            // TODO Auto-generated method stub

        }
        //OnGestureListener接口对滑动处理的动作
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            // 参数解释：
            // e1：第1个ACTION_DOWN MotionEvent
            // e2：最后一个ACTION_MOVE MotionEvent
            // velocityX：X轴上的移动速度，像素/秒
            // velocityY：Y轴上的移动速度，像素/秒
            // 触发条件：X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
            if ((e1 == null) || (e2 == null)){
                return false;
            }
            int FLING_MIN_DISTANCE = 50;
            int FLING_MIN_VELOCITY = 50;
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY)
            {
                // 向左滑动发送消息
                Intent intent3 = new Intent(SCOSEntry.this, MainScreen.class);
                intent3.putExtra("entry","FromEntry");
                startActivity(intent3);
                /**
                 * R.anim.move_right_in新的Activity进入时的动画，这里是指OtherActivity进入时的动画
                 * R.anim.move_left_out：旧的Activity出去时的动画，这里是指this进入时的动画
                 */
                overridePendingTransition(R.anim.move_right_in, R.anim.move_left_out);
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                     //此处也可以加入对滑动速度的要求
                  && Math.abs(velocityX) > FLING_MIN_VELOCITY
                    )
            {
                // 向右滑动
            }
            return false;
        }
    }
}
