package com.example.myecomerceapp;

import static com.example.myecomerceapp.fragments.ItemGridViewFragment.getData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myecomerceapp.adapters.BannerAdapter;
import com.example.myecomerceapp.fragments.AccountFragment;
import com.example.myecomerceapp.fragments.CartFragment;
import com.example.myecomerceapp.fragments.CategoryFragment;
import com.example.myecomerceapp.fragments.SalesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener,DrawerLayout.DrawerListener , ItemOnClickInterface {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
   public static RecyclerView bannerRecycleView;
    FrameLayout frameLayout;
   public static TextView specialsTv;

   public static SearchView searchView;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*toolbar = findViewById(R.id.toolbar);*/
        bottomNavigationView=findViewById(R.id.bottomnav);
        specialsTv=findViewById(R.id.specalstv);
        frameLayout=findViewById(R.id.frameLayout);
        searchView=findViewById(R.id.searchview);

        bannerRecycleView = findViewById(R.id.bannerRecycleView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        bannerRecycleView.setLayoutManager(linearLayoutManager);
        BannerAdapter bannerAdapter = new BannerAdapter(getData("Fashion"),this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bannerRecycleView.setAdapter(bannerAdapter);




        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()== R.id.home){

                        loadFragment(new CategoryFragment());
                        bannerRecycleView.setVisibility(View.VISIBLE);


                }
                else if (item.getItemId()== R.id.sales) {
                    loadFragment(new SalesFragment());
                    removeBannerRecyclerView();



                } else if (item.getItemId()== R.id.account) {
                    loadFragment(new AccountFragment());
                    removeBannerRecyclerView();



                }else if (item.getItemId()== R.id.cart) {
                    loadFragment(new CartFragment());
                    removeBannerRecyclerView();



                }
                return true;
            }
        });
        setSupportActionBar(toolbar);

        setupDrawer();
        loadFragment(new CategoryFragment());
    }

    public static void removeSearchView() {
        searchView.setVisibility(View.GONE);
    }

    public static  void removeBannerRecyclerView(){
        specialsTv.setVisibility(View.GONE);
        bannerRecycleView.setVisibility(View.GONE);
    }

    private  void loadFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void setupDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        drawerLayout.addDrawerListener(this);

        setupNavigationView();
    }
    private void setupNavigationView() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setDefaultMenuItem();
        setupHeader();
    }

    private void setDefaultMenuItem() {
        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);
    }

    private void setupHeader() {
        View header = navigationView.getHeaderView(0);
        header.findViewById(R.id.header_title).setOnClickListener(view -> Toast.makeText(
                MainActivity.this,
                getString(R.string.title_click),
                Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        return true;
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }


    @Override
    public void onItemClicked(int position) {

    }
}