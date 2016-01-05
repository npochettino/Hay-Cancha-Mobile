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
//
//        List<Invitation> mListInvitation = new ArrayList<>();
//
//
//        Invitation invitation = new Invitation();
//
//        invitation.setCodigoCancha(1);
//        invitation.setCodigoComplejo(2);
//        invitation.setCodigoEstadoSolicitud(2);
//        invitation.setDescripcionCancha("Cancha 1");
//        invitation.setDescripcionComplejo("La playa");
//        invitation.setDirection("Oroño 827");
//        invitation.setFecha("21/11/2015");
//        invitation.setHoraDesde("14:00");
//        invitation.setCodigoEstadoSolicitud(1);
//        invitation.setHoraHasta("15:00");
//        invitation.setImagenUsuario("");
//        invitation.setIsCreator(false);
//        invitation.setNombreApellidoUsuario("Ezequiel Dalaison");
//        invitation.setPrecio(200f);
//        invitation.setPuntaje(4.5f);
//
//        mListInvitation.add(invitation);
//
//
//        Invitation invitation1 = new Invitation();
//
//        invitation1.setCodigoCancha(1);
//        invitation1.setCodigoComplejo(2);
//        invitation1.setCodigoEstadoSolicitud(2);
//        invitation1.setDescripcionCancha("Cancha 1");
//        invitation1.setDescripcionComplejo("La playa");
//        invitation1.setDirection("Oroño 827");
//        invitation1.setFecha("21/11/2015");
//        invitation1.setHoraDesde("14:00");
//        invitation1.setCodigoEstadoSolicitud(2);
//        invitation1.setHoraHasta("15:00");
//        invitation1.setImagenUsuario(ConfigurationClass.getImageUser(mContext));
//        invitation1.setIsCreator(true);
//        invitation1.setNombreApellidoUsuario("Juan Diego Vivero");
//        invitation1.setPrecio(200f);
//        invitation1.setPuntaje(4.5f);

//        mListInvitation.add(invitation1);

