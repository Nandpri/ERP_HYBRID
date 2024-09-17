package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public  class Excelfileutil {
	Workbook wb;

	public  Excelfileutil(String ExcelPath) throws Throwable, IOException
	{
		FileInputStream fi = new FileInputStream(ExcelPath);
		wb = WorkbookFactory.create(fi);
	}


	public  int rowcount(String sheetname)
	{
		return wb.getSheet(sheetname).getLastRowNum();
	}

	public  String getCellData( String sheetname,int row , int colnum)
	{
	
		String data = " ";
		if (wb.getSheet(sheetname).getRow(row).getCell(colnum).getCellType() == CellType.NUMERIC)
		{
			int celldata = (int)wb.getSheet(sheetname).getRow(row).getCell(colnum).getNumericCellValue();
			data = String.valueOf(celldata);
		}else
		{
			data = wb.getSheet(sheetname).getRow(row).getCell(colnum).getStringCellValue();
		}
		return data;
	}
	
	public void setCelldata(String sheetname , int row,int colnum,String status,String writeExcelpath) throws IOException
	{
	Sheet ws = wb.getSheet(sheetname);
	Row rown = ws.getRow(row);
	Cell cell = rown.createCell(colnum);
	
	cell.setCellValue(status);
	if (status.equalsIgnoreCase("pass"))
	{
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setColor(IndexedColors.BRIGHT_GREEN.getIndex());
		font.setBold(true);
	   style.setFont(font);
	   rown.getCell(colnum).setCellStyle(style);
	   
	}else if (status.equalsIgnoreCase("fail"))
		{
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setColor(IndexedColors.RED.getIndex());
		font.setBold(true);
	   style.setFont(font);
	   rown.getCell(colnum).setCellStyle(style);	
		}else if (status.equalsIgnoreCase("Blocked"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
		   style.setFont(font);
		   rown.getCell(colnum).setCellStyle(style);
		}
	FileOutputStream fo = new FileOutputStream(writeExcelpath);
	wb.write(fo);
	}

	public static void main(String[] args) throws IOException, Throwable
	{
		Excelfileutil xl = new Excelfileutil("C:\\Users\\Aparna\\Downloads\\sample_lyst9730.xlsx");
		int rc = xl.rowcount("Emp");
		System.out.println(rc);
		for (int i=1;i<=rc;i++)
		{
		String fname = xl.getCellData("Emp",i, 0);
		String mname = xl.getCellData("Emp",i, 1);
		String lname = xl.getCellData("Emp",i, 2);
		String eid = xl.getCellData("Emp",i, 3);
		System.out.println(fname+"" +mname +""+lname+"" +eid);
		xl.setCelldata("Emp",i, 4,"pass","C:\\Users\\Aparna\\Downloads\\resultsxlf.xlsx");
		}
	}
		
	}

