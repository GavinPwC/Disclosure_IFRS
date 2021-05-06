package Pages;

import SetUp.CoreFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Step;
import SetUp.SetupFramework;

public class LandingPage extends SetupFramework {

	By ssoButton= By.xpath("/html/body/div[2]/div/div/div/div/div/div[2]/div/div/div[1]/a");
	By emailField=By.xpath("//*[@id=\"Email\"]");
	By passwordField=By.xpath("//*[@id=\"Password\"]");
	By login=By.xpath("/html/body/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div/form/div[3]/div/div/div[2]/input");
	By credentials=By.xpath("//*[@id=\"initEmail\"]");
	By loginCredentials=By.xpath("/html/body/app-root/div/app-login-form/div/div[2]/div/div/div[2]/form/div[1]/div/div[2]/button");

	//By ssoButton= By.name("Single Sign On");
	//By credentials = By.name("ctl00$phCenter$txtEMAIL");
	//By loginCredentials = By.name("ctl00$phCenter$btnSubmitx");

	String email = "gavin.x.caldwell@pwc.com";
	String password = "Cassie20**";
	
	CoreFunctions cf=new CoreFunctions(driver);
	
	@Step("Login production environment ")
	public void goToHomePage(WebDriver driver) {
		
		//driver.findElement(ssoButton).click(); 
		cf.waitForDesiredElement(driver, ssoButton, 10);
		cf.clickElement(driver, ssoButton);
		enterCredentials(driver, email);
	}
	
	@Step("Login QA environment ")
	public void goToHomePageQA(WebDriver driver) {

		cf.waitForDesiredElement(driver, emailField, 20);
		cf.setTextBox(driver, emailField, email);
		cf.setTextBox(driver, passwordField, password);
		cf.clickElement(driver, login);		
	}
	
	@Step("Enter email credentials for PwC authentication ")
	public void enterCredentials(WebDriver driver, String Login) {
	    
		cf.waitForDesiredElement(driver, credentials, 20);
		cf.setTextBox(driver, credentials, Login);
		cf.clickElement(driver, loginCredentials);
	}
	
	public WebElement getEmail(WebDriver driver)
	{
		return driver.findElement(emailField);
	}
	
	public WebElement getPassword(WebDriver driver)
	{
		return driver.findElement(passwordField);
	}
	
	public WebElement getLogin(WebDriver driver)
	{
		return driver.findElement(login);
	}

}
