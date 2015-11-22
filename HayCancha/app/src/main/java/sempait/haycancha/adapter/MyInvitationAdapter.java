package sempait.haycancha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.math.BigDecimal;
import java.util.List;

import sempait.haycancha.R;
import sempait.haycancha.Utils;
import sempait.haycancha.models.Invitation;
import sempait.haycancha.models.Turn;
import sempait.haycancha.services.GET.GetLocalTask;
import sempait.haycancha.services.LocationManager;

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

        final viewHolder holder;

        final Invitation invitation = invitationList.get(position);

        if (invitation.getIsHeader()) {


            if (convertView == null) {

                convertView = mInflater.inflate(R.layout.row_invitations_title_list, null);
                holder = new viewHolder();
                holder.titleInvitation = (TextView) convertView.findViewById(R.id.txt_ttle_invitation);

                convertView.setTag(holder);

            } else {
                holder = (viewHolder) convertView.getTag();
            }

            holder.titleInvitation.setText(invitation.getHeaderText());

        } else {


            if (convertView == null) {
                holder = new viewHolder();


                convertView = mInflater.inflate(R.layout.row_invitations_list, null);

                holder.name = (TextView) convertView.findViewById(R.id.txt_field_row_name);
                holder.direction = (TextView) convertView.findViewById(R.id.txt_field_row_direction);
                holder.distance = (TextView) convertView.findViewById(R.id.txt_field_row_distance);
                holder.hourTo = (TextView) convertView.findViewById(R.id.txt_field_row_hoour_to);
                holder.rating = (LinearLayout) convertView.findViewById(R.id.lyt_stars_rating);
                holder.state = (ImageView) convertView.findViewById(R.id.img_field_row_state);
                holder.fieldCod = (TextView) convertView.findViewById(R.id.txt_field_row_field_cod);
                holder.logoStadium = (ImageView) convertView.findViewById(R.id.img_logo);


                convertView.setTag(holder);


            } else
                holder = (viewHolder) convertView.getTag();


            holder.name.setText(invitation.getNombreApellidoUsuario());
            holder.direction.setText(invitation.getDirection());
//        holder.distance.setText(" (A " + String.valueOf(round(turn.calculateDistanceTo(LocationManager.getInstance(mContext).getLocation()) / 1000, 2)) + " Km)");
//            Utils.setupRating(invitation.getPuntaje(), holder.rating);
            holder.hourTo.setText(invitation.getHoraDesde() + " hs");
            holder.fieldCod.setText(invitation.getDescripcionCancha() + " ($ " + invitation.getPrecio() + ")");
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

        }

        return convertView;

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

    }


}
