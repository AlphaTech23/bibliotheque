<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Effectuer un prêt</title>
    <!-- Bootstrap CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">

    <h2 class="mb-4">Effectuer un prêt</h2>

    <!-- Message d'erreur -->
    <% if(request.getAttribute("error") != null) { %>
        <div class="alert alert-danger">
            <%= request.getAttribute("error") %>
        </div>
    <% } %>

    <!-- Message de succès -->
    <% if(request.getAttribute("success") != null) { %>
        <div class="alert alert-success">
            <%= request.getAttribute("success") %>
        </div>
    <% } %>

    <form method="post" action="<%= request.getContextPath() %>/prets/effectuer" class="border p-4 rounded bg-light shadow-sm">

        <div class="mb-3">
            <label for="adherentId" class="form-label">Adhérent :</label>
            <input type="number" name="adherentId" id="adherentId" class="form-control" required/>
        </div>

        <div class="mb-3">
            <label for="exemplaireId" class="form-label">Exemplaire :</label>
            <input type="number" name="exemplaireId" id="exemplaireId" class="form-control" required/>
        </div>

        <div class="mb-3">
            <label for="typePretId" class="form-label">Type de prêt :</label>
            <input type="number" name="typePretId" id="typePretId" class="form-control" required/>
        </div>

        <button type="submit" class="btn btn-primary">Effectuer le prêt</button>
    </form>

    <!-- Bootstrap JS (facultatif, pour composants interactifs) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
