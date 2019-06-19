package com.wbx.mall.bean;

import java.util.List;

public class ListgoodsBean {

    /**
     * msg : 成功
     * state : 1
     * data : [{"product_id":29818,"product_name":"黑土猪封肉","desc":"主材:太湖梅山黑土猪 板栗 虾仁 香菇等（无添加封肉精等防腐剂）！由同安金牌老店带加工！","shop_id":586,"cate_id":1040,"photo":"http://imgs.wbx365.com/FvKNpPuOR15YIVG7f42YfwYcmdap","goods_photo":"","price":4600,"settlement_price":4580,"is_que":0,"is_new":0,"is_hot":0,"is_tuijian":0,"sold_num":0,"month_num":8,"create_time":1515802720,"create_ip":"58.23.142.245","closed":0,"audit":1,"is_use_num":0,"num":0,"sales_promotion_is":1,"sales_promotion_price":4600,"is_attr":0,"loss":0,"is_seckill":1,"is_partake_full_reduce":0,"is_partake_coupon":0,"seckill_price":1000,"seckill_start_time":1528965840,"seckill_end_time":1830329040,"seckill_num":46,"limitations_num":1,"is_update":1,"is_delete":0,"shop_member_price":0,"is_shop_member":0,"nature":[],"consume_free_create_time":0,"consume_free_duration":999999999,"is_consume_free":0,"consume_free_num":0,"consume_free_price":0,"consume_free_amount":0,"share_free_create_time":0,"share_free_duration":0,"is_share_free":0,"share_free_num":0,"share_free_price":0,"share_free_amount":0,"is_accumulate_free":0,"accumulate_free_need_num":0,"accumulate_free_get_num":0,"inventory_num":0,"current_num":0,"original_price":4580,"over_time":275634783,"surplus_num":0,"goods_activity_type":4}]
     */

    private String msg;
    private int state;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * product_id : 29818
         * product_name : 黑土猪封肉
         * desc : 主材:太湖梅山黑土猪 板栗 虾仁 香菇等（无添加封肉精等防腐剂）！由同安金牌老店带加工！
         * shop_id : 586
         * cate_id : 1040
         * photo : http://imgs.wbx365.com/FvKNpPuOR15YIVG7f42YfwYcmdap
         * goods_photo :
         * price : 4600
         * settlement_price : 4580
         * is_que : 0
         * is_new : 0
         * is_hot : 0
         * is_tuijian : 0
         * sold_num : 0
         * month_num : 8
         * create_time : 1515802720
         * create_ip : 58.23.142.245
         * closed : 0
         * audit : 1
         * is_use_num : 0
         * num : 0
         * sales_promotion_is : 1
         * sales_promotion_price : 4600
         * is_attr : 0
         * loss : 0
         * is_seckill : 1
         * is_partake_full_reduce : 0
         * is_partake_coupon : 0
         * seckill_price : 1000
         * seckill_start_time : 1528965840
         * seckill_end_time : 1830329040
         * seckill_num : 46
         * limitations_num : 1
         * is_update : 1
         * is_delete : 0
         * shop_member_price : 0
         * is_shop_member : 0
         * nature : []
         * consume_free_create_time : 0
         * consume_free_duration : 999999999
         * is_consume_free : 0
         * consume_free_num : 0
         * consume_free_price : 0
         * consume_free_amount : 0
         * share_free_create_time : 0
         * share_free_duration : 0
         * is_share_free : 0
         * share_free_num : 0
         * share_free_price : 0
         * share_free_amount : 0
         * is_accumulate_free : 0
         * accumulate_free_need_num : 0
         * accumulate_free_get_num : 0
         * inventory_num : 0
         * current_num : 0
         * original_price : 4580
         * over_time : 275634783
         * surplus_num : 0
         * goods_activity_type : 4
         */

