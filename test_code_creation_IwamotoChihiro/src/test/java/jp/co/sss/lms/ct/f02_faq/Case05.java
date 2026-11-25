package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import jp.co.sss.lms.ct.helper.LoginHelper;
import jp.co.sss.lms.ct.pages.FaqPage;
import jp.co.sss.lms.ct.util.DateTimeUtils;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

	private final LoginHelper loginHelper = new LoginHelper();
	private final FaqPage faqPage = new FaqPage();
	private final DateTimeUtils dateTimeUtils = new DateTimeUtils();

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

		loginHelper.showPage();
		assertThat("ログイン画面アクセス失敗", loginHelper.getCurrentPageTaitle(), equalTo(loginPageTitle));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		String urlPath = "/lms/course/detail";
		String pageMarker = "コース詳細";
		String loginUserMsg = "ようこそ受講生ＡＡ１さん";

		loginHelper.studentLoginSuccess();

		assertThat("URLのパス異常", loginHelper.getLoginUriPath(), equalTo(urlPath));
		assertThat("コース画面表示失敗", loginHelper.getExpectedText(), equalTo(pageMarker));
		assertThat("ログインユーザー不一致", loginHelper.getExpectedLoginUserName(), equalTo(loginUserMsg));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//データ照合用
		String urlPath = "/lms/help";
		String pageMarker = "ヘルプ";

		//画面遷移処理
		faqPage.clickFunctionButton();
		faqPage.clickHelpButton();

		assertThat("URLのパス異常", faqPage.getCurrentPageUriPath(), equalTo(urlPath));
		assertThat(pageMarker + "画面表示失敗", faqPage.getExpectedHelpText(), equalTo(pageMarker));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//データ照合用
		String urlPath = "/lms/faq";
		String pageMarker = "よくある質問";

		//「よくある質問」ページの表示
		faqPage.clickFaqButton();
		faqPage.showPage();

		assertThat("URLのパス異常", faqPage.getCurrentPageUriPath(), equalTo(urlPath));
		assertThat(pageMarker + "画面表示失敗", faqPage.getExpectedFaqText(), equalTo(pageMarker));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		String serchWord = "研修";
		int searchResultCount = 2;
		String questionOne = "Q.助成金書類の作成方法が分かりません";
		int ArrayNoZero = 0;
		String questionTwo = "Q.研修の申し込みはどのようにすれば良いですか？";
		int ArrayNoTwo = 2;

		//キーワード検索実施
		faqPage.enterSearchKeyword(serchWord);
		faqPage.clickSearchButton();

		//検索結果を昇順に並び替え
		faqPage.sortAscending();

		assertThat("検索件数不一致", faqPage.getSearchResultCount(), equalTo(searchResultCount));

		//検索結果は配列で持っており偶数[0,2,4..]にクエスチョンの結果が含まれる。
		List<String> questions = faqPage.getSearchResults();
		assertThat("検索結果不一致", questions.get(ArrayNoZero), equalTo(questionOne));
		assertThat("検索結果不一致", questions.get(ArrayNoTwo), equalTo(questionTwo));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		String questionOne = "Q.助成金書類の作成方法が分かりません";
		int ArrayNoZero = 0;
		String questionTwo = "Q.研修の申し込みはどのようにすれば良いですか？";
		int ArrayNoTwo = 2;

		faqPage.clickClearButton();

		//検索結果は配列で持っており偶数[0,2,4..]にクエスチョンの結果が含まれる。
		List<String> questions = faqPage.getSearchResults();
		assertThat("検索結果不一致", questions.get(ArrayNoZero), equalTo(questionOne));
		assertThat("検索結果不一致", questions.get(ArrayNoTwo), equalTo(questionTwo));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

}
