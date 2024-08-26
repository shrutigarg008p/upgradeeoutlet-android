package com.eoutlet.Eoutlet.adpters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.NewOrderListFragment;
import com.eoutlet.Eoutlet.fragments.UpgradeReturnOrderFragment;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradeOrderListData;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NewOrderListAdapter extends RecyclerView.Adapter<NewOrderListAdapter.ViewHolder> {

    private Context context;
    private List<UpgradeOrderListData> upgradeOrderListData;
    private NewOrderListFragment testFragment;
    private View view;
    private View childView;
    boolean isOpened = false;
    private int expandedPosition = -1;
    private OnItemClickListener onItemClickListener;
    private boolean mExpanded;
    private RecyclerView recyclerView = null;
    String Locale;

    public NewOrderListAdapter(Context context, List<UpgradeOrderListData> upgradeOrderListData, NewOrderListFragment testFragment) {
        this.upgradeOrderListData = upgradeOrderListData;
        this.context = context;
        this.testFragment = testFragment;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Boolean value);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                view = LayoutInflater.from(context).inflate(R.layout.new_order_list_item_arabic, parent, false);
                Locale = "ar";
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.new_order_list_item, parent, false);
                Locale = "en";
            }
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.new_order_list_item_arabic, parent, false);
            Locale = "ar";
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.orderid.setText(Locale.equals("ar") ? "بطاقة هوية للطلب: " + " " + upgradeOrderListData.get(position).getIncrement_id() : "Order ID: " + upgradeOrderListData.get(position).getIncrement_id());
        holder.totalamount.setText(Locale.equals("ar") ? upgradeOrderListData.get(position).getCurrency() + " " + Math.round (upgradeOrderListData.get(position).getGrand_total()) + " " : Math.round (upgradeOrderListData.get(position).getGrand_total() ) +" "+ upgradeOrderListData.get(position).getCurrency());

        //holder.totalamount.setText(upgradeOrderListData.get(position).getGrand_total()+" "+MySharedPreferenceClass.getSelectedCurrencyName(view.getContext()));

        if (upgradeOrderListData.get(position).getStatus() != null) {
            holder.orderstatus.setText(upgradeOrderListData.get(position).getStatus());
        } else {
            holder.orderstatus.setText("");
        }

        holder.date.setText(upgradeOrderListData.get(position).getCreated_at());

        if (upgradeOrderListData.get(position).getStatus_code() != null) {
            if (upgradeOrderListData.get(position).getStatus_code().equalsIgnoreCase("pending") || upgradeOrderListData.get(position).getStatus_code().equalsIgnoreCase("processing")) {
                holder.orderstatus.setTextColor(context.getResources().getColor(R.color.pending));
                holder.textcancel.setVisibility(View.VISIBLE);
            } else if (upgradeOrderListData.get(position).getStatus_code().equalsIgnoreCase("complete")) {
                holder.orderstatus.setTextColor(context.getResources().getColor(R.color.success));
                holder.textcancel.setVisibility(View.GONE);
            } else {
                holder.orderstatus.setTextColor(context.getResources().getColor(R.color.canceled));
                holder.textcancel.setVisibility(View.GONE);
            }
        } else {
            holder.orderstatus.setTextColor(context.getResources().getColor(R.color.pending));
        }
        if (upgradeOrderListData.get(position).getCanreturn().equals(1)) {
            holder.textReturn.setVisibility(View.VISIBLE);
        } else {
            holder.textReturn.setVisibility(View.GONE);
        }


        final boolean isExpanded = position == expandedPosition;
        holder.layoutChild.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.arrow_imageview.setRotation(isExpanded ? 270 : 90);
        holder.itemView.setActivated(isExpanded);

        holder.textcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCancelDialogue(upgradeOrderListData.get(position).getOrder_id(), position);
            }
        });


        holder.textreorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NewOrderListFragment) testFragment).doReorder(upgradeOrderListData.get(position).getIncrement_id(), position);
            }
        });
        holder.textReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment upgradeReturnOrderFragment = new UpgradeReturnOrderFragment();
                Bundle bundle = new Bundle();
                bundle.putString("order_id", upgradeOrderListData.get(position).getOrder_id());
                upgradeReturnOrderFragment.setArguments(bundle);
                FragmentTransaction mFragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.profileContainer, upgradeReturnOrderFragment).addToBackStack(null).commit();
            }
        });


        holder.layoutChild.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.mainHeaderView.setOnClickListener(new View.OnClickListener() {

            ImageView childitemimage;
            private RelativeLayout mainHeaderView;
            TextView childorderId, childtotalamount, childsize, childcolor, childqty, childorderName, previeworderId;

            @Override
            public void onClick(View v) {
                expandedPosition = isExpanded ? -1 : position;
                TransitionManager.beginDelayedTransition(recyclerView);
                notifyDataSetChanged();
                holder.layoutChild.removeAllViews();
                for (int i = 0; i < upgradeOrderListData.get(position).getProducts().size(); i++) {
                    if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
                        if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                            childView = testFragment.getLayoutInflater().inflate(R.layout.child_order_list_arabic, null);
                        } else {
                            childView = testFragment.getLayoutInflater().inflate(R.layout.child_order_list, null);
                        }
                    } else {
                            childView = testFragment.getLayoutInflater().inflate(R.layout.child_order_list_arabic, null);
                    }
                    childorderId = childView.findViewById(R.id.childorderId);
                    childtotalamount = childView.findViewById(R.id.childtotalamount);
                    childsize = childView.findViewById(R.id.childsize);
                    childcolor = childView.findViewById(R.id.childcolor);
                    childqty = childView.findViewById(R.id.childqty);
                    childitemimage = childView.findViewById(R.id.childitemimage);
                    childorderName = childView.findViewById(R.id.childorderName);
                    previeworderId = childView.findViewById(R.id.previeworderid);
                    childorderName.setText(upgradeOrderListData.get(position).getProducts().get(i).getName());
                    childorderId.setText("رقم الأمر:" + " " + upgradeOrderListData.get(position).getProducts().get(i).getSku());
                    childtotalamount.setText(Locale.equals("ar") ?  Math.round((upgradeOrderListData.get(position).getProducts().get(i).getPrice()*MySharedPreferenceClass.getSelectedCurrencyRate(view.getContext())))+ MySharedPreferenceClass.getSelectedCurrencyName(view.getContext()) +" "+"قيمة الطلب:" +" ": "Order value: " + Math.round(upgradeOrderListData.get(position).getProducts().get(i).getPrice()*MySharedPreferenceClass.getSelectedCurrencyRate(view.getContext())) +" "+ MySharedPreferenceClass.getSelectedCurrencyName(view.getContext()));


                    if ((upgradeOrderListData.get(position).getProducts().get(i).getSize().toString().equals("false") || upgradeOrderListData.get(position).getProducts().get(i).getSize() == null)) {
                        childsize.setVisibility(View.GONE);
                    } else {
                        childsize.setText(Locale.equals("ar") ? "المقاس:"
                                + " " + upgradeOrderListData.get(position).getProducts().get(i).getSize() +  " " + "|" + " " :
                                "Size: " + upgradeOrderListData.get(position).getProducts().get(i).getSize() + " " + " |  ");
                    }

                    if (upgradeOrderListData.get(position).getProducts().get(i).getColor().toString().equals("false") || upgradeOrderListData.get(position).getProducts().get(i).getColor() == null) {
                        childcolor.setVisibility(View.GONE);
                    } else {
                        childcolor.setText(Locale.equals("ar") ? "اللون:" + " " +
                                upgradeOrderListData.get(position).getProducts().get(i).getColor() + " " + "|" + " " : "Color: " + upgradeOrderListData.get(position).getProducts().get(i).getColor() + " " + " |");
                    }

                    childqty.setText(Locale.equals("ar") ? "الكمية:" + " " + upgradeOrderListData.get(position).getProducts().get(i).getQuantity() :
                            "Quantity: " + upgradeOrderListData.get(position).getProducts().get(i).getQuantity());
                    if (i == 0) {
                        previeworderId.setText("رقم الأمر:" + " " + upgradeOrderListData.get(position).getOrder_id());
                    } else {
                        previeworderId.setVisibility(View.GONE);
                    }

                    if (upgradeOrderListData.get(position).getProducts().get(i).getImage() != "") {
                        Picasso.get().load(upgradeOrderListData.get(position).getProducts().get(i).getImage()).into(childitemimage);
                    } else {
                        Picasso.get().load("https://upgrade.eoutlet.com/pub/media/catalog/product/imp/ort/5-_-5_1__4.png").into(childitemimage);
                    }
                    holder.layoutChild.addView(childView);
                }
            }
        });
    }

    private void openCancelDialogue(final String orderId, final int position) {
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialogBuilder.setMessage(Locale == "ar" ? "هل أنت متأكد من الغاء الطلب" : "Are you sure to cancel the request?");
        alertDialogBuilder.setPositiveButton(Locale == "ar" ? "نعم" : "YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        ((NewOrderListFragment) testFragment).callCancelOrderApi(orderId, position);
                        ;
                      /*  final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                       ((NewOrderListFragment) testFragment).callCancelOrderApi(orderId, position);
                            }
                        }, 5000);

*/

                    }
                });

        alertDialogBuilder.setNegativeButton(Locale == "ar" ? "لا" : "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //alertDialog.dismiss();
            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    @Override
    public int getItemCount() {
        return upgradeOrderListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutChild;
        TextView textPreview, orderid, totalamount, orderstatus, textcancel, textreorder, textReturn, date;
        ImageView arrow_imageview;
        private RelativeLayout mainHeaderView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutChild = itemView.findViewById(R.id.layoutChild);
        /*layoutChild = itemView.findViewById(R.id.layoutChild);
        textPreview = itemView.findViewById(R.id.textPreview);*/
            orderid = itemView.findViewById(R.id.orderid);
            totalamount = itemView.findViewById(R.id.totalamount);
            orderstatus = itemView.findViewById(R.id.orderstatus);
            textcancel = itemView.findViewById(R.id.textcancel);
            textreorder = itemView.findViewById(R.id.textreorder);
            textReturn = itemView.findViewById(R.id.textReturn);
            arrow_imageview = itemView.findViewById(R.id.arrow_imageview);
            date = itemView.findViewById(R.id.date);
            mainHeaderView = itemView.findViewById(R.id.mainHeaderView);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}


