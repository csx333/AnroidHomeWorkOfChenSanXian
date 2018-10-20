package es.source.code.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import es.source.code.adapter.MyOrderFragmentPagerAdapter;

public class FoodOrderView extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyOrderFragmentPagerAdapter myOrderFragmentPagerAdapter;
    private TabLayout.Tab one;
    private TabLayout.Tab two;

    @Override
    public void onBackPressed(){
        backFoodView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order_view);

        //初始化视图
        initViews();
        int id = getIntent().getIntExtra("idOfFoodView", 0);
        if(id==1) {
            mViewPager.setCurrentItem(1);//
        }
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

    private  void  backFoodView(){
        Intent intent_return = new Intent(FoodOrderView.this, FoodView.class);
        startActivity(intent_return);
        finish();
    }
}
