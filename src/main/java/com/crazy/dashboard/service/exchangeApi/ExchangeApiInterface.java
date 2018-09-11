package com.crazy.dashboard.service.exchangeApi;


import com.crazy.code.httpclient.HttpClientException;
import com.crazy.dashboard.model.exchange.Depth;
import com.crazy.dashboard.model.exchange.Ticket;

import java.io.IOException;
import java.util.List;

/**
 * Created by developer2 on 2018/2/9.
 */
public interface ExchangeApiInterface {

    Depth getDepth(String name)throws HttpClientException, IOException;

    Ticket getTicket(String name, String type) throws HttpClientException, IOException;


}
