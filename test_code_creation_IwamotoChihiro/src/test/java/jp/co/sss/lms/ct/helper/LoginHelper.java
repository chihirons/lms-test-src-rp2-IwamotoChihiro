package jp.co.sss.lms.ct.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.pages.CourseDetailPage;
import jp.co.sss.lms.ct.pages.NavigationBarPage;
import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * ログイン/ログイン後の制御用POM
 * @author 岩本 知寛
 */
public class LoginHelper extends WebDriverUtils {

	NavigationBarPage navi = new NavigationBarPage();
	CourseDetailPage courseDetail = new CourseDetailPage();

	/**
	 * ログインページの表示
	 * @author 岩本 知寛
	 */
	public void showPage() {
		goTo("http://localhost:8080/lms/");
	}

	/**
	 * ログインページのページタイトルを返却する
	 * @author 岩本 知寛
	 * @return ページタイトルを返却
	 */
	public String getCurrentPageTaitle() {
		return getPageTaitle();
	}

	/**
	 * 初回ログイン済みの「StudentAA01」ユーザーでログインを実行する
	 * @author 岩本 知寛
	 */
	public void studentLoginSuccess() {
		String idFieldLoginId = "loginId";
		String idFieldPassword = "password";
		String classFieldSubmitBuutton = "btn-primary";
		String userIdString = "StudentAA01";
		String failPassword = "Student01";
		int timeOutSeconds = 5;

		//描画待ち
		visibilityTimeout(By.id(idFieldLoginId), timeOutSeconds);
		visibilityTimeout(By.id(idFieldPassword), timeOutSeconds);
		visibilityTimeout(By.className(classFieldSubmitBuutton), timeOutSeconds);

		WebElement loginIdElement = findElementById(idFieldLoginId);
		WebElement passwordElement = findElementById(idFieldPassword);
		WebElement submitButtonElement = findElementByClassName(classFieldSubmitBuutton);

		clearElement(loginIdElement);
		enterElement(loginIdElement, userIdString);
		clearElement(passwordElement);
		enterElement(passwordElement, failPassword);
		clickButton(submitButtonElement);

		// ログイン後の画面描画待ち処理
		String classFieldPageMarkerString = "active";
		visibilityTimeout(By.className(classFieldPageMarkerString), timeOutSeconds);
	}

	/**
	 * ログイン後のURIパス取得
	 * @author 岩本 知寛
	 * @return URIパス
	 */
	public String getLoginUriPath() {
		return getCurrentUriPath();
	}

	/**
	 * ログイン完了を判定するための文言取得
	 * @author 岩本 知寛
	 * @return ログイン完了後画面の見出し
	 */
	public String getExpectedText() {
		return courseDetail.getExpectedText();
	}

	/**
	 * ログインユーザー名の取得
	 * @author 岩本 知寛
	 * @return ログインユーザー名含むメッセージ
	 */
	public String getExpectedLoginUserName() {
		return navi.getExpectedLoginUserName();
	}
}
