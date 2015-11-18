package sempait.haycancha.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.ConfirmDialogCustom;
import sempait.haycancha.R;
import sempait.haycancha.adapter.MyReservationsConfirmAdapter;
import sempait.haycancha.adapter.TurnListAdapter;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.models.Turn;
import sempait.haycancha.services.GetTurnsCurrentForUser;

/**
 * Created by martin on 18/11/15.
 */
public class ReservationFragments extends BaseFragment {

    private ListView mListReservations;
    private Context mActivity;
    private GetTurnsCurrentForUser mGetReservationsUser;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View mView = inflater.inflate(R.layout.list_general_view, container, false);
        mListReservations = (ListView) mView.findViewById(R.id.list_general);

        getBaseActivity().setSectionTitle(getResources().getString(R.string.reservations));


        executeGetReservationsServices();

        return mView;
    }

    private void executeGetReservationsServices() {
        mGetReservationsUser = new GetTurnsCurrentForUser(mContext) {


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if (result != null) {

                    if (result.length() != 2) {

                        List<Turn> mTurns = new Gson().fromJson(result.toString(), new TypeToken<List<Turn>>() {
                        }.getType());

                        fillDataAdapter(mTurns);

                    } else {

                        ConfirmDialogCustom dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_message), mContext.getString(R.string.fields), mContext.getString(R.string.acept_text));

                        FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                        ft.add(dialog, null);
                        ft.commitAllowingStateLoss();
                    }


                }

            }
        };

        mGetReservationsUser.mCodigoUsuario = ConfigurationClass.getUserCod(mContext);
        mGetReservationsUser.execute();

    }


    private void fillDataAdapter(List<Turn> listReservation) {


        if (listReservation != null && !listReservation.isEmpty()) {

            MyReservationsConfirmAdapter turnAdapter = new MyReservationsConfirmAdapter(listReservation, mContext, "");
            mListReservations.setAdapter(turnAdapter);
        }


    }


}
