package com.chunlangjiu.app.amain.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.pkqup.commonlibrary.view.TimerTextView;

/**
 * @CreatedbBy: liucun on 2018/6/16.
 * @Describe: 竞拍
 */
public class AuctionFragment extends BaseFragment {

    private TimerTextView timerTextView;

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.amain_fragment_auction,container,true);
    }

    @Override
    public void initView() {
        timerTextView = rootView.findViewById(R.id.timeTextView);

        timerTextView.setTime(1,55,3);
        timerTextView.start();
    }

    @Override
    public void initData() {

    }
}
