<?php # Use: DOMDocument XSLTProcessor

/** Converts an XML string using a XSL transform.
 * - Available if the <tt>--with-xsl[=DIR]</tt> is defined, DIR being the <tt>libxslt</tt> library installation directory.
 * @param xml The XML source string.
 * @param xsl The XSL transformation string.
 * @return The transformed string.
 */
function xml_to_xml($xml, $xsl) {
  if (class_exists("DOMDocument") && class_exists("XSLTProcessor")) {
    $dxml = new DOMDocument; $dxml->loadXML($xml);
    $dxsl = new DOMDocument; $dxsl->loadXML($xsl);
    $proc = new XSLTProcessor; $proc->importStyleSheet($dxsl);
    return $proc->transformToXML($dxml);
  } else
    trigger_error("The ".(class_exists("DOMDocument") ? "" : "DOM")." ".(class_exists("XSLTProcessor") ? "" : "XSLT")." module(s) of libxslt undefined", E_USER_ERROR);
}
?>
