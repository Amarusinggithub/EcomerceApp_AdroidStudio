package com.example.myecomerceapp.fragments;


import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.myecomerceapp.interfaces.ItemOnClickInterface;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.ItemAdapter;
import com.example.myecomerceapp.models.ItemModel;

public class ItemGridViewFragment extends Fragment implements ItemOnClickInterface {
    public static String categoryId;
    static List<ItemModel>  filtertedList;
    public static ItemAdapter itemAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ItemCridView=inflater.inflate(R.layout.fragment_item_grid_view, container, false);

        GridView itemGridView =ItemCridView.findViewById( R.id.gridView);
         itemAdapter = new ItemAdapter(this, getData(categoryId));
        itemGridView.setNumColumns(2);
        itemGridView.setAdapter(itemAdapter);

        return ItemCridView;
    }
    public static void filter(String newText) {
        filtertedList= new ArrayList<>();
        for(ItemModel item:getData(categoryId)){
            if(item.getItemName().toLowerCase().contains(newText.toLowerCase())){
                filtertedList.add(item);

            }
        }itemAdapter.filteredList(filtertedList);

    }

    public static List<ItemModel> getData(String categoryId) {
        List<ItemModel>ItemsArrayList = new ArrayList<>();
        if ("Fashion".equals(categoryId)) {
            // add fashion items
            ItemsArrayList.add(new ItemModel(R.drawable.women_oversize_tshirt,"Turtleneck Pullover Long Sleeve Hoodie","$19","Trendy Queen Womens Oversized Sweatshirts Turtleneck Pullover Long Sleeve Hoodies Tops Fall Outfits 2023 Clothes","Fashion"));
            ItemsArrayList.add(new ItemModel(R.drawable.whomendressshoes,"TRETORN Women's Rawlins Casual Lace-Up Sneakers","$20"," FASHIONABLE, TRENDY & VERSATILE for all your warm weather outfits. Wear with dresses and skirts, cropped pants and cute summer shorts.","Fashion"));
            ItemsArrayList.add(new ItemModel(R.drawable.white_buttondown_shirt,"White Shirt","$500","All black suit","Fashion"));
            ItemsArrayList.add(new ItemModel(R.drawable.men_cargo_pants,"THWEI Mens Cargo Pants Casual Joggers Athletic Pants Cotton Loose Straight Sweatpants","$18","Material: mens cargo pants made of premium fabric, 95% Cotton & 5% Spandex.Durable & Comfortable,Lightweight,breathable fabric great for everyday wear","Fashion"));
            ItemsArrayList.add(new ItemModel(R.drawable.men_sweather,"Zaitun Mens Hooded Sweatshirt Long Sleeve Solid Knitted Hoodie Pullover Sweater","$30","Smart Fabric - The material is woven to be medium stretchable & breathable, easy to fit with different body types, and keep warm but not stuffy.\n" +
                    "Design - More of a trendy weave texture sweater with good color selection, than a classic hooded sweatshirt. Good for layering.\n" +
                    "Occasion - A nice casual outfit, everyday/workout lightweight pullover hoodie. A good choice during the season transition.\n","Fashion"));
            ItemsArrayList.add(new ItemModel(R.drawable.men_tshirts,"Gildan Men's Crew T-Shirts, Multipack, Style G1100","$30","Soft, breathable cotton\n" +
                    "Moisture wicking technology keeps you cool and dry","Fashion"));
        } else if ("Groceries".equals(categoryId)) {
            // add groceries items
            ItemsArrayList.add(new ItemModel(R.drawable.peanuts,"LANTERS Pistachio Lovers Nut Mix ","$12","ANTERS Pistachio Lovers Nut Mix is an assortment of pistachios, almonds and cashews roasted in peanut oil to crispy perfection. Wonderfully crunchy and seasoned with salt, PLANTERS Mixed Nuts with no peanuts have the delicious flavor you crave, whether you need a between-meal or on-the-go snack. ","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.cranberryjuice,"Cranberry Juice","$5","Ocean Spray® Cranberry Juice Cocktail","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.dorritos,"Doritos","$20","Doritoz","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.butter,"Butter","$200","Kerrygold, Butter With Canola Oil","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.doritoz,"Doritoz","$200","Doritoz","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.sausage,"Honest Dogs","$200","Pasture-raised Beef, Water, Contains 2% or Less of the Following: Onion, Garlic, Mustard, Paprika, Celery Powder, Vinegar, Salt, Sugar, Extractives of Paprika, Allspice, Coriander, Nutmeg, Red Pepper, Rosemary.\n","Groceries"));
        } else if ("Electronics".equals(categoryId)) {
            ItemsArrayList.add(new ItemModel(R.drawable.gaming_laptop,"ASUS ROG Strix G16 (2023)","$1,219.89","ASUS ROG Strix G16 (2023) Gaming Laptop, 16” 16:10 FHD 165Hz, GeForce RTX 4060, Intel Core i7-13650HX, 16GB DDR5, 512GB PCIe SSD, Wi-Fi 6E, Windows 11, G614JV-AS73, Eclipse Gray","Electronics"));
            ItemsArrayList.add(new ItemModel(R.drawable.s24ultra,"SAMSUNG Galaxy S24 Ultra","$1,419.99","SAMSUNG Galaxy S24 Ultra Cell Phone, 512GB AI Smartphone, Unlocked Android, 200MP, 100x Zoom Cameras, Long Battery Life, S Pen, US Version, 2024, Titanium Black","Electronics"));
            ItemsArrayList.add(new ItemModel(R.drawable.doritoz,"Doritoz","$200","Doritoz","Electronics"));
            ItemsArrayList.add(new ItemModel(R.drawable.butter,"Butter","$200","Butter","Electronics"));
            ItemsArrayList.add(new ItemModel(R.drawable.doritoz,"Doritoz","$200","Doritoz","Electronics"));
            ItemsArrayList.add(new ItemModel(R.drawable.butter,"Butter","$200","Butter","Electronics"));
        } else if ("Home Appliances".equals(categoryId)) {
            ItemsArrayList.add(new ItemModel(R.drawable.milk_img,"Milk","$500","white cows milk","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.cranberryjuice,"Cranberry Juice","$500","Cranberry Juice","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.doritoz,"Doritoz","$200","Doritoz","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.butter,"Butter","$200","Butter","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.doritoz,"Doritoz","$200","Doritoz","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.butter,"Butter","$200","Butter","Groceries"));
        } /*else if ("Personal Care".equals(categoryId)) {
            ItemsArrayList.add(new ItemModel(R.drawable.milk_img,"Milk","$500","white cows milk","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.cranberryjuice,"Cranberry Juice","$500","Cranberry Juice","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.doritoz,"Doritoz","$200","Doritoz","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.butter,"Butter","$200","Butter","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.doritoz,"Doritoz","$200","Doritoz","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.butter,"Butter","$200","Butter","Groceries"));
        } *//*else if ("Sports And Adventure".equals(categoryId)) {
            ItemsArrayList.add(new ItemModel(R.drawable.milk_img,"Milk","$500","white cows milk","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.cranberryjuice,"Cranberry Juice","$500","Cranberry Juice","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.doritoz,"Doritoz","$200","Doritoz","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.butter,"Butter","$200","Butter","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.doritoz,"Doritoz","$200","Doritoz","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.butter,"Butter","$200","Butter","Groceries"));
        } else if ("Sales".equals(categoryId)) {
            ItemsArrayList.add(new ItemModel(R.drawable.milk_img,"Milk","$500","white cows milk","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.cranberryjuice,"Cranberry Juice","$500","Cranberry Juice","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.doritoz,"Doritoz","$200","Doritoz","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.butter,"Butter","$200","Butter","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.doritoz,"Doritoz","$200","Doritoz","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.butter,"Butter","$200","Butter","Groceries"));
        }*/
        return ItemsArrayList;

    }

    private  void loadFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemClicked(int position) {
        ItemModel item = getData(categoryId).get(position);
        Bundle bundle = new Bundle();
        bundle.putString("ItemName", item.getItemName());
        bundle.putString("ItemPrice", item.getItemPrice());
        bundle.putString("ItemDescription", item.getItemSpecs());
        bundle.putInt("ItemImage", item.getItemImage());
        ItemViewFragment itemViewFragment = new ItemViewFragment();
        itemViewFragment.setArguments(bundle);
        loadFragment(itemViewFragment);
    }

}