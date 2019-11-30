package com.avaloq.rest.application;

import com.avaloq.rest.constants.ConstantsKeys;
import com.avaloq.rest.service.PageInformationService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author VKopante
 */
@Component(property = { 
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + ConstantsKeys.JAX_RS_APPLICATION_BASE,
		JaxrsWhiteboardConstants.JAX_RS_NAME + ConstantsKeys.JAX_RS_NAME }, 
		service = Application.class)
public class AvaloqRestApplication extends Application {
	private static final Log LOG = LogFactoryUtil.getLog(AvaloqRestApplication.class);

	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}

	@Reference
	PageInformationService pageInformationService;

	@GET
	@Path("/sitepages")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSitePages() {

		JSONObject jsonObject = pageInformationService.getSitePages(ConstantsKeys.SITE_GROUP_ID);

		if (jsonObject != null) {
			return Response.status(Response.Status.OK).entity(jsonObject.toString()).build();
		} else {
			LOG.debug("Site Pages not found:" + LocalDateTime.now());
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}