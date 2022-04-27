package Cucumber;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/features",
        glue = "Cucumber"
)
public class OpenCucumberTest extends AbstractTestNGCucumberTests {

}
