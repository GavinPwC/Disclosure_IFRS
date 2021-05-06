package SetUp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;



@Listeners(ListenerTest.class)
public class SetupFramework implements ITestNGListener {
	//Landing page
	//String URL = "https://disclose.ew2.ideagenpentana.com/Account/Login/pwc"; 
	//public Properties prop;
	
	
	protected WebDriver driver;
	  //Set up a new instance of web driver for each test. Tests should be independent of each other  	
	  public Properties prop;
	  public String ErrSS = System.getProperty("user.dir")+"\\Screenshots\\";
	  public static DateFormat DF = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
	  public static Date D = new Date();
	  public static String time = DF.format(D);
	  
	  @BeforeMethod
	  public WebDriver Setup() throws IOException {
		  
		prop= new Properties();
		FileInputStream fis=new FileInputStream("C:\\Users\\gcaldwell006\\Desktop\\Disclose Automation\\Disclosure_IJ\\data.properties");
        prop.load(fis);

        String URL=prop.getProperty("baseURL"); //Change to QAbaseURL for QA environment
		
		WebDriverManager.chromedriver().setup();
        //Workaround for the pop-up due to the lack of admin rights.
        ChromeOptions options = new ChromeOptions();
	  	options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
	  	options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));
	  	options.setExperimentalOption("useAutomationExtension", false);
	  	options.addArguments("start-maximized");  
	  	//options.addArguments("--disable-gpu");
	    options.addArguments("--no-sandbox");
	    //options.addArguments("--ignore-certificate-errors");
	    //options.addArguments("--disable-web-security");
	    //options.addArguments("--allow-insecure-localhost");
	    //options.addArguments("--allow-running-insecure-content");
	    //options.addArguments("--acceptInsecureCerts=true");
	  	//options.addArguments("version");
	  	//options.addArguments("--headless");
	  	options.addArguments("incognito");
	  	//options.addArguments("--disable-extensions");
	  	
	  	//Setting desired capabilities
	  	DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(capabilities);
		
	  	//Apply the options and capabilities to the chrome driver instance before running.
	  	driver = new ChromeDriver(options);
		  	
	    //Ensure the web browser is full screen.
	    driver.get(URL);
        return driver;
	  }

	  @AfterMethod(alwaysRun = false)
	  public void browserShutDown(ITestResult result){
		
		int num=0;
		String className = Thread.currentThread().getStackTrace()[num].getClassName();
      	String methodName = Thread.currentThread().getStackTrace()[num].getMethodName();
      	String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
      	String filename = className+"_"+methodName;
	      //Take the Screenshot Only, If the Test is failed.
	      // Change the condition , If the screenshot needs to be taken for other status as well
	      if(ITestResult.FAILURE==result.getStatus()){
	          System.out.println("Failed Status Check");
	          File temp= ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	          String dest = ErrSS +filename+ "_"+time+".png";
	          try{
	              FileUtils.copyFile(temp,new File(dest));
	              System.out.println("Screenshot saved at: "+dest);
	          }
	          catch(Exception e){
	              System.out.println(e.getStackTrace());
	              System.out.println("Screenshot NOT saved ...");
	          }
	      }
	      // put allure code in here
	      driver.quit();
	      prop.clear();
	      
	  }



	  //Clean up the driver instance after each test has run
	 // @AfterMethod
	  //public void browserShutDown() {
        //Quit current instance of driver to set up a new one.
		 // driver.quit();
        //driver.quit();
	  //}
	  

}
