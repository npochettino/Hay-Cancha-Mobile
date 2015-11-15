package sempait.haycancha.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

import sempait.haycancha.R;
import sempait.haycancha.adapter.TurnListAdapter;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.models.Turn;

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


        if (mListTurn != null && !mListTurn.isEmpty()) {

            TurnListAdapter mBillboardAdapter = new TurnListAdapter(mListTurn, mContext);
            mListViewMovies.setAdapter(mBillboardAdapter);
        }


    }
}
