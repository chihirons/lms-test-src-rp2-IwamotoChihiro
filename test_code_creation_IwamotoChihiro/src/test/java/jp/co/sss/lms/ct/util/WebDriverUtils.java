package jp.co.sss.lms.ct.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

/**
 * Webドライバーユーティリティ
 * @author holy
 */
public class WebDriverUtils {

	/** Webドライバ */
	public static WebDriver webDriver;

	/**
	 * インスタンス取得
	 * @return Webドライバ
	 */
	public static void createDriver() {
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
		webDriver = new ChromeDriver();
	}

	/**
	 * インスタンス終了
	 */
	public static void closeDriver() {
		webDriver.quit();
	}

	/**
	 * 画面遷移
	 * @param url
	 */
	public static void goTo(String url) {
		webDriver.get(url);
		pageLoadTimeout(5);
	}

	/**
	 * ページロードタイムアウト設定
	 * @param second
	 */
	public static void pageLoadTimeout(int second) {
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(second));
	}

	/**
	 * 要素の可視性タイムアウト設定
	 * @param locater
	 * @param second
	 */
	public static void visibilityTimeout(By locater, int second) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(second));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locater));
	}

	/**
	 * 指定ピクセル分だけスクロール
	 * @param pixel
	 */
	public static void scrollBy(String pixel) {
		((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0," + pixel + ");");
	}

	/**
	 * 指定位置までスクロール
	 * @param pixel
	 */
	public static void scrollTo(String pixel) {
		((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0," + pixel + ");");
	}

	/**
	 * エビデンス取得
	 * @param instance
	 */
	public static void getEvidence(Object instance) {
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			String className = instance.getClass().getEnclosingClass().getSimpleName();
			String methodName = instance.getClass().getEnclosingMethod().getName();
			Files.move(tempFile, new File("evidence\\" + className + "_" + methodName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * エビデンス取得（サフィックスあり）
	 * @param instance
	 * @param suffix
	 */
	public static void getEvidence(Object instance, String suffix) {
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			String className = instance.getClass().getEnclosingClass().getSimpleName();
			String methodName = instance.getClass().getEnclosingMethod().getName();
			Files.move(tempFile, new File("evidence\\" + className + "_" + methodName + "_" + suffix + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 指定した要素が存在する画面表示位置の取得
	 * @author 岩本 知寛
	 * @return 要素の表示座標Y位置をPixelで返却
	 */
	public String getScreenPixelY(WebElement element) {
		Point location = element.getLocation();
		return String.valueOf(location.getY());
	}

	/**
	 * ページタイトルを取得する
	 * @author 岩本 知寛
	 * @return 現在表示されているページのタイトル
	 */
	public String getPageTaitle() {
		return webDriver.getTitle();
	}

	/**
	 * ページ内の一致するIDを持つ要素を返す
	 * @author 岩本 知寛
	 * @param target ID名
	 * @return 要素情報
	 */
	public WebElement findElementById(String target) {
		return webDriver.findElement(By.id(target));
	}

	/**
	 * ページ内の一致するクラス名を持つ要素を返す
	 * @author 岩本 知寛
	 * @param target class名
	 * @return 要素情報
	 */
	public WebElement findElementByClassName(String target) {
		return webDriver.findElement(By.className(target));
	}

	/**
	 * 指定されたCSSセレクタに一致する要素を返す
	 * @author 岩本 知寛
	 * @param target 検索対象のCSSセレクタ
	 *                (例: ".btn.btn-primary", "#submit-id")
	 * @return 要素情報
	 */
	public WebElement findElementByCssSelector(String target) {
		return webDriver.findElement(By.cssSelector(target));
	}

	/**
	 * 指定されたCSSセレクタに一致する要素を全て取得する
	 * @author 岩本 知寛
	 * @param target 検索対象のCSSセレクタ
	 *                (例: "[id^=xxxx_]", "[id$=_xxx]")
	 * @return 一致する全ての要素情報
	 */
	public List<WebElement> findAllElementsByCssSelector(String target) {
		return webDriver.findElements(By.cssSelector(target));
	}

	/**
	 * HTMLタグで指定された一致する先頭の要素を返す
	 * @author 岩本 知寛
	 * @param target タグ名 (例> h1, span)
	 * @return 要素情報
	 */
	public WebElement findElementByTagName(String target) {
		return webDriver.findElement(By.tagName(target));
	}

	/**
	 * aタグリンクのメッセージと一致した要素を返す(完全一致)
	 * @author 岩本 知寛
	 * @param target タグ名 (例> h1, span)
	 * @return 要素情報
	 */
	public WebElement findElementByLinkText(String target) {
		return webDriver.findElement(By.linkText(target));
	}

	/**
	 * 要素に値を入力する
	 * @author 岩本 知寛
	 * @param target     入力ターゲットの要素
	 * @param inputValue 要素へ入力するデータ
	 */
	public void enterElement(WebElement target, String inputValue) {
		target.sendKeys(inputValue);
	}

	/**
	 * 要素の値をクリアする
	 * @author 岩本 知寛
	 * @param target clear対象の要素
	 */
	public void clearElement(WebElement target) {
		target.clear();
	}

	/**
	 * 要素をクリックする
	 * @author 岩本 知寛
	 * @param target click対象の要素
	 */
	public void clickButton(WebElement target) {
		target.click();
	}

	/**
	 * 要素内のテキストを取得する
	 * @author 岩本 知寛
	 * @param target click対象の要素
	 * @return 要素内のテキストを返却する
	 */
	public String getText(WebElement target) {
		return target.getText();
	}

	/**
	 * 現在表示しているページのURLを返す。
	 * @author 岩本 知寛
	 * @return 現在表示中のページURL
	 */
	public String getCurrentUrl() {
		return webDriver.getCurrentUrl();
	}

	/**
	 * アクティブ画面切り替え
	 * @author 岩本 知寛
	 */
	public void switchWindow() {
		//現在の画面取得
		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);
	}

	/**
	 * URIパスの取得
	 * @author 岩本 知寛
	 * @return 現在表示中画面のURIファイルパス
	 */
	public String getCurrentUriPath() {
		//現在の画面取得
		return URI.create(webDriver.getCurrentUrl()).getPath();
	}

}
