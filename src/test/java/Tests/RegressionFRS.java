package Tests;

import Pages.*;
import TailoringAndMainCheclists.FRS102_103_105;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegressionFRS extends LandingPage {

    public void testSetup() throws IOException {

        driver = Setup();
    }

    HomePage h = new HomePage(driver);
    AnswersetsPage as = new AnswersetsPage(driver);
    ClientPage c = new ClientPage(driver);
    TailoringQuestionPage tq = new TailoringQuestionPage(driver);
    HomeScreenPage hs = new HomeScreenPage(driver);
    FRS102_103_105 fr = new FRS102_103_105(driver);
    CompareCSVFilesPage csv = new CompareCSVFilesPage(driver);
    ReportingPage r = new ReportingPage(driver);

    //change alwaysRun value for desired test, use only when changing environments
    @Test(alwaysRun = true, priority = 0, description = "Tailoring Questionnaire Prod", groups = {"Smoke Test"})
    @Description(" Tailoring for FRS client in production environment")
    @Severity(SeverityLevel.CRITICAL)
    public void regression_FRS102_103_105() throws Exception {

        testSetup();
        goToHomePage(driver); // Change method name to goToHomePageQA() to access QA environment
        h.assertTitles(driver);  // Confirm that the titles on homepage are correct
        as.createNewAnswerset(driver); //Create an answer set
        fr.FRS102_103_105_Logic1_Tailoring(driver); // Tailors question according to logic
    }

    @Test(alwaysRun = true, priority = 1, description = "Tailoring Review",
            groups = {"Smoke Test"})
    @Description("Review completed tailoring and ACCEPT")
    @Severity(SeverityLevel.CRITICAL)
    public void reviewCompleteTailoringFRS() throws InterruptedException, IOException {

        testSetup();
        goToHomePage(driver); // Change method name to goToHomePageQA() to access QA environment
        h.assertTitles(driver); // Confirm that the titles on homepage are correct
        hs.reviewQuestionaire(driver); //Select review questionnaire from homepage screen
        tq.completeReviewAccept(driver); // Accept review
    }

    @Test(alwaysRun = true, priority = 2, description = "Answering Main Checklist",
            groups = {"Smoke Test"})
    @Description("Answering the FRS main checklist in production environment")
    @Severity(SeverityLevel.CRITICAL)
    public void regression_FRS102_103_105_Mainchecklist() throws Exception {

        testSetup();
        goToHomePage(driver);
        h.assertTitles(driver);  // Confirm that the titles on homepage are correct
        hs.answerSections(driver); // Navigate to answer section page
        fr.FRS102_103_105_Logic1_mainChecklist(driver); // Answer main checklist until completion
    }

    @Test(alwaysRun = true, priority = 3, description = "Second Review and Roll Forward",
            groups = {"Smoke Test"})
    @Description("Review process after completed tailoring and main checklist, with roll forward")
    @Severity(SeverityLevel.CRITICAL)
    public void finalReviewAcceptFRS() throws InterruptedException, IOException {

        testSetup();
        goToHomePage(driver);
        h.assertTitles(driver);  // Confirm that the titles on homepage are correct
        hs.reviewQuestionaire(driver); //Select review questionnaire from homepage screen
        tq.completeReviewAccept(driver); // Accept review
        tq.completeFinalReviewAccept(driver); // Complete final review and roll forward
    }

    @Test(alwaysRun = true, priority = 4, description = "Compare .CSV's",
            groups = {"Smoke Test"})
    @Description("Compare tailored CSV with original CSV")
    @Severity(SeverityLevel.CRITICAL)
    public void compareCSVFiles() throws InterruptedException, IOException {

        csv.compareCSVFiles(driver);
    }

    @Test(alwaysRun = true, priority = 5, description = "Tailoring Report",
            groups = {"Smoke Test"})
    @Description("Generate report post tailoring")
    @Severity(SeverityLevel.CRITICAL)
    public void generateReportFRS() throws InterruptedException, IOException {

        testSetup();
        goToHomePage(driver);
        h.generateReport(driver);
        r.generateReportTailoring(driver);
    }

}
