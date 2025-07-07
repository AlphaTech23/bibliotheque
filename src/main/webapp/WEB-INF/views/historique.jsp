<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.example.bibliotheque.models.*" %>
<% 
    Adherent adherent = (Adherent)request.getSession().getAttribute("adherent"); 
    boolean isLoggedIn = adherent != null;
%>
<html>
<head>
    <title>Historique des emprunts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-4">
    <h2>Historique des emprunts</h2>

    <form method="get" action="" class="row g-3 mb-4">
        <% if(!isLoggedIn) { %>
        <div class="col-md-3">
            <label>Adhérent</label>
            <select name="adherentId" class="form-select">
                <option value="">-- Tous --</option>
                <%
                    List<Adherent> adherents = (List<Adherent>) request.getAttribute("adherents");
                    Integer adherentId = (Integer) request.getAttribute("adherentId");
                    if (adherents != null) {
                        for (Adherent a : adherents) {
                %>
                    <option value="<%= a.getId() %>" <%= (a.getId().equals(adherentId)) ? "selected" : "" %>>
                        <%= a.getNom() %>
                    </option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <% } %>
        <div class="col-md-3">
            <label>Type de prêt</label>
            <select name="typePretId" class="form-select">
                <option value="">-- Tous --</option>
                <%
                    List<TypePret> types = (List<TypePret>) request.getAttribute("typesPret");
                    Integer typePretId = (Integer) request.getAttribute("typePretId");
                    if (types != null) {
                        for (TypePret t : types) {
                %>
                    <option value="<%= t.getId() %>" <%= (t.getId().equals(typePretId)) ? "selected" : "" %>>
                        <%= t.getLibelle() %>
                    </option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <div class="col-md-3">
            <label>Date début</label>
            <input type="date" name="dateDebut" class="form-control" value="<%= request.getAttribute("dateDebut") != null ? request.getAttribute("dateDebut") : "" %>"/>
        </div>

        <div class="col-md-3">
            <label>Date fin</label>
            <input type="date" name="dateFin" class="form-control" value="<%= request.getAttribute("dateFin") != null ? request.getAttribute("dateFin") : "" %>"/>
        </div>

        <div class="col-md-6">
            <label>Mot-clé (nom, email, livre...)</label>
            <input type="text" name="nom" class="form-control" value="<%= request.getAttribute("nom") != null ? request.getAttribute("nom") : "" %>"/>
        </div>

        <div class="col-md-2 d-flex align-items-end">
            <button type="submit" class="btn btn-primary w-100">Rechercher</button>
        </div>
    </form>

    <%
        List<Pret> prets = (List<Pret>) request.getAttribute("prets");
        if (prets != null && !prets.isEmpty()) {
    %>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Adhérent</th>
                    <th>Livre</th>
                    <th>Date prêt</th>
                    <th>Type de prêt</th>
                    <th>Date retour prévue</th>
                    <th>Date retour effective</th>
                    <th>État</th>
                    <th>Prolongé</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Pret p : prets) {
                %>
                    <tr>
                        <td><%= p.getAdherent().getNom() %></td>
                        <td><%= p.getExemplaire().getLivre().getNom() %></td>
                        <td><%= p.getDatePret().getLibelle() %></td>
                        <td><%= p.getTypePret() %></td>
                        <td><%= p.getDateRetourPrevue() != null ? p.getDateRetourPrevue() : "—" %></td>
                        <td><%= p.getDateRetour() != null ? p.getDateRetour() : "—" %></td>
                        <td><%= p.getEtat() != null ? p.getEtat() : (p.getDateRetour() == null ? "En cours" : "Retourné") %></td>
                        <td><%= p.isProlonge() ? "Oui" : "Non" %></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        } else {
    %>
        <div class="alert alert-warning">Aucun emprunt trouvé.</div>
    <%
        }
    %>
</body>
</html>
