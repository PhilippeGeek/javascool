cat `pwd`/http.root/http.conf.src																													\
| sed "s/WEB_DIR/$(echo `pwd` | sed -e 's/\\/\\\\/g' -e 's/\//\\\//g' -e 's/&/\\\&/g')/g"		\
| sed "s/MOD_MIME/$(echo `locate mod_mime.so|tail -n 1` | sed -e 's/\\/\\\\/g' -e 's/\//\\\//g' -e 's/&/\\\&/g')/g"		\
| sed "s/MOD_PHP/$(echo  `locate libphp5.so |tail -n 1` | sed -e 's/\\/\\\\/g' -e 's/\//\\\//g' -e 's/&/\\\&/g')/g"		\
| sed "s/MOD_DIR/$(echo  `locate mod_dir.so |tail -n 1` | sed -e 's/\\/\\\\/g' -e 's/\//\\\//g' -e 's/&/\\\&/g')/g"		\
 > `pwd`/http.root/http.conf
 
 
/usr/sbin/httpd -f `pwd`/http.root/http.conf -k start

