package com.example.myecomerceapp.activitys;


import static com.example.myecomerceapp.fragments.ItemGridViewFragment.getData;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myecomerceapp.adapters.CategoryAdapter;
import com.example.myecomerceapp.fragments.ItemGridViewFragment;
import com.example.myecomerceapp.interfaces.ItemOnClickInterface;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.BannerAdapter;
import com.example.myecomerceapp.fragments.AccountFragment;
import com.example.myecomerceapp.fragments.CartFragment;
import com.example.myecomerceapp.fragments.SalesFragment;
import com.example.myecomerceapp.models.CategoryModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener,DrawerLayout.DrawerListener , ItemOnClickInterface {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
   public static RecyclerView specialsBanner;
    public static FrameLayout frameLayout;
   public static TextView specialsTv;

   public static SearchView searchView;

    public static CardView displayBanner;

    public static RecyclerView categoryRecycleView;



    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView=findViewById(R.id.bottomnav);
        specialsTv=findViewById(R.id.specalstv);
        frameLayout=findViewById(R.id.frameLayout);
        displayBanner=findViewById(R.id.displayBanner);
        /*searchView=findViewById(R.id.searchview);*/
       specialsBanner = findViewById(R.id.specialsbanner);
       categoryRecycleView = findViewById(R.id.categoriesRecycleView);

        //special banner recycleview
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
       specialsBanner.setLayoutManager(linearLayoutManager);
        BannerAdapter bannerAdapter = new BannerAdapter(getData("Fashion"),this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
       specialsBanner.setAdapter(bannerAdapter);

      //category recycleView
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecycleView.setLayoutManager(linearLayoutManager1);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getCategoryData());
        categoryRecycleView.setAdapter(categoryAdapter);

        // instanciate and declare a 'TimerClass', pass 'delay' and 'period' arguments
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if(linearLayoutManager.findLastCompletelyVisibleItemPosition() < (bannerAdapter.getItemCount() -1)) {


                    linearLayoutManager.smoothScrollToPosition(specialsBanner, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);

                } else if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == (bannerAdapter.getItemCount() -1)) {

                    // scroll back to the first item in recyclerview
                    linearLayoutManager.smoothScrollToPosition(specialsBanner, new RecyclerView.State(), 0);

                }

            }// end 'run' method
        }, 0, 3000);


       /* searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);

                return true;
            }
        });
*/
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId()== R.id.home){
                addBannerRecyclerView();

            }
            else if (item.getItemId()== R.id.sales) {
                loadFragment(new SalesFragment());
                categoryRecycleView.setVisibility(View.GONE);
                removeBannerRecyclerView();

            } else if (item.getItemId()== R.id.account) {
                loadFragment(new AccountFragment());
                categoryRecycleView.setVisibility(View.GONE);
                removeBannerRecyclerView();

            }else if (item.getItemId()== R.id.cart) {
                loadFragment(new CartFragment());
                categoryRecycleView.setVisibility(View.GONE);
                removeBannerRecyclerView();

            }
            return true;
        });

        setSupportActionBar(toolbar);
        setupDrawer();

    }

    public static List<CategoryModel> getCategoryData() {
        List<CategoryModel> categoryModelArrayList = new ArrayList<>();
        categoryModelArrayList.add(new CategoryModel("Phones", R.drawable.smartphones_category_icon,"SmartPhones"));
        categoryModelArrayList.add(new CategoryModel("Laptop", R.drawable.laptop_category_icon,"Laptop"));
        categoryModelArrayList.add(new CategoryModel("Games", R.drawable.games_category_icon,"Games"));
        categoryModelArrayList.add(new CategoryModel("Consoles",R.drawable.gamingconsoles_category_icon ,"Gaming Consoles"));
        categoryModelArrayList.add(new CategoryModel("Appliances",R.drawable.homeappliance_category_icon,"Home Appliances"));
        return categoryModelArrayList;
    }

    public static  void removeBannerRecyclerView(){
        frameLayout.setVisibility(View.VISIBLE);
       /* searchView.setVisibility(View.GONE);*/
        specialsTv.setVisibility(View.GONE);
       specialsBanner.setVisibility(View.GONE);
       displayBanner.setVisibility(View.GONE);
    }

    public static  void addBannerRecyclerView(){
        frameLayout.setVisibility(View.GONE);
       /* searchView.setVisibility(View.VISIBLE);*/
        categoryRecycleView.setVisibility(View.VISIBLE);
        specialsTv.setVisibility(View.VISIBLE);
       specialsBanner.setVisibility(View.VISIBLE);
       displayBanner.setVisibility(View.VISIBLE);

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
        removeBannerRecyclerView();
        categoryRecycleView.setVisibility(View.VISIBLE);
        CategoryModel categoryModel=getCategoryData().get(position);
        ItemGridViewFragment itemGridViewFragment=new ItemGridViewFragment();
        ItemGridViewFragment.categoryId=categoryModel.getCategoryId();
        loadFragment(itemGridViewFragment);
    }

}