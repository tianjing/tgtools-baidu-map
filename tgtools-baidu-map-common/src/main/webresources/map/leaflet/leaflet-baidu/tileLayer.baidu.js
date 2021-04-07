//请引入 proj4.js 和 proj4leaflet.js
L.CRS.Baidu = new L.Proj.CRS('EPSG:3395', '+proj=merc +lon_0=0 +k=1 +x_0=1440 +y_0=255 +datum=WGS84 +units=m +no_defs', {
    resolutions: function () {
        level = 19
        var res = [];
        res[0] = Math.pow(2, 18);
        for (var i = 1; i < level; i++) {
            res[i] = Math.pow(2, (18 - i))
        }
        return res;
    }(),
    origin: [0, 0],
    bounds: L.bounds([20037508.342789244, 0], [0, 20037508.342789244])
});

L.tileLayer.baidu = function (option) {
    option = option || {};
    option.url = option.url || {};

    option.url.vec= option.url.vec || "http://online{s}.map.bdimg.com/onlinelabel/?qt=tile&x={x}&y={y}&z={z}&styles={ph}&scaler=1&p=1";
    option.url.img_d = option.url.img_d || "http://shangetu{s}.map.bdimg.com/it/u=x={x};y={y};z={z};v=009;type=sate&fm=46";
    option.url.img_z = option.url.img_z || "http://online{s}.map.bdimg.com/tile/?qt=tile&x={x}&y={y}&z={z}&styles={sh}&v=020";
    option.url.custom = option.url.custom || "http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid={customid}";
    option.url.time = option.url.time || "http://its.map.baidu.com:8002/traffic/TrafficTileService?x={x}&y={y}&level={z}&time={time}&label=web2D&v=017";



    var layer;
    var subdomains = '0123456789';
    switch (option.layer) {
        //单图层


        default:
            layer = L.tileLayer(option.url.vec.replace('{ph}',option.bigfont ? 'ph' : 'pl'), {
            //'http://online{s}.map.bdimg.com/onlinelabel/?qt=tile&x={x}&y={y}&z={z}&styles=' + (option.bigfont ? 'ph' : 'pl') + '&scaler=1&p=1', {
                name:option.name,subdomains: subdomains, tms: true
            });
            break;
        case "img_d": 
            layer = L.tileLayer(option.url.img_d , {
            //'http://shangetu{s}.map.bdimg.com/it/u=x={x};y={y};z={z};v=009;type=sate&fm=46', {
                name: option.name, subdomains: subdomains, tms: true
            });
            break;
        case "img_z":
            layer = L.tileLayer(option.url.img_z.replace('{sh}',option.bigfont ? 'sh' : 'sl'), {
            //'http://online{s}.map.bdimg.com/tile/?qt=tile&x={x}&y={y}&z={z}&styles=' + (option.bigfont ? 'sh' : 'sl') + '&v=020', {
                name: option.name, subdomains: subdomains, tms: true
            });
            break;

        case "custom"://Custom 各种自定义样式
            //可选值：dark,midnight,grayscale,hardedge,light,redalert,googlelite,grassgreen,pink,darkgreen,bluish
            option.customid = option.customid || 'midnight';
            layer = L.tileLayer(option.url.custom.replace('{customid}',option.customid) , {
            //'http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid=' + option.customid, {
                name: option.name, subdomains: "012", tms: true
            });
            break;

        case "time"://实时路况
            var time = new Date().getTime();
            layer = L.tileLayer(option.url.time.replace('{time}',time) , {
            //'http://its.map.baidu.com:8002/traffic/TrafficTileService?x={x}&y={y}&level={z}&time=' + time + '&label=web2D&v=017', {
                name: option.name, subdomains: subdomains, tms: true
            });
            break;

            //合并
        case "img":
            layer = L.layerGroup([
				L.tileLayer.baidu({ name: "底图", layer: 'img_d', bigfont: option.bigfont }),
				L.tileLayer.baidu({ name: "注记", layer: 'img_z', bigfont: option.bigfont })
            ]);
            break;
    }
    return layer;
};