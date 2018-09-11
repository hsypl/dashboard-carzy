<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<s:url value="/dashboard/token/monitor/save" var="saveURL"/>
<%@ page isELIgnored="false" %>
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
                                <h2>编辑</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="col-md-4">
                                    <form:form commandName="monitorSymbol" id="ico" action="${saveURL}" class="form-horizontal">
                                        <form:hidden path="id" />
                                        <div class="form-group">
                                            <label class="control-label">代币符号</label>
                                            <form:input path="symbol" class="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label">卖价</label>
                                            <form:input path="sellPrice" class="form-control"  />
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label">买价</label>
                                            <form:input path="buyPrice" class="form-control" />
                                        </div>
                                    <div class="form-group">
                                        <label class="control-label">交易所</label>
                                        <form:select path="exchange" >
                                            <form:option value="" label="- 交易所 -"/>
                                            <form:options items="${exchangeList}" itemValue="description" itemLabel="value" />
                                        </form:select>
                                    </div>
                                        <div class="form-group">
                                            <label class="control-label">交易类型</label>
                                            <form:select path="type" >
                                                <form:option value="" label="- 交易类型 -"/>
                                                <form:options items="${changeType}" itemValue="value" itemLabel="description" />
                                            </form:select>
                                        </div>
                                        <button type="submit" class="btn btn-primary pull-right">Update</button>
                                    </form:form>
                                </div>
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
