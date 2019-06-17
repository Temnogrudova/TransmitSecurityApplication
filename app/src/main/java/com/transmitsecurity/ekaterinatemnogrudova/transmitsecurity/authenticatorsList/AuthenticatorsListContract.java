package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.authenticatorsList;

import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.TransmitSDK;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.BaseView;

import java.util.List;

public class AuthenticatorsListContract {
    public interface View extends BaseView<Presenter> {
        void onGetAuthenticatorListSuccess(List<TransmitSDK.Authenticator> result);
    }

    interface Presenter  {
        void getAuthenticatorList();
    }
}
