package com.ccode.sib.news.models;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

@Root(name = "channel")
public class Channel {

	@ElementList(inline = true, entry = "item")
	private List<News> news;

	public List<News> getNews() {
		return news;
	}
}