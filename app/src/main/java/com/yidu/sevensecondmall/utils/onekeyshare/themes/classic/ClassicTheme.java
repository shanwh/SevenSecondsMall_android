/*

 */

package com.yidu.sevensecondmall.utils.onekeyshare.themes.classic;

import android.content.Context;
import android.content.res.Configuration;

import com.yidu.sevensecondmall.utils.onekeyshare.OnekeyShareThemeImpl;
import com.yidu.sevensecondmall.utils.onekeyshare.themes.classic.land.EditPageLand;
import com.yidu.sevensecondmall.utils.onekeyshare.themes.classic.land.PlatformPageLand;
import com.yidu.sevensecondmall.utils.onekeyshare.themes.classic.port.EditPagePort;
import com.yidu.sevensecondmall.utils.onekeyshare.themes.classic.port.PlatformPagePort;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;

/** 九宫格经典主题样式的实现类*/
public class ClassicTheme extends OnekeyShareThemeImpl {

	/** 展示平台列表*/
	protected void showPlatformPage(Context context) {
		PlatformPage page;
		int orientation = context.getResources().getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			page = new PlatformPagePort(this);
		} else {
			page = new PlatformPageLand(this);
		}
		page.show(context, null);
	}

	/** 展示编辑界面*/
	protected void showEditPage(Context context, Platform platform, ShareParams sp) {
		EditPage page;
		int orientation = context.getResources().getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			page = new EditPagePort(this);
		} else {
			page = new EditPageLand(this);
		}
		page.setPlatform(platform);
		page.setShareParams(sp);
		page.show(context, null);
	}

}
