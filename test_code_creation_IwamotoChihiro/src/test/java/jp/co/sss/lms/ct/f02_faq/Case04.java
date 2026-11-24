package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.helper.LoginHelper;
import jp.co.sss.lms.ct.util.DateTimeUtils;
import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

	private final WebDriverUtils webDriverUtils = new WebDriverUtils();
	private final LoginHelper loginHelper = new LoginHelper(webDriverUtils);

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		String loginPageTitle = "ログイン | LMS";
		goTo("http://localhost:8080/lms/");
		assertEquals(loginPageTitle, (new WebDriverUtils()).getPageTaitle());

		getEvidence(
				new Object() {
				}, (new DateTimeUtils()).createTimestampSuffix());
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		String urlPath = "/lms/course/detail";
		String pageMarker = "コース詳細";
		String htmlTagFieldPageMarker = "active";
		String loginUserMsg = "ようこそ受講生ＡＡ１さん";
		String cssSelecterLoginUserMsg = "li > a > small";

		loginHelper.studentLoginSuccess();

		// ログインURLのホスト名含めた前方文字列を削除しフォルダパス比較
		assertThat(
				"URLのパス異常", URI.create(webDriverUtils.getCurrentUrl()).getPath(), equalTo(urlPath));

		String getText = webDriverUtils.getText(webDriverUtils.findElementByClassName(htmlTagFieldPageMarker));
		assertThat(
				"コース画面表示失敗", getText, equalTo(pageMarker));

		getText = webDriverUtils.getText(webDriverUtils.findElementByCssSelector(cssSelecterLoginUserMsg));
		assertThat(
				"ログインユーザー不一致", getText, equalTo(loginUserMsg));

		getEvidence(
				new Object() {
				}, (new DateTimeUtils()).createTimestampSuffix());
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//データ照合用
		String urlPath = "/lms/help";
		String pageMarker = "ヘルプ";
		String h2TagFieldPageMarker = "h2";

		//画面遷移用
		String tagFieldFanctionList = "機能";
		String getAttriguteLink = "href";
		String functionLinkPath = "#";
		String tagFieldHelpLinkMsg = pageMarker;

		//aタグリンク「機能」の取得と実行 ※これにより「ヘルプ」が表示されるようになる
		WebElement linkElement = webDriverUtils.findElementByLinkText(tagFieldFanctionList);
		Boolean isJudgeLink = linkElement.getAttribute(getAttriguteLink).contains(functionLinkPath);
		assertThat("機能ボタンが見つからない", isJudgeLink, equalTo(true));
		webDriverUtils.clickButton(linkElement);

		//aタグリンク「ヘルプ」の取得と実行
		linkElement = webDriverUtils.findElementByLinkText(tagFieldHelpLinkMsg);
		isJudgeLink = linkElement.getAttribute(getAttriguteLink).contains(urlPath);
		assertThat("heplボタンが見つからない", isJudgeLink, equalTo(true));
		webDriverUtils.clickButton(linkElement);

		assertThat(
				"URLのパス異常", URI.create(webDriverUtils.getCurrentUrl()).getPath(), equalTo(urlPath));

		String getText = webDriverUtils.getText(webDriverUtils.findElementByTagName(h2TagFieldPageMarker));
		assertThat(
				pageMarker + "画面表示失敗", getText, equalTo(pageMarker));

		getEvidence(
				new Object() {
				}, (new DateTimeUtils()).createTimestampSuffix());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//データ照合用
		String urlPath = "/lms/faq";
		String pageMarker = "よくある質問";
		String h2TagFieldPageMarker = "h2";

		//画面遷移用
		String getAttriguteLink = "href";
		String tagFieldHelpLinkMsg = pageMarker;
		int connectTimeout = 5;

		//aタグリンク「よくある質問」の取得と実行
		WebElement linkElement = webDriverUtils.findElementByLinkText(tagFieldHelpLinkMsg);
		Boolean isJudgeLink = linkElement.getAttribute(getAttriguteLink).contains(urlPath);
		assertThat("リンク「" + pageMarker + " 」が見つからない", isJudgeLink, equalTo(true));
		webDriverUtils.clickButton(linkElement);

		//アクティブページを新規ページへ切り替え
		webDriverUtils.switchWindow();
		visibilityTimeout(By.tagName(h2TagFieldPageMarker), connectTimeout);

		String getText = webDriverUtils.getText(webDriverUtils.findElementByTagName(h2TagFieldPageMarker));
		assertThat(
				pageMarker + "画面表示失敗", getText, equalTo(pageMarker));

		getEvidence(
				new Object() {
				}, (new DateTimeUtils()).createTimestampSuffix());
	}

}
