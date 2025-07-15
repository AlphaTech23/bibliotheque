<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Livre> livres = (List<Livre>) request.getAttribute("livres");
    Map<Integer, Map<String, Object>> statsMap = (Map<Integer, Map<String, Object>>) request.getAttribute("statsMap");
    
    String nom = request.getParameter("nom");
    String minRestriction = request.getParameter("minRestriction");
    String maxRestriction = request.getParameter("maxRestriction");
%>
<%@ include file="header.jsp" %>
    <title>Recherche de Livres</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <%@ include file="sidebar.jsp" %>

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h2><i class="bi bi-search me-2"></i>Recherche de livres</h2>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="btn-group me-2">
                        <a href="${pageContext.request.contextPath}/reservation" class="btn btn-sm btn-outline-primary">
                            <i class="bi bi-plus-circle me-1"></i> Nouvelle réservation
                        </a>
                    </div>
                </div>
            </div>

            <!-- Formulaire de recherche -->
            <div class="card mb-4">
                <div class="card-body">
                    <form method="get" class="row g-3">
                        <div class="col-md-4">
                            <input type="text" name="nom" placeholder="Nom du livre" class="form-control" 
                                   value="<%= nom != null ? nom : "" %>" />
                        </div>
                        <div class="col-md-3">
                            <input type="number" name="minRestriction" placeholder="Restriction min" class="form-control" 
                                   value="<%= minRestriction != null ? minRestriction : "" %>" />
                        </div>
                        <div class="col-md-3">
                            <input type="number" name="maxRestriction" placeholder="Restriction max" class="form-control" 
                                   value="<%= maxRestriction != null ? maxRestriction : "" %>" />
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-primary w-100">
                                <i class="bi bi-search me-1"></i> Rechercher
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Résultats -->
            <% if (livres != null && !livres.isEmpty()) { %>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-dark">
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
                            <% for (Livre livre : livres) { 
                                Map<String, Object> stats = statsMap.get(livre.getId());
                                if (stats == null) stats = new HashMap<>();
                                Integer total = (Integer) stats.get("total");
                                Integer disponible = (Integer) stats.get("disponible");
                                Integer indisponible = (Integer) stats.get("indisponible");
                                List<?> pretInfos = (List<?>) stats.get("pretInfos");
                            %>
                                <tr>
                                    <td><%= livre.getNom() %></td>
                                    <td><%= livre.getRestriction() %></td>
                                    <td><%= total != null ? total : 0 %></td>
                                    <td><%= disponible != null ? disponible : 0 %></td>
                                    <td><%= indisponible != null ? indisponible : 0 %></td>
                                    <td>
                                        <% if (pretInfos != null && !pretInfos.isEmpty()) { %>
                                            <button class="btn btn-sm btn-info" type="button" data-bs-toggle="collapse" 
                                                    data-bs-target="#detail-<%= livre.getId() %>">
                                                <i class="bi bi-eye me-1"></i> Voir
                                            </button>
                                        <% } %>
                                    </td>
                                </tr>

                                <% if (pretInfos != null && !pretInfos.isEmpty()) { %>
                                <tr class="collapse" id="detail-<%= livre.getId() %>">
                                    <td colspan="6">
                                        <strong>Exemplaires prêtés :</strong>
                                        <ul class="list-group list-group-flush">
                                            <% for (Object infoObj : pretInfos) { 
                                                Map<String, Object> info = (Map<String, Object>) infoObj;
                                                adherent = (Adherent) info.get("adherent");
                                                LocalDate dateDispo = (LocalDate) info.get("dateDispo");
                                            %>
                                                <li class="list-group-item">
                                                    <%= adherent.getNom() %> (<%= adherent.getEmail() %>) - 
                                                    Disponible le : <%= dateDispo != null ? dateDispo.toString() : "N/A" %>
                                                </li>
                                            <% } %>
                                        </ul>
                                    </td>
                                </tr>
                                <% } %>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } else { %>
                <div class="alert alert-warning">
                    <i class="bi bi-exclamation-triangle me-1"></i> Aucun livre trouvé.
                </div>
            <% } %>

    <%@ include file="footer.jsp" %>