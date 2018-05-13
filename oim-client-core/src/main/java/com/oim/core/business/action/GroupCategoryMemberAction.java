package com.oim.core.business.action;

import com.oim.core.business.service.GroupMemberService;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;
import com.onlyxiahui.general.annotation.action.ActionMapping;
import com.onlyxiahui.general.annotation.action.MethodMapping;
import com.onlyxiahui.general.annotation.parameter.Define;

/**
 * 
 * @author: XiaHui
 * @date 2018-01-18 16:21:59
 */
@ActionMapping(value = "1.203")
public class GroupCategoryMemberAction extends AbstractAction {

	public GroupCategoryMemberAction(AppContext appContext) {
		super(appContext);
	}

	@MethodMapping(value = "1.2.0002")
	public void updateGroupUserList(@Define("groupId") String groupId, @Define("userId") String userId) {
		GroupMemberService gms=appContext.getService(GroupMemberService.class);
		gms.updateGroupMemberList(groupId);
	}
}
