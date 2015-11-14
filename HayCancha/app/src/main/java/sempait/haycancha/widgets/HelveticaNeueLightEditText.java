package sempait.haycancha.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class HelveticaNeueLightEditText extends EditText {

    Typeface neueLightFont;

    public HelveticaNeueLightEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        neueLightFont = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue-Light.otf");
        this.setTypeface(neueLightFont);
    }

    public HelveticaNeueLightEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setTypeface(neueLightFont);
    }

    public HelveticaNeueLightEditText(Context context) {
        super(context);
        this.setTypeface(neueLightFont);
    }
}
