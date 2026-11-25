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
import jp.co.sss.lms.ct.pages.ReportPage;
import jp.co.sss.lms.ct.pages.SectionDetailPage;
import jp.co.sss.lms.ct.util.DateTimeUtils;
import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	private final LoginHelper loginHelper = new LoginHelper();
	private final DateTimeUtils dateTimeUtils = new DateTimeUtils();
	private final CourseDetailPage courseDetail = new CourseDetailPage();
	private final SectionDetailPage sectionDetail = new SectionDetailPage();
	private final ReportPage report = new ReportPage();

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() throws InterruptedException {
		String clickCouresTarget = "未提出";
		String pageMarker = "セクション詳細";
		String urlPath = "/lms/section/detail";

		courseDetail.openAllAccordions();

		//画面外で画面情報が取得できない(ElementClickInterceptedException)
		//エラーの場合は下記スクロール「scrollBy」を有効化すること
		String scrollPixel = "500";
		WebDriverUtils.scrollBy(scrollPixel);

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
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		String sectionButtonValue = "日報【デモ】を提出する";
		String urlPath = "/lms/report/regist";
		String pageMarker1 = "日報【デモ】";
		String pageMarker2 = "報告レポート";

		//日報【デモ】を提出する ボタンを探し出し実行
		sectionDetail.clickSubmitButton(sectionButtonValue);

		assertThat("URLのパス異常", report.getUriPath(), equalTo(urlPath));
		assertThat("レポート登録画面表示失敗", report.getExpectedTitleText(), startsWith(pageMarker1));
		assertThat("レポート登録画面表示失敗", report.getExpectedItem1Text(), equalTo(pageMarker2));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		String setText = "TEST";
		String urlPath = "/lms/section/detail";
		String pageMarker = "セクション詳細";
		String reportName = "日報【デモ】";
		String dispButtonName = "提出済み" + reportName + "を確認する";

		report.enterNewDailyReport(setText);
		report.clickRegistReport();

		assertThat("セクション詳細画面表示失敗", sectionDetail.getExpectedText(), equalTo(pageMarker));
		assertThat("URLのパス異常", sectionDetail.getUriPath(), equalTo(urlPath));
		assertThat("ボタン名異常", sectionDetail.getReportSubmittedButtonName(reportName), equalTo(dispButtonName));

		getEvidence(new Object() {
		}, dateTimeUtils.createTimestampSuffix());
	}

}
