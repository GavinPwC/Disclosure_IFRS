package TailoringAndMainCheclists;

import Pages.AnswersetsPage;
import Pages.ClientPage;
import Pages.TailoringQuestionPage;
import SetUp.CoreFunctions;
import io.qameta.allure.Step;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;

public class GAAP2IFRS2015 {

    public WebDriver driver;

    By answerField = By.xpath("//*[@id=\"select2-tickonceanswer-container\"]");
    By questionNumber = By.xpath("//*[@id=\"question\"]/div/div[1]/div/h3/span[1]");
    By questionDisc = By.xpath("//*[@id=\"question\"]/p");
    By questionTitle = By.xpath("{INSERT XPATH}");
    By answerField2 = By.xpath("//*[@id=\"tickonceanswer\"]");
    By nextUnanswered = By.xpath("//*[@id=\"question-nav\"]/div[3]/div[1]/a/span[1]");



    String actFolderName = "Background";
    int actStateOneCountconfirm = 6;


    public GAAP2IFRS2015(WebDriver driver) {
        // TODO Auto-generated constructor stub
        this.driver = driver;
    }

    CoreFunctions cf=new CoreFunctions(driver);
    ClientPage c=new ClientPage(driver);
    AnswersetsPage as=new AnswersetsPage(driver);
    TailoringQuestionPage tq = new TailoringQuestionPage(driver);

    @Step("Retrieve folder name")
    public String getFolderName() {

        By Folder = By.xpath("//*[@id=\"section-contents\"]/ul/li[1]/div");
        String folderVal = driver.findElement(Folder).getText();
        return folderVal;
    }

    @Step("GAAP2IFRS2015 tailoring for {include name of client} client")
    public void GAAP2IFRS2015_Logic1_Tailoring(WebDriver driver) throws Exception {


        String expFolderName = " ";
        String fileNameIN = "Data.xlsx";
        String filePathIN = System.getProperty("user.dir") + "\\src\\main\\java\\Data Files\\" + fileNameIN;
        String sheetName = "Tailoring";

        cf.waitForDesiredElement(driver, answerField, 60);

        //cf.setExcelFile(driver, filePathIN, sheetName);
        //cf.setExcelFile(driver, filePathIN);

        FileInputStream ExcelFile = new FileInputStream(filePathIN);
        XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
        XSSFSheet ExcelWSheet = ExcelWBook.getSheet(sheetName);

        int rowCount1 = ExcelWSheet.getLastRowNum();
        int rowCount2 = 0;
        int newRowCount = rowCount1 - 1;
        int columnCount = 4;
        int expStateOneCountConfirm = cf.getQuestionCount(driver);

        System.out.println("There are " +newRowCount+ " questions to answer");

        for (int i = 0; i<=newRowCount; i++) {

            // Retrieve below values from DISCLOSE
            String questionTextVal= driver.findElement(questionNumber).getText();
            String questionDiscription= driver.findElement(questionDisc).getText();
            String Title= driver.findElement(questionTitle).getText();

            // Retrieve below values from spreadsheet
            String Qnumber = cf.getCellDataString(driver,i,0);
            String QTitle = cf.getCellDataString(driver,i,1);
            String QBody = cf.getCellDataString(driver,i,2);
            String QResponse= cf.getCellDataString(driver,i,3);

            Object[][] questionData = {
                    {questionTextVal, Title},
            };

            for (Object[] aData : questionData) {

                Row row = ExcelWSheet.createRow(++rowCount2);

                for (Object field : aData) {
                    Cell cell = row.createCell(++columnCount);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }
            }

            if (i == 1) {

                tq.retrieveQuestionCount(driver);
                cf.compareValues(driver, "Question Count", expStateOneCountConfirm, actStateOneCountconfirm);
                tq.retrieveFolderName(driver);
                tq.retrieveQuestionAnswered(driver);
                cf.compareValues(driver, "Question Number", Qnumber, questionTextVal);
                cf.compareValues(driver, "Question Body", QBody, questionDiscription);
                cf.compareValues(driver, "Question Title", QTitle, Title);
            } else {
                System.out.println("Encountering validation issues");
            }
            Thread.sleep(1000);
            tq.questionResponse(driver, QResponse);

        }

        //System.out.println("Question count validation has been performed AND is accurate");


    }
}
