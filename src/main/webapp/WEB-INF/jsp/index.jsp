<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Demo</title>
    <%--<base href="/" />--%>
    <link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="static/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="static/css/bootstrap-social.css"/>
</head>
<body>
    <script type="text/javascript" src="static/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="static/bootstrap/js/bootstrap.min.js"></script>

    <h1>Login</h1>

    <!--<div class="container unauthenticated">-->
        <!--<div>-->
            <!--<a href="/login/facebook" class="fa fa-facebook">Войти с помощью Facebook</a>-->
        <!--</div>-->
        <!--<div>-->
            <!--<a href="/login/github">Войти с помощью Github</a>-->
        <!--</div>-->
    <!--</div>-->
    <div class="container unauthenticated" >
        <div>
            <a href="/login/facebook" class="btn btn-block btn-social btn-facebook">
                <span class="fa fa-facebook"></span> Войти с помощью Facebook
            </a>
        </div>
        <div>
            <a href="/login/github" class="btn btn-block btn-social btn-github">
                <span class="fa fa-github"></span> Войти с помощью Github
            </a>
        </div>
    </div>

       <!--With Facebook: <a href="/login/facebook">click here</a>-->

    <div class="container authenticated" style="display: none">
        Logged in as: <span id="user"></span>
        <div>
            <button onClick="logout()" class="btn btn-primary">Logout</button>
        </div>
    </div>
    <script type="text/javascript"
            src="/webjars/js-cookie/js.cookie.js"></script>
    <script type="text/javascript">
        $
            .ajaxSetup({
                beforeSend : function(xhr, settings) {
                    if (settings.type == 'POST' || settings.type == 'PUT'
                        || settings.type == 'DELETE') {
                        if (!(/^http:.*/.test(settings.url) || /^https:.*/
                            .test(settings.url))) {
                            // Only send the token to relative URLs i.e. locally.
                            xhr.setRequestHeader("X-XSRF-TOKEN",
                                Cookies.get('XSRF-TOKEN'));
                        }
                    }
                }
            });
        $.get("/user", function(data) {
            $("#user").html(data.userAuthentication.details.name);
            $(".unauthenticated").hide();
            $(".authenticated").show();
        });
        var logout = function() {
            $.post("/logout", function() {
                $("#user").html('');
                $(".unauthenticated").show();
                $(".authenticated").hide();
            });
            return true;
        }
    </script>
</body>
</html>