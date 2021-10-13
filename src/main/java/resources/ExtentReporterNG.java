package resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG extends base {
	static ExtentReports extent;

	public static ExtentReports getReportObject() {

		DateFormat dateFormat = new SimpleDateFormat("MMddyyyy-HHmmss");
		Date date = new Date();
		String time = dateFormat.format(date);

		String path = System.getProperty("user.dir") + "\\reports\\index" + time + ".html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("EME Automation Results");
		reporter.config().setDocumentTitle("Run Results");

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Test Engineer", "JFI");
		return extent;

	}

}
