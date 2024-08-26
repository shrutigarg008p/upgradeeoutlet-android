package com.eoutlet.Eoutlet.adpters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.UpgradeWishListFragment;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradeWishListItems;
import com.squareup.picasso.Picasso;

import java.util.List;


public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {
    private List<UpgradeWishListItems> upgradeWishListItems;
    private Context context;
    View itemView;
    UpgradeWishListFragment upgradeWishListFragment;

    public WishListAdapter(Context context, List<UpgradeWishListItems> upgradeWishListItems, UpgradeWishListFragment upgradeWishListFragment) {
        this.context = context;
        this.upgradeWishListItems = upgradeWishListItems;
        this.upgradeWishListFragment = upgradeWishListFragment;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView wishListItemImage;
        CardView sellerProductContainer;
        TextView wishListOptions, wishListItemName, wishListItemPrice, wishListItemOldPrice, wishlistDealText, wishlistOutOfStockText;
        RatingBar wishListRatingBar;
        FrameLayout addToCartButton, removeWishListItem;
        RelativeLayout secondmainlayout;

        MyViewHolder(View view) {
            super(view);
            wishListItemImage = view.findViewById(R.id.wishListItemImage);
            removeWishListItem = view.findViewById(R.id.removeWishListItem);
            wishListOptions = view.findViewById(R.id.wishListOptions);
            wishListRatingBar = view.findViewById(R.id.wishListRatingBar);
            wishListItemName = view.findViewById(R.id.wishListItemName);
            wishListItemPrice = view.findViewById(R.id.wishListItemPrice);
            wishListItemOldPrice = view.findViewById(R.id.wishListItemOldPrice);
            addToCartButton = view.findViewById(R.id.addToCartButton);
            wishlistDealText = view.findViewById(R.id.wishlistDealText);
            wishlistOutOfStockText = view.findViewById(R.id.wishlistOutOfStockText);
            sellerProductContainer = view.findViewById(R.id.sellerProductContainer);
            secondmainlayout = view.findViewById(R.id.secondmainlayout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                itemView = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.wishlist_items_arabic, parent, false);
                        .inflate(R.layout.wishlist_item_new_arabic, parent, false);
            } else {
                itemView = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.wishlist_items, parent, false);
                        .inflate(R.layout.wishlist_item_new, parent, false);
            }
        } else {
            itemView = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.wishlist_items_arabic, parent, false);
                    .inflate(R.layout.wishlist_items_arabic, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       // Log.e("wishlist adapter type", position + " " + upgradeWishListItems.get(position).getType());
        UpgradeWishListItems wishList = upgradeWishListItems.get(position);
        Picasso.get().load(wishList.getImg()).into(holder.wishListItemImage);

        holder.wishListItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UpgradeWishListFragment) upgradeWishListFragment).opendetail(wishList.getItem_id(), wishList.getProduct_id(), wishList.getType(), wishList.getSku(), wishList.getName(), wishList.getOld_price(), wishList.getPrice());

            }
        });

      /*  holder.secondmainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UpgradeWishListFragment) upgradeWishListFragment).opendetail(wishList.getItem_id(), wishList.getProduct_id(), wishList.getType(), wishList.getSku(), wishList.getName(), wishList.getOld_price(), wishList.getPrice());

            }
        });*/
        holder.wishListItemName.setText(wishList.getName());
        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            holder.wishListItemPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext())+" " + Math.round(wishList.getPrice()*MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())));

           /* if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                holder.wishListItemPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext())+" " + wishList.getPrice()*MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext()));
            } else if (MySharedPreferenceClass.getChoosenlanguage(context).equals("en")) {

                holder.wishListItemPrice.setText(wishList.getPrice()*MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext()) + " "+MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext()));
            } else {
                holder.wishListItemPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext())+" " + wishList.getPrice()*MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext()));
            }*/
        } else {
            holder.wishListItemPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext())+" " + Math.round(wishList.getPrice()*MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())));
        }
        if (wishList.getOld_price().equals(0)) {
            holder.wishListItemOldPrice.setVisibility(View.GONE);
        } else {
            holder.wishListItemOldPrice.setVisibility(View.VISIBLE);
            if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
                /*if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                    holder.wishListItemOldPrice.setText("SAR " + wishList.getOld_price().toString());
                } else if (MySharedPreferenceClass.getChoosenlanguage(context).equals("en")) {

                    holder.wishListItemOldPrice.setText(wishList.getOld_price().toString() + " USD");
                } else {
                    holder.wishListItemOldPrice.setText("SAR " + wishList.getOld_price().toString());
                }*/

                holder.wishListItemOldPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext())+" " + Math.round(wishList.getOld_price()*MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())));
            } else {
               // holder.wishListItemOldPrice.setText("SAR " + wishList.getOld_price().toString());
                holder.wishListItemOldPrice.setText(MySharedPreferenceClass.getSelectedCurrencyName(itemView.getContext())+" " + Math.round(wishList.getOld_price()*MySharedPreferenceClass.getSelectedCurrencyRate(itemView.getContext())));
            }
        }
        holder.removeWishListItem.bringToFront();
        holder.removeWishListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UpgradeWishListFragment) upgradeWishListFragment).removeWishlist(wishList.getItem_id());
            }
        });

        if (wishList.getDeal().equals(1)) {
            if (wishList.getInstock().booleanValue() == true) {
                Log.e("instock", wishList.getInstock().toString());
//                holder.wishlistDealText.setVisibility(View.VISIBLE);
                holder.wishlistDealText.setVisibility(View.GONE);
                holder.wishlistOutOfStockText.setVisibility(View.GONE);
                holder.addToCartButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_mapbutton));
                holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ( upgradeWishListFragment).addToCart(wishList.getItem_id(), wishList.getProduct_id(), wishList.getType(), wishList.getSku(), wishList.getName(), wishList.getOld_price(), wishList.getPrice());
                    //    if (MySharedPreferenceClass.getMyUserId(itemView.getContext()) != " ") {

                     //   }
                      //  else{
                            //((UpgradeWishListFragment) upgradeWishListFragment).upgradeaddtocartforguestuser(wishList.getItem_id(), wishList.getProduct_id(), wishList.getType(), wishList.getSku(), wishList.getName(), wishList.getOld_price(), wishList.getPrice());
                      //  }

                        }

                });
            } else {
                holder.wishlistDealText.setVisibility(View.GONE);
//                holder.wishlistOutOfStockText.setVisibility(View.VISIBLE);
                holder.wishlistOutOfStockText.setVisibility(View.GONE);
                Log.e("not instock", wishList.getInstock().toString());
                holder.addToCartButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_mapgreybutton));
               holder.addToCartButton.setEnabled(false) ;
            }
        } else {

            if (wishList.getInstock().booleanValue() == true) {
                Log.e("instock", wishList.getInstock().toString());
                holder.wishlistOutOfStockText.setVisibility(View.GONE);
                holder.wishlistDealText.setVisibility(View.GONE);
                holder.addToCartButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_mapbutton));
                holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((UpgradeWishListFragment) upgradeWishListFragment).addToCart(wishList.getItem_id(), wishList.getProduct_id(), wishList.getType(), wishList.getSku(), wishList.getName(), wishList.getOld_price(), wishList.getPrice());


                    }
                });
            } else {
                Log.e("not instock", wishList.getInstock().toString());
//                holder.wishlistOutOfStockText.setVisibility(View.VISIBLE);
                holder.wishlistOutOfStockText.setVisibility(View.GONE);
                holder.wishlistDealText.setVisibility(View.GONE);
                holder.addToCartButton.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_mapgreybutton));
            }

        }


    }

    @Override
    public int getItemCount() {
        return upgradeWishListItems.size();
    }
}
