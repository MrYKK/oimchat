package com.oim.core.business.action;

import com.oim.core.business.service.GroupService;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;
import com.onlyxiahui.general.annotation.action.ActionMapping;
import com.onlyxiahui.general.annotation.action.MethodMapping;
import com.onlyxiahui.general.annotation.parameter.Define;
import com.onlyxiahui.im.bean.GroupCategory;

/**
 * 描述：
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午9:31:55
 * @version 0.0.1
 */

@ActionMapping(value = "1.202")
public class GroupCategoryAction extends AbstractAction {

	public GroupCategoryAction(AppContext appContext) {
		super(appContext);
	}
	@MethodMapping(value = "1.1.0002")
	public void setGroupCategory(
			@Define("groupCategory") GroupCategory groupCategory) {
		GroupService groupService = appContext.getService(GroupService.class);
		groupService.addGroupCategory(groupCategory);;
	}
}
