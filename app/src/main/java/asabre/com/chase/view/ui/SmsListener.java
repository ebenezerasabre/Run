package asabre.com.chase.view.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsListener extends BroadcastReceiver {
    private static final String TAG = SmsListener.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: called");
        if(Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())){
            for(SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)){
                String messageBody = smsMessage.getMessageBody();
                Log.d(TAG, "onReceive: msg body " + messageBody);
            }
        }
    }




}
