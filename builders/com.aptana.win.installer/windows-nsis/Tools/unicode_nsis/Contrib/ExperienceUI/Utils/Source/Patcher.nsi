; BASIC INFO
!ifdef MAKEFILE
  !verbose 0
  !define XPUI_VERBOSE 0
  !define XPUI_SILENT
!else
  !define VERSION 1.1M3
!endif
SetCompressor /FINAL /SOLID lzma
Name "ExperienceUI Skinning/Patching Utility"
Caption "ExperienceUI Skinning/Patching Engine"
OutFile ..\PatchInstaller.exe
!define XPUI_ICON patcher.ico
!define XPUI_UNICON Patcher.ico
InstallDirRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ExperienceUI Codename $\"Bryce$\"" UninstallString
SetPluginUnload alwaysoff
!include system.nsh
!ifdef MAKEFILE
  !verbose 0
!endif
!define SOUND_DEFAULT "${MB_OK}"
!define SOUND_STOP "${MB_ICONHAND}"
!define SOUND_ASTERISK "${MB_ICONASTERISK}"
!define SOUND_ERROR "${MB_ICONEXCLAMATION}"
!define SOUND_QUESTION "${MB_ICONQUESTION}"
!define XPUI_BOTTOMIMAGE
!define XPUI_INSTFILESPAGE_DONE_SUBTITLE "The $TYPE you opened was installed successfully."
!include XPUI.nsh
!insertmacro XPUI_LANGUAGE English
!insertmacro XPUI_PAGE_INSTFILES

!macro AboutDlg
System::Call '${sysMessageBeep} (${SOUND_ASTERISK})'
!insertmacro smMessageBox '`$EXEDIR\XPUIRes.dll`' 'ExperienceUI for NSIS$\nPatch/Skin Installer (version ${VERSION})$\nCopyright © 2005 Dan Fuhry.$\n$\nTo install a skin or patch, double-click the file in Windows Explorer.$\nTo see this About dialog, run this program with the /ABOUT switch.$\n$\nThis program is a component of the ExperienceUI for NSIS.  Copyright © 2004-2005 Dan Fuhry.$\nThe ExperienceUI was written to provide a pretty alternative to the huge, slow InstallShield®* installers.  The default skin contains colors used by the NVidia® ForceWare™ Setup Program.$\n$\nMore information about the ExperienceUI can be found at http://forums.winamp.com/showthread.php?threadid=204836.$\n$\n*InstallShield is a registered trademark of MacroVision, Inc.  ForceWare is a registered trademark of NVidia, Inc.' 'About the ExperienceUI for NSIS Patch/Skin Installer' '${MB_OK}' 'i 5'
!macroend

ShowInstDetails show

VIProductVersion "1.1.0.0"
VIAddVersionKey /LANG=1033 "FileVersion" "${VERSION}"
VIAddVersionKey /LANG=1033 "ProductVersion" "${VERSION}"
VIAddVersionKey /LANG=1033 "ProductName" "ExperienceUI for NSIS"
VIAddVersionKey /LANG=1033 "Comments" "This program was written by Dan Fuhry using Nullsoft Scriptable Install System (http://nsis.sourceforge.net)"
VIAddVersionKey /LANG=1033 "CompanyName" "Fuhry Computers, Inc."
VIAddVersionKey /LANG=1033 "LegalTrademarks" "ExperienceUI for NSIS by Dan Fuhry. Copyright © 2004-2005 Fuhry Computers, Inc."
VIAddVersionKey /LANG=1033 "LegalCopyright" "Copyright © Dan Fuhry"
VIAddVersionKey /LANG=1033 "FileDescription" "ExperienceUI for NSIS Patch/Skin Installer"
VIAddVersionKey /LANG=1033 "SpecialBuild" "ExperienceUI for NSIS Patching/Skinning Engine, version ${VERSION}, built on ${__TIMESTAMP__}"

Var TYPE
Var FILE

!define LVM_DELETEALLITEMS 0x1009
!macro CLS
Push $9
FindWindow $9 "#32770" "" $HWNDPARENT
GetDlgItem $9 $9 1016
SendMessage $9 ${LVM_DELETEALLITEMS} 0 0
Pop $9
!macroend

; INITIALIZATION
Function .onInit
Call GetParameters
Pop $2
StrLen $1 $2
IntOp $1 $1 - 3
StrCpy $1 $2 3 $1
StrCmp $1 eup patch
StrCmp $1 eus skin
Call GetParameters
Pop $2
StrCmp $2 /ABOUT abtdlg

