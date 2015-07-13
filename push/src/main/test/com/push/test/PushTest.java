package com.push.test ;

import java.util.Date;

import org.json.JSONException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import push.api.PushSettings;
import push.api.PushTarget;
import push.api.PushTarget.Query;
import push.api.UmengAndroidApi;
import push.api.UmengIosApi;

public class PushTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		PushSettings.isProduct = false;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		testAndroid();
//		testIos();
	}
	
	private void testAndroid () throws Exception {
		Date date = new Date();
		date.setTime(date.getTime() + 3600000 * 24);;
		UmengAndroidApi.getInstance().sendGoApp(getAliasTarget(), "ticker", "title", "text", null, date);
//		UmengAndroidApi.getInstance().sendGoUrl(getAliasTarget(), "hah", "go to url", "go to url", "http://www.baidu.com", null);
//		UmengAndroidApi.getInstance().sendGoCustomMessage(getUniqueDeviceTarget(), "ccccc", null);
	}
	
	private void testIos () throws Exception {
		UmengIosApi.getInstance().send(getAliasTarget(), "test !!!", new Date());
	}

	public PushTarget getBroadcastTarget () throws JSONException {
		//所有用户
		return PushTarget.createBroadcast();
	}
	
	public PushTarget getUniqueDeviceTarget () {
		//设备唯一标识
		String deviceToken = "AjML-6gfyOPqnT4GIsz_LWLLn4w6NseVwV4hv3aji8IV";
		return PushTarget.createUniqueDevice(deviceToken);
	}
	
	public PushTarget getAliasTarget () {
		//用户id为123的用户
		String alias = "10041675";
		String aliasType = "userid";
		return PushTarget.createAlias(alias, aliasType);
	}
	
	public PushTarget getGroupTarget () throws JSONException {
		//所有tag为111并且版本号为1.0的用户
		Query query = Query.and().tag("starfans111").appVersion("1.0");
		return PushTarget.createGruop(query);
	}
}
