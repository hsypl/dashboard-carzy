<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<html lang="en">
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
                <div class="page-title">
                    <div class="title_left">
                        <h3>Plain Page</h3>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="row">
                    <div class="col-md-6">
                        <form data-toggle="validator" role="form">
                            <div class="form-group">
                                <label for="inputName" class="control-label">Name</label>
                                <input type="text" class="form-control" id="inputName" placeholder="Cina Saffary" required>
                            </div>
                            <div class="form-group">
                                <label for="inputEmail" class="control-label">Email</label>
                                <input type="email" class="form-control" id="inputEmail" placeholder="Email" data-error="Bruh, that email address is invalid" required>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword" class="control-label">Password</label>
                                <div class="form-inline row">
                                    <div class="form-group col-sm-6">
                                        <input type="password" data-minlength="6" class="form-control" id="inputPassword" placeholder="Password" required>
                                        <div class="help-block">Minimum of 6 characters</div>
                                    </div>
                                    <div class="form-group col-sm-6">
                                        <input type="password" class="form-control" id="inputPasswordConfirm" data-match="#inputPassword" data-match-error="Whoops, these don't match" placeholder="Confirm" required>
                                        <div class="help-block with-errors"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </div>
                        </form>
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
