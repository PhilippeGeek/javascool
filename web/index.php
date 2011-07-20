<?php
include('includes/sal.class.php');
include("includes/get_wiki_page.php");

?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Java's Cool</title>
        <!--FIXME <link rel="stylesheet" type="text/css" href="pages/style.css" /> -->
        <?php
        ?>
        <style type="text/css"><?php include('pages/style.css'); //FIXME ?></style>

        <script type="text/javascript">
            function getElementsByClass( searchClass, domNode, tagName) { 
                if (domNode == null) domNode = document;
                if (tagName == null) tagName = '*';
                var el = new Array();
                var tags = domNode.getElementsByTagName(tagName);
                var tcl = " "+searchClass+" ";
                for(i=0,j=0; i<tags.length; i++) { 
                    var test = " " + tags[i].className + " ";
                    if (test.indexOf(tcl) != -1) 
                        el[j++] = tags[i];
                } 
                return el;
            } 

            function applyLabelStyles() {
                var labels=getElementsByClass('label');
                for (i=0; i<labels.length; i++) {
                    labels[i].innerHTML='<table class="label"><tr><td class="label-left"></td><td class="label-center">'+labels[i].innerHTML+'</td><td class="label-right"></td></tr></table>';
                }
                var labels=getElementsByClass('labelclickable');
                for (i=0; i<labels.length; i++) {
                    labels[i].innerHTML='<table class="labelclickable"><tr><td class="label-left"></td><td class="label-center">'+labels[i].innerHTML+'</td><td class="label-right"></td></tr></table>';
                }
            }

            function gotoloc(loc) {
                document.location=loc;
            }

        </script>

    </head>
    <body onload="applyLabelStyles()">
        <?php
        $page = (isset($_GET['page'])) ? $_GET['page'] : 'home';
        Sal::validatePage($page);
        $action = (isset($_GET['action'])) ? $_GET['action'] : 'display';
        Sal::validateAction($action);
        $include = 'pages/' . $page . '/' . $action . '.php';
        if (!is_file($include))
            die("Page not found");


        include('pages/header.php');
        ?>
        <div class="main2">
            <table class="main2"><tr class="top"><td class="topleft"></td><td class="top"></td><td class="topright"></td></tr>
                <tr class="center"><td class="left"></td><td class="center">

                        <?php
                        include($include);
                        ?>
                    </td><td class="right"></td></tr>
                <tr class="bottom"><td class="bottomleft"></td><td class="bottom"></td><td class="bottomright"></td></tr></table>
        </div>

    </body>
</html>
