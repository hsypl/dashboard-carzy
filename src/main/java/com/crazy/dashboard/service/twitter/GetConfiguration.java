package com.crazy.dashboard.service.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by developer2 on 2018/7/12.
 */
public class GetConfiguration {
    public Twitter getNewInstance(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("MBwX4zZQjGA8g3czZ79B1jPyk")
                .setOAuthConsumerSecret("jcixtqTfxzlCiX3aAaCZ81sGRF0EqXNYMtAhNaW73IRNbWZ2gY")
                .setOAuthAccessToken("911786885155917825-V43vKxGEXaxyreermQHSc2RZeKq3DBm")
                .setOAuthAccessTokenSecret("7ErjtatjSOJ6UOzQytphAXKR0r0I5f6WqmcElpuouT8Ut");
        TwitterFactory tf = new TwitterFactory(cb.build());
        //Twitter twitter = tf.getSingleton();
        return tf.getInstance();
    }

}
