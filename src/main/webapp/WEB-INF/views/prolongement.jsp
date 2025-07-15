<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
    <title>Prolongation</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <%@ include file="sidebar.jsp" %>

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h2><i class="bi bi-calendar2-plus me-2"></i>Prolongation de prêt</h2>
            </div>

            <div class="card card-form">
                <div class="card-body">
                    <%-- Messages d'erreur/succès --%>
                    <%@ include file="alerts.jsp" %>

                    <form method="post" action="<%= request.getContextPath() %>/prolongement/effectuer">
                        <div class="row g-3">
                            <div class="col-md-6">
                                <label for="pretId" class="form-label">Prêt</label>
                                <select name="pretId" class="form-select" id="pretId" required>
                                    <option value="">-- Sélectionnez un prêt --</option>
                                    <% 
                                    List<Pret> prets = (List<Pret>) request.getAttribute("prets");
                                    if (prets != null) {
                                        for (Pret pret : prets) { 
                                    %>
                                        <option value="<%= pret.getId() %>">
                                            Prêt #<%= pret.getId() %> - <%= pret.getExemplaire().getLivre().getNom() %> 
                                            (Retour prévu: <%= pret.getDateRetourPrevue() %>)
                                        </option>
                                    <% 
                                        } 
                                    }
                                    %>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label for="dateProlongement" class="form-label">Date prolongation</label>
                                <input type="date" name="dateProlongement" class="form-control" id="dateProlongement" required>
                            </div>
                        </div>

                        <div class="mt-4">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-check-circle me-2"></i>Valider la prolongation
                            </button>
                        </div>
                    </form>
                </div>
            </div>

    <%@ include file="footer.jsp" %>