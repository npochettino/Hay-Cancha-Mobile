package sempait.haycancha.services;

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
 * Created by martin on 17/11/15.
 */
public class PutStadiumCommentTask extends AsyncTask<Void, Void, String> {

    private String METHOD_NAME = "InsertarActualizarValoracionComplejo";
    private String SOAP_ACTION = Constants.SOAP_ACTION + "InsertarActualizarValoracionComplejo";

    public int mCodigoComplejo;
    public int mCodigoUsuario;
    public int mPuntaje;
    public String mComentario;
    public String mTitulo;

    public String mPassword;
    private Context mContext;


    public PutStadiumCommentTask(Context ctx) {
        mContext = ctx;
    }

    @Override
    protected String doInBackground(Void... params) {

        SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);

        request.addProperty("codigoComplejo", mCodigoComplejo);
        request.addProperty("puntaje", mPuntaje);
        request.addProperty("codigoUsuarioApp", mCodigoUsuario);
        request.addProperty("comentario", mComentario);
        request.addProperty("titulo", mTitulo);


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
