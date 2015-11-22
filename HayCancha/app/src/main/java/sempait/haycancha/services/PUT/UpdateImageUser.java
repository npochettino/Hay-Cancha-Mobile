package sempait.haycancha.services.PUT;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;

import sempait.haycancha.Constants;
import sempait.haycancha.base.BaseActivity;

/**
 * Created by martin on 19/11/15.
 */
public class UpdateImageUser extends AsyncTask<Void, Void, String> {

    private String METHOD_NAME = "InsertarActualizarImagenUsuarioApp";
    private String SOAP_ACTION = Constants.SOAP_ACTION + "InsertarActualizarImagenUsuarioApp";
    public int mCodigoUsuario;
    public String mImageUser;
    private Context mContext;


    public UpdateImageUser(Context ctx) {
        mContext = ctx;
    }

    @Override
    protected String doInBackground(Void... params) {

        SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);


        request.addProperty("codigoUsuarioApp", mCodigoUsuario);
        request.addProperty("base64", mImageUser);

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
