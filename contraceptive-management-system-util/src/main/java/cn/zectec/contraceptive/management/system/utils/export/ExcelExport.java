package cn.zectec.contraceptive.management.system.utils.export;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.BeanUtils;

public class ExcelExport<T> {
	private List<Filed<T>> fileds;
	private List<? extends T> content;
	private String font;
	private int fontSize;
	private String title = "sheet1";
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Filed<T>> getFileds() {
		return fileds;
	}

	public void setFileds(List<Filed<T>> fileds) {
		this.fileds = fileds;
	}


	public List<? extends T> getContent() {
		return content;
	}

	public void setContent(List<? extends T> content) {
		this.content = content;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	@SuppressWarnings("deprecation")
	public byte[] export() {
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < fileds.size(); i++) {
			sheet.setColumnWidth(i, fileds.get(i).getWidth()*35);
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(fileds.get(i).getName());
            cell.setCellValue(text);
        }
		int index = 1;
		if(content == null || content.isEmpty()){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				workbook.write(baos);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return baos.toByteArray();
		}
		for(T t : content){
			row = sheet.createRow(index++);
			for (int i = 0; i < fileds.size(); i++) {
	            HSSFCell cell = row.createCell(i);
	            cell.setCellStyle(style2);
	            if(fileds.get(i).getFormaterr() != null){
	            	HSSFRichTextString text = new HSSFRichTextString(fileds.get(i).getFormaterr().formatter(t, index,fileds.get(i)));
	            	cell.setCellValue(text);
	            }else if(!fileds.get(i).getFiledName().contains(".")){
	            	PropertyDescriptor p = BeanUtils.getPropertyDescriptor(t.getClass(), fileds.get(i).getFiledName());
	            	if(p ==null){
	            		HSSFRichTextString text = new HSSFRichTextString("");
	            		cell.setCellValue(text);
	            	}else{
	            		Method m = p.getReadMethod();
		            	try {
							Object o = m.invoke(t,null);
							if(o == null){
								HSSFRichTextString text = new HSSFRichTextString("");
				            	cell.setCellValue(text);
							}else if(o.getClass().equals(Date.class)){
			            		HSSFRichTextString text = new HSSFRichTextString(dateFormat.format((Date)o));
			            		cell.setCellValue(text);
			            	}else{
			            		HSSFRichTextString text = new HSSFRichTextString(o.toString());
			            		cell.setCellValue(text);
			            	}
		            	} catch (Exception e) {
							e.printStackTrace();
						} 
	            	}
	            }else if(fileds.get(i).getFiledName().contains(".")){
	            	String ss[] = fileds.get(i).getFiledName().split("\\.");
	            	Object o = t;
	            	for(String s : ss){
	            		PropertyDescriptor p = BeanUtils.getPropertyDescriptor(o.getClass(), s);
	            		if(p != null){
	            			Method m = p.getReadMethod();
			            	try {
								o = m.invoke(o,null);
								if(o != null){
									continue;
								}else{
									break;
								}
			            	} catch (Exception e) {
								e.printStackTrace();
							} 
	            		}
		            	
	            	}
	            	if(o == null){
						HSSFRichTextString text = new HSSFRichTextString("");
		            	cell.setCellValue(text);
					}else if(o.getClass().equals(Date.class)){
	            		HSSFRichTextString text = new HSSFRichTextString(dateFormat.format((Date)o));
	            		cell.setCellValue(text);
	            	}else{
	            		HSSFRichTextString text = new HSSFRichTextString(o.toString());
	            		cell.setCellValue(text);
	            	}
	            }
	        }
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			workbook.write(baos);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return baos.toByteArray();
	}
	

}
