<?php
if (!isset($_GET['id']))
    die('Error');
$progletId = $_GET['id'];
Sal::validateProgletId($progletId);
$progletName = Sal::progletIdToName($progletId);
?>

<table class="labelMain">
    <tr>
        <td>
            <span class="labelclickable" onclick="gotoloc('index.php');">Java's Cool</span>
            <span class="label-arrow"></span>
            <span class="labelclickable" onclick="gotoloc('index.php?page=proglets');">Proglets</span>
            <span class="label-arrow"></span>
            <span class="label"><?php echo $progletName ?></span>
        </td>
    </tr>
</table>
<br />

<table>
    <tr>
        <td class="proglet">
            <span><?php echo $progletName; ?></span>
            <span class="proglet-image"><img src="images/<?php echo $progletId; ?>.png" alt=""/></span>
        </td>
    </tr>
</table>
<br />

<div class="news">
    <table class="news">
        <tr class="news-top">
            <td colspan="5" class="news-top"></td>
        </tr>
        <tr class="news-center">
            <td class="news-left"></td>
            <td class="news-leftborder"></td>
            <td class="news-center">
                <p>abcd</p>
            </td>
            <td class="news-rightborder"></td>
            <td class="news-right"></td>
        </tr>
        <tr class="news-bottom">
            <td colspan="5" class="news-bottom"></td>
        </tr>
    </table>
</div>
