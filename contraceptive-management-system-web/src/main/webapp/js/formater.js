function convertAreas2Tree(areas,level){
	var result = null;
	if(level <= 0){
		return result;
	}
	result = new Array();
	--level;
	for(i in areas){
		result[i] = {};
		result[i].id = areas[i].id;
		result[i].text = areas[i].name;
		result[i].children = convertAreas2Tree(areas[i].childAreas,level);
	}
	return result;
}

function convertMenu2Tree(menu){
	var result = {};
	if(menu){
		result.id = menu.id;
		result.text = menu.name;
		result.children = new Array();
		for(i in menu.childMenu){
			result.children.push(convertMenu2Tree(menu.childMenu[i]));
		}
	}
	return result;
}