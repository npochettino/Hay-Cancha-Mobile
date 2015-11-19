package sempait.haycancha.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import sempait.haycancha.ConfirmDialogCustom;
import sempait.haycancha.R;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.models.Turn;
import sempait.haycancha.models.User;
import sempait.haycancha.services.GET.GetPlayerAvailableForPosition;

/**
 * Created by martin on 18/11/15.
 */
public class SearchPlayerFragment extends BaseFragment {

    private String TURN = "turn";
    private Turn mTurn;
    private int mPositionSelected;
    private View mView;
    private String mDescPositionSelected;
    private ImageView mImgProfile, mImgArquero, mImgDefensor, mImgDelantero, mImgTodaElCampo;
    private Button mDoneButton;
    private GetPlayerAvailableForPosition mGetPlayerTask;


    public Fragment newInstance(Turn turn) {

        SearchPlayerFragment mSearchPlayer = new SearchPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TURN, turn);


        mSearchPlayer.setArguments(bundle);

        return mSearchPlayer;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTurn = (Turn) getArguments().getSerializable(TURN);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getBaseActivity().setSectionTitle("Elegí una posicion");

        mView = (ViewGroup) inflater.inflate(R.layout.search_player_fragment, container, false);

        mImgProfile = (ImageView) mView.findViewById(R.id.img_profile);
        mImgArquero = (ImageView) mView.findViewById(R.id.img_arquero);
        mImgDefensor = (ImageView) mView.findViewById(R.id.img_defensor);
        mImgDelantero = (ImageView) mView.findViewById(R.id.img_delantero);
        mImgTodaElCampo = (ImageView) mView.findViewById(R.id.img_todo_el_campo);
        mDoneButton = (Button) mView.findViewById(R.id.btn_done);


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

        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                executeGetPlayerForPosition();
            }
        });
    }

    private void executeGetPlayerForPosition() {

        mGetPlayerTask = new GetPlayerAvailableForPosition(mContext) {

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if (result != null && result.length() != 2) {

                    List<User> mUsers = new Gson().fromJson(result.toString(), new TypeToken<List<User>>() {
                    }.getType());

                    getBaseActivity().replaceInnerFragmentWhitFLip(ResultSearchPlayerFragment.newIntance(mUsers, mTurn), true);


                } else {

                    ConfirmDialogCustom dialog = new ConfirmDialogCustom(mContext.getString(R.string.no_found_player), mContext.getString(R.string.players), mContext.getString(R.string.acept_text));
                    FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.add(dialog, null);
                    ft.commitAllowingStateLoss();
                }

            }
        };

        mGetPlayerTask.mCodigoPosicion = mPositionSelected;
        mGetPlayerTask.execute();

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
