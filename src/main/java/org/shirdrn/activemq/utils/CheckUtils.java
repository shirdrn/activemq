package org.shirdrn.activemq.utils;

public class CheckUtils {

	public static <T> void checkNotNull(T object) {
		if(object == null) {
			throw new RuntimeException(object + " must NOT null!");
		}
	}
	
	public static <T> void checkNotNull(T object, String message) {
		if(object == null) {
			throw new RuntimeException(message);
		}
	}
}
