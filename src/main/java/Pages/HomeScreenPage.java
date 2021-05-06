package Pages;

import SetUp.CoreFunctions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class HomeScreenPage {

    public WebDriver driver;

//    By tableRecordName = By.xpath("//*[@id=\"answersets-data\"]/tr/td[1]");

    By tableRecordName = By.xpath("//tbody/tr[1]/td[1]");
//    //tbody/tr[1]/td[1]
//    /html[1]/body[1]/div[5]/div[5]/div[6]/div[1]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[1]/td[1]

    By tableRecordStatus = By.xpath("//*[@id=\"answersets-data\"]/tr/td[6]/span");
    By selectButton = By.xpath("//*[@id=\"answersets-data\"]/tr/td[8]/div/a");
    By selectButtonValueDelete = By.xpath("//*[@id=\"g-positioner\"]/ul/li[4]/a");
    By selectButtonValueResumeReview = By.xpath("//*[@id=\"g-positioner\"]/ul/li[3]/a/span");

    By selectButtonValueAnswerSections = By.xpath("//*[@id=\"g-positioner\"]/ul/li[3]/a");
    By selectButtonValueAnswerSections2 = By.xpath("//*[@id=\"g-positioner\"]/ul/li[4]/a");
    By acceptSelectButton= By.xpath("//*[@id=\"confirm-yes\"]");
    By clientField = By.xpath("//*[@id=\"filters\"]/div/div/div/div/div[3]/div/span/span[1]/span");
    By clientField2 = By.xpath("//*[@id=\"filters\"]/div/div/div/div/div[3]/div/select");

    //By tableRecordStatusInProgress = By.xpath("//*[@id=\"status-list-80372\"]/span");
    //By clientField = By.xpath("//*[@id=\"filters\"]/div/div/div/div/div[3]/div/span/span[1]/span/span[2]/span");
    //By selectButtonValueResume = By.xpath("//*[text()[contains(.,'Resume')]]");
    //By selectButtonValueReview = By.xpath("//*[text()[contains(.,'Review')]]");
    //By selectButtonValueReview = By.xpath("//*[@id=\"g-positioner\"]/ul/li[3]/a");
    //*[@id="g-positioner"]/ul/li[3]/a/span
    //*[@id="g-positioner"]/ul/li[3]/a
    //*[@id="g-positioner"]/ul/li[3]/a
    // /html/body/div[7]/ul/li[3]/a
    //*[@id="filters"]/div/div/div/div/div[3]/div/span/span[1]/span
    //*[@id="g-positioner"]/ul/li[3]/a

    public String recordName = "Gavin Caldwell";
    public String clientName = "GavFRSRegression3";

    public HomeScreenPage(WebDriver driver) {
        // TODO Auto-generated constructor stub
        this.driver = driver;
    }

    CoreFunctions cf=new CoreFunctions(driver);

    @Step("Search for client record")
    public void searchRecord(WebDriver driver) throws InterruptedException {

        // Refresh page incase some values are not displayed
        driver.navigate().refresh();

        // Search for name stored in clientName variable
        cf.waitForDesiredElement(driver, clientField, 20);
        System.out.println("Searching table for "+clientName+" client");

        //Refresh page if clientField element NOT displayed
        cf.refreshForElement(driver, clientField);
        cf.clickElement(driver, clientField);
        cf.waitForDesiredElement(driver, clientField2, 20);

        // Search for the client
        Select Clientdropdown = new Select(driver.findElement(clientField2));
        Clientdropdown.selectByVisibleText(clientName);

        // Assert client found
        System.out.println("Client found... ");
    }

    @Step("Select desired client from the home screen")
    public void selectClientHomeScreen(WebDriver driver) throws InterruptedException {

        cf.waitForDesiredElement(driver, clientField, 50);
        Thread.sleep(3000);
        cf.clickElement(driver, clientField);
        Thread.sleep(3000);
        cf.waitForDesiredElement(driver, clientField2, 50);

        // Loop to pick client from list of clients, Xpath created on the fly
        for(Integer i=1; i<=5;i++) {

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

    @Step("Check tailoring or main checklist status")
    public void statusCheck(WebDriver driver) throws InterruptedException {

        searchRecord(driver);

        cf.waitForDesiredElement(driver, tableRecordName, 10);
        String sCellValue = driver.findElement(tableRecordName).getText();
        String sCellValueStatus = driver.findElement(tableRecordStatus).getText();

        // display status of client found
        if (sCellValue.equalsIgnoreCase(clientName)) {

            System.out.println("Tailoring is in "+sCellValueStatus+" status.");
        }
    }
    
    @Step("Delete questionaire in tailoring stage")
    public void deleteQuestionaire(WebDriver driver) throws InterruptedException {

        searchRecord(driver);
        //cf.waitForDesiredElement(driver, worksetList, 10);

        // Find desired record
        cf.waitForDesiredElement(driver, tableRecordName, 10);
        String sCellValue = driver.findElement(tableRecordName).getText();
        String sCellValueStatus = driver.findElement(tableRecordStatus).getText();

        // Display status
        System.out.println("Tailoring is in "+sCellValueStatus+" status.") ;

        if (sCellValue.equalsIgnoreCase(clientName))
        {
            // Hover mouse on found record to make select butotn visible
            cf.mouseover(driver, tableRecordName);
            driver.findElement(selectButton).click();
        }

        // Select DELETE from list
        cf.clickElement(driver, selectButtonValueDelete);

        // confirm you want to delete record on pop up displayed
        cf.clickElement(driver, acceptSelectButton);
    }
    
    @Step("Resume answering questionaire")
    public void resumeCurrentQuestionaire(WebDriver driver) throws InterruptedException {

        searchRecord(driver);
        selectClientHomeScreen(driver);

        // Find desired record
        cf.waitForDesiredElement(driver, tableRecordName, 10);
        String sCellValue = driver.findElement(tableRecordName).getText();
        String sCellValueStatus = driver.findElement(tableRecordStatus).getText();

        // Display status of record found
        System.out.println("Tailoring is in "+sCellValueStatus+" status.") ;

        if (sCellValue.equalsIgnoreCase(clientName))
        {
            // Hover mouse on found record to make select butotn visible
            cf.waitForDesiredElement(driver, tableRecordName, 10);
            cf.mouseover(driver, tableRecordName);
            driver.findElement(selectButton).click();
        }

        // Select RESUME from list, this is the same as the REVIEW button
        cf.clickElement(driver, selectButtonValueResumeReview);
    }

    @Step("Review tailored OR main checklist questionnaire")
    public void reviewQuestionaire(WebDriver driver) throws InterruptedException {

        searchRecord(driver);

        // Find desired record
        cf.waitForDesiredElement(driver, tableRecordName, 10);
        String sCellValue = driver.findElement(tableRecordName).getText();
        String sCellValueStatus = driver.findElement(tableRecordStatus).getText();
        System.out.println("Tailoring is in "+sCellValueStatus+" status.") ;

        if (sCellValue.equalsIgnoreCase(clientName))
        {
            // Hover mouse on found record to make select butotn visible
            cf.waitForDesiredElement(driver, tableRecordName, 10);
            cf.mouseover(driver, tableRecordName);
            driver.findElement(selectButton).click();
        }

        // Click on resume button, which is same as Review button from other stage
        cf.clickElement(driver, selectButtonValueResumeReview);
    }

    @Step("Navigate to sections list for main checklist")
    public void answerSections(WebDriver driver) throws InterruptedException {

        searchRecord(driver);

        cf.waitForDesiredElement(driver, tableRecordName, 10);
        String sCellValue = driver.findElement(tableRecordName).getText();

        // ADD THIS SECTION FOR END 2 END RUN, UNABLE TO LOCATE ELEMENT IN 'IN PROGRESS' STATUS
        //cf.waitForDesiredElement(driver, tableRecordStatus, 10);
        //String sCellValueStatus = driver.findElement(tableRecordStatus).getText();
        //System.out.println("Tailoring is in "+sCellValueStatus+" status.") ;

        if (sCellValue.equalsIgnoreCase(clientName))
        {
            cf.waitForDesiredElement(driver, tableRecordName, 10);
            cf.mouseover(driver, tableRecordName);
            cf.waitForDesiredElement(driver, selectButton, 10);
            driver.findElement(selectButton).click();
        }

        // Select ANSWER SECTIONS from list
        cf.waitForDesiredElement(driver, selectButtonValueAnswerSections, 20);
        cf.clickElement(driver, selectButtonValueAnswerSections);
        Thread.sleep(2000);
    }

    // Execute this method if an answerset section is in progress, change in main script
    @Step("Navigate to sections list for main checklist if a checklist is in progress")
    public void answerSectionsInProgress(WebDriver driver) throws InterruptedException {

        searchRecord(driver);

        cf.waitForDesiredElement(driver, tableRecordName, 10);
        String sCellValue = driver.findElement(tableRecordName).getText();

        // ADD THIS SECTION FOR END 2 END RUN, UNABLE TO LOCATE ELEMENT IN 'IN PROGRESS' STATUS
        //cf.waitForDesiredElement(driver, tableRecordStatus, 10);
        //String sCellValueStatus = driver.findElement(tableRecordStatus).getText();
        //System.out.println("Tailoring is in "+sCellValueStatus+" status.") ;

        if (sCellValue.equalsIgnoreCase(clientName))
        {
            cf.waitForDesiredElement(driver, tableRecordName, 10);
            cf.mouseover(driver, tableRecordName);
            cf.waitForDesiredElement(driver, selectButton, 10);
            driver.findElement(selectButton).click();
        }

        // Select 4th option on list
        cf.waitForDesiredElement(driver, selectButtonValueAnswerSections2, 20);
        cf.clickElement(driver, selectButtonValueAnswerSections2);
        Thread.sleep(2000);
    }
}
