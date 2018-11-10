package configAutomation;

public class ProductOption {
	public String originalString;
	public String action;
	public String attributeIdPair;
	public String selection;
	
	public ProductOption(String temp, String tempSelection) {
		originalString = temp;
		System.out.println("ProductOption String: " + temp);
		action = temp.split(":")[0];
		attributeIdPair = temp.split(":")[1];
		selection = tempSelection;
	}
}
