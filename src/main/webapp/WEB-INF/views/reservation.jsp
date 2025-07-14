<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.example.bibliotheque.models.*" %>
<% 
    Adherent adherent = (Adherent)request.getSession().getAttribute("adherent"); 
    boolean isLoggedIn = adherent != null;
%>
<html>
<head>
    <title>Historique des Réservations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-4">
    <h2>Historique des réservations</h2>

    <!-- Formulaire de filtrage -->
    <form method="get" action="" class="row g-3 mb-4">
        <% if(!isLoggedIn) { %>
        <div class="col-md-3">
            <label>Nom / Email</label>
            <input type="text" name="nom" class="form-control" 
                   value="<%= request.getAttribute("nom") != null ? request.getAttribute("nom") : "" %>" />
        </div>
        <% } %>
        <div class="col-md-3">
            <label>Livre</label>
            <input type="text" name="livre" class="form-control" 
                   value="<%= request.getAttribute("livre") != null ? request.getAttribute("livre") : "" %>" />
        </div>
        <div class="col-md-2">
            <label>Date début</label>
            <input type="date" name="dateDebut" class="form-control" 
                   value="<%= request.getAttribute("dateDebut") != null ? request.getAttribute("dateDebut") : "" %>" />
        </div>
        <div class="col-md-2">
            <label>Date fin</label>
            <input type="date" name="dateFin" class="form-control" 
                   value="<%= request.getAttribute("dateFin") != null ? request.getAttribute("dateFin") : "" %>" />
        </div>
        <div class="col-md-2">
            <label>Statut</label>
            <select name="statut" class="form-select">
                <option value="">-- Tous --</option>
                <option value="encours" <%= "encours".equals(request.getAttribute("statut")) ? "selected" : "" %>>En cours</option>
                <option value="valide" <%= "valide".equals(request.getAttribute("statut")) ? "selected" : "" %>>Validée</option>
                <option value="refusee" <%= "refusee".equals(request.getAttribute("statut")) ? "selected" : "" %>>Refusée</option>
            </select>
        </div>
        <div class="col-md-2 d-flex align-items-end">
            <button type="submit" class="btn btn-primary w-100">Filtrer</button>
        </div>
    </form>

    <!-- Tableau des réservations -->
    <%
        List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
        if (reservations != null && !reservations.isEmpty()) {
    %>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <% if(!isLoggedIn) { %>
                    <th>Adhérent</th>
                    <% } %>
                    <th>Email</th>
                    <th>Livre</th>
                    <th>Date réservation</th>
                    <th>Statut</th>
                    <% if(!isLoggedIn) { %>
                    <th>Actions</th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
            <% for (Reservation r : reservations) {
                String statut;
                if (r.getValide() == null) statut = "En cours";
                else if (r.getValide()) statut = "Validée";
                else statut = "Refusée";
            %>
                <tr>
                    <% if(!isLoggedIn) { %>
                    <td><%= r.getAdherent().getNom() %></td>
                    <% } %>
                    <td><%= r.getAdherent().getEmail() %></td>
                    <td><%= r.getExemplaire().getLivre().getNom() %></td>
                    <td><%= r.getDateReservation() %></td>
                    <td><%= statut %></td>
                    <% if(!isLoggedIn) { %>
                    <td>
                        <% if (r.getValide() == null) { %>
                            <form action="/reservation/valider" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="<%= r.getId() %>"/>
                                <button type="submit" class="btn btn-success btn-sm">Accepter</button>
                            </form>
                            <form action="/reservation/refuser" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="<%= r.getId() %>"/>
                                <button type="submit" class="btn btn-danger btn-sm">Refuser</button>
                            </form>
                        <% } else { %>
                            —
                        <% } %>
                    </td>
                    <% } %>
                </tr>
            <% } %>
            </tbody>
        </table>
    <% } else { %>
        <div class="alert alert-warning">Aucune réservation trouvée.</div>
    <% } %>
</body>
</html>
