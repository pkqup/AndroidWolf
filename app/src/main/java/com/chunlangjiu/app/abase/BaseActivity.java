package com.chunlangjiu.app.abase;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chunlangjiu.app.R;
import com.jaeger.library.StatusBarUtil;
import com.pkqup.commonlibrary.dialog.CommonLoadingDialog;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity {

    //标题容器
    public RelativeLayout titleView;
    public ImageView titleImgLeft;
    public TextView titleName;
    public ImageView titleImgRightOne;
    public ImageView titleImgRightTwo;
    public RelativeLayout titleSearchView;
    public EditText titleSearchEdit;

    public RelativeLayout contentView;//内容
    public RelativeLayout loadingView;//加载
    public RelativeLayout emptyView;//空态
    public RelativeLayout errorView;//加载失败

    public CommonLoadingDialog loadingDialog;

    private View emptyListView;
    private View errorListView;
    private TextView tvEmpty;
    private TextView tvError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.abase_activity);
        findView();
        setTitleView();
    }

    private void findView() {
        titleView = findViewById(R.id.title_view);
        titleImgLeft = findViewById(R.id.img_title_left);
        titleName = findViewById(R.id.tv_title);
        titleImgRightOne = findViewById(R.id.img_title_right_one);
        titleImgRightTwo = findViewById(R.id.img_title_right_two);
        titleSearchView = findViewById(R.id.rl_title_search);
        titleSearchEdit = findViewById(R.id.et_title_search);

        contentView = findViewById(R.id.content_view);
        loadingView = findViewById(R.id.loading_view);
        emptyView = findViewById(R.id.empty_view);
        errorView = findViewById(R.id.error_view);

        emptyListView = View.inflate(this, R.layout.abase_activity_empty_view, null);
        errorListView = View.inflate(this, R.layout.abase_activity_error_view, null);
        tvEmpty = emptyListView.findViewById(R.id.tvEmpty);
        tvError = emptyListView.findViewById(R.id.tvError);
    }

    @Override
    public void setContentView(int layoutResID) {
        addSubContentView(layoutResID);
        ButterKnife.bind(this);
        setStatusBarColor();
    }

    private void setStatusBarColor() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.bg_black),
                StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    public abstract void setTitleView();

    private void addSubContentView(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, contentView, true);
    }

    public void hideTitleView() {
        titleView.setVisibility(View.GONE);
    }


    public void showContentView() {
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    public void showLoadingView() {
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    public void showEmptyView() {
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    public void showErrorView() {
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    public void showLoadingDialog() {
        if (null == loadingDialog) {
            loadingDialog = new CommonLoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void hideLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
    }


    public void setEmptyListView(BaseQuickAdapter baseQuickAdapter, String content) {
        baseQuickAdapter.setEmptyView(emptyListView);
        tvEmpty.setText(content);
    }

    public void setErrorListView(BaseQuickAdapter baseQuickAdapter, String content) {
        baseQuickAdapter.setEmptyView(emptyListView);
        tvError.setText(content);
    }
}
