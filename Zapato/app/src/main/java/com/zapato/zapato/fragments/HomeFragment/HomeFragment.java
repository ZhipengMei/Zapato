package com.zapato.zapato.fragments.HomeFragment;//package com.zapato.zapato.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zapato.zapato.HomeView.Tab1Fragment;
import com.zapato.zapato.HomeView.Tab2Fragment;
import com.zapato.zapato.HomeView.Tab3Fragment;
import com.zapato.zapato.Model.SectionsPageAdapter;
import com.zapato.zapato.R;
import com.zapato.zapato.activities.MainActivity;
import com.zapato.zapato.fragments.BaseFragment;
import com.zapato.zapato.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends BaseFragment {

    private static final String TAG = "Home";

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private ArrayList<LinearLayout> bottomTabsList;

    public static HomeFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mSectionsPageAdapter = new SectionsPageAdapter(getFragmentManager());

        // Set up ViewPager with the sections adapter
        mViewPager = (ViewPager) view.findViewById(R.id.container); //creates id for ViewPager in main layout
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        final View homeDot = view.findViewById(R.id.home_dot);
        homeDot.setVisibility(View.VISIBLE);


        //ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ( (MainActivity)getActivity()).updateToolbarTitle("Zapato");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void setupViewPager(ViewPager viewPager) {
        Log.d("setupViewPager","in method");
        SectionsPageAdapter adapter = new SectionsPageAdapter(getFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Buy Now");
        adapter.addFragment(new Tab2Fragment(), "Near Me");
        adapter.addFragment(new Tab3Fragment(), "Trending");
        viewPager.setAdapter(adapter);
    }
}
