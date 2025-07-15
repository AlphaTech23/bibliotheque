<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    List<Penalite> penalites = (List<Penalite>) request.getAttribute("penalites");
%>
    <title>Liste des pénalités</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <%@ include file="sidebar.jsp" %>

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h2><i class="bi bi-exclamation-triangle me-2"></i>Liste des pénalités</h2>
            </div>

            <!-- Tableau des résultats -->
            <% if (penalites != null && !penalites.isEmpty()) { %>
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <% if (!isLoggedIn) { %>
                                    <th>Adhérent</th>
                                <% } %>
                                <th>Date debut</th>
                                <th>Durée</th>
                                <th>Motif</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Penalite p : penalites) { %>
                                <tr>
                                    <% if (!isLoggedIn) { %>
                                        <td><%= p.getAdherent().getNom() %></td>
                                    <% } %>
                                    <td><%= p.getDateDebut() %></td>
                                    <td><%= p.getAdherent().getTypeAdherent().getDureePenalite() %> €</td>
                                    <td><%= p.getMotif() %></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } else { %>
                <div class="alert alert-info">
                    <i class="bi bi-info-circle me-1"></i> Aucune pénalité à afficher.
                </div>
            <% } %>

    <%@ include file="footer.jsp" %>