<template>
	<div>
		<div class="main-logo">
			<img :src="imgSrc" alt="logo">
		</div>
		<div class="search-input">
			<input type="text" v-model="keyword" placeholder="输入关键词" @keydown.enter="search()" @input="input_watch">
			<span class="search-reset" v-show="this.span_dis" @click="clearInput()">&times;</span>
			<button class="search-btn" @click="search()">search</button>
			<select class="search-type" v-model="searchType">
				<option v-for="x in typelist" :key="x.id">{{x.ctype}}</option>
			</select>
		</div>
		<el-row :gutter="20"  style="background-color: rgb(220, 245, 175); border: 1px solid black;width:900px;margin-top: 100px;margin-right:auto;margin-left:auto;">
			<el-row :gutter="20"  style="background-color: mediumseagreen; border: 1px solid black;width:800px;margin-top: 40px;margin-right:auto;margin-left:auto;">
			<el-col :span="6"><div  style="display:flex;width:80px;margin-right:auto;margin-left:auto; margin-top: 10px;margin-bottom: 10px; font-size: 28px;color: black">word1:</div></el-col>
			<el-col :span="10">
				<div  style="display:flex;width:200px;margin-right:auto;margin-left:auto; margin-top: 6px; font-size: 28px;color: black">
					<el-input v-model="word1"  placeholder="请输入内容"></el-input></div></el-col>
			<el-col :span="6">
				<div style="display:flex;width:100px;margin-right:auto;margin-left:auto; margin-top: 6px; font-size: 28px;color: black">
					<select class="search-type2" v-model="searchType1">
					<option v-for="x in typelist" :key="x.id">{{x.ctype}}</option>
					</select>	
				</div>
			</el-col>
		</el-row>
		<el-row :gutter="20"  style="background-color: mediumseagreen; border: 1px solid black;width:800px;margin-top: 10px;margin-right:auto;margin-left:auto;">
			<el-col :span="6"><div  style="display:flex;width:80px;margin-right:auto;margin-left:auto; margin-top: 10px;margin-bottom: 10px; font-size: 28px;color: black">word2:</div></el-col>
			<el-col :span="10">
				<div  style="display:flex;width:200px;margin-right:auto;margin-left:auto; margin-top: 6px; font-size: 28px;color: black">
					<el-input v-model="word2"  placeholder="请输入内容"></el-input></div></el-col>
			<el-col :span="6">
				<div style="display:flex;width:100px;margin-right:auto;margin-left:auto; margin-top: 6px; font-size: 28px;color: black">
					<select class="search-type2" v-model="searchType2">
					<option v-for="x in typelist" :key="x.id">{{x.ctype}}</option>
					</select>	
				</div>
			</el-col>
		</el-row>
		<el-row :gutter="20"  style="width:800px;margin-top: 10px;margin-right:auto;margin-left:auto;">
       <select class="search-type2" v-model="relation" style="margin-right: auto">
					<option v-for="x in Rlist" :key="x.id">{{x.ctype}}</option>
       </select>
       <button class="search-btn"  @click="Rsearch()" style="margin-left: 70%; margin-bottom: 20px; position: relative; ">搜索</button>
		</el-row>
		</el-row>
	</div>
</template>
<script>
import communication from "../utils/communication";
export default {
	name: "search-panel",
	data: function() {
		return {
			word1:'',
			word2:'',
			keyword: '',
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
      Rlist: [
				{ id: 0, ctype: "all" },
				{ id: 1, ctype: "related" },
				{ id: 2, ctype: "same" },
				{ id: 3, ctype: "subclass" },
          { id: 3, ctype: "instanceof" },
          { id: 4, ctype: "comment" },
          { id: 5, ctype: "supplement" },
          { id: 6, ctype: "property" },
			],
			searchType: "all",
			searchType1:"all",
			searchType2:"all",
      relation:"all",
			searchS: "2%%%id///bdi1569743%%type///instance%%name///北京北京%%url///https://baike.baidu.com/item/%E5%8C%97%E4%BA%AC%E5%8C%97%E4%BA%AC%2F1551988%%comment///《北京 北京》北京民谣歌手 郝云在2008年发布的专辑《郝云 北京》的主打歌曲，曾经流传于北京的大街小巷。%%%id///bdi1569747%%type///instance%%name///北京北京%%url///https://baike.baidu.com/item/%E5%8C%97%E4%BA%AC%E5%8C%97%E4%BA%AC%2F19210090%%comment///《北京北京》是G.E.M. 邓紫棋在 湖南卫视2016跨年晚会 开唱并于新年之际发布录音室单曲版本， 汪峰原唱、作词、作曲。   邓紫棋演唱版本由 Lupo Groinig重新编曲。%%%",
		}
	},
	methods: {
		search: function() {
			console.log("sending")
			if (this.keyword !== '') {
				console.log(this.keyword)
				// var message = {"title": "title", "content": "content", "user": "name", "timestamp": "time"};
				communication.components.testConnect(this.keyword, this.searchType, this.logging)

				// this.$emit('searchresult',this.searchS)


			}

		},
		Rsearch: function() {
			console.log("sending")
			if (this.word1 !== '' && this.word2 !== '') {
				console.log(this.word1+ " " +this.word2)
				communication.components.relationConnect(this.word1, this.word2,this.searchType1, this.searchType2, this.sendRe)
			}
		},
		sendRe: function(result) {
			console.log("rback")
			console.log(result)
			this.$emit('searchrelationresult', result,this.relation)
			this.$emit('childByValue', 3);
		},
		clearInput: function() {
			this.keyword = '';
		},
		input_watch: function() {
			this.span_dis = this.keyword !== '';
		},

		logging: function(result) {
			console.log("back")
			console.log(result)
			this.searchS = result
			this.$emit('searchresult', this.searchS)
			this.$emit('childByValue', 1);
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

.search-reset {
	width: 21px;
	height: 21px;
	position: absolute;
	display: block;
	line-height: 21px;
	text-align: center;
	cursor: pointer;
	font-size: 20px;
	right: 35%;
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
.search-type2 {
	height: 40px;
	width: 120px;
	border: 1px solid rgb(157, 235, 14);
	background-color: rgb(157, 235, 14);
	color: white;
	font-size: 16px;
	font-weight: bold;
	float: left;
	cursor: pointer
}
</style>