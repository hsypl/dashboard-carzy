package com.crazy.dashboard.service;

import com.crazy.code.crawler.ClientConfigure;
import com.crazy.code.crawler.ClientUserAgent;
import com.crazy.code.service.ServiceProcessException;
import com.crazy.dashboard.config.TwitterConfig;
import com.crazy.dashboard.service.twitter.GetConfiguration;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangshuoying on 2018/7/17.
 */
@Service
public class TwitterService {

    private static final Logger log = LoggerFactory.getLogger(TwitterService.class);

    @Autowired
    private TwitterMessageService twitterMessageService;

    @Autowired
    private TwitterConfig twitterConfig;

    private Twitter twitter ;

    private long[] ids = new long[50];

    public TwitterService(){
        GetConfiguration getConfiguration = new GetConfiguration();
        twitter = getConfiguration.getNewInstance();
    }


//    public void login(HttpServletRequest request) throws TwitterException, IOException {
//        String verifier = request.getParameter("oauth_verifier");
//        login(verifier);
//    }
//
//    private void login(String pin) throws TwitterException, IOException {
//        twitter.setOAuthConsumer(twitterConfig.getConsumerKey(), twitterConfig.getConsumerSecret());
//        RequestToken requestToken = twitter.getOAuthRequestToken(twitterConfig.getCallbackUrl());
//        AccessToken accessToken = null;
//        while (null == accessToken) {
//            try{
//                accessToken = twitter.getOAuthAccessToken(requestToken, pin);
//            } catch (TwitterException te) {
//                if(401 == te.getStatusCode()){
//                    log.debug("Unable to get the access token.");
//                }else{
//                    te.printStackTrace();
//                }
//            }
//        }
//        twitterConfig.setProperty("token",accessToken.getToken());
//        twitterConfig.setProperty("tokenSecret",accessToken.getTokenSecret());
//    }

    /**
     * 刷新关注列表
     */
    public void updateFriendsIds() throws TwitterException {
        ids = twitter.getFriendsIDs(-1).getIDs();
    }

    /**
     * 获取关注列表ID
     * @return long[]
     */
    public long[] getFriendsIds(){
        return ids;
    }

    @Async
    public void refreshMessage() throws TwitterException {
        updateFriendsIds();
        for (long id : ids) {
            List<Status> statuses = twitter.getUserTimeline(id);
            for (Status status : statuses) {
//                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                try {
                    String subText = status.getText().substring(status.getText().length() - 28);
                    Integer index = subText.indexOf("https");
                    if (index == -1){
                        continue;
                    }
                    String link = subText.substring(index);
                    Connection conn = Jsoup.connect(link);
                    conn.userAgent(ClientUserAgent.getChromeUserAgent())
                            .timeout(ClientConfigure.TIMEOUT_FIVE_MINUTE)
                            .ignoreContentType(true)
                            .followRedirects(false);
                    Connection.Response response = conn.execute();
                    Document doc = Jsoup.parse(response.body());
                    Elements elements = doc.select("title");
                    String title = elements.get(0).text();

                    Connection conn1 = Jsoup.connect(title);
                    conn1.userAgent(ClientUserAgent.getChromeUserAgent())
                            .timeout(ClientConfigure.TIMEOUT_FIVE_MINUTE)
                            .ignoreContentType(true)
                            .followRedirects(false);
                    Connection.Response response1 = conn1.execute();
                    Document doc1 = Jsoup.parse(response1.body());
                    Elements elements1 = doc1.select("title");
                    String content = elements1.get(0).text();
                    twitterMessageService.save(content,status.getUser().getScreenName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        String a = "@crypto_tycoon Keep an eye on your inbox ;)";
        System.out.print(a.indexOf("https"));
    }
}
