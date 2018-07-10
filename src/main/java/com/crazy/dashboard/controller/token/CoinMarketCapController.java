package com.crazy.dashboard.controller.token;

import com.crazy.code.annotation.Command;
import com.crazy.code.annotation.Module;
import com.crazy.code.service.ServiceProcessException;
import com.crazy.code.util.GsonUtils;
import com.crazy.code.web.DataTables;
import com.crazy.dashboard.model.CoinMarketCap;
import com.crazy.dashboard.service.CoinMarketCapService;
import com.crazy.dashboard.service.down.MarketCapDownService;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by developer2 on 2018/7/10.
 */
@Controller
@RequestMapping(CoinMarketCapController.URL_PREFIX)
@Module(value = CoinMarketCapController.MODULE_NAME, order = 2, icon="fa fa-user-md")
public class CoinMarketCapController {

    public static final String URL_PREFIX = "/dashboard/token/market";

    public static final String MODULE_NAME = "market";

    @Autowired
    private CoinMarketCapService coinMarketCapService;

    @Autowired
    private MarketCapDownService marketCapDownService;

    @Command(value = MODULE_NAME + "首页", isInlet = true, order = 1)
    @RequestMapping(value = "/index")
    public void index(){

    }

    @Command(value = "列表" + MODULE_NAME, order = 2)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public DataTables<List<CoinMarketCap>> list(@RequestBody String data){
        Type type = new TypeToken<DataTables<List<CoinMarketCap>>>() {
        }.getType();
        DataTables<List<CoinMarketCap>> dataTables = GsonUtils.fromJson(data,type);
        List<CoinMarketCap> coinMarketCaps = coinMarketCapService.
                getList(dataTables.getPagination(),dataTables.getFilter());
        dataTables.setData(coinMarketCaps);
        dataTables.setCode(0);
        return dataTables;
    }

    @Command(value = MODULE_NAME + "下载", order = 5)
    @RequestMapping("/download")
    public ModelAndView download(
            HttpServletRequest request,
            HttpServletResponse response)
            throws UnsupportedEncodingException, ServiceProcessException {
//        queryFilter.init(request);
        Map<String, Object> modelData = new HashMap<>();
//        String fileName = marketCapDownService.downloadStatList(queryFilter, modelData);
        String fileName = marketCapDownService.downloadStatList(null, modelData);
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        response.setHeader(
                "Content-Disposition",
                String.format("attachment; filename=\"%s.xls\"", URLEncoder.encode(fileName, "UTF-8")));
        return new ModelAndView("normalExcelView", modelData);
    }
}
