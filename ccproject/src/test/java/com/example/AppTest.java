package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest 
{
    WebDriver driver;
    ExtentReports reports;
    ExtentTest test;
    Logger logger=Logger.getLogger(AppTest.class);
    @BeforeTest
    public void setup() {
        reports = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter(
                "C:\\Users\\91701\\Desktop\\it sckcet\\softwareTesting-1\\ExtentReports\\exercise1\\report.html");
        reports.attachReporter(spark);
        test = reports.createTest("Demo Result");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        PropertyConfigurator.configure("C:\\Users\\91701\\Desktop\\cc2softwaretesting\\src\\test\\java\\com\\resources\\log4j.properties");
    }
    @BeforeMethod
    public void navigateUrl() {
        driver.navigate().to("https://www.barnesandnoble.com/");
    }
    @Test(priority = 0) 
    public void testCase1() throws IOException, InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/div[1]/a")).click();
        driver.findElement(By.linkText("Books")).click();
        FileInputStream fs = new FileInputStream("\"D:\\input for websites.xlsx\"");
        XSSFWorkbook work = new XSSFWorkbook(fs);
        XSSFSheet sheet = work.getSheet("Bank login");
        XSSFRow row = sheet.getRow(6);
        String input = row.getCell(0).getStringCellValue();
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/div[2]/div/input[1]")).sendKeys(input);
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/span/button")).click();
        Thread.sleep(5000);
        String name = driver.findElement(By.xpath("//*[@id=\"searchGrid\"]/div/section[1]/section[1]/div/div[1]/div[1]/h1")).getText();
        if(name.contains("Chetan Bhagat")) {
            logger.info("The text contains Chetan Bhagat");
        }
        else {
            logger.error("The text doesnot contains Chetan Bhagat");
        }
    }
    @Test(priority = 1)
    public void testCase2() throws InterruptedException {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//*[@id=\"rhfCategoryFlyout_Audiobooks\"]")));
        driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/div/ul/li[5]/div/div/div[1]/div/div[2]/div[1]/dd/a[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"addToBagForm_2940159543998\"]/input[11]")).click();
    }
    @Test(priority = 2)
    public void testCase3() throws InterruptedException, IOException {
        driver.findElement(By.xpath("//*[@id=\"footer\"]/div/dd/div/div/div[1]/div/a[5]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"rewards-modal-link\"]")).click();
        Thread.sleep(2000);
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = "C:\\Users\\91701\\Desktop\\it sckcet\\softwareTesting-1\\ExtentReports\\exercise1\\calculator.png";
        FileUtils.copyFile(screen, new File(path));
        
    }
}
