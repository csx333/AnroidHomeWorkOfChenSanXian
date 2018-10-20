package es.source.code.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import es.source.code.fragment.NotOrderedFragment;
import es.source.code.fragment.OrderedFragment;

public class MyOrderFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"未下单", "已下单"};

    public MyOrderFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new OrderedFragment();
        }
         return new NotOrderedFragment();
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

