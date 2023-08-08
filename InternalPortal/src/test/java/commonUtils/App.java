package commonUtils;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
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
	public static void login() throws Exception
	{
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		//options.addArguments("--headless");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		//Select Environment
		urlOpen("qa");
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
		CellStyle style = wb.createCellStyle(); 
		style.setAlignment(HorizontalAlignment.JUSTIFY);
		row = sheet.createRow(0);
		row.createCell(0).setCellValue("Test Case Name");
		row.createCell(1).setCellValue("Actual Text");
		row.createCell(2).setCellValue("Expected Text");
		row.createCell(3).setCellValue("Page Name");
		row.createCell(4).setCellValue("Status");
		row.createCell(5).setCellValue("Date");
		row.createCell(6).setCellValue("Environment");
		//String dbName = "demo"; String userName = "root"; String pwd = "siva7661@"; String host = "localhost";
		String dbName = "testing"; String userName = "enterpi"; String pwd = "enterpi@1234"; String host = "192.168.1.35";
		Connection con = DriverManager.getConnection("jdbc:mysql://"+host+"/"+dbName, userName, pwd);
		Statement st = con.createStatement();
		//To down load Table use below command
		String sql = ("SELECT * FROM buzzworld_automation_logs ORDER BY test_case_name;");
		ResultSet rs = st.executeQuery(sql);
		int col = 1;
		while(rs.next()) 
		{
			String tc = rs.getString("test_case_name"); 
			String actText = rs.getString("actual_text");
			String expText = rs.getString("expected_text");
			String pn = rs.getString("page_name");
			String status = rs.getString("status");
			String dt = rs.getString("date");
			String inst = rs.getString("environment");
			row = sheet.createRow(col);
			row.createCell(0).setCellValue(tc);
			row.createCell(1).setCellValue(actText);
			row.createCell(2).setCellValue(expText);
			row.createCell(3).setCellValue(pn);
			row.createCell(4).setCellValue(status);
			row.createCell(5).setCellValue(String.valueOf(dt));
			row.createCell(6).setCellValue(inst);
			col=col+1;
		}
		Font boldFont = wb.createFont();
		//Set the bold to font in excel sheet
		boldFont.setBold(true);
		//Increase the header row font size 
		boldFont.setFontHeightInPoints((short) 13);
		CellStyle boldCellStyle = wb.createCellStyle();
		//Set the bold to font in excel sheet
		boldCellStyle.setFont(boldFont);
		//Set the bold to font in excel sheet
		boldCellStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		boldCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		for(int i =0; i<sheet.getRow(0).getLastCellNum();i++)
		{
			sheet.getRow(0).getCell(i).setCellStyle(boldCellStyle);
			//Freeze the header row (first row)
			sheet.createFreezePane(i, 1); // (column, row)
		}
		//Adjust the column width to header row
		sheet.setColumnWidth(0, 20*600); sheet.setColumnWidth(1, 20*400); sheet.setColumnWidth(2,20*400);
		sheet.setColumnWidth(3, 20*255); sheet.setColumnWidth(4,20*166); sheet.setColumnWidth(5, 20*253);
		sheet.setColumnWidth(6, 20*177);
		con.close();
		wb.write(fo);
		fo.close();
		//Send reports to email
		App.mail("sivakrishna.d@enterpi.com", "Siva7661@", "sivakrishna.d@enterpi.com");
	}
	public static void mail(final String sendorMail, final String sendorPwd, String receiverMail) {

		// Create object of Property file
		Properties props = new Properties();

		// this will set host of server- you can change based on your requirement 
		props.put("mail.smtp.host", "smtp.gmail.com");

		// set the port of socket factory 
		props.put("mail.smtp.socketFactory.port", "465");

		// set socket factory
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

		// set the authentication to true
		props.put("mail.smtp.auth", "true");

		// set the port of SMTP server
		props.put("mail.smtp.port", "465");

		// This will handle the complete authentication
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sendorMail, sendorPwd);
			}
		});
		try {

			// Create object of MimeMessage class
			Message message = new MimeMessage(session);

			// Set the from address
			message.setFrom(new InternetAddress(sendorMail));

			// Set the recipient address
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(receiverMail));

			// Add the subject link
			message.setSubject("Automation Testing Reports");

			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();

			// Set the body of email
			messageBodyPart1.setText("Automation Reports");

			// Create another object to add another content
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			// Mention the file which you want to send
			String filename = "tcfile.xlsx";

			// Create data source and pass the filename
			DataSource source = new FileDataSource(filename);

			// set the handler
			messageBodyPart2.setDataHandler(new DataHandler(source));

			// set the file
			messageBodyPart2.setFileName(filename);

			// Create object of MimeMultipart class
			Multipart multipart = new MimeMultipart();

			// add body part 1
			multipart.addBodyPart(messageBodyPart2);

			// add body part 2
			multipart.addBodyPart(messageBodyPart1);

			// set the content
			message.setContent(multipart);

			// finally send the email
			Transport.send(message);

			System.out.println("=====Email Sent=====");

		} catch (MessagingException e) {

			throw new RuntimeException(e);

		}
		System.err.println("email sent successfully....");
	}
	public static String clickLabel(String val) throws SQLException 
	{
		String sql = "SELECT label_path FROM Clicking_Label WHERE label_name='"+val+"';";
		Connection con = DriverManager.getConnection("jdbc:mysql://192.168.1.35/testing", "enterpi", "enterpi@1234");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		String labelVal = "";
		while(rs.next()) {
			labelVal = rs.getString("label_path");
			System.out.println(labelVal);
		}
		return labelVal;
	}
	public static ResultSet adminTabs() throws SQLException {
		String sql = "SELECT admin_tab_names,tc_name FROM Clicking_Label WHERE admin_tab_names IS not null;";
		Connection con = DriverManager.getConnection("jdbc:mysql://192.168.1.35/testing", "enterpi", "enterpi@1234");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		return rs;
	}
	public static void horizentalScroll() throws SQLException 
	{
		Actions act = new Actions(driver);
		act.dragAndDrop(driver.findElement(By.xpath(App.clickLabel("scroll_from")))
				, driver.findElement(By.xpath(App.clickLabel("scroll_to")))).build().perform();
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
	public static String getGridData(int count) 
	{
		//list price value from first row
		String lp = driver.findElement(By.xpath("//*[@row-index = '0']")).findElements(By.xpath("//*[starts-with(@style, 'left')]")).get(count).getText();
		return lp;
	}
	public static void clearFilter() 
	{
		try {
			driver.findElement(By.xpath(App.clickLabel("filter_clear"))).isDisplayed();
			driver.findElement(By.xpath(App.clickLabel("filter_clear"))).click();
			App.spinner();
			Thread.sleep(1200);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public static void clearTopSearch() 
	{
		try {
			driver.findElement(By.xpath(App.clickLabel("cross_symbol_search"))).isDisplayed();
			driver.findElement(By.xpath(App.clickLabel("cross_symbol_search"))).click();
			App.spinner();
			Thread.sleep(1200);
		} catch (Exception e) {}
		App.spinner();
	}
	public static void spinner() 
	{
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		try {
			Thread.sleep(1200);
		} catch (Exception e) {
		}
	}
	public static void values1(Object data[]) throws Exception {
		String date = java.time.LocalDateTime.now().toString().replace("T", " ").substring(0, 19);
		Class.forName("com.mysql.jdbc.Driver");  
		//String dbName = "demo"; String userName = "root"; String pwd = "siva7661@"; String host = "localhost";
		String dbName = "testing"; String userName = "enterpi"; String pwd = "enterpi@1234"; String host = "192.168.1.35";
		Connection con = DriverManager.getConnection("jdbc:mysql://"+host+"/"+dbName, userName, pwd);
		Statement stmt=con.createStatement(); 
		String sql = "INSERT INTO buzzworld_automation_logs ( test_case_name, actual_text, expected_text, page_name, status, date, environment) "
				+ "VALUES ('"+ data[0]+ "',\""+ data[1] + "\",\""+ data[2] + "\",'" + data[3] + "','" + data[4]+ "','"+date+"','"+data[6]+"')";
		stmt.executeUpdate(sql);  
	}
}
