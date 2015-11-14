package sempait.haycancha.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import sempait.haycancha.R;
import sempait.haycancha.base.BaseActivity;


public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final View mWindow;

    public CustomInfoWindowAdapter(Context context) {
        mWindow = ((BaseActivity) context).getLayoutInflater().inflate(R.layout.balloon_overlay,
                null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        render(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void render(Marker marker, View view) {
        String title = marker.getTitle();
        TextView titleUi = ((TextView) view
                .findViewById(R.id.balloon_item_title));
        titleUi.setText(title);

        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view
                .findViewById(R.id.balloon_item_snippet));
        snippetUi.setText(snippet);
    }
}
