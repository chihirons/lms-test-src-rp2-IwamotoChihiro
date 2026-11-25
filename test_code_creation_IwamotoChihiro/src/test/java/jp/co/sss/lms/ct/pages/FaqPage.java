package jp.co.sss.lms.ct.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * よくある質問機能画面試験のPOM
 * @author 岩本 知寛
 */
public class FaqPage extends WebDriverUtils {

	/**
	 * 上部ナビゲーションバーの「機能」ボタンを実行
	 * @author 岩本 知寛
	 */
	public void clickFunctionButton() {
		String tagFieldFanctionList = "機能";

		clickButton(findElementByLinkText(tagFieldFanctionList));
	}

	/**
	 * 「機能」ボタン配下の「ヘルプ」ボタンを実行
	 * @author 岩本 知寛
	 */
	public void clickHelpButton() {
		String tagFieldHelpLinkMsg = "ヘルプ";

		clickButton(findElementByLinkText(tagFieldHelpLinkMsg));
	}

	/**
	 * URIパス取得
	 * @author 岩本 知寛
	 * @return 現在表示されているページのURIパス
	 */
	public String getCurrentPageUriPath() {
		return getCurrentUriPath();
	}

	/**
	 * ペルプページ遷移完了を判定するための文言取得
	 * @return ペルプページ遷移完了後画面の見出し
	 */
	public String getExpectedHelpText() {
		String h2TagFieldPageMarker = "h2";
		return getText(findElementByTagName(h2TagFieldPageMarker));
	}

	/**
	 * 「よくある質問」ボタンを実行
	 * @author 岩本 知寛
	 */
	public void clickFaqButton() {
		String tagFieldHelpLinkMsg = "よくある質問";

		clickButton(findElementByLinkText(tagFieldHelpLinkMsg));
	}

	/**
	 * 別ページに表示された画面の切り替え
	 * @author 岩本 知寛
	 */
	public void showPage() {
		String h2TagFieldPageMarker = "h2";
		int connectTimeout = 5;

		switchWindow();
		visibilityTimeout(By.tagName(h2TagFieldPageMarker), connectTimeout);
	}

	/**
	 * よくある質問ページ遷移完了を判定するための文言取得
	 * @author 岩本 知寛
	 * @return よくある質問ページ遷移完了後画面の見出し
	 */
	public String getExpectedFaqText() {
		String h2TagFieldPageMarker = "h2";
		return getText(findElementByTagName(h2TagFieldPageMarker));
	}

	/**
	 * キーワード検索のキーワード入力
	 * @author 岩本 知寛
	 * @param 入力キーワード
	 */
	public void enterSearchKeyword(String keyword) {
		String idFieldKeywordTextBox = "form";
		int timeOut = 5;

		visibilityTimeout(By.id(idFieldKeywordTextBox), timeOut);

		WebElement keywordTextBoxElement = findElementById(idFieldKeywordTextBox);
		clearElement(keywordTextBoxElement);
		enterElement(keywordTextBoxElement, keyword);
	}

	/**
	 * キーワードの検索
	 * @author 岩本 知寛
	 */
	public void clickSearchButton() {
		String typeFieldKeywordSerchButton = "input[type='submit'][value='検索']";
		int timeOut = 5;

		visibilityTimeout(By.cssSelector(typeFieldKeywordSerchButton), timeOut);

		WebElement searchButtonElement = findElementByCssSelector(typeFieldKeywordSerchButton);
		clickButton(searchButtonElement);
		pageLoadTimeout(timeOut);
	}

	/**
	 * キーワードの検索
	 * @author 岩本 知寛
	 */
	public void clickClearButton() {
		String typeFieldKeywordClearButton = "input[type='button'][value='クリア']";
		int timeOut = 5;

		visibilityTimeout(By.cssSelector(typeFieldKeywordClearButton), timeOut);

		WebElement searchButtonElement = findElementByCssSelector(typeFieldKeywordClearButton);
		clickButton(searchButtonElement);
		pageLoadTimeout(timeOut);
	}

	/**
	 * キーワードの検索結果の昇順切り替え
	 * @author 岩本 知寛
	 */
	public void sortAscending() {
		String classFieldToggle = "[class^='sorting_']";
		String field = "class";
		String descStatus = "sorting_desc";
		int timeOut = 5;

		visibilityTimeout(By.cssSelector(classFieldToggle), timeOut);

		WebElement toggle = findElementByCssSelector(classFieldToggle);
		String status = toggle.getAttribute(field);

		if (status != null && status.contains(descStatus)) {
			clickButton(toggle);
		}
		pageLoadTimeout(timeOut);
	}

	/**
	 * キーワード検索で表示されたデータを取得
	 * @author 岩本 知寛
	 * @return 表示されたクエスチョンのデータを返す。
	 */
	public List<WebElement> findSearchQuestionResults() {
		String idFieldSerchResult = "[id^=question-h]";

		return findAllElementsByCssSelector(idFieldSerchResult);
	}

	/**
	 * キーワード検索で表示されたデータを取得
	 * @author 岩本 知寛
	 * @return 表示されたアンサーのデータを返す。
	 */
	public List<WebElement> findSearchAnswerResults() {
		String idFieldSerchResult = "[id^=answer-h]";

		return findAllElementsByCssSelector(idFieldSerchResult);
	}

	/**
	 * キーワード検索で表示されたデータ件数を取得
	 * @author 岩本 知寛
	 * @return 検索された件数を返す。
	 */
	public int getSearchResultCount() {
		return findSearchQuestionResults().size();
	}

	/**
	 * キーワード検索で表示された文字列を返す。
	 * @author 岩本 知寛
	 * @return 検索された結果を返す。
	 */
	public List<String> getSearchResults() {
		List<WebElement> questions = findSearchQuestionResults();
		List<WebElement> answers = findSearchAnswerResults();

		List<String> searchResults = new ArrayList<>();

		for (int i = 0; i < questions.size(); i++) {
			searchResults.add(getText(questions.get(i)));
			searchResults.add(getText(answers.get(i)));
		}

		return searchResults;
	}

	/**
	 * カテゴリ名を選択する。
	 * @author 岩本 知寛
	 * @param 一致選択するカテゴリ名
	 */
	public void clickCategory(String category) {
		int timeOut = 5;

		WebElement getCategoryElement = findElementByLinkText(category);

		clickButton(getCategoryElement);
		pageLoadTimeout(timeOut);
	}

	/**
	 * クエスチョン２の回答を表示しメッセージを展開
	 * @author 岩本 知寛
	 */
	public void getQuestion2OpenAnswer() {
		//配列は0から始まるため
		String idFieldSerchResult = "question-h1";
		int timeOut = 5;

		visibilityTimeout(By.id(idFieldSerchResult), timeOut);
		WebElement toggle = findElementById(idFieldSerchResult);
		clickButton(toggle);

	}

}
