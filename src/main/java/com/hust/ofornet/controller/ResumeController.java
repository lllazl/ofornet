package com.hust.ofornet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


import com.hust.ofornet.pojo.Resume;
import com.hust.ofornet.pojo.User;
import com.hust.ofornet.service.ResumeService;
import com.hust.ofornet.service.UserService;
import com.hust.ofornet.util.Page;

@Controller
@RequestMapping
public class ResumeController {

	@Autowired
	ResumeService resumeService;
	@Autowired
	UserService userService;
	
	@RequestMapping("")
	public String add(Model model, Resume c) {
		resumeService.add(c);
		return "redirect:";
	}
	
	@RequestMapping("admin_resume_delete")
    public String delete(int id) {
        Resume c = resumeService.get(id);
        resumeService.delete(id);
//        return "redirect:admin_job_list?cid="+p.getCid();
        return "redirect:";
    }
 
    @RequestMapping("admin_resume_edit")
    public String edit(Model model, int id) {
        Resume c = resumeService.get(id);
        User p = userService.get(c.getUid());
        c.setUser(p);
        model.addAttribute("c", c);
        return "admin/editResume";
    }
 
    @RequestMapping("admin_resume_update")
    public String update(Resume p) {
        resumeService.update(p);
        return "redirect:admin_resume_list?";
    }
 
//    @RequestMapping("admin_job_list_")
//    public String list(int cid, Model model, Page page) {
//        Category c = categoryService.get(cid);
// 
//        PageHelper.offsetPage(page.getStart(),page.getCount());
//        List<Job> ps = jobService.listByCategory(cid);
// 
//        int total = (int) new PageInfo<>(ps).getTotal();
//        page.setTotal(total);
//        page.setParam("&cid="+c.getId());
// 
//        model.addAttribute("ps", ps);
//        model.addAttribute("c", c);
//        model.addAttribute("page", page);
// 
//        return "admin/listJobByCategory";
//    }
    
    @RequestMapping("admin_resume_list")
    public String list(Model model, Page page) {
 
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Resume> ps = resumeService.list();
        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        model.addAttribute("ps", ps);
        model.addAttribute("page", page);
 
        return "admin/listResume";
    }
}
