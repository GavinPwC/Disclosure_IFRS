package Tests;

import Pages.*;
import TailoringAndMainCheclists.GAAP2IFRS2015;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegressionIFSR extends LandingPage{

    public void testSetup() throws IOException {

        driver = Setup();
    }

    HomePage h=new HomePage(driver);
    AnswersetsPage as=new AnswersetsPage(driver);
    ClientPage c=new ClientPage(driver);
    TailoringQuestionPage tq=new TailoringQuestionPage(driver);
    HomeScreenPage hs=new HomeScreenPage(driver);
    GAAP2IFRS2015 ga=new GAAP2IFRS2015(driver);

    //change alwaysRun value for desired test, use only when changing environments
    @Test(alwaysRun = true, priority = 0, description = "Tailoring first set of questions",groups = {"Smoke Test"})
    @Description(" Tailoring set of questions in production environment")
    @Severity(SeverityLevel.CRITICAL)
    public void regression_GAAP2IFRS2015() throws Exception {

        testSetup();
        //WebDriver driver = Setup();
        goToHomePage(driver); // Change method name to goToHomePageQA() to access QA environment
        h.assertTitles(driver);  // Confirm that the titles on homepage are correct
        as.createNewAnswerset(driver); //Create an answer set
        ga.GAAP2IFRS2015_Logic1_Tailoring(driver);
    }

    @Test(alwaysRun = false)
    @Description(" Tailoring set of questions in QA environment")
    @Severity(SeverityLevel.CRITICAL)
    public void regression_GAAP2IFRS2015_QA() throws Exception {

        testSetup();
        goToHomePageQA(driver);
        h.assertTitles(driver);  // Confirm that the titles on homepage are correct
        as.createNewAnswerset(driver); //Create an answer set
        ga.GAAP2IFRS2015_Logic1_Tailoring(driver); // Tailors question according to logic

    }
}