package com.fkgpby0329.yxb.fragment;


import android.app.Fragment;
import android.os.Bundle;

import com.fkgpby0329.yxb.back.BackHandledInterface;


/**
 * Created by tgw on 2017/8/8.
 */

public abstract class BackHandledFragment extends Fragment {
    protected BackHandledInterface mBackHandledInterface;

    public abstract boolean onBackPressed();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandledInterface)) {
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        } else {
            this.mBackHandledInterface = (BackHandledInterface) getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mBackHandledInterface.setSelectedFragment(this);
    }


}
