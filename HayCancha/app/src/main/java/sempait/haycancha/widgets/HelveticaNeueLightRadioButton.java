package sempait.haycancha.widgets;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class HelveticaNeueLightRadioButton extends RadioButton {

    Typeface neueLightFont;

    public HelveticaNeueLightRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        neueLightFont = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue-Light.otf");
        this.setTypeface(neueLightFont);
    }

    public HelveticaNeueLightRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setTypeface(neueLightFont);
    }

    public HelveticaNeueLightRadioButton(Context context) {
        super(context);
        this.setTypeface(neueLightFont);
    }

}
