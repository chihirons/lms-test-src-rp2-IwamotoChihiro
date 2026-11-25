package jp.co.sss.lms.ct.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

public class CourseDetailPage {

	private final WebDriverUtils driverUtils = new WebDriverUtils();

	/**
	 * コース詳細ページと判断するためのメッセージ取得
	 * @author 岩本 知寛
	 * @return ログイン完了後画面の見出し
	 */
	public String getExpectedText() {
		String classNameFieldPageMarker = "active";
		int timeOutSeconds = 5;

		WebDriverUtils.visibilityTimeout(By.className(classNameFieldPageMarker), timeOutSeconds);

		return driverUtils.getText(driverUtils.findElementByClassName(classNameFieldPageMarker));
	}

	/**
	 * アコーディオン表示をすべて展開する。
	 * @author 岩本 知寛
	 * @return ログイン完了後画面の見出し
	 */
	public void openAllAccordions() {
		String idFieldOpenButton = "open-all-panel";
		int timeOutSeconds = 5;

		WebDriverUtils.visibilityTimeout(By.id(idFieldOpenButton), timeOutSeconds);
		driverUtils.clickButton(driverUtils.findElementById(idFieldOpenButton));
	}

	/**
	 * コース詳細のリスト内から特定の名称が含まれる要素を返す
	 * なお、最初に見つけた列の要素一覧を返す。
	 * @author 岩本 知寛
	 * @param 取得したいデータ(提出済み/未提出 or 試験有)
	 * @return ターゲット情報を含む最初に見つかったコースの情報
	 */
	public List<WebElement> getDailySectionData(String target) {
		String cssSelecterCouresList = ".panel-body";
		String cssSelecterCoures = "table > tbody > tr";
		String splitTag = "\\s+";
		int timeOut = 5;

		List<WebElement> matchElement = new ArrayList<>();

		WebDriverUtils.visibilityTimeout(By.cssSelector(cssSelecterCouresList), timeOut);

		//コース一覧をすべて取得
		List<WebElement> coursePanelList = driverUtils.findAllElementsByCssSelector(cssSelecterCouresList);

		searchLoop: for (WebElement coursePanels : coursePanelList) {

			List<WebElement> courseList = coursePanels.findElements(By.cssSelector(cssSelecterCoures));

			for (WebElement course : courseList) {

				String[] courseArray = course.getText().trim().split(splitTag);
				if (courseArray.length >= 4) {
					if (courseArray[2].equals(target) || courseArray[3].equals(target)) {
						matchElement.add(course);
						break searchLoop;
					}
				}
			}
		}
		return matchElement;
	}

	/**
	 * 未提出の詳細ボタンを押下
	 * @author 岩本 知寛
	 * @param コース一覧から検索したいメッセージを指定
	 */
	public void clickDetailButton(String target) {
		int timeOut = 5;
		String cssSelecterSubmit = "input[type='submit'][value='詳細']";

		//1行分のコース一覧以外取得しないまた、配列のため0開始
		int getDataListNo = 0;

		WebElement detailButton = getDailySectionData(target).get(getDataListNo)
				.findElement(By.cssSelector(cssSelecterSubmit));

		driverUtils.clickButton(detailButton);
		WebDriverUtils.pageLoadTimeout(timeOut);
	}

}
