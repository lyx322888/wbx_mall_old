package com.wbx.mall.bean;

import java.util.List;

public class ShopGoodsBean {


    /**
     * msg : 成功
     * state : 1
     * data : {"detail":{"shop_id":721,"grade_id":20,"logistics":550,"is_subscribe":1,"subscribe_money":1000,"shop_name":"晴天超市","addr":"厦门大学","photo":"http://www.wbx365.com/attachs/2017/09/01/thumb_59a90ea571410.png","view":18,"score":4,"since_money":550,"peisong_fanwei":"额咯福特哦，URL喷壶闺女","notice":"多买多惠","video":null,"small_routine_photo":"http://www.wbx365.com/api/small_routine_photo/721.jpg","business_time":"5:00-23:00","details":null,"audit_photo":"http://imgs.wbx365.com/15110779751_1543202405.jpg","hygiene_photo":"","audit_name":"111","audit_addr":"苹果","scan_order_type":1,"zhucehao":"466464694","audit_end_date":"2019-11-1","hx_username":null,"view_num":1768,"sold_num":1725,"month_num":214,"goods_num":105,"shop_favorites_num":19,"is_favorites":1,"is_shop_member_user":0,"is_buy":0,"qr_url":"0","consumption_money":3500},"cate":[{"cate_id":3410,"cate_name":"好吃的","num":0},{"cate_id":3500,"cate_name":"好喝的","num":0},{"cate_id":3502,"cate_name":"好玩的","num":0},{"cate_id":3509,"cate_name":"积分商品","num":0}],"goods":[],"seckill_goods":[{"goods_id":59906,"title":"白水杜康 N88 帝王黄 浓香型白酒 52度 500ml","photo":"http://www.wbx365.com/attachs/2018/03/14/5aa87a8b37677.jpg","price":300,"is_attr":0,"nature":[],"sold_num":0},{"goods_id":60889,"title":"蛋糕(多规格单属性)","photo":"http://imgs.wbx365.com/Fhcnhh_MiCm4BbPnNrJG_l-WRrWQ","price":10,"is_attr":1,"nature":[],"goods_attr":[{"attr_id":3042,"goods_id":60889,"attr_name":"大","price":8800,"mall_price":700,"sales_promotion_price":0,"num":0,"type":"shop","loss":0,"seckill_price":10,"seckill_num":49,"limitations_num":5,"is_seckill":0,"casing_price":600,"shop_member_price":0,"is_shop_member":0},{"attr_id":3043,"goods_id":60889,"attr_name":"中","price":7900,"mall_price":600,"sales_promotion_price":0,"num":0,"type":"shop","loss":0,"seckill_price":20,"seckill_num":50,"limitations_num":5,"is_seckill":0,"casing_price":500,"shop_member_price":0,"is_shop_member":0}],"sold_num":11}],"coupon":{"money":2000,"num":4,"list":[{"coupon_id":273,"money":500,"condition_money":2000,"num":41,"shop_id":721,"create_time":1542009459,"is_delete":0,"start_time":1510416000,"end_time":1636646400,"is_receive":0},{"coupon_id":274,"money":800,"condition_money":3000,"num":490,"shop_id":721,"create_time":1542009490,"is_delete":0,"start_time":1541865600,"end_time":1636646400,"is_receive":0},{"coupon_id":275,"money":100,"condition_money":1000,"num":42,"shop_id":721,"create_time":1542072369,"is_delete":0,"start_time":1541520000,"end_time":1857657600,"is_receive":0},{"coupon_id":291,"money":600,"condition_money":1800,"num":47,"shop_id":721,"create_time":1546996470,"is_delete":0,"start_time":1546876800,"end_time":1578499200,"is_receive":0}]},"full_money_reduce":[{"id":192,"full_money":8800,"reduce_money":600,"shop_id":721,"is_delete":0},{"id":198,"full_money":5000,"reduce_money":500,"shop_id":721,"is_delete":0},{"id":199,"full_money":1000,"reduce_money":100,"shop_id":721,"is_delete":0},{"id":200,"full_money":2000,"reduce_money":200,"shop_id":721,"is_delete":0}],"list_assess":{"list":[{"create_time":1539669741,"message":"嘿嘿","pics":["http://imgs.wbx365.com/Fi0mCTWlg4GRaDa64fbGpGr5mYSS"],"grade":4,"nickname":"杜帅***","face":"http://imgs.wbx365.com/Fih_wrKbWhfv4FmWyjoYLkPohVfo"},{"create_time":1540264272,"message":"哈哈哈，味道一流","pics":["http://imgs.wbx365.com/FmTwvyIEd6dX8UWSi8JqAvQwwnph"],"grade":5,"nickname":"杜帅***","face":"http://imgs.wbx365.com/Fih_wrKbWhfv4FmWyjoYLkPohVfo"},{"create_time":1540369362,"message":"味道超棒，只可惜凉了","pics":[],"grade":4,"nickname":"在一起***","face":"http://imgs.wbx365.com/152608791621553222810"}],"assess_count":4,"assess_average":4.5}}
     */

