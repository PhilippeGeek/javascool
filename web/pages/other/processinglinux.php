<p><br />
</p>
<table id="toc" class="toc"><tr><td><div id="toctitle"><h2>Sommaire</h2></div>
<ul>
<li class="toclevel-1 tocsection-1"><a href="#How_to_nicely_install_processing_with_linux"><span class="tocnumber">1</span> <span class="toctext">How to nicely install processing with linux</span></a>
<ul>
<li class="toclevel-2 tocsection-2"><a href="#Initial_installation_to_have_processing_run"><span class="tocnumber">1.1</span> <span class="toctext">Initial installation to have processing run</span></a></li>
<li class="toclevel-2 tocsection-3"><a href="#How_to_choose_your_sketchbook_workdir"><span class="tocnumber">1.2</span> <span class="toctext">How to choose your sketchbook workdir</span></a></li>
<li class="toclevel-2 tocsection-4"><a href="#Using_you_own_processing_starter_.28makefile_commands.29"><span class="tocnumber">1.3</span> <span class="toctext">Using you own processing starter (makefile commands)</span></a></li>
</ul>
</li>
</ul>
</td></tr></table><script>if (window.showTocToggle) { var tocShowText = "afficher"; var tocHideText = "masquer"; showTocToggle(); } </script>
<h2> <span class="mw-headline" id="How_to_nicely_install_processing_with_linux"> How to nicely install processing with linux </span></h2>
<h3> <span class="mw-headline" id="Initial_installation_to_have_processing_run"> Initial installation to have processing run </span></h3>
<p>As root you have to
</p>
<ol><li> Install the Sun JDK6 in a standard location, e.g. <i>/usr/java</i> 
<ol><li> Gets, e.g. <i>jdk-6u21-linux-i586.bin</i>, from <a href="http://www.oracle.com/technetwork/java/javase/downloads/index.html" class="external free" rel="nofollow">http://www.oracle.com/technetwork/java/javase/downloads/index.html</a> in <i>/tmp</i>
</li><li> Runs <i>cd /usr/java&nbsp;; /tmp/jdk-6u21-linux-i586.bin</i> and follow instructions
</li></ol>
</li><li> Install the processing files
<ol><li> Gets, e.g. <i>processing-1.4.2.1.tgz</i> from <a href="http://processing.org/download" class="external free" rel="nofollow">http://processing.org/download</a> 
</li><li> Copy processing in a standard location, e.g. <i>/usr/java</i> 
<ol><li> <i>tar xvzf processing-1.4.2.1.tgz&nbsp;; mv processing-1.2.1 /usr/java&nbsp;; /bin/rm processing-1.4.2.1.tgz</i>
</li></ol>
</li><li> Adapt the preferences to Linux (assuimg here you use firefox and gnome)
<ol><li> <i>sed -i 's/brower.linux *= .*/browser.linux = firefox/'           /usr/java/processing-1.2.1/lib/preferences.txt</i>
</li><li> <i>sed -i 's/[#]?launcher.linux *= .*/launcher.linux = gnome-open/' /usr/java/processing-1.2.1/lib/preferences.txt</i> 
</li></ol>
</li></ol>
</li><li> Link the JDK6 to processing
<ol><li> <i>/bin/rm -rf /usr/java/processing-1.2.1/java&nbsp;; ln -s /usr/java/jdk1.6.0_21 /usr/java/processing-1.2.1/java</i>
</li></ol>
</li><li> You may have to install the proper sound/graphic librairies from the openjdk to the sunjdk (use <i>locate libpulse-java.so</i> to find the libraries in your system)
<ol><li> <i>cp /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0/jre/lib/i386/libpulse-java.so  /usr/java/jdk1.6.0_21/jre/lib/i386/</i>
</li><li> <i>cp /usr/lib/jvm/java-1.6.0-openjdk-1.6.0.0/jre/lib/ext/pulse-java.jar /usr/java/jdk1.6.0_21/</i>
</li><li> <i>cp /usr/java/processing-1.2.1/libraries/opengl/library/*.so /usr/java/jdk1.6.0_21/jre/lib/i386/</i>
</li></ol>
</li><li> To see the applets in firefox you may have to
<ol><li> <i>rpm -e java-1.6.0-openjdk-plugin</i> erase the open-jdk web browser plugin
</li><li> <i>ln -s /usr/java/jdk1.6.0_21/jre/plugin/i386/ns7/libjavaplugin_oji.so /usr/lib/mozilla/plugins</i> install manually the JRE plugin
</li><li> for <a href="http://blogs.sun.com/thinslice/entry/firefox_3_6_changes_the" class="external text" rel="nofollow">firefox-3.6</a> and latter 
<ol><li> <i>ln -s /usr/java/jdk1.6.0_22/jre/lib/i386/libnpjp2.so /usr/lib/mozilla/plugins</i> 
</li></ol>
</li></ol>
</li><li> Optionnaly install additional librairies if needed in  <i>/usr/java/processing-1.2.1/librairies</i>
</li></ol>
<h3> <span class="mw-headline" id="How_to_choose_your_sketchbook_workdir"> How to choose your sketchbook workdir </span></h3>
<ul><li> Run <i>/usr/java/processing-1.2.1/processing</i>
</li><li> In <i>$HOME/.processing/preferences.txt</i> edit the <i>sketchbook.path=&lt;my-workdir&gt;</i> line for your convinience
</li><li> rm <i>$HOME/sketchbook</i>
</li></ul>
<h3> <span class="mw-headline" id="Using_you_own_processing_starter_.28makefile_commands.29"> Using you own processing starter (makefile commands) </span></h3>
<pre>
P_HOME=/usr/java/processing-1.2.1
J_HOME=$(P_HOME)/java
export PATH:=$(J_HOME)/bin:$(PATH)
export CLASSPATH=$(shell echo '$(J_HOME)/lib/tools.jar $(J_HOME)/jre/lib/rt.jar $(wildcard $(P_HOME)/lib/*.jar)' | sed 's/ /:/g')

gui&nbsp;:
	@cd $(P_HOME)&nbsp;; java processing.app.Base
</pre>

<!-- 
NewPP limit report
Preprocessor node count: 18/1000000
Post-expand include size: 0/2097152 bytes
Template argument size: 0/2097152 bytes
Expensive parser function count: 0/100
-->

<!-- Saved in parser cache with key wiki_sciencinfolycee:pcache:idhash:120-1!1!0!!fr!2!edit=0!printable=1 and timestamp 20110721112923 -->