        private int product_id;
        private String product_name;
        private String desc;
        private int shop_id;
        private int cate_id;
        private String photo;
        private String goods_photo;
        private int price;
        private int settlement_price;
        private int is_que;
        private int is_new;
        private int is_hot;
        private int is_tuijian;
        private int sold_num;
        private int month_num;
        private int create_time;
        private String create_ip;
        private int closed;
        private int audit;
        private int is_use_num;
        private int num;
        private int sales_promotion_is;
        private int sales_promotion_price;
        private int is_attr;
        private int loss;
        private int is_seckill;
        private int is_partake_full_reduce;
        private int is_partake_coupon;
        private int seckill_price;
        private int seckill_start_time;
        private int seckill_end_time;
        private int seckill_num;
        private int limitations_num;
        private int is_update;
        private int is_delete;
        private int shop_member_price;
        private int is_shop_member;
        private int consume_free_create_time;
        private int consume_free_duration;
        private int is_consume_free;
        private int consume_free_num;
        private int consume_free_price;
        private int consume_free_amount;
        private int share_free_create_time;
        private int share_free_duration;
        private int is_share_free;
        private int share_free_num;
        private int share_free_price;
        private int share_free_amount;
        private int is_accumulate_free;
        private int accumulate_free_need_num;
        private int accumulate_free_get_num;
        private int inventory_num;
        private int current_num;
        private int original_price;
        private int over_time;
        private int surplus_num;
        private int goods_activity_type;
        private List<?> nature;
        private List<GoodsInfo2> goodsInfo2s;


        public List<GoodsInfo2> getGoodsInfo2s() {
            return goodsInfo2s;
        }

