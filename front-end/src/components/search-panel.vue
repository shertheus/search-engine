<template>
  <div>
    <div class="main-logo">
      <img :src="imgSrc" alt="logo">
    </div>

    <div class="search-input">
      <input type="text" v-model="keyword" placeholder="输入关键词" @keydown.enter="search()" @input="input_watch">
      <span class="search-reset" v-show="this.span_dis" @click="clearInput()">&times;</span>
      <button class="search-btn" @click="search()">search</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "search-panel",
  data: function() {
    return {
      keyword: '',
      imgSrc: require('../assets/logo.png'),
      searchSrc: 'https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd=',
      span_dis: false,
    }
  },
  methods:{
    search: function() {
      window.open(this.searchSrc + this.keyword);
    },
    clearInput: function() {
      this.keyword = '';
    },
    input_watch: function () {
      this.span_dis = this.keyword !== '';
    }
  }
}
</script>

<style scoped>
.main-logo {
  position: relative;
  margin-top: 10%;
}
.main-logo img {
  display: block;
  margin: 0 auto;
}
.search-input {
  height: 45px;
  width: 600px;
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
</style>