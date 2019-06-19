package com.wbx.mall.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Zero
 * @date 2018/9/19
 */
public class OrderDetailBean {

    /**
     * order_id : 5088
     * need_pay : 117600
     * num : 1
     * status : 0
     * create_time : 1537259259
     * message :
     * full_money_reduce : 3000
     * coupon_money : 0
     * user_subsidy_money : 0
     * red_packet_money : 0
     * distance_logistics : 0
     * over_order_time : 0
     * shop_name : 果然鲜果园
     * shop_id : 1443
     * shop_logo : http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I
     * discounts_all_money : 3000
     * is_fengniao : 0
     * address : {"id":904,"default":0,"xm":"阿狸","tel":"18311111111","area_str":"福建省厦门市思明区","info":"环岛干道与环岛干道出口交叉口西50米信息消费示范公寓"}
     * goods : [{"id":8379,"num":1,"total_price":120000,"photo":"http://vrzff.com/attachs/2017/11/07/thumb_5a0157e45b126.jpg","shop_name":"果然鲜果园","attr_name":"","goods_id":35382,"title":"香蕉","mall_price":120000}]
     * express_price : 600
     * address_id : 904
     */

    private String order_id;
    private int order_type;
    private int need_pay;
    private int num;
    private int status;
    private int create_time;
    private String message;
    private int full_money_reduce;
    private int coupon_money;
    private int user_subsidy_money;
    private int red_packet_money;
    private int casing_price;
    private int distance_logistics;
    private int over_order_time;
    private String shop_name;
    private String shop_id;
    private String shop_logo;
    private int discounts_all_money;
    private int is_fengniao;
    private int is_dada;
    private AddressBean address;
    private int express_price;
    private int address_id;
    private String pay_type;
    private String hx_username;
    private String tel;
    private String expected_delivery_time;
    private double lat;
    private double lng;
    private List<FengNiaoBean> fengniao;
    private DaDa dada;
    private List<GoodsBean> goods;
    private String carrier_driver_phone;
    private String carrier_driver_name;
    private String dm_name;
    private String dm_mobile;

    public String getDm_name() {
        return dm_name;
    }

    public void setDm_name(String dm_name) {
        this.dm_name = dm_name;
    }

    public String getDm_mobile() {
        return dm_mobile;
    }

    public void setDm_mobile(String dm_mobile) {
        this.dm_mobile = dm_mobile;
    }

    public DaDa getDada() {
        return dada;
    }

    public void setDada(DaDa dada) {
        this.dada = dada;
    }

    public int getIs_dada() {
        return is_dada;
    }

    public void setIs_dada(int is_dada) {
        this.is_dada = is_dada;
    }

    public String getCarrier_driver_name() {
        return carrier_driver_name;
    }

    public void setCarrier_driver_name(String carrier_driver_name) {
        this.carrier_driver_name = carrier_driver_name;
    }

    public String getCarrier_driver_phone() {
        return carrier_driver_phone;
    }

    public void setCarrier_driver_phone(String carrier_driver_phone) {
        this.carrier_driver_phone = carrier_driver_phone;
    }

    public String getExpected_delivery_time() {
        return expected_delivery_time;
    }

    public void setExpected_delivery_time(String expected_delivery_time) {
        this.expected_delivery_time = expected_delivery_time;
    }

    public List<FengNiaoBean> getFengniao() {
        return fengniao;
    }

