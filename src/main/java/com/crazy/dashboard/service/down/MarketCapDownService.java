package com.crazy.dashboard.service.down;

import com.crazy.code.message.I18nMessage;
import com.crazy.code.poi.SheetColumnMeta;
import com.crazy.code.poi.SheetMeta;
import com.crazy.code.service.ServiceProcessException;
import com.crazy.code.web.DataTables;
import com.crazy.dashboard.model.CoinMarketCap;
import com.crazy.dashboard.service.CoinMarketCapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by developer2 on 2018/7/10.
 */
@Service
public class MarketCapDownService {

//    @Autowired
//    private I18nMessage i18nMessage;

    @Autowired
    private CoinMarketCapService coinMarketCapService;

    /**
     * 下载指定条件的应用日报数据
     *
     * @param dataTables DataTables 查询条件
     * @param modelData   Map<String, Object> 视图数据map
     * @return String 下载文件名(不含前缀)
     */
    public String downloadStatList(
            DataTables dataTables, Map<String, Object> modelData)
            throws ServiceProcessException {
        String fileName = "test";
        SheetMeta sheetMeta = new SheetMeta();
        sheetMeta.setSheetName(fileName);
        List<SheetColumnMeta> columnMetas = new ArrayList<>();
        columnMetas.add(new SheetColumnMeta("id","id"));
        columnMetas.add(new SheetColumnMeta("代币编号","symbol"));
        columnMetas.add(new SheetColumnMeta("排名","rank"));
        sheetMeta.setColumns(columnMetas);
        modelData.put("sheetMeta", sheetMeta);
//        List<CoinMarketCap> data = coinMarketCapService.
//                getList(dataTables.getPagination(),dataTables.getFilter());
        List<CoinMarketCap> data = coinMarketCapService.getList(dataTables.getPagination(),dataTables.getFilter());
        modelData.put("data", data);
        return fileName;
    }
}
