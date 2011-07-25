<?php showBrowser(array(array("Java's Cool", "index.php"), array("Proglets", ""))); ?>
<div class="proglets">
    <table class="proglets">
        <tr>
            <?php
            $sketchbook = opendir('sketchbook');
            $proglets = array();
            $i = 0;
            while ($file = readdir($sketchbook)) {
                if (!preg_match('#^\.#', $file)) {
                    $proglets[$i] = $file;
                    $i++;
                }
            }
            foreach ($proglets as $id) {
                if (!preg_match('#\.#',$id)) {
                    Sal::validateProgletId($id);
                    if (!is_file("sketchbook/" . $id . "/proglet.php"))
                        die("La proglet " . $id . " n'a pas de fichier proglet.php");
                    $proglet=null;
                    include("sketchbook/" . $id . "/proglet.php");  //TODO testme
                    if (isset($pml['name'])) $name=$proglet['name']; else $name="";
                    if (isset($pml['description'])) $desc=$proglet['description']; else $desc="";
                    if (isset($pml['icon'])) $icon=$proglet['icon']; else $icon="";
                    if ($name=="") $name=$id;

                    $defaulticon="../../images/defaultProglet.png";

                    if ($icon=="")
                        $icon=$defaulticon;
                    else
                        Sal::validateAsIconFile($icon);
                    if (!is_file('sketchbook/' . $id . '/'.$icon))
                        $icon=$defaulticon;
                    echo('<script type="text/javascript">document.write(\'<td class="progletclickable" onClick="gotoloc(\\\'index.php?page=proglets&action=show&id=' . $id . '\\\')"><span>' . $name . '</span><span class="proglet-image"><img src="sketchbook/' . $id . '/'.$icon.'" alt=""/></span></td>\');</script>');
                    echo('<noscript><td class="progletclickable"><a href="index.php?page=proglets&action=show&id=' . $id . '"><span>' . $name . '</span><span class="proglet-image"><img style="border: 0px" src="sketchbook/' . $id . '/'.$icon.'" alt=""/></span></a></td></noscript>');
                }
            }
            ?>
        </tr>
    </table>
</div>
