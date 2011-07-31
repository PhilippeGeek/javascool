<?php showBrowser(array(array("Java's Cool", "index.php"), array("Proglets", ""))); ?>
<div class="proglets">
    <table class="proglets">
        <tr>
            <?php
            $sketchbook = opendir('proglets');
            $proglets = array();
            $i = 0;
            while ($file = readdir($sketchbook)) {
                if (!preg_match('#^\.#', $file)) {
                    $proglets[$i] = $file;
                    $i++;
                }
            }
            foreach ($proglets as $id) {
                if (!preg_match('#\.|^tmp#',$id)) {
                    Sal::validateProgletId($id);
                    if (!is_file("proglets/" . $id . "/proglet.php"))
                        die("La proglet " . $id . " n'a pas de fichier proglet.php");
                    $proglet=null;
                    include("proglets/" . $id . "/proglet.php");  //TODO testme
                    if (isset($proglet['title'])) $name=$proglet['title']; else $name="";
                    if (isset($proglet['description'])) $desc=$proglet['description']; else $desc="";
                    if (isset($proglet['icon'])) $icon='proglets/'.$id.'/'.$proglet['icon']; else $icon="";
                    if ($name=="") $name=$id;

                    $defaulticon="images/defaultProglet.png";

                    if ($icon=="")
                        $icon=$defaulticon;
                    
                    if (!is_file($icon))
                        $icon=$defaulticon;
                    
                    echo('<script type="text/javascript">document.write(\'<td class="progletclickable" onClick="gotoloc(\\\'index.php?page=proglets&action=show&id=' . $id . '\\\')"><span>' . $id . '</span><span class="proglet-image"><img src="'.$icon.'" alt=""/></span></td>\');</script>');
                    echo('<noscript><td class="progletclickable"><a href="index.php?page=proglets&action=show&id=' . $id . '"><span>' . $id . '</span><span class="proglet-image"><img style="border: 0px" src="'.$icon.'" alt=""/></span></a></td></noscript>');
                }
            }
            ?>
        </tr>
    </table>
</div>
