package com.lanen.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.Stage;



import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.lanen.view.test.DataValidation.visceraWeight;

public class POIExcelUtil{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HSSFWorkbook wb;
	HSSFSheet sheet;
	List<visceraWeight> list;
	List<String> structure = new ArrayList<String>();
	
	public HSSFSheet newSheet(String sheetName)
	{
		 wb = new HSSFWorkbook();//创建Excel工作簿对象   
		 sheet = wb.createSheet(sheetName);//创建Excel工作表对象    
		 /*HSSFCellStyle cellStyle = wb.createCellStyle();//创建单元格样式   
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);//下边框        
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);//左边框        
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框        
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  */
		// wb.setSheetName(1, "第一张工作表");  //后面修改名字
		return sheet;
	}

		//sheet.setColumnWidth((short)column,(short)width);      
		//row.setHeight((short)height); 
		
		//设置打印区域
		//wb.setPrintArea(0, "$A$1:$C$2"); 
	
	public boolean save(String defaultName,Stage stage) throws IOException
	{
		
		boolean flag = false;
		FileChooser fileChooser = new FileChooser();
		//fileChooser.setInitialFileName("laundryrecords.xls");
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.xls", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
       
        //Show open file dialog
       // fileChooser.setInitialDirectory(new File("default"));
       File file = fileChooser.showSaveDialog(stage);
       if(file!=null)
       {
		String path = file.getPath();
		if(!path.contains(".xls")&&!path.contains(".xlsx"))
			path+=".xls";
		
		FileOutputStream fileOut = new FileOutputStream(path);
		wb.write(fileOut);  
		
		fileOut.close();
		flag = true;
       }else
       {
    	   System.out.println("没有选择文件");
       }
      
       return flag;
		
	}
	



}
