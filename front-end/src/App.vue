<template>
  
  <div id="app" :nowpage="nowpage">
    
    {{nowpage}}
    <panel  v-if="nowpage==0" v-on:childByValue="childByValue" v-on:searchresult="searchresult"></panel>
    <show ref="sayshow" v-if="nowpage==1" v-on:childByValue="childByValue" v-on:searchId="searchId"></show>
    <entity ref="sendid" v-if="nowpage==2" v-on:childByValue="childByValue"></entity>
  </div>
</template>

<script>
import panel from './components/search-panel.vue';
import show from './components/showPage.vue';
import entity from './components/entityPage.vue';

export default {
  name: 'App',
  components: {
    panel,
    show,
    entity,
  },
  data: function() {
    return {
     nowpage : 0,
     sr: "",
     id:"",
    }
  },
  methods:{
    childByValue: function (childValue) {
        this.nowpage = childValue
        if (childValue == 1) {
            this.$nextTick(() => {
            this.$refs.sayshow.refushShow(this.sr)
          });
        }
        if (childValue == 2) {
            this.$nextTick(() => {
            this.$refs.sendid.refrush(this.id)
          });
        }
      },
      searchresult: function (searchresult) {
        this.sr = searchresult
      },
      searchId:function(id){
        this.id = id;
      }
  }
}

</script>

<style>
html {
  height: 100%;
}
body {
  background: url('./assets/background.png') center;;
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
