package com.eoutlet.Eoutlet.fragments;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.fragments.UpgradeReturnOrderFragment;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.pojo.UpgradeReturnResponseFormOrderItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpgradeReturnOrderAdapter extends RecyclerView.Adapter<UpgradeReturnOrderAdapter.ViewHolder> {
    private Context context;
    private List<UpgradeReturnResponseFormOrderItems> upgradeReturnResponseFormOrderItems = new ArrayList<>();
    Boolean isDetailExpanded = false;
    private List<String> returnReasonsList = new ArrayList<>();
    private List<String> itemConditionList = new ArrayList<>();
    private List<String> resolutionList = new ArrayList<>();
    private UpgradeReturnOrderFragment upgradeReturnOrderFragment;
    View view;
    String Locale;

    public UpgradeReturnOrderAdapter(Context context, List<UpgradeReturnResponseFormOrderItems> upgradeReturnResponseFormOrderItems, UpgradeReturnOrderFragment upgradeReturnOrderFragment) {
        this.context = context;
        this.upgradeReturnResponseFormOrderItems = upgradeReturnResponseFormOrderItems;
        this.upgradeReturnOrderFragment = upgradeReturnOrderFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (MySharedPreferenceClass.getChoosenlanguage(context) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(context).equals("ar")) {
                Locale = "ar";
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upgrade_return_order_items_arabic, null);
            } else {
                Locale = "en";
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upgrade_return_order_items, null);
            }
        } else {
            Locale = "ar";
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upgrade_return_order_items_arabic, null);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UpgradeReturnResponseFormOrderItems returnResponseFormOrderItems = upgradeReturnResponseFormOrderItems.get(position);
        Picasso.get().load(returnResponseFormOrderItems.getImage()).into(holder.productImage);
        holder.productName.setText(returnResponseFormOrderItems.getName());
        holder.productSku.setText(Locale == "ar" ? returnResponseFormOrderItems.getSku() + "وحدة حفظ المخزون (ايس كي يو): " : "SKU: " + returnResponseFormOrderItems.getSku());
        if (returnResponseFormOrderItems.getOptions() != null) {
            holder.productDetailsBox.setVisibility(View.VISIBLE);
            holder.productMoreDetailRecyclerView.setVisibility(View.GONE);
            holder.productDetailsBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isDetailExpanded == false) {
                        isDetailExpanded = true;
                        holder.productDetailsIcon.setImageResource(R.drawable.ic_up_arrow);
                        holder.productMoreDetailRecyclerView.setVisibility(View.VISIBLE);
                        UpgradeReturnOrderMoreDetailsAdapter upgradeReturnOrderMoreDetailsAdapter = new UpgradeReturnOrderMoreDetailsAdapter(returnResponseFormOrderItems.getOptions(), context);
                        holder.productMoreDetailRecyclerView.setHasFixedSize(true);
                        holder.productMoreDetailRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        holder.productMoreDetailRecyclerView.setAdapter(upgradeReturnOrderMoreDetailsAdapter);
                    } else {
                        holder.productDetailsIcon.setImageResource(R.drawable.ic_down_arrow);
                        holder.productMoreDetailRecyclerView.setVisibility(View.GONE);
                        isDetailExpanded = false;
                    }
                }
            });
        } else {
            holder.productDetailsBox.setVisibility(View.GONE);
            holder.productMoreDetailRecyclerView.setVisibility(View.GONE);
        }

        if (returnResponseFormOrderItems.getItemstatus().equals(1)) {
            holder.productCheckbox.setVisibility(View.VISIBLE);
            // Add listeners to your checkboxes to tell them to update the text view when they are clicked
            holder.productCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        holder.returnReasonDropDownBox.setVisibility(View.VISIBLE);
                        HashMap<Object, Object> itemDetails = new HashMap<Object, Object>();
                        itemDetails.put("active", returnResponseFormOrderItems.getItemstatus().toString());
                        itemDetails.put("order_item_id", returnResponseFormOrderItems.getItem_id());
                        itemDetails.put("qty_requested", returnResponseFormOrderItems.getOrdered_qty().toString());
//                        ((MainActivity) context).items.add(returnResponseFormOrderItems);


                        returnReasonsList.clear();
                        itemConditionList.clear();
                        resolutionList.clear();
                        returnReasonsList.add(Locale == "ar" ? "يرجى الاختيار" : "Please Choose");
                        itemConditionList.add(Locale == "ar" ? "يرجى الاختيار" : "Please Choose");
                        resolutionList.add(Locale == "ar" ? "يرجى الاختيار" : "Please Choose");
                        for (int i = 0; i < returnResponseFormOrderItems.getReturnreason().size(); i++) {
                            returnReasonsList.add(returnResponseFormOrderItems.getReturnreason().get(i).getLabel());
                        }
                        Log.e("returnReasonList", String.valueOf(returnReasonsList.size()));
                        initReturnReasonsSpinner(holder, position, itemDetails);
                        for (int i = 0; i < returnResponseFormOrderItems.getItemcondition().size(); i++) {
                            itemConditionList.add(returnResponseFormOrderItems.getItemcondition().get(i).getLabel());
                        }
                        initItemConditionSpinner(holder, position, itemDetails);
                        for (int i = 0; i < returnResponseFormOrderItems.getRmaresolution().size(); i++) {
                            resolutionList.add(returnResponseFormOrderItems.getRmaresolution().get(i).getLabel());
                        }
                        initResolutionSpinner(holder, position, itemDetails);
                        if (holder.returnReasonSpinner.getSelectedItem() == null) {
                            itemDetails.put("reason_id", "");
                        }
                        if (holder.itemConditionSpinner.getSelectedItem() == null) {
                            itemDetails.put("condition_id", "");
                        }
                        if (holder.resolutionSpinner.getSelectedItem() == null) {
                            itemDetails.put("resolution_id", "");
                        }

                        ((MainActivity) context).requestItems.add(itemDetails);


                    } else {
                        holder.returnReasonDropDownBox.setVisibility(View.GONE);
                        if (((MainActivity) context).requestItems.size() > 0) {
                            for (int i = 0; i < ((MainActivity) context).requestItems.size(); i++) {
                                if (((MainActivity) context).requestItems.get(i).get("order_item_id").equals(returnResponseFormOrderItems.getItem_id())) {
                                    ((MainActivity) context).requestItems.remove(i);
                                }
                            }
                        } else {
                            Log.e("size", "zero");
                        }
                    }

                }
            });

        } else {
            holder.productCheckbox.setVisibility(View.INVISIBLE);
        }
    }

    private void initReturnReasonsSpinner(ViewHolder holder, int Position, HashMap<Object, Object> itemDetails) {
        ArrayAdapter returnReasonAdapter
                = new ArrayAdapter(
                context,
                Locale == "ar" ? R.layout.return_order_spinner_text_view_arabic : R.layout.return_order_spinner_text_view,
                returnReasonsList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.parseColor("#D8A664"));
                    tv.setAllCaps(true);
                } else {
                    tv.setTextColor(Color.WHITE);
                }
                return view;
            }
        };
        returnReasonAdapter.setDropDownViewResource(Locale == "ar" ? R.layout
                .return_order_spinner_item_view_arabic :
                R.layout
                        .return_order_spinner_item_view);
        holder.returnReasonSpinner.setAdapter(returnReasonAdapter);

        holder.returnReasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
