package Pages;

import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CompareCSVFilesPage {

    public WebDriver driver;

    public CompareCSVFilesPage(WebDriver driver) {
        // TODO Auto-generated constructor stub
        this.driver = driver;
    }

    public void compareCSVFiles(WebDriver driver) throws IOException {

            String fileNameIN                = "Original_Tailoring_File.csv";
            String fileNameINWrite           = "autoClient2 - 27-11-2020 - TailoringAssumptions.csv";
            //String fileNameINWrite         = "DiscloseResponses.csv";
            String fileNameINWriteDifference = "DifferenceFile.csv";
            String filePathIN                = System.getProperty("user.dir") + "\\src\\main\\java\\Data Files\\" + fileNameIN;
            String filePathINWrite           = System.getProperty("user.dir") + "\\src\\main\\java\\Data Files\\" + fileNameINWrite;
            String filePathINWriteDifference = System.getProperty("user.dir") + "\\src\\main\\java\\Data Files\\" + fileNameINWriteDifference;

            ArrayList al1=new ArrayList();
            ArrayList al2=new ArrayList();

            BufferedReader CSVFile1 = new BufferedReader(new FileReader(filePathIN));
            String dataRow1 = CSVFile1.readLine();

            while (dataRow1 != null)
            {
                String[] dataArray1 = dataRow1.split("/n");
                Collections.addAll(al1, dataArray1);

                dataRow1 = CSVFile1.readLine(); // Read next line of data.
            }

            CSVFile1.close();

            BufferedReader CSVFile2 = new BufferedReader(new FileReader(filePathINWrite));
            String dataRow2 = CSVFile2.readLine();
            while (dataRow2 != null)
            {
                String[] dataArray2 = dataRow2.split("/n");
                Collections.addAll(al2, dataArray2);
                dataRow2 = CSVFile2.readLine(); // Read next line of data.
            }
            CSVFile2.close();

            String bs = null;
            for(Object o: al2)
            {
                bs = o.toString();
                al1.remove(bs); // Checks for Additional Row in al1 and difference in rows in al1,
                                // but does not check for missing rows which are in bs but not in al1
            }

            int size=al1.size();
            System.out.println("Total Differences: "+size);
            System.out.println("Additional Rows missing on other .CSV : "+bs);

            try
            {
                FileWriter writer=new FileWriter(filePathINWriteDifference);
                while(size!=0)
                {
                    size--;
                    writer.append("").append(String.valueOf(al1.get(size)));
                    writer.append('\n');
                }
                writer.flush();
                writer.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }


