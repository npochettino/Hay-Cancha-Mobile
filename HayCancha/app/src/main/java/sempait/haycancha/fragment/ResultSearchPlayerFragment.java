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
import sempait.haycancha.adapter.PlayerListAdapter;
import sempait.haycancha.adapter.TurnListAdapter;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.models.Turn;
import sempait.haycancha.models.User;

/**
 * Created by martin on 18/11/15.
 */
public class ResultSearchPlayerFragment extends BaseFragment {


    private ListView mPlayerListView;
    private Context mActivity;
    private static String LIST_PLAYER = "list";
    private static String TURN = "turn";

    private static String DATE = "date";
    private String mDate;
    private Turn mTurn;

    private List<User> mListPlayer;


    public static ResultSearchPlayerFragment newIntance(List<User> playerList, Turn turn) {

        ResultSearchPlayerFragment mResultFragment = new ResultSearchPlayerFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(LIST_PLAYER, (Serializable) playerList);
        bundle.putSerializable(TURN, turn);
        mResultFragment.setArguments(bundle);


        return mResultFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListPlayer = (List<User>) getArguments().getSerializable(LIST_PLAYER);
        mTurn = (Turn) getArguments().getSerializable(TURN);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View mView = inflater.inflate(R.layout.list_general_view, container, false);
        mPlayerListView = (ListView) mView.findViewById(R.id.list_general);

        getBaseActivity().setSectionTitle(getResources().getString(R.string.resultado));


        fillDataAdapter();

        return mView;
    }

    private void fillDataAdapter() {


        if (mListPlayer != null && !mListPlayer.isEmpty()) {

            PlayerListAdapter turnAdapter = new PlayerListAdapter(mListPlayer, mContext, mTurn);
            mPlayerListView.setAdapter(turnAdapter);
        }


    }

}
