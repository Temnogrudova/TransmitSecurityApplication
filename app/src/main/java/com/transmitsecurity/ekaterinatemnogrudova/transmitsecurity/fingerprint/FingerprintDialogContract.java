package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.fingerprint;

import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.BaseView;

public class FingerprintDialogContract {
    public interface View extends BaseView<Presenter> {
        void onAuthenticateSuccess();
        void onAuthenticateFail();
    }

    interface Presenter  {
        void authenticateWithFingerprint(boolean useFingerprint);
    }
}
