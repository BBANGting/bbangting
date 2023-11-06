<%@ page language="java" contentType="text/html;charset-UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<!DOCTYPE html>

<html lang="en">
<head>
    <title>BBangting</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>
<nav class="navbar navbar-expand-mg bg-dark navbar-dark">
    <div class="navbar navbar-expand-sm bg-dark navbar-dark">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="/">BBangting</a>
            </li>
            <div class="collapse navbar-collapse" id="collapsibleNavbar">
                <C:choose>
                    <C:when test="${empty principal}">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" href="/auth/loginForm">로그인</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/auth/joinForm">회원가입</a>
                            </li>
                        </ul>
                    </C:when>
                    <C:otherwise>
                        <ul class="navbar-nav">
                                <li class="nav-item">
                                    <a class="nav-link" href="#">오픈예정</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">스토어</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="/user/updateForm">마이페이지</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="/logout">로그아웃</a>
                                </li>
                            </ul>
                    </C:otherwise>
                </C:choose>
            </div>
        </ul>
    </div>
</nav>

<br>