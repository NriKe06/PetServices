package com.doapps.petservices.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.doapps.petservices.Fragments.MensajesFragment;
import com.doapps.petservices.Fragments.PerfilFragment;
import com.doapps.petservices.Fragments.PublicacionesFragment;

/**
 * Created by NriKe on 06/05/2017.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PublicacionesFragment();
            case 1:
                return new MensajesFragment();
            case 2:
                return new PerfilFragment();
            default:
                return new PublicacionesFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
