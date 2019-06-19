package com.wbx.mall.bean;

import java.util.List;

/**
 * @author Zero
 * @date 2018/5/23 0023
 */
public class NoticeBean {

    /**
     * msg : 成功
     * state : 1
     * data : [[{"msg_id":717,"receive_user_id":13620,"from_user_id":13614,"msg":"您在微百姓（果然鲜果园）购买的商品商家已发货","title":"订单消息","create_time":1524552547,"status":2,"msg_type":1,"order_id":4178,"order_type":1,"photo":"http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I","day":"2018-04-24"}],[{"msg_id":685,"receive_user_id":13620,"from_user_id":5249,"msg":"您在微百姓（老猪高黑土猪肉专卖店）购买的商品商家已退款","title":"订单消息","create_time":1524463570,"status":5,"msg_type":1,"order_id":4160,"order_type":1,"photo":"http://imgs.wbx365.com/FmAB36NcaHMc9TE4SNvuGRGlMPNs","day":"2018-04-23"},{"msg_id":684,"receive_user_id":13620,"from_user_id":5249,"msg":"您在微百姓（老猪高黑土猪肉专卖店）购买的商品商家已退款","title":"订单消息","create_time":1524463569,"status":5,"msg_type":1,"order_id":4162,"order_type":1,"photo":"http://imgs.wbx365.com/FmAB36NcaHMc9TE4SNvuGRGlMPNs","day":"2018-04-23"},{"msg_id":683,"receive_user_id":13620,"from_user_id":5249,"msg":"您在微百姓（老猪高黑土猪肉专卖店）购买的商品商家已退款","title":"订单消息","create_time":1524463568,"status":5,"msg_type":1,"order_id":4163,"order_type":1,"photo":"http://imgs.wbx365.com/FmAB36NcaHMc9TE4SNvuGRGlMPNs","day":"2018-04-23"},{"msg_id":682,"receive_user_id":13620,"from_user_id":5249,"msg":"您在微百姓（老猪高黑土猪肉专卖店）购买的商品商家已退款","title":"订单消息","create_time":1524463565,"status":5,"msg_type":1,"order_id":4164,"order_type":1,"photo":"http://imgs.wbx365.com/FmAB36NcaHMc9TE4SNvuGRGlMPNs","day":"2018-04-23"},{"msg_id":681,"receive_user_id":13620,"from_user_id":5249,"msg":"您在微百姓（老猪高黑土猪肉专卖店）购买的商品商家已退款","title":"订单消息","create_time":1524463561,"status":5,"msg_type":1,"order_id":4165,"order_type":1,"photo":"http://imgs.wbx365.com/FmAB36NcaHMc9TE4SNvuGRGlMPNs","day":"2018-04-23"}],[{"msg_id":546,"receive_user_id":13620,"from_user_id":5249,"msg":"您在微百姓（老猪高黑土猪肉专卖店）购买的商品商家已发货","title":"订单消息","create_time":1524032674,"status":2,"msg_type":1,"order_id":4142,"order_type":1,"photo":"http://imgs.wbx365.com/FmAB36NcaHMc9TE4SNvuGRGlMPNs","day":"2018-04-18"},{"msg_id":545,"receive_user_id":13620,"from_user_id":5249,"msg":"您在微百姓（老猪高黑土猪肉专卖店）购买的商品商家已退款","title":"订单消息","create_time":1524032662,"status":5,"msg_type":1,"order_id":4141,"order_type":1,"photo":"http://imgs.wbx365.com/FmAB36NcaHMc9TE4SNvuGRGlMPNs","day":"2018-04-18"},{"msg_id":542,"receive_user_id":13620,"from_user_id":13556,"msg":"您在微百姓（大海捞真（野生海鲜））购买的商品商家拒绝退款","title":"订单消息","create_time":1524032026,"status":6,"msg_type":1,"order_id":2980,"order_type":2,"photo":"http://imgs.wbx365.com/15160001114_1517824179.jpg","day":"2018-04-18"},{"msg_id":540,"receive_user_id":13620,"from_user_id":13556,"msg":"您在微百姓（大海捞真（野生海鲜））购买的商品商家拒绝退款","title":"订单消息","create_time":1524031848,"status":6,"msg_type":1,"order_id":2980,"order_type":2,"photo":"http://imgs.wbx365.com/15160001114_1517824179.jpg","day":"2018-04-18"},{"msg_id":538,"receive_user_id":13620,"from_user_id":13556,"msg":"您在微百姓（大海捞真（野生海鲜））购买的商品商家已发货","title":"订单消息","create_time":1524031804,"status":2,"msg_type":1,"order_id":2980,"order_type":2,"photo":"http://imgs.wbx365.com/15160001114_1517824179.jpg","day":"2018-04-18"},{"msg_id":535,"receive_user_id":13620,"from_user_id":13556,"msg":"您在微百姓（大海捞真（野生海鲜））购买的商品商家已发货","title":"订单消息","create_time":1524031663,"status":2,"msg_type":1,"order_id":2979,"order_type":2,"photo":"http://imgs.wbx365.com/15160001114_1517824179.jpg","day":"2018-04-18"},{"msg_id":530,"receive_user_id":13620,"from_user_id":13614,"msg":"您在微百姓（果然鲜果园）购买的商品商家已发货","title":"订单消息","create_time":1524031435,"status":2,"msg_type":1,"order_id":4152,"order_type":1,"photo":"http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I","day":"2018-04-18"},{"msg_id":528,"receive_user_id":13620,"from_user_id":13614,"msg":"您在微百姓（果然鲜果园）购买的商品商家已退款","title":"订单消息","create_time":1524031390,"status":5,"msg_type":1,"order_id":4149,"order_type":1,"photo":"http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I","day":"2018-04-18"},{"msg_id":527,"receive_user_id":13620,"from_user_id":13614,"msg":"您在微百姓（果然鲜果园）购买的商品商家拒绝退款","title":"订单消息","create_time":1524031371,"status":6,"msg_type":1,"order_id":4149,"order_type":1,"photo":"http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I","day":"2018-04-18"},{"msg_id":526,"receive_user_id":13620,"from_user_id":13614,"msg":"您在微百姓（果然鲜果园）购买的商品商家已退款","title":"订单消息","create_time":1524031316,"status":5,"msg_type":1,"order_id":4151,"order_type":1,"photo":"http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I","day":"2018-04-18"},{"msg_id":525,"receive_user_id":13620,"from_user_id":13614,"msg":"您在微百姓（果然鲜果园）购买的商品商家已发货","title":"订单消息","create_time":1524031071,"status":2,"msg_type":1,"order_id":4151,"order_type":1,"photo":"http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I","day":"2018-04-18"},{"msg_id":523,"receive_user_id":13620,"from_user_id":13614,"msg":"您在微百姓（果然鲜果园）购买的商品商家已退款","title":"订单消息","create_time":1524030998,"status":5,"msg_type":1,"order_id":4150,"order_type":1,"photo":"http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I","day":"2018-04-18"},{"msg_id":520,"receive_user_id":13620,"from_user_id":13614,"msg":"您在微百姓（果然鲜果园）购买的商品商家已发货","title":"订单消息","create_time":1524025041,"status":2,"msg_type":1,"order_id":4149,"order_type":1,"photo":"http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I","day":"2018-04-18"},{"msg_id":518,"receive_user_id":13620,"from_user_id":13614,"msg":"您在微百姓（果然鲜果园）购买的商品商家已退款","title":"订单消息","create_time":1524024990,"status":5,"msg_type":1,"order_id":4148,"order_type":1,"photo":"http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I","day":"2018-04-18"},{"msg_id":517,"receive_user_id":13620,"from_user_id":13614,"msg":"您在微百姓（果然鲜果园）购买的商品商家已发货","title":"订单消息","create_time":1524024882,"status":2,"msg_type":1,"order_id":4148,"order_type":1,"photo":"http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I","day":"2018-04-18"},{"msg_id":514,"receive_user_id":13620,"from_user_id":5249,"msg":"您在微百姓（老猪高黑土猪肉专卖店）购买的商品商家已发货","title":"订单消息","create_time":1524023861,"status":2,"msg_type":1,"order_id":4141,"order_type":1,"photo":"http://imgs.wbx365.com/FmAB36NcaHMc9TE4SNvuGRGlMPNs","day":"2018-04-18"},{"msg_id":512,"receive_user_id":13620,"from_user_id":5249,"msg":"您在微百姓（老猪高黑土猪肉专卖店）购买的商品商家已退款","title":"订单消息","create_time":1524023819,"status":5,"msg_type":1,"order_id":4140,"order_type":1,"photo":"http://imgs.wbx365.com/FmAB36NcaHMc9TE4SNvuGRGlMPNs","day":"2018-04-18"},{"msg_id":510,"receive_user_id":13620,"from_user_id":5249,"msg":"您在微百姓（老猪高黑土猪肉专卖店）购买的商品商家已退款","title":"订单消息","create_time":1524023785,"status":5,"msg_type":1,"order_id":4139,"order_type":1,"photo":"http://imgs.wbx365.com/FmAB36NcaHMc9TE4SNvuGRGlMPNs","day":"2018-04-18"}]]
     */

    private String msg;
    private int state;
    private List<List<DataBean>> data;

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

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * msg_id : 717
         * receive_user_id : 13620
         * from_user_id : 13614
         * msg : 您在微百姓（果然鲜果园）购买的商品商家已发货
         * title : 订单消息
         * create_time : 1524552547
         * status : 2
         * msg_type : 1
         * order_id : 4178
         * order_type : 1
         * photo : http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I
         * day : 2018-04-24
         */

        private int msg_id;
        private int receive_user_id;
        private int from_user_id;
        private String msg;
        private String title;
        private int create_time;
        private int status;
        private int msg_type;
        private int order_id;
        private int order_type;
        private String photo;
        private String day;

        public int getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(int msg_id) {
            this.msg_id = msg_id;
        }

        public int getReceive_user_id() {
            return receive_user_id;
        }

        public void setReceive_user_id(int receive_user_id) {
            this.receive_user_id = receive_user_id;
        }

        public int getFrom_user_id() {
            return from_user_id;
        }

        public void setFrom_user_id(int from_user_id) {
            this.from_user_id = from_user_id;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getMsg_type() {
            return msg_type;
        }

        public void setMsg_type(int msg_type) {
            this.msg_type = msg_type;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }
}
