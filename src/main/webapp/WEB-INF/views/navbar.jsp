<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Navbar principale -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <i class="bi bi-book me-2"></i>Bibliothèque
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="mainNavbar">
                <ul class="navbar-nav ms-auto">
                    <% if (session.getAttribute("bibliothecaire") != null) { %>
                        <li class="nav-item">
                            <span class="nav-link text-white">
                                <i class="bi bi-person-vcard me-1"></i>
                                ${sessionScope.bibliothecaire.nom}
                            </span>
                        </li>
                    <% } else if (session.getAttribute("adherent") != null) { %>
                        <li class="nav-item">
                            <span class="nav-link text-white">
                                <i class="bi bi-person me-1"></i>
                                ${sessionScope.adherent.nom}
                            </span>
                        </li>
                    <% } %>
                </ul>
            </div>
        </div>
    </nav>