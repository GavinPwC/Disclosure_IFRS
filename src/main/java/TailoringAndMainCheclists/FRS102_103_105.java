package TailoringAndMainCheclists;

import Pages.*;
import SetUp.CoreFunctions;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FRS102_103_105 {

    public WebDriver driver;
    SoftAssert softAssert = new SoftAssert();


    By questionNumber = By.xpath("//*[@id=\"question\"]/div/div[1]/div/h3/span[1]");
    By questionDisc = By.xpath("//*[@id=\"question\"]/p");
    By questionTitle = By.xpath("//*[@id=\"question\"]/div/div[1]/div/h3/span[2]");
    By Folder = By.xpath("//*[@id=\"section-contents\"]/ul/li[1]/div");
    By sectionName = By.xpath("//*[@id=\"answersets-data\"]/tr[7]/td[4]/span");
    By answersetMenuItem = By.xpath("//*[@id=\"answersets-link\"]/span[2]");

    By answerField = By.xpath("//*[@id=\"select2-tickonceanswer-container\"]");
    By acknowledgement = By.xpath("//*[@id=\"infoanswer\"]");
    By answerField2 = By.xpath("//*[@id=\"tickonceanswer\"]");
    By nextUnanswered = By.xpath("//*[@id=\"question-nav\"]/div[3]/div[1]/a/span[1]");
    By sectionStatus = By.xpath("//*[@id=\"answersets-data\"]/tr[6]/td[3]/span");

    int actStateOneCountconfirm = 84;
    int actStateOneCountconfirmMain = 66;
    int actStateTwoCountconfirm = 84;


    public FRS102_103_105(WebDriver driver) {
        // TODO Auto-generated constructor stub
        this.driver = driver;
    }

    CoreFunctions cf=new CoreFunctions(driver);
    ClientPage c=new ClientPage(driver);
    AnswersetsPage as=new AnswersetsPage(driver);
    TailoringQuestionPage tq = new TailoringQuestionPage(driver);
    HomeScreenPage hs=new HomeScreenPage(driver);
    HomePage h=new HomePage(driver);

    @Step("Retrieve folder name")
    public String getFolderName(WebDriver driver) {

        cf.waitForDesiredElement(driver, Folder, 60);
        return driver.findElement(Folder).getText();
    }

    @Step("Return to the main checklist")
    public String returnToMainChecklist(WebDriver driver) {

        cf.waitForDesiredElement(driver, Folder, 60);
        return driver.findElement(Folder).getText();
    }

    @Step("Navigate to main checklist screen to select new section")
    public void navigateToMainChecklistScreen(WebDriver driver) throws InterruptedException {

        // ** CAN BE DELETED **
        By startButton = By.xpath("//*[@id=\"select-sections\"]");

        System.out.println("New section about to begin...");
        cf.refreshForElement(driver, startButton);
        hs.answerSections(driver);
    }

    @Step("Answer questions from selected section")
    public void mainCheckListResponse(WebDriver driver, String StartLine) throws Exception {

        cf.waitForDesiredElement(driver, Folder, 60);
        Thread.sleep(3000);

        String expFolderName = "General";
        String fileNameMain = "MainChecklist_FRS102.csv";
        String filePathMain = System.getProperty("user.dir") + "\\src\\main\\java\\Data Files\\" + fileNameMain;

        //CSVReader reader = new CSVReader(new FileReader(filePathMain));
        String[] csvCell1;
        String[] csvCell2;
        String[] csvCell3;
        String[] csvCell4;
        String[] csvCell5;
        String[] csvCell6;

        // Execute block of code based on StartLine variable value
        if (StartLine.equalsIgnoreCase("Primary Statements")) {

            try {
                // Create an object of file reader
                // class with CSV file as a parameter.
                FileReader filereader = new FileReader(filePathMain);

                // Put csvReader.readNext() in a loop
                // Change this value to the value of the section in progress
                // create csvReader object and skip no Line
                CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(0).build();
                while ((csvCell1 = csvReader.readNext()) != null) {

                    String Qnumber1 = csvCell1[0];
                    String QTitle1 = csvCell1[1];
                    String QBody1 = csvCell1[2];
                    String QResponse1 = csvCell1[3];

                    // Retrieve below values from DISCLOSE
                    String Title1 = driver.findElement(questionTitle).getText();
                    String questionTextVal1 = driver.findElement(questionNumber).getText();
                    String questionDescription1 = driver.findElement(questionDisc).getText();

                    // Compare DISCLOSE values with spreadsheet values and display result
                    cf.compareValues(driver, "Question Number", Qnumber1, questionTextVal1);
                    cf.compareValues(driver, "Question Title", QTitle1, Title1);
                    cf.compareValues(driver, "Question Body", QBody1, questionDescription1);

                    // Execute blocks of code based on QResponse
                    if ((QResponse1.equalsIgnoreCase("Begin Section"))) {

                        //By sectionSummary = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl");
                        By sectionSummary = By.id("main-detail-panels");

                        //display summary after completing section
                        cf.refreshForElement(driver, sectionSummary);
                        String Summary = driver.findElement(sectionSummary).getText();
                        Thread.sleep(3000);
                        System.out.println(Summary);
                        Thread.sleep(3000);
                        h.assertTitles(driver);

                        // Refresh browser and navigate back to main checklist screen
                        driver.navigate().refresh();
                        Thread.sleep(3000);
                        hs.answerSections(driver);
                        FRS102_103_105_Logic1_mainChecklist(driver);
                    } else if (QResponse1.equalsIgnoreCase("End Main Checklist")){

                        // display final summary upon completion of final section
                        //By sectionSummary = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl");
                        By sectionSummary = By.id("main-detail-panels");
                        Thread.sleep(5000);
                        cf.refreshForElement(driver, sectionSummary);
                        String Summary = driver.findElement(sectionSummary).getText();
                        Thread.sleep(3000);
                        System.out.println(Summary);
                        Thread.sleep(2000);
                        csvReader.close();
                    }
                    tq.questionResponse(driver, QResponse1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (StartLine.equalsIgnoreCase("Financial Statement Presentation")) {

            try {
                // Create an object of file reader
                // class with CSV file as a parameter.
                FileReader filereader = new FileReader(filePathMain);

                // Put csvReader.readNext() in a loop
                // Change this value to the value of the section in progress
                // create csvReader object and skip first 109 Line
                CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(109).build();
                while ((csvCell2 = csvReader.readNext()) != null) {

                    String Qnumber1 = csvCell2[0];
                    String QTitle1 = csvCell2[1];
                    String QBody1 = csvCell2[2];
                    String QResponse1 = csvCell2[3];

                    String Title1 = driver.findElement(questionTitle).getText();
                    String questionTextVal1 = driver.findElement(questionNumber).getText();
                    String questionDescription1 = driver.findElement(questionDisc).getText();

                    cf.compareValues(driver, "Question Number", Qnumber1, questionTextVal1);
                    cf.compareValues(driver, "Question Title", QTitle1, Title1);
                    cf.compareValues(driver, "Question Body", QBody1, questionDescription1);

                    if ((QResponse1.equalsIgnoreCase("Begin Section"))) {

                        //By sectionSummary = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl");
                        By sectionSummary = By.id("main-detail-panels");

                        cf.refreshForElement(driver, sectionSummary);
                        String Summary = driver.findElement(sectionSummary).getText();
                        Thread.sleep(3000);
                        System.out.println(Summary);
                        Thread.sleep(3000);
                        h.assertTitles(driver);
                        driver.navigate().refresh();
                        Thread.sleep(3000);
                        hs.answerSections(driver);
                        //reader.readNext();
                        FRS102_103_105_Logic1_mainChecklist(driver);
                    } else if (QResponse1.equalsIgnoreCase("End Main Checklist")){

                        //tq.checkAlert(driver);
                        //tq.checkProblem(driver);
                        //By sectionSummary = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl");
                        By sectionSummary = By.id("main-detail-panels");
                        Thread.sleep(3000);
                        cf.refreshForElement(driver, sectionSummary);
                        String Summary = driver.findElement(sectionSummary).getText();
                        Thread.sleep(3000);
                        System.out.println(Summary);
                        Thread.sleep(2000);
                        csvReader.close();
                    }
                    tq.questionResponse(driver, QResponse1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (StartLine.equalsIgnoreCase("Financial Instruments")) {

            try {
                // Create an object of file reader
                // class with CSV file as a parameter.
                FileReader filereader = new FileReader(filePathMain);

                // Put csvReader.readNext() in a loop
                // Change this value to the value of the section in progress
                // create csvReader object and skip first 123 Line
                CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(123).build();
                while ((csvCell3 = csvReader.readNext()) != null) {

                    String Qnumber1 = csvCell3[0];
                    String QTitle1 = csvCell3[1];
                    String QBody1 = csvCell3[2];
                    String QResponse1 = csvCell3[3];

                    String Title1 = driver.findElement(questionTitle).getText();
                    String questionTextVal1 = driver.findElement(questionNumber).getText();
                    String questionDescription1 = driver.findElement(questionDisc).getText();

                    cf.compareValues(driver, "Question Number", Qnumber1, questionTextVal1);
                    cf.compareValues(driver, "Question Title", QTitle1, Title1);
                    cf.compareValues(driver, "Question Body", QBody1, questionDescription1);

                    if ((QResponse1.equalsIgnoreCase("Begin Section"))) {

                        //By sectionSummary = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl");
                        By sectionSummary = By.id("main-detail-panels");

                        cf.refreshForElement(driver, sectionSummary);
                        String Summary = driver.findElement(sectionSummary).getText();
                        Thread.sleep(3000);
                        System.out.println(Summary);
                        Thread.sleep(3000);
                        h.assertTitles(driver);
                        driver.navigate().refresh();
                        Thread.sleep(3000);
                        hs.answerSections(driver);
                        //reader.readNext();
                        FRS102_103_105_Logic1_mainChecklist(driver);
                    } else if (QResponse1.equalsIgnoreCase("End Main Checklist")){

                        //tq.checkAlert(driver);
                        //tq.checkProblem(driver);
                        //By sectionSummary = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl");
                        By sectionSummary = By.id("main-detail-panels");
                        Thread.sleep(3000);
                        cf.refreshForElement(driver, sectionSummary);
                        String Summary = driver.findElement(sectionSummary).getText();
                        Thread.sleep(3000);
                        System.out.println(Summary);
                        Thread.sleep(2000);
                        csvReader.close();
                    }
                    tq.questionResponse(driver, QResponse1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (StartLine.equalsIgnoreCase("Accounting Policies")) {

            try {
                // Create an object of file reader
                // class with CSV file as a parameter.
                FileReader filereader = new FileReader(filePathMain);

                // Put csvReader.readNext() in a loop
                // Change this value to the value of the section in progress
                // create csvReader object and skip first 132 Line
                CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(132).build(); // added 6
                while ((csvCell4 = csvReader.readNext()) != null) {

                    String Qnumber1 = csvCell4[0];
                    String QTitle1 = csvCell4[1];
                    String QBody1 = csvCell4[2];
                    String QResponse1 = csvCell4[3];

                    String Title1 = driver.findElement(questionTitle).getText();
                    String questionTextVal1 = driver.findElement(questionNumber).getText();
                    String questionDescription1 = driver.findElement(questionDisc).getText();
                    System.out.println(QResponse1 );

                    cf.compareValues(driver, "Question Number", Qnumber1, questionTextVal1);
                    cf.compareValues(driver, "Question Title", QTitle1, Title1);
                    cf.compareValues(driver, "Question Body", QBody1, questionDescription1);

                    if ((QResponse1.equalsIgnoreCase("Begin Section"))) {

                        //By sectionSummary = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl");
                        By sectionSummary = By.id("main-detail-panels");

                        cf.refreshForElement(driver, sectionSummary);
                        String Summary = driver.findElement(sectionSummary).getText();
                        Thread.sleep(3000);
                        System.out.println(Summary);
                        Thread.sleep(3000);
                        h.assertTitles(driver);
                        driver.navigate().refresh();
                        Thread.sleep(3000);
                        hs.answerSections(driver);
                        //reader.readNext();
                        FRS102_103_105_Logic1_mainChecklist(driver);
                    } else if (QResponse1.equalsIgnoreCase("End Main Checklist")){

                        //tq.checkAlert(driver);
                        //tq.checkProblem(driver);
                        //By sectionSummary = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl");
                        By sectionSummary = By.id("main-detail-panels");
                        Thread.sleep(3000);
                        cf.refreshForElement(driver, sectionSummary);
                        String Summary = driver.findElement(sectionSummary).getText();
                        Thread.sleep(3000);
                        System.out.println(Summary);
                        Thread.sleep(2000);
                        csvReader.close();
                    }
                    tq.questionResponse(driver, QResponse1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (StartLine.equalsIgnoreCase("Notes to the Financial Statements")) {

            try {
                // Create an object of file reader
                // class with CSV file as a parameter.
                FileReader filereader = new FileReader(filePathMain);

                // Put csvReader.readNext() in a loop
                // Change this value to the value of the section in progress
                // create csvReader object and skip first 155 Lines
                CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(155).build();
                while ((csvCell5 = csvReader.readNext()) != null) {

                    String Qnumber1 = csvCell5[0];
                    String QTitle1 = csvCell5[1];
                    String QBody1 = csvCell5[2];
                    String QResponse1 = csvCell5[3];

                    String Title1 = driver.findElement(questionTitle).getText();

                    String questionTextVal1 = driver.findElement(questionNumber).getText();
                    String questionDescription1 = driver.findElement(questionDisc).getText();

                    cf.compareValues(driver, "Question Number", Qnumber1, questionTextVal1);
                    cf.compareValues(driver, "Question Title", QTitle1, Title1);
                    cf.compareValues(driver, "Question Body", QBody1, questionDescription1);

                    if ((QResponse1.equalsIgnoreCase("Begin Section"))) {

                        //By sectionSummary = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl");
                        By sectionSummary = By.id("main-detail-panels");

                        cf.refreshForElement(driver, sectionSummary);
                        String Summary = driver.findElement(sectionSummary).getText();
                        Thread.sleep(3000);
                        System.out.println(Summary);
                        Thread.sleep(2000);
                        h.assertTitles(driver);
                        driver.navigate().refresh();
                        Thread.sleep(3000);
                        hs.answerSections(driver);
                        FRS102_103_105_Logic1_mainChecklist(driver);
                    } else if (QResponse1.equalsIgnoreCase("End Main Checklist")){

                        //tq.checkAlert(driver);
                        //tq.checkProblem(driver);
                        //By sectionSummary = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl");
                        By sectionSummary = By.id("main-detail-panels");
                        Thread.sleep(3000);
                        cf.refreshForElement(driver, sectionSummary);
                        String Summary = driver.findElement(sectionSummary).getText();
                        Thread.sleep(3000);
                        System.out.println(Summary);
                        Thread.sleep(2000);
                        csvReader.close();
                    }
                    tq.questionResponse(driver, QResponse1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ((StartLine.equalsIgnoreCase("In Progress"))) {

            try {
                // Create an object of file reader
                // class with CSV file as a parameter.
                FileReader filereader = new FileReader(filePathMain);

                // Put csvReader.readNext() in a loop
                // Change this value to the value of the section in progress
                CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(123).build();
                while ((csvCell6 = csvReader.readNext()) != null) {

                    String Qnumber1 = csvCell6[0];
                    String QTitle1 = csvCell6[1];
                    String QBody1 = csvCell6[2];
                    String QResponse1 = csvCell6[3];

                    String Title1 = driver.findElement(questionTitle).getText();

                    String questionTextVal1 = driver.findElement(questionNumber).getText();
                    String questionDescription1 = driver.findElement(questionDisc).getText();

                    cf.compareValues(driver, "Question Number", Qnumber1, questionTextVal1);
                    cf.compareValues(driver, "Question Title", QTitle1, Title1);
                    cf.compareValues(driver, "Question Body", QBody1, questionDescription1);

                    if ((QResponse1.equalsIgnoreCase("Begin Section"))) {

                        //By sectionSummary = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl");
                        By sectionSummary = By.id("main-detail-panels");

                        cf.refreshForElement(driver, sectionSummary);
                        String Summary = driver.findElement(sectionSummary).getText();
                        Thread.sleep(3000);
                        System.out.println(Summary);
                        Thread.sleep(2000);
                        h.assertTitles(driver);
                        driver.navigate().refresh();
                        Thread.sleep(3000);
                        hs.answerSections(driver);
                        FRS102_103_105_Logic1_mainChecklist(driver);
                    } else if (QResponse1.equalsIgnoreCase("End Main Checklist")){

                        //tq.checkAlert(driver);
                        //tq.checkProblem(driver);
                        //By sectionSummary = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl");
                        By sectionSummary = By.id("main-detail-panels");
                        Thread.sleep(3000);
                        cf.refreshForElement(driver, sectionSummary);
                        String Summary = driver.findElement(sectionSummary).getText();
                        Thread.sleep(3000);
                        System.out.println(Summary);
                        Thread.sleep(2000);
                        csvReader.close();
                    }
                    tq.questionResponse(driver, QResponse1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else  {

            System.out.println(" ALL SECTIONS COMPLETE ...");
        }
    }

    @Step("FRS102_103_105 tailoring for {include name of client} client")
    public void FRS102_103_105_Logic1_Tailoring(WebDriver driver) throws Exception {

        cf.waitForDesiredElement(driver, Folder, 60);

        String expFolderName = "General";
        String actFolderName = getFolderName(driver);
        String sheetName = "Autosheet";
        String[] csvCell;

        // Initialise .csv file
        int expStateOneCountConfirm = cf.getQuestionCount(driver);
        String fileNameIN      = "DataFRS102.csv";
        String fileNameINWrite = "DiscloseResponses.csv";
        String fileNameINDisc  = "DiscrepanciesFile.csv";

        String filePathIN      = System.getProperty("user.dir") + "\\src\\main\\java\\Data Files\\" + fileNameIN;
        String filePathINDisc  = System.getProperty("user.dir") + "\\src\\main\\java\\Data Files\\" + fileNameINDisc;
        String filePathINWrite = System.getProperty("user.dir") + "\\src\\main\\java\\Data Files\\" + fileNameINWrite;

        CSVReader reader = new CSVReader(new FileReader(filePathIN));

        // Write to discloseResponses file
        //CSVWriter writer = new CSVWriter(new FileWriter(filePathINWrite));
        File file = new File(filePathINWrite);
        FileWriter outputfile = new FileWriter(file);
        CSVWriter writer2 = new CSVWriter(outputfile);
        List<String[]> data = new ArrayList<>();

        // Write to discrepancies file
        //CSVWriter writer = new CSVWriter(new FileWriter(filePathINWrite));
        File file1 = new File(filePathINDisc);
        FileWriter outputfile1 = new FileWriter(file1);
        CSVWriter writer3 = new CSVWriter(outputfile1);
        List<String[]> data1 = new ArrayList<>();

        while ((csvCell = reader.readNext()) != null) {

            //Assign spreadsheet values to variables
            String Qnumber = csvCell[0];
            String QTitle = csvCell[1];
            String QBody = csvCell[2];
            String QResponse = csvCell[3];

            // Retrieve values from DISCLOSE
            String questionTextVal = driver.findElement(questionNumber).getText();
            String questionDiscription = driver.findElement(questionDisc).getText();
            String Title = driver.findElement(questionTitle).getText();

            // Compare values fron DISCLOSE and spreadsheet and display results in terminal
            cf.compareValues(driver, "Question Number", Qnumber, questionTextVal);
            cf.compareValues(driver, "Question Title", QTitle, Title);
            cf.compareValues(driver, "Question Body", QBody, questionDiscription);

            // Execute various blocks of code depending on Title and QResponse values respectively
            if (Title.equalsIgnoreCase("Purpose of the checklist")) {

                cf.compareValues(driver, "Question Count", expStateOneCountConfirm, actStateOneCountconfirm);
                System.out.println("Question count at this stage is accurate, question title is: "+Title);
                if (expFolderName.equalsIgnoreCase(actFolderName)) {
                    System.out.println("Folder Match ...");
                } else {
                    System.out.println("Folder Mismatch ...");
                }
            }else if (Title.equalsIgnoreCase("SORPS")){
                cf.compareValues(driver, "Question Count", expStateOneCountConfirm, actStateTwoCountconfirm);
                System.out.println("Question count at this stage is accurate, question title is: "+Title);
            } else if (Title.equalsIgnoreCase("End of Tailoring") && QResponse.equalsIgnoreCase("A")){

                tq.checkAlert(driver);
                tq.checkProblem(driver);
            } else if (QResponse.equalsIgnoreCase("End Tailoring")){

                //By sectionSummary = By.xpath("//*[@id=\"main-detail-panels\"]/div/div/div[2]/div/div/div[1]/dl");
                By sectionSummary = By.id("main-detail-panels");

                tq.completeTailoring(driver);
                cf.refreshForElement(driver, sectionSummary);
                String Summary = driver.findElement(sectionSummary).getText();
                System.out.println(Summary);
                Thread.sleep(2000);
                reader.close();
                writer2.close();
                cf.selectMenuOption(driver, answersetMenuItem);
            }

            try{
                if ((Qnumber.equalsIgnoreCase(questionTextVal))){

                    Reporter.log("Match Successful: " + questionTextVal);
                    if ((QTitle.equalsIgnoreCase(Title))){

                        Reporter.log("Match Successful: " + Title);
                        if ((QBody.equalsIgnoreCase(questionDiscription))){

                            Reporter.log("Match Successful: " + Title);
                        } else {

                            Reporter.log(" MisMatch Detected. Expected: " + QBody + " Actual: " + questionDiscription);

                            // add data to csv
                            String[] data2 = { "Question Body Mismatch",QBody, questionDiscription};
                            writer3.writeNext(data2);
                            System.out.println(Arrays.toString(data2));
                            data1.add(new String[] { "Question Body Mismatch",QBody, questionDiscription });
                        }
                    } else {

                        Reporter.log(" MisMatch Detected. Expected: " + QTitle + " Actual: " + Title);

                        // add data to csv
                        String[] data2 = { "Question Title Mismatch",QTitle, Title};
                        writer3.writeNext(data2);
                        System.out.println(Arrays.toString(data2));
                        data1.add(new String[] { "Question Title Mismatch",QTitle, Title});
                    }
                } else if ((!QTitle.equalsIgnoreCase(Title))){

                    Reporter.log(" MisMatch Detected. Expected: " + QTitle + " Actual: " + Title);

                    // add data to csv
                    String[] data2 = { "Question Title Mismatch",QTitle, Title};
                    writer3.writeNext(data2);
                    System.out.println(Arrays.toString(data2));
                    data1.add(new String[] { "Question Title Mismatch",QTitle, Title});
                } else if ((!QBody.equalsIgnoreCase(questionDiscription))) {

                    Reporter.log(" MisMatch Detected. Expected: " + QBody + " Actual: " + questionDiscription);

                    // add data to csv
                    String[] data2 = { "Question Body Mismatch",QBody, questionDiscription};
                    writer3.writeNext(data2);
                    System.out.println(Arrays.toString(data2));
                    data1.add(new String[] { "Question Body Mismatch",QBody, questionDiscription });
                } else {

                    Reporter.log(" MisMatch Detected. Expected: " + Qnumber + " Actual: " + questionTextVal);

                    // add data to csv
                    //String[] data2 = { "Question Number Mismatch",Qnumber, questionTextVal};
                    //writer3.writeNext(data2);
                    //System.out.println(Arrays.toString(data2));
                    //data1.add(new String[] { "Question Number Mismatch",Qnumber, questionTextVal});
                }
            } catch (Exception e){

                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // add data to csv
            String[] data3 = { questionTextVal, Title, questionDiscription, QResponse };
            writer2.writeNext(data3);
            System.out.println(Arrays.toString(data3));
            data.add(new String[] { questionTextVal, Title, questionDiscription, QResponse });

            // Respond to DISCLOSE questions with spreadsheet value
            tq.questionResponse(driver, QResponse);
        }
        writer2.writeAll(data);
        writer3.writeAll(data);
    }

    @Step("FRS102_103_105 tailoring for {include name of client} client")
    public void FRS102_103_105_Logic1_mainChecklist(WebDriver driver) throws Exception {

        // TIP: GET CHECKLIST TO ITS ORIGINAL POSITION BY UNANSWERING THE QUESTIONS

        By startButton = By.xpath("//*[@id=\"select-sections\"]");
        By currentInProgress = By.xpath("//*[@id=\"answersets-data\"]/tr[7]/td[3]/span"); // always change xpath

        cf.waitForDesiredElement(driver, startButton, 60);
        String currentStatus = driver.findElement(currentInProgress).getText();

        try {
        WebElement sectionsTable = driver.findElement(By.id("sections-table"));

        List<WebElement> rows = sectionsTable.findElements(By.tagName("tr"));
        List<WebElement> columns = null;
        for (WebElement row : rows) {

            columns = row.findElements(By.tagName("td"));
            for (WebElement column : columns) {

                    String sCellValue = (column.getText());

                    if ((sCellValue.equalsIgnoreCase("Primary Statements"))) {

                        By sectionTickPS = By.xpath("//*[@id=\"Sections_1__Selected\"]");
                        By sectionName = By.xpath("//*[@id=\"answersets-data\"]/tr[2]/td[4]/span");
                        By sectionStatus = By.xpath("//*[@id=\"answersets-data\"]/tr[2]/td[3]/span");
                        String Status = driver.findElement(sectionStatus).getText();
                        String startLine = "Primary Statements";

                        if (Status.equalsIgnoreCase("Not Started")) {

                            cf.clickElement(driver, sectionTickPS);
                            Thread.sleep(1000);

                            String Section = driver.findElement(sectionName).getText();

                            System.out.println("Initial Status: " + Status);
                            System.out.println("Now answering " + Section + " checklist");
                            cf.clickElement(driver, startButton);
                            as.selectReviewerMainchecklist(driver);
                            cf.waitForDesiredElement(driver, Folder, 90);

                            mainCheckListResponse(driver, startLine);
                        } else if ((Status.equalsIgnoreCase("In Progress"))) {

                            String Section = driver.findElement(sectionName).getText();

                            System.out.println("Initial Status: " + Status);
                            System.out.println("Now answering " + Section + " checklist");
                            cf.clickElement(driver, startButton);
                            //as.selectReviewerMainchecklist(driver); ONLY ADD IF THERE IS AN ERROR AT THE REVIEWER STAGE
                            cf.waitForDesiredElement(driver, Folder, 90);
                            mainCheckListResponse(driver, startLine);
                        } else {
                            String Section = driver.findElement(sectionName).getText();
                            System.out.println(Section + " has already been completed");
                        }
                    } else if (sCellValue.equalsIgnoreCase("Financial Statement Presentation")) {

                        By sectionTickPS = By.xpath("//*[@id=\"Sections_2__Selected\"]");
                        By sectionName = By.xpath("//*[@id=\"answersets-data\"]/tr[3]/td[4]/span");
                        By sectionStatus = By.xpath("//*[@id=\"answersets-data\"]/tr[3]/td[3]/span");
                        String Status = driver.findElement(sectionStatus).getText();
                        String startLine = "Financial Statement Presentation";

                        if (Status.equalsIgnoreCase("Not Started")) {

                            cf.clickElement(driver, sectionTickPS);
                            Thread.sleep(1000);

                            String Section = driver.findElement(sectionName).getText();

                            System.out.println("Initial Status: " + Status);
                            System.out.println("Now answering " + Section + " checklist");
                            cf.clickElement(driver, startButton);
                            cf.waitForDesiredElement(driver, Folder, 90);
                            mainCheckListResponse(driver, startLine);
                        } else if ((Status.equalsIgnoreCase("In Progress"))) {

                            String Section = driver.findElement(sectionName).getText();

                            System.out.println("Initial Status: " + Status);
                            System.out.println("Now answering " + Section + " checklist");
                            cf.clickElement(driver, startButton);
                            cf.waitForDesiredElement(driver, Folder, 90);
                            mainCheckListResponse(driver, startLine);
                        } else {

                            String Section = driver.findElement(sectionName).getText();
                            System.out.println(Section + " has already been completed");
                        }
                    } else if (sCellValue.equalsIgnoreCase("Financial Instruments")) {

                        By sectionTickPS = By.xpath("//*[@id=\"Sections_4__Selected\"]");
                        By sectionStatus = By.xpath("//*[@id=\"answersets-data\"]/tr[5]/td[3]/span");
                        By sectionName = By.xpath("//*[@id=\"answersets-data\"]/tr[5]/td[4]/span");
                        String Status = driver.findElement(sectionStatus).getText();
                        String startLine = "Financial Instruments";
                        if (Status.equalsIgnoreCase("Not Started")) {

                            cf.clickElement(driver, sectionTickPS);
                            Thread.sleep(1000);

                            String Section = driver.findElement(sectionName).getText();

                            System.out.println("Initial Status: " + Status);
                            System.out.println("Now answering " + Section + " checklist");
                            cf.clickElement(driver, startButton);
                            cf.waitForDesiredElement(driver, Folder, 90);
                            mainCheckListResponse(driver, startLine);
                            //cf.waitForDesiredElement(driver, Folder, 60);
                        } else if ((Status.equalsIgnoreCase("In Progress"))) {

                            String Section = driver.findElement(sectionName).getText();

                            System.out.println("Initial Status: " + Status);
                            System.out.println("Now answering " + Section + " checklist");
                            cf.clickElement(driver, startButton);
                            cf.waitForDesiredElement(driver, Folder, 90);
                            mainCheckListResponse(driver, startLine);
                        } else {

                            String Section = driver.findElement(sectionName).getText();
                            System.out.println(Section + " has already been completed");
                        }
                    } else if (sCellValue.equalsIgnoreCase("Accounting Policies")) {

                        By sectionTickPS = By.xpath("//*[@id=\"Sections_5__Selected\"]");
                        By sectionStatus = By.xpath("//*[@id=\"answersets-data\"]/tr[6]/td[3]/span");
                        By sectionName = By.xpath("//*[@id=\"answersets-data\"]/tr[6]/td[4]/span");
                        String Status = driver.findElement(sectionStatus).getText();
                        String startLine = "Accounting Policies";
                        if (Status.equalsIgnoreCase("Not Started")) {

                            cf.clickElement(driver, sectionTickPS);
                            Thread.sleep(1000);

                            String Section = driver.findElement(sectionName).getText();

                            System.out.println("Initial Status: " + Status);
                            System.out.println("Now answering " + Section + " checklist");
                            cf.clickElement(driver, startButton);
                            cf.waitForDesiredElement(driver, Folder, 90);
                            mainCheckListResponse(driver, startLine);
                        } else if ((Status.equalsIgnoreCase("In Progress"))) {

                            String Section = driver.findElement(sectionName).getText();

                            System.out.println("Initial Status: " + Status);
                            System.out.println("Now answering " + Section + " checklist");
                            cf.clickElement(driver, startButton);
                            cf.waitForDesiredElement(driver, Folder, 90);
                            mainCheckListResponse(driver, startLine);
                        } else {

                            String Section = driver.findElement(sectionName).getText();
                            System.out.println(Section + " has already been completed");
                        }
                    } else if (sCellValue.equalsIgnoreCase("Notes to the Financial Statements")) {

                        By sectionTickPS = By.xpath("//*[@id=\"Sections_6__Selected\"]");
                        By sectionStatus = By.xpath("//*[@id=\"answersets-data\"]/tr[7]/td[3]/span");
                        By sectionName = By.xpath("//*[@id=\"answersets-data\"]/tr[7]/td[4]/span");
                        String Status = driver.findElement(sectionStatus).getText();
                        String startLine = "Notes to the Financial Statements";
                        if (Status.equalsIgnoreCase("Not Started")) {

                            cf.clickElement(driver, sectionTickPS);
                            Thread.sleep(1000);

                            String Section = driver.findElement(sectionName).getText();

                            System.out.println("Initial Status: " + Status);
                            System.out.println("Now answering " + Section + " checklist");
                            cf.clickElement(driver, startButton);
                            cf.waitForDesiredElement(driver, Folder, 90);
                            mainCheckListResponse(driver, startLine);
                        } else if ((Status.equalsIgnoreCase("In Progress"))) {

                            String Section = driver.findElement(sectionName).getText();

                            System.out.println("Initial Status: " + Status);
                            System.out.println("Now answering " + Section + " checklist");
                            cf.clickElement(driver, startButton);
                            cf.waitForDesiredElement(driver, Folder, 90);
                            mainCheckListResponse(driver, startLine);
                        } else {

                            String Section = driver.findElement(sectionName).getText();
                            System.out.println(Section + " has already been completed");
                        }
                    } else if ((sCellValue.equalsIgnoreCase("In Progress"))) {

                        String Section = driver.findElement(sectionName).getText();
                        String startLine = "In Progress";

                        System.out.println("Initial Status: " + sCellValue);
                        System.out.println("Now answering " + Section + " checklist");
                        System.out.println("Section name might be incorrect, as checklist began in 'IN PROGRESS' status");

                        cf.clickElement(driver, startButton);
                        cf.waitForDesiredElement(driver, Folder, 90);
                        mainCheckListResponse(driver,startLine);
                    }
            }
            }
        } catch (StaleElementReferenceException e) {

            System.out.println("Stale element error, trying ::  " + e.getMessage());
        }

    }

}


