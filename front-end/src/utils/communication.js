 // 请在下方实现自己的后端通信函数
import API from "./API";
import axios from "axios";

export default {
    name: "communication",
    components: {
        testConnect,
    },
}


 function testConnect(keyword, callback){
    axios({
        url : API.SEARCH.path,
        method : API.SEARCH.method,
        params: {"content":"content","keyword":keyword}
    }).then(function (response){
        callback(response.data)
    })
}