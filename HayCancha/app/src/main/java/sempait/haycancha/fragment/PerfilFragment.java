package sempait.haycancha.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.ConfirmDialogCustom;
import sempait.haycancha.R;
import sempait.haycancha.Utils;
import sempait.haycancha.activities.LoginActivity;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.drawer.MainNavigationDrawer;
import sempait.haycancha.services.PUT.CreateAccountTask;
import sempait.haycancha.services.PUT.UpdateImageUser;

/**
 * Created by martin on 16/11/15.
 */
public class PerfilFragment extends BaseFragment {

    private EditText mTxtUserName, mTxtUserEmail, mTxtUserPassword, mTxtUserPhone, mEtxtLastName;
    private TextView mTxtPosicion;
    private ImageView mImgProfile, mImgArquero, mImgDefensor, mImgDelantero, mImgTodaElCampo;
    private int mPositionSelected;
    private String mDescPositionSelected;
    private CreateAccountTask mUpdateAccountTask;
    private UpdateImageUser mUpdateImageTask;
    private Switch mSwichActivo;
    private MainNavigationDrawer mInstace;
    public static final CharSequence options_Upload_Photo[] = new CharSequence[]{"Choose from Library", "Take a Photo"};

    public PerfilFragment(MainNavigationDrawer mainNavigationDrawer) {
        mInstace = mainNavigationDrawer;
    }

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
        mSwichActivo = (Switch) mView.findViewById(R.id.sw_activo);

        setDataUser();


