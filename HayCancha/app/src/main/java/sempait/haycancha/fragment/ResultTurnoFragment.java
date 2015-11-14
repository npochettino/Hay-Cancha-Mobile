package sempait.haycancha.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sempait.haycancha.adapter.TurnListAdapter;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.models.Turn;
import sempait.haycancha.R;

/**
 * Created by martin on 24/10/15.
 */
public class ResultTurnoFragment extends BaseFragment {
    private ListView mListViewMovies;
    private Context mActivity;
    private static String LIST_TURNS = "list";

    private List<Turn> mListTurn;


    public static ResultTurnoFragment newIntance(List<Turn> turnList) {

        ResultTurnoFragment mResultFragment = new ResultTurnoFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(LIST_TURNS, (Serializable) turnList);
        mResultFragment.setArguments(bundle);


        return mResultFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListTurn = (List<Turn>) getArguments().getSerializable(LIST_TURNS);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View mView = inflater.inflate(R.layout.list_general_view, container, false);
        mListViewMovies = (ListView) mView.findViewById(R.id.list_general);

        getBaseActivity().setSectionTitle(getResources().getString(R.string.resultado));


        fillDataAdapter();

        return mView;
    }

    private void fillDataAdapter() {


//        List<Turn> mListTurn = new ArrayList<Turn>();
//
//        Turn mTurn = new Turn();
//        mTurn.setDate("27/10/2015");
//        mTurn.setHoraDesde("21:00");
//        mTurn.setHoraHasta("11:00");
//        mTurn.setDirection("Francia 2000");
//        mTurn.setName("Heroes Futbol");
//        mTurn.setDistance(3.5f);
//        mTurn.setRaiting(4.5f);
//
//        mListTurn.add(mTurn);
//
//        Turn mTurn1 = new Turn();
//        mTurn1.setDate("27/10/2015");
//        mTurn1.setHoraDesde("20:00");
//        mTurn1.setHoraHasta("11:00");
//        mTurn1.setDirection("Oroño 839");
//        mTurn1.setName("Centro Gol");
//        mTurn1.setDistance(4.5f);
//        mTurn1.setRaiting(5);
//
//        mListTurn.add(mTurn1);
//
//
//        Turn mTurn2 = new Turn();
//        mTurn2.setDate("27/10/2015");
//        mTurn2.setHoraDesde("23:00");
//        mTurn2.setHoraHasta("11:00");
//        mTurn2.setDirection("Salta 1982");
//        mTurn2.setName("Fultito");
//        mTurn2.setDistance(5.5f);
//        mTurn2.setRaiting(2.2f);
//
//        mListTurn.add(mTurn2);


        if (mListTurn != null && !mListTurn.isEmpty()) {

            TurnListAdapter mBillboardAdapter = new TurnListAdapter(mListTurn, mContext);
            mListViewMovies.setAdapter(mBillboardAdapter);
        }


    }
}
