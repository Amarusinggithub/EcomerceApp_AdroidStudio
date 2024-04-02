package com.example.myecomerceapp.activities;






import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.myecomerceapp.fragments.ProductRecyclerViewFragment;
import com.example.myecomerceapp.interfaces.MyOnClickInterface;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.BannerAdapter;
import com.example.myecomerceapp.fragments.AccountFragment;
import com.example.myecomerceapp.fragments.CartFragment;
import com.example.myecomerceapp.fragments.SalesFragment;
import com.example.myecomerceapp.models.CategoryModel;
import com.example.myecomerceapp.models.ProductModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener,DrawerLayout.DrawerListener , MyOnClickInterface {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
   public static RecyclerView specialsBanner;
    public static FrameLayout frameLayout;
   public static TextView specialsTv;

    public static CardView displayBanner;

    public static RecyclerView categoryRecycleView;

    public static  TextView popularProductstv;

    public static RecyclerView popularProductsRecycleview;

    public FirebaseUser user;


    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variables
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView=findViewById(R.id.bottomnav);
        specialsTv=findViewById(R.id.specalstv);
        frameLayout=findViewById(R.id.frameLayout);
        displayBanner=findViewById(R.id.displayBanner);

       specialsBanner = findViewById(R.id.specialsbanner);
       categoryRecycleView = findViewById(R.id.categoriesRecycleView);
       popularProductsRecycleview=findViewById(R.id.popularproductrecycleview);
       popularProductstv=findViewById(R.id.popularproducttv);


        //specials banner recycleview
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
       specialsBanner.setLayoutManager(linearLayoutManager);
        BannerAdapter bannerAdapter = new BannerAdapter(getProductsData("specials"),this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
       specialsBanner.setAdapter(bannerAdapter);

      //category recycleView
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecycleView.setLayoutManager(linearLayoutManager1);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getCategory());
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
                categoryRecycleView.setVisibility(View.GONE);
                removeViews();

            }
            return true;
        });

        setSupportActionBar(toolbar);
        setupDrawer();

    }

    public static List<CategoryModel> getCategory() {
        List<CategoryModel> categoryModelArrayList = new ArrayList<>();
        categoryModelArrayList.add(new CategoryModel("Phones", R.drawable.smartphones_category_icon,"Phones"));
        categoryModelArrayList.add(new CategoryModel("Laptop", R.drawable.laptop_category_icon,"Laptop"));
        categoryModelArrayList.add(new CategoryModel("Games", R.drawable.games_category_icon,"Games"));
        categoryModelArrayList.add(new CategoryModel("Consoles",R.drawable.gamingconsoles_category_icon ,"Consoles"));
        categoryModelArrayList.add(new CategoryModel("Appliances",R.drawable.homeappliance_category_icon,"Appliances"));
        return categoryModelArrayList;
    }

    public static List<ProductModel> getProductsData(String Id) {
        List<ProductModel>productsArrayList = new ArrayList<>();
        if ("Phones".equals(Id)) {
            productsArrayList.add(new ProductModel(R.drawable.s24plus,"SAMSUNG Galaxy S24+ Plus ","$969.99 ","SAMSUNG Galaxy S24+ Plus Cell Phone, 512GB AI Smartphone, Unlocked Android, 50MP Camera, Fastest Processor, Long Battery Life, US Version, 2024, Onyx Black","Phones"));
            productsArrayList.add(new ProductModel(R.drawable.oneplus_12,"OnePlus 12","$899.99","OnePlus 12,16GB RAM+512GB,Dual-SIM,Unlocked Android Smartphone,Supports 50W Wireless Charging,Latest Mobile Processor,Advanced Hasselblad Camera,5400 mAh Battery,2024,Flowy Emerald","Phones"));
            productsArrayList.add(new ProductModel(R.drawable.pixel_8,"Google Pixel 8 ","$499.00 ","Smartphone with Advanced Pixel Camera, 24-Hour Battery, and Powerful Security - Obsidian - 128 GB","Phones"));
            productsArrayList.add(new ProductModel(R.drawable.a54_samsung,"SAMSUNG Galaxy A54 5G ","$449.99","SAMSUNG Galaxy A54 5G A Series Cell Phone, Unlocked Android Smartphone, 128GB, 6.4” Fluid Display Screen, Pro Grade Camera, Long Battery Life, Refined Design, US Version, 2023, Awesome Black","Phones"));
            productsArrayList.add(new ProductModel(R.drawable.redmi_note13_pro,"Redmi Note 13 PRO 5G ","$310.96","Xiaomi Redmi Note 13 PRO 5G + 4G LTE (256GB + 8GB) 6.67, 200MP Triple (Tmobile Mint Tello & Global) Global Bands Unlocked + (Fast Car Dual Charger Bundle) (Midnight Black (Global ROM))","Phones"));
            productsArrayList.add(new ProductModel(R.drawable.asus_rog_phone_8_pro,"ASUS ROG Phone 8 Pro","$1,199.99","ASUS ROG Phone 8 Pro Unlocked Android Phone, US Version, 6.78 165Hz AMOLED Display, 512GB, 16GB RAM, 5500mAh Battery, 50MP Gimbal Camera, 32MP Front, Snapdragon 8 Gen 3, Dual-SIM, Phantom Black","Phones"));
            productsArrayList.add(new ProductModel(R.drawable.pixel_8_pro,"Google Pixel 8 Pro","$749.00 ","Google Pixel 8 Pro - Unlocked Android Smartphone with Telephoto Lens and Super Actua Display - 24-Hour Battery - Porcelain - 128 GB","Phones"));
            productsArrayList.add(new ProductModel(R.drawable.iphone_13,"Apple iPhone 13","$388.10","The iPhone 13 features a 6.1-inch (155 mm) display with Super Retina XDR OLED technology at a resolution of 2532×1170 pixels and a pixel density of about 460 PPI with a refresh rate of 60 Hz and Dolby Vision HDR.","Phones"));
            productsArrayList.add(new ProductModel(R.drawable.iphone_13_pro,"Apple iPhone 13 Pro","$494.75","iPhone 13 Pro, 256GB, Sierra Blue - Unlocked (Renewed Premium), comes with charger, Mfi cable and SIM Pin ejector","Phones"));
            productsArrayList.add(new ProductModel(R.drawable.s10_plus,"SAMSUNG Galaxy S10+ Plus","$265.72","SAMSUNG Galaxy S10+ Plus (128GB, 8GB) 6.4\" AMOLED, Snapdragon 855, IP68 Water Resistant, Global 4G LTE (GSM + CDMA) Unlocked (AT&T, Verizon, T-Mobile, Metro) G975U (Prism Blue)","Phones"));
            productsArrayList.add(new ProductModel(R.drawable.iphone_15_pro_max,"Apple iPhone 15 Pro Max","$1,529.97","iPhone 15 Pro Max has a 6.7-inch all-screen Super Retina XDR display with the Dynamic Island. The back is textured matte glass, and there is a contoured-edge titanium band around the frame.","Phones"));
            productsArrayList.add(new ProductModel(R.drawable.iphone_14_pro_max,"Apple iPhone 14 Pro Max","$799.90","6.7-inch Super Retina XDR display featuring Always-On & ProMotion.","Phones"));
        } else if ("Laptop".equals(Id)) {
            productsArrayList.add(new ProductModel(R.drawable.acer_aspire_3,"Acer Aspire 3","$299.99","Acer Aspire 3 A315-24P-R7VH Slim Laptop | 15.6\" Full HD IPS Display | AMD Ryzen 3 7320U Quad-Core Processor | AMD Radeon Graphics | 8GB LPDDR5 | 128GB NVMe SSD | Wi-Fi 6 | Windows 11 Home in S Mode","Laptop"));
            productsArrayList.add(new ProductModel(R.drawable.leveni_legion_slim_7i,"Lenovo Legion Slim 7i ","$1,499.99","Lenovo Legion Slim 7i Gaming & Entertainment Laptop (Intel i9-13900H 14-Core, 16GB DDR5 5200MHz RAM, 1TB SSD, GeForce RTX 4070, 16.0\" Win 11 Home) with Microsoft 365 Personal, Dockztorm Hub","Laptop"));
            productsArrayList.add(new ProductModel(R.drawable.asus_rog_strix_16,"ASUS ROG Strix Scar 16","$2,899.99","ASUS ROG Strix Scar 16 (2024) Gaming Laptop, 16” Nebula HDR 16:10 QHD 240Hz/3ms, 1100 nits, Mini LED Display, GeForce RTX 4080, Intel Core i9-14900HX, 32GB DDR5, 1TB SSD, Windows 11 Pro, G634JZR-XS96","Laptop"));
            productsArrayList.add(new ProductModel(R.drawable.asus_tuf_a17,"SUS TUF A17 Gaming Laptop","$1,859.00","ASUS TUF A17 Gaming Laptop - 17.3\" FHD Display, AMD Ryzen 9-7940HS (8-core), NVIDIA GeForce RTX 4070, 32GB DDR5, 1TB SSD, Backlit Keyboard, Wi-Fi 6, Windows 11 Home, with Laptop Stand","Laptop"));
            productsArrayList.add(new ProductModel(R.drawable.asus_tuff_a16,"ASUS TUF Gaming A16 Laptop","$979.00","ASUS TUF Gaming A16 Laptop 16.0\" 165 Hz FHD+WVA (8-Core AMD Ryzen 7 7735HS, 16GB DDR5, 1TB PCIe SSD, AMD Radeon RX 7600S 8GB, Backlit KYB, WiFi 6, Win11 Home) with Dockztorm Hub","Laptop"));
            productsArrayList.add(new ProductModel(R.drawable.acer_nitro_17,"Acer Nitro 17 Gaming Laptop","$983.48","Acer Nitro 17 Gaming Laptop AMD Ryzen 7 7840HS Octa-Core CPU 17.3\" FHD 165Hz IPS Display NVIDIA GeForce RTX 4050 16GB DDR5 1TB SSD Wi-Fi 6E RGB Backlit KB AN17-41-R6L9","Laptop"));
            productsArrayList.add(new ProductModel(R.drawable.msi_sword_15,"MSI Newest Sword 15 Gaming Laptop"," $1,199.99","MSI Newest Sword 15 Gaming Laptop, 15.6\" FHD 144Hz IPS-Type Display, NVIDIA GeForce RTX 4060, Intel Core i7-12650H, 32GB DDR4, 1TB PCIe SSD, Wi-Fi 6, Windows 11 Home, Backlit Keyboard, White/OLY","Laptop"));
            productsArrayList.add(new ProductModel(R.drawable.msi_raider_laptop,"MSI Raider GE76 Gaming Laptop","$1,391.03","MSI Raider GE76 Gaming Laptop: Intel Core i9-12900H, GeForce RTX 3060, 17.3\" 144Hz FHD Display,16GB DDR5, 1TB NVMe SSD, Thunderbolt 4, Cooler Boost 5, Win 11 Home: Titanium Blue 12UE-871","Laptop"));
            productsArrayList.add(new ProductModel(R.drawable.gigabyte_laptop,"GIGABYTE - G6 (2024) Gaming Laptop","$999.00","GIGABYTE - G6 (2024) Gaming Laptop - 165Hz 1920x1200 WUXGA - NVIDIA GeForce RTX 4050 - Intel i7-13620H - 1TB SSD with 16GB DDR5 RAM - Win11 Home+ (G6 MF-H2US854KH)","Laptop"));
            productsArrayList.add(new ProductModel(R.drawable.razor_laptop,"Razer Blade 16 (2024) Gaming Laptop","$4,699.99","Razer Blade 16 (2024) Gaming Laptop: 16” Mini LED Dual Mode 4K UHD+ 120Hz / FHD+ 240Hz – NVIDIA GeForce RTX 4090 – Intel Core i9-14900HX - 64GB DDR5 RAM  - 4TB M.2 SSD – Chroma RGB – Black","Laptop"));
            productsArrayList.add(new ProductModel(R.drawable.hp_xictus_laptop,"HP Victus 15 Gaming Laptop ","$578.00","HP Victus 15 Gaming Laptop 15.6\" FHD IPS 144Hz AMD 7000 Ryzen 5 7535HS (Beats i7-11800H) GeForce RTX 2050 4GB Graphic Backlit USB-C B&O Win11 Black + HDMI Cable (8GB RAM | 512GB PCIe SSD)","Laptop"));
            productsArrayList.add(new ProductModel(R.drawable.msi_pulse,"MSI Pulse 17 Gaming Laptop","$1,899.00","MSI Pulse 17 Gaming Laptop: 13th Gen i9, 17” 240Hz QHD Display, NVIDIA GeForce RTX 4070, 32GB DDR5, 1TB NVMe SSD, Cooler Boost 5, Win11 Home: Black B13VGK-887US","Laptop"));

        }else if ("Games".equals(Id)) {
            productsArrayList.add(new ProductModel(R.drawable.mass_effect_legendary,"Mass Effect Legendary","$59.99","One person is all that stands between humanity and the greatest threat it's ever faced. Relive the Legend of Commander shepard in the highly acclaimed mass Effect Trilogy with the mass Effect legendary Edition. Includes single-player base content and DLC from mass Effect, mass Effect 2, and mass Effect 3, plus Promo weapons, armors, and packs - all remastered and optimized for 4K Ultra HD.\n","Games"));
            productsArrayList.add(new ProductModel(R.drawable.dead_space_game,"Dead Space Standard","$59.99","The sci-fi survival horror classic Dead Space returns, completely rebuilt from the ground up to offer a deeper and more immersive experience. This remake brings jaw-dropping visual fidelity, suspenseful atmospheric audio, and improvements to gameplay while staying faithful to the original game’s thrilling vision. Isaac ","Games"));
            productsArrayList.add(new ProductModel(R.drawable.star_wars_jedi_game,"Star Wars Jedi: Survivor Standard","$69.99","The story of Cal Kestis continues in Star Wars Jedi: Survivor, a third person galaxy-spanning action-adventure game from Respawn Entertainment, developed in collaboration with Lucasfilm Games. This narratively-driven, single player title picks up five years after the events of Star Wars Jedi: Fallen Order and follows Cal’s increasingly desperate fight as the galaxy descends further into darkness. ","Games"));
            productsArrayList.add(new ProductModel(R.drawable.transformers_game,"Transformers: Fall of Cybertron","$49.99","Transformers: Fall of Cybertron is a third-person shooter that returns players to the Transformer's planet of Cybertron for the final battles of the legendary war that preceded their arrival on Earth. The game continues the story of the earlier game, Transformers: War for Cybertron.","Games"));
            productsArrayList.add(new ProductModel(R.drawable.nfs_unbound_game,"Need for Speed Unbound Palace Edition Bundle","$79.99","The world is your canvas in Need for Speed Unbound. Prove you have what it takes to win The Grand, Lakeshore’s ultimate street racing challenge. Across four intense weeks of racing, earn enough cash to enter weekly qualifiers, beat the competition, and make your mark on the street racing scene while outdriving and outsmarting the cops.","Games"));
            productsArrayList.add(new ProductModel(R.drawable.god_war_game,"God of War Standard","$44.99","Kratos is a father again. As mentor and protector to Atreus, a son determined to earn his respect, he is forced to deal with and control the rage that has long defined him while out in a very dangerous world with his son.","Games"));
            productsArrayList.add(new ProductModel(R.drawable.madden_nfl_gmae,"Madden NFL 24 Standard","$69.99","More realistic character movement and smarter AI gives you control to play out your gameplay strategy with the confidence to dominate any opponent in Madden NFL 24. *NEW GAMEPLAY MECHANICS ONLY ON PlayStation 5, Xbox Series X|S and PC. SAPIEN technology provides a leap forward in character technology introduces new anatomically accurate NFL player skeletons that are more responsive and true-to-life player motion.","Games"));
            productsArrayList.add(new ProductModel(R.drawable.mafia_game,"Mafia: Definitive Edition ","$39.99","Play a Mob Movie: Live the life of a Prohibition-era gangster and rise through the ranks of the Mafia.","Games"));
            productsArrayList.add(new ProductModel(R.drawable.the_outer_worlds_game,"The Outer Worlds: Standard ","$29.99","The Outer Worlds is an award-winning single-player first-person sci-fi RPG from Obsidian Entertainment and Private Division.","Games"));
            productsArrayList.add(new ProductModel(R.drawable.the_immortals_game,"Immortals of Aveum Standard","$59.99","Summon your power, stop the Everwar, save the realms. Immortals of Aveum is a single-player first person magic shooter that tells the story of Jak as he joins an elite order of battlemages to save a world on the edge of abyss. Master three forces of magic, unleash spells with deadly skill, and decimate legions of enemies in a game that defies conventions of what we’ve come to expect from first person shooters.","Games"));
            productsArrayList.add(new ProductModel(R.drawable.red_dead_redempdemtion_game,"Red Dead Redemption 2","$56.99","Red Dead Redemption 2, the critically acclaimed open world epic from Rockstar Games and the highest rated game of the console generation, now enhanced for PC with new Story Mode content, visual upgrades and more.","Games"));
            productsArrayList.add(new ProductModel(R.drawable.battlefield_3_game,"Battlefield 3: Premium Edition ","$39.99","Ramp up the intensity in Battlefield 3 and enjoy total freedom to fight the way you want. Explore nine massive multiplayer maps and use loads of vehicles, weapons, and gadgets to help you turn up the heat. Plus, every second of battle gets you closer to unlocking tons of extras and moving up in the Ranks. So get in the action. Key Features: Play to your strengths.","Games"));

        }  else if ("Consoles".equals(Id)) {
            productsArrayList.add(new ProductModel(R.drawable.nintendo_switch,"Nintendo Switch™","$296.01","Play at home or on the go with one system The Nintendo Switch™ system is designed to go wherever you do, instantly transforming from a home console you play on TV to a portable system you can play anywhere. So you get more time to play the games you love, however you like.","Consoles"));
            productsArrayList.add(new ProductModel(R.drawable.meta_quest,"Meta Quest 2","$199.00","Meta Quest 2 is the all-in-one system that truly sets you free to explore in VR. Simply put on the headset and enter fully-immersive, imagination-defying worlds. A built-in battery, fast processor and immersive graphics keep your experience smooth and seamless, while 3D positional audio, hand tracking and easy-to-use controllers make virtual worlds feel real.","Consoles"));
            productsArrayList.add(new ProductModel(R.drawable.xbox_series_s,"Microsoft Xbox Series S ","$299.00","2021 Microsoft Xbox Series S 512GB Game All-Digital Console, One Xbox Wireless Controller, 1440p Gaming Resolution, 4K Streaming, 3D Sound, WiFi, White","Consoles"));
            productsArrayList.add(new ProductModel(R.drawable.ps4_slim,"Sony PlayStation 4 Slim","$223.99","Edition:Slim 1TB The all new lighter and slimmer PlayStation4 system has a 1TB hard drive for all of the greatest games, TV, music and more. Incredible Games You've come to the right place.","Consoles"));
            productsArrayList.add(new ProductModel(R.drawable.steam_deck,"Valve Steam Deck ","$526.99","Valve Steam Deck 512GB Handheld Gaming Console, 1280 x 800 LCD Display, with Carring case, Tempered Film and Soft Silicone Protective Case","Consoles"));
            productsArrayList.add(new ProductModel(R.drawable.asus_ally,"ASUS ROG Ally","$659.99","Any Game, Anywhere. Sink deep into your favourite AAA or indie games and watch the hours melt away with an expansive Full HD 120Hz display and incredibly comfortable ergonomics.","Consoles"));
            productsArrayList.add(new ProductModel(R.drawable.xbox_series_x," Xbox Series X ","$439.99","Next Gen Console Bundle - Xbox Series X 1TB + 8K Premium HDMI Cable - 4 feet- 48Gbps Hight Speed HDR for Gaming Console.","Consoles"));
            productsArrayList.add(new ProductModel(R.drawable.ps5,"PlayStation PS5 Console","$659.00","PlayStation 5 console, DualSense Wireless Controller, Base, HDMI Cable, AC power cord, USB cable, God of War Ragnarok full game voucher\n","Consoles"));
            productsArrayList.add(new ProductModel(R.drawable.ps4_pro,"Sony PlayStation 4 Pro","$244.00","Sony PlayStation 4 Pro w/ Accessories, 1TB HDD, CUH-7215B - Jet Black.Enhanced games - PS4 Pro games burst into life with intensely sharp graphics, stunningly vibrant colours, textures and environments and smoother, more stable performance","Consoles"));
            productsArrayList.add(new ProductModel(R.drawable.ps4,"PlayStation 4","$205.00","ncredible games; Endless entertainment,1 TB hard drive,Blu-ray technology, delivers exceptional video quality.","Consoles"));
            productsArrayList.add(new ProductModel(R.drawable.xbox_one_s,"Xbox One S","$224.99","Microsoft - Xbox One S 500GB Console - White - ZQ9-00028 ","Gaming Consoles"));
            productsArrayList.add(new ProductModel(R.drawable.xbox_1,"Xbox One ","$176.00","This item includes the Xbox One console, 1 wireless controller, HDMI cable, and power supply . For more troubleshooting steps please check the manufacturer's webiste","Consoles"));

        } else if ("Appliances".equals(Id)) {
            productsArrayList.add(new ProductModel(R.drawable.samsung_32_qled_tv,"SAMSUNG 32-Inch Class QLED 4K Q60C Series Quantum","$447.99","SAMSUNG 32-Inch Class QLED 4K Q60C Series Quantum HDR, Dual LED, Object Tracking Sound Lite, Q-Symphony, Motion Xcelerator, Gaming Hub, Smart TV with Alexa Built-in (QN32Q60C, 2023 Model),Titan Gray","Appliances"));
            productsArrayList.add(new ProductModel(R.drawable.air_fryer,"Instant Vortex Plus 6QT ClearCook Air Fryer","$89.95","Instant Vortex Plus 6QT ClearCook Air Fryer, Clear Windows, Custom Program Options, 6-in-1 Functions, Crisps, Broils, Roasts, Dehydrates, Bakes, Reheats, from the Makers of Instant Pot, Black","Appliances"));
            productsArrayList.add(new ProductModel(R.drawable.smart_fan,"Dreo Smart Tower Fan"," $63.99","Dreo Smart Tower Fan for Bedroom, Standing Fans for Indoors, 90° Oscillating, Quiet 26ft/s Velocity Floor Fan with Remote, 5 Speeds, 8H Timer, Voice Control Bladeless Room Fan, Works with Alexa","Appliances"));
            productsArrayList.add(new ProductModel(R.drawable.air_purifier,"LEVOIT Air Purifier","$189.99","LEVOIT Air Purifiers for Home Large Room Up to 1980 Ft² in 1 Hr With Air Quality Monitor, Smart WiFi and Auto Mode, 3-in-1 Filter Captures Pet Allergies, Smoke, Dust, Core 400S/Core 400S-P, White","Appliances"));
            productsArrayList.add(new ProductModel(R.drawable.thermostat,"Google Nest Learning Thermostat ","$199.98","Google Nest Learning Thermostat - Programmable Smart Thermostat for Home - 3rd Generation Nest Thermostat - Works with Alexa - Stainless Steel","Appliances"));
            productsArrayList.add(new ProductModel(R.drawable.roomba, "iRobot Roomba 694 Robot Vacuum","$219.99","iRobot Roomba 694 Robot Vacuum-Wi-Fi Connectivity, Personalized Cleaning Recommendations, Works with Alexa, Good for Pet Hair, Carpets, Hard Floors, Self-Charging, Roomba 694","Appliances"));
            productsArrayList.add(new ProductModel(R.drawable.amazon_echo,"Amazon Echo Dot (5th Gen) ","$59.99","Amazon Echo Dot (5th Gen) with clock | Compact smart speaker with Alexa and enhanced LED display for at-a-glance clock, timers, weather, and more | Cloud Blue","Appliances"));
            productsArrayList.add(new ProductModel(R.drawable.soundbar,"Bose TV Speaker"," $229.00","Bose TV Speaker - Soundbar for TV with Bluetooth and HDMI-ARC Connectivity, Black, Includes Remote Control","Appliances"));
            productsArrayList.add(new ProductModel(R.drawable.vaccume_cleaner,"Dyson Ball Animal 3 Upright Vacuum Cleaner"," $299.00","Ball technology. Navigate around obstacles with a simple turn of the wrist. For easy, precise maneuvering around your home.","Appliances"));
            productsArrayList.add(new ProductModel(R.drawable.blender,"Ninja BL770 Mega Kitchen System"," $159.95","Ninja BL770 Mega Kitchen System, 1500W, 4 Functions for Smoothies, Processing, Dough, Drinks & More, with 72-oz.* Blender Pitcher, 64-oz. Processor Bowl, (2) 16-oz. To-Go Cups & (2) Lids, Black","Appliances"));
            productsArrayList.add(new ProductModel(R.drawable.kettle,"COSORI Electric Gooseneck Kettle ","$69.99","COSORI Electric Gooseneck Kettle with 5 Temperature Control Presets, Pour Over Kettle for Coffee & Tea, Hot Water Boiler, 100% Stainless Steel Inner Lid & Bottom, 1200W/0.8L","Appliances"));
            productsArrayList.add(new ProductModel(R.drawable.lamp,"LED Floor Lamp","$115.99","LED Floor Lamp, Height Adjustable Floor Lamps for Living Room, Super Bright Standing Lamp with Timer, Adjustable Colors & Brightness Floor lamp for Bedroom with Remote & Touch Control, Black","Appliances"));
        }else if ("specials".equals(Id)) {

        }else if ("everything".equals(Id)) {

        }
        return productsArrayList;

    }

    public static  void removeViews(){
        categoryRecycleView.setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
        specialsTv.setVisibility(View.GONE);
       specialsBanner.setVisibility(View.GONE);
       displayBanner.setVisibility(View.GONE);
       popularProductstv.setVisibility(View.GONE);
       popularProductsRecycleview.setVisibility(View.GONE);
    }

    public static  void addViews(){

        categoryRecycleView.setVisibility(View.VISIBLE);
        specialsTv.setVisibility(View.VISIBLE);
       specialsBanner.setVisibility(View.VISIBLE);
       displayBanner.setVisibility(View.VISIBLE);
       popularProductsRecycleview.setVisibility(View.VISIBLE);
       popularProductstv.setVisibility(View.VISIBLE);

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
    public void onClicked(int position) {
        removeViews();
        categoryRecycleView.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.VISIBLE);
        CategoryModel categoryModel= getCategory().get(position);
        ProductRecyclerViewFragment itemProductRecyclerViewFragment =new ProductRecyclerViewFragment();
        ProductRecyclerViewFragment.categoryId=categoryModel.getCategoryId();
        loadFragment(itemProductRecyclerViewFragment);
    }


    @Override
    protected void onStart() {
        super.onStart();
       /* user = mAuth.getCurrentUser();*/
    }
}