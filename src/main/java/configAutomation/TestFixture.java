package configAutomation;

import java.util.ArrayList;

public class TestFixture {
	String filename;
	ExcelUtilities excelUtilities;
	ArrayList<ProductOption> completeOptionsList;
	int rowCount;
	
	//Fitnesse attributes and methods
	String targetSheet;
	String targetProduct;	
	public void setTargetSheet(String temp) {
		targetSheet = temp;
	}	
	public String getTargetSheet() {
		return targetSheet;
	}	
	public void setTargetProduct(String temp) {
		targetProduct = temp;
	}
	public String getTargetProduct() {
		return targetProduct;
	}	
	
	public TestFixture() {
		filename = "";
		excelUtilities = new ExcelUtilities(filename);		
	}	
	
	//Each simulate call creates one and only one product
	//targetSheet and targetProduct are set by the Fitnesse Spreadsheet
	//Each simulate call also looks at the sheet
	public void simulate() {
		rowCount = excelUtilities.getRowCount(targetSheet);
		completeOptionsList = excelUtilities.getInitialOptionsList(rowCount, targetSheet);
		
		Product product = new Product(targetSheet, targetProduct);
		product.extractTaggedOptions(rowCount, completeOptionsList, excelUtilities);
		navigate(product);
	}
	
	public void navigate(Product product) {
		int optionCount = product.getOptionCount();
		for(int i=0; i<optionCount; i++) {
			ProductOption tempOption = product.getOption(i);

		}
	}
}
