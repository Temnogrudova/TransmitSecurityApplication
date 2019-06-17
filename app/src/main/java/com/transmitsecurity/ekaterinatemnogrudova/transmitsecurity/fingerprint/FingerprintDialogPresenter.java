package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.fingerprint;

import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.TransmitSDK;

public class FingerprintDialogPresenter implements FingerprintDialogContract.Presenter {
    private FingerprintDialogContract.View mView;

    public FingerprintDialogPresenter(FingerprintDialogContract.View view) {
      mView = view;
      mView.setPresenter(this);
    }

    @Override
    public void authenticateWithFingerprint(boolean useFingerprint) {
        TransmitSDK.getInstance().authenticateWithFingerprint(useFingerprint, new TransmitSDK.OnResult() {
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
