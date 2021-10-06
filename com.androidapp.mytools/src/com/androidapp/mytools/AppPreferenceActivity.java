package com.androidapp.mytools;
import android.preference.PreferenceActivity;
import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
//import com.androidapp.mytools.bluetooth.BluetoothSharedPrefs;
import com.androidapp.mytools.*;
import com.androidapp.mytools.bluetooth.BluetoothSharedPrefs;
public class AppPreferenceActivity extends PreferenceActivity {
	private CheckBoxPreference checkBoxPreference;
    private Context context;
    private ListPreference listPreference;
    private PreferenceCategory pref2;

    /* renamed from: com.androidapp.mytools.AppPreferenceActivity.1 */
    class C00001 implements OnPreferenceChangeListener {
        C00001() {
        }

        public boolean onPreferenceChange(Preference preference, Object newValue) {
            AppPreferenceActivity.this.listPreference.setValue(newValue.toString());
            BluetoothSharedPrefs.setMacAddress(AppPreferenceActivity.this.getBaseContext(), newValue.toString());
            preference.setSummary(AppPreferenceActivity.this.listPreference.getEntry());
            return true;
        }
    }

    /* renamed from: com.androidapp.mytools.AppPreferenceActivity.2 */
    class C00012 implements OnPreferenceChangeListener {
        C00012() {
        }

        public boolean onPreferenceChange(Preference preference, Object newValue) {
            AppPreferenceActivity.this.checkBoxPreference.setChecked(((Boolean) newValue).booleanValue());
            BluetoothSharedPrefs.setBluetoothAlwaysOn(AppPreferenceActivity.this.getBaseContext(), AppPreferenceActivity.this.checkBoxPreference.isChecked());
            return true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        addPreferencesFromResource(R.xml.preference);
        this.listPreference = (ListPreference) findPreference("bluetooth_mac");
        this.pref2 = (PreferenceCategory) findPreference("pref2");
        this.checkBoxPreference = (CheckBoxPreference) findPreference("bluetooth_always_on");
        if (this.listPreference.getValue() != null) {
            if (this.listPreference.getEntry() == null) {
                this.listPreference.setSummary("");
            } else {
                this.listPreference.setSummary(this.listPreference.getEntry().toString());
            }
        }
        this.checkBoxPreference.setChecked(BluetoothSharedPrefs.getBluetoothAlwaysOn(getBaseContext()));
        this.listPreference.setOnPreferenceChangeListener(new C00001());
        this.checkBoxPreference.setOnPreferenceChangeListener(new C00012());
    }

    public void finish() {
        if (BluetoothSharedPrefs.getMacAddress(this) != null) {
            BluetoothSharedPrefs.setDeviceName(this.context, this.listPreference.getEntry().toString());
            setResult(-1);
        } else {
            setResult(0);
        }
        super.finish();
    }
}
