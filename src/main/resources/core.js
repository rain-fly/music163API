
var  emj = {
    "色": "00e0b",
    "流感": "509f6",
    "这边": "259df",
    "弱": "8642d",
    "嘴唇": "bc356",
    "亲": "62901",
    "开心": "477df",
    "呲牙": "22677",
    "憨笑": "ec152",
    "猫": "b5ff6",
    "皱眉": "8ace6",
    "幽灵": "15bb7",
    "蛋糕": "b7251",
    "发怒": "52b3a",
    "大哭": "b17a8",
    "兔子": "76aea",
    "星星": "8a5aa",
    "钟情": "76d2e",
    "牵手": "41762",
    "公鸡": "9ec4e",
    "爱意": "e341f",
    "禁止": "56135",
    "狗": "fccf6",
    "亲亲": "95280",
    "叉": "104e0",
    "礼物": "312ec",
    "晕": "bda92",
    "呆": "557c9",
    "生病": "38701",
    "钻石": "14af6",
    "拜": "c9d05",
    "怒": "c4f7f",
    "示爱": "0c368",
    "汗": "5b7a4",
    "小鸡": "6bee2",
    "痛苦": "55932",
    "撇嘴": "575cc",
    "惶恐": "e10b4",
    "口罩": "24d81",
    "吐舌": "3cfe4",
    "心碎": "875d3",
    "生气": "e8204",
    "可爱": "7b97d",
    "鬼脸": "def52",
    "跳舞": "741d5",
    "男孩": "46b8e",
    "奸笑": "289dc",
    "猪": "6935b",
    "圈": "3ece0",
    "便便": "462db",
    "外星": "0a22b",
    "圣诞": "8e7",
    "流泪": "01000",
    "强": "1",
    "爱心": "0CoJU",
    "女孩": "m6Qyw",
    "惊恐": "8W8ju",
    "大笑": "d"
};

var  md = ["色", "流感", "这边", "弱", "嘴唇", "亲", "开心", "呲牙", "憨笑", "猫", "皱眉", "幽灵", "蛋糕", "发怒", "大哭", "兔子", "星星", "钟情", "牵手", "公鸡", "爱意", "禁止", "狗", "亲亲", "叉", "礼物", "晕", "呆", "生病", "钻石", "拜", "怒", "示爱", "汗", "小鸡", "痛苦", "撇嘴", "惶恐", "口罩", "吐舌", "心碎", "生气", "可爱", "鬼脸", "跳舞", "男孩", "奸笑", "猪", "圈", "便便", "外星", "圣诞"];












//映射值
function costToCode(arr){
    var code = "";
    arr.forEach(function(value,i){
        //  console.log('forEach遍历:'+i+'--'+value);
        for (var item in emj ) {
            if (value == item) {
                code = code+ emj[item];
            }
        }
    })
    return code;
}


//得到a位随机数
function a(a) {
    var d, e, b = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", c = "";
    for (d = 0; a > d; d += 1)
        e = Math.random() * b.length,
            e = Math.floor(e),
            c += b.charAt(e);
    //console.log("random:"+c);
    return "I0LKIkkMSKqL2fQG";
}
// AES加密，iv为0102030405060708     a密文  b是密钥
function b(a, b) {
    var c = CryptoJS.enc.Utf8.parse(b)  //c /  b加密秘钥
        , d = CryptoJS.enc.Utf8.parse("0102030405060708")  //d  //  矢量
        , e = CryptoJS.enc.Utf8.parse(a)  // e  a密文
        , f = CryptoJS.AES.encrypt(e, c, {   //  AES加密   f AES加密结果
        iv: d,
        mode: CryptoJS.mode.CBC
    });
    return f.toString()
}
//	RSA加密
function c(a, b, c) {
    var d, e;
    return setMaxDigits(131),   //131 => n的十六进制位数/2+3
        d = new RSAKeyPair(b,"",c),  //公钥计算
        e = encryptedString(d, a)  //a 加密密文   d是 公钥key
}


function d(d, e, f, g) {  //参数1 是变量json  参数2 参数3 参数4都是常量  g 私key
    var h = {}, i = a(16);
    return h.encText = b(d, g),  //对json d数据进行AES加密  g为密钥
        h.encText = b(h.encText, i),  // 加密获得数据 在进行AES加密 加密随机数为密钥 获取encText
        h.encSecKey = c(i, e, f),  //对随机数i进行RSA加密  e f为常量计算公钥
        h
}

function e(a, b, d, e) {
    var f = {};
    return f.encText = c(a + e, b, d),
        f
}


function getRouteInfo(id){
    var  obj_json =  {
        ids: "["+id+"]",
        level: "standard",
        encodeType: "aac",
        csrf_token: ""
    }
    // console.log( d(JSON.stringify(obj_json),  costToCode(["流泪", "强"]),  costToCode(md),    costToCode(["爱心", "女孩", "惊恐", "大笑"])))
    return  d(JSON.stringify(obj_json),  costToCode(["流泪", "强"]),  costToCode(md),    costToCode(["爱心", "女孩", "惊恐", "大笑"]));
}