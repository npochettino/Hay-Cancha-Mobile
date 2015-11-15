package sempait.haycancha.services;

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
 * Created by martin on 12/11/15.
 */
public class SaveTurnTask extends AsyncTask<Void, Void, String> {

    private String METHOD_NAME = "InsertarActualizarTurnoVariable";
    private String SOAP_ACTION = Constants.SOAP_ACTION + "InsertarActualizarTurnoVariable";
    public int mCodigoCancha;
    public String mFecha;
    public int mHDesde;
    public int mHHasta;
    public int mCodigoUsuario;

    public String mPassword;
    private Context mContext;


    public SaveTurnTask(Context ctx) {
        mContext = ctx;
    }

    @Override
    protected String doInBackground(Void... params) {

        SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);

        request.addProperty("codigoTurnoVariable", 0);
        request.addProperty("codigoCancha", mCodigoCancha);
        request.addProperty("fecha", mFecha);
        request.addProperty("horaDesde", mHDesde);
        request.addProperty("horaHasta", mHHasta);
        request.addProperty("codigoUsuarioApp", mCodigoUsuario);
        request.addProperty("observaciones", "hola");

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL);


        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive result = (SoapPrimitive) envelope.getResponse();


            return result.toString();

        } catch (Exception e) {
            Log.d("", e.getMessage());

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
