package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import jp.co.sss.lms.ct.helper.LoginHelper;
import jp.co.sss.lms.ct.pages.CourseDetailPage;
import jp.co.sss.lms.ct.pages.NavigationBarPage;
import jp.co.sss.lms.ct.pages.ReportPage;
import jp.co.sss.lms.ct.pages.SectionDetailPage;
import jp.co.sss.lms.ct.pages.UserDetailPage;
import jp.co.sss.lms.ct.util.DateTimeUtils;
import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

	private final LoginHelper loginHelper = new LoginHelper();
	private final DateTimeUtils dateTimeUtils = new DateTimeUtils();
	private final CourseDetailPage courseDetail = new CourseDetailPage();
	private final SectionDetailPage sectionDetail = new SectionDetailPage();
	private final NavigationBarPage naviBar = new NavigationBarPage();
	private final ReportPage report = new ReportPage();
	private final UserDetailPage userDetail = new UserDetailPage();

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() throws InterruptedException {
		// 今回のサンプルは試験有->週報記入済み
		String clickCouresTarget = "試験有";
		String pageMarker = "セクション詳細";
		String urlPath = "/lms/section/detail";

		courseDetail.openAllAccordions();
		courseDetail.clickDetailButton(clickCouresTarget);

		//画面遷移時に描画に時間がかかりエラーとなることがあるため暫定対応
		Thread.sleep(1000);

		assertThat("セクション詳細画面表示失敗", sectionDetail.getExpectedText(), equalTo(pageMarker));
		assertThat("URLのパス異常", sectionDetail.getUriPath(), equalTo(urlPath));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		String sectionButtonValue = "提出済み週報【デモ】を確認する";
		String urlPath = "/lms/report/regist";
		String pageMarker1 = "週報【デモ】";
		String pageMarker2 = "学習理解度";
		String pageMarker3 = "報告レポート";

		//日報【デモ】を提出する ボタンを探し出し実行
		sectionDetail.clickSubmitButton(sectionButtonValue);

		assertThat("URLのパス異常", report.getUriPath(), equalTo(urlPath));
		assertThat("レポート登録画面表示失敗", report.getExpectedTitleText(), startsWith(pageMarker1));
		assertThat("レポート登録画面表示失敗", report.getExpectedItem1Text(), equalTo(pageMarker2));
		assertThat("レポート登録画面表示失敗", report.getExpectedItem2Text(), equalTo(pageMarker3));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		String pageMarker = "セクション詳細";
		String urlPath = "/lms/section/detail";
		String setTextGoalAchievement = "10";

		report.updateGoalAchievement(setTextGoalAchievement);

		//画面サイズによっては不要、ボタンが画面外にある場合の対応
		String scrollPixel = "100";
		WebDriverUtils.scrollBy(scrollPixel);

		report.clickRegistReport();

		assertThat("セクション詳細画面表示失敗", sectionDetail.getExpectedText(), equalTo(pageMarker));
		assertThat("URLのパス異常", sectionDetail.getUriPath(), equalTo(urlPath));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		String pageMarker = "ユーザー詳細";
		String urlPath = "/lms/user/detail";

		naviBar.clickUserDetailButton();

		assertThat("セクション詳細画面表示失敗", userDetail.getExpectedTitleText(), equalTo(pageMarker));
		assertThat("URLのパス異常", userDetail.getUriPath(), equalTo(urlPath));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() throws InterruptedException {
		String urlPath = "/lms/report/detail";
		String setTextGoalAchievement = "10";

		userDetail.clickWeekliyReportDetailButton();

		//画面遷移時に描画に時間がかかりエラーとなることがあるため暫定対応
		Thread.sleep(1000);

		assertThat("URLのパス異常", report.getUriPath(), equalTo(urlPath));
		assertThat("入力情報の一致確認", report.getGoalAchievementValue(), equalTo(setTextGoalAchievement));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

}
