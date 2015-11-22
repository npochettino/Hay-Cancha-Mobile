package sempait.haycancha.services.GET;

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
public class LoginTask extends AsyncTask<Void, Void, String> {
    private String METHOD_NAME = "RecuperarUsuarioApp";
    private String SOAP_ACTION = Constants.SOAP_ACTION + "RecuperarUsuarioApp";
    public String mEmail;
    public String mPassword;
    private Context mContext;


    public LoginTask(Context ctx) {
        mContext = ctx;
    }

    @Override
    protected String doInBackground(Void... params) {

        SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);

        request.addProperty("mail", mEmail);
        request.addProperty("contraseña", mPassword);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL);


        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive result = (SoapPrimitive) envelope.getResponse();


            return result.toString();

        } catch (Exception e) {
            Log.d("", "");

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
