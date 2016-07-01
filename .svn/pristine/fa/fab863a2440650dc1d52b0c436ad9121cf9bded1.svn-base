package com.lanen.util;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lanen.model.clinicaltest.TblClinicalTestData;
import com.lanen.model.studyplan.TblClinicalTestReq;
import com.lanen.model.studyplan.TblClinicalTestReqIndex;
import com.lanen.model.studyplan.TblClinicalTestReqIndex2;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;

public class DocOutportUtils {
	public static boolean OutportClinicalTestReqDoc(Map<String, Object> paraMap) throws DocumentException,IOException {
		 // 设置纸张大小
		 Document document = new Document(PageSize.A4);
		 String filePath = (String) paraMap.get("filePath");
			 //"c:/Temp/ClinicalTestApply"+DateUtil.dateToString(new Date(), "yyyyMMddHHmmss.sss")+".doc";
		 // 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中
		 RtfWriter2.getInstance(document, new FileOutputStream(filePath));
		 document.open();
		 // 设置中文字体
		 BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		 // 标题字体风格
		 Font titleFont = new Font(bfChinese, 13, Font.BOLD);
		 // 正文字体风格
		 Font contextFont = new Font(bfChinese, 10, Font.NORMAL);
		 Paragraph title = new Paragraph("临床检验申请单");
		 // 设置标题格式对齐方式
		 title.setAlignment(Element.ALIGN_CENTER);
		 title.setFont(titleFont);
		 document.add(title);

		 // 设置 Table 表格列
		 Table table1 = new Table(6);
		 int width1[] = {15,20,15,15,15,20};
		 table1.setWidths(width1);//设置每列所占比例
		 table1.setWidth(100); // 占页面宽度 90%

		 table1.setAlignment(Element.ALIGN_LEFT);//居中显示
		 table1.setAlignment(Element.ALIGN_MIDDLE);//纵向居中显示
		 table1.setAutoFillEmptyCells(true); //自动填满
		 table1.setBorderWidth(1); //边框宽度
		 table1.setBorderColor(new Color(255, 255, 255)); //边框颜色
		 table1.setPadding(5);//衬距，看效果就知道什么意思了
		 table1.setSpacing(0);//即单元格之间的间距
		 table1.setBorder(2);//边框

		 //设置临检申请基本数据
		 //获取临检申请数据
		 TblClinicalTestReq tblClinicalTestReq = (TblClinicalTestReq) paraMap.get("TblClinicalTestReq");
		 //如果为查到数据
		 if(tblClinicalTestReq == null){
			 return false;
		 }
		 //设置好表格列后每添加一个cell，会自动横向添加一个单元格，一行填满后自动换行
		 Font fontChinese = new Font(bfChinese, 10, Font.NORMAL);
		 Cell cell = new Cell(new Phrase("课题编号："+tblClinicalTestReq.getTblStudyPlan().getStudyNo(), fontChinese ));
		 cell.setVerticalAlignment(Element.ALIGN_TOP);
		 cell.setColspan(2);
		 table1.addCell(cell);
		 Cell cell2 = new Cell(new Phrase("动物种类："+tblClinicalTestReq.getTblStudyPlan().getAnimalType(),fontChinese));
		 cell2.setColspan(2);
		 table1.addCell(cell2);
		 Cell cell3 = new Cell(new Phrase("课题类型："+(tblClinicalTestReq.getTblStudyPlan().getIsGLP()==1?"GLP":"非GLP"), fontChinese ));
		 cell3.setColspan(2);
		 table1.addCell(cell3);
		 Cell cell4 = new Cell(new Phrase("实验阶段：", fontChinese ));
		 table1.addCell(cell4);
		 Cell cell5 = new Cell(new Phrase(tblClinicalTestReq.getTestPhase(), fontChinese ));
		 cell5.setColspan(5);
		 table1.addCell(cell5);
		 document.add(table1);
		 
		 //设置临检申请检验指标
		 // 设置 Table 表格列
		 Table table2 = new Table(2);
		 int width2[] = {20,80};
		 table2.setWidths(width2);//设置每列所占比例
		 table2.setWidth(100); // 占页面宽度 90%

		 table2.setAlignment(Element.ALIGN_LEFT);//居中显示
		 table2.setAlignment(Element.ALIGN_MIDDLE);//纵向居中显示
		 table2.setAutoFillEmptyCells(true); //自动填满
		 table2.setBorderWidth(1); //边框宽度
		 table2.setBorderColor(new Color(255, 255, 255)); //边框颜色
		 table2.setPadding(5);//衬距，看效果就知道什么意思了
		 table2.setSpacing(0);//即单元格之间的间距
		 table2.setBorder(2);//边框
		 
		 if(tblClinicalTestReq.getTblClinicalTestReqIndexs()==null || tblClinicalTestReq.getTblClinicalTestReqIndexs().size()<1){
			 return false;
		 }
		 List<TblClinicalTestReqIndex> tblClinicalTestReqIndexList = new ArrayList<TblClinicalTestReqIndex>(tblClinicalTestReq.getTblClinicalTestReqIndexs());
		 String dictBioChem = "";
		 String dictHemat = "";
		 String dictBloodCoag = "";
		 String dictUrine = "";
		 for(TblClinicalTestReqIndex tempIndex : tblClinicalTestReqIndexList){
			if (tempIndex.getTestitem()==1) {
				dictBioChem = dictBioChem + tempIndex.getTestIndex()+"("+tempIndex.getTestIndexAbbr()+")  ";
			}
			if (tempIndex.getTestitem()==2) {
				dictHemat = dictHemat + tempIndex.getTestIndex()+"("+tempIndex.getTestIndexAbbr()+")  ";	
			}
			if (tempIndex.getTestitem()==3) {
				dictBloodCoag = dictBloodCoag +	tempIndex.getTestIndex()+"("+tempIndex.getTestIndexAbbr()+")  ";
			}
			if (tempIndex.getTestitem()==4) {
				dictUrine = dictUrine +	tempIndex.getTestIndex()+"("+tempIndex.getTestIndexAbbr()+")  ";
			}
		 }
		 Cell cell6 = new Cell(new Phrase("血液常规检查", fontChinese ));
		 table2.addCell(cell6);
		 Cell cell7 = new Cell(new Phrase(dictHemat, fontChinese ));
		 table2.addCell(cell7);
		 Cell cell8 = new Cell(new Phrase("凝血功能检查", fontChinese ));
		 table2.addCell(cell8);
		 Cell cell9 = new Cell(new Phrase(dictBloodCoag, fontChinese ));
		 table2.addCell(cell9);
		 Cell cell10 = new Cell(new Phrase("血液生化检查", fontChinese ));
		 table2.addCell(cell10);
		 Cell cell11 = new Cell(new Phrase(dictBioChem, fontChinese ));
		 table2.addCell(cell11);
		 Cell cell12 = new Cell(new Phrase("尿液检查", fontChinese ));
		 table2.addCell(cell12);
		 Cell cell13 = new Cell(new Phrase(dictUrine, fontChinese ));
		 table2.addCell(cell13);
		 Cell cell14 = new Cell(new Phrase("其他检查项目", fontChinese ));
		 table2.addCell(cell14);
		 Cell cell15 = new Cell(new Phrase(tblClinicalTestReq.getTestOther(), fontChinese ));
		 table2.addCell(cell15);
		 Cell cell16 = new Cell(new Phrase("备注", fontChinese ));
		 table2.addCell(cell16);
		 Cell cell17 = new Cell(new Phrase(tblClinicalTestReq.getRemark(), fontChinese ));
		 table2.addCell(cell17);
		 document.add(table2);
		 
		 //设置临检申请动物编号
		// 设置 Table 表格列
		 Table table3 = new Table(12);
		 int width3[] = {9,7,9,9,7,9,9,7,9,9,7,9};
		 table3.setWidths(width3);//设置每列所占比例
		 table3.setWidth(100); // 占页面宽度 90%

		 table3.setAlignment(Element.ALIGN_LEFT);//居中显示
		 table3.setAlignment(Element.ALIGN_MIDDLE);//纵向居中显示
		 table3.setAutoFillEmptyCells(true); //自动填满
		 table3.setBorderWidth(1); //边框宽度
		 table3.setBorderColor(new Color(255, 255, 255)); //边框颜色
		 table3.setPadding(5);//衬距，看效果就知道什么意思了
		 table3.setSpacing(0);//即单元格之间的间距
		 table3.setBorder(2);//边框
		 
		 if(tblClinicalTestReq.getTblClinicalTestReqIndex2s()==null || tblClinicalTestReq.getTblClinicalTestReqIndex2s().size()<1){
			 return false;
		 }
		 for (int i = 0; i < 4; i++) {
			Cell cell18 = new Cell(new Phrase("动物编号", fontChinese ));
			Cell cell19 = new Cell(new Phrase("性别", fontChinese ));
			Cell cell20 = new Cell(new Phrase("动物ID号", fontChinese ));
			table3.addCell(cell18);
			table3.addCell(cell19);
			table3.addCell(cell20);
		}
		List<TblClinicalTestReqIndex2> tempIndex2 = new ArrayList<TblClinicalTestReqIndex2>(tblClinicalTestReq.getTblClinicalTestReqIndex2s());
		int row = MathUtils.DivideBigger(tempIndex2.size(), 4);
		for (int i = 0; i < row; i++) {
			for(int j = 0; j < 4; j++){
				if((i+row*j)<tempIndex2.size()){
					Cell cell21 = new Cell(new Phrase(tempIndex2.get(i+row*j).getAnimalCode() , fontChinese));
					Cell cell22 = new Cell(new Phrase((tempIndex2.get(i+row*j).getGender()==1)?"♂":"♀" , fontChinese));
					Cell cell23 = new Cell(new Phrase(tempIndex2.get(i+row*j).getAnimalId() , fontChinese));
					table3.addCell(cell21);
					table3.addCell(cell22);
					table3.addCell(cell23);
				}else {
					Cell cell21 = new Cell(new Phrase("" , fontChinese));
					Cell cell22 = new Cell(new Phrase("" , fontChinese));
					Cell cell23 = new Cell(new Phrase("" , fontChinese));
					table3.addCell(cell21);
					table3.addCell(cell22);
					table3.addCell(cell23);
				}
			}
		}
		document.add(table3);
		
		String contextString = "备注：雌性用\"♀\"表示，雄性用\"♂\"表示。";
		Paragraph context = new Paragraph(contextString);
		// 正文格式左对齐
		context.setAlignment(Element.ALIGN_LEFT);
		context.setFont(contextFont);
		context.setSpacingBefore(8);
		// 设置第一行空的列数
		context.setFirstLineIndent(4);
		document.add(context);


		 //Font.UNDERLINE 下划线
		Paragraph checkDateLable = new Paragraph("计划检查日期: ");
		Paragraph checkDate = new Paragraph(DateUtil.dateToString(tblClinicalTestReq.getBeginDate(), "yyyy-MM-dd")+"～"+DateUtil.dateToString(tblClinicalTestReq.getEndDate(), "yyyy-MM-dd"),FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 10, Font.UNDERLINE));
		checkDateLable.add(checkDate, false);
		Paragraph signLable = new Paragraph("    课题负责人(签字): ");
		Paragraph sign = new Paragraph("              ",FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 10, Font.UNDERLINE));
		signLable.add(sign, false);
		Paragraph dateLable = new Paragraph("    日期: ");
		Paragraph date = new Paragraph(DateUtil.dateToString(tblClinicalTestReq.getCreateDate(), "yyyy-MM-dd"),FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 10, Font.UNDERLINE));
		dateLable.add(date, false);
		Paragraph applyInfo = new Paragraph();
		//设置第一行空的列数
		applyInfo.setFirstLineIndent(4);
		//设置第一行与上一段落空的行数
		applyInfo.setSpacingBefore(8);
		//设置正文格式
		applyInfo.setFont(contextFont);
		//添加计划检查日期
		applyInfo.add(checkDateLable, false);
		//添加课题负责人
		applyInfo.add(signLable, false);
		//添加日期
		applyInfo.add(dateLable, false);
		document.add(applyInfo);
		document.add(new Paragraph("\n"));
		document.close();
		return true;
		}

	public static boolean OutportClinicalDateDoc(List<TblClinicalTestData> list, String filePath,
			int testItem)throws DocumentException,IOException  {
		 // 设置纸张大小
		 Document document = new Document(PageSize.A4);
		 // 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中
		 RtfWriter2.getInstance(document, new FileOutputStream(filePath));
		 document.open();
		 // 设置中文字体
		 BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		 // 标题字体风格
		 Font titleFont = new Font(bfChinese, 13, Font.BOLD);
		 // 正文字体风格
		 @SuppressWarnings("unused")
		Font contextFont = new Font(bfChinese, 10, Font.NORMAL);
		 String title_="";
		 switch (testItem) {
		case 1:	title_="生化";		break;
		case 2:	title_="血常规";		break;
		case 3:	title_="血凝";		break;
		case 4:	title_="尿常规";		break;
		default:
			break;
		}
		 Paragraph title = new Paragraph("临检报告单（"+title_+"）");
		 // 设置标题格式对齐方式
		 title.setAlignment(Element.ALIGN_CENTER);
		 title.setFont(titleFont);
		 document.add(title);

//		 int length=list.size();
		 // 设置 Table 表格列
		 Table table1 = new Table(5);//5 列
		 int width1[] = {15,20,15,30,20};
		 table1.setWidths(width1);//设置每列所占比例
		 table1.setWidth(100); // 占页面宽度 90%

		 table1.setAlignment(Element.ALIGN_LEFT);//居中显示
		 table1.setAlignment(Element.ALIGN_MIDDLE);//纵向居中显示
		 table1.setAutoFillEmptyCells(true); //自动填满
		 table1.setBorderWidth(1); //边框宽度
		 table1.setBorderColor(new Color(255, 255, 255)); //边框颜色
		 table1.setPadding(5);//衬距，看效果就知道什么意思了
		 table1.setSpacing(0);//即单元格之间的间距
		 table1.setBorder(2);//边框
		 
		 if(null==list||list.size()<1){
			 return false;
		 }
		 //设置好表格列后每添加一个cell，会自动横向添加一个单元格，一行填满后自动换行
		 Font fontChinese = new Font(bfChinese, 10, Font.NORMAL);
		 Cell cell = new Cell(new Phrase("动物编号", fontChinese ));
		 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		 cell.setColspan(1);
		 table1.addCell(cell);
		 Cell cell2 = new Cell(new Phrase("动物编号",fontChinese));
		 cell2.setColspan(1);
		 table1.addCell(cell2);
		 Cell cell3 = new Cell(new Phrase("指标", fontChinese ));
		 cell3.setColspan(1);
		 table1.addCell(cell3);
		 Cell cell4 = new Cell(new Phrase("数据", fontChinese ));
		 table1.addCell(cell4);
		 Cell cell5 = new Cell(new Phrase("单位", fontChinese ));
		 cell5.setColspan(1);
		 table1.addCell(cell5);
//		 document.add(table1);
		 
		 for(TblClinicalTestData obj:list){
			 
			
			 //设置好表格列后每添加一个cell，会自动横向添加一个单元格，一行填满后自动换行
			
			 cell = new Cell(new Phrase(obj.getAnimalId(), fontChinese ));
			 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 cell.setColspan(1);
			 table1.addCell(cell);
			 cell2 = new Cell(new Phrase(obj.getAnimalCode(),fontChinese));
			 cell2.setColspan(1);
			 table1.addCell(cell2);
			 cell3 = new Cell(new Phrase(obj.getTestIndexAbbr(), fontChinese ));
			 cell3.setColspan(1);
			 table1.addCell(cell3);
			 cell4 = new Cell(new Phrase(obj.getTestData(), fontChinese ));
			 table1.addCell(cell4);
			 cell5 = new Cell(new Phrase(obj.getTestIndexUnit(), fontChinese ));
			 cell5.setColspan(1);
			 table1.addCell(cell5);
		 }
		 document.add(table1);
//		 
//		 //设置临检申请检验指标
//		 // 设置 Table 表格列
//		 Table table2 = new Table(2);
//		 int width2[] = {20,80};
//		 table2.setWidths(width2);//设置每列所占比例
//		 table2.setWidth(100); // 占页面宽度 90%
//
//		 table2.setAlignment(Element.ALIGN_LEFT);//居中显示
//		 table2.setAlignment(Element.ALIGN_MIDDLE);//纵向居中显示
//		 table2.setAutoFillEmptyCells(true); //自动填满
//		 table2.setBorderWidth(1); //边框宽度
//		 table2.setBorderColor(new Color(255, 255, 255)); //边框颜色
//		 table2.setPadding(5);//衬距，看效果就知道什么意思了
//		 table2.setSpacing(0);//即单元格之间的间距
//		 table2.setBorder(2);//边框
//		 
//		 if(tblClinicalTestReq.getTblClinicalTestReqIndexs()==null || tblClinicalTestReq.getTblClinicalTestReqIndexs().size()<1){
//			 return false;
//		 }
//		 List<TblClinicalTestReqIndex> tblClinicalTestReqIndexList = new ArrayList<TblClinicalTestReqIndex>(tblClinicalTestReq.getTblClinicalTestReqIndexs());
//		 String dictBioChem = "";
//		 String dictHemat = "";
//		 String dictBloodCoag = "";
//		 String dictUrine = "";
//		 for(TblClinicalTestReqIndex tempIndex : tblClinicalTestReqIndexList){
//			if (tempIndex.getTestitem()==1) {
//				dictBioChem = dictBioChem + tempIndex.getTestIndex()+"("+tempIndex.getTestIndexAbbr()+")  ";
//			}
//			if (tempIndex.getTestitem()==2) {
//				dictHemat = dictHemat + tempIndex.getTestIndex()+"("+tempIndex.getTestIndexAbbr()+")  ";	
//			}
//			if (tempIndex.getTestitem()==3) {
//				dictBloodCoag = dictBloodCoag +	tempIndex.getTestIndex()+"("+tempIndex.getTestIndexAbbr()+")  ";
//			}
//			if (tempIndex.getTestitem()==4) {
//				dictUrine = dictUrine +	tempIndex.getTestIndex()+"("+tempIndex.getTestIndexAbbr()+")  ";
//			}
//		 }
//		 Cell cell6 = new Cell(new Phrase("血液常规检查", fontChinese ));
//		 table2.addCell(cell6);
//		 Cell cell7 = new Cell(new Phrase(dictHemat, fontChinese ));
//		 table2.addCell(cell7);
//		 Cell cell8 = new Cell(new Phrase("凝血功能检查", fontChinese ));
//		 table2.addCell(cell8);
//		 Cell cell9 = new Cell(new Phrase(dictBloodCoag, fontChinese ));
//		 table2.addCell(cell9);
//		 Cell cell10 = new Cell(new Phrase("血液生化检查", fontChinese ));
//		 table2.addCell(cell10);
//		 Cell cell11 = new Cell(new Phrase(dictBioChem, fontChinese ));
//		 table2.addCell(cell11);
//		 Cell cell12 = new Cell(new Phrase("尿液检查", fontChinese ));
//		 table2.addCell(cell12);
//		 Cell cell13 = new Cell(new Phrase(dictUrine, fontChinese ));
//		 table2.addCell(cell13);
//		 Cell cell14 = new Cell(new Phrase("其他检查项目", fontChinese ));
//		 table2.addCell(cell14);
//		 Cell cell15 = new Cell(new Phrase(tblClinicalTestReq.getTestOther(), fontChinese ));
//		 table2.addCell(cell15);
//		 Cell cell16 = new Cell(new Phrase("备注", fontChinese ));
//		 table2.addCell(cell16);
//		 Cell cell17 = new Cell(new Phrase(tblClinicalTestReq.getRemark(), fontChinese ));
//		 table2.addCell(cell17);
//		 document.add(table2);
//		 
//		 //设置临检申请动物编号
//		// 设置 Table 表格列
//		 Table table3 = new Table(12);
//		 int width3[] = {9,7,9,9,7,9,9,7,9,9,7,9};
//		 table3.setWidths(width3);//设置每列所占比例
//		 table3.setWidth(100); // 占页面宽度 90%
//
//		 table3.setAlignment(Element.ALIGN_LEFT);//居中显示
//		 table3.setAlignment(Element.ALIGN_MIDDLE);//纵向居中显示
//		 table3.setAutoFillEmptyCells(true); //自动填满
//		 table3.setBorderWidth(1); //边框宽度
//		 table3.setBorderColor(new Color(255, 255, 255)); //边框颜色
//		 table3.setPadding(5);//衬距，看效果就知道什么意思了
//		 table3.setSpacing(0);//即单元格之间的间距
//		 table3.setBorder(2);//边框
//		 
//		 if(tblClinicalTestReq.getTblClinicalTestReqIndex2s()==null || tblClinicalTestReq.getTblClinicalTestReqIndex2s().size()<1){
//			 return false;
//		 }
//		 for (int i = 0; i < 4; i++) {
//			Cell cell18 = new Cell(new Phrase("动物编号", fontChinese ));
//			Cell cell19 = new Cell(new Phrase("性别", fontChinese ));
//			Cell cell20 = new Cell(new Phrase("动物ID号", fontChinese ));
//			table3.addCell(cell18);
//			table3.addCell(cell19);
//			table3.addCell(cell20);
//		}
//		List<TblClinicalTestReqIndex2> tempIndex2 = new ArrayList<TblClinicalTestReqIndex2>(tblClinicalTestReq.getTblClinicalTestReqIndex2s());
//		int row = MathUtils.DivideBigger(tempIndex2.size(), 4);
//		for (int i = 0; i < row; i++) {
//			for(int j = 0; j < 4; j++){
//				if((i+row*j)<tempIndex2.size()){
//					Cell cell21 = new Cell(new Phrase(tempIndex2.get(i+row*j).getAnimalCode() , fontChinese));
//					Cell cell22 = new Cell(new Phrase((tempIndex2.get(i+row*j).getSex()==1)?"♂":"♀" , fontChinese));
//					Cell cell23 = new Cell(new Phrase(tempIndex2.get(i+row*j).getAnimalId() , fontChinese));
//					table3.addCell(cell21);
//					table3.addCell(cell22);
//					table3.addCell(cell23);
//				}else {
//					Cell cell21 = new Cell(new Phrase("" , fontChinese));
//					Cell cell22 = new Cell(new Phrase("" , fontChinese));
//					Cell cell23 = new Cell(new Phrase("" , fontChinese));
//					table3.addCell(cell21);
//					table3.addCell(cell22);
//					table3.addCell(cell23);
//				}
//			}
//		}
//		document.add(table3);
//		
//		String contextString = "备注：雌性用\"♀\"表示，雄性用\"♂\"表示。";
//		Paragraph context = new Paragraph(contextString);
//		// 正文格式左对齐
//		context.setAlignment(Element.ALIGN_LEFT);
//		context.setFont(contextFont);
//		context.setSpacingBefore(8);
//		// 设置第一行空的列数
//		context.setFirstLineIndent(4);
//		document.add(context);
//
//
//		 //Font.UNDERLINE 下划线
//		Paragraph checkDateLable = new Paragraph("计划检查日期: ");
//		Paragraph checkDate = new Paragraph(DateUtil.dateToString(tblClinicalTestReq.getBeginDate(), "yyyy-MM-dd")+"～"+DateUtil.dateToString(tblClinicalTestReq.getEndDate(), "yyyy-MM-dd"),FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 10, Font.UNDERLINE));
//		checkDateLable.add(checkDate, false);
//		Paragraph signLable = new Paragraph("    课题负责人(签字): ");
//		Paragraph sign = new Paragraph("              ",FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 10, Font.UNDERLINE));
//		signLable.add(sign, false);
//		Paragraph dateLable = new Paragraph("    日期: ");
//		Paragraph date = new Paragraph(DateUtil.dateToString(tblClinicalTestReq.getCreateDate(), "yyyy-MM-dd"),FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 10, Font.UNDERLINE));
//		dateLable.add(date, false);
//		Paragraph applyInfo = new Paragraph();
//		//设置第一行空的列数
//		applyInfo.setFirstLineIndent(4);
//		//设置第一行与上一段落空的行数
//		applyInfo.setSpacingBefore(8);
//		//设置正文格式
//		applyInfo.setFont(contextFont);
//		//添加计划检查日期
//		applyInfo.add(checkDateLable, false);
//		//添加课题负责人
//		applyInfo.add(signLable, false);
//		//添加日期
//		applyInfo.add(dateLable, false);
//		document.add(applyInfo);
//		document.add(new Paragraph("\n"));
		document.close();
		return true;
	}
	

}
