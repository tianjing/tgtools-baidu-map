//按项目 content-path 加载
__CreateJSPath = function (js) {
    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }

    serverpath="/"+ss[3]+"/";
    return path;
}
var serverpath="";
var bootPATH = __CreateJSPath("leafletboot.js");




document.write('<script src="' + serverpath + 'webjars/map/leaflet/leaflet.js" type="text/javascript" ></script>');
document.write('<link href="'  + serverpath + 'webjars/map/leaflet/leaflet.css" rel="stylesheet"  type="text/css" />');

document.write('<script src="' + serverpath + 'webjars/map/leaflet/proj4/proj4-compressed.js" type="text/javascript" ></script>');
document.write('<script src="' + serverpath + 'webjars/map/leaflet/proj4/proj4leaflet.js" type="text/javascript" ></script>');
document.write('<script src="' + serverpath + 'webjars/map/leaflet/leaflet-baidu/tileLayer.baidu.js" type="text/javascript" ></script>');

