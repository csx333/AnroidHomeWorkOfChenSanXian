package es.source.code.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import es.source.code.application.MessageOfApplication;
import es.source.code.fragment.FoodFragment;

public class FoodViewFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"冷菜", "热菜", "海鲜","酒水"};

    public FoodViewFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return FoodFragment.newInstance(MessageOfApplication.HOTFOOD);
        } else if (position == 2) {
            return FoodFragment.newInstance(MessageOfApplication.SEAFOOD);
        }else if (position==3){
            return FoodFragment.newInstance(MessageOfApplication.WINEFOOD);
        }
         return FoodFragment.newInstance(MessageOfApplication.COOLFOOD);
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

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

}

