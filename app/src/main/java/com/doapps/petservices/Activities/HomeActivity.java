package com.doapps.petservices.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.doapps.petservices.Adapters.HomePagerAdapter;
import com.doapps.petservices.R;
import com.doapps.petservices.Utils.PreferenceManager;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabs;
    ViewPager pager;
    HomePagerAdapter adapter;
    public static FragmentManager fragmentManager;
    private PreferenceManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        manager = PreferenceManager.getInstance(getApplicationContext());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                manager.logout();
                Intent intent = new Intent(HomeActivity.this,PetFlash.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
