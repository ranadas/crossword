package com.rdas;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Spring5JUnit4ConcurrentTest.SimpleConfiguration.class)
public class Spring5JUnit4ConcurrentTest implements ApplicationContextAware, InitializingBean {

    @Configuration
    public static class SimpleConfiguration {}

    private ApplicationContext applicationContext;

    private boolean beanInitialized = false;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.beanInitialized = true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void whenTestStarted_thenContextSet() throws Exception {
        TimeUnit.SECONDS.sleep(2);

        assertNotNull(
                "The application context should have been set due to ApplicationContextAware semantics.",
                this.applicationContext);
    }

    @Test
    public void whenTestStarted_thenBeanInitialized() throws Exception {
        TimeUnit.SECONDS.sleep(2);

        assertTrue(
                "This test bean should have been initialized due to InitializingBean semantics.",
                this.beanInitialized);
    }

}
