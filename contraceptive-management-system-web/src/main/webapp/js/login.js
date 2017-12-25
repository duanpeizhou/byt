//删除cookies
function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null)
		document.cookie = name + "=" + cval + ";expires="+ exp.toGMTString();
}
//读取cookies
function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

	if (arr = document.cookie.match(reg))

		return unescape(arr[2]);
	else
		return null;
}
$(function(){
	delCookie("name");
	$("input[name='username']").blur(function(){
		var value = $(this).val();
		if(value.length==0){
			$("#username_msg").text("请输入用户名").show();
			return;
		}
		$("#username_msg").text("").hiden();
	});
	$("input[name='password']").blur(function(){
		var value = $(this).val();
		if(value.length==0){
			$("#password_msg").text("请输入密码").show();
			return;
		}
		$("#password_msg").text("").hiden();
	});
	$("#IbtnEnter").click(function(){
		 var flag = ($("input[name='username']").val().length!=0) && ($("input[name='password']").val().length!=0);
		 if(flag){
			 $("#login_form").submit();
		 }else{
				$("input[name='username']").blur();
				$("input[name='password']").blur();
				return false;
		 }
	});
});


