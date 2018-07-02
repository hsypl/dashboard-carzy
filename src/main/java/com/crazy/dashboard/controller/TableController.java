package com.crazy.dashboard.controller;

import com.crazy.code.web.DataTables;
import com.crazy.dashboard.model.CoinMarketCap;
import com.crazy.dashboard.service.CoinMarketCapService;
import com.google.gson.reflect.TypeToken;
import com.sungness.core.util.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by developer2 on 2018/6/29.
 */
@Controller
@RequestMapping("/dashboard/table")
public class TableController {

    @Autowired
    CoinMarketCapService coinMarketCapService;

    @RequestMapping("/index")
    public void index(){

    }

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
}
