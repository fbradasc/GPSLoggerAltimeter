/**
 * ActionsBroadcastReceiver - Java Class for Android
 * Created by G.Capelli (BasicAirData) on 25/9/2020
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

package org.fbradasc.trekking.walklogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ActionsBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        //Log.w("myApp", "[#] EVENT");
        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_OFF:
                // Turns off the EventBus Signals of the Recording Thread
                // in order to save Battery
                GPSApplication.getInstance().onScreenOff();
                break;
            case Intent.ACTION_SCREEN_ON:
                // Turns on the EventBus Signals of the Recording Thread
                GPSApplication.getInstance().onScreenOn();
                break;
            case Intent.ACTION_SHUTDOWN:
                // Gracefully finish to write data and close the Database
                GPSApplication.getInstance().onShutdown();
                break;
        }
    }
}