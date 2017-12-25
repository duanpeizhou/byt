
	function newMap(id,location){
		var map = new BMap.Map(id);            // 创建Map实例
		map.centerAndZoom(location,12);                     // 初始化地图,设置中心点坐标和地图级别。
		map.enableScrollWheelZoom();  
		map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
		return map;
	}
	function clearMark(addMap){
		//console.info(addMap);
		addMap.clearOverlays();
	}
	function addmark(machineryEquipments,map){
		map.clearOverlays();
		if(machineryEquipments instanceof Array){
			for(i in machineryEquipments){
				 var point = new BMap.Point(machineryEquipments[i].longitude,machineryEquipments[i].latitude);
				 var marker = new BMap.Marker(point);
				 map.addOverlay(marker);
			}
		}else{
			 var point = new BMap.Point(machineryEquipments.longitude,machineryEquipments.latitude);
			 var marker = new BMap.Marker(point);
			 map.addOverlay(marker);
		}
	}
	function addTip(machineryEquipment,map){
		var point = new BMap.Point(machineryEquipments.longitude,machineryEquipments.latitude);
		var marker = new BMap.Marker(point);
		map.addOverlay(marker);
		var opts = {
			  width : 200,     // 信息窗口宽度
			  height: 60,     // 信息窗口高度
			  title : "编号："+machineryEquipment.deviceNo // 信息窗口标题
			};
		var infoWindow = new BMap.InfoWindow("坐标："+machineryEquipment.longitude+" | "+machineryEquipment.latitude+"地址:"+machineryEquipment.distributionPoints, opts);  // 创建信息窗口对象
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	}
	function positionEquipment(callback,addmap){
		var pre;
		var remove = function(e) {
			if(pre){
				addMap.removeOverlay(pre);
			}
			var marker = new BMap.Marker(e.point);
			addMap.addOverlay(marker);
			pre = marker;
		};
		var click = function(e) {
			var marker = new BMap.Marker(e.point);
			if(window.confirm('你确定在这标注？')){
				addMap.clearOverlays();
				addMap.addOverlay(marker);
				callback(e.point);
				addMap.removeEventListener("mousemove", remove);
				addMap.removeEventListener("click", click);
			}
		};
		
		addMap.addEventListener("mousemove", remove);
		addMap.addEventListener("click", click);
}
