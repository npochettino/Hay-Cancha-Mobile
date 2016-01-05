package sempait.haycancha.fragment;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class ResultTurnFragment extends BaseFragment {
    private ListView mListViewMovies;
    private ImageView mImgEmptyResult;
    private Context mActivity;
    private static String LIST_TURNS = "list";
    private static String DATE = "date";
    private String mDate;

    private List<Turn> mListTurn;


    public static ResultTurnFragment newIntance(List<Turn> turnList, String date) {

        ResultTurnFragment mResultFragment = new ResultTurnFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(LIST_TURNS, (Serializable) turnList);
        bundle.putString(DATE, date);
        mResultFragment.setArguments(bundle);


        return mResultFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListTurn = (List<Turn>) getArguments().getSerializable(LIST_TURNS);
        mDate = getArguments().getString(DATE);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View mView = inflater.inflate(R.layout.list_general_view, container, false);
        mListViewMovies = (ListView) mView.findViewById(R.id.list_general);
        mImgEmptyResult = (ImageView) mView.findViewById(R.id.img_empty_resutl);

        mListViewMovies.setVisibility(View.VISIBLE);
        mImgEmptyResult.setVisibility(View.GONE);


        getBaseActivity().setSectionTitle(getResources().getString(R.string.resultado));


        fillDataAdapter();

        return mView;
    }

    private void fillDataAdapter() {


        if (mListTurn != null && !mListTurn.isEmpty()) {

            TurnListAdapter turnAdapter = new TurnListAdapter(mListTurn, mContext, mDate);
            mListViewMovies.setAdapter(turnAdapter);
        } else {
            mListViewMovies.setVisibility(View.GONE);
            mImgEmptyResult.setVisibility(View.VISIBLE);

        }


    }
}