//        Invitation invitation2 = new Invitation();
//
//        invitation2.setCodigoCancha(1);
//        invitation2.setCodigoComplejo(2);
//        invitation2.setCodigoEstadoSolicitud(2);
//        invitation2.setDescripcionCancha("Cancha 3");
//        invitation2.setDescripcionComplejo("Heroes");
//        invitation2.setDirection("Oroño 827");
//        invitation2.setFecha("21/11/2015");
//        invitation2.setHoraDesde("14:00");
//        invitation2.setCodigoEstadoSolicitud(2);
//        invitation2.setHoraHasta("15:00");
//        invitation2.setImagenUsuario("");
//        invitation2.setIsCreator(false);
//        invitation2.setNombreApellidoUsuario("Juan Diego Vivero");
//        invitation2.setPrecio(200f);
//        invitation2.setPuntaje(3.5f);
//
//        mListInvitation.add(invitation2);
//
//
//        Invitation invitation3 = new Invitation();
//
//        invitation3.setCodigoCancha(1);
//        invitation3.setCodigoComplejo(2);
//        invitation3.setCodigoEstadoSolicitud(2);
//        invitation3.setDescripcionCancha("Cancha 3");
//        invitation3.setDescripcionComplejo("Heroes");
//        invitation3.setDirection("Oroño 827");
//        invitation3.setFecha("21/11/2015");
//        invitation3.setHoraDesde("14:00");
//        invitation3.setCodigoEstadoSolicitud(2);
//        invitation3.setHoraHasta("15:00");
//        invitation3.setImagenUsuario("");
//        invitation3.setIsCreator(false);
//        invitation3.setNombreApellidoUsuario("Juan Diego Vivero");
//        invitation3.setPrecio(200f);
//        invitation3.setPuntaje(3.5f);
//
//        mListInvitation.add(invitation3);
//
//
//        Invitation invitation4 = new Invitation();
//
//        invitation4.setCodigoCancha(1);
//        invitation4.setCodigoComplejo(2);
//        invitation4.setCodigoEstadoSolicitud(2);
//        invitation4.setDescripcionCancha("Cancha 3");
//        invitation4.setDescripcionComplejo("Heroes");
//        invitation4.setDirection("Oroño 827");
//        invitation4.setFecha("21/11/2015");
//        invitation4.setHoraDesde("14:00");
//        invitation4.setCodigoEstadoSolicitud(2);
//        invitation4.setHoraHasta("15:00");
//        invitation4.setImagenUsuario("");
//        invitation4.setIsCreator(true);
//        invitation4.setNombreApellidoUsuario("Juan Diego Vivero");
//        invitation4.setPrecio(200f);
//        invitation4.setPuntaje(3.5f);
//
//        mListInvitation.add(invitation4);
//
//        Invitation invitation5 = new Invitation();
//
//        invitation5.setCodigoCancha(1);
//        invitation5.setCodigoComplejo(2);
//        invitation5.setCodigoEstadoSolicitud(2);
//        invitation5.setDescripcionCancha("Cancha 1");
//        invitation5.setDescripcionComplejo("La playa");
//        invitation5.setDirection("Oroño 827");
//        invitation5.setFecha("21/11/2015");
//        invitation5.setHoraDesde("14:00");
//        invitation5.setCodigoEstadoSolicitud(2);
//        invitation5.setHoraHasta("15:00");
//        invitation5.setImagenUsuario("");
//        invitation5.setIsCreator(true);
//        invitation5.setNombreApellidoUsuario("Ezequiel Dalaison");
//        invitation5.setPrecio(200f);
//        invitation5.setPuntaje(4.5f);
//
//        mListInvitation.add(invitation5);
//
//
//        Invitation invitation6 = new Invitation();
//
//        invitation6.setCodigoCancha(1);
//        invitation6.setCodigoComplejo(2);
//        invitation6.setCodigoEstadoSolicitud(2);
//        invitation6.setDescripcionCancha("Cancha 1");
//        invitation6.setDescripcionComplejo("La playa");
//        invitation6.setDirection("Oroño 827");
//        invitation6.setFecha("21/11/2015");
//        invitation6.setHoraDesde("14:00");
//        invitation6.setCodigoEstadoSolicitud(2);
//        invitation6.setHoraHasta("15:00");
//        invitation6.setImagenUsuario("");
//        invitation6.setIsCreator(true);
//        invitation6.setNombreApellidoUsuario("Ezequiel Dalaison");
//        invitation6.setPrecio(200f);
//        invitation6.setPuntaje(4.5f);
//
//        mListInvitation.add(invitation6);
//
//        Invitation invitation7 = new Invitation();
//
//        invitation7.setCodigoCancha(1);
//        invitation7.setCodigoComplejo(2);
//        invitation7.setCodigoEstadoSolicitud(2);
//        invitation7.setDescripcionCancha("Cancha 1");
//        invitation7.setDescripcionComplejo("La playa");
//        invitation7.setDirection("Oroño 827");
//        invitation7.setFecha("21/11/2015");
//        invitation7.setHoraDesde("14:00");
//        invitation7.setCodigoEstadoSolicitud(2);
//        invitation7.setHoraHasta("15:00");
//        invitation7.setImagenUsuario("");
//        invitation7.setIsCreator(true);
//        invitation7.setNombreApellidoUsuario("Ezequiel Dalaison");
//        invitation7.setPrecio(200f);
//        invitation7.setPuntaje(4.5f);
//
//        mListInvitation.add(invitation7);


//        fillDataAdapter(mListInvitation);

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

                    }


                } else {
                    ConfirmDialogCustom dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_message), mContext.getString(R.string.fields), mContext.getString(R.string.acept_text));
                    FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.add(dialog, null);
                    ft.commitAllowingStateLoss();
                }

            }
        };

        mGetMyInvitations.mCodigoUsuario = ConfigurationClass.getUserCod(mContext);
        mGetMyInvitations.execute();

    }


    private void fillDataAdapter(List<Invitation> listInvitation) {


        List<Invitation> listInvitationsDone = new ArrayList<Invitation>();

        Boolean isPast = false;
        Boolean isPast1 = false;

        for (Invitation invitation : listInvitation) {

            if (invitation.getIsCreator()) {

                if (!isPast) {
                    Invitation invitation1 = new Invitation();
                    invitation1.setHeaderText("Enviadas");
                    invitation1.setIsHeader(true);
                    listInvitationsDone.add(invitation1);
                    isPast = true;
                }

                invitation.setIsHeader(false);
                listInvitationsDone.add(invitation);
            } else {
                if (!isPast1) {
                    Invitation invitation2 = new Invitation();
                    invitation2.setHeaderText("Recibidas");
                    invitation2.setIsHeader(true);
                    listInvitationsDone.add(invitation2);
                    isPast1 = true;
                }
                invitation.setIsHeader(false);
                listInvitationsDone.add(invitation);


            }


        }


        if (listInvitationsDone != null && !listInvitationsDone.isEmpty()) {

            MyInvitationAdapter turnAdapter = new MyInvitationAdapter(listInvitationsDone, mContext, "");
            mList.setAdapter(turnAdapter);
        }
    }

}
