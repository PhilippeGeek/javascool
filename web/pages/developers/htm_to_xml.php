<?php # Use: imp ; ereg_replace

/** Converts a HTML string into a XHTML logical-structure.
 * - Accentuation is re-encoded in ISO-8859-1, spurious constructs (comments, instructions, ..) are deleted and empty tags are terminated by '/>'.
 * - This is a fragible command in the sense that unexpected spurious or non-well formed HTML syntax may defeat it.
 * @param string The HTML string.
 * @return The XHTML transformation
 */
function htm_to_xml($string) {
  // Eliminate html accentuation
  $string = 
    ereg_replace("&agrave;", "à", 
    ereg_replace("&acirc;", "â", 
    ereg_replace("&eacute;", "é", 
    ereg_replace("&egrave;", "è", 
    ereg_replace("&euml;", "ë", 
    ereg_replace("&ecirc;", "ê", 
    ereg_replace("&iuml;", "ï", 
    ereg_replace("&icirc;", "î", 
    ereg_replace("&ouml;", "ö", 
    ereg_replace("&ocirc;", "ô", 
    ereg_replace("&ugrave;", "ù", 
    ereg_replace("&ccedil;", "ç", 
  $string))))))))))));
  // Eliminate spurious constructs
  $string = ereg_replace("<[!?][^>]*>","", ereg_replace("&nbsp;","", $string));
  // Encapsulate non XML HTML constructs
  $string = ereg_replace("(<(meta|img|hr|br|link)[^>/]*)/?>","\\1/>", $string);
  return $string;
}
?>
