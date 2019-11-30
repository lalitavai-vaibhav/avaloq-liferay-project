package com.avaloq.rest.service;

import com.avaloq.rest.constants.ConstantsKeys;
import com.avaloq.rest.service.entities.PageDetails;
import com.avaloq.rest.service.entities.Pages;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vaibhav Khopade
 */
@Component(immediate = true, service = PageInformationService.class)
public class PageInformationService {

	private static final Log LOG = LogFactoryUtil.getLog(PageInformationService.class);

	@Reference
	JournalArticleLocalService journalArticleLocalService;

	@Reference
	LayoutLocalService layoutLocalService;

	@Reference
	JSONFactory jsonFactory;

	/**
	 * Get all pages of given site id
	 * 
	 * @param siteId
	 * @return JSONObject
	 */
	public JSONObject getSitePages(long siteId) {

		try {
			List<Layout> layouts = layoutLocalService.getLayouts(siteId, true);

			Pages pages = new Pages();

			List<PageDetails> pageDetailList = new ArrayList<>();

			layouts.stream().forEach(lay -> {
				PageDetails pdetails = new PageDetails();
				System.out.println("Page:" + lay.getName(new Locale("en")));

				pdetails.setName(lay.getName(new Locale("en")));
				pdetails.setTitle(lay.getTitle(new Locale("en")));
				pdetails.setPageid(lay.getPlid());
				pdetails.setCustomfiledvalue(
						lay.getExpandoBridge().getAttribute(ConstantsKeys.PAGE_CUSTOM_FIELD_NAME).toString());

				List<PageDetails> childpages = new ArrayList<>();
				if (lay.getAllChildren().size() > 0) {

					lay.getAllChildren().stream().forEach(sub -> {

						System.out.println("Subpage:" + sub.getName(new Locale("en")));

						PageDetails pdetails2 = new PageDetails();
						pdetails2.setName(lay.getName(new Locale("en")));
						pdetails2.setTitle(sub.getTitle(new Locale("en")));
						pdetails2.setPageid(sub.getPlid());
						pdetails2.setCustomfiledvalue(
								sub.getExpandoBridge().getAttribute(ConstantsKeys.PAGE_CUSTOM_FIELD_NAME).toString());

						childpages.add(pdetails2);

					});
				}

				pdetails.setSubpages(childpages);
				pageDetailList.add(pdetails);

			});

			pages.setPages(pageDetailList);

			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();
			String repsonse = gson.toJson(pages);

			return jsonFactory.createJSONObject(repsonse);
			
		} catch (JSONException e) {
			LOG.debug("Error while conveting object to json :" + e.getMessage());
		} catch (Exception e) {
			LOG.debug("Error while getting site pages: " + e.getMessage());
		}
		return null;

	}

}
