package com.doapps.petservices.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.doapps.petservices.Adapters.HomePagerAdapter;
import com.doapps.petservices.R;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabs;
    ViewPager pager;
    HomePagerAdapter adapter;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupTabs();
    }

    private void setupTabs() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new HomePagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        tabs.getTabAt(0).setText("Inicio");
        tabs.getTabAt(1).setText("Mensajes");
        tabs.getTabAt(2).setText("Perfil");

        fragmentManager = getSupportFragmentManager();

        tabs.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.black ));

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }
}
