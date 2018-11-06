package es.source.code.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import es.source.code.adapter.FoodViewFragmentPagerAdapter;
import es.source.code.application.MessageOfApplication;
import es.source.code.service.ServerObserverService;
import es.source.code.fragment.FoodFragment;

public class FoodView extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FoodViewFragmentPagerAdapter foodViewFragmentPagerAdapter;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;

    private Messenger mService;
    private  int flag =1;

    private  static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    Bundle bundle = msg.getData();
                    String str = bundle.getString("reply");
                    int i=Integer.parseInt(str);
                    Log.e("从service接受的值", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +i);
                    MessageOfApplication.getInstance().operateFoodList("葱淋莴苣", i);
                    upDate();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            messageToService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

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
            case R.id.action_refresh:
                item.setTitle(MessageOfApplication.getInstance().fresh);
                if(MessageOfApplication.getInstance().fresh.equals("启动实时更新")){
                    flag =1;
                    item.setTitle("关闭实时更新");
                    MessageOfApplication.getInstance().fresh="关闭实时更新";
                    Intent intentService = new Intent(this, ServerObserverService.class);
                    bindService(intentService, mConnection, Context.BIND_AUTO_CREATE);
                }else{
                    flag = 0;
                    item.setTitle("启动实时更新");
                    MessageOfApplication.getInstance().fresh="启动实时更新";
                    messageToService();
                    unbindService(mConnection);
                }

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

    private static void upDate() {
        FoodFragment.mFoodViewRecyclerAdapter.notifyDataSetChanged();
        Log.e("更新Fragment", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" );
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
    private void  messageToService(){
        Message msg;
        if(flag == 1){ msg = Message.obtain(null,1,0,0);}
        else { msg = Message.obtain(null,0,0,0);}
        msg.replyTo = mGetReplyMessenger;
        try {
            mService.send(msg);
            Log.e("mService", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+msg.what);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
