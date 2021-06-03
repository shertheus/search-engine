<template>
    <div>
        <button class="retrun-btn" @click="return0()">返回首页</button>
        <div class="search-input">
            <input type="text" v-model="keyword" placeholder="输入关键词" @keydown.enter="search()" @input="input_watch">
            <span class="search-reset" v-show="this.span_dis" @click="clearInput()">&times;</span>
            <button class="search-btn" @click="search()">search</button>
            <select class="search-type" v-model="searchType">
                <option v-for="x in typelist" :key="x.id">{{x.ctype}}</option>
            </select>
        </div>
        <div id="message-list" style="margin-top:50px">
            <MessageBlock v-on:childByValue="childByValue" v-for="x in messageList" :key="x.id" v-bind:id="x.id" :name="x.name" :type="x.type" :url="x.url" :comment="x.comment">
            </MessageBlock>
        </div>
    </div>
</template>
<script>
import MessageBlock from './messageBlock.vue';
import communication from "../utils/communication";
export default {
    name: "search-panel",
    components: {
        MessageBlock
    },
    props: {
        messageList: {
            type: Array,
            default: () => new Array(1).fill({
                id: "bdc2189749",
                name: "北京",
                url: "https://baike.baidu.com/item/%E5%8C%97%E4%BA%AC/128981?fr=aladdin",
                comment:
                    // ""
                    "北京（Beijing）"
                    // "北京（Beijing），简称“京”，古称燕京、北平，是中华人民共和国的首都、直辖市、国家中心城市、超大城市，国务院批复确定的中国政治中心、文化中心、国际交往中心、科技创新中心 [1]  ，截至2020年，全市下辖16个区，总面积16410.54平方千米，常住人口2189.31万人 [129]  。城镇人口1865万人，城镇化率86.6%（2018年末数据） [2]  ，常住外来人口达794.3万人（2017年数据） [3]  。北京地处中国北部、华北平原北部，东与天津毗连，其余均与河北相邻，中心位置东经116°20′、北纬39°56′，是世界著名古都和现代化国际城市，也是中国共产党中央委员会、中华人民共和国中央人民政府和全国人民代表大会常务委员会的办公所在地"
                    ,
                type: "concept",
            })
        }
    },
    data: function() {
        return {
            keyword: "",
            imgSrc: require('../assets/logo.png'),
            span_dis: false,
            typelist: [
                { id: 0, ctype: "all" },
                { id: 1, ctype: "concept" },
                { id: 2, ctype: "instance" },
                { id: 3, ctype: "property" },
                { id: 4, ctype: "not concept" },
                { id: 5, ctype: "not instance" },
                { id: 6, ctype: "not property" }
            ],
            searchType: "all",
            searchS: "2%%%id///bdi1569743%%type///instance%%name///北京北京%%url///https://baike.baidu.com/item/%E5%8C%97%E4%BA%AC%E5%8C%97%E4%BA%AC%2F1551988%%comment///《北京 北京》北京民谣歌手 郝云在2008年发布的专辑《郝云 北京》的主打歌曲，曾经流传于北京的大街小巷。%%%id///bdi1569747%%type///instance%%name///北京北京%%url///https://baike.baidu.com/item/%E5%8C%97%E4%BA%AC%E5%8C%97%E4%BA%AC%2F19210090%%comment///《北京北京》是G.E.M. 邓紫棋在 湖南卫视2016跨年晚会 开唱并于新年之际发布录音室单曲版本， 汪峰原唱、作词、作曲。   邓紫棋演唱版本由 Lupo Groinig重新编曲。%%%",

        }
    },
    methods: {
        refushShow: function(newS) {
            this.searchS = newS
            this.clearShow()
            this.addShow()
        },
        return0: function() { //返回主页
            this.$emit('childByValue', 0)
        },
        childByValue: function(childValue, sid) { //block的按钮，进入具体页
            this.$emit('searchId', sid)
            this.$emit('childByValue', childValue)
        },
        search: function() {
            console.log("sending")
            if (this.keyword !== '') {
                console.log(this.keyword)
                // var message = {"title": "title", "content": "content", "user": "name", "timestamp": "time"};
                communication.components.testConnect(this.keyword, this.searchType, this.logging)

            }
        },
        clearInput: function() {
            this.keyword = '';
        },
        input_watch: function() {
            this.span_dis = this.keyword !== '';
        },

        logging: function(result) {
            console.log("logging in showPage")
            console.log(result)
            this.searchS = result
            this.clearShow()
            this.addShow()
        },

        clearShow: function() {
            if (this.messageList != null) {
                var len = this.messageList.length
                this.messageList.splice(0, len)
            }
        },
        addShow: function() { //更新搜索的内容
            var sl = this.searchS.split("%%%")
            for (let i = 1; i < sl.length - 1; i++) {
                var element = sl[i]
                var sll = element.split("%%")
                var nid = ""
                var nname = ""
                var nurl = ""
                var ncomment = ""
                var ntype = ""
                for (let j = 0; j < sll.length; j++) {
                    var tt = sll[j].split("///")
                    var type1 = tt[0]
                    var cont1 = tt[1]
                    switch (type1) {
                        case "id":
                            nid = cont1
                            break;
                        case "name":
                            nname = cont1
                            break;
                        case "url":
                            nurl = cont1
                            break;
                        case "comment":
                            ncomment = cont1
                            break;
                        case "type":
                            ntype = cont1
                            break;
                        default:
                            break;
                    }

                }
                this.messageList.push({
                    id: nid,
                    name: nname,
                    url: nurl,
                    comment: ncomment,
                    type: ntype,
                })
            }
        },
    }
}
</script>
<style scoped>
.search-input {
    height: 45px;
    width: 750px;
    margin: 10px auto 0;
    position: relative;
}

.search-input input {
    border: 1px solid #e4e4e4;
    box-sizing: border-box;
    width: 500px;
    height: 45px;
    font-size: 18px;
    float: left;
    padding-left: 10px;
    padding-right: 10px;
    overflow: hidden;
}

.search-btn {
    height: 45px;
    width: 100px;
    border: 1px solid mediumseagreen;
    background-color: mediumseagreen;
    color: white;
    font-size: 16px;
    font-weight: bold;
    float: left;
    cursor: pointer
}

.retrun-btn {
    height: 45px;
    width: 100px;
    border: 1px solid mediumseagreen;
    background-color: mediumseagreen;
    color: white;
    font-size: 16px;
    font-weight: bold;
    float: left;
    cursor: pointer
}

.search-reset {
    width: 21px;
    height: 21px;
    position: absolute;
    display: block;
    line-height: 21px;
    text-align: center;
    cursor: pointer;
    font-size: 20px;
    right: 110px;
    top: 12px
}

.search-type {
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
</style>