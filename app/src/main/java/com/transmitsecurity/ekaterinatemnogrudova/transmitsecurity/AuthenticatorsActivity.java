package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.authenticator.AuthenticatorFailFragment;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.authenticatorsList.AuthenticatorsListFragment;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.databinding.ActivityAuthenticatorsBinding;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.authenticator.AuthenticatorFragment;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.fingerprint.FingerprintDialogFragment;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.SharedPreference;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.TransmitSDK.Authenticator.FINGERPRINT;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.TransmitSDK.Authenticator.PASSWORD;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.TransmitSDK.Authenticator.PINCODE;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.Constants.Fragment.FRAGMENT_AUTHENTICATOR;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.Constants.Fragment.FRAGMENT_AUTHENTICATORS_LIST;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.Constants.Fragment.FRAGMENT_AUTHENTICATOR_FAIL;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.Constants.Fragment.FRAGMENT_FINGERPRINT;

public class AuthenticatorsActivity extends AppCompatActivity implements
        AuthenticatorsListFragment.onAuthenticatorClickListener,
        AuthenticatorFragment.onAuthenticateListener,
        AuthenticatorFailFragment.onTryClickListener,
        FingerprintDialogFragment.onAuthenticateListener

{
    public ActivityAuthenticatorsBinding mBinder;
    private SharedPreference sharedPreference = new SharedPreference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_authenticators);
        mBinder.toolBar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mBinder.toolBar);
        if (savedInstanceState == null) {
            AuthenticatorsListFragment authenticatorsListFragment = AuthenticatorsListFragment.newInstance();
            handleFragment(authenticatorsListFragment, null, FRAGMENT_AUTHENTICATORS_LIST);
            sharedPreference.saveAuthenticatorPosition(this, -1);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            if (getFragmentManager().findFragmentById(R.id.fragment_container) ==
            getFragmentManager().findFragmentByTag(FRAGMENT_AUTHENTICATORS_LIST)) {
                finish();
            }
            else {
                sharedPreference.saveAuthenticatorPosition(this, -1);
                getFragmentManager().popBackStack();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void authenticateWithPassword() {
        AuthenticatorFragment authenticatorFragment = new AuthenticatorFragment();
        Bundle authenticateWithPasswordBundle = new Bundle();
        authenticateWithPasswordBundle.putSerializable(FRAGMENT_AUTHENTICATOR, PASSWORD );
        handleFragment(authenticatorFragment,authenticateWithPasswordBundle, FRAGMENT_AUTHENTICATOR);
    }

    @Override
    public void authenticateWithFingerprint() {
        FingerprintDialogFragment fingerprintDialogFragment = FingerprintDialogFragment.newInstance();
        fingerprintDialogFragment.show(getFragmentManager(), FRAGMENT_FINGERPRINT);
    }

    @Override
    public void authenticateWithPinCode() {
        AuthenticatorFragment authenticatorFragment = new AuthenticatorFragment();
        Bundle authenticateWithPinCodeBundle = new Bundle();
        authenticateWithPinCodeBundle.putSerializable(FRAGMENT_AUTHENTICATOR, PINCODE );
        handleFragment(authenticatorFragment,authenticateWithPinCodeBundle, FRAGMENT_AUTHENTICATOR);
    }

    public void handleFragment(Fragment fragment, Bundle bundle, String tag) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onAuthenticateSuccess() {
        if (getFragmentManager().findFragmentById(R.id.fragment_container) ==
                getFragmentManager().findFragmentByTag(FRAGMENT_AUTHENTICATOR)) {
            getFragmentManager().popBackStack();
        }
        ((AuthenticatorsListFragment)getFragmentManager().findFragmentByTag(FRAGMENT_AUTHENTICATORS_LIST)).remove(sharedPreference.getAuthenticatorPosition(this));
        sharedPreference.saveAuthenticatorPosition(this, -1);
    }

    @Override
    public void onAuthenticateFail() {
        AuthenticatorFailFragment authenticatorFailFragment = new AuthenticatorFailFragment();
        handleFragment(authenticatorFailFragment,null, FRAGMENT_AUTHENTICATOR_FAIL);
        sharedPreference.saveIsUpdated(this, true);
        ((AuthenticatorsListFragment)getFragmentManager().findFragmentByTag(FRAGMENT_AUTHENTICATORS_LIST)).mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onTryClick() {
        getFragmentManager().popBackStack();
        int position = sharedPreference.getAuthenticatorPosition(this);
        TransmitSDK.Authenticator authenticatorName = ((AuthenticatorsListFragment)getFragmentManager().findFragmentByTag(FRAGMENT_AUTHENTICATORS_LIST)).mAuthenticators.get(position);
        if (authenticatorName.name() != FINGERPRINT.name()) {
            getFragmentManager().popBackStack();
        }
    }
}
