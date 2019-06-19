package com.wbx.mall.bean;

import java.util.List;

/**
 * @author Zero
 * @date 2018/6/12
 */
public class IntegralGoodsBean {

    /**
     * goods : [{"goods_id":2,"title":"盘子","face_pic":"http://www.wbx365.com/attachs/2017/08/09/thumb_598ab8e69ab04.jpg","integral":100},{"goods_id":1,"title":"杯子","face_pic":"http://www.wbx365.com/attachs/2017/08/09/thumb_598ab8981709c.jpg","integral":500}]
     * integral : 65000
     */

    private int integral;
    private List<GoodsBean> goods;

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * goods_id : 2
         * title : 盘子
         * face_pic : http://www.wbx365.com/attachs/2017/08/09/thumb_598ab8e69ab04.jpg
         * integral : 100
         */

        private int goods_id;
        private String title;
        private String face_pic;
        private int integral;

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

        public String getFace_pic() {
            return face_pic;
        }

        public void setFace_pic(String face_pic) {
            this.face_pic = face_pic;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }
    }
}
