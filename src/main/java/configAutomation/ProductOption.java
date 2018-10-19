package configAutomation;

public class ProductOption {
	public String originalString;
	public String value;
	public String attributeIdPair;
	
	public ProductOption(String temp) {
		originalString = temp;
		value = temp.split(":")[0];
		attributeIdPair = temp.split(":")[1];
	}
}
