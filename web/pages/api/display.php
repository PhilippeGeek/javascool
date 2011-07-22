<?php
    $apiurl=$_GET['api'];
    Sal::validateApiUrl($apiurl);
    include("api/".$apiurl);
    //TODO
?>
