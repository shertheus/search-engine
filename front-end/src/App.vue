<template>
    <div id="app" :nowpage="nowpage">
        <panel v-if="nowpage==0" v-on:childByValue="childByValue" v-on:searchresult="searchresult" v-on:searchrelationresult="searchrelationresult"></panel>
        <show ref="sayshow" v-if="nowpage==1" v-on:childByValue="childByValue" v-on:searchresult="searchresult"  v-on:searchId="searchId"></show>
        <entity ref="sendid" v-if="nowpage==2" v-on:childByValue="childByValue"></entity>
        <relation ref="sendrr" v-if="nowpage==3" v-on:childByValue="childByValue" v-on:searchId="searchId"></relation>
    </div>
</template>
<script>
import panel from './components/search-panel.vue';
import show from './components/showPage.vue';
import entity from './components/entityPage.vue';
import relation from './components/relation.vue';

export default {
    name: 'App',
    components: {
        panel,
        show,
        entity,
        relation,
    },
    data: function() {
        return {
            nowpage: 0,
            sr: "",
            id: "",
            rr:"",
            RR:"",
        }
    },
    methods: {
        childByValue: function(childValue,lastpage) {
            this.nowpage = childValue
            if (childValue == 1) {
                this.$nextTick(() => {
                    this.$refs.sayshow.refushShow(this.sr)
                });
            }
            if (childValue == 2) {
                this.$nextTick(() => {
                    this.$refs.sendid.refrush(this.id,lastpage)
                });
            }
            if (childValue == 3) {
                this.$nextTick(() => {
                    this.$refs.sendrr.searchR(this.rr,this.RR)
                });
            }
        },
        searchresult: function(searchresult) {
            this.sr = searchresult
        },
        searchId: function(id) {
            this.id = id;
        },
        searchrelationresult:function(Relation,relation){
            this.rr = Relation
            this.RR = relation
        }
    }
}
</script>
<style>
html {
    height: 100%;
}

body {
    background: url('./assets/123.jpg') center;
    ;
    /*width: 100%;*/
    /*height: 100%;*/
    /*position: fixed;*/
    background-size: 100% 100%;
}

#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
}
</style>