<?php

// Renvoie le texte sur http://wiki.inria.fr/sciencinfolycee avec mise en forme des liens
function wiki_get_contents($name) {
  // return cache_get_contents_exists($name) ? cache_get_contents_get($name) : cache_get_contents_put($name, wiki_get_contents_load($name));
  $text = file_get_contents('v4/cache/'+$name, FILE_USE_INCLUDE_PATH);
  if ($text === FALSE)
    echo "<hr>/home/groups/javascool/htdocs/v4/cache/".$name."<hr><tt>Désolé le site est cassé suite à une erreur des services informatiques Inria</tt><hr>";
  else
    return $text;
  /*
  $txt0 = file_get_contents('/home/groups/javascool/htdocs/v4/cache/'+$name, FILE_USE_INCLUDE_PATH);
  $txt1 = file_get_contents('./cache/'+$name, FILE_USE_INCLUDE_PATH);
  $txt2 = file_get_contents('./v4/cache/'+$name, FILE_USE_INCLUDE_PATH);
  if ($txt0 === FALSE) echo "<br>KK0 :".file_exists('/home/groups/javascool/htdocs/v4/cache/'+$name)."!";
  if ($txt1 === FALSE) echo "<br>KK1 :".file_exists('./cache/'+$name)."!";
  if ($txt2 === FALSE) echo "<br>KK2 :".file_exists('./v4/cache/'+$name)."!";
  */
}

?>