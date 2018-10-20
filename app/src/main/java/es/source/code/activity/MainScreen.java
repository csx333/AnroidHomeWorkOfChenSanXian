package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import es.source.code.adapter.MainScreenAdapter;
import es.source.code.application.MessageOfApplication;
import es.source.code.model.User;
import es.source.code.model.IconOfMainScreen;

public class MainScreen extends Activity {

    private ArrayList<IconOfMainScreen> iconList = new ArrayList<>();
    private MainScreenAdapter adapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        String messageEntry = getIntent().getStringExtra("entry");
        String messageLog = getIntent().getStringExtra("Log");
        if(messageEntry ==null)messageEntry = "";
        if(messageLog == null)messageLog = "";
        if (messageLog.equals("Return") || messageEntry.equals("FromEntry")) {
            iconList.add(new IconOfMainScreen(R.drawable.ic_account_green, "登陆/注册"));
            iconList.add(new IconOfMainScreen(R.drawable.ic_help_green, "帮助"));
        } else {
            iconList.add(new IconOfMainScreen(R.drawable.ic_account_green, "登陆/注册"));
            iconList.add(new IconOfMainScreen(R.drawable.ic_help_green, "帮助"));
            iconList.add(new IconOfMainScreen(R.drawable.ic_order_green, "订餐"));
            iconList.add(new IconOfMainScreen(R.drawable.ic_form_green, "订单"));
            switch (messageLog){
                case "LoginSuccess":
                    user =(User)getIntent().getParcelableExtra("userData");
                    //将user存储到app
                    MessageOfApplication.getInstance().setUser(user);
                    break;
                case "RegisterSuccess":
                    user =(User)getIntent().getParcelableExtra("userData");
                    //将user存储到app
                    MessageOfApplication.getInstance().setUser(user);
                    Toast.makeText(getApplicationContext(),user.getUserName()+"，"
                            + "欢迎您成为SCOS新用户",Toast.LENGTH_LONG).show();
                    break;
                default:user =null;break;
            }
        }

        MainScreenAdapter adapter = new MainScreenAdapter(MainScreen.this,
                R.layout.item_gv_main_screen,iconList);
        GridView gridView = (GridView)findViewById(R.id.gv_icon);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IconOfMainScreen icon = iconList.get(position);
                Intent intent;
                switch (icon.getIName()){
                    case "登陆/注册":
                        intent =new Intent(MainScreen.this, LoginOrRegister.class);
                        startActivity(intent);
                        break;
                    case "订餐":
                        intent =new Intent(MainScreen.this, FoodView.class);
                        startActivity(intent);
                        break;
                    case "订单":
                        break;
                    case "帮助":
                        break;
                }
            }
        });
/*
//      Button buttonOrder = (Button)findViewById(R.id.button_order);
        Button buttonOrderForm = (Button)findViewById(R.id.button_order_form);
        Button buttonAccount = (Button)findViewById(R.id.button_account);
        Button buttonHelp = (Button)findViewById(R.id.button_help);
        buttonAccount.setOnClickListener(this);

        //接受消息并进行相应处理
        Intent intent = getIntent();
        String messageEntry = intent.getStringExtra("entry");
        if( MessageOfApplication.messOfLogin .equals("Return")|| messageEntry.equals("FromEntry")){
            buttonOrder.setVisibility(View.INVISIBLE);
            buttonOrderForm.setVisibility(View.INVISIBLE);
        }
 */
    }
}
