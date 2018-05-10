package com.cbmie.lh.stock.service;


import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cbmie.lh.stock.entity.InStock;
import com.cbmie.lh.stock.entity.OutStockDetail;
import com.cbmie.lh.stock.entity.StockHistory;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

public class ReadExcel {

	public static  List<OutStockDetail> importByPoi(InputStream fis,Integer sheetNo) throws IOException {   
        List<OutStockDetail> infos = new ArrayList<OutStockDetail>();   
        OutStockDetail outStockDetailInfo = null;   
            //创建Excel工作薄   
            HSSFWorkbook hwb = new HSSFWorkbook(fis);   
            //得到第一个工作表   
            HSSFSheet sheet = hwb.getSheetAt(sheetNo);   
            HSSFRow row = null;   
            //遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数   
            for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {   
                row = sheet.getRow(j); 
                if(getCellValue(row.getCell(0))!=""){
                	outStockDetailInfo = new OutStockDetail();   
                    outStockDetailInfo = setEntityValue(row);
                    infos.add(outStockDetailInfo);
                }
            }   
        return infos;   
    }
	
	
	public static  Map<String, Object> historyImportByPoi(InputStream fis,Integer sheetNo,List<String > shipNos,List<String > goodsNos,List<String > wareHouseNos) throws IOException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		//excel 值的错误消息
		StringBuffer msg = new StringBuffer();
		List<StockHistory> stockHistorys = new ArrayList<StockHistory>();
        StockHistory stockHistory = null;   
            //创建Excel工作薄   
            HSSFWorkbook hwb = new HSSFWorkbook(fis);   
            //得到第一个工作表   
            HSSFSheet sheet = hwb.getSheetAt(sheetNo);   
            HSSFRow row = null;   
            //遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数   
            for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {   
                row = sheet.getRow(j); 
                if(getCellValue(row.getCell(0))!=""){
                	stockHistory = new StockHistory();   
                	stockHistory = setEntityValueForHistory(row);
                	//逐行检查excel设置返回消息
                	msg.append(checkExcelValue(j,stockHistory, shipNos,goodsNos,wareHouseNos));
                	stockHistorys.add(stockHistory);
                }
            }
            returnMap.put("list", stockHistorys);
            returnMap.put("msg", msg);
        return returnMap;   
    }
	
	private static  StockHistory setEntityValueForHistory(HSSFRow row){
		StockHistory stockHistory = new StockHistory();
		User currentUser = UserUtil.getCurrentUser();
		stockHistory.setCreaterNo(currentUser.getLoginName());
		stockHistory.setCreaterName(currentUser.getName());
		stockHistory.setCreateDate(new Date());
		stockHistory.setUpdateDate(new Date());
		stockHistory.setCreaterDept(currentUser.getOrganization().getOrgName());
		stockHistory.setUserId(currentUser.getId().toString());
		stockHistory.setDeptId(currentUser.getOrganization().getId());
		 //此方法调用getCellValue(HSSFCell cell)对解析出来的数据进行判断，并做相应的处理  
        stockHistory.setInStockDate(new Date(getCellValue(row.getCell(1))));
        stockHistory.setShipNo(getCellValue(row.getCell(2)));
        stockHistory.setShipName(getCellValue(row.getCell(3)));
        stockHistory.setGoodsName(getCellValue(row.getCell(4)));
//        stockHistory.setInnerContractNo(getCellValue(row.getCell(6)));
        stockHistory.setQuantity(Double.valueOf(getCellValue(row.getCell(6))).doubleValue());
        stockHistory.setStockCost(Double.valueOf(getCellValue(row.getCell(7))).doubleValue());
        stockHistory.setWarehouseName(getCellValue(row.getCell(8)));
        stockHistory.setInStockId(getCellValue(row.getCell(2)));
		return stockHistory;
		
	}
	
	private static  OutStockDetail setEntityValue(HSSFRow row){
		OutStockDetail outStockDetail = new OutStockDetail();
		User currentUser = UserUtil.getCurrentUser();
		outStockDetail.setCreaterNo(currentUser.getLoginName());
		outStockDetail.setCreaterName(currentUser.getName());
		outStockDetail.setCreateDate(new Date());
		outStockDetail.setUpdateDate(new Date());
		outStockDetail.setCreaterDept(currentUser.getOrganization().getOrgName());
		outStockDetail.setUserId(currentUser.getId().toString());
		outStockDetail.setDeptId(currentUser.getOrganization().getId());
		 //此方法调用getCellValue(HSSFCell cell)对解析出来的数据进行判断，并做相应的处理  
        outStockDetail.setNumbering(getCellValue(row.getCell(0)));//序号
        outStockDetail.setShipment(getCellValue(row.getCell(1)));//码头
        outStockDetail.setShipTime(getCellValue(row.getCell(2)));//发货日期
        outStockDetail.setBillTime(getCellValue(row.getCell(3)));//开单日期
        outStockDetail.setSaleContractNo(getCellValue(row.getCell(4)));//销售合同编号
        outStockDetail.setDeleveryNo(getCellValue(row.getCell(5)));//发货通知编号
        outStockDetail.setMainVoyage(getCellValue(row.getCell(6)));//主航次
        outStockDetail.setBillVoyage(getCellValue(row.getCell(7)));//开单航次
        outStockDetail.setTransferVoyage(getCellValue(row.getCell(8)));//调拨航次
        outStockDetail.setGoodsName(getCellValue(row.getCell(9)));//物料名称
        outStockDetail.setCustomerShort(getCellValue(row.getCell(10)));//客户简称
        outStockDetail.setSaleCategory(getCellValue(row.getCell(11)));//销售方式
        outStockDetail.setTransType(getCellValue(row.getCell(12)));//运输方式
        outStockDetail.setShipCompany(getCellValue(row.getCell(13)));//承运商
        outStockDetail.setPoundsNo(getCellValue(row.getCell(14)));//磅单号
        outStockDetail.setLicense(getCellValue(row.getCell(15)));//车牌号/火车卡号/船名
        outStockDetail.setCarNumber(getCellValue(row.getCell(16)));//卡数/车数
        outStockDetail.setTrainWeight(getCellValue(row.getCell(17)));//火车标重
        outStockDetail.setRailwayTrackScale(getCellValue(row.getCell(18)));//火车轨道衡
        outStockDetail.setElectQuantity(getCellValue(row.getCell(19)));//装车楼电子数
        outStockDetail.setDeliveryQuantity(getCellValue(row.getCell(20)));//发货数量
        outStockDetail.setChargeableWeight(getCellValue(row.getCell(21)));//计费重量
        outStockDetail.setShipmentMode(getCellValue(row.getCell(22)));//"码头作业方式"
        outStockDetail.setTractor(getCellValue(row.getCell(23)));//"是否拖头"
        outStockDetail.setTransportTime(getCellValue(row.getCell(24)));//承运时间
        outStockDetail.setReceiptDate(getCellValue(row.getCell(25)));//收货日期
        outStockDetail.setReceiptQunatity(getCellValue(row.getCell(26)));//收货数量
        outStockDetail.setPoundsDifference(getCellValue(row.getCell(27)));//磅差
        outStockDetail.setTransportFee(getCellValue(row.getCell(28)));//扣运费
        outStockDetail.setPlanNo(getCellValue(row.getCell(29)));//配煤计划号
        outStockDetail.setCheckNo(getCellValue(row.getCell(30)));//检验批号
        outStockDetail.setShipmentSpace(getCellValue(row.getCell(31)));//码头作业场地
        outStockDetail.setComment(getCellValue(row.getCell(32)));//备注
		return outStockDetail;
	}
	
    //判断从Excel文件中解析出来数据的格式   
    private static  String getCellValue(HSSFCell cell){   
        String value = null;   
        if(cell!=null){
        	//简单的查检列类型   
            switch(cell.getCellType())   
            {   
                case HSSFCell.CELL_TYPE_STRING://字符串   
                    value = cell.getRichStringCellValue().getString();   
                    break;   
                case HSSFCell.CELL_TYPE_NUMERIC://数字   
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {    //判断是日期类型  
                        SimpleDateFormat dateformat = null; 
//                        System.out.println(cell.getCellStyle().getDataFormat()+"======>");
                        if (cell.getCellStyle().getDataFormat() == 176) { //判断为时间HH:ss
                        	dateformat = new SimpleDateFormat("HH:mm");
                        } else{
                        	dateformat = new SimpleDateFormat("yyyy/MM/dd");  
                        }
                        Date dt = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());//获取成DATE类型     
                        value = dateformat.format(dt);   
                    }else{  
                        value = String.valueOf(cell.getNumericCellValue());  
                    }  
                    break;   
                case HSSFCell.CELL_TYPE_BLANK:   
                    value = "";   
                    break;      
                case HSSFCell.CELL_TYPE_FORMULA:   
                    value = String.valueOf(cell.getCellFormula());   
                    break;   
                case HSSFCell.CELL_TYPE_BOOLEAN://boolean型值   
                    value = String.valueOf(cell.getBooleanCellValue());   
                    break;   
                case HSSFCell.CELL_TYPE_ERROR:   
                    value = String.valueOf(cell.getErrorCellValue());   
                    break;   
                default:   
                    break;   
            }   
        }
        return value;   
    }
    
    public static String checkExcelValue(Integer rowNum,StockHistory stockHistory,List<String > shipNos,List<String > goodsNos,List<String > wareHouseNos){
    	boolean errorFlg = false;
    	String msg = "Excel第"+rowNum+"行";
    	if(!shipNos.contains(stockHistory.getShipNo())){
    		errorFlg = true;
    		msg = msg + "船编号====>>" + stockHistory.getShipNo() +"不存在;";
    	}
    	if(!goodsNos.contains(stockHistory.getGoodsName())){
    		errorFlg = true;
    		msg = msg + "商品编码====>>" + stockHistory.getGoodsName() +"不存在;";
    	}
    	if(!wareHouseNos.contains(stockHistory.getWarehouseName())){
    		errorFlg = true;
    		msg = msg + "仓库编码====>>" + stockHistory.getWarehouseName() +"不存在;";
    	}
    	msg = msg + "<br/>";
    	if(errorFlg){
    		return msg;
    	}else{
    		return "";
    	}
    }
}