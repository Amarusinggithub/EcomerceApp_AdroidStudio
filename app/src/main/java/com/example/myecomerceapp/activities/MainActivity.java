package com.example.myecomerceapp.activities;



import static com.example.myecomerceapp.fragments.ProductRecyclerViewFragment.categoryId;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myecomerceapp.adapters.CategoryAdapter;
import com.example.myecomerceapp.adapters.ProductAdapter;
import com.example.myecomerceapp.fragments.ProductRecyclerViewFragment;
import com.example.myecomerceapp.fragments.ProductViewFragment;
import com.example.myecomerceapp.interfaces.MyCategoryOnClickListener;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.fragments.AccountFragment;
import com.example.myecomerceapp.fragments.CartFragment;
import com.example.myecomerceapp.fragments.SalesFragment;
import com.example.myecomerceapp.interfaces.MyProductOnClickListener;
import com.example.myecomerceapp.models.Category;
import com.example.myecomerceapp.models.Product;
import com.example.myecomerceapp.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener,DrawerLayout.DrawerListener , MyCategoryOnClickListener, MyProductOnClickListener {
    public static final String LAPTOP = "Laptop";
    public static final String PHONES = "Phones";
    public static final String GAMES = "Games";
    public static final String CONSOLES = "Consoles";
    public static final String APPLIANCES = "Appliances";
    public static final String POPULAR_PRODUCTS = "popular-products";
    public static final String EVERY_PRODUCT = "every-Product";
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private static SearchView searchView;
    public static FrameLayout frameLayout;
    public static CardView displayBanner;
    public static RecyclerView categoryRecycleView;
    private static LinearLayout popularProductsLinearLayout;
    public static RecyclerView popularProductsRecyclerview;
    private User user;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       getUserFromDatabase();


    }

    private void getUserFromDatabase() {
        email =getIntent().getStringExtra("email");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference parentReference = firebaseDatabase.getReference("MyDatabase");
        DatabaseReference usersReference = parentReference.child("users");
        Query checkUser = usersReference.orderByChild("email").equalTo(email);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        if(Objects.equals(Objects.requireNonNull(userSnapshot.getValue(User.class)).getEmail(), email)){
                            user=userSnapshot.getValue(User.class);
                            if (user!=null){
                                Bundle bundle=new Bundle();
                                bundle.putString("username",user.getUsername());
                                initializeViews();

                                return;
                            }else{
                                Log.d(TAG,"user is null");
                            }
                            return;
                        }

                    }

                } else {
                    Toast.makeText(MainActivity.this, "Email not found. Please try again or create an account.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error retrieving user data.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initializeViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottomnav);
        searchView=findViewById(R.id.searchview);
        frameLayout = findViewById(R.id.frameLayout);
        displayBanner = findViewById(R.id.displayBanner);
        categoryRecycleView = findViewById(R.id.categoriesRecycleView);
        popularProductsRecyclerview = findViewById(R.id.popularproductrecycleview);
        popularProductsLinearLayout = findViewById(R.id.popularproductsLL);


        setSupportActionBar(toolbar);
        setupDrawer();
        setupCategoryRecyclerView();
        setupPopularProductsRecyclerView();
        setupBottomNavigationView();

    }

    private void setupCategoryRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecycleView.setLayoutManager(linearLayoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getCategory());
        categoryRecycleView.setAdapter(categoryAdapter);
    }

    private void setupPopularProductsRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popularProductsRecyclerview.setLayoutManager(linearLayoutManager);
        ProductAdapter popularProductAdapter = new ProductAdapter(this, getProductsData(POPULAR_PRODUCTS));
        popularProductsRecyclerview.setAdapter(popularProductAdapter);
    }

    private void setupBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId()== R.id.home){

                removeViews();
                addViews();

            }
            else if (item.getItemId()== R.id.sales) {
                loadFragment(new SalesFragment());
                categoryRecycleView.setVisibility(View.GONE);
                removeViews();

            } else if (item.getItemId()== R.id.account) {
                loadFragment(new AccountFragment());
                categoryRecycleView.setVisibility(View.GONE);
                removeViews();

            }else if (item.getItemId()== R.id.cart) {
                loadFragment(new CartFragment());
                removeViews();

            }
            return true;
        });

    }



    public static  void removeViews(){
        searchView.setVisibility(View.GONE);
        categoryRecycleView.setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
       displayBanner.setVisibility(View.GONE);
       popularProductsLinearLayout.setVisibility(View.GONE);
       popularProductsRecyclerview.setVisibility(View.GONE);
    }

    public static  void addViews(){
        categoryRecycleView.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.VISIBLE);
       displayBanner.setVisibility(View.VISIBLE);
       popularProductsRecyclerview.setVisibility(View.VISIBLE);
       popularProductsLinearLayout.setVisibility(View.VISIBLE);

    }


    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        drawerLayout.addDrawerListener(this);

        setupNavigationView();
    }
    private void setupNavigationView() {
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
        TextView headerTitle = header.findViewById(R.id.header_title);
        TextView headerEmail = header.findViewById(R.id.header_email);
        headerTitle
                .setOnClickListener(view -> Toast.makeText(
                MainActivity.this,
                getString(R.string.title_click),
                Toast.LENGTH_SHORT).show());

        headerEmail
                .setOnClickListener(view -> Toast.makeText(
                        MainActivity.this,
                        getString(R.string.title_click),
                        Toast.LENGTH_SHORT).show());

if(user!=null){
    headerEmail.setText(user.getEmail());
    headerTitle.setText(user.getUsername());
}else {
    Log.d(TAG,"user is null");
    Toast.makeText(MainActivity.this,"user is null",Toast.LENGTH_SHORT).show();
}
    }

    private  void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    public static List<Category> getCategory() {
        List<Category> categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category(PHONES, R.drawable.smartphones_category_icon, PHONES));
        categoryArrayList.add(new Category(LAPTOP, R.drawable.laptop_category_icon, LAPTOP));
        categoryArrayList.add(new Category(GAMES, R.drawable.games_category_icon, GAMES));
        categoryArrayList.add(new Category(CONSOLES,R.drawable.gamingconsoles_category_icon , CONSOLES));
        categoryArrayList.add(new Category(APPLIANCES,R.drawable.homeappliance_category_icon, APPLIANCES));
        return categoryArrayList;
    }

    public static List<Product> getProductsData(String id) {
        List<Product>productsArrayList = new ArrayList<>();
        if (PHONES.equals(id)) {
            productsArrayList.add(new Product(R.drawable.s24plus,"SAMSUNG Galaxy S24+ Plus ","$969.99 ","SAMSUNG Galaxy S24+ Plus Cell Phone, 512GB AI Smartphone, Unlocked Android, 50MP Camera, Fastest Processor, Long Battery Life, US Version, 2024, Onyx Black", PHONES));
            productsArrayList.add(new Product(R.drawable.oneplus_12,"OnePlus 12","$899.99","OnePlus 12,16GB RAM+512GB,Dual-SIM,Unlocked Android Smartphone,Supports 50W Wireless Charging,Latest Mobile Processor,Advanced Hasselblad Camera,5400 mAh Battery,2024,Flowy Emerald", PHONES));
            productsArrayList.add(new Product(R.drawable.pixel_8,"Google Pixel 8 ","$499.00 ","Smartphone with Advanced Pixel Camera, 24-Hour Battery, and Powerful Security - Obsidian - 128 GB", PHONES));
            productsArrayList.add(new Product(R.drawable.a54_samsung,"SAMSUNG Galaxy A54 5G ","$449.99","SAMSUNG Galaxy A54 5G A Series Cell Phone, Unlocked Android Smartphone, 128GB, 6.4” Fluid Display Screen, Pro Grade Camera, Long Battery Life, Refined Design, US Version, 2023, Awesome Black", PHONES));
            productsArrayList.add(new Product(R.drawable.redmi_note13_pro,"Redmi Note 13 PRO 5G ","$310.96","Xiaomi Redmi Note 13 PRO 5G + 4G LTE (256GB + 8GB) 6.67, 200MP Triple (Tmobile Mint Tello & Global) Global Bands Unlocked + (Fast Car Dual Charger Bundle) (Midnight Black (Global ROM))", PHONES));
            productsArrayList.add(new Product(R.drawable.asus_rog_phone_8_pro,"ASUS ROG Phone 8 Pro","$1,199.99","ASUS ROG Phone 8 Pro Unlocked Android Phone, US Version, 6.78 165Hz AMOLED Display, 512GB, 16GB RAM, 5500mAh Battery, 50MP Gimbal Camera, 32MP Front, Snapdragon 8 Gen 3, Dual-SIM, Phantom Black", PHONES));
            productsArrayList.add(new Product(R.drawable.pixel_8_pro,"Google Pixel 8 Pro","$749.00 ","Google Pixel 8 Pro - Unlocked Android Smartphone with Telephoto Lens and Super Actua Display - 24-Hour Battery - Porcelain - 128 GB", PHONES));
            productsArrayList.add(new Product(R.drawable.iphone_13,"Apple iPhone 13","$388.10","The iPhone 13 features a 6.1-inch (155 mm) display with Super Retina XDR OLED technology at a resolution of 2532×1170 pixels and a pixel density of about 460 PPI with a refresh rate of 60 Hz and Dolby Vision HDR.", PHONES));
            productsArrayList.add(new Product(R.drawable.iphone_13_pro,"Apple iPhone 13 Pro","$494.75","iPhone 13 Pro, 256GB, Sierra Blue - Unlocked (Renewed Premium), comes with charger, Mfi cable and SIM Pin ejector", PHONES));
            productsArrayList.add(new Product(R.drawable.s10_plus,"SAMSUNG Galaxy S10+ Plus","$265.72","SAMSUNG Galaxy S10+ Plus (128GB, 8GB) 6.4\" AMOLED, Snapdragon 855, IP68 Water Resistant, Global 4G LTE (GSM + CDMA) Unlocked (AT&T, Verizon, T-Mobile, Metro) G975U (Prism Blue)", PHONES));
            productsArrayList.add(new Product(R.drawable.iphone_15_pro_max,"Apple iPhone 15 Pro Max","$1,529.97","iPhone 15 Pro Max has a 6.7-inch all-screen Super Retina XDR display with the Dynamic Island. The back is textured matte glass, and there is a contoured-edge titanium band around the frame.", PHONES));
            productsArrayList.add(new Product(R.drawable.iphone_14_pro_max,"Apple iPhone 14 Pro Max","$799.90","6.7-inch Super Retina XDR display featuring Always-On & ProMotion.", PHONES));
        } else if (LAPTOP.equals(id)) {
            productsArrayList.add(new Product(R.drawable.acer_aspire_3,"Acer Aspire 3","$299.99","Acer Aspire 3 A315-24P-R7VH Slim Laptop | 15.6\" Full HD IPS Display | AMD Ryzen 3 7320U Quad-Core Processor | AMD Radeon Graphics | 8GB LPDDR5 | 128GB NVMe SSD | Wi-Fi 6 | Windows 11 Home in S Mode", LAPTOP));
            productsArrayList.add(new Product(R.drawable.leveni_legion_slim_7i,"Lenovo Legion Slim 7i ","$1,499.99","Lenovo Legion Slim 7i Gaming & Entertainment Laptop (Intel i9-13900H 14-Core, 16GB DDR5 5200MHz RAM, 1TB SSD, GeForce RTX 4070, 16.0\" Win 11 Home) with Microsoft 365 Personal, Dockztorm Hub", LAPTOP));
            productsArrayList.add(new Product(R.drawable.asus_rog_strix_16,"ASUS ROG Strix Scar 16","$2,899.99","ASUS ROG Strix Scar 16 (2024) Gaming Laptop, 16” Nebula HDR 16:10 QHD 240Hz/3ms, 1100 nits, Mini LED Display, GeForce RTX 4080, Intel Core i9-14900HX, 32GB DDR5, 1TB SSD, Windows 11 Pro, G634JZR-XS96", LAPTOP));
            productsArrayList.add(new Product(R.drawable.asus_tuf_a17,"SUS TUF A17 Gaming Laptop","$1,859.00","ASUS TUF A17 Gaming Laptop - 17.3\" FHD Display, AMD Ryzen 9-7940HS (8-core), NVIDIA GeForce RTX 4070, 32GB DDR5, 1TB SSD, Backlit Keyboard, Wi-Fi 6, Windows 11 Home, with Laptop Stand", LAPTOP));
            productsArrayList.add(new Product(R.drawable.asus_tuff_a16,"ASUS TUF Gaming A16 Laptop","$979.00","ASUS TUF Gaming A16 Laptop 16.0\" 165 Hz FHD+WVA (8-Core AMD Ryzen 7 7735HS, 16GB DDR5, 1TB PCIe SSD, AMD Radeon RX 7600S 8GB, Backlit KYB, WiFi 6, Win11 Home) with Dockztorm Hub", LAPTOP));
            productsArrayList.add(new Product(R.drawable.acer_nitro_17,"Acer Nitro 17 Gaming Laptop","$983.48","Acer Nitro 17 Gaming Laptop AMD Ryzen 7 7840HS Octa-Core CPU 17.3\" FHD 165Hz IPS Display NVIDIA GeForce RTX 4050 16GB DDR5 1TB SSD Wi-Fi 6E RGB Backlit KB AN17-41-R6L9", LAPTOP));
            productsArrayList.add(new Product(R.drawable.msi_sword_15,"MSI Newest Sword 15 Gaming Laptop"," $1,199.99","MSI Newest Sword 15 Gaming Laptop, 15.6\" FHD 144Hz IPS-Type Display, NVIDIA GeForce RTX 4060, Intel Core i7-12650H, 32GB DDR4, 1TB PCIe SSD, Wi-Fi 6, Windows 11 Home, Backlit Keyboard, White/OLY", LAPTOP));
            productsArrayList.add(new Product(R.drawable.msi_raider_laptop,"MSI Raider GE76 Laptop","$1,391.03","MSI Raider GE76 Gaming Laptop: Intel Core i9-12900H, GeForce RTX 3060, 17.3\" 144Hz FHD Display,16GB DDR5, 1TB NVMe SSD, Thunderbolt 4, Cooler Boost 5, Win 11 Home: Titanium Blue 12UE-871", LAPTOP));
            productsArrayList.add(new Product(R.drawable.gigabyte_laptop,"GIGABYTE - G6 (2024) Laptop","$999.00","GIGABYTE - G6 (2024) Gaming Laptop - 165Hz 1920x1200 WUXGA - NVIDIA GeForce RTX 4050 - Intel i7-13620H - 1TB SSD with 16GB DDR5 RAM - Win11 Home+ (G6 MF-H2US854KH)", LAPTOP));
            productsArrayList.add(new Product(R.drawable.razor_laptop,"Razer Blade 16 Laptop","$4,699.99","Razer Blade 16 (2024) Gaming Laptop: 16” Mini LED Dual Mode 4K UHD+ 120Hz / FHD+ 240Hz – NVIDIA GeForce RTX 4090 – Intel Core i9-14900HX - 64GB DDR5 RAM  - 4TB M.2 SSD – Chroma RGB – Black", LAPTOP));
            productsArrayList.add(new Product(R.drawable.hp_xictus_laptop,"HP Victus 15 Laptop ","$578.00","HP Victus 15 Gaming Laptop 15.6\" FHD IPS 144Hz AMD 7000 Ryzen 5 7535HS (Beats i7-11800H) GeForce RTX 2050 4GB Graphic Backlit USB-C B&O Win11 Black + HDMI Cable (8GB RAM | 512GB PCIe SSD)", LAPTOP));
            productsArrayList.add(new Product(R.drawable.msi_pulse,"MSI Pulse 17 Laptop","$1,899.00","MSI Pulse 17 Gaming Laptop: 13th Gen i9, 17” 240Hz QHD Display, NVIDIA GeForce RTX 4070, 32GB DDR5, 1TB NVMe SSD, Cooler Boost 5, Win11 Home: Black B13VGK-887US", LAPTOP));

        }else if (GAMES.equals(id)) {
            productsArrayList.add(new Product(R.drawable.mass_effect_legendary,"Mass Effect Legendary","$59.99","One person is all that stands between humanity and the greatest threat it's ever faced. Relive the Legend of Commander shepard in the highly acclaimed mass Effect Trilogy with the mass Effect legendary Edition. Includes single-player base content and DLC from mass Effect, mass Effect 2, and mass Effect 3, plus Promo weapons, armors, and packs - all remastered and optimized for 4K Ultra HD.\n", GAMES));
            productsArrayList.add(new Product(R.drawable.dead_space_game,"Dead Space Standard","$59.99","The sci-fi survival horror classic Dead Space returns, completely rebuilt from the ground up to offer a deeper and more immersive experience. This remake brings jaw-dropping visual fidelity, suspenseful atmospheric audio, and improvements to gameplay while staying faithful to the original game’s thrilling vision. Isaac ", GAMES));
            productsArrayList.add(new Product(R.drawable.star_wars_jedi_game,"Star Wars Jedi: Survivor Standard","$69.99","The story of Cal Kestis continues in Star Wars Jedi: Survivor, a third person galaxy-spanning action-adventure game from Respawn Entertainment, developed in collaboration with Lucasfilm Games. This narratively-driven, single player title picks up five years after the events of Star Wars Jedi: Fallen Order and follows Cal’s increasingly desperate fight as the galaxy descends further into darkness. ", GAMES));
            productsArrayList.add(new Product(R.drawable.transformers_game,"Transformers: Fall of Cybertron","$49.99","Transformers: Fall of Cybertron is a third-person shooter that returns players to the Transformer's planet of Cybertron for the final battles of the legendary war that preceded their arrival on Earth. The game continues the story of the earlier game, Transformers: War for Cybertron.", GAMES));
            productsArrayList.add(new Product(R.drawable.nfs_unbound_game,"Need for Speed Unbound Palace","$79.99","The world is your canvas in Need for Speed Unbound. Prove you have what it takes to win The Grand, Lakeshore’s ultimate street racing challenge. Across four intense weeks of racing, earn enough cash to enter weekly qualifiers, beat the competition, and make your mark on the street racing scene while outdriving and outsmarting the cops.", GAMES));
            productsArrayList.add(new Product(R.drawable.god_war_game,"God of War Standard","$44.99","Kratos is a father again. As mentor and protector to Atreus, a son determined to earn his respect, he is forced to deal with and control the rage that has long defined him while out in a very dangerous world with his son.", GAMES));
            productsArrayList.add(new Product(R.drawable.madden_nfl_gmae,"Madden NFL 24 ","$69.99","More realistic character movement and smarter AI gives you control to play out your gameplay strategy with the confidence to dominate any opponent in Madden NFL 24. *NEW GAMEPLAY MECHANICS ONLY ON PlayStation 5, Xbox Series X|S and PC. SAPIEN technology provides a leap forward in character technology introduces new anatomically accurate NFL player skeletons that are more responsive and true-to-life player motion.", GAMES));
            productsArrayList.add(new Product(R.drawable.mafia_game,"Mafia: Definitive Edition ","$39.99","Play a Mob Movie: Live the life of a Prohibition-era gangster and rise through the ranks of the Mafia.", GAMES));
            productsArrayList.add(new Product(R.drawable.the_outer_worlds_game,"The Outer Worlds: Standard ","$29.99","The Outer Worlds is an award-winning single-player first-person sci-fi RPG from Obsidian Entertainment and Private Division.", GAMES));
            productsArrayList.add(new Product(R.drawable.the_immortals_game,"Immortals of Aveum Standard","$59.99","Summon your power, stop the Everwar, save the realms. Immortals of Aveum is a single-player first person magic shooter that tells the story of Jak as he joins an elite order of battlemages to save a world on the edge of abyss. Master three forces of magic, unleash spells with deadly skill, and decimate legions of enemies in a game that defies conventions of what we’ve come to expect from first person shooters.", GAMES));
            productsArrayList.add(new Product(R.drawable.red_dead_redempdemtion_game,"Red Dead Redemption 2","$56.99","Red Dead Redemption 2, the critically acclaimed open world epic from Rockstar Games and the highest rated game of the console generation, now enhanced for PC with new Story Mode content, visual upgrades and more.", GAMES));
            productsArrayList.add(new Product(R.drawable.battlefield_3_game,"Battlefield 3: Premium Edition ","$39.99","Ramp up the intensity in Battlefield 3 and enjoy total freedom to fight the way you want. Explore nine massive multiplayer maps and use loads of vehicles, weapons, and gadgets to help you turn up the heat. Plus, every second of battle gets you closer to unlocking tons of extras and moving up in the Ranks. So get in the action. Key Features: Play to your strengths.", GAMES));

        }  else if (CONSOLES.equals(id)) {
            productsArrayList.add(new Product(R.drawable.nintendo_switch,"Nintendo Switch™","$296.01","Play at home or on the go with one system The Nintendo Switch™ system is designed to go wherever you do, instantly transforming from a home console you play on TV to a portable system you can play anywhere. So you get more time to play the games you love, however you like.", CONSOLES));
            productsArrayList.add(new Product(R.drawable.meta_quest,"Meta Quest 2","$199.00","Meta Quest 2 is the all-in-one system that truly sets you free to explore in VR. Simply put on the headset and enter fully-immersive, imagination-defying worlds. A built-in battery, fast processor and immersive graphics keep your experience smooth and seamless, while 3D positional audio, hand tracking and easy-to-use controllers make virtual worlds feel real.", CONSOLES));
            productsArrayList.add(new Product(R.drawable.xbox_series_s,"Microsoft Xbox Series S ","$299.00","2021 Microsoft Xbox Series S 512GB Game All-Digital Console, One Xbox Wireless Controller, 1440p Gaming Resolution, 4K Streaming, 3D Sound, WiFi, White", CONSOLES));
            productsArrayList.add(new Product(R.drawable.ps4_slim,"Sony PlayStation 4 Slim","$223.99","Edition:Slim 1TB The all new lighter and slimmer PlayStation4 system has a 1TB hard drive for all of the greatest games, TV, music and more. Incredible Games You've come to the right place.", CONSOLES));
            productsArrayList.add(new Product(R.drawable.steam_deck,"Valve Steam Deck","$526.99","Valve Steam Deck 512GB Handheld Gaming Console, 1280 x 800 LCD Display, with Carring case, Tempered Film and Soft Silicone Protective Case", CONSOLES));
            productsArrayList.add(new Product(R.drawable.asus_ally,"ASUS ROG Ally","$659.99","Any Game, Anywhere. Sink deep into your favourite AAA or indie games and watch the hours melt away with an expansive Full HD 120Hz display and incredibly comfortable ergonomics.", CONSOLES));
            productsArrayList.add(new Product(R.drawable.xbox_series_x," Xbox Series X ","$439.99","Next Gen Console Bundle - Xbox Series X 1TB + 8K Premium HDMI Cable - 4 feet- 48Gbps Hight Speed HDR for Gaming Console.", CONSOLES));
            productsArrayList.add(new Product(R.drawable.ps5,"PlayStation PS5 Console","$659.00","PlayStation 5 console, DualSense Wireless Controller, Base, HDMI Cable, AC power cord, USB cable, God of War Ragnarok full game voucher\n", CONSOLES));
            productsArrayList.add(new Product(R.drawable.ps4_pro,"Sony PlayStation 4 Pro","$244.00","Sony PlayStation 4 Pro w/ Accessories, 1TB HDD, CUH-7215B - Jet Black.Enhanced games - PS4 Pro games burst into life with intensely sharp graphics, stunningly vibrant colours, textures and environments and smoother, more stable performance", CONSOLES));
            productsArrayList.add(new Product(R.drawable.ps4,"PlayStation 4","$205.00","incredible games; Endless entertainment,1 TB hard drive,Blu-ray technology, delivers exceptional video quality.", CONSOLES));
            productsArrayList.add(new Product(R.drawable.xbox_one_s,"Xbox One S","$224.99","Microsoft - Xbox One S 500GB Console - White - ZQ9-00028 ","Gaming Consoles"));
            productsArrayList.add(new Product(R.drawable.xbox_1,"Xbox One ","$176.00","This item includes the Xbox One console, 1 wireless controller, HDMI cable, and power supply . For more troubleshooting steps please check the manufacturer's webiste", CONSOLES));

        } else if (APPLIANCES.equals(id)) {
            productsArrayList.add(new Product(R.drawable.samsung_32_qled_tv,"SAMSUNG 32-Inch QLED 4K Q60C ","$447.99","SAMSUNG 32-Inch Class QLED 4K Q60C Series Quantum HDR, Dual LED, Object Tracking Sound Lite, Q-Symphony, Motion Xcelerator, Gaming Hub, Smart TV with Alexa Built-in (QN32Q60C, 2023 Model),Titan Gray", APPLIANCES));
            productsArrayList.add(new Product(R.drawable.air_fryer," ClearCook Air Fryer","$89.95","Instant Vortex Plus 6QT ClearCook Air Fryer, Clear Windows, Custom Program Options, 6-in-1 Functions, Crisps, Broils, Roasts, Dehydrates, Bakes, Reheats, from the Makers of Instant Pot, Black", APPLIANCES));
            productsArrayList.add(new Product(R.drawable.smart_fan,"Dreo Smart Tower Fan"," $63.99","Dreo Smart Tower Fan for Bedroom, Standing Fans for Indoors, 90° Oscillating, Quiet 26ft/s Velocity Floor Fan with Remote, 5 Speeds, 8H Timer, Voice Control Bladeless Room Fan, Works with Alexa", APPLIANCES));
            productsArrayList.add(new Product(R.drawable.air_purifier,"LEVOIT Air Purifier","$189.99","LEVOIT Air Purifiers for Home Large Room Up to 1980 Ft² in 1 Hr With Air Quality Monitor, Smart WiFi and Auto Mode, 3-in-1 Filter Captures Pet Allergies, Smoke, Dust, Core 400S/Core 400S-P, White", APPLIANCES));
            productsArrayList.add(new Product(R.drawable.thermostat,"Google Nest Thermostat ","$199.98","Google Nest Learning Thermostat - Programmable Smart Thermostat for Home - 3rd Generation Nest Thermostat - Works with Alexa - Stainless Steel", APPLIANCES));
            productsArrayList.add(new Product(R.drawable.roomba, "iRobot Roomba 694 Robot Vacuum","$219.99","iRobot Roomba 694 Robot Vacuum-Wi-Fi Connectivity, Personalized Cleaning Recommendations, Works with Alexa, Good for Pet Hair, Carpets, Hard Floors, Self-Charging, Roomba 694", APPLIANCES));
            productsArrayList.add(new Product(R.drawable.amazon_echo,"Amazon Echo Dot","$59.99","Amazon Echo Dot (5th Gen) with clock | Compact smart speaker with Alexa and enhanced LED display for at-a-glance clock, timers, weather, and more | Cloud Blue", APPLIANCES));
            productsArrayList.add(new Product(R.drawable.soundbar,"Bose TV Speaker"," $229.00","Bose TV Speaker - Soundbar for TV with Bluetooth and HDMI-ARC Connectivity, Black, Includes Remote Control", APPLIANCES));
            productsArrayList.add(new Product(R.drawable.vaccume_cleaner,"Dyson Ball Animal 3 Vacuum"," $299.00","Ball technology. Navigate around obstacles with a simple turn of the wrist. For easy, precise maneuvering around your home.", APPLIANCES));
            productsArrayList.add(new Product(R.drawable.blender,"Ninja BL770 Mega Kitchen System"," $159.95","Ninja BL770 Mega Kitchen System, 1500W, 4 Functions for Smoothies, Processing, Dough, Drinks & More, with 72-oz.* Blender Pitcher, 64-oz. Processor Bowl, (2) 16-oz. To-Go Cups & (2) Lids, Black", APPLIANCES));
            productsArrayList.add(new Product(R.drawable.kettle,"COSORI Electric Gooseneck Kettle ","$69.99","COSORI Electric Gooseneck Kettle with 5 Temperature Control Presets, Pour Over Kettle for Coffee & Tea, Hot Water Boiler, 100% Stainless Steel Inner Lid & Bottom, 1200W/0.8L", APPLIANCES));
            productsArrayList.add(new Product(R.drawable.lamp,"LED Floor Lamp","$115.99","LED Floor Lamp, Height Adjustable Floor Lamps for Living Room, Super Bright Standing Lamp with Timer, Adjustable Colors & Brightness Floor lamp for Bedroom with Remote & Touch Control, Black", APPLIANCES));
        }else if (POPULAR_PRODUCTS.equals(id)) {
            productsArrayList.add(new Product(R.drawable.leveni_legion_slim_7i,"Lenovo Legion Slim 7i ","$1,499.99","Lenovo Legion Slim 7i Gaming & Entertainment Laptop (Intel i9-13900H 14-Core, 16GB DDR5 5200MHz RAM, 1TB SSD, GeForce RTX 4070, 16.0\" Win 11 Home) with Microsoft 365 Personal, Dockztorm Hub", POPULAR_PRODUCTS));
            productsArrayList.add(new Product(R.drawable.asus_rog_strix_16,"ASUS ROG Strix Scar 16","$2,899.99","ASUS ROG Strix Scar 16 (2024) Gaming Laptop, 16” Nebula HDR 16:10 QHD 240Hz/3ms, 1100 nits, Mini LED Display, GeForce RTX 4080, Intel Core i9-14900HX, 32GB DDR5, 1TB SSD, Windows 11 Pro, G634JZR-XS96", POPULAR_PRODUCTS));
            productsArrayList.add(new Product(R.drawable.asus_tuf_a17,"SUS TUF A17 Gaming Laptop","$1,859.00","ASUS TUF A17 Gaming Laptop - 17.3\" FHD Display, AMD Ryzen 9-7940HS (8-core), NVIDIA GeForce RTX 4070, 32GB DDR5, 1TB SSD, Backlit Keyboard, Wi-Fi 6, Windows 11 Home, with Laptop Stand", POPULAR_PRODUCTS));
            productsArrayList.add(new Product(R.drawable.asus_tuff_a16,"ASUS TUF Gaming A16 Laptop","$979.00","ASUS TUF Gaming A16 Laptop 16.0\" 165 Hz FHD+WVA (8-Core AMD Ryzen 7 7735HS, 16GB DDR5, 1TB PCIe SSD, AMD Radeon RX 7600S 8GB, Backlit KYB, WiFi 6, Win11 Home) with Dockztorm Hub", POPULAR_PRODUCTS));
            productsArrayList.add(new Product(R.drawable.samsung_32_qled_tv,"SAMSUNG 32-Inch QLED 4K Q60C ","$447.99","SAMSUNG 32-Inch Class QLED 4K Q60C Series Quantum HDR, Dual LED, Object Tracking Sound Lite, Q-Symphony, Motion Xcelerator, Gaming Hub, Smart TV with Alexa Built-in (QN32Q60C, 2023 Model),Titan Gray", POPULAR_PRODUCTS));
            productsArrayList.add(new Product(R.drawable.air_fryer," ClearCook Air Fryer","$89.95","Instant Vortex Plus 6QT ClearCook Air Fryer, Clear Windows, Custom Program Options, 6-in-1 Functions, Crisps, Broils, Roasts, Dehydrates, Bakes, Reheats, from the Makers of Instant Pot, Black", POPULAR_PRODUCTS));
            productsArrayList.add(new Product(R.drawable.meta_quest,"Meta Quest 2","$199.00","Meta Quest 2 is the all-in-one system that truly sets you free to explore in VR. Simply put on the headset and enter fully-immersive, imagination-defying worlds. A built-in battery, fast processor and immersive graphics keep your experience smooth and seamless, while 3D positional audio, hand tracking and easy-to-use controllers make virtual worlds feel real.", POPULAR_PRODUCTS));
            productsArrayList.add(new Product(R.drawable.xbox_series_s,"Microsoft Xbox Series S ","$299.00","2021 Microsoft Xbox Series S 512GB Game All-Digital Console, One Xbox Wireless Controller, 1440p Gaming Resolution, 4K Streaming, 3D Sound, WiFi, White", POPULAR_PRODUCTS));
            productsArrayList.add(new Product(R.drawable.ps4_slim,"Sony PlayStation 4 Slim","$223.99","Edition:Slim 1TB The all new lighter and slimmer PlayStation4 system has a 1TB hard drive for all of the greatest games, TV, music and more. Incredible Games You've come to the right place.", POPULAR_PRODUCTS));
            productsArrayList.add(new Product(R.drawable.steam_deck,"Valve Steam Deck","$526.99","Valve Steam Deck 512GB Handheld Gaming Console, 1280 x 800 LCD Display, with Carring case, Tempered Film and Soft Silicone Protective Case", POPULAR_PRODUCTS));
            productsArrayList.add(new Product(R.drawable.asus_ally,"ASUS ROG Ally","$659.99","Any Game, Anywhere. Sink deep into your favourite AAA or indie games and watch the hours melt away with an expansive Full HD 120Hz display and incredibly comfortable ergonomics.", POPULAR_PRODUCTS));
            productsArrayList.add(new Product(R.drawable.smart_fan,"Dreo Smart Tower Fan"," $63.99","Dreo Smart Tower Fan for Bedroom, Standing Fans for Indoors, 90° Oscillating, Quiet 26ft/s Velocity Floor Fan with Remote, 5 Speeds, 8H Timer, Voice Control Bladeless Room Fan, Works with Alexa", POPULAR_PRODUCTS));
        }else if (EVERY_PRODUCT.equals(id)) {

        }
        return productsArrayList;

    }

    @Override
    public void categoryClicked(int position) {
        removeViews();
        categoryRecycleView.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.VISIBLE);
        Category category = getCategory().get(position);
        ProductRecyclerViewFragment itemProductRecyclerViewFragment =new ProductRecyclerViewFragment();
        categoryId= category.getCategoryId();
        loadFragment(itemProductRecyclerViewFragment);
    }


    @Override
    public void productClicked(int position) {
        Product product = getProductsData(categoryId).get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putString("productName", product.getProductName());
        bundle.putString("proPrice", product.getProductPrice());
        bundle.putString("productDescription", product.getProductDescription());
        bundle.putString("position", product.getProductId());
        bundle.putInt("productImage", product.getProductImage());
        ProductViewFragment productViewFragment = new ProductViewFragment();
        productViewFragment.setArguments(bundle);
        loadFragment(productViewFragment);
    }
}