package sempait.haycancha.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import sempait.haycancha.R;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.drawer.MainNavigationDrawer;


public class MainActivity extends BaseActivity {


    private DrawerLayout mDrawerLayout;
    private FrameLayout mLeftDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private MainNavigationDrawer mMainNavigationDrawer;
    private Boolean firstRun = true;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_main_drawer);

        setUpdDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));


    }

    private void setUpdDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftDrawer = (FrameLayout) findViewById(R.id.drawer_left);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mMainNavigationDrawer = new MainNavigationDrawer();

        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_left, mMainNavigationDrawer).commitAllowingStateLoss();

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();

        mMainNavigationDrawer.setUp(R.id.drawer_left, mDrawerLayout);

//        if (firstRun) {
//            mMainNavigationDrawer.openSection(mMainNavigationDrawer.mSelectedSection);
//            firstRun = false;
//        }
    }

    public MainNavigationDrawer getNavigationDrawer() {
        return mMainNavigationDrawer;
    }


}
