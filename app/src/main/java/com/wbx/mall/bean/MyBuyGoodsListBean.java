package com.wbx.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MyBuyGoodsListBean implements Parcelable {
    /**
     * shop_id : 721
     * shop_name : 晴天超市
     * logo : http://www.wbx365.com/attachs/2017/09/01/thumb_59a90ea571410.png
     * grade_id : 20
     * goods : [{"goods_id":60902,"title":"晴天超市_积分_商品1","photo":"http://www.wbx365.com/attachs/2019/01/02/thumb_5c2c676800712.png","current_num":1,"accumulate_free_need_num":6}]
     */

    private int shop_id;
    private String shop_name;
    private String logo;
    private int grade_id;
    private List<GoodsBean> goods;

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(int grade_id) {
        this.grade_id = grade_id;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean implements Parcelable {
        /**
         * goods_id : 60902
         * title : 晴天超市_积分_商品1
         * photo : http://www.wbx365.com/attachs/2019/01/02/thumb_5c2c676800712.png
         * current_num : 1
         * accumulate_free_need_num : 6
         */

        private int goods_id;
        private String title;
        private String photo;
        private int current_num;
        private int accumulate_free_need_num;
        private int grade_id;

        public int getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(int grade_id) {
            this.grade_id = grade_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getCurrent_num() {
            return current_num;
        }

        public void setCurrent_num(int current_num) {
            this.current_num = current_num;
        }

        public int getAccumulate_free_need_num() {
            return accumulate_free_need_num;
        }

        public void setAccumulate_free_need_num(int accumulate_free_need_num) {
            this.accumulate_free_need_num = accumulate_free_need_num;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.goods_id);
            dest.writeString(this.title);
            dest.writeString(this.photo);
            dest.writeInt(this.current_num);
            dest.writeInt(this.accumulate_free_need_num);
            dest.writeInt(this.grade_id);
        }

        public GoodsBean() {
        }

        protected GoodsBean(Parcel in) {
            this.goods_id = in.readInt();
            this.title = in.readString();
            this.photo = in.readString();
            this.current_num = in.readInt();
            this.accumulate_free_need_num = in.readInt();
            this.grade_id = in.readInt();
        }

        public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
            @Override
            public GoodsBean createFromParcel(Parcel source) {
                return new GoodsBean(source);
            }

            @Override
            public GoodsBean[] newArray(int size) {
                return new GoodsBean[size];
            }
        };
    }

    public MyBuyGoodsListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.shop_id);
        dest.writeString(this.shop_name);
        dest.writeString(this.logo);
        dest.writeTypedList(this.goods);
        dest.writeInt(this.grade_id);
    }

    protected MyBuyGoodsListBean(Parcel in) {
        this.shop_id = in.readInt();
        this.shop_name = in.readString();
        this.logo = in.readString();
        this.goods = in.createTypedArrayList(GoodsBean.CREATOR);
        this.grade_id = in.readInt();
    }

    public static final Creator<MyBuyGoodsListBean> CREATOR = new Creator<MyBuyGoodsListBean>() {
        @Override
        public MyBuyGoodsListBean createFromParcel(Parcel source) {
            return new MyBuyGoodsListBean(source);
        }

        @Override
        public MyBuyGoodsListBean[] newArray(int size) {
            return new MyBuyGoodsListBean[size];
        }
    };
}
