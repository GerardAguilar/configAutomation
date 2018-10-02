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
	
	//TODO: Product.extractTaggedOptions()
	public ArrayList<ProductOption> extractTaggedOptions(int rowCount, ArrayList<ProductOption> completeList) {
		ArrayList<ProductOption> extracted = new ArrayList<ProductOption>();
		//find column# from productName
		//TODO: Product.findColumn()
		
		for(int i=0; i<rowCount; i++) {
			
			//if cellData is "x"
			
			//then get equivalent from completeOptionsList
			//TODO ExcelUtilities.getCellData()
			//TODO create ProductOption from cellData
			//TODO ProductOption.getOption()
			//Add to optionsConfiguration
		}
		return null;
	}
	
	public ProductOption getOption(int column, int row, String sheetName) {
		ProductOption productOption = new ProductOption();
		return productOption;
		
	}
}
