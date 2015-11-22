package sempait.haycancha.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.ConfirmDialogCustom;
import sempait.haycancha.R;
import sempait.haycancha.adapter.MyInvitationAdapter;
import sempait.haycancha.adapter.MyReservationsAdapter;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.models.Invitation;
import sempait.haycancha.models.Turn;
import sempait.haycancha.services.GET.GetMyInvitationsTask;

/**
 * Created by martin on 19/11/15.
 */
public class MyInvitationFragment extends BaseFragment {

    private ListView mList;
    private Context mActivity;
    private GetMyInvitationsTask mGetMyInvitations;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View mView = inflater.inflate(R.layout.list_general_view, container, false);
        mList = (ListView) mView.findViewById(R.id.list_general);

        getBaseActivity().setSectionTitle(mContext.getString(R.string.invitations));


//        setDataInvitaions();

        executeGetInvitationsServices();

        return mView;
    }

    private void setDataInvitaions() {

        List<Invitation> mListInvitation = new ArrayList<>();


        Invitation invitation = new Invitation();

        invitation.setCodigoCancha(1);
        invitation.setCodigoComplejo(2);
        invitation.setCodigoEstadoSolicitud(2);
        invitation.setDescripcionCancha("Cancha 1");
        invitation.setDescripcionComplejo("La playa");
        invitation.setDirection("Oroño 827");
        invitation.setFecha("21/11/2015");
        invitation.setHoraDesde("14:00");
        invitation.setCodigoEstadoSolicitud(2);
        invitation.setHoraHasta("15:00");
        invitation.setImagenUsuario("");
        invitation.setIsCreator(true);
        invitation.setNombreApellidoUsuario("Ezequiel Dalaison");
        invitation.setPrecio(200f);
        invitation.setPuntaje(4.5f);

        mListInvitation.add(invitation);

        Invitation invitation2 = new Invitation();

        invitation2.setCodigoCancha(1);
        invitation2.setCodigoComplejo(2);
        invitation2.setCodigoEstadoSolicitud(2);
        invitation2.setDescripcionCancha("Cancha 3");
        invitation2.setDescripcionComplejo("Heroes");
        invitation2.setDirection("Oroño 827");
        invitation2.setFecha("21/11/2015");
        invitation2.setHoraDesde("14:00");
        invitation2.setCodigoEstadoSolicitud(2);
        invitation2.setHoraHasta("15:00");
        invitation2.setImagenUsuario("");
        invitation2.setIsCreator(false);
        invitation2.setNombreApellidoUsuario("Juan Diego Vivero");
        invitation2.setPrecio(200f);
        invitation2.setPuntaje(3.5f);

        mListInvitation.add(invitation2);

        fillDataAdapter(mListInvitation);

    }

    private void executeGetInvitationsServices() {
        mGetMyInvitations = new GetMyInvitationsTask(mContext) {


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if (result != null) {

                    if (result.length() != 2) {

                        List<Invitation> invitations = new Gson().fromJson(result.toString(), new TypeToken<List<Invitation>>() {
                        }.getType());

                        fillDataAdapter(invitations);

                    } else {

                        ConfirmDialogCustom dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_message), mContext.getString(R.string.fields), mContext.getString(R.string.acept_text));

                        FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                        ft.add(dialog, null);
                        ft.commitAllowingStateLoss();
                    }


                }

            }
        };

        mGetMyInvitations.mCodigoUsuario = ConfigurationClass.getUserCod(mContext);
        mGetMyInvitations.execute();

    }


    private void fillDataAdapter(List<Invitation> listInvitation) {


        List<Invitation> invitations = new ArrayList<Invitation>();

        Boolean isPast = false;
        Boolean isPast1 = false;

        for (Invitation invitation : listInvitation) {

            if (invitation.getIsCreator()) {

                if (!isPast) {
                    Invitation invitation1 = new Invitation();
                    invitation1.setHeaderText("Enviadas");
                    invitation1.setIsHeader(true);
                    invitations.add(invitation1);
                    isPast = true;
                }

                invitation.setIsHeader(false);
                invitations.add(invitation);
            } else {
                if (!isPast1) {
                    Invitation invitation2 = new Invitation();
                    invitation2.setHeaderText("Recibidas");
                    invitation2.setIsHeader(true);
                    invitations.add(invitation2);
                    isPast1 = true;
                }
                invitation.setIsHeader(false);
                invitations.add(invitation);


            }


        }


        if (invitations != null && !invitations.isEmpty()) {

            MyInvitationAdapter turnAdapter = new MyInvitationAdapter(invitations, mContext, "");
            mList.setAdapter(turnAdapter);
        }
    }

}
