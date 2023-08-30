package com.anf.core.services;

import org.json.JSONObject;

public interface ContentService {
	String commitUserDetails(JSONObject userdetails);
	boolean validateUserAge(int age);
}
