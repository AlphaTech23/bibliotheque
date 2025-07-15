<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
    <title>Historique des Réservations</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <%@ include file="sidebar.jsp" %>
    <% List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations"); %>

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h2><i class="bi bi-calendar-check me-2"></i>Historique des réservations</h2>
            </div>

            <!-- Formulaire de filtrage -->
            <div class="card mb-4">
                <div class="card-body">
                    <form method="get" action="" class="row g-3">
                        <% if(!isLoggedIn) { %>
                        <div class="col-md-3">
                            <label class="form-label">Nom / Email</label>
                            <input type="text" name="nom" class="form-control" 
                                   value="${not empty param.nom ? param.nom : ''}">
                        </div>
                        <% } %>
                        <div class="col-md-3">
                            <label class="form-label">Livre</label>
                            <input type="text" name="livre" class="form-control" 
                                   value="${not empty param.livre ? param.livre : ''}">
                        </div>
                        <div class="col-md-2">
                            <label class="form-label">Date début</label>
                            <input type="date" name="dateDebut" class="form-control" 
                                   value="${not empty param.dateDebut ? param.dateDebut : ''}">
                        </div>
                        <div class="col-md-2">
                            <label class="form-label">Date fin</label>
                            <input type="date" name="dateFin" class="form-control" 
                                   value="${not empty param.dateFin ? param.dateFin : ''}">
                        </div>
                        <div class="col-md-2">
                            <label class="form-label">Statut</label>
                            <select name="statut" class="form-select">
                                <option value="">-- Tous --</option>
                                <option value="encours" ${param.statut eq 'encours' ? 'selected' : ''}>En cours</option>
                                <option value="valide" ${param.statut eq 'valide' ? 'selected' : ''}>Validée</option>
                                <option value="refusee" ${param.statut eq 'refusee' ? 'selected' : ''}>Refusée</option>
                            </select>
                        </div>
                        <div class="col-md-2 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary w-100">
                                <i class="bi bi-funnel me-1"></i> Filtrer
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Tableau des réservations -->
            <% if (reservations != null && !reservations.isEmpty()) { %>
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
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
                                    <td>
                                        <span class="badge bg-<%= 
                                            r.getValide() == null ? "warning" : 
                                            r.getValide() ? "success" : "danger" 
                                        %>">
                                            <%= statut %>
                                        </span>
                                    </td>
                                    <% if(!isLoggedIn) { %>
                                    <td>
                                        <% if (r.getValide() == null) { %>
                                            <div class="btn-group btn-group-sm" role="group">
                                                <form action="${pageContext.request.contextPath}/reservation/valider" method="post">
                                                    <input type="hidden" name="id" value="<%= r.getId() %>"/>
                                                    <button type="submit" class="btn btn-success">
                                                        <i class="bi bi-check-circle"></i> Accepter
                                                    </button>
                                                </form>
                                                <form action="${pageContext.request.contextPath}/reservation/refuser" method="post">
                                                    <input type="hidden" name="id" value="<%= r.getId() %>"/>
                                                    <button type="submit" class="btn btn-danger">
                                                        <i class="bi bi-x-circle"></i> Refuser
                                                    </button>
                                                </form>
                                            </div>
                                        <% } else { %>
                                            —
                                        <% } %>
                                    </td>
                                    <% } %>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } else { %>
                <div class="alert alert-info">
                    <i class="bi bi-info-circle"></i> Aucune réservation trouvée.
                </div>
            <% } %>

    <%@ include file="footer.jsp" %>