    private String msg;
    private int state;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * detail : {"shop_id":721,"grade_id":20,"logistics":550,"is_subscribe":1,"subscribe_money":1000,"shop_name":"晴天超市","addr":"厦门大学","photo":"http://www.wbx365.com/attachs/2017/09/01/thumb_59a90ea571410.png","view":18,"score":4,"since_money":550,"peisong_fanwei":"额咯福特哦，URL喷壶闺女","notice":"多买多惠","video":null,"small_routine_photo":"http://www.wbx365.com/api/small_routine_photo/721.jpg","business_time":"5:00-23:00","details":null,"audit_photo":"http://imgs.wbx365.com/15110779751_1543202405.jpg","hygiene_photo":"","audit_name":"111","audit_addr":"苹果","scan_order_type":1,"zhucehao":"466464694","audit_end_date":"2019-11-1","hx_username":null,"view_num":1768,"sold_num":1725,"month_num":214,"goods_num":105,"shop_favorites_num":19,"is_favorites":1,"is_shop_member_user":0,"is_buy":0,"qr_url":"0","consumption_money":3500}
         * cate : [{"cate_id":3410,"cate_name":"好吃的","num":0},{"cate_id":3500,"cate_name":"好喝的","num":0},{"cate_id":3502,"cate_name":"好玩的","num":0},{"cate_id":3509,"cate_name":"积分商品","num":0}]
         * goods : []
         * seckill_goods : [{"goods_id":59906,"title":"白水杜康 N88 帝王黄 浓香型白酒 52度 500ml","photo":"http://www.wbx365.com/attachs/2018/03/14/5aa87a8b37677.jpg","price":300,"is_attr":0,"nature":[],"sold_num":0},{"goods_id":60889,"title":"蛋糕(多规格单属性)","photo":"http://imgs.wbx365.com/Fhcnhh_MiCm4BbPnNrJG_l-WRrWQ","price":10,"is_attr":1,"nature":[],"goods_attr":[{"attr_id":3042,"goods_id":60889,"attr_name":"大","price":8800,"mall_price":700,"sales_promotion_price":0,"num":0,"type":"shop","loss":0,"seckill_price":10,"seckill_num":49,"limitations_num":5,"is_seckill":0,"casing_price":600,"shop_member_price":0,"is_shop_member":0},{"attr_id":3043,"goods_id":60889,"attr_name":"中","price":7900,"mall_price":600,"sales_promotion_price":0,"num":0,"type":"shop","loss":0,"seckill_price":20,"seckill_num":50,"limitations_num":5,"is_seckill":0,"casing_price":500,"shop_member_price":0,"is_shop_member":0}],"sold_num":11}]
         * coupon : {"money":2000,"num":4,"list":[{"coupon_id":273,"money":500,"condition_money":2000,"num":41,"shop_id":721,"create_time":1542009459,"is_delete":0,"start_time":1510416000,"end_time":1636646400,"is_receive":0},{"coupon_id":274,"money":800,"condition_money":3000,"num":490,"shop_id":721,"create_time":1542009490,"is_delete":0,"start_time":1541865600,"end_time":1636646400,"is_receive":0},{"coupon_id":275,"money":100,"condition_money":1000,"num":42,"shop_id":721,"create_time":1542072369,"is_delete":0,"start_time":1541520000,"end_time":1857657600,"is_receive":0},{"coupon_id":291,"money":600,"condition_money":1800,"num":47,"shop_id":721,"create_time":1546996470,"is_delete":0,"start_time":1546876800,"end_time":1578499200,"is_receive":0}]}
         * full_money_reduce : [{"id":192,"full_money":8800,"reduce_money":600,"shop_id":721,"is_delete":0},{"id":198,"full_money":5000,"reduce_money":500,"shop_id":721,"is_delete":0},{"id":199,"full_money":1000,"reduce_money":100,"shop_id":721,"is_delete":0},{"id":200,"full_money":2000,"reduce_money":200,"shop_id":721,"is_delete":0}]
         * list_assess : {"list":[{"create_time":1539669741,"message":"嘿嘿","pics":["http://imgs.wbx365.com/Fi0mCTWlg4GRaDa64fbGpGr5mYSS"],"grade":4,"nickname":"杜帅***","face":"http://imgs.wbx365.com/Fih_wrKbWhfv4FmWyjoYLkPohVfo"},{"create_time":1540264272,"message":"哈哈哈，味道一流","pics":["http://imgs.wbx365.com/FmTwvyIEd6dX8UWSi8JqAvQwwnph"],"grade":5,"nickname":"杜帅***","face":"http://imgs.wbx365.com/Fih_wrKbWhfv4FmWyjoYLkPohVfo"},{"create_time":1540369362,"message":"味道超棒，只可惜凉了","pics":[],"grade":4,"nickname":"在一起***","face":"http://imgs.wbx365.com/152608791621553222810"}],"assess_count":4,"assess_average":4.5}
         */

