package es.source.code.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.os.Handler;
import android.util.Log;

public class ServerObserverService extends Service {
    public boolean isRunning =false;
    MessageThread mt;
    private int data;
    private Handler cMessageHandler = new CMessageHandler();
    public Messenger sMessenger = new Messenger(cMessageHandler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sMessenger.getBinder();
    }

    private class CMessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Messenger cMessenger = msg.replyTo;
                    Message replyMessage = Message.obtain(null, 10);

                    isRunning = true;
                    mt = new MessageThread(cMessenger,replyMessage);
                    mt.start();
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    //从服务器获取消息线程
    class MessageThread extends Thread{
        Messenger cMessenger;
        Message replyMessage;
        MessageThread(Messenger cMessenger,Message replyMessage){
            this.cMessenger=cMessenger;
            this.replyMessage=replyMessage;
        }
        @Override
        public void run(){
            while (isRunning){
                //模拟接收服务器传回的信息
                getServerMessage();
                Log.e("getServerMessage",">>>>>>>>>>>>>>>>>>>>>>>>>>"+data+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                Bundle bundle = new Bundle();
                bundle.putString("reply", data + "");
                replyMessage.setData(bundle);
                if (replyMessage == null) {
                    Log.e("replyMessage", ">>>>>>>>>>>>>>>>>>>>>>>>>>null>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                }
                if (cMessenger == null) {
                    Log.e("cMessenger", ">>>>>>>>>>>>>>>>>>>>>>>>>>null>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                }
                try {
                    cMessenger.send(replyMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try{
                    Thread.sleep(30000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    //模拟取得服务器的消息
    public void getServerMessage(){
        data =(int) (Math.random() * 100);
    }
}
