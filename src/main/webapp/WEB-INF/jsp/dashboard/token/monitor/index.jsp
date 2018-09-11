<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %>
<s:url value="/dashboard/token/monitor/edit" var="editUrl"/>
<s:url value="/dashboard/token/monitor/delete" var="deleteUrl"/>
<html>
<head>
    <%@include file="/WEB-INF/jsp/includes/meta.jsp" %>
    <title>Gentelella Alela! | </title>
    <!-- Bootstrap -->
    <%@include file="/WEB-INF/jsp/includes/linksOfHead.jsp" %>
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
                                <h2>价格监控</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="toolbar">
                                    <div class="btn-group" style="margin-top: 10px;margin-bottom: 20px">
                                        <a href="${editUrl}" class="btn btn-success" type="button">新增</a>
                                    </div>
                                </div>
                                <table class="table table-bordered">
                                    <thead>
                                    <tr>
                                        <th>名称</th>
                                        <th>交易所</th>
                                        <th>卖价</th>
                                        <th>买价</th>
                                        <th>类型</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${monitorList}" varStatus="status">
                                        <c:set var="monitor" value="${status.current}"/>
                                        <tr>
                                            <td>${monitor.symbol}</td>
                                            <td>${monitor.exchange}</td>
                                            <td><fmt:formatNumber type="number" value="${monitor.sellPrice} " maxFractionDigits="8"/></td>
                                            <td><fmt:formatNumber type="number" value="${monitor.buyPrice} " maxFractionDigits="8"/></td>
                                            <td>${monitor.type}</td>
                                            <td>
                                                <a href="${editUrl}?id=${monitor.id}" rel="tooltip" title="Edit symbol" class="btn btn-primary btn-simple btn-xs">
                                                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                                </a>
                                                <a href="${deleteUrl}?id=${monitor.id}" rel="tooltip" title="Remove" class="btn btn-danger btn-simple btn-xs">
                                                    <i class="fa fa-times" aria-hidden="true"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
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
</html>
