<?php
/** Renvoie le path debarassé des «../» et des «./» redondants. */
function html_get_normalized_url($path) {
  $newpath = ereg_replace("^\./", "", ereg_replace("([:/])[^\.:/][^/:]*/\.\.", "\\1", ereg_replace("(/+|/\./)", "/", "./$path")));
  if (ereg(":/?\.\.", $newpath))
    $path = $newpath = "";
  return $newpath == $path ? $path : html_get_normalized_url($newpath);
}
?>
