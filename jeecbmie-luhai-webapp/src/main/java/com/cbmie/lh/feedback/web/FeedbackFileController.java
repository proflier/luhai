package com.cbmie.lh.feedback.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.web.BaseController;
import com.cbmie.lh.feedback.entity.FeedbackFile;
import com.cbmie.lh.feedback.service.FeedbackFileService;
import com.cbmie.lh.feedback.service.FeedbackThemeService;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="feedback/file")
public class FeedbackFileController extends BaseController {
	@Autowired
	private FeedbackFileService fileService;
	@Autowired
	private FeedbackThemeService themeService;
	
	@RequestMapping(value="list/{themeId}",method=RequestMethod.GET)
	@ResponseBody
	public List<FeedbackFile> getFilesByThemeId(@PathVariable(value="themeId") Long themeId){
		return fileService.getFilesByThemeId(themeId);
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create/{themeId}", method = RequestMethod.GET)
	public String createForm(@PathVariable(value="themeId") Long themeId,Model model) {
		FeedbackFile feedbackFile = new FeedbackFile();
		feedbackFile.setThemeId(themeId);
		model.addAttribute("feedbackFile", feedbackFile);
		model.addAttribute("actionFile", "create");
		return "feedback/fileForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid FeedbackFile feedbackFile, Model model) throws JsonProcessingException {
		try {
			fileService.save(feedbackFile);
			return setReturnData("success","保存成功",feedbackFile.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",feedbackFile.getId());
		}
	}
	
	/**
	 * 修改
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		FeedbackFile feedbackFile = fileService.get(id);
		model.addAttribute("feedbackFile",feedbackFile );
		model.addAttribute("actionFile", "update");
		return "feedback/fileForm";
	}
	/**
	 * 修改
	 * 
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody FeedbackFile feedbackFile, Model model) throws JsonProcessingException {
		
		fileService.update(feedbackFile);
		return setReturnData("success","更新成功",feedbackFile.getId());
	}
	@ModelAttribute
	public void getFeedbackFile(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("feedbackFile", fileService.get(id));
		}
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws  Exception {
		fileService.delete(id);
		return setReturnData("success","删除成功",id);
	}
}
