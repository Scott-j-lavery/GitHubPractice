package services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by scott on 9/17/15.
 */
public class RSSPullService extends IntentService {

    public RSSPullService() {
        super("RSSPullService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String dataString = intent.getDataString();
    }
}
