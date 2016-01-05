package sempait.haycancha.adapter;

import android.content.Context;

import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.ConfirmDialogCustom;
import sempait.haycancha.ConfirmDialogCustom2;
import sempait.haycancha.R;
import sempait.haycancha.Utils;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.models.Invitation;
import sempait.haycancha.services.GET.GetLocalTask;
import sempait.haycancha.services.PUT.PutInvitationTask;

/**
 * Created by martin on 21/11/15.
 */
public class MyInvitationAdapter extends BaseAdapter {

    private static final int TYPE_FEATURED = 0;
    private static final int TYPE_NO_FEATURED = 1;

    private List<Invitation> invitationList;
    private LayoutInflater mInflater;
    private Context mContext;
    private GetLocalTask mGetLocalTask;
    private String mDate;
    private PutInvitationTask mPutInvitation;


    public MyInvitationAdapter(List<Invitation> invitations, Context context, String date) {
        invitationList = invitations;
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDate = date;

    }


    @Override
    public int getCount() {
        if (invitationList == null)
            return 0;
        else
            return invitationList.size();
    }


    @Override
    public Object getItem(int position) {
        return invitationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        viewHolder holder;
        View view = null;
        final Invitation invitation = invitationList.get(position);

        if (invitation.getIsHeader()) {


            if (view == null) {
                view = mInflater.inflate(R.layout.row_invitations_title_list, null);
                holder = new viewHolder();

                holder.titleInvitation = (TextView) view.findViewById(R.id.txt_ttle_invitation);

                view.setTag(holder);

            } else {
                holder = (viewHolder) view.getTag();
            }

            holder.titleInvitation.setText(invitation.getHeaderText());

        } else {


            if (view == null) {
                view = mInflater.inflate(R.layout.row_invitations_list, null);

                holder = new viewHolder();

                holder.name = (TextView) view.findViewById(R.id.txt_field_row_name);
                holder.direction = (TextView) view.findViewById(R.id.txt_field_row_direction);
                holder.distance = (TextView) view.findViewById(R.id.txt_field_row_distance);
                holder.hourTo = (TextView) view.findViewById(R.id.txt_field_row_hoour_to);
                holder.rating = (LinearLayout) view.findViewById(R.id.lyt_stars_rating);
                holder.state = (ImageView) view.findViewById(R.id.img_field_row_state);
                holder.fieldCod = (TextView) view.findViewById(R.id.txt_field_row_field_cod);
                holder.logoStadium = (ImageView) view.findViewById(R.id.img_logo);
                holder.date = (TextView) view.findViewById(R.id.txt_field_row_date);


                view.setTag(holder);


            } else {
                holder = (viewHolder) view.getTag();
            }


            holder.name.setText(invitation.getNombreApellidoUsuario());
            holder.direction.setText(invitation.getDescripcionComplejo());
            holder.hourTo.setText(invitation.getHoraDesde() + " hs");
            holder.date.setText(invitation.getFecha());
            if (invitation.getPuntaje() != null)
                Utils.setupRating(invitation.getPuntaje(), holder.rating);
            holder.fieldCod.setText(invitation.getDescripcionComplejo() + " ($ " + invitation.getPrecio() + ")");
            if (invitation.getImagenUsuario() != null)
                ImageLoader.getInstance().displayImage(invitation.getImagenUsuario().contains("http:") ? invitation.getImagenUsuario() : "http:" + invitation.getImagenUsuario(), holder.logoStadium, Utils.getImageLoaderOptionRouded());


            switch (invitation.getCodigoEstadoSolicitud()) {

                case 1:
                    holder.state.setImageResource(R.drawable.pendiente);
                    break;

                case 2:
                    holder.state.setImageResource(R.drawable.confirmado);
                    break;
                case 3:
                    holder.state.setImageResource(R.drawable.rechazado);
                    break;
            }

            if (invitation.getCodigoEstadoSolicitud() == 1 && !invitation.getIsCreator()) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ConfirmDialogCustom2 dialog = new ConfirmDialogCustom2("¿Queres ir a jugar con" + " " + invitation.getNombreApellidoUsuario() + "?", mContext.getString(R.string.invitations), "Rechazar", "Aceptar") {


                            @Override
                            public void actionButtonLeft() {
                                super.actionButtonLeft();

                                executeServiceInvitateTask(invitation, 3);

                            }

                            @Override
                            public void actionButtonRigth() {
                                super.actionButtonRigth();
                                executeServiceInvitateTask(invitation, 2);

                            }
                        };
                        FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                        ft.add(dialog, null);
                        ft.commitAllowingStateLoss();
                    }
                });

            }
        }

        return view;

    }

    private void executeServiceInvitateTask(final Invitation invitation, int cod) {
        mPutInvitation = new PutInvitationTask(mContext) {


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                ConfirmDialogCustom dialog;
                if (result != null) {


                    ParseQuery pushQuery = ParseInstallation.getQuery();
                    pushQuery.whereEqualTo("deviceToken", invitation.getCodigoTelefono());
                    ParsePush push = new ParsePush();

                    JSONObject obj = new JSONObject();

                    try {
                        obj.put("deep_link", "invitation");
                        obj.put("message", ConfigurationClass.getUserNameCompleted(mContext) + " " + "ha respondido");
                        obj.put("title", "HayCancha!");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    push.setQuery(pushQuery);
                    push.setData(obj);
                    push.sendInBackground();


                    dialog = new ConfirmDialogCustom(mContext.getString(R.string.success_response_player_message), mContext.getString(R.string.invitations), mContext.getString(R.string.acept_text));


                } else
                    dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_message), mContext.getString(R.string.create_account_title), mContext.getString(R.string.acept_text));


                FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                ft.add(dialog, null);
                ft.commitAllowingStateLoss();


            }
        };


        mPutInvitation.mCodigoSolicitud = invitation.getCodigoSolicitud();
        mPutInvitation.mCodigoEstadoSolicitud = cod;
        mPutInvitation.mCodigoTurnoVariable = invitation.getCodigoTurno();
        mPutInvitation.mcodigoUsuarioAppInvitado = invitation.getCodigoUsuarioAppInvitado();
        mPutInvitation.execute();

    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }


    private static class viewHolder {

        TextView name;
        TextView direction;
        TextView distance;
        TextView hourTo;
        ImageView logoStadium;
        TextView fieldCod;
        ImageView state;
        LinearLayout rating;
        TextView titleInvitation;
        TextView date;

    }


}
