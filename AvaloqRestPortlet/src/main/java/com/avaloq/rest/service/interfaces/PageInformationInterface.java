package com.avaloq.rest.service.interfaces;

import com.liferay.portal.kernel.json.JSONObject;

import javax.ws.rs.ext.Provider;


/**
 * @author Vaibhav Khopade
 */
@Provider
public interface PageInformationInterface {
	
	/**
	 * Get all pages of given site id
	 * 
	 * @param siteId
	 * @return JSONObject
	 */
	public JSONObject getSitePages(long siteId);
}
