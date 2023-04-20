<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="imports.jsp"%>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="Auth.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    <link href='https://fonts.googleapis.com/css?family=Poppins' rel='stylesheet'>
</head>
<body>
  <div class="title">
    <h1>Espace Admin</h1>
  </div>
  <form method="post" id="form" action="Books">
    <h2>Sign in</h2>
    <div class="input-control">
      <label for="username">Email</label>
      <input type="text" id="username" name="username">
      <div id="error" class="error"></div>
    </div>
    <div class="input-control">
      <label for="password">Password</label>
      <input type="password" id="password" name="password">
      <div id="perror" class="error"></div>
    </div>
    <button type="submit" name="operation" value="login">Login</button>
  </form>
  <c:if test="${logerr != null}" >
      <div class="alert alert-danger" role="alert">
          Login ou Password incorrect
      </div>
  </c:if>

</body>
</html>
