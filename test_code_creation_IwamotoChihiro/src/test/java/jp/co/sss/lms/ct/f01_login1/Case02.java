package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.DateTimeUtils;
import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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

	/**
	 * ログイン処理の共通化
	 * @author 岩本 知寛
	 */
	void dispLoginScreen() {
		String loginPageTitle = "ログイン | LMS";
		goTo("http://localhost:8080/lms/");
		assertEquals(loginPageTitle, (new WebDriverUtils()).getPageTaitle());
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		dispLoginScreen();
		// ログイン画面をLMSプロジェクト直下のevidenceフォルダに保存 
		getEvidence(
				new Object() {
				}, (new DateTimeUtils()).createTimestampSuffix());
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		WebDriverUtils webUtils = new WebDriverUtils();

		String idFieldLoginId = "loginId";
		String idFieldPassword = "password";
		String classFieldSubmitBuutton = "btn-primary";
		String classFieldLoginFaildMsg = ".help-inline.error";
		String userIdString = "StudentAA01";
		String failPassword = "NoSuchUserTestPass01";
		int timeOutSeconds = 5;

		dispLoginScreen();

		//描画待ち
		visibilityTimeout(By.id(idFieldLoginId), timeOutSeconds);
		visibilityTimeout(By.id(idFieldPassword), timeOutSeconds);
		visibilityTimeout(By.className(classFieldSubmitBuutton), timeOutSeconds);

		WebElement loginIdElement = webUtils.findElementById(idFieldLoginId);
		WebElement passwordElement = webUtils.findElementById(idFieldPassword);
		WebElement submitButtonElement = webUtils.findElementByClassName(classFieldSubmitBuutton);

		webUtils.clearElement(loginIdElement);
		webUtils.enterElement(loginIdElement, userIdString);
		webUtils.clearElement(passwordElement);
		webUtils.enterElement(passwordElement, failPassword);
		webUtils.clickButton(submitButtonElement);

		// ログインエラー画面のチェック
		String errorMsgString = "* ログインに失敗しました。";
		String loginPageTitle = "ログイン | LMS";
		visibilityTimeout(By.cssSelector(classFieldLoginFaildMsg), timeOutSeconds);
		WebElement faildMsgElement = webUtils.findElementByCssSelector(classFieldLoginFaildMsg);
		assertEquals(errorMsgString, webUtils.getText(faildMsgElement));
		assertEquals(loginPageTitle, webUtils.getPageTaitle());
		getEvidence(
				new Object() {
				}, (new DateTimeUtils()).createTimestampSuffix());
	}

}
