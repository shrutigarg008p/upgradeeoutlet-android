package com.eoutlet.Eoutlet.adpters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.fragments.OrderListFragment;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.CustomData;
import com.eoutlet.Eoutlet.pojo.OrderListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private Context context;
    private List<OrderListItem> customDataList;
    private OrderListFragment testFragment;
    boolean isOpened=false;
    View view;
    private int expandedPosition = -1;
    private OnItemClickListener onItemClickListener;
    private boolean mExpanded;
    private RecyclerView recyclerView = null;

    public OrderListAdapter(Context context, List<OrderListItem> customDataList, OrderListFragment testFragment) {
        this.customDataList = customDataList;
        this.context = context;
        this.testFragment = testFragment;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position,Boolean value);
    }

    @NonNull
    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       view = LayoutInflater.from(context).inflate(R.layout.order_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListAdapter.ViewHolder holder, final int position) {
        holder.orderid.setText("رقم الطلب:" +" "+ customDataList.get(position).getIncrementOrderId());
        holder.totalamount.setText("اجمالي الطلب:"+" "+ customDataList.get(position).getTotalAmount()*MySharedPreferenceClass.getSelectedCurrencyRate(view.getContext())+" "+ MySharedPreferenceClass.getSelectedCurrencyName(view.getContext()));
        holder.orderstatus.setText("حالة الطلب:"+" "+ customDataList.get(position).getStatus());
        if (customDataList.get(position).getStatus().equalsIgnoreCase("canceled")){
            holder.textcancel.setVisibility(View.GONE);
        }
        else {
            holder.textcancel.setVisibility(View.VISIBLE);
        }




        final boolean isExpanded = position==expandedPosition;
        holder.layoutChild.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);

        holder.textcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCancelDialogue(customDataList.get(position).getOrderId(),position);

            }
        });



        holder.textreorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OrderListFragment)testFragment).doReorder(customDataList.get(position).getOrderId(),position);

            }
        });


        holder.textPreview.setOnClickListener(new View.OnClickListener() {

            ImageView childitemimage;
            TextView childorderId,childtotalamount,childsize,childcolor,childqty,childorderName,previeworderId;
            @Override
            public void onClick(View v) {




                expandedPosition = isExpanded ? -1:position;
                TransitionManager.beginDelayedTransition(recyclerView);
                notifyDataSetChanged();
                holder.layoutChild.removeAllViews();
                for (int i = 0; i < customDataList.get(position).getItems().size(); i++) {
                    View view = testFragment.getLayoutInflater().inflate(R.layout.child_order_list, null);
                    childorderId = view.findViewById(R.id.childorderId);
                    childtotalamount = view.findViewById(R.id.childtotalamount);
                    childsize = view.findViewById(R.id.childsize);
                    childcolor = view.findViewById(R.id.childcolor);
                    childqty = view.findViewById(R.id.childqty);
                    childitemimage = view.findViewById(R.id.childitemimage);
                    childorderName = view.findViewById(R.id.childorderName);
                    previeworderId  =view.findViewById(R.id.previeworderid);
                    childorderName.setText(customDataList.get(position).getItems().get(i).getName());
                    childorderId.setText("رقم الأمر:" +" "+ customDataList.get(position).getItems().get(i).getSku());
                    childtotalamount.setText("قيمة الطلب:"+" "+ customDataList.get(position).getItems().get(i).getPrice()+" " +MySharedPreferenceClass.getSelectedCurrencyName(view.getContext()));



                    if(customDataList.get(position).getItems().get(i).getSize()!="") {
                        //childsize.setText("|"+" "+"المقاس:" + " " + customDataList.get(position).getItems().get(i).getSize());
                        childsize.setText("المقاس:" + " " + customDataList.get(position).getItems().get(i).getSize()+" "+" |");
                    }
                    else{
                        childsize.setVisibility(View.GONE);

                    }
                    if(customDataList.get(position).getItems().get(i).getColor()!="") {
                       // childcolor.setText("|"+" "+"اللون:" + " " + customDataList.get(position).getItems().get(i).getColor());
                        childcolor.setText("اللون:" + " " + customDataList.get(position).getItems().get(i).getColor()+" "+ "|"+" ");
                    }
                    else{
                        childcolor.setVisibility(View.GONE);

                    }


                        childqty.setText("الكمية:" + " " + customDataList.get(position).getItems().get(i).getOrderedQty());


                    if(i==0) {
                        previeworderId.setText("رقم الأمر:" + " " + customDataList.get(position).getOrderId());
                    }



                    else
                    {
                        previeworderId.setVisibility(View.GONE);
                    }


                    Picasso.get().load(customDataList.get(position).getItems().get(i).getImageUrl()).into(childitemimage);



                    holder.layoutChild.addView(view);
                }



//                notifyItemChanged(position);
//                n


            }
        });



    }

    private void openCancelDialogue(final String orderId, final int position) {




            final AlertDialog alertDialog;
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

            alertDialogBuilder.setMessage("هل أنت متأكد من الغاء الطلب");


            alertDialogBuilder.setPositiveButton("نعم",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {


                            ((OrderListFragment)testFragment).callCancelOrderApi(orderId,position);

                        }
                    });

            alertDialogBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
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
        return customDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutChild;
        TextView textPreview,orderid,totalamount,orderstatus,textcancel,textreorder;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutChild = itemView.findViewById(R.id.layoutChild);
            textPreview = itemView.findViewById(R.id.textPreview);
            orderid = itemView.findViewById(R.id.orderid);
            totalamount = itemView.findViewById(R.id.totalamount);
            orderstatus = itemView.findViewById(R.id.orderstatus);
            textcancel = itemView.findViewById(R.id.textcancel);
            textreorder = itemView.findViewById(R.id.textreorder);






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
