<?php
    echo('<ul>');
    $dir=opendir("pages");
    while ($file=readdir($dir)) {
        if (!preg_match('#\.#',$file)) {
            echo '<li>'.$file.'';
            echo '<ul>';
            $dir2=opendir('pages/'.$file);
            while ($file2=readdir($dir2)) {
                if (preg_match('#\.php$#',$file2)) {
                    $action=preg_replace('#^(.*)\.php$#','$1',$file2);
                    echo '<li><a href="?page='.$file.'&action='.$action.'">'.$action.'</a></li>';
                }
            }
            echo '</ul></li>';
        }
    }
    closedir($dir);
    echo('</ul>');
?>