        public void setGoodsInfo2s(List<GoodsInfo2> goodsInfo2s) {
            this.goodsInfo2s = goodsInfo2s;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getGoods_photo() {
            return goods_photo;
        }

        public void setGoods_photo(String goods_photo) {
            this.goods_photo = goods_photo;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSettlement_price() {
            return settlement_price;
        }

        public void setSettlement_price(int settlement_price) {
            this.settlement_price = settlement_price;
        }

        public int getIs_que() {
            return is_que;
        }

        public void setIs_que(int is_que) {
            this.is_que = is_que;
        }

        public int getIs_new() {
            return is_new;
        }

        public void setIs_new(int is_new) {
            this.is_new = is_new;
        }

        public int getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(int is_hot) {
            this.is_hot = is_hot;
        }

        public int getIs_tuijian() {
            return is_tuijian;
        }

        public void setIs_tuijian(int is_tuijian) {
            this.is_tuijian = is_tuijian;
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

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getCreate_ip() {
            return create_ip;
        }

        public void setCreate_ip(String create_ip) {
            this.create_ip = create_ip;
        }

        public int getClosed() {
            return closed;
        }

        public void setClosed(int closed) {
            this.closed = closed;
        }

        public int getAudit() {
            return audit;
        }

        public void setAudit(int audit) {
            this.audit = audit;
        }

        public int getIs_use_num() {
            return is_use_num;
        }

        public void setIs_use_num(int is_use_num) {
            this.is_use_num = is_use_num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getSales_promotion_is() {
            return sales_promotion_is;
        }

        public void setSales_promotion_is(int sales_promotion_is) {
            this.sales_promotion_is = sales_promotion_is;
        }

        public int getSales_promotion_price() {
            return sales_promotion_price;
        }

        public void setSales_promotion_price(int sales_promotion_price) {
            this.sales_promotion_price = sales_promotion_price;
        }

        public int getIs_attr() {
            return is_attr;
        }

        public void setIs_attr(int is_attr) {
            this.is_attr = is_attr;
        }

        public int getLoss() {
            return loss;
        }

        public void setLoss(int loss) {
            this.loss = loss;
        }

        public int getIs_seckill() {
            return is_seckill;
        }

        public void setIs_seckill(int is_seckill) {
            this.is_seckill = is_seckill;
        }

        public int getIs_partake_full_reduce() {
            return is_partake_full_reduce;
        }

        public void setIs_partake_full_reduce(int is_partake_full_reduce) {
            this.is_partake_full_reduce = is_partake_full_reduce;
        }

        public int getIs_partake_coupon() {
            return is_partake_coupon;
        }

        public void setIs_partake_coupon(int is_partake_coupon) {
            this.is_partake_coupon = is_partake_coupon;
        }

        public int getSeckill_price() {
            return seckill_price;
        }

        public void setSeckill_price(int seckill_price) {
            this.seckill_price = seckill_price;
        }

        public int getSeckill_start_time() {
            return seckill_start_time;
        }

        public void setSeckill_start_time(int seckill_start_time) {
            this.seckill_start_time = seckill_start_time;
        }

        public int getSeckill_end_time() {
            return seckill_end_time;
        }

        public void setSeckill_end_time(int seckill_end_time) {
            this.seckill_end_time = seckill_end_time;
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

        public int getIs_update() {
            return is_update;
        }

        public void setIs_update(int is_update) {
            this.is_update = is_update;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
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

        public int getConsume_free_create_time() {
            return consume_free_create_time;
        }

        public void setConsume_free_create_time(int consume_free_create_time) {
            this.consume_free_create_time = consume_free_create_time;
        }

        public int getConsume_free_duration() {
            return consume_free_duration;
        }

        public void setConsume_free_duration(int consume_free_duration) {
            this.consume_free_duration = consume_free_duration;
        }

        public int getIs_consume_free() {
            return is_consume_free;
        }

        public void setIs_consume_free(int is_consume_free) {
            this.is_consume_free = is_consume_free;
        }

        public int getConsume_free_num() {
            return consume_free_num;
        }

        public void setConsume_free_num(int consume_free_num) {
            this.consume_free_num = consume_free_num;
        }

        public int getConsume_free_price() {
            return consume_free_price;
        }

        public void setConsume_free_price(int consume_free_price) {
            this.consume_free_price = consume_free_price;
        }

        public int getConsume_free_amount() {
            return consume_free_amount;
        }

        public void setConsume_free_amount(int consume_free_amount) {
            this.consume_free_amount = consume_free_amount;
        }

        public int getShare_free_create_time() {
            return share_free_create_time;
        }

        public void setShare_free_create_time(int share_free_create_time) {
            this.share_free_create_time = share_free_create_time;
        }

        public int getShare_free_duration() {
            return share_free_duration;
        }

        public void setShare_free_duration(int share_free_duration) {
            this.share_free_duration = share_free_duration;
        }

        public int getIs_share_free() {
            return is_share_free;
        }

        public void setIs_share_free(int is_share_free) {
            this.is_share_free = is_share_free;
        }

        public int getShare_free_num() {
            return share_free_num;
        }

        public void setShare_free_num(int share_free_num) {
            this.share_free_num = share_free_num;
        }

        public int getShare_free_price() {
            return share_free_price;
        }

        public void setShare_free_price(int share_free_price) {
            this.share_free_price = share_free_price;
        }

        public int getShare_free_amount() {
            return share_free_amount;
        }

        public void setShare_free_amount(int share_free_amount) {
            this.share_free_amount = share_free_amount;
        }

        public int getIs_accumulate_free() {
            return is_accumulate_free;
        }

        public void setIs_accumulate_free(int is_accumulate_free) {
            this.is_accumulate_free = is_accumulate_free;
        }

        public int getAccumulate_free_need_num() {
            return accumulate_free_need_num;
        }

        public void setAccumulate_free_need_num(int accumulate_free_need_num) {
            this.accumulate_free_need_num = accumulate_free_need_num;
        }

        public int getAccumulate_free_get_num() {
            return accumulate_free_get_num;
        }

        public void setAccumulate_free_get_num(int accumulate_free_get_num) {
            this.accumulate_free_get_num = accumulate_free_get_num;
        }

        public int getInventory_num() {
            return inventory_num;
        }

        public void setInventory_num(int inventory_num) {
            this.inventory_num = inventory_num;
        }

        public int getCurrent_num() {
            return current_num;
        }

        public void setCurrent_num(int current_num) {
            this.current_num = current_num;
        }

        public int getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(int original_price) {
            this.original_price = original_price;
        }

        public int getOver_time() {
            return over_time;
        }

        public void setOver_time(int over_time) {
            this.over_time = over_time;
        }

        public int getSurplus_num() {
            return surplus_num;
        }

        public void setSurplus_num(int surplus_num) {
            this.surplus_num = surplus_num;
        }

        public int getGoods_activity_type() {
            return goods_activity_type;
        }

        public void setGoods_activity_type(int goods_activity_type) {
            this.goods_activity_type = goods_activity_type;
        }

        public List<?> getNature() {
            return nature;
        }

        public void setNature(List<?> nature) {
            this.nature = nature;
        }
    }
}
