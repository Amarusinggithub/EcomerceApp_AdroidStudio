package com.example.myecomerceapp.activities;






import androidx.appcompat.app.AppCompatActivity;


import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;


import android.widget.FrameLayout;



import com.example.myecomerceapp.fragments.HomeFragment;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.fragments.AccountFragment;
import com.example.myecomerceapp.fragments.CartFragment;
import com.example.myecomerceapp.fragments.OrdersFragment;
import com.example.myecomerceapp.models.Category;
import com.example.myecomerceapp.models.Product;
import com.example.myecomerceapp.models.User;

import com.google.android.material.bottomnavigation.BottomNavigationView;



import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;



import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity  {
    public static final String STRIPE_SECRET_KEY="sk_test_51PEvksP13euDVuhqWG2M6aI09MAAI9tKJwiJxjuCF4YRX6nRsbkEPRtD4cRM8LujTGKfApzYvTJsezSeW4Ckl8Vx00khuc6HHr";
    public static final String STRIPE_PUBLISH_KEY="pk_test_51PEvksP13euDVuhqdujVacuTKlz9tSOoBW2CwTlUhgkn6xgUwDgoYFgMR7eYFFGQUFGWVlJz4yRlndv65mBtLB1p00ICZgRXGU";
    public static final String LAPTOP = "Laptop";
    public static final String PHONES = "Phones";
    public static final String GAMES = "Games";
    public static final String CONSOLES = "Consoles";
    public static final String APPLIANCES = "Appliances";
    public static final String EVERY_PRODUCT = "every-Product";
    private static final String TAG = "MainActivity";
    public static ArrayList<Product> productsAddedToCart;
    public static ArrayList<Product> productsUserOrdered;
    public static ArrayList<Product> productsFavorited;
    public static User user;

    public static Product productInProductViewFragment;
     BottomNavigationView bottomNavigationView;
     FrameLayout frameLayout;
     String email;
     String username;
     FirebaseFirestore db;

    // Phones
    public static Product SAMSUNGGalaxyS24Plus = new Product(R.drawable.s24plus,1,"SAMSUNG Galaxy S24+","$969.99","$775.99","SAMSUNG Galaxy S24+ Plus Cell Phone, 512GB AI Smartphone, Unlocked Android, 50MP Camera, Fastest Processor, Long Battery Life, US Version, 2024, Onyx Black", PHONES);
    public static Product oneplus_12 = new Product(R.drawable.oneplus_12,1,"OnePlus 12","$899.99","$719.99","OnePlus 12,16GB RAM+512GB,Dual-SIM,Unlocked Android Smartphone,Supports 50W Wireless Charging,Latest Mobile Processor,Advanced Hasselblad Camera,5400 mAh Battery,2024,Flowy Emerald", PHONES);
    public static Product googlePixel8 = new Product(R.drawable.pixel_8,1,"Google Pixel 8","$499.00 ","$399.00","Smartphone with Advanced Pixel Camera, 24-Hour Battery, and Powerful Security - Obsidian - 128 GB", PHONES);
    public static Product samsungGalaxyA54 = new Product(R.drawable.a54_samsung,1,"SAMSUNG Galaxy A54","$449.99","$399.99","SAMSUNG Galaxy A54 5G A Series Cell Phone, Unlocked Android Smartphone, 128GB, 6.4” Fluid Display Screen, Pro Grade Camera, Long Battery Life, Refined Design, US Version, 2023, Awesome Black", PHONES);
    public static Product redmiNote13Pro = new Product(R.drawable.redmi_note13_pro,1,"Redmi Note 13 PRO","$310.96","$248.76","Xiaomi Redmi Note 13 PRO 5G + 4G LTE (256GB + 8GB) 6.67, 200MP Triple (Tmobile Mint Tello & Global) Global Bands Unlocked + (Fast Car Dual Charger Bundle) (Midnight Black (Global ROM))", PHONES);
    public static Product asusROGPhone8Pro = new Product(R.drawable.asus_rog_phone_8_pro,1,"ASUS ROG Phone 8 Pro","$1,199.99","$959.99","ASUS ROG Phone 8 Pro Unlocked Android Phone, US Version, 6.78 165Hz AMOLED Display, 512GB, 16GB RAM, 5500mAh Battery, 50MP Gimbal Camera, 32MP Front, Snapdragon 8 Gen 3, Dual-SIM, Phantom Black", PHONES);
    public static Product googlePixel8Pro = new Product(R.drawable.pixel_8_pro,1,"Google Pixel 8 Pro","$749.00","$599.99","Google Pixel 8 Pro - Unlocked Android Smartphone with Telephoto Lens and Super Actua Display - 24-Hour Battery - Porcelain - 128 GB", PHONES);
    public static Product iPhone13 = new Product(R.drawable.iphone_13,1,"IPhone 13","$388.10","$310.48","The iPhone 13 features a 6.1-inch (155 mm) display with Super Retina XDR OLED technology at a resolution of 2532×1170 pixels and a pixel density of about 460 PPI with a refresh rate of 60 Hz and Dolby Vision HDR.", PHONES);
    public static Product iPhone13Pro = new Product(R.drawable.iphone_13_pro,1,"IPhone 13 Pro","$494.75","$395.00","iPhone 13 Pro, 256GB, Sierra Blue - Unlocked (Renewed Premium), comes with charger, Mfi cable and SIM Pin ejector", PHONES);
    public static Product samsungGalaxyS10Plus = new Product(R.drawable.s10_plus,1,"SAMSUNG Galaxy S10+","$265.72","$212.58","SAMSUNG Galaxy S10+ Plus (128GB, 8GB) 6.4\" AMOLED, Snapdragon 855, IP68 Water Resistant, Global 4G LTE (GSM + CDMA) Unlocked (AT&T, Verizon, T-Mobile, Metro) G975U (Prism Blue)", PHONES);
    public static Product iPhone15ProMax = new Product(R.drawable.iphone_15_pro_max,1,"IPhone 15 Pro Max","$1,529.99","$1,223.99","iPhone 15 Pro Max has a 6.7-inch all-screen Super Retina XDR display with the Dynamic Island. The back is textured matte glass, and there is a contoured-edge titanium band around the frame.", PHONES);
    public static Product iPhone14ProMax = new Product(R.drawable.iphone_14_pro_max,1,"IPhone 14 Pro Max","$799.99","639.99","6.7-inch Super Retina XDR display featuring Always-On & ProMotion.", PHONES);

    //laptops
    public static Product acerAspire3 = new Product(R.drawable.acer_aspire_3,1,"Acer Aspire 3","$299.99","$239.99","Acer Aspire 3 A315-24P-R7VH Slim Laptop | 15.6\" Full HD IPS Display | AMD Ryzen 3 7320U Quad-Core Processor | AMD Radeon Graphics | 8GB LPDDR5 | 128GB NVMe SSD | Wi-Fi 6 | Windows 11 Home in S Mode", LAPTOP);
    public static Product lenovoLegion7i = new Product(R.drawable.leveni_legion_slim_7i,1,"Lenovo Legion 7i","$1,499.99","$1,199.99","Lenovo Legion Slim 7i Gaming & Entertainment Laptop (Intel i9-13900H 14-Core, 16GB DDR5 5200MHz RAM, 1TB SSD, GeForce RTX 4070, 16.0\" Win 11 Home) with Microsoft 365 Personal, Dockztorm Hub", LAPTOP);
    public static Product asusROGStrix16 = new Product(R.drawable.asus_rog_strix_16,1,"ROG Strix Scar","$2,899.99","$2,319.99","ASUS ROG Strix Scar 16 (2024) Gaming Laptop, 16” Nebula HDR 16:10 QHD 240Hz/3ms, 1100 nits, Mini LED Display, GeForce RTX 4080, Intel Core i9-14900HX, 32GB DDR5, 1TB SSD, Windows 11 Pro, G634JZR-XS96", LAPTOP);
    public static Product asusTUFA17Gaming = new Product(R.drawable.asus_tuf_a17,1,"ASUS TUF A17 Gaming ","$1,859.00","$1,487.00","ASUS TUF A17 Gaming Laptop - 17.3\" FHD Display, AMD Ryzen 9-7940HS (8-core), NVIDIA GeForce RTX 4070, 32GB DDR5, 1TB SSD, Backlit Keyboard, Wi-Fi 6, Windows 11 Home, with Laptop Stand", LAPTOP);
    public static Product asusTufA16 = new Product(R.drawable.asus_tuff_a16,1,"ASUS TUF A16","$979.00","$783.00","ASUS TUF Gaming A16 Laptop 16.0\" 165 Hz FHD+WVA (8-Core AMD Ryzen 7 7735HS, 16GB DDR5, 1TB PCIe SSD, AMD Radeon RX 7600S 8GB, Backlit KYB, WiFi 6, Win11 Home) with Dockztorm Hub", LAPTOP);
    public static Product acerNitro17Gaming = new Product(R.drawable.acer_nitro_17,1,"Acer Nitro Gaming","$983.48","$786.78","Acer Nitro 17 Gaming Laptop AMD Ryzen 7 7840HS Octa-Core CPU 17.3\" FHD 165Hz IPS Display NVIDIA GeForce RTX 4050 16GB DDR5 1TB SSD Wi-Fi 6E RGB Backlit KB AN17-41-R6L9", LAPTOP);
    public static Product msiSwordGaming = new Product(R.drawable.msi_sword_15,1,"MSI Sword Gaming"," $1,199.99","$959.99","MSI Newest Sword 15 Gaming Laptop, 15.6\" FHD 144Hz IPS-Type Display, NVIDIA GeForce RTX 4060, Intel Core i7-12650H, 32GB DDR4, 1TB PCIe SSD, Wi-Fi 6, Windows 11 Home, Backlit Keyboard, White/OLY", LAPTOP);
    public static Product msiRaiderGE76 = new Product(R.drawable.msi_raider_laptop,1,"MSI Raider GE76","$1,391.03","$1,112.82","MSI Raider GE76 Gaming Laptop: Intel Core i9-12900H, GeForce RTX 3060, 17.3\" 144Hz FHD Display,16GB DDR5, 1TB NVMe SSD, Thunderbolt 4, Cooler Boost 5, Win 11 Home: Titanium Blue 12UE-871", LAPTOP);
    public static Product gigabyteG6 = new Product(R.drawable.gigabyte_laptop,1,"GIGABYTE - G6","$999.00","$799.00","GIGABYTE - G6 (2024) Gaming Laptop - 165Hz 1920x1200 WUXGA - NVIDIA GeForce RTX 4050 - Intel i7-13620H - 1TB SSD with 16GB DDR5 RAM - Win11 Home+ (G6 MF-H2US854KH)", LAPTOP);
    public static Product razerBlade16 = new Product(R.drawable.razor_laptop,1,"Razer Blade 16","$4,699.99","$3,759.99","Razer Blade 16 (2024) Gaming Laptop: 16” Mini LED Dual Mode 4K UHD+ 120Hz / FHD+ 240Hz – NVIDIA GeForce RTX 4090 – Intel Core i9-14900HX - 64GB DDR5 RAM  - 4TB M.2 SSD – Chroma RGB – Black", LAPTOP);
    public static Product hpVictus15 = new Product(R.drawable.hp_xictus_laptop,1,"HP Victus 15","$578.00","$462.00","HP Victus 15 Gaming Laptop 15.6\" FHD IPS 144Hz AMD 7000 Ryzen 5 7535HS (Beats i7-11800H) GeForce RTX 2050 4GB Graphic Backlit USB-C B&O Win11 Black + HDMI Cable (8GB RAM | 512GB PCIe SSD)", LAPTOP);
    public static Product msiPulse17 = new Product(R.drawable.msi_pulse,1,"MSI Pulse 17 ","$1,899.00","$1519.99","MSI Pulse 17 Gaming Laptop: 13th Gen i9, 17” 240Hz QHD Display, NVIDIA GeForce RTX 4070, 32GB DDR5, 1TB NVMe SSD, Cooler Boost 5, Win11 Home: Black B13VGK-887US", LAPTOP);


    // Games
    public static Product massEffectLegendary = new Product(R.drawable.mass_effect_legendary,1,"Mass Effect Legendary","$59.99","$47.99","One person is all that stands between humanity and the greatest threat it's ever faced. Relive the Legend of Commander shepard in the highly acclaimed mass Effect Trilogy with the mass Effect legendary Edition. Includes single-player base content and DLC from mass Effect, mass Effect 2, and mass Effect 3, plus Promo weapons, armors, and packs - all remastered and optimized for 4K Ultra HD.\n", GAMES);
    public static Product deadSpaceStandard = new Product(R.drawable.dead_space_game,1,"Dead Space Standard","$59.99","$47.99","The sci-fi survival horror classic Dead Space returns, completely rebuilt from the ground up to offer a deeper and more immersive experience. This remake brings jaw-dropping visual fidelity, suspenseful atmospheric audio, and improvements to gameplay while staying faithful to the original game’s thrilling vision. Isaac ", GAMES);
    public static Product starWarsJediSurvivorStandard = new Product(R.drawable.star_wars_jedi_game,1,"Star Wars Jedi: Survivor Standard","$69.99","$55.99","The story of Cal Kestis continues in Star Wars Jedi: Survivor, a third person galaxy-spanning action-adventure game from Respawn Entertainment, developed in collaboration with Lucasfilm Games. This narratively-driven, single player title picks up five years after the events of Star Wars Jedi: Fallen Order and follows Cal’s increasingly desperate fight as the galaxy descends further into darkness. ", GAMES);
    public static Product transformersFallOfCybertron = new Product(R.drawable.transformers_game,1,"Transformers: Fall of Cybertron","$49.99","$39.99","Transformers: Fall of Cybertron is a third-person shooter that returns players to the Transformer's planet of Cybertron for the final battles of the legendary war that preceded their arrival on Earth. The game continues the story of the earlier game, Transformers: War for Cybertron.", GAMES);
    public static Product needForSpeedUnboundPalace = new Product(R.drawable.nfs_unbound_game,1,"Need for Speed Unbound Palace","$79.99","$63.99","The world is your canvas in Need for Speed Unbound. Prove you have what it takes to win The Grand, Lakeshore’s ultimate street racing challenge. Across four intense weeks of racing, earn enough cash to enter weekly qualifiers, beat the competition, and make your mark on the street racing scene while outdriving and outsmarting the cops.", GAMES);
    public static Product godOfWarStandard = new Product(R.drawable.god_war_game,1,"God of War Standard","$44.99","$35.99","Kratos is a father again. As mentor and protector to Atreus, a son determined to earn his respect, he is forced to deal with and control the rage that has long defined him while out in a very dangerous world with his son.", GAMES);
    public static Product maddenNFL24 = new Product(R.drawable.madden_nfl_gmae,1,"Madden NFL 24 ","$69.99","$55.99","More realistic character movement and smarter AI gives you control to play out your gameplay strategy with the confidence to dominate any opponent in Madden NFL 24. *NEW GAMEPLAY MECHANICS ONLY ON PlayStation 5, Xbox Series X|S and PC. SAPIEN technology provides a leap forward in character technology introduces new anatomically accurate NFL player skeletons that are more responsive and true-to-life player motion.", GAMES);
    public static Product mafiaDefinitiveEdition = new Product(R.drawable.mafia_game,1,"Mafia: Definitive Edition ","$39.99","$28.79","Play a Mob Movie: Live the life of a Prohibition-era gangster and rise through the ranks of the Mafia.", GAMES);
    public static Product theOuterWorldsStandard = new Product(R.drawable.the_outer_worlds_game,1,"The Outer Worlds: Standard ","$29.99","$23.99","The Outer Worlds is an award-winning single-player first-person sci-fi RPG from Obsidian Entertainment and Private Division.", GAMES);
    public static Product immortalsOfAveumStandard = new Product(R.drawable.the_immortals_game,1,"Immortals of Aveum Standard","$59.99","$47.99","Summon your power, stop the Everwar, save the realms. Immortals of Aveum is a single-player first person magic shooter that tells the story of Jak as he joins an elite order of battlemages to save a world on the edge of abyss. Master three forces of magic, unleash spells with deadly skill, and decimate legions of enemies in a game that defies conventions of what we’ve come to expect from first person shooters.", GAMES);
    public static Product redDeadRedemption2 = new Product(R.drawable.red_dead_redempdemtion_game,1,"Red Dead Redemption 2","$56.99","$45.59","Red Dead Redemption 2, the critically acclaimed open world epic from Rockstar Games and the highest rated game of the console generation, now enhanced for PC with new Story Mode content, visual upgrades and more.", GAMES);
    public static Product battlefield3PremiumEdition = new Product(R.drawable.battlefield_3_game,1,"Battlefield 3: Premium Edition ","$39.99","$31.99","Ramp up the intensity in Battlefield 3 and enjoy total freedom to fight the way you want. Explore nine massive multiplayer maps and use loads of vehicles, weapons, and gadgets to help you turn up the heat. Plus, every second of battle gets you closer to unlocking tons of extras and moving up in the Ranks. So get in the action. Key Features: Play to your strengths.", GAMES);

    // Consoles
    public static Product nintendoSwitch = new Product(R.drawable.nintendo_switch,1,"Nintendo Switch™","$296.01","$236.80","Play at home or on the go with one system The Nintendo Switch™ system is designed to go wherever you do, instantly transforming from a home console you play on TV to a portable system you can play anywhere. So you get more time to play the games you love, however you like.", CONSOLES);
    public static Product metaQuest2 = new Product(R.drawable.meta_quest,1,"Meta Quest 2","$199.00","$159.00","Meta Quest 2 is the all-in-one system that truly sets you free to explore in VR. Simply put on the headset and enter fully-immersive, imagination-defying worlds. A built-in battery, fast processor and immersive graphics keep your experience smooth and seamless, while 3D positional audio, hand tracking and easy-to-use controllers make virtual worlds feel real.", CONSOLES);
    public static Product xboxSeriesS = new Product(R.drawable.xbox_series_s,1,"Xbox Series S","$299.00","$239.00","2021 Microsoft Xbox Series S 512GB Game All-Digital Console, One Xbox Wireless Controller, 1440p Gaming Resolution, 4K Streaming, 3D Sound, WiFi, White", CONSOLES);
    public static Product playstation4Slim = new Product(R.drawable.ps4_slim,1,"PlayStation 4 Slim","$223.99","$179.99","Edition:Slim 1TB The all new lighter and slimmer PlayStation4 system has a 1TB hard drive for all of the greatest games, TV, music and more. Incredible Games You've come to the right place.", CONSOLES);
    public static Product valveSteamDeck = new Product(R.drawable.steam_deck,1,"Valve Steam Deck","$526.99","$421.59","Valve Steam Deck 512GB Handheld Gaming Console, 1280 x 800 LCD Display, with Carring case, Tempered Film and Soft Silicone Protective Case", CONSOLES);
    public static Product asusRogAlly = new Product(R.drawable.asus_ally,1,"ASUS ROG Ally","$659.99","$527.99","Any Game, Anywhere. Sink deep into your favourite AAA or indie games and watch the hours melt away with an expansive Full HD 120Hz display and incredibly comfortable ergonomics.", CONSOLES);
    public static Product xboxSeriesX = new Product(R.drawable.xbox_series_x,1,"Xbox Series X","$439.99","$351.99","Next Gen Console Bundle - Xbox Series X 1TB + 8K Premium HDMI Cable - 4 feet- 48Gbps Hight Speed HDR for Gaming Console.", CONSOLES);
    public static Product playstationPS5Console = new Product(R.drawable.ps5,1,"PlayStation PS5 Console","$659.00","$527.00","PlayStation 5 console, DualSense Wireless Controller, Base, HDMI Cable, AC power cord, USB cable, God of War Ragnarok full game voucher\n", CONSOLES);
    public static Product playstation4Pro = new Product(R.drawable.ps4_pro,1,"PlayStation 4 Pro","$244.00","$195.00","Sony PlayStation 4 Pro w/ Accessories, 1TB HDD, CUH-7215B - Jet Black.Enhanced games - PS4 Pro games burst into life with intensely sharp graphics, stunningly vibrant colours, textures and environments and smoother, more stable performance", CONSOLES);
    public static Product playstation4 = new Product(R.drawable.ps4,1,"PlayStation 4","$205.00","$164.00","incredible games; Endless entertainment,1 TB hard drive,Blu-ray technology, delivers exceptional video quality.", CONSOLES);
    public static Product xboxOneS = new Product(R.drawable.xbox_one_s,1,"Xbox One S","$224.99","$179.99","Microsoft - Xbox One S 500GB Console - White - ZQ9-00028 ","Gaming Consoles");
    public static Product xboxOne = new Product(R.drawable.xbox_1,1,"Xbox One","$176.00","$140.00","This item includes the Xbox One console, 1 wireless controller, HDMI cable, and power supply . For more troubleshooting steps please check the manufacturer's webiste", CONSOLES);

    // Appliances
    public static Product samsung32Inch4K = new Product(R.drawable.samsung_32_qled_tv,1,"SAMSUNG 32-Inch 4K","$447.99","$358.39","SAMSUNG 32-Inch Class QLED 4K Q60C Series Quantum HDR, Dual LED, Object Tracking Sound Lite, Q-Symphony, Motion Xcelerator, Gaming Hub, Smart TV with Alexa Built-in (QN32Q60C, 2023 Model),Titan Gray", APPLIANCES);
    public static Product clearCookAirFryer = new Product(R.drawable.air_fryer,1,"ClearCook Air Fryer","$89.95","$71.76","Instant Vortex Plus 6QT ClearCook Air Fryer, Clear Windows, Custom Program Options, 6-in-1 Functions, Crisps, Broils, Roasts, Dehydrates, Bakes, Reheats, from the Makers of Instant Pot, Black", APPLIANCES);
    public static Product smartTowerFan = new Product(R.drawable.smart_fan,1,"Smart Tower Fan"," $63.99","$51.19","Dreo Smart Tower Fan for Bedroom, Standing Fans for Indoors, 90° Oscillating, Quiet 26ft/s Velocity Floor Fan with Remote, 5 Speeds, 8H Timer, Voice Control Bladeless Room Fan, Works with Alexa", APPLIANCES);
    public static Product airPurifier = new Product(R.drawable.air_purifier,1,"Air Purifier","$189.99","$151.99","LEVOIT Air Purifiers for Home Large Room Up to 1980 Ft² in 1 Hr With Air Quality Monitor, Smart WiFi and Auto Mode, 3-in-1 Filter Captures Pet Allergies, Smoke, Dust, Core 400S/Core 400S-P, White", APPLIANCES);
    public static Product thermostat = new Product(R.drawable.thermostat,1,"Thermostat ","$199.98","$159.98","Google Nest Learning Thermostat - Programmable Smart Thermostat for Home - 3rd Generation Nest Thermostat - Works with Alexa - Stainless Steel", APPLIANCES);
    public static Product robotVacuum = new Product(R.drawable.roomba, 1,"Robot Vacuum","$219.99","$175.99","iRobot Roomba 694 Robot Vacuum-Wi-Fi Connectivity, Personalized Cleaning Recommendations, Works with Alexa, Good for Pet Hair, Carpets, Hard Floors, Self-Charging, Roomba 694", APPLIANCES);
    public static Product amazonEcho = new Product(R.drawable.amazon_echo,1,"Amazon Echo","$59.99","$47.99","Amazon Echo Dot (5th Gen) with clock | Compact smart speaker with Alexa and enhanced LED display for at-a-glance clock, timers, weather, and more | Cloud Blue", APPLIANCES);
    public static Product tvSpeaker = new Product(R.drawable.soundbar,1,"TV Speaker"," $229.00","$183.00","Bose TV Speaker - Soundbar for TV with Bluetooth and HDMI-ARC Connectivity, Black, Includes Remote Control", APPLIANCES);
    public static Product dysonVacuum = new Product(R.drawable.vaccume_cleaner,1,"Dyson Vacuum"," $299.00","$239.00","Ball technology. Navigate around obstacles with a simple turn of the wrist. For easy, precise maneuvering around your home.", APPLIANCES);
    public static Product ninjaKitchenSystem = new Product(R.drawable.blender,1,"Ninja Kitchen System"," $159.95","$127.96","Ninja BL770 Mega Kitchen System, 1500W, 4 Functions for Smoothies, Processing, Dough, Drinks & More, with 72-oz.* Blender Pitcher, 64-oz. Processor Bowl, (2) 16-oz. To-Go Cups & (2) Lids, Black", APPLIANCES);
    public static Product electricKettle = new Product(R.drawable.kettle,1,"Electric Kettle","$69.99","$55.99","COSORI Electric Gooseneck Kettle with 5 Temperature Control Presets, Pour Over Kettle for Coffee & Tea, Hot Water Boiler, 100% Stainless Steel Inner Lid & Bottom, 1200W/0.8L", APPLIANCES);
    public static Product ledLamp = new Product(R.drawable.lamp,1,"LED Lamp","$115.99","$92.79","LED Floor Lamp, Height Adjustable Floor Lamps for Living Room, Super Bright Standing Lamp with Timer, Adjustable Colors & Brightness Floor lamp for Bedroom with Remote & Touch Control, Black", APPLIANCES);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       getUserFromDatabase();
      /* for(Product product:getProductsData(EVERY_PRODUCT)){
           addProductDataToDatabase(product);
       }*/
    }

    public void addProductDataToDatabase(Product product){
        Map<String, Object> productData = new HashMap<>();
        productData.put("productName", product.getProductName());
        productData.put("productPrice", product.getProductPrice());
        productData.put("productSalesPrice", product.getProductSalesPrice());
        productData.put("productDescription", product.getProductDescription());
        productData.put("productId", product.getProductId());
        productData.put("productQuantity", product.getProductQuantity());

        db.collection("products")
                .add(productData)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Product added to Firestore with name: " +product.getProductName()))
                .addOnFailureListener(e -> Log.e(TAG, "Error adding product to Firestore", e));
    }

    private void getUserFromDatabase() {
        email =getIntent().getStringExtra("email");

        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            user = document.toObject(User.class);
                            if (user.getEmail().equals(email)) {
                                username = user.getUsername();
                                initializeViews();
                                return;
                            }
                        }
                        Log.d(TAG, "No user found with email: " + email);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    private void initializeViews() {
        bottomNavigationView = findViewById(R.id.bottomnav);
        setupBottomNavigationView();
        productsAddedToCart=user.getProductsUserAddedToCart();
        productsUserOrdered=user.getProductsUserOrdered();
        productsFavorited=user.getProductsFavorited();
    }

    private void setupBottomNavigationView() {
        loadFragment(new HomeFragment());
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId()== R.id.home){
                loadFragment(new HomeFragment());
            }
            else if (item.getItemId()== R.id.orders) {
                loadFragment(new OrdersFragment());
            } else if (item.getItemId()== R.id.account) {

                loadFragment(new AccountFragment());

            }else if (item.getItemId()== R.id.cart) {
                loadFragment(new CartFragment());
            }
            return true;
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        /*fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
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
            productsArrayList.add(SAMSUNGGalaxyS24Plus);
            productsArrayList.add(oneplus_12);
            productsArrayList.add(googlePixel8);
            productsArrayList.add(samsungGalaxyA54);
            productsArrayList.add(redmiNote13Pro);
            productsArrayList.add(asusROGPhone8Pro);
            productsArrayList.add(googlePixel8Pro);
            productsArrayList.add(iPhone13);
            productsArrayList.add(iPhone13Pro);
            productsArrayList.add(samsungGalaxyS10Plus);
            productsArrayList.add(iPhone15ProMax);
            productsArrayList.add(iPhone14ProMax);

        } else if (LAPTOP.equals(id)) {
            productsArrayList.add(acerAspire3);
            productsArrayList.add(lenovoLegion7i);
            productsArrayList.add(asusROGStrix16);
            productsArrayList.add(asusTUFA17Gaming);
            productsArrayList.add(asusTufA16);
            productsArrayList.add(acerNitro17Gaming);
            productsArrayList.add(msiSwordGaming);
            productsArrayList.add(msiRaiderGE76);
            productsArrayList.add(gigabyteG6);
            productsArrayList.add(razerBlade16);
            productsArrayList.add(hpVictus15);
            productsArrayList.add(msiPulse17);

        }else if (GAMES.equals(id)) {
            productsArrayList.add(massEffectLegendary);
            productsArrayList.add(deadSpaceStandard);
            productsArrayList.add(starWarsJediSurvivorStandard);
            productsArrayList.add(transformersFallOfCybertron);
            productsArrayList.add(needForSpeedUnboundPalace);
            productsArrayList.add(godOfWarStandard);
            productsArrayList.add(maddenNFL24);
            productsArrayList.add(mafiaDefinitiveEdition);
            productsArrayList.add(theOuterWorldsStandard);
            productsArrayList.add(immortalsOfAveumStandard);
            productsArrayList.add(redDeadRedemption2);
            productsArrayList.add(battlefield3PremiumEdition);

        }  else if (CONSOLES.equals(id)) {
            productsArrayList.add(nintendoSwitch);
            productsArrayList.add(metaQuest2);
            productsArrayList.add(xboxSeriesS);
            productsArrayList.add(playstation4Slim);
            productsArrayList.add(valveSteamDeck);
            productsArrayList.add(asusRogAlly);
            productsArrayList.add(xboxSeriesX);
            productsArrayList.add(playstationPS5Console);
            productsArrayList.add(playstation4Pro);
            productsArrayList.add(playstation4);
            productsArrayList.add(xboxOneS);
            productsArrayList.add(xboxOne);
        } else if (APPLIANCES.equals(id)) {
            productsArrayList.add(samsung32Inch4K);
            productsArrayList.add(clearCookAirFryer);
            productsArrayList.add(smartTowerFan);
            productsArrayList.add(airPurifier);
            productsArrayList.add(thermostat);
            productsArrayList.add(robotVacuum);
            productsArrayList.add(amazonEcho);
            productsArrayList.add(tvSpeaker);
            productsArrayList.add(dysonVacuum);
            productsArrayList.add(ninjaKitchenSystem);
            productsArrayList.add(electricKettle);
            productsArrayList.add(ledLamp);
        } else if (EVERY_PRODUCT.equals(id)) {
            productsArrayList.add(SAMSUNGGalaxyS24Plus);
            productsArrayList.add(oneplus_12);
            productsArrayList.add(googlePixel8);
            productsArrayList.add(samsungGalaxyA54);
            productsArrayList.add(redmiNote13Pro);
            productsArrayList.add(asusROGPhone8Pro);
            productsArrayList.add(googlePixel8Pro);
            productsArrayList.add(iPhone13);
            productsArrayList.add(iPhone13Pro);
            productsArrayList.add(samsungGalaxyS10Plus);
            productsArrayList.add(iPhone15ProMax);
            productsArrayList.add(iPhone14ProMax);
            productsArrayList.add(lenovoLegion7i);
            productsArrayList.add(asusROGStrix16);
            productsArrayList.add(asusTUFA17Gaming);
            productsArrayList.add(asusTufA16);
            productsArrayList.add(acerNitro17Gaming);
            productsArrayList.add(msiSwordGaming);
            productsArrayList.add(msiRaiderGE76);
            productsArrayList.add(gigabyteG6);
            productsArrayList.add(razerBlade16);
            productsArrayList.add(hpVictus15);
            productsArrayList.add(msiPulse17);
            productsArrayList.add(massEffectLegendary);
            productsArrayList.add(deadSpaceStandard);
            productsArrayList.add(starWarsJediSurvivorStandard);
            productsArrayList.add(transformersFallOfCybertron);
            productsArrayList.add(needForSpeedUnboundPalace);
            productsArrayList.add(godOfWarStandard);
            productsArrayList.add(maddenNFL24);
            productsArrayList.add(mafiaDefinitiveEdition);
            productsArrayList.add(theOuterWorldsStandard);
            productsArrayList.add(immortalsOfAveumStandard);
            productsArrayList.add(redDeadRedemption2);
            productsArrayList.add(battlefield3PremiumEdition);
            productsArrayList.add(nintendoSwitch);
            productsArrayList.add(metaQuest2);
            productsArrayList.add(xboxSeriesS);
            productsArrayList.add(playstation4Slim);
            productsArrayList.add(valveSteamDeck);
            productsArrayList.add(asusRogAlly);
            productsArrayList.add(xboxSeriesX);
            productsArrayList.add(playstationPS5Console);
            productsArrayList.add(playstation4Pro);
            productsArrayList.add(playstation4);
            productsArrayList.add(xboxOneS);
            productsArrayList.add(xboxOne);
            productsArrayList.add(samsung32Inch4K);
            productsArrayList.add(clearCookAirFryer);
            productsArrayList.add(smartTowerFan);
            productsArrayList.add(airPurifier);
            productsArrayList.add(thermostat);
            productsArrayList.add(robotVacuum);
            productsArrayList.add(amazonEcho);
            productsArrayList.add(tvSpeaker);
            productsArrayList.add(dysonVacuum);
            productsArrayList.add(ninjaKitchenSystem);
            productsArrayList.add(electricKettle);
            productsArrayList.add(ledLamp);
        }

        return productsArrayList;

    }
    public static List<Product> getPopularProductsData() {
        List<Product>productsArrayList = new ArrayList<>();
        productsArrayList.add(lenovoLegion7i);
        productsArrayList.add(asusROGStrix16);
        productsArrayList.add(asusTUFA17Gaming);
        productsArrayList.add(asusTufA16);
        productsArrayList.add(samsung32Inch4K);
        productsArrayList.add(clearCookAirFryer);
        productsArrayList.add(metaQuest2);
        productsArrayList.add(xboxSeriesS);
        productsArrayList.add(playstation4Slim);
        productsArrayList.add(valveSteamDeck);
        productsArrayList.add(asusRogAlly);
        productsArrayList.add(smartTowerFan);
        return productsArrayList;
    }

    public static List<Product> getSalesProductsData() {
        List<Product>productsArrayList = new ArrayList<>();
        productsArrayList.add(lenovoLegion7i);
        productsArrayList.add(asusROGStrix16);
        productsArrayList.add(asusTUFA17Gaming);
        productsArrayList.add(asusTufA16);
        productsArrayList.add(samsung32Inch4K);
        productsArrayList.add(clearCookAirFryer);
        productsArrayList.add(metaQuest2);
        productsArrayList.add(xboxSeriesS);
        productsArrayList.add(playstation4Slim);
        productsArrayList.add(valveSteamDeck);
        productsArrayList.add(asusRogAlly);
        productsArrayList.add(smartTowerFan);
        return productsArrayList;
    }

    public static List<Product> getPickedForYouProductsData() {
        List<Product>productsArrayList = new ArrayList<>();
        productsArrayList.add(needForSpeedUnboundPalace);
        productsArrayList.add(xboxSeriesX);
        productsArrayList.add(SAMSUNGGalaxyS24Plus );
        productsArrayList.add(oneplus_12);
        productsArrayList.add(lenovoLegion7i);
        productsArrayList.add(googlePixel8);
        productsArrayList.add(samsungGalaxyA54);
        productsArrayList.add(redmiNote13Pro);
        return productsArrayList;
    }

}