package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.qameta.allure.Step;
import SetUp.CoreFunctions;
import SetUp.SetupFramework;

public class AnswersetsPage extends SetupFramework {
	
	public WebDriver driver;
	WebElement element;
	By addButton = By.xpath("//*[@id=\"add-answerset-button\"]");
	
	By checklistField = By.xpath("//*[@id=\"select2-ChecklistVersionID-container\"]");
	By checklistField2 = By.xpath("//*[@id=\"ChecklistVersionID\"]");
	By referenceField = By.xpath("//*[@id=\"Reference\"]");

	By reviewerField = By.xpath("//*[@id=\"select2-SignOffs_0__ReviewerID-container\"]");
	By reviewerField2 = By.xpath("//*[@id=\"SignOffs_0__ReviewerID\"]");

	By firstReviewerField = By.xpath("//*[@id=\"select2-SignOffs_0__ReviewerID-container\"]");
	By firstReviewerField2 = By.xpath("//*[@id=\"SignOffs_0__ReviewerID\"]");
	By secondReviewerField = By.xpath("//*[@id=\"select2-SignOffs_1__ReviewerID-container\"]");
	By secondReviewerField2 = By.xpath("//*[@id=\"SignOffs_1__ReviewerID\"]");

	By startButton = By.xpath("//*[@id=\"add-new-answerset\"]");
	By saveReviewerButton = By.xpath("//*[@id=\"SaveReviewers\"]");

	By tick = By.xpath("//*[@id=\"Sections_0__Selected\"]");
	By statusCheck = By.xpath("//*[@id=\"answersets-data\"]/tr[1]/td[3]/span");
	By sectionCheck = By.xpath("//*[@id=\"answersets-data\"]/tr[1]/td[4]/span");
	By start = By.xpath("//*[@id=\"select-sections\"]");
	
	public String checklistNameIFRS    = "UK PWC IFRS UK and FRS 101 (1.2.2.0)";
	public String checklistNameFRS     = "UK PWC FRS 102, 103 and 105 (3.0.8.0)";
	public String checklistNameReduced = "IFRS PWC reduced tailoring (5.3.8.0)";
	public String reviewer = "Gavin Caldwell";

	//*[@id="SignOffs_1__ReviewerID"]
	//*[@id="select-reviewers-form"]/div[2]/div/div/div/span
	//*[@id="select-reviewers-form"]/div[2]/div/div/div/span/span[1]/span
	//*[@id="SignOffs_0__ReviewerID"]
	//*[@id="select-reviewers-form"]/div[3]/div/div/div/span/span[1]/span
	//*[@id="select2-SignOffs_0__ReviewerID-container"]
	//*[@id="select2-SignOffs_0__ReviewerID-container"]
	By checklistVal1 = By.xpath("//*[@id=\"select2-ChecklistVersionID-result-fbzx-202\"]");
	By checklistVal2 = By.xpath("//*[@id=\"select2-ChecklistVersionID-result-kszs-208\"]");
	
	public AnswersetsPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
	
	CoreFunctions cf=new CoreFunctions(driver);
	ClientPage c=new ClientPage(driver);
	
	@Step("Begin a new answer set")
	public void createNewAnswerset(WebDriver driver) throws InterruptedException {

		// Click on the (+) button on the home screen
		cf.waitForDesiredElement(driver, addButton, 30);
		cf.clickElement(driver, addButton);

		// Replace with selectClientAS method once client has been created
		//c.createClientAS(driver);
		c.selectClientAS(driver); // add to my notes

		cf.waitForDesiredElement(driver, addButton, 30);
		cf.scrollToElement(driver, checklistField);
		checklistSelectValForFRS(driver); // Remove or Add the "I" at the end to switch between FRS & IFRS checklists

		//Scroll down so start button and reference field are visible
		cf.scrollToElement(driver, startButton);
		AddReference(driver);
		cf.clickElement(driver, startButton);

		// Select reviewer on the next page, this uses the Reviewer variable
		selectReviewer(driver);
	}

