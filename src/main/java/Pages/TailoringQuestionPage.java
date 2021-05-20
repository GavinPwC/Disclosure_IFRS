package Pages;

import SetUp.CoreFunctions;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class TailoringQuestionPage {
	
	public WebDriver driver;
	WebElement element;
	By addButton = By.xpath("//*[@id=\"add-answerset-button\"]");
	
	By answerField = By.xpath("//*[@id=\"select2-tickonceanswer-container\"]");
	By answerField2 = By.xpath("//*[@id=\"tickonceanswer\"]");

	By answerbox1 = By.id("tickmanyanswer0");
	By answerbox2 = By.id("tickmanyanswer1");
	By answerbox3 = By.id("tickmanyanswer2");
	By answerbox4 = By.id("tickmanyanswer3");

	//input[contains(@id,'tickmanyanswer0')]
	By dateField = By.xpath("//*[@id=\"dateanswer\"]");
	By nextUnanswered = By.xpath("//*[@id=\"question-nav\"]/div[3]/div[1]/a/span[1]");
	By previousUnanswered = By.xpath("//*[@id=\"question-nav\"]/div[1]/div[2]/a/span[2]");
	By questionCount = By.xpath("//*[@id=\"counts\"]/div[3]/ul/li[1]/a/span[1]");
	By acceptAcknowledgement = By.xpath("//*[@id=\"infoanswer\"]");
	By T111_acknowledgement = By.xpath("//*[@id=\"question\"]/div/div[1]/div/h3/span[2]");
	By questionText = By.xpath("//*[@id=\"question\"]/div/div[1]/div/h3/span[1]");
	By questionDisc = By.xpath("//*[@id=\"question\"]/p");
	By Folder = By.xpath("//*[@id=\"section-contents\"]/ul/li[1]/div");
	By T124_answer = By.xpath("//*[@id=\"question\"]/div/div[1]/div/h3/span[2]");

	By tAssumptionText = By.xpath("//*[@id=\"tailoring-complete-body\"]/div[2]/div/p[2]");
	By tAssumptionTextMain = By.xpath("//*[@id=\"main-sections-statement\"]");
	By assumptionClientName = By.xpath("//*[@id=\"tailoring-complete-clientname\"]");
	By assumptionClientNameMain = By.xpath("//*[@id=\"main-complete-clientname\"]");
	By tAgreeButton = By.xpath("//*[@id=\"tailoring-complete-yes\"]");
	By tAgreeButtonMain = By.xpath("//*[@id=\"main-complete-yes\"]");
	By problemCount = By.xpath("//*[@id=\"counts\"]/div[3]/ul/li[3]/a/span[1]");
	By alertCount = By.xpath("//*[@id=\"counts\"]/div[3]/ul/li[4]/a/span[1]");
	By selectNotMember = By.id("tickmanyanswer0");
	By selectParent = By.id("tickmanyanswer1");
	By selectSubsidiary = By.id("tickmanyanswer2");


	By T114_acknowledgement = By.xpath("//*[@id=\"question\"]/div/div[1]/div/h3/span[1]");
	By tCompleteButton = By.xpath("//*[@id=\"tailoring-complete-button\"]");
	By tCompleteButtonMain = By.xpath("//*[@id=\"sections-complete-button\"]");
	//*[@id="tailoring-complete-yes"]

	public String optionY = "Yes";
	public String optionN = "No";
	public String AP_optionY = "Yes";
	public String AP_optionN = "No";
	public static final String accountingStartDate = "01/01/2020";
	public static final String accountingEndDate = "31/12/2020";
	
	public TailoringQuestionPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
	
	CoreFunctions cf=new CoreFunctions(driver);
	HomeScreenPage hs=new HomeScreenPage(driver);
	HomePage h=new HomePage(driver);
	//FRS102_103_105 fr=new FRS102_103_105(driver);


	@Step("Retrieve remaining unanswered questions")
	public void retrieveQuestionCount(WebDriver driver){

		String questionCountVal= driver.findElement(questionCount).getText();
		System.out.println("There are "+questionCountVal+" unanswered questions remaining for tailoring");
	}
    
	@Step("Retrieve current question being answered")
	public void retrieveQuestionAnswered(WebDriver driver){

		String questionTextVal= driver.findElement(questionText).getText();
		String questionDiscription= driver.findElement(questionDisc).getText();
		System.out.println(questionTextVal+ " Has been answered ");
		System.out.println("BODY OF QUESTION -> "+questionDiscription);
	}
    
	@Step("Retrieve folder name for question being answered")
	public void retrieveFolderName(WebDriver driver){

		String folderName= driver.findElement(Folder).getText();
		System.out.println("Present question IN "+folderName+" FOLDER") ;
		System.out.println("") ;

	}
	
	@Step("End to End tailoring process for DEMO")
    public void tailorQuestionnaire(WebDriver driver, int questionNumber) throws InterruptedException {

		String T124_Text_exp= "First Accounting period";

    	cf.waitForDesiredElement(driver, answerField, 60);
    	
    	for (int i=0; i<questionNumber; i++) {
       	     Select answerdropdown = new Select(driver.findElement(answerField2));
    	     answerdropdown.selectByVisibleText("Yes");
    	     cf.clickElement(driver, nextUnanswered);
			 retrieveQuestionCount(driver);
    	     Thread.sleep(3000);
    	     //answerdropdown.selectByVisibleText("Yes");
    	     //cf.clickElement(driver, nextUnanswered);	
    	}
    	
		//Accept all 4 acknowledments in the introduction section
		setAcceptAcknowledgement(driver);

		//  set start and end date for accounting period
		cf.waitForDesiredElement(driver, dateField, 50);

		// Set accounting start and end dates
		cf.setTextBox(driver, dateField, accountingStartDate);
		Thread.sleep(1000);
		cf.clickElement(driver, nextUnanswered);
		cf.waitForDesiredElement(driver, dateField, 50);
		cf.setTextBox(driver, dateField, accountingEndDate);
		Thread.sleep(1000);
		cf.clickElement(driver, nextUnanswered);
		String T124_Text_act = driver.findElement(T124_answer).getText();
		System.out.println(T124_Text_act+ " has been acknowledged") ;
		retrieveQuestionCount(driver);
		cf.clickElement(driver, acceptAcknowledgement);

		// Confirm entity's first accounting period
		cf.waitForDesiredElement(driver, answerField2, 50);
		Select accountingPeriodDropdown = new Select(driver.findElement(answerField2));
		accountingPeriodDropdown.selectByVisibleText(optionY );
		Thread.sleep(1000);
		retrieveQuestionCount(driver);
		Thread.sleep(1000);
		cf.clickElement(driver, nextUnanswered);
		answerQuestionaire(driver);

	}



    // Introduction Folder
    @Step("Accept initial acknowledgements for DEMO")
	public void setAcceptAcknowledgement(WebDriver driver) throws InterruptedException {

		String T111_Text_exp= "Purpose of the checklist";
		String T112_Text_exp= "Listing Rules";
		String T113_Text_exp= "Purpose of the checklist";
		String T114_Text_exp= "Introduction";

		cf.waitForDesiredElement(driver, acceptAcknowledgement, 50);
		String T111_Text_act = driver.findElement(T111_acknowledgement).getText();
		System.out.println(T111_Text_act+ " has been acknowledged") ;
		Thread.sleep(1000);
		cf.clickElement(driver, acceptAcknowledgement);

		cf.waitForDesiredElement(driver, acceptAcknowledgement, 50);
		String T112_Text_act = driver.findElement(T111_acknowledgement).getText();
		System.out.println(T112_Text_act+ " has been acknowledged twice");
		Thread.sleep(1000);
		cf.clickElement(driver, acceptAcknowledgement);

		cf.waitForDesiredElement(driver, acceptAcknowledgement, 50);
		String T113_Text_act = driver.findElement(T111_acknowledgement).getText();
		System.out.println(T113_Text_act+ " has been acknowledged");
		Thread.sleep(1000);
		cf.clickElement(driver, acceptAcknowledgement);

		retrieveQuestionCount(driver);

	}
    
	@Step("Respond to a set of questions with a specific response for DEMO")
	public void questionnaireResponses(WebDriver driver, int questionAmount, String response) throws InterruptedException {

		cf.waitForDesiredElement(driver, answerField, 50);

		for (int i=0; i<questionAmount; i++) {
			Select answerdropdown = new Select(driver.findElement(answerField2));
			answerdropdown.selectByVisibleText(response);

			// Execute the below methods
			retrieveFolderName(driver);
			retrieveQuestionCount(driver);
			retrieveQuestionAnswered(driver);
			checkProblem(driver);
			checkAlert(driver);

			Thread.sleep(1000);
			cf.clickElement(driver, nextUnanswered);
			Thread.sleep(3000);
		}
	}
    
	@Step("Answer questionaire with specific responses for DEMO")
	public void answerQuestionaire(WebDriver driver) throws InterruptedException {

		//General Folder
		questionnaireResponses(driver, 10, "No");
	}
	
	@Step("Complete the tailoring phase and display tailoring summary")
	public void completeTailoring(WebDriver driver) throws InterruptedException {


		//cf.clickElement(driver, tCompleteButton);
		cf.waitForDesiredElement(driver, tAgreeButton, 50);

		// Display client name
		String  assumptionClient = driver.findElement(assumptionClientName).getText();
		System.out.println("Tailoring complete for "+assumptionClient);

		// Display confirmation text
		String  tAssumption = driver.findElement(tAssumptionText).getText();
		System.out.println("Please confirm text:  "+tAssumption);

		// Click agree button
		cf.waitForDesiredElement(driver, tAgreeButton, 50);
		cf.clickElement(driver, tAgreeButton);	
		
	}

	@Step("Complete current section of main checklist and display summary")
	public void completeTailoringMain(WebDriver driver) throws InterruptedException {

		//cf.clickElement(driver, tCompleteButtonMain);
		cf.waitForDesiredElement(driver, tAgreeButtonMain, 50);

		// Display client name for main checklist
		String  assumptionClient = driver.findElement(assumptionClientNameMain).getText();
		System.out.println("Tailoring complete for "+assumptionClient);

		// Display confirmation text for main checklist
		String  tAssumption = driver.findElement(tAssumptionTextMain).getText();
		System.out.println("Please confirm text:  "+tAssumption);

		// click agree button
		cf.clickElement(driver, tAgreeButtonMain);
	}
	
	@Step("Display summary upon completion of tailoring")
	public void displayTailoringSummary(WebDriver driver) throws InterruptedException {
		
		By displayStatus = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[2]/dl/dd[1]");
		By displayTailoringcompletedAt = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[2]/dl/dd[4]");
		By displayTailoringcompletedBy= By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[2]/dl/dd[3]");
		By displayChecklistVersion= By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl/dd[3]");
		By displayPeriodEnd= By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl/dd[5]");
		By displayLastAccessed= By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[2]/dl/dd[2]");

		cf.waitForDesiredElement(driver, displayStatus, 120);

		System.out.println("Current status of tailoring: " + displayStatus);
		System.out.println("Tailoring was completed at: " + displayTailoringcompletedAt);
		System.out.println("Tailoring was completed by: " + displayTailoringcompletedAt);
		System.out.println("Tailoring was completed by: " + displayTailoringcompletedBy);
		System.out.println("Checklist version used: " + displayChecklistVersion);
		System.out.println("Tailoring period end: " + displayPeriodEnd);
		System.out.println("Tailoring last access by: " + displayLastAccessed);
	}
	
	@Step("Reviewer accepts tailoring")
	public void completeReviewAccept(WebDriver driver) throws InterruptedException {
		
		By reviewButton= By.xpath("//*[@id=\"review-complete-button\"]");
		//By reviewButton= By.id("review-complete-button");
		By addComment= By.xpath("//*[@id=\"Comments\"]");
		//By reviewAccept= By.xpath("//*[@id=\"review-complete-accept\"]");
		//By reviewAccept= By.tagName("Button");
		By reviewAccepted= By.id("review-complete-accept");
		By reviewAcceptedXpath= By.xpath("//*[@id=\"review-complete-accept\"]");
		By reviewAcceptedTag= By.tagName("button");
		By reviewAcceptedXpath2=By.xpath("/html/body/div[4]/div[5]/div[3]/div/div/div/div/div/div/div[2]/div[2]/form/div[4]/div/div/div[1]/button");

		String comment = "Happy to accept";
		cf.waitForDesiredElement(driver, reviewButton, 30);
		Thread.sleep(5000);

		// Click review button to display popup
		try {

			driver.findElement(reviewButton).click();
			System.out.println("Review in process ... ");
		} catch (Exception e) {

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(reviewButton));
			System.out.println("Review in process ... ");
		}

		Thread.sleep(2000);
		//cf.waitForDesiredElement(driver, reviewAccepted, 20);
		//driver.findElement(By.id("review-complete-form")).submit();
		//Thread.sleep(10000);

		// Enter accept comments
		cf.scrollToElement(driver, addComment);
		cf.setTextBox(driver, addComment, comment);
		System.out.println("Comment added ... ");
		cf.waitForElementToBeClickable(driver, reviewAccepted, 20);
        cf.waitForElementPresent(driver,reviewAccepted,20);

		try {

			WebElement reviewAccept = driver.findElement(By.id("review-complete-accept"));
			WebElement reviewAcceptXpath2 = driver.findElement(By.xpath("/html/body/div[4]/div[5]/div[3]/div/div/div/div/div/div/div[2]/div[2]/form/div[4]/div/div/div[1]/button"));
			WebElement reviewAcceptTag = driver.findElement(By.tagName("Button"));
			Actions actions = new Actions(driver);
			actions.moveToElement(reviewAccept).click().build().perform();
			actions.moveToElement(reviewAcceptXpath2).click().build().perform();
			actions.moveToElement(reviewAcceptTag).click().build().perform();

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(reviewAccepted));
			executor.executeScript("arguments[0].click();", driver.findElement(reviewAcceptedTag));
			executor.executeScript("arguments[0].click();", driver.findElement(reviewAcceptedXpath2));
			executor.executeScript("arguments[0].click();", driver.findElement(reviewAcceptedXpath));

			driver.findElement(By.cssSelector("Button[id=review-complete-accept]")).click();
			driver.findElement(By.xpath("//*[contains(text(),'I Agree')]")).click();
			driver.findElement(By.xpath("//*[text()='I Agree']")).click();
			// Text - I Agree
			cf.clickElement(driver, reviewAccepted);
			cf.clickElement(driver, reviewAcceptedXpath);
			cf.clickElement(driver, reviewAcceptedXpath2);
			driver.findElement(reviewAccepted).sendKeys(Keys.ENTER);
			driver.findElement(reviewAcceptedXpath).sendKeys(Keys.ENTER);
			driver.findElement(reviewAcceptedXpath2).sendKeys(Keys.ENTER);
			//driver.findElement(By.xpath("//*[@id=\"review-complete-form\"]")).submit();
			//driver.findElement(By.id("review-complete-form")).submit();
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("Accept button already clicked... ");
		}
		Thread.sleep(1000);
		System.out.println("REVIEW COMPLETE... ");
	}
	
	@Step("Reviewer refuses tailoring")
	public void completeReviewReject(WebDriver driver) throws InterruptedException {
		
		By reviewButton= By.xpath("//*[@id=\"review-complete-button\"]");
		By addComment= By.xpath("//*[@id=\"Comments\"]");
		//By reviewAccept= By.xpath("//*[@id=\"review-complete-accept\"]");
		By reviewReject= By.xpath("//*[@id=\"review-complete-reject\"]");
		
		String comment = "Rejected, please amend";
		
		cf.waitForDesiredElement(driver, reviewButton, 50);
		cf.clickElement(driver, reviewButton);	
		
		cf.waitForDesiredElement(driver, reviewReject, 50);
		cf.setTextBox(driver, addComment, comment);
		cf.clickElement(driver, reviewReject);
				
	}

	@Step("Final review after tailoring and main checklist completion")
	public void completeFinalReviewAccept(WebDriver driver) throws InterruptedException {

		By confirmReviewComments= By.xpath("//*[@id=\"review-comments-body\"]/div/div/div");
		By acceptedBy= By.xpath("//*[@id=\"review-comments-body\"]/div/div/div/span[2]/span[2]");
		By reviewButton= By.xpath("//*[@id=\"review-comments-ok\"]");
		By rollForwardSelect= By.xpath("//*[@id=\"g-positioner\"]/ul/li[4]/a");
		By rollForwardButton= By.xpath("//*[@id=\"rollforward-answerset\"]");

        // Asset titles and select 'Review' from answerset screen
		h.assertTitles(driver);
		driver.navigate().refresh();
		Thread.sleep(3000);
		hs.reviewQuestionaire(driver);

		cf.waitForDesiredElement(driver, reviewButton, 60);

		// Display review comments from SDC admin
		String reviewComments= driver.findElement(confirmReviewComments).getText();
		String accepted= driver.findElement(acceptedBy).getText();
		Thread.sleep(3000);
		System.out.println("Review comments left by SDC Reviewer: "+ reviewComments);
		System.out.println("Accepted By: "+ accepted);
		Thread.sleep(3000);
		cf.clickElement(driver, reviewButton);

		// Accept review by clicking on 'Accept' button
		Thread.sleep(3000);
		completeReviewAccept(driver);
		Thread.sleep(3000);
		driver.navigate().refresh();
		h.assertTitles(driver);

		// Roll forward questionaire
		Thread.sleep(3000);
		hs.answerSectionsInProgress(driver);
		Thread.sleep(3000);
		cf.scrollToElement(driver, rollForwardButton);
		cf.clickElement(driver, rollForwardButton);
		Thread.sleep(3000);
		h.assertTitles(driver);
		Thread.sleep(8000);
	}
	
	@Step("Check correct question is displayed")
	public void checkLogic(WebDriver driver) throws InterruptedException {
		
		// ** TO BE DEVELOPED **
	}
	
	@Step("Display total amount of problems created at current stage")
	public void checkProblem(WebDriver driver)  {
		String problemCountValue= driver.findElement(problemCount).getText();
		System.out.println("Total problems created for tailoring "+ problemCountValue);
	}
	
	@Step("Display total amount of alerts created at current stage")
	public void checkAlert(WebDriver driver)  {
		String alertCountValue= driver.findElement(alertCount).getText();
		System.out.println("Total alerts created for tailoring "+ alertCountValue);
	}

	@Step("Respond to question with Yes, No or Acknowledgement")
	public void questionResponse(WebDriver driver, String response) throws Exception {

		// Execute script based on spreadsheet value
		switch (response.trim().toLowerCase()) {

			case "y":
				cf.waitForDesiredElement(driver, answerField, 60);
				Select answerdropdown1 = new Select(driver.findElement(answerField2));
				answerdropdown1.selectByVisibleText("Yes");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
  			    break;
			case "n":
				cf.waitForDesiredElement(driver, answerField, 50);
				Select answerdropdown2 = new Select(driver.findElement(answerField2));
				answerdropdown2.selectByVisibleText("No");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "a":
				cf.waitForDesiredElement(driver, acceptAcknowledgement, 50);
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				checkAlert(driver);
				checkProblem(driver);
				Thread.sleep(1000);
				System.out.println("Information acknowledged for tailoring");
				cf.scrollToElement(driver, acceptAcknowledgement);
				cf.clickElement(driver, acceptAcknowledgement);
				break;
			case "(Info)":
				cf.waitForDesiredElement(driver, acceptAcknowledgement, 50);
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				checkAlert(driver);
				checkProblem(driver);
				Thread.sleep(1000);
				System.out.println("Information acknowledged for main checklist");
				cf.scrollToElement(driver, acceptAcknowledgement);
				cf.clickElement(driver, acceptAcknowledgement);
				break;
			case "none":
				cf.waitForDesiredElement(driver, answerField, 50);
				Select answerdropdown3 = new Select(driver.findElement(answerField2));
				answerdropdown3.selectByVisibleText("None");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "none of the above":
				cf.waitForDesiredElement(driver, answerField, 50);
				Select answerdropdown4 = new Select(driver.findElement(answerField2));
				answerdropdown4.selectByVisibleText("None of the above");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "1":
				cf.waitForDesiredElement(driver, answerField, 50);
				Select answerdropdown5 = new Select(driver.findElement(answerField2));
				answerdropdown5.selectByVisibleText("1");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "2":
				cf.waitForDesiredElement(driver, answerField, 50);
				Select answerdropdown6 = new Select(driver.findElement(answerField2));
				answerdropdown6.selectByVisibleText("2");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "3":
				cf.waitForDesiredElement(driver, answerField, 50);
				Select answerdropdown7 = new Select(driver.findElement(answerField2));
				answerdropdown7.selectByVisibleText("3");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "1b":
				WebElement CheckBox_1 = driver.findElement(answerbox1);
				CheckBox_1.click();
				System.out.println("1 Selected");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "2b":
				WebElement CheckBox_2 = driver.findElement(answerbox2);
				CheckBox_2.click();
				System.out.println("2 Selected");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "3b":
				WebElement CheckBox_3 = driver.findElement(answerbox3);
				CheckBox_3.click();
				System.out.println("3 Selected");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "4b":
				WebElement CheckBox_4 = driver.findElement(answerbox4);
				CheckBox_4.click();
				System.out.println("4 Selected");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "medium":
				cf.waitForDesiredElement(driver, answerField, 50);
				Select answerdropdown8 = new Select(driver.findElement(answerField2));
				answerdropdown8.selectByVisibleText("Medium");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "small":
				cf.waitForDesiredElement(driver, answerField, 50);
				Select answerdropdown9 = new Select(driver.findElement(answerField2));
				answerdropdown9.selectByVisibleText("Small");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "ok":
				cf.waitForDesiredElement(driver, answerField, 50);
				Select answerdropdown10 = new Select(driver.findElement(answerField2));
				answerdropdown10.selectByVisibleText("OK");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "indirect":
				cf.waitForDesiredElement(driver, answerField, 50);
				Select answerdropdown11 = new Select(driver.findElement(answerField2));
				answerdropdown11.selectByVisibleText("Indirect");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "4":
				cf.waitForDesiredElement(driver, answerField, 50);
				Select answerdropdown12 = new Select(driver.findElement(answerField2));
				answerdropdown12.selectByVisibleText("4");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "n/a":
				cf.waitForDesiredElement(driver, answerField, 50);
				Select answerdropdown13 = new Select(driver.findElement(answerField2));
				answerdropdown13.selectByVisibleText("N/A");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "blank":
			case "b":
				System.out.println("Question not being presented hence no response given ...");
				break;
			case "h":
				System.out.println("Header identified, tailoring will continue ...");
				break;
			case accountingStartDate:
				cf.waitForDesiredElement(driver, dateField, 50);
				cf.setTextBox(driver, dateField, accountingStartDate);
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case accountingEndDate:
				cf.waitForDesiredElement(driver, dateField, 50);
				cf.setTextBox(driver, dateField, accountingEndDate);
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				Thread.sleep(1000);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "not a member of a group":
				WebElement notMemberCheckBox = driver.findElement(selectNotMember);
				notMemberCheckBox.click();
				System.out.println("Not a member of a group Selected");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "parent":
				WebElement parentCheckBox = driver.findElement(selectParent);
				parentCheckBox.click();
				System.out.println("Parent Selected");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "subsidiary":
				WebElement subsidiaryCheckBox = driver.findElement(selectSubsidiary);
				subsidiaryCheckBox.click();
				System.out.println("Subsidiary Selected");
				retrieveQuestionAnswered(driver);
				retrieveQuestionCount(driver);
				retrieveFolderName(driver);
				cf.scrollToElement(driver, nextUnanswered);
				cf.clickElement(driver, nextUnanswered);
				break;
			case "end tailoring":
				Thread.sleep(3000);
				System.out.println("AUTOMATION RUN COMPLETE FOR TAILORING PROCESS ...");
				break;
			case "end checklist":
				Thread.sleep(3000);
				completeTailoringMain(driver);
				System.out.println("AUTOMATION RUN COMPLETE FOR MAIN CHECKLIST PROCESS ...");
				break;
			case "end section":
				Thread.sleep(3000);
				completeTailoringMain(driver);
				System.out.println("Section Complete ...");
				break;
			case "begin section":
				Thread.sleep(3000);
				System.out.println("New section has began ...");
				break;
			case "end main checklist":
				Thread.sleep(3000);
				System.out.println("NOW REVIEW THE LIST TO CONFIRM ALL SECTIONS ARE COMPLETE ...");
				break;
			default:
				System.out.println("Invalid selection type mentioned: " + response);
				break;

		}



	}

}
