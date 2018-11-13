package es.source.code.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import es.source.code.activity.MainScreen;
import es.source.code.activity.R;
import es.source.code.application.MessageOfApplication;
import es.source.code.model.Food;
import es.source.code.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Response;


public class UpdateService extends IntentService {

    private ArrayList<Food> listFoodNew = new ArrayList<>();
    private final String URLOFLOGIN = "http://192.168.9.241:8080/SCOSServer/FoodUpdateService";


    public UpdateService(){
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        getServerMessage();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("UpdateService","onHandleIntent is executed>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Intent intent2 = new Intent(this, MainScreen.class);
//      Intent intent2 = new Intent(this, FoodDetailed.class);
//      intent2.putExtra("positionOfItem",0);
//      intent2.putParcelableArrayListExtra("foodList",listFoodNew);
//      intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder notificationBuilder = new Notification.Builder(this);
            notificationBuilder.setContentTitle("新品上架");
            notificationBuilder.setContentText(listFoodNew.get(0).getFoodName() + " " + listFoodNew.get(0).getPrice() + " ");
            notificationBuilder.setWhen(System.currentTimeMillis());
            notificationBuilder.setSmallIcon(R.drawable.logo);
            notificationBuilder.setContentIntent(pi);
            notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
            Notification notification = notificationBuilder.getNotification();
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(1, notification);
   }
   @Override
    public void onDestroy(){
       super.onDestroy();
       Log.d("UpdateService","onDestroy is executed>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
   }

    //取得服务器的消息
    public void getServerMessage(){
        useokHttp(URLOFLOGIN);
    }
    private void useokHttp(String URLOFLOGIN){
        HttpUtil.sendRequestWithOkHttpUseJSON(URLOFLOGIN,new okhttp3.Callback(){
            @Override
            public void onResponse(Call call,Response response) throws IOException{
                String responseData = response.body().string();

                long time = System.currentTimeMillis();
                Log.d("解析开始的时间",time+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                showResponseWithJSONGSON(responseData);
//                parseXmlWithPull(responseData);
                long time2 = System.currentTimeMillis();
                Log.d("解析结束的时间",time2+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                Log.d("解析花费时间的时间",(time2-time)+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }
            @Override
            public void onFailure(Call call, IOException e){
                e.printStackTrace();
                Log.d("HTTPOFService","连接失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }
        } );
    }

    //使用GSON解析数据JSON
    private void showResponseWithJSONGSON(String responseData){
        Log.d("JSON的大小",responseData.length()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Gson gson = new Gson();
        listFoodNew = gson.fromJson(responseData,new TypeToken<ArrayList<Food>>(){}.getType());
        //更新库存
        for(Food food : listFoodNew) {
            MessageOfApplication.getInstance().operateFoodList(food);
        }
    }

    //使用PULL解析XML
    private void  parseXmlWithPull(String xmlData){
        Log.d("xml的大小",xmlData.length()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Food food=null;
        boolean flag = false;
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser =factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String foodName="";
            String price="";
            String store="";
            String order="";
            String imgId="";
            String style="";
            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName= xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT: {
                        break;
                    }
                    case XmlPullParser.START_TAG:{
                        if("food".equals(nodeName)){
                            flag=true;
                            food= new Food();
                        }
                        if(flag){
                            if("foodName".equals(nodeName)){
                                food.setFoodName(xmlPullParser.nextText());
                            }else if("price".equals(nodeName)){
                                food.setPrice(Integer.valueOf(xmlPullParser.nextText()));
                            }else if("store".equals(nodeName)){
                                food.setStore(Integer.valueOf(xmlPullParser.nextText()));
                            }else if("order".equals(nodeName)){
                                food.setOrder(xmlPullParser.nextText());
                            }else if("imgId".equals(nodeName)){
                                food.setImgId(Integer.valueOf(xmlPullParser.nextText()));
                            }else if("style".equals(nodeName)){
                                food.setStyle(Integer.valueOf(xmlPullParser.nextText()));
                            }
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        if("food".equals(nodeName)&& food !=null){
                            flag = false;
                            listFoodNew.add(food);
                            MessageOfApplication.getInstance().operateFoodList(food);
                            Log.d("food节点",food.toString()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                            food= null;
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
