<?php showBrowser(array(array("Java's Cool","index.php"),array("Proglets",""))); ?>
<div class="proglets">
    <table class="proglets">
        <tr>
            <?php
            $proglets = array("ingredients", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game");
            foreach ($proglets as &$id) {
                $name = Sal::progletIdToName($id);
                echo('<script type="text/javascript">document.write(\'<td class="progletclickable" onClick="gotoloc(\\\'index.php?page=proglets&action=show&id=' . $id . '\\\')"><span>' . $name . '</span><span class="proglet-image"><img src="images/' . $id . '.png" alt=""/></span></td>\');</script>');
                echo('<noscript><td class="progletclickable"><a href="index.php?page=proglets&action=show&id=' . $id . '"><span>' . $name . '</span><span class="proglet-image"><img style="border: 0px" src="images/' . $id . '.png" alt=""/></span></a></td></noscript>');
            }
            ?>
        </tr>
    </table>
</div>
