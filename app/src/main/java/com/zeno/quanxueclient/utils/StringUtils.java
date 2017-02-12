package com.zeno.quanxueclient.utils;

public class StringUtils
{
	public static String filterHtmlUrl(String url)
	{
		return url.replace("index.html", "").replace("Index.html", "");
	}
}