        private DetailBean detail;
        private CouponBean coupon;
        private ListAssessBean list_assess;
        private List<CateBean> cate;
        private List<?> goods;
        private List<SeckillGoodsBean> seckill_goods;
        private List<FullMoneyReduceBean> full_money_reduce;

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public CouponBean getCoupon() {
            return coupon;
        }

        public void setCoupon(CouponBean coupon) {
            this.coupon = coupon;
        }

        public ListAssessBean getList_assess() {
            return list_assess;
        }

        public void setList_assess(ListAssessBean list_assess) {
            this.list_assess = list_assess;
        }

        public List<CateBean> getCate() {
            return cate;
        }

        public void setCate(List<CateBean> cate) {
            this.cate = cate;
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

        public List<FullMoneyReduceBean> getFull_money_reduce() {
            return full_money_reduce;
        }

        public void setFull_money_reduce(List<FullMoneyReduceBean> full_money_reduce) {
            this.full_money_reduce = full_money_reduce;
        }

        public static class DetailBean {
            /**
             * shop_id : 721
             * grade_id : 20
             * logistics : 550
             * is_subscribe : 1
             * subscribe_money : 1000
             * shop_name : 晴天超市
             * addr : 厦门大学
             * photo : http://www.wbx365.com/attachs/2017/09/01/thumb_59a90ea571410.png
             * view : 18
             * score : 4
             * since_money : 550
             * peisong_fanwei : 额咯福特哦，URL喷壶闺女
             * notice : 多买多惠
             * video : null
             * small_routine_photo : http://www.wbx365.com/api/small_routine_photo/721.jpg
             * business_time : 5:00-23:00
             * details : null
             * audit_photo : http://imgs.wbx365.com/15110779751_1543202405.jpg
             * hygiene_photo :
             * audit_name : 111
             * audit_addr : 苹果
             * scan_order_type : 1
             * zhucehao : 466464694
             * audit_end_date : 2019-11-1
             * hx_username : null
             * view_num : 1768
             * sold_num : 1725
             * month_num : 214
             * goods_num : 105
             * shop_favorites_num : 19
             * is_favorites : 1
             * is_shop_member_user : 0
             * is_buy : 0
             * qr_url : 0
             * consumption_money : 3500
             */

            private int shop_id;
            private int grade_id;
            private int logistics;
            private int is_subscribe;
            private int subscribe_money;
            private String shop_name;
            private String addr;
            private String photo;
            private int view;
            private int score;
            private int since_money;
            private String peisong_fanwei;
            private String notice;
            private Object video;
            private String small_routine_photo;
            private String business_time;
            private Object details;
            private String audit_photo;
            private String hygiene_photo;
            private String audit_name;
            private String audit_addr;
            private int scan_order_type;
            private String zhucehao;
            private String audit_end_date;
            private Object hx_username;
            private int view_num;
            private int sold_num;
            private int month_num;
            private int goods_num;
            private int shop_favorites_num;
            private int is_favorites;
            private int is_shop_member_user;
            private int is_buy;
            private String qr_url;
            private int consumption_money;

            public int getShop_id() {
                return shop_id;
            }

            public void setShop_id(int shop_id) {
                this.shop_id = shop_id;
            }

            public int getGrade_id() {
                return grade_id;
            }

            public void setGrade_id(int grade_id) {
                this.grade_id = grade_id;
            }

            public int getLogistics() {
                return logistics;
            }

            public void setLogistics(int logistics) {
                this.logistics = logistics;
            }

            public int getIs_subscribe() {
                return is_subscribe;
            }

            public void setIs_subscribe(int is_subscribe) {
                this.is_subscribe = is_subscribe;
            }

            public int getSubscribe_money() {
                return subscribe_money;
            }

            public void setSubscribe_money(int subscribe_money) {
                this.subscribe_money = subscribe_money;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
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

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getSince_money() {
                return since_money;
            }

            public void setSince_money(int since_money) {
                this.since_money = since_money;
            }

            public String getPeisong_fanwei() {
                return peisong_fanwei;
            }

            public void setPeisong_fanwei(String peisong_fanwei) {
                this.peisong_fanwei = peisong_fanwei;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
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

            public String getBusiness_time() {
                return business_time;
            }

            public void setBusiness_time(String business_time) {
                this.business_time = business_time;
            }

            public Object getDetails() {
                return details;
            }

            public void setDetails(Object details) {
                this.details = details;
            }

            public String getAudit_photo() {
                return audit_photo;
            }

            public void setAudit_photo(String audit_photo) {
                this.audit_photo = audit_photo;
            }

            public String getHygiene_photo() {
                return hygiene_photo;
            }

            public void setHygiene_photo(String hygiene_photo) {
                this.hygiene_photo = hygiene_photo;
            }

            public String getAudit_name() {
                return audit_name;
            }

            public void setAudit_name(String audit_name) {
                this.audit_name = audit_name;
            }

            public String getAudit_addr() {
                return audit_addr;
            }

            public void setAudit_addr(String audit_addr) {
                this.audit_addr = audit_addr;
            }

            public int getScan_order_type() {
                return scan_order_type;
            }

            public void setScan_order_type(int scan_order_type) {
                this.scan_order_type = scan_order_type;
            }

            public String getZhucehao() {
                return zhucehao;
            }

            public void setZhucehao(String zhucehao) {
                this.zhucehao = zhucehao;
            }

            public String getAudit_end_date() {
                return audit_end_date;
            }

            public void setAudit_end_date(String audit_end_date) {
                this.audit_end_date = audit_end_date;
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

            public int getSold_num() {
                return sold_num;
            }

            public void setSold_num(int sold_num) {
                this.sold_num = sold_num;
            }

            public int getMonth_num() {
                return month_num;
            }

            public void setMonth_num(int month_num) {
                this.month_num = month_num;
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

            public String getQr_url() {
                return qr_url;
            }

            public void setQr_url(String qr_url) {
                this.qr_url = qr_url;
            }

            public int getConsumption_money() {
                return consumption_money;
            }

            public void setConsumption_money(int consumption_money) {
                this.consumption_money = consumption_money;
            }
        }

        public static class CouponBean {
            /**
             * money : 2000
             * num : 4
             * list : [{"coupon_id":273,"money":500,"condition_money":2000,"num":41,"shop_id":721,"create_time":1542009459,"is_delete":0,"start_time":1510416000,"end_time":1636646400,"is_receive":0},{"coupon_id":274,"money":800,"condition_money":3000,"num":490,"shop_id":721,"create_time":1542009490,"is_delete":0,"start_time":1541865600,"end_time":1636646400,"is_receive":0},{"coupon_id":275,"money":100,"condition_money":1000,"num":42,"shop_id":721,"create_time":1542072369,"is_delete":0,"start_time":1541520000,"end_time":1857657600,"is_receive":0},{"coupon_id":291,"money":600,"condition_money":1800,"num":47,"shop_id":721,"create_time":1546996470,"is_delete":0,"start_time":1546876800,"end_time":1578499200,"is_receive":0}]
             */

            private int money;
            private int num;
            private List<ListBean> list;

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

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * coupon_id : 273
                 * money : 500
                 * condition_money : 2000
                 * num : 41
                 * shop_id : 721
                 * create_time : 1542009459
                 * is_delete : 0
                 * start_time : 1510416000
                 * end_time : 1636646400
                 * is_receive : 0
                 */

                private int coupon_id;
                private int money;
                private int condition_money;
                private int num;
                private int shop_id;
                private int create_time;
                private int is_delete;
                private int start_time;
                private int end_time;
                private int is_receive;

                public int getCoupon_id() {
                    return coupon_id;
                }

                public void setCoupon_id(int coupon_id) {
                    this.coupon_id = coupon_id;
                }

                public int getMoney() {
                    return money;
                }

                public void setMoney(int money) {
                    this.money = money;
                }

                public int getCondition_money() {
                    return condition_money;
                }

                public void setCondition_money(int condition_money) {
                    this.condition_money = condition_money;
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public int getShop_id() {
                    return shop_id;
                }

                public void setShop_id(int shop_id) {
                    this.shop_id = shop_id;
                }

                public int getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(int create_time) {
                    this.create_time = create_time;
                }

                public int getIs_delete() {
                    return is_delete;
                }

                public void setIs_delete(int is_delete) {
                    this.is_delete = is_delete;
                }

                public int getStart_time() {
                    return start_time;
                }

                public void setStart_time(int start_time) {
                    this.start_time = start_time;
                }

                public int getEnd_time() {
                    return end_time;
                }

                public void setEnd_time(int end_time) {
                    this.end_time = end_time;
                }

                public int getIs_receive() {
                    return is_receive;
                }

                public void setIs_receive(int is_receive) {
                    this.is_receive = is_receive;
                }
            }
        }

        public static class ListAssessBean {
            /**
             * list : [{"create_time":1539669741,"message":"嘿嘿","pics":["http://imgs.wbx365.com/Fi0mCTWlg4GRaDa64fbGpGr5mYSS"],"grade":4,"nickname":"杜帅***","face":"http://imgs.wbx365.com/Fih_wrKbWhfv4FmWyjoYLkPohVfo"},{"create_time":1540264272,"message":"哈哈哈，味道一流","pics":["http://imgs.wbx365.com/FmTwvyIEd6dX8UWSi8JqAvQwwnph"],"grade":5,"nickname":"杜帅***","face":"http://imgs.wbx365.com/Fih_wrKbWhfv4FmWyjoYLkPohVfo"},{"create_time":1540369362,"message":"味道超棒，只可惜凉了","pics":[],"grade":4,"nickname":"在一起***","face":"http://imgs.wbx365.com/152608791621553222810"}]
             * assess_count : 4
             * assess_average : 4.5
             */

            private int assess_count;
            private double assess_average;
            private List<ListBeanX> list;

            public int getAssess_count() {
                return assess_count;
            }

            public void setAssess_count(int assess_count) {
                this.assess_count = assess_count;
            }

            public double getAssess_average() {
                return assess_average;
            }

            public void setAssess_average(double assess_average) {
                this.assess_average = assess_average;
            }

            public List<ListBeanX> getList() {
                return list;
            }

            public void setList(List<ListBeanX> list) {
                this.list = list;
            }

            public static class ListBeanX {
                /**
                 * create_time : 1539669741
                 * message : 嘿嘿
                 * pics : ["http://imgs.wbx365.com/Fi0mCTWlg4GRaDa64fbGpGr5mYSS"]
                 * grade : 4
                 * nickname : 杜帅***
                 * face : http://imgs.wbx365.com/Fih_wrKbWhfv4FmWyjoYLkPohVfo
                 */

                private int create_time;
                private String message;
                private int grade;
                private String nickname;
                private String face;
                private List<String> pics;

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

                public int getGrade() {
                    return grade;
                }

                public void setGrade(int grade) {
                    this.grade = grade;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getFace() {
                    return face;
                }

                public void setFace(String face) {
                    this.face = face;
                }

                public List<String> getPics() {
                    return pics;
                }

                public void setPics(List<String> pics) {
                    this.pics = pics;
                }
            }
        }

        public static class CateBean {
            /**
             * cate_id : 3410
             * cate_name : 好吃的
             * num : 0
             */

            private int cate_id;
            private String cate_name;
            private int num;

            public int getCate_id() {
                return cate_id;
            }

            public void setCate_id(int cate_id) {
                this.cate_id = cate_id;
            }

            public String getCate_name() {
                return cate_name;
            }

            public void setCate_name(String cate_name) {
                this.cate_name = cate_name;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }
        }

        public static class SeckillGoodsBean {
            /**
             * goods_id : 59906
             * title : 白水杜康 N88 帝王黄 浓香型白酒 52度 500ml
             * photo : http://www.wbx365.com/attachs/2018/03/14/5aa87a8b37677.jpg
             * price : 300
             * is_attr : 0
             * nature : []
             * sold_num : 0
             * goods_attr : [{"attr_id":3042,"goods_id":60889,"attr_name":"大","price":8800,"mall_price":700,"sales_promotion_price":0,"num":0,"type":"shop","loss":0,"seckill_price":10,"seckill_num":49,"limitations_num":5,"is_seckill":0,"casing_price":600,"shop_member_price":0,"is_shop_member":0},{"attr_id":3043,"goods_id":60889,"attr_name":"中","price":7900,"mall_price":600,"sales_promotion_price":0,"num":0,"type":"shop","loss":0,"seckill_price":20,"seckill_num":50,"limitations_num":5,"is_seckill":0,"casing_price":500,"shop_member_price":0,"is_shop_member":0}]
             */

            private int goods_id;
            private String title;
            private String photo;
            private int price;
            private int is_attr;
            private int sold_num;
            private List<?> nature;
            private List<GoodsAttrBean> goods_attr;

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

            public List<GoodsAttrBean> getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(List<GoodsAttrBean> goods_attr) {
                this.goods_attr = goods_attr;
            }

            public static class GoodsAttrBean {
                /**
                 * attr_id : 3042
                 * goods_id : 60889
                 * attr_name : 大
                 * price : 8800
                 * mall_price : 700
                 * sales_promotion_price : 0
                 * num : 0
                 * type : shop
                 * loss : 0
                 * seckill_price : 10
                 * seckill_num : 49
                 * limitations_num : 5
                 * is_seckill : 0
                 * casing_price : 600
                 * shop_member_price : 0
                 * is_shop_member : 0
                 */

                private int attr_id;
                private int goods_id;
                private String attr_name;
                private int price;
                private int mall_price;
                private int sales_promotion_price;
                private int num;
                private String type;
                private int loss;
                private int seckill_price;
                private int seckill_num;
                private int limitations_num;
                private int is_seckill;
                private int casing_price;
                private int shop_member_price;
                private int is_shop_member;

                public int getAttr_id() {
                    return attr_id;
                }

                public void setAttr_id(int attr_id) {
                    this.attr_id = attr_id;
                }

                public int getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public String getAttr_name() {
                    return attr_name;
                }

                public void setAttr_name(String attr_name) {
                    this.attr_name = attr_name;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public int getMall_price() {
                    return mall_price;
                }

                public void setMall_price(int mall_price) {
                    this.mall_price = mall_price;
                }

                public int getSales_promotion_price() {
                    return sales_promotion_price;
                }

                public void setSales_promotion_price(int sales_promotion_price) {
                    this.sales_promotion_price = sales_promotion_price;
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getLoss() {
                    return loss;
                }

                public void setLoss(int loss) {
                    this.loss = loss;
                }

                public int getSeckill_price() {
                    return seckill_price;
                }

                public void setSeckill_price(int seckill_price) {
                    this.seckill_price = seckill_price;
                }

                public int getSeckill_num() {
                    return seckill_num;
                }

                public void setSeckill_num(int seckill_num) {
                    this.seckill_num = seckill_num;
                }

                public int getLimitations_num() {
                    return limitations_num;
                }

                public void setLimitations_num(int limitations_num) {
                    this.limitations_num = limitations_num;
                }

                public int getIs_seckill() {
                    return is_seckill;
                }

                public void setIs_seckill(int is_seckill) {
                    this.is_seckill = is_seckill;
                }

                public int getCasing_price() {
                    return casing_price;
                }

                public void setCasing_price(int casing_price) {
                    this.casing_price = casing_price;
                }

                public int getShop_member_price() {
                    return shop_member_price;
                }

                public void setShop_member_price(int shop_member_price) {
                    this.shop_member_price = shop_member_price;
                }

                public int getIs_shop_member() {
                    return is_shop_member;
                }

                public void setIs_shop_member(int is_shop_member) {
                    this.is_shop_member = is_shop_member;
                }
            }
        }

        public static class FullMoneyReduceBean {
            /**
             * id : 192
             * full_money : 8800
             * reduce_money : 600
             * shop_id : 721
             * is_delete : 0
             */

            private int id;
            private int full_money;
            private int reduce_money;
            private int shop_id;
            private int is_delete;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getFull_money() {
                return full_money;
            }

            public void setFull_money(int full_money) {
                this.full_money = full_money;
            }

            public int getReduce_money() {
                return reduce_money;
            }

            public void setReduce_money(int reduce_money) {
                this.reduce_money = reduce_money;
            }

            public int getShop_id() {
                return shop_id;
            }

            public void setShop_id(int shop_id) {
                this.shop_id = shop_id;
            }

            public int getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(int is_delete) {
                this.is_delete = is_delete;
            }
        }
    }
}
