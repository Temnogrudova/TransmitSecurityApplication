package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.authenticator;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.AuthenticatorsActivity;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.R;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.databinding.FragmentAuthenticatorFailBinding;


public class AuthenticatorFailFragment extends Fragment{
    private FragmentAuthenticatorFailBinding mBinder;
    public interface onTryClickListener {
        void onTryClick();
    }
    onTryClickListener tryClickListener;

    public static AuthenticatorFailFragment newInstance() {
        return new AuthenticatorFailFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            tryClickListener = (onTryClickListener) getActivity();
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
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_authenticator_fail, container, false);
        View view = mBinder.getRoot();
        mBinder.btnTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryClickListener.onTryClick();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AuthenticatorsActivity) getActivity()).mBinder.toolBar.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((AuthenticatorsActivity) getActivity()).mBinder.toolBar.setVisibility(View.VISIBLE);
    }
}
