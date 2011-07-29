<?php
    if (!isset($_GET['id']))
        die('Error');
    $id = $_GET['id'];
    Sal::validateProgletId($id);
    if (!is_file("proglets/" . $id . "/proglet.php"))
        die("La proglet " . $id . " n'a pas de fichier proglet.php");
    $proglet=null;
    include("proglets/" . $id . "/proglet.php");  //TODO testme
    if (isset($proglet['name'])) $name=$proglet['name']; else $name="";
    if (isset($proglet['description'])) $desc=$proglet['description']; else $desc="";
    if (isset($proglet['icon'])) $icon='proglets/'.$id.'/'.$proglet['icon']; else $icon="";
    if ($name=="") $name=$id;

    $defaulticon="images/defaultProglet.png";
                
    if ($icon=="")
        $icon=$defaulticon;
    if (!is_file($icon))
        $icon=$defaulticon;
?>

<?php showBrowser(array(array("Java's Cool","index.php"),array("Proglets","index.php?page=proglets"),array($name,""))); ?>

<table>
    <tr>
        <?php
        echo('<script type="text/javascript">document.write(\'<td class="progletclickable" onClick="gotolocnow(\\\'proglets/javascool-proglet-' . $id . '.jar\\\')"><span>' . $id . '</span><span class="proglet-image"><img src="'.$icon.'" alt=""/></span></td>\');</script>');
                    echo('<noscript><td class="progletclickable"><a href="proglets/javascool-proglet-' . $id . '.jar"><span>' . $id . '</span><span class="proglet-image"><img style="border: 0px" src="'.$icon.'" alt=""/></span></a></td></noscript>');
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
                <p><div style="max-width: 100%"><?php include('proglets/'.$id.'/help.htm'); ?></div></p>
            </td>
            <td class="news-rightborder"></td>
            <td class="news-right"></td>
        </tr>
        <tr class="news-bottom">
            <td colspan="5" class="news-bottom"></td>
        </tr>
    </table>
</div>
