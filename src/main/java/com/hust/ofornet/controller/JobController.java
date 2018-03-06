package com.hust.ofornet.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hust.ofornet.pojo.Category;
import com.hust.ofornet.pojo.Job;
import com.hust.ofornet.service.CategoryService;
import com.hust.ofornet.service.CompanyService;
import com.hust.ofornet.service.JobService;
import com.hust.ofornet.util.Page;

@Controller
@RequestMapping("")
public class JobController {

	@Autowired
	CategoryService categoryService;
	@Autowired
	CompanyService companyService;
	@Autowired
	JobService jobService;
	
	@RequestMapping("admin_job_add")
    public String add(Model model, Job p) {
        p.setCreateDate(new Date());
        jobService.add(p);
        return "redirect:admin_job_list";
    }
 
    @RequestMapping("admin_job_delete")
    public String delete(int id) {
        Job p = jobService.get(id);
        jobService.delete(id);
//        return "redirect:admin_job_list?cid="+p.getCid();
        return "redirect:admin_job_list";
    }
 
    @RequestMapping("admin_job_edit")
    public String edit(Model model, int id) {
        Job p = jobService.get(id);
        Category c = categoryService.get(p.getCid());
        p.setCategory(c);
        model.addAttribute("p", p);
        return "admin/editJob";
    }
 
    @RequestMapping("admin_job_update")
    public String update(Job p) {
        jobService.update(p);
        return "redirect:admin_job_list?";
    }
 
    @RequestMapping("admin_job_list_")
    public String list(int cid, Model model, Page page) {
        Category c = categoryService.get(cid);
 
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Job> ps = jobService.listByCategory(cid);
 
        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+c.getId());
 
        model.addAttribute("ps", ps);
        model.addAttribute("c", c);
        model.addAttribute("page", page);
 
        return "admin/listJobByCategory";
    }
    
    @RequestMapping("admin_job_list")
    public String list(Model model, Page page) {
 
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Job> ps = jobService.list();
        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        model.addAttribute("ps", ps);
        model.addAttribute("page", page);
 
        return "admin/listJob";
    }
	
}
