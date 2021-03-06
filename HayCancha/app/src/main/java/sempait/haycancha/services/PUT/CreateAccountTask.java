package sempait.haycancha.services.PUT;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import sempait.haycancha.Constants;
import sempait.haycancha.base.BaseActivity;

/**
 * Created by martin on 02/11/15.
 */
public class CreateAccountTask extends AsyncTask<Void, Void, String> {


    private String METHOD_NAME = "InsertarActualizarUsuarioApp";
    private String SOAP_ACTION = Constants.SOAP_ACTION + "InsertarActualizarUsuarioApp";
    public int mCodigoUsuario;
    public String mNombre;
    public String mApellido;
    public String mEmail;
    public String mPassword;
    public String mTelefono;
    public int mPosicion;
    public String mCodigoTelefono;
    public Boolean mIsActivo;
    public String mUrlImage;

    public CreateAccountTask() {
        super();
    }

    private Context mContext;


    public CreateAccountTask(Context ctx) {
        mContext = ctx;
    }

    @Override
    protected String doInBackground(Void... params) {


        SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);


        request.addProperty("codigoUsuario", mCodigoUsuario);
        request.addProperty("nombre", mNombre);
        request.addProperty("apellido", mApellido);
        request.addProperty("mail", mEmail);
        request.addProperty("contraseña", mPassword);
        request.addProperty("telefono", mTelefono);
        request.addProperty("codigoPosicion", mPosicion);
        request.addProperty("codigoTelefono", mCodigoTelefono);
        request.addProperty("isActivo", mIsActivo);
        request.addProperty("imagen", mUrlImage);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL);


        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive result = (SoapPrimitive) envelope.getResponse();


            return result.toString();

        } catch (Exception e) {

        }
        return null;


    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ((BaseActivity) mContext).dismissLoadingView();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        ((BaseActivity) mContext).showLoadingView();
    }
}
