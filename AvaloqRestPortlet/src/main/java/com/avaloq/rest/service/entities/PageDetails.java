package com.avaloq.rest.service.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PageDetails {
	

	@SerializedName("PageId")
    @Expose
    private long pageid;
	
	@SerializedName("Name")
    @Expose
    private String name;
	
	@SerializedName("Title")
    @Expose
    private String title;
    
    @SerializedName("CustomFieldValue")
    @Expose
    private String customfiledvalue;
    
    @SerializedName("SubPages")
    @Expose
    private List<PageDetails> subpages;
    

	

	public long getPageid() {
		return pageid;
	}

	public void setPageid(long pageid) {
		this.pageid = pageid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustomfiledvalue() {
		return customfiledvalue;
	}

	public void setCustomfiledvalue(String customfiledvalue) {
		this.customfiledvalue = customfiledvalue;
	}

	public List<PageDetails> getSubpages() {
		return subpages;
	}

	public void setSubpages(List<PageDetails> subpages) {
		this.subpages = subpages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
    
    
}
