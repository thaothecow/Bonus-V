// package hellocucumber;
// package stepdefinitions;

// import io.cucumber.java.en.*;
// import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
// import org.junit.Assert;

// import io.cucumber.java.en.Given;
// import io.cucumber.java.en.When;
// import io.cucumber.java.en.Then;
// import static org.assertj.core.api.Assertions.assertThat;
// import org.openqa.selenium.support.ui.Select;

import java.util.Map;

public class CarbohydrateCalculatorSteps {

    WebDriver driver;

    @Given("the user is on the Carbohydrate Calculator page")
    public void userIsOnCalculatorPage() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.calculator.net/carbohydrate-calculator.html");
    }

    @Given("the following inputs are entered:")
    public void enterInputs(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> inputs = dataTable.asMaps().get(0);

        // age
        WebElement ageField = driver.findElement(By.name("cage"));
        ageField.clear();
        ageField.sendKeys(inputs.get("Age"));

        // gender
        if (inputs.get("Gender").equalsIgnoreCase("Male")) {
            driver.findElement(By.xpath("//input[@name='csex' and @value='m']")).click();
        } else {
            driver.findElement(By.xpath("//input[@name='csex' and @value='f']")).click();
        }

        // height
        WebElement heightField = driver.findElement(By.name("cheightmeter"));
        heightField.clear();
        heightField.sendKeys(inputs.get("Height (cm)"));

        // weight
        WebElement weightField = driver.findElement(By.name("ckg"));
        weightField.clear();
        weightField.sendKeys(inputs.get("Weight (kg)"));

        // activity level
        WebElement activityDropdown = driver.findElement(By.id("cactivity"));
        Select select = new Select(activityDropdown);
        select.selectByVisibleText(inputs.get("Activity Level"));
    }

    @When("the user clicks on the Clear button")
    public void clickClearButton() {
        driver.findElement(By.xpath("//input[@value='Clear']")).click();
    }

    @When("the user clicks on the Calculate button")
    public void clickCalculateButton() {
        driver.findElement(By.xpath("//input[@value='Calculate']")).click();
    }

    @When("the user enters {string} into the Age field")
    public void enterAge(String ageValue) {
        WebElement ageField = driver.findElement(By.id("cage"));
        ageField.clear();
        ageField.sendKeys(ageValue);
    }

    @Then("all input fields should be empty")
    public void verifyFieldsAreEmpty() {
        Assert.assertEquals("", driver.findElement(By.name("cage")).getAttribute("value"));
        Assert.assertEquals("", driver.findElement(By.name("cheightmeter")).getAttribute("value"));
        Assert.assertEquals("", driver.findElement(By.name("ckg")).getAttribute("value"));
    }

    @Then("an error message {string} should be displayed")
    public void verifyErrorMessage(String expectedMessage) {
        WebElement error = driver.findElement(By.xpath(
            "//div[contains(@style,'error.svg')]//font[contains(text(),'" + expectedMessage + "')]"
        ));
        Assert.assertTrue("Expected error message not displayed: " + expectedMessage, error.isDisplayed());
    }

    @Then("the age input error message {string} should be displayed")
    public void verifyAgeFieldErrorMessage(String expectedMessage) {
        WebElement error = driver.findElement(By.id("cageifcErr"));
        Assert.assertTrue("Expected error message not displayed: " + expectedMessage,
                      error.isDisplayed() && error.getText().contains(expectedMessage));
    }

    @Then("the daily calorie allowance should be {string}")
    public void verifyDailyCalorieAllowance(String expectedCalories) {
        WebElement allowanceCell = driver.findElement(By.xpath(
            "//table[@class='cinfoT']//tr[td[text()='Weight Maintenance']]/td[2]"
        ));

        String allowanceText = allowanceCell.getText();
        Assert.assertEquals("Daily calorie allowance mismatch", expectedCalories, allowanceText);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}