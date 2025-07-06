<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Recherche de Livres</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="container mt-4">
    <h2>Recherche de livres</h2>

    <form method="get" class="row g-3 mb-4">
        <div class="col-md-4">
            <input type="text" name="nom" placeholder="Nom du livre" class="form-control"
                   value="<%= request.getAttribute("nom") != null ? request.getAttribute("nom") : "" %>" />
        </div>
        <div class="col-md-3">
            <input type="number" name="minRestriction" placeholder="Restriction min" class="form-control"
                   value="<%= request.getAttribute("minRestriction") != null ? request.getAttribute("minRestriction") : "" %>" />
        </div>
        <div class="col-md-3">
            <input type="number" name="maxRestriction" placeholder="Restriction max" class="form-control"
                   value="<%= request.getAttribute("maxRestriction") != null ? request.getAttribute("maxRestriction") : "" %>" />
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-primary">Rechercher</button>
        </div>
    </form>

<%
    java.util.List<com.example.bibliotheque.models.Livre> livres =
        (java.util.List<com.example.bibliotheque.models.Livre>) request.getAttribute("livres");
    java.util.Map<Integer, java.util.Map<String, Object>> statsMap =
        (java.util.Map<Integer, java.util.Map<String, Object>>) request.getAttribute("statsMap");
%>

<% if (livres != null && !livres.isEmpty()) { %>
    <%
        boolean hasDetails = false;
    %>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Nom</th>
                <th>Restriction</th>
                <th>Total</th>
                <th>Disponibles</th>
                <th>Indisponibles</th>
                <th>Détails</th>
            </tr>
        </thead>
        <tbody>
            <% for (com.example.bibliotheque.models.Livre livre : livres) {
                java.util.Map<String, Object> stats = statsMap.get(livre.getId());
                if (stats == null) stats = new java.util.HashMap<>();
                Integer total = (Integer) stats.get("total");
                Integer disponible = (Integer) stats.get("disponible");
                Integer indisponible = (Integer) stats.get("indisponible");
                java.util.List<?> pretInfos = (java.util.List<?>) stats.get("pretInfos");
            %>
            <tr>
                <td><%= livre.getNom() %></td>
                <td><%= livre.getRestriction() %></td>
                <td><%= total != null ? total : 0 %></td>
                <td><%= disponible != null ? disponible : 0 %></td>
                <td><%= indisponible != null ? indisponible : 0 %></td>
                <td>
                    <% if (pretInfos != null && !pretInfos.isEmpty()) { %>
                        <button class="btn btn-sm btn-info" type="button" data-bs-toggle="collapse" data-bs-target="#detail-<%= livre.getId() %>">
                            Voir
                        </button>
                        <%
                            hasDetails = true;
                        %>
                    <% } %>
                </td>
            </tr>

            <% if (pretInfos != null && !pretInfos.isEmpty()) { %>
            <tr class="collapse" id="detail-<%= livre.getId() %>">
                <td colspan="6">
                    <strong>Exemplaires prêtés :</strong>
                    <ul>
                        <% for (Object infoObj : pretInfos) {
                            // On suppose que infoObj est un Map<String,Object>
                            java.util.Map<String, Object> info = (java.util.Map<String, Object>) infoObj;
                            com.example.bibliotheque.models.Adherent adherent = (com.example.bibliotheque.models.Adherent) info.get("adherent");
                            java.time.LocalDate dateDispo = (java.time.LocalDate) info.get("dateDispo");
                        %>
                            <li><%= adherent.getNom() %> (<%= adherent.getEmail() %>) – Disponible le : <%= dateDispo != null ? dateDispo.toString() : "N/A" %></li>
                        <% } %>
                    </ul>
                </td>
            </tr>
            <% } %>
            <% } %>
        </tbody>
    </table>
    <% if (!hasDetails) { %>
        <div class="alert alert-info">Aucun détail de prêt disponible pour les livres listés.</div>
    <% } %>

<% } else { %>
    <div class="alert alert-warning">Aucun livre trouvé.</div>
<% } %>

</body>
</html>
