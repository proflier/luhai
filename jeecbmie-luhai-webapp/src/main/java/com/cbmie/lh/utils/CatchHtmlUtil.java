package com.cbmie.lh.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 
 * @author Administrator
 *
 */
public class CatchHtmlUtil {
	
	/**
	 * 环渤海动力煤指数
	 * @param strUrl
	 * @return
	 * @throws IOException
	 */
	public static String bspi() throws IOException{
		Document doc = Jsoup.connect("http://www.osc.org.cn/CoalIndex/chs/new").get();
		//秦港-黄骅-天津-曹港-国投-京唐
		String e = doc.getElementById("myTab_Content0").html();
		//黄骅
		String e1 = doc.getElementById("myTab_Content1").html();
		//天津
		String e2 = doc.getElementById("myTab_Content2").html();
		//曹港
		String e3 = doc.getElementById("myTab_Content3").html();
		//国投
		String e4 = doc.getElementById("myTab_Content4").html();
		//京唐
		String e5 = doc.getElementById("myTab_Content5").html();
//		 System.out.println(myStringFormatter(e));
//		 System.out.println(myStringFormatter(e1));
//		 System.out.println(myStringFormatter(e2));
//		 System.out.println(myStringFormatter(e3));
//		 System.out.println(myStringFormatter(e4));
//		 System.out.println(myStringFormatter(e5));
		return myStringFormatter(e)+"<span>秦港</span>"+myStringFormatter(e1)+"<span>黄骅</span>"+myStringFormatter(e2)+"<span>天津</span>"+myStringFormatter(e3)+"<span>曹港</span>"+myStringFormatter(e4)+"<span>国投</span>"+myStringFormatter(e5)+"<span>京唐</span>";
	}
	
	/**
	 * 最新价格
	 * @param strUrl
	 * @return
	 * @throws IOException
	 */
	public static String newPrice() throws IOException{
		Document doc = Jsoup.connect("http://www.sxcoal.com").get();
		ArrayList<Element> es  = doc.getElementsByClass("zhishu1");
		String returnValue = es.get(0).toString();
		return returnValue;
	}
	
	/**
	 * 动力煤 走势图
	 * @return
	 * @throws IOException
	 */
	public static String steamCoalTrend() throws IOException{
		Document doc = Jsoup.connect("http://www.sxcoal.com/").get();
		ArrayList<Element> es = doc.getElementsByTag("script");
		//动力煤
		String steamCoal = es.get(18).toString().replace("<script type=\"text/javascript\">", "").
				replace("// 基于准备好的dom，初始化echarts实例", "").
				replace("var myChart = echarts.init(document.getElementById('tablefirst'));", "").
				replace("option = ", "").replace("// 使用刚指定的配置项和数据显示图表。", "").
				replace("myChart.setOption(option);", "").replace("</script>", "").replace(";","")
				.trim();
		System.out.println(es.get(18));
		System.out.println(steamCoal);
		return steamCoal;
	}
	
	/**
	 * 无烟 走势图
	 * @return
	 * @throws IOException
	 */
	public static  String anthraciteTrend() throws IOException{
		Document doc = Jsoup.connect("http://www.sxcoal.com/").get();
		ArrayList<Element> es = doc.getElementsByTag("script");
		//无烟煤
//		System.out.println(es.get(20));
		String anthracite = es.get(20).toString().replace("<script type=\"text/javascript\">", "").replace("// 基于准备好的dom，初始化echarts实例", "").replace("var myChart2 = echarts.init(document.getElementById('tablefirst2'))", "").replace("option2 = ", "").replace("// 使用刚指定的配置项和数据显示图表。", "").replace("myChart2.setOption(option2);", "").replace("</script>", "").replace(";","").trim();
		System.out.println(anthracite);
		// 转成标准的json字符串
		anthracite = StringEscapeUtils.unescapeHtml4(anthracite);
		return anthracite;
	}
	
	
	/**
	 * 处理table样式
	 * @return
	 */
	public static String myStringFormatter(String origen){
		return origen.replace("zhishu_tab_tit", "").replace("font_14", "").replace("zhishu_tab_con", "").replace("border=\"0\"", "class=\"tableClass\" style=\"width:100%;\"").replace("class=\"\"", "").replace("class=\"f_red\"", "style=\"color:red\"").replace("<tbody>", "").replace("</table>", "");
	}
	
	public static void main(String[] args) throws IOException {  
		//环渤海动力煤指数
//		bspi();
		//最新价格
//		newPrice();
		//动力煤 走势图
//		steamCoalTrend();
		//无烟煤 走势图
		anthraciteTrend();
    } 
	
}
