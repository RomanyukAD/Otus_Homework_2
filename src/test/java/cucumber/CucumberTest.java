package cucumber;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/features",
        glue = "cucumber"
)
public class CucumberTest extends AbstractTestNGCucumberTests {

}
