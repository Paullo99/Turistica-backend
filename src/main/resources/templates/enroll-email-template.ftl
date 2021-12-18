<html>
<head></head>

<body>
<h3>Sz. P. <br>
    ${firstName} ${lastName}</h3>
<p>Miło nam poinformać, że weźmiesz udział w wycieczce: <b>${tripName}</b>!</p>
<p>Szczegółowe informacje znajdziesz poniżej: </p>
<p>
    <b>Data wyjazdu: </b>${beginDate}<br>
    <b>Data powrotu: </b>${endDate}<br>
    <b>Kwota do zapłaty: </b>${pricePerPerson} zł<br>
    <a href="https://turistica.herokuapp.com/trip-details/${tripId}"> Strona wyjazdu</a>
</p>
<br>
<p> Dane do przelewu: <br>
    <b>Nazwa odbiorcy: </b>Turistica Company <br>
    <b>Numer konta: </b>44 1000 1098 5794 4213 1900 7324 <br>
    <b>Waluta: </b>PLN <br>
    <b>Tytuł przelewu: </b>${tripId}_${firstName}_${lastName} <br>
</p>
<br>
<p> Życzymy udanego wyjazdu! </p>
<h3><b>Turistica Company</b></h3>
<a href="https://turistica.herokuapp.com/">turistica.herokuapp.com</a>
<br><br>
<p>Wiadomość została wygenerowana automatycznie. Prosimy na nią nie odpowiadać.</p>
</body>

</html>