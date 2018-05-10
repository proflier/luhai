package com.cbmie.activiti.web;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.constants.StencilConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.editor.language.json.converter.util.JsonConverterUtil;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cbmie.activiti.entity.ModelBean;
import com.cbmie.activiti.entity.ProcessEntity;
import com.cbmie.activiti.service.ProcessEntityService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.utils.Json;
import com.cbmie.common.web.BaseController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;  
  
/** 
 * 流程模型控制器 
 * 
 * @author henryyan 
 */  
@Controller  
@RequestMapping(value = "/model")  
public class ModelController extends BaseController { 
  
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
		return "activiti/modelList";
	}
    /** 
     * 模型列表 
     */  
    @RequestMapping(value="list",method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> list(HttpServletRequest request) {
    	Page<Model> page = getPage(request);
    	ModelQuery query = repositoryService.createModelQuery().latestVersion();
    	List<Model> result = null;
    	if("key".equals(page.getOrderBy())){
    		query = query.orderByModelKey();
    	}else if("name".equals(page.getOrderBy())){
    		query = query.orderByModelName();
    	}else if("version".equals(page.getOrderBy())){
    		query = query.orderByModelVersion();
    	}else if("createTime".equals(page.getOrderBy())){
    		query = query.orderByCreateTime();
    	}else if("lastUpdateTime".equals(page.getOrderBy())){
    		query = query.orderByLastUpdateTime();
    	}else{
    		query = query.orderByModelId();
    	}
    	if("desc".equals(page.getOrder())){
    		query = query.desc();
    	}else{
    		query = query.asc();
    	}
    	page.setTotalCount(query.count());
    	page.getResult().addAll(query.listPage(page.getFirst()-1,page.getPageSize()));
    	/*result = query.list();
		// 分页
		page.setTotalCount(result.size());
		int firstResult = page.getFirst() - 1;
		int endResult = result.size() > (firstResult + page.getPageSize()) ? firstResult + page.getPageSize()
				: result.size();
		for (int i = firstResult; i < endResult; i++) {
			page.getResult().add(result.get(i));
		}*/
		return getEasyUIData(page);
	}
    
    
    /** 
     * 通过spring mvc 的rest地址打开bpmn编辑器 
     * @return 
     */  
    @RequestMapping("editor")  
    public ModelAndView getEditor(){  
        ModelAndView modelAndView = new ModelAndView("system/editor") ;  
        return modelAndView ;  
    }
    
    /** 
     * 流程模型创建页面跳转
     * @return 
     */  
    @RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(org.springframework.ui.Model model) {
		model.addAttribute("modelBean", new ModelBean());
		model.addAttribute("action", "create");
		return "activiti/modelForm";
	}
    
    /** 
     * 创建流程模型
     * @return 
     */  
    @RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ModelBean modelBean, org.springframework.ui.Model model) throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();  
        ObjectNode editorNode = objectMapper.createObjectNode();  
        editorNode.put("id", "canvas");  
        editorNode.put("resourceId", "canvas");  
        ObjectNode stencilSetNode = objectMapper.createObjectNode();  
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");  
        editorNode.put("stencilset", stencilSetNode);  
        Model modelData = repositoryService.newModel();  

        ObjectNode modelObjectNode = objectMapper.createObjectNode();  
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, modelBean.getName());  
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);  
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, StringUtils.defaultString(modelBean.getDescription()));  
        modelData.setMetaInfo(modelObjectNode.toString());  
        modelData.setName(modelBean.getName());  
        modelData.setKey(StringUtils.defaultString(modelBean.getKey()));  

        //保存模型  
        repositoryService.saveModel(modelData);  
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        
		return "success";
	}
    @RequestMapping(value = "set", method = RequestMethod.GET)
    public String setProcEntity(String modelId,org.springframework.ui.Model model){
    	Model modelData = repositoryService.getModel(modelId);
    	String key = modelData.getKey();
    	ProcessEntity pe = procEntityService.getProcEntityByKey(key);
    	if(pe == null){
    		pe = new ProcessEntity();
    		pe.setModeId(modelData.getId());
    		pe.setProcDefKey(key);
    	}
    	model.addAttribute("procEntity", pe);
		model.addAttribute("action", "set");
		return "activiti/processEntityForm";
    }
  
    @RequestMapping(value = "set", method = RequestMethod.POST)
	@ResponseBody
	public String setProcEntity(@Valid ProcessEntity pe, org.springframework.ui.Model model) throws Exception {
    	if(pe.getId()==null){
    		procEntityService.save(pe);
    	}else{
    		procEntityService.update(pe);
    	}
		return "success";
	}
  
    /** 
     * 根据Model部署流程 
     */  
    @ResponseBody  
    @RequestMapping("deploy")  
    public Json deploy(String modelId) {  
        Json json = new Json();  
        try {  
            Model modelData = repositoryService.getModel(modelId);  
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));  
            byte[] bpmnBytes = null;  
