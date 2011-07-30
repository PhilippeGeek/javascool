#
# Publication du site sur http://javascool.gforge.inria.fr/v4
#

# Commande rsync avec qq blindages
# - on exclue les repertoires .svn 
# - on filtre qq erreurs non pertinentes du rsync
rsync --rsh='ssh -C' --archive --delete-excluded --exclude .svn/ --exclude index.php ./* scm.gforge.inria.fr:/home/groups/javascool/htdocs/v4 \
 2>&1 | grep -v '^rsync: failed to set'
# - on copie le index.php en deux temps pour éviter un problème de blocage de droits d'accès
rsync --rsh='ssh -C' index.php scm.gforge.inria.fr:/home/groups/javascool/htdocs/v4/.htindex.php
ssh scm.gforge.inria.fr 'sh -c "cd /home/groups/javascool/htdocs/v4 ; cat .htindex.php > index.php ; rm .htindex.php"'
