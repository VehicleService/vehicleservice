package com.example.navigationdemo.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigationdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Details extends Fragment {

    public ViewPager viewPager;
    public TabLayout tabLayout;

    public Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_details, container, false);

        viewPager=(ViewPager)v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout=(TabLayout)v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Inflate the layout for this fragment
        return v;
    }

    public void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter=new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new Userhistory(),"History");
        adapter.addFragment(new Userrecent(),"Recent");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments=new ArrayList<>();
        List<String> title=new ArrayList<>();
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment,String titles){
            fragments.add(fragment);
            title.add(titles);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }

}
