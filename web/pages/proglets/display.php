<table><tr><td><span class="labelclickable" onclick="gotoloc('index.php');">Java's Cool</span><span class="label-arrow"></span><span onclick="gotoloc('index.php');" class="label">Proglets</span></td></tr></table><br />
<div class="proglets">
    <table class="proglets">
        <tr>
            <?php
            $proglets = array("ingredients", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game", "game");
            foreach ($proglets as &$id) {
                $name = Sal::progletIdToName($id);
                echo('<td class="progletclickable" onClick="gotoloc(\'index.php?page=proglets&action=show&id=' . $id . '\')"><span>' . $name . '</span><span class="proglet-image"><img src="images/' . $id . '.png" alt=""/></span></td>');
            }
            ?>
        </tr>
    </table>
</div>
