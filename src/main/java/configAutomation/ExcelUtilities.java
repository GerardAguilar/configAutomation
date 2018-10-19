package configAutomation;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilities {
	
	public XSSFWorkbook excelWBook;

	public ExcelUtilities(String filename) {
		initialize(filename);
	}
	public int getRowCount(String targetSheet) {
		XSSFSheet excelWSheet = excelWBook.getSheet(targetSheet);
		System.out.println("opening sheet: " + targetSheet + " in "+excelWSheet.toString());		
		int count=0;
		try {
			String cellData;
			do {
				cellData = this.getCellData(count,0, excelWSheet);
				System.out.println("getting cell data: " + cellData + " ["+cellData.length()+"]");
				count++;
			}while(cellData.length()>0);
		}catch(Exception e) {
			System.out.println("getRowCount failed");
		}
		return count;
	}
	public void initialize(String filename) {	
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(filename);
			excelWBook = new XSSFWorkbook(inputStream);
		} catch (FileNotFoundException e1) {
			System.out.println("Couldn't open FileInputStream: "+ filename);
			e1.printStackTrace();
		} catch (IOException e) {
			System.out.println("Couldn't create XSSFWorkbook: " + filename);
			e.printStackTrace();
		}
	}
	
	public ArrayList<ProductOption> getInitialOptionsList(int rowCount, String targetSheet) {
		return getOptionsList(1, rowCount, targetSheet);
	}
	
	public ArrayList<ProductOption> getOptionsList(int column, int rowCount, String targetSheet){
		ArrayList<ProductOption> optionList = new ArrayList<ProductOption>();
		XSSFSheet excelWSheet = excelWBook.getSheet(targetSheet);
		for(int i=0; i<rowCount; i++) {
			String cellData;
			try {
				cellData = this.getCellData(i, column, excelWSheet);
				ProductOption tempOption = new ProductOption(cellData);
				optionList.add(tempOption);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return optionList;
	}
		
	public String getCellData(int RowNum, int ColNum, XSSFSheet excelWSheet) throws Exception{		
		try{
			XSSFCell cell = excelWSheet.getRow(RowNum).getCell(ColNum);
			String cellData = cell.getStringCellValue();
			return cellData;
		}catch (Exception e){
			return"";	
		}	
	}
	
	public String getCellData(int RowNum, int ColNum, String sheetName) throws Exception{
		try {
			XSSFSheet excelWSheet = excelWBook.getSheet(sheetName);
			String cellData = getCellData(RowNum, ColNum,excelWSheet);
			return cellData;
		}catch(Exception e) {
			return"";
		}		
	}
	
	public String getColumnHeader(int colNum, XSSFSheet excelWSheet) throws Exception{
		try {
			String colHeader = getCellData(0, colNum, excelWSheet);
			return colHeader;
		}catch(Exception e) {
			return "";
		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public int getColumnCount(String sheetName) {
		try {
			XSSFSheet excelWSheet = excelWBook.getSheet(sheetName);
			XSSFCell cell;
			int count=0;			
			do {
				cell  = excelWSheet.getRow(0).getCell(count);
				count++;
			}while((!cell.equals("")));			
			return count;
		}catch(Exception e) {
			return 0;
		}
	}
}
