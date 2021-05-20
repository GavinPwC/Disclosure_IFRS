package Pages;

import SetUp.CoreFunctions;
import SetUp.SetupFramework;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

public class ClientPage extends SetupFramework {
	
	public WebDriver driver;
	WebElement element;
	
	By addButtonClient = By.xpath("//*[@id=\"add-client-button\"]");
	By addButtonSearchResult = By.xpath("/html/body/div[5]/div[5]/div[4]/div/div/div/div/div/div/div[2]/div[2]/form/div[3]/div/div/div[2]/table/tbody/tr[1]/td[3]/a");
	By clientCreateButton = By.xpath("//*[@id=\"add-new-client\"]");
	
	By nameField = By.xpath("//*[@id=\"Name\"]");
	By searchField = By.xpath("//*[@id=\"acl-search-text\"]");
	By clientField = By.xpath("//*[@id=\"add-client-parent\"]/span/span[1]/span/span[2]/span");
	By clientField2 = By.xpath("//*[@id=\"ClientID\"]");

	By creatorIcon = By.xpath("//*[@id=\"add-client-form\"]/div[2]/div/div/div[2]/table/tbody/tr[1]/td[2]");
	By adminIcon = By.xpath("//*[@id=\"add-client-form\"]/div[2]/div/div/div[2]/table/tbody/tr[2]/td[2]");
	By everyoneIcon = By.xpath("//*[@id=\"add-client-form\"]/div[2]/div/div/div[2]/table/tbody/tr[2]/td[2]");
	By sdcAdminIcon = By.xpath("//*[@id=\"add-client-form\"]/div[2]/div/div/div[2]/table/tbody/tr[3]/td[2]");

	By clientSearchResult = By.xpath("//*[@id=\"select2-ClientID-results\"]/li[2]");
	//By clientField2 = By.id("ClientID");
	By clientSearchField = By.xpath("/html/body/span/span/span[2]/ul/li[2]"); // /html/body/span/span/span[1]/input
	//By clientField = By.xpath("//*[@id=\"select2-ClientID-container\"]/span");
	//*[@id="select2-ClientID-container"]
	//*[@id="ClientID"]
	//*[@id="select2-ClientID-container"]/span
	//*[@id="select2-ClientID-container"/span]
	//*[@id="add-client-form"]/div[2]/div/div/div[2]/table/tbody/tr[2]/td[2]
	//By clientField2 = By.xpath("/html/body/div[5]/div[5]/div[3]/div/div/div/div/div/div/div[2]/div[2]/div/form/div/div[1]/div[1]/div[1]/div/div/div/div/select");
	
	public String clientName = "GavIFRS_Automation2";
	
	public ClientPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
	
	CoreFunctions cf=new CoreFunctions(driver);
	
	// Create client via client menu
	public void createClient(WebDriver driver) {
		
	}
	
	// Create client via Answersets menu
	@Step("Create a new client via the answerset section")
	public void createClientAS(WebDriver driver) throws InterruptedException {  
		
		String winHandle = driver.getWindowHandle();
		
		driver.switchTo().window(winHandle);
		cf.waitForDesiredElement(driver, addButtonClient, 10);
		cf.clickElement(driver, addButtonClient);

		// Set the client name
		cf.waitForDesiredElement(driver, nameField, 5);
		cf.setTextBox(driver, nameField, clientName); //Delete autoClient1

		// Execute confirmSecurity module below
		confirmSecurity(driver);
		cf.scrollToElement(driver, clientCreateButton);

		// execute addUserAndGroup module below
		addUserAndGroup(driver, "Edgal");
		cf.clickElement(driver, clientCreateButton);
		
	}

	@Step("Select an existing client to begin a new answerset")
    public void selectClientAS(WebDriver driver) throws InterruptedException {

		cf.waitForDesiredElement(driver, clientField, 50);
		Thread.sleep(3000);
    	cf.clickElement(driver, clientField);
		Thread.sleep(3000);
		cf.waitForDesiredElement(driver, clientField2, 50);

		// Scroll through list to find name that matches your clientName (See variable above)
		for(Integer i=1; i<=15;i++) {

			By new_client_AutoClient = By.xpath("//*[@id=\"select2-ClientID-results\"]/li["+i+"]");
			String clientVal= driver.findElement(new_client_AutoClient).getText();
			if (clientVal.equalsIgnoreCase(clientName)) {

				System.out.println("Selecting "+clientVal+" as client");
                cf.clickElement(driver, new_client_AutoClient);
				break;
			}else{
				System.out.println("Searching for record...");
			}
		}
	}

	@Step("Check that the creator and both admin's have been assigned")
    public void confirmSecurity(WebDriver driver) {
    	
    	try {
    		cf.waitForDesiredElement(driver, creatorIcon, 5);
    		System.out.println( "Creator assigned") ;
        	cf.waitForDesiredElement(driver, adminIcon, 5);
        	System.out.println( "Admin assigned") ;
        	cf.waitForDesiredElement(driver, sdcAdminIcon, 5);
        	System.out.println( "SDC Admin assigned") ;
        	System.out.println( "All security info accurate ...");    		
    	}catch (Exception e) {
      		Reporter.log(e.getMessage());
    	}		
	 }

	@Step("Check that the creator and both admin's have been assigned in QA environment")
	public void confirmSecurityQA(WebDriver driver) {

		try {
			cf.waitForDesiredElement(driver, creatorIcon, 5);
			System.out.println( "Creator assigned") ;
			cf.waitForDesiredElement(driver, everyoneIcon, 5);
			System.out.println( "Everyone assigned") ;
			System.out.println( "All security info accurate ...");
		}catch (Exception e) {
			Reporter.log(e.getMessage());
		}
	}

	@Step("Add an authorised user to view tailoring and checklist")
     public void addUserAndGroup(WebDriver driver, String usrName) {

		// usrName variable will set the additional user
    	 cf.setTextBox(driver, searchField, usrName);
    	 WebElement searchbox = driver.findElement(searchField);
    	 searchbox.sendKeys(Keys.ENTER);
    	 cf.waitForDesiredElement(driver, addButtonSearchResult, 10);
    	 driver.findElement(addButtonSearchResult).click();
    	 System.out.println(usrName+ " has been added to authorised users") ;
	 }

	 @Step("Add a specific client in a list of clients")
     public void addClientSearchResult(WebDriver driver, int resultPosition) {

    	 By clientSearchResult = By.xpath("/html/body/span/span/span[2]/ul/li["+resultPosition+"]");
    	 cf.clickElement(driver, clientSearchResult);
     }

}
