package configAutomation;

import java.util.ArrayList;



public class TestFixture {
	String filename;
	ExcelUtilities excelUtilities;
	ArrayList<ProductOption> completeOptionsList;
	AutomationTools automationTools;
	int rowCount;
	String appendedImageString;
	
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
	//This call is made for every product column
	public String simulate() {

		appendedImageString = "";
		rowCount = excelUtilities.getRowCount(targetSheet);
		completeOptionsList = excelUtilities.getInitialOptionsList(rowCount, targetSheet);
				
		Product product = new Product(targetSheet, targetProduct);
		product.extractTaggedOptions(rowCount, completeOptionsList, excelUtilities);
		
		try {
			automationTools.startRecording(product.productNameParsed);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		navigate(product);		
		String videoString = "<video width='960' height='640' controls='controls'>" + 
				"<source src=\"http://localhost/files/"+ product.productNameParsed +".mp4\" type='video/mp4'>" + 
				"</video>";
//		String slideShowBeginning = "<div class='slideshow-container'>";
//		String slideShowEnding = "<a class='prev' onclick='plusSlides(-1)'>&#10094;</a>"+
//								"<a class='next' onclick='pluSlides(1)'>&#10095;</a>"+
//								"</div>";
//		String returnMe = videoString+ slideShowBeginning + appendedImageString + slideShowEnding;			
		String returnMe = "<div>" + videoString+appendedImageString + "</div>";
		try {
			automationTools.stopRecording();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnMe;
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
			System.out.print("\n" + 
							"action: "+action+"\n" +
							"attributeIdPair: "+attributeIdPair+"\n" +
							"selection: " +selection+"\n"
					);
			if(action.equals("select")) {
				System.out.println("select");
				automationTools.select(attributeIdPair, selection);
				appendedImageString = automationTools.screenshot(product.productName,i+"",optionCount);
			}else if(action.equals("click")) {
				System.out.println("click");
				automationTools.click(attributeIdPair, selection);
				appendedImageString = automationTools.screenshot(product.productName,i+"",optionCount);
			}else if(action.equals("mouse")) {
				System.out.println("mouse");
				automationTools.mouse(attributeIdPair, selection);
				appendedImageString = automationTools.screenshot(product.productName,i+"",optionCount);
			}else {
				System.out.println(action + " could not be found in the available actions");
			}
		}
	}
}
