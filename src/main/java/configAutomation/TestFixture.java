package configAutomation;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;


public class TestFixture {
	String filename;
	ExcelUtilities excelUtilities;
	ArrayList<ProductOption> completeOptionsList;
	AutomationTools automationTools;
	int rowCount;
	String imageString;
	String videoString;
	
	//Fitnesse attributes and methods
	String targetSheet;
	String targetProduct;	
	String targetExcelFilename;
	String targetChrome;
	
	public void initializeTargetExcelSheet(String temp) {
		targetExcelFilename = temp;
		System.out.println("Target Excel Sheet = " + targetExcelFilename);
	}	
	public void initializeTargetChrome(String temp) {
		targetChrome = temp;
		System.out.println("Target Chrome = " + targetChrome);
	}	
	
	public void setTargetSheet(String temp) {
		targetSheet = temp;
		System.out.println("Target Sheet = " + targetSheet);
	}	
	
	public TestFixture() {
		//TODO: Change excel file to account for being in the resource folder instead, or at least have a configurable location
	    File directory = new File("./");
	    System.out.println("Directory: " + directory.getAbsolutePath());
//		filename = "C:\\Users\\gaguilar\\Desktop\\Andersen.xlsx";	
//	    excelUtilities = new ExcelUtilities(filename);		
	}		
	
	public boolean initialize() {
		try {
			excelUtilities = new ExcelUtilities(targetExcelFilename);
			automationTools = new AutomationTools(targetChrome);
			return true;
		}catch(Exception e) {
			System.out.println("initialize() error: "+e);
			return false;
		}		
	}
	
	//Each simulate call creates one and only one product
	//targetSheet and targetProduct are set by the Fitnesse Spreadsheet
	//Each simulate call also looks at the sheet
	//This call is made for every product column
	public String simulation(String target) {
		targetProduct = target;
		imageString = "";
		videoString = ""; 
		rowCount = excelUtilities.getRowCount(targetSheet);
		completeOptionsList = excelUtilities.getInitialOptionsList(rowCount, targetSheet);
				
		Product product = new Product(targetSheet, targetProduct);
		product.extractTaggedOptions(rowCount, completeOptionsList, excelUtilities);
		
		try {
			automationTools.startRecording(product.sheetName + "_" + product.productNameParsed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		navigate(product);		
		String returnMe = 
				"<table>"
				+ "<tbody>"
				+ 	"<tr>"
				+ 		"<td>"
				+ 			"<div>" 
				+ 				videoString
				+ 			"</div>"
				+ 		"</td>"
				+ 		"<td>"
				+ 			"<div>"
				+	 			imageString 
				+ 			"</div>"
				+ 		"</td>"
				+ 	"</tr>"
				+ "</tbody>"
				+ "</table>";
		try {
			automationTools.stopRecording();
		} catch (Exception e) {
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
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Long timestampId = timestamp.getTime();
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
				imageString = automationTools.screenshot(product.productName,i+"",optionCount);
			}else if(action.equals("click")) {
				System.out.println("click");
				automationTools.click(attributeIdPair, selection);
				imageString = automationTools.screenshot(product.productName,i+"",optionCount);
			}else if(action.equals("mouse")) {
				System.out.println("mouse");
				automationTools.mouse(attributeIdPair, selection);
				imageString = automationTools.screenshot(product.productName,i+"",optionCount);
			}else {
				System.out.println(action + " could not be found in the available actions");
			}
		}
		
		videoString = "<video id='"+timestampId+"'"
				+ "width='600' controls='controls'>" 
				+ "<source src=\"http://localhost/files/"
				+ product.sheetName + "_" + product.productNameParsed
				+ ".mp4\" type='video/mp4'></video>"
				+ "<script>"
				+ "var vid = document.getElementById('"+timestampId+"');"
				+ "vid.playbackRate = 4.0;"
				+ "</script>";	
	}
	
	public void closeBrowser() {
		automationTools.closeBrowser();
	}
}
