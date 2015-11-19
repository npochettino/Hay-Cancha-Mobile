package sempait.haycancha;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by martin on 29/03/15.
 */
public class ConfigurationClass {


    private static final String PREFS_NAME = "hay_cancha_settings";
    private static final String USER_NAME_COMPLETED = "user_name_completed";
    private static final String USER_FIRST_NAME = "user_first_name";
    private static final String USER_LAST_NAME = "user_last_name";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_COD = "codigo_user";
    private static final String CODIGO_SORTEO = "codigo_sorteo";
    private static final String CANTIDAD_TIROS_USUARIO = "cantidad_tiros_usuario";
    private static final String CANTIDAD_PREMIOS_USUARIO = "cantidad_premios_usuario";
    private static final String CANTIDAD_PREMIOS_TOTALES = "cantidad_premios_totales";
    private static final String CODIGO_PREMIO = "codigo_premio";
    private static final String SONIDO_STATE = "sonido_state";
    private static final String EMAIL_USER = "email_user";
    private static final String PASSWORD_USER = "password_user";
    private static final String TELEFONO_USER = "telefono_user";
    private static final String DNI_USER = "dni_user";
    private static final String CODIGO_POSITION = "codigo_position";
    private static final String DESC_POSITION = "desc_position";
    private static final String UUID = "uuid";
    private static final String IS_ACTIVO = "is_activo";
    private static final String IMAGE_USER = "image_user";


    public static SharedPreferences sharedPref(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0);
    }


    public static void setUserNameCompleted(Context context, String userName) {
        sharedPref(context).edit().putString(USER_NAME_COMPLETED, userName).commit();
    }

    public static String getUserNameCompleted(Context context) {
        return sharedPref(context).getString(USER_NAME_COMPLETED, null);
    }

    public static void setUserCod(Context context, int userCod) {
        sharedPref(context).edit().putInt(USER_COD, userCod).commit();
    }

    public static int getUserCod(Context context) {
        return sharedPref(context).getInt(USER_COD, 0);
    }


    public static void setCodigoSorteo(Context context, String CodigoSorteo) {

        sharedPref(context).edit().putString(CODIGO_SORTEO, CodigoSorteo).commit();
    }

    public static String getCodigoSorteo(Context context) {
        return sharedPref(context).getString(CODIGO_SORTEO, null);
    }

    public static void setcantidadTirosPorUsuario(Context context, String CodigoSorteo) {

        sharedPref(context).edit().putString(CANTIDAD_TIROS_USUARIO, CodigoSorteo).commit();
    }

    public static String getcantidadTirosPorUsuario(Context context) {
        return sharedPref(context).getString(CANTIDAD_TIROS_USUARIO, null);
    }


    public static void setcantidadPremiosPorUsuario(Context context, String CodigoSorteo) {

        sharedPref(context).edit().putString(CANTIDAD_PREMIOS_USUARIO, CodigoSorteo).commit();
    }

    public static String getcantidadPremiosPorUsuario(Context context) {
        return sharedPref(context).getString(CANTIDAD_PREMIOS_USUARIO, null);
    }


    public static void setcantidadPremiosTotales(Context context, String CodigoSorteo) {

        sharedPref(context).edit().putString(CANTIDAD_PREMIOS_TOTALES, CodigoSorteo).commit();
    }

    public static String getcantidadPremiosTotales(Context context) {
        return sharedPref(context).getString(CANTIDAD_PREMIOS_TOTALES, null);
    }


    public static void setcodigoPremio(Context context, String CodigoSorteo) {

        sharedPref(context).edit().putString(CODIGO_PREMIO, CodigoSorteo).commit();
    }

    public static String getcodigoPremio(Context context) {
        return sharedPref(context).getString(CODIGO_PREMIO, null);
    }


    public static void setStateSonido(Boolean state, Context mContext) {


        sharedPref(mContext).edit().putBoolean(SONIDO_STATE, state).commit();


    }

    public static Boolean getSonidoState(Context context) {
        return sharedPref(context).getBoolean(SONIDO_STATE, true);
    }

    public static void setEmailUser(Context mContext, String email) {


        sharedPref(mContext).edit().putString(EMAIL_USER, email).commit();


    }

    public static String getEmalUser(Context context) {
        return sharedPref(context).getString(EMAIL_USER, null);
    }

    public static void setTelUser(Context mContext, String telefono) {


        sharedPref(mContext).edit().putString(TELEFONO_USER, telefono).commit();


    }

    public static String getTelUser(Context context) {
        return sharedPref(context).getString(TELEFONO_USER, null);
    }

    public static void setDNIUser(Context mContext, String dni) {
        sharedPref(mContext).edit().putString(DNI_USER, dni).commit();
    }

    public static String getDNIUser(Context context) {
        return sharedPref(context).getString(DNI_USER, null);
    }

    public static void setPasswodUser(Context mContext, String password) {
        sharedPref(mContext).edit().putString(PASSWORD_USER, password).commit();
    }

    public static String getPasswordUser(Context context) {
        return sharedPref(context).getString(PASSWORD_USER, null);
    }

    public static void setFirstNameUser(Context mContext, String password) {


        sharedPref(mContext).edit().putString(USER_FIRST_NAME, password).commit();


    }

    public static String getUserFirstNameUser(Context context) {
        return sharedPref(context).getString(USER_FIRST_NAME, null);
    }

    public static void setUserLastNameUser(Context mContext, String password) {


        sharedPref(mContext).edit().putString(USER_LAST_NAME, password).commit();


    }

    public static String getUserLastNameUserUser(Context context) {
        return sharedPref(context).getString(USER_LAST_NAME, null);
    }


    public static void setCodigoPosicion(Context mContext, int codigoPosition) {
        sharedPref(mContext).edit().putInt(CODIGO_POSITION, codigoPosition).commit();
    }

    public static int getCodigoPosicion(Context context) {
        return sharedPref(context).getInt(CODIGO_POSITION, 0);
    }


    public static void setDescPosicion(Context mContext, String descPosition) {
        sharedPref(mContext).edit().putString(DESC_POSITION, descPosition).commit();
    }

    public static String getDescPosition(Context context) {
        return sharedPref(context).getString(DESC_POSITION, null);
    }

    public static void setCodigoTelefono(Context mContext, String uuid) {
        sharedPref(mContext).edit().putString(UUID, uuid).commit();

    }

    public static String getCodigoTelefono(Context context) {
        return sharedPref(context).getString(UUID, null);
    }


    public static void setIsActivo(Context mContext, Boolean isActivo) {
        sharedPref(mContext).edit().putBoolean(IS_ACTIVO, isActivo).commit();

    }

    public static Boolean getIsActivo(Context context) {
        return sharedPref(context).getBoolean(IS_ACTIVO, false);
    }

    public static void setImageUser(Context mContext, String imgaeUrl) {
        sharedPref(mContext).edit().putString(IMAGE_USER, imgaeUrl).commit();

    }

    public static String getImageUser(Context context) {
        return sharedPref(context).getString(IMAGE_USER, null);
    }


}
