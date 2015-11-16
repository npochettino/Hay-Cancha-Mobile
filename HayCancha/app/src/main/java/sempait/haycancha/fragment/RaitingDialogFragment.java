package sempait.haycancha.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sempait.haycancha.R;

/**
 * Created by martin on 16/11/15.
 */
public class RaitingDialogFragment extends DialogFragment {

    private ImageView mRate1;
    private ImageView mRate2;
    private ImageView mRate3;
    private ImageView mRate4;
    private ImageView mRate5;
    private int mRate = 0;
    private Boolean mRate1State = false;
    private Boolean mRate2State = false;
    private Boolean mRate3State = false;
    private Boolean mRate4State = false;
    private Boolean mRate5State = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(STYLE_NO_TITLE, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_rating_stadium, container, false);
        mRate1 = (ImageView) view.findViewById(R.id.img_star_1);
        mRate2 = (ImageView) view.findViewById(R.id.img_star_2);
        mRate3 = (ImageView) view.findViewById(R.id.img_star_3);
        mRate4 = (ImageView) view.findViewById(R.id.img_star_4);
        mRate5 = (ImageView) view.findViewById(R.id.img_star_5);


        return view;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRate1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mRate = 1;
                if (!mRate1State) {
                    mRate1.setImageResource(R.drawable.star_on);
                    mRate1State = true;

                } else {

                    mRate2.setImageResource(R.drawable.star_off);
                    mRate3.setImageResource(R.drawable.star_off);
                    mRate4.setImageResource(R.drawable.star_off);
                    mRate5.setImageResource(R.drawable.star_off);
                    mRate1State = false;
                }

            }
        });

        mRate2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mRate = 2;

                if (!mRate2State) {
                    mRate1.setImageResource(R.drawable.star_on);
                    mRate2.setImageResource(R.drawable.star_on);
                    mRate2State = true;
                } else {

                    mRate3.setImageResource(R.drawable.star_off);
                    mRate4.setImageResource(R.drawable.star_off);
                    mRate5.setImageResource(R.drawable.star_off);
                    mRate2State = false;
                }
            }
        });

        mRate3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mRate = 3;
                if (!mRate3State) {
                    mRate1.setImageResource(R.drawable.star_on);
                    mRate2.setImageResource(R.drawable.star_on);
                    mRate3.setImageResource(R.drawable.star_on);
                    mRate3State = true;
                } else {

                    mRate4.setImageResource(R.drawable.star_off);
                    mRate5.setImageResource(R.drawable.star_off);
                    mRate3State = false;
                }

            }
        });

        mRate4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mRate = 4;

                if (!mRate4State) {
                    mRate1.setImageResource(R.drawable.star_on);
                    mRate2.setImageResource(R.drawable.star_on);
                    mRate3.setImageResource(R.drawable.star_on);
                    mRate4.setImageResource(R.drawable.star_on);
                    mRate4State = true;
                } else {

                    mRate5.setImageResource(R.drawable.star_off);
                    mRate4State = false;
                }
            }
        });

        mRate5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mRate = 5;
                if (!mRate5State) {
                    mRate1.setImageResource(R.drawable.star_on);
                    mRate2.setImageResource(R.drawable.star_on);
                    mRate3.setImageResource(R.drawable.star_on);
                    mRate4.setImageResource(R.drawable.star_on);
                    mRate5.setImageResource(R.drawable.star_on);
                    mRate5State = true;
                } else {
                    mRate5.setImageResource(R.drawable.star_off);
                    mRate5State = false;
                }
            }
        });
    }
}
