<?php // Mécanisme de report d'erreur à travers le web
include('includes/mailto.php');

if (isset($_GET['report']))
  mailto("mailto:thierry.vieville@inria.fr?subject=javascool-web-alert", "Spurious page=".$_GET['report']);

?>
