<%@ page import="objects.Room" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.RoomsDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<html>
<body>
<h2>Hello to RoomsWithLamps World!</h2>
</body>
<%
    RoomsDAO roomsDAO = new RoomsDAO();
    List<Room> rooms = roomsDAO.getRooms();
    pageContext.setAttribute("rooms", rooms);
%>
<c:if test="${rooms != null}">
    <ul><c:forEach var="room" items="${rooms}">
        <li><a href="${pageContext.request.contextPath}/room/${fn:replace(room.roomName, ' ', '_').toLowerCase()}">${room.roomName}</a></li>
    </c:forEach></ul>
</c:if>
<div>
    <h2>Create your own room!</h2>
    <form method="POST" action="${pageContext.request.contextPath}/addroom">
        <p>Room name: </p><input type="text" placeholder="Room name" name="roomName"/>
        <p>Country:</p>
        <div class="dropdown">
            <select name="country">
                <option value="Belarus">Belarus</option>
                <option value="Russia">Russia</option>
                <option value="Germany">Germany</option>
                <option value="France">France</option>
                <option value="Italy">Italy</option>
            </select>
        </div>
        <input type="submit" value="Create!"/>
    </form>
</div>

</html>
