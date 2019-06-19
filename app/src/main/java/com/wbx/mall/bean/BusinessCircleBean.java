package com.wbx.mall.bean;

import java.util.ArrayList;

/**
 * @author Zero
 * @date 2018/6/15
 */
public class BusinessCircleBean {
    /**
     * text : 噢力给
     * create_time : 2018-06-15 15:10:02
     * photos : ["http://imgs.wbx365.com/Fif8wxW5xV_HMNKa-PNRduQt8hRV","http://imgs.wbx365.com/Fs_9OUn_D6RaI-dqrKiFg6KdmEtH","http://imgs.wbx365.com/Fsm7VZv-g2X-hm_OgyyQb92GajxU"]
     * shop_id : 1443
     * shop_name : 果然鲜果园
     * photo : http://imgs.wbx365.com/FtQs-hcgannG5Qo7jPgxKb-55K9I
     * lat : 24.479874
     * lng : 118.108982
     * is_favorites : 0
     * share_url :
     */

    private String text;
    private String create_time;
    private String addr;
    private int shop_id;
    private int grade_id;
    private String shop_name;
    private String photo;
    private double lat;
    private double lng;
    private int is_favorites;
    private String share_url;
    private ArrayList<String> photos;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(int grade_id) {
        this.grade_id = grade_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public int getIs_favorites() {
        return is_favorites;
    }

    public void setIs_favorites(int is_favorites) {
        this.is_favorites = is_favorites;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }
}
