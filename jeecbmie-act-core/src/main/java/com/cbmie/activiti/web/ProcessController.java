package com.cbmie.activiti.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cbmie.activiti.service.ProcessEntityService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.utils.DateUtils;
import com.cbmie.common.utils.Json;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
@Controller  
@RequestMapping(value = "/process")  
public class ProcessController extends BaseController {

	 protected Logger logger = LoggerFactory.getLogger(getClass());
	  
    @Autowired  
    RepositoryService repositoryService;  
    
    @Autowired
    private ProcessEntityService procEntityService;

    /**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "activiti/processList";
	}
	
	/** 
     * 模型列表 
     */  
    @RequestMapping(value="list",method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> list(HttpServletRequest request) {
    	Page<Map> page = getPage(request);
    	 ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery()
 	    		.latestVersion();
    	if("key".equals(page.getOrderBy())){
    		query = query.orderByProcessDefinitionKey();
    	}else if("name".equals(page.getOrderBy())){
    		query = query.orderByProcessDefinitionName();
    	}else if("version".equals(page.getOrderBy())){
    		query = query.orderByProcessDefinitionVersion();
    	}else if("deployTime".equals(page.getOrderBy())){
    		query = query.orderByDeploymentId();
    	}else{
    		query = query.orderByProcessDefinitionId();
    	}
    	if("desc".equals(page.getOrder())){
    		query = query.desc();
    	}else{
    		query = query.asc();
    	}
    	page.setTotalCount(query.count());
    	List<ProcessDefinition> result = query.listPage(page.getFirst()-1,page.getPageSize());
    	
    	Map<String,Object> tmp = null; 
    	for(ProcessDefinition pd : result){
    		tmp = new HashMap<String,Object>();
    		tmp.put("id", pd.getId());
    		tmp.put("key", pd.getKey());
    		tmp.put("name", pd.getName());
    		tmp.put("version", pd.getVersion());
    		tmp.put("active", pd.isSuspended()?"0":"1");
    		tmp.put("desc", pd.getDescription());
    		Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(pd.getDeploymentId()).singleResult();
    		Date t = deployment==null?null:deployment.getDeploymentTime();
    		tmp.put("deploymentId", deployment.getId());
    		tmp.put("deployTime", t==null?null:DateUtils.formatDateTime(t));
    		page.getResult().add(tmp);
    	}
		return getEasyUIData(page);
	}
    
    @RequestMapping(value="allProcIns",method = RequestMethod.GET)
   	@ResponseBody
    public List<Map<String,String>> getAllProcIns(){
    	return procEntityService.getAllProcIns();
    }
    @RequestMapping(value="moduleNameByKey/{processKey}",method = RequestMethod.GET)
   	@ResponseBody
    public String getModuleNameByKey(@PathVariable(value="processKey") String processKey){
    	if(StringUtils.isBlank(processKey)){
    		return "";
    	}
    	List<String> nameList = new ArrayList<String>();
    	String[] modulenameArray = processKey.split(",");
    	for (String modulename : modulenameArray) {
    		ProcessDefinition pdf = repositoryService.createProcessDefinitionQuery().processDefinitionKey(modulename).latestVersion().singleResult();
        	if(pdf!=null){
        		if(StringUtils.isNotBlank(pdf.getName())){
        			nameList.add(pdf.getName());
        		}
        	}
    	}
    	return StringUtils.join(nameList, ",");
    }
    /** 
     * 导出img文件 
     */  
    @RequestMapping("history")  
    public String export(String procDefId,String procKey,org.springframework.ui.Model model) {  
       /* try {  
        	ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
    		
    		String resourceName = processDefinition.getDiagramResourceName();
    		
    		InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName); 
            IOUtils.copy(resourceAsStream, response.getOutputStream());  
            String filename = resourceName + ".png";  
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);  
            response.flushBuffer();  
            return "";
        } catch (Exception e) {  
            logger.error("导出流程图失败：procDefId={}", procDefId, e);  
        }  */
    	model.addAttribute("procDefId", procDefId);
    	model.addAttribute("procKey", procKey);
    	return "activiti/procDefHiList";
    }
    @RequestMapping("history/img/{procDefId}") 
    public void showImag(@PathVariable("procDefId") String procDefId,HttpServletResponse response){
    	ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
    	String resourceName = processDefinition.getDiagramResourceName();
		InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		try {
			while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /** 
     * 挂起
     */  
    @ResponseBody  
    @RequestMapping("update/suspend")  
    public Json suspendProc(String procDefId) {  
        Json json = new Json();  
        try {  
        	repositoryService.suspendProcessDefinitionById(procDefId, true, null);
            json.setMsg("挂起成功，流程定义ID=" + procDefId);  
            json.setSuccess(true);  
        } catch (ActivitiException e) {  
            json.setMsg("流程已经是挂起状态");  
        } catch (Exception e) {  
            logger.error("流程挂起失败：procDefId={}", procDefId, e);  
            json.setMsg("流程挂起失败");  
        }  
        return json;  
    } 
    
    /** 
     * 激活 
     */  
    @ResponseBody  
    @RequestMapping("update/active")  
    public Json activeProc(String procDefId) {  
        Json json = new Json();  
        try {  
        	repositoryService.activateProcessDefinitionById(procDefId, true, null);
            json.setMsg("激活成功，流程定义ID=" + procDefId);  
            json.setSuccess(true);  
        } catch (ActivitiException e) {  
            json.setMsg("流程已经是激活状态");  
        } catch (Exception e) {  
            logger.error("流程激活失败：procDefId={}", procDefId, e);  
            json.setMsg("流程激活失败");  
        }  
        return json;  
    }
    
    /**
	 * 流程上传
	 */
	@RequestMapping(value="uploadProc",method = RequestMethod.GET)
	public String uploadProc() {
		return "activiti/processUpload";
	}
    /**
	 * 流程上传 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadProc", method=RequestMethod.POST)
	@ResponseBody
	public String uploadProc(MultipartFile file) {
		String result = "fail";
		String fileName = file.getOriginalFilename();
		if (StringUtils.isBlank(fileName)){
			result = "上传文件不能为空";
			return result;
		}else{
			try {
				InputStream fileInputStream = file.getInputStream();
				Deployment deployment = null;
				String extension = FilenameUtils.getExtension(fileName);
				if (extension.equals("zip") || extension.equals("bar")) {
					ZipInputStream zip = new ZipInputStream(fileInputStream);
					deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
				}else if (fileName.indexOf("bpmn20.xml") != -1) {
					deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
				} else if (extension.equals("bpmn")) { // bpmn扩展名特殊处理，转换为bpmn20.xml
					String baseName = FilenameUtils.getBaseName(fileName); 
					deployment = repositoryService.createDeployment().addInputStream(baseName + ".bpmn20.xml", fileInputStream).deploy();
				} else {
					result = "不支持的文件类型：" + extension;
				}
				
				List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();

				// 设置流程分类
				/*for (ProcessDefinition processDefinition : list) {
//						ActUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
					repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
					result += "部署成功，流程ID=" + processDefinition.getId() + "<br/>";
				}
				
				if (list.size() == 0){
					message = "部署失败，没有流程。";
				}*/
				if(list.size() == 0){
					result = "部署失败";
				}else{
					result = "success";
				}
			} catch (Exception e) {
				result = "操作失败";
				throw new ActivitiException("部署失败！", e);
			}
		}

		return result;
	}
	@RequestMapping(value = "convert")
	@ResponseBody 
	public Json convertToModel(String procDefId) throws Exception {
		 Json json = new Json();
		try{
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
			InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),processDefinition.getResourceName());
			XMLInputFactory xif = XMLInputFactory.newInstance();
			InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
			XMLStreamReader xtr = xif.createXMLStreamReader(in);
			BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
		
