package sempait.haycancha.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.ConfirmDialogCustom;
import sempait.haycancha.R;
import sempait.haycancha.Utils;
import sempait.haycancha.activities.LoginActivity;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.services.CreateAccountTask;

/**
 * Created by martin on 11/11/15.
 */
public class CreateAccountFragment2 extends BaseFragment {

    private View mView;
    private CreateAccountTask mCreateTask;
    private String PHONE = "phone";
    private String NAME = "name";
    private String LAST_NAME = "last_name";
    private String mPhone;
    private String mName;
    private String mLastName;
    private Button mCreateAccount;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private EditText mEtConfirmPassword;


    public Fragment newInstance(String name, String lastName, String phone) {

        CreateAccountFragment2 mCreateFragment2 = new CreateAccountFragment2();
        Bundle bundle = new Bundle();
        bundle.putString(PHONE, phone);
        bundle.putString(NAME, name);
        bundle.putString(LAST_NAME, lastName);

        mCreateFragment2.setArguments(bundle);

        return mCreateFragment2;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mName = getArguments().getString(NAME);
        mLastName = getArguments().getString(LAST_NAME);
        mPhone = getArguments().getString(PHONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getBaseActivity().setSectionTitle(getString(R.string.create_account_title));

        mView = (ViewGroup) inflater.inflate(R.layout.fragment_create_account_2, container, false);
        mEtEmail = (EditText) mView.findViewById(R.id.et_email);
        mEtPassword = (EditText) mView.findViewById(R.id.et_password);
        mEtConfirmPassword = (EditText) mView.findViewById(R.id.et_password_2);

        mCreateAccount = (Button) mView.findViewById(R.id.btn_crear_cuenta);

        return mView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (allFildCompleted()) {

                    if (mEtPassword.getText().toString().equals(mEtConfirmPassword.getText().toString()))
                        executeServiceAccount();
                    else {
                        ConfirmDialogCustom dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_password_message), mContext.getString(R.string.create_account_title), mContext.getString(R.string.acept_text));
                        FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                        ft.add(dialog, null);
                        ft.commitAllowingStateLoss();
                    }


                } else {

                    ConfirmDialogCustom dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_form_incomplete_message), mContext.getString(R.string.create_account_title), mContext.getString(R.string.acept_text));
                    FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.add(dialog, null);
                    ft.commitAllowingStateLoss();
                }

            }
        });
    }

    private void executeServiceAccount() {
        mCreateTask = new CreateAccountTask(mContext) {


            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);


                ConfirmDialogCustom dialog;


                if (result != null) {

                    dialog = new ConfirmDialogCustom(mContext.getString(R.string.success_create_account_message), mContext.getString(R.string.create_account_title), mContext.getString(R.string.acept_text));
                    setDataUser();

                    ((BaseActivity) mContext).startActivity(new Intent(((BaseActivity) mContext), LoginActivity.class));
                    ((BaseActivity) mContext).finish();
                } else
                    dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_message), mContext.getString(R.string.create_account_title), mContext.getString(R.string.acept_text));


                FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                ft.add(dialog, null);
                ft.commitAllowingStateLoss();

            }


        };


//                    switch (Integer.valueOf(result)) {


//                        case 1:
//                            if (ConfigurationClass.getUserNameCompleted(mContext) != null) {
//                                setDataUser();
//                                DialogCatalog.mensajeError("Los datos fueron modificados con éxito", mContext);
//                            } else {
//                                setDataUser();
//                                DialogCatalog.mensajeError("La cuenta fue creada con éxito", mContext);
//                                ((BaseActivity) mContext).startActivity(new Intent(((BaseActivity) mContext), LoginActivity.class));
//                                ((BaseActivity) mContext).finish();
//                            }
//                            break;
//
//                        case 2:
//                            DialogCatalog.mensajeError("El DNI fue ya está en uso", mContext);
//                            break;
//
//                        case 3:
//                            DialogCatalog.mensajeError("El email ya esta en uso", mContext);
//                            break;


//                }


        if (ConfigurationClass.getUserNameCompleted(mContext) != null)
            mCreateTask.mCodigoUsuario = ConfigurationClass.getUserCod(mContext);
        else
            mCreateTask.mCodigoUsuario = 0;
        mCreateTask.mApellido = mLastName;
        mCreateTask.mNombre = mName;
        mCreateTask.mEmail = mEtEmail.getText().toString();
        mCreateTask.mTelefono = mPhone;
        mCreateTask.mPassword = mEtPassword.getText().toString();
        mCreateTask.mPosicion = 1;
        mCreateTask.mCodigoTelefono = ConfigurationClass.getCodigoTelefono(mContext);
        mCreateTask.mIsActivo = true;


        mCreateTask.execute();


    }


    private void setDataUser() {


        ConfigurationClass.setUserNameCompleted(mContext, mLastName + " " + mName);
        ConfigurationClass.setFirstNameUser(mContext, mName);
        ConfigurationClass.setUserLastNameUser(mContext, mLastName);
        ConfigurationClass.setEmailUser(mContext, mEtEmail.getText().toString());
        ConfigurationClass.setTelUser(mContext, mPhone);
        ConfigurationClass.setPasswodUser(mContext, mEtPassword.getText().toString());
        ConfigurationClass.setCodigoPosicion(mContext, 1);
        ConfigurationClass.setDescPosicion(mContext, "Todo el campo");
        ConfigurationClass.setIsActivo(mContext, true);


//        if (mInstace != null)
//            mInstace.setNameUser();


    }

    private boolean allFildCompleted() {

        if (mEtEmail.getText().toString().isEmpty()
                || mEtPassword.getText().toString().isEmpty() || mEtConfirmPassword.getText().toString().isEmpty()) {
            return false;

        } else
            return true;


    }


}

