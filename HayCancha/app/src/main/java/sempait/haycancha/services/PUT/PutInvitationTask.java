package sempait.haycancha.services.PUT;

import android.content.Context;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import sempait.haycancha.Constants;
import sempait.haycancha.base.BaseActivity;

/**
 * Created by martin on 18/11/15.
 */
public class PutInvitationTask extends AsyncTask<Void, Void, String> {

    private String METHOD_NAME = "InsertarActualizarSolicitud";
    private String SOAP_ACTION = Constants.SOAP_ACTION + "InsertarActualizarSolicitud";

    public int mCodigoSolicitud;
    public int mCodigoTurnoVariable;
    public int mcodigoUsuarioAppInvitado;
    public int mCodigoEstadoSolicitud;


    public String mPassword;
    private Context mContext;


    public PutInvitationTask(Context ctx) {
        mContext = ctx;
    }

    @Override
    protected String doInBackground(Void... params) {

        SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);

        request.addProperty("codigoSolicitud", mCodigoSolicitud);
        request.addProperty("codigoTurnoVariable", mCodigoTurnoVariable);
        request.addProperty("codigoUsuarioAppInvitado", mcodigoUsuarioAppInvitado);
        request.addProperty("codigoEstadoSolicitud", mCodigoEstadoSolicitud);


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
