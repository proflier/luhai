package com.cbmie.common.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class JavaToPdfHtml {

	public class UnicodeFontFactory extends XMLWorkerFontProvider {

		public Font getFont(String fontname, String encoding, boolean embedded, float size, int style,
				BaseColor color) {
			try {
				BaseFont baseFont = BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
				return new Font(baseFont, size, style, color);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}
	
	/**
	 * 工程目录
	 */
	private String home;
	
	//生成的pdf文件目录
	//private String DEST = "D:/Program Files/workspace-luhai/jeecbmie/jeecbmie-base-common/target/HelloWorld_CN_HTML_FREEMARKER_FS.pdf";
	
	/**
	 * html文件名
	 */
	private String html;
	
	/**
	 * pdf中文字体文件名
	 */
	private String font;
	
	/**
	 * css文件名
	 */
	private String css;
	
	private Configuration freemarkerCfg = null;
	
	/**
	 * 构造方法
	 * @param request
	 * @param html html文件名
	 * @param font pdf中文字体文件名
	 * @param css css文件名
	 */
	public JavaToPdfHtml (HttpServletRequest request, String html, String font, String css) {
		
		this.home = request.getSession().getServletContext().getRealPath("/") + "WEB-INF" + System.getProperty("file.separator") + "html-pdf";
		this.html = html;
		this.font = home + System.getProperty("file.separator") + "font" + System.getProperty("file.separator") + font;
		this.css = home + System.getProperty("file.separator") +  "style" + System.getProperty("file.separator") + css;
		
		freemarkerCfg = new Configuration();
		// freemarker的模板目录
		try {
			freemarkerCfg.setDirectoryForTemplateLoading(new File(home));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void entrance(Map<String, Object> data, HttpServletResponse response, String fileName) {

		try {
			// 设置文件ContentType类型
			response.setContentType("multipart/form-data");
			// 设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + fileName);
			
			String content = freeMarkerRender(data, this.html);
			createPdf(content, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @param data
	 *   title是导出PDF的文件名，data是表单内容数据
	 * @param response
	 */
	public void entrance(Map<String, Object> data, HttpServletResponse response) {
		try {
			String fileName = data.get("title").toString();
			// 设置文件ContentType类型
			response.setContentType("multipart/form-data");
			// 设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + fileName + ".pdf");
			String content = freeMarkerRender((Map<String, Object>)data.get("data"), this.html);
			createPdf(content, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * freemarker渲染html
	 */
	private String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
		Writer out = new StringWriter();
		try {
			// 获取模板,并设置编码方式
			Template template = freemarkerCfg.getTemplate(htmlTmp, "UTF-8");
			template.setEncoding("UTF-8");
			template.setOutputEncoding("UTF-8");
			// 合并数据模型与模板
			template.process(data, out); // 将合并后的数据和模板写入到流中，这里使用的字符流
			out.flush();
			return out.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	private void createPdf(String content, HttpServletResponse response)
			throws IOException, DocumentException, com.lowagie.text.DocumentException {
		// step 1
		Document document = new Document();
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
		// step 3
		document.open();
		// step 4
		UnicodeFontFactory fontImp = new UnicodeFontFactory();
		fontImp.setUseUnicode(true);
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(content.getBytes()),
				new FileInputStream(css), fontImp);
		// step 5
		document.close();
	}
	
}
