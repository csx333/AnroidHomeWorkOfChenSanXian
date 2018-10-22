package es.source.code.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * 缓冲信息存储
 * @author SanXian Chen
 *
 */
public class SharedPreferencesUtils {
    SharedPreferences sharedPreferences;
    Editor editor;
    public SharedPreferencesUtils(Context context,String fileName){
        sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    /**
     * 将数据保存在缓冲中
     * @param key
     * @param obj
     */
    public  void save(String key,Object obj){
        //Context.MODE_PRIVATE为默认操作模式，代表该文件是私有数据，只能被应用本身访问，在该模式下，写入的内容会覆盖原文件的内容，
        //如果想把新写入的内容追加到原文件中。可以使用Context.MODE_APPEND
        if(obj instanceof String){
            editor.putString(key, (String) obj);
        }else if(obj instanceof Boolean){
            editor.putBoolean(key, (Boolean) obj);
        }
        editor.commit();//保存数据到缓冲
    }

    //获取数据的方法
    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public boolean getBoolean(String key, Boolean b) {
        return sharedPreferences.getBoolean(key, b);
    }

    //清除当前文件的所有的数据
    public void clear() {
        sharedPreferences.edit().clear().commit();
    }

}
