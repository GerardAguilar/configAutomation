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
	public int getRowCount() {
		//TODO: ExcelUtilities.getRowCount()
		int localRowCount = 0;
		return localRowCount;
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
	
	public ArrayList<ProductOption> getInitialOptionsList(int rowCount) {
		return getOptionsList(1, rowCount);
	}
	
	public ArrayList<ProductOption> getOptionsList(int column, int rowCount){
		ArrayList<ProductOption> optionList = new ArrayList<ProductOption>();
		for(int i=0; i<rowCount; i++) {
			ProductOption tempOption = new ProductOption();
			optionList.add(tempOption);
		}
		return optionList;
	}
	
	public void openExcelFile(String filename) {
		
	}
	
	public String getCellData(int RowNum, int ColNum, String sheetName) throws Exception{
		
		try{
			XSSFSheet excelWSheet = excelWBook.getSheet(sheetName);

			XSSFCell cell = excelWSheet.getRow(RowNum).getCell(ColNum);

			String cellData = cell.getStringCellValue();

			return cellData;

		}catch (Exception e){

			return"";	
		}	
}
}
