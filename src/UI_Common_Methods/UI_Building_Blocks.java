package UI_Common_Methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.io.Files;

import Common_Utilities.Utility_Methods;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.Select;

public class UI_Building_Blocks {

	WebDriver driver;
	File logDir;
	Actions act;

	public UI_Building_Blocks(WebDriver driver, File logDir) {
		this.driver = driver;
		this.logDir = logDir;
		act = new Actions(driver);
	}

	@Step("Wait until page contains element")
	public WebElement Wait_Until_Page_Contains_Element(String locator, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = expwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		return element;
	}

	@Step("Wait_Until_Page_Doesnt_Contains_Element")
	public void Wait_Until_Page_Doesnt_Contains_Element(String locator, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		expwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
	}

	@Step("Wait_Until_Page_Contains_Elements")
	public List<WebElement> Wait_Until_Page_Contains_Elements(String locator, int noofelements, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		List<WebElement> elements = expwait
				.until(ExpectedConditions.numberOfElementsToBe(By.xpath(locator), noofelements));
		return elements;
	}

	@Step("Click_Element")
	public void Click_Element(WebElement Element, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		expwait.until(ExpectedConditions.elementToBeClickable(Element)).click();
	}

	@Step("Scroll_Click_Element")
	public void Scroll_Click_Element(WebElement Element, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = expwait.until(ExpectedConditions.elementToBeClickable(Element));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scroll-IntoView(true);", element);
		element.click();
	}

	@Step("Input_Text_With_String")
	public void Input_Text(WebElement Element, String data, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		expwait.until(ExpectedConditions.elementToBeClickable(Element)).sendKeys(data);
	}

	@Step("Scroll_Input_Text_With_String")
	public void Scroll_Input_Text(WebElement Element, String data, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = expwait.until(ExpectedConditions.elementToBeClickable(Element));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scroll-IntoView(true);", element);
		element.sendKeys(data);
	}

	@Step("Input_Text_With_Integer")
	public void Input_Text(WebElement Element, int data, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		expwait.until(ExpectedConditions.elementToBeClickable(Element)).sendKeys(String.valueOf(data));
	}

	@Step("Scroll_Input_Text_With_Int")
	public void Scroll_Input_Text(WebElement Element, int data, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		expwait.until(ExpectedConditions.elementToBeClickable(Element));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scroll-IntoView(true);", Element);
		Element.sendKeys(String.valueOf(data));
	}

	@Step("Scroll_To_WebElement")
	public void Scroll_To_WebElement(WebElement Element, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		expwait.until(ExpectedConditions.elementToBeClickable(Element));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scroll-IntoView(true);", Element);
	}

	@Step("Switch_Frame")
	public void Switch_Frame(WebDriver driver, WebElement frameElement, int timeout) {
		driver.switchTo().frame(frameElement);
	}

	@Step("Fetch_Multi_WebElement")
	public List<WebElement> Fetch_Multi_WebElement(WebElement Element, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		List<WebElement> Elements = expwait.until(ExpectedConditions.visibilityOfAllElements(Element));
		return Elements;
	}

	@Step("Calendar_Select_Any_Date")
	public void Calendar_Select_Any_Date(WebElement calendarelement, WebElement dateselement, String desireddate,
			int timeout) {
		Click_Element(calendarelement, timeout);
		List<WebElement> All_Dates = Fetch_Multi_WebElement(dateselement, timeout);
		for (WebElement Date : All_Dates) {
			String datevalue = Date.getText();
			if (datevalue.equals(desireddate)) {
				Date.click();
			}
		}

	}

	@Step("Dropdown_Select_Any_Value")
	public void Dropdown_Select_Any_Value(WebElement dropdownelement, List<WebElement> All_Values, String searchvalue,
			String desireddvalue, int timeout) {
		Input_Text(dropdownelement, searchvalue, timeout);
		for (WebElement Value : All_Values) {
			String value = Value.getText();
			if (value.equals(desireddvalue)) {
				Value.click();
			}
		}

	}

	@Step("Static_Dropdown_Select_Any_Option_By_Value")
	public void Static_Dropdown_Select_Any_Value(WebElement dropdownlement, String searchvalue, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		expwait.until(ExpectedConditions.visibilityOf(dropdownlement));
		Select sc = new Select(dropdownlement);
		sc.selectByValue(searchvalue);
	}

	@Step("Static_Dropdown_Select_Any_Option_By_Index")
	public void Static_Dropdown_Select_Any_Value(WebElement dropdownlement, int searchindex, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		expwait.until(ExpectedConditions.visibilityOf(dropdownlement));
		Select sc = new Select(dropdownlement);
		sc.selectByIndex(searchindex);
	}

	@Step("Static_Dropdown_Select_Any_Option_By_Visible_Text")
	public void Static_Dropdown_Select_Any_Value(String searchtext, WebElement dropdownlement, int timeout) {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		expwait.until(ExpectedConditions.visibilityOf(dropdownlement));
		Select sc = new Select(dropdownlement);
		sc.selectByVisibleText(searchtext);
	}

