<?php
    $apiurl=$_GET['api'];
    Sal::validateApiUrl($apiurl);
    $api=file_get_contents("api/".$apiurl);
    $api=preg_replace('#^.*<BODY[^>]*>#sm','',$api);
    $api=preg_replace('#^</BODY[^>]*>.*#sm','',$api);
    $api=preg_replace('#<A HREF=\"[^"]*?[^"]*\"[^>]*><B>(NO )?FRAMES</B></A>#','',$api);
    $api=preg_replace('#<A HREF=\"([^"\#][^"]*)\"[^>]*>(.*)</A>#','<a href="?page=api&api='.dirname($apiurl).'/$1">$2</a>',$api);
    $api=preg_replace('#[^"= /.]+/[^"= /.]+/[^"= /.]+/[^"= /.]+/\.\./\.\./\.\./\.\./#','',$api);
    $api=preg_replace('#[^"= /.]+/[^"= /.]+/[^"= /.]+/\.\./\.\./\.\./#','',$api);
    $api=preg_replace('#[^"= /.]+/[^"= /.]+/\.\./\.\./#','',$api);
    $api=preg_replace('#[^"= /.]+/\.\./#','',$api);
    $api=preg_replace('#=\./#','=',$api);
    echo '<div id="javadoc">'.$api.'</div>';
?>
