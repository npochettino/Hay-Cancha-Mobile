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

import java.util.List;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.ConfirmDialogCustom;
import sempait.haycancha.R;
import sempait.haycancha.Utils;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.fragment.FieldDetailFragment;
import sempait.haycancha.models.Stadium;
import sempait.haycancha.models.Turn;
import sempait.haycancha.services.GetLocalTask;
import sempait.haycancha.services.SaveTurnTask;


public class TurnListAdapter extends BaseAdapter {

    private static final int TYPE_FEATURED = 0;
    private static final int TYPE_NO_FEATURED = 1;

    private List<Turn> turnList;
    private LayoutInflater mInflater;
    private Context mContext;
    private SaveTurnTask mSaveTurnTask;
    private GetLocalTask mGetLocalTask;


    public TurnListAdapter(List<Turn> turns, Context context) {
        turnList = turns;
        mInflater = LayoutInflater.from(context);
        mContext = context;

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
        holder.distance.setText(" (A " + String.valueOf(turn.getDistance()) + " Km)");
        setupRating(Float.parseFloat(turn.getPuntajeComplejo()), holder.rating);
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

    private void executeGetLocalService(Turn turn) {

        mGetLocalTask = new GetLocalTask(mContext) {
            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                if (result != null) {

                    List<Stadium> stadiums = new Gson().fromJson(result.toString(), new TypeToken<List<Stadium>>() {
                    }.getType());

                    Stadium stadium = stadiums.get(0);

                    ((BaseActivity) mContext).replaceInnerFragmentWhitFLip(new FieldDetailFragment().newInstance(stadium), true);


                } else {

                    ConfirmDialogCustom dialog = new ConfirmDialogCustom("Hubo un problema, intentalo nuevamente, más tarde", "Buscar turnos", "Aceptar") {
                        @Override
                        public void actionButtonLeft() {
                            super.actionButtonLeft();


                        }

                        @Override
                        public void actionButtonRigth() {
                            super.actionButtonRigth();


                        }
                    };
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

        mSaveTurnTask = new SaveTurnTask(mContext) {
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
        mSaveTurnTask.mFecha = turn.getDate();
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
