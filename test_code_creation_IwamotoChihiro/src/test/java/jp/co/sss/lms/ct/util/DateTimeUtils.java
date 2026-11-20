package jp.co.sss.lms.ct.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

	/**
	 * 現在時刻を取得する
	 * @author 岩本 知寛
	 * @return 現在時刻
	 */
	public Date getCurrentDateTime() {
		return new Date();
	}

	/**
	 * "yyyyMMdd_HHmmss"形式のタイムスタンプを作成する。
	 * @author 岩本 知寛
	 * @return タイムスタンプを返却する。
	 */
	public String createTimestampSuffix() {
		return (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(getCurrentDateTime());
	}
}
