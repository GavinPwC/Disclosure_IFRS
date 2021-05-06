package Pages;

import SetUp.CoreFunctions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ReportingPage {

    public WebDriver driver;

    By reportField = By.xpath("//*[@id=\"select-report-form\"]/div[1]/div/span/span[1]/span");
    By reportField2 = By.xpath("//*[@id=\"select2-lra7-results\"]");
    By clientField = By.xpath("//*[@id=\"select-report-form\"]/div[2]/div/div/div/span");
    By clientField2 = By.xpath("//*[@id=\"select2-select-client-results\"]");
    By viewReportButton = By.xpath("//*[@id=\"select-report-form\"]/div[4]/div/div/div/a");

    public String recordName = "John Edgal";
    public String clientName = "autoClient2";

    public ReportingPage(WebDriver driver) {
        // TODO Auto-generated constructor stub
        this.driver = driver;
    }

    CoreFunctions cf=new CoreFunctions(driver);

    @Step("Generate report for completed tailoring")
    public void generateReportTailoring(WebDriver driver) throws InterruptedException {

        // populate report field
        cf.waitForDesiredElement(driver, reportField, 60);
        Select answerdropdown1 = new Select(driver.findElement(reportField2));
        answerdropdown1.selectByVisibleText("Tailoring Assumptions Report");
        Thread.sleep(2000);

        // Search for name stored in clientName variable
        cf.waitForDesiredElement(driver, clientField, 20);
        System.out.println("Searching table for "+clientName+" client");

        //Refresh page if clientField element NOT displayed
        cf.clickElement(driver, clientField);
        cf.waitForDesiredElement(driver, clientField2, 20);

        // Search for the client
        Select Clientdropdown = new Select(driver.findElement(clientField2));
        Clientdropdown.selectByVisibleText(clientName);
        Thread.sleep(2000);

        // Assert client found
        System.out.println("Client found... ");

        //Click view report button
        cf.clickElement(driver, viewReportButton);

        // wait for next element
        cf.waitForDesiredElement(driver, clientField2, 20);

        By infoTab = By.xpath("//*[@id=\"report\"]/div[1]/div/nav/ul/li[1]/a");
        By infoDetails = By.xpath("//*[@id=\"report\"]/div[2]/div[1]");
        By completionTab = By.xpath("//*[@id=\"report\"]/div[1]/div/nav/ul/li[2]/a");
        By completionDetails = By.xpath("//*[@id=\"report\"]/div[2]/div[2]");
        By reviewsTab = By.xpath("//*[@id=\"report\"]/div[1]/div/nav/ul/li[3]");
        By reviewDetails = By.xpath("//*[@id=\"report\"]/div[2]/div[3]");
        By assumptionsTab = By.xpath("//*[@id=\"report\"]/div[1]/div/nav/ul/li[4]");
        By assumptionDetails = By.xpath("//*[@id=\"report\"]/div[2]/div[4]");
        By reportExportButton = By.xpath("//*[@id=\"export-report-button\"]/span");
        By formatSelect = By.xpath("//*[@id=\"ReportFormat\"]");
        By formatReport = By.xpath("//*[@id=\"export-report\"]");





        // Display report details on all 4 tabs
        Thread.sleep(5000);
        cf.refreshForElement(driver, infoTab);
        String info = driver.findElement(infoDetails).getText();
        System.out.println("INFO:");
        System.out.println(info);

        Thread.sleep(2000);
        cf.clickElement(driver, completionTab);
        String completion = driver.findElement(completionDetails).getText();
        System.out.println("COMPLETION:");
        System.out.println(completion);

        Thread.sleep(2000);
        cf.clickElement(driver,  reviewsTab);
        String review= driver.findElement(reviewDetails).getText();
        System.out.println("REVIEW:");
        System.out.println(review);

        Thread.sleep(2000);
        cf.clickElement(driver,  assumptionsTab);
        String assumption = driver.findElement(assumptionDetails).getText();
        System.out.println("ASSUMPTION:");
        System.out.println(assumption);

        // Export report
        Thread.sleep(2000);
        cf.clickElement(driver, reportExportButton);

        // Select report format
        Select Formatdropdown = new Select(driver.findElement(formatSelect));
        Formatdropdown.selectByVisibleText("Csv Text File (*.csv)");
        Thread.sleep(2000);
        cf.clickElement(driver, formatReport);
    }
}
