var map;
var mkrTool;
function clickbtnsetpoint() {
    mkrTool.open();
}
function clickbtnclosepoint() {
    mkrTool.close();
}
function mapEvent() {
    map.addEventListener("click", function (e) {
        if (mkrTool._isOpen == true) {
            var lng = $("#Longitude").val();
            var x = e.point.lng;
            var y = e.point.lat;
            if (lng.length > 0 && lng > 0) {
                if (window.confirm("你已经标注了，确定修改吗？")) {
                    //                            var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
                    //                                offset: new BMap.Size(10, 25), // 指定定位位置  
                    //                                imageOffset: new BMap.Size(0, 0 - 10 * 25) // 设置图片偏移  
                    //                            });
                    //                            var marker = new BMap.Marker(e.point);
                    map.removeOverlay();
                    markMap(x, y, e);
                } else {
                    mkrTool.close();
                }
            } else {
                map.removeOverlay();
                markMap(x, y, e);
            }
        }
    });
}
function markMap(x, y, e) {
    $("#Longitude").val(x);
    $("#Dimension").val(y);
    //            map.clearOverlays(e.point);    // 首先清理已有标记
    //            map.addOverlay(e.point);
    //            $.post("restaurantAction!resMapMark.do", { posX: x, posY: y }, function (data) {
    //                if ("success" == $.trim(data)) {
    //                    alert("标注成功");
    //                } else {
    //                    alert("标注失败");
    //                }
    //            }, 'text');
    //map.removeOverlay(x, y)
}


//1、数据源格式
var data = [
				{ id: 100, point: "|", addr: "", mainFlow: 3, subFlow: 169.9, press: 4, voltage: 13.3, flashFlow: 1, isEle: "", time: "" },
				{ id: 101, point: "|", addr: "", mainFlow: 3, subFlow: 169.9, press: 4, voltage: 13.3, flashFlow: 1, isEle: "", time: "" }
			];

//2、动态加载数据源（左侧table）
function init_MiddleLeft() {
    var top_div = document.getElementById("id_middle_left");

    var table = document.createElement("table");
    table.setAttribute("border", 1);
    table.setAttribute("width", 280);
    for (var i = 0; i < data.length; i++) {
        var tr = document.createElement("tr");

        var td = document.createElement("td");
        var str = data[i].id;
        var msg = document.createTextNode(str);
        td.appendChild(msg);
        tr.appendChild(td);

        td = document.createElement("td");
        str = data[i].addr;
        msg = document.createTextNode(str);
        td.appendChild(msg);
        tr.appendChild(td);

        td = document.createElement("td");
        var img = document.createElement("img");
        img.src = "./info.gif";
        img.value = this.data[i];
        img.onclick = function () { return click(this) };     //  img.onclick=Function("click(this.value)");

        td.appendChild(img);
        tr.appendChild(td);

        table.appendChild(tr);
    }
    top_div.appendChild(table);
}





//3、精准与模糊查询
// search类原型
function searchClass(data) {
    this.datas = data;
}
// 设置数据源
searchClass.prototype.setData = function (data) {
    this.datas = data;
}
// 去掉字符串空格
searchClass.prototype.trim = function (str) {
    if (null == str) {
        str = "";
    } else {
        str = str.toString();
    }

    return str.replace(/(^[\s\t\xa0\u3000]+)|([\u3000\xa0\s\t]+$)/g, "");
}
// search原型查询模块
// rule = {id: "id", key: "keyword", query: "single|more", show: "one|all"}
searchClass.prototype.search = function (rule) {
    if (null == this.datas) {
        alert("数据源不存在!");
        return false;
    }
    if ("" == this.trim(rule) || "" == this.trim(rule.id) || "" == this.trim(rule.key) || "" == this.trim(rule.query)) {
        alert("请指定要搜索内容!");
        return false;
    }
    var reval = [];              // 返回值，object数组类型
    var datas = this.datas;      // search类，成员变量
    me = this;                   // 全局this，getData中me

    // 添加查询结果
    var addData = function (data) {
        reval.push(data);
    }

    // 获取查询数据源串
    var getData = function (data, id) {
        var _id = me.trim(id);
        var d = "data";
        if (0 == _id.length) {
            return data;
        } else {
            d += '["' + _id + '"]';
            return eval(d);
        }
    }
    // 检索遍历
    for (var i = 0; i < datas.length; i++) {
        var data = datas[i];
        var d = getData(data, rule.id);
        var dReg = new RegExp(this.trim(rule.key));

        if ("one" == rule.show) {                                // 显示查询标记
            if ("single" == rule.query && d == rule.key) {       // 精准查询(single)
                addData(data);
            } else if ("more" == rule.query && dReg.test(d)) {   // 模糊查询(正则式实现)
                addData(data);
            }
        } else if ("all" == rule.show) {                         // 显示全部标记      
            addData(data);
        }
    }
    // 返回结果
    return reval;
}






