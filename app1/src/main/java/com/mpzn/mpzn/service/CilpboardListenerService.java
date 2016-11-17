package com.mpzn.mpzn.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CilpboardListenerService extends Service {
    public CilpboardListenerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
