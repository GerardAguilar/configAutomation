package configAutomation;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

public class AutomationTools {
	WebDriver driver;
	public String fitnesseRootDirectory;
	public String homePage;
	
	public AutomationTools() {
		driver = new ChromeDriver();
		homePage = "https://andersen.inhance.io/app/test.html";
		driver.get(homePage);
		Dimension d = new Dimension(420,660);
		driver.manage().window().setSize(d);
		fitnesseRootDirectory = "C:\\eclipse-workspace\\configAutomation\\FitNesseRoot\\files\\";
	}
	
	public void waitThenSelect(final String customAttributeIdPair, String value) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)			    
				.withTimeout(12, TimeUnit.SECONDS)
			    .pollingEvery((long) .5, TimeUnit.SECONDS)
			    .ignoring(NoSuchElementException.class)
				.ignoring(ElementNotVisibleException.class);
		
		JavascriptExecutor jseWait = (JavascriptExecutor)driver;
		Wait<JavascriptExecutor> waitJse = new FluentWait<JavascriptExecutor>(jseWait)
			    .withTimeout(6, TimeUnit.SECONDS)
			    .pollingEvery(2, TimeUnit.SECONDS)
			    .ignoring(NoSuchElementException.class)
			    .ignoring(ElementNotVisibleException.class);
		
		wait.until(new Function<WebDriver, Boolean>(){
			public Boolean apply(WebDriver driverCopy) {
				System.out.println("waitFor(): " + customAttributeIdPair);
				Boolean elementIsPresent = false;
				WebElement tempElement = driver.findElement(By.cssSelector("*["+customAttributeIdPair+"]"));
				if(tempElement!=null) {
					elementIsPresent = true;
				}else {
					elementIsPresent = false;
				}
				return elementIsPresent;						
			}
		});	
		
		select(customAttributeIdPair, value);
	}
	
	public void select(String customAttributeIdPair, String value) {
		System.out.println("select: "+customAttributeIdPair + "|"+value);
		WebElement mySelectElement = driver.findElement(By.cssSelector("*["+customAttributeIdPair+"]"));
		Select dropdown = new Select(mySelectElement);
		dropdown.selectByVisibleText(value);
	}
	
	public void click(String customAttributeIdPair, String value) {
		WebElement element = driver.findElement(By.cssSelector("*["+customAttributeIdPair+"]"));			
		element.click();
	}
	
	//TODO: Mouse manipulation that focuses on the interaction of the canvas, may need to be on a different class
	public void mouse(String selection, String actionType) {
		int xOffset=0;
		int yOffset=0;
		if(!selection.equals("")) {
			xOffset = Integer.parseInt(selection.split(",")[0]);
			yOffset = Integer.parseInt(selection.split(",")[1]);
			switch(actionType) {
				case "move":
					mouseMove(xOffset, yOffset);
					break;
				case "drag":
					mouseDrag(xOffset, yOffset);
					break;
			}
		}
		else {
			switch(actionType) {
				case "click":
					mouseManualClick();
					break;
				case "hold":
					mouseHold();
					break;
				case "release":
					mouseRelease();
					break;
			}
		}
	}
	
	public void mouseManualClick(){
		mouseHold();
		mouseRelease();
	}
	public void mouseHold() {
		try {
			Robot robot = new Robot();
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		} catch (AWTException e) {
			e.printStackTrace();
		}		
	}
	public void mouseRelease() {
		try {
			Robot robot = new Robot();
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		} catch (AWTException e) {
			e.printStackTrace();
		}		
	}
	public void mouseMove(int xOffset, int yOffset) {
			try {			
				Robot robot = new Robot();
				robot.mouseMove(xOffset, yOffset);
			} catch (AWTException e) {
				e.printStackTrace();
			}			
	}
	
	public void mouseDrag(int xOffset, int yOffset) {
		//TODO: the below target needs to be generic
		WebElement target = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/canvas[1]"));
		Actions builder = (new Actions(driver)).dragAndDropBy(target, xOffset, yOffset);
		builder.perform();
	}	
	
	@SuppressWarnings("unused")
	public void screenshot(String sheetName, String configName) {
		File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Long timestamp = (new Timestamp(System.currentTimeMillis())).getTime();
//		String currentFilename = fitnesseRootDirectory+""+navId+".png";
		String currentFilename = fitnesseRootDirectory+""+sheetName+"_"+configName+".png";
		
		try {
			FileUtils.copyFile(screenshotFile, new File(currentFilename));					
		} catch (IOException e) {
			e.printStackTrace();
		}					
	}
	

}
