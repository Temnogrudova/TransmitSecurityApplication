package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.authenticatorsList;

import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.TransmitSDK;

import java.util.List;

public class AuthenticatorsListPresenter implements AuthenticatorsListContract.Presenter {
    private AuthenticatorsListContract.View mView;

    public AuthenticatorsListPresenter(AuthenticatorsListContract.View view) {
      mView = view;
      mView.setPresenter(this);
    }

    @Override
    public void getAuthenticatorList() {
        TransmitSDK.getInstance().authenticatorsList(new TransmitSDK.OnListResult() {
            @Override
            public void onComplete(List<TransmitSDK.Authenticator> result) {
                mView.onGetAuthenticatorListSuccess(result);
            }
        });
    }
}
