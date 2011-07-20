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
            
            function loaded() {
                applyLabelStyles();
                addListeners();/*
                showButtons();
                plugContent();*/
                animationrunning=true;
                updateAnimation(1);
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
            
            function addListeners() {
                var lefts=getElementsByClass('menubegin');
                for (i=0; i<lefts.length; i++) {
                    lefts[i].addEventListener("mouseover", mouseOverMenuSide, false);
                    lefts[i].addEventListener("mouseout", mouseOutMenuSide, false);
                    lefts[i].addEventListener("click", mouseClickMenuSide, false);
                }
                var rights=getElementsByClass('menuend');
                for (i=0; i<rights.length; i++) {
                    rights[i].addEventListener("mouseover", mouseOverMenuSide, false);
                    rights[i].addEventListener("mouseout", mouseOutMenuSide, false);
                    rights[i].addEventListener("click", mouseClickMenuSide, false);
                }
                var middles=getElementsByClass('menuitem');
                for (i=0; i<middles.length; i++) {
                    middles[i].addEventListener("mouseover", mouseOverMenu, false);
                    middles[i].addEventListener("mouseout", mouseOutMenu, false);
                }
                window.addEventListener("resize", windowResized, false);
            }
            
            function windowResized() {
                if (animationrunning) return;
                framePosition=getPos(document.getElementById("header"));
                var w=getSize(document.getElementById("header")).width;
                document.getElementById("plugright").style.left=(framePosition.left+w+316-document.getElementById("plugright").offsetWidth)-(animationend-30)*3+"px";
                document.getElementById("plugleft").style.left=(framePosition.left-316)+(frame-30)*3+"px";
            }
            
            function mouseOverMenuSide(event) {
                var menuItemName=this.id.substr(0,this.id.length-2);
                document.getElementById(menuItemName).className="menuitemselected";
                document.getElementById(menuItemName+"_l").className="menuselectedbegin";
                document.getElementById(menuItemName+"_r").className="menuselectedend";
            }
            
            function mouseOutMenuSide(event) {
                var menuItemName=this.id.substr(0,this.id.length-2);
                document.getElementById(menuItemName).className="menuitem";
                document.getElementById(menuItemName+"_l").className="menubegin";
                document.getElementById(menuItemName+"_r").className="menuend";
            }
            
            function mouseOverMenu(event) {
                var menuItemName=this.id;
                document.getElementById(menuItemName).className="menuitemselected";
                document.getElementById(menuItemName+"_l").className="menuselectedbegin";
                document.getElementById(menuItemName+"_r").className="menuselectedend";
            }
            
            function mouseOutMenu(event) {
                var menuItemName=this.id;
                document.getElementById(menuItemName).className="menuitem";
                document.getElementById(menuItemName+"_l").className="menubegin";
                document.getElementById(menuItemName+"_r").className="menuend";
            }
            
            function mouseClickMenuSide(event) {
                var menuItemName=this.id.substr(0,this.id.length-2);
                var elm=document.getElementById(menuItemName);
                elm.onclick();
            }
            
            function gotoloc(loc) {
                
                document.location=loc;
            }
            
            var w=300;
            var h=200;
            var frame=1;
            
            function showButtons() {
                var middles=getElementsByClass('menuitem');
                for (i=0; i<middles.length; i++) {
                    middles[i].width=w+"px";
                }
                w--;
                if (w>0)
                    setTimeout("showButtons()",1);
            }
            
            function plugContent() {
                document.getElementById("mainPanel").style.marginTop=h+"px";
                
                h--;
                if (h>0) 
                    setTimeout("plugContent()",1);
            }
            function getPos(obj) {
                var output = new Object();
                var mytop=0, myleft=0;
                while( obj) {
                    mytop+= obj.offsetTop;
                    myleft+= obj.offsetLeft;
                    obj= obj.offsetParent;
                }
                output.left = myleft;
                output.top = mytop;
                return output;
            }
            
            function getSize(obj) {
                var output=new Object();
                output.width=obj.offsetWidth;
                output.height=obj.offsetHeight;
                return output;
            }
            
            var animationend=85;
            var animationrunning=false;
            
            function updateAnimation(increment) {
                var plugleft=document.getElementById("plugleft");
                var plugright=document.getElementById("plugright");
                framePosition=getPos(document.getElementById("header"));
                var w=getSize(document.getElementById("header")).width;
                
                if (frame<30) {
                    plugleft.style.top=120-frame*3+"px";
                    plugleft.style.left=(framePosition.left-316)+"px";
                    plugright.style.top=120-frame*3+"px";
                    plugright.style.left=(framePosition.left+w+316-document.getElementById("plugright").offsetWidth)+"px";
                }
                if (frame>30) {
                    plugleft.style.left=(framePosition.left-316)+(frame-30)*3+"px";
                    plugleft.style.top=30;
                    plugright.style.left=(framePosition.left+w+316-document.getElementById("plugright").offsetWidth)-(frame-30)*3+"px";
                    plugright.style.top=30;
                }
                
                if (frame==80) {
                    var lights=getElementsByClass("pluglight");
                    for (i=0; i<lights.length; i++) {
                        lights[i].src="images/ledgreen.png";
                    }
                }
                
                frame+=increment;
                if (frame<animationend)
                    setTimeout("updateAnimation("+increment+")",10);
                else
                    animationrunning=false;
            }

        </script>

    </head>
    <body onload="loaded()">
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
        <div class="main2" id="mainPanel">
            <table class="main2"><tr class="top"><td class="topleft"></td><td class="top"></td><td class="topright"></td></tr>
                <tr class="center"><td class="left"></td><td class="center">
                        <table style="width: 100%">
                            <tr>
                                <td>
                                    <img src="images/ledred.png" style="width: 50px; height: 50px;" class="pluglight" />
                                </td>
                                <td style="width: 100%"></td>
                                <td>
                                    <img src="images/ledred.png" style="width: 50px; height: 50px;" class="pluglight" />
                                </td>
                            </tr>
                        </table>
                        <?php
                        include($include);
                        ?>
                    </td><td class="right"></td></tr>
                <tr class="bottom"><td class="bottomleft"></td><td class="bottom"></td><td class="bottomright"></td></tr></table>
        </div>

    </body>
</html>
