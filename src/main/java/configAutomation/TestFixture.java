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
		//TODO: TestFixture.TestFixture() excel filename
		filename = "";
		excelUtilities = new ExcelUtilities(filename);
		rowCount = excelUtilities.getRowCount();
		completeOptionsList = excelUtilities.getInitialOptionsList(rowCount);
		
	}	
	
	//Each simulate call creates one and only one product
	//targetSheet and targetProduct are set by the Fitnesse Spreadsheet
	//Each simulate call also looks at the sheet
	public void simulate() {
		Product product = new Product(targetSheet, targetProduct);
		ArrayList<ProductOption> optionsList = new ArrayList<ProductOption>();
		optionsList = product.extractTaggedOptions(rowCount, optionsList);
	}
	
	
}
