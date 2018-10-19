package configAutomation;

import java.util.ArrayList;

public class Product {
	ArrayList<ProductOption> optionsConfiguration;
	String sheetName;
	String productName;
	
	public Product(String targetSheet, String targetProduct) {
		optionsConfiguration = new ArrayList<ProductOption>(); 
		sheetName = targetSheet;
		productName = targetProduct; 
	}
	
	public int getOptionCount() {
		return optionsConfiguration.size();
	}
	
	public void extractTaggedOptions(int rowCount, ArrayList<ProductOption> completeOptionsList, ExcelUtilities excelUtilities) {
		//find column# from productName
		int columnId = getColumnId(excelUtilities);
		String tempData = "";
		for(int i=0; i<rowCount; i++) {
			
			//if cellData is "x"
			try {
				tempData = excelUtilities.getCellData(i, columnId, sheetName);
			} catch (Exception e) {
				tempData = "";
				e.printStackTrace();
			}
			
			if(tempData.equals("x")) {
				//then get equivalent from completeOptionsList
				ProductOption optionFromCompleteList = completeOptionsList.get(i);
				//add to optionsConfiguration
				optionsConfiguration.add(optionFromCompleteList);
			}
		}
	}
	
	public ProductOption getOption(int index) {
		return optionsConfiguration.get(index);
	}
	/**
	 * Gets the columnId corresponding to this product's name
	 * @param excelUtilities
	 * @return
	 */
	private int getColumnId(ExcelUtilities excelUtilities) {
		int columnId = -1;
		int columnCount = excelUtilities.getColumnCount(sheetName);
		String tempColumnName = "";
		for(int i=0; i<columnCount; i++ ) {
			try {
				tempColumnName = excelUtilities.getCellData(0, i, sheetName);
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
