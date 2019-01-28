package common.webdriver.errorReporting;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

/*********
 * Utility to to generate cucumber report
 * 
 * @author AKASH KATE
 *
 */
public class GenerateReport {

	public static void generateReport(String projectName, String reportFolderName) {
		CucumberResultsOverview results = new CucumberResultsOverview();
		results.setOutputDirectory(reportFolderName);
		results.setOutputName("cucumber-results");
		results.setSourceFile(reportFolderName + "/cucumber.json");
		try {
			results.executeFeaturesOverviewReport();
			generateCucumberReport(projectName, reportFolderName);
		} catch (Exception e) {
			System.out.println("Not able to create cucumber reports" + e.getMessage());
		}

	}

	public static void generateCucumberReport(String projectName, String reportFolderName) {
		File reportOutputDirectory = new File(reportFolderName);
		List<String> jsonFiles = new ArrayList<>();
		jsonFiles.add(System.getProperty("user.dir") + "\\" + reportFolderName + "\\cucumber.json");
		String jenkinsBasePath = "";
		String buildNumber = "version 1";
		String projName = projectName + " Automation Report";
		boolean skippedFails = true;
		boolean pendingFails = false;
		boolean undefinedFails = true;
		boolean runWithJenkins = false;
		boolean parallelTesting = false;
		Configuration configuration = new Configuration(reportOutputDirectory, projName);
		configuration.setStatusFlags(skippedFails, pendingFails, undefinedFails);
		configuration.setParallelTesting(parallelTesting);
		configuration.setJenkinsBasePath(jenkinsBasePath);
		configuration.setRunWithJenkins(runWithJenkins);
		configuration.setBuildNumber(buildNumber);
		ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
		reportBuilder.generateReports();
	}
}
