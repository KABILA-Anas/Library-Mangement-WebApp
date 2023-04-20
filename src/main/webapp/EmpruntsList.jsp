<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="imports.jsp"%>
<%! int i = 0; %>
<html>
<head>
    <title>Books</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    <link href='https://fonts.googleapis.com/css?family=Poppins' rel='stylesheet'>
    <link rel="stylesheet" type="text/css" href="BooksList.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light position-fixed" style="z-index: 9; width: 100%; top: 0; background: #F3F5F8 !important;">
    <div class="container-fluid">
        <a class="navbar-brand navt d-lg-block d-lg-none" href="#"><img src="blogo.png" alt="" width="150" height="35"></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ">
                <li class="nav-item underline">
                    <a class="nav-link navlink" href="Books">Livres</a>
                </li>
                <c:if test="${connect != null}">
                    <li class="nav-item underline">
                        <a class="nav-link navlink " href="Adherent">Adhérents</a>
                    </li>
                    <li class="nav-item underline">
                        <a class="nav-link navlink active_link_color" href="Emprunt">Emprunts</a><span class="active_link_line"></span>
                    </li>
                </c:if>
            </ul>
        </div>
        <div class="menu" style="margin:5px;">
            <c:choose>
                <c:when test="${connect != null}">
                    <button id="so" type="button" class="btn btn-outline-dark"><a href='Books?operation=logout'>Se Deconnecter</a></button>
                </c:when>
                <c:otherwise>
                    <button type="button" class="btn btn-outline-dark" onclick="document.location.href='Login.jsp'">Se Connecter</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<div class="title">
    <h1>Liste des Emprunts</h1>
</div>
<div>
    <div class="search">
        <form method="get" action="Emprunt">
            <div class="input-group mb-3">
                <input type="text" class="form-control" placeholder="CIN" aria-label="CIN" aria-describedby="button-addon2" name="cin">
                <button class="btn btn-outline-secondary" type="submit" id="button-addon2" name="operation" value="search">Search</button>
            </div>
        </form>
        <c:if test="${connect != null}">
            <button id="create" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#createModal">Ajouter un emprunt</button>
        </c:if>
    </div>
    <c:if test="${minExemp != null}" >
        <div id="primkey" class="alert alert-danger" role="alert">
            Impossible d'emprunter le livre avec l'ISBN : ${minExemp}, il a aucun exemplaire disponible
        </div>
    </c:if>
    <table class="table table-dark table-striped">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">CIN</th>
            <th scope="col">ISBN</th>
            <th scope="col">Exemplaire</th>
            <th scope="col">Date d'emprunt</th>
            <th scope="col">Date retour</th>
            <th scope="col">Status</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${emprunts}" var="emprunt">

            <tr>
                <th scope="row">${i = i + 1}</th>
                <td> <c:out value="${emprunt.adherent.cin}" /> </td>
                <td> <c:out value="${emprunt.exemplaire.livre.isbn}" /> </td>
                <td> <c:out value="${emprunt.exemplaire.idExemp}" /> </td>
                <td> <c:out value="${emprunt.dateEmp}" /> </td>
                <td> <c:out value="${emprunt.dateRet}" /> </td>
                <td> <c:out value="${emprunt.status}" /> </td>
                <c:if test="${emprunt.status == 0}">
                    <td>
                        <button id="dl" class="btn btn-outline-danger"><a href='Emprunt?id=${emprunt.idEmp}&operation=update'>Render</a></button>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${emprunts.size() == 0}">
        <div id="livreEmpty" class="alert alert-warning" role="alert">
            Aucun emprunt existe!
        </div>
    </c:if>
</div>
<div class="modal fade" id="createModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="createModalLavel">Nouveau Emprunt</h1>
            </div>
            <form method="post" action="Emprunt">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="cin" class="col-form-label">CIN :</label>
                        <input type="text" class="form-control" id="cin" name="cin">
                    </div>
                    <div class="mb-3">
                        <label for="isbn" class="col-form-label">ISBN :</label>
                        <input type="text" class="form-control" id="isbn" name="isbn">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-primary" name="operation" value="create">Enregistrer</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
<script>
    const exampleModal = document.getElementById('exampleModal')
    exampleModal.addEventListener('show.bs.modal', event => {
        // Button that triggered the modal
        const button = event.relatedTarget
        // Extract info from data-bs-* attributes
        const isbn = button.getAttribute('data-bs-whatever')
        // If necessary, you could initiate an AJAX request here
        // and then do the updating in a callback.
        //
        // Update the modal's content.
        const modalTitle = exampleModal.querySelector('.modal-title')
        const modalBodyInput = exampleModal.querySelector('.modal-body input')

        modalBodyInput.value = isbn
    })
</script>
</html>
