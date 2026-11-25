package jp.co.sss.lms.ct.f01_login1;

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

import jp.co.sss.lms.ct.helper.LoginHelper;
import jp.co.sss.lms.ct.util.DateTimeUtils;
import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

	private final WebDriverUtils webDriverUtils = new WebDriverUtils();
	//	private final LoginHelper loginHelper = new LoginHelper(webDriverUtils);
	private final LoginHelper loginHelper = new LoginHelper();

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
		String classFieldPageMarker = "active";
		String loginUserMsg = "ようこそ受講生ＡＡ１さん";
		String cssSelecterLoginUserMsg = "li > a > small";

		loginHelper.studentLoginSuccess();

		// ログインURLのホスト名含めた前方文字列を削除しフォルダパス比較
		assertThat(
				"URLのパス異常", URI.create(webDriverUtils.getCurrentUrl()).getPath(), equalTo(urlPath));

		String getText = webDriverUtils.getText(webDriverUtils.findElementByClassName(classFieldPageMarker));
		assertThat(
				"コース画面表示失敗", getText, equalTo(pageMarker));

		getText = webDriverUtils.getText(webDriverUtils.findElementByCssSelector(cssSelecterLoginUserMsg));
		assertThat(
				"ログインユーザー不一致", getText, equalTo(loginUserMsg));

		getEvidence(
				new Object() {
				}, (new DateTimeUtils()).createTimestampSuffix());
	}

}
