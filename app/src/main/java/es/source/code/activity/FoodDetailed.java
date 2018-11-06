package es.source.code.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.logging.Logger;
import es.source.code.application.MessageOfApplication;
import es.source.code.model.Food;

public class FoodDetailed extends AppCompatActivity {
    private GestureDetector gestureDetector; //手势检测
    private GestureDetector.OnGestureListener onSlideGestureListener = null;//左右滑动手势检测监听器
    public ImageView mItemFoodsImg;
    public TextView mItemFoodsName;
    public TextView mItemFoodsPrice;
    public TextView mItemFoodsOrder;
    private int positionOfItem;
    private ArrayList<Food> listFood = new ArrayList<>();
    private Food food =new Food();
    private Logger log = Logger.getLogger("FoodDetailed");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail);

        //定义左右滑动手势监听器,将监听器传入手势识别器
        onSlideGestureListener = new FoodDetailed.OnSlideGestureListener();
        gestureDetector = new GestureDetector(this, onSlideGestureListener);
        fromFoodFragment();
        intData();
        food = getMoveFood(positionOfItem);
        mItemFoodsOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemFoodsOrder.getText().toString().equals("点餐")) {
                    MessageOfApplication.getInstance().operateFoodOrderList(food,true);
                    Toast.makeText(v.getContext(), "点菜成功", Toast.LENGTH_SHORT).show();
                    food.setOrder("退点");
                    refesh();
                }else{
                    MessageOfApplication.getInstance().operateFoodOrderList(food,false);
                    food.setOrder("点餐");
                    refesh();
                }
            }
        });
    }

    //将touch动作事件交由手势检测监听器来处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void intData(){
        mItemFoodsImg = (ImageView)findViewById(R.id.iv_food_image_detail);
        mItemFoodsName = (TextView)findViewById(R.id.tv_food_name_detail);
        mItemFoodsPrice = (TextView)findViewById(R.id.tv_food_price_detail);
        mItemFoodsOrder = (TextView)findViewById(R.id.btn_order_detail);
    }

    private void refesh() {
        finish();
        Intent intent = new Intent(FoodDetailed.this, FoodDetailed.class);
        intent.putExtra("positionOfItem", positionOfItem);
        intent.putParcelableArrayListExtra("foodList",listFood);
        startActivity(intent);
    }
    private void fromFoodFragment() {
        listFood = getIntent().getParcelableArrayListExtra("foodList");
        Log.d("listFood",listFood.size()+"");
        positionOfItem = getIntent().getIntExtra("positionOfItem", 0);
        Log.d("positionOfItem",positionOfItem+"");
    }

    private Food getMoveFood(int positionOfItem){
        if(positionOfItem>=listFood.size() || positionOfItem<0){
            positionOfItem=Math.abs(positionOfItem%listFood.size());
        }
        Food foodTemp = listFood.get(positionOfItem);
        mItemFoodsImg.setImageResource(foodTemp.getImgId());
        mItemFoodsName.setText(foodTemp.getFoodName());//获取实体类中的name字段并设置
        mItemFoodsPrice.setText(String.valueOf(foodTemp.getPrice()));//获取实体类中的price字段并设置
        mItemFoodsOrder.setText(foodTemp.Order());
        return foodTemp;
    }

    private class OnSlideGestureListener implements GestureDetector.OnGestureListener
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
                food =getMoveFood(positionOfItem++);
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
                food =getMoveFood(positionOfItem--);
                // 向右滑动
            }
            return false;
        }
    }
}
