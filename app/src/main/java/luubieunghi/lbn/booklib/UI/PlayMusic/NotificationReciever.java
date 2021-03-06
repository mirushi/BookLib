package luubieunghi.lbn.booklib.UI.PlayMusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import luubieunghi.lbn.booklib.R;


public class NotificationReciever extends BroadcastReceiver {
    boolean isMusic;
    @Override
    public void onReceive(Context context, Intent intent) {

        // lấy id của control để start service theo từng control
        int id=intent.getIntExtra("control_id",-1);
        isMusic=intent.getBooleanExtra("isMusic", false);
        switch (id){
            case R.id.btn_close_notification:
            {
                xuLiBtnCloseClicked(context, intent);
                break;
            }
            case R.id.btn_play_notification:{
                xuLiBtnPlayClicked(context,intent);
                break;
            }
            case R.id.btn_next_notification:{
                xuLiBtnNextClicked(context,intent);
                break;
            }
            case R.id.btn_previous_notification:{
                xuLiBtnPreviousClicked(context,intent);
                break;
            }
            default:
                break;
        }
    }

    private void xuLiBtnPreviousClicked(Context context, Intent intent) {
    }

    private void xuLiBtnNextClicked(Context context, Intent intent) {
    }

    private void xuLiBtnPlayClicked(Context context, Intent intent) {
        Intent stopIntent=new Intent(context,MyService.class);
        stopIntent.setAction("Action_Play");
        stopIntent.putExtra("isMusic",true );
        context.startService(stopIntent);
    }

    private void xuLiBtnCloseClicked(Context context, Intent intent) {
        Intent stopIntent=new Intent(context, MyService.class);
        stopIntent.setAction("Action_Stop");
        stopIntent.putExtra("isMusic",true);
        context.startService(stopIntent);
    }
}
