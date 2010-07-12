!ifdef MAKEFILE
  !verbose 0
!else
  !define VERSION 1.1
!endif
;!define BETA
!define BETA_IP 192.168.0.253
!define BETA_PORT 8765
SetCompress off
XPStyle on
Icon Update.ico
Name "ExperienceUI Downloader"
Caption "ExperienceUI Update Wizard"
OutFile ..\Updater.exe
ChangeUI all ..\..\UIs\tinyui.exe
SubCaption 3 " "
SubCaption 4 " "
InstallColors 0x000000 0xFFFFFF
BrandingText " "
CompletedText "Done."
!define VER_MAJOR 1
!define VER_TENS 1
!define VER_ONES 0

Page custom welcome lock
Page custom infonotice lock
Page instfiles

Function welcome
LockWindow on
SetOutPath $TEMP
File Updater.ini
InstallOptions::initDialog /NOUNLOAD $TEMP\Updater.ini
Pop $1
SetCtlColors $1 0xFFFFFF 0xFFFFFF
GetDlgItem $0 $1 1200
SetCtlColors $0 0xC0C0C0 0xFFFFFF
CreateFont $2 "Verdana" 28 100
SendMessage $0 0x30 $2 ""
GetDlgItem $0 $1 1201
SetCtlColors $0 0x000000 transparent
CreateFont $2 "MS Sans Serif" 8 700
SendMessage $0 0x30 $2 ""
!ifdef BETA
SendMessage $0 0xC 0 "STR:This is a ßeta release."
!endif
GetDlgItem $0 $1 1202
SetCtlColors $0 0x000000 transparent
CreateFont $2 "MS Sans Serif" 8 350
SendMessage $0 0x30 $2 ""
!ifdef BETA
SendMessage $0 0xC 0 "STR:This updater will fail unless you have a computer with the IP address ${BETA_IP} on yur network, and it has the file xpui/updates.ini on port ${BETA_PORT}.  Please e-mail dandaman32@users.sourceforge.net for an updated version of this program!"
!endif
LockWindow off
InstallOptions::show
FunctionEnd

Function infonotice
SetOutPath $TEMP
File Updater.ini
WriteINIStr $TEMP\Updater.ini "Field 1" Left -173
InstallOptions::initDialog /NOUNLOAD $TEMP\Updater.ini
Pop $1
SetCtlColors $1 0xFFFFFF 0xFFFFFF
GetDlgItem $0 $1 1200
SetCtlColors $0 0xC0C0C0 0xFFFFFF
CreateFont $2 "Verdana" 28 100
SendMessage $0 0x30 $2 ""
SendMessage $0 0xC 0 "STR:privacy notice"
GetDlgItem $0 $1 1201
SetCtlColors $0 0x000000 transparent
CreateFont $2 "MS Sans Serif" 8 700
SendMessage $0 0x30 $2 ""
SendMessage $0 0xC 0 "STR:Your privacy is important."
GetDlgItem $0 $1 1202
SetCtlColors $0 0x000000 transparent
CreateFont $2 "MS Sans Serif" 8 350
SendMessage $0 0x30 $2 ""
SendMessage $0 0xC 0 "STR:This program does not send any information about to or your computer to anyone.  The only thing it does is download a text file from my web server and check its version information."
LockWindow off
InstallOptions::show
FunctionEnd

Function lock
LockWindow on
FunctionEnd

Function .onUserAbort
LockWindow off
FunctionEnd

!define LVM_DELETEALLITEMS 0x1009
!macro CLS
Push $0
FindWindow $0 "#32770" "" $HWNDPARENT
GetDlgItem $0 $0 1016
SendMessage $0 ${LVM_DELETEALLITEMS} 0 0
Pop $0
!macroend

Section
LockWindow off
GetDlgItem $0 $HWNDPARENT 1044
ShowWindow $0 0
GetDlgItem $0 $HWNDPARENT 3
ShowWindow $0 0
FindWindow $0 "#32770" "" $HWNDPARENT
GetDlgItem $0 $0 1004
ShowWindow $0 0
FindWindow $0 "#32770" "" $HWNDPARENT
GetDlgItem $0 $0 1027
ShowWindow $0 0
GetDlgItem $0 $HWNDPARENT 1
ShowWindow $0 0
DetailPrint "Connecting to the Internet..."
Dialer::AttemptConnect
IfErrors noie3
Pop $R0
!insertmacro CLS
DetailPrint "Connecting to the Internet...$R0"
StrCmp $R0 "online" connected      
MSIBanner::Destroy
MessageBox MB_OK|MB_ICONSTOP "The ExperienceUI was unable to establish a connection to the Internet.  A few possible reasons for this are:$\n$\n1) You have an Internet provider that requires authentication (such as dial-up) and you did not supply a valid$\n     username or password.$\n2) Your firewall is preventing this program ($CMDLINE) from accessing the Internet.$\n3) You do not have an Internet connection set up on this computer.$\n$\nThe updater cannot continue.  Please click OK to exit."
Call cleanup
Abort "Unable to connect."
noie3:
!insertmacro CLS
DetailPrint "Connecting to the Internet...cannot find IE3 or later"
MSIBanner::Destroy
MessageBox MB_OK|MB_ICONINFORMATION "The ExperienceUI has detected that you do not have Microsoft Internet Explorer 3 or later installed.  The automatic internet connection feature requires MSIE3 or later to work right.$\n$\nPlease connect to the Internet now, and then click OK to continue updating the ExperienceUI."
connected:
!ifdef BETA
  nsisdl::download /TRANSLATE "Downloading the update information file..." "Initializing..." " " " " " "  s " "                        "Time left: %d %s%s"   "http://${BETA_IP}:${BETA_PORT}/xpui/Updates.ini" "$TEMP\xpuiupd.ini"
    !else
  nsisdl::download /TRANSLATE "Downloading the update information file..." "Initializing..." " " " " " "  s " "                        "Time left: %d %s%s"   http://xpui.sourceforge.net/xpui/Updates.ini "$TEMP\xpuiupd.ini"