//            System.out.println(modelNode.get("properties").get("documentation").asText());;
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode); 
            //暂时使用下面加入说明，后续有其他适用方法代替
            JsonNode processIdNode = JsonConverterUtil.getProperty(StencilConstants.PROPERTY_PROCESS_ID, modelNode);
            JsonNode documentNode = JsonConverterUtil.getProperty(StencilConstants.PROPERTY_DOCUMENTATION, modelNode);
            if (processIdNode != null && StringUtils.isNotEmpty(processIdNode.asText()) && documentNode != null && StringUtils.isNotEmpty(documentNode.asText())) {
            	 for(org.activiti.bpmn.model.Process t : model.getProcesses()){
            		 if(processIdNode.asText().equals(t.getId())){
            			 t.setDocumentation(documentNode.asText());
            			 break;
            		 }
                 }
              }
           
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);  
  
            String processName = modelData.getName() + ".bpmn20.xml";  
            
            Deployment deployment = repositoryService.createDeployment()
            		.name(modelData.getName())
            		.addString(processName, new String(bpmnBytes,"UTF-8"))
            		.deploy();
            
            json.setMsg("部署成功，部署ID=" + deployment.getId());  
            json.setSuccess(true);  
        } catch (NullPointerException e) {  
            json.setMsg("未配置工作流");  
        } catch (Exception e) {  
            logger.error("根据模型部署流程失败：modelId={}", modelId, e);  
            json.setMsg("模型部署流程失败");  
        }  
        return json;  
    }  
  
    /** 
     * 导出model的xml文件 
     */  
    @RequestMapping("export")  
    public void export(String modelId, HttpServletResponse response) {  
        try {  
            Model modelData = repositoryService.getModel(modelId);  
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();  
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));  
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);  
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();  
            byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);  
  
            ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);  
            IOUtils.copy(in, response.getOutputStream());  
            String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";  
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);  
//            response.setContentType("text/xml;charset=UTF-8");
            response.flushBuffer();  
        } catch (Exception e) {  
            logger.error("导出model的xml文件失败：modelId={}", modelId, e);  
        }  
    }  
    @ResponseBody
    @RequestMapping(value = "delete/{modelId}")  
    public String delete(@PathVariable("modelId") String modelId) {  
    	String key = repositoryService.createModelQuery().modelId(modelId).singleResult().getKey();
    	List<Model> list_model = repositoryService.createModelQuery().modelKey(key).list();
    	for(Model m: list_model){
    		  repositoryService.deleteModel(m.getId());
    	}
        List<Deployment> list = repositoryService.createDeploymentQuery().processDefinitionKey(key).list();
        for(Deployment d : list){
        	repositoryService.deleteDeployment(d.getId());
        }
//        return "redirect:/workflow/model/list";
        return "success";
    }  
  
}
