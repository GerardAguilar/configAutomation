package configAutomation;

import java.util.ArrayList;

public class Product {
	ArrayList<ProductOption> optionsConfiguration;
	String sheet;
	String productName;
	
	public Product(String targetSheet, String targetProduct) {
		//TODO Product.Product()
		optionsConfiguration = new ArrayList<ProductOption>(); 
		sheet = targetSheet;
		productName = targetProduct; 
	}
	
	public void extractTaggedOptions(int rowCount, ArrayList<ProductOption> completeList, ExcelUtilities excelUtilities) {
		ArrayList<ProductOption> extracted = new ArrayList<ProductOption>();
		//find column# from productName
		int columnId = getColumnId(excelUtilities);
		String tempData = "";
		for(int i=0; i<rowCount; i++) {
			
			//if cellData is "x"
			try {
				tempData = excelUtilities.getCellData(i, columnId, sheet);
			} catch (Exception e) {
				tempData = "";
				e.printStackTrace();
			}
			
			if(tempData.equals("x")) {
				//then get equivalent from completeOptionsList
				ProductOption optionFromCompleteList = completeList.get(i);
				//add to optionsConfiguration
				optionsConfiguration.add(optionFromCompleteList);
			}
		}
	}
	
	public ProductOption getOption(int column, int row, String sheetName) {
		ProductOption productOption = new ProductOption();
		return productOption;
		
	}
	
	private int getColumnId(ExcelUtilities excelUtilities) {
		int columnId = -1;
		int columnCount = excelUtilities.getColumnCount(sheet);
		String tempColumnName = "";
		for(int i=0; i<columnCount; i++ ) {
			try {
				tempColumnName = excelUtilities.getCellData(0, i, sheet);
			} catch (Exception e) {
				tempColumnName = "";
				e.printStackTrace();
			}
			
			if(tempColumnName.equals(productName)) {
				columnId = i;
				break;
			}
		}
		return columnId;
	}
}
