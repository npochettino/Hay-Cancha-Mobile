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

import java.util.List;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.ConfirmDialogCustom;
import sempait.haycancha.R;
import sempait.haycancha.Utils;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.models.Turn;
import sempait.haycancha.models.User;
import sempait.haycancha.services.GET.GetLocalTask;
import sempait.haycancha.services.PUT.PutInvitationTask;
import sempait.haycancha.services.PUT.PutTurnTask;

/**
 * Created by martin on 18/11/15.
 */
public class PlayerListAdapter extends BaseAdapter {


    private List<User> userList;
    private LayoutInflater mInflater;
    private Context mContext;
    private PutTurnTask mSaveTurnTask;
    private GetLocalTask mGetLocalTask;
    private Turn mTurn;
    private PutInvitationTask mPutInvitation;


    public PlayerListAdapter(List<User> users, Context context, Turn turn) {
        userList = users;
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mTurn = turn;


    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class viewHolder {

        TextView name;
        TextView direction;
        TextView distance;
        TextView hourTo;
        ImageView logoStadium;
        TextView fieldCod;
        ImageView save;
        LinearLayout rating;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final viewHolder holder;
        final User user = userList.get(position);

        if (convertView == null) {

            holder = new viewHolder();


            convertView = mInflater.inflate(R.layout.row_player_list, null);

            holder.name = (TextView) convertView.findViewById(R.id.txt_field_row_name);
            holder.direction = (TextView) convertView.findViewById(R.id.txt_field_row_direction);
            holder.distance = (TextView) convertView.findViewById(R.id.txt_field_row_distance);
            holder.hourTo = (TextView) convertView.findViewById(R.id.txt_field_row_hoour_to);
            holder.rating = (LinearLayout) convertView.findViewById(R.id.lyt_stars_rating);
            holder.save = (ImageView) convertView.findViewById(R.id.txt_field_row_save_me);
            holder.fieldCod = (TextView) convertView.findViewById(R.id.txt_field_row_field_cod);
            holder.logoStadium = (ImageView) convertView.findViewById(R.id.img_logo);


            convertView.setTag(holder);


        } else
            holder = (viewHolder) convertView.getTag();


        holder.name.setText(user.getApellido() + " " + user.getNombre());
        holder.direction.setText(user.getTelefono());
//        holder.distance.setText(" (A " + String.valueOf(round(turn.calculateDistanceTo(LocationManager.getInstance(mContext).getLocation()) / 1000, 2)) + " Km)");
        if (user.getPuntaje() != 0)
            setupRating(user.getPuntaje(), holder.rating);
//        holder.hourTo.setText(mTurn.getHoraDesde() + " hs");
        holder.fieldCod.setText(user.getDescPosicion());
        if (user.getImagen() != null)
            ImageLoader.getInstance().displayImage(user.getImagen().contains("http:") ? user.getImagen() : "http:" + user.getImagen(), holder.logoStadium, Utils.getImageLoaderOptionRouded());


        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                executeServiceInvitateTask(user);
            }
        });

        return convertView;

    }

    private void executeServiceInvitateTask(final User user) {
        mPutInvitation = new PutInvitationTask(mContext) {


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                ConfirmDialogCustom dialog;
                if (result != null) {


                    ParseQuery pushQuery = ParseInstallation.getQuery();
                    pushQuery.whereEqualTo("deviceToken", user.getCodigoTelefono());
                    ParsePush push = new ParsePush();

                    JSONObject obj = new JSONObject();

                    try {
                        obj.put("deep_link", "invitation");
                        obj.put("message", ConfigurationClass.getUserNameCompleted(mContext) + " " + "te ha invitado a un partido");
                        obj.put("title", "HayCancha!");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    push.setQuery(pushQuery);
                    push.setData(obj);
                    push.sendInBackground();


                    dialog = new ConfirmDialogCustom(mContext.getString(R.string.success_invite_player_message), mContext.getString(R.string.invitations), mContext.getString(R.string.acept_text));



                } else
                    dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_message), mContext.getString(R.string.create_account_title), mContext.getString(R.string.acept_text));


                FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                ft.add(dialog, null);
                ft.commitAllowingStateLoss();


            }
        };


        mPutInvitation.mCodigoEstadoSolicitud = 0;
        mPutInvitation.mCodigoEstadoSolicitud = 1;
        mPutInvitation.mCodigoTurnoVariable = mTurn.getCodigoTurno();
        mPutInvitation.mcodigoUsuarioAppInvitado = user.getCodigoUsuario();
        mPutInvitation.execute();

    }

    private void setupRating(float rating, LinearLayout holder) {

        int intRating = (int) rating;
        int doubleRating = (int) (rating * 2);

        switch (intRating) {
            case 5: // 5
                ((ImageView) holder.findViewById(R.id.img_billboard_star_5)).setImageResource(R.drawable.star_on);
            case 4: // 4
                ((ImageView) holder.findViewById(R.id.img_billboard_star_4)).setImageResource(R.drawable.star_on);
                if (doubleRating == 9)
                    ((ImageView) holder.findViewById(R.id.img_billboard_star_5)).setImageResource(R.drawable.star_half);
            case 3: // 3
                ((ImageView) holder.findViewById(R.id.img_billboard_star_3)).setImageResource(R.drawable.star_on);
                if (doubleRating == 7)
                    ((ImageView) holder.findViewById(R.id.img_billboard_star_4)).setImageResource(R.drawable.star_half);
            case 2: // 2
                ((ImageView) holder.findViewById(R.id.img_billboard_star_2)).setImageResource(R.drawable.star_on);
                if (doubleRating == 5)
                    ((ImageView) holder.findViewById(R.id.img_billboard_star_3)).setImageResource(R.drawable.star_half);
            case 1: // 1
                ((ImageView) holder.findViewById(R.id.img_billboard_star_1)).setImageResource(R.drawable.star_on);
                if (doubleRating == 3)
                    ((ImageView) holder.findViewById(R.id.img_billboard_star_2)).setImageResource(R.drawable.star_half);
            case 0:
                if (doubleRating == 1)
                    ((ImageView) holder.findViewById(R.id.img_billboard_star_1)).setImageResource(R.drawable.star_half);
                break;
        }

    }

}