//                    ((MainActivity) context).items.get(Position).setSelectedPosition(String.valueOf(position));
                }
                itemDetails.put("reason_id", position > 0 ? String.valueOf(position) : "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("onNothingSelected", "onNothingSelected");

            }
        });
    }

    private void initItemConditionSpinner(ViewHolder holder, int Position, HashMap<Object, Object> itemDetails) {
        ArrayAdapter itemConditionAdapter
                = new ArrayAdapter(
                context,
                Locale == "ar" ? R.layout.return_order_spinner_text_view_arabic : R.layout.return_order_spinner_text_view,
                itemConditionList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.parseColor("#D8A664"));
                    tv.setAllCaps(true);
                } else {
                    tv.setTextColor(Color.WHITE);
                }
                return view;
            }
        };
        itemConditionAdapter.setDropDownViewResource(
                Locale == "ar" ? R.layout
                        .return_order_spinner_item_view_arabic :
                        R.layout
                                .return_order_spinner_item_view);
        holder.itemConditionSpinner.setAdapter(itemConditionAdapter);

        holder.itemConditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
//                    ((MainActivity) context).items.get(Position).setItemConditionPosition(String.valueOf(position));
                }
                itemDetails.put("condition_id", position > 0 ? String.valueOf(position) : "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initResolutionSpinner(ViewHolder holder, int Position, HashMap<Object, Object> itemDetails) {
        ArrayAdapter resolutionAdapter
                = new ArrayAdapter(
                context,
                Locale == "ar" ? R.layout.return_order_spinner_text_view_arabic : R.layout.return_order_spinner_text_view,
                resolutionList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.parseColor("#D8A664"));
                    tv.setAllCaps(true);

                } else {
                    tv.setTextColor(Color.WHITE);
                }
                return view;
            }
        };
        resolutionAdapter.setDropDownViewResource(
                Locale == "ar" ? R.layout
                        .return_order_spinner_item_view_arabic :
                        R.layout
                                .return_order_spinner_item_view);
        holder.resolutionSpinner.setAdapter(resolutionAdapter);

        holder.resolutionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
//                    ((MainActivity) context).items.get(Position).setResolutionPosition(String.valueOf(position));
                }
                itemDetails.put("resolution_id", position > 0 ? String.valueOf(position) : "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return upgradeReturnResponseFormOrderItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox productCheckbox;
        ImageView productImage, productDetailsIcon;
        LinearLayout  productDetailsBox, returnReasonDropDownBox;
        TextView productName, productSku, productDetails;
        Spinner returnReasonSpinner, itemConditionSpinner, resolutionSpinner;
        RecyclerView productMoreDetailRecyclerView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productCheckbox = itemView.findViewById(R.id.productCheckbox);
            productImage = itemView.findViewById(R.id.productImage);
            productDetailsIcon = itemView.findViewById(R.id.productDetailsIcon);
            productDetailsBox = itemView.findViewById(R.id.productDetailsBox);
            returnReasonDropDownBox = itemView.findViewById(R.id.returnReasonDropDownBox);
            productName = itemView.findViewById(R.id.productName);
            productSku = itemView.findViewById(R.id.productSku);
            productDetails = itemView.findViewById(R.id.productDetails);
            returnReasonSpinner = itemView.findViewById(R.id.returnReasonSpinner);
            itemConditionSpinner = itemView.findViewById(R.id.itemConditionSpinner);
            resolutionSpinner = itemView.findViewById(R.id.resolutionSpinner);
            productMoreDetailRecyclerView = itemView.findViewById(R.id.productMoreDetailRecyclerView);

        }
    }
}
