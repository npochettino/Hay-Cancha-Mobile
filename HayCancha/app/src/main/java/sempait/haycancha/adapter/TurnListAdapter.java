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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.math.BigDecimal;
import java.util.List;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.ConfirmDialogCustom;
import sempait.haycancha.R;
import sempait.haycancha.Utils;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.fragment.StadiumDetailFragment;
import sempait.haycancha.models.Stadium;
import sempait.haycancha.models.Turn;
import sempait.haycancha.services.GET.GetLocalTask;
import sempait.haycancha.services.PUT.PutTurnTask;


public class TurnListAdapter extends BaseAdapter {

    private static final int TYPE_FEATURED = 0;
    private static final int TYPE_NO_FEATURED = 1;

    private List<Turn> turnList;
    private LayoutInflater mInflater;
    private Context mContext;
    private PutTurnTask mSaveTurnTask;
    private GetLocalTask mGetLocalTask;
    private String mDate;


    public TurnListAdapter(List<Turn> turns, Context context, String date) {
        turnList = turns;
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDate = date;

    }


    @Override
    public int getCount() {
        if (turnList == null)
            return 0;
        else
            return turnList.size();
    }


    @Override
    public Object getItem(int position) {
        return turnList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final viewHolder holder;
        final Turn turn = turnList.get(position);

        if (convertView == null) {

            holder = new viewHolder();


            convertView = mInflater.inflate(R.layout.row_turn_list, null);

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


        holder.name.setText(turn.getDescripcionComplejo());
        holder.direction.setText(turn.getDireccion());
/*
        holder.distance.setText(" (A " + String.valueOf(round(turn.calculateDistanceTo(LocationManager.getInstance(mContext).getLocation()) / 1000, 2)) + " Km)");
*/
        Utils.setupRating(turn.getPuntajeComplejo(), holder.rating);
        holder.hourTo.setText(turn.getHoraDesde() + " hs");
        holder.fieldCod.setText(turn.getDescripcionCancha() + " ($ " + turn.getPrecio() + ")");
        ImageLoader.getInstance().displayImage(turn.getImagenComplejo().contains("http:") ? turn.getImagenComplejo() : "http:" + turn.getImagenComplejo(), holder.logoStadium, Utils.getImageLoaderOptionRouded());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeGetLocalService(turn);
            }


        });

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeSaveTurnService(turn, holder.save);
            }
        });

        return convertView;

    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    private void executeGetLocalService(final Turn turn) {

        mGetLocalTask = new GetLocalTask(mContext) {
            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                if (result != null) {

                    List<Stadium> stadiums = new Gson().fromJson(result.toString(), new TypeToken<List<Stadium>>() {
                    }.getType());

                    Stadium stadium = stadiums.get(0);
                    stadium.setPuntajeComplejo(turn.getPuntajeComplejo());

                    ((BaseActivity) mContext).replaceInnerFragmentWhitFLip(new StadiumDetailFragment().newInstance(stadium), true);


                } else {

                    ConfirmDialogCustom dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_message), mContext.getString(R.string.fields), mContext.getString(R.string.acept_text));

                    FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.add(dialog, null);
                    ft.commitAllowingStateLoss();

                }


            }
        };

        mGetLocalTask.mCodigoComplejo = Integer.parseInt(turn.getCodigoComplejo());


        mGetLocalTask.execute();

    }

    private void executeSaveTurnService(Turn turn, final ImageView image) {

        mSaveTurnTask = new PutTurnTask(mContext) {
            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                ConfirmDialogCustom dialog;

                if (result != null) {


                    dialog = new ConfirmDialogCustom(mContext.getString(R.string.success_save_turn_message), mContext.getString(R.string.save_turn), mContext.getString(R.string.acept_text));


                    image.setImageResource(R.drawable.pendiente);

                } else {


                    dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_message), mContext.getString(R.string.save_turn), mContext.getString(R.string.acept_text));


                }

                FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                ft.add(dialog, null);
                ft.commitAllowingStateLoss();


            }
        };

        mSaveTurnTask.mCodigoUsuario = ConfigurationClass.getUserCod(mContext);
        mSaveTurnTask.mFecha = mDate;
        mSaveTurnTask.mCodigoCancha = Integer.parseInt(turn.getCodigoCancha());
        mSaveTurnTask.mHDesde = Integer.parseInt(turn.getHoraDesde());
        mSaveTurnTask.mHHasta = Integer.parseInt(turn.getHoraHasta());


        mSaveTurnTask.execute();

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


}
