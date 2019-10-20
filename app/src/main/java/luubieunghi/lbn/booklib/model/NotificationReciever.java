package luubieunghi.lbn.booklib.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import luubieunghi.lbn.booklib.R;

public class NotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int id=intent.getIntExtra("control_id",-1);
        switch (id){
            case R.id.btn_close_notification:
            {
                Toast.makeText(context,"Close", Toast.LENGTH_LONG).show();
                Intent stopIntent=new Intent(context,MyService.class);
                stopIntent.setAction("Action_Stop");
                context.startService(stopIntent);
                break;
            }
            case R.id.btn_play_notification:{
                Toast.makeText(context,"Play", Toast.LENGTH_LONG).show();
                Intent stopIntent=new Intent(context,MyService.class);
                stopIntent.setAction("Action_Play");
                context.startService(stopIntent);
                break;
            }
        }
    }
}
