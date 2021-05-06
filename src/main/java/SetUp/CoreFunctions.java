package SetUp;

import com.opencsv.CSVWriter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sonatype.plexus.components.cipher.Base64;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class CoreFunctions extends SetupFramework {
	
	public WebDriver driver;
	WebElement element;
	
	public XSSFSheet ExcelWSheet;
	public XSSFWorkbook ExcelWBook;
	public XSSFCell Cell;
	public XSSFRow Row;
	
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	//Declaring constructor
	public CoreFunctions(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
		
	}
	
	/*        Created by: John Edgal
	 *        Description: Upon navigating to new page selenium, will wait for the element before clicking
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
	
	public WebDriverWait waitForElementToBeClickable(WebDriver driver, By strelement, int time) {
	 	  WebDriverWait wait = new WebDriverWait(driver, time);
	 	  wait.until(
	 	          ExpectedConditions.elementToBeClickable(strelement));	 
	 	  return wait;
	}	
	                
    /*        Created by: John Edgal
	 *        Description: Upon navigating to new page selenium, will wait for the element to be visible
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
	public WebDriverWait waitForDesiredElement(WebDriver driver, By strelement, int time) {
	 	  WebDriverWait wait = new WebDriverWait(driver, time);
	 	  wait.until(
	 			 ExpectedConditions.visibilityOfElementLocated(strelement)); 
	 	  return wait;
	}

	/*        Created by: John Edgal
	 *        Description: Upon navigating to new page selenium, will wait for the element to be visible
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified:
	 */
	public WebDriverWait waitForDesiredElement(WebDriver driver, WebElement element, int time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		By strelement = (By) element;
		//strelement = (By) element;
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(strelement));
		return wait;
	}
	
	
	/*        Created by: John Edgal
	 *        Description: Upon navigating to new page selenium, will wait for the element to be present
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
	public WebDriverWait waitForElementPresent(WebDriver driver, By strelement, int time) {
	 	  WebDriverWait wait = new WebDriverWait(driver, time);
	 	  wait.until(
	 			 ExpectedConditions.presenceOfElementLocated(strelement)); 
	 	  return wait;
	}
	
	
	/*        Created by: John Edgal
	 *        Description: Upon navigating to new page selenium, will wait for invincibility of element
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
	public WebDriverWait waitForInvincibilityElement(WebDriver driver, By strelement, int time) {
	 	  WebDriverWait wait = new WebDriverWait(driver, time);
	 	  wait.until(
	 			 ExpectedConditions.invisibilityOfElementLocated(strelement)); 
	 	  return wait;
	}
	/*        Created by: John Edgal
	 *        Description: Upon navigating to new page selenium, will wait for invincibility of element
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified:
	 */
	public void refreshForElement(WebDriver driver, By strelement) throws InterruptedException {

		boolean displayed = false;
		//waitForDesiredElement(driver, strelement, 60);
		do {
			try {
				Thread.sleep(25000);
				displayed = driver.findElement(strelement).isDisplayed();
			} catch (NoSuchElementException | InterruptedException e) {
				Thread.sleep(20000);
				System.out.println(e);
				driver.navigate().refresh();
				Thread.sleep(15000);
			}
		} while (!displayed);
	}

	/*        Created by: John Edgal
	 *        Description: Select an option from home screen
	 *        Date: 16/11/2020
	 *        Output: None
	 *        Last Modified:
	 */

	public void selectMenuOption(WebDriver driver, By strelement) throws InterruptedException {

		By menuIcon = By.xpath("//*[@id=\"navMenuLink\"]/div/span[1]");

		waitForDesiredElement(driver, menuIcon, 60);
		clickElement(driver, menuIcon);
		Thread.sleep(3000);
		clickElement(driver, strelement);
	}

	/*        Created by: John Edgal
	 *        Description: This will get the element list matching the locator
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
    public java.util.List<WebElement> getElements_List(WebDriver driver, By strelement){
    	List<WebElement> list = driver.findElements(strelement);
    	return list;
    	
    }
    
    /*        Created by: John Edgal
	 *        Description: This will check the element based on the locator
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
    public Boolean checkElement(WebDriver driver, By strelement) {
    	Boolean blnExist = false;
    	List<WebElement> list = driver.findElements(strelement);
    	if (list.size() == 1) {
    		blnExist = true;	
    	} else {
    		captureScreenshot(driver);
    	}
    	return blnExist;
    }
    
    /*        Created by: John Edgal
	 *        Description: This will get the element based on the locator
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
    public WebElement getElement(WebDriver driver, By strelement) {
    	WebElement element = null;
    	
    	if (checkElement(driver, strelement)) {
    		element = driver.findElement(strelement);
    	} else {
    		Assert.fail("Unable to find element with "+ strelement);
    	}
    	return element;
    }
	
    /*        Created by: John Edgal
	 *        Description: This will set a value into a text box for string
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
    public void setTextBox(WebDriver driver, By strelement, String login) {
    	WebElement element = getElement(driver, strelement);
    	element.click();
    	element.clear();
    	element.click();
    	element.sendKeys(login);
    }
    
    /*        Created by: John Edgal
	 *        Description: This will set a value into a text box for integer
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
    public void setTextBox(WebDriver driver, By strelement, Integer intValue) {
    	WebElement element = getElement(driver, strelement);
    	element.click();
    	element.clear();
    	element.click();
    	element.sendKeys(intValue.toString());
    }
    
    /*        Created by: John Edgal
	 *        Description: Click on an element i.e. Link, button Image e.t.c
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
    public void clickElement(WebDriver driver, By strelement) {
    	
    	WebElement element = getElement(driver, strelement);

    	if (element.isDisplayed() && element.isEnabled()) {
    		element.click();
    	} else {
    		captureScreenshot(driver);
    		Assert.fail("Element not displayed or not enabled" + strelement);
    	}
    }

	/*        Created by: John Edgal
	 *        Description: Click on an element i.e. Link, button Image e.t.c
	 *        Date: 24/11/2020
	 *        Output: None
	 *        Version: 0.2
	 *        Last Modified:
	 */
	public void clickOnElement(WebDriver driver, By strelement) {

		try {
			driver.findElement(strelement).click();
		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(strelement));
		}
	}

    /*        Created by: John Edgal
	 *        Description: Get element attribute
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
    public String getElement_Attribute(WebDriver driver, By strelement, String strattribute) {
    	String strReturn = "";
    	WebElement element = getElement(driver, strelement);
    	
    	switch (strattribute.toLowerCase()) {
    	case "value":
			strReturn = element.getText().trim();
    		break;

			//default:
    	//    if (!(element.getAttribute(strattribute)) = "")) {
    	//		strReturn = element.getAttribute(strattribute.toLowerCase()).trim();
    	//	}
    		
    	//	break;
    	}
    	
    	return strReturn.trim();
    }
    
    
    /*        Created by: John Edgal
	 *        Description: Select check-box (ignores if already selected)
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
    public void selectCheckBox(WebDriver driver, By strelement) {
    	String val = getElement_Attribute(driver, strelement, "Value");
    	if (!val.equals("on")) {
    		WebElement element = getElement(driver, strelement);
    		element.click();
    	}
    }
    
    /*        Created by: John Edgal
	 *        Description: Unselect check-box (ignores if already selected)
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
    public void unselectCheckBox(WebDriver driver, By strelement) {
    	String val = getElement_Attribute(driver, strelement, "Value");
    	if (!val.equals("off")) {
    		WebElement element = getElement(driver, strelement);
    		element.click();
    	}
    }
    
    /*        Created by: John Edgal
	 *        Description: performs a mouseover action on an element
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
    public void mouseover(WebDriver driver, By strelement) {
    	WebElement element = getElement(driver, strelement);
    	Actions action = new Actions(driver);
    	action.moveToElement(element).release().build().perform();
    	
    }
    
    /*        Created by: John Edgal
	 *        Description: compares two string values f
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified: 
	 */
    
    public void compareValues(WebDriver driver, String fieldName, String expVal, String actVal) {


    	actVal = actVal.replace("\n","");
		expVal = expVal.replace("\n","");

		try {
			if ((expVal.equalsIgnoreCase(actVal))){
				Reporter.log(fieldName + " Match Successful: " + actVal);
				System.out.println(fieldName + " Match Successful: " + actVal);
			}else {
				Reporter.log(fieldName + " MisMatch Detected. Expected: " + expVal + " Actual: " + actVal);
				System.out.println(fieldName + " MisMatch Detected. Expected: " + expVal + " Actual: " + actVal);
			}
		} catch (Exception e){
			System.out.println("Mismatch");
			e.printStackTrace();
		}
		//if((!expVal.equalsIgnoreCase(actVal)))
    }

    /*        Created by: John Edgal
	 *        Description: compares two int values
	 *        Date: 23/09/2020
	 *        Output: None
	 *        Last Modified:
	 */
	public void compareValues(WebDriver driver, String fieldName, int expVal, int actVal) {
        try {
			if (Integer.compare(expVal, actVal) == 0) {
				Reporter.log(fieldName + " Match Successful: " + actVal);
				System.out.println(fieldName + " Match Successful: " + actVal);
			}else {
				Reporter.log(fieldName + " MisMatch Detected. Expected: " + expVal + " Actual: " + actVal);
				System.out.println(fieldName + " MisMatch Detected. Expected: " + expVal + " Actual: " + actVal);
			}
		} catch (Exception e){
			System.out.println("Mismatch");
			e.printStackTrace();
		}

	}
    
    /*        Created by: John Edgal
  	 *        Description: compares two values
  	 *        Date: 23/09/2020
  	 *        Output: None
  	 *        Last Modified: 
  	 */      
      public void checkContainsValues(WebDriver driver, String fieldName, String expVal, String actVal) {
      	
      	String strAct = actVal.trim().toLowerCase();
      	String strExp = expVal.trim().toLowerCase();
    	 if (strAct.contains(strExp)) {
      		Reporter.log(fieldName + "Validation Successful: " + actVal);
      	}else {
      		Assert.fail(fieldName + "Validation Failed. Expected: " + expVal + "Actual: " + actVal);
      	}
      }
      
        /*        Created by: John Edgal
    	 *        Description: Select the required tab name
    	 *        Date: 23/09/2020
    	 *        Output: None
    	 *        Last Modified: 
    	 */      
        public void selectTab(WebDriver driver, String tabName) throws InterruptedException {
        	
        By strActiveTab = By.xpath("//td[@class='activeTab']");
        WebElement activeTab = getElement(driver, strActiveTab);
        Thread.sleep(1000);
        if (!activeTab.getText().equalsIgnoreCase(tabName)) {
        	String tabLocator = "//td[text()='" + tabName + "']";
        	By strTab = By.xpath(tabLocator);
        	clickElement(driver, strTab);
        }
        }
        
        /*        Created by: John Edgal
    	 *        Description: Scroll page down
    	 *        Date: 23/09/2020
    	 *        Output: None
    	 *        Last Modified: 
    	 */ 
        public void pageDown(WebDriver driver, Integer number) {
        	JavascriptExecutor jse = (JavascriptExecutor)driver;
        	
        	for(int i = 1; 1<=number; i++) {
        		//jse.executeScript("window.scrollBy(0,250)");
        		jse.executeScript("scroll(0, 250);");
        	}
        }
        
        /*        Created by: John Edgal
    	 *        Description: Scroll page up
    	 *        Date: 23/09/2020
    	 *        Output: None
    	 *        Last Modified: 
    	 */ 
        public void pageUp(WebDriver driver, Integer number) {
        	JavascriptExecutor jse = (JavascriptExecutor)driver;
        	
        	for(Integer i=1; 1<=number;i++) {
        		//jse.executeScript("window.scrollBy(0,-250)");
        		jse.executeScript("scroll(0, -250);");
        	}
        }
        
        /*        Created by: John Edgal
    	 *        Description: Scroll  to desired element prior to clicking
    	 *        Date: 09/10/2020
    	 *        Output: None
    	 *        Last Modified: 
    	 */ 
        public void scrollToElement(WebDriver driver, By strElement) {
        	JavascriptExecutor jse = (JavascriptExecutor)driver;
        	
        	WebElement Element = driver.findElement(strElement);
        	jse.executeScript("arguments[0].scrollIntoView();", Element);       	
        }
        
        /*        Created by: John Edgal
    	 *        Description: Select from dropdown based on selection parameters
    	 *        Date: 23/09/2020
    	 *        Output: None
    	 *        Last Modified: 
    	 */ 
        public void selectDropdown(WebDriver driver, By strelement, String selectBy, String value) throws InterruptedException {
        	//String currValue  = " ";
        	String expValue = value.trim();
        	WebElement element = getElement(driver, strelement);
        	Select dropdown = new Select(element);
        	//java.util.List<WebElement> sel1 = dropdown.getAllSelectedOptions();
        	List<WebElement> sel1 = dropdown.getAllSelectedOptions();
        	
        	switch (selectBy.trim().toLowerCase()) {
        	
        	case "value":
        		if (sel1.size()==0) {
        			dropdown.selectByValue(expValue);
        		}else {
        			String currValue = sel1.get(0).getAttribute("value").trim();
        			if (!currValue.equalsIgnoreCase(expValue)) {
        				dropdown.selectByValue(expValue);       				
        			}
        		}
        		break;
        		
        	case "visibletext":
        		if (sel1.size()==0) {
        			dropdown.selectByVisibleText(expValue);
        		}else {
        			String currValue = sel1.get(0).getAttribute("visibletext").trim();
        			if (!currValue.equalsIgnoreCase(expValue)) {
        				dropdown.selectByVisibleText(expValue);
        			}
        		}
        		break;	
        	case "index":
        		dropdown.selectByIndex(Integer.parseInt(expValue));
        		break;
        	default:
        		System.out.println("Invalid selection type for dropdown mentioned: " + selectBy);
        		break;
        	}
        	
        	try {
        		TimeUnit.SECONDS.sleep(1);
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}
        }
        
        /*        Created by: John Edgal
    	 *        Description: validate page shown
    	 *        Date: 23/09/2020
    	 *        Output: None
    	 *        Last Modified: 
    	 */ 
        public void checkPage(WebDriver driver, String pageTitle) {
        	String strTitle = driver.getTitle();
        	checkContainsValues(driver, "Page Title", strTitle, pageTitle);
        }
        
        /*        Created by: John Edgal
    	 *        Description: encode password for security reasons
    	 *        Date: 23/09/2020
    	 *        Output: None
    	 *        Last Modified: 
    	 */ 
        public String encodePwd(WebDriver driver, String str) {
        	byte[] encodePwdBytes = Base64.encodeBase64(str.getBytes());
			return new String(encodePwdBytes);
        }
        
        /*        Created by: John Edgal
    	 *        Description: Decode password for security reasons
    	 *        Date: 23/09/2020
    	 *        Output: None
    	 *        Last Modified: 
    	 */ 
        public String decodePwd(WebDriver driver, String str) {
        	byte[] decodePwdBytes = Base64.decodeBase64(str.getBytes());
			return new String(decodePwdBytes);
        }
        
        /*        Created by: John Edgal
    	 *        Description: Capture Screenshot where required
    	 *        Date: 23/09/2020
    	 *        Output: None
    	 *        Last Modified: 
    	 */ 
        public void captureScreenshot(WebDriver driver) {
        	
        	int num=0;
        	StackTraceElement[] threadList = Thread.currentThread().getStackTrace();
        	
        	for (int i=0; i<threadList.length; i++) {
        		StackTraceElement a = threadList[i];
        		String value = a.getClassName();
        		if (value.equalsIgnoreCase("sun.reflect.NativeMethodAccessorImpl")) {
        			num = i-1;
        			break;
        		}
        		
        	}
        	String className = Thread.currentThread().getStackTrace()[num].getClassName();
        	String methodName = Thread.currentThread().getStackTrace()[num].getMethodName();
        	String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        	String filename = className+"_"+methodName+"_"+timeStamp+".jpg";
        	String filePath = System.getProperty("user.dir") + "/scr/screenshots/" + filename;
        	
        	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        	      	
        	try {
        		FileUtils.copyFile(scrFile, new File(filePath));
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        	        	
        }
        
        /*        Created by: John Edgal
    	 *        Description: Capture Screenshot for the listeners, with parameters
    	 *        Date: 23/09/2020
    	 *        Output: None
    	 *        Last Modified: 
    	 */ 
        public void getScreenshot(WebDriver driver, String result)
        {
        	File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        	//FileUtils.copyFile(src, new File("C://test//"+result+"screenshot.png"));
        	
        	try {
        		FileUtils.copyFile(src, new File("C://test//"+result+"screenshot.png"));
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }	
        
        /*        Created by: John Edgal
    	 *        Description: Check a table for a given cell value
    	 *        Date: 24/09/2020
    	 *        Output: None
    	 *        Last Modified: 
    	 */ 
         public WebElement checkTableReturnCell(WebDriver driver, By strTable, String tagName, String cellContent) {
        	 
        	 WebElement cellElement = null;
        	 
        	 //Get all the span elements fron table
        	 WebElement element = getElement(driver, strTable);
        	 java.util.List<WebElement> allCells = element.findElements(By.tagName(tagName));
        	 
        	 //Iterate over them
        	 for (WebElement cell: allCells) {
        		 String strCellContentAct = cell.getText().trim().toLowerCase();
        		 String strCellContentScrh = cell.getText().trim().toLowerCase();
        		 
        		 if (strCellContentAct.contains(strCellContentScrh)) {
        			 cellElement = cell;
        			 break;
        		 }
        		 
        	 }
        	 return cellElement;
         }
         
         /*        Created by: John Edgal
     	 *        Description: Select a specific menu item
     	 *        Date: 24/09/2020
     	 *        Output: None
     	 *        Last Modified: 
     	 */
         public void clickMenuItem(WebDriver driver, String menuItemName) {
        	 String itemName = "";
        	 String itemText = "";
        	 
        	 java.util.List<WebElement> list = driver.findElements(By.xpath("//td[contains(@id,'sideMenuItem')]"));
        	 
        	 for (WebElement item:list) {
        		 itemText = item.getText();
        		 
        		 if(!itemText.isEmpty()) {
        			 
        			 if (itemText.equalsIgnoreCase(menuItemName)) {
        				 String itemID = item.getAttribute("id");
        				 itemName = "//td[@id='" +itemID+ "']";
        				 break;
        			 }
        		 }
        	 }
        	 
        	 if (!itemName.equals("")) {
        		 clickElement(driver, By.xpath("itemName"));
             }else {
        	 Assert.fail("No menu item available witht the name: " + menuItemName );       	 
             }
         }
         
         /*        Created by: John Edgal
      	 *        Description: Set of functions to manipulate excel files using XSSFWorkbook
      	 *        Date: 24/09/2020
      	 *        Output: None
      	 *        Last Modified: 
      	 *        Status: 
      	 *        Version:0.1
      	 */
         public XSSFWorkbook setExcelFile(WebDriver driver, String path) throws Exception {
         
        	try {
        		 FileInputStream ExcelFile = new FileInputStream(path);
        		 XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
        		 return ExcelWBook;		 
        	 }catch (Exception e) {
        		 throw (e);
        	 }       	 
         }
         
         // Access required data sheet
         public XSSFSheet setSheet(WebDriver driver, XSSFWorkbook workbook, String sheetName) {
        	 
        	 try {
        		 XSSFSheet ExcelWSheet = workbook.getSheet(sheetName);
        		 return ExcelWSheet;
        	 }catch (Exception e) {
        		 throw (e);
        	 }
         }
         
         //Save the excel file (WORKIGN PROGRESS)
         public void saveExcelFile(String path) throws Exception {
        	 FileInputStream outFile = new FileInputStream(new File(path));
        	 //ExcelWBook.write(outFile);
        	 outFile.close();        	 
         }
         
         //Read test data from excel file STRING VALUE
         public String getCellDataString(WebDriver driver, int rowNum, int colNum) {
        	 try {
        		 Cell = ExcelWSheet.getRow(rowNum).getCell(colNum);
        		 String cellData = Cell.getStringCellValue();
        		 return cellData;
        	 }catch (Exception e) {
        		 return"";
        	 }
        	 
         }
         
       //Read test data from excel file
         public String getCellData(WebDriver driver, int rowNum, int colNum) {
        	 try {
        		 Cell = ExcelWSheet.getRow(rowNum).getCell(colNum);
        		 String cellData = Cell.getRawValue();
        		 return cellData;
        	 }catch (Exception e) {
        		 return"";
        	 }
        	 
         }
         
       //Write test data to the excel file
         public void setCellData(WebDriver driver, String result, int rowNum, int colNum) {
        	 try {
        		 Cell = ExcelWSheet.getRow(rowNum).getCell(colNum);
        		 if (Cell == null) {
        			 Cell = Row.createCell(colNum);
        			 Cell.setCellValue(result);
        		 }else {
        			Cell.setCellValue(result); 
        		 }
        	 }catch (Exception e) {
        		 Reporter.log(e.getMessage());
        	 }        	 
         }
         
         // This method will get the column count based on the row number
         public Integer getColumnCount(WebDriver driver, int rowNum) {
        	 Integer count = 0;
        	 try {
        		 Row = ExcelWSheet.getRow(rowNum);
        		 count = (int) Row.getLastCellNum();
        	 }catch (Exception e) {
        		 Reporter.log(e.getMessage());
        	 } 
        	 return count;
         }
         
         //This will get the column number in the relevant row
         public Integer getColumnNumber(WebDriver driver, int rowNum, String columnName) {
        	 Integer colNum = null;
        	 Integer colCount = getColumnCount(driver, rowNum);
        	 Row = ExcelWSheet.getRow(rowNum);
        	 
        	 try {
        		 for(Integer i=0; i<colCount; i++) {
        			 String cellValue = Row.getCell(i).getStringCellValue();
        			 if (cellValue.equalsIgnoreCase(columnName)) {
        				 colNum = i;
        			 }
        		 }
        	 }catch (Exception e) {
        		 Reporter.log(e.getMessage());
        	 } 
        	 return colNum;
         }

	    /*        Created by: John Edgal
	     *        Description: Write to a .CSV file
	     *        Date: 25/11/2020
	     *        Output: None
	     *        Last Modified:
	     *        Status:
	     *        Version:
	     */
	     public void writeToCsv(WebDriver driver, String filePath, String fieldName, String expVal, String actVal, String val1, String val2, String val3, String val4) throws Exception {

			 String filePathIN = filePath;

			 File file = new File(filePathIN);
			 FileWriter outputfile = new FileWriter(file);
			 CSVWriter writer2 = new CSVWriter(outputfile);
			 List<String[]> data = new ArrayList<>();

			 try{

				 if ((expVal.equalsIgnoreCase(actVal))){

					 Reporter.log(fieldName + " Match Successful: " + actVal);
				 }else {

					 Reporter.log(fieldName + " MisMatch Detected. Expected: " + expVal + " Actual: " + actVal);

					 // add data to csv
					 String[] data1 = { val1, val2, val3, val4 };
					 writer2.writeNext(data1);
					 System.out.println(Arrays.toString(data1));
					 data.add(new String[] { fieldName, val1, val2, val3, val4 });
				 }
			 } catch (Exception e){

				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }
	     }
         
         /*        Created by: John Edgal
      	 *        Description: List of functions to read from an Excel file using XSSFWorkbook
      	 *        Date: 24/09/2020
      	 *        Output: None
      	 *        Last Modified: 
      	 *        Status: WORKING PROGRESS
      	 *        Version:0.2
      	 */
         public void setExcelFile(WebDriver driver, String path, String sheetName) throws Exception {
        	 
        	 try {
        		 FileInputStream ExcelFile = new FileInputStream(path);
				 XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
				 ExcelWSheet = ExcelWBook.getSheet(sheetName);
        	 }catch (Exception e) {
        		 throw (e);
        	 }
        	 
         }
         
         /*        Created by: John Edgal
        	 *        Description: Switch to a child window of the parent window
        	 *        Date: 24/09/2020
        	 *        Output: None
        	 *        Last Modified: 
        	 */
          public void switchToParentWindow(WebDriver driver, String parent) {
        	  for (String window : driver.getWindowHandles()) {
        		  if (!window.equals(parent)) {
        			  driver.switchTo().window(window);
        			  System.out.println("Switch to parent window");
        		  }
        	  }
          }	  
         
         /*        Created by: John Edgal
       	 *        Description: Switch to a child window of the parent window
       	 *        Date: 24/09/2020
       	 *        Output: None
       	 *        Last Modified: 
       	 */
         public void switchToChildWindow(WebDriver driver, String parent) throws InterruptedException {
        	 TimeUnit.SECONDS.sleep(1);
        	 if (driver.getWindowHandles().size()>=1) {
        		 for (String window : driver.getWindowHandles()) {
        			 if (!window.equals(parent)) {
        				 driver.switchTo().window(window);
        			 }   			 
        		 }     		 
        	 }
         }
         
         /*        Created by: John Edgal
          *        Description: Handle alert popups
          *        Date: 24/09/2020
          *        Output: None
          *        Last Modified: 
          */
         public void handleAlert(WebDriver driver, Integer Timeout) {
        	 
        	 WebDriverWait wait = new WebDriverWait(driver, Timeout);
        	 
        	 try {
        		 if (wait.until(ExpectedConditions.alertIsPresent()) !=null){
        			 System.out.println("Alert is present");
        			 Alert alert = driver.switchTo().alert();
        			 alert.accept();      			 
        		 }
        	 } catch (Exception e) {
        		 System.out.println("No Alert");        		 
        	 }
        	 
         }
         
         /*        Created by: John Edgal
          *        Description: Create a text file
          *        Date: 24/09/2020
          *        Output: None
          *        Last Modified: 
          */
         public void createTextFile(WebDriver driver, List<String> fileContent, String fileCreationPath) throws UnsupportedEncodingException {
        	 PrintWriter createFile;
        	 try {
        		 createFile = new PrintWriter(fileCreationPath, "UTF-8");
        		 if(fileContent.size() > 0) {
        			 fileContent.forEach(createFile::println);
        			 createFile.flush();
        			 createFile.close();
        		 }else {
        			 System.out.println("No content received to create file");
        		 }
        		 
        	 }catch (FileNotFoundException e) {
        		 e.printStackTrace();
        		 
        	 }
         }
         
         public int getRandomNumberInRange(int min, int max) {

        	    if (min >= max) {
        	        throw new IllegalArgumentException("max must be greater than min");
        	    }

        	    Random r = new Random();
        	    return r.nextInt((max - min) + 1) + min;
         }

         /*        Created by: John Edgal
	      *        Description: Returns the current page count
	      *        Date: 24/10/2020
	      *        Output: None
	      *        Last Modified:
	      */

	     public int getQuestionCount(WebDriver driver) {

	     	By questionCount = By.xpath("//*[@id=\"counts\"]/div[3]/ul/li[1]/a/span[1]");
	     	waitForDesiredElement(driver, questionCount, 20);
	     	return Integer.parseInt(driver.findElement(questionCount).getText());

	     }

	     /*        Created by: John Edgal
	      *        Description: Returns the current page count
	      *        Date: 24/10/2020
	      *        Output: None
	      *        Last Modified:
          */
	      public String getFolderNameCF(WebDriver driver) {

	      	By Folder = By.xpath("//*[@id=\"section-contents\"]/ul/li[1]/div");
		    String folderVal = driver.findElement(Folder).getText();
	      	return folderVal;

	      }

}



