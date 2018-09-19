package cn.zectec.contraceptive.management.system.web.controller;

import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Menu;
import cn.zectec.contraceptive.management.system.security.service.SecurityContext;
import cn.zectec.contraceptive.management.system.service.IManagerService;
import cn.zectec.contraceptive.management.system.service.IMenuService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class LoginController {
	private static Logger logger = org.apache.log4j.Logger.getLogger(MenuController.class);
	@Autowired
	private IManagerService managerService;
	@Autowired
	private IMenuService menuService;
	
	
	
	@RequestMapping(value={"/login","/"},method=RequestMethod.GET)
	public String loginUI(){
		if(SecurityContext.getCurrentManager()!=null){
			return "redirect:/index";
		}
		return "Login";
	}

	@ResponseBody
	@RequestMapping(value = {"/login"}, method = RequestMethod.POST)
	public Map<String, Object> login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
		logger.info("user login , username = " + username + ", password = " + password);
		Map<String, Object> map = new HashMap<>();
		if (SecurityContext.getCurrentManager() != null) {
			map.put("status", 1);
			return map;
		}
		Manager manager = managerService.login(username, password);
		if (manager == null) {
			map.put("error", "用户名或密码错误");
			map.put("status", 0);
			return map;
		} else {
			Menu m = menuService.getCurrentManagerMenu();
			session.setAttribute("manager_menu", m);
			session.setAttribute("manager_menu_url", getAllUrl(m));
			session.setAttribute("all_menus_url", getAllUrl(menuService.getAllMenu()));
			map.put("status", 1);
			return map;
		}
	}
	private Set<String> getAllUrl(Menu m){
		Set<String> result = new HashSet<String>();
		putUrl(m, result);
		return result;
	}
	private void putUrl(Menu m,Set<String> result){
		if(m != null){
			result.add(m.getUrl());
			for(Menu menu : m.getChildMenu()){
				putUrl(menu,result);
			}
		}
	}
	private Set<String> getAllUrl(List<Menu> menus){
		Set<String> result = new HashSet<String>();
		for(Menu m: menus){
			result.add(m.getUrl());
		}
		return result;
	}
	@RequestMapping(value={"/logout"},method=RequestMethod.GET)
	public String logout(){
		return "redirect:/";
	}
	
	@RequestMapping(value={"/noPermission"},method=RequestMethod.GET)
	public String noPermissionUI(){
		return "noPermission";
	}
	
}