System::Call '${sysMessageBeep} (${SOUND_STOP})'
!insertmacro smMessageBox "`$EXEDIR\XPUIRes.dll`" "The file '$2' either does not exist, is not a valid ExperienceUI skin/patch, or does not have the extension .eup or .eus." "Invalid File" "${MB_OK}" "i 1"

Quit

abtdlg:
!insertmacro AboutDlg
Quit

patch:
StrCpy $TYPE patch

System::Call '${sysMessageBeep} (${SOUND_QUESTION})'
!insertmacro smMessageBox '`$EXEDIR\XPUIRes.dll`' 'You have opened an ExperienceUI patch file.  Do you wish to install this patch now?' 'ExperienceUI Setup' '${MB_YESNO}' 'i 1'
StrCmp $R0 6 +2
Quit
Call GetParameters
Pop $FILE
Goto done
skin:
StrCpy $TYPE skin
System::Call '${sysMessageBeep} (${SOUND_QUESTION})'
!insertmacro smMessageBox '`$EXEDIR\XPUIRes.dll`' 'You have opened an ExperienceUI skin file.  Do you wish to install this skin now?' 'ExperienceUI Setup' '${MB_YESNO}' 'i 2'
StrCmp $R0 6 +2
Quit
Call GetParameters
Pop $FILE
done:
WriteRegStr   HKCR XPUI_P "" "ExperienceUI Patch"
WriteRegStr   HKCR XPUI_P\Shell "" Install
WriteRegStr   HKCR XPUI_P\Shell\Install\Command '' '"$EXEDIR\PatchInstaller.exe" %1'
WriteRegStr   HKCR XPUI_P\DefaultIcon '' '"$EXEDIR\icon-patch.ico"'
WriteRegStr   HKCR .eup "" XPUI_P

WriteRegStr   HKCR XPUI_S "" "ExperienceUI Skin"
WriteRegStr   HKCR XPUI_S\Shell "" Install
WriteRegStr   HKCR XPUI_S\Shell\Install\Command '' '"$EXEDIR\PatchInstaller.exe" %1'
WriteRegStr   HKCR XPUI_S\DefaultIcon '' '"$EXEDIR\icon-skin.ico"'
WriteRegStr   HKCR .eus "" XPUI_S
FunctionEnd

Var TOTAL
Var CURRENT
Var NAME
Var CF

!macro Debug
  MessageBox MB_OK "Debug:$\n$$CURRENT: $CURRENT$\n$$TOTAL: $TOTAL$\n$$FILE: $FILE$\n$$NAME: $NAME$\n$$CF: $CF$\n$$0: $0$\n$$1: $1$\n$$2: $2"
!macroend

!define Debug "!insertmacro Debug"

SetPluginUnload manual

Section -InstallFiles
; SHARED 1
!insertmacro XPUI_HEADER_TEXT "Installing" "Please wait while your installation of the ExperienceUI is updated with the $TYPE you opened."
IfFileExists $FILE "" Error
StrCpy $CURRENT 0
StrCmp $TYPE patch patch
StrCmp $TYPE skin skin
Error:
System::Call '${sysMessageBeep} (${SOUND_STOP})'
!insertmacro smMessageBox '`$EXEDIR\XPUIRes.dll`' 'The file $\'$CF$\' either does not exist, is not a valid ExperienceUI skin/patch, or does not have the extension .eup or .eus.$\n$\n' 'Invalid File' '${MB_OK}' 'i 1'
!insertmacro XPUI_HEADER_TEXT "Installation Incomplete" "Setup was unable to install the $TYPE you opened."
DetailPrint `WARNING: The file "$FILE" does not exist or cannot be read.`
abort `Error: aborting installation process`

