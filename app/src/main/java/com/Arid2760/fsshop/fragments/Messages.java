package com.Arid2760.fsshop.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.Arid2760.fsshop.R;

public class Messages extends Fragment {
    Button messageBtn, notificationBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_messages, container, false);
        init(root);
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* PopupMenu popupMenu = new PopupMenu(getContext(), messageBtn);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getContext(), "You Clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();

//                        }

                        return true;
                    }
                });
                popupMenu.show();*/

                messageBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_custom_button));
                notificationBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_transparent_btn));
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
                transaction.replace(R.id.messageFrame, new Chats());
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_custom_button));
                messageBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_transparent_btn));
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
                transaction.replace(R.id.messageFrame, new Notifications());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return root;
    }

    void init(View v) {
        messageBtn = v.findViewById(R.id.messageBtn);
        notificationBtn = v.findViewById(R.id.notificationsBtn);

    }
}