//4、标记查询的结果
// 标记查询结果
window.addMarker = function (data_a) {
    if (data_a.length > 0) {
        var i = 0;
        var px = data_a[i].Longitude;
        var py = data_a[i].Dimension;
        if (px > 0 && py > 0) {
            map.centerAndZoom(new BMap.Point(data_a[0].Longitude, data_a[0].Dimension), 11);     // 初始化地图,设置中心点坐标和地图级别
        }
        map.clearOverlays();    // 首先清理已有标记
        // 获取坐标(经度、纬度),在地图map上显示
        var point = new BMap.Point(px, py);
        var marker = new BMap.Marker(point);
        map.addOverlay(marker);
        //marker.enableDragging(true);
        marker.setAnimation(BMAP_ANIMATION_BOUNCE);
        // 生成标记信息(table)
        var content = "<table>";
        content = content + "<tr><td> 编号：" + data_a[i].TerminalID + "</td></tr>";
        content = content + "<tr><td> 坐标：" + px + "|" + py + "</td></tr>";
        content = content + "<tr><td> 地址：" + data_a[i].DistributionPoints + "</td></tr>";
        content += "</table>";

        if (px > 0 && py > 0) {
            var opts = {
                width: 250,     // 信息窗口宽度  
                height: 100,     // 信息窗口高度  
                title: "提示:"  // 信息窗口标题  
            }
            py = parseFloat(py) + parseFloat(0.025);
            var infoWindow = new BMap.InfoWindow(content);
            map.openInfoWindow(infoWindow, new BMap.Point(px, py));
        }

        // 捕获标记点击事件，并且显示信息
        // 函数闭包，总是执行
        (function () {
            var infoWindow = new BMap.InfoWindow(content);
            marker.addEventListener("click", function () {
                this.openInfoWindow(infoWindow);
            });
        })()
    }
}


//window.addMarker = function (data_a) {
//    map.centerAndZoom(new BMap.Point(data_a[0].Longitude, data_a[0].Dimension), 11);     // 初始化地图,设置中心点坐标和地图级别
//    map.clearOverlays();    // 首先清理已有标记
//    // 遍历查询结果数据(data_a)
//    for (var i = 0; i < data_a.length; i++) {
//        // 获取坐标(经度、纬度),在地图map上显示
//        var px = data_a[i].Longitude;
//        var py = data_a[i].Dimension;
//        var point = new BMap.Point(px, py);
//        var marker = new BMap.Marker(point);
//        map.addOverlay(marker);
//        //marker.enableDragging(true);
//        marker.setAnimation(BMAP_ANIMATION_BOUNCE);
//        // 生成标记信息(table)
//        var content = "<table>";
//        content = content + "<tr><td> 编号：" + data_a[i].TerminalID + "</td></tr>";
//        content = content + "<tr><td> 坐标：" + px + "|" + py + "</td></tr>";
//        content = content + "<tr><td> 地址：" + data_a[i].DistributionPoints + "</td></tr>";
//        content += "</table>";
//        // 捕获标记点击事件，并且显示信息
//        // 函数闭包，总是执行
//        (function () {
//            var infoWindow = new BMap.InfoWindow(content);
//            marker.addEventListener("click", function () {
//                this.openInfoWindow(infoWindow);
//            });
//        })()
//    }
//}

