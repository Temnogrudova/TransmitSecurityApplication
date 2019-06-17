package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.authenticator;

import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.BaseView;

public class AuthenticatorContract {
    public interface View extends BaseView<Presenter> {
        void onAuthenticateSuccess();
        void onAuthenticateFail();
    }

    interface Presenter  {
        void authenticateWithPassword(String password);
        void authenticateWithPinCode(String pinCode);
    }
}