    public void setFengniao(List<FengNiaoBean> fengniao) {
        this.fengniao = fengniao;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHx_username() {
        return hx_username;
    }

    public void setHx_username(String hx_username) {
        this.hx_username = hx_username;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public int getCasing_price() {
        return casing_price;
    }

    public void setCasing_price(int casing_price) {
        this.casing_price = casing_price;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
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

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFull_money_reduce() {
        return full_money_reduce;
    }

    public void setFull_money_reduce(int full_money_reduce) {
        this.full_money_reduce = full_money_reduce;
    }

    public int getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(int coupon_money) {
        this.coupon_money = coupon_money;
    }

    public int getUser_subsidy_money() {
        return user_subsidy_money;
    }

    public void setUser_subsidy_money(int user_subsidy_money) {
        this.user_subsidy_money = user_subsidy_money;
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

    public int getOver_order_time() {
        return over_order_time;
    }

    public void setOver_order_time(int over_order_time) {
        this.over_order_time = over_order_time;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    public int getDiscounts_all_money() {
        return discounts_all_money;
    }

    public void setDiscounts_all_money(int discounts_all_money) {
        this.discounts_all_money = discounts_all_money;
    }

    public int getIs_fengniao() {
        return is_fengniao;
    }

    public void setIs_fengniao(int is_fengniao) {
        this.is_fengniao = is_fengniao;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public int getExpress_price() {
        return express_price;
    }

    public void setExpress_price(int express_price) {
        this.express_price = express_price;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class AddressBean {
        /**
         * id : 904
         * default : 0
         * xm : 阿狸
         * tel : 18311111111
         * area_str : 福建省厦门市思明区
         * info : 环岛干道与环岛干道出口交叉口西50米信息消费示范公寓
         */

        private int id;
        @SerializedName("default")
        private int defaultX;
        private String xm;
        private String tel;
        private String area_str;
        private String info;
        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(int defaultX) {
            this.defaultX = defaultX;
        }

        public String getXm() {
            return xm;
        }

        public void setXm(String xm) {
            this.xm = xm;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getArea_str() {
            return area_str;
        }

        public void setArea_str(String area_str) {
            this.area_str = area_str;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    public static class GoodsBean {
        /**
         * id : 8379
         * num : 1
         * total_price : 120000
         * photo : http://vrzff.com/attachs/2017/11/07/thumb_5a0157e45b126.jpg
         * shop_name : 果然鲜果园
         * attr_name :
         * goods_id : 35382
         * title : 香蕉
         * mall_price : 120000
         */

        private int id;
        private int num;
        private int total_price;
        private String photo;
        private String shop_name;
        private String attr_name;
        private int goods_id;
        private String title;
        private int mall_price;
        private String nature_name;

        public String getNature_name() {
            return nature_name;
        }

        public void setNature_name(String nature_name) {
            this.nature_name = nature_name;
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

        public int getMall_price() {
            return mall_price;
        }

        public void setMall_price(int mall_price) {
            this.mall_price = mall_price;
        }
    }

    public static class FengNiaoBean {

        /**
         * fengniao_status : 2
         * lat : 24.485274
         * lng : 118.170248
         * carrier_driver_name : 关启超
         * carrier_driver_phone : 17600270990
         * create_time : 1536890941
         */

        private int fengniao_status;
        private double lat;
        private double lng;
        private String carrier_driver_name;
        private String carrier_driver_phone;
        private int create_time;

        public int getFengniao_status() {
            return fengniao_status;
        }

        public void setFengniao_status(int fengniao_status) {
            this.fengniao_status = fengniao_status;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getCarrier_driver_name() {
            return carrier_driver_name;
        }

        public void setCarrier_driver_name(String carrier_driver_name) {
            this.carrier_driver_name = carrier_driver_name;
        }

        public String getCarrier_driver_phone() {
            return carrier_driver_phone;
        }

        public void setCarrier_driver_phone(String carrier_driver_phone) {
            this.carrier_driver_phone = carrier_driver_phone;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }

    public static class DaDa {

        /**
         * orderId : 1w5393
         * statusCode : 1
         * statusMsg : 待接单
         * transporterName :
         * transporterPhone :
         * transporterLng :
         * transporterLat :
         * deliveryFee : 36
         * tips : 0
         * distance : 817881
         * createTime : 2018-10-25 10:31:40
         * acceptTime :
         * fetchTime :
         * finishTime :
         * cancelTime :
         * orderFinishCode :
         * couponFee : 0
         * actualFee : 36
         * insuranceFee : 0
         * supplierName : 测试门店
         * supplierAddress : 上海市隆宇大厦
         * supplierPhone : 15811112222
         * supplierLat : 31.230238
         * supplierLng : 121.515559
         */

        private String orderId;
        private int statusCode;
        private String statusMsg;
        private String transporterName;
        private String transporterPhone;
        private String transporterLng;
        private String transporterLat;
        private int deliveryFee;
        private int tips;
        private int distance;
        private String createTime;
        private String acceptTime;
        private String fetchTime;
        private String finishTime;
        private String cancelTime;
        private String orderFinishCode;
        private int couponFee;
        private int actualFee;
        private int insuranceFee;
        private String supplierName;
        private String supplierAddress;
        private String supplierPhone;
        private String supplierLat;
        private String supplierLng;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public String getStatusMsg() {
            return statusMsg;
        }

        public void setStatusMsg(String statusMsg) {
            this.statusMsg = statusMsg;
        }

        public String getTransporterName() {
            return transporterName;
        }

        public void setTransporterName(String transporterName) {
            this.transporterName = transporterName;
        }

        public String getTransporterPhone() {
            return transporterPhone;
        }

        public void setTransporterPhone(String transporterPhone) {
            this.transporterPhone = transporterPhone;
        }

        public String getTransporterLng() {
            return transporterLng;
        }

        public void setTransporterLng(String transporterLng) {
            this.transporterLng = transporterLng;
        }

        public String getTransporterLat() {
            return transporterLat;
        }

        public void setTransporterLat(String transporterLat) {
            this.transporterLat = transporterLat;
        }

        public int getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(int deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public int getTips() {
            return tips;
        }

        public void setTips(int tips) {
            this.tips = tips;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAcceptTime() {
            return acceptTime;
        }

        public void setAcceptTime(String acceptTime) {
            this.acceptTime = acceptTime;
        }

        public String getFetchTime() {
            return fetchTime;
        }

        public void setFetchTime(String fetchTime) {
            this.fetchTime = fetchTime;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public String getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getOrderFinishCode() {
            return orderFinishCode;
        }

        public void setOrderFinishCode(String orderFinishCode) {
            this.orderFinishCode = orderFinishCode;
        }

        public int getCouponFee() {
            return couponFee;
        }

        public void setCouponFee(int couponFee) {
            this.couponFee = couponFee;
        }

        public int getActualFee() {
            return actualFee;
        }

        public void setActualFee(int actualFee) {
            this.actualFee = actualFee;
        }

        public int getInsuranceFee() {
            return insuranceFee;
        }

        public void setInsuranceFee(int insuranceFee) {
            this.insuranceFee = insuranceFee;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getSupplierAddress() {
            return supplierAddress;
        }

        public void setSupplierAddress(String supplierAddress) {
            this.supplierAddress = supplierAddress;
        }

        public String getSupplierPhone() {
            return supplierPhone;
        }

        public void setSupplierPhone(String supplierPhone) {
            this.supplierPhone = supplierPhone;
        }

        public String getSupplierLat() {
            return supplierLat;
        }

        public void setSupplierLat(String supplierLat) {
            this.supplierLat = supplierLat;
        }

        public String getSupplierLng() {
            return supplierLng;
        }

        public void setSupplierLng(String supplierLng) {
            this.supplierLng = supplierLng;
        }
    }
}
