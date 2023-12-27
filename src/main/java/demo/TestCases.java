package demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import static java.util.concurrent.TimeUnit.SECONDS;


public class TestCases {
    ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, SECONDS);

    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }


    public void testCase01_VerifyHomePageUrl() {
//        System.out.println("Start Test case: testCase01");
//        driver.get("https://www.google.com");
//        System.out.println("end Test case: testCase02");


        try {
            System.out.println("Start Test case: testCase01");
            driver.get("https://www.makemytrip.com/");


            String currenturl = driver.getCurrentUrl();

            if (currenturl.contains("makemytrip")) {
                System.out.println("URL contains makemytrip");
            } else {
                System.out.println("URL does not contain makemytrip");
            }

            System.out.println("end Test case: testCase01");


        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void testCase02_GetFlightDetails() {

            WebElement flightsTab = driver.findElement(By.xpath("(//span[text()='Flights'])[1]"));
            flightsTab.click();

            // Selecting departure airport
            WebElement departureInput = driver.findElement(By.xpath("//input[@id='fromCity']"));
            departureInput.sendKeys("blr");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement selectBLR = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Bengaluru, India']")));
            selectBLR.click();

            // Selecting arrival airport
            WebElement arrivalInput = driver.findElement(By.xpath("//input[@id='toCity']"));
            arrivalInput.sendKeys("DEL");

            WebElement selectDEL = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='New Delhi, India']"))); // Corrected XPath
            selectDEL.click();

            // Selecting date
            WebElement selectJan20 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//p[text()='20']/following-sibling::p)[1]")));
            selectJan20.click();

            WebElement searchButton = driver.findElement(By.xpath("//a[contains(text(),'Search')]"));
            searchButton.click();
            WebElement popUp = driver.findElement(By.xpath("//span[@class='bgProperties  overlayCrossIcon icon20']"));
            popUp.click();

            // Wait for results to load and select first flight price
            driver.manage().timeouts().implicitlyWait(10, SECONDS);
            List<WebElement> flightPriceElems = driver.findElements(By.xpath("//div[@class='blackText fontSize18 blackFont white-space-no-wrap clusterViewPrice']"));
            String flightPrice = flightPriceElems.get(0).getText();

            assert !flightPrice.isEmpty();
            System.out.println("end Test case: testCase02");

    }

         public void testCase03_GetTrainDetails() throws InterruptedException {
        // Going to trains tab
             driver.get("https://www.makemytrip.com/");
             Thread.sleep(3000);
         WebElement trainsTab = driver.findElement(By.xpath("//span[@class='chNavIcon appendBottom2 chSprite chTrains']"));
         trainsTab.click();
         Thread.sleep(3000);
         WebElement fromStation = driver.findElement(By.xpath("//label[@for='fromCity']//span[1]"));
         fromStation.click();

         //selecting departure station
        // WebElement selectFromStation = driver.findElement(By.cssSelector("span[data-cy='fromCityLabel']"));
         WebElement SelectBangalore = driver.findElement(By.xpath("//input[@placeholder='From']"));
         SelectBangalore.click();
         SelectBangalore.sendKeys("ypr");
         Thread.sleep(3000);
         WebElement confirmBangalore = driver.findElement(By.xpath("(//div[@class='calc50']//p)[1]"));
         confirmBangalore.click();

             //selecting Arrival station
         WebElement selectArrivalStation = driver.findElement(By.xpath("//label[@for='toCity']//span[1]"));
         selectArrivalStation.click();
         WebElement searchStation = driver.findElement(By.xpath("//input[@title='To']"));
         searchStation.sendKeys("ndls");
         WebElement selectNewDelhi = driver.findElement(By.xpath("//span[normalize-space()='NDLS']"));
         selectNewDelhi.click();
         Thread.sleep(2000);

         // Selecting Date and class

             WebElement selectDateForTrain = driver.findElement(By.xpath("//input[@id='travelDate']"));
             selectDateForTrain.click();
             WebElement confirmDate = driver.findElement(By.xpath("//div[@aria-label='Fri Jan 19 2024']/following-sibling::div[1]"));
             confirmDate.click();
             Thread.sleep(2000);
             WebElement selectClass = driver.findElement(By.xpath("//span[@class='appendBottom5 downArrow']"));
             Thread.sleep(2000);
             selectClass.click();
             WebElement confirmClass = driver.findElement(By.xpath("//li[text()='Third AC']"));
             Thread.sleep(2000);
             confirmClass.click();
             WebElement searchTrainBtn = driver.findElement(By.xpath("//a[contains(text(),'Search')]"));
             searchTrainBtn.click();


             // Wait for results to load and select first Train ticket price
             driver.manage().timeouts().implicitlyWait(10, SECONDS);
             List<WebElement> trainPriceElems = driver.findElements(By.xpath("//div[@id='train_options_20-01-2024_0']"));
             String trainPrice = trainPriceElems.get(0).getText();

             assert !trainPrice.isEmpty();
             System.out.println("end Test case: testCase03");


    }

    public void testCase04_VerifyNoBusesFound() throws InterruptedException {

        driver.get("https://www.makemytrip.com/");
        Thread.sleep(3000);
        WebElement selectBuses = driver.findElement(By.xpath("//span[text()='Buses']"));
        selectBuses.click();

        //Selecting departure city for buses

        WebElement departureCity = driver.findElement(By.xpath("(//div[@class='bussw_inputBox selectHtlCity']//label)[1]"));
        departureCity.click();
        WebElement putCityName = driver.findElement(By.xpath("//input[@placeholder='From']"));
        putCityName.sendKeys("bangl");
        Thread.sleep(2000);
        WebElement confirmCityName = driver.findElement(By.xpath("//span[text()='Bangalore, Karnataka']"));
        Thread.sleep(2000);
        confirmCityName.click();

        //Selecting arrival city for buses

        WebElement to = driver.findElement(By.xpath("//input[@id='toCity']"));

        WebElement tocity = driver.findElement(By.xpath("//input[contains(@class,'react-autosuggest__input react-autosuggest__input--open')]"));
        tocity.sendKeys("del");
        Thread.sleep(3000);
        WebElement toci = driver.findElement(By.xpath("//p[@class='searchedResult font14 darkText'][1]"));
        toci.click();
        // Selecting Date of Journey

        String date = "January 2024";

        while(driver.findElement(By.xpath("//div[@role='heading']//div[1]")).isDisplayed()){

            System.out.println("Testcase04 : " + driver.findElement(By.xpath("//div[@role='heading']//div[1]")));
            driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click();
            String currMonth =driver.findElement(By.xpath("//div[@role='heading']//div[1]")).getText();
            if(date.equalsIgnoreCase(currMonth))
            {
                driver.findElement(By.xpath("//div[@aria-label='Sat Jan 20 2024']")).click();
                break;
            }
        }

        //click on search buttton
        WebElement searchbtn = driver.findElement(By.xpath("//button[@data-cy='submit']"));
        searchbtn.click();


        //verify no buses found
        WebElement noBusesElement = driver.findElement(By.xpath("//span[@class='error-title']"));
        String displayedText = noBusesElement.getText();
        if(displayedText.contains("No buses found")){
            System.out.println(displayedText);
        }else {
            System.out.println("bus is shown");
        }
        System.out.println("end Test case: testCase04");
        }
}




