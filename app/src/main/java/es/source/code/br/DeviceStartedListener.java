package es.source.code.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import es.source.code.service.UpdateService;

public class DeviceStartedListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DeviceStartedListener","DeviceStartedListener is executed>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Intent intentService= new Intent(context,UpdateService.class);
        context.startService(intentService);

    }
}
