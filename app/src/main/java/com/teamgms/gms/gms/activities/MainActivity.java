package com.teamgms.gms.gms.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.teamgms.gms.gms.BaseActivity;
import com.teamgms.gms.gms.Config;
import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.Toolbar)
    Toolbar toolbar;

    private CharSequence mTitle, mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    Button btnSignOut, btnLogin;
    Unbinder unbinder;
    Bundle bundle;
    private User userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        unbinder = ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        if(bundle != null) {
          userInfo = (User)bundle.getSerializable(Config.USER);
        }

        //home activity navigation drawer

//        mTitle = mDrawerTitle = getTitle();

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,
                toolbar,/* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
            );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        updateHeaderView();
    }

    public void updateHeaderView(){
        LinearLayout mHeader = (LinearLayout)navigationView.getHeaderView(0);
        btnSignOut = ButterKnife.findById(mHeader, R.id.btnSignOut);
        btnLogin = ButterKnife.findById(mHeader, R.id.btnLogin);
        TextView tvName = ButterKnife.findById(mHeader, R.id.tvNickname);
        ImageView imgPhoto = ButterKnife.findById(mHeader, R.id.imgPhoto);
        tvName.setText(userInfo.userName);
        Glide.with(this).load(userInfo.profilePictureUrl).into(imgPhoto);
        btnSignOut.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                btnSignOut.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
            }
        });
        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin(MainActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_myPage) {

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
