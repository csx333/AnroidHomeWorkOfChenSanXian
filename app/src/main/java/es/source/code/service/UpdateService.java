package es.source.code.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import java.util.ArrayList;

import es.source.code.activity.FoodDetailed;
import es.source.code.activity.R;
import es.source.code.application.MessageOfApplication;
import es.source.code.model.Food;


public class UpdateService extends IntentService {

    Food food;
    private ArrayList<Food> listFoodNew = new ArrayList<>();

    public UpdateService(){ super("UpdateService"); }

    @Override
    protected void onHandleIntent(Intent intent){
       Log.d("UpdateService","onHandleIntent is executed>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        getServerMessage();
       Intent intent2 = new Intent(this,FoodDetailed.class);

        intent2.putExtra("positionOfItem",0);
        intent2.putParcelableArrayListExtra("foodList",listFoodNew);
        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       PendingIntent pi = PendingIntent.getActivity(this,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
       Notification.Builder notificationBuilder = new Notification.Builder(this);
       notificationBuilder.setContentTitle("新品上架");
       notificationBuilder.setContentText(listFoodNew.get(0).getFoodName()+" "+listFoodNew.get(0).getPrice()+" ");
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setSmallIcon(listFoodNew.get(0).getImgId());
//        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(),listFoodNew.get(0).getImgId()));
        notificationBuilder.setContentIntent(pi);
        Notification notification = notificationBuilder.getNotification();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1,notification);

   }
   @Override
    public void onDestroy(){
       super.onDestroy();
       Log.d("UpdateService","onDestroy is executed>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
   }

    //模拟取得服务器的消息
    public void getServerMessage(){
        Food food = new Food("油淋莴苣",25,"点餐",R.drawable.cool_chongyouwuju,MessageOfApplication.COOLFOOD,200);
        MessageOfApplication.getInstance().operateFoodList(food);
        listFoodNew.add(food);
    }
}
