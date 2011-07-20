<?php

/*
 * Security abstraction layer
 */
class Sal {

    public function validatePage($page) {
        if (!preg_match('#^[a-z][a-z0-9]+$#', $page))
            die("Error : page name not valid");
    }

    public function validateAction($action) {
        if (!preg_match('#^[a-z][a-z0-9]+$#', $action))
            die("Error : action name not valid");
    }

    public function validateProgletId($progletId) {
        if (!preg_match('#^[a-z][a-z0-9]+$#', $progletId))
            die("Error : proglet id not valid");
    }

    public function progletIdToName($progletId) {
        if ($progletId == 'game')
            return 'Jeux';
        else if ($progletId == 'ingredients')
            return 'Ingr&eacute;dients';
    }

}
?>
