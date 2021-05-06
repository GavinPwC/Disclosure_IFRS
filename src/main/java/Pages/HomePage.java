package Pages;

import SetUp.CoreFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.qameta.allure.Step;
import SetUp.SetupFramework;

//import setUp.CoreFunctions;



public class HomePage extends SetupFramework {
	
	public WebDriver driver;
	WebElement element;
	By title = By.xpath("//*[@id=\"home-link\"]/span/img");
	By menuIcon = By.xpath("//*[@id=\"navMenuLink\"]/div/span[1]");
	By answersetMenuItem = By.xpath("//*[@id=\"answersets-link\"]/span[2]");
	By clientMenuItem = By.xpath("//*[@id=\"clients-link\"]/span[2]");
	By reportingMenuItem = By.xpath("//*[@id=\"reporting-link\"]/span[2]");
	//By title = By.xpath("/html/body/div[2]/div/div/div/div/div/div[1]/div/span/img");


	public HomePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
	
	CoreFunctions cf=new CoreFunctions(driver);

	@Step("Confirm home page title and navigate to answerset page ")
	public void assertTitles(WebDriver driver) throws InterruptedException {

		cf.waitForDesiredElement(driver, menuIcon, 50);
		String aTitle = driver.getTitle();
		String eTitle = "Home Page - Disclose";
		String eTitle2 = "Answer Sets - Disclose";

		// Assert titles, all title to be asserted can be added here
		System.out.println(aTitle) ;
		if (aTitle.contentEquals(eTitle)) {
		System.out.println( "Title is correct, you are now on the home page.");
		} else if (aTitle.contentEquals(eTitle2)){
			System.out.println( "Title is correct, you are now on the answersets page.");
		} else {
		System.out.println( "Title is incorrect" );
		}
		//assertLogoImage2(driver);   **WORKING PROGRESS**
		cf.clickElement(driver, menuIcon);

		// Select answersets from menu option, use this method to select menu options
		selectMenuOption(driver, answersetMenuItem);
		Thread.sleep(3000);
	}

	@Step("Generate report tailored OR main checklist questionaire")
	public void generateReport(WebDriver driver) throws InterruptedException {

		cf.waitForDesiredElement(driver, menuIcon, 50);
		String aTitle = driver.getTitle();
		String eTitle = "Home Page - Disclose";
		String eTitle2 = "Answer Sets - Disclose";

		// Assert titles, all title to be asserted can be added here
		System.out.println(aTitle) ;
		if (aTitle.contentEquals(eTitle)) {
			System.out.println( "Title is correct, you are now on the home page.");
		} else if (aTitle.contentEquals(eTitle2)){
			System.out.println( "Title is correct, you are now on the answersets page.");
		} else {
			System.out.println( "Title is incorrect" );
		}
		//assertLogoImage2(driver);   **WORKING PROGRESS**
		cf.clickElement(driver, menuIcon);

		// Select answersets from menu option, use this method to select menu options
		selectMenuOption(driver, reportingMenuItem);
		Thread.sleep(3000);
	}

	@Step("Confirm presence of disclose logo")
	public void assertLogoImage(WebDriver driver) throws InterruptedException {
		
		boolean logoPresent = driver.findElement(title).isDisplayed();
		Assert.assertTrue(logoPresent);
		System.out.println( "Logo is displayed");		
	}

	@Step("Confirm presence of disclose logo")
	public void assertLogoImage2(WebDriver driver) {

	    try {
	      cf.waitForDesiredElement(driver, title, 5);
	      System.out.println( "Logo is displayed");
	    } catch (TimeoutException e) {
	      throw new IllegalStateException("Logo is NOT displayed");
	    }
	  }
	
	@Step("Select a specific menu option")
	public void selectMenuOption(WebDriver driver, By strelement) throws InterruptedException {
		
		Thread.sleep(3000);
		cf.clickElement(driver, strelement);
	}
}
