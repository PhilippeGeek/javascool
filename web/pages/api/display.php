<?php
    $apiurl = isset($_GET['api']) ? html_contents_path_normalize($_GET['api']) : 'overview-summary.html';
    Sal::validateApiUrl($apiurl);
    $api=file_get_contents("api/".$apiurl);
    $api=preg_replace('#^.*<BODY[^>]*>#sm','',$api);
    $api=preg_replace('#^</BODY[^>]*>.*#sm','',$api);
    $api = html_contents_normalize($api, "?page=api&api=".dirname($apiurl), "/api/".dirname($apiurl));
    echo '<div id="javadoc">'.$api.'</div>';
?>
