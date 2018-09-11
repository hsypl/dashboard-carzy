package com.crazy.dashboard.scheduling;

import com.crazy.dashboard.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by root on 2018/8/26.
 */
@Service
public class TwitterTask {

    @Autowired
    private TwitterService twitterService;

    public void start() throws TwitterException {
        twitterService.refreshMessage();
    }
}
