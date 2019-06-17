package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils;

public class Constants {

    public interface Fragment
    {
        String FRAGMENT_AUTHENTICATORS_LIST = "FRAGMENT_AUTHENTICATORS_LIST";
        String FRAGMENT_AUTHENTICATOR = "FRAGMENT_AUTHENTICATOR";
        String FRAGMENT_AUTHENTICATOR_FAIL = "FRAGMENT_AUTHENTICATOR_FAIL";
        String FRAGMENT_FINGERPRINT = "FRAGMENT_FINGERPRINT";
    }

    public interface SharedPreference
    {
        String PREFS_NAME = "TRANSMIT_SECURITY_APP";
        String AUTHENTICATORS_LIST = "AUTHENTICATORS_LIST";
        String AUTHENTICATOR_POSITION = "AUTHENTICATOR_POSITION";
        String IS_UPDATED = "IS_UPDATED";
    }

    public static final int MAX_PINCODE_LENGTH = 5;
}
