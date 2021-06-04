<template>
	<div>
		<el-row>
			<el-button class="retrun-btn" @click="return0()">返回首页</el-button>
			<el-button class="retrun-btn" @click="return1()">返回搜索页</el-button>
		</el-row>
		<el-row>
			<p class="baseinfo">{{name}}({{type}})</p>
		</el-row>
		<div v-if="type=='concept'">
			<el-row>
				<el-link class="url" :href=this.curl target="_blank">{{curl}}</el-link>
			</el-row>
			<el-row>
				<p class="obj" v-show="this.crelated.length!==0">related :</p>
<!--        <p v-if="this.crelated.length===0" style="float: left">  none</p>-->
				<p class="list" :crelated="crelated" v-for="x in crelated" :key="x.id" >
					<el-button class="entbut" @click="searchID(x.id)">{{x.ctype}}</el-button>
				</p>
			</el-row>
			<el-row>
				<p class="obj" v-show="this.csame.length!==0">same :</p>
				<p class="list" :csame="csame" v-for="x in csame" :key="x.id">
					<el-button class="entbut" @click="searchID(x.id)">{{x.ctype}}</el-button>
				</p>
			</el-row>
			<el-row>
				<p class="obj" v-show="this.cinstance.length!==0">instance :</p>
				<p class="list" :cinstance="cinstance" v-for="x in cinstance" :key="x.id">
					<el-button class="entbut" @click="searchID(x.id)">{{x.ctype}}</el-button>
				</p>
			</el-row>
			<el-row>
				<p class="obj" v-show="this.csub.length!==0">sub :</p>
				<p class="list" :csub="csub" v-for="x in csub" :key="x.id">
					<el-button class="entbut" @click="searchID(x.id)">{{x.ctype}}</el-button>
				</p>
			</el-row>
		</div>
		<div v-if="type==='instance'">
			<el-row>
				<el-link class="url" :href=this.iurl target="_blank">{{iurl}}</el-link>
			</el-row>
			<el-row>
				<p class="obj" v-show="this.icomment!==''">comment : {{icomment}}</p>
			</el-row>
			<!--            <el-row>-->
			<!--                <p class="obj">comment_ins :</p>-->
			<!--                <p class="list" :icomment_ins="icomment_ins" v-for="x in icomment_ins" :key="x.id">-->
			<!--                    <el-button class="entbut" @click="searchID(x.id)">{{x.itype}}</el-button>-->
			<!--                </p>-->
			<!--            </el-row>-->
			<el-row>
				<p class="obj" v-show="this.irelated.length!==0">related :</p>
				<p class="list" :irelated="irelated" v-for="x in irelated" :key="x.id">
					<el-button class="entbut" @click="searchID(x.id)">{{x.itype}}</el-button>
				</p>
			</el-row>
			<el-row>
				<p class="obj" v-show="this.isame.length!==0">same :</p>
				<p class="list" :isame="isame" v-for="x in isame" :key="x.id">
					<el-button class="entbut" @click="searchID(x.id)">{{x.itype}}</el-button>
				</p>
			</el-row>
			<el-row>
				<p class="obj" v-show="this.iproperty.length!==0">property :</p>
				<p class="list" :iproperty="iproperty" v-for="x in iproperty" :key="x.id1">
					<el-button class="entbut" @click="searchID(x.id1)">{{x.name1}}</el-button>:
					<el-button class="entbut" @click="searchID(x.id2)">{{x.name2}}</el-button>|
				</p>
			</el-row>
			<el-row>
				<p class="obj" v-show="this.ialias.length!==0">alias :</p>
				<p class="list" :ialias="ialias" v-for="x in ialias" :key="x.id">
					{{"\t[" + x.itype + "]\t"}}
				</p>
			</el-row>
			<el-row>
				<p class="obj" v-show="this.isupplement.length!==0">supplement :</p>
				<p class="list" :isupplement="isupplement" v-for="x in isupplement" :key="x.id">
					{{"\t[" + x.itype + "]\t"}}
				</p>
			</el-row>
		</div>
		<div v-if="type=='propert'">
			<p class="obj"> fullName : {{fullName}}</p>
		</div>
	</div>
