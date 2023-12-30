package com.example.signupsignin.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.signupsignin.Fragment.MainActivityFragment;
import com.example.signupsignin.Fragment.StatusFragment;

public
class FragmentPageAdapter extends FragmentPagerAdapter {
    public
    FragmentPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public
    Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MainActivityFragment();
            case 1:
                return new StatusFragment();
        }
        return null;
    }

    @Override
    public
    int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public
    CharSequence getPageTitle(int position) {
        if (position==0){
            return "CHATS";
        }
        else  if (position==1){
            return "STATUS";
        }
        return "NONE";
    }
}
