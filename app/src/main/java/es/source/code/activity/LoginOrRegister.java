package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import es.source.code.model.User;

public class LoginOrRegister extends Activity implements View.OnClickListener{
    //布局内的控件
    private EditText et_name;
    private EditText et_password;
    private Button mLoginBtn;
    private Button mRegisterBtn;
    private Button mReturnBtn;
    private CheckBox checkBox_password;
    private CheckBox checkBox_login;
    private ImageView iv_see_password;
    private ProgressBar progressBar;
    private User loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        initViews();
        setupEvents();
      //  initData();
    }

    private void initViews() {
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mReturnBtn = (Button) findViewById(R.id.btn_return);
        mRegisterBtn=(Button) findViewById(R.id.btn_register);
        progressBar = (ProgressBar)findViewById(R.id.pb) ;
        et_name = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        checkBox_password = (CheckBox) findViewById(R.id.checkBox_password);
        checkBox_login = (CheckBox) findViewById(R.id.checkBox_login);
        iv_see_password = (ImageView) findViewById(R.id.iv_see_password);
    }

    private void setupEvents() {
        mLoginBtn.setOnClickListener(this);
        mReturnBtn.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
       // checkBox_password.setOnCheckedChangeListener(this);
       // checkBox_login.setOnCheckedChangeListener(this);
        iv_see_password.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        loginUser = new User();
        switch (v.getId()) {
            //点击登陆按钮
            case R.id.btn_login:
                loadProgressBar();
                //倒计时
                new CountDownTimer(2000,1000){
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        loadProgressBar();
                        if(checkUserName()){
                            if(checkUserPassword()){
                                loginUser.setUserName(getAccount());
                                loginUser.setPassword(getPassword());
                                loginUser.setOldUser(true);
                                sendOlderUser(loginUser);
                            }else{setErrorOf(et_password);}
                        }else {setErrorOf(et_name);}
                    }
                }.start();
                break;
            //点击注册
            case R.id.btn_register:
                loadProgressBar();
                //倒计时
                new CountDownTimer(2000,1000){
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        loadProgressBar();
                        if(checkUserName()){
                            if(checkUserPassword()){
                                loginUser.setUserName(getAccount());
                                loginUser.setPassword(getPassword());
                                loginUser.setOldUser(false);
                                sendNewUser(loginUser);
                            }else{setErrorOf(et_password);}
                        }else {setErrorOf(et_name);}
                    }
                }.start();
                break;
            //点击返回按键
            case R.id.btn_return:
                backMainScreen();
                break;
            //点击密码是否可见
            case R.id.iv_see_password:
                setPasswordVisibility();    //改变图片并设置输入框的文本可见或不可见
                break;
        }
    }

    @Override
    public void onBackPressed(){
        backMainScreen();
    }
    /**
     * 加载进度条
     */
    public void loadProgressBar(){
        if (progressBar.getVisibility() == View.GONE) {
            progressBar.setVisibility(View.VISIBLE);}
        else progressBar.setVisibility(View.GONE);
    }
    /**
     * 验证账号
     */
    public Boolean checkUserName() {
       return getAccount().matches("^[a-zA-Z0-9]{6,16}$");
    }
    /**
     * 验证密码
     */
    public Boolean checkUserPassword() {
        return getPassword().matches("^[a-zA-Z0-9]{6,16}$");
    }

    /**
     * 传递老用户
     */
    private void sendOlderUser(User loginUser){
        Intent intent_login = new Intent(LoginOrRegister.this, MainScreen.class);
        intent_login.putExtra("Log","LoginSuccess");
        intent_login.putExtra("userData",loginUser);
        startActivity(intent_login);
        finish();
    }
    /**
     * 传递新用户
     */
    private void sendNewUser(User loginUser){
        Intent intentReg = new Intent(LoginOrRegister.this, MainScreen.class);
        intentReg.putExtra("Log","RegisterSuccess");
        intentReg.putExtra("userData",loginUser);
        startActivity(intentReg);
//        Toast.makeText(getApplicationContext(),"RegSucc",Toast.LENGTH_LONG).show();
        finish();
    }

    /**
     * 弹出输入内容不符合规则
     */
    private void setErrorOf(EditText t){
        t.setError("输入内容不符合规则");
    }

    /**
     * 获取账号
     */
    public String getAccount() {
        return et_name.getText().toString();
    }

    /**
     * 获取密码
     */
    public String getPassword() {
        return et_password.getText().toString();
    }
    /**
     * 设置密码可见和不可见的相互转换
     */
    private void setPasswordVisibility() {
        if (iv_see_password.isSelected()) {
            iv_see_password.setSelected(false);
            //密码不可见
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        } else {
            iv_see_password.setSelected(true);
            //密码可见
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }
    /**
     * 返回mainscreen
     */
    private  void backMainScreen(){
        Intent intent_return = new Intent(LoginOrRegister.this, MainScreen.class);
        intent_return.putExtra("Log","Return");
        startActivity(intent_return);
        Toast.makeText(getApplicationContext(),"Return",Toast.LENGTH_LONG).show();
        finish();
    }

}
