<?php
    $apiurl=$_GET['api'];
    if ($apiurl=='') $apiurl='overview-summary.html';
    Sal::validateApiUrl($apiurl);
    $api=file_get_contents("api/".$apiurl);
    $api=preg_replace('#^.*<BODY[^>]*>#sm','',$api);
    $api=preg_replace('#^</BODY[^>]*>.*#sm','',$api);
    $api=preg_replace('#<A HREF=\"[^"]*?[^"]*\"[^>]*><B>(NO )?FRAMES</B></A>#i','',$api);
    $api=preg_replace('#<A HREF=\"([^"\#][^"]*)\"[^>]*>((.(?!</A>))*).</A>#i','<a href="?page=api&api='.dirname($apiurl).'/$1">$2</a>',$api);
    $api=preg_replace('#[^"= /.]+/[^"= /.]+/[^"= /.]+/[^"= /.]+/\.\./\.\./\.\./\.\./#','',$api);
    $api=preg_replace('#[^"= /.]+/[^"= /.]+/[^"= /.]+/\.\./\.\./\.\./#','',$api);
    $api=preg_replace('#[^"= /.]+/[^"= /.]+/\.\./\.\./#','',$api);
    $api=preg_replace('#[^"= /.]+/\.\./#','',$api);
    $api=preg_replace('#=\./#','=',$api);
    echo '<div id="javadoc">'.$api.'</div>';
?>