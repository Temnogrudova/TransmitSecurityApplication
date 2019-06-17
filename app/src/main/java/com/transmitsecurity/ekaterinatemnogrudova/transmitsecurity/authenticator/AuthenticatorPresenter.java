package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.authenticator;

import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.TransmitSDK;

public class AuthenticatorPresenter implements AuthenticatorContract.Presenter {
    private AuthenticatorContract.View mView;

    public AuthenticatorPresenter(AuthenticatorContract.View view) {
      mView = view;
      mView.setPresenter(this);
    }

    @Override
    public void authenticateWithPassword(String password) {
        TransmitSDK.getInstance().authenticateWithPassword(password, new TransmitSDK.OnResult() {
            @Override
            public void onComplete() {
                mView.onAuthenticateSuccess();
            }

            @Override
            public void onReject(String error) {
                mView.onAuthenticateFail();
            }
        });
    }

    @Override
    public void authenticateWithPinCode(String pinCode) {
        TransmitSDK.getInstance().authenticateWithPincode(pinCode, new TransmitSDK.OnResult() {
            @Override
            public void onComplete() {
                mView.onAuthenticateSuccess();
            }

            @Override
            public void onReject(String error) {
                mView.onAuthenticateFail();
            }
        });
    }
}
