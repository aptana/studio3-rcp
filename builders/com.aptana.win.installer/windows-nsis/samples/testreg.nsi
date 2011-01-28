    #
    # Write general file association registry keys
    #
    WriteRegStr HKCR "*\shell\edit.aptanastudio"                          ""                'Edit with Aptana Studio 3'
    WriteRegStr HKCR "*\shell\edit.aptanastudio\command"                  ""                '"$INSTDIR\AptanaStudio3.exe" "%1"'
    WriteRegStr HKCR "Applications\aptanastudio.exe\shell\open\command"   ""                '"$INSTDIR\AptanaStudio3.exe" "%1"'
    
    # JS
    WriteRegStr HKCR "AptanaStudio.js"                              ""                      "JSFile"
    WriteRegStr HKCR ".js\OpenWithProgids"                          "AptanaStudio.js"       ""
    WriteRegStr HKCR ".js\OpenWithList\aptanastudio.exe"            "aptanastudio.exe"      ""
    
    # SDOC
    WriteRegStr HKCR "AptanaStudio.sdoc"                            ""                      "SDOCFile"
    WriteRegStr HKCR ".sdoc\OpenWithProgids"                        "AptanaStudio.sdoc"     ""
    WriteRegStr HKCR ".sdoc\OpenWithList\aptanastudio.exe"          "aptanastudio.exe"      ""
    
    # HTML
    WriteRegStr HKCR "AptanaStudio.html"                            ""                      "htmlfile"
    WriteRegStr HKCR ".htm\OpenWithProgids"                         "AptanaStudio.html"     ""
    WriteRegStr HKCR ".htm\OpenWithList\aptanastudio.exe"           "aptanastudio.exe"      ""
    WriteRegStr HKCR ".html\OpenWithProgids"                        "AptanaStudio.html"     ""
    WriteRegStr HKCR ".html\OpenWithList\aptanastudio.exe"          "aptanastudio.exe"      ""

    # CSS
    
    ; CSS is derived from HTML, so we do not need to add an explicit CSS entry here
    
    # XML
    WriteRegStr HKCR "AptanaStudio.xml"                             ""                      "xmlfile"
    WriteRegStr HKCR ".xml\OpenWithProgids"                         "AptanaStudio.xml"      ""
    WriteRegStr HKCR ".xml\OpenWithList\aptanastudio.exe"           "aptanastudio.exe"      ""

    # Now see which icon set we use, legacy or standard
    StrCmp $OnWindows2000 "1" ItIsWindows2000 ItIsNotWindows2000
    
    ItIsWindows2000:
        StrCpy $IconLocation "legacy"
        Goto DoneSetIconLocation
            
    ItIsNotWindows2000:
        StrCpy $IconLocation "standard"
        
    DoneSetIconLocation:
    
    #  
    # Now set icons for each of the file types
    #
    
    # JS
    WriteRegStr HKCR "JSFile\DefaultIcon"           ""              "$INSTDIR\Icons\$IconLocation\aptana_file_js.ico,0"
    WriteRegStr HKCR "JSFile\shell\open\command"    ""              '"$INSTDIR\AptanaStudio3.exe" "%1"'

    # SDOC
    WriteRegStr HKCR ".sdoc"                        ""              "SDOCFile"
    WriteRegStr HKCR ".sdoc"                        "ContentType"   "text/plain"
    WriteRegStr HKCR ".sdoc"                        "PerceivedType" "text"
    WriteRegStr HKCR "SDOCFile\DefaultIcon"         ""              "$INSTDIR\Icons\$IconLocation\aptana_file_sdoc.ico"
    WriteRegStr HKCR "SDOCFile\shell\open\command"  ""              '"$INSTDIR\AptanaStudio3.exe" "%1"'
        
    # CSS
    WriteRegStr HKCR "CSSFile\DefaultIcon"          ""              "$INSTDIR\Icons\$IconLocation\aptana_file_css.ico"
    WriteRegStr HKCR "CSSFile\shell\open\command"   ""              '"$INSTDIR\AptanaStudio3.exe" "%1"'
        
    # XML
    WriteRegStr HKCR "xmlfile\DefaultIcon"          ""              "$INSTDIR\Icons\$IconLocation\aptana_file_xml.ico"
    WriteRegStr HKCR "xmlfile\shell\open\command"   ""              '"$INSTDIR\AptanaStudio3.exe" "%1"'
