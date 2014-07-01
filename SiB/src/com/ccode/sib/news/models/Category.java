package com.ccode.sib.news.models;

import com.ccode.sib.R;

/**
 * Title: CCode SiB <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0
 */

public enum Category {

	VIJESTI(R.string.vijesti), SPORT(R.string.sport), LIFESTYLE(R.string.lifestyle), KULTURA(R.string.kultura), PREDSTAVLJAMO(
			R.string.predstavljamo), SIBPLUS(R.string.sib_plus), STUDENTI(R.string.studenti);

	public int name;

	Category(int name) {
		this.name = name;
	}

	public int getName() {
		return name;
	}

}
