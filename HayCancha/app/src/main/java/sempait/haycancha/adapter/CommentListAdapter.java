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

import java.util.List;

import sempait.haycancha.R;
import sempait.haycancha.Utils;
import sempait.haycancha.models.Comment;

/**
 * Created by martin on 14/11/15.
 */
public class CommentListAdapter extends BaseAdapter {

    List<Comment> mListComment;
    Context mContext;
    LayoutInflater mInflater;


    public CommentListAdapter(List<Comment> comments, Context context) {
        mListComment = comments;
        mInflater = LayoutInflater.from(context);
        mContext = context;

    }

    @Override
    public int getCount() {
        return mListComment.size();
    }

    @Override
    public Object getItem(int position) {
        return mListComment.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final viewHolder holder;
        final Comment comment = mListComment.get(position);

        if (convertView == null) {

            holder = new viewHolder();


            convertView = mInflater.inflate(R.layout.row_comment_list, null);

            holder.imgUser = (ImageView) convertView.findViewById(R.id.img_cinema_row_favorite);
            holder.nameUser = (TextView) convertView.findViewById(R.id.txt_name_user);
            holder.rating = (LinearLayout) convertView.findViewById(R.id.lyt_stars_rating);
            holder.date = (TextView) convertView.findViewById(R.id.txt_date);
            holder.title = (TextView) convertView.findViewById(R.id.txt_title_commet);
            holder.comment = (TextView) convertView.findViewById(R.id.txt_commet);

            convertView.setTag(holder);


        } else
            holder = (viewHolder) convertView.getTag();


        holder.nameUser.setText(comment.getNombreApellidoUsuarioApp());
        setupRating(comment.getRaiting(), holder.rating);
        holder.date.setText(comment.getDate());
        holder.title.setText(comment.getTitle());
        holder.comment.setText(comment.getComment());
        if (comment.getUrlImagen() != null)
            ImageLoader.getInstance().displayImage(comment.getUrlImagen().contains("http:") ? comment.getUrlImagen() : "http:" + comment.getUrlImagen(), holder.imgUser, Utils.getImageLoaderOptionRouded());


        return convertView;
    }

    private static class viewHolder {

        ImageView imgUser;
        TextView nameUser;
        TextView date;
        TextView title;
        TextView comment;
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

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
