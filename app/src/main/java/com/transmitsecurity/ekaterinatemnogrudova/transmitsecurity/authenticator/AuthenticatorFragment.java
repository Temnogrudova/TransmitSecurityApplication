package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.authenticator;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.R;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.TransmitSDK;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.databinding.FragmentAuthenticatorBinding;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.Constants.Fragment.FRAGMENT_AUTHENTICATOR;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.Constants.MAX_PINCODE_LENGTH;

public class AuthenticatorFragment extends Fragment implements AuthenticatorContract.View
{
    private FragmentAuthenticatorBinding mBinder;
    private AuthenticatorContract.Presenter mPresenter;
    private TransmitSDK.Authenticator mAuthenticator;

    public interface onAuthenticateListener {
        void onAuthenticateSuccess();
        void onAuthenticateFail();
    }

    onAuthenticateListener authenticateListener;

    public static AuthenticatorFragment newInstance() {
        return new AuthenticatorFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            authenticateListener = (onAuthenticateListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement onAuthenticatorClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this Fragment across configuration changes.
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_authenticator, container, false);
        View view = mBinder.getRoot();
        mPresenter = new AuthenticatorPresenter(this);
        if (getArguments() != null) {
            mAuthenticator = (TransmitSDK.Authenticator) getArguments().getSerializable(FRAGMENT_AUTHENTICATOR);
            switch (mAuthenticator){
                case PASSWORD:
                    setEtCodeParams(getText(R.string.authenticator_et_password_hint),
                            new InputFilter[] {},
                            InputType.TYPE_CLASS_TEXT);
                    break;
                case PINCODE:
                    setEtCodeParams(getText(R.string.authenticator_et_pincode_hint),
                            new InputFilter[]{new InputFilter.LengthFilter(MAX_PINCODE_LENGTH)},
                            InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    break;
            }
            mBinder.btnAuthenticate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (mAuthenticator){
                        case PASSWORD:
                            mPresenter.authenticateWithPassword(mBinder.etCode.getText().toString());
                            break;
                        case PINCODE:
                            mPresenter.authenticateWithPinCode(mBinder.etCode.getText().toString());
                            break;
                    }
                }});
        }
        return view;
    }

    private void setEtCodeParams(CharSequence hint, InputFilter[] filters, int type) {
        mBinder.etCode.setHint(hint);
        mBinder.etCode.setFilters(filters);
        mBinder.etCode.setInputType(type);
    }

    @Override
    public void setPresenter(AuthenticatorContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onAuthenticateSuccess() {
        authenticateListener.onAuthenticateSuccess();
    }

    @Override
    public void onAuthenticateFail() {
        authenticateListener.onAuthenticateFail();
    }
}
