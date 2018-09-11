package com.crazy.dashboard;

import com.crazy.dashboard.service.TwitterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import twitter4j.TwitterException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by developer2 on 2017/11/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {"classpath:test-config.xml"})
public class ServiceTest {

    @Autowired
    private TwitterService twitterService;

    @Test
    public void test() throws TwitterException {
        System.getProperties().setProperty("http.proxyHost", "127.0.0.1");
        System.getProperties().setProperty("http.proxyPort", "1080");
        System.getProperties().setProperty("https.proxyHost", "127.0.0.1");
        System.getProperties().setProperty("https.proxyPort", "1080");
        twitterService.refreshMessage();
    }

}