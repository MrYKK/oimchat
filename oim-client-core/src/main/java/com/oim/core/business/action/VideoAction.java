package com.oim.core.business.action;

import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.constant.VideoConstant;
import com.oim.core.business.manager.VideoManager;
import com.oim.core.business.sender.UserSender;
import com.oim.core.business.sender.VideoSender;
import com.only.net.action.Back;
import com.onlyxiahui.net.data.action.DataBackAction;
import com.onlyxiahui.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;
import com.onlyxiahui.general.annotation.action.ActionMapping;
import com.onlyxiahui.general.annotation.action.MethodMapping;
import com.onlyxiahui.general.annotation.parameter.Define;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.AddressData;

/**
 * 描述：
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午9:31:55
 * @version 0.0.1
 */
@ActionMapping(value = "1.502")
public class VideoAction extends AbstractAction {

	public VideoAction(AppContext appContext) {
		super(appContext);
	}

	@MethodMapping(value = "1.2.0004")
	public void getRequest(@Define("sendUserId")String sendUserId) {
		final UserDataBox ub=appContext.getBox(UserDataBox.class);
		UserData userData = ub.getUserData(sendUserId);// 先从好友集合里面获取用户信息，如果用户不是好友，那么从服务器下载用户信息
		if (null == userData) {// 为null说明发送信息的不在好友列表，那么就要从服务器获取发送信息用户的信息了
			
			DataBackAction dataBackAction = new DataBackActionAdapter() {
				@Back
				public void back(@Define("userData") UserData userData) {
					ub.putUserData(userData);
					showGetVideoFrame(userData);
				}
			};
			UserSender uh = this.appContext.getSender(UserSender.class);
			uh.getUserDataById(sendUserId, dataBackAction);
		} else {
			showGetVideoFrame(userData);
		}
	}

	private void showGetVideoFrame(UserData userData) {
		VideoManager vm = this.appContext.getManager(VideoManager.class);
		vm.showGetVideoFrame(userData);
	}

	@MethodMapping(value = "1.2.0005")
	public void getResponse(@Define("sendUserId") final String sendUserId,@Define("actionType")String actionType) {

		final VideoManager vm = this.appContext.getManager(VideoManager.class);
		if (VideoConstant.action_type_agree.equals(actionType)) {
			DataBackAction dataBackAction = new DataBackActionAdapter() {
				@Back
				public void back(@Define("videoAddress") AddressData videoAddress) {
					vm.getAgree(sendUserId, videoAddress);
				}
			};
			VideoSender vh = this.appContext.getSender(VideoSender.class);
			vh.getUserVideoAddress(sendUserId, dataBackAction);
		} else {
			vm.getShut(sendUserId);
		}
	}
	
	@MethodMapping(value = "1.2.0002")
	public void receivedVideoServerBack() {
		VideoManager vm = this.appContext.getManager(VideoManager.class);
		vm.receivedServerBack();
	}
}
