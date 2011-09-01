<?php
    if (!isset($_GET['id']))
        die('Error');
    $id = $_GET['id'];
    Sal::validateProgletId($id);
    if (!is_file("proglets/" . $id . "/proglet.php"))
        die("La proglet " . $id . " n'est pas définie sur le site web.");
    $pml=null;
    include("proglets/" . $id . "/proglet.php");  //TODO testme
    if (isset($pml['name'])) $name=$pml['name']; else $name="";
    if (isset($pml['description'])) $desc=$pml['description']; else $desc="";
    if (isset($pml['icon'])) $icon='proglets/'.$id.'/'.$pml['icon']; else $icon="";
    if ($name=="") $name=$id;

    $defaulticon="images/defaultProglet.png";
                
    if ($icon=="")
        $icon=$defaulticon;
    if (!is_file($icon))
        $icon=$defaulticon;

    if (isset($_GET['helpFile'])) {
	$helpFile=$_GET['helpFile'];
	if (preg_match('#^[a-z0-9_-]+\.htm$#i',$helpFile)) $helpFile=$id.'/'.$helpFile;
	else if (preg_match('#^[a-z0-9_-]+/[a-z0-9_-]+\.htm$#i',$helpFile)) {}
	else if (preg_match('#^[a-z0-9_-]+#i',$helpFile)) $helpFile=$helpFile.'/help.htm';
	else die('Invalid help file name');
    }
    else
	$helpFile=$id.'/help.htm';
?>

<?php showBrowser(array(array("Java's Cool","index.php"),array("Proglets","index.php?page=proglets"),array($name,""))); ?>

<table>
    <tr>
        <?php
        echo('<script type="text/javascript">document.write(\'<td class="progletclickable" onClick="gotolocnow(\\\'proglets/'.$id.'/javascool-proglet-' . $id . '.jar\\\')"><span>' . $id . '</span><span class="proglet-image"><img src="'.$icon.'" alt=""/></span></td>\');</script>');
                    echo('<noscript><td class="progletclickable"><a href="proglets/'.$id.'/javascool-proglet-' . $id . '.jar"><span>' . $id . '</span><span class="proglet-image"><img style="border: 0px" src="'.$icon.'" alt=""/></span></a></td></noscript>');
                    ?>
    </tr>
</table>
<br />

<?php showButton(array('Voir la d&eacute;monstration','?page=proglets&action=demo&id='.$id)); ?>
<div class="news">
    <table class="news">
        <tr class="news-top">
            <td colspan="5" class="news-top"></td>
        </tr>
        <tr class="news-center">
            <td class="news-left"></td>
            <td class="news-leftborder"></td>
            <td class="news-center">
                <p><div style="max-width: 100%"><?php

	$help=file_get_contents('proglets/'.$helpFile);
	$help=preg_replace('#href="([a-zA-Z0-9_-]+)/([a-zA-Z0-9_-]+\.htm)"#','href="?page=proglets&action=show&id='.$id.'&helpFile=$1/$2"',$help);
	$help=preg_replace('#href="(\.\./\.\./)?([a-zA-Z0-9_-]+)/doc-files/([a-zA-Z0-9_-]+\.htm)"#','href="?page=proglets&action=show&id='.$id.'&helpFile=$2/$3"',$help);
	$help=preg_replace('#href="([a-zA-Z0-9_-]+\.htm)"#','href="?page=proglets&action=show&id='.$id.'&helpFile=$1"',$help);

	echo $help;
		?></div></p>
            </td>
            <td class="news-rightborder"></td>
            <td class="news-right"></td>
        </tr>
        <tr class="news-bottom">
            <td colspan="5" class="news-bottom"></td>
        </tr>
    </table>
</div>
