package configAutomation;

import java.util.ArrayList;



public class TestFixture {
	String filename;
	ExcelUtilities excelUtilities;
	ArrayList<ProductOption> completeOptionsList;
	AutomationTools automationTools;
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
		filename = "C:\\Users\\gaguilar\\Desktop\\Andersen.xlsx";
		excelUtilities = new ExcelUtilities(filename);		
		automationTools = new AutomationTools();
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
	
	//Modifies the website based on the action:attribute='id' of each product option
	//First, decide what 'action'
	//Next, decide how to find the web component
	//Then, find that specific web component by id
	//Do this for all of the products options
	//Ex: click:id='ui-btn'
	public void navigate(Product product) {
		int optionCount = product.getOptionCount();
		String action;
		String attributeIdPair;
		String selection;
		System.out.println("Start navigation for " + product.productName + " ["+optionCount+"]");
		for(int i=0; i<optionCount; i++) {
			ProductOption tempOption = product.getOption(i);
			action = tempOption.action;
			attributeIdPair = tempOption.attributeIdPair;
			selection = tempOption.selection;
			switch(action) {
			case "select":
				System.out.println("select");
				automationTools.select(attributeIdPair, selection);
				automationTools.screenshot(product.productName,i+"");
				break;
			case "click":
				System.out.println("click");
				automationTools.click(attributeIdPair, selection);
				automationTools.screenshot(product.productName,i+"");
				break;
			case "mouse":
				System.out.println("mouse");
				automationTools.mouse(attributeIdPair, selection);
				automationTools.screenshot(product.productName,i+"");
				break;
			default:
				System.out.println(action + " could not be found in the available actions");
			}
		}
	}
	

}