	@Step("Begin a new answer set in the QA environment")
	public void createNewAnswersetQA(WebDriver driver) throws InterruptedException {

		// Click on the (+) button on the home screen
		cf.waitForDesiredElement(driver, addButton, 30);
		cf.clickElement(driver, addButton);

		// Replace with selectClientAS method once client has been created
		//c.createClientAS(driver);
		c.selectClientAS(driver);
		cf.waitForDesiredElement(driver, addButton, 30);
		cf.scrollToElement(driver, checklistField);

		// Select desired checklist, irrelevant checklists commented out
		checklistSelectValForFRS(driver); // Remove or Add the "I" at the end to switch between FRS & IFRS checklists
		//checklistSelectValForReduced(driver);

		//Scroll down so start button and reference field are visible
		cf.scrollToElement(driver, startButton);
		AddReference(driver);
		cf.clickElement(driver, startButton);
	}
	
	@Step("Select desired checklist IFRS client")
	public void checklistSelectValForIFRS(WebDriver driver) {
					
		cf.waitForDesiredElement(driver, checklistField, 30);
    	Select Checklistdropdown = new Select(driver.findElement(checklistField2));
    	Checklistdropdown.selectByVisibleText(checklistNameIFRS);
	}


	@Step("Select desired checklist FRS client")
	public void checklistSelectValForFRS(WebDriver driver) {

		cf.waitForDesiredElement(driver, checklistField, 30);
		Select Checklistdropdown = new Select(driver.findElement(checklistField2));
		Checklistdropdown.selectByVisibleText(checklistNameFRS);
	}

	@Step("Select desired checklist Reduced Tailoring client")
	public void checklistSelectValForReduced(WebDriver driver) {

		cf.waitForDesiredElement(driver, checklistField, 30);
		Select Checklistdropdown = new Select(driver.findElement(checklistField2));
		Checklistdropdown.selectByVisibleText(checklistNameFRS);
	}
	
	@Step("Add reference")
    public void AddReference(WebDriver driver) {
		
		int randonInteger = cf.getRandomNumberInRange(1, 999999999);
		String refValue = "PwCAuto"+randonInteger+"Test";
		cf.setTextBox(driver, referenceField, refValue);
	}
    
	@Step("Select reviewer for the tailoring")
    public void selectReviewer(WebDriver driver) {
    	
    	cf.waitForDesiredElement(driver, reviewerField, 50);
    	Select Reviewerdropdown = new Select(driver.findElement(reviewerField2));
    	Reviewerdropdown.selectByVisibleText(reviewer);
    	cf.clickElement(driver, saveReviewerButton);    	
    }

	@Step("Select both reviewers for the main checklist")
	public void selectReviewerMainchecklist(WebDriver driver) {

		// Select first reviewer
		cf.waitForDesiredElement(driver, firstReviewerField, 50);
		Select Reviewerdropdown1 = new Select(driver.findElement(firstReviewerField2));
		Reviewerdropdown1.selectByVisibleText(reviewer);

		// Select second reviewer
		cf.waitForDesiredElement(driver, secondReviewerField, 50);
		Select Reviewerdropdown2 = new Select(driver.findElement(secondReviewerField2));
		Reviewerdropdown2.selectByVisibleText(reviewer);

		cf.clickElement(driver, saveReviewerButton);
	}

    // This function has not been used for the regression script but might be useful for demo
	@Step("Start new section for main checklist")
	public void selectSection(WebDriver driver, String sectionName, int sectionNumber) {

		cf.waitForDesiredElement(driver, start, 50);

		String statusCheckVal = driver.findElement(statusCheck).getText();
		System.out.println("Status: " +statusCheckVal);

		String sectionCheckVal = driver.findElement(sectionCheck).getText();
		System.out.println("Section: " +sectionCheckVal) ;

		cf.clickElement(driver, start);
	}

	// This function is rellevant for creating new Xpaths
	@Step("Create a new xpath to select the next section")
	public void sectionSelectXpath(WebDriver driver, int selection) {

		String formedXpath = "//*[@id=\"Sections_"+selection+"__Selected\"]";
		By newXpath = By.xpath(formedXpath);
		cf.selectCheckBox(driver, newXpath);
	}
}
