package app.sctp.core;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class SharedPreferenceAccessor {
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String UPDATE_AVAILABLE = "app_update_available";
    private static final String UPDATE_MANDATORY = "app_update_mandatory";
    private static final String KEY_SELECTED_VILLAGE = "target.village";
    private static final String KEY_SUPPRESS_LOCATION_PROMPT = "target.suppress_location_prompt";


    private final Application application;

    public SharedPreferenceAccessor(Application applicationContext) {
        this.application = applicationContext;
    }

    private SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    private SharedPreferences.Editor getEditor() {
        return getPrefs().edit();
    }

    public String getAccessToken() {
        return getPrefs()
                .getString(KEY_ACCESS_TOKEN, null);
    }

    public void setAccessToken(String accessToken) {
        getEditor()
                .putString(KEY_ACCESS_TOKEN, accessToken)
                .apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getPrefs().getBoolean(key, defaultValue);
    }

    public boolean hasAccessToken() {
        return getAccessToken() != null;
    }

    public void setUpdateAvailable(Boolean updateAvailable, Boolean mandatoryUpdate) {
        getEditor().putBoolean(UPDATE_AVAILABLE, updateAvailable)
                .putBoolean(UPDATE_MANDATORY, mandatoryUpdate)
                .apply();
    }

    public boolean isLocationSelected() {
        return getBoolean(KEY_SELECTED_VILLAGE, false);
    }

    public boolean getSuppressInitialLocationPrompt() {
        return getBoolean(KEY_SUPPRESS_LOCATION_PROMPT, false);
    }

    public void setSuppressInitialLocationPrompt(boolean suppress) {
        getEditor().putBoolean(KEY_SUPPRESS_LOCATION_PROMPT, suppress)
                .apply();
    }
}
