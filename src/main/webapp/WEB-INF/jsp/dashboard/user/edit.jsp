<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                <div class="toolbar">
                    <a href="/dashboard/user/index" class="btn btn-success" type="button">返回</a>
                </div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>用户编辑</h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="row">
                                    <div class="col-md-4">
                                    <form:form commandName="userInfo" data-toggle="validator" role="form" action="/dashboard/user/save">
                                        <form data-toggle="validator" role="form">
                                            <div class="form-group">
                                                <label for="username" class="control-label">账号</label>
                                                <form:input path="username" class="form-control" id="username" placeholder="账号" required = "required" />
                                            </div>
                                            <div class="form-group">
                                                <label for="name" class="control-label">姓名</label>
                                                <form:input path="name" class="form-control" id="name" placeholder="姓名" data-error="name is invalid" required = "required"/>
                                                <div class="help-block with-errors"></div>
                                            </div>
                                            <div class="form-group">
                                                <label for="inputPassword" class="control-label">Password</label>
                                                <div class="form-inline row">
                                                    <div class="form-group col-sm-6">
                                                        <form:input path="password" type="password" data-minlength="9" class="form-control" id="inputPassword" placeholder="Password" required = "required"/>
                                                        <div class="help-block" >最小长度为9个字符串</div>
                                                    </div>
                                                    <div class="form-group col-sm-6">
                                                        <input type="password" class="form-control" id="inputPasswordConfirm" data-match="#inputPassword" data-match-error="输入密码不匹配" placeholder="确定密码" required>
                                                        <div class="help-block with-errors"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="radio">
                                                    <label>
                                                        <form:radiobutton path="admin" value="1" />
                                                        管理员
                                                    </label>
                                                </div>
                                                <div class="radio">
                                                    <label>
                                                        <form:radiobutton path="admin" value="2" />
                                                        非管理员
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <button type="submit" class="btn btn-primary">保存</button>
                                            </div>
                                            <form:hidden path="uid"/>
                                        </form>
                                        </form:form>
                                    </div>
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
<script src="<s:url value="/media/vendors/form-validate/validator.min.js"/>" type="text/javascript"></script>
</html>
