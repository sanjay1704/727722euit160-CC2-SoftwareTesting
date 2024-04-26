package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bouncycastle.crypto.prng.drbg.DualECPoints;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class AppTest 
{
    WebDriver driver;
    JavascriptExecutor js;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() throws Exception{
        driver = new ChromeDriver();
        driver.get("https://www.barnesandnoble.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test(priority = 1)
    public void TestCase1() throws Exception{
        FileInputStream fis = new FileInputStream("D:\\Software Testing\\credentails.xlsx");
        String author = new XSSFWorkbook(fis).getSheet("cc2Sheet").getRow(1).getCell(0).getStringCellValue();
        driver.findElement(By.linkText("All")).click();
        driver.findElement(By.linkText("Books")).click();
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/div[2]/div/input[1]")).sendKeys(author);
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/span/button")).click();
        assertTrue(driver.getPageSource().contains("Chetan Bhagat"));
    }

    @Test(priority=2)
    public void TestCase2() throws Exception{
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.linkText("Audiobooks"))).perform();
        driver.findElement(By.linkText("Audiobooks Top 100")).click();
        js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 100)");
        driver.findElement(By.linkText("Funny Story")).click();
        js.executeScript("window.scrollBy(0, 300)");
        driver.findElement(By.xpath("//*[@id='commerce-zone']/div[2]/ul/li[2]/div/div/label/span")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"find-radio-checked\"]/div[1]/form/input[5]"))).click();
        String msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"dialog-title\"]/em/div"))).getText();
        assertEquals(msg, "Item Successfully Added To Your Cart"); 
    }

    @Test(priority =3)
    public void TestCase3() throws Exception{
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='onetrust-accept-btn-handler']"))).click();
        js.executeScript("window.scrollBy(0, 10000)");
        driver.findElement(By.linkText("B&N Membership")).click();
        js.executeScript("window.scrollBy(0, 2000)");
        driver.findElement(By.linkText("JOIN REWARDS")).click();
        String msg = driver.switchTo().frame(driver.findElement(By.xpath("/html/body/div[7]/div/iframe"))).findElement(By.xpath("//*[@id=\"dialog-title\"]")).getText();
        assertEquals(msg, "Sign in or Create an Account");
    }

    @AfterMethod
    public void setdown() throws Exception{
        driver.close();
    }
 }