; XPUI PATCH
patch:
ClearErrors
DetailPrint "Extracting files..."
SetDetailsPrint none
CreateDirectory $TEMP\EUI-Setup
ZipDLL::extractall $FILE $TEMP\EUI-Setup
STrCpy $0 0
Call GetParameters
Pop $FILE
SetDetailsPrint textonly
Banner::show /NOUNLOAD "Parsing CONTENT.DAT file/path/description file..."
DetailPrint "Reading content and description file..."
Banner::destroy
;!insertmacro CLS
ReadINIStr $NAME "$TEMP\EUI-Setup\Content.dat" "EUI-Patch" Name
StrCmp $NAME "" NoPatchName
MessageBox MB_YESNO|MB_ICONQUESTION `Install the patch "$NAME" from file "$FILE"?` IDYES +3
Call .onGUIEnd
Quit
NoPatchName:
SetDetailsPrint listonly
ReadINIStr $TOTAL "$TEMP\EUI-Setup\Content.dat" "EUI-Patch" NumFiles
ext-loop:
IntOp $CURRENT $CURRENT + 1
ReadINIStr $2 "$TEMP\EUI-Setup\Content.dat" "EUI-Patch" "File$CURRENT Desc"
SetDetailsPrint textonly
DetailPrint "Copying file $CURRENT of $TOTAL ($2)..."
ReadINIStr $CF "$TEMP\EUI-Setup\Content.dat" "EUI-Patch" "File$CURRENT"
ReadINIStr $1 "$TEMP\EUI-Setup\Content.dat" "EUI-Patch" "File$CURRENT Path"
SetDetailsPrint listonly
DetailPrint "Install file: $CF ($2); Copy to $INSTDIR\$1"
SetDetailsPrint none
CreateDirectory $INSTDIR\$1
CopyFiles $TEMP\EUI-Setup\$CF $INSTDIR\$1\$CF
Sleep 100
SetDetailsPrint listonly
IntCmp $CURRENT $TOTAL done-p ext-loop done-p
done-p:
DetailPrint "Remove Directory: $TEMP\EUI-Setup"
SetDetailsPrint none
RMDir /r $TEMP\EUI-Setup
SetDetailsPrint listonly
goto done

; XPUI SKIN
skin:
ClearErrors
DetailPrint "Extracting files..."
CreateDirectory $TEMP\EUI-Setup
ZipDLL::extractall $FILE $TEMP\EUI-Setup
Banner::show /NOUNLOAD "Parsing CONTENT.DAT file/path/description file..."
Sleep 1500
DetailPrint "Reading content and description file..."
Banner::destroy
;!insertmacro CLS
ReadINIStr $NAME "$TEMP\EUI-Setup\Content.dat" "EUI-Skin" Name
StrCmp $NAME "" NoName
MessageBox MB_YESNO|MB_ICONQUESTION `Install the skin "$NAME" from file "$FILE"?` IDYES +3
Call .onGUIend
Quit
NoName: 
ReadINIStr $TOTAL "$TEMP\EUI-Setup\Content.dat" "EUI-Skin" NumFiles
skn-loop:
IntOp $CURRENT $CURRENT + 1
SetDetailsPrint textonly
DetailPrint "Copying file $CURRENT of $TOTAL..."
SetDetailsPrint listonly
ReadINIStr $CF "$TEMP\EUI-Setup\Content.dat" "EUI-Skin" "File$CURRENT"
ReadINIStr $1 "$TEMP\EUI-Setup\Content.dat" "EUI-Skin" "File$CURRENT Path"
DetailPrint "Copy file: $CF to $INSTDIR\$1"
SetDetailsPrint none
CreateDirectory $INSTDIR\$1
CopyFiles $TEMP\EUI-Setup\$CF $INSTDIR\$1
Sleep 100
SetDetailsPrint listonly
IntCmp $CURRENT $TOTAL done-s skn-loop done-s
done-s:
SetDetailsPrint listonly
DetailPrint "Remove Directory: $TEMP\EUI-Setup"
SetDetailsPrint none
RMDir /r $TEMP\EUI-Setup
SetDetailsPrint listonly
goto done

; SHARED 2
done:
SetDetailsPrint both
System::Free $0
SectionEnd

Function .onInstFailed
!insertmacro XPUI_HEADER_TEXT "Installation Incomplete" "Setup was unable to install the $TYPE you opened."
System::Call '${sysMessageBeep} (${SOUND_STOP})'
!insertmacro smMessageBox '`$EXEDIR\XPUIRes.dll`' 'Setup failed to install the $TYPE you opened.$\n$\nError: Error writing to file $INSTDIR\$1\$0$\nIf this file is open, please close it and restart Setup.  If this fails, you probably do not have permission to overwrite this file.  If this is the case, please log off and then log back on as a member of the Administrators group.' 'Installation Failure' '${MB_OK}' 'i 1'
FunctionEnd

Function GetParameters
Push $R0
Push $R1
Push $R2
Push $R3
StrCpy $R2 1
StrLen $R3 $CMDLINE
;Check for quote or space
StrCpy $R0 $CMDLINE $R2
StrCmp $R0 '"' 0 +3
StrCpy $R1 '"'
Goto loop
StrCpy $R1 " "
loop:
IntOp $R2 $R2 + 1
StrCpy $R0 $CMDLINE 1 $R2
StrCmp $R0 $R1 get
StrCmp $R2 $R3 get
Goto loop
get:
IntOp $R2 $R2 + 1
StrCpy $R0 $CMDLINE 1 $R2
StrCmp $R0 " " get
StrCpy $R0 $CMDLINE "" $R2
Pop $R3
Pop $R2
Pop $R1
Exch $R0
FunctionEnd

