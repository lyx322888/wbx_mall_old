package com.wbx.mall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wushenghui on 2017/8/25.
 */

public class CookBookDetailInfo implements Serializable {

    /**
     * id : 26604
     * classid : 303
     * name : 糖醋里脊
     * peoplenum : 1-2人
     * preparetime : 30分钟-1小时
     * cookingtime : 10-20分钟
     * content : 糖醋里脊，在浙菜，川菜、清真菜，鲁菜里都有此菜，特点是色泽鲜亮，皮酥肉嫩，外面酸酸甜甜，里面肉香浓郁。此菜用来接待客人最能体现主人的好客和热情了。
     * pic : http://api.jisuapi.com/recipe/upload/20160719/180352_49535.jpg
     * tag : 下饭菜,私房菜,糖醋味
     * material : [{"mname":"鸡蛋","type":"0","amount":"1个"},{"mname":"香葱","type":"0","amount":"适量"},{"mname":"生姜","type":"0","amount":"适量"},{"mname":"面粉","type":"0","amount":"适量"},{"mname":"淀粉","type":"0","amount":"适量"},{"mname":"花生油","type":"0","amount":"200ml "},{"mname":"盐","type":"0","amount":"适量"},{"mname":"绍酒","type":"0","amount":"15g"},{"mname":"醋","type":"0","amount":"25g"},{"mname":"酱油","type":"0","amount":"25g "},{"mname":"白糖","type":"0","amount":"25g "},{"mname":"香油","type":"0","amount":"少许"},{"mname":"芝麻","type":"0","amount":"适量 "},{"mname":"里脊肉","type":"1","amount":"200g "}]
     * process : [{"pcontent":"里脊肉切成条。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121656_69633.jpg"},{"pcontent":"加盐、绍酒、酱油、淀粉用手抓均腌制10分钟。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121659_30619.jpg"},{"pcontent":"葱白、生姜切末备用。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121659_50830.jpg"},{"pcontent":"酱油、白糖、绍酒、醋、湿淀粉、水调成糖醋汁待用。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121659_73229.jpg"},{"pcontent":"鸡蛋打入碗中，加入淀粉、面粉少许水调成面糊。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121659_18862.jpg"},{"pcontent":"将里脊肉放到面糊里挂糊。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121659_74707.jpg"},{"pcontent":"炒锅置中火烧热，下油烧至六成热时，将挂好糊的肉块逐一入锅煎炸。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121659_62022.jpg"},{"pcontent":"待肉微焦捞出。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121659_55497.jpg"},{"pcontent":"把油锅里的残渣捞干净，待油温升至七成热时，复炸至表面金黄。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121659_51757.jpg"},{"pcontent":"捞出沥干油。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121700_64296.jpg"},{"pcontent":"锅内留底油，爆香姜葱末。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121703_67931.jpg"},{"pcontent":"下入炸好的里脊肉。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121703_46551.jpg"},{"pcontent":"烹入糖醋汁翻炒，使芡汁均匀地包住肉块。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121703_32314.jpg"},{"pcontent":"待糖醋汁变浓，加入香油、撒上黑芝麻即可出锅。","pic":"http://api.jisuapi.com/recipe/upload/20160803/121704_74525.jpg"}]
     */

    private String id;
    private String classid;
    private String name;
    private String peoplenum;
    private String preparetime;
    private String cookingtime;
    private String content;
    private String pic;
    private String tag;
    private List<MaterialBean> material;
    private List<ProcessBean> process;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeoplenum() {
        return peoplenum;
    }

    public void setPeoplenum(String peoplenum) {
        this.peoplenum = peoplenum;
    }

    public String getPreparetime() {
        return preparetime;
    }

    public void setPreparetime(String preparetime) {
        this.preparetime = preparetime;
    }

    public String getCookingtime() {
        return cookingtime;
    }

    public void setCookingtime(String cookingtime) {
        this.cookingtime = cookingtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<MaterialBean> getMaterial() {
        return material;
    }

    public void setMaterial(List<MaterialBean> material) {
        this.material = material;
    }

    public List<ProcessBean> getProcess() {
        return process;
    }

    public void setProcess(List<ProcessBean> process) {
        this.process = process;
    }

    public static class MaterialBean implements  Serializable{
        /**
         * mname : 鸡蛋
         * type : 0
         * amount : 1个
         */

        private String mname;
        private String type;
        private String amount;

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }

    public static class ProcessBean implements Serializable{
        /**
         * pcontent : 里脊肉切成条。
         * pic : http://api.jisuapi.com/recipe/upload/20160803/121656_69633.jpg
         */

        private String pcontent;
        private String pic;

        public String getPcontent() {
            return pcontent;
        }

        public void setPcontent(String pcontent) {
            this.pcontent = pcontent;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
