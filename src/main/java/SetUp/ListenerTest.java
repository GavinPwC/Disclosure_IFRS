package SetUp;

import SetUp.CoreFunctions;
import SetUp.SetupFramework;
import io.qameta.allure.Attachment;
import org.testng.ITestContext;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ListenerTest implements ITestNGListener {

    SetupFramework sf =new SetupFramework();
    CoreFunctions cf = new CoreFunctions(null);


    public void onFinish(ITestContext context) {
        // TODO Auto-generated method stub
        System.out.println("Testing completed ...");
        System.out.println("Total tests passed: " + context.getPassedTests());
        System.out.println("Total tests failed: " + context.getFailedTests());
    }

    public void onStart(ITestResult result) {
        // TODO Auto-generated method stub
        System.out.println("Test has started running: " + result.getMethod().getMethodName() + " at: " + result.getStartMillis());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub

    }

    public void onTestFailure(ITestResult result) {
        // TODO Auto-generated method stub

        System.out.println("Testcase " + result.getName() + " has failed");
        try
        {
            saveScreenshotPNG();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    @Attachment(value="Page screenshot", type="image/png")
    public byte[] saveScreenshotPNG() throws IOException
    {

        //Screenshot reference in byte for allure to import.
        byte[] out = null;

        try {
            BufferedImage screencapture = new Robot().createScreenCapture(
                    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ImageIO.write(screencapture, "png", bo);
            out = bo.toByteArray();
            bo.close();

        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub
        System.out.println("Testcase " + result.getName() + " has been skipped");

    }

    public void onTestStart(ITestContext context) {
        // TODO Auto-generated method stub
        System.out.println(context.getName() + " has began");

    }

    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub
        System.out.println("Testcase " + result.getName() + " has passed");

    }

}




