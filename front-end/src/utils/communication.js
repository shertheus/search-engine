 // 请在下方实现自己的后端通信函数
import API from "./API";
import axios from "axios";

export default {
    name: "communication",
    components: {
        testConnect,
        entityConnect,
        relationConnect,
    },
}


 function testConnect(keyword, type,callback){
    axios({
        url : API.SEARCH.path,
        method : API.SEARCH.method,
        params: {"ftype":"word","type": type,"keyword":keyword}
    }).then(function (response){
        callback(response.data['data'])
    })
}

function entityConnect(id, callback){
    axios({
        url : API.SEARCH.path,
        method : API.SEARCH.method,
        params: {"ftype": "id","id":id}
    }).then(function (response){
        callback(response.data['data'])
    })
}

function relationConnect(name1, name2, type1, type2 ,callback){
    axios({
        url : API.SEARCH.path,
        method : API.SEARCH.method,
        params: {"ftype":"relation","name1":name1,"name2":name2,"type1":type1,"type2":type2}
    }).then(function (response){
        callback(response.data['data'])
    })
}