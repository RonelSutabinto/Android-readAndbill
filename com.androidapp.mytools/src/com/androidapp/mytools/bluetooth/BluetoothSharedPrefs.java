package com.androidapp.mytools.bluetooth;
import android.content.Context;
import android.content.SharedPreferences.Editor;

public class BluetoothSharedPrefs {
	public static final String PREFS_NAME = "readandbill_prefs";

    public static String getDeviceName(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getString("bluetooth_name", null);
    }

    public static void setDeviceName(Context context, String newValue) {
        Editor prefEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefEditor.putString("bluetooth_name", newValue);
        prefEditor.commit();
    }

    public static String getMacAddress(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getString("bluetooth_mac", null);
    }

    public static void setMacAddress(Context context, String newValue) {
        Editor prefsEditior = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefsEditior.putString("bluetooth_mac", newValue);
        prefsEditior.commit();
    }

    public static boolean getBluetoothAlwaysOn(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getBoolean("bluetooth_always_on", false);
    }

    public static void setBluetoothAlwaysOn(Context context, boolean newValue) {
        Editor prefsEditior = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefsEditior.putBoolean("bluetooth_always_on", newValue);
        prefsEditior.commit();
    }
}
