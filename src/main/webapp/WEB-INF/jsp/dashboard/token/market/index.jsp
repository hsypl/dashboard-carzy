<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <%@include file="/WEB-INF/jsp/includes/meta.jsp" %>
    <title>Gentelella Alela! | </title>
    <!-- Bootstrap -->
    <%@include file="/WEB-INF/jsp/includes/linksOfHead.jsp" %>
    <link href="<s:url value="/media/vendors/datatables/datatables.min.css"/>" rel="stylesheet">

    <link href="<s:url value="/media/vendors/dateTimePicker/bootstrap-datetimepicker.min.css"/>" rel="stylesheet">
    <%--<link href="/media/vendors/css/iCheck/skins/flat/green.css" rel="stylesheet">--%>
</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <!-- right navigation -->
        <%@include file="/WEB-INF/jsp/includes/navigationRight.jsp" %>
        <!-- top navigation -->
        <%@include file="/WEB-INF/jsp/includes/navigationTop.jsp" %>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>Plus Table Design</h2>
                        <ul class="nav navbar-right panel_toolbox">
                            <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                            </li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                   aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a href="#">Settings 1</a>
                                    </li>
                                    <li><a href="#">Settings 2</a>
                                    </li>
                                </ul>
                            </li>
                            <li><a class="close-link"><i class="fa fa-close"></i></a>
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <div class="toolbar">
                            <div class="btn-group" style="margin-top: 10px;margin-bottom: 20px">
                                <button class="btn btn-success" type="button" onclick="down()">下载</button>
                                <a href="/dashboard/token/market/download" class="btn btn-success" type="button">新增</a>
                            </div>
                        </div>
                        <div class="searchTool">
                            <div class="row">
                                <div class="col-md-5">
                                    <div class="form-group">
                                        <div class="input-group date form_date col-md-4" id="datetimepicker"
                                             data-date="" data-date-format="yyyy-mm-dd">
                                            <input class="form-control" size="8" type="text" id="selectTime"
                                                   placeholder="选择时间">
                                            <span class="input-group-addon"><span
                                                    class="glyphicon glyphicon-calendar"></span></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <table id="example" class="table table-striped table-bordered bulk_action">
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>name</th>
                                <th>symbol</th>
                                <th>rank</th>
                                <th>priceUsd</th>
                                <th>时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <%@include file="/WEB-INF/jsp/includes/footer.jsp" %>
        <!-- /footer content -->
    </div>
</div>

</body>
<%@include file="/WEB-INF/jsp/includes/scriptsOfBaseHead.jsp" %>
<script src="<s:url value="/media/vendors/datatables/datatables.min.js"/>" type="text/javascript"></script>
<script src="<s:url value="/media/vendors/datatables/china.js"/>" type="text/javascript"></script>

<script src="<s:url value="/media/vendors/dateTimePicker/moment.min.js"/>" type="text/javascript"></script>
<script src="<s:url value="/media/vendors/dateTimePicker/moment.js"/>" type="text/javascript"></script>
<script src="<s:url value="/media/vendors/dateTimePicker/bootstrap-datetimepicker.min.js"/>"
        type="text/javascript"></script>

<%--<script src="/media/vendors/jquery/icheck.min.js"></script>--%>
<script>
    var table;
    var param = {};
    $('#datetimepicker').datetimepicker({
        format: 'YYYY-MM-DD'
    }).on("dp.change", function () {
        var time = $('#selectTime').val();
        table
                .columns(5)
                .search(time)
                .draw();
    });

    $(document).ready(function () {
        table = $('#example').DataTable({
            stateSave: true,
            searchDelay: 1000,
            processing: true,
            serverSide: true,
            language: language,
            //设置时间
            "stateSaveParams": function (settings, data) {
                var time = data.columns[5].search.search;
                if(time != 'NaN'){
                    $('#selectTime').val(time);
                }
            },
            ajax: function (data, callback, settings) {
                ajaxTable(data, callback, settings);
            },
            columns: [
                {"data": "id"},
                {"data": "name"},
                {"data": "symbol"},
                {"data": "rank"},
                {"data": "priceUsd", orderable: false},
                {"data": "lastUpdated", orderable: false},
                {
                    data: null,
                    orderable: false,
                    width: "120px"
                }
            ],
            columnDefs: [{
                targets: 6,
                render: function (data, type, row, meta) {
                    var id = '"' + row.id + '"';
                    return "<a class='btn btn-default' onclick=edit(" + id + ");><i class='fa fa-pencil-square-o fa-lg'></i></a>" +
                            "<a class='btn btn-default' onclick=edit(" + id + ");><i class='fa fa-trash fa-lg'></i></a>";
                }
            },
//                {
//                    render: function(data, type, row) {
//                        return data + ' (' + row['priceUsd'] + ')';
//                    },
//                    targets: 0
//                },
                {"orderable": false, "targets": 6}
            ]
        });

    });

    function ajaxTable(data, callback, settings) {
        //封装请求参数
        var filter = {};
        data['columns'].forEach(function (val) {
            if (val['search'].value != ''){
                eval('filter.'+val.data+'='+val.search.value+';');
            }
        });
        console.log(data);
        var pagination = {};
        var columnIndex = data['order'][0]['column'];  // 获取排序列的索引
        var columnName = data['columns'][columnIndex]['data'];
        var dir = data['order'][0]['dir'];
        filter.fullordering = 'a.' + columnName + ' ' + dir;
        filter.name = data.search.value;
        pagination.pageNumber = (data.start / data.length) + 1;
        pagination.pageSize = data.length;
        param.pagination = pagination;
        param.draw = data['draw'];
        param.filter = filter;
        console.log(param);
        $.ajax({
            type: "POST",
            url: "/dashboard/token/market/list",
            contentType: "application/json",
            cache: false,  //禁用缓存
            data: JSON.stringify(param),  //传入组装的参数
            dataType: "json",
            success: function (response) {
                if (response.code === 0) {
                    var returnData = {};
                    returnData.draw = response.draw;
                    returnData.recordsTotal = response.pagination.totalCount;
                    returnData.recordsFiltered = response.pagination.totalCount;
                    if (response.data === null) {
                        response.data = {};
                    }
                    returnData.data = response.data;//返回的数据列表
                    callback(returnData);
                } else {
//                    sweetAlert("请求失败！");
                }
            }
        });
    }

    function down(){
        $.ajax({
            type: "POST",
            url: "/dashboard/token/market/down",
            contentType: "application/json",
            cache: false,  //禁用缓存
            data: JSON.stringify(param),  //传入组装的参数
            dataType: "json",
            success: function (response) {
                if (response.code === 0) {
                    var returnData = {};
                    returnData.draw = response.draw;
                    returnData.recordsTotal = response.pagination.totalCount;
                    returnData.recordsFiltered = response.pagination.totalCount;
                    if (response.data === null) {
                        response.data = {};
                    }
                    returnData.data = response.data;//返回的数据列表
                    callback(returnData);
                } else {
//                    sweetAlert("请求失败！");
                }
            }
        });
    }

    function edit(id) {
        alert(id);
    }
</script>
</html>
