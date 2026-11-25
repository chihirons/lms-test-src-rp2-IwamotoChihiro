package jp.co.sss.lms.ct.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

public class UserDetailPage {

	private final WebDriverUtils driverUtils = new WebDriverUtils();

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
	 * レポート一覧の取得
	 * @author 岩本 知寛
	 * @return レポート一覧画面のデータ一覧を返却
	 */
	public WebElement getReportWiewList() {
		//ユーザー詳細画面の表示内容をすべて取得
		String cssSelecterPageDataList = "#main.main2clm";
		String tagFieldH3Marker = "h3";

		//h3タグの値が「レポート」の次に出てくるテーブル要素を取得
		String targetList = "//h3[contains(text(), 'レポート')]/following-sibling::table[1]";
		int timeOutSeconds = 5;

		WebElement result = null;

		WebDriverUtils.visibilityTimeout(By.cssSelector(cssSelecterPageDataList), timeOutSeconds);
		WebDriverUtils.visibilityTimeout(By.cssSelector(tagFieldH3Marker), timeOutSeconds);

		WebElement pageDataElements = driverUtils.findElementByCssSelector(cssSelecterPageDataList);
		result = pageDataElements.findElement(By.xpath(targetList));

		return result;
	}

	/**
	 * 週報のレポート詳細を表示
	 * @author 岩本 知寛
	 */
	public void clickWeekliyReportDetailButton() {
		//週報が1つしかない状態の動作確認
		String targetList = ".//tr[td[2][normalize-space()='週報【デモ】']]";
		String cssSelecterButton = "input[value='詳細']";
		int timeOut = 5;

		//週報の要素を取得
		WebElement detailPageButtonElement = getReportWiewList().findElement(By.xpath(targetList));
		WebDriverUtils.scrollBy(driverUtils.getScreenPixelY(detailPageButtonElement));

		//週報要素配下のボタン要素を取得し実行
		driverUtils.clickButton(detailPageButtonElement.findElement(By.cssSelector(cssSelecterButton)));
		WebDriverUtils.pageLoadTimeout(timeOut);
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
