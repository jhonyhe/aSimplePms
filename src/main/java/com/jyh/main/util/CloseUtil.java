package com.jyh.main.util;

import com.jyh.main.Application;

public class CloseUtil {
	public static void close() {
		Application.context.close();
	}
}
