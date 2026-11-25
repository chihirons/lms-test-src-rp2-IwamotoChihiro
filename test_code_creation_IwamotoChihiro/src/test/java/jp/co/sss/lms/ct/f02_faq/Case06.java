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
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		String categorySelect = "【人材開発支援助成金】";
		int searchResultCount = 3;
		int ArrayNoZero = 0;
		String questionOne = "Q.セルフ・キャリアドック制度とは何か";
		int ArrayNoTwo = 2;
		String questionTwo = "Q.事業所が変わった場合、何かしら手続きをする必要がありますか？";
		int ArrayNoFour = 4;
		String questionThree = "Q.助成金書類の作成方法が分かりません";

		faqPage.clickCategory(categorySelect);

		//検索結果を昇順に並び替え
		faqPage.sortAscending();

		assertThat("検索件数不一致", faqPage.getSearchResultCount(), equalTo(searchResultCount));

		//検索結果は配列で持っており偶数[0,2,4..]にクエスチョンの結果が含まれる。
		List<String> questions = faqPage.getSearchResults();
		assertThat("検索結果不一致", questions.get(ArrayNoZero), equalTo(questionOne));
		assertThat("検索結果不一致", questions.get(ArrayNoTwo), equalTo(questionTwo));
		assertThat("検索結果不一致", questions.get(ArrayNoFour), equalTo(questionThree));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		String answerTwo = "A. 以前は変更申請の必要がございましたが、2020年4月～は変更届の必要がなくなりました。";
		int ArrayNoThree = 3;

		faqPage.getQuestion2OpenAnswer();
		assertThat("検索結果不一致", faqPage.getSearchResults().get(ArrayNoThree), equalTo(answerTwo));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

}
