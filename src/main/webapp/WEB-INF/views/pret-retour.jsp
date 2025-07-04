<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Retour d'un exemplaire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-5">

    <h2 class="mb-4">Retour d’un exemplaire</h2>

    <% if(request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>

    <% if(request.getAttribute("success") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("success") %></div>
    <% } %>

    <% if(request.getAttribute("warning") != null) { %>
        <div class="alert alert-warning"><%= request.getAttribute("warning") %></div>
    <% } %>

    <form method="post" action="<%= request.getContextPath() %>/prets/retour">
        <div class="mb-3">
            <label for="exemplaireId" class="form-label">ID de l'exemplaire</label>
            <input type="number" class="form-control" name="exemplaireId" id="exemplaireId" required>
        </div>

        <div class="mb-3">
            <label for="dateRetour" class="form-label">Date de retour</label>
            <input type="date" class="form-control" name="dateRetour" id="dateRetour" required>
        </div>

        <button type="submit" class="btn btn-primary">Valider le retour</button>
    </form>

</body>
</html>
