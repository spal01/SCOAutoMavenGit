package com.xls;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;


public class ReadWriteXlsx {
	
		

	//Declare Instance Variable
	String xlsxPath="";
	FileInputStream fis=null;
	FileOutputStream fos=null;
	XSSFWorkbook workbook=null;
	XSSFSheet sheet=null;
	Row row=null;
	Cell cell=null;
	String data="";



	public ReadWriteXlsx(String xlsxPath)throws IOException{
	this.xlsxPath=xlsxPath;
	fis=new FileInputStream(xlsxPath);
	workbook=new XSSFWorkbook(fis);
	}


	public int getRow(String sheetName){
	sheet=workbook.getSheet(sheetName);
	return sheet.getLastRowNum();
	}

	public String getData(String sheetName,int rowIndex,int cellIndex){
	sheet=workbook.getSheet(sheetName);
	row=sheet.getRow(rowIndex);
	cell=row.getCell(cellIndex);

	if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
	data=String.valueOf(cell.getNumericCellValue());
	}

	if(cell.getCellType()==Cell.CELL_TYPE_STRING){
	data=cell.getStringCellValue();
	}

	return data;

	}

	public void writeData (String  sheetName,int rowIndex,int cellIndex,String value)throws IOException{

	sheet=workbook.getSheet(sheetName);
	row=sheet.getRow(rowIndex);
	cell=row.getCell(cellIndex);
	
	cell.setCellType(Cell.CELL_TYPE_STRING);
	cell.setCellValue(value);

	fos=new FileOutputStream(xlsxPath);
	workbook.write(fos);
	fos.close();

	}


}
