<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
    <title>Réservation</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <%@ include file="sidebar.jsp" %>

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h2><i class="bi bi-bookmark-plus me-2"></i>Nouvelle réservation</h2>
            </div>

            <div class="card card-form">
                <div class="card-body">
                    <%-- Messages d'erreur/succès --%>
                    <%@ include file="alerts.jsp" %>

                    <form method="post" action="<%= request.getContextPath() %>/reservation/effectuer">
                        <div class="row g-3">
                            <% if(!isLoggedIn) { %>
                            <div class="mb-3">
                                <label for="adherentId" class="form-label">Numéro d'adhérent</label>
                                <input type="number" name="adherentId" id="adherentId" class="form-control" required>
                            </div>
                            <% } else { %>
                                <input type="hidden" name="adherentId" value="<%= adherent.getId() %>">
                            <% } %>
                            
                            <div class="mb-3">
                                <label for="dateReservation" class="form-label">Date de réservation</label>
                                <input type="date" name="dateReservation" id="dateReservation" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Exemplaire</label>
                                <select name="exemplaireId" class="form-select" required>
                                    <option value="">-- Sélectionnez un exemplaire --</option>
                                    <% 
                                    List<Exemplaire> exemplaires = (List<Exemplaire>) request.getAttribute("exemplaires");
                                    for (Exemplaire exemplaire : exemplaires) { 
                                    %>
                                        <option value="<%= exemplaire.getId() %>">
                                            <%= exemplaire.getLivre().getNom() %> (Ex. <%= exemplaire.getId() %>)
                                        </option>
                                    <% } %>
                                </select>
                            </div>
                        </div>

                        <div class="mt-4">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-bookmark-check me-2"></i>Confirmer la réservation
                            </button>
                        </div>
                    </form>
                </div>
            </div>

    <%@ include file="footer.jsp" %>