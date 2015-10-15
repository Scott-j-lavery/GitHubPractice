package receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by scott on 9/15/15.
 */
public class SampleReceiver extends BroadcastReceiver {

    public final class Constants {

        public static final String BROADCAST_ACTION = "com.example.scott.freestyle.BROADCAST_ACTION";
        public static final String EXTRA_KEY = "com.example.scott.freestyle.EXTRA_KEY";
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
