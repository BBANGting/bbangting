<%@ page language="java" contentType="text/html;charset-UTF-8" pageEncoding="UTF-8" %>


<%@include file="../layout/header.jsp" %>

<div class="container">
    <form action="/auth/loginProc" method="post">
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="text" name="email" class="form-control" placeholder="Enter Email" id="email">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" name="password" class="form-control" placeholder="Enter Password" id="password">
        </div>
        <button id="btn-login" class="btn btn-primary">Sign in</button>
    </form>
</div>

<%@include file="../layout/footer.jsp" %>