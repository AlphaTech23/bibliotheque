<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% 
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
%>

<% if (message != null) { %>
    <div class="alert alert-info">
        <i class="bi bi-info-circle-fill me-2"></i><%= message %>
    </div>
<% } %>

<% if (error != null) { %>
    <div class="alert alert-danger">
        <i class="bi bi-exclamation-triangle-fill me-2"></i><%= error %>
    </div>
<% } %>

<% if (success != null) { %>
    <div class="alert alert-success">
        <i class="bi bi-check-circle-fill me-2"></i><%= success %>
    </div>
<% } %>