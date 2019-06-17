package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.TransmitSDK;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.Constants.SharedPreference.AUTHENTICATORS_LIST;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.Constants.SharedPreference.AUTHENTICATOR_POSITION;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.Constants.SharedPreference.IS_UPDATED;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.Constants.SharedPreference.PREFS_NAME;

public class SharedPreference {

    public SharedPreference() {
        super();
    }

    private Editor getEditor(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        return settings.edit();
    }

    public void saveAuthenticatorsList(Context context, List<TransmitSDK.Authenticator> authenticators) {
        Editor editor = getEditor(context);
        Gson gson = new Gson();
        String jsonAuthenticatorsList = gson.toJson(authenticators);
        editor.putString(AUTHENTICATORS_LIST, jsonAuthenticatorsList);
        editor.commit();
    }

    public void removeAuthenticator(Context context, TransmitSDK.Authenticator authenticator) {
        ArrayList<TransmitSDK.Authenticator> authenticatorsList = getAuthenticatorsList (context);
        if (authenticatorsList != null) {
            authenticatorsList.remove(authenticator);
            saveAuthenticatorsList(context, authenticatorsList);
        }
    }

    public ArrayList<TransmitSDK.Authenticator> getAuthenticatorsList (Context context) {
        List<TransmitSDK.Authenticator> authenticatorsList;
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        if (settings.contains(AUTHENTICATORS_LIST)) {
            String jsonAuthenticatorsList = settings.getString(AUTHENTICATORS_LIST, null);
            Gson gson = new Gson();
            TransmitSDK.Authenticator[] authenticators = gson.fromJson(jsonAuthenticatorsList,
                    TransmitSDK.Authenticator[].class);
            authenticatorsList = Arrays.asList(authenticators);
            authenticatorsList = new ArrayList<TransmitSDK.Authenticator>(authenticatorsList);
        } else
            return null;
        return (ArrayList<TransmitSDK.Authenticator>) authenticatorsList;
    }

    public void saveAuthenticatorPosition(Context context, int authenticatorPosition) {
        Editor editor = getEditor(context);
        editor.putInt(AUTHENTICATOR_POSITION, authenticatorPosition);
        editor.commit();
    }

    public int getAuthenticatorPosition(Context context) {
        int authenticatorPosition;
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        authenticatorPosition = settings.getInt(AUTHENTICATOR_POSITION,-1);
        return authenticatorPosition;
    }

    public void saveIsUpdated(Context context, Boolean isUpd) {
        Editor editor = getEditor(context);
        editor.putBoolean(IS_UPDATED, isUpd);
        editor.commit();
    }

    public Boolean getIsUpdated(Context context) {
        Boolean authenticatorPosition;
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        authenticatorPosition = settings.getBoolean(IS_UPDATED,false);
        return authenticatorPosition;
    }
}
