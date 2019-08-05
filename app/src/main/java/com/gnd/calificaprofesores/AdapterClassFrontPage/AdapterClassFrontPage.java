package com.gnd.calificaprofesores.AdapterClassFrontPage;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdapterClassFrontPage extends FragmentPagerAdapter  {

    private final String[] TITLES = {
            "VISTA GENERAL",
            "OPINIONES RECIENTES",
            "TU OPINIÓN"}; // Opiniones importantes para futuro



    public AdapterClassFrontPage(FragmentManager fm) {
        super(fm);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
    public int getCount() {
        return TITLES.length;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ActivityClassFrontPageCapital();
            case 1:
                return new ActivityOpinionRecent();
            case 2:
                return new ActivityYourOpinion();
        }
        return new ActivityYourOpinion();
    }
}

