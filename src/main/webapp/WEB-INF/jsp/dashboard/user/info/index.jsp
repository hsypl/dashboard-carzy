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
    <%--<style>--%>
    <%--.toolbar {--%>
    <%--float: left;--%>
    <%--}--%>
    <%--</style>--%>
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
            <div class="">
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Plain Page</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="toolbar">
                                    <div class="btn-group" style="margin-top: 10px;margin-bottom: 20px">
                                        <a href="/dashboard/user/info/edit" class="btn btn-success" type="button">新增</a>
                                        <a href="${editUrl}" class="btn btn-success" type="button">编辑</a>
                                        <button type="button" class="btn btn-default">删除</button>
                                    </div>
                                </div>
                                <div class="searchTool">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <div class="input-group" id="datetimepicker" data-date=""
                                                     data-date-format="yyyy-mm-dd">
                                                    <input class="form-control" size="8" type="text" id="selectTime"
                                                           placeholder="选择时间">
                                                    <span class="input-group-addon"><span
                                                            class="glyphicon glyphicon-calendar"></span></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <select class="form-control">
                                                <option>1</option>
                                                <option>2</option>
                                                <option>3</option>
                                                <option>4</option>
                                                <option>5</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <table id="example" class="table table-striped table-bordered bulk_action">
                                    <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>name</th>
                                        <th>username</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
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
    $('#datetimepicker').datetimepicker({
        format: 'YYYY-MM-DD'
    }).on("dp.change", function () {
        console.log("123")
    });

    $(document).ready(function () {
        $('#example').DataTable({
            stateSave: true,
            searchDelay: 1000,
            processing: true,
            serverSide: true,
            language: language,
            ajax: function (data, callback, settings) {
                ajaxTable(data, callback, settings);
            },
            columns: [
                {"data": "uid"},
                {"data": "name"},
                {"data": "username"},
                {
                    data: null,
                    orderable: false,
                    width: "150px"
                }
            ],
            columnDefs: [{
                targets: 3,
                render: function (data, type, row, meta) {
                    var uid =  row.uid ;
                    var editUrl = "/dashboard/user/info/edit?uid="+uid;
                    var deleteUrl = "/dashboard/user/info/delete?uid="+uid;
                    var authorityUrl = "/dashboard/user/info/authority?uid="+uid;
                    return "<a class='btn btn-default' href = " +editUrl +" ><i class='fa fa-pencil-square-o fa-lg'></i></a>"+
                    "<a class='btn btn-default' href = " +deleteUrl +" ><i class='fa fa-trash fa-lg'></i></a>"+
                            "<a class='btn btn-default' href = " +authorityUrl +" ><i class='fa fa-cog fa-lg'></i></a>";
//                    return "<a class='btn btn-default' onclick=edit(" + uid + ");><i class='fa fa-pencil-square-o fa-lg'></i></a>" +
//                            "<a class='btn btn-default' onclick=edit(" + id + ");><i class='fa fa-trash fa-lg'></i></a>";
                }
            }
//                {
//                    render: function(data, type, row) {
//                        return data + ' (' + row['priceUsd'] + ')';
//                    },
//                    targets: 0
//                },
            ]
        });

    });

    function ajaxTable(data, callback, settings) {
        //封装请求参数
        console.log(data);
        var param = {};
        var pagination = {};
        var filter = {};
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
            url: "/dashboard/user/info/list",
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
                    alert("请求失败！");
                }
            }
        });
    }

    function edit(id) {
        alert(id);
    }
</script>
</html>
