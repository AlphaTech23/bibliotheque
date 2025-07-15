<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.example.bibliotheque.models.*" %>
<%
    List<Pret> prets = (List<Pret>) request.getAttribute("prets");
    List<Adherent> adherents = (List<Adherent>) request.getAttribute("adherents");
    List<TypePret> typesPret = (List<TypePret>) request.getAttribute("typesPret");
    
    // Récupération des paramètres de filtre
    Integer adherentId = (Integer) request.getAttribute("adherentId");
    Integer typePretId = (Integer) request.getAttribute("typePretId");
    String dateDebut = (String) request.getAttribute("dateDebut");
    String dateFin = (String) request.getAttribute("dateFin");
    String nom = (String) request.getAttribute("nom");
%>
<%@ include file="header.jsp" %>
    <title>Historique des prêts</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <%@ include file="sidebar.jsp" %>

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h2><i class="bi bi-clock-history me-2"></i>Historique des prêts</h2>
                <% if (!isLoggedIn) { %>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <a href="${pageContext.request.contextPath}/prets" class="btn btn-sm btn-outline-primary">
                        <i class="bi bi-plus-circle me-1"></i> Nouveau prêt
                    </a>
                </div>
                <% } %>
            </div>

            <!-- Formulaire de filtrage -->
            <div class="card mb-4">
                <div class="card-body">
                    <form method="get" class="row g-3">
                        <% if(!isLoggedIn) { %>
                        <div class="col-md-3">
                            <label class="form-label">Adhérent</label>
                            <select name="adherentId" class="form-select">
                                <option value="">-- Tous --</option>
                                <% for (Adherent a : adherents) { %>
                                    <option value="<%= a.getId() %>" 
                                        <%= (a.getId().equals(adherentId)) ? "selected" : "" %>>
                                        <%= a.getNom() %>
                                    </option>
                                <% } %>
                            </select>
                        </div>
                        <% } %>
                        <div class="col-md-3">
                            <label class="form-label">Type de prêt</label>
                            <select name="typePretId" class="form-select">
                                <option value="">-- Tous --</option>
                                <% for (TypePret t : typesPret) { %>
                                    <option value="<%= t.getId() %>" 
                                        <%= (t.getId().equals(typePretId)) ? "selected" : "" %>>
                                        <%= t.getLibelle() %>
                                    </option>
                                <% } %>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Date début</label>
                            <input type="date" name="dateDebut" class="form-control" 
                                   value="<%= dateDebut != null ? dateDebut : "" %>">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Date fin</label>
                            <input type="date" name="dateFin" class="form-control" 
                                   value="<%= dateFin != null ? dateFin : "" %>">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Recherche</label>
                            <input type="text" name="nom" class="form-control" placeholder="Nom du livre..."
                                   value="<%= nom != null ? nom : "" %>">
                        </div>
                        <div class="col-md-2 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary w-100">
                                <i class="bi bi-funnel me-1"></i> Filtrer
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Résultats -->
            <% if (prets != null && !prets.isEmpty()) { %>
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>Adhérent</th>
                                <th>Livre</th>
                                <th>Date prêt</th>
                                <th>Type</th>
                                <th>Retour prévu</th>
                                <th>Retour effectif</th>
                                <th>État</th>
                                <th>Prolongé</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Pret p : prets) { 
                                String etatBadge;
                                String etatText;
                                if (p.getDateRetour() == null) {
                                    etatBadge = "warning";
                                    etatText = "En cours";
                                } else if (p.getDateRetour().isAfter(p.getDateRetourPrevue())) {
                                    etatBadge = "danger";
                                    etatText = "En retard";
                                } else {
                                    etatBadge = "success";
                                    etatText = "Terminé";
                                }
                            %>
                                <tr>
                                    <td><%= p.getAdherent().getNom() %></td>
                                    <td><%= p.getExemplaire().getLivre().getNom() %></td>
                                    <td><%= p.getDatePret() %></td>
                                    <td><%= p.getTypePret().getLibelle() %></td>
                                    <td><%= p.getDateRetourPrevue() != null ? p.getDateRetourPrevue() : "—" %></td>
                                    <td><%= p.getDateRetour() != null ? p.getDateRetour() : "—" %></td>
                                    <td>
                                        <span class="badge bg-<%= etatBadge %>">
                                            <%= etatText %>
                                        </span>
                                    </td>
                                    <td>
                                        <% if (p.isProlonge()) { %>
                                            <i class="bi bi-check-circle text-success"></i>
                                        <% } else { %>
                                            <i class="bi bi-x-circle text-secondary"></i>
                                        <% } %>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } else { %>
                <div class="alert alert-info">
                    <i class="bi bi-info-circle me-1"></i> Aucun prêt trouvé avec ces critères.
                </div>
            <% } %>

    <%@ include file="footer.jsp" %>