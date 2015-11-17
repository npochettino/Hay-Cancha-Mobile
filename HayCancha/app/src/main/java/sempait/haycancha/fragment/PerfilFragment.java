package sempait.haycancha.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.ConfirmDialogCustom;
import sempait.haycancha.R;
import sempait.haycancha.Utils;
import sempait.haycancha.activities.LoginActivity;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.services.CreateAccountTask;

/**
 * Created by martin on 16/11/15.
 */
public class PerfilFragment extends BaseFragment {

    private EditText mTxtUserName, mTxtUserEmail, mTxtUserPassword, mTxtUserPhone, mEtxtLastName;
    private TextView mTxtPosicion;
    private ImageView mImgProfile, mImgArquero, mImgDefensor, mImgDelantero, mImgTodaElCampo;
    private int mPositionSelectec;
    private CreateAccountTask mUpdateAccountTask;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View mView = inflater.inflate(R.layout.fragment_perfil, container, false);

        mTxtUserName = (EditText) mView.findViewById(R.id.exp_name_user);
        mTxtUserEmail = (EditText) mView.findViewById(R.id.exp_email);
        mTxtUserPassword = (EditText) mView.findViewById(R.id.exp_password);
        mTxtUserPhone = (EditText) mView.findViewById(R.id.exp_phone);
        mEtxtLastName = (EditText) mView.findViewById(R.id.exp_last_name_user);
        mImgProfile = (ImageView) mView.findViewById(R.id.img_profile);
        mImgArquero = (ImageView) mView.findViewById(R.id.img_arquero);
        mImgDefensor = (ImageView) mView.findViewById(R.id.img_defensor);
        mImgDelantero = (ImageView) mView.findViewById(R.id.img_delantero);
        mImgTodaElCampo = (ImageView) mView.findViewById(R.id.img_todo_el_campo);
        mTxtPosicion = (TextView) mView.findViewById(R.id.txt_position);

        setDataUser();


        return mView;


    }

    private void setDataUser() {

        mTxtUserName.setText(ConfigurationClass.getUserFirstNameUser(mContext));
        mEtxtLastName.setText(ConfigurationClass.getUserLastNameUserUser(mContext));
        mTxtUserEmail.setText(ConfigurationClass.getEmalUser(mContext));
        mTxtUserPassword.setText(ConfigurationClass.getPasswordUser(mContext));
        mTxtUserPhone.setText(ConfigurationClass.getTelUser(mContext));
        String url = "http://3.bp.blogspot.com/-QgXJDaHt5Lo/UpxQPX_W-gI/AAAAAAAANII/TzU2P5KfGxU/s1600/3874_3_1219.jpg";
        ImageLoader.getInstance().displayImage(url.contains("http:") ? url : "http:" + url, mImgProfile, Utils.getImageLoaderOptionRouded());
        refreshPostion(ConfigurationClass.getCodigoPosicion(mContext));

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.txt_cerrar_sesion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = ConfigurationClass.sharedPref(mContext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                getActivity().finish();

            }
        });

        mImgArquero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPostion(1);
            }
        });

        mImgDefensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPostion(2);
            }
        });

        mImgDelantero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPostion(3);
            }
        });


        mImgTodaElCampo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPostion(4);
            }
        });


        view.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (allFildCompleted())
                    executeUpdateData();
                else {
                    ConfirmDialogCustom dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_form_incomplete_message), mContext.getString(R.string.perfil), mContext.getString(R.string.acept_text));
                    FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.add(dialog, null);
                    ft.commitAllowingStateLoss();
                }

            }
        });
    }

    private void executeUpdateData() {

        mUpdateAccountTask = new CreateAccountTask(mContext) {


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);


                ConfirmDialogCustom dialog;


                if (result != null && result.equalsIgnoreCase("ok")) {

                    dialog = new ConfirmDialogCustom(mContext.getString(R.string.success_update_user_info), mContext.getString(R.string.perfil), mContext.getString(R.string.acept_text));

                } else
                    dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_message), mContext.getString(R.string.perfil), mContext.getString(R.string.acept_text));


                FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                ft.add(dialog, null);
                ft.commitAllowingStateLoss();


            }
        };

        mUpdateAccountTask.mPosicion = mPositionSelectec;
        mUpdateAccountTask.mApellido = mEtxtLastName.getText().toString();
        mUpdateAccountTask.mNombre = mTxtUserName.getText().toString();
        mUpdateAccountTask.mEmail = mTxtUserEmail.getText().toString();
        mUpdateAccountTask.mPassword = mTxtUserPassword.getText().toString();
        mUpdateAccountTask.mTelefono = mTxtUserPhone.getText().toString();
        mUpdateAccountTask.mCodigoUsuario = ConfigurationClass.getUserCod(mContext);

        mUpdateAccountTask.execute();


    }

    private void refreshPostion(int position) {


        switch (position) {

            case 1:
                mImgArquero.setImageResource(R.drawable.arquero_activo);
                mImgDefensor.setImageResource(R.drawable.defensa_desactivado);
                mImgDelantero.setImageResource(R.drawable.delantero_desactivado);
                mImgTodaElCampo.setImageResource(R.drawable.todo_campo_desactivado);
                mTxtPosicion.setText("Arquero");
                mPositionSelectec = 1;
                break;

            case 2:
                mImgArquero.setImageResource(R.drawable.arquero_desactivado);
                mImgDefensor.setImageResource(R.drawable.defensa_activo);
                mImgDelantero.setImageResource(R.drawable.delantero_desactivado);
                mImgTodaElCampo.setImageResource(R.drawable.todo_campo_desactivado);
                mTxtPosicion.setText("Defensor");
                mPositionSelectec = 2;
                break;

            case 3:
                mImgArquero.setImageResource(R.drawable.arquero_desactivado);
                mImgDefensor.setImageResource(R.drawable.defensa_desactivado);
                mImgDelantero.setImageResource(R.drawable.delantero_activo);
                mImgTodaElCampo.setImageResource(R.drawable.todo_campo_desactivado);
                mTxtPosicion.setText("Delantero");
                mPositionSelectec = 3;
                break;
            case 4:
                mImgArquero.setImageResource(R.drawable.arquero_desactivado);
                mImgDefensor.setImageResource(R.drawable.defensa_desactivado);
                mImgDelantero.setImageResource(R.drawable.delantero_desactivado);
                mImgTodaElCampo.setImageResource(R.drawable.todo_campo_activo);
                mTxtPosicion.setText("Todo el campo");
                mPositionSelectec = 4;
                break;

        }


    }

    private boolean allFildCompleted() {

        if (mEtxtLastName.getText().toString().isEmpty()
                || mTxtUserName.getText().toString().isEmpty() || mTxtUserPhone.getText().toString().isEmpty() || mTxtUserPassword.getText().toString().isEmpty() || mTxtUserEmail.getText().toString().isEmpty()) {

            return false;

        } else
            return true;


    }
}