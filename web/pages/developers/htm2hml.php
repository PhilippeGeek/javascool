<?php
    showBrowser(
        array(
            array("Java's Cool","?"),
            array("Développeurs","?page=developers"),
	    array("Comment ça marche","?page=developers&action=spec-proglets"),
	    array("HML","")
        ),array(
	    array("Java's Cool Builder","?page=developers&action=spec-javascoolbuilder")
	)
    );
  include('pages/developers/xml_to_xml.xslt');
  include('pages/developers/htm_to_xml.xslt');
?>
<form action="http://javascool.gforge.inria.fr/index.php?page=developers&action=htm2hml" method="post" enctype="text/plain"><table align="center">
<tr><th>Votre texte HTML</th><th><input type="submit" value="Traduire"/> en HML</th></tr>
<tr><td><textarea name="htm" rows="32" cols="80"></textarea></td><td><textarea name="hml" rows="32" cols="80" readonly="readonly">
<?php
 if(isset($_REQUEST['htm']))
   echo xml_to_xml(htm_to_xml($_REQUEST['htm']), file_get_contents('pages/developers/htm2hml.xslt'));
?>
</textarea></td></tr>
</table></form>