	@Step("Static_Dropdown_Validate_All_Values")
	public void Static_Dropdown_Validate_All_Values(WebElement dropdownlement, List<WebElement> dropdownoptions,
			int timeout) throws IOException {
		WebDriverWait expwait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		expwait.until(ExpectedConditions.visibilityOf(dropdownlement));
		Select sc = new Select(dropdownlement);
		for (WebElement option : dropdownoptions) {
			String searchtext = option.getText();
			sc.selectByVisibleText(searchtext);
		}

	}

	@Step("Actions_Mouse_Over")
	public void Actions_Mouse_Over(WebElement element, int timeout) {
		act.moveToElement(element).build().perform();
	}

	@Step("Actions_SendKeys_UpperCase")
	public void Actions_SendKeys_UpperCase(WebElement textboxelement, String texttoenter, int timeout) {
		act.moveToElement(textboxelement).click().keyDown(Keys.SHIFT).sendKeys(texttoenter).keyUp(Keys.SHIFT)
				.keyDown(Keys.ENTER).keyUp(Keys.ENTER).build().perform();
	}

	@Step("Actions_Double_Click")
	public void Actions_Double_Click(WebElement Element, int timeout) {
		act.moveToElement(Element).doubleClick().build().perform();

	}

	@Step("Actions_Right_Click")
	public void Actions_Right_Click(WebElement Element, int timeout) {
		act.moveToElement(Element).contextClick().build().perform();
	}

	@Step("Actions_Drag_Drop")
	public void Actions_Drag_Drop(WebElement sourceelement, WebElement destinationelement, int timeout) {
		act.dragAndDrop(sourceelement, destinationelement).build().perform();
	}

	@Step("Actions_Open_Link_New_Tab")
	public void Actions_Open_Link_New_Tab(List<WebElement> hylinks, int timeout) {
		int count = hylinks.size();
		for (int i = 0; i < count; i++) {
			act.moveToElement(hylinks.get(i)).keyDown(Keys.CONTROL).click().keyUp(Keys.CONTROL).build().perform();
		}
	}

	@Step("handle_popup_click")
	public void handle_popup_click(WebElement Element, int timeout) {
		try {
			Click_Element(Element, timeout);
		} catch (TimeoutException e) {
			System.out.println("Pop Up Not Found Hence Progressing Ahead");
			e.printStackTrace();
		} finally {
			System.out.println("Pop Up Handled");
		}
	}

	@Step("validate_broken_links")
	public void validate_broken_links(String logFile) throws IOException {
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> window = windows.iterator();
		window.next();
		while (window.hasNext()) {
			driver.switchTo().window(window.next());
			String title = driver.getTitle();
			title = Utility_Methods.pagename(title);
			SaveTextInWordFile(logFile, title);
			SaveImageInWordFile(logFile);
		}
	}

	@Step("TakeScreenshot")
	public String TakeScreenshot() throws IOException {

		String ScreenShotName = Utility_Methods.generateRandomAlphanumericString();
		File screenshotsource = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		File screenshotfile = new File(logDir + "\\" + ScreenShotName + ".png");

		String ss = screenshotfile.getPath();

		Files.copy(screenshotsource, screenshotfile);

		byte[] screenshotBytes = java.nio.file.Files.readAllBytes(Paths.get(ss));

		//Allure.addAttachment("screenshot", "image/png", screenshotBytes);

		return ss;
	}

	@Step("CreateWordLogFile")
	public void CreateWordLogFile(String FileName) throws IOException {
		FileOutputStream out = new FileOutputStream(logDir + "\\" + FileName + ".docx");
		XWPFDocument document = new XWPFDocument();
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText(
				"::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"
						+ FileName
						+ "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		run.setBold(true);
		run.setColor("008000");
		document.write(out);
		document.close();
	}

	@Step("SaveTextInWordFile")
	public void SaveTextInWordFile(String FileName, String TextToWrite) throws IOException {
		FileInputStream fis = new FileInputStream(logDir + "\\" + FileName + ".docx");
		XWPFDocument document = new XWPFDocument(fis);
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText("--------------------------------------------------------------------------------------------"
				+ TextToWrite
				+ "--------------------------------------------------------------------------------------------");
		run.setBold(true);
		run.setColor("008000");
		FileOutputStream out = new FileOutputStream(logDir + "\\" + FileName + ".docx");
		document.write(out);
		document.close();
	}

	@Step("SaveImageInWordFile")
	public void SaveImageInWordFile(String FileName) throws IOException {
		FileInputStream fis = new FileInputStream(logDir + "\\" + FileName + ".docx");
		XWPFDocument document = new XWPFDocument(fis);
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		String imagePath = TakeScreenshot();
		// System.out.println(imagePath);

		try {
			byte[] imgBytes = java.nio.file.Files.readAllBytes(Paths.get(imagePath));
			run.addPicture(java.nio.file.Files.newInputStream(Paths.get(imagePath)), Document.PICTURE_TYPE_PNG,
					imagePath, Units.toEMU(1000), Units.toEMU(500));
		} catch (IOException | InvalidFormatException e) {
			e.printStackTrace();
		}
		FileOutputStream out = new FileOutputStream(logDir + "\\" + FileName + ".docx");
		document.write(out);
		document.close();
	}

}
