package sempait.haycancha.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import sempait.haycancha.ConfirmDialogCustom;
import sempait.haycancha.R;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.base.BaseFragment;

/**
 * Created by martin on 10/11/15.
 */
public class CreateAccountFragment extends BaseFragment {


    private View mView;
    private EditText mEtName;
    private EditText mEtLastName;
    private EditText mEtPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getBaseActivity().setSectionTitle(getString(R.string.create_account_title));

        mView = (ViewGroup) inflater.inflate(R.layout.fragment_create_account, container, false);
        mEtName = (EditText) mView.findViewById(R.id.et_name);
        mEtLastName = (EditText) mView.findViewById(R.id.et_last_name);
        mEtPhone = (EditText) mView.findViewById(R.id.et_phone);

        return mView;
    }


    public static Fragment newInstance() {
        return new CreateAccountFragment();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((Button) view.findViewById(R.id.btn_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (allFildCompleted())
                    ((BaseActivity) mContext).replaceInnerFragmentWhitFLip(new CreateAccountFragment2().newInstance(mEtName.getText().toString(), mEtLastName.getText().toString(), mEtPhone.getText().toString()), true);
                else {
                    ConfirmDialogCustom dialog = new ConfirmDialogCustom(getString(R.string.error_form_incomplete_message), getString(R.string.create_account_title), getString(R.string.acept_text));
                    FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.add(dialog, null);
                    ft.commitAllowingStateLoss();
                }
            }
        });
    }


    private boolean allFildCompleted() {

        if (mEtName.getText().toString().isEmpty()
                || mEtLastName.getText().toString().isEmpty() || mEtPhone.getText().toString().isEmpty()) {

            return false;

        } else
            return true;


    }
}
