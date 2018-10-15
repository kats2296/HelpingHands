package com.helpinghands.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.helpinghands.EventDetailsActivity;
import com.helpinghands.Fragments.OrgHomeFragment;
import com.helpinghands.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrgHomeActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    private Fragment orgHomeFrag;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_home);
        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();
        onNavigation();

    }

    private void onNavigation() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.create_events:
                        orgHomeFrag = new OrgHomeFragment();
                        addFragment(orgHomeFrag);
                        break;

                    case R.id.ongoing_events:
                        orgHomeFrag = new OrgHomeFragment();
                        addFragment(orgHomeFrag);
                        break;

                    case R.id.previous_events:
                        orgHomeFrag = new OrgHomeFragment();
                        addFragment(orgHomeFrag);
                        break;
                }

                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.create_events);
    }

    private void addFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void startIntent() {

        Intent intent = new Intent(OrgHomeActivity.this, EventDetailsActivity.class);
        startActivity(intent);
    }

}
