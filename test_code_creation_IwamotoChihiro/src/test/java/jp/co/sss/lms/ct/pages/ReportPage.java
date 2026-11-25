package jp.co.sss.lms.ct.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

public class ReportPage {

	private final WebDriverUtils driverUtils = new WebDriverUtils();

	/************
	 * レポート登録ページ
	 ************/

	/**
	 * 表示項目名取得
	 * @author 岩本 知寛
	 * @return 表示項目名
	 */
	public String getExpectedItem1Text() {
		String tagFieldPageItemMarker = "legend";
		int timeOutSeconds = 5;

		WebDriverUtils.visibilityTimeout(By.tagName(tagFieldPageItemMarker), timeOutSeconds);

		return driverUtils.getText(driverUtils.findElementByTagName(tagFieldPageItemMarker));
	}

	/**
	 * 表示項目名取得 ※2つ目の要素がない画面だと要素取得に失敗する。
	 * @author 岩本 知寛
	 * @return 表示項目名
	 */
	public String getExpectedItem2Text() {
		String cssSelecterPageItemMarker = ".form-horizontal";
		String tagFieldPageItemMarker = "legend";
		int timeOutSeconds = 5;
		String resultText = "";
		int ArraySecond = 1;

		WebDriverUtils.visibilityTimeout(By.cssSelector(cssSelecterPageItemMarker), timeOutSeconds);
		List<WebElement> parents = driverUtils.findAllElementsByCssSelector(cssSelecterPageItemMarker);

		for (WebElement parent : parents) {
			List<WebElement> children = parent.findElements(By.tagName(tagFieldPageItemMarker));
			if (children.size() >= 2) {
				resultText = driverUtils.getText(children.get(ArraySecond));
			}
		}
		return resultText;
	}

	/**
	 * 日報/週報入力欄の要素を取得する
	 * @author 岩本 知寛
	 * @param enterTextBox テキストボックス番号 ※配列定義のため0が先頭
	 * @return 入力先のテキストボックス要素
	 */
	public WebElement getTextBox(int textBoxIndex) {
		String idFieldTextBox = "content_" + textBoxIndex;
		int timeOutSeconds = 5;

		WebDriverUtils.visibilityTimeout(By.id(idFieldTextBox), timeOutSeconds);

		return driverUtils.findElementById(idFieldTextBox);
	}

	/**
	 * 日報の入力
	 * @author 岩本 知寛
	 * @param 入力するテキスト
	 */
	public void enterNewDailyReport(String enterText) {
		WebElement dailyReportElement = getTextBox(0);

		driverUtils.clearElement(dailyReportElement);
		driverUtils.enterElement(dailyReportElement, enterText);
	}

	/**
	 * 週報の目標の達成度更新処理
	 * @author 岩本 知寛
	 * @param 入力するテキスト
	 */
	public void updateGoalAchievement(String enterText) {
		WebElement dailyReportElement = getTextBox(0);

		driverUtils.clearElement(dailyReportElement);
		driverUtils.enterElement(dailyReportElement, enterText);
	}

	/**
	 * 提出するボタンの押下
	 * @author 岩本 知寛
	 */
	public void clickRegistReport() {
		String classFieldRegistButton = "btn-primary";
		int timeOutSeconds = 5;

		WebDriverUtils.visibilityTimeout(By.className(classFieldRegistButton), timeOutSeconds);
		driverUtils.clickButton(driverUtils.findElementByClassName(classFieldRegistButton));
	}

	/************
	 * レポート詳細ページ
	 ************/

	/**
	 * 週報の目標の達成度の入力値取得
	 * @author 岩本 知寛
	 * @return 入力値
	 */
	public String getGoalAchievementValue() {
		//ユーザー詳細画面の表示内容をすべて取得
		String cssSelecterPageDataList = "#contents.clearfix";

		//「目標達成度」配下の値
		String targetList = "//th[contains(text(), '目標の達成度')]/following-sibling::td";
		int timeOutSeconds = 5;

		WebDriverUtils.visibilityTimeout(By.cssSelector(cssSelecterPageDataList), timeOutSeconds);
		WebElement pageDataElements = driverUtils.findElementByCssSelector(cssSelecterPageDataList);

		return driverUtils.getText(pageDataElements.findElement(By.xpath(targetList)));
	}

	/************
	 * 共通
	 ************/

	/**
	 * 見出し部分の取得
	 * @author 岩本 知寛
	 * @return 画面の名前
	 */
	public String getExpectedTitleText() {
		String tagFieldPageMarker = "h2";
		int timeOutSeconds = 5;

		WebDriverUtils.visibilityTimeout(By.tagName(tagFieldPageMarker), timeOutSeconds);

		return driverUtils.getText(driverUtils.findElementByTagName(tagFieldPageMarker));
	}

	/**
	 * URIパス取得
	 * @author 岩本 知寛
	 * @return URIパス
	 */
	public String getUriPath() {
		return driverUtils.getCurrentUriPath();
	}
}
