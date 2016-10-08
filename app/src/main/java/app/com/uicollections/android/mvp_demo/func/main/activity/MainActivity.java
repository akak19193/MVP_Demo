package app.com.uicollections.android.mvp_demo.func.main.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import app.com.uicollections.android.mvp_demo.R;
import app.com.uicollections.android.mvp_demo.common.base.baseAdapter.BaseFragmentPagerAdapter;
import app.com.uicollections.android.mvp_demo.common.widget.NoScrollViewPager;
import app.com.uicollections.android.mvp_demo.func.detail.fragment.DetailFragment;
import app.com.uicollections.android.mvp_demo.func.detail.fragment.ReviewFragment;
import app.com.uicollections.android.mvp_demo.func.entitiy.MovieEntity;
import app.com.uicollections.android.mvp_demo.func.main.fragment.MainFragment;
import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @Bind(R.id.main_drawer_layout)
    DrawerLayout mDrawer;

    @Bind(R.id.main_viewPager)
    NoScrollViewPager mNoScrollViewPager;

    private DetailFragment mDetailFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private boolean opened = false;
    private ReviewFragment reviewFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        reviewFragment = new ReviewFragment();
        mDetailFragment = new DetailFragment();
        mNoScrollViewPager.setAdapter(
                new BaseFragmentPagerAdapter.Holder(getSupportFragmentManager()).add(
                        new MainFragment()).add(mDetailFragment).add(reviewFragment).build(null));
        mNoScrollViewPager.setOffscreenPageLimit(3);
        initDrawer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }

    public void toDetail(String url) {
        mNoScrollViewPager.setCurrentItem(1, true);
        mDetailFragment.showWeb(url);
    }

    public void toReview(MovieEntity.MovieBean movieBean) {
        mNoScrollViewPager.setCurrentItem(2, true);
        reviewFragment.setMovie(movieBean);

    }

    public void onBack() {
        mNoScrollViewPager.setCurrentItem(0, true);
        mDetailFragment.clear();
    }

    public void drawerAction(Boolean open) {
        if (open) {
            result.openDrawer();
        } else {
            result.closeDrawer();
        }
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            if (mNoScrollViewPager.getCurrentItem() != 0) {
                onBack();
            } else {
                super.onBackPressed();
            }
        }
    }

    public void initDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);
        final int PROFILE_SETTING = 1;
        final IProfile profile1 = new ProfileDrawerItem().withName("USER1").withEmail("USER1@gmail.com").withIcon(R.drawable.user1).withIdentifier(100);
        final IProfile profile2 = new ProfileDrawerItem().withName("USER2").withEmail("USER2@gmail.com").withIcon(R.drawable.user2).withIdentifier(101);
        final IProfile profile3 = new ProfileDrawerItem().withName("USER3").withEmail("USER3@gmail.com").withIcon(R.drawable.user3).withIdentifier(102);
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        profile1,
                        profile2,
                        profile3,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBar().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && profile.getIdentifier() == PROFILE_SETTING) {
                            int count = 100 + headerResult.getProfiles().size() + 1;
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman" + count).withEmail("batman" + count + "@gmail.com").withIcon(R.drawable.user3).withIdentifier(count);
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withHasStableIds(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .withSelectedItem(-1)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Drawer item1").withDescription("Drawer item1").withIcon(GoogleMaterial.Icon.gmd_rss_feed).withIdentifier(1).withSelectable(false),
                        new PrimaryDrawerItem().withName("Drawer item2").withDescription("Drawer item2").withIcon(GoogleMaterial.Icon.gmd_content_copy).withIdentifier(2).withSelectable(false),
                        new SectionDrawerItem().withName("Section Header"),
                        new SecondaryDrawerItem().withName("Collapsable").withIcon(GoogleMaterial.Icon.gmd_library_music).withIdentifier(3).withSelectable(false),
                        new SecondaryDrawerItem().withName("About").withIcon(GoogleMaterial.Icon.gmd_help).withIdentifier(4).withSelectable(false),
                        new DividerDrawerItem(),
                        new SwitchDrawerItem().withName("Switch").withIcon(GoogleMaterial.Icon.gmd_build).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new SwitchDrawerItem().withName("Switch2").withIcon(GoogleMaterial.Icon.gmd_build).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener).withSelectable(false),
                        new ToggleDrawerItem().withName("Toggle").withIcon(GoogleMaterial.Icon.gmd_library_books).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new DividerDrawerItem(),
                        new SwitchDrawerItem().withName("Switch3").withIcon(GoogleMaterial.Icon.gmd_build).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new SwitchDrawerItem().withName("Switch4").withIcon(GoogleMaterial.Icon.gmd_build).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener).withSelectable(false),
                        new ToggleDrawerItem().withName("Toggle2").withIcon(GoogleMaterial.Icon.gmd_library_books).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 4) {
                                Toast.makeText(getApplicationContext(), "enter about page", Toast.LENGTH_SHORT).show();
                            } else if (drawerItem.getIdentifier() == 3) {
                                //showcase a simple collapsable functionality
                                if (opened) {
                                    //remove the items which are hidden
                                    result.removeItems(2000, 2001);
                                } else {
                                    int curPos = result.getPosition(drawerItem) + 1;
                                    result.addItemsAtPosition(
                                            curPos,
                                            new SecondaryDrawerItem().withName("CollapsableItem").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_opacity).withIdentifier(2000),
                                            new SecondaryDrawerItem().withName("CollapsableItem 2").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_pets).withIdentifier(2001)
                                    );
                                }
                                opened = !opened;
                                return true;
                            }
                        }

                        return false;
                    }
                })
                .withShowDrawerOnFirstLaunch(false)
                .build();
    }

    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            if (drawerItem instanceof Nameable) {
                Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
            } else {
                Log.i("material-drawer", "toggleChecked: " + isChecked);
            }
        }
    };
}
