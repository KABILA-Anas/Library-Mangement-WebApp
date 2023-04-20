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
                    <a class="nav-link navlink active_link_color" href="Books">Livres</a><span class="active_link_line"></span>
                </li>
                <c:if test="${connect != null}">
                    <li class="nav-item underline">
                        <a class="nav-link navlink " href="Adherent">Adhérents</a>
                    </li>
                    <li class="nav-item underline">
                        <a class="nav-link navlink " href="Emprunt">Emprunts</a>
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
                    <button type="button" class="btn btn-outline-dark" >
                        <a href="Login">Se Connecter</a></button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

    <div class="title">
        <h1>Liste des livres</h1>
    </div>
    <div>
        <div class="search">
            <form method="get" action="Books">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="Auteur..." aria-label="Auteur..." aria-describedby="button-addon2" name="auteur">
                    <button class="btn btn-outline-secondary" type="submit" id="button-addon2" name="operation" value="search">Search</button>
                </div>
            </form>
            <c:if test="${connect != null}">
                <button id="create" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#createModal">Ajouter un livre</button>
            </c:if>
        </div>
        <c:if test="${primkeylivre != null}" >
            <div id="primkey" class="alert alert-danger" role="alert">
                Il existe deja un livre avec l'ISBN : ${primkeylivre}
            </div>
        </c:if>
        <c:if test="${FKLivre != null}" >
            <div id="primkey" class="alert alert-danger" role="alert">
                Impossible de supprimer le livre avec l'ISBN : ${FKLivre}, il a deja des emprunts
            </div>
        </c:if>
        <table class="table table-dark table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">ISBN</th>
                <th scope="col">Titre</th>
                <th scope="col">Auteur</th>
                <th scope="col">Exemplaires</th>
                <c:if test="${connect != null}">
                    <th scope="col">Action</th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${livres}" var="livre">

                <tr>
                    <th scope="row">${i = i + 1}</th>
                    <td> <c:out value="${livre.isbn}" /> </td>
                    <td> <c:out value="${livre.titre}" /> </td>
                    <td> <c:out value="${livre.auteur}" /> </td>
                    <td> <c:out value="${livre.nbrexemp}" /> </td>
                    <c:if test="${connect != null}">
                        <td>
                            <button class="btn btn-outline-warning" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever=${livre.isbn}>Modifier</button>
                            <button id="dl" class="btn btn-outline-danger"><a href='Books?id=${livre.isbn}&operation=delete'>Supprimer</a></button>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${livres.size() == 0}">
            <div id="livreEmpty" class="alert alert-warning" role="alert">
                Aucun livre existe!
            </div>
        </c:if>
    </div>
    <div class="modal fade" id="createModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="createModalLavel">Nouveau livre</h1>
                </div>
                <form method="post" action="Books">
                    <div class="modal-body">
                            <div class="mb-3">
                                <label for="isbn" class="col-form-label">ISBN :</label>
                                <input type="text" class="form-control" id="isbn" name="isbn">
                            </div>
                            <div class="mb-3">
                                <label for="title" class="col-form-label">Titre :</label>
                                <input type="text" class="form-control" id="title" name="title">
                            </div>
                            <div class="mb-3">
                                <label for="auteur" class="col-form-label">Auteur :</label>
                                <input type="text" class="form-control" id="auteur" name="auteur">
                            </div>
                            <div class="mb-3">
                                <label for="exemp" class="col-form-label">Nombre d'exemplaires :</label>
                                <input type="text" class="form-control" id="exemp" name="exemp">
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
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Update Book</h1>
                </div>
                <form method="post" action="Books">
                    <div class="modal-body">
                            <div class="mb-3">
                                <label for="recipient-name" class="col-form-label">ISBN :</label>
                                <input type="text" class="form-control" id="recipient-name" name="isbn" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="titleU" class="col-form-label">Titre :</label>
                                <input type="text" class="form-control" id="titleU" name="title">
                            </div>
                            <div class="mb-3">
                                <label for="auteurU" class="col-form-label">Auteur :</label>
                                <input type="text" class="form-control" id="auteurU" name="auteur">
                            </div>
                            <div class="mb-3">
                                <label for="exempu" class="col-form-label">Nombre d'exemplaires :</label>
                                <input type="text" class="form-control" id="exempu" name="exemp">
                            </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="submit" class="btn btn-primary" name="operation" value="update">Enregistrer</button>
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