window.addMarker_View = function (data_a, kindof) {
    if (kindof == 'all') {
        mapView.clearOverlays();    // 首先清理已有标记
        // 遍历查询结果数据(data_a)
        for (var i = 0; i < data_a.length; i++) {
            var pointOne = new BMap.Point(data_a[0].Longitude, data_a[0].Dimension);
            mapView.centerAndZoom(pointOne, 11);
            // 获取坐标(经度、纬度),在地图map上显示
            var px = data_a[i].Longitude;
            var py = data_a[i].Dimension;
            var point = new BMap.Point(px, py);
            var addMarker_View = new BMap.Marker(point);
            mapView.addOverlay(addMarker_View);
            //marker.enableDragging(true);
            // 生成标记信息(table)
            var content = "<table>";
            content = content + "<tr><td> 编号：" + data_a[i].TerminalID + "</td></tr>";
            content = content + "<tr><td> 坐标：" + px + "|" + py + "</td></tr>";
            content = content + "<tr><td> 地址：" + data_a[i].DistributionPoints + "</td></tr>";
            content += "</table>";

            // 捕获标记点击事件，并且显示信息
            // 函数闭包，总是执行
            (function () {
                var infoWindow = new BMap.InfoWindow(content);
                addMarker_View.addEventListener("click", function () {
                    this.openInfoWindow(infoWindow);
                });
            })()
        }
    }
    else {
        if (data_a.length > 0) {
            var pointOne = new BMap.Point(data_a[0].Longitude, data_a[0].Dimension);
            mapView.centerAndZoom(pointOne, 11);

            // 获取坐标(经度、纬度),在地图map上显示
            var px = data_a[0].Longitude;
            var py = data_a[0].Dimension;
            var point = new BMap.Point(px, py);
            var addMarker_View = new BMap.Marker(point);
            mapView.removeOverlay(addMarker_View);

            mapView.addOverlay(addMarker_View);
            //addMarker_View.enableDragging(true);
            addMarker_View.setAnimation(BMAP_ANIMATION_BOUNCE);
            //addMarker_View.setAnimation(null);


            // 生成标记信息(table)
            var content = "<table>";
            content = content + "<tr><td> 编号：" + data_a[0].TerminalID + "</td></tr>";
            content = content + "<tr><td> 坐标：" + px + "|" + py + "</td></tr>";
            content = content + "<tr><td> 地址：" + data_a[0].DistributionPoints + "</td></tr>";
            content += "</table>";

            var opts = {
                width: 250,     // 信息窗口宽度  
                height: 100,     // 信息窗口高度  
                title: "提示:"  // 信息窗口标题  
            }
            py = parseFloat(py) + parseFloat(0.025);
            var infoWindow = new BMap.InfoWindow(content);
            mapView.openInfoWindow(infoWindow, new BMap.Point(px, py));

            // 捕获标记点击事件，并且显示信息
            // 函数闭包，总是执行
            (function () {
                var infoWindow = new BMap.InfoWindow(content);
                addMarker_View.addEventListener("click", function () {
                    this.openInfoWindow(infoWindow);
                });
            })()
        }
    }
}











// 5、右键菜单的实现
// 添加右键菜单
var contextMenu = new BMap.ContextMenu();
var txtMenuItem = [
        {
            text: "放大",
            callback: function () { map.zoomIn() }
        },
        {
            text: "缩小",
            callback: function () { map.zoomOut() }
        },
        {
            text: '查看北京',
            callback: function () { map.centerAndZoom("北京") }
        },
        {
            text: '放置到最大',
            callback: function () { map.zoomTo(18) }
        },
        {
            text: '获取改点坐标',
            callback: function (p) {
                var px = p.lng;
                var py = p.lat;
                alert("该点坐标：\n经度：" + px + "; \n纬度：" + py);
            }
        },
        {
            text: '添加该点标注',
            callback: function (p) {
                var marker = new BMap.Marker(p), px = map.pointToPixel(p);
                map.addOverlay(marker);
                marker.enableDragging(true);
            }
        }
    ];

// 遍历菜单items，添加进菜单
for (var i = 0; i < txtMenuItem.length; i++) {
    contextMenu.addItem(new BMap.MenuItem(txtMenuItem[i].text, txtMenuItem[i].callback, 100));
    if (i == 1 || i == 3) {
        contextMenu.addSeparator();
    }
}
//map.addContextMenu(contextMenu);    // 添加菜单到map



//6、a、输入框自动提示

// 用户按下鼠标，提示信息清除
function clearKeyword(keyword) {
    var input = document.getElementsByName(keyword);
    input[0].value = "";    // 清除提示

}

// 鼠标移走，如果内容为空，则重新提示
function showKeyword(keyword) {
    var input = document.getElementsByName(keyword);
    var value = input[0].value;
    if ("" == value) {  // 判断是否为空
        input[0].value = "input id";
    }
}


