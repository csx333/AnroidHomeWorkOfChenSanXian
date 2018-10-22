package es.source.code.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.List;

import es.source.code.adapter.GridOfIconAdapter;
import es.source.code.model.IconOfSCOS;
import es.source.code.util.MailManagerUtil;

import android.Manifest;
import android.widget.Toast;
import android.telephony.SmsManager;

public class SCOSHelper extends AppCompatActivity {

    private ArrayList<IconOfSCOS> iconList = new ArrayList<>();
    private GridOfIconAdapter adapter;
    //处理返回的发送状态
    private final String SENT_SMS_ACTION = "SENT_SMS_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoshelper);

        iconList.add(new IconOfSCOS(R.drawable.icon_file, "使用协议"));
        iconList.add(new IconOfSCOS(R.drawable.icon_settings, "关于系统"));
        iconList.add(new IconOfSCOS(R.drawable.icon_phone, "电话帮助"));
        iconList.add(new IconOfSCOS(R.drawable.icon_msg, "短信帮助"));
        iconList.add(new IconOfSCOS(R.drawable.icon_email, "邮件帮助"));
        iconList.add(new IconOfSCOS(R.drawable.icon_fava, "给与好评"));

        GridOfIconAdapter adapter = new GridOfIconAdapter(SCOSHelper.this,
                R.layout.item_gv_screen, iconList);
        GridView gridView = (GridView)findViewById(R.id.gv_help_icon);
        gridView.setAdapter(adapter);

        this.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(SCOSHelper.this ,  "短信发送成功", Toast.LENGTH_SHORT)  .show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IconOfSCOS icon = iconList.get(position);
                Intent intent;
                switch (icon.getIName()){
                    case "使用协议":
                        break;
                    case "关于系统":
                        break;
                    case "电话帮助":
                        getPermissionToCall();
                        break;
                    case "短信帮助":
                        getPermissionToSendMsg();
                        break;
                    case "邮件帮助" :
                        sendEMail();
                        break;
                    case "给与好评" :
                        break;
                }
            }
        });
    }

    private  void getPermissionToCall(){
        if(ContextCompat.checkSelfPermission(SCOSHelper.this, Manifest.permission.CALL_PHONE) !=  PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SCOSHelper.this,new String[]{Manifest.permission.CALL_PHONE},1);
        }else{
            call("5554");
        }
    }

    private  void getPermissionToSendMsg(){
        if(ContextCompat.checkSelfPermission(SCOSHelper.this, Manifest.permission.SEND_SMS) !=  PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SCOSHelper.this,new String[]{Manifest.permission.SEND_SMS},2);
        }else{
            sendMsg("5554","error:5504");
        }
    }

    private void call(String phone){
        try{
            Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));
            startActivity(intent);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private void sendMsg(String phoneNumber,String message){
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent,  0);
        // 获取短信管理器
        SmsManager smsm = SmsManager.getDefault();
        ArrayList<String> divideContents = smsm.divideMessage(message);
        ArrayList<PendingIntent> pendingIntents = new ArrayList<>();
        pendingIntents.add(sentPI);
        smsm.sendMultipartTextMessage(phoneNumber, null, divideContents, pendingIntents, null);
    }

    private void sendEMail(){
        MailManagerUtil.getInstance().sendMail("SCOS need help","please help me!!");
       Toast.makeText(this,"求助邮件发送成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call("5554");
                }else {
                    Toast.makeText(this,"你应该同意！！！！",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if(grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    sendMsg("5554","error:5504");
                }else {
                    Toast.makeText(this,"你应该同意！！！！",Toast.LENGTH_SHORT).show();
                }
                default:
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}
