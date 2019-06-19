package com.wbx.mall.bean;

import java.util.HashMap;
import java.util.List;

public class BuygreensGoodsBean {

    /**
     * state : 1
     * msg : 成功
     * data : {"coupon":{"money":0,"num":0,"list":[]},"full_money_reduce":[],"shop_detail":{"shop_id":1259,"shop_name":"艸电","month_num":0,"sold_num":1392,"since_money":0,"logistics":0,"intro":"","is_open":1,"peisong_fanwei":"","addr":"大地方","score":5,"is_renzheng":0,"photo":"http://www.wbx365.com/attachs/2017/09/01/thumb_59a90b4cb2c97.jpg","view":1,"notice":"dtygh","grade_id":15,"business_time":null,"details":null,"video":null,"small_routine_photo":"http://www.wbx365.com/api/small_routine_photo/1259201901.jpg","scan_order_type":1,"hx_username":null,"view_num":768,"audit_photo":"","hygiene_photo":null,"audit_name":null,"audit_addr":null,"zhucehao":null,"audit_end_date":null,"consumption_money":0,"goods_num":59,"shop_favorites_num":1,"is_favorites":0,"is_shop_member_user":0,"is_buy":0,"qr_url":null},"menu":[],"goods":[],"seckill_goods":[{"product_id":29414,"product_name":"菠萝","photo":"http://www.wbx365.com/attachs/2017/11/15/thumb_5a0c05e6e8184.jpg","price":300,"is_attr":0,"nature":[],"month_num":1,"sold_num":1}],"list_assess":{"list":[],"assess_count":0,"assess_average":null}}
     */

    private int state;
    private String msg;
    private DataBean data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * coupon : {"money":0,"num":0,"list":[]}
         * full_money_reduce : []
         * shop_detail : {"shop_id":1259,"shop_name":"艸电","month_num":0,"sold_num":1392,"since_money":0,"logistics":0,"intro":"","is_open":1,"peisong_fanwei":"","addr":"大地方","score":5,"is_renzheng":0,"photo":"http://www.wbx365.com/attachs/2017/09/01/thumb_59a90b4cb2c97.jpg","view":1,"notice":"dtygh","grade_id":15,"business_time":null,"details":null,"video":null,"small_routine_photo":"http://www.wbx365.com/api/small_routine_photo/1259201901.jpg","scan_order_type":1,"hx_username":null,"view_num":768,"audit_photo":"","hygiene_photo":null,"audit_name":null,"audit_addr":null,"zhucehao":null,"audit_end_date":null,"consumption_money":0,"goods_num":59,"shop_favorites_num":1,"is_favorites":0,"is_shop_member_user":0,"is_buy":0,"qr_url":null}
         * menu : []
         * goods : []
         * seckill_goods : [{"product_id":29414,"product_name":"菠萝","photo":"http://www.wbx365.com/attachs/2017/11/15/thumb_5a0c05e6e8184.jpg","price":300,"is_attr":0,"nature":[],"month_num":1,"sold_num":1}]
         * list_assess : {"list":[],"assess_count":0,"assess_average":null}
         */

        private CouponBean coupon;
        private ShopDetailBean shop_detail;
        private ListAssessBean list_assess;
        private List<?> full_money_reduce;
        private List<?> menu;
        private List<?> goods;
        private List<SeckillGoodsBean> seckill_goods;

        public CouponBean getCoupon() {
            return coupon;
        }

        public void setCoupon(CouponBean coupon) {
            this.coupon = coupon;
        }

        public ShopDetailBean getShop_detail() {
            return shop_detail;
        }

        public void setShop_detail(ShopDetailBean shop_detail) {
            this.shop_detail = shop_detail;
        }

        public ListAssessBean getList_assess() {
            return list_assess;
        }

        public void setList_assess(ListAssessBean list_assess) {
            this.list_assess = list_assess;
        }

        public List<?> getFull_money_reduce() {
            return full_money_reduce;
        }

        public void setFull_money_reduce(List<?> full_money_reduce) {
            this.full_money_reduce = full_money_reduce;
        }

        public List<?> getMenu() {
            return menu;
        }

        public void setMenu(List<?> menu) {
            this.menu = menu;
        }

        public List<?> getGoods() {
            return goods;
        }

        public void setGoods(List<?> goods) {
            this.goods = goods;
        }

        public List<SeckillGoodsBean> getSeckill_goods() {
            return seckill_goods;
        }

        public void setSeckill_goods(List<SeckillGoodsBean> seckill_goods) {
            this.seckill_goods = seckill_goods;
        }

