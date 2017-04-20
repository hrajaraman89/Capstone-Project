package com.rajaraman.bbcnewsreader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import org.greenrobot.eventbus.EventBus;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new MyPreferenceFragment())
                .commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);

            SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals(MyPreferenceFragment.this.getString(R.string.selected_feed_key))) {
                        EventBus.getDefault().post(new MainActivity.FeedChangedEvent());
                    }
                }
            };

            PreferenceManager.getDefaultSharedPreferences(this.getActivity())
                    .registerOnSharedPreferenceChangeListener(listener);
        }

    }


}
