<html>
<head></head>

<body>
<h3>Sz. P. <br>
    ${firstName} ${lastName}</h3>
<p>Miło nam poinformać, że weźmiesz udział w wycieczce: <b>${tripName}</b>!</p>
<p>Szczegółowe informacje znajdziesz poniżej: </p>
<p>
Data wyjazdu: <b>${beginDate}</b> <br>
Data powrotu: <b>${endDate}</b><br>
Kwota do zapłaty: <b>${pricePerPerson} zł</b><br>
    <a href="https://turistica.herokuapp.com/trip-details/${tripId}"> Strona wyjazdu</a>
</p>

<p> Życzymy udanego wyjazdu! </p>
<h3><b>Turistica Company</b></h3>
<a href="https://turistica.herokuapp.com/">turistica.herokuapp.com</a>
<br><br>
<p>Wiadomość została wygenerowana automatycznie. Prosimy na nią nie odpowiadać.</p>
</body>

</html>