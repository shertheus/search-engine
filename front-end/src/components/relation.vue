<template>
    <div>
        <el-row>
            <el-button class="retrun-btn" @click="return0()">返回首页</el-button>
        </el-row>
        <div v-if="this.list.length===0" style="margin-bottom: auto;margin-top: 300px; font-size: 200px;"> 无</div>
        <p :list="list" v-for="x in list" :key="x.relation +x.id1+x.id2+x.name2">
            <el-row :gutter="20">
                <el-col :span="8">
                    <button class="id-btn" @click="searchId(id1)">
                        <div style="display:flex; margin-top: 3px; font-size: 20px;color: black">{{ x.name1 }} ({{ x.type1 }})</div>
                        <div style="display:flex; margin-top: 3px; font-size: small;color: black">{{ x.comment1 }}</div>
                    </button></el-col>
                <el-col :span="6">
                    <div style="display:flex; margin-top: 10px; font-size: 40px;color: black">
                        {{x.relation}}
                    </div>
                </el-col>
                <el-col :span="8"><button class="id-btn" @click="searchId(id2)">
                        <!-- {{x.name2}} -->
                        <div style="display:flex; margin-top: 3px; font-size: 20px;color: black">{{ x.name2 }} {{ (x.type2) }}</div>
                        <div style="display:flex; margin-top: 3px; font-size: small;color: black">{{ x.comment2 }}</div>
                    </button></el-col>
            </el-row>
        </p>
    </div>
