package sempait.haycancha.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.ConfirmDialogCustom;
import sempait.haycancha.R;
import sempait.haycancha.activities.LoginActivity;
import sempait.haycancha.activities.MainActivity;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.models.User;
import sempait.haycancha.services.LoginTask;

/**
 * Created by martin on 21/10/15.
 */
public class LoginFragment extends BaseFragment {

    private ViewGroup mView;
    private Button mButtonDone;
    private RelativeLayout mButtonForgotPass;
    private Button mButtonCrearCuenta;
    private ScrollView mMainScroll;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private LoginTask mLoginTask;
    private Context context;


    private User mUserActual = new User();

    public static LoginFragment newInstance() {

        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);

        getBaseActivity().setSectionTitle(getString(R.string.login_title));

        mButtonDone = (Button) mView.findViewById(R.id.btn_hay_cancha);
        mButtonForgotPass = (RelativeLayout) mView.findViewById(R.id.btn_forgot_pass);
        mButtonCrearCuenta = (Button) mView.findViewById(R.id.btn_crear_cuenta);

        mEmailEditText = (EditText) mView.findViewById(R.id.et_email);
        mPasswordEditText = (EditText) mView.findViewById(R.id.et_password);
        context = mContext;


        return mView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (allFildCompleted())
                    executeLoginService();
                else {
                    ConfirmDialogCustom dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_form_incomplete_message), mContext.getString(R.string.login_title), mContext.getString(R.string.acept_text));
                    FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.add(dialog, null);
                    ft.commitAllowingStateLoss();
                }
            }
        });

        mButtonCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((BaseActivity) mContext).replaceInnerFragmentWhitFLip(CreateAccountFragment.newInstance(), true);

//                executeServiceAccount();


            }
        });


        mButtonForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ((BaseActivity) mContext).replaceInnerFragmentWhitFLip(ForgotPasswordFragemnt.newInstance(), true);

            }
        });

    }


    public void executeLoginService() {


        mLoginTask = new LoginTask(mContext) {
            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                if (result != null && result.length() != 2) {


                    List<User> mUsers = new Gson().fromJson(result.toString(), new TypeToken<List<User>>() {
                    }.getType());

                    mUserActual = mUsers.get(0);

                    ConfigurationClass.setUserNameCompleted(mContext, mUserActual.getApellido() + " " + mUserActual.getNombre());
                    ConfigurationClass.setFirstNameUser(mContext, mUserActual.getNombre());
                    ConfigurationClass.setUserLastNameUser(mContext, mUserActual.getApellido());
                    ConfigurationClass.setUserCod(mContext, mUserActual.getCodigoUsuario());
                    ConfigurationClass.setEmailUser(mContext, mUserActual.getMail());
                    ConfigurationClass.setTelUser(mContext, mUserActual.getTelefono());
                    ConfigurationClass.setDNIUser(mContext, mUserActual.getDni());
                    ConfigurationClass.setPasswodUser(mContext, mUserActual.getContraseña());
                    ConfigurationClass.setCodigoPosicion(mContext, mUserActual.getCodigoPosicion());
                    ConfigurationClass.setDescPosicion(mContext, mUserActual.getDescPosicion());


                    mContext.startActivity(new Intent(mContext, MainActivity.class));

                    ((LoginActivity) mContext).finish();


                } else {

                    ConfirmDialogCustom dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_user_password_wrong), mContext.getString(R.string.login_title), mContext.getString(R.string.acept_text));
                    FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.add(dialog, null);
                    ft.commitAllowingStateLoss();
                }


            }
        };


        mLoginTask.mEmail = mEmailEditText.getText().toString();
        mLoginTask.mPassword = mPasswordEditText.getText().toString();


        mLoginTask.execute();

    }


    private boolean allFildCompleted() {

        if (mEmailEditText.getText().toString().isEmpty()
                || mPasswordEditText.getText().toString().isEmpty()) {


            return false;

        } else
            return true;


    }


}