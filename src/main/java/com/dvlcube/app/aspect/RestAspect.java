package com.dvlcube.app.aspect;

import com.dvlcube.app.rest.StatService;
import com.dvlcube.utils.aspects.stats.StatsAspect;
import com.dvlcube.utils.interfaces.MxAspect;
import com.dvlcube.utils.interfaces.MxBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * Logs all HTTP requests, generating stats about the time it takes to run every
 * method.
 * 
 * @see StatService
 * @since 13 de fev de 2019
 * @author Ulisses Lima
 */
@Configuration
@Aspect
public class RestAspect implements MxAspect {
	private Logger log = LogManager.getLogger(this.getClass());

	/**
	 * @param point
	 * @throws Throwable
	 * @since 12 de abr de 2019
	 * @author Ulisses Lima
	 */
	@Around("execution(* com.dvlcube.app.rest.*.*(..))")
	public Object stats(ProceedingJoinPoint point) throws Throwable {
		return StatsAspect.timeAround(point);
	}

	/**
	 * POST.
	 * 
	 * @param point
	 * @param body
	 * @return point
	 * @throws Throwable
	 * @since 12 de abr de 2019
	 * @author Ulisses Lima
	 */
	@Around("execution(* com.dvlcube.app.rest.*.post(..)) && args(body,..)")
	public Object postDebug(ProceedingJoinPoint point, MxBean<? extends Serializable> body) throws Throwable {
		try {
			return point.proceed();
		} catch (Throwable e) {
			log.error("on {}. body: {}", point, body);
			throw e;
		}
	}
}
