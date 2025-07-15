<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.time.*, com.example.bibliotheque.models.*" %>
<% 
    Adherent adherent = (Adherent)request.getSession().getAttribute("adherent"); 
    boolean isLoggedIn = adherent != null;
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        .sidebar {
            background: #f8f9fa;
            border-right: 1px solid #dee2e6;
            height: 100vh;
            position: sticky;
            top: 0;
        }
        .main-content {
            padding: 2rem;
        }
        .nav-divider {
            border-top: 1px solid rgba(0,0,0,.1);
            margin: 0.5rem 0;
        }
    </style>
    