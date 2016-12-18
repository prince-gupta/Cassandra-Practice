package com.practice.utility;

import java.util.function.Predicate;

public final class CustomStringUtility {
	
	public static Predicate<String> isNotBlank = (s) -> s != null;
	
	public static ReplaceFunction replace = (s,v,r) -> s.replaceAll(r, v);
	
	public interface ReplaceFunction{
		String replace(String source, String value, String regex);
	}
}
