package jp.co.sss.lms.ct.pages;

import org.openqa.selenium.By;

import jp.co.sss.lms.ct.util.WebDriverUtils;

public class NavigationBarPage {

	private final WebDriverUtils driverUtils = new WebDriverUtils();

	/**
	 * 上部ナビゲーションバーの「機能」ボタンを実行
	 * @author 岩本 知寛
	 */
	public void clickFunctionButton() {
		String tagFieldFanctionList = "機能";

		driverUtils.clickButton(driverUtils.findElementByLinkText(tagFieldFanctionList));
	}

	/**
	 * 「機能」ボタン配下の「ヘルプ」ボタンを実行
	 * @author 岩本 知寛
	 */
	public void clickHelpButton() {
		String tagFieldHelpLinkMsg = "ヘルプ";

		driverUtils.clickButton(driverUtils.findElementByLinkText(tagFieldHelpLinkMsg));
	}

	/**
	 * ログインユーザー名の取得(受講生)
	 * @author 岩本 知寛
	 * @return ログインユーザー名含むメッセージ
	 */
	public String getExpectedLoginUserName() {
		String cssSelecterLoginUserMsg = "li > a > small";
		return driverUtils.getText(driverUtils.findElementByCssSelector(cssSelecterLoginUserMsg));
	}

	/**
	 * ユーザー詳細ページへの移動
	 * @author 岩本 知寛
	 */
	public void clickUserDetailButton() {
		String cssSelecterLoginUserMsg = "li > a > small";
		int timeOutSeconds = 5;

		WebDriverUtils.visibilityTimeout(By.cssSelector(cssSelecterLoginUserMsg), timeOutSeconds);

		driverUtils.clickButton(driverUtils.findElementByCssSelector(cssSelecterLoginUserMsg));
	}

}
