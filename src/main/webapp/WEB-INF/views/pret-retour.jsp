<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
    <title>Retour de prêt</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <%@ include file="sidebar.jsp" %>

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h2><i class="bi bi-journal-arrow-up me-2"></i>Retour d'exemplaire</h2>
            </div>

            <div class="card card-form">
                <div class="card-body">
                    <%-- Messages d'erreur/succès --%>
                    <%@ include file="alerts.jsp" %>

                    <form method="post" action="<%= request.getContextPath() %>/prets/retour">
                        <div class="row g-3">
                            <!-- Remplacer l'input exemplaire par un select -->
                            <div class="mb-3">
                                <label class="form-label">Exemplaire</label>
                                <select name="exemplaireId" class="form-select" required>
                                    <option value="">-- Sélectionnez un exemplaire --</option>
                                    <% 
                                    List<Exemplaire> exemplaires = (List<Exemplaire>) request.getAttribute("exemplaires");
                                    for (Exemplaire exemplaire : exemplaires) { 
                                    %>
                                        <option value="<%= exemplaire.getId() %>">
                                            <%= exemplaire.getLivre().getNom()  %> (Ex. <%= exemplaire.getId() %>)
                                        </option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="dateRetour" class="form-label">Date de retour</label>
                                <input type="date" class="form-control" name="dateRetour" id="dateRetour" required>
                            </div>
                        </div>

                        <div class="mt-4">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-check-circle me-2"></i>Enregistrer le retour
                            </button>
                        </div>
                    </form>
                </div>
            </div>

    <%@ include file="footer.jsp" %>