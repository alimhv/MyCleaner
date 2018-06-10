package co.raac.cleaner;
import co.raac.cleaner.model.CleanerService;
import co.raac.cleaner.model.AppsListItem;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends Activity implements CleanerService.OnActionListener {
    private CleanerService mCleanerService;
    private static final String TAG = "MainActivityLog";
    private Method mGetPackageSizeInfoMethod;
    private Method mFreeStorageAndNotifyMethod;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            mCleanerService = ((CleanerService.CleanerServiceBinder) service).getService();
            mCleanerService.setOnActionListener(MainActivity.this);

            if (!mCleanerService.isCleaning() && !mCleanerService.isScanning()) {
//                if (mSharedPreferences.getBoolean(mCleanOnAppStartupKey, false) &&
//                        !mAlreadyCleaned) {
//                    mAlreadyCleaned = true;
//
//                    cleanCache();
//                } else if (!mAlreadyScanned) {
//                    // mCleanerService.scanCache();
//                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceConnected: ");
            mCleanerService.setOnActionListener(null);
            mCleanerService = null;
        }
    };
    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         bindService(new Intent(this, CleanerService.class),
                mServiceConnection, Context.BIND_AUTO_CREATE);
//        sendSMS("+989396675507","jiii");
        //mServiceConnection.notify();
        //mCleanerService.onCreate();
    }
    public void scanCache(View target) {
        mCleanerService.scanCache();
        // Do stuff
    }
    public void cleanCache(View target) {
        mCleanerService.cleanCache();
        // Do stuff
    }
    @Override
    public void onScanStarted(Context context) {
        Log.d(TAG, "onScanStarted: ");
//        if (isAdded()) {
//            if (mProgressDialog.isShowing()) {
//                mProgressDialog.dismiss();
//            }
//            mProgressBarText.setText(R.string.scanning);
//            showProgressBar(true);
//        }
    }

    @Override
    public void onScanProgressUpdated(Context context, int current, int max) {
        Log.d(TAG, "onScanProgressUpdated: ");
//        if (isAdded()) {
//            mpknameText.setText(CleanerService.currentPK);
//            mpknameIcon.setImageDrawable(CleanerService.appicon);
//            mProgressBarText.setText(getString(R.string.scanning_m_of_n, current, max));
//        }
    }

    @Override
    public void onScanCompleted(Context context, List<AppsListItem> apps) {
        Log.d(TAG, "onScanCompleted: ");
    }

    @Override
    public void onCleanStarted(Context context) {
        Log.d(TAG, "onCleanStarted: ");
    }

    @Override
    public void onCleanCompleted(Context context, boolean succeeded) {
        Log.d(TAG, "onCleanCompleted: ");
    }
}
