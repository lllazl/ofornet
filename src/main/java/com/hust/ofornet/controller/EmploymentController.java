package com.hust.ofornet.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hust.ofornet.pojo.Company;
import com.hust.ofornet.pojo.Employment;
import com.hust.ofornet.pojo.Job;
import com.hust.ofornet.pojo.User;
import com.hust.ofornet.service.CompanyService;
import com.hust.ofornet.service.EmploymentService;
import com.hust.ofornet.service.UserService;
import com.hust.ofornet.util.Page;

@Controller
@RequestMapping("")
public class EmploymentController {

	@Autowired
	EmploymentService employmentService;
	@Autowired
	UserService userService;
	@Autowired
	CompanyService companyService;
	
	@RequestMapping("Employment_listByUser")
	public String listByUser(int uid, Model model, Page page) {
		User u = userService.get(uid);
		
		PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Employment> ps = employmentService.listByUser();
 
        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&uid="+u.getId());
 
        model.addAttribute("ps", ps);
        model.addAttribute("u", u);
        model.addAttribute("page", page);
 
        return "admin/listEmploymentByUser";
	}
	
	@RequestMapping("Employment_listByCompany")
	public String listByCompany(int coid, Model model, Page page) {
		Company co = companyService.get(coid);
		
		PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Employment> ps = employmentService.listByCompany();
 
        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&coid="+co.getId());
 
        model.addAttribute("ps", ps);
        model.addAttribute("co", co);
        model.addAttribute("page", page);
 
        return "admin/listEmploymentByCompany";
	}
	
	@RequestMapping("Employment_updateStatus")
	public String updateStatus(Employment e, String status) throws IOException{
		e.setEmployStatus(status);
		employmentService.update(e);
		return "redirect:admin/listEmploymentByCompany";
	}
	
}
