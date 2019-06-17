package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.fingerprint;

import android.app.DialogFragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.R;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.databinding.DialogFingerprintBinding;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.SharedPreference;

public class FingerprintDialogFragment extends DialogFragment implements FingerprintDialogContract.View{
    private DialogFingerprintBinding mBinder;
    private FingerprintDialogContract.Presenter mPresenter;
    private SharedPreference sharedPreference = new SharedPreference();

    public static FingerprintDialogFragment newInstance() {
        return new FingerprintDialogFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mAuthenticateListener = (onAuthenticateListener) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + " must implement onAuthenticatorClickListener");
        }
    }
    public interface onAuthenticateListener{
        void onAuthenticateSuccess();
        void onAuthenticateFail();
    }

    public onAuthenticateListener mAuthenticateListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.dialog_fingerprint, container, false);
        View view = mBinder.getRoot();
        mPresenter = new FingerprintDialogPresenter(this);
        mBinder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                sharedPreference.saveAuthenticatorPosition(getActivity(), -1);

            }
        });
        mBinder.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.authenticateWithFingerprint(true);
                getDialog().dismiss();

            }
        });
        return view;
    }

    @Override
    public void setPresenter(FingerprintDialogContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onAuthenticateSuccess() {
        mAuthenticateListener.onAuthenticateSuccess();
    }

    @Override
    public void onAuthenticateFail() {
        mAuthenticateListener.onAuthenticateFail();
    }
}