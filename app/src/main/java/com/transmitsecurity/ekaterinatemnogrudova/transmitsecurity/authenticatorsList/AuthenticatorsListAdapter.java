package com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.authenticatorsList;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.R;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.TransmitSDK;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.AuthenticatorInterface;
import com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.utils.SharedPreference;
import java.util.ArrayList;
import java.util.List;
import static com.transmitsecurity.ekaterinatemnogrudova.transmitsecurity.TransmitSDK.Authenticator.FINGERPRINT;

public class AuthenticatorsListAdapter extends RecyclerView.Adapter<AuthenticatorsListAdapter.BindingHolder> implements AuthenticatorInterface {

    private List<TransmitSDK.Authenticator> mAuthenticators = new ArrayList<>();
    private IAuthenticatorClicked mListener;
    private Context mContext;
    SharedPreference sharedPreference = new SharedPreference();
    public interface IAuthenticatorClicked {
        void onAuthenticatorClick(TransmitSDK.Authenticator authenticator);
    }

    public AuthenticatorsListAdapter(List<TransmitSDK.Authenticator> authenticators, Context context, IAuthenticatorClicked authenticatorListener) {
        mAuthenticators = authenticators;
        mListener = authenticatorListener;
        mContext = context;
    }

    @Override
    public AuthenticatorsListAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final ViewDataBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_authenticators_list_item, parent, false);

        AuthenticatorsListAdapter.BindingHolder holder = new AuthenticatorsListAdapter.BindingHolder(binding.getRoot());
        holder.setBinding(binding);
        holder.setClickedListener(this);
        return holder;
    }
    View item;
    @Override
    public void onBindViewHolder(AuthenticatorsListAdapter.BindingHolder holder, final int position) {
        TransmitSDK.Authenticator authenticator = mAuthenticators.get(position);
        item = holder.itemView;
        TextView title = holder.itemView.findViewById(R.id.title);
        title.setText(authenticator.toString());
        TextView subTitle = holder.itemView.findViewById(R.id.sub_title);
        subTitle.setText(mContext.getText(R.string.authenticator_list_subtitle));
        int row_index = sharedPreference.getAuthenticatorPosition(mContext);
        Boolean isUpdated = sharedPreference.getIsUpdated(mContext);

        if(row_index == position && /*row_index != FINGERPRINT.ordinal()*/isUpdated)
        {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }
        else
        {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorBackground));
        }
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (mAuthenticators == null) {
            return 0;
        }
        return mAuthenticators.size();
    }

    @Override
    public void onAuthenticatorClick(int position) {
        TransmitSDK.Authenticator authenticator = mAuthenticators.get(position);
        if (mListener != null) {
            mListener.onAuthenticatorClick(authenticator);
        }
        sharedPreference.saveAuthenticatorPosition(mContext, position);
        sharedPreference.saveIsUpdated(mContext, false);
        notifyDataSetChanged();
    }
    public class BindingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View mRowView;
        private ViewDataBinding binding;
        private AuthenticatorInterface mClickedListener;

        public BindingHolder(View rowView) {
            super(rowView);
            mRowView = rowView;
            mRowView.setOnClickListener(this);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        @Override
        public void onClick(View v) {
            mRowView.findViewById(R.id.ll_item).setBackgroundColor(mContext.getResources().getColor(R.color.colorBackground));
            mClickedListener.onAuthenticatorClick(getAdapterPosition());
        }

        public void setClickedListener(AuthenticatorInterface clickedListener) {
            mClickedListener = clickedListener;
        }
    }
}
