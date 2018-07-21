package com.chunlangjiu.app.amain.bean;

/**
 * @CreatedbBy: liucun on 2018/7/21.
 * @Describe:
 */
public class HomeBean {

    public static final int ITEM_GOODS = 1;
    public static final int ITEM_PIC = 2;

    private int itemType;

    public static int getItemGoods() {
        return ITEM_GOODS;
    }

    public static int getItemPic() {
        return ITEM_PIC;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