        public static class CouponBean {
            /**
             * money : 0
             * num : 0
             * list : []
             */

            private int money;
            private int num;
            private List<?> list;

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public List<?> getList() {
                return list;
            }

            public void setList(List<?> list) {
                this.list = list;
            }
        }

        public static class ShopDetailBean {
            /**
             * shop_id : 1259
             * shop_name : 艸电
             * month_num : 0
             * sold_num : 1392
             * since_money : 0
             * logistics : 0
             * intro :
             * is_open : 1
             * peisong_fanwei :
             * addr : 大地方
             * score : 5
             * is_renzheng : 0
             * photo : http://www.wbx365.com/attachs/2017/09/01/thumb_59a90b4cb2c97.jpg
             * view : 1
             * notice : dtygh
             * grade_id : 15
             * business_time : null
             * details : null
             * video : null
             * small_routine_photo : http://www.wbx365.com/api/small_routine_photo/1259201901.jpg
             * scan_order_type : 1
             * hx_username : null
             * view_num : 768
             * audit_photo :
             * hygiene_photo : null
             * audit_name : null
             * audit_addr : null
             * zhucehao : null
             * audit_end_date : null
             * consumption_money : 0
             * goods_num : 59
             * shop_favorites_num : 1
             * is_favorites : 0
             * is_shop_member_user : 0
             * is_buy : 0
             * qr_url : null
             */

            private int shop_id;
            private String shop_name;
            private int month_num;
            private int sold_num;
            private int since_money;
            private int logistics;
            private String intro;
            private int is_open;
            private String peisong_fanwei;
            private String addr;
            private int score;
            private int is_renzheng;
            private String photo;
            private int view;
            private String notice;
            private int grade_id;
            private Object business_time;
            private Object details;
            private Object video;
            private String small_routine_photo;
            private int scan_order_type;
            private Object hx_username;
            private int view_num;
            private String audit_photo;
            private Object hygiene_photo;
            private Object audit_name;
            private Object audit_addr;
            private Object zhucehao;
            private Object audit_end_date;
            private int consumption_money;
            private int goods_num;
            private int shop_favorites_num;
            private int is_favorites;
            private int is_shop_member_user;
            private int is_buy;
            private Object qr_url;

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

            public int getMonth_num() {
                return month_num;
            }

            public void setMonth_num(int month_num) {
                this.month_num = month_num;
            }

            public int getSold_num() {
                return sold_num;
            }

            public void setSold_num(int sold_num) {
                this.sold_num = sold_num;
            }

            public int getSince_money() {
                return since_money;
            }

            public void setSince_money(int since_money) {
                this.since_money = since_money;
            }

            public int getLogistics() {
                return logistics;
            }