//7、b、左侧查询高亮显示
// 点击左侧查询小图标
function MapMarkerClick(obj) {
    //    // 先清理所有td元素，擦除上次高亮显示脚印
    //    var td_a = document.getElementsByTagName("td");
    //    for (var i = 0; i < td_a.length; i++) {
    //        td_a[i].setAttribute("bgcolor", "#ffffff");
    //    }

    //    // 高亮标记本次查询信息
    //    obj.parentNode.setAttribute("bgcolor", "#ff0000");

    var data_a = [];
    var data = obj.value;
    data_a.push(data);
    addMarker(data_a);
}



function GetLocalSearch(address) {
    var local = new BMap.LocalSearch(map, {
        renderOptions: {
            map: map,
            panel: "r-result",
            autoViewport: true,
            selectFirstResult: true
        }
    });
    local.search(address);
}




//另一个方法
var MarkArr = new Array();
var cIndex = -1;
var cLocation;
//var map;

function bdResetMarker() {
    for (var i = 0; i < MarkArr.length; i++) {
        if (MarkArr[i]) map.removeOverlay(MarkArr[i]);
    }
    MarkArr = [];
}

function bdStartLocation(prov, city) {
    map = new BMap.Map("bdmap");
    if (map == null) return;

    map.addControl(new BMap.NavigationControl());
    var myGeo = new BMap.Geocoder();
    myGeo.getPoint(city, function (point) {
        if (point) { cLocation = point; } else { cLocation = new BMap.Point(116.404, 39.915); }
        map.centerAndZoom(cLocation, 10);
    }, prov);
}

function bdShow(idx) {
    if (map == null) return;
    if (cIndex != -1) {
        MarkArr[cIndex].setAnimation(null);
    }

    if (MarkArr[idx] == null) {
        map.centerAndZoom(cLocation, 10);
        cIndex = -1;
    }
    else {
        map.centerAndZoom(MarkArr[idx].point, 16);
        MarkArr[idx].setAnimation(BMAP_ANIMATION_BOUNCE);
        cIndex = idx;
    }
}

function bdAddNullMarker() {
    var i = MarkArr.length;
    MarkArr[i] = null;
}

function bdAddMarker(x, y) {
    if (map == null) return;
    var point = new BMap.Point(x, y);
    var cMarker = new BMap.Marker(point);  // 创建标注
    map.addOverlay(cMarker);              // 将标注添加到地图中
    var i = MarkArr.length;
    MarkArr[i] = cMarker;
}

function bdGetXY(e) {
    if (map == null) return;

    var i = parseInt($get("gvIndex").value, 10);
    if (i < 0) return;

    $get("lbl_LONGITUDE").innerText = e.point.lng;
    $get("lbl_LATITUDE").innerText = e.point.lat;
    $get("p_lng").value = e.point.lng;
    $get("p_lat").value = e.point.lat;
    map.removeEventListener("click", bdGetXY);
    $get("cbSelectPoint").checked = false;

    if (MarkArr[i]) {
        map.removeOverlay(MarkArr[i]);
    }

    var cMarker = new BMap.Marker(e.point);
    map.addOverlay(cMarker);
    cMarker.setAnimation(BMAP_ANIMATION_BOUNCE);
    cIndex = i;

    MarkArr[i] = cMarker;
    $get("btnSave").click();
}

function bdStartClick(obj) {
    if (map == null) return;
    if (obj.checked) {
        map.addEventListener("click", bdGetXY);
    }
    else {
        map.removeEventListener("click", bdGetXY);
    }
}



//==========================================================================
function showDetail(obj) {
    if (obj.checked) {
        $get("p_Detail").style.display = "";
    }
    else {
        $get("p_Detail").style.display = "none";
    }
}

function closeDetail() {
    $get('p_Detail').style.display = "none";
    $get('cbShowDetail').checked = false;
}

function SetScrollPosition(sender) {
    $get("HiddenFieldScroll").value = sender.scrollTop;
}

function ConfirmmSetTel() {
    var i = parseInt($get("gvIndex").value, 10);
    var msg = "确认对<选中的发放机>设置此物流人员手机号码吗？";
    if (i < 0) msg = "确认对<列表中的所有发放机>设置此物流人员手机号码吗？"
    return confirm(msg);
}

function valid() {
    return true;
}