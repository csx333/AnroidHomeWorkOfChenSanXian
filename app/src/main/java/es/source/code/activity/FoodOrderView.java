package es.source.code.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import es.source.code.adapter.MyOrderFragmentPagerAdapter;
import es.source.code.fragment.notOrderedFragment;
import es.source.code.model.Food;

public class FoodOrderView extends AppCompatActivity {

    public ArrayList<Food> foodsOrderList = new ArrayList<>();
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyOrderFragmentPagerAdapter myOrderFragmentPagerAdapter;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TextView number;
    private TextView price;
    private TextView textButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fromFoodView();
       // getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.food_order_view);
        //初始化视图
        initViews();
        foodsOrderList = MessageOfApplication.getInstance().getOrderFoodList();
        int i=0;
        textButton=(TextView) findViewById(R.id.btn_submit);
        price=(TextView) findViewById(R.id.order_price);
        number=(TextView)findViewById(R.id.order_number);

        number.setText("已点数量："+ foodsOrderList.size());
        for(Food food:foodsOrderList){
            i=i+food.getPrice();
        }
        price.setText("总价："+i);

    }
    private void initViews() {

        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager) findViewById(R.id.order_viewpager);
        myOrderFragmentPagerAdapter = new MyOrderFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myOrderFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.order_tablayout);
        mTabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
    }

    private void fromFoodView(){
        int id = getIntent().getIntExtra("id", 0);
        if (id == 1) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.order_viewpager,new notOrderedFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