</template>
<script>
import communication from "../utils/communication";
export default {
	data: function() {
		return {
			id: 'id',
			name: 'name',

			type: 'concept',

			//comcept
			crelated: [],
			csame: [],
			cinstance: [],
			csub: [],
			curl: '',

			//instance
			icomment: '',
			iurl: '',
			irelated: [],
			isame: [],
			iproperty: [],
			icomment_ins: [],
			ialias: [],
			isupplement: [],

			//propert
			fullName: '',

			searchresult: "",
			related: "",
			same: "",
			instance: "",
			sub: "",
			property: "",
			comment_ins: "",
			alias: "",
			supplement: "",

			rname: "",
			sname: "",
			subname: "",
			iname: "",
			pname: "",

      lastP:1,

		}
	},
	methods: {
		return0: function() {
			this.$emit('childByValue', 0);
		},
		return1: function() {
			this.$emit('childByValue', this.lastP)
		},
		searchID: function(id) { //搜索id对应的所有信息并填入searchresult

			if (id !== "null" && id !== "") {
				communication.components.entityConnect(id, this.entity_back)
				//  this.searchresult = "id///bdi1569743%%type///instance%%name///北京北京%%url///https://baike.baidu.com/item/%E5%8C%97%E4%BA%AC%E5%8C%97%E4%BA%AC%2F1551988%%related///[\"bdi1834586\", \"bdi1561325\", \"bdi5440238\", \"bdi514416\"]%%same///[]%%comment///《北京 北京》北京民谣歌手 郝云在2008年发布的专辑《郝云 北京》的主打歌曲，曾经流传于北京的大街小巷。%%supplement///[\"郝云演唱歌曲\"]%%alias///[]%%pro///[\"bdp80-[[bdi7339683|郝云]]\", \"bdp76-[[bdi7339683|郝云]]\", \"bdp122-[[bdi7339683|郝云]]\", \"bdp68-[[bdi7339683|郝云]]\", \"bdp16-北京北京\"]";

			} else {
				alert("cannot click")
			}
		},
		entity_back: function(result) {
			console.log("entity_back")
			console.log(result)
			this.searchresult = result
			this.updata()
		},
		refrush: function(nid,lastpage) {
      this.lastP = lastpage
			this.searchID(nid)
		},
		updata: function() {
			console.log("update")
			this.crelated.splice(0, this.crelated.length)
			this.csame.splice(0, this.csame.length)
			this.csub.splice(0, this.csub.length)
			this.cinstance.splice(0, this.cinstance.length)

			this.ialias.splice(0, this.ialias.length)
			this.isupplement.splice(0, this.isupplement.length)
			this.isame.splice(0, this.isame.length)
			this.irelated.splice(0, this.irelated.length)
			this.iproperty.splice(0, this.iproperty.length)
			this.related = ""
			this.same = ""
			this.instance = ""
			this.sub = ""
			this.property = ""
			this.comment_ins = ""
			this.alias = ""
			this.supplement = ""
			var baseinf = this.searchresult.split("%%")
			for (let i = 0; i < baseinf.length; i++) {
				var info = baseinf[i].split("///")
				var t = info[0]
				var con = info[1]

				switch (t) {
					case "id":
						this.id = con
						break;
					case "name":
						this.name = con
						break;
					case "type":
						this.type = con
						break;
					case "url":
						this.curl = con
						this.iurl = con
						break;
					case "comment":
						this.icomment = con
						break;
					case "related":
						this.related = con
						break;
					case "same":
						this.same = con
						break;
					case "instance":
						this.instance = con
						break;
					case "sub":
						this.sub = con
						break;
					case "pro":
						this.property = con
						break;
					case "comment_ins":
						this.comment_ins = con
						break;
					case "alias":
						this.alias = con
						break;
					case "supplement":
						this.supplement = con
						break;
					case "fullname":
						this.fullName = con
						break;
					case "sname":
						this.sname = con
						break;
					case "rname":
						this.rname = con
						break;
					case "subname":
						this.subname = con
						break;
					case "pname":
						this.pname = con
						break;
					case "iname":
						this.iname = con
						break;

					default:
						break;
				}
			}
			this.toList()
		},
		toList: function() {
			console.log("tolist")
			if (this.type === "concept") {
				var crr = this.related.match(/[0-9a-zA-Z]+/g)
				var crrname = this.rname.substring(1, this.rname.length - 1).split(", ")
				let count = 0
				if (crr != null) {
					crr.forEach(element => {
						this.crelated.push({
							id: element,
							ctype: crrname[count],
						})
						count += 1
					});
				}
				crr = this.same.match(/[0-9a-zA-Z]+/g)
				crrname = this.sname.substring(1, this.sname.length - 1).split(", ")
				count = 0
				if (crr != null) {
					crr.forEach(element => {
						this.csame.push({
							id: element,
							ctype: crrname[count],
						})
						count += 1
					});
				}

				crr = this.instance.match(/[0-9a-zA-Z]+/g)
				crrname = this.iname.substring(1, this.iname.length - 1).split(", ")
				count = 0
				if (crr != null) {
					crr.forEach(element => {
						this.cinstance.push({
							id: element,
							ctype: crrname[count],
						})
						count += 1
					});
				}
				crr = this.sub.match(/[0-9a-zA-Z]+/g)
				crrname = this.subname.substring(1, this.subname.length - 1).split(", ")
				count = 0
				if (crr != null) {
					crr.forEach(element => {
						this.csub.push({
							id: element,
							ctype: crrname[count],
						})
						count += 1
					});
				}
			}
			if (this.type === "instance") {
				var rr = this.related.match(/[0-9a-zA-Z]+/g)
				var crrname1 = this.rname.substring(1, this.rname.length - 1).split(", ")
				let count = 0
				if (rr != null) {
					rr.forEach(element => {
						this.irelated.push({
							id: element,
							itype: crrname1[count],
						})
						count += 1
					});
				}

				rr = this.same.match(/[0-9a-zA-Z]+/g)
				crrname1 = this.sname.substring(1, this.sname.length - 1).split(", ")
				count = 0
				if (rr != null) {
					rr.forEach(element => {
						this.isame.push({
							id: element,
							itype: crrname1[count],
						})
						count += 1
					});
				}
				rr = this.alias.match(/(?<=").*?(?=")/g)
				if (rr != null) {
					rr.forEach(element => {
						if (element !== ", ") {
							this.ialias.push({
								id: element,
								itype: element,
							})
						}
					});
				}
				rr = this.supplement.match(/(?<=").*?(?=")/g)
				if (rr != null) {
					rr.forEach(element => {
						if (element !== ", ") {
							this.isupplement.push({
								id: element,
								itype: element,
							})
						}
					});
				}
				rr = this.property.match(/(?<=").*?(?=")/g)
				crrname1 = this.pname.substring(1, this.pname.length - 1).split(", ")
				count = 0
				// console.log(rr)
				if (rr != null) {
					rr.forEach(element => {
						var idd = element.match(/[0-9a-zA-Z]+/g)
						// console.log(idd)
						if (idd != null) {
							if (idd.length === 2) {
								var nam2 = element.split("-")[1]
								this.iproperty.push({
									id1: idd[0],
									name1: crrname1[count],
									id2: idd[1],
									name2: nam2, //TODO
								})
								count += 1
							} else {
								this.iproperty.push({
									id1: idd[0],
									name1: crrname1[count],
									id2: "null",
									name2: element.split("-")[1],
								})
								count += 1
							}
						}
					});
				}
			}
		},
	},
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

.baseinfo {
	float: left;
	font-family: PingFang SC;
	font-size: 50px;
	margin-bottom: 10px;
}

.url {
	float: left;
	margin-bottom: 10px;
}

.list {
	float: left;
	margin-bottom: 10px;
	font-size: 20px;
}

.obj {
	float: left;
	margin-bottom: 50px;
	font-size: 20px;
}

.entbut {
	color: black;
	font-size: 15px;
	background-color: unset;
}
</style>