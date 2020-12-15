package com.open.hotel.hooks;

import com.open.hotel.loadConfig.Config;
import com.open.hotel.pages.Login;
import com.open.hotel.pages.Search;
import com.open.hotel.threadVariables.VariableManager;
import com.open.hotel.threadVariables.Variables;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.*;

public class Hooks {

	@Before()
	public void beforeScenario(Scenario scenario) {
		String testCaseName = scenario.getName().split(":")[1];
		String testCaseID = scenario.getName().split(":")[0];
		Variables variables = new Variables();
		VariableManager.getInstance().setVariables(variables);
		VariableManager.getInstance().getVariables().setVar("testCaseName", testCaseName);
		VariableManager.getInstance().getVariables().setVar("testCaseID", testCaseID);
		VariableManager.getInstance().getVariables().setVar("scenario", scenario);
	}

	@After()
	public void afterScenario(Scenario scenario) throws ParseException {
		WebDriver driver = (WebDriver) VariableManager.getInstance().getVariables().getVar("driver");
		if (driver != null) {
			driver.close();
			driver.quit();
		}
	}
}