<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.example.bibliotheque.models.*" %>
<html>
<head>
    <title>Créer un adhérent et abonnement</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-5">
    <h2>Créer un adhérent et abonnement</h2>

    <form method="post" action="abonnements" class="mb-4">
        <div class="mb-3">
            <label class="form-label">Nom :</label>
            <input type="text" name="nom" class="form-control" required />
        </div>
        <div class="mb-3">
            <label class="form-label">Email :</label>
            <input type="email" name="email" class="form-control" required />
        </div>
        <div class="mb-3">
            <label class="form-label">Mot de passe :</label>
            <input type="password" name="motDePasse" class="form-control" required />
        </div>
        <div class="mb-3">
            <label class="form-label">Type adhérent (id) :</label>
            <input type="number" name="typeId" class="form-control" required />
            <small>Entrer l'ID du type adhérent</small>
        </div>
        <div class="mb-3">
            <label class="form-label">Date de naissance :</label>
            <input type="date" name="dateNaissance" class="form-control" required />
        </div>

        <hr/>

        <div class="mb-3">
            <label class="form-label">Date début abonnement :</label>
            <input type="date" name="dateDebut" class="form-control" required />
        </div>
        <div class="mb-3">
            <label class="form-label">Date fin abonnement :</label>
            <input type="date" name="dateFin" class="form-control" required />
        </div>

        <button type="submit" class="btn btn-primary">Créer</button>
    </form>

    <%
        String message = (String) request.getAttribute("message");
        String error = (String) request.getAttribute("error");

        if (message != null) {
    %>
        <div class="alert alert-success"><%= message %></div>
    <%
        }

        if (error != null) {
    %>
        <div class="alert alert-danger"><%= error %></div>
    <%
        }
    %>
</body>
</html>