        return mView;


    }

    private void setDataUser() {

        mTxtUserName.setText(ConfigurationClass.getUserFirstNameUser(mContext));
        mEtxtLastName.setText(ConfigurationClass.getUserLastNameUserUser(mContext));
        mTxtUserEmail.setText(ConfigurationClass.getEmalUser(mContext));
        mTxtUserPassword.setText(ConfigurationClass.getPasswordUser(mContext));
        mTxtUserPhone.setText(ConfigurationClass.getTelUser(mContext));
        mSwichActivo.setChecked(ConfigurationClass.getIsActivo(mContext));
        String url = ConfigurationClass.getImageUser(mContext);
        if (url != null)
            ImageLoader.getInstance().displayImage(url.contains("http:") ? url : "http:" + url, mImgProfile, Utils.getImageLoaderOptionRouded());
        refreshPostion(ConfigurationClass.getCodigoPosicion(mContext));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = mContext.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            String url = picturePath;
            if (url != null)
                executeUpdateImage(encode(encodeTobase64(picturePath)));
        }
    }

    public String encode(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static byte[] streamToBytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (java.io.IOException e) {
        }
        return os.toByteArray();
    }

    public static byte[] encodeTobase64(String image) {
        byte[] bytearray = null;
        try {
            FileInputStream is = new FileInputStream(image);
            if (image != null)
                try {
                    bytearray = streamToBytes(is);
                } finally {
                    is.close();
                }
        } catch (Exception e) {
        }

        return bytearray;
    }


    private void executeUpdateImage(String urlImage) {

        mUpdateImageTask = new UpdateImageUser(mContext) {

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);


                if (result != null) {

                    result = result.replace("\"", "");
                    String url = result;
                    ConfigurationClass.setImageUser(mContext, result);
                    if (url != null)
                        ImageLoader.getInstance().displayImage(url.contains("http:") ? url : "http:" + url, mImgProfile, Utils.getImageLoaderOptionRouded());
                    Toast.makeText(mContext, R.string.success_update_user_image, Toast.LENGTH_SHORT);
                } else {
                    ConfirmDialogCustom dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_message), mContext.getString(R.string.perfil), mContext.getString(R.string.acept_text));
                    FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.add(dialog, null);
                    ft.commitAllowingStateLoss();
                }


            }

        };

        mUpdateImageTask.mCodigoUsuario = ConfigurationClass.getUserCod(mContext);
        mUpdateImageTask.mImageUser = urlImage;
        mUpdateImageTask.execute();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mImgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);

            }
        });


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


        mImgArquero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPostion(2);
            }
        });

        mImgDefensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPostion(3);
            }
        });

        mImgDelantero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPostion(4);
            }
        });


        mImgTodaElCampo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPostion(1);
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

                    setDataUserInShared();
                    dialog = new ConfirmDialogCustom(mContext.getString(R.string.success_update_user_info), mContext.getString(R.string.perfil), mContext.getString(R.string.acept_text));

                } else
                    dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_message), mContext.getString(R.string.perfil), mContext.getString(R.string.acept_text));


                FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                ft.add(dialog, null);
                ft.commitAllowingStateLoss();


            }
        };

        mUpdateAccountTask.mPosicion = mPositionSelected;
        mUpdateAccountTask.mApellido = mEtxtLastName.getText().toString();
        mUpdateAccountTask.mNombre = mTxtUserName.getText().toString();
        mUpdateAccountTask.mEmail = mTxtUserEmail.getText().toString();
        mUpdateAccountTask.mPassword = mTxtUserPassword.getText().toString();
        mUpdateAccountTask.mTelefono = mTxtUserPhone.getText().toString();
        mUpdateAccountTask.mCodigoUsuario = ConfigurationClass.getUserCod(mContext);
        mUpdateAccountTask.mCodigoTelefono = ConfigurationClass.getCodigoTelefono(mContext);
        mUpdateAccountTask.mIsActivo = mSwichActivo.isChecked();
        mUpdateAccountTask.execute();


    }

    private void refreshPostion(int position) {


        switch (position) {

            case 2:
                mImgArquero.setImageResource(R.drawable.arquero_activo);
                mImgDefensor.setImageResource(R.drawable.defensa_desactivado);
                mImgDelantero.setImageResource(R.drawable.delantero_desactivado);
                mImgTodaElCampo.setImageResource(R.drawable.todo_campo_desactivado);
                mDescPositionSelected = "Arquero";
                mTxtPosicion.setText(mDescPositionSelected);
                mPositionSelected = 2;
                break;

            case 3:
                mImgArquero.setImageResource(R.drawable.arquero_desactivado);
                mImgDefensor.setImageResource(R.drawable.defensa_activo);
                mImgDelantero.setImageResource(R.drawable.delantero_desactivado);
                mImgTodaElCampo.setImageResource(R.drawable.todo_campo_desactivado);
                mDescPositionSelected = "Defensor";
                mTxtPosicion.setText(mDescPositionSelected);
                mPositionSelected = 3;
                break;

            case 4:
                mImgArquero.setImageResource(R.drawable.arquero_desactivado);
                mImgDefensor.setImageResource(R.drawable.defensa_desactivado);
                mImgDelantero.setImageResource(R.drawable.delantero_activo);
                mImgTodaElCampo.setImageResource(R.drawable.todo_campo_desactivado);
                mDescPositionSelected = "Delantero";
                mTxtPosicion.setText(mDescPositionSelected);

                mPositionSelected = 4;
                break;
            case 1:
                mImgArquero.setImageResource(R.drawable.arquero_desactivado);
                mImgDefensor.setImageResource(R.drawable.defensa_desactivado);
                mImgDelantero.setImageResource(R.drawable.delantero_desactivado);
                mImgTodaElCampo.setImageResource(R.drawable.todo_campo_activo);
                mDescPositionSelected = "Todo el campo";
                mTxtPosicion.setText(mDescPositionSelected);
                mPositionSelected = 1;
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


    private void setDataUserInShared() {


        ConfigurationClass.setUserNameCompleted(mContext, mEtxtLastName.getText().toString() + " " + mTxtUserName.getText().toString());
        ConfigurationClass.setFirstNameUser(mContext, mTxtUserName.getText().toString());
        ConfigurationClass.setUserLastNameUser(mContext, mEtxtLastName.getText().toString());
        ConfigurationClass.setEmailUser(mContext, mTxtUserEmail.getText().toString());
        ConfigurationClass.setTelUser(mContext, mTxtUserPhone.getText().toString());
        ConfigurationClass.setPasswodUser(mContext, mTxtUserPassword.getText().toString());
        ConfigurationClass.setCodigoTelefono(mContext, ConfigurationClass.getCodigoTelefono(mContext));
        ConfigurationClass.setCodigoPosicion(mContext, mPositionSelected);
        ConfigurationClass.setDescPosicion(mContext, mDescPositionSelected);
        ConfigurationClass.setIsActivo(mContext, mSwichActivo.isChecked());


        if (mInstace != null)
            mInstace.setUserInfo();


    }
}
