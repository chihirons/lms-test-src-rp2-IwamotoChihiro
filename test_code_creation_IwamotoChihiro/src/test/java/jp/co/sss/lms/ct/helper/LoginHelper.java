package jp.co.sss.lms.ct.helper;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

public class LoginHelper {

	private WebDriverUtils webUtils;

	public LoginHelper(WebDriverUtils webUtils) {
		this.webUtils = webUtils;
	}

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

		WebElement loginIdElement = webUtils.findElementById(idFieldLoginId);
		WebElement passwordElement = webUtils.findElementById(idFieldPassword);
		WebElement submitButtonElement = webUtils.findElementByClassName(classFieldSubmitBuutton);

		webUtils.clearElement(loginIdElement);
		webUtils.enterElement(loginIdElement, userIdString);
		webUtils.clearElement(passwordElement);
		webUtils.enterElement(passwordElement, failPassword);
		webUtils.clickButton(submitButtonElement);

		// ログイン後の画面描画待ち処理
		String classFieldPageMarkerString = "active";
		visibilityTimeout(By.className(classFieldPageMarkerString), timeOutSeconds);
	}
}
