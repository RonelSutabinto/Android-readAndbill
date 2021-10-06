package com.androidapp.mytools.bluetooth;

import android.preference.ListPreference;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import java.util.Set;

public class BluetoothDevicePreference extends ListPreference {
	  public BluetoothDevicePreference(Context context, AttributeSet attrs) {
	        super(context, attrs);
	        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
	        CharSequence[] entries = new CharSequence[pairedDevices.size()];
	        CharSequence[] entryValues = new CharSequence[pairedDevices.size()];
	        int i = 0;
	        for (BluetoothDevice dev : pairedDevices) {
	            entries[i] = dev.getName();
	            entryValues[i] = dev.getAddress();
	            i++;
	        }
	        setEntries(entries);
	        setEntryValues(entryValues);
	    }

	    public BluetoothDevicePreference(Context context) {
	        this(context, null);
	    }
}
