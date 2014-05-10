package com.sita.mob.controller.facility;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import com.parse.*;
import com.sita.mob.R;
import com.sita.mob.model.FacilityItem;
import com.sita.mob.datasource.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * STEP in wizard - each facility.
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p />
 */
public class FacilityFragment extends Fragment implements AbsListView.OnItemClickListener {
    private OnFragmentInteractionListener mListener;
    private ImageView mPhotoView;
    private Button mPhotoBtn;
    private Button mSaveBtn;
    private String schoolCode;
    private TextView descr;
    ParseFile photoFile;
    int parameter;
    int position;

    RampContent fac;
    LibContent lib;

    public static final int BARRIER = 0;
    public static final int TOILETS = 1;
    public static final int DRINKING = 2;
    public static final int PLAYGROUND = 3;
    public static final int LIB = 4;

    //public static final int
    //public static final int



    Button.OnClickListener mTakePicOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakePictureIntent();
                }
            };

    Button.OnClickListener mSaveOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveFacility();
                }
            };

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static FacilityFragment newInstance(int position, String schoolCode) {
        FacilityFragment fragment = new FacilityFragment();
        Bundle args = new Bundle();
        args.putInt("POS", position);
        args.putString("schoolCode", schoolCode);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FacilityFragment() {
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.
                    getExtras();
            Bitmap rawImg = (Bitmap) extras.get("data");

            saveScaledPhoto(rawImg);
        }
    }

    private void saveScaledPhoto(Bitmap rawImg) {

        // Resize photo from camera byte array
        Bitmap scaledImg = Bitmap.createScaledBitmap(rawImg, 200, 200
                * rawImg.getHeight() / rawImg.getWidth(), false);

        // Override Android default landscape orientation and save portrait
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedScaledMealImage = Bitmap.createBitmap(scaledImg, 0,
                0, scaledImg.getWidth(), scaledImg.getHeight(),
                matrix, true);

        // Set image in view
        mPhotoView.setImageBitmap(scaledImg);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        rotatedScaledMealImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        byte[] scaledData = bos.toByteArray();

        // Save the scaled image to Parse
        photoFile = new ParseFile("facility_photo.jpg", scaledData);
        photoFile.saveInBackground(new SaveCallback() {

            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(getActivity(),
                            "Error saving: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } else {
                    // saveFacility();
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("POS");
        mAdapter = new ArrayAdapter<FacilityItem>(getActivity(),
                android.R.layout.simple_list_item_single_choice, android.R.id.text1, getItems());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.facility_view, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        mPhotoView = (ImageView) view.findViewById(R.id.photo);
        mPhotoBtn = (Button) view.findViewById(R.id.takePhoto);
        mPhotoBtn.setOnClickListener(mTakePicOnClickListener);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        mSaveBtn = (Button)view.findViewById(R.id.save_button);
        mSaveBtn.setOnClickListener(mSaveOnClickListener);

        descr = (TextView) view.findViewById(R.id.descr);

        switch(position) {
            case BARRIER:
                descr.setText(getString(R.string.ramp_descr));
                break;
            case TOILETS:
                descr.setText(getString(R.string.toilet_descr));
                break;
            case DRINKING:
                descr.setText(getString(R.string.drinking_descr));
                break;
            case PLAYGROUND:
                descr.setText(getString(R.string.playground_descr));
                break;
            case LIB:
                descr.setText(getString(R.string.lib_descr));
                break;
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        schoolCode = getArguments().getString("schoolCode");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    public void saveFacility() {
        ParseObject report = new ParseObject("report");
        if (!TextUtils.isEmpty(schoolCode)) {
            report.put("schoolCode", schoolCode);
        }
        report.put("status", "OPEN");
        report.put("facilityNumber", position + 1);
        report.put("comments", "dummyComments");
        report.put("parameterNumber", parameter+1);
        if (photoFile != null) {
            report.put("photo", photoFile);
        }
        report.saveInBackground();
        Toast.makeText(getActivity(),
                "Saving facility details..",Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),
                        "Saved successfully",Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }}, 3000);
        }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener && getItems() != null) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(getItems().get(position).id);
            parameter = position;
        }
    }

    public List<FacilityItem> getItems() {
        switch (position) {
            case BARRIER:
                return new RampContent(getActivity()).ITEMS;
            case TOILETS:
                return new ToiletContent(getActivity()).ITEMS;
            case DRINKING:
                return new DrinkingContent(getActivity()).ITEMS;
            case PLAYGROUND:
                return  new PlaygroundContent(getActivity()).ITEMS;
            case LIB:
                return new LibContent(getActivity()).ITEMS;
        }
        return new ArrayList<FacilityItem>();
    }

    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity.
    */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
    }

    /**
     * Fragment used for managing interactions for and presentation of a navigation drawer.
     * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
     * design guidelines</a> for a complete explanation of the behaviors implemented here.
     */
    public static class NavigationDrawerFragment extends Fragment {
        /**
         * Remember the position of the selected item.
         */
        public static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

        /**
         * Per the design guidelines, you should show the drawer on launch until the user manually
         * expands it. This shared preference tracks this.
         */
        private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

        /**
         * A pointer to the current callbacks instance (the Activity).
         */
        private NavigationDrawerCallbacks mCallbacks;

        /**
         * Helper component that ties the action bar to the navigation drawer.
         */
        private ActionBarDrawerToggle mDrawerToggle;

        private DrawerLayout mDrawerLayout;
        private ListView mDrawerListView;
        private View mFragmentContainerView;

        private int mCurrentSelectedPosition = 0;
        private boolean mFromSavedInstanceState;
        private boolean mUserLearnedDrawer;

        public NavigationDrawerFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Read in the flag indicating whether or not the user has demonstrated awareness of the
            // drawer. See PREF_USER_LEARNED_DRAWER for details.
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
            mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

            if (savedInstanceState != null) {
                mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
                mFromSavedInstanceState = true;
            }
            // Select either the default item (0) or the last selected item.
            selectItem(mCurrentSelectedPosition);
        }

        @Override
        public void onActivityCreated (Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            // Indicate that this fragment would like to influence the set of actions in the action bar.
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            mDrawerListView = (ListView) inflater.inflate(
                    R.layout.fragment_navigation_drawer, container, false);
            mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectItem(position);
                }
            });
            mDrawerListView.setAdapter(new ArrayAdapter<String>(
                    getActionBar().getThemedContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    new String[]{
                            getString(R.string.ramp),
                            getString(R.string.toilet),
                            getString(R.string.drinkingWater),
                            getString(R.string.playground),
                            getString(R.string.lib)
                    }));
            mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
            return mDrawerListView;
        }

        public boolean isDrawerOpen() {
            return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
        }

        /**
         * Users of this fragment must call this method to set up the navigation drawer interactions.
         *
         * @param fragmentId   The android:id of this fragment in its activity's layout.
         * @param drawerLayout The DrawerLayout containing this fragment's UI.
         */
        public void setUp(int fragmentId, DrawerLayout drawerLayout) {
            mFragmentContainerView = getActivity().findViewById(fragmentId);
            mDrawerLayout = drawerLayout;

            // set a custom shadow that overlays the main content when the drawer opens
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
            // set up the drawer's list view with items and click listener

            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

            // ActionBarDrawerToggle ties together the the proper interactions
            // between the navigation drawer and the action bar app icon.
            mDrawerToggle = new ActionBarDrawerToggle(
                    getActivity(),                    /* host Activity */
                    mDrawerLayout,                    /* DrawerLayout object */
                    R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                    R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                    R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
            ) {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    if (!isAdded()) {
                        return;
                    }

                    getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    if (!isAdded()) {
                        return;
                    }

                    if (!mUserLearnedDrawer) {
                        // The user manually opened the drawer; store this flag to prevent auto-showing
                        // the navigation drawer automatically in the future.
                        mUserLearnedDrawer = true;
                        SharedPreferences sp = PreferenceManager
                                .getDefaultSharedPreferences(getActivity());
                        sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                    }

                    getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                }
            };

            // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
            // per the navigation drawer design guidelines.
            if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
                mDrawerLayout.openDrawer(mFragmentContainerView);
            }

            // Defer code dependent on restoration of previous instance state.
            mDrawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    mDrawerToggle.syncState();
                }
            });

            mDrawerLayout.setDrawerListener(mDrawerToggle);
        }

        private void selectItem(int position) {
            mCurrentSelectedPosition = position;
            if (mDrawerListView != null) {
                mDrawerListView.setItemChecked(position, true);
            }
            if (mDrawerLayout != null) {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }
            if (mCallbacks != null) {
                mCallbacks.onNavigationDrawerItemSelected(position);
            }
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            try {
                mCallbacks = (NavigationDrawerCallbacks) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            mCallbacks = null;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            // Forward the new configuration the drawer toggle component.
            mDrawerToggle.onConfigurationChanged(newConfig);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // If the drawer is open, show the global app actions in the action bar. See also
            // showGlobalContextActionBar, which controls the top-left area of the action bar.
            if (mDrawerLayout != null && isDrawerOpen()) {
                inflater.inflate(R.menu.global, menu);
                showGlobalContextActionBar();
            }
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        /**
         * Per the navigation drawer design guidelines, updates the action bar to show the global app
         * 'context', rather than just what's in the current screen.
         */
        private void showGlobalContextActionBar() {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setTitle(R.string.app_name);
        }

        private ActionBar getActionBar() {
            return ((ActionBarActivity) getActivity()).getSupportActionBar();
        }

        /**
         * Callbacks interface that all activities using this fragment must implement.
         */
        public static interface NavigationDrawerCallbacks {
            /**
             * Called when an item in the navigation drawer is selected.
             */
            void onNavigationDrawerItemSelected(int position);
        }
    }
}
