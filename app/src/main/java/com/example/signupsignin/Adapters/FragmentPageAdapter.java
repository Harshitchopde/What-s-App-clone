package com.example.signupsignin.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.signupsignin.Fragment.MainActivityFragment;

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
        }
        return null;
    }

    @Override
    public
    int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public
    CharSequence getPageTitle(int position) {
        if (position==0){
            return "CHATS";
        }
        return "NONE";
    }
}
