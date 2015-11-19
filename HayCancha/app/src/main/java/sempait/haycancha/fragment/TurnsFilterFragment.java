package sempait.haycancha.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.ConfirmDialogCustom;
import sempait.haycancha.R;
import sempait.haycancha.activities.LoginActivity;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.models.Turn;
import sempait.haycancha.services.GET.GetTurnsTask;

/**
 * Created by martin on 29/10/15.
 */
public class TurnsFilterFragment extends BaseFragment {

    private Context mActivity;
    View mView;
    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog.OnTimeSetListener hourFrom;
    TimePickerDialog.OnTimeSetListener hourTo;
    Calendar myCalendar = Calendar.getInstance();
    GetTurnsTask mGetTurnTask;
    EditText mExpDate;
    EditText mExpHFrom;
    EditText mExpHTo;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getBaseActivity().setSectionTitle(mContext.getString(R.string.fields));

        mView = inflater.inflate(R.layout.fragment_filter_turns, container, false);
        mExpDate = (EditText) mView.findViewById(R.id.exp_date);
        mExpHFrom = (EditText) mView.findViewById(R.id.exp_hour_from);
        mExpHTo = (EditText) mView.findViewById(R.id.exp_hour_to);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };

        hourFrom = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                updateLabelHFrom();
            }
        };

        hourTo = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                updateLabelHTo();
            }
        };

        setData();


        return mView;
    }

    private void setData() {
        updateLabel();
        updateLabelHFrom();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView.findViewById(R.id.img_hay_cancha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(mExpHFrom.getText().toString().substring(0, 2)) < Integer.valueOf(mExpHTo.getText().toString().substring(0, 2)))
                    executeTurnsService();
                else {


                    ConfirmDialogCustom dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_hora_message), mContext.getString(R.string.fields), mContext.getString(R.string.acept_text));
                    FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.add(dialog, null);
                    ft.commitAllowingStateLoss();
                }

            }
        });


        mView.findViewById(R.id.exp_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dpd = new DatePickerDialog(
                        mActivity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar
                        .get(Calendar.MONTH), myCalendar
                        .get(Calendar.DAY_OF_MONTH));

                dpd.show();

            }
        });

        mView.findViewById(R.id.exp_hour_from).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int hour1 = c.get(Calendar.HOUR_OF_DAY);
                int minute = 00;

                // Create a new instance of TimePickerDialog and return it
                TimePickerDialog tp = new TimePickerDialog(mActivity, hourFrom, hour1, minute,
                        DateFormat.is24HourFormat(mActivity));
                tp.show();
            }
        });


        mView.findViewById(R.id.exp_hour_to).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int hour1 = c.get(Calendar.HOUR_OF_DAY);
                int minute = 00;

                // Create a new instance of TimePickerDialog and return it
                TimePickerDialog tp = new TimePickerDialog(mActivity, hourTo, hour1, minute,
                        DateFormat.is24HourFormat(mActivity));
                tp.show();
            }
        });


    }

    private void updateLabel() {

        String myFormat = "dd/MM"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ((EditText) mView.findViewById(R.id.exp_date)).setText(sdf.format(myCalendar.getTime()));
        ((EditText) mView.findViewById(R.id.exp_date)).setFocusable(false);
    }

    private void updateLabelHFrom() {

        String myFormat = "HH:00"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ((EditText) mView.findViewById(R.id.exp_hour_from)).setText(sdf.format(myCalendar.getTime()));
        ((EditText) mView.findViewById(R.id.exp_hour_from)).setFocusable(false);

        ((EditText) mView.findViewById(R.id.exp_hour_to)).setText(sdf.format(myCalendar.getTimeInMillis() + 3600000));
        ((EditText) mView.findViewById(R.id.exp_hour_to)).setFocusable(false);
    }

    private void updateLabelHTo() {

        String myFormat = "HH:00"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ((EditText) mView.findViewById(R.id.exp_hour_to)).setText(sdf.format(myCalendar.getTime()));
        ((EditText) mView.findViewById(R.id.exp_hour_to)).setFocusable(false);
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


        }
    }

    public void executeTurnsService() {


        mGetTurnTask = new GetTurnsTask(mContext) {
            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                if (result != null) {

                    Date date = myCalendar.getTime();
                    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                    String stringDate = format1.format(date);

                    if (result.length() != 2) {

                        List<Turn> mTurns = new Gson().fromJson(result.toString(), new TypeToken<List<Turn>>() {
                        }.getType());

                        getBaseActivity().replaceInnerFragmentWhitFLip(ResultTurnFragment.newIntance(mTurns, stringDate), true);
                    } else
                        getBaseActivity().replaceInnerFragmentWhitFLip(ResultTurnFragment.newIntance(new ArrayList<Turn>(), stringDate), true);


                } else {

                    ConfirmDialogCustom dialog = new ConfirmDialogCustom(mContext.getString(R.string.error_message), mContext.getString(R.string.fields), mContext.getString(R.string.acept_text));

                    FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.add(dialog, null);
                    ft.commitAllowingStateLoss();
                }


            }
        };


        mGetTurnTask.mCodigoUsuario = ConfigurationClass.getUserCod(mContext);


        Date date = myCalendar.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = format1.format(date);

        mGetTurnTask.mDate = stringDate;
        mGetTurnTask.mHdesde = Integer.valueOf((String) ((EditText) mView.findViewById(R.id.exp_hour_from)).getText().toString().substring(0, 2));
        mGetTurnTask.mHHasta = Integer.valueOf((String) ((EditText) mView.findViewById(R.id.exp_hour_to)).getText().toString().substring(0, 2));


        mGetTurnTask.execute();

    }


}
