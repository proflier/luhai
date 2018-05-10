package com.cbmie.accessory.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.cbmie.accessory.entity.Accessory;
import com.cbmie.accessory.entity.Plupload;
import com.cbmie.accessory.service.AccessoryService;
import com.cbmie.accessory.utils.AccessoryUtil;
import com.cbmie.accessory.utils.PluploadUtil;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.Result;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.UserService;
import com.cbmie.system.utils.UserUtil;

/**
 * 附件controller
 */
@Controller
@RequestMapping("accessory")
public class AccController extends BaseController {
	/**
	 * 对不同操作系统的路径支持
	 */
	private static String SYSTEM_SEPARATOR = System.getProperty("file.separator");
	
	@Autowired
	private AccessoryService accessoryService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取所有附件json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> accessoryList(HttpServletRequest request) {
		Page<Accessory> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		if(filters!=null&&filters.size()>0){
			page = accessoryService.searchNoPermission(page, filters);
		}
		return getEasyUIData(page);
	}
	
	/**
	 * 获取所有附件json
	 */
	@RequestMapping(value = "jsonAll", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> accessoryNoPerList(HttpServletRequest request) {
		Page<Accessory> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		if(filters!=null&&filters.size()>0){
			page = accessoryService.searchNoPermission(page, filters);
		}
		return getEasyUIData(page);
	}
	@RequestMapping(value = "listByPidE/{accParentId}/{accParentEntity}", method = RequestMethod.GET)
	@ResponseBody
	public List<Accessory> getListByPidAndEntity(@PathVariable("accParentId") String accParentId,@PathVariable("accParentEntity") String accParentEntity){
		return accessoryService.getListByPidAndEntity(accParentId, accParentEntity);
	}
	/**
	 * 上传附件页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "toUpload/{pid}", method = RequestMethod.GET)
	public String toUpload(@PathVariable("pid") String pid) {
		return "accessory/uploader";
	}
	/**
	 * 上传附件
	 * 
	 * @param request
	 * @param id
	 */
	@ResponseBody
	@RequestMapping(value = "upload/{pid}/{entity}", method = RequestMethod.POST)
	public void upload(HttpServletRequest request, @PathVariable("pid") String pid, @PathVariable("entity") String entity) {
		Accessory accessory = new Accessory();
		Plupload plupload = new Plupload();
		String FileDir = SYSTEM_SEPARATOR+"uploadfile" + SYSTEM_SEPARATOR;
		plupload.setRequest(request);
		String totalPath = plupload.getRequest().getSession().getServletContext().getRealPath("/");
		totalPath = totalPath.substring(0, totalPath.lastIndexOf(SYSTEM_SEPARATOR));
		totalPath = totalPath.substring(0, totalPath.lastIndexOf(SYSTEM_SEPARATOR));
		totalPath = totalPath.substring(0, totalPath.lastIndexOf(SYSTEM_SEPARATOR));
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
		FileDir+=sf.format(new Date())+SYSTEM_SEPARATOR;
		// 文件存储路径
		File dir = new File(totalPath + FileDir);
		 System.out.println("文件保存位置：：：" + dir.getPath());
		try {
			// 上传文件
			accessory = PluploadUtil.upload(plupload, dir);
			// 判断文件是否上传成功（被分成块的文件是否全部上传完成）
			if (PluploadUtil.isUploadFinish(plupload)) {
				// System.out.println(plupload.getName() + "----");
			}
			// 保存信息到数据库
			if (accessory != null && accessory.getAccId() != null) {
				User currentUser = UserUtil.getCurrentUser();
				accessory.setAccAuthor(currentUser.getLoginName());
				accessory.setAccParentId(pid);
				accessory.setAccParentEntity(entity);
				accessoryService.save(accessory);

			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除附件
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws IOException {
		Accessory accessory = new Accessory();
		accessory = accessoryService.getAcc(id);
		accessoryService.delete(accessory.getId());
		AccessoryUtil.deleteFile(accessory);
		return "success";
	}

	/**
	 * 下载附件
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "download/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String download(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) {
		Accessory accessory = new Accessory();
		accessory = accessoryService.getAcc(id);
		// 中文文件名
		String fileName = accessory.getAccRealName();
		// 用户设置的源文件的路径
		String path = accessory.getAccSrc() + SYSTEM_SEPARATOR + accessory.getAccId();
		// 创建文件对象
		File file = new File(path);

		BufferedInputStream bin = null;
		BufferedOutputStream bos = null;
		if (file.exists()) {
			try {
				response.setContentType("application/x-download");
				// 这里文件名一定要转码，不然下载时会出问题。
				response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
				bin = new BufferedInputStream(new FileInputStream(file));
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[1024 * 1024];
				int iSize;
				while (-1 != (iSize = bin.read(buff))) {
					bos.write(buff, 0, iSize);
				}
				bos.flush();
				bos.close();
				bin.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 头像上传
	 * @param request
	 * @param pid
	 * @throws IOException 
	 * @throws FileUploadException 
	 */
	@ResponseBody
	@RequestMapping(value = "avatarUpload/{pid}", method = RequestMethod.POST)
	public String avatarUpload(HttpServletRequest request, @PathVariable("pid") Integer pid) throws IOException, FileUploadException {
		Accessory accessory = new Accessory();
		Result result = new Result();
		String FileDir = SYSTEM_SEPARATOR+"uploadfile" + SYSTEM_SEPARATOR;
		String totalPath = request.getSession().getServletContext().getRealPath("/");
		totalPath = totalPath.substring(0, totalPath.lastIndexOf(SYSTEM_SEPARATOR));
		totalPath = totalPath.substring(0, totalPath.lastIndexOf(SYSTEM_SEPARATOR));
		totalPath = totalPath.substring(0, totalPath.lastIndexOf(SYSTEM_SEPARATOR));
		// 文件存储路径
		File dir = new File(totalPath + FileDir+"avatar"+SYSTEM_SEPARATOR);
		if (!dir.exists())
			dir.mkdirs();
		System.out.println("头像保存位置：：：" + dir.getPath());
		String contentType = request.getContentType();
		if ( contentType.indexOf("multipart/form-data") >= 0 ){
			//取服务器时间+8位随机码作为部分文件名，确保文件名无重复。
			String fileName =  "" + System.currentTimeMillis();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
			File targetFile = new File(dir.getPath() + SYSTEM_SEPARATOR + fileName+".jpg");
			//此处取第一个编辑后头像 key : "__avatar1"
			MultipartFile multipartFile = map.get("__avatar1").get(0);
			multipartFile.transferTo(targetFile);
			// 保存信息到数据库
			User currentUser = UserUtil.getCurrentUser();
			String realName = multipartFile.getOriginalFilename();
			accessory.setAccSrc(dir.getPath());
			accessory.setAccId(Long.parseLong(fileName));
			accessory.setAccRealName(realName);
			accessory.setAccExtension(".jpg");
			accessory.setAccAuthor(currentUser.getLoginName());
			accessory.setAccParentId(pid.toString());
			accessory.setAccParentEntity("com_cbmie_system_entity_User");
			User user = new User();
			user = userService.get(pid);
			Long img = user.getImg();
			if(img!=null){
				Accessory accessoryOld = accessoryService.getAcc(img);
				accessoryService.delete(accessoryOld);
			}
			accessoryService.save(accessory);
			user.setImg(Long.valueOf(fileName));
			userService.updateImg(user);
			result.sourceUrl = fileName;
		}	
		result.success = true;
		result.msg = "Success!";
		return JSON.toJSONString(result);
	}
	
	/**
	 * 头像显示
	 * @param request
	 * @param response
	 * @param img
	 * @throws IOException
	 * @throws FileUploadException
	 */
	@ResponseBody
	@RequestMapping(value = "avatarView/{img}", method = RequestMethod.GET)
	public void avatarView(HttpServletRequest request,HttpServletResponse response,@PathVariable("img") Long img) throws IOException, FileUploadException {
		Accessory accessory = new Accessory();
		accessory = accessoryService.getAcc(img);
		// 用户设置的源文件的路径
		String path = accessory.getAccSrc() + SYSTEM_SEPARATOR + accessory.getAccId()+".jpg";
		// 创建文件对象
				File file = new File(path);

				BufferedInputStream bin = null;
				BufferedOutputStream bos = null;
				if (file.exists()) {
					try {
						bin = new BufferedInputStream(new FileInputStream(file));
						bos = new BufferedOutputStream(response.getOutputStream());
						byte[] buff = new byte[1024 * 1024];
						int iSize;
						while (-1 != (iSize = bin.read(buff))) {
							bos.write(buff, 0, iSize);
						}
						bos.flush();
						bos.close();
						bin.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
	
	
}
