package com.o2osys.pos.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;

public class logUtil {
    
	private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");

	public static long startlog(Logger log) {
		long startTime = System.currentTimeMillis();
		log.info("====================================================");
		log.info("The time is now " + LocalDateTime.now().format(format) );
		return startTime;
	}

	public static void endlog(Logger log, long startTime) {
		long endTime = System.currentTimeMillis();
		log.info("start time:" + startTime + ".end time:" + endTime + ".diff:" + (endTime - startTime));
		log.info("====================================================");
	}
}
