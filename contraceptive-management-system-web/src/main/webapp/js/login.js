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
		$("#username_msg").text("");
        $("#username_msg").hiden();
	});
	$("input[name='password1']").blur(function(){
		var value = $(this).val();
		if(value.length==0){
			$("#password_msg").text("请输入密码").show();
			return;
		}
		$("#password_msg").text("").hiden();
	});
	$("#IbtnEnter").click(function(){
		 var flag = ($("input[name='username']").val().length!=0)
			 && ($("input[name='password']").val().length!=0);
		 if(flag){
             var username = hex_md5($("input[name='username']").val());
             var passwordHash = hex_md5($("input[name='password']").val());
		 	$.post("login",{"username":username,"password":passwordHash}, function(data) {
		 		if (data.status == 1) {
                    window.location.href = "index";
                } else {
                    alert(data.error);
				}
			},"json");
             // ($("input[name='password']").val(hex_md5(($("input[name='password1']").val()))));
             // $("#login_form").submit();
		 }else{
				$("input[name='username']").blur();
				$("input[name='password1']").blur();
				return false;
		 }
	});
});


