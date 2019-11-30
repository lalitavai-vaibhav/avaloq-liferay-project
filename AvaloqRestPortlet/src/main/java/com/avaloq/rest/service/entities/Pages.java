package com.avaloq.rest.service.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pages {

	@SerializedName("Pages")
    @Expose
    private List<PageDetails> pages;

	public List<PageDetails> getPages() {
		return pages;
	}

	public void setPages(List<PageDetails> pages) {
		this.pages = pages;
	}

	
	
}
