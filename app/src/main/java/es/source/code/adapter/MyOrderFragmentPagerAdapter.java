package es.source.code.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import es.source.code.fragment.coolFoodFragment;
import es.source.code.fragment.hotFoodFragment;
import es.source.code.fragment.notOrderedFragment;
import es.source.code.fragment.orderedFragment;
import es.source.code.fragment.seaFoodFragment;
import es.source.code.fragment.wineFoodFragment;

public class MyOrderFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"未下单", "已下单"};

    public MyOrderFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new orderedFragment();
        }
         return new notOrderedFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}