!endif
Pop $0
StrCmp $0 success GoodINI
MessageBox MB_OK|MB_ICONINFORMATION "The ExperienceUI updater was unable to download the update information file from the web site.$\nThe web server may be down, or there may be a problem with your Internet connection.$\n$\nError: $0"
Call cleanup
SetDetailsPrint textonly
DetailPrint "The updater cannot continue. Click Cancel to exit."
SetDetailsPrint listonly
Abort "Release info download failed: $0"
Goto Done
GoodINI:
DetailPrint "Checking version..."
DetailPrint "  Current: ${VERSION}"
ReadINIStr $0 $TEMP\xpuiupd.ini ExperienceUI Display
DetailPrint "  Latest: $0"
ReadINIStr $1 $TEMP\xpuiupd.ini ExperienceUI Major
ReadINIStr $2 $TEMP\xpuiupd.ini ExperienceUI Tens
ReadINIStr $3 $TEMP\xpuiupd.ini ExperienceUI Ones
ReadINIStr $4 $TEMP\xpuiupd.ini ExperienceUI Size
IntCmp $1 ${VER_MAJOR} "" "" Updates
IntCmp $2 ${VER_TENS} "" "" Updates
IntCmp $3 ${VER_ONES} "" "" Updates
Goto NoUpdates
Updates:
ReadINIStr $5 $TEMP\xpuiupd.ini ExperienceUI Class
ReadINIStr $6 $TEMP\xpuiupd.ini ExperienceUI HighlightsLine1
ReadINIStr $7 $TEMP\xpuiupd.ini ExperienceUI HighlightsLine2 
ReadINIStr $8 $TEMP\xpuiupd.ini ExperienceUI TimeID
DetailPrint "Update ready!"
MessageBox MB_YESNO|MB_ICONQUESTION "An update is available!$\n$\nYour version: ${VERSION}$\nNew version: $0 (Release ID: $8) $\n$\nThis update is classified as $5.$\n$\nRelease highlights:$\n$6$\n$7$\n$\nDownload it now? (size: ~$4kb)" IDYES Download
DetailPrint "Update found, but not downloaded."
Goto done
Download:
!ifdef BETA
nsisdl::download /TRANSLATE "Downloading ExperienceUI version $0..."  "Connecting to server..." second minute hour s "%dKB (%d%%) of %dKB at %d.%01dKB/second" "; Time left: %d %s%s" http://${BETA_IP}:${BETA_PORT}/xpui/$0/ExperienceUISetup$0.exe "$DESKTOP\ExperienceUI Setup $0.exe"
!else
nsisdl::download /TRANSLATE "Downloading ExperienceUI version $0..."  "Connecting to server..." second minute hour s "%dKB (%d%%) of %dKB at %d.%01dKB/second" "; Time left: %d %s%s" http://internap.dl.sourceforge.net/sourceforge/xpui/ExperienceUISetup$0.exe "$DESKTOP\ExperienceUI Setup $0.exe"
!endif
Pop $0
StrCmp $0 cancel DLCancelled
StrCmp $0 success "" DLError
MessageBox MB_OK|MB_ICONINFORMATION "The file was downloaded successfully to your Desktop."
DetailPrint "Update found and downloaded successfully."
Goto done
DLCancelled:
DetailPrint "Download cancelled."
MessageBox MB_OK|MB_ICONINFORMATION "The download was cancelled."
Goto done
DLError:
SetDetailsPrint textonly
DetailPrint "The updater cannot continue. Click Cancel to exit."
SetDetailsPrint listonly
Call Cleanup
Abort "Download error: $0"
MessageBox MB_OK|MB_ICONINFORMATION "The updater was unable to download the file from the web site.$\n$\nThe following error occurred:$\n$0"
Goto done
NoUpdates:
DetailPrint "No updates available."
MessageBox MB_OK|MB_ICONINFORMATION "No updates are available at this time.  Please try again later.$\n$\nCurrent version: ${VERSION}$\nLatest version: $0$\n"
Done:
Call Cleanup
SectionEnd

Function cleanup
SetDetailsPrint none
Delete $TEMP\xpuiupd.ini
SetDetailsPrint both
FindWindow $R0 "#32770" "" $HWNDPARENT
GetDlgItem $R0 $R0 1016
ShowWindow $R0 5
GetDlgItem $R0 $HWNDPARENT 1
ShowWindow $R0 5
Functionend