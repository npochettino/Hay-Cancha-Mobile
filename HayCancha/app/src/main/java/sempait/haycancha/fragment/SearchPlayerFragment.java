package sempait.haycancha.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import sempait.haycancha.R;
import sempait.haycancha.base.BaseFragment;

/**
 * Created by martin on 18/11/15.
 */
public class SearchPlayerFragment extends BaseFragment {

    private String CODIGO_USER = "codigo_user";
    private int mCodigoUser;
    private View mView;
    private int mPositionSelected;
    private String mDescPositionSelected;
    private ImageView mImgProfile, mImgArquero, mImgDefensor, mImgDelantero, mImgTodaElCampo;


    public Fragment newInstance(int codigo) {

        SearchPlayerFragment mSearchPlayer = new SearchPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CODIGO_USER, codigo);


        mSearchPlayer.setArguments(bundle);

        return mSearchPlayer;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCodigoUser = getArguments().getInt(CODIGO_USER);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getBaseActivity().setSectionTitle(getString(R.string.players));

        mView = (ViewGroup) inflater.inflate(R.layout.search_player_fragment, container, false);

        mImgProfile = (ImageView) mView.findViewById(R.id.img_profile);
        mImgArquero = (ImageView) mView.findViewById(R.id.img_arquero);
        mImgDefensor = (ImageView) mView.findViewById(R.id.img_defensor);
        mImgDelantero = (ImageView) mView.findViewById(R.id.img_delantero);
        mImgTodaElCampo = (ImageView) mView.findViewById(R.id.img_todo_el_campo);


        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mImgArquero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPostion(2);
            }
        });

        mImgDefensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPostion(3);
            }
        });

        mImgDelantero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPostion(4);
            }
        });


        mImgTodaElCampo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPostion(1);
            }
        });
    }

    private void refreshPostion(int position) {


        switch (position) {

            case 2:
                mImgArquero.setImageResource(R.drawable.arquero_activo);
                mImgDefensor.setImageResource(R.drawable.defensa_desactivado);
                mImgDelantero.setImageResource(R.drawable.delantero_desactivado);
                mImgTodaElCampo.setImageResource(R.drawable.todo_campo_desactivado);
                mDescPositionSelected = "Arquero";

                mPositionSelected = 2;
                break;

            case 3:
                mImgArquero.setImageResource(R.drawable.arquero_desactivado);
                mImgDefensor.setImageResource(R.drawable.defensa_activo);
                mImgDelantero.setImageResource(R.drawable.delantero_desactivado);
                mImgTodaElCampo.setImageResource(R.drawable.todo_campo_desactivado);
                mDescPositionSelected = "Defensor";

                mPositionSelected = 3;
                break;

            case 4:
                mImgArquero.setImageResource(R.drawable.arquero_desactivado);
                mImgDefensor.setImageResource(R.drawable.defensa_desactivado);
                mImgDelantero.setImageResource(R.drawable.delantero_activo);
                mImgTodaElCampo.setImageResource(R.drawable.todo_campo_desactivado);
                mDescPositionSelected = "Delantero";


                mPositionSelected = 4;
                break;
            case 1:
                mImgArquero.setImageResource(R.drawable.arquero_desactivado);
                mImgDefensor.setImageResource(R.drawable.defensa_desactivado);
                mImgDelantero.setImageResource(R.drawable.delantero_desactivado);
                mImgTodaElCampo.setImageResource(R.drawable.todo_campo_activo);
                mDescPositionSelected = "Todo el campo";

                mPositionSelected = 1;
                break;

        }


    }

}
