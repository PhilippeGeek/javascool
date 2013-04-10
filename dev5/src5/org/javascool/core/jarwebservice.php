<?php // Ajoute un fichier d'argument à une jarre pour lancer un web service
if (isset($_GET['jar']) && isset($_GET['name']) && isset($_GET['body'])) {
  $jar = $_GET['jar'];
  $fileName = $_GET['name'];
  $fileBody = $_GET['body'];

  // etc...
}

?>
