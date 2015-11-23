package sempait.haycancha.drawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.R;
import sempait.haycancha.Utils;
import sempait.haycancha.base.BaseDrawerFragment;
import sempait.haycancha.fragment.MyInvitationFragment;
import sempait.haycancha.fragment.PerfilFragment;
import sempait.haycancha.fragment.ReservationConfirmsFragments;
import sempait.haycancha.fragment.ReservationsPendingFragment;
import sempait.haycancha.fragment.TurnsFilterFragment;

import static sempait.haycancha.R.drawable.background_drawer_selected;


public class MainNavigationDrawer extends BaseDrawerFragment {

    private View mView;

    private LinearLayout mProfileItem;
    private LinearLayout mSearchFieldItem;
    private LinearLayout mSearchPlayerItem;
    private LinearLayout mSearchMatchItem;
    private LinearLayout mMyreservationsItem;
    private LinearLayout mInvitationItem;

    private ImageView imgAvatar;
    private TextView txt_userPosition;
    private TextView txt_userTeamName;
    private TextView txt_userName;
    private TextView txt_userLastName;

    public static enum Section {
        FIELDS, MATCHES, PROFILE, INVITATIONS, RESERVATIONS, PLAYERS
    }

    public Section mSelectedSection = Section.FIELDS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.main_drawer_fragment, container, false);

        mProfileItem = (LinearLayout) mView.findViewById(R.id.profile_item);
        mSearchFieldItem = (LinearLayout) mView.findViewById(R.id.buscar_turno_item);
        mSearchPlayerItem = (LinearLayout) mView.findViewById(R.id.buscar_jugador_item);
        mSearchMatchItem = (LinearLayout) mView.findViewById(R.id.buscar_partido_item);
        mMyreservationsItem = (LinearLayout) mView.findViewById(R.id.mis_reservas_item);
        mInvitationItem = (LinearLayout) mView.findViewById(R.id.invitation_item);


        imgAvatar = (ImageView) mView.findViewById(R.id.img_profile);
        txt_userPosition = (TextView) mView.findViewById(R.id.user_position);
        txt_userName = (TextView) mView.findViewById(R.id.user_name);
        txt_userLastName = (TextView) mView.findViewById(R.id.user_last_name);


        setUserInfo();

        openSection(Section.FIELDS);

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

//        mView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        mProfileItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openSection(Section.PROFILE);
            }
        });

        mSearchFieldItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                openSection(Section.FIELDS);
            }
        });

        mSearchPlayerItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openSection(Section.PLAYERS);
            }
        });

        mSearchMatchItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openSection(Section.MATCHES);
            }
        });

        mMyreservationsItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openSection(Section.RESERVATIONS);
            }
        });

        mInvitationItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openSection(Section.INVITATIONS);
            }
        });
    }


    public void setUserInfo() {

        mProfileItem.setVisibility(View.VISIBLE);
        txt_userPosition.setText("(" + ConfigurationClass.getDescPosition(mContext) + ")");
        txt_userName.setText(ConfigurationClass.getUserFirstNameUser(mContext));
        txt_userLastName.setText(ConfigurationClass.getUserLastNameUserUser(mContext));
        String url = ConfigurationClass.getImageUser(mContext);
        ImageLoader.getInstance().displayImage(url.contains("http:") ? url : "http:" + url, imgAvatar, Utils.getImageLoaderOptionRouded());


    }


    public void openSection(Section section) {
        Boolean addToBackStack = true;
        mSelectedSection = section;
        String tagFragment = getString(R.string.fields);

        Fragment fragment = null;

        switch (section) {

            case PROFILE:

                fragment = new PerfilFragment(MainNavigationDrawer.this);
                tagFragment = getString(R.string.perfil);
                break;

            case FIELDS:
                fragment = new TurnsFilterFragment();
                tagFragment = getString(R.string.fields);
                break;

            case PLAYERS:
                fragment = new ReservationConfirmsFragments();
                tagFragment = getString(R.string.players);
                break;

            case MATCHES:
//                fragment = new NotificationsFragment();
                getBaseActivity().setSectionTitle(getString(R.string.matchs));
                break;

            case RESERVATIONS:
                fragment = new ReservationsPendingFragment();
                tagFragment = getString(R.string.reservations);
                break;

            case INVITATIONS:
                fragment = new MyInvitationFragment();
                tagFragment = getString(R.string.players);
                break;


            default:
                break;
        }


        if (fragment != null) {
            getBaseActivity().setSectionTitle(tagFragment);
            getBaseActivity().replaceFragmentSection(fragment, tagFragment);
//            getBaseActivity().cleanBacStack();
        }

        if (mDrawerLayout != null)
            mDrawerLayout.closeDrawers();

//        refreshViewDrawer();
    }

    //
    private void refreshViewDrawer() {

        mProfileItem.setBackgroundResource(android.R.color.transparent);
        mSearchFieldItem.setBackgroundResource(android.R.color.transparent);
        mSearchPlayerItem.setBackgroundResource(android.R.color.transparent);
        mSearchMatchItem.setBackgroundResource(android.R.color.transparent);
        mMyreservationsItem.setBackgroundResource(android.R.color.transparent);
        mInvitationItem.setBackgroundResource(android.R.color.transparent);


        switch (mSelectedSection) {

            case PROFILE:
                mProfileItem.setBackgroundResource(background_drawer_selected);
                break;

            case FIELDS:
                mSearchFieldItem.setBackgroundResource(background_drawer_selected);
                break;

            case PLAYERS:
                mSearchPlayerItem.setBackgroundResource(background_drawer_selected);
                break;

            case MATCHES:
                mSearchMatchItem.setBackgroundResource(background_drawer_selected);
                break;

            case RESERVATIONS:
                mMyreservationsItem.setBackgroundResource(background_drawer_selected);
                break;

            case INVITATIONS:
                mInvitationItem.setBackgroundResource(background_drawer_selected);
                break;


            default:
                break;
        }
    }


}
