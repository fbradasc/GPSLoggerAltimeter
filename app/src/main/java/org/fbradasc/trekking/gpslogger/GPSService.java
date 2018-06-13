/**
 * GPSService - Java Class for Android
 * Created by G.Capelli (BasicAirData) on 2/11/2016
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.fbradasc.trekking.gpslogger;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityRecognitionResult;
// import com.google.android.gms.location.DetectedActivity;

import org.greenrobot.eventbus.EventBus;

public class GPSService extends Service {
    // Singleton instance
    private static GPSService singleton;
    PendingIntent activityRecognitionPendingIntent = null;
//    private long _UserStillSinceTimeStamp = 0;

    public static GPSService getInstance(){
        return singleton;
    }
    // IBinder
    private final IBinder mBinder = new LocalBinder();
    public class LocalBinder extends Binder {                                   //returns the instance of the service
        public GPSService getServiceInstance(){
            return GPSService.this;
        }
    }

    private Notification getNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //builder.setSmallIcon(R.drawable.ic_notification_24dp)
        builder.setSmallIcon(R.mipmap.ic_notify_24dp)
                .setContentTitle(getString(R.string.app_name))
                .setShowWhen(false)
                .setContentText(getString(R.string.notification_contenttext));

        final Intent startIntent = new Intent(getApplicationContext(), GPSActivity.class);
        startIntent.setAction(Intent.ACTION_MAIN);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        //startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 1, startIntent, 0);
        builder.setContentIntent(contentIntent);
        return builder.build();
    }

    /* THREAD FOR DEBUG PURPOSE
    Thread t = new Thread() {
        public void run() {
            boolean i = true;
            while (i) {
                try {
                    sleep(1000);
                    Log.w("myApp", "[#] GPSService.java - ** RUNNING **");
                } catch (InterruptedException e) {
                    i = false;
                }
            }
        }
    }; */

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        // THREAD FOR DEBUG PURPOSE
        //if (!t.isAlive()) {
        //    t.start();
        //}
        Log.w("myApp", "[#] GPSService.java - CREATE = onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w("myApp", "[#] GPSService.java - START = onStartCommand");
        if (!handleIntent(intent)) {
            startForeground(1, getNotification());
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.w("myApp", "[#] GPSService.java - BIND = onBind");
        requestActivityRecognitionUpdates();
        return mBinder;
        //return null;
    }

    @Override
    public void onDestroy() {
        Log.w("myApp", "[#] GPSService.java - DESTROY = onDestroy");
        stopActivityRecognitionUpdates();
        // THREAD FOR DEBUG PURPOSE
        //if (t.isAlive()) t.interrupt();
        super.onDestroy();
    }

    private void requestActivityRecognitionUpdates() {
        if (activityRecognitionPendingIntent == null) {
            Log.d("myApp", "Requesting activity recognition updates");
            Intent intent = new Intent(getApplicationContext(), GPSService.class);
            activityRecognitionPendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            ActivityRecognitionClient arClient = ActivityRecognition.getClient(getApplicationContext());
            arClient.requestActivityUpdates(/*TODO:*/ 60 * 1000, activityRecognitionPendingIntent);
        }
    }

    private void stopActivityRecognitionUpdates(){
        try{
            if (activityRecognitionPendingIntent != null){
                Log.d("myApp", "Stopping activity recognition updates");
                ActivityRecognitionClient arClient = ActivityRecognition.getClient(getApplicationContext());
                arClient.removeActivityUpdates(activityRecognitionPendingIntent);
                activityRecognitionPendingIntent = null;
            }
        } catch (Exception ex){
            Log.e("myApp", "Could not stop activity recognition service", ex);
        }
    }

    private boolean handleIntent(Intent intent)
    {
        Log.d("myApp", "handleIntent");
        ActivityRecognitionResult arr = ActivityRecognitionResult.extractResult(intent);
        if(arr != null){
            Log.d("myApp", "handleIntent: Activity: " + arr.getMostProbableActivity().toString());

            EventBus.getDefault().post(new EventBusMSGNormal(EventBusMSG.ACTIVITY_DETECTED, arr.getMostProbableActivity().getType()));
/*
            switch (arr.getMostProbableActivity().getType()) {
                case DetectedActivity.STILL:
                case DetectedActivity.IN_VEHICLE:
                case DetectedActivity.TILTING:
                    // case DetectedActivity.UNKNOWN:
                    if(_UserStillSinceTimeStamp == 0){
                        Log.d("myApp", "handleIntent: Just entered still state, attempt to log");
                        EventBus.getDefault().post(EventBusMSG.GPS_RESUME);
                        _UserStillSinceTimeStamp = System.currentTimeMillis();
                    } else {
                        Log.d("myApp", "handleIntent: Not walking stop logging");
                        EventBus.getDefault().post(EventBusMSG.GPS_PAUSE);
                    }
                    break;

                case DetectedActivity.UNKNOWN:
                case DetectedActivity.ON_BICYCLE:
                case DetectedActivity.ON_FOOT:
                case DetectedActivity.WALKING:
                case DetectedActivity.RUNNING:
                default:
                    //Reset the still-since timestamp
                    _UserStillSinceTimeStamp = 0;
                    Log.d("myApp", "handleIntent: Just exited still state, attempt to log");
                    EventBus.getDefault().post(EventBusMSG.GPS_RESUME);
                    break;
            }
*/
            return true;
        }

        return false;
    }
}