			BpmnJsonConverter converter = new BpmnJsonConverter();
			ObjectNode modelNode = converter.convertToJson(bpmnModel);
			org.activiti.engine.repository.Model modelData = repositoryService.newModel();
			modelData.setKey(processDefinition.getKey());
			modelData.setName(processDefinition.getResourceName());
//			modelData.setCategory(processDefinition.getCategory());//.getDeploymentId());
			modelData.setDeploymentId(processDefinition.getDeploymentId());
			modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));
		
			ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
			modelData.setMetaInfo(modelObjectNode.toString());
		
			repositoryService.saveModel(modelData);
		
			repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
			json.setSuccess(true);
			json.setMsg("转换成功");  
		}catch(Exception e){
			json.setMsg("转换失败");
		}
		return json;
	}
	
	 @ResponseBody
    @RequestMapping(value = "delete/{deploymentId}")  
    public String delete(@PathVariable("deploymentId") String deploymentId) {
        repositoryService.deleteDeployment(deploymentId);
        return "success";
    }
	@RequestMapping(value="history/list/{procKey}",method = RequestMethod.GET)
	@ResponseBody
	 public Map<String, Object> listHistory(@PathVariable("procKey") String procKey,HttpServletRequest request) {
		 Page<Map> page = getPage(request);
		 ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().processDefinitionKey(procKey).orderByDeploymentId().desc();
		 page.setTotalCount(query.count());
		 List<ProcessDefinition> result = query.listPage(page.getFirst()-1,page.getPageSize());
	 	
	 	Map<String,Object> tmp = null; 
	 	for(ProcessDefinition pd : result){
	 		tmp = new HashMap<String,Object>();
	 		tmp.put("id", pd.getId());
	 		tmp.put("key", pd.getKey());
	 		tmp.put("name", pd.getName());
	 		tmp.put("version", pd.getVersion());
	 		tmp.put("active", pd.isSuspended()?"0":"1");
	 		Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(pd.getDeploymentId()).singleResult();
	 		Date t = deployment==null?null:deployment.getDeploymentTime();
	 		tmp.put("deploymentId", deployment.getId());
	 		tmp.put("deployTime", t==null?null:DateUtils.formatDateTime(t));
	 		page.getResult().add(tmp);
	 	}
		return getEasyUIData(page);
	 }
}
