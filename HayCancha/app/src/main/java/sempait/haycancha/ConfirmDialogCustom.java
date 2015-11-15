package sempait.haycancha;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class ConfirmDialogCustom extends DialogFragment {

    private String message;
    private String title;
    private String messageButtonRigth;
    private String messageButtonLeft;

    private TextView buttonLeft;

    public ConfirmDialogCustom() {
    }

    @SuppressLint("ValidFragment")
    public ConfirmDialogCustom(String message, String title, String buttonLeft) {

        this.message = message;
        this.title = title;
        this.messageButtonLeft = buttonLeft;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentl_dialog, null);

        buttonLeft = (TextView) view.findViewById(R.id.cancel_button);


        ((TextView) view.findViewById(R.id.title_message)).setText(title);
        ((TextView) view.findViewById(R.id.message)).setText(message);
        buttonLeft.setText(messageButtonLeft);


        buttonLeft.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                actionButtonLeft();
                dismiss();
            }
        });

        return view;
    }

    public void actionButtonRigth() {
    }

    public void actionButtonLeft() {
    }

}
