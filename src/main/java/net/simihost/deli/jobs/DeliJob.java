package net.simihost.deli.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * Created by Rashed on  22/05/2019
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DeliJob extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void executeInternal(JobExecutionContext ctx) {
        try {
            logger.info("Gateways Keys Refreshed Successfully!");
        } catch (Exception e) {
            logger.warn("Stack Trace: {}",e.getMessage(), e);
        }
    }
}
