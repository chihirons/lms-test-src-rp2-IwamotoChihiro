package jp.co.sss.lms.ct.pages;

import org.openqa.selenium.By;

import jp.co.sss.lms.ct.util.WebDriverUtils;

public class SectionDetailPage {

	private final WebDriverUtils driverUtils = new WebDriverUtils();

	/**
	 * セクション詳細画面の表示されたことを判断するメッセージを取得
	 * @author 岩本 知寛
	 * @return 画面の名前
	 */
	public String getExpectedText() {
		String classFeildPageMarker = "active";
		int timeOutSeconds = 5;

		WebDriverUtils.visibilityTimeout(By.className(classFeildPageMarker), timeOutSeconds);
		return driverUtils.getText(driverUtils.findElementByClassName(classFeildPageMarker));
	}

	/**
	 * ボタン名と一致するSubmitButtonを探しクリックする
	 * @author 岩本 知寛
	 * @param ボタンに表示されている名前を指定
	 */
	public void clickSubmitButton(String buttonName) {
		int timeOut = 5;
		String cssSelecterDailyReport = "input[type='submit'][value='" + buttonName + "']";

		WebDriverUtils.visibilityTimeout(By.cssSelector(cssSelecterDailyReport), timeOut);
		driverUtils.clickButton(driverUtils.findElementByCssSelector(cssSelecterDailyReport));
		WebDriverUtils.pageLoadTimeout(timeOut);
	}

	/**
	 * 提出済み時に表示されるボタンの表示名を返す
	 * @author 岩本 知寛
	 * @param レポート名
	 * @return 表示されているボタンの表示名
	 */
	public String getReportSubmittedButtonName(String reportName) {
		int timeOut = 5;
		String cssSelecterDailyReport = "input[value='提出済み" + reportName + "を確認する']";

		WebDriverUtils.visibilityTimeout(By.cssSelector(cssSelecterDailyReport), timeOut);
		return driverUtils.findElementByCssSelector(cssSelecterDailyReport).getAttribute("value");
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
