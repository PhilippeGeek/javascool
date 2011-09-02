<?php

$cache_get_contents_folder = file_exists('/home/groups/javascool/htdocs') ? '/home/groups/javascool/htdocs/.http.cache' : '.http.cache';

// Lecture du wiki à  travers un cache local
function cache_get_contents($name, $function) {
  global $cache_get_contents_folder;
  $name = rawurlencode($name);
  // Utilisation d'un cache local
  if (!file_exists($cache_get_contents_folder)) 
    mkdir($cache_get_contents_folder, 0777);
  if (!file_exists($cache_get_contents_folder.'/'.$name)) {
    file_put_contents($cache_get_contents_folder.'/'.$name, call_user_func($function, $name));
    chmod($cache_get_contents_folder.'/'.$name, 0666);
  }
  return file_get_contents($cache_get_contents_folder.'/'.$name);
}

// Vide le cache 
if(isset($_GET['kezako']) && $_GET['kezako'] == 'niquelekacheux') { 
  passthru("rm -rf $cache_get_contents_folder"); echo 'wraz'; exit; 
}

?>