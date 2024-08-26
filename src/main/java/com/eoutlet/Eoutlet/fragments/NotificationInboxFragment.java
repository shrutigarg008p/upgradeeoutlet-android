package com.eoutlet.Eoutlet.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.adpters.NotificationAdapter;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.moengage.addon.inbox.MoEInboxHelper;
import com.moengage.addon.inbox.model.InboxMessage;

import java.util.List;

public class NotificationInboxFragment extends Fragment {
    View view;
    private ImageView searchImage, backarrow;
    private Toolbar toolbar1;
    RecyclerView notificationRecyclerView;
    LinearLayout noNotificationLayout;
    NotificationAdapter notificationAdapter;
    String Locale;

    @SuppressLint("WrongThread")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (MySharedPreferenceClass.getChoosenlanguage(getContext()) != null) {
            if (MySharedPreferenceClass.getChoosenlanguage(getContext()).equals("ar")) {
                Locale = "ar";
                view = inflater.inflate(R.layout.fragment_notification_inbox_arabic, container, false);
            } else {
                Locale = "en";
                view = inflater.inflate(R.layout.fragment_notification_inbox, container, false);
            }
        } else {
            Locale = "ar";
            view = inflater.inflate(R.layout.fragment_notification_inbox_arabic, container, false);
        }

        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView);
        noNotificationLayout = view.findViewById(R.id.noNotificationLayout);
        toolbar1 = view.findViewById(R.id.toolbar);
        searchImage = toolbar1.findViewById(R.id.serachbar);
        backarrow = toolbar1.findViewById((R.id.backarrow));

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment prFrag = new SearchResultFragment();
                getFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .add(NotificationInboxFragment.this.getId(), prFrag)
                        .commit();
            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<InboxMessage> messageList;
        messageList = MoEInboxHelper.getInstance().fetchAllMessages(getContext());
        int unreadMessagesCount = MoEInboxHelper.getInstance().getUnClickedMessagesCount(getContext());
        Log.e("unreadMessagesCount", String.valueOf(unreadMessagesCount));
        if (messageList.size() > 0) {
            Log.e("messageListSize", String.valueOf(messageList.size()));
            notificationRecyclerView.setVisibility(View.VISIBLE);
            noNotificationLayout.setVisibility(View.GONE);
            notificationAdapter = new NotificationAdapter(getContext(), messageList, NotificationInboxFragment.this);
            notificationRecyclerView.setAdapter(notificationAdapter);
            notificationAdapter.notifyDataSetChanged();
        } else {
            notificationRecyclerView.setVisibility(View.GONE);
            noNotificationLayout.setVisibility(View.VISIBLE);
        }
        return view;

    }

}