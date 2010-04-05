; ExperienceUI for NSIS 1.1
; Setup Script
; Copyright © 2004-2005 Dashboard Software

; This program is free software; you redistribute and/or modify it
; under the terms of the zlib/libpng license.

; This program is distributed in the hope that it will be useful,
; but WITHOUT ANY WARRANTY; without even the implied warranty of
; merchantability or fitness for a particular purpose.  See the
; zlib/libpng license for details.

; You should have received a copy of the zlib/libpng license with
; this program; if not, visit http://xpui.sf.net/docs/?start=legal.htm.

; This script requires the following add-ons and libraries in order to compile:
; * ExperienceUI for NSIS SDK v1.1 or later - http://xpui.sourceforge.net/
; * Component Manager 0.2 or later - http://xpui.sourceforge.net/compmgr/

;-------------------------------------
; First Things First

SetCompressor /FINAL /SOLID lzma

;-------------------------------------
; Declarations

Name "ExperienceUI for NSIS"
Caption "ExperienceUI SDK Setup"
OutFile "ExperienceUISetup-1.1.exe"
ShowInstDetails show
CheckBitmap "${NSISDIR}\Contrib\Graphics\Checks\Modern.bmp"
!define /date XPUI_BUILD_ID "%H%M-%y%m%d"
!define /date XPUI_BUILD_ID_DECIMAL "%y%m%d.%H%M"

InstType "Full Install"
InstType "Standard Install"
InstType "Minimal Install"

;-------------------------------------
; ExperienceUI Settings

!define XPUI_ABORTWARNING
!define XPUI_UNABORTWARNING
!define XPUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\XPUI-Install.ico"
!define XPUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\XPUI-UnInstall.ico"
!define XPUI_BRANDINGTEXT "ExperienceUI for NSIS 1.1"
; WAnsis disabled because of size and stability issues
;!define XPUI_WANSIS
;!define XPUI_WANSIS_SKIN LCD
;!define XPUI_WANSIS_HEADERIMAGE
!define XPUI_SKIN "Windows XP"
!define XPUI_LICENSEPAGE_CHECKBOX
!define XPUI_DISABLEBG
!define XPUI_FINISHPAGE_DOCS
!define XPUI_FINISHPAGE_DOCS_FILE "$INSTDIR\..\..\Docs\ExperienceUI\open_popup.hta"

!define CM_STARTMENU_REG_ROOT HKLM
!define CM_STARTMENU_REG_KEY  "Software\NSIS\ExperienceUI"
!define CM_STARTMENU_REG_VALUE "StartMenuFolder"

!include XPUI.nsh
!include CM.nsh

;-------------------------------------
; Compile-time Options

; Define to include the "A Better Installer" splash screen (adds a whopping 100KB)
  !define XPUI_SETUP_SPLASH

;-------------------------------------
; Variables

Var SMFOLDER
Var INSTALL_EX
Var INSTALL_DOCS
Var INSTALL_UTILS

!insertmacro XPUI_LANGUAGE "English"
!insertmacro XPUI_PAGE_STARTMENU_INIT App $SMFOLDER

;-------------------------------------
; Version Info

VIProductVersion "1.1.${XPUI_BUILD_ID_DECIMAL}"
VIAddVersionKey /LANG=1033 "FileVersion" "1.1.${XPUI_BUILD_ID}"
VIAddVersionKey /LANG=1033 "ProductVersion" "1.1.${XPUI_BUILD_ID}"
VIAddVersionKey /LANG=1033 "ProductName" "ExperienceUI for NSIS"
VIAddVersionKey /LANG=1033 "Comments" "This installer was written by Dan Fuhry using Nullsoft Scriptable Install System (http://nsis.sourceforge.net)"
VIAddVersionKey /LANG=1033 "CompanyName" "Dashboard Software Ltd."
VIAddVersionKey /LANG=1033 "LegalTrademarks" "ExperienceUI SDK by Dan Fuhry. Copyright © 2004-2005 Dashboard Software Ltd."
VIAddVersionKey /LANG=1033 "LegalCopyright" "Copyright © 2004-2005 Dan Fuhry"
VIAddVersionKey /LANG=1033 "FileDescription" "ExperienceUI SDK Setup/Maintenance Program"
VIAddVersionKey /LANG=1033 "SpecialBuild" "ExperienceUI for NSIS Software Development Kit Setup, built on ${__TIMESTAMP__} (${XPUI_BUILD_ID})"

;-------------------------------------
; Initialization

Function .onInit
  !ifdef XPUI_SETUP_SPLASH
  InitPluginsDir
  SetOutPath $PLUGINSDIR
  File LargeLogo.gif
  newadvsplash::show /NOUNLOAD 2000 2000 2000 -1 "$PLUGINSDIR\LargeLogo.gif"
  Delete $PLUGINSDIR\LargeLogo.gif
  !endif
  ReadRegStr $0 HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\NSIS" "UninstallString"
  StrCpy $0 $0 "" 1
  StrCpy $0 $0 -17
  IfFileExists $0\MakeNSIS.exe FoundNSIS
    MessageBox MB_OK|MB_ICONEXCLAMATION "Setup was unable to find NSIS on your system.  The ExperienceUI SDK requires NSIS in order to function.$\n$\nDo you want to go to the NSIS website now?" IDNO +2
      ExecShell open "http://nsis.sourceforge.net/"
    Abort
  FoundNSIS:
  StrCpy $INSTDIR "$0\Contrib\ExperienceUI"
FunctionEnd

;-------------------------------------
; Sections

!include ExperienceUIFileList.nsh

!macro CM_UNINST
  Delete $INSTDIR\Utils\Updater.exe
  Delete $INSTDIR\Utils\PatchInstaller.exe
  SetOutPath "C:\"
  ${CMInclude} Dirlist # doing this three
  ${CMInclude} Dirlist # times to make sure
  ${CMInclude} Dirlist # everything's gone
  RMDir $INSTDIR # Will delete $INSTDIR if it's empty
!macroend

;-------------------------------------
; Pages

!verbose 0
!insertmacro CM_SYSTEM

LangString WPTEXT1 ${LANG_ENGLISH} "Welcome to ExperienceUI 1.1."
LangString WPTEXT2 ${LANG_ENGLISH} "Welcome to the ExperienceUI, the user interface that makes NSIS the installer your programs truly deserve.\r\n\r\nClick Next to start.\r\n\r\nThis release is version 1.1 and it was built on ${__TIMESTAMP__}."
!define XPUI_WELCOMEPAGESTYLE2_TEXT_TOP "$(WPTEXT1)"
!define XPUI_WELCOMEPAGESTYLE2_TEXT     "$(WPTEXT2)"
!insertmacro XPUI_PAGE_WELCOME2
!insertmacro XPUI_PAGE_LICENSE License.rtf
!insertmacro XPUI_PAGE_COMPONENTS
!insertmacro XPUI_PAGE_DIRECTORY
!insertmacro XPUI_PAGE_STARTMENU_SHOW App
!insertmacro XPUI_PAGE_INSTCONFIRM
!insertmacro XPUI_PAGE_INSTFILES
!insertmacro XPUI_PAGE_FINISH
!insertmacro XPUI_PAGE_ABORT

!insertmacro XPUI_PAGEMODE_UNINST
!insertmacro XPUI_PAGE_WELCOME2
!insertmacro XPUI_PAGE_UNINSTCONFIRM_NSIS
!insertmacro XPUI_PAGE_INSTCONFIRM
!insertmacro XPUI_PAGE_INSTFILES
!insertmacro XPUI_PAGE_FINISH
!insertmacro XPUI_PAGE_ABORT
!verbose 4