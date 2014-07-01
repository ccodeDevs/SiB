package com.ccode.sib.news.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.ccode.sib.base.models.BaseModel;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

@Root
public class NewsWrapper extends BaseModel {

	@Element(name = "channel")
	private Channel channel;

	public Channel getChannel() {
		return channel;
	}
}