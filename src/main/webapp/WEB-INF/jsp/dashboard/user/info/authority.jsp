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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.5/themes/default/style.min.css"/>
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
                            <div class="toolbar">
                                <form action="/dashboard/user/info/authority/save">
                                    <input type="text" name="uid" value="${uid}" hidden/>
                                    <input type="text" name="select" id="select" hidden/>
                                    <button class="btn btn-success" type="submit">提交</button>
                                </form>
                            </div>
                            <div class="x_content">
                                <div id="container"></div>
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.5/jstree.min.js"></script>
<script>
    $('#container').jstree({
        'core': {
            'data': ${privilegeList}
        },
        'checkbox': {
            three_state: true
        },
        'plugins': ["checkbox"]
    });

    $('#container')
    // listen for event
            .on('changed.jstree', function (e, data) {
                console.log(data);
                var i, j, r = [];
                for(i = 0, j = data.selected.length; i < j; i++) {
                    var node = data.instance.get_node(data.selected[i]);
                    r.push(node.id);
                    var type = node.id.split("-");
                    if ($.inArray(node.parent,r) === -1 && type[0] === "2"){
                        r.push(node.parent);
                        r.push(node.parents[node.parents.length - 3]);
                    }
                }
                $("#select").val(r);
                console.log(r);
            })
            // create the instance
            .jstree();
</script>
</html>
