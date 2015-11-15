package sempait.haycancha.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import sempait.haycancha.R;
import sempait.haycancha.adapter.CommentListAdapter;
import sempait.haycancha.adapter.CustomInfoWindowAdapter;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.models.Comment;
import sempait.haycancha.models.Stadium;
import sempait.haycancha.services.LocationManager;

/**
 * Created by martin on 12/11/15.
 */
public class FieldDetailFragment extends BaseFragment implements GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private String STADIUM = "stadium";
    private Stadium mStadium;
    private TextView mTxtNameStadium;
    private TextView mTxtPhoneStadium;
    private TextView mTxtInfoStadium;
    private LinearLayout mLinearStars;
    private GoogleMap mMap;
    private SupportMapFragment supportMapFragment;
    private FragmentManager fm;
    private LinearLayout mLinearImages;
    private ListView mListViewComment;


    public Fragment newInstance(Stadium stadium) {

        FieldDetailFragment mFragment = new FieldDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(STADIUM, stadium);

        mFragment.setArguments(bundle);

        return mFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStadium = (Stadium) getArguments().getSerializable(STADIUM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        getBaseActivity().setSectionTitle(getString(R.string.stadium_detail));

        View mView = inflater.inflate(R.layout.field_detail_fragment, container, false);

        mTxtInfoStadium = (TextView) mView.findViewById(R.id.txt_info_stadium);
        mTxtNameStadium = (TextView) mView.findViewById(R.id.stadium_name);
        mTxtPhoneStadium = (TextView) mView.findViewById(R.id.txt_phone);
        mLinearStars = (LinearLayout) mView.findViewById(R.id.lyt_stars_rating);
        mLinearImages = (LinearLayout) mView.findViewById(R.id.img_type_salas);
        mListViewComment = (ListView) mView.findViewById(R.id.list_comment);


        setData();

        return mView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fm = getChildFragmentManager();

        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.cinema_map);

        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.cinema_map, supportMapFragment).commit();
        }
    }

    private void setData() {

        mTxtNameStadium.setText(mStadium.getDescripcion());
        mTxtPhoneStadium.setText(mStadium.getTelefono());
        mTxtInfoStadium.setText(mStadium.getDireccion() + "/" + "Horarios: " + mStadium.getHoraApertura() + " a " + mStadium.getHoraCierre());
        setupRating(4.5f, mLinearStars);
        setStadiumImages();
        setComment();

    }

    private void setComment() {


        List<Comment> mListComment = new ArrayList<Comment>();

        Comment mComment = new Comment();
        mComment.setDate("27/10/2015");
        mComment.setApellidoUser("Diguilio");
        mComment.setNomUser("Martin");
        mComment.setRaiting(4.5f);
        mComment.setUrlImagen("http://3.bp.blogspot.com/-QgXJDaHt5Lo/UpxQPX_W-gI/AAAAAAAANII/TzU2P5KfGxU/s1600/3874_3_1219.jpg");
        mComment.setTitle("Me encanto");
        mComment.setComment("La cancha es excelente, y encima te dan una coca de regalo");

        mListComment.add(mComment);

        Comment mComment1 = new Comment();
        mComment1.setDate("23/10/2015");
        mComment1.setApellidoUser("Dalaison");
        mComment1.setNomUser("Ezequiel");
        mComment1.setRaiting(1);
        mComment1.setUrlImagen("http://www.vitaldent.com/pix/tratamientos/tratamientos_infantil/ortopedia_dentofacial/detalle/tratamientos_infantil_ortopedia_dentofacial_foto_frente_despues_detalle.jpg");
        mComment1.setTitle("Soy muy malo");
        mComment1.setComment("No me gusto la cancha, le pegue muchas veces al piso y me canse de levantar tierra");

        mListComment.add(mComment1);


        Comment mComment2 = new Comment();
        mComment2.setDate("15/10/2015");
        mComment2.setNomUser("Nicolas");
        mComment2.setApellidoUser("Pochettino");
        mComment2.setUrlImagen("http://www.nosdivorciamos.com/imagenes/articulo/0054.jpg");
        mComment2.setComment("La verdad es que la cancha es muy buena, pero yo no se si soy zurdo o derecho aun");
        mComment2.setTitle("Que reserrrrva");
        mComment2.setRaiting(2.2f);

        mListComment.add(mComment2);


        Comment mComment3 = new Comment();
        mComment3.setDate("15/10/2015");
        mComment3.setNomUser("Nikete");
        mComment3.setApellidoUser("Demayo");
        mComment3.setUrlImagen("http://www.nosdivorciamos.com/imagenes/articulo/0054.jpg");
        mComment3.setComment("No puedo ser mas reserva");
        mComment3.setTitle("Que reserrrrva");
        mComment3.setRaiting(2.2f);

        mListComment.add(mComment3);



        Comment mComment4 = new Comment();
        mComment4.setDate("15/10/2015");
        mComment4.setNomUser("Bruno");
        mComment4.setApellidoUser("Minino");
        mComment4.setUrlImagen("http://www.nosdivorciamos.com/imagenes/articulo/0054.jpg");
        mComment4.setComment("Me canse de perder al metegol");
        mComment4.setTitle("Soy el hijo de tincho");
        mComment4.setRaiting(2.2f);

        mListComment.add(mComment4);


        Comment mComment5 = new Comment();
        mComment5.setDate("15/10/2015");
        mComment5.setNomUser("Juan");
        mComment5.setApellidoUser("Crespi");
        mComment5.setUrlImagen("http://www.nosdivorciamos.com/imagenes/articulo/0054.jpg");
        mComment5.setComment("El problema es que yo soy zurdo");
        mComment5.setTitle("Soy el hijo de tincho");
        mComment5.setRaiting(2.2f);

        mListComment.add(mComment5);


        Comment mComment6 = new Comment();
        mComment6.setDate("15/10/2015");
        mComment6.setNomUser("Gonza");
        mComment6.setApellidoUser("Sanchez");
        mComment6.setUrlImagen("http://www.nosdivorciamos.com/imagenes/articulo/0054.jpg");
        mComment6.setComment("Me gusssssta");
        mComment6.setTitle("Holiss");
        mComment6.setRaiting(2.2f);

        mListComment.add(mComment6);


        if (mListComment != null && !mListComment.isEmpty()) {

            CommentListAdapter commentListAdapter = new CommentListAdapter(mListComment, mContext);
            mListViewComment.setAdapter(commentListAdapter);
        }


    }


    private void setStadiumImages() {

        mLinearImages.removeAllViews();

        View view = null;


        view = getImgaeItemView(mContext, R.drawable.imgen_1);
        mLinearImages.addView(view);

        view = getImgaeItemView(mContext, R.drawable.imagen_2);
        mLinearImages.addView(view);

        view = getImgaeItemView(mContext, R.drawable.imagen_3);
        mLinearImages.addView(view);

        view = getImgaeItemView(mContext, R.drawable.imagen_4);
        mLinearImages.addView(view);

        view = getImgaeItemView(mContext, R.drawable.imagen_5);
        mLinearImages.addView(view);


    }

    public static View getImgaeItemView(Context context, int img) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_img_sala, null);
        ImageView img_sala = (ImageView) v.findViewById(R.id.img_item);

        img_sala.setImageResource(img);

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();

        setUpMapIfNeeded();
        centerMapOnMyLocation();
    }

    private void setUpMapIfNeeded() {

        if (mMap == null) {
            mMap = supportMapFragment.getMap();

            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        addMarkersToMap();

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(mContext));
        mMap.setOnInfoWindowClickListener(FieldDetailFragment.this);
        mMap.setOnMarkerClickListener(FieldDetailFragment.this);

        final View mapView = supportMapFragment.getView();

        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {

                        @SuppressWarnings("deprecation")
                        @SuppressLint("NewApi")
                        @Override

                        public void onGlobalLayout() {
                            LatLngBounds.Builder latLongBuilder = new LatLngBounds.Builder();

                            LatLng latLong = new LatLng(mStadium.getLatitud(),
                                    mStadium.getLongitud());
                            latLongBuilder.include(latLong);
                            LatLngBounds bounds = latLongBuilder.build();

                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                mapView.getViewTreeObserver()
                                        .removeGlobalOnLayoutListener(this);
                            } else {
                                mapView.getViewTreeObserver()
                                        .removeOnGlobalLayoutListener(this);
                            }
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngBounds(bounds, 10));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                        }
                    });
        }
    }

    private void centerMapOnMyLocation() {

        Location l = LocationManager.getInstance(mContext).getLocation();

        if (l != null) {
            LatLng location = new LatLng(l.getLatitude(), l.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
        }
    }

    private void addMarkersToMap() {
        double latitude = 0;
        double longitude = 0;
        String title = "";
        String description = "";

        latitude = mStadium.getLatitud();
        longitude = mStadium.getLongitud();
        title = mStadium.getDescripcion();
        description = mStadium.getDescripcion();
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude)).title(title)
                .snippet(description)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.buscara_partidos_pin)));

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
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
