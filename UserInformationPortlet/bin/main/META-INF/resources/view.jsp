<%@ include file="/init.jsp"%>

<!-- Basic user information -->

<aui:form name="fm">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset label="User Information:">
			<aui:row>
				<liferay-ui:user-display markupView="lexicon" showUserDetails="true"
					showUserName="true" userId="<%=themeDisplay.getRealUserId()%>"
					userName="<%=themeDisplay.getRealUser().getFullName()%>" />

			</aui:row>
			<aui:row>
				<div>
					Email:
					<%=themeDisplay.getRealUser().getEmailAddress()%></div>
			</aui:row>

			<aui:row>
				<div>
					User Id:
					<%=themeDisplay.getRealUser().getUserId()%></div>
			</aui:row>
		</aui:fieldset>
	</aui:fieldset-group>

</aui:form>



<!-- User Role related  -->
<aui:form name="fm2">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset label="User Role List:">
			<aui:row>
			
			
				<liferay-ui:search-container
					emptyResultsMessage="there-are-no-roles">
					<liferay-ui:search-container-results results="${userRoles}" />

					<liferay-ui:search-container-row
						className="com.avaloq.user.information.dtos.UserRoleInfo"
						modelVar="entry1">
						<liferay-ui:search-container-column-text property="roleName"
							name="${user.getFullName()}'s Roles:" cssClass="columnRole" />

					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator />
				</liferay-ui:search-container>
				
				
			</aui:row>

		</aui:fieldset>
	</aui:fieldset-group>

</aui:form>


<hr />


<!-- User web content related  -->

<aui:form name="fm3">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset label="User Web Content List:">
			<aui:row>
				<liferay-ui:search-container
					emptyResultsMessage="there-are-no-articles" total="${articlecount}">
					<liferay-ui:search-container-results results="${articlList}" />

					<liferay-ui:search-container-row
						className="com.liferay.journal.model.JournalArticle"
						modelVar="entry">
						<liferay-ui:search-container-column-text property="title"
							name="${user.getFullName()}'s allowed web content titles:"
							cssClass="columnArticle" />


					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator />
				</liferay-ui:search-container>
			</aui:row>

		</aui:fieldset>
	</aui:fieldset-group>

</aui:form> 

