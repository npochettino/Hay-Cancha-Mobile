package sempait.haycancha.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.R;
import sempait.haycancha.adapter.CommentListAdapter;
import sempait.haycancha.adapter.CustomInfoWindowAdapter;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.models.Comment;
import sempait.haycancha.models.Stadium;
import sempait.haycancha.services.GET.GetCommentForStadiumTask;
import sempait.haycancha.services.LocationManager;
import sempait.haycancha.services.PUT.PutStadiumCommentTask;

/**
 * Created by martin on 12/11/15.
 */
public class StadiumDetailFragment extends BaseFragment implements GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private String STADIUM = "stadium";
    private Stadium mStadium;
    private TextView mTxtNameStadium;
    private TextView mTxtPhoneStadium;
    private TextView mTxtCalificar;
    private TextView mTxtInfoStadium;
    private LinearLayout mLinearStars;
    private GoogleMap mMap;
    private SupportMapFragment supportMapFragment;
    private FragmentManager fm;
    private LinearLayout mLinearImages;
    private ListView mListViewComment;
    private GetCommentForStadiumTask mGetCommentTask;
    private PutStadiumCommentTask mPutCommentTask;
    private CommentListAdapter commentListAdapter;
    private List<Comment> mListComment;
    private ImageView imgPhone;


    public Fragment newInstance(Stadium stadium) {

        StadiumDetailFragment mFragment = new StadiumDetailFragment();
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
        mTxtCalificar = (TextView) mView.findViewById(R.id.txt_calificar_stadium);
        imgPhone = (ImageView) mView.findViewById(R.id.img_phone);


        setData();

        return mView;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mStadium.getTelefono()));
                startActivity(intent);
            }
        });


        mTxtCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RaitingDialogFragment dialog = new RaitingDialogFragment(StadiumDetailFragment.this, mListComment);
                FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                ft.add(dialog, null);
                ft.commitAllowingStateLoss();


            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fm = getChildFragmentManager();

        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.stadium_map);

        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.stadium_map, supportMapFragment).commit();
        }
    }

    private void setData() {

        mTxtNameStadium.setText(mStadium.getDescripcion());
        mTxtPhoneStadium.setText(mStadium.getTelefono());
        mTxtInfoStadium.setText(mStadium.getDireccion() + "/" + "Horarios: " + mStadium.getHoraApertura() + " a " + mStadium.getHoraCierre());
        setupRating(mStadium.getPuntajeComplejo(), mLinearStars);
        setStadiumImages();
        executeCommentTask();


    }

    private void executeCommentTask() {

        mGetCommentTask = new GetCommentForStadiumTask(mContext) {


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                List<Comment> coments = new Gson().fromJson(result.toString(), new TypeToken<List<Comment>>() {
                }.getType());

                mListComment = coments;

                if (coments != null && !coments.isEmpty()) {

                    commentListAdapter = new CommentListAdapter(coments, mContext);
                    mListViewComment.setAdapter(commentListAdapter);
                }


            }
        };

        mGetCommentTask.mCodigoComplejo = mStadium.getCodigoComplejo();
        mGetCommentTask.mCodigoUser = ConfigurationClass.getUserCod(mContext);
        mGetCommentTask.execute();
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
        mMap.setOnInfoWindowClickListener(StadiumDetailFragment.this);
        mMap.setOnMarkerClickListener(StadiumDetailFragment.this);

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
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.puntero_mapa_1)));

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

    public void executeRateService(String title, String comment, int rate) {

        mPutCommentTask = new PutStadiumCommentTask(this.getActivity()) {


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                executeCommentTask();

            }
        };


        mPutCommentTask.mCodigoComplejo = mStadium.getCodigoComplejo();
        mPutCommentTask.mCodigoUsuario = ConfigurationClass.getUserCod(mContext);
        mPutCommentTask.mTitulo = title;
        mPutCommentTask.mComentario = comment;
        mPutCommentTask.mPuntaje = rate;
        mPutCommentTask.execute();

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
