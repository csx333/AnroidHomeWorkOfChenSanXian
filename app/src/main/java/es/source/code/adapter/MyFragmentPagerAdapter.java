package es.source.code.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import es.source.code.fragment.coolFoodFragment;
import es.source.code.fragment.hotFoodFragment;
import es.source.code.fragment.seaFoodFragment;
import es.source.code.fragment.wineFoodFragment;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"冷菜", "热菜", "海鲜","酒水"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new hotFoodFragment();
        } else if (position == 2) {
            return new seaFoodFragment();
        }else if (position==3){
            return new wineFoodFragment();
        }
         return new coolFoodFragment();
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

