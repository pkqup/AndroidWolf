package com.chunlangjiu.app.amain.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseApplication;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.activity.LoginActivity;
import com.chunlangjiu.app.order.activity.OrderApplyForAfterSaleActivity;
import com.chunlangjiu.app.order.activity.OrderMainActivity;
import com.chunlangjiu.app.order.params.OrderParams;
import com.chunlangjiu.app.user.activity.AddGoodsActivity;
import com.chunlangjiu.app.user.activity.AddressListActivity;
import com.chunlangjiu.app.user.activity.CompanyAuthActivity;
import com.chunlangjiu.app.user.activity.PersonAuthActivity;
import com.chunlangjiu.app.util.ConstantMsg;
import com.chunlangjiu.app.web.WebViewActivity;
import com.pkqup.commonlibrary.eventmsg.EventManager;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @CreatedbBy: liucun on 2018/6/16.
 * @Describe: 我的
 */
public class UserFragment extends BaseFragment {

    private TextView tvToLogin;
    private RelativeLayout rlHead;

    private RelativeLayout rlBackground;
    private ImageView imgSetting;

    private CircleImageView imgHead;
    private TextView tvName;
    private TextView tvAccountType;
    private TextView tvChangeType;
    private TextView tvAuthRealName;

    private LinearLayout llCanUseMoney;
    private TextView tvCanUseMoney;
    private LinearLayout llNotUseMoney;
    private TextView tvNotUseMoney;
    private LinearLayout llMessageNum;
    private TextView tvMessageNum;

    /*订单管理*/
    private RelativeLayout rlOrderManager;
    //-----------买家中心------------//
    private LinearLayout llBuyOrder;
    private RelativeLayout rlOrderOne;
    private TextView tvOrderOneNum;
    private RelativeLayout rlOrderTwo;
    private TextView tvOrderTwoNum;
    private RelativeLayout rlOrderThree;
    private TextView tvOrderThreeNum;
    private RelativeLayout rlOrderFour;
    private TextView tvOrderFourNum;
    private RelativeLayout rlOrderFive;
    private TextView tvOrderFiveNum;
    //-----------买家中心------------//

    //-----------卖家中心------------//
    private LinearLayout llSellOrder;
    private RelativeLayout rlSellOrderOne;
    private TextView tvSellOrderOneNum;
    private RelativeLayout rlSellOrderTwo;
    private TextView tvSellOrderTwoNum;
    private RelativeLayout rlSellOrderThree;
    private TextView tvSellOrderThreeNum;
    private RelativeLayout rlSellOrderFour;
    private TextView tvSellOrderFourNum;
    //-----------卖家中心------------//
    /*订单管理*/

    /*竞拍订单管理*/
    private RelativeLayout rlAuctionManager;
    private LinearLayout llBuyAuction;
    private RelativeLayout rlAuctionOne;
    private TextView tvAuctionOneNum;
    private RelativeLayout rlAuctionTwo;
    private TextView tvAuctionTwoNum;
    private RelativeLayout rlAuctionThree;
    private TextView tvAuctionThreeNum;
    private RelativeLayout rlAuctionFour;
    private TextView tvAuctionFourNum;
    private RelativeLayout rlAuctionFive;
    private TextView tvAuctionFiveNum;

    private LinearLayout llSellAuction;
    private RelativeLayout rlSellAuctionOne;
    private TextView tvSellAuctionOneNum;
    private RelativeLayout rlSellAuctionTwo;
    private TextView tvSellAuctionTwoNum;
    private RelativeLayout rlSellAuctionThree;
    private TextView tvSellAuctionThreeNum;
    private RelativeLayout rlSellAuctionFour;
    private TextView tvSellAuctionFourNum;
    /*竞拍订单管理*/

    /*商品管理*/
    private RelativeLayout rlGoodsManager;
    private LinearLayout llGoodsContent;
    private RelativeLayout rlAddGoods;
    private RelativeLayout rlSellGoods;
    private RelativeLayout rlAuctionGoods;
    private RelativeLayout rlWareHouseGoods;
    private RelativeLayout rlCheckGoods;
    /*商品管理*/

