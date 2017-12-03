<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>
<c:if test="${error}">
    <div class="alert alert-danger" role="alert">
        Name, teacher names and subjects can contain only letters. Use picker to set date.
    </div>
</c:if>
<form action="/storage/add/" method="post">
    <div class="form-group row">
        <label for="name" class="col-sm-2 col-form-label">Name: </label>
        <div class="col-sm-10">
            <input type="text" id="name" required pattern="^[A-Z].{0,100}$"
                   title="Name can contain only letters." class="form-control" name="name" placeholder="Enter name">
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
<jsp:include page="../includes/end.jsp"/>
<script src="/static/js/validation.js"></script>