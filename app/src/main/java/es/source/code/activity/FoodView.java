package es.source.code.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import es.source.code.adapter.FoodViewFragmentPagerAdapter;

public class FoodView extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FoodViewFragmentPagerAdapter foodViewFragmentPagerAdapter;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);

        //初始化视图
        initViews();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_food_view, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent= new Intent();
        switch (item.getItemId()) {
            case R.id.action_order:
                intent.setClass(FoodView.this,FoodOrderView.class);
                startActivity(intent);
                break;
            case R.id.action_bill:
                intent.setClass(FoodView.this,FoodOrderView.class);
                intent.putExtra("idOfFoodView",1);
                startActivity(intent);
                break;
            case R.id.action_service:
                Toast.makeText(this, "service", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager = (ViewPager) findViewById(R.id.home_viewpager);
        foodViewFragmentPagerAdapter = new FoodViewFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(foodViewFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.home_tablayout);
        mTabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        four = mTabLayout.getTabAt(3);
    }

}
