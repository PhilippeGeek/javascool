# vthierry's ongoing tasks
#
# * Pb avec enVoiture reste vide, pete tout, exemple pas valide ! (repartir d'une JRE propre pour installer opengl)
#

ant :
	ant prun

tst : 
	ant javascool-proglets.jar ; /usr/java/jre1.6.0_11/bin/java -jar dist/javascool-proglets.jar

nbe :
	netbeans --jdkhome $(JHOME) &

www : 
	firefox http://code.google.com/p/javascool/wiki/JavaScoolOnGoingWork &

#jsrc>sedgrep -name '*.java' -in 'utf'
#./tools/Pml.java:    "<?xml version='1.0' encoding='utf-8'?>\n"
#./tools/Pml.java:    + "  <xsl:output method='xml' encoding='utf-8' omit-xml-declaration='yes'/>\n"
#./macros/Macros.java:            p.setContentType("text/html; charset=utf-8");
#./widgets/HtmlDisplay.java:      pane.setContentType("text/html; charset=utf-8");
#jsrc>sedgrep -name '*.java' -in 'UTF'
#./tools/Xml2Xml.java:   * <p>Elimine les entitées HTML connues (tout n'est pas implémenté) et passe à de l'accentuation UTF-8,
#./tools/UserConfig.java:            properties.storeToXML(new FileOutputStream(getApplicationFolder() + configFile), null, "UTF-8");
#./tools/ErrorCatcher.java:      System.setProperty("file.encoding", "UTF-8");
#./tools/FileManager.java: * <p>Lit/Ecrit un contenu textuel local ou distant en tenant compte de l'encodage UTF-8.</p>
#./tools/FileManager.java:    /** Lit un contenu textuel local ou distant en tenant compte de l'encodage UTF-8.
#./tools/FileManager.java:            BufferedReader reader = new BufferedReader(new InputStreamReader(Macros.getResourceURL(location, true).openStream(),"UTF-8"), 10240);
#./tools/FileManager.java:    /** Ecrit un contenu textuel local ou distant en tenant compte de l'encodage UTF-8.
#./tools/FileManager.java:        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
#./tools/FileManager.java:        return new OutputStreamWriter(new FileOutputStream(location), "UTF-8");
#./builder/ProgletsBuilder.java:                        + "\t-public\t-author\t-windowtitle\tJava's Cool v4\t-doctitle\tJava's Cool v4\t-version\t-nodeprecated\t-nohelp\t-nonavbar\t-notree\t-charset\tUTF-8";
#./gui/JVSFileTabs.java:                        new InputStreamReader(is, "UTF-8"));
#./widgets/HtmlDisplay.java:      return setPage(stringPrefix + URLEncoder.encode(text, "UTF-8"));
#./widgets/HtmlDisplay.java:    } catch(java.io.UnsupportedEncodingException e) { throw new IllegalStateException("UTF-8 n'est pas reconnu comme encodage: (" + e + ") c'est un bug Java !");
#./widgets/HtmlDisplay.java:        pane.setText(URLDecoder.decode(location.substring(stringPrefix.length()), "UTF-8"));
#./widgets/HtmlDisplay.java:      } catch(java.io.UnsupportedEncodingException e) { throw new IllegalStateException("UTF-8 n'est pas reconnu comme encodage: (" + e + ") c'est un bug Java !");
