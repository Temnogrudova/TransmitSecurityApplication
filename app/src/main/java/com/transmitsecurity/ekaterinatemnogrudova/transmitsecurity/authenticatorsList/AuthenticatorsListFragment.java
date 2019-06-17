package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.authenticatorsList;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.R;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.TransmitSDK;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.databinding.FragmentAuthenticatorsListBinding;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.SharedPreference;
import java.util.ArrayList;
import java.util.List;

public class AuthenticatorsListFragment extends Fragment implements AuthenticatorsListContract.View, AuthenticatorsListAdapter.IAuthenticatorClicked {
    public FragmentAuthenticatorsListBinding mBinder;
    public List<TransmitSDK.Authenticator> mAuthenticators = new ArrayList<>();
    public AuthenticatorsListAdapter mAdapter;
    private AuthenticatorsListContract.Presenter mPresenter;

    public interface onAuthenticatorClickListener {
        void authenticateWithPassword();
        void authenticateWithFingerprint();
        void authenticateWithPinCode();
    }

    onAuthenticatorClickListener authenticatorClickListener;

    private SharedPreference sharedPreference = new SharedPreference();

    public static AuthenticatorsListFragment newInstance() {
        return new AuthenticatorsListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            authenticatorClickListener = (onAuthenticatorClickListener) getActivity();
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
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_authenticators_list, container, false);
        View view = mBinder.getRoot();
        mPresenter = new AuthenticatorsListPresenter(this);
        mBinder.authentificatorsList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        mAdapter = new AuthenticatorsListAdapter(mAuthenticators, getActivity(), this);
        mBinder.authentificatorsList.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPreference.getAuthenticatorsList(getActivity()) ==null) {
            mBinder.networkProgress.setVisibility(View.VISIBLE);
            mPresenter.getAuthenticatorList();
        }
        else{
            refreshAuthenticatorList(sharedPreference.getAuthenticatorsList(getActivity()));
        }
    }

    @Override
    public void onGetAuthenticatorListSuccess(List<TransmitSDK.Authenticator> result) {
        mBinder.networkProgress.setVisibility(View.GONE);
        ArrayList<TransmitSDK.Authenticator> authenticatorList = new ArrayList<TransmitSDK.Authenticator>(result);
        refreshAuthenticatorList(authenticatorList);
        sharedPreference.saveAuthenticatorsList(getActivity(), result);
    }

    @Override
    public void setPresenter(AuthenticatorsListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onAuthenticatorClick(TransmitSDK.Authenticator authenticator) {
        switch (authenticator){
            case PASSWORD:
                authenticatorClickListener.authenticateWithPassword();
                break;
            case FINGERPRINT:
                authenticatorClickListener.authenticateWithFingerprint();
                break;
            case PINCODE:
                authenticatorClickListener.authenticateWithPinCode();
                break;
        }
    }

    public void remove(int i){
        ArrayList<TransmitSDK.Authenticator> authenticatorList = sharedPreference.getAuthenticatorsList(getActivity());
        sharedPreference.removeAuthenticator(getActivity(), authenticatorList.get(i));
        refreshAuthenticatorList(sharedPreference.getAuthenticatorsList(getActivity()));
        sharedPreference.saveAuthenticatorPosition(getActivity(),-1);
    }

    private void refreshAuthenticatorList(ArrayList<TransmitSDK.Authenticator> authenticatorList){
        mAuthenticators.clear();
        mAuthenticators.addAll(authenticatorList);
        mAdapter.notifyDataSetChanged();
    }
}
