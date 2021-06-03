<template>
    
    <button class="ans-btn" @click="entity()" style="margin-top:1px">
        <div style="display:flex; margin-top: 3px; font-size: 20px;color: black">{{ name }} ({{ type }})</div>
            <div style="display:flex; margin-top: 3px; font-size: small;color: black">{{ url }}</div>
            <div style="display:flex; margin-top: 3px; font-size: small;color: black">{{ comment }}</div>
    </button>
</template>
<script>
    export default {
        name: "MessageBlock",
        props: {
            id: {
                type:String,
                default: () => "bdc218974981"
            },
            name: {
                type:String,
                default: () => "北京"
            },
            url: {
                type:String,
                default: () => "https://baike.baidu.com/item/%E5%8C%97%E4%BA%AC/128981?fr=aladdin"
            },
            comment: {
                type:String,
            //    default: () => "北京（Beijing）"
                default: () => "北京（Beijing），简称“京”，古称燕京、北平，是中华人民共和国的首都、直辖市、国家中心城市、超大城市，国务院批复确定的中国政治中心、文化中心、国际交往中心、科技创新中心 [1]  ，截至2020年，全市下辖16个区，总面积16410.54平方千米，常住人口2189.31万人 [129]  。城镇人口1865万人，城镇化率86.6%（2018年末数据） [2]  ，常住外来人口达794.3万人（2017年数据） [3]  。北京地处中国北部、华北平原北部，东与天津毗连，其余均与河北相邻，中心位置东经116°20′、北纬39°56′，是世界著名古都和现代化国际城市，也是中国共产党中央委员会、中华人民共和国中央人民政府和全国人民代表大会常务委员会的办公所在地"
            },
            type: {
                type:String,
                default: () => "concept"
            },
            ss: {
                type:String,
                default: () => "dasdfsdfas"
            },
        },

        methods:{
            entity:function(){
                this.$emit('childByValue', 2,this.id);
            }
        }
    
    }
</script>
<style scoped>
.ans-btn{
  width: 99%;
  float: left;
  text-align: left;
  /* background-color: unset; */
}
</style>