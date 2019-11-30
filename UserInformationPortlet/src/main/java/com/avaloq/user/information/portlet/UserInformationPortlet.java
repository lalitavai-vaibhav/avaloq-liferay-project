package com.avaloq.user.information.portlet;

import com.avaloq.user.information.constants.UserInformationPortletKeys;
import com.avaloq.user.information.dtos.UserRoleInfo;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vaibhav Khopade
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css", 
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=UserInformation", 
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + UserInformationPortletKeys.USERINFORMATION,
		"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user" 
		}, 

service = Portlet.class)
public class UserInformationPortlet extends MVCPortlet {
	 private static final Log LOG = LogFactoryUtil.getLog(UserInformationPortlet.class);

	@Reference
	JournalArticleService journalArticleLocalService;

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			// Exit. The portlet is available for logged in users only.
			return;
		}

		User user = themeDisplay.getRealUser();

		List<UserRoleInfo> userRoles = getUserRoleList(user, themeDisplay);

		List<JournalArticle> articlesList = getUsersWebContentList(user, themeDisplay);

		int articlesfound = articlesfound = articlesList.size();

		renderRequest.setAttribute("user", user);
		renderRequest.setAttribute("articlecount", articlesfound);
		renderRequest.setAttribute("articlList", articlesList);
		renderRequest.setAttribute("userRoles", userRoles);

		super.doView(renderRequest, renderResponse);
	}
	
	/**Get all roles of loggedin user
	 * 
	 * @param user
	 * @param themeDisplay
	 * @return  List<UserRoleInfo>
	 */

	private List<UserRoleInfo> getUserRoleList(User user, ThemeDisplay themeDisplay) {

		List<UserRoleInfo> userRoles = new ArrayList<>();

		List<Role> roles = user.getRoles();

		roles.forEach(rol -> {

			userRoles.add(new UserRoleInfo(rol.getTitle(themeDisplay.getLocale())));
		});

		java.util.List<UserGroupRole> userGroupRoleList = UserGroupRoleLocalServiceUtil
				.getUserGroupRoles(user.getUserId(), themeDisplay.getScopeGroupId());

		if (userGroupRoleList != null) {
			for (UserGroupRole userGroupRole : userGroupRoleList) {
				/* Get Role object based on userGroupRole.getRoleId() */
				Role role;
				try {
					role = RoleLocalServiceUtil.getRole(userGroupRole.getRoleId());
					userRoles.add(new UserRoleInfo(role.getTitle(themeDisplay.getLocale())));
				} catch (PortalException e) {
					LOG.debug("Error while getting user roles"+e.getMessage());
				}

			}
		}

		return userRoles;
	}

	/**
	 * Get all web content of loggedin user
	 * 
	 * @param user
	 * @param themeDisplay
	 * @return  List<JournalArticle>
	 */
	private List<JournalArticle> getUsersWebContentList(User user, ThemeDisplay themeDisplay) {
		List<JournalArticle> articlesList = new ArrayList<>();
		try {
			articlesList = journalArticleLocalService.getGroupArticles(themeDisplay.getScopeGroupId(), user.getUserId(),
					0, 0, true, themeDisplay.getLocale(), 0, 1000, null);
			
			
		} catch (PortalException e) {
			LOG.debug("Error while getting user web content articles"+e.getMessage());
		}

		return articlesList;

	}

}