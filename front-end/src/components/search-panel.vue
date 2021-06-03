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
	</div>
</template>
<script>
import communication from "../utils/communication";
export default {
	name: "search-panel",
	data: function() {
		return {
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
			searchType: "all",
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
</style>