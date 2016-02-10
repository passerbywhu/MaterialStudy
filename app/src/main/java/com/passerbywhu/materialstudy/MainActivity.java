package com.passerbywhu.materialstudy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.passerbywhu.materialstudy.behavior.fab.FABActivity;
import com.passerbywhu.materialstudy.behavior.follow.FollowActivity;
import com.passerbywhu.materialstudy.behavior.follow.FollowBehavior;
import com.passerbywhu.materialstudy.behavior.footer.FooterActivity;
import com.passerbywhu.materialstudy.behavior.scrollToTop.ScrollToTopActivity;
import com.passerbywhu.materialstudy.recyclerview.DragSortRecyclerView;
import com.passerbywhu.materialstudy.recyclerview.DragSortRecyclerViewAdapter;
import com.passerbywhu.materialstudy.recyclerview.HeaderFooterRecyclerViewAdapter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton mFabBtn;
    private View mRootLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private NavigationView mNavigationView;
    private DragSortRecyclerView mRecyclerView;
    private ActivityAdapter mAdapter;
    private Class[] activityClasses = new Class[]{
            FABActivity.class, FollowActivity.class, FooterActivity.class, ScrollToTopActivity.class,
            FABActivity.class, FollowActivity.class, FooterActivity.class, ScrollToTopActivity.class,
            FABActivity.class, FollowActivity.class, FooterActivity.class, ScrollToTopActivity.class,
            FABActivity.class, FollowActivity.class, FooterActivity.class, ScrollToTopActivity.class
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootLayout = findViewById(R.id.rootLayout);
        mFabBtn = (FloatingActionButton) findViewById(R.id.fabBtn);
        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mRootLayout, "Hello, I am Snackbar!", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
            }
        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.sesame_open, R.string.sesame_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mToggle);

        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Snackbar.make(mRootLayout, item.getTitle(), Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab 1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab 2"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab 3"));

        mRecyclerView = (DragSortRecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ActivityAdapter(this);
        mAdapter.setData(Arrays.asList(activityClasses));
        mAdapter.setOnItemClickListener(new HeaderFooterRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                Intent intent = new Intent(MainActivity.this, mAdapter.getItem(position));
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
}