</template>
<script>
export default {
    data: function() {
        return {
            listS: "15%%%supplement=-=id///bdi6315627%%type///instance%%name///老爷山%%url///https://baike.baidu.com/item/%E8%80%81%E7%88%B7%E5%B1%B1%2F6741651%%comment///邢台老爷山，本名北武当山，又名小武当山，因山上供奉武当老爷，故称老爷山，位于河北省 邢台 沙河市西部山区，主峰海拔1437.3米，俗称“老爷山”，距市区60公里，是河北省重点风景名胜区。=-=省%%%comment=-=id///bdi6315627%%type///instance%%name///老爷山%%url///https://baike.baidu.com/item/%E8%80%81%E7%88%B7%E5%B1%B1%2F6741651%%comment///邢台老爷山，本名北武当山，又名小武当山，因山上供奉武当老爷，故称老爷山，位于河北省 邢台 沙河市西部山区，主峰海拔1437.3米，俗称“老爷山”，距市区60公里，是河北省重点风景名胜区。=-=省%%%supplement=-=id///bdi6315644%%type///instance%%name///老爷庙%%url///https://baike.baidu.com/item/%E8%80%81%E7%88%B7%E5%BA%99%2F47149%%comment///老爷庙，旧为 龙王庙。位于 江西省 九江市 都昌县 多宝乡 龙头山首，与 庐山市隔 鄱阳湖相望。建庙久远，该庙基以 花岗石条堆砌7米高，右侧有阶梯曲折而上，庙群总面积为600多平方米，分主庙、龙王殿、同仁堂、大小客厅、厨房6部分，附属建筑分布主庙两侧。::;老爷庙装饰精微，构思巧妙，散发出中华民族传统文化的精神、气质、神韵。主庙面积为300多平方米，高9米，面宽14.2米，进深26.8米，系穿斗与架梁式混合结构，共52个立柱。庙内由上中下三部分组成，上部为正殿，中部为游楼，下部为万年台。庙内门窗梁坊雕刻花纹并涂以丹漆。1983年县政府又进行了修缮。为江西省省级保护文物。::;自古以来，这里翻沉了无数的船只，出现了一系列令人们弄不明白的怪谜。老爷庙附近的 鄱阳湖水域号称“东方丧命地域”，中国“ 魔鬼三角”的鄱阳湖“ 百慕大”。::;当地人把这死亡之地叫“ 老爷庙水域”。据悉，在近100多年时间里，有上千多艘船只在此沉没和离奇失踪，令人百思不解，认为有一种违反物理定律超自然神秘力量存在着   。=-=省%%%comment=-=id///bdi6315644%%type///instance%%name///老爷庙%%url///https://baike.baidu.com/item/%E8%80%81%E7%88%B7%E5%BA%99%2F47149%%comment///老爷庙，旧为 龙王庙。位于 江西省 九江市 都昌县 多宝乡 龙头山首，与 庐山市隔 鄱阳湖相望。建庙久远，该庙基以 花岗石条堆砌7米高，右侧有阶梯曲折而上，庙群总面积为600多平方米，分主庙、龙王殿、同仁堂、大小客厅、厨房6部分，附属建筑分布主庙两侧。::;老爷庙装饰精微，构思巧妙，散发出中华民族传统文化的精神、气质、神韵。主庙面积为300多平方米，高9米，面宽14.2米，进深26.8米，系穿斗与架梁式混合结构，共52个立柱。庙内由上中下三部分组成，上部为正殿，中部为游楼，下部为万年台。庙内门窗梁坊雕刻花纹并涂以丹漆。1983年县政府又进行了修缮。为江西省省级保护文物。::;自古以来，这里翻沉了无数的船只，出现了一系列令人们弄不明白的怪谜。老爷庙附近的 鄱阳湖水域号称“东方丧命地域”，中国“ 魔鬼三角”的鄱阳湖“ 百慕大”。::;当地人把这死亡之地叫“ 老爷庙水域”。据悉，在近100多年时间里，有上千多艘船只在此沉没和离奇失踪，令人百思不解，认为有一种违反物理定律超自然神秘力量存在着   。=-=省%%%supplement=-=id///bdi6315646%%type///instance%%name///老爷庙%%url///https://baike.baidu.com/item/%E8%80%81%E7%88%B7%E5%BA%99%2F6376778%%comment///关王庙又名关帝庙，俗称老爷庙，位于 阳泉市郊区白泉乡 林里村南玉泉山腰。关王庙建成已有900多年，据有关资料证实，是我国最早的关羽庙建筑。关王庙以关王正殿为主，由 献殿、 马殿、南北配殿，以及外院的戏台、大门、忠恕牌坊、落箭亭、饮马亭等，组成了一座错落有致，气势雄伟，左右对峙，布局合理，并且具有一定历史、艺术、科学价值的宋代及明、清木构建筑群体，为 全国重点文物保护单位。=-=省%%%",
            list: [],


            id1: "",
            name1: "",
            type1: "",
            url1: "",
            comment1: "",
            id2: "",
            name2: "",
            type2: "",
            url2: "",
            comment2: "",

            needRelation:"",
        }
    },
    methods: {
        return0: function() {
            this.$emit('childByValue', 0);
        },
        searchId: function(sid) {
            this.$emit('searchId', sid)
            this.$emit('childByValue', 2, 3)
        },
        searchR: function(result,RR) {
            this.list.splice(0, this.list.length)
            this.needRelation = RR
            this.listS = result
            this.addList()
        },
        addList: function() {
            var elist = this.listS.split("%%%")
            for (let i = 1; i < elist.length - 1; i++) {
                var element = elist[i].split("=-=")
                this.id1 = ""
                this.name1 = ""
                this.type1 = ""
                this.url1 = ""
                this.comment1 = ""
                this.id2 = ""
                this.name2 = ""
                this.type2 = ""
                this.url2 = ""
                this.comment2 = ""
                this.relation = ""
                for (let j = 0; j < element.length; j++) {
                    var e2 = element[j]
                    if (j == 0) {
                        this.relation = e2
                    }
                    if (j == 1) {
                        var en = e2.split("%%")
                        for (let k = 0; k < en.length; k++) {
                            switch (en[k].split("///")[0]) {
                                case "id":
                                    this.id1 = en[k].split("///")[1]
                                    break;
                                case "name":
                                    this.name1 = en[k].split("///")[1]
                                    break;
                                case "type":
                                    this.type1 = en[k].split("///")[1]
                                    break;
                                case "url":
                                    this.url1 = en[k].split("///")[1]
                                    break;
                                case "comment":
                                    this.comment1 = en[k].split("///")[1]
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    if (j == 2) {
                        en = e2.split("%%")
                        for (let k = 0; k < en.length; k++) {
                            switch (en[k].split("///")[0]) {
                                case "id":
                                    this.id2 = en[k].split("///")[1]
                                    break;
                                case "name":
                                    this.name2 = en[k].split("///")[1]
                                    break;
                                case "type":
                                    this.type2 = en[k].split("///")[1]
                                    break;
                                case "url":
                                    this.url2 = en[k].split("///")[1]
                                    break;
                                case "comment":
                                    this.comment2 = en[k].split("///")[1]
                                    break;
                                default:
                                    this.name2 = en
                                    break;
                            }
                        }
                    }
                }
                if(this.needRelation == "all" || this.needRelation == this.relation) {
                  this.list.push({
                    relation: this.relation,
                    id1: this.id1,
                    name1: this.name1,
                    type1: this.type1,
                    url1: this.url1,
                    comment1: this.comment1,
                    id2: this.id2,
                    name2: this.name2,
                    type2: this.type2,
                    url2: this.url2,
                    comment2: this.comment2,
                  })
                }

            }
        }

    }
}
</script>
<style scoped>
.retrun-btn {
    height: 45px;
    width: 120px;
    border: 1px solid mediumseagreen;
    background-color: mediumseagreen;
    color: white;
    font-size: 16px;
    font-weight: bold;
    float: left;
    cursor: pointer
}

.id-btn {
    float: left;
    text-align: left;
    border: 1px solid rgb(234, 241, 237);
    background-color: unset;
    color: rgb(19, 1, 1);
    font-size: 16px;
    font-weight: bold;
    margin-top: 5px;
}
</style>