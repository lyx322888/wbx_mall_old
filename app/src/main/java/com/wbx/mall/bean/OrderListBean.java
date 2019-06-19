package com.wbx.mall.bean;

import java.util.List;

/**
 * @author Zero
 * @date 2018/9/17
 */
public class OrderListBean {

    /**
     * create_time : 1537174607
     * total_price : 117600
     * order_id : 5026
     * need_pay : 117600
     * num : 1
     * status : 0
     * is_daofu : 0
     * red_packet_money : 0
     * distance_logistics : 0
     * order_type : 1
     * over_order_time : -1537241479
     * shop_id : 1443
     * express_price : 600
     * is_fengniao : 0
     * shop_name : 果然鲜果园
     * shop_logo : http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I
     * goods : [{"id":8288,"num":1,"total_price":120000,"photo":"http://vrzff.com/attachs/2017/11/07/thumb_5a0157e45b126.jpg","shop_name":"果然鲜果园","attr_name":null,"goods_id":35382,"title":"香蕉","price":120000}]
     * casing_price : 200
     */

    private int create_time;
    private int total_price;
    private String order_id;
    private int need_pay;
    private int num;
    private int status;
    private int is_daofu;
    private int red_packet_money;
    private int distance_logistics;
    private int order_type;//1菜市场 2实体店
    private long over_order_time;
    private int shop_id;
    private int express_price;
    private int is_fengniao;
    private int is_dada;
    private int fengniao_status;
    private int dada_status;
    private String shop_name;
    private String shop_logo;
    private String tel;
    private int casing_price;
    private List<GoodsBean> goods;
    private String carrier_driver_phone;

    public int getDada_status() {
        return dada_status;
    }

    public void setDada_status(int dada_status) {
        this.dada_status = dada_status;
    }

    public int getIs_dada() {
        return is_dada;
    }

    public void setIs_dada(int is_dada) {
        this.is_dada = is_dada;
    }

    public String getCarrier_driver_phone() {
        return carrier_driver_phone;
    }

    public void setCarrier_driver_phone(String carrier_driver_phone) {
        this.carrier_driver_phone = carrier_driver_phone;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getFengniao_status() {
        return fengniao_status;
    }

    public void setFengniao_status(int fengniao_status) {
        this.fengniao_status = fengniao_status;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getNeed_pay() {
        return need_pay;
    }

    public void setNeed_pay(int need_pay) {
        this.need_pay = need_pay;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_daofu() {
        return is_daofu;
    }

    public void setIs_daofu(int is_daofu) {
        this.is_daofu = is_daofu;
    }

    public int getRed_packet_money() {
        return red_packet_money;
    }

    public void setRed_packet_money(int red_packet_money) {
        this.red_packet_money = red_packet_money;
    }

    public int getDistance_logistics() {
        return distance_logistics;
    }

    public void setDistance_logistics(int distance_logistics) {
        this.distance_logistics = distance_logistics;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public long getOver_order_time() {
        return over_order_time;
    }

    public void setOver_order_time(long over_order_time) {
        this.over_order_time = over_order_time;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getExpress_price() {
        return express_price;
    }

    public void setExpress_price(int express_price) {
        this.express_price = express_price;
    }

    public int getIs_fengniao() {
        return is_fengniao;
    }

    public void setIs_fengniao(int is_fengniao) {
        this.is_fengniao = is_fengniao;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    public int getCasing_price() {
        return casing_price;
    }

    public void setCasing_price(int casing_price) {
        this.casing_price = casing_price;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * id : 8288
         * num : 1
         * total_price : 120000
         * photo : http://vrzff.com/attachs/2017/11/07/thumb_5a0157e45b126.jpg
         * shop_name : 果然鲜果园
         * attr_name : null
         * goods_id : 35382
         * title : 香蕉
         * price : 120000
         */

        private int id;
        private int num;
        private int total_price;
        private String photo;
        private String shop_name;
        private String attr_name;
        private int goods_id;
        private String title;
        private String intro;
        private int price;
        private String nature_name;

        public String getNature_name() {
            return nature_name;
        }

        public void setNature_name(String nature_name) {
            this.nature_name = nature_name;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
            this.total_price = total_price;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}