            public void setLogistics(int logistics) {
                this.logistics = logistics;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public int getIs_open() {
                return is_open;
            }

            public void setIs_open(int is_open) {
                this.is_open = is_open;
            }

            public String getPeisong_fanwei() {
                return peisong_fanwei;
            }

            public void setPeisong_fanwei(String peisong_fanwei) {
                this.peisong_fanwei = peisong_fanwei;
            }

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getIs_renzheng() {
                return is_renzheng;
            }

            public void setIs_renzheng(int is_renzheng) {
                this.is_renzheng = is_renzheng;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public int getView() {
                return view;
            }

            public void setView(int view) {
                this.view = view;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }

            public int getGrade_id() {
                return grade_id;
            }

            public void setGrade_id(int grade_id) {
                this.grade_id = grade_id;
            }

            public Object getBusiness_time() {
                return business_time;
            }

            public void setBusiness_time(Object business_time) {
                this.business_time = business_time;
            }

            public Object getDetails() {
                return details;
            }

            public void setDetails(Object details) {
                this.details = details;
            }

            public Object getVideo() {
                return video;
            }

            public void setVideo(Object video) {
                this.video = video;
            }

            public String getSmall_routine_photo() {
                return small_routine_photo;
            }

            public void setSmall_routine_photo(String small_routine_photo) {
                this.small_routine_photo = small_routine_photo;
            }

            public int getScan_order_type() {
                return scan_order_type;
            }

            public void setScan_order_type(int scan_order_type) {
                this.scan_order_type = scan_order_type;
            }

            public Object getHx_username() {
                return hx_username;
            }

            public void setHx_username(Object hx_username) {
                this.hx_username = hx_username;
            }

            public int getView_num() {
                return view_num;
            }

            public void setView_num(int view_num) {
                this.view_num = view_num;
            }

            public String getAudit_photo() {
                return audit_photo;
            }

            public void setAudit_photo(String audit_photo) {
                this.audit_photo = audit_photo;
            }

            public Object getHygiene_photo() {
                return hygiene_photo;
            }

            public void setHygiene_photo(Object hygiene_photo) {
                this.hygiene_photo = hygiene_photo;
            }

            public Object getAudit_name() {
                return audit_name;
            }

            public void setAudit_name(Object audit_name) {
                this.audit_name = audit_name;
            }

            public Object getAudit_addr() {
                return audit_addr;
            }

            public void setAudit_addr(Object audit_addr) {
                this.audit_addr = audit_addr;
            }

            public Object getZhucehao() {
                return zhucehao;
            }

            public void setZhucehao(Object zhucehao) {
                this.zhucehao = zhucehao;
            }

            public Object getAudit_end_date() {
                return audit_end_date;
            }

            public void setAudit_end_date(Object audit_end_date) {
                this.audit_end_date = audit_end_date;
            }

            public int getConsumption_money() {
                return consumption_money;
            }

            public void setConsumption_money(int consumption_money) {
                this.consumption_money = consumption_money;
            }

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }

            public int getShop_favorites_num() {
                return shop_favorites_num;
            }

            public void setShop_favorites_num(int shop_favorites_num) {
                this.shop_favorites_num = shop_favorites_num;
            }

            public int getIs_favorites() {
                return is_favorites;
            }

            public void setIs_favorites(int is_favorites) {
                this.is_favorites = is_favorites;
            }

            public int getIs_shop_member_user() {
                return is_shop_member_user;
            }

            public void setIs_shop_member_user(int is_shop_member_user) {
                this.is_shop_member_user = is_shop_member_user;
            }

            public int getIs_buy() {
                return is_buy;
            }

            public void setIs_buy(int is_buy) {
                this.is_buy = is_buy;
            }

            public Object getQr_url() {
                return qr_url;
            }

            public void setQr_url(Object qr_url) {
                this.qr_url = qr_url;
            }
        }

        public static class ListAssessBean {
            /**
             * list : []
             * assess_count : 0
             * assess_average : null
             */

            private int assess_count;
            private Object assess_average;
            private List<?> list;

            public int getAssess_count() {
                return assess_count;
            }

            public void setAssess_count(int assess_count) {
                this.assess_count = assess_count;
            }

            public Object getAssess_average() {
                return assess_average;
            }

            public void setAssess_average(Object assess_average) {
                this.assess_average = assess_average;
            }

            public List<?> getList() {
                return list;
            }

            public void setList(List<?> list) {
                this.list = list;
            }
        }

        public static class SeckillGoodsBean {
            /**
             * product_id : 29414
             * product_name : 菠萝
             * photo : http://www.wbx365.com/attachs/2017/11/15/thumb_5a0c05e6e8184.jpg
             * price : 300
             * is_attr : 0
             * nature : []
             * month_num : 1
             * sold_num : 1
             */

            private String product_id;
            private String product_name;
            private String photo;
            private int price;
            private int is_attr;
            private int month_num;
            private int sold_num;
            private List<?> nature;
            private HashMap<String, Integer> hmBuyNum = new HashMap<>();//key拼接规则：goodsId,attrId,natureId+natureId+natureId，例如100,5,1001+1002+1003
            private int inventory_num;//库存
            private int is_use_num;//是否开启库存
            private int limitations_num;

            public int getLimitations_num() {
                return limitations_num;
            }

            public void setLimitations_num(int limitations_num) {
                this.limitations_num = limitations_num;
            }

            public int getIs_use_num() {
                return is_use_num;
            }

            public void setIs_use_num(int is_use_num) {
                this.is_use_num = is_use_num;
            }

            public int getInventory_num() {
                return inventory_num;
            }

            public void setInventory_num(int inventory_num) {
                this.inventory_num = inventory_num;
            }

            public HashMap<String, Integer> getHmBuyNum() {
                return hmBuyNum;
            }

            public void setHmBuyNum(HashMap<String, Integer> hmBuyNum) {
                this.hmBuyNum = hmBuyNum;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getIs_attr() {
                return is_attr;
            }

            public void setIs_attr(int is_attr) {
                this.is_attr = is_attr;
            }

            public int getMonth_num() {
                return month_num;
            }

            public void setMonth_num(int month_num) {
                this.month_num = month_num;
            }

            public int getSold_num() {
                return sold_num;
            }

            public void setSold_num(int sold_num) {
                this.sold_num = sold_num;
            }

            public List<?> getNature() {
                return nature;
            }

            public void setNature(List<?> nature) {
                this.nature = nature;
            }
        }
    }
}
