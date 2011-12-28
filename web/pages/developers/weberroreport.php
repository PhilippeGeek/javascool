<?php // Mécanisme de report d'erreur à travers le web
  //include('includes/mailto.php');
  if (isset($_GET['weberroreport']))
    mailto("mailto:thierry.vieville@inria.fr?subject=javascool-web-alert", "Report:\n".$_GET['weberroreport']);

?>
