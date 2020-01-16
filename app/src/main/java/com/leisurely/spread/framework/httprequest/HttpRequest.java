package com.leisurely.spread.framework.httprequest;

/**
 * 网络请求对象规范
 * @author xcl
 * create at 2015/12/3 16:05
 */
public interface HttpRequest {
	/**
	 * 网络请求
	 * @param url http网络地址
	 * @param params 请求参数
	 * @param callback 请求回调
	 */
	public void onRequest(String request, String url, HttpRequestParams params, HttpCallback callback);

	/**
	 * 网络请求
	 * @param url http网络地址
	 * @param params 请求参数
	 * @param tag 请求标记
	 * @param callback 请求回调
	 */
	public void onRequest(String request, String url, HttpRequestParams params, String tag, HttpCallback callback);

	/**
	 *网络请求
	 * @param url http网络地址
	 * @param params 请求参数
	 * @param tag 请求标记
	 * @param callback 请求回调
     * @param filePath 文件路径
     */
	public void onRequest(String request, String url, HttpRequestParams params, String tag, HttpCallback callback, String filePath);

}