    /*我的管理*/
    private RelativeLayout rlMoneyManager;
    private RelativeLayout rlCollect;
    private RelativeLayout rlShare;
    private RelativeLayout rlVip;
    private RelativeLayout rlAddress;
    private RelativeLayout rlBankCard;
    private LinearLayout llMyManagerSecond;
    private RelativeLayout rlBankCardSecond;
    /*我的管理*/

    private static final int TYPE_BUYER = 0;//买家中心
    private static final int TYPE_SELLER = 1;//卖家中心
    private int userType = TYPE_BUYER;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!BaseApplication.isLogin()) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                return;
            }
            switch (view.getId()) {
                case R.id.tvToLogin:
                    break;
                case R.id.imgSetting:
                    break;
                case R.id.tvChangeType:// 切换买/卖家中心
                    changeUserType();
                    break;
                case R.id.tvAuthRealName:// 企业/个人认证
                    startActivity(new Intent(getActivity(), PersonAuthActivity.class));
                    startActivity(new Intent(getActivity(), CompanyAuthActivity.class));
                    break;
                case R.id.rlOrderManager:// 订单管理
                    if (llSellAuction.isShown()) {
                        toOrderMainActivity(3, 0);
                    } else {
                        toOrderMainActivity(0, 0);
                    }
                    break;
                case R.id.rlOrderOne:// 买家待付款
                    toOrderMainActivity(0, 1);
                    break;
                case R.id.rlOrderTwo:// 买家待收货
                    toOrderMainActivity(0, 3);
                    break;
                case R.id.rlOrderThree:// 买家待发货
                    toOrderMainActivity(0, 2);
                    break;
                case R.id.rlOrderFour:// 买家售后订单
                    toOrderMainActivity(2, 0);
                    break;
                case R.id.rlOrderFive:// 买家全部订单
                    toOrderMainActivity(0, 0);
                    break;
                case R.id.rlSellOrderOne:// 卖家待付款
                    toOrderMainActivity(3, 1);
                    break;
                case R.id.rlSellOrderTwo:// 卖家待发货
                    toOrderMainActivity(3, 2);
                    break;
                case R.id.rlSellOrderThree:// 卖家售后订单
                    break;
                case R.id.rlSellOrderFour:// 卖家全部订单
                    toOrderMainActivity(3, 0);
                    break;
                case R.id.rlAuctionManager:// 竞拍订单管理
                    break;
                case R.id.rlAuctionOne:// 买家竞拍订单-待付定金
                    break;
                case R.id.rlAuctionTwo:// 买家竞拍订单-竞拍中
                    break;
                case R.id.rlAuctionThree:// 买家竞拍订单-已中标
                    break;
                case R.id.rlAuctionFour:// 买家竞拍订单-落标
                    break;
                case R.id.rlAuctionFive:// 买家竞拍订单-全部订单
                    break;
                case R.id.rlSellAuctionOne:// 卖家竞拍订单-待付款
                    break;
                case R.id.rlSellAuctionTwo:// 卖家竞拍订单-待发货
                    break;
                case R.id.rlSellAuctionThree:// 卖家竞拍订单-待收货
                    break;
                case R.id.rlSellAuctionFour:// 卖家竞拍订单-全部订单
                    break;
                case R.id.rlAddGoods:// 添加商品
                    startActivity(new Intent(getActivity(), AddGoodsActivity.class));
                    break;
                case R.id.rlSellGoods:// 在售商品
                    break;
                case R.id.rlAuctionGoods:// 竞拍商品
                    break;
                case R.id.rlWareHouseGoods:// 仓库商品
                    break;
                case R.id.rlCheckGoods:// 审核商品
                    break;
                case R.id.rlMoneyManager:// 资金管理
                    break;
                case R.id.rlCollect:// 我的收藏
                    break;
                case R.id.rlVip:// 会员资料
                    break;
                case R.id.rlAddress:// 地址管理
                    startActivity(new Intent(getActivity(), AddressListActivity.class));
                    break;
                case R.id.rlBankCard:// 银行卡管理
                    break;
            }
        }
    };


    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.amain_fragment_user, container, true);
    }

    @Override
    public void initView() {
        tvToLogin = rootView.findViewById(R.id.tvToLogin);
        tvToLogin.setOnClickListener(onClickListener);
        rlHead = rootView.findViewById(R.id.rlHead);

        rlBackground = rootView.findViewById(R.id.rlBackground);
        imgSetting = rootView.findViewById(R.id.imgSetting);
        imgSetting.setOnClickListener(onClickListener);

        imgHead = rootView.findViewById(R.id.imgHead);
        tvName = rootView.findViewById(R.id.tvName);
        tvAccountType = rootView.findViewById(R.id.tvAccountType);

        tvChangeType = rootView.findViewById(R.id.tvChangeType);
        tvChangeType.setOnClickListener(onClickListener);
        tvAuthRealName = rootView.findViewById(R.id.tvAuthRealName);
        tvAuthRealName.setOnClickListener(onClickListener);

        llCanUseMoney = rootView.findViewById(R.id.llCanUseMoney);
        llCanUseMoney.setOnClickListener(onClickListener);
        tvCanUseMoney = rootView.findViewById(R.id.tvCanUseMoney);
        llNotUseMoney = rootView.findViewById(R.id.llNotUseMoney);
        llNotUseMoney.setOnClickListener(onClickListener);
        tvNotUseMoney = rootView.findViewById(R.id.tvNotUseMoney);
        llMessageNum = rootView.findViewById(R.id.llMessageNum);
        llMessageNum.setOnClickListener(onClickListener);
        tvMessageNum = rootView.findViewById(R.id.tvMessageNum);

        rlOrderManager = rootView.findViewById(R.id.rlOrderManager);
        rlOrderManager.setOnClickListener(onClickListener);
        llBuyOrder = rootView.findViewById(R.id.llBuyOrder);
        rlOrderOne = rootView.findViewById(R.id.rlOrderOne);
        rlOrderOne.setOnClickListener(onClickListener);
        tvOrderOneNum = rootView.findViewById(R.id.tvOrderOneNum);
        rlOrderTwo = rootView.findViewById(R.id.rlOrderTwo);
        rlOrderTwo.setOnClickListener(onClickListener);
        tvOrderTwoNum = rootView.findViewById(R.id.tvOrderTwoNum);
        rlOrderThree = rootView.findViewById(R.id.rlOrderThree);
        rlOrderThree.setOnClickListener(onClickListener);
        tvOrderThreeNum = rootView.findViewById(R.id.tvOrderThreeNum);
        rlOrderFour = rootView.findViewById(R.id.rlOrderFour);
        rlOrderFour.setOnClickListener(onClickListener);
        tvOrderFourNum = rootView.findViewById(R.id.tvOrderFourNum);
        rlOrderFive = rootView.findViewById(R.id.rlOrderFive);
        rlOrderFive.setOnClickListener(onClickListener);
        tvOrderFiveNum = rootView.findViewById(R.id.tvOrderFiveNum);
        llSellOrder = rootView.findViewById(R.id.llSellOrder);
        rlSellOrderOne = rootView.findViewById(R.id.rlSellOrderOne);
        rlSellOrderOne.setOnClickListener(onClickListener);
        tvSellOrderOneNum = rootView.findViewById(R.id.tvSellOrderOneNum);
        rlSellOrderTwo = rootView.findViewById(R.id.rlSellOrderTwo);
        rlSellOrderTwo.setOnClickListener(onClickListener);
        tvSellOrderTwoNum = rootView.findViewById(R.id.tvSellOrderTwoNum);
        rlSellOrderThree = rootView.findViewById(R.id.rlSellOrderThree);
        rlSellOrderThree.setOnClickListener(onClickListener);
        tvSellOrderThreeNum = rootView.findViewById(R.id.tvSellOrderThreeNum);
        rlSellOrderFour = rootView.findViewById(R.id.rlSellOrderFour);
        rlSellOrderFour.setOnClickListener(onClickListener);
        tvSellOrderFourNum = rootView.findViewById(R.id.tvSellOrderFourNum);

        rlAuctionManager = rootView.findViewById(R.id.rlAuctionManager);
        llBuyAuction = rootView.findViewById(R.id.llBuyAuction);
        rlAuctionOne = rootView.findViewById(R.id.rlAuctionOne);
        tvAuctionOneNum = rootView.findViewById(R.id.tvAuctionOneNum);
        rlAuctionTwo = rootView.findViewById(R.id.rlAuctionTwo);
        tvAuctionTwoNum = rootView.findViewById(R.id.tvAuctionTwoNum);
        rlAuctionThree = rootView.findViewById(R.id.rlAuctionThree);
        tvAuctionThreeNum = rootView.findViewById(R.id.tvAuctionThreeNum);
        rlAuctionFour = rootView.findViewById(R.id.rlAuctionFour);
        tvAuctionFourNum = rootView.findViewById(R.id.tvAuctionFourNum);
        rlAuctionFive = rootView.findViewById(R.id.rlAuctionFive);
        tvAuctionFiveNum = rootView.findViewById(R.id.tvAuctionFiveNum);
        llSellAuction = rootView.findViewById(R.id.llSellAuction);
        rlSellAuctionOne = rootView.findViewById(R.id.rlSellAuctionOne);
        tvSellAuctionOneNum = rootView.findViewById(R.id.tvSellAuctionOneNum);
        rlSellAuctionTwo = rootView.findViewById(R.id.rlSellAuctionTwo);
        tvSellAuctionTwoNum = rootView.findViewById(R.id.tvSellAuctionTwoNum);
        rlSellAuctionThree = rootView.findViewById(R.id.rlSellAuctionThree);
        tvSellAuctionThreeNum = rootView.findViewById(R.id.tvSellAuctionThreeNum);
        rlSellAuctionFour = rootView.findViewById(R.id.rlSellAuctionFour);
        tvSellAuctionFourNum = rootView.findViewById(R.id.tvSellAuctionFourNum);
        rlAuctionManager.setOnClickListener(onClickListener);
        rlAuctionOne.setOnClickListener(onClickListener);
        rlAuctionTwo.setOnClickListener(onClickListener);
        rlAuctionThree.setOnClickListener(onClickListener);
        rlAuctionFour.setOnClickListener(onClickListener);
        rlAuctionFive.setOnClickListener(onClickListener);
        rlSellAuctionOne.setOnClickListener(onClickListener);
        rlSellAuctionTwo.setOnClickListener(onClickListener);
        rlSellAuctionThree.setOnClickListener(onClickListener);
        rlSellAuctionFour.setOnClickListener(onClickListener);

        rlGoodsManager = rootView.findViewById(R.id.rlGoodsManager);
        llGoodsContent = rootView.findViewById(R.id.llGoodsContent);
        rlAddGoods = rootView.findViewById(R.id.rlAddGoods);
        rlSellGoods = rootView.findViewById(R.id.rlSellGoods);
        rlAuctionGoods = rootView.findViewById(R.id.rlAuctionGoods);
        rlWareHouseGoods = rootView.findViewById(R.id.rlWareHouseGoods);
        rlCheckGoods = rootView.findViewById(R.id.rlCheckGoods);
        rlAddGoods.setOnClickListener(onClickListener);
        rlSellGoods.setOnClickListener(onClickListener);
        rlAuctionGoods.setOnClickListener(onClickListener);
        rlWareHouseGoods.setOnClickListener(onClickListener);
        rlCheckGoods.setOnClickListener(onClickListener);

        rlMoneyManager = rootView.findViewById(R.id.rlMoneyManager);
        rlCollect = rootView.findViewById(R.id.rlCollect);
        rlShare = rootView.findViewById(R.id.rlShare);
        rlVip = rootView.findViewById(R.id.rlVip);
        rlAddress = rootView.findViewById(R.id.rlAddress);
        rlBankCard = rootView.findViewById(R.id.rlBankCard);
        rlMoneyManager.setOnClickListener(onClickListener);
        rlCollect.setOnClickListener(onClickListener);
        rlShare.setOnClickListener(onClickListener);
        rlVip.setOnClickListener(onClickListener);
        rlAddress.setOnClickListener(onClickListener);
        rlBankCard.setOnClickListener(onClickListener);

        llMyManagerSecond = rootView.findViewById(R.id.llMyManagerSecond);
        rlBankCardSecond = rootView.findViewById(R.id.rlBankCardSecond);
        rlBankCardSecond.setOnClickListener(onClickListener);

        checkLogin();
        showUserTypeView();
    }

    private void checkLogin() {
        if (BaseApplication.isLogin()) {
            tvToLogin.setVisibility(View.GONE);
            rlHead.setVisibility(View.VISIBLE);
        } else {
            tvToLogin.setVisibility(View.VISIBLE);
            rlHead.setVisibility(View.GONE);
        }
    }

    private void showUserTypeView() {
        if (userType == TYPE_BUYER) {
            //买家中心
            rlBackground.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.bg_low_black));
            tvChangeType.setText("切换到卖家中心");
            llNotUseMoney.setVisibility(View.GONE);

            llBuyOrder.setVisibility(View.VISIBLE);
            llSellOrder.setVisibility(View.GONE);
            llBuyAuction.setVisibility(View.VISIBLE);
            llSellAuction.setVisibility(View.GONE);

            rlGoodsManager.setVisibility(View.GONE);
            llGoodsContent.setVisibility(View.GONE);

            rlCollect.setVisibility(View.VISIBLE);
            rlBankCard.setVisibility(View.GONE);
            llMyManagerSecond.setVisibility(View.VISIBLE);
        } else {
            //卖家中心
            rlBackground.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.bg_red));
            tvChangeType.setText("切换到买家中心");
            llNotUseMoney.setVisibility(View.VISIBLE);

            llBuyOrder.setVisibility(View.GONE);
            llSellOrder.setVisibility(View.VISIBLE);
            llBuyAuction.setVisibility(View.GONE);
            llSellAuction.setVisibility(View.VISIBLE);

            rlGoodsManager.setVisibility(View.VISIBLE);
            llGoodsContent.setVisibility(View.VISIBLE);

            rlCollect.setVisibility(View.GONE);
            rlBankCard.setVisibility(View.VISIBLE);
            llMyManagerSecond.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        EventManager.getInstance().registerListener(onNotifyListener);
    }

    private void changeUserType() {
        userType = userType == TYPE_BUYER ? TYPE_SELLER : TYPE_BUYER;
        showUserTypeView();
    }


    private void toOrderWeb() {
        WebViewActivity.startWebViewActivity(getActivity(),
                "http://mall.chunlangjiu.com/wap/trade-list.html?token=" + BaseApplication.getToken());
    }


    private EventManager.OnNotifyListener onNotifyListener = new EventManager.OnNotifyListener() {
        @Override
        public void onNotify(Object object, String eventTag) {
            loginSuccess(eventTag);
        }
    };

    private void loginSuccess(String eventTag) {
        if (eventTag.equals(ConstantMsg.LOGIN_SUCCESS)) {
            checkLogin();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.getInstance().unRegisterListener(onNotifyListener);
    }

    private void toOrderMainActivity(int type, int target) {
        Intent intent = new Intent(getActivity(), OrderMainActivity.class);
        intent.putExtra(OrderParams.TYPE, type);
        intent.putExtra(OrderParams.TARGET, target);
        startActivity(intent);
    }
}
