package Tests;

import Pages.*;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import java.io.IOException;


public class Demo extends LandingPage {
	

	public void testSetup() throws IOException {
		
		driver = Setup();		
	}
	
	HomePage h=new HomePage(driver);
	AnswersetsPage as=new AnswersetsPage(driver);
	ClientPage c=new ClientPage(driver);
	TailoringQuestionPage tq=new TailoringQuestionPage(driver);
	HomeScreenPage hs=new HomeScreenPage(driver);

	//change alwaysRun value for desired test, use only when changing environments
	@Test(alwaysRun = true, priority = 0, description = "Tailoring first set of questions",groups = {"Smoke Test"})
	@Description(" Tailoring first set of questions in production environment")
	@Severity(SeverityLevel.CRITICAL)
	public void homeScreen() throws InterruptedException {
		
		goToHomePage(driver); // Change method name to goToHomePageQA() to access QA environment
		h.assertTitles(driver);  // Confirm that the titles on homepage are correct
		as.createNewAnswerset(driver); //Create an answer set
		tq.tailorQuestionnaire(driver, 3); //  	                	
	  }
	
	@Test(alwaysRun = false)
	@Description(" Tailoring first set of questions in QA environment")
	@Severity(SeverityLevel.CRITICAL)
	public void homeScreenQA() throws InterruptedException {
		goToHomePageQA(driver);
		h.assertTitles(driver);  // Confirm that the titles on homepage are correct
		as.createNewAnswerset(driver); //Create an answer set
		tq.tailorQuestionnaire(driver, 3);
	}

	@Test(alwaysRun = true, priority = 1, description = "Continue from where you stopped during tailoring", 
		  groups = {"Smoke Test"})
	@Description("Continue from where you stopped during tailoring")
	@Severity(SeverityLevel.CRITICAL)
	public void resumeTailoring() throws InterruptedException {

		goToHomePage(driver); // Change method name to goToHomePageQA() to access QA environment
		h.assertTitles(driver);  // Confirm that the titles on homepage are correct
		hs.resumeCurrentQuestionaire(driver); //Resume current questionnaire
		tq.questionnaireResponses(driver, 10, "No");  //Respond 'No' to the next 10 questions

	}

	@Test(alwaysRun = true, priority = 2, description = "Delete record",groups = {"Smoke Test"})
	@Description("Delete record")
	@Severity(SeverityLevel.CRITICAL)
	public void confirmAndDelete() throws InterruptedException {

		goToHomePage(driver); // Change method name to goToHomePageQA() to access QA environment
		h.assertTitles(driver);  // Confirm that the titles on homepage are correct
        hs.deleteQuestionaire(driver); // Delete questionnaire from answerset screen
	}

	
}
