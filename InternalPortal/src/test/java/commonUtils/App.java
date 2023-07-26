package commonUtils;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class App {
	public static WebDriver driver;
	public static WebDriverWait wait;

	public static String url ;
	public static String mail ;
	public static String pwd ;

	@BeforeTest
	public static void login() throws Exception{
		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		//		options.addArguments("--headless");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();


		urlOpen("qa");
		Actions act = new Actions(driver);
		//		act.sendKeys(Keys.CONTROL ,Keys.SHIFT , "I").build().perform();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/form/div[3]/button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-center-cols-container']")));
		Thread.sleep(1800);
	} 
	@AfterTest
	public static void logout() throws Exception {
		driver.findElement(By.xpath("//*[@class='down-arrow']")).click();
		List<WebElement> btns = driver.findElements(By.xpath("//*[@role='menuitem']"));
		btns.get(1).click();
		driver.close();
	}
	public static void urlOpen(String instance) {

		if (instance.equals("qa")) {
			url = "https://buzzworld-web-iidm.enterpi.com/quote_for_parts";
			mail = "sivakrishna.d@enterpi.com";
			pwd = "Test@4321";
		} else if(instance.equals("stage")) {
			url = "https://www.staging-buzzworld.iidm.com/quote_for_parts";
			mail = "defaultuser@enterpi.com";
			pwd = "Enter@4321";
		}
		driver.get(url);

		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		driver.findElement(By.id("username")).sendKeys(mail);
		driver.findElement(By.id("password")).sendKeys(pwd);
	}
	public static void main(String args[]) throws  Exception 
	{
		String file = "tcfile.xlsx";
		FileOutputStream fo = new FileOutputStream(file);
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("Sheet1");
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		XSSFRow row ;
		row = sheet.createRow(0);
		row.createCell(0).setCellValue("Test Case Name");
		row.createCell(1).setCellValue("Actual Text");
		row.createCell(2).setCellValue("Expected Text");
		row.createCell(3).setCellValue("Page Name");
		row.createCell(4).setCellValue("Status");
		row.createCell(5).setCellValue("Date");
		CellStyle style = wb.createCellStyle();
		XSSFFont font= wb.createFont();
		font.setBold(true);
		style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(font);
		row.setRowStyle(style);
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/testing", "enterpi", "enterpi@1234");
		Statement st = con.createStatement();
		//To down load Table use below command

		String sql = ("SELECT * FROM buzzworld_automation_logs ORDER BY test_case_name;");
		ResultSet rs = st.executeQuery(sql);
		int col = 1;
		while(rs.next()) {
			String tc = rs.getString("test_case_name"); 
			String actText = rs.getString("actual_text");
			String expText = rs.getString("expected_text");
			String pn = rs.getString("page_name");
			String status = rs.getString("status");
			Date dt = rs.getDate("date");
			row = sheet.createRow(col);
			row.createCell(0).setCellValue(tc);
			row.createCell(1).setCellValue(actText);
			row.createCell(2).setCellValue(expText);
			row.createCell(3).setCellValue(pn);
			row.createCell(4).setCellValue(status);
			CellStyle style1 = wb.createCellStyle();
			if (status.equalsIgnoreCase("Passed")) {
				style1.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
				style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				row.getCell(4).setCellStyle(style);
			} else {
			}
			row.createCell(5).setCellValue(String.valueOf(dt));
			col=col+1;

		}
		con.close();
		wb.write(fo);
		fo.close();
	}
	public static void displayPopUp(String data) 
	{
		JOptionPane jop = new JOptionPane();
		jop.setMessageType(JOptionPane.PLAIN_MESSAGE);
		jop.setMessage("<html><ul><h4 style=\"color: blue;\">"+data+"</h4></ul></html>");
		final JDialog dialog = jop.createDialog(null, "Executed Test Case is...");
		// Set a 2 second timer
		new Thread(new Runnable() {
		    @Override
		    public void run() {
		        try {
		            Thread.sleep(2500);
		        } catch (Exception e) {
		        }
		        dialog.dispose();
		    }
		}).start();

		dialog.setVisible(true);
	}
	public static void spinner() 
	{
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		try {
			
			Thread.sleep(1200);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
