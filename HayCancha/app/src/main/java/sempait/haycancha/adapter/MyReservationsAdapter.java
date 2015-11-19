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
import sempait.haycancha.models.Turn;
import sempait.haycancha.services.GET.GetLocalTask;
import sempait.haycancha.services.LocationManager;

/**
 * Created by martin on 18/11/15.
 */
public class MyReservationsAdapter extends BaseAdapter {

    private static final int TYPE_FEATURED = 0;
    private static final int TYPE_NO_FEATURED = 1;

    private List<Turn> turnList;
    private LayoutInflater mInflater;
    private Context mContext;
    private GetLocalTask mGetLocalTask;
    private String mDate;


    public MyReservationsAdapter(List<Turn> turns, Context context, String date) {
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


            convertView = mInflater.inflate(R.layout.row_reservations_list, null);

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
        holder.distance.setText(" (A " + String.valueOf(round(turn.calculateDistanceTo(LocationManager.getInstance(mContext).getLocation()) / 1000, 2)) + " Km)");
        setupRating(Float.parseFloat(turn.getPuntajeComplejo()), holder.rating);
        holder.hourTo.setText(turn.getHoraDesde() + " hs");
        holder.fieldCod.setText(turn.getDescripcionCancha() + " ($ " + turn.getPrecio() + ")");
        ImageLoader.getInstance().displayImage(turn.getImagenComplejo().contains("http:") ? turn.getImagenComplejo() : "http:" + turn.getImagenComplejo(), holder.logoStadium, Utils.getImageLoaderOptionRouded());


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
