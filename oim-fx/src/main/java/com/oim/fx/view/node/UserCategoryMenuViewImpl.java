package com.oim.fx.view.node;

import com.oim.core.business.sender.UserCategorySender;
import com.oim.core.business.service.UserService;
import com.oim.core.business.view.FindView;
import com.oim.core.common.app.view.UserCategoryMenuView;
import com.only.common.result.Info;
import com.only.fx.common.component.OnlyMenuItem;
import com.only.net.action.Back;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.general.annotation.parameter.Define;
import com.onlyxiahui.im.bean.UserCategory;
import com.onlyxiahui.net.data.action.DataBackActionAdapter;

import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * @author XiaHui
 * @date 2017年9月4日 下午5:22:57
 */
public class UserCategoryMenuViewImpl extends AbstractView implements UserCategoryMenuView {

	Alert information = new Alert(AlertType.INFORMATION);
	TextInputDialog textInput = new TextInputDialog("");

	private ContextMenu menu = new ContextMenu();
	private OnlyMenuItem addCategoryMenuItem = new OnlyMenuItem();
	private OnlyMenuItem findUserMenuItem = new OnlyMenuItem();
	private OnlyMenuItem updateCategoryNameMenuItem = new OnlyMenuItem();
	UserCategory userCategory;

	public UserCategoryMenuViewImpl(AppContext appContext) {
		super(appContext);
		initMenu();
		initEvent();
	}

	private void initMenu() {

		information.initModality(Modality.APPLICATION_MODAL);
		information.initOwner(menu);
		information.getDialogPane().setHeaderText(null);

		textInput.setTitle("输入分组");
		textInput.setContentText("名称:");
		textInput.getEditor().setText("");

		addCategoryMenuItem.setText("新建分组");
		findUserMenuItem.setText("查找用户");
		updateCategoryNameMenuItem.setText("重命名分组");

		menu.getItems().add(addCategoryMenuItem);
		menu.getItems().add(findUserMenuItem);
		menu.getItems().add(updateCategoryNameMenuItem);
	}

	private void initEvent() {

		addCategoryMenuItem.setOnAction(a -> {
			clearData();
			textInput.showAndWait().ifPresent(response -> {
				if (null == response || response.isEmpty()) {
				} else {
					addUserCategory(response);
				}
			});
		});

		findUserMenuItem.setOnAction(a -> {
			FindView findView = appContext.getSingleView(FindView.class);
			findView.selectedTab(0);
			findView.setVisible(true);
		});

		updateCategoryNameMenuItem.setOnAction(a -> {
			textInput.getEditor().setText(null == userCategory ? "" : userCategory.getName());
			textInput.showAndWait().ifPresent(response -> {
				if (null == response || response.isEmpty()) {
				} else {
					updateUserCategory(response);
				}
			});
		});

	}

	@Override
	public void setUserCategory(UserCategory userCategory) {
		this.userCategory = userCategory;
	}

	@Override
	public void show(Window ownerWindow, double anchorX, double anchorY) {
		menu.show(ownerWindow, anchorX, anchorY);
	}

	public void showPrompt(String text) {
		information.getDialogPane().setContentText(text);
		information.showAndWait();
	}

	public void clearData() {
		textInput.getEditor().setText("");
	}

	public void addUserCategory(String name) {
		if (null != name && !"".equals(name)) {

			DataBackActionAdapter action = new DataBackActionAdapter() {

				@Override
				public void lost() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							showPrompt("添加失败！");
						}
					});
				}

				@Override
				public void timeOut() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							showPrompt("添加失败！");
						}
					});
				}

				@Back
				public void back(Info info, @Define("userCategory") UserCategory userCategory) {

					if (info.isSuccess()) {
						UserService userService = appContext.getService(UserService.class);
						userService.addUserCategory(userCategory);
					} else {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								showPrompt("添加失败！");
							}
						});
					}
				}
			};

			UserCategorySender uch = this.appContext.getSender(UserCategorySender.class);
			uch.addUserCategory(name, action);
		}
	}

	public void updateUserCategory(String name) {
		if (null != userCategory && null != name && !"".equals(name)) {

			DataBackActionAdapter action = new DataBackActionAdapter() {

				@Override
				public void lost() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							showPrompt("修改失败！");
						}
					});
				}

				@Override
				public void timeOut() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							showPrompt("修改失败！");
						}
					});
				}

				@Back
				public void back(Info info, @Define("userCategory") UserCategory userCategory) {

					if (info.isSuccess()) {
						UserService userService = appContext.getService(UserService.class);
						userService.updateUserCategory(userCategory);
					} else {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								showPrompt("修改失败！");
							}
						});
					}
				}
			};

			String userCategoryId = userCategory.getId();

			UserCategorySender uch = this.appContext.getSender(UserCategorySender.class);
			uch.updateUserCategoryName(userCategoryId, name, action);
		}
	}

	@Override
	public void show(Node anchor, double screenX, double screenY) {
		menu.show(anchor, screenX, screenY);
	}

	@Override
	public void show(Node anchor, Side side, double dx, double dy) {
		menu.show(anchor, side, dx, dy);
	}
}
