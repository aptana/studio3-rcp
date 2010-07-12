; NSIS ExperienceUI User Interface version 1.1
; Macro System
; Written by Dan Fuhry

; Copyright Љ 2004-2005 Dan Fuhry
; Portions written by Joost Verburg
; Copyright Љ 2002-2004 Joost Verburg

; This script includes a goto system; just use your editor's find command
; To go to a bookmark, type #goto BookmarkName
; Example:
; #goto WelcomePage

; Available bookmarks: BasicMacros, WAnsis, BasicSettings, FeatureMacros, InstallOptions, GUIInit,
; WelcomePage, LicensePage, ComponentsPage, DirectoryPage, StartMenuPage, InstConfirmPage, InstFilesPage,
; InstSuccessPage, FinishPage, UnConfirmPage, NullPages, SecDesc, LeftInfo, Lang, MUIConvert, FinishUp

!ifdef XPUI_INCLUDED
!warning `The ExperienceUI was included multiple times. Please include it only once in your script.`
!endif

!ifndef XPUI_VERBOSE
  !define XPUI_VERBOSE 1
!endif

!ifndef XPUI_INCLUDED
!verbose push
!verbose ${XPUI_VERBOSE}
!define XPUI_INCLUDED
!define XPUI_VERSION 1.1

!verbose 4

; my infamous (new) ASCII art
!ifndef XPUI_SILENT
!echo "\
+------------------------------------------------------------------------------------------+$\n\
|                                                                             /ии|  /ии|   |$\n\
| |иииииии|                         _                           |и|  |и| |и| /   | /   |   |$\n\
| | |иииии                         |_|                          | |  | | | |  и| |  и| |   |$\n\
| |  иии|   _  _        ___   ___   _   ___   ____    ____  ___ | |  | | | |   | |   | |   |$\n\
| | |иии    \\// |иии\ / __\ |  _| | | / __\ |  _  \ / __/ / __\| |__| | | |   | |   | |   |$\n\
| |  иииии| //\\ | __/ \___/ |_|   |_| \___/ |_| |_| \___\ \___/\______/ |_|   |_| O |_|   |$\n\
|  иииииии  и  и |_|                                                [ A better installer ] |$\n\
+------------------------------------------------------------------------------------------+$\n"

;!echo `$\n+----------------------------------------------------------+$\n|         N       NNN.         S     IIII          S       |$\n|        NNN      NNN.       SSSS.   IIII.       SSSS.     |$\n|      NNNNN.     NNN.      SSSSSS.  IIII.      SSSSSS.    |$\n|      NNNNNN     NNN.     SSSSS..   IIII.     SSSSS..     |$\n|      NNNNNN.    NNN.    SSSSS.     IIII.    SSSSS.       |$\n|      NNN.NNN    NNN.   SSSSS..     IIII.   SSSSS..       |$\n|      NNN.NNN.   NNN.   SSSSS.      IIII.   SSSSS.        |$\n|      NNN. NNN   NNN.    SSSSS      IIII.    SSSSS        |$\n|      NNN. NNN.  NNN.    SSSSS.     IIII.    SSSSS.       |$\n|      NNN.  NNN  NNN.     SSSSS.    IIII.     SSSSS.      |$\n|      NNN.  NNN. NNN.      SSSSS.   IIII.      SSSSS.     |$\n|      NNN.   NNN NNN.       SSSSS.  IIII.       SSSSS.    |$\n|      NNN.   NNN.NNN.       SSSSS.  IIII.       SSSSS..   |$\n|      NNN.    NNNNNN.      SSSSS..  IIII.      SSSSS..    |$\n|      NNN.    NNNNNN.     SSSSS..   IIII.     SSSSS..     |$\n|      NNN.     NNNNN.    SSSSS..    IIII.    SSSSS..      |$\n|      NNN.     NNNNN.   SSSSS..     IIII.   SSSSS..       |$\n|      NNN.      NNNN.  SSSSSS..     IIII.  SSSSSS..       |$\n|      NNN.      NNNN.   SSSS..      IIII.   SSSS..        |$\n|      NNN.       N...    S...       IIII.     S...        |$\n|       ...        .       .          ....      .          |$\n| EEE                     i                        U   U I |$\n| E...                     .                       U.  U.I.|$\n| EE  x x pppp   eee  rrr i  eee  nnn   ccc  eee   U.  U.I.|$\n| E..  x .p...p EeeeE r...i.EeeeE n. n.c ...EeeeE  U.  U.I.|$\n| EEE x x pppp . eee..r.  i. eee..n. n. ccc  eee..  UUU .I.|$\n|  ... . .p....   ...  .   .  ...  .  .  ...  ...    ...  .|$\n|         p                                                |$\n+----------------------------------------------------------+$\n`

!echo `NSIS ExperienceUI User Interface version ${XPUI_VERSION}   $\n\
       Copyright Љ 2004-2005 Dan Fuhry                            $\n\
                                                                  $\n\
       Portions written by Joost Verburg                          $\n\
       Copyright Љ 2002-2004 Joost Verburg                        $\n\
                                                                  $\n\
       XPUI: Processing XPUI information...                       $\n`

!endif

!verbose ${XPUI_VERBOSE}

!verbose 4
!ifndef XPUI_SILENT
!echo `  XPUI: Checking your NSIS compiler for required components...$\n  `
!endif
!verbose ${XPUI_VERBOSE}

!define XPUI_NSIS_REQUIRED_FLAGS "NSIS_CONFIG_VISIBLE_SUPPORT | \
                                  NSIS_CONFIG_ENHANCEDUI_SUPPORT | \
                                  NSIS_SUPPORT_CODECALLBACKS | \
                                  NSIS_SUPPORT_INTOPTS | \
                                  NSIS_SUPPORT_STROPTS | \
                                  NSIS_SUPPORT_STACK | \
                                  NSIS_SUPPORT_INIFILES | \
                                  NSIS_SUPPORT_EXECUTE | \
                                  NSIS_SUPPORT_FILE | \
                                  NSIS_SUPPORT_DELETE | \
                                  NSIS_SUPPORT_MESSAGEBOX | \
                                  NSIS_CONFIG_PLUGIN_SUPPORT"

  !ifndef ${XPUI_NSIS_REQUIRED_FLAGS}
          
    !verbose 4
    !error   "$\n  EXPERIENCEUI: FATAL: MakeNSIS was compiled without certain options that are required in order for the \
                ExperienceUI to work.$\n  The compiler options required in order for core ExperienceUI functionality are: \
                ${XPUI_NSIS_REQUIRED_FLAGS}.$\n  Please remove the ExperienceUI from your script or recompile NSIS to \
                support these features."
  !endif

!verbose 4
!ifndef XPUI_SILENT
!echo `  XPUI: Processing basic components...$\n  `
!endif
!verbose ${XPUI_VERBOSE}

#goto BasicMacros

; BASIC MACROS
!macro XPUI_DEFAULT SYMBOL CONTENT
!ifndef `${SYMBOL}`
!define `${SYMBOL}` `${CONTENT}`
!endif
!macroend

!macro MUI_DEFAULT SYMBOL CONTENT
!ifndef `${SYMBOL}`
!define `${SYMBOL}` `${CONTENT}`
!endif
!macroend

!macro XPUI_SET SYMBOL CONTENT
!ifdef `${SYMBOL}`
!undef `${SYMBOL}`
!endif
!define `${SYMBOL}` `${CONTENT}`
!macroEnd

!macro XPUI_SET_QUOTE_ALT SYMBOL CONTENT
!ifdef "${SYMBOL}"
!undef "${SYMBOL}"
!endif
!define "${SYMBOL}" "${CONTENT}"
!macroEnd

!macro XPUI_UNSET SYMBOL
!ifdef `${SYMBOL}`
!undef `${SYMBOL}`
!endif
!macroEnd

!macro XPUI_CREATEID

  !ifndef XPUI_UNIQUEID
    !define XPUI_UNIQUEID ${__LINE__}
  !endif

  !ifdef XPUI_UNIQUEID
    !undef XPUI_UNIQUEID
    !define XPUI_UNIQUEID ${__LINE__}
  !endif

!macroend

!macro XPUI_CONVERT XPUI MUI

  !ifdef ${MUI}
    !insertmacro XPUI_SET ${XPUI} `${${MUI}}`
  !endif

!macroend

!macro XPUI_CONVERT_QUOTE_ALT XPUI MUI

  !ifdef ${MUI}
    !insertmacro XPUI_SET_QUOTE_ALT ${XPUI} "${${MUI}}"
  !endif

!macroend

!macro XPUI_CONTROL_SKIN HWND
  !ifndef XPUI_FULLBODY_SKIN
    SetCtlColors ${HWND} ${XPUI_TEXT_COLOR} ${XPUI_TEXT_BGCOLOR}
  !endif
!macroend

!macro XPUI_CONTROL_SKIN_PAGE HWND
  !ifndef XPUI_FULLBODY_SKIN
    SetCtlColors ${HWND} ${XPUI_TEXT_COLOR} ${XPUI_TEXT_BGCOLOR}
  !endif
  !ifdef XPUI_PAGE_BGIMAGE
    SetCtlColors ${HWND} ${XPUI_TEXT_COLOR} Transparent
  !endif
!macroend

!macro XPUI_PAGE_CUSTOMFUNCTION MODE
!ifdef XPUI_PAGE_CUSTOMFUNCTION_${MODE}
Call `${XPUI_PAGE_CUSTOMFUNCTION_${MODE}}`
!undef XPUI_PAGE_CUSTOMFUNCTION_${MODE}
!endif
!ifdef MUI_PAGE_CUSTOMFUNCTION_${MODE}
Call `${MUI_PAGE_CUSTOMFUNCTION_${MODE}}`
!undef MUI_PAGE_CUSTOMFUNCTION_${MODE}
!endif
!macroend


#goto WAnsis

SetPluginUnload manual

!ifdef XPUI_WANSIS
  !define XPUI_FULLBODY_SKIN ; Disable the ExperienceUI's internal skinning support

  !insertmacro XPUI_DEFAULT XPUI_WANSIS_SKIN Forum

  CheckBitmap `${NSISDIR}\Contrib\ExperienceUI\Skins\${XPUI_WANSIS_SKIN}\checks.bmp`
  XPStyle off

  !macro XPUI_WANSIS_INIT
    InitPluginsDir
    SetOutPath $PLUGINSDIR
    File `${XPUI_WANSIS_GEN}`
    File `${XPUI_WANSIS_GENEX}`
    wansis::skinit /NOUNLOAD `$PLUGINSDIR\gen.bmp` `$PLUGINSDIR\genex.bmp`
  !macroend

  !macro XPUI_WANSIS_UNIT
    wansis::unskinit
    Delete $PLUGINSDIR\gen.bmp
    Delete $PLUGINSDIR\genex.bmp
  !macroend

  ; WAnsis can sometimes be unstable...
  !warning `ExperienceUI WAnsis support: Some bugs in WAnsis can make it unstable on certain computers.`

!endif

#goto BasicSettings

; INITIALIZATION
!include WinMessages.nsh
Var XPUI_TEMP1
Var XPUI_TEMP2
Var NOABORTWARNING
!ifdef XPUI_FULLBODY_SKIN
XPStyle off
!else
XPStyle on
!endif

!insertmacro XPUI_DEFAULT XPUI_PLUGINUNLOAD alwaysoff
SetPluginUnload ${XPUI_PLUGINUNLOAD}

!macro XPUI_HEADER_TEXT TEXT SUBTEXT
LockWindow on
!ifdef XPUI_HEADERIMAGE
SetBrandingImage /IMGID=1046 /RESIZETOFIT `$PLUGINSDIR\Header.bmp`
!endif
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1037
SendMessage $XPUI_TEMP1 0xC 0 `STR:${TEXT}`
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1038
SendMessage $XPUI_TEMP1 0xC 0 `STR:${SUBTEXT}`
LockWindow off
!macroend

!verbose ${XPUI_VERBOSE}

#goto FeatureMacros

; PAGE MODE FEATURE

!macro XPUI_PAGEMODE_INST
!ifndef XPUI_VERBOSE_MD
!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `XPUI Page Mode: INSTALL`
  !endif
  !verbose ${XPUI_VERBOSE}
!endif
!insertmacro XPUI_SET XPUI_PAGEMODE_INSERTED ``
!insertmacro XPUI_SET XPUI_UN ``
!insertmacro XPUI_SET XPUI_UNINST ``
!insertmacro XPUI_SET XPUI_UNFUNC ``
!insertmacro XPUI_SET XPUI_PAGEMODE `Install`
!ifndef XPUI_VERBOSE_MD
!verbose pop
!endif
!macroend

!macro XPUI_PAGEMODE_UNINST
!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `XPUI Page Mode: UNINSTALL`
  !endif
  !verbose ${XPUI_VERBOSE}
!insertmacro XPUI_SET XPUI_PAGEMODE_INSERTED ``
!insertmacro XPUI_SET XPUI_UN `UN`
!insertmacro XPUI_SET XPUI_UNINST `Uninst`
!insertmacro XPUI_SET XPUI_UNFUNC `un.`
!insertmacro XPUI_SET XPUI_PAGEMODE `Uninstall`
!verbose pop
!macroend

; ABORT PAGE VARIABLE
!ifndef XPUI_VAR_ABORTED
!define XPUI_VAR_ABORTED
Var XPUI_ABORTED
!endif

; MACROS: FILES

!macro XPUI_TEMPFILES_DELETE
Delete $PLUGINSDIR\*.bmp
Delete $PLUGINSDIR\*.ini
Delete $PLUGINSDIR\*.dll
!ifmacrodef XPUI_BGFILES_DELETE
  !insertmacro XPUI_BGFILES_DELETE
!endif
!macroend

!macro XPUI_RESERVEFILE_HEADERIMAGE
!ifdef XPUI_WANSIS
  !ifdef XPUI_WANSIS_HEADERIMAGE
    ReserveFile `${XPUI_WANSIS_HEADERIMAGE_BMP}`
  !endif
!else
  ReserveFile `${XPUI_HEADERIMAGE}`
!endif
!macroend

!macro XPUI_RESERVEFILE_BOTTOMIMAGE
ReserveFile `${XPUI_BOTTOMIMAGE_BMP}`
!macroend

!macro XPUI_RESERVEFILE_LEFTBRANDINGIMAGE
ReserveFile `${XPUI_LEFTLOGO}`
!macroend

; ABORT PAGE HANDLER

!macro XPUI_USERABORT
!insertmacro XPUI_CREATEID
IfFileExists $PLUGINSDIR\Finish.ini `` xpui.nofinish.${XPUI_UNIQUEID}
WriteINIStr $PLUGINSDIR\Finish.ini `Field 1` State 0
WriteINIStr $PLUGINSDIR\Finish.ini `Field 2` State 0
xpui.nofinish.${XPUI_UNIQUEID}:
StrCmp $NOABORTWARNING 1 xpui.exitnow.${XPUI_UNIQUEID}
StrCpy $XPUI_ABORTED 1
SendMessage $HWNDPARENT `0x408` `1` ``
Abort
xpui.exitnow.${XPUI_UNIQUEID}:
!macroend

!insertmacro XPUI_DEFAULT XPUI_INSTALLOPTIONS_MAXFIELD "1350"
!define XPUI_INSTALLOPTIONS_MAXFIELD_TEMP "${XPUI_INSTALLOPTIONS_MAXFIELD}"
!insertmacro XPUI_SET XPUI_INSTALLOPTIONS_MAXFIELD 1215

#goto MUIConvert

; ExperienceUI for NSIS
; Converter script
; Implements support for MUI and UMUI defines into ExperienceUI

; Copyright Љ 2005 Dan Fuhry (dandaman32)

!ifndef XPUI_SILENT
!echo `$\n\
       ExperienceUI for NSIS version ${XPUI_VERSION}$\n\
       MUI->XPUI Script Converter$\n\
       Copyright Љ 2005 Dan Fuhry`
!endif

!macro XPUI_PAGE_TITLE_CONVERT P
  !insertmacro XPUI_CONVERT XPUI_${P}PAGE_TITLE MUI_TEXT_${P}_TITLE
  !insertmacro XPUI_CONVERT XPUI_${P}PAGE_SUBTITLE MUI_TEXT_${P}_SUBTITLE
!macroend

!macro XPUI_LANGUAGE_CONVERT

; CONVERSION LIST (VERY LONG)
; LEGEND:

; MACRO                 | XPUI DEFINE                               | MUI/UMUI DEFINE
;                       |                                           |
!insertmacro XPUI_CONVERT XPUI_HEADERIMAGE                            MUI_HEADERIMAGE_BITMAP
!insertmacro XPUI_CONVERT XPUI_HEADERIMAGE_NORESIZETOFIT              MUI_HEADERIMAGE_BITMAP_NOSTRETCH
!insertmacro XPUI_CONVERT XPUI_UNHEADERIMAGE                          MUI_HEADERIMAGE_UNBITMAP
!insertmacro XPUI_CONVERT XPUI_UNHEADERIMAGE_NORESIZETOFIT            MUI_HEADERIMAGE_UNBITMAP_NOSTRETCH
!insertmacro XPUI_CONVERT XPUI_TEXT_COLOR                             MUI_TEXT_COLOR
!insertmacro XPUI_CONVERT XPUI_TEXT_BGCOLOR                           MUI_BGCOLOR
!insertmacro XPUI_CONVERT XPUI_TEXT_LIGHTCOLOR                        UMUI_TEXT_LIGHTCOLOR
!insertmacro XPUI_CONVERT XPUI_HEADERIMAGE                            UMUI_HEADERIMAGE_BMP
!insertmacro XPUI_CONVERT XPUI_UNHEADERIMAGE                          UMUI_UNHEADERIMAGE_BMP
!insertmacro XPUI_CONVERT XPUI_ICON                                   MUI_ICON
!insertmacro XPUI_CONVERT XPUI_UNICON                                 MUI_UNICON
!insertmacro XPUI_CONVERT XPUI_ABORTWARNING_TEXT                      MUI_TEXT_ABORTWARNING
!insertmacro XPUI_CONVERT XPUI_ABORTWARNING_TEXT                      MUI_ABORTWARNING_TEXT
!insertmacro XPUI_CONVERT XPUI_BRANDINGTEXT_COLOR_BG                  UMUI_BRANDINGTEXTBACKCOLOR
!insertmacro XPUI_CONVERT XPUI_BRANDINGTEXT_COLOR_FG                  UMUI_BRANDINGTEXTFRONTCOLOR

!insertmacro XPUI_CONVERT XPUI_BOTTOMIMAGE                            UMUI_BOTTOMIMAGE
!insertmacro XPUI_CONVERT XPUI_BOTTOMIMAGE_BMP                        UMUI_BOTTOMIMAGE_BMP
!insertmacro XPUI_CONVERT XPUI_UNBOTTOMIMAGE                          UMUI_UNBOTTOMIMAGE
!insertmacro XPUI_CONVERT XPUI_UNBOTTOMIMAGE_BMP                      UMUI_UNBOTTOMIMAGE_BMP

!insertmacro XPUI_CONVERT XPUI_LEFTLOGO                               UMUI_LEFTIMAGE_BMP
!insertmacro XPUI_CONVERT XPUI_UNLEFTLOGO                             UMUI_UNLEFTIMAGE_BMP

!insertmacro XPUI_CONVERT XPUI_UI                                     MUI_UI
!insertmacro XPUI_CONVERT XPUI_UI                                     UMUI_UI

!insertmacro XPUI_CONVERT XPUI_LICENSEBKCOLOR                         MUI_LICENSEPAGE_BGCOLOR

!insertmacro XPUI_CONVERT XPUI_COMPONENTSPAGE_NODESC                  MUI_COMPONENTSPAGE_NODESC

!insertmacro XPUI_CONVERT XPUI_WELCOMEPAGE_TEXT                       MUI_WELCOMEPAGE_TEXT
!insertmacro XPUI_CONVERT XPUI_WELCOMEPAGE_TEXT_TOP                   MUI_WELCOMEPAGE_TITLE

!insertmacro XPUI_CONVERT XPUI_WELCOMEPAGESTYLE2_TEXT                 MUI_WELCOMEPAGE_TEXT
!insertmacro XPUI_CONVERT XPUI_WELCOMEPAGESTYLE2_TEXT_TOP             MUI_WELCOMEPAGE_TITLE

!insertmacro XPUI_CONVERT XPUI_WELCOMEPAGESTYLE2_TEXT                 MUI_TEXT_WELCOME_INFO_TEXT
!insertmacro XPUI_CONVERT XPUI_WELCOMEPAGESTYLE2_TEXT_TOP             MUI_TEXT_WELCOME_INFO_TITLE

!ifdef MUI_TEXT_WELCOME_INFO_TEXT
  !insertmacro XPUI_DEFAULT XPUI_WELCOMEPAGE_TEXT `${MUI_TEXT_WELCOME_INFO_TEXT}`
!endif

!insertmacro XPUI_CONVERT XPUI_WELCOMEPAGE_TEXT_TOP                   MUI_TEXT_WELCOME_INFO_TITLE

!insertmacro XPUI_CONVERT XPUI_WELCOMEPAGESTYLE2_TEXT                 MUI_WELCOME_INFO_TEXT
!insertmacro XPUI_CONVERT XPUI_WELCOMEPAGESTYLE2_TEXT_TOP             MUI_WELCOME_INFO_TITLE

!insertmacro XPUI_CONVERT XPUI_LICENSEPAGE_TEXT_TOP                   MUI_LICENSEPAGE_TEXT_TOP
!insertmacro XPUI_CONVERT XPUI_LICENSEPAGE_TEXT_BOTTOM                MUI_LICENSEPAGE_TEXT_BOTTOM
!insertmacro XPUI_CONVERT XPUI_LICENSEPAGE_CHECKBOX                   MUI_LICENSEPAGE_CHECKBOX
!insertmacro XPUI_CONVERT XPUI_LICENSEPAGE_CHECKBOX_TEXT              MUI_LICENSEPAGE_CHECKBOX_TEXT
!insertmacro XPUI_CONVERT XPUI_LICENSEPAGE_RADIOBUTTONS               MUI_LICENSEPAGE_RADIOBUTTONS
!insertmacro XPUI_CONVERT XPUI_LICENSEPAGE_RADIOBUTTONS_TEXT_AGREE    MUI_LICENSEPAGE_RAGIOBUTTONS_TEXT_ACCEPT
!insertmacro XPUI_CONVERT XPUI_LICENSEPAGE_RADIOBUTTONS_TEXT_DECLINE  MUI_LICENSEPAGE_RAGIOBUTTONS_TEXT_DECLINE
!insertmacro XPUI_CONVERT XPUI_LICENSEPAGE_TEXT_TOP                   MUI_INNERTEXT_LICENSE_TOP
!insertmacro XPUI_CONVERT XPUI_LICENSEPAGE_TEXT_BOTTOM                MUI_INNERTEXT_LICENSE_BOTTOM

; MUI Japanese Translation license page checkbox text has a `...
!insertmacro XPUI_CONVERT_QUOTE_ALT XPUI_LICENSEPAGE_CHECKBOX_TEXT    MUI_INNERTEXT_LICENSE_BOTTOM_CHECKBOX

!insertmacro XPUI_CONVERT_QUOTE_ALT XPUI_LICENSEPAGE_CHECKBOX_TEXT              MUI_INNERTEXT_LICENSE_BOTTOM_CHECKBOX

!insertmacro XPUI_CONVERT XPUI_COMPONENTSPAGE_TEXT_DESCRIPTION_TITLE  MUI_COMPONENTSPAGE_TEXT_DESCRIPTION_TITLE
!insertmacro XPUI_CONVERT XPUI_COMPONENTSPAGE_TEXT_DESCRIPTION_INFO   MUI_COMPONENTSPAGE_TEXT_DESCRIPTION_INFO
!insertmacro XPUI_CONVERT XPUI_COMPONENTSPAGE_TEXT_DESCRIPTION_TITLE  MUI_INNERTEXT_COMPONENTS_DESCRIPTION_TITLE
!insertmacro XPUI_CONVERT XPUI_COMPONENTSPAGE_TEXT_DESCRIPTION_INFO   MUI_INNERTEXT_COMPONENTS_DESCRIPTION_INFO

!insertmacro XPUI_CONVERT XPUI_DIRECTORYPAGE_TEXT_TOP                 MUI_DIRECTORYPAGE_TEXT_TOP
!insertmacro XPUI_CONVERT XPUI_DIRECTORYPAGE_TEXT_DESTINATION         MUI_DIRECTORYPAGE_TEXT_DESTINATION

!insertmacro XPUI_CONVERT XPUI_STARTMENUPAGE_TEXT                     MUI_STARTMENUPAGE_TEXT_TOP
!insertmacro XPUI_CONVERT XPUI_STARTMENUPAGE_CHECKBOX                 MUI_STARTMENUPAGE_TEXT_CHECKBOX
!insertmacro XPUI_CONVERT XPUI_STARTMENUPAGE_FOLDER                   MUI_STARTMENUPAGE_DEFAULTFOLDER
!insertmacro XPUI_CONVERT XPUI_STARTMENUPAGE_TEXT                     MUI_INNERTEXT_STARTMENU_TOP
!insertmacro XPUI_CONVERT XPUI_STARTMENUPAGE_CHECKBOX                 MUI_INNERTEXT_STARTMENU_CHECKBOX

!insertmacro XPUI_CONVERT XPUI_INSTFILESPAGE_DONE_TITLE               MUI_TEXT_FINISH_TITLE
!insertmacro XPUI_CONVERT XPUI_INSTFILESPAGE_DONE_SUBTITLE            MUI_TEXT_FINISH_SUBTITLE

!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_TEXT_TOP                        MUI_FINISHPAGE_INFO_TITLE
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_TEXT                            MUI_FINISHPAGE_INFO_TEXT
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_TEXT_RUN                        MUI_FINISHPAGE_INFO_TEXT
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_TEXT_REBOOT                     MUI_FINISHPAGE_INFO_REBOOT
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_RUN                             MUI_FINISHPAGE_RUN
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_RUN_FILE                        MUI_FINISHPAGE_RUN
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_RUN_FUNCTION                    MUI_FINISHPAGE_RUN_FUNCTION
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_CHECKBOX_RUN                    MUI_FINISHPAGE_RUN_TEXT
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_DOCS                            MUI_FINISHPAGE_SHOWREADME
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_DOCS_FILE                       MUI_FINISHPAGE_SHOWREADME
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_CHECKBOX_DOCS                   MUI_FINISHPAGE_SHOWREADME_TEXT
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_NOREBOOT                        MUI_FINISHPAGE_NOREBOOTSUPPORT
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_LINK_TEXT                       MUI_FINISHPAGE_LINK
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_LINK_LOCATION                   MUI_FINISHPAGE_LINK_LOCATION

!ifdef MUI_FINISHPAGE_LINK
  !define XPUI_FINISHPAGE_LINK
  !insertmacro XPUI_SET XPUI_FINISHPAGE_LINK_TEXT `${MUI_FINISHPAGE_LINK}`
!endif

!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_TEXT_TOP                    MUI_TEXT_FINISH_INFO_TITLE
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_TEXT_TOP_ALT                MUI_TEXT_FINISH_INFO_TITLE
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_TEXT                        MUI_TEXT_FINISH_INFO_TEXT
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_TEXT_REBOOT                 MUI_TEXT_FINISH_INFO_REBOOT

!insertmacro XPUI_CONVERT XPUI_FINISH_TEXT_TOP                        MUI_TEXT_FINISH_INFO_TITLE
!insertmacro XPUI_CONVERT XPUI_FINISH_TEXT                            MUI_TEXT_FINISH_INFO_TEXT
!insertmacro XPUI_CONVERT XPUI_FINISH_TEXT_REBOOT                     MUI_TEXT_FINISH_INFO_REBOOT
!insertmacro XPUI_CONVERT XPUI_FINISH_RUN                             MUI_TEXT_FINISH_RUN
!insertmacro XPUI_CONVERT XPUI_FINISH_RUN_FILE                        MUI_TEXT_FINISH_RUN
!insertmacro XPUI_CONVERT XPUI_FINISH_RUN_FUNCTION                    MUI_TEXT_FINISH_RUN_FUNCTION
!insertmacro XPUI_CONVERT XPUI_FINISH_CHECKBOX_RUN                    MUI_TEXT_FINISH_RUN_TEXT
!insertmacro XPUI_CONVERT XPUI_FINISH_DOCS                            MUI_TEXT_FINISH_SHOWREADME
!insertmacro XPUI_CONVERT XPUI_FINISH_DOCS_FILE                       MUI_TEXT_FINISH_SHOWREADME
!insertmacro XPUI_CONVERT XPUI_FINISH_CHECKBOX_DOCS                   MUI_TEXT_FINISH_SHOWREADME_TEXT
!insertmacro XPUI_CONVERT XPUI_FINISH_NOREBOOT                        MUI_TEXT_FINISH_NOREBOOTSUPPORT
!insertmacro XPUI_CONVERT XPUI_FINISH_LINK                            MUI_TEXT_FINISH_LINK
!insertmacro XPUI_CONVERT XPUI_FINISH_LINK_TEXT                       MUI_TEXT_FINISH_LINK

!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_TEXT_TOP                    MUI_TEXT_FINISH_INFO_TITLE
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_TEXT_TOP_ALT                MUI_TEXT_FINISH_INFO_TITLE
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_TEXT                        MUI_TEXT_FINISH_INFO_TEXT
!insertmacro XPUI_CONVERT XPUI_FINISHPAGE_TEXT_REBOOT                 MUI_TEXT_FINISH_INFO_REBOOT

!insertmacro XPUI_CONVERT XPUI_UNINSTCONFIRMPAGE_NSIS_TEXT_TOP        MUI_UNCONFIRMPAGE_TEXT_TOP
!insertmacro XPUI_CONVERT XPUI_UNINSTCONFIRMPAGE_NSIS_TEXT_FOLDER     MUI_UNCONFIRMPAGE_TEXT_LOCATION

!insertmacro XPUI_CONVERT XPUI_LANGDLL_REGISTRY_ROOT                  MUI_LANGDLL_REGISTRY_ROOT
!insertmacro XPUI_CONVERT XPUI_LANGDLL_REGISTRY_KEY                   MUI_LANGDLL_REGISTRY_KEY
!insertmacro XPUI_CONVERT XPUI_LANGDLL_REGISTRY_VALUE                 MUI_LANGDLL_REGISTRY_VALUENAME

!insertmacro XPUI_CONVERT XPUI_BUTTONTEXT_CLOSE                       MUI_BUTTONTEXT_FINISH

!insertmacro XPUI_PAGE_TITLE_CONVERT                                  WELCOME
!insertmacro XPUI_PAGE_TITLE_CONVERT                                  LICENSE
!insertmacro XPUI_PAGE_TITLE_CONVERT                                  COMPONENTS
!insertmacro XPUI_PAGE_TITLE_CONVERT                                  DIRECTORY
!insertmacro XPUI_PAGE_TITLE_CONVERT                                  STARTMENU

!insertmacro XPUI_CONVERT XPUI_INSTFILESPAGE_TITLE MUI_TEXT_INSTALLING_TITLE
!insertmacro XPUI_CONVERT XPUI_INSTFILESPAGE_SUBTITLE MUI_TEXT_INSTALLING_SUBTITLE

!insertmacro XPUI_PAGE_TITLE_CONVERT FINISH
!insertmacro XPUI_PAGE_TITLE_CONVERT ABORT

!macroend

# MUI REPLACEMENT MACROS

!macro MUI_PAGE_WELCOME
  !ifdef UMUI_USE_ALTERNATE_PAGE
    !insertmacro XPUI_PAGE_WELCOME2
  !else
    !insertmacro XPUI_PAGE_WELCOME
  !endif
!macroend

!macro MUI_PAGE_LICENSE FILE
  !insertmacro XPUI_PAGE_LICENSE `${FILE}`
!macroend

!macro MUI_PAGE_COMPONENTS
  !insertmacro XPUI_PAGE_COMPONENTS
!macroend

!macro MUI_PAGE_DIRECTORY
  !insertmacro XPUI_PAGE_DIRECTORY
!macroend

!macro MUI_PAGE_STARTMENU ID VAR
  !insertmacro XPUI_PAGE_STARTMENU `${ID}` `${VAR}`
!macroend

!macro MUI_PAGE_INSTFILES
  !insertmacro XPUI_PAGE_INSTFILES
!macroend

!macro MUI_PAGE_FINISH
  !ifdef UMUI_USE_ALTERNATE_PAGE
    !insertmacro XPUI_SET XPUI_FINISHPAGE_TEXT_USE_TOP_ALT ``
  !endif
  !insertmacro XPUI_PAGE_FINISH
!macroend

!macro MUI_UNPAGE_WELCOME
  !insertmacro XPUI_PAGEMODE_UNINST
  !insertmacro XPUI_PAGE_WELCOME
!macroend

!macro MUI_UNPAGE_LICENSE FILE
  !insertmacro XPUI_PAGEMODE_UNINST
  !insertmacro XPUI_PAGE_LICENSE `${FILE}`
!macroend

!macro MUI_UNPAGE_COMPONENTS
  !insertmacro XPUI_PAGEMODE_UNINST
  !insertmacro XPUI_PAGE_COMPONENTS
!macroend

!macro MUI_UNPAGE_DIRECTORY
  !insertmacro XPUI_PAGEMODE_UNINST
  !insertmacro XPUI_PAGE_DIRECTORY
!macroend

!macro MUI_UNPAGE_STARTMENU ID VAR
  !insertmacro XPUI_PAGEMODE_UNINST
  !insertmacro XPUI_PAGE_STARTMENU `${ID}` `${VAR}`
!macroend

!macro MUI_UNPAGE_CONFIRM
  !insertmacro XPUI_PAGEMODE_UNINST
  !insertmacro XPUI_PAGE_UNINSTCONFIRM_NSIS
!macroend

!macro MUI_UNPAGE_INSTFILES
  !insertmacro XPUI_PAGEMODE_UNINST
  !insertmacro XPUI_PAGE_INSTFILES
!macroend

!macro MUI_UNPAGE_FINISH
  !insertmacro XPUI_PAGEMODE_UNINST
  !insertmacro XPUI_PAGE_FINISH
!macroend

!macro UMUI_PAGE_CONFIRM
  !insertmacro XPUI_PAGEMODE_INST
  !insertmacro XPUI_PAGE_INSTCONFIRM
!macroend

!macro UMUI_UNPAGE_CONFIRM
  !insertmacro XPUI_PAGEMODE_UNINST
  !insertmacro XPUI_PAGE_INSTCONFIRM
!macroend

!macro UMUI_PAGE_ABORT
  !insertmacro XPUI_PAGEMODE_INST
  !insertmacro XPUI_PAGE_ABORT

  !insertmacro XPUI_UNSET UMUI_ABORTPAGE_LINK
  !insertmacro XPUI_UNSET UMUI_ABORTPAGE_LINK_LOCATION
!macroend

!macro UMUI_UNPAGE_ABORT
  !insertmacro XPUI_PAGEMODE_UNINST
  !insertmacro XPUI_PAGE_ABORT

  !insertmacro XPUI_UNSET UMUI_ABORTPAGE_LINK
  !insertmacro XPUI_UNSET UMUI_ABORTPAGE_LINK_LOCATION
!macroend

!macro UMUI_LEFT_SETTIME M
  !insertmacro XPUI_LEFT_SETTIME `${M} minutes`
!macroend

!macro UMUI_CONFIRMPAGE_TEXTBOX_ADDLINE NULL
!macroend

!macro UMUI_PAGE_LEFTMESSAGEBOX T C M

  !insertmacro XPUI_CREATEID
  Page custom umuilmb.c.${XPUI_UNIQUEID} umuilmb.l.${XPUI_UNIQUEID} ` `

  Function umuilmb.c.${XPUI_UNIQUEID}
  
    StrCmp $XPUI_ABORTED 1 `` +2
    Abort
  
    !insertmacro XPUI_HEADER_TEXT ` ` ` `
    !insertmacro XPUI_LEFT_MESSAGE `${C}` `${M}` `${T}`
  FunctionEnd

  Function umuilmb.l.${XPUI_UNIQUEID}
    !ifdef UMUI_LEFTMESSAGEBOX_VAR
      ReadINIStr `$${UMUI_LEFTMESSAGEBOX_VAR}` `$PLUGINSDIR\MBSide.ini` `Settings` `State`
    !endif
    
    !ifdef UMUI_LEFTMESSAGEBOX_LEFTFUNC
      Call `${UMUI_LEFTMESSAGEBOX_LEFTFUNC}`
      !undef UMUI_LEFTMESSAGEBOX_LEFTFUNC
    !endif
    
    !ifdef UMUI_LEFT_MESSAGEBOX_LEFTFUNC
      Call `${UMUI_LEFT_MESSAGEBOX_LEFTFUNC}`
      !undef UMUI_LEFT_MESSAGEBOX_LEFTFUNC
    !endif
    
  FunctionEnd

  !insertmacro XPUI_UNSET UMUI_LEFT_MESSAGEBOX_LEFTFUNC
!macroend

!macro UMUI_UNPAGE_LEFTMESSAGEBOX T C M

  !insertmacro XPUI_CREATEID
  UninstPage custom un.umuilmb.c.${XPUI_UNIQUEID} un.umuilmb.l.${XPUI_UNIQUEID} ` `

  Function un.umuilmb.c.${XPUI_UNIQUEID}
    !insertmacro XPUI_HEADER_TEXT ` ` ` `
    !insertmacro XPUI_LEFT_MESSAGE `${C}` `${M}` `${T}`
  FunctionEnd

  Function un.umuilmb.l.${XPUI_UNIQUEID}
    !ifdef UMUI_LEFTMESSAGEBOX_VAR
      ReadINIStr `$${UMUI_LEFTMESSAGEBOX_VAR}` `$PLUGINSDIR\MBSide.ini` `Settings` `State`
    !endif

    !ifdef UMUI_LEFTMESSAGEBOX_LEFTFUNC
      Call `${UMUI_LEFTMESSAGEBOX_LEFTFUNC}`
      !undef UMUI_LEFTMESSAGEBOX_LEFTFUNC
    !endif

    !ifdef UMUI_LEFT_MESSAGEBOX_LEFTFUNC
      Call `${UMUI_LEFT_MESSAGEBOX_LEFTFUNC}`
      !undef UMUI_LEFT_MESSAGEBOX_LEFTFUNC
    !endif

  FunctionEnd

!macroend

!macro MUI_LANGUAGE LANG
  !insertmacro XPUI_LANGUAGE `${LANG}`
!macroend

!macro MUI_FUNCTION_DESCRIPTION_BEGIN
  !insertmacro XPUI_FUNCTION_DESCRIPTION_BEGIN
!macroend

!macro MUI_DESCRIPTION_TEXT SEC TEXT
  !insertmacro XPUI_DESCRIPTION_TEXT `${SEC}` `${TEXT}`
!macroend

!macro MUI_FUNCTION_DESCRIPTION_END
  !insertmacro XPUI_FUNCTION_DESCRIPTION_END
!macroend

!macro MUI_HEADER_TEXT T S
  !insertmacro XPUI_HEADER_TEXT `${T}` `${S}`
!macroend

!macro MUI_STARTMENU_WRITE_BEGIN ID
  !insertmacro XPUI_STARTMENU_WRITE_BEGIN '${ID}'
!macroend

!macro MUI_STARTMENU_WRITE_END
  !insertmacro XPUI_STARTMENU_WRITE_END
!macroend

!macro MUI_STARTMENU_GETFOLDER ID VAR

  !ifdef MUI_STARTMENUPAGE_REGISTRY_ROOT & MUI_STARTMENUPAGE_REGISTRY_KEY & MUI_STARTMENUPAGE_REGISTRY_VALUENAME

    ReadRegStr $XPUI_TEMP1 `${MUI_STARTMENUPAGE_REGISTRY_ROOT}` `${MUI_STARTMENUPAGE_REGISTRY_KEY}` `${MUI_STARTMENUPAGE_REGISTRY_VALUENAME}`
      StrCmp $XPUI_TEMP1 `` +3
        StrCpy `${VAR}` $XPUI_TEMP1
        Goto +2

        StrCpy `${VAR}` `${XPUI_STARTMENUPAGE_FOLDER}`

   !else

     StrCpy `${VAR}` `${XPUI_STARTMENUPAGE_FOLDER}`

   !endif

!macroend

!macro MUI_RESERVEFILE_LANGDLL

  !verbose push
  !verbose ${MUI_VERBOSE}

  ReserveFile `${NSISDIR}\Plugins\LangDLL.dll`

  !verbose pop

!macroend

!ifdef MUI_ABORTWARNING
!define XPUI_ABORTWARNING
!endif

!ifdef MUI_UNABORTWARNING
  !define XPUI_UNABORTWARNING
!endif

!macro MUI_LANGDLL_DISPLAY
  !insertmacro XPUI_LANGDLL_DISPLAY
!macroend

!macro MUI_UNGETLANGUAGE
  !insertmacro XPUI_UNGETLANGUAGE
!macroend

!macro MUI_LANGUAGEFILE_BEGIN L
  !insertmacro XPUI_LANGUAGEFILE_BEGIN ${L}
!macroend

!macro MUI_LANGUAGEFILE_END
  !insertmacro XPUI_LANGUAGEFILE_END
!macroend

# FIXES (DON'T EDIT)

!ifdef MUI_COMPONENTSPAGE_CHECKBITMAP
  !warning `The XPUI system uses a normal CheckBitmap command.`
  CheckBitmap `${MUI_COMPONENTSPAGE_CHECKBITMAP}`
!endif

!ifdef MUI_FINISHPAGE_NOAUTOCLOSE
  AutoCloseWindow false
!endif

!ifdef MUI_UNFINISHPAGE_NOAUTOCLOSE
  AutoCloseWindow false
!endif

!ifdef MUI_COMPONENTSPAGE_TEXT_TOP
  !ifndef MUI_COMPONENTSPAGE_TEXT_COMPLIST
    !ifndef MUI_COMPONENTSPAGE_TEXT_INSTTYPE
      ComponentText `${MUI_COMPONENTSPAGE_TEXT_TOP}`
    !endif
  !endif
!endif

!ifndef MUI_COMPONENTSPAGE_TEXT_TOP
  !ifdef MUI_COMPONENTSPAGE_TEXT_COMPLIST
    !ifndef MUI_COMPONENTSPAGE_TEXT_INSTTYPE
      ComponentText `` `${MUI_COMPONENTSPAGE_TEXT_TOP}`
    !endif
  !endif
!endif

!ifndef MUI_COMPONENTSPAGE_TEXT_TOP
  !ifndef MUI_COMPONENTSPAGE_TEXT_COMPLIST
    !ifdef MUI_COMPONENTSPAGE_TEXT_INSTTYPE
      ComponentText `` `` `${MUI_COMPONENTSPAGE_TEXT_TOP}`
    !endif
  !endif
!endif

!ifdef MUI_COMPONENTSPAGE_TEXT_TOP
  !ifdef MUI_COMPONENTSPAGE_TEXT_COMPLIST
    !ifndef MUI_COMPONENTSPAGE_TEXT_INSTTYPE
      ComponentText `${MUI_COMPONENTSPAGE_TEXT_TOP}` `${MUI_COMPONENTSPAGE_TEXT_COMPLIST}`
    !endif
  !endif
!endif

!ifdef MUI_COMPONENTSPAGE_TEXT_TOP
  !ifndef MUI_COMPONENTSPAGE_TEXT_COMPLIST
    !ifdef MUI_COMPONENTSPAGE_TEXT_INSTTYPE
      ComponentText `${MUI_COMPONENTSPAGE_TEXT_TOP}` `` `${MUI_COMPONENTSPAGE_TEXT_INSTTYPE}`
    !endif
  !endif
!endif

!ifdef MUI_COMPONENTSPAGE_TEXT_TOP
  !ifdef MUI_COMPONENTSPAGE_TEXT_COMPLIST
    !ifdef MUI_COMPONENTSPAGE_TEXT_INSTTYPE
      ComponentText `${MUI_COMPONENTSPAGE_TEXT_TOP}` `${MUI_COMPONENTSPAGE_TEXT_COMPLIST}` `${MUI_COMPONENTSPAGE_TEXT_INSTTYPE}`
    !endif
  !endif
!endif

!ifndef MUI_COMPONENTSPAGE_TEXT_TOP
  !ifdef MUI_COMPONENTSPAGE_TEXT_COMPLIST
    !ifdef MUI_COMPONENTSPAGE_TEXT_INSTTYPE
      ComponentText `` `${MUI_COMPONENTSPAGE_TEXT_COMPLIST}` `${MUI_COMPONENTSPAGE_TEXT_INSTTYPE}`
    !endif
  !endif
!endif

!ifdef UMUI_USE_ALTERNATE_PAGE
  !insertmacro XPUI_DEFAULT XPUI_FINISHPAGE_TEXT_USE_TOP_ALT ``
!endif

!ifdef MUI_WELCOMEFINISHPAGE_INI | UMUI_WELCOMEFINISHABORTPAGE_INI
  !ifndef XPUI_WARNING_MUI_IO
    !warning `The ExperienceUI does not support custom InstallOptions INI files for pages (yet).  When (if?) this is added, there will be an individual define for each page.`
    !define XPUI_WARNING_MUI_IO
  !endif
!endif

!ifdef MUI_UNWELCOMEFINISHPAGE_INI | UMUI_UNWELCOMEFINISHABORTPAGE_INI
  !ifndef XPUI_WARNING_MUI_IO
    !warning `The ExperienceUI does not support custom InstallOptions INI files for pages (yet).  When (if?) this is added, there will be an individual define for each page.`
    !define XPUI_WARNING_MUI_IO
  !endif
!endif

!ifdef MUI_FINISHPAGE_LINK_LOCATION

  !insertmacro XPUI_CREATEID
  !define XPUI_FINISHPAGE_LINK_FUNCTION muiOpenLinkLocation.${XPUI_UNIQUEID}

  Function muiOpenLinkLocation.${XPUI_UNIQUEID}
    ExecShell open `${MUI_FINISHPAGE_LINK_LOCATION}`
  FunctionEnd

!endif

!insertmacro XPUI_LANGUAGE_CONVERT

; ********************************************
; END MODERNUI/ULTRAMODERNUI CONVERSION SCRIPT

#goto InstallOptions

!macro XPUI_INSTALLOPTIONS_EXTRACT FILE
  !verbose push
  !verbose ${XPUI_VERBOSE}
  InitPluginsDir
  File `/oname=$PLUGINSDIR\${FILE}` `${FILE}`
  !insertmacro XPUI_INSTALLOPTIONS_WRITE `${FILE}` `Settings` `RTL` `$(^RTL)`
  !verbose pop
!macroend

!macro XPUI_INSTALLOPTIONS_EXTRACT_AS FILE FILENAME
  !verbose push
  !verbose ${XPUI_VERBOSE}
  InitPluginsDir
  File `/oname=$PLUGINSDIR\${FILENAME}` `${FILE}`
  !insertmacro XPUI_INSTALLOPTIONS_WRITE `${FILENAME}` `Settings` `RTL` `$(^RTL)`
  !verbose pop
!macroend

!macro XPUI_INSTALLOPTIONS_DISPLAY FILE
  !verbose push
  !verbose ${XPUI_VERBOSE}
  !insertmacro XPUI_CREATEID
  StrCmp $XPUI_ABORTED 1 `` +2
  Abort
  WriteINIStr `$PLUGINSDIR\${FILE}` `Settings` `RTL` `$(^RTL)`
  InstallOptions::initDialog /NOUNLOAD `$PLUGINSDIR\${FILE}`
  Pop $XPUI_HWND
  !insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_HWND
  LockWindow on
  StrCpy $XPUI_TEMP2 1199
  xpui.loop.setctl.${XPUI_UNIQUEID}:
  IntOp $XPUI_TEMP2 $XPUI_TEMP2 + 1
  GetDlgItem $XPUI_TEMP1 $XPUI_HWND $XPUI_TEMP2
  !insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
  IntCmp $XPUI_TEMP2 ${XPUI_INSTALLOPTIONS_MAXFIELD} `` xpui.loop.setctl.${XPUI_UNIQUEID}
  GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1018
  !insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
  LockWindow off
  InstallOptions::show
  !verbose pop
!macroend

!macro XPUI_INSTALLOPTIONS_DISPLAY_RETURN FILE
  !verbose push
  !verbose ${XPUI_VERBOSE}
  !insertmacro XPUI_CREATEID
  StrCmp $XPUI_ABORTED 1 `` +2
  Abort
  WriteINIStr `$PLUGINSDIR\${FILE}` `Settings` `RTL` `$(^RTL)`
  InstallOptions::initDialog /NOUNLOAD `$PLUGINSDIR\${FILE}`
  Pop $XPUI_HWND
  LockWindow on
  !insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_HWND
  StrCpy $XPUI_TEMP2 1199
  xpui.loop.setctl.${XPUI_UNIQUEID}:
  IntOp $XPUI_TEMP2 $XPUI_TEMP2 + 1
  GetDlgItem $XPUI_TEMP1 $XPUI_HWND $XPUI_TEMP2
  StrCmp $XPUI_TEMP1 0 xpui.done
  !insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
  IntCmp $XPUI_TEMP2 ${XPUI_INSTALLOPTIONS_MAXFIELD} xpui.done.${XPUI_UNIQUEID} xpui.loop.setctl.${XPUI_UNIQUEID}
  xpui.done.${XPUI_UNIQUEID}:
  LockWindow off
  InstallOptions::show
  !verbose pop
!macroend

!macro XPUI_INSTALLOPTIONS_INITDIALOG FILE
  !verbose push
  !verbose ${XPUI_VERBOSE}
  !insertmacro XPUI_CREATEID
  StrCmp $XPUI_ABORTED 1 `` +2
  Abort
  WriteINIStr `$PLUGINSDIR\${FILE}` `Settings` `RTL` `$(^RTL)`
  InstallOptions::initDialog /NOUNLOAD `$PLUGINSDIR\${FILE}`
  Pop $XPUI_HWND
  LockWindow on
  !insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_HWND
  StrCpy $XPUI_TEMP2 1199
  xpui.loop.setctl.${XPUI_UNIQUEID}:
  IntOp $XPUI_TEMP2 $XPUI_TEMP2 + 1
  GetDlgItem $XPUI_TEMP1 $XPUI_HWND $XPUI_TEMP2
  StrCmp $XPUI_TEMP1 0 xpui.done.${XPUI_UNIQUEID}
  !insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
  IntCmp $XPUI_TEMP2 ${XPUI_INSTALLOPTIONS_MAXFIELD} xpui.done.${XPUI_UNIQUEID} xpui.loop.setctl.${XPUI_UNIQUEID}
  xpui.done.${XPUI_UNIQUEID}:
  LockWindow off
  Push $XPUI_HWND
  !verbose 4
!macroend

!macro XPUI_INSTALLOPTIONS_SHOW

  !verbose push
  !verbose ${XPUI_VERBOSE}
  InstallOptions::show
  Pop $XPUI_TEMP1
  !verbose pop
!macroend

!macro XPUI_INSTALLOPTIONS_SHOW_RETURN

  !verbose push
  !verbose ${XPUI_VERBOSE}
  InstallOptions::show
  !verbose pop
!macroend

!macro XPUI_INSTALLOPTIONS_READ VAR FILE SECTION KEY
  !verbose push
  !verbose ${XPUI_VERBOSE}
  ReadIniStr ${VAR} `$PLUGINSDIR\${FILE}` `${SECTION}` `${KEY}`
  !verbose pop
!macroend

!macro XPUI_INSTALLOPTIONS_WRITE FILE SECTION KEY VALUE

  !verbose push
  !verbose ${XPUI_VERBOSE}
  WriteIniStr `$PLUGINSDIR\${FILE}` `${SECTION}` `${KEY}` `${VALUE}`
  !verbose pop
!macroend

!macro XPUI_RESERVEFILE_INSTALLOPTIONS
  !verbose push
  !verbose ${XPUI_VERBOSE}
  ReserveFile `${NSISDIR}\Plugins\InstallOptions.dll`
  !verbose pop
!macroend

!macro MUI_INSTALLOPTIONS_EXTRACT F
  !insertmacro XPUI_INSTALLOPTIONS_EXTRACT `${F}`
!macroend

!macro MUI_INSTALLOPTIONS_EXTRACT_AS O N
  !insertmacro XPUI_INSTALLOPTIONS_EXTRACT_AS `${O}` `${N}`
!macroend

!macro MUI_INSTALLOPTIONS_DISPLAY F
  !insertmacro XPUI_INSTALLOPTIONS_DISPLAY `${F}`
!macroend

!macro MUI_INSTALLOPTIONS_DISPLAY_RETURN F
  !insertmacro XPUI_INSTALLOPTIONS_DISPLAY_RETURN `${F}`
!macroend

!macro MUI_INSTALLOPTIONS_INITDIALOG F
  !insertmacro XPUI_INSTALLOPTIONS_INITDIALOG `${F}`
!macroend

!macro MUI_INSTALLOPTIONS_SHOW
  !insertmacro XPUI_INSTALLOPTIONS_SHOW
!macroend

!macro MUI_INSTALLOPTIONS_SHOW_RETURN
  !insertmacro XPUI_INSTALLOPTIONS_SHOW_RETURN
!macroend

!macro MUI_INSTALLOPTIONS_READ VAR FILE SECTION KEY
  !verbose push
  !verbose ${XPUI_VERBOSE}
  ReadIniStr ${VAR} `$PLUGINSDIR\${FILE}` `${SECTION}` `${KEY}`
  !verbose pop
!macroend

!macro MUI_INSTALLOPTIONS_WRITE FILE SECTION KEY VALUE

  !verbose push
  !verbose ${XPUI_VERBOSE}
  WriteIniStr `$PLUGINSDIR\${FILE}` `${SECTION}` `${KEY}` `${VALUE}`
  !verbose pop
!macroend

!macro MUI_RESERVEFILE_INSTALLOPTIONS
  !verbose push
  !verbose ${XPUI_VERBOSE}
  ReserveFile `${NSISDIR}\Plugins\InstallOptions.dll`
  !verbose pop
!macroend

#goto GUIInit

; GUI INIT

; Removed because of MUI language conflicts
; MiscButtonText `$(XPUI_BUTTONTEXT_BACK)` `$(XPUI_BUTTONTEXT_NEXT)` `$(XPUI_BUTTONTEXT_CANCEL)` `$(XPUI_BUTTONTEXT_CLOSE)`

; MAIN GUI INIT (INSTALL AND UNINSTALL)

!macro XPUI_GUIINIT UNPREFIX UNFUNCPREFIX

!ifdef XPUI_WANSIS
  !insertmacro XPUI_WANSIS_INIT
!endif

InitPluginsDir

; HEADER TITLE
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1037
CreateFont $XPUI_TEMP2 `$(^Font)` `$(^FontSize)` `700`
SendMessage $XPUI_TEMP1 0x30 $XPUI_TEMP2 0
!ifndef XPUI_FULLBODY_SKIN
  SetCtlColors $XPUI_TEMP1 ${XPUI_TEXT_COLOR} `transparent`
!endif

; HEADER SUBTITLE
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1038
!ifndef XPUI_FULLBODY_SKIN
  SetCtlColors $XPUI_TEMP1 ${XPUI_TEXT_COLOR} `transparent`
!endif

; HEADER BACKGROUND
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1034
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1

; BOTTOM LABEL (NO BOTTOM IMAGE ONLY)
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1039
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1

; BRANDING TEXT

GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1028
  SetCtlColors $XPUI_TEMP1 "${XPUI_BRANDINGTEXT_COLOR_FG}" Transparent
  CreateFont $XPUI_TEMP2 "Trebuchet MS" 10 700
  SendMessage $XPUI_TEMP1 0x30 $XPUI_TEMP2 0
  SendMessage $XPUI_TEMP1 0xC 0 "STR:$(^Branding)"

GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1256
  SetCtlColors $XPUI_TEMP1 "${XPUI_BRANDINGTEXT_COLOR_BG}" Transparent
  CreateFont $XPUI_TEMP2 "Trebuchet MS" 10 700
  SendMessage $XPUI_TEMP1 0x30 $XPUI_TEMP2 0
  SendMessage $XPUI_TEMP1 0xC 0 "STR:$(^Branding)"

; LEFT INFO PANEL: TOP
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1100
CreateFont $XPUI_TEMP2 Arial 8 700
SendMessage `$XPUI_TEMP1` `0x30` `$XPUI_TEMP2` `$XPUI_TEMP2`
SetCtlColors $XPUI_TEMP1 ${XPUI_TEXT_LIGHTCOLOR} `Transparent`
ShowWindow $XPUI_TEMP1 0

; LEFT INFO PANEL: BOTTOM
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1101
CreateFont $XPUI_TEMP2 Arial 8 350
SendMessage `$XPUI_TEMP1` `0x30` `$XPUI_TEMP2` `$XPUI_TEMP2`
!ifndef XPUI_FULLBODY_SKIN
SetCtlColors `$XPUI_TEMP1` ${XPUI_TEXT_COLOR} Transparent
!endif
ShowWindow $XPUI_TEMP1 0

; LEFT INFO PANEL: BUTTONS
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1102
ShowWindow $XPUI_TEMP1 0
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1103
ShowWindow $XPUI_TEMP1 0
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1104
ShowWindow $XPUI_TEMP1 0

; LEFT INFO PANEL: InstallOptions RECTANGLE CONTROL
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1304
ShowWindow $XPUI_TEMP1 5

; NEXT BUTTON
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1

; CANCEL BUTTON
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 2
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1

; BACK BUTTON
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 3
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1

; LEFT INFO PANEL: BITMAP BG
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1300
SendMessage $XPUI_TEMP1 0xC 0 `STR:`

; LEFT PANEL MAIN BACKGROUND
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1301
SendMessage $XPUI_TEMP1 0xC 0 `STR:`
!ifndef XPUI_FULLBODY_SKIN
SetCtlColors $XPUI_TEMP1 0xFFFFFF ${XPUI_TEXT_BGCOLOR}
!endif

; OBSOLETE RECTANGLE 1044
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1044
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
ShowWindow $XPUI_TEMP1 5

; MISC. LEFT CONTROLS
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1304
SetCtlColors $XPUI_TEMP1 Transparent Transparent
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1308
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1305
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1306
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1307
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1309
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1047
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1048
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1

; MAIN RECTANGLE
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1018
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1

; HEADER IMAGE BITMAP CONTROL
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1046
SetCtlColors $XPUI_TEMP1 ${XPUI_TEXT_BGCOLOR} Transparent
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1034
SetCtlColors $XPUI_TEMP1 `` Transparent

; OBSOLETE RECTANGLE 1044
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1044
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1

; SET LEFT LOGO
SetOutPath `$PLUGINSDIR`
File /oname=$PLUGINSDIR\LeftImg.bmp `${XPUI_${UNPREFIX}LEFTLOGO}`
!ifndef XPUI_LEFTLOGO_NORESIZETOFIT
SetBrandingImage /IMGID=1302 /RESIZETOFIT `$PLUGINSDIR\LeftImg.bmp`
!endif

!ifdef XPUI_LEFTLOGO_NORESIZETOFIT
SetBrandingImage /IMGID=1302 `$PLUGINSDIR\LeftImg.bmp`
!endif

; SET HEADER IMAGE
!ifndef XPUI_WANSIS
SetOutPath `$PLUGINSDIR`
File /oname=Header.bmp `${XPUI_${UNPREFIX}HEADERIMAGE}`
!ifndef XPUI_${UNPREFIX}HEADERIMAGE_NORESIZETOFIT
SetBrandingImage /IMGID=1046 /RESIZETOFIT `$PLUGINSDIR\Header.bmp`
!else
SetBrandingImage /IMGID=1046 `$PLUGINSDIR\Header.bmp`
!endif
!endif ; XPUI_WANSIS

!ifdef XPUI_WANSIS
  !ifndef XPUI_WANSIS_HEADERIMAGE
    GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1046
    ShowWindow $XPUI_TEMP1 0
  !else
    GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1047
    ShowWindow $XPUI_TEMP1 0
    SetOutPath $PLUGINSDIR
    File `/oname=$PLUGINSDIR\Header.bmp` `${XPUI_WANSIS_HEADERIMAGE_BMP}`
    SetBrandingImage /IMGID=1046 `$PLUGINSDIR\Header.bmp`
  !endif
!endif

; SET BOTTOM IMAGE
!ifdef XPUI_${UNPREFIX}BOTTOMIMAGE
SetOutPath $PLUGINSDIR
File /oname=BtmImg.bmp `${XPUI_${UNPREFIX}BOTTOMIMAGE_BMP}`
!ifndef XPUI_${UNPREFIX}BOTTOMIMAGE_NORESIZETOFIT
SetBrandingImage /IMGID=1039 /RESIZETOFIT `$PLUGINSDIR\BtmImg.bmp`
!endif
!ifdef XPUI_${UNPREFIX}BOTTOMIMAGE_NORESIZETOFIT
SetBrandingImage /IMGID=1039 `$PLUGINSDIR\BtmImg.bmp`
!endif
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1302
;!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1045
ShowWindow $XPUI_TEMP1 0
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1035
ShowWindow $XPUI_TEMP1 0
!endif

; OTHER CONTROLS
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1018
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1039
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1035
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1045
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1

; BACKGROUND IMAGE ON PAGES

!ifdef XPUI_PAGE_BGIMAGE
FindWindow $XPUI_TEMP1 "#32770" "" $HWNDPARENT
SetCtlColors $XPUI_TEMP1 Transparent Transparent
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1018
SetCtlColors $XPUI_TEMP1 Transparent Transparent
File "/oname=page.bmp" "${XPUI_PAGE_BGIMAGE_BMP}"
SetBrandingImage /IMGID=1019 /RESIZETOFIT "$PLUGINSDIR\page.bmp"
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1019
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
!endif

!ifndef XPUI_FULLBODY_SKIN
  GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1038
  SetCtlColors $XPUI_TEMP1 ${XPUI_TEXT_COLOR} `transparent`
!endif

; RESET ABORT PAGE VARIABLE
!ifndef XPUI_ABORT_RESET_DISABLE
StrCpy $XPUI_ABORTED 0
!endif

; CALL THE USER FUNCTION IF IT EXISTS
!ifdef `XPUI_CUSTOMFUNCTION_${UNPREFIX}GUIINIT`
Call `${XPUI_CUSTOMFUNCTION_${UNPREFIX}GUIINIT}`
!endif

; Modern UI Compatible
!ifdef `MUI_CUSTOMFUNCTION_${UNPREFIX}GUIINIT`
Call `${MUI_CUSTOMFUNCTION_${UNPREFIX}GUIINIT}`
!endif

!macroend

!macro XPUI_FUNCTION_GUIINIT

!ifndef XPUI_INTERNAL_MUI_CONVERTER_GUI_INCLUDED
  !define XPUI_INTERNAL_MUI_CONVERTER_GUI_INCLUDED
!endif

InstallColors ${XPUI_TEXT_COLOR} ${XPUI_TEXT_BGCOLOR}

Function .onGUIInit
!insertmacro XPUI_GUIINIT `` ``
FunctionEnd

Function un.onGUIInit
!insertmacro XPUI_GUIINIT UN un.
FunctionEnd

!macroend

; GUI DE-INITIALIZATION
Function .onGUIEnd

!ifdef XPUI_WANSIS
  !insertmacro XPUI_WANSIS_UNIT
!endif

; CALL THE DEVELOPER'S OWN CLEANUP, IF APPLICABLE
!ifdef `XPUI_CUSTOMFUNCTION_GUIEND`
Call `${XPUI_CUSTOMFUNCTION_GUIEND}`
!endif

; DELETE BITMAP IMAGES
Delete $PLUGINSDIR\Header.bmp
Delete $PLUGINSDIR\BtmImg.bmp
Delete $PLUGINSDIR\LeftImg.bmp

; IF USING A NON-DEFAULT SKIN, CLEAN UP ITS FILES
!ifmacrodef XPUI_BGFILES_DELETE
  !insertmacro XPUI_BGFILES_DELETE
!endif

; UNLOCK THE MAIN WINDOW
LockWindow off

FunctionEnd

; GUI DE-INITIALIZATION
Function un.onGUIEnd

!ifdef XPUI_WANSIS
  !insertmacro XPUI_WANSIS_UNIT
!endif

; CALL THE DEVELOPER'S OWN CLEANUP, IF APPLICABLE
!ifdef `XPUI_CUSTOMFUNCTION_UNGUIEND`
Call `${XPUI_CUSTOMFUNCTION_UNGUIEND}`
!endif

; DELETE BITMAP IMAGES
Delete $PLUGINSDIR\Header.bmp
Delete $PLUGINSDIR\BtmImg.bmp
Delete $PLUGINSDIR\LeftImg.bmp

; IF USING A NON-DEFAULT SKIN, CLEAN UP ITS FILES
!ifmacrodef XPUI_BGFILES_DELETE
  !insertmacro XPUI_BGFILES_DELETE
!endif

; UNLOCK THE MAIN WINDOW
LockWindow off

FunctionEnd

!macro XPUI_PAGEBG_INIT
LockWindow on
FindWindow $XPUI_TEMP1 `#32770` `` $HWNDPARENT
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP1 1005
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
!ifdef XPUI_FULLBODY_SKIN
  ShowWindow $XPUI_TEMP1 0
!endif
LockWindow off

!ifmacrodef XPUI_SET_BG
  !ifndef XPUI_DISABLEBG
    !ifndef XPUI_INTERNAL_BG_INSERTED
      !define XPUI_INTERNAL_BG_INSERTED
      !insertmacro XPUI_SET_BG
    !endif
  !endif
!endif

    !ifdef XPUI_PAGE_BGIMAGE

      GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1019
      ShowWindow $XPUI_TEMP1 5
      GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1047
      ShowWindow $XPUI_TEMP1 5

    !endif

!macroend

!macro XPUI_PAGECOLOR_INIT ID
FindWindow $XPUI_TEMP1 `#32770` `` $HWNDPARENT
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP1 ${ID}
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
!macroend

!macro XPUI_INNERDIALOG_TEXT TEXT ID
FindWindow $XPUI_TEMP1 `#32770` `` $HWNDPARENT
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP1 ${ID}
SendMessage $XPUI_TEMP1 0xC 0 `STR:${TEXT}`
!macroend

; MISC. BUTTON TEXT
; MiscButtonText `$(XPUI_BUTTONTEXT_BACK)` `$(XPUI_BUTTONTEXT_NEXT)` `$(XPUI_BUTTONTEXT_CANCEL)` `$(XPUI_BUTTONTEXT_CLOSE)`

  ; UI SETTINGS
  ; THE HEADER IMAGE IN XPUI IS MANDATORY!
  !insertmacro XPUI_DEFAULT XPUI_UI `${NSISDIR}\Contrib\ExperienceUI\UIs\UI.exe`
  ChangeUI all `${XPUI_UI}`

  !ifndef XPUI_SEPERATE_IDDINST
  !define XPUI_SEPERATE_IDDINST
  !endif

  !ifdef XPUI_SEPERATE_IDDINST
  
    !ifdef XPUI_PAGE_BGIMAGE
      !insertmacro XPUI_DEFAULT XPUI_UI_IDDINST `${NSISDIR}\Contrib\ExperienceUI\UIs\BgUI.exe`
    !else
      !insertmacro XPUI_DEFAULT XPUI_UI_IDDINST `${NSISDIR}\Contrib\ExperienceUI\UIs\HeaderUI.exe`
    !endif
    ChangeUI IDD_INST `${XPUI_UI_IDDINST}`
  !endif

  !ifdef XPUI_BOTTOMIMAGE

    !ifdef XPUI_PAGE_BGIMAGE
      !insertmacro XPUI_DEFAULT XPUI_UI_BOTTOMIMAGE `${NSISDIR}\Contrib\ExperienceUI\UIs\BgUI.exe`
    !else
      !insertmacro XPUI_DEFAULT XPUI_UI_BOTTOMIMAGE `${NSISDIR}\Contrib\ExperienceUI\UIs\HeaderUI_BtmImg.exe`
    !endif

  ChangeUI all `${XPUI_UI_BOTTOMIMAGE}`
  !endif
  
  !ifdef XPUI_WANSIS
    !insertmacro XPUI_DEFAULT XPUI_WANSIS_UI        `${NSISDIR}\Contrib\ExperienceUI\UIs\WAnsis_ui.exe`
    ChangeUI all `${XPUI_WANSIS_UI}`
  !endif
  
  !ifdef UMUI_USE_BG
  !verbose 4
    !include `${NSISDIR}\Contrib\UltraModernUI\BGSkins\${UMUI_USE_BG}.nsh`
  !verbose ${XPUI_VERBOSE}

  !macro XPUI_SET_BG
    !insertmacro UMUI_BG
  !macroend

!endif

; ------------------------------------------------------
; XPUI Interface (inserted with first language in 1.1)

!macro XPUI_INTERFACE

  !ifdef XPUI_SKIN
    !verbose 1
    !include `${NSISDIR}\Contrib\ExperienceUI\Skins\${XPUI_SKIN}.XPUIskin`
    !verbose ${XPUI_VERBOSE}
  !endif

!ifdef UMUI_SKIN
; We'll just assume that if the user defined UMUI_SKIN he wants to use a UMUI-style installer
!verbose 4
!include `${NSISDIR}\Contrib\UltraModernUI\Skins\${UMUI_SKIN}.nsh`
!verbose ${XPUI_VERBOSE}
!insertmacro XPUI_CONVERT XPUI_HEADERIMAGE                            MUI_HEADERIMAGE_BITMAP
!insertmacro XPUI_CONVERT XPUI_HEADERIMAGE_NORESIZETOFIT              MUI_HEADERIMAGE_BITMAP_NOSTRETCH
!insertmacro XPUI_CONVERT XPUI_UNHEADERIMAGE                          MUI_HEADERIMAGE_UNBITMAP
!insertmacro XPUI_CONVERT XPUI_UNHEADERIMAGE_NORESIZETOFIT            MUI_HEADERIMAGE_UNBITMAP_NOSTRETCH
!insertmacro XPUI_CONVERT XPUI_LEFTLOGO                               UMUI_LEFTIMAGE_BMP
!insertmacro XPUI_CONVERT XPUI_BOTTOMIMAGE_BMP                        UMUI_BOTTOMIMAGE_BMP
!insertmacro XPUI_CONVERT XPUI_UNBOTTOMIMAGE_BMP                      UMUI_BOTTOMIMAGE_BMP
!insertmacro XPUI_CONVERT XPUI_TEXT_COLOR                             MUI_TEXT_COLOR
!insertmacro XPUI_CONVERT XPUI_TEXT_BGCOLOR                           MUI_BGCOLOR
!insertmacro XPUI_CONVERT XPUI_TEXT_LIGHTCOLOR                        UMUI_TEXT_LIGHTCOLOR
!insertmacro XPUI_CONVERT XPUI_HEADERIMAGE                            UMUI_HEADERIMAGE_BMP
!insertmacro XPUI_CONVERT XPUI_UNHEADERIMAGE                          UMUI_UNHEADERIMAGE_BMP
!insertmacro XPUI_CONVERT XPUI_ICON                                   MUI_ICON
!insertmacro XPUI_CONVERT XPUI_UNICON                                 MUI_UNICON
!insertmacro XPUI_CONVERT XPUI_BRANDINGTEXT_COLOR_BG                  UMUI_BRANDINGTEXTBACKCOLOR
!insertmacro XPUI_CONVERT XPUI_BRANDINGTEXT_COLOR_FG                  UMUI_BRANDINGTEXTFRONTCOLOR
!ifndef XPUI_LEFTLOGO_NORESIZETOFIT
!define XPUI_LEFTLOGO_NORESIZETOFIT
!endif
!ifndef UMUI_NO_BOTTOMIMAGE
  !insertmacro XPUI_DEFAULT XPUI_BOTTOMIMAGE ``
  !insertmacro XPUI_DEFAULT XPUI_UNBOTTOMIMAGE ``
!endif
;!insertmacro XPUI_DEFAULT XPUI_UI_IDDINST `${NSISDIR}\Contrib\UIs\UltraModernUI\UltraModern.exe`
;!insertmacro XPUI_DEFAULT XPUI_UI_BOTTOMIMAGE `${NSISDIR}\Contrib\UIs\UltraModernUI\UltraModern.exe`

XPStyle off
!endif

  !insertmacro XPUI_DEFAULT XPUI_TEXT_COLOR `0xB4D3EA`
  !insertmacro XPUI_DEFAULT XPUI_TEXT_BGCOLOR `0x566978`
  !insertmacro XPUI_DEFAULT XPUI_TEXT_LIGHTCOLOR `0xFFFFFF`

  !insertmacro XPUI_LANGUAGE_CONVERT

  !ifdef XPUI_WANSIS
    !insertmacro XPUI_DEFAULT XPUI_WANSIS_SKIN Forum
    !insertmacro XPUI_DEFAULT XPUI_LEFTLOGO               `${NSISDIR}\Contrib\ExperienceUI\Skins\${XPUI_WANSIS_SKIN}\LeftLogo.bmp`
    !insertmacro XPUI_DEFAULT XPUI_BOTTOMIMAGE            ``
    !insertmacro XPUI_DEFAULT XPUI_UNBOTTOMIMAGE          ``
    !insertmacro XPUI_DEFAULT XPUI_BOTTOMIMAGE_BMP        `${NSISDIR}\Contrib\ExperienceUI\Skins\${XPUI_WANSIS_SKIN}\Bottom.bmp`
    !insertmacro XPUI_DEFAULT XPUI_WANSIS_GEN             `${NSISDIR}\Contrib\ExperienceUI\Skins\${XPUI_WANSIS_SKIN}\gen.bmp`
    !insertmacro XPUI_DEFAULT XPUI_WANSIS_GENEX           `${NSISDIR}\Contrib\ExperienceUI\Skins\${XPUI_WANSIS_SKIN}\genex.bmp`
    !insertmacro XPUI_DEFAULT XPUI_WANSIS_HEADERIMAGE_BMP `${NSISDIR}\Contrib\ExperienceUI\Skins\${XPUI_WANSIS_SKIN}\header.bmp`
  !endif
  
  !insertmacro XPUI_DEFAULT XPUI_ICON `${NSISDIR}\Contrib\Graphics\Icons\XPUI-install.ico`
  !insertmacro XPUI_DEFAULT XPUI_UNICON `${NSISDIR}\Contrib\Graphics\Icons\XPUI-uninstall.ico`

  !ifdef XPUI_ICON
  Icon `${XPUI_ICON}`
  !Endif

  !ifdef XPUI_UNICON
  UninstallIcon `${XPUI_UNICON}`
  !Endif
  
  ; DEFAULTS

  !ifndef XPUI_DISABLEBG
    !ifdef XPUI_BGGRADIENT
      !ifndef XPUI_BGGRADIENT_CUSTOMCOLORS
        BGGradient ${XPUI_TEXT_COLOR} ${XPUI_TEXT_BGCOLOR} ${XPUI_TEXT_LIGHTCOLOR}
      !endif
    !ifdef XPUI_BGGRADIENT_CUSTOMCOLORS
      !insertmacro XPUI_DEFAULT XPUI_BGGRADIENT_CUSTOMCOLORS_TOP `${XPUI_TEXT_COLOR}`
      !insertmacro XPUI_DEFAULT XPUI_BGGRADIENT_CUSTOMCOLORS_BOTTOM `${XPUI_TEXT_BGCOLOR}`
        !insertmacro XPUI_DEFAULT XPUI_BGGRADIENT_CUSTOMCOLORS_TEXT `${XPUI_TEXT_LIGHTCOLOR}`
        BGGradient `${XPUI_BGGRADIENT_CUSTOMCOLORS_TOP}` `${XPUI_BGGRADIENT_CUSTOMCOLORS_BOTTOM}` `${XPUI_BGGRADIENT_CUSTOMCOLORS_TEXT}`
      !endif
    !endif
  !endif

  !ifdef XPUI_PAGE_BGIMAGE
    !insertmacro XPUI_DEFAULT XPUI_PAGE_BGIMAGE_BMP    `${NSISDIR}\Contrib\ExperienceUI\Skins\Default\Page.bmp`
    !insertmacro XPUI_DEFAULT XPUI_LEFTLOGO `${NSISDIR}\Contrib\ExperienceUI\Skins\Default\lb_page.bmp`
  !else
    !insertmacro XPUI_DEFAULT XPUI_LEFTLOGO `${NSISDIR}\Contrib\ExperienceUI\Skins\Default\LeftBranding.bmp`
  !endif
  !insertmacro XPUI_DEFAULT XPUI_UNLEFTLOGO `${XPUI_LEFTLOGO}`
  !insertmacro XPUI_DEFAULT XPUI_HEADERIMAGE `${NSISDIR}\Contrib\ExperienceUI\Skins\Default\Header.bmp`
  !insertmacro XPUI_DEFAULT XPUI_UNHEADERIMAGE `${XPUI_HEADERIMAGE}`
  !insertmacro XPUI_DEFAULT XPUI_BOTTOMIMAGE_BMP `${NSISDIR}\Contrib\ExperienceUI\Skins\Default\Bottom.bmp`
  !insertmacro XPUI_DEFAULT XPUI_UNBOTTOMIMAGE_BMP `${XPUI_BOTTOMIMAGE_BMP}`

  !insertmacro XPUI_DEFAULT XPUI_BRANDINGTEXT ` `
  !insertmacro XPUI_DEFAULT XPUI_BRANDINGTEXT_COLOR_FG 606060
  !insertmacro XPUI_DEFAULT XPUI_BRANDINGTEXT_COLOR_BG 808080
  
  !ifdef XPUI_BOTTOMIMAGE
  !insertmacro XPUI_DEFAULT XPUI_UI_BOTTOMIMAGE `${NSISDIR}\Contrib\ExperienceUI\UIs\HeaderUI_BtmImg.exe`
  ChangeUI all `${XPUI_UI_BOTTOMIMAGE}`
  !endif
  
  !ifdef XPUI_WANSIS
    !insertmacro XPUI_DEFAULT XPUI_WANSIS_UI        `${NSISDIR}\Contrib\ExperienceUI\UIs\WAnsis_ui.exe`
    ChangeUI all `${XPUI_WANSIS_UI}`
  !endif
  
  BrandingText `${XPUI_BRANDINGTEXT}`

  ; Defaults are now defined, so it's time to insert the interface...
  !insertmacro XPUI_FUNCTION_GUIINIT
  
  !ifdef MUI_ABORTWARNING
    !ifndef XPUI_ABORTWARNING
      !define XPUI_ABORTWARNING
    !endif
  !endif
  
  !ifdef XPUI_ABORTWARNING
  !ifndef XPUI_AW_I ; I'm too lazy for XPUI_ABORTWARNING_FUNCTION_INSERTED or something fancy like that...
  !define XPUI_AW_I

    Function .onUserAbort
      StrCmp $NOABORTWARNING 1 xpui.done
      MessageBox MB_YESNO|MB_ICONQUESTION `$(XPUI_ABORTWARNING_TEXT)` IDYES +2
      Abort
      !insertmacro XPUI_USERABORT
      xpui.done:
      !ifdef MUI_CUSTOMFUNCTION_ABORT
        Call `${MUI_CUSTOMFUNCTION_ABORT}`
      !endif
    FunctionEnd

  !endif
  !endif
  
  !ifdef XPUI_UNABORTWARNING
  !ifndef XPUI_AW_U_I ; I'm too lazy for XPUI_ABORTWARNING_FUNCTION_INSERTED or something fancy like that...
  !define XPUI_AW_U_I

    Function un.onUserAbort
      StrCmp $NOABORTWARNING 1 xpui.done
      MessageBox MB_YESNO|MB_ICONQUESTION `You are about to quit uninstall.$\n$\nIf you quit now, $(^Name) will not be uninstalled.$\n$\nDo you want to continue?` IDYES +2
      Abort
      !insertmacro XPUI_USERABORT
      xpui.done:
      !ifdef MUI_CUSTOMFUNCTION_ABORT
        Call `${MUI_CUSTOMFUNCTION_ABORT}`
      !endif
    FunctionEnd

  !endif
  !endif

!macroend

!verbose 4
!ifndef XPUI_SILENT
!echo `  XPUI: Processing pages...$\n  `
!endif
!verbose ${XPUI_VERBOSE}

#goto WelcomePage

; +-------+
; | PAGES |
; +-------+

; WELCOME PAGE

!macro XPUI_PAGE_WELCOME

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_PAGE_WELCOME (${XPUI_PAGEMODE} Welcome Page)`
  !endif
  !verbose ${XPUI_VERBOSE}
!insertmacro XPUI_CREATEID

!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}WELCOMEPAGE_TITLE "$(XPUI_${XPUI_UN}WELCOMEPAGE_TITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}WELCOMEPAGE_SUBTITLE "$(XPUI_${XPUI_UN}WELCOMEPAGE_SUBTITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}WELCOMEPAGE_TEXT_TOP "$(XPUI_${XPUI_UN}WELCOMEPAGE_TEXT_TOP)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}WELCOMEPAGE_TEXT "$(XPUI_${XPUI_UN}WELCOMEPAGE_TEXT)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}WELCOMEPAGE_CAPTION "$(XPUI_${XPUI_UN}WELCOMEPAGE_CAPTION)"

!ifndef XPUI_VAR_HWND
Var XPUI_HWND
!define XPUI_VAR_HWND
!endif
PageEx ${XPUI_UNFUNC}custom
PageCallbacks ${XPUI_UNFUNC}XPUI.io.WelcomePage.${XPUI_UNIQUEID} ${XPUI_UNFUNC}XPUI.io.WelcomePage.${XPUI_UNIQUEID}.leave
Caption `${XPUI_${XPUI_UN}WELCOMEPAGE_CAPTION}`
PageExEnd

Function ${XPUI_UNFUNC}XPUI.io.WelcomePage.${XPUI_UNIQUEID}
LockWindow on
StrCmp $XPUI_ABORTED 1 Aborted.${XPUI_UNIQUEID}
!insertmacro XPUI_PAGEBG_INIT
SetOutPath $PLUGINSDIR
File `${NSISDIR}\Contrib\ExperienceUI\INI\isWelcome.ini`
!ifdef XPUI_${XPUI_UN}PAGE_CUSTOMFUNCTION_PRE
Call `${XPUI_${XPUI_UN}PAGE_CUSTOMFUNCTION_PRE}`
!undef XPUI_${XPUI_UN}PAGE_CUSTOMFUNCTION_PRE
!endif
WriteINIStr $PLUGINSDIR\isWelcome.ini `Field 2` Text `${XPUI_${XPUI_UN}WELCOMEPAGE_TEXT}`
WriteINIStr `$PLUGINSDIR\isWelcome.ini` `Settings` `RTL` `$(^RTL)`
InstallOptions::initDialog /NOUNLOAD `$PLUGINSDIR\isWelcome.ini`
Pop $XPUI_HWND
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_HWND
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1200
CreateFont $XPUI_TEMP2 `$(^Font)` 12 700 ; previously Tahoma
SendMessage $XPUI_TEMP1 0x30 $XPUI_TEMP2 $XPUI_TEMP2
SendMessage $XPUI_TEMP1 0xC 0 `STR:${XPUI_${XPUI_UN}WELCOMEPAGE_TEXT_TOP}`
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1201
CreateFont $XPUI_TEMP2 `$(^Font)` 8 350 ; previously Tahoma
SendMessage $XPUI_TEMP1 0x30 $XPUI_TEMP2 $XPUI_TEMP2
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 3
ShowWindow $XPUI_TEMP1 0
!insertmacro XPUI_HEADER_TEXT `${XPUI_${XPUI_UN}WELCOMEPAGE_TITLE}` `${XPUI_${XPUI_UN}WELCOMEPAGE_SUBTITLE}`

!insertmacro XPUI_PAGE_CUSTOMFUNCTION SHOW

LockWindow off
InstallOptions::show
Aborted.${XPUI_UNIQUEID}:
FunctionEnd

Function ${XPUI_UNFUNC}XPUI.io.WelcomePage.${XPUI_UNIQUEID}.leave
!ifndef XPUI_NOLOCK
LockWindow on
!endif
!insertmacro XPUI_PAGE_CUSTOMFUNCTION LEAVE
FunctionEnd

!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_PRE
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_SHOW
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_LEAVE

!undef XPUI_${XPUI_UN}WELCOMEPAGE_TITLE
!undef XPUI_${XPUI_UN}WELCOMEPAGE_SUBTITLE
!undef XPUI_${XPUI_UN}WELCOMEPAGE_TEXT_TOP
!undef XPUI_${XPUI_UN}WELCOMEPAGE_TEXT
!undef XPUI_${XPUI_UN}WELCOMEPAGE_CAPTION

!verbose pop
!macroend

#goto WelcomePage2

; ALTERNATATIVE WELCOME PAGE
!macro XPUI_PAGE_WELCOME2

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_PAGE_WELCOME2 (${XPUI_PAGEMODE} InstallShieldЦ-style Welcome Page)`
  !endif
  !verbose ${XPUI_VERBOSE}
!insertmacro XPUI_CREATEID

!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_TITLE "$(XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_TITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_SUBTITLE "$(XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_SUBTITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_TEXT_TOP "$(XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_TEXT_TOP)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_TEXT "$(XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_TEXT)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_CAPTION "$(XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_CAPTION)"

!ifndef XPUI_VAR_HWND
Var XPUI_HWND
!define XPUI_VAR_HWND
!endif
PageEx ${XPUI_UNFUNC}custom
PageCallbacks ${XPUI_UNFUNC}XPUI.io.WelcomePage.Style2.${XPUI_UNIQUEID} ${XPUI_UNFUNC}XPUI.io.WelcomePage.Style2.${XPUI_UNIQUEID}.leave
Caption `${XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_CAPTION}`
PageExEnd

Function ${XPUI_UNFUNC}XPUI.io.WelcomePage.Style2.${XPUI_UNIQUEID}
LockWindow on
StrCmp $XPUI_ABORTED 1 Aborted.${XPUI_UNIQUEID}
SetBrandingImage /IMGID=1046 /RESIZETOFIT `$PLUGINSDIR\Header.bmp`
!insertmacro XPUI_HEADER_TEXT `${XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_TITLE}` `${XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_SUBTITLE}`
SetBrandingImage /IMGID=1046 /RESIZETOFIT `$PLUGINSDIR\Header.bmp`
!insertmacro XPUI_PAGEBG_INIT
SetOutPath $PLUGINSDIR
File `${NSISDIR}\Contrib\ExperienceUI\INI\Welcome.ini`
WriteINIStr $PLUGINSDIR\Welcome.ini `Field 1` Text `${XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_TEXT_TOP}`
WriteINIStr $PLUGINSDIR\Welcome.ini `Field 2` Text `${XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_TEXT}`

!insertmacro XPUI_PAGE_CUSTOMFUNCTION PRE

WriteINIStr `$PLUGINSDIR\Welcome.ini` `Settings` `RTL` `$(^RTL)`
InstallOptions::initDialog /NOUNLOAD `$PLUGINSDIR\Welcome.ini`
Pop $XPUI_HWND
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_HWND
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1200
CreateFont $XPUI_TEMP2 `$(^Font)` 8 700 ; previously MS Sans Serif
SendMessage $XPUI_TEMP1 0x30 $XPUI_TEMP2 $XPUI_TEMP2
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1201
CreateFont $XPUI_TEMP2 `$(^Font)` 8 350 ; previously MS Sans Serif
SendMessage $XPUI_TEMP1 0x30 $XPUI_TEMP2 $XPUI_TEMP2
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 3
ShowWindow $XPUI_TEMP1 0

!insertmacro XPUI_PAGE_CUSTOMFUNCTION SHOW

!ifndef XPUI_NOLOCK
LockWindow off
!endif
InstallOptions::show
Aborted.${XPUI_UNIQUEID}:
FunctionEnd

Function ${XPUI_UNFUNC}XPUI.io.WelcomePage.Style2.${XPUI_UNIQUEID}.leave
!ifndef XPUI_NOLOCK
LockWindow on
!endif

!insertmacro XPUI_PAGE_CUSTOMFUNCTION LEAVE

FunctionEnd

!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_PRE
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_SHOW
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_LEAVE

!undef XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_TITLE
!undef XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_SUBTITLE
!undef XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_TEXT_TOP
!undef XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_TEXT
!undef XPUI_${XPUI_UN}WELCOMEPAGESTYLE2_CAPTION

!verbose pop

!macroend

#goto LicensePage

; LICENSE PAGE
!macro XPUI_PAGE_LICENSE DATA

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!insertmacro XPUI_CONVERT XPUI_LICENSEPAGE_CHECKBOX       MUI_LICENSEPAGE_CHECKBOX
!insertmacro XPUI_CONVERT XPUI_LICENSEPAGE_RADIOBUTTONS   MUI_LICENSEPAGE_RADIOBUTTONS

!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}LICENSEPAGE_CAPTION "$(XPUI_${XPUI_UN}LICENSEPAGE_CAPTION)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}LICENSEPAGE_TITLE "$(XPUI_${XPUI_UN}LICENSEPAGE_TITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}LICENSEPAGE_SUBTITLE "$(XPUI_${XPUI_UN}LICENSEPAGE_SUBTITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}LICENSEPAGE_TEXT_TOP "$(XPUI_${XPUI_UN}LICENSEPAGE_TEXT_TOP)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}LICENSEPAGE_TEXT_BOTTOM "$(XPUI_${XPUI_UN}LICENSEPAGE_TEXT_BOTTOM)"

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_PAGE_LICENSE (${XPUI_PAGEMODE} License Page, Data File: ${DATA}`
  !endif
  !verbose ${XPUI_VERBOSE}
!insertmacro XPUI_CREATEID
PageEx ${XPUI_UNFUNC}license
Caption `$(XPUI_${XPUI_UN}LICENSEPAGE_CAPTION)`
PageCallbacks ${XPUI_UNFUNC}XPUI.lic.${XPUI_UNIQUEID}.pre ${XPUI_UNFUNC}XPUI.lic.${XPUI_UNIQUEID}.show ${XPUI_UNFUNC}XPUI.lic.${XPUI_UNIQUEID}.leave
LicenseData `${DATA}`
!ifdef XPUI_LICENSEPAGE_RADIOBUTTONS
LicenseForceSelection radiobuttons `$(^AcceptBtn)` `$(^DontAcceptBtn)`
!endif

!ifdef XPUI_LICENSEPAGE_CHECKBOX
LicenseForceSelection checkbox `$(^AcceptBtn)`
!endif

PageExEnd

!insertmacro XPUI_DEFAULT XPUI_LICENSEBKCOLOR 0xFFFFFF
LicenseBkColor `${XPUI_LICENSEBKCOLOR}`

Function ${XPUI_UNFUNC}XPUI.lic.${XPUI_UNIQUEID}.pre
StrCmp $XPUI_ABORTED 1 Aborted.${XPUI_UNIQUEID}

!insertmacro XPUI_PAGE_CUSTOMFUNCTION PRE

Return
Aborted.${XPUI_UNIQUEID}:
Abort
FunctionEnd

Function ${XPUI_UNFUNC}XPUI.lic.${XPUI_UNIQUEID}.show

!insertmacro XPUI_HEADER_TEXT `${XPUI_${XPUI_UN}LICENSEPAGE_TITLE}` `${XPUI_${XPUI_UN}LICENSEPAGE_SUBTITLE}`

!insertmacro XPUI_PAGEBG_INIT
!insertmacro XPUI_PAGECOLOR_INIT 1040

FindWindow $XPUI_HWND `#32770` `` $HWNDPARENT

GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1034
!ifndef XPUI_FULLBODY_SKIN
SetCtlColors $XPUI_TEMP1 ${XPUI_TEXT_LIGHTCOLOR} ${XPUI_TEXT_BGCOLOR}
!endif
!ifdef XPUI_LICENSEPAGE_RESET
SendMessage $XPUI_TEMP1 0x00F1 0 0
!endif
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1035
!ifndef XPUI_FULLBODY_SKIN
SetCtlColors $XPUI_TEMP1 ${XPUI_TEXT_LIGHTCOLOR} ${XPUI_TEXT_BGCOLOR}
!endif
!ifdef XPUI_LICENSEPAGE_RESET
SendMessage $XPUI_TEMP1 0x00F1 0 0
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1
EnableWindow $XPUI_TEMP1 0
!endif

!insertmacro XPUI_PAGECOLOR_INIT 1036
!insertmacro XPUI_PAGECOLOR_INIT 1006

!insertmacro XPUI_INNERDIALOG_TEXT `${XPUI_${XPUI_UN}LICENSEPAGE_TEXT_TOP}` 1040

!ifndef XPUI_LICENSEPAGE_RADIOBUTTONS
  !ifndef XPUI_LICENSEPAGE_CHECKBOX
    !insertmacro XPUI_INNERDIALOG_TEXT `${XPUI_${XPUI_UN}LICENSEPAGE_TEXT_BOTTOM}` 1006
  !endif
!endif

!ifdef XPUI_LICENSEPAGE_CHECKBOX
!insertmacro XPUI_INNERDIALOG_TEXT `${XPUI_${XPUI_UN}LICENSEPAGE_TEXT_BOTTOM}` 1006
!endif

!ifdef XPUI_LICENSEPAGE_RADIOBUTTONS
!insertmacro XPUI_INNERDIALOG_TEXT `${XPUI_${XPUI_UN}LICENSEPAGE_TEXT_BOTTOM}` 1006
!endif

!insertmacro XPUI_PAGE_CUSTOMFUNCTION SHOW

!ifndef XPUI_NOLOCK
LockWindow off
!endif
FunctionEnd

Function ${XPUI_UNFUNC}XPUI.lic.${XPUI_UNIQUEID}.leave
!ifndef XPUI_NOLOCK
LockWindow on
!endif

!insertmacro XPUI_PAGE_CUSTOMFUNCTION LEAVE

FunctionEnd

!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_PRE
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_SHOW
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_LEAVE

!undef XPUI_${XPUI_UN}LICENSEPAGE_CAPTION
!undef XPUI_${XPUI_UN}LICENSEPAGE_TITLE
!undef XPUI_${XPUI_UN}LICENSEPAGE_SUBTITLE
!undef XPUI_${XPUI_UN}LICENSEPAGE_TEXT_TOP
!undef XPUI_${XPUI_UN}LICENSEPAGE_TEXT_BOTTOM

!verbose pop
!macroend

#goto ComponentsPage

; COMPONENTS PAGE
!macro XPUI_PAGE_COMPONENTS

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_PAGE_COMPONENTS (${XPUI_PAGEMODE} Components Page)`
  !endif
  !verbose ${XPUI_VERBOSE}
  
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}COMPONENTSPAGE_TITLE "$(XPUI_${XPUI_UN}COMPONENTSPAGE_TITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}COMPONENTSPAGE_SUBTITLE "$(XPUI_${XPUI_UN}COMPONENTSPAGE_SUBTITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}COMPONENTSPAGE_CAPTION "$(XPUI_${XPUI_UN}COMPONENTSPAGE_CAPTION)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_DESCRIPTION_INFO "$(XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_DESCRIPTION_INFO)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_DESCRIPTION_TITLE "$(XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_DESCRIPTION_TITLE)"

!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_TOP      "$(^ComponentsText)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_INSTTYPE "$(^ComponentsSubText1)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_SECLIST  "$(^ComponentsSubText2)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_SECLIST_NOINSTTYPE  "$(^ComponentsSubText2_NoInstTypes)"

!insertmacro XPUI_CREATEID
PageEx ${XPUI_UNFUNC}components
Caption `$(XPUI_${XPUI_UN}COMPONENTSPAGE_CAPTION)`
PageCallbacks ${XPUI_UNFUNC}XPUI.cmp.${XPUI_UNIQUEID}.pre ${XPUI_UNFUNC}XPUI.cmp.${XPUI_UNIQUEID}.show ${XPUI_UNFUNC}XPUI.cmp.${XPUI_UNIQUEID}.leave
ComponentText "${XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_TOP}" "${XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_INSTTYPE}" "${XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_SECLIST}"
PageExEnd

Function ${XPUI_UNFUNC}XPUI.cmp.${XPUI_UNIQUEID}.pre
StrCmp $XPUI_ABORTED 1 Aborted.${XPUI_UNIQUEID}

!insertmacro XPUI_PAGE_CUSTOMFUNCTION PRE

Return
Aborted.${XPUI_UNIQUEID}:
Abort
FunctionEnd

Function ${XPUI_UNFUNC}XPUI.cmp.${XPUI_UNIQUEID}.show

!insertmacro XPUI_HEADER_TEXT `${XPUI_${XPUI_UN}COMPONENTSPAGE_TITLE}` `${XPUI_${XPUI_UN}COMPONENTSPAGE_SUBTITLE}`

!insertmacro XPUI_PAGECOLOR_INIT 1022
!insertmacro XPUI_PAGECOLOR_INIT 1021
!insertmacro XPUI_PAGECOLOR_INIT 1023
!insertmacro XPUI_PAGECOLOR_INIT 1006
!insertmacro XPUI_PAGECOLOR_INIT 1042
!insertmacro XPUI_PAGECOLOR_INIT 1043
FindWindow $XPUI_TEMP1 "#32770" "" "$HWNDPARENT"
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP1 1032
;SetCtlColors $XPUI_TEMP1 "0x000000" "0xFFFFFF";
FindWindow $XPUI_TEMP1 "#32770" "" "$HWNDPARENT"
!insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP1 1017
;SetCtlColors $XPUI_TEMP1 "0x000000" "Transparent";
!insertmacro XPUI_PAGEBG_INIT
FindWindow $XPUI_TEMP1 `#32770` `` $HWNDPARENT
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP1 1043
SendMessage $XPUI_TEMP1 0xC 0 `STR:${XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_DESCRIPTION_INFO}`
StrCpy $XPUI_HWND "${XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_DESCRIPTION_INFO}"
EnableWindow $XPUI_TEMP1 0

InstTypeGetText 0 $XPUI_TEMP1
StrCmp $XPUI_TEMP1 "" "" xpui.using_insttypes
  !insertmacro XPUI_INNERDIALOG_TEXT "${XPUI_COMPONENTSPAGE_TEXT_SECLIST_NOINSTTYPE}" 1022
  !insertmacro XPUI_INNERDIALOG_TEXT " " 1021
xpui.using_insttypes:

!ifdef XPUI_COMPONENTSPAGE_NODESC
FindWindow $XPUI_TEMP1 `#32770` `` `$HWNDPARENT`
GetDlgItem $XPUI_TEMP2 $XPUI_TEMP1 1042
ShowWindow $XPUI_TEMP2 0
GetDlgItem $XPUI_TEMP2 $XPUI_TEMP1 1043
ShowWindow $XPUI_TEMP2 0
!endif

!insertmacro XPUI_INNERDIALOG_TEXT `${XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_DESCRIPTION_TITLE}` 1042
StrCpy $XPUI_TEMP2 "${XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_DESCRIPTION_TITLE}"

!insertmacro XPUI_PAGE_CUSTOMFUNCTION SHOW

!ifndef XPUI_NOLOCK
LockWindow off
!endif
FunctionEnd

Function ${XPUI_UNFUNC}XPUI.cmp.${XPUI_UNIQUEID}.leave
!ifndef XPUI_NOLOCK
LockWindow on
!endif

!insertmacro XPUI_PAGE_CUSTOMFUNCTION LEAVE

FunctionEnd

!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_PRE
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_SHOW
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_LEAVE

!undef XPUI_${XPUI_UN}COMPONENTSPAGE_TITLE
!undef XPUI_${XPUI_UN}COMPONENTSPAGE_CAPTION
!undef XPUI_${XPUI_UN}COMPONENTSPAGE_SUBTITLE
!undef XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_DESCRIPTION_TITLE
!undef XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_DESCRIPTION_INFO
!undef XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_TOP
!undef XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_INSTTYPE
!undef XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_SECLIST
!undef XPUI_${XPUI_UN}COMPONENTSPAGE_TEXT_SECLIST_NOINSTTYPE

!verbose pop
!macroend

#goto DirectoryPage

; DIRECTORY PAGE
!macro XPUI_PAGE_DIRECTORY

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_PAGE_DIRECTORY (${XPUI_PAGEMODE} Directory Page)`
  !endif
  !verbose ${XPUI_VERBOSE}
  
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}DIRECTORYPAGE_TITLE "$(XPUI_${XPUI_UN}DIRECTORYPAGE_TITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}DIRECTORYPAGE_SUBTITLE "$(XPUI_${XPUI_UN}DIRECTORYPAGE_SUBTITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}DIRECTORYPAGE_CAPTION "$(XPUI_${XPUI_UN}DIRECTORYPAGE_CAPTION)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}DIRECTORYPAGE_TEXT "$(^DirText)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}DIRECTORYPAGE_SUBTEXT "$(^DirSubText)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}DIRECTORYPAGE_BROWSEDIALOG "$(^DirBrowseText)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}DIRECTORYPAGE_BROWSEBUTTON "$(^BrowseBtn)"

!insertmacro XPUI_DEFAULT XPUI_DIRVAR $INSTDIR

PageEx ${XPUI_UNFUNC}directory
Caption `${XPUI_${XPUI_UN}DIRECTORYPAGE_CAPTION}`
DirVar ${XPUI_DIRVAR}
PageCallbacks ${XPUI_UNFUNC}XPUI.dir.${XPUI_UNIQUEID}.pre ${XPUI_UNFUNC}XPUI.dir.${XPUI_UNIQUEID}.show ${XPUI_UNFUNC}XPUI.dir.${XPUI_UNIQUEID}.leave
DirText "${XPUI_${XPUI_UN}DIRECTORYPAGE_TEXT}" "${XPUI_${XPUI_UN}DIRECTORYPAGE_SUBTEXT}" "${XPUI_${XPUI_UN}DIRECTORYPAGE_BROWSEBUTTON}" "${XPUI_${XPUI_UN}DIRECTORYPAGE_BROWSEDIALOG}"
PageExEnd

Function ${XPUI_UNFUNC}XPUI.dir.${XPUI_UNIQUEID}.pre
StrCmp $XPUI_ABORTED 1 Aborted.${XPUI_UNIQUEID}

!insertmacro XPUI_PAGE_CUSTOMFUNCTION PRE

!ifmacrodef XPUI_DIRECTORYPAGE_PRE
  !ifndef XPUI_INTERNAL_DIRECTORYPAGE_PRE_MACRO_INSERTED
    !define XPUI_INTERNAL_DIRECTORYPAGE_PRE_MACRO_INSERTED
    !insertmacro XPUI_DIRECTORYPAGE_PRE
  !endif
!endif

!ifdef XPUI_DOWNLOADER
StrCmp $XPUI_PAGE_ABORTED 1 Aborted.${XPUI_UNIQUEID}
!endif

Return
Aborted.${XPUI_UNIQUEID}:
Abort
FunctionEnd

Function ${XPUI_UNFUNC}XPUI.dir.${XPUI_UNIQUEID}.show

!ifmacrodef XPUI_DIRECTORYPAGE_SHOW
  !ifndef XPUI_INTERNAL_DIRECTORYPAGE_SHOW_MACRO_INSERTED
    !define XPUI_INTERNAL_DIRECTORYPAGE_SHOW_MACRO_INSERTED
    !insertmacro XPUI_DIRECTORYPAGE_SHOW
  !endif
!endif

!insertmacro XPUI_HEADER_TEXT `${XPUI_${XPUI_UN}DIRECTORYPAGE_TITLE}` `${XPUI_${XPUI_UN}DIRECTORYPAGE_SUBTITLE}`
!insertmacro XPUI_PAGEBG_INIT
FindWindow $XPUI_TEMP1 `#32770` `` $HWNDPARENT
!insertmacro XPUI_DEFAULT XPUI_DIRECTORYPAGE_TEXTBOX_COLOR ${XPUI_TEXT_BGCOLOR}
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP1 1019
SetCtlColors $XPUI_TEMP1 0x000000 0xFFFFFF
!insertmacro XPUI_PAGECOLOR_INIT 1001
!insertmacro XPUI_PAGECOLOR_INIT 1008
!insertmacro XPUI_PAGECOLOR_INIT 1006
!insertmacro XPUI_PAGECOLOR_INIT 1020
!insertmacro XPUI_PAGECOLOR_INIT 1023
!insertmacro XPUI_PAGECOLOR_INIT 1024

!insertmacro XPUI_PAGE_CUSTOMFUNCTION SHOW

!ifndef XPUI_NOLOCK
LockWindow off
!endif
FunctionEnd

Function ${XPUI_UNFUNC}XPUI.dir.${XPUI_UNIQUEID}.leave
!ifndef XPUI_NOLOCK
LockWindow on
!endif

!insertmacro XPUI_PAGE_CUSTOMFUNCTION LEAVE

FunctionEnd

!undef XPUI_DIRVAR
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_PRE
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_SHOW
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_LEAVE

!undef XPUI_${XPUI_UN}DIRECTORYPAGE_TITLE
!undef XPUI_${XPUI_UN}DIRECTORYPAGE_SUBTITLE
!undef XPUI_${XPUI_UN}DIRECTORYPAGE_CAPTION
!undef XPUI_${XPUI_UN}DIRECTORYPAGE_TEXT
!undef XPUI_${XPUI_UN}DIRECTORYPAGE_SUBTEXT
!undef XPUI_${XPUI_UN}DIRECTORYPAGE_BROWSEBUTTON
!undef XPUI_${XPUI_UN}DIRECTORYPAGE_BROWSEDIALOG

!verbose pop

!macroend

#goto StartMenuPage

; START MENU PAGE

!macro XPUI_PAGE_STARTMENU ID VAR

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_PAGE_STARTMENU (${XPUI_PAGEMODE} Start Menu folder selection Page)`
  !endif
  !verbose ${XPUI_VERBOSE}
  
  SetPluginUnload manual

  !insertmacro XPUI_SET XPUI_${XPUI_UNFUNC}STARTMENUPAGE ""
  
  !insertmacro XPUI_DEFAULT XPUI_INTERNAL_STARTMENUPAGE_ID_DEFAULT ${ID}

  !insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_FOLDER "$(^Name)"
  !insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_TEXT "$(XPUI_${XPUI_UN}STARTMENUPAGE_TEXT)"
  !insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_CHECKBOX "$(XPUI_${XPUI_UN}STARTMENUPAGE_CHECKBOX)"

  !define XPUI_STARTMENUPAGE_VARIABLE "${VAR}"
  !define "XPUI_STARTMENUPAGE_${ID}_VARIABLE" "${XPUI_STARTMENUPAGE_VARIABLE}"
  !define "XPUI_STARTMENUPAGE_${ID}_DEFAULTFOLDER" "${XPUI_STARTMENUPAGE_FOLDER}"
  !ifdef XPUI_STARTMENUPAGE_REGISTRY_ROOT
    !define "XPUI_STARTMENUPAGE_${ID}_REGISTRY_ROOT" "${XPUI_STARTMENUPAGE_REGISTRY_ROOT}"
  !endif
  !ifdef XPUI_STARTMENUPAGE_REGISTRY_KEY
    !define "XPUI_STARTMENUPAGE_${ID}_REGISTRY_KEY" "${XPUI_STARTMENUPAGE_REGISTRY_KEY}"
  !endif
  !ifdef XPUI_STARTMENUPAGE_REGISTRY_VALUENAME
    !define "XPUI_STARTMENUPAGE_${ID}_REGISTRY_VALUENAME" "${XPUI_STARTMENUPAGE_REGISTRY_VALUENAME}"
  !endif

  PageEx ${XPUI_UNFUNC}custom

    PageCallbacks ${XPUI_UNFUNC}xpui.StartmenuPre_${XPUI_UNIQUEID} ${XPUI_UNFUNC}xpui.StartmenuLeave_${XPUI_UNIQUEID}

    Caption "$(XPUI_${XPUI_UN}STARTMENUPAGE_CAPTION)"

  PageExEnd

  Function "${XPUI_UNFUNC}xpui.StartmenuPre_${XPUI_UNIQUEID}"
  
    StrCmp $XPUI_ABORTED 1 "" +2
    Abort

    !insertmacro XPUI_PAGE_CUSTOMFUNCTION PRE

     !ifdef XPUI_STARTMENUPAGE_REGISTRY_ROOT & XPUI_STARTMENUPAGE_REGISTRY_KEY & XPUI_STARTMENUPAGE_REGISTRY_VALUENAME

      StrCmp "${XPUI_STARTMENUPAGE_VARIABLE}" "" 0 +4

      ReadRegStr $XPUI_TEMP1 "${XPUI_STARTMENUPAGE_REGISTRY_ROOT}" "${XPUI_STARTMENUPAGE_REGISTRY_KEY}" "${XPUI_STARTMENUPAGE_REGISTRY_VALUENAME}"
        StrCmp $XPUI_TEMP1 "" +2
          StrCpy "${XPUI_STARTMENUPAGE_VARIABLE}" $XPUI_TEMP1

    !endif

    !insertmacro XPUI_HEADER_TEXT $(XPUI_${XPUI_UN}STARTMENUPAGE_TITLE) $(XPUI_${XPUI_UN}STARTMENUPAGE_SUBTITLE)

    StrCmp $(^RTL) 0 xpui.startmenu_nortl
      !ifndef XPUI_STARTMENUPAGE_NODISABLE
        StartMenu::Init /NOUNLOAD /rtl /noicon /autoadd /text "${XPUI_STARTMENUPAGE_TEXT}" /lastused "${XPUI_STARTMENUPAGE_VARIABLE}" /checknoshortcuts "${XPUI_STARTMENUPAGE_CHECKBOX}" "${XPUI_STARTMENUPAGE_FOLDER}"
      !else
        StartMenu::Init /NOUNLOAD /rtl /noicon /autoadd /text "${XPUI_STARTMENUPAGE_TEXT}" /lastused "${XPUI_STARTMENUPAGE_VARIABLE}" "${XPUI_STARTMENUPAGE_FOLDER}"
      !endif
      Goto xpui.startmenu_calldone
    xpui.startmenu_nortl:
      !ifndef XPUI_STARTMENUPAGE_NODISABLE
        StartMenu::Init /NOUNLOAD /noicon /autoadd /text "${XPUI_STARTMENUPAGE_TEXT}" /lastused "${XPUI_STARTMENUPAGE_VARIABLE}" /checknoshortcuts "${XPUI_STARTMENUPAGE_CHECKBOX}" "${XPUI_STARTMENUPAGE_FOLDER}"
      !else
        StartMenu::Init /NOUNLOAD /noicon /autoadd /text "${XPUI_STARTMENUPAGE_TEXT}" /lastused "${XPUI_STARTMENUPAGE_VARIABLE}" "${XPUI_STARTMENUPAGE_FOLDER}"
      !endif
      
      !ifdef XPUI_PAGE_BGIMAGE
      
        GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1019
        ShowWindow $XPUI_TEMP1 0
        GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1047
        ShowWindow $XPUI_TEMP1 0

      !endif

      Pop $XPUI_HWND
      !insertmacro XPUI_CONTROL_SKIN $XPUI_HWND
      !insertmacro XPUI_CONTROL_SKIN $HWNDPARENT

      GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1000
      !insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
      GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1001
      !insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
      GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1002
      !insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
      GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1003
      !insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
      GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1004
      !insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
      GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1005
      !insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
      
      !insertmacro XPUI_PAGE_CUSTOMFUNCTION SHOW

      StartMenu::Show

    xpui.startmenu_calldone:

    Pop $XPUI_TEMP1
    StrCmp $XPUI_TEMP1 "success" 0 +2
      Pop "${XPUI_STARTMENUPAGE_VARIABLE}"

  FunctionEnd

  Function "${XPUI_UNFUNC}xpui.StartmenuLeave_${XPUI_UNIQUEID}"

    !ifdef XPUI_PAGE_BGIMAGE

      GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1019
      ShowWindow $XPUI_TEMP1 5
      GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1047
      ShowWindow $XPUI_TEMP1 5

    !endif

    !insertmacro XPUI_PAGE_CUSTOMFUNCTION LEAVE

  FunctionEnd

  !undef XPUI_STARTMENUPAGE_VARIABLE
  !undef XPUI_STARTMENUPAGE_TEXT
  !undef XPUI_STARTMENUPAGE_CHECKBOX
  !undef XPUI_STARTMENUPAGE_FOLDER
  !insertmacro XPUI_UNSET XPUI_STARTMENUPAGE_NODISABLE
  !insertmacro XPUI_UNSET XPUI_STARTMENUPAGE_REGISTRY_ROOT
  !insertmacro XPUI_UNSET XPUI_STARTMENUPAGE_REGISTRY_KEY
  !insertmacro XPUI_UNSET XPUI_STARTMENUPAGE_REGISTRY_VALUENAME
  
  SetPluginUnload ${XPUI_PLUGINUNLOAD}

  !verbose pop

!macroend

!macro XPUI_PAGE_STARTMENU_INIT ID VAR

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_PAGE_STARTMENU (${XPUI_PAGEMODE} Start Menu folder selection Page)`
  !endif
  !verbose ${XPUI_VERBOSE}
  
  !insertmacro XPUI_CREATEID

  SetPluginUnload manual

  !insertmacro XPUI_SET XPUI_${XPUI_UNFUNC}STARTMENUPAGE ""

  !insertmacro XPUI_DEFAULT XPUI_INTERNAL_STARTMENUPAGE_ID_DEFAULT ${ID}

  !insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_FOLDER "$(^Name)"
  !insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_TEXT "$(XPUI_${XPUI_UN}STARTMENUPAGE_TEXT)"
  !insertmacro XPUI_DEFAULT XPUI_STARTMENUPAGE_CHECKBOX "$(XPUI_${XPUI_UN}STARTMENUPAGE_CHECKBOX)"

  !define XPUI_STARTMENUPAGE_VARIABLE "${VAR}"
  !define "XPUI_STARTMENUPAGE_${ID}_VARIABLE" "${XPUI_STARTMENUPAGE_VARIABLE}"
  !define "XPUI_STARTMENUPAGE_${ID}_DEFAULTFOLDER" "${XPUI_STARTMENUPAGE_FOLDER}"
  !ifdef XPUI_STARTMENUPAGE_REGISTRY_ROOT
    !define "XPUI_STARTMENUPAGE_${ID}_REGISTRY_ROOT" "${XPUI_STARTMENUPAGE_REGISTRY_ROOT}"
  !endif
  !ifdef XPUI_STARTMENUPAGE_REGISTRY_KEY
    !define "XPUI_STARTMENUPAGE_${ID}_REGISTRY_KEY" "${XPUI_STARTMENUPAGE_REGISTRY_KEY}"
  !endif
  !ifdef XPUI_STARTMENUPAGE_REGISTRY_VALUENAME
    !define "XPUI_STARTMENUPAGE_${ID}_REGISTRY_VALUENAME" "${XPUI_STARTMENUPAGE_REGISTRY_VALUENAME}"
  !endif
  
  !define XPUI_STARTMENUPAGE_${ID}_ID "${XPUI_UNIQUEID}"

  Function "${XPUI_UNFUNC}xpui.StartmenuPre_${XPUI_UNIQUEID}"

    StrCmp $XPUI_ABORTED 1 "" +2
    Abort

    !insertmacro XPUI_PAGE_CUSTOMFUNCTION PRE

     !ifdef XPUI_STARTMENUPAGE_REGISTRY_ROOT & XPUI_STARTMENUPAGE_REGISTRY_KEY & XPUI_STARTMENUPAGE_REGISTRY_VALUENAME

      StrCmp "${XPUI_STARTMENUPAGE_VARIABLE}" "" 0 +4

      ReadRegStr $XPUI_TEMP1 "${XPUI_STARTMENUPAGE_REGISTRY_ROOT}" "${XPUI_STARTMENUPAGE_REGISTRY_KEY}" "${XPUI_STARTMENUPAGE_REGISTRY_VALUENAME}"
        StrCmp $XPUI_TEMP1 "" +2
          StrCpy "${XPUI_STARTMENUPAGE_VARIABLE}" $XPUI_TEMP1

    !endif

    !insertmacro XPUI_HEADER_TEXT $(XPUI_${XPUI_UN}STARTMENUPAGE_TITLE) $(XPUI_${XPUI_UN}STARTMENUPAGE_SUBTITLE)

    StrCmp $(^RTL) 0 xpui.startmenu_nortl
      !ifndef XPUI_STARTMENUPAGE_NODISABLE
        StartMenu::Init /NOUNLOAD /rtl /noicon /autoadd /text "${XPUI_STARTMENUPAGE_TEXT}" /lastused "${XPUI_STARTMENUPAGE_VARIABLE}" /checknoshortcuts "${XPUI_STARTMENUPAGE_CHECKBOX}" "${XPUI_STARTMENUPAGE_FOLDER}"
      !else
        StartMenu::Init /NOUNLOAD /rtl /noicon /autoadd /text "${XPUI_STARTMENUPAGE_TEXT}" /lastused "${XPUI_STARTMENUPAGE_VARIABLE}" "${XPUI_STARTMENUPAGE_FOLDER}"
      !endif
      Goto xpui.startmenu_calldone
    xpui.startmenu_nortl:
      !ifndef XPUI_STARTMENUPAGE_NODISABLE
        StartMenu::Init /NOUNLOAD /noicon /autoadd /text "${XPUI_STARTMENUPAGE_TEXT}" /lastused "${XPUI_STARTMENUPAGE_VARIABLE}" /checknoshortcuts "${XPUI_STARTMENUPAGE_CHECKBOX}" "${XPUI_STARTMENUPAGE_FOLDER}"
      !else
        StartMenu::Init /NOUNLOAD /noicon /autoadd /text "${XPUI_STARTMENUPAGE_TEXT}" /lastused "${XPUI_STARTMENUPAGE_VARIABLE}" "${XPUI_STARTMENUPAGE_FOLDER}"
      !endif

      !ifdef XPUI_PAGE_BGIMAGE

        GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1019
        ShowWindow $XPUI_TEMP1 0
        GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1047
        ShowWindow $XPUI_TEMP1 0

      !endif

      Pop $XPUI_HWND
      !insertmacro XPUI_CONTROL_SKIN $XPUI_HWND
      !insertmacro XPUI_CONTROL_SKIN $HWNDPARENT

      GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1000
      !insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
      GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1001
      !insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
      GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1002
      !insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
      GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1003
      !insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
      GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1004
      !insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1
      GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1005
      !insertmacro XPUI_CONTROL_SKIN $XPUI_TEMP1

      !insertmacro XPUI_PAGE_CUSTOMFUNCTION SHOW

      StartMenu::Show

    xpui.startmenu_calldone:

    Pop $XPUI_TEMP1
    StrCmp $XPUI_TEMP1 "success" 0 +2
      Pop "${XPUI_STARTMENUPAGE_VARIABLE}"

  FunctionEnd

  Function "${XPUI_UNFUNC}xpui.StartmenuLeave_${XPUI_UNIQUEID}"

    !ifdef XPUI_PAGE_BGIMAGE

      GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1019
      ShowWindow $XPUI_TEMP1 5
      GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1047
      ShowWindow $XPUI_TEMP1 5

    !endif

    !insertmacro XPUI_PAGE_CUSTOMFUNCTION LEAVE

  FunctionEnd

  !undef XPUI_STARTMENUPAGE_VARIABLE
  !undef XPUI_STARTMENUPAGE_TEXT
  !undef XPUI_STARTMENUPAGE_CHECKBOX
  !undef XPUI_STARTMENUPAGE_FOLDER
  !insertmacro XPUI_UNSET XPUI_STARTMENUPAGE_NODISABLE
  !insertmacro XPUI_UNSET XPUI_STARTMENUPAGE_REGISTRY_ROOT
  !insertmacro XPUI_UNSET XPUI_STARTMENUPAGE_REGISTRY_KEY
  !insertmacro XPUI_UNSET XPUI_STARTMENUPAGE_REGISTRY_VALUENAME

  SetPluginUnload ${XPUI_PLUGINUNLOAD}

  !verbose pop

!macroend

!macro XPUI_PAGE_STARTMENU_SHOW ID

  PageEx ${XPUI_UNFUNC}custom

    PageCallbacks ${XPUI_UNFUNC}xpui.StartmenuPre_${XPUI_STARTMENUPAGE_${ID}_ID} ${XPUI_UNFUNC}xpui.StartmenuLeave_${XPUI_STARTMENUPAGE_${ID}_ID}

    Caption "$(XPUI_${XPUI_UN}STARTMENUPAGE_CAPTION)"

  PageExEnd
  
  !undef XPUI_STARTMENUPAGE_${ID}_ID

!macroend

!macro XPUI_STARTMENU_GETFOLDER ID VAR

  !ifdef XPUI_STARTMENUPAGE_${ID}_REGISTRY_ROOT & XPUI_STARTMENUPAGE_${ID}_REGISTRY_KEY & XPUI_STARTMENUPAGE_${ID}_REGISTRY_VALUENAME

    ReadRegStr $XPUI_TEMP1 "${XPUI_STARTMENUPAGE_${ID}_REGISTRY_ROOT}" "${XPUI_STARTMENUPAGE_${ID}_REGISTRY_KEY}" "${XPUI_STARTMENUPAGE_${ID}_REGISTRY_VALUENAME}"
      StrCmp $XPUI_TEMP1 "" +3
        StrCpy "${VAR}" $XPUI_TEMP1
        Goto +2

        StrCpy "${VAR}" "${XPUI_STARTMENUPAGE_${ID}_DEFAULTFOLDER}"

   !else

     StrCpy "${VAR}" "${XPUI_STARTMENUPAGE_${ID}_DEFAULTFOLDER}"

   !endif

!macroend

!macro XPUI_STARTMENU_WRITE_BEGIN ID

  !verbose push
  !verbose ${XPUI_VERBOSE}

  !define XPUI_STARTMENUPAGE_CURRENT_ID "${ID}"

  StrCpy $XPUI_TEMP1 "${XPUI_STARTMENUPAGE_${XPUI_STARTMENUPAGE_CURRENT_ID}_VARIABLE}" 1
  StrCmp $XPUI_TEMP1 ">" xpui.startmenu_write_${XPUI_STARTMENUPAGE_CURRENT_ID}_done

  StrCmp "${XPUI_STARTMENUPAGE_${XPUI_STARTMENUPAGE_CURRENT_ID}_VARIABLE}" "" 0 xpui.startmenu_writebegin_${XPUI_STARTMENUPAGE_CURRENT_ID}_notempty

    !insertmacro XPUI_STARTMENU_GETFOLDER "${XPUI_STARTMENUPAGE_CURRENT_ID}" "${XPUI_STARTMENUPAGE_${XPUI_STARTMENUPAGE_CURRENT_ID}_VARIABLE}"

  xpui.startmenu_writebegin_${XPUI_STARTMENUPAGE_CURRENT_ID}_notempty:

  !verbose pop

!macroend

!macro XPUI_STARTMENU_WRITE_END

  !verbose push
  !verbose ${XPUI_VERBOSE}

  !ifdef XPUI_STARTMENUPAGE_${XPUI_STARTMENUPAGE_CURRENT_ID}_REGISTRY_ROOT & XPUI_STARTMENUPAGE_${XPUI_STARTMENUPAGE_CURRENT_ID}_REGISTRY_KEY & XPUI_STARTMENUPAGE_${XPUI_STARTMENUPAGE_CURRENT_ID}_REGISTRY_VALUENAME
    WriteRegStr "${XPUI_STARTMENUPAGE_${XPUI_STARTMENUPAGE_CURRENT_ID}_REGISTRY_ROOT}" "${XPUI_STARTMENUPAGE_${XPUI_STARTMENUPAGE_CURRENT_ID}_REGISTRY_KEY}" "${XPUI_STARTMENUPAGE_${XPUI_STARTMENUPAGE_CURRENT_ID}_REGISTRY_VALUENAME}" "${XPUI_STARTMENUPAGE_${XPUI_STARTMENUPAGE_CURRENT_ID}_VARIABLE}"
  !endif

  xpui.startmenu_write_${XPUI_STARTMENUPAGE_CURRENT_ID}_done:

  !undef XPUI_STARTMENUPAGE_CURRENT_ID

  !verbose pop

!macroend

#goto InstConfirmPage

; INSTALL CONFIRMATION PAGE
!macro XPUI_PAGE_INSTCONFIRM

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_PAGE_INSTCONFIRM (${XPUI_PAGEMODE} Install Confirm Page)`
  !endif
  !verbose ${XPUI_VERBOSE}
  
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTCONFIRMPAGE_TITLE "$(XPUI_${XPUI_UN}INSTCONFIRMPAGE_TITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTCONFIRMPAGE_SUBTITLE "$(XPUI_${XPUI_UN}INSTCONFIRMPAGE_SUBTITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTCONFIRMPAGE_CAPTION "$(XPUI_${XPUI_UN}INSTCONFIRMPAGE_CAPTION)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTCONFIRMPAGE_TEXT_TOP "$(XPUI_${XPUI_UN}INSTCONFIRMPAGE_TEXT_TOP)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTCONFIRMPAGE_TEXT_BOTTOM "$(XPUI_${XPUI_UN}INSTCONFIRMPAGE_TEXT_BOTTOM)"

!insertmacro XPUI_CREATEID
PageEx ${XPUI_UNFUNC}custom
Caption `${XPUI_${XPUI_UN}INSTCONFIRMPAGE_CAPTION}`
PageCallbacks `${XPUI_UNFUNC}XPUI.instconfirm.${XPUI_UNIQUEID}.show` `${XPUI_UNFUNC}XPUI.instconfirm.${XPUI_UNIQUEID}.verify`
PageExEnd

Function `${XPUI_UNFUNC}XPUI.instconfirm.${XPUI_UNIQUEID}.show`
StrCmp $XPUI_ABORTED 1 Aborted.${XPUI_UNIQUEID}
SetOutPath $PLUGINSDIR
File `${NSISDIR}\Contrib\ExperienceUI\INI\confirm.ini`

!insertmacro XPUI_HEADER_TEXT `${XPUI_${XPUI_UN}INSTCONFIRMPAGE_TITLE}` `${XPUI_${XPUI_UN}INSTCONFIRMPAGE_SUBTITLE}`

!insertmacro XPUI_PAGE_CUSTOMFUNCTION PRE

WriteINIStr $PLUGINSDIR\Confirm.ini `Field 1` Text `${XPUI_${XPUI_UN}INSTCONFIRMPAGE_TEXT_TOP}`
WriteINIStr $PLUGINSDIR\Confirm.ini `Field 2` Text `${XPUI_${XPUI_UN}INSTCONFIRMPAGE_TEXT_BOTTOM}`
!ifdef XPUI_INSTCONFIRMPAGE_INFOBOX
  WriteINIStr $PLUGINSDIR\Confirm.ini `Field 2` Bottom 98
  WriteINIStr $PLUGINSDIR\Confirm.ini `Field 5` Type Text
  WriteINIStr $PLUGINSDIR\Confirm.ini `Field 5` Flags GROUP|MULTILINE|READONLY
  WriteINIStr $PLUGINSDIR\Confirm.ini `Field 5` Left 6
  WriteINIStr $PLUGINSDIR\Confirm.ini `Field 5` Right -1
  WriteINIStr $PLUGINSDIR\Confirm.ini `Field 5` Top 104
  WriteINIStr $PLUGINSDIR\Confirm.ini `Field 5` Bottom 204
  WriteINIStr $PLUGINSDIR\Confirm.ini `Field 5` State `Installation Settings:\r\n______________________________________________________________\r\n\r\nDestination folder: $INSTDIR`

  !ifdef XPUI_INTERNAL_STARTMENUPAGE_ID_DEFAULT
    !insertmacro XPUI_STARTMENU_WRITE_BEGIN ${XPUI_INTERNAL_STARTMENUPAGE_ID_DEFAULT}
    WriteINIStr $PLUGINSDIR\Confirm.ini `Field 5` State `Installation Settings:\r\n______________________________________________________________\r\n\r\nDestination folder: $INSTDIR\r\nStart Menu Folder: ${XPUI_STARTMENUPAGE_${XPUI_INTERNAL_STARTMENUPAGE_ID_DEFAULT}_VARIABLE}`
    !insertmacro XPUI_STARTMENU_WRITE_END
  !endif
  
  WriteINIStr $PLUGINSDIR\Confirm.ini `Settings` Numfields 5
!endif
WriteINIStr `$PLUGINSDIR\Confirm.ini` `Settings` `RTL` `$(^RTL)`
InstallOptions::initDialog /NOUNLOAD $PLUGINSDIR\confirm.ini
Pop $XPUI_TEMP2
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP2

!insertmacro XPUI_PAGE_CUSTOMFUNCTION SHOW

GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1200
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1201
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1202
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1203
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1204
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
!ifndef XPUI_NOLOCK
LockWindow off
!endif

      !ifdef UMUI_CONFIRMPAGE_TEXTBOX
      Goto +2
      Call `${UMUI_CONFIRMPAGE_TEXTBOX}`
      !undef UMUI_CONFIRMPAGE_TEXTBOX
      !endif

InstallOptions::show
Delete $PLUGINSDIR\Confirm.ini
Goto Aborted.${XPUI_UNIQUEID}
Aborted.${XPUI_UNIQUEID}:
FunctionEnd

Function `${XPUI_UNFUNC}XPUI.instconfirm.${XPUI_UNIQUEID}.verify`
!ifndef XPUI_NOLOCK
LockWindow on
!endif

!insertmacro XPUI_PAGE_CUSTOMFUNCTION LEAVE

FunctionEnd

!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_PRE
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_SHOW
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_LEAVE

!undef XPUI_${XPUI_UN}INSTCONFIRMPAGE_TITLE
!undef XPUI_${XPUI_UN}INSTCONFIRMPAGE_SUBTITLE
!undef XPUI_${XPUI_UN}INSTCONFIRMPAGE_CAPTION
!undef XPUI_${XPUI_UN}INSTCONFIRMPAGE_TEXT_TOP
!undef XPUI_${XPUI_UN}INSTCONFIRMPAGE_TEXT_BOTTOM

!verbose pop
!macroend

#goto InstFilesPage

; INSTFILES PAGE
!macro XPUI_PAGE_INSTFILES

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTFILESPAGE_TITLE "$(XPUI_${XPUI_UN}INSTFILESPAGE_TITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTFILESPAGE_SUBTITLE "$(XPUI_${XPUI_UN}INSTFILESPAGE_SUBTITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTFILESPAGE_CAPTION "$(XPUI_${XPUI_UN}INSTFILESPAGE_CAPTION)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTFILESPAGE_DONE_TITLE "$(XPUI_${XPUI_UN}INSTFILESPAGE_DONE_TITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTFILESPAGE_DONE_SUBTITLE "$(XPUI_${XPUI_UN}INSTFILESPAGE_DONE_SUBTITLE)"

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_PAGE_INSTFILES (${XPUI_PAGEMODE} InstFiles Page)`
  !endif
  !verbose ${XPUI_VERBOSE}
!insertmacro XPUI_CREATEID
PageEx ${XPUI_UNFUNC}instfiles
Caption `${XPUI_${XPUI_UN}INSTFILESPAGE_CAPTION}`
PageCallbacks `${XPUI_UNFUNC}XPUI.cpy.${XPUI_UNIQUEID}.pre` `${XPUI_UNFUNC}XPUI.cpy.${XPUI_UNIQUEID}.show` `${XPUI_UNFUNC}XPUI.cpy.${XPUI_UNIQUEID}.leave`
PageExEnd

Function ${XPUI_UNFUNC}XPUI.cpy.${XPUI_UNIQUEID}.pre
StrCmp $XPUI_ABORTED 1 Aborted.${XPUI_UNIQUEID}

!insertmacro XPUI_PAGE_CUSTOMFUNCTION PRE

Return
Aborted.${XPUI_UNIQUEID}:
SetAutoClose true
Abort
FunctionEnd

Function ${XPUI_UNFUNC}XPUI.cpy.${XPUI_UNIQUEID}.show

!insertmacro XPUI_HEADER_TEXT `${XPUI_${XPUI_UN}INSTFILESPAGE_TITLE}` `${XPUI_${XPUI_UN}INSTFILESPAGE_SUBTITLE}`

!insertmacro XPUI_PAGEBG_INIT
!insertmacro XPUI_PAGECOLOR_INIT 1027
!insertmacro XPUI_PAGECOLOR_INIT 1004
!insertmacro XPUI_PAGECOLOR_INIT 1006
!insertmacro XPUI_PAGECOLOR_INIT 1016
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 3
SendMessage $XPUI_TEMP1 0xC `` `STR:STOP`
EnableWindow $XPUI_TEMP1 1
SetCtlColors $XPUI_TEMP1 0xFF0000 transparent

!insertmacro XPUI_PAGE_CUSTOMFUNCTION SHOW

  !ifdef UMUI_INSTFILEPAGE_ENABLE_CANCEL_BUTTON
    Goto +2
    Call `${UMUI_INSTFILEPAGE_ENABLE_CANCEL_BUTTON}`
    !undef UMUI_INSTFILEPAGE_ENABLE_CANCEL_BUTTON
  !endif

LockWindow off
FunctionEnd

Function ${XPUI_UNFUNC}XPUI.cpy.${XPUI_UNIQUEID}.leave
!insertmacro XPUI_HEADER_TEXT `${XPUI_${XPUI_UN}INSTFILESPAGE_DONE_TITLE}` `${XPUI_${XPUI_UN}INSTFILESPAGE_DONE_SUBTITLE}`

!insertmacro XPUI_PAGE_CUSTOMFUNCTION LEAVE

FunctionEnd

!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_PRE
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_SHOW
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_LEAVE

!undef XPUI_${XPUI_UN}INSTFILESPAGE_TITLE
!undef XPUI_${XPUI_UN}INSTFILESPAGE_SUBTITLE
!undef XPUI_${XPUI_UN}INSTFILESPAGE_CAPTION
!undef XPUI_${XPUI_UN}INSTFILESPAGE_DONE_TITLE
!undef XPUI_${XPUI_UN}INSTFILESPAGE_DONE_SUBTITLE

!verbose pop
!macroend

#goto InstSuccessPage

; INSTALL SUCCESS PAGE
!macro XPUI_PAGE_INSTSUCCESS

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTSUCCESSPAGE_TITLE "$(XPUI_${XPUI_UN}INSTSUCCESSPAGE_TITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTSUCCESSPAGE_SUBTITLE "$(XPUI_${XPUI_UN}INSTSUCCESSPAGE_SUBTITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTSUCCESSPAGE_CAPTION "$(XPUI_${XPUI_UN}INSTSUCCESSPAGE_CAPTION)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTSUCCESSPAGE_TEXT_TOP "$(XPUI_${XPUI_UN}INSTSUCCESSPAGE_TEXT_TOP)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}INSTSUCCESSPAGE_TEXT_BOTTOM "$(XPUI_${XPUI_UN}INSTSUCCESSPAGE_TEXT_BOTTOM)"

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_PAGE_INSTSUCCESS (${XPUI_PAGEMODE} Install Success Page)`
  !endif
  !verbose ${XPUI_VERBOSE}

!insertmacro XPUI_CREATEID
PageEx ${XPUI_UNFUNC}custom
Caption `${XPUI_${XPUI_UN}INSTSUCCESSPAGE_CAPTION}`
PageCallbacks `${XPUI_UNFUNC}XPUI.instSuccess.${XPUI_UNIQUEID}.show` `${XPUI_UNFUNC}XPUI.instSuccess.${XPUI_UNIQUEID}.verify`
PageExEnd

Function `${XPUI_UNFUNC}XPUI.instSuccess.${XPUI_UNIQUEID}.show`
StrCmp $XPUI_ABORTED 1 Aborted.${XPUI_UNIQUEID}
SetOutPath $PLUGINSDIR
File `${NSISDIR}\Contrib\ExperienceUI\INI\confirm.ini`

!insertmacro XPUI_HEADER_TEXT `${XPUI_${XPUI_UN}INSTSUCCESSPAGE_TITLE}` `${XPUI_${XPUI_UN}INSTSUCCESSPAGE_SUBTITLE}`

WriteINIStr $PLUGINSDIR\Confirm.ini `Settings` NextButtonText `Close`
WriteINIStr $PLUGINSDIR\Confirm.ini `Field 1` Text `${XPUI_${XPUI_UN}INSTSUCCESSPAGE_TEXT_TOP}`
WriteINIStr $PLUGINSDIR\Confirm.ini `Field 2` Text `${XPUI_${XPUI_UN}INSTSUCCESSPAGE_TEXT_BOTTOM}`
WriteINIStr `$PLUGINSDIR\Confirm.ini` `Settings` `RTL` `$(^RTL)`
InstallOptions::initDialog /NOUNLOAD $PLUGINSDIR\confirm.ini
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1
EnableWindow $XPUI_TEMP1 0
SendMessage $XPUI_TEMP1 0xC `` `STR:Next`
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 2
SendMessage $XPUI_TEMP1 0xC `` `STR:Close`
EnableWindow $XPUI_TEMP1 1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 3
SendMessage $XPUI_TEMP1 0xC `` `STR:Reinstall`
Pop $XPUI_TEMP2
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP2

!insertmacro XPUI_PAGE_CUSTOMFUNCTION SHOW

GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1200
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1201
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1202
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1203
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1204
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
StrCpy $NOABORTWARNING 1
!ifndef XPUI_NOLOCK
LockWindow off
!endif
InstallOptions::show
Delete $PLUGINSDIR\Confirm.ini
Aborted.${XPUI_UNIQUEID}:
FunctionEnd

Function `${XPUI_UNFUNC}XPUI.instSuccess.${XPUI_UNIQUEID}.verify`
!ifndef XPUI_NOLOCK
LockWindow on
!endif

!insertmacro XPUI_PAGE_CUSTOMFUNCTION LEAVE

FunctionEnd

!undef XPUI_${XPUI_UN}INSTSUCCESSPAGE_CAPTION
!undef XPUI_${XPUI_UN}INSTSUCCESSPAGE_TITLE
!undef XPUI_${XPUI_UN}INSTSUCCESSPAGE_SUBTITLE
!undef XPUI_${XPUI_UN}INSTSUCCESSPAGE_TEXT_TOP
!undef XPUI_${XPUI_UN}INSTSUCCESSPAGE_TEXT_BOTTOM

!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_PRE
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_SHOW
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_LEAVE

!verbose pop

!macroend

#goto FinishPage

; FINISH PAGE
!macro XPUI_PAGE_FINISH

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}FINISHPAGE_TITLE "$(XPUI_${XPUI_UN}FINISHPAGE_TITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}FINISHPAGE_SUBTITLE "$(XPUI_${XPUI_UN}FINISHPAGE_SUBTITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}FINISHPAGE_CAPTION "$(XPUI_${XPUI_UN}FINISHPAGE_CAPTION)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}FINISHPAGE_CHECKBOX_RUN "$(XPUI_${XPUI_UN}FINISHPAGE_CHECKBOX_RUN)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}FINISHPAGE_CHECKBOX_DOCS "$(XPUI_${XPUI_UN}FINISHPAGE_CHECKBOX_DOCS)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}FINISHPAGE_TEXT "$(XPUI_${XPUI_UN}FINISHPAGE_TEXT)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}FINISHPAGE_TEXT_RUN "$(XPUI_${XPUI_UN}FINISHPAGE_TEXT_RUN)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}FINISHPAGE_TEXT_TOP "$(XPUI_${XPUI_UN}FINISHPAGE_TEXT_TOP)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}FINISHPAGE_TEXT_TOP_ALT "$(XPUI_${XPUI_UN}FINISHPAGE_TEXT_TOP_ALT)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}FINISHPAGE_TEXT_REBOOT "$(XPUI_${XPUI_UN}FINISHPAGE_TEXT_REBOOT)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}FINISHPAGE_REBOOT_MESSAGEBOX "$(XPUI_FINISHPAGE_REBOOT_MESSAGEBOX)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}FINISHPAGE_RADIOBUTTON_REBOOT "$(XPUI_${XPUI_UN}FINISHPAGE_RADIOBUTTON_REBOOT)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}FINISHPAGE_RADIOBUTTON_NOREBOOT "$(XPUI_${XPUI_UN}FINISHPAGE_RADIOBUTTON_NOREBOOT)"

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_PAGE_FINISH (${XPUI_PAGEMODE} Finished Page)`
  !endif
  !verbose ${XPUI_VERBOSE}
  
!insertmacro XPUI_LANGUAGE_CONVERT

!insertmacro XPUI_CREATEID
PageEx ${XPUI_UNFUNC}custom
Caption `${XPUI_${XPUI_UN}FINISHPAGE_CAPTION}`
PageCallbacks `${XPUI_UNFUNC}XPUI.finish.${XPUI_UNIQUEID}.show` `${XPUI_UNFUNC}XPUI.finish.${XPUI_UNIQUEID}.verify`
PageExEnd

!ifndef XPUI_VAR_HWND
Var XPUI_HWND
!define XPUI_VAR_HWND
!endif

!ifndef XPUI_VAR_REBOOT
Var XPUI_REBOOT
!define XPUI_VAR_REBOOT
!endif

Function `${XPUI_UNFUNC}XPUI.FINISH.${XPUI_UNIQUEID}.show`

StrCmp $XPUI_ABORTED 1 Aborted.${XPUI_UNIQUEID}

; SET THE XPUI LOCAL REBOOT FLAG
StrCpy $XPUI_REBOOT 0
!ifdef XPUI_${XPUI_UN}FINISHPAGE_REBOOT_FORCE
StrCpy $XPUI_REBOOT 1
!endif
!ifndef XPUI_${XPUI_UN}FINISHPAGE_NOREBOOT
IfRebootFlag `` +2
StrCpy $XPUI_REBOOT 1
!endif

!ifdef `XPUI_${XPUI_UN}FINISHPAGE_ABORT_ON_NOREBOOT`
StrCmp $XPUI_REBOOT 1 `` Aborted.${XPUI_UNIQUEID}
!endif
SetOutPath $PLUGINSDIR
File `${NSISDIR}\Contrib\ExperienceUI\INI\finish.ini`

!insertmacro XPUI_HEADER_TEXT `${XPUI_${XPUI_UN}FINISHPAGE_TITLE}` `${XPUI_${XPUI_UN}FINISHPAGE_SUBTITLE}`

WriteINIStr $PLUGINSDIR\Finish.ini `Settings` NextButtonText `$(XPUI_BUTTONTEXT_CLOSE)`

StrCmp $XPUI_REBOOT 1 `` XPUI.finish.norb.${XPUI_UNIQUEID}
WriteINIStr $PLUGINSDIR\Finish.ini `Field 1` Type RadioButton
WriteINIStr $PLUGINSDIR\Finish.ini `Field 2` Type RadioButton
WriteINIStr $PLUGINSDIR\Finish.ini `Field 1` Text `${XPUI_${XPUI_UN}FINISHPAGE_RADIOBUTTON_REBOOT}`
WriteINIStr $PLUGINSDIR\Finish.ini `Field 2` Text `${XPUI_${XPUI_UN}FINISHPAGE_RADIOBUTTON_NOREBOOT}`
XPUI.finish.norb.${XPUI_UNIQUEID}:

StrCmp $XPUI_REBOOT 1 xpui.nochk
!ifdef XPUI_${XPUI_UN}FINISHPAGE_RUN_NOTCHECKED
WriteINIStr $PLUGINSDIR\Finish.ini `Field 1` State 0
!endif
!ifdef XPUI_${XPUI_UN}FINISHPAGE_DOCS_NOTCHECKED
WriteINIStr $PLUGINSDIR\Finish.ini `Field 2` State 0
!endif
xpui.nochk:

StrCmp $XPUI_REBOOT 1 `` +2
WriteINIStr $PLUGINSDIR\Finish.ini `Field 2` State 0

!ifdef XPUI_${XPUI_UN}FINISHPAGE_LINK_TEXT
WriteINIStr $PLUGINSDIR\Finish.ini `Field 3` Text `${XPUI_${XPUI_UN}FINISHPAGE_LINK_TEXT}`
!endif

StrCmp $XPUI_REBOOT 1 `` +3
WriteINIStr $PLUGINSDIR\Finish.ini `Field 5` Text  `${XPUI_${XPUI_UN}FINISHPAGE_TEXT_REBOOT}`
Goto +2
!ifdef XPUI_FINISHPAGE_RUN & XPUI_FINISHPAGE_DOCS
WriteINIStr $PLUGINSDIR\Finish.ini `Field 5` Text `${XPUI_${XPUI_UN}FINISHPAGE_TEXT_RUN}`
!else
WriteINIStr $PLUGINSDIR\Finish.ini `Field 5` Text `${XPUI_${XPUI_UN}FINISHPAGE_TEXT}`
!endif

!ifdef XPUI_FINISHPAGE_TEXT_USE_TOP_ALT
WriteINIStr $PLUGINSDIR\Finish.ini `Field 4` Top 17
!endif

WriteINIStr `$PLUGINSDIR\Finish.ini` `Settings` `RTL` `$(^RTL)`
InstallOptions::initDialog /NOUNLOAD $PLUGINSDIR\finish.ini
Pop $XPUI_TEMP2
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP2
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1
EnableWindow $XPUI_TEMP1 1
;SendMessage $XPUI_TEMP1 0xC `` `STR:$(^CloseBtn)`
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 2
SendMessage $XPUI_TEMP1 0xC `` `STR:$(^CancelBtn)`
EnableWindow $XPUI_TEMP1 1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 3
;SendMessage $XPUI_TEMP1 0xC `` `STR:$(^BackBtn)`
!ifdef XPUI_${XPUI_UN}FINISHPAGE_NO_REINSTALL
  EnableWindow $XPUI_TEMP1 0
!endif

LockWindow on
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1200
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
StrCmp $XPUI_REBOOT 1 a
!ifdef XPUI_${XPUI_UN}FINISHPAGE_RUN
SendMessage $XPUI_TEMP1 0xC 0 `STR:${XPUI_${XPUI_UN}FINISHPAGE_CHECKBOX_RUN}`
!endif
a:

StrCmp $XPUI_REBOOT 1 xpui.norunchk
!ifndef XPUI_${XPUI_UN}FINISHPAGE_RUN
ShowWindow $XPUI_TEMP1 0
!endif
xpui.norunchk:

GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1201
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
StrCmp $XPUI_REBOOT 1 b
!ifdef XPUI_${XPUI_UN}FINISHPAGE_DOCS
SendMessage $XPUI_TEMP1 0xC 0 `STR:${XPUI_${XPUI_UN}FINISHPAGE_CHECKBOX_DOCS}`
!endif
b:

StrCmp $XPUI_REBOOT 1 xpui.nodocchk
!ifndef XPUI_${XPUI_UN}FINISHPAGE_DOCS
ShowWindow $XPUI_TEMP1 0
!endif
xpui.nodocchk:

GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1203
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1203
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
SendMessage $XPUI_TEMP1 0xC 0 `STR:${XPUI_${XPUI_UN}FINISHPAGE_TEXT_TOP}`
CreateFont $XPUI_HWND `$(^Font)` 12 700 ; previously Tahoma
SendMessage $XPUI_TEMP1 0x30 $XPUI_HWND $XPUI_HWND
!ifdef XPUI_${XPUI_UN}FINISHPAGE_TEXT_USE_TOP_ALT
  SendMessage $XPUI_TEMP1 0xC 0 `STR:${XPUI_${XPUI_UN}FINISHPAGE_TEXT_TOP_ALT}`
  CreateFont $XPUI_HWND `$(^Font)` 8 700 ; previously MS Sans Serif
  SendMessage $XPUI_TEMP1 0x30 $XPUI_HWND $XPUI_HWND
!endif
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1204
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1

StrCmp $XPUI_REBOOT 1 `` +2
; SendMessage $XPUI_TEMP1 0xC 0 `STR:$(XPUI_${XPUI_UN}FINISHPAGE_TEXT_REBOOT)`
StrCmp $XPUI_REBOOT 1 c
/* !ifdef XPUI_${XPUI_UN}FINISHPAGE_RUN
  !ifndef XPUI_${XPUI_UN}FINISHPAGE_DOCS
    SendMessage $XPUI_TEMP1 0xC 0 `STR:$(XPUI_${XPUI_UN}FINISHPAGE_TEXT)`
  !endif
  !ifdef XPUI_${XPUI_UN}FINISHPAGE_DOCS
    SendMessage $XPUI_TEMP1 0xC 0 `STR:$(XPUI_${XPUI_UN}FINISHPAGE_TEXT_RUN)`
  !endif
!endif

!ifdef XPUI_${XPUI_UN}FINISHPAGE_DOCS
  !ifndef XPUI_${XPUI_UN}FINISHPAGE_RUN
    SendMessage $XPUI_TEMP1 0xC 0 `STR:$(XPUI_${XPUI_UN}FINISHPAGE_TEXT)`
  !endif
!endif */
c:

!ifndef XPUI_${XPUI_UN}FINISHPAGE_RUN & XPUI_${XPUI_UN}FINISHPAGE_DOCS
StrCmp $XPUI_REBOOT 1 xpui.no-normal-text
;SendMessage $XPUI_TEMP1 0xC 0 `STR:$(XPUI_${XPUI_UN}FINISHPAGE_TEXT)`
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1200
ShowWindow $XPUI_TEMP1 0
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1201
ShowWindow $XPUI_TEMP1 0
xpui.no-normal-text:
!endif

GetDlgItem $XPUI_TEMP1 $XPUI_TEMP2 1202
SetCtlColors $XPUI_TEMP1 ${XPUI_TEXT_LIGHTCOLOR} ${XPUI_TEXT_BGCOLOR}
!ifdef XPUI_${XPUI_UN}FINISHPAGE_LINK_TEXT
SendMessage $XPUI_TEMP1 0xC 0 `STR:${XPUI_${XPUI_UN}FINISHPAGE_LINK_TEXT}`
!endif
!ifndef XPUI_${XPUI_UN}FINISHPAGE_LINK
ShowWindow $XPUI_TEMP1 0
!endif

!insertmacro XPUI_PAGE_CUSTOMFUNCTION SHOW

LockWindow off
InstallOptions::show
Aborted.${XPUI_UNIQUEID}:
FunctionEnd

Function `${XPUI_UNFUNC}XPUI.FINISH.${XPUI_UNIQUEID}.verify`
StrCmp $XPUI_ABORTED 1 xpui.grinding-halt

!ifdef XPUI_${XPUI_UN}FINISHPAGE_LINK
ReadINIStr $XPUI_TEMP1 `$PLUGINSDIR\Finish.ini` `Settings` `State`
StrCmp $XPUI_TEMP1 3 `` xpui.nolink
!ifdef XPUI_${XPUI_UN}FINISHPAGE_LINK_FUNCTION
Call `${XPUI_${XPUI_UN}FINISHPAGE_LINK_FUNCTION}`
!else
  !ifdef XPUI_FINISHPAGE_LINK_LOCATION
    ExecShell open `${XPUI_FINISHPAGE_LINK_LOCATION}`
  !else
    MessageBox MB_OK|MB_ICONINFORMATION `Developer: Please specify a function for the Finish Page link to run in the symbol XPUI_FINISHPAGE_LINK_FUNCTION or specify a location (local or http) to load in XPUI_FINISHPAGE_LINK_LOCATION.  The Abort command is called automatically.$\n$\nUser: The developer of this setup program specified that a link be shown on this page, but he/she did not define what the link will do.  Please contact the company from which you obtained this installer and tell them about this message.`
  !endif
!endif
LockWindow off
Abort
xpui.nolink:
!endif

!ifndef XPUI_${XPUI_UN}FINISHPAGE_NOREBOOT
ReadINIStr $XPUI_TEMP1 `$PLUGINSDIR\Finish.ini` `Settings` `State`
StrCmp $XPUI_TEMP1 1 `` xpui.nodonebutton
StrCmp $XPUI_TEMP1 2 `` xpui.nodonebutton
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1
EnableWindow $XPUI_TEMP1 1
Abort
xpui.nodonebutton:
!endif

ReadINIStr $XPUI_TEMP1 `$PLUGINSDIR\Finish.ini` `Field 1` `State`
ReadINIStr $XPUI_TEMP2 `$PLUGINSDIR\Finish.ini` `Field 2` `State`

StrCmp $XPUI_REBOOT 1 `` xpui.norb2
StrCmp $XPUI_TEMP1 1 `` xpui.norb
MessageBox MB_OKCANCEL|MB_ICONINFORMATION `${XPUI_${XPUI_UN}FINISHPAGE_REBOOT_MESSAGEBOX}` IDCANCEL xpui.done
# the big...
Reboot # REBOOT! (Wow)
xpui.norb:
Goto xpui.done

xpui.norb2:

!ifdef XPUI_${XPUI_UN}FINISHPAGE_RUN
  !ifdef XPUI_${XPUI_UN}FINISHPAGE_RUN_FILE
    StrCmp $XPUI_TEMP1 1 `` NoCallRun
    Exec `${XPUI_${XPUI_UN}FINISHPAGE_RUN_FILE}`
  !endif
  !ifdef XPUI_${XPUI_UN}FINISHPAGE_RUN_FUNCTION
    StrCmp $XPUI_TEMP1 1 `` NoCallRun
    Call `${XPUI_${XPUI_UN}FINISHPAGE_RUN_FUNCTION}`
  !endif
  NoCallRun:
!endif

!ifdef XPUI_${XPUI_UN}FINISHPAGE_DOCS
  !ifdef XPUI_${XPUI_UN}FINISHPAGE_DOCS_FILE
  StrCmp $XPUI_TEMP2 1 `` NoCallDocs
  ExecShell open `${XPUI_${XPUI_UN}FINISHPAGE_DOCS_FILE}`
  !endif
  !ifdef XPUI_${XPUI_UN}FINISHPAGE_DOCS_FUNCTION
  StrCmp $XPUI_TEMP2 1 `` NoCallDocs
  Call `${XPUI_${XPUI_UN}FINISHPAGE_DOCS_FUNCTION}`
  !endif
  NoCallDocs:
!endif

xpui.done:

!insertmacro XPUI_PAGE_CUSTOMFUNCTION LEAVE

xpui.grinding-halt:
Delete $PLUGINSDIR\Finish.ini
SetRebootFlag false

FunctionEnd

!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_PRE
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_SHOW
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_LEAVE

!insertmacro XPUI_UNSET XPUI_FINISHPAGE_LINK
!insertmacro XPUI_UNSET XPUI_FINISHPAGE_LINK_FUNCTION

!insertmacro XPUI_UNSET MUI_FINISHPAGE_LINK
!insertmacro XPUI_UNSET MUI_FINISHPAGE_LINK_LOCATION

!insertmacro XPUI_UNSET XPUI_${XPUI_UN}FINISHPAGE_RUN
!insertmacro XPUI_UNSET XPUI_${XPUI_UN}FINISHPAGE_RUN_FILE
!insertmacro XPUI_UNSET XPUI_${XPUI_UN}FINISHPAGE_RUN_FUNCTION
!insertmacro XPUI_UNSET XPUI_${XPUI_UN}FINISHPAGE_DOCS
!insertmacro XPUI_UNSET XPUI_${XPUI_UN}FINISHPAGE_DOCS_FILE
!insertmacro XPUI_UNSET XPUI_${XPUI_UN}FINISHPAGE_DOCS_FUNCTION
!insertmacro XPUI_UNSET XPUI_${XPUI_UN}FINISHPAGE_REBOOT_DISABLE
!insertmacro XPUI_UNSET XPUI_${XPUI_UN}FINISHPAGE_REBOOT_FORCE

!undef XPUI_${XPUI_UN}FINISHPAGE_CAPTION
!undef XPUI_${XPUI_UN}FINISHPAGE_TITLE
!undef XPUI_${XPUI_UN}FINISHPAGE_SUBTITLE
!undef XPUI_${XPUI_UN}FINISHPAGE_TEXT_TOP
!undef XPUI_${XPUI_UN}FINISHPAGE_TEXT_TOP_ALT
!undef XPUI_${XPUI_UN}FINISHPAGE_TEXT
!undef XPUI_${XPUI_UN}FINISHPAGE_TEXT_RUN
!undef XPUI_${XPUI_UN}FINISHPAGE_TEXT_REBOOT
!undef XPUI_${XPUI_UN}FINISHPAGE_RADIOBUTTON_REBOOT
!undef XPUI_${XPUI_UN}FINISHPAGE_RADIOBUTTON_NOREBOOT
!undef XPUI_${XPUI_UN}FINISHPAGE_CHECKBOX_RUN
!undef XPUI_${XPUI_UN}FINISHPAGE_CHECKBOX_DOCS
!undef XPUI_${XPUI_UN}FINISHPAGE_REBOOT_MESSAGEBOX

!verbose pop

!macroend

#goto AbortPage

; ABORT PAGE

!macro XPUI_PAGE_ABORT

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}ABORTPAGE_TITLE "$(XPUI_${XPUI_UN}ABORTPAGE_TITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}ABORTPAGE_SUBTITLE "$(XPUI_${XPUI_UN}ABORTPAGE_SUBTITLE)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}ABORTPAGE_CAPTION "$(XPUI_${XPUI_UN}ABORTPAGE_CAPTION)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}ABORTPAGE_TEXT_TOP "$(XPUI_${XPUI_UN}ABORTPAGE_TEXT_TOP)"
!insertmacro XPUI_DEFAULT XPUI_${XPUI_UN}ABORTPAGE_TEXT "$(XPUI_${XPUI_UN}ABORTPAGE_TEXT)"

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_PAGE_ABORT (${XPUI_PAGEMODE} Aborted Page)`
  !endif
  !verbose ${XPUI_VERBOSE}
  
!insertmacro XPUI_CREATEID

!ifndef XPUI_VAR_HWND
Var XPUI_HWND
!define XPUI_VAR_HWND
!endif
PageEx ${XPUI_UNFUNC}custom
PageCallbacks ${XPUI_UNFUNC}XPUI.io.AbortPage.${XPUI_UNIQUEID}
Caption `${XPUI_${XPUI_UN}ABORTPAGE_CAPTION}`
PageExEnd

Function ${XPUI_UNFUNC}XPUI.io.AbortPage.${XPUI_UNIQUEID}
StrCmp $XPUI_ABORTED 1 `` Aborted.${XPUI_UNIQUEID}
!insertmacro XPUI_PAGEBG_INIT
SetOutPath $PLUGINSDIR
File `${NSISDIR}\Contrib\ExperienceUI\INI\isWelcome.ini`
!ifdef XPUI_${XPUI_UN}PAGE_CUSTOMFUNCTION_PRE
Call `${XPUI_${XPUI_UN}PAGE_CUSTOMFUNCTION_PRE}`
!undef XPUI_${XPUI_UN}PAGE_CUSTOMFUNCTION_PRE
!endif
WriteINIStr $PLUGINSDIR\isWelcome.ini `Field 2` Text `${XPUI_${XPUI_UN}ABORTPAGE_TEXT}`
WriteINIStr `$PLUGINSDIR\isWelcome.ini` `Settings` `RTL` `$(^RTL)`
InstallOptions::initDialog /NOUNLOAD `$PLUGINSDIR\isWelcome.ini`
LockWindow on
Pop $XPUI_HWND
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_HWND

GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1200
CreateFont $XPUI_TEMP2 `$(^Font)` 8 700 ; previously MS Sans Serif
SendMessage $XPUI_TEMP1 0x30 $XPUI_TEMP2 $XPUI_TEMP2
SendMessage $XPUI_TEMP1 0xC 0 `STR:${XPUI_${XPUI_UN}ABORTPAGE_TEXT_TOP}`
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1201
CreateFont $XPUI_TEMP2 `$(^Font)` 8 350 ; previously MS Sans Serif
SendMessage $XPUI_TEMP1 0x30 $XPUI_TEMP2 $XPUI_TEMP2
;SendMessage $XPUI_TEMP1 0xC 0 `STR:$(XPUI_${XPUI_UN}ABORTPAGE_TEXT)`
!insertmacro XPUI_CONTROL_SKIN_PAGE $XPUI_TEMP1

GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1
EnableWindow $XPUI_TEMP1 0
SendMessage $XPUI_TEMP1 0xC `` `STR:$(XPUI_BUTTONTEXT_NEXT)`
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 2
SendMessage $XPUI_TEMP1 0xC `` `STR:$(XPUI_BUTTONTEXT_CLOSE)`
EnableWindow $XPUI_TEMP1 1
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 3
EnableWindow $XPUI_TEMP1 0

StrCpy $NOABORTWARNING 1
!insertmacro XPUI_HEADER_TEXT `${XPUI_${XPUI_UN}ABORTPAGE_TITLE}` `${XPUI_${XPUI_UN}ABORTPAGE_SUBTITLE}`
!insertmacro XPUI_PAGE_CUSTOMFUNCTION SHOW
LockWindow off
InstallOptions::show
!insertmacro XPUI_PAGE_CUSTOMFUNCTION LEAVE
Aborted.${XPUI_UNIQUEID}:
FunctionEnd

!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_PRE
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_SHOW
!insertmacro XPUI_UNSET XPUI_PAGE_CUSTOMFUNCTION_LEAVE

!verbose pop
!macroend

#goto UnConfirmPage

; UNINST CONFIRM PAGE
!macro XPUI_PAGE_UNINSTCONFIRM_NSIS

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_UNPAGE_UNINSTCONFIRM_NSIS (${XPUI_PAGEMODE} Uninstall Confirm Page, NSIS Style)`
  !endif
  !verbose ${XPUI_VERBOSE}

!insertmacro XPUI_CREATEID
PageEx un.uninstConfirm
Caption `$(XPUI_UNINSTCONFIRMPAGE_NSIS_CAPTION)`
PageCallbacks `un.XPUI.unc_NSIS.${XPUI_UNIQUEID}.pre` `un.XPUI.unc_NSIS.${XPUI_UNIQUEID}.show` `un.XPUI.unc_NSIS.${XPUI_UNIQUEID}.leave`
PageExEnd

Function `un.XPUI.unc_NSIS.${XPUI_UNIQUEID}.pre`
StrCmp $XPUI_ABORTED 1 `` +2
Abort
!insertmacro XPUI_PAGE_CUSTOMFUNCTION PRE
FunctionEnd

Function `un.XPUI.unc_NSIS.${XPUI_UNIQUEID}.show`

!insertmacro XPUI_HEADER_TEXT `$(XPUI_UNINSTCONFIRMPAGE_NSIS_TITLE)` `$(XPUI_UNINSTCONFIRMPAGE_NSIS_SUBTITLE)`

!insertmacro XPUI_PAGEBG_INIT
FindWindow $XPUI_TEMP1 `#32770` `` $HWNDPARENT
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP1 1019
SetCtlColors $XPUI_TEMP1 0x000000 ${XPUI_TEXT_COLOR}

!insertmacro XPUI_PAGECOLOR_INIT 1029
!insertmacro XPUI_PAGECOLOR_INIT 1000
!insertmacro XPUI_PAGECOLOR_INIT 1006

!insertmacro XPUI_INNERDIALOG_TEXT `$(XPUI_UNINSTCONFIRMPAGE_NSIS_TEXT_TOP)` 1006
!insertmacro XPUI_INNERDIALOG_TEXT `$(XPUI_UNINSTCONFIRMPAGE_NSIS_TEXT_FOLDER)` 1029
!insertmacro XPUI_PAGE_CUSTOMFUNCTION SHOW
FunctionEnd

Function `un.XPUI.unc_NSIS.${XPUI_UNIQUEID}.leave`
!insertmacro XPUI_PAGE_CUSTOMFUNCTION LEAVE
FunctionEnd
!verbose pop
!macroEnd

#goto NullPages

; UN.INSTFILES PAGE
!macro XPUI_UNPAGE_INSTFILES

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_UNPAGE_INSTFILES (${XPUI_PAGEMODE} UninstFiles Page)`
    !endif
    !warning `The new page mode system uses the XPUI_PAGE_INSTFILES macro for both install and uninstall files pages.`
    !insertmacro XPUI_PAGEMODE_UNINST
    !insertmacro XPUI_PAGE_INSTFILES
  !verbose ${XPUI_VERBOSE}
!verbose pop
!macroend

; UNINST SUCCESS/FAILURE
!macro XPUI_UNPAGE_INSTSUCCESS

!ifndef XPUI_PAGEMODE_INSERTED
  !error `An XPUI_PAGEMODE macro must be inserted before inserting page macros!`
!endif

!verbose push
  !verbose 4
    !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_UNPAGE_INSTSUCCESS (${XPUI_PAGEMODE} Uninstall Success Page)`
    !endif
        !warning `The new page mode system uses the XPUI_PAGE_INSTFILES macro for both install and uninstall files pages.`
    !insertmacro XPUI_PAGEMODE_UNINST
    !insertmacro XPUI_PAGE_INSTFILES
  !verbose ${XPUI_VERBOSE}
!verbose pop
!macroend

!verbose 4
!ifndef XPUI_SILENT
!echo `  XPUI: Processing section descriptions...$\n  `
!endif
!verbose ${XPUI_VERBOSE}

#goto SecDesc

; SECTION DESCRIPTION CODE
; WRITTEN BY JOOST VERBURG
; IMPORTED FROM THE MODERN
; UI SCRIPT FILE

!macro XPUI_DESCRIPTION_BEGIN
FindWindow $XPUI_TEMP1 `#32770` `` $HWNDPARENT
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP1 1043
StrCmp $0 -1 0 XPUI.description_begin_done
SendMessage $XPUI_TEMP1 0xC 0 `STR:$XPUI_HWND`
EnableWindow $XPUI_TEMP1 0

Goto XPUI.description_done
XPUI.description_begin_done:
!macroend

!macro XPUI_DESCRIPTION_TEXT VAR TEXT
!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `XPUI Section Description Text: Section ${VAR}, Text "${TEXT}"`
  !endif
  !verbose ${XPUI_VERBOSE}
StrCmp $0 ${VAR} 0 XPUI.description_${VAR}_done
SendMessage $XPUI_TEMP1 0xC 0 `STR:`
EnableWindow $XPUI_TEMP1 1
SendMessage $XPUI_TEMP1 0xC 0 `STR:${TEXT}`
Goto XPUI.description_done
XPUI.description_${VAR}_done:
!verbose pop
!macroend

!macro XPUI_DESCRIPTION_END
XPUI.description_done:
!macroend


!macro XPUI_FUNCTION_DESCRIPTION_BEGIN
!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_FUNCTION_DESCRIPTION_BEGIN (Start Description Function)`
  !endif
  !verbose ${XPUI_VERBOSE}
Function .onMouseOverSection
FindWindow $XPUI_TEMP1 `#32770` `` $HWNDPARENT
GetDlgItem $XPUI_TEMP1 $XPUI_TEMP1 1042
SendMessage $XPUI_TEMP1 0xC 0 `STR:$XPUI_TEMP2`
!insertmacro XPUI_DESCRIPTION_BEGIN
!verbose pop
!macroend

!macro XPUI_FUNCTION_DESCRIPTION_END
!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_FUNCTION_DESCRIPTION_END (End Description Function)`
  !endif
  !verbose ${XPUI_VERBOSE}
!insertmacro XPUI_DESCRIPTION_END
FunctionEnd
!verbose pop
!macroend

; END SECTION DESCRIPTION CODE

!verbose 4
!ifndef XPUI_SILENT
!echo `  XPUI: Processing the left information panel macros...$\n  `
!endif
!verbose ${XPUI_VERBOSE}

#goto LeftInfo

; LEFT INFO PANEL

!macro XPUI_LEFT_SETTIME MINS
LockWindow on
Push $XPUI_TEMP1
Push $XPUI_TEMP2
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1100
ShowWindow $XPUI_TEMP1 5
CreateFont $XPUI_TEMP2 Arial 8 350
SendMessage `$XPUI_TEMP1` `0x30` `$XPUI_TEMP2` ``
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_LIGHTCOLOR}` `transparent`
SendMessage `$XPUI_TEMP1` `0xC` 0 `STR:Setup will complete in approximately:`
ShowWindow $XPUI_TEMP1 5
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1101
ShowWindow $XPUI_TEMP1 5
CreateFont $XPUI_TEMP2 Arial 8 700
SendMessage `$XPUI_TEMP1` `0x30` `$XPUI_TEMP2` ``
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `transparent`
SendMessage `$XPUI_TEMP1` `0xC` 0 `STR:${MINS}`
ShowWindow $XPUI_TEMP1 5
SetBrandingImage /IMGID=1302 $PLUGINSDIR\LeftImg.bmp
Pop $XPUI_TEMP2
Pop $XPUI_TEMP1
LockWindow off
!macroend

!macro XPUI_LEFT_BLANK

GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1100
ShowWindow $XPUI_TEMP1 0
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1101
ShowWindow $XPUI_TEMP1 0
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1102
ShowWindow $XPUI_TEMP1 0
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1103
ShowWindow $XPUI_TEMP1 0
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1104
ShowWindow $XPUI_TEMP1 0

!macroend

!macro XPUI_LEFT_MESSAGE MSGTOP MSGBTM TYPE

!verbose push

  !verbose 4
  !ifndef XPUI_SILENT
    !echo `!insertmacro: XPUI_LEFT_MESSAGE (${MSGTOP}, ${MSGBTM}, ${TYPE})`
  !endif
  !verbose ${XPUI_VERBOSE}
  
  StrCmp $XPUI_ABORTED 1 `` +2
  Return

; Type:
; 1=Single Button (OK)
; 2=Dual Buttons (OK/Cancel)
; 3=Dual Buttons (Yes/No)
!insertmacro XPUI_SET XPUI_UNIQUEID_PUSH ${XPUI_UNIQUEID}
!insertmacro XPUI_CREATEID
LockWindow on
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1304
ShowWindow $XPUI_TEMP1 5
SetCtlColors `$XPUI_TEMP1` `Transparent` `Transparent`

GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1
ShowWindow $XPUI_TEMP1 0

;for setting the main UI area
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1044
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1018
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`

SetOutPath $PLUGINSDIR
File `${NSISDIR}\Contrib\ExperienceUI\INI\MBSide.ini`
WriteINIStr `$PLUGINSDIR\MBSide.ini` `Settings` `RTL` `$(^RTL)`
InstallOptions::initDialog /NOUNLOAD $PLUGINSDIR\MBSide.ini
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1018
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_BGCOLOR}` `${XPUI_TEXT_BGCOLOR}`
Pop $XPUI_HWND
!insertmacro XPUI_CONTROL_SKIN $XPUI_HWND
; MESSAGE TEXTS
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1203
SendMessage $XPUI_TEMP1 0xC 0 `STR:${MSGTOP}`
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`
CreateFont $XPUI_TEMP2 Arial 8 700
SendMessage $XPUI_TEMP1 0x30 $XPUI_TEMP2 $XPUI_TEMP2
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1204
SendMessage $XPUI_TEMP1 0xC 0 `STR:${MSGBTM}`
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_LIGHTCOLOR}` `${XPUI_TEXT_BGCOLOR}`
CreateFont $XPUI_TEMP2 Arial 8 350
SendMessage $XPUI_TEMP1 0x30 $XPUI_TEMP2 $XPUI_TEMP2

GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1202
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_BGCOLOR}` `${XPUI_TEXT_BGCOLOR}`
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1018
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_BGCOLOR}` `${XPUI_TEXT_BGCOLOR}`

; BUTTON GOTOS
StrCmp ${TYPE} 3 Type3.${XPUI_UNIQUEID}
StrCmp ${TYPE} 1 Type1.${XPUI_UNIQUEID} Type2.${XPUI_UNIQUEID}

; OK
Type1.${XPUI_UNIQUEID}:
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1200
SendMessage $XPUI_TEMP1 0xC 0 `STR:OK`
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`
ShowWindow $XPUI_TEMP1 5
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1201
SendMessage $XPUI_TEMP1 0xC 0 `STR:Invisible`
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`
ShowWindow $XPUI_TEMP1 0
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1202
SendMessage $XPUI_TEMP1 0xC 0 `STR:Invisible`
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`
ShowWindow $XPUI_TEMP1 0
Goto Done.${XPUI_UNIQUEID}
StrCmp ${TYPE} 2 `` Type3.${XPUI_UNIQUEID}

; OK/CANCEL
Type2.${XPUI_UNIQUEID}:
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1200
SendMessage $XPUI_TEMP1 0xC 0 `STR:Invisible`
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`
ShowWindow $XPUI_TEMP1 0
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1201
SendMessage $XPUI_TEMP1 0xC 0 `STR:OK`
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`
ShowWindow $XPUI_TEMP1 5
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1202
SendMessage $XPUI_TEMP1 0xC 0 `STR:Cancel`
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`
ShowWindow $XPUI_TEMP1 5
Goto Done.${XPUI_UNIQUEID}

; YES/NO
Type3.${XPUI_UNIQUEID}:
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1200
SendMessage $XPUI_TEMP1 0xC 0 `STR:Invisible`
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`
ShowWindow $XPUI_TEMP1 0
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1201
SendMessage $XPUI_TEMP1 0xC 0 `STR:Yes`
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`
ShowWindow $XPUI_TEMP1 5
GetDlgItem $XPUI_TEMP1 $XPUI_HWND 1202
SendMessage $XPUI_TEMP1 0xC 0 `STR:No`
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_COLOR}` `${XPUI_TEXT_BGCOLOR}`
ShowWindow $XPUI_TEMP1 5

; SHARED
Done.${XPUI_UNIQUEID}:
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1018
SetCtlColors `$XPUI_TEMP1` `${XPUI_TEXT_BGCOLOR}` `${XPUI_TEXT_BGCOLOR}`
ShowWindow $XPUI_TEMP1 5

; EDIT: 9/11/05: HIDE LEFT LOGO BECAUSE OF VISUAL PROBLEMS
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1302
ShowWindow $XPUI_TEMP1 0

LockWindow off
InstallOptions::show
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1304
ShowWindow $XPUI_TEMP1 5
SetCtlColors `$XPUI_TEMP1` `Transparent` `Transparent`
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1304
ShowWindow $XPUI_TEMP1 5
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1
ShowWindow $XPUI_TEMP1 5
GetDlgItem $XPUI_TEMP1 $HWNDPARENT 1302
ShowWindow $XPUI_TEMP1 5

!insertmacro XPUI_SET XPUI_UNIQUEID ${XPUI_UNIQUEID_PUSH}

!verbose pop

!macroend

!verbose 4
!ifndef XPUI_SILENT
!echo `  XPUI: Processing multi-language support...$\n  `
!endif
!verbose ${XPUI_VERBOSE}

#goto Lang

; MULTI-LANGUAGE SUPPORT

; SELECTION DIALOG

!macro XPUI_LANGDLL_SAVELANGUAGE
!ifndef XPUI_PAGE_UNINSTALLER
IfAbort xpui.langdllsavelanguage_abort
!ifdef XPUI_LANGDLL_REGISTRY_ROOT & XPUI_LANGDLL_REGISTRY_KEY & XPUI_LANGDLL_REGISTRY_VALUENAME
WriteRegStr `${XPUI_LANGDLL_REGISTRY_ROOT}` `${XPUI_LANGDLL_REGISTRY_KEY}` `${XPUI_LANGDLL_REGISTRY_VALUENAME}` $LANGUAGE
!endif
xpui.langdllsavelanguage_abort:
!endif

!macroend

!macro XPUI_LANGDLL_DISPLAY
!verbose push
!verbose ${XPUI_VERBOSE}
!ifdef NSIS_CONFIG_SILENT_SUPPORT
IfSilent xpui.langdll_done
!endif
!insertmacro XPUI_DEFAULT XPUI_LANGDLL_WINDOWTITLE `Installer Language`
!insertmacro XPUI_DEFAULT XPUI_LANGDLL_INFO `Please select a language.`
!ifdef XPUI_LANGDLL_REGISTRY_ROOT & XPUI_LANGDLL_REGISTRY_KEY & XPUI_LANGDLL_REGISTRY_VALUENAME
ReadRegStr $XPUI_TEMP1 `${XPUI_LANGDLL_REGISTRY_ROOT}` `${XPUI_LANGDLL_REGISTRY_KEY}` `${XPUI_LANGDLL_REGISTRY_VALUENAME}`
StrCmp $XPUI_TEMP1 `` xpui.langdll_show
StrCpy $LANGUAGE $XPUI_TEMP1
!ifndef XPUI_LANGDLL_ALWAYSSHOW
Goto xpui.langdll_done
!endif
xpui.langdll_show:
!endif
LangDLL::LangDialog `${XPUI_LANGDLL_WINDOWTITLE}` `${XPUI_LANGDLL_INFO}` A ${XPUI_LANGDLL_PUSHLIST} ``
Pop $LANGUAGE
StrCmp $LANGUAGE `cancel` 0 xpui.langdll_noquit
!insertmacro XPUI_TEMPFILES_DELETE
Abort
xpui.langdll_noquit:
!ifdef NSIS_CONFIG_SILENT_SUPPORT
xpui.langdll_done:
!else ifdef XPUI_LANGDLL_REGISTRY_ROOT & XPUI_LANGDLL_REGISTRY_KEY & XPUI_LANGDLL_REGISTRY_VALUENAME
xpui.langdll_done:
!endif
!insertmacro XPUI_LANGDLL_SAVELANGUAGE
!verbose pop
!macroend

!macro XPUI_LANGPAGE_COMPILE
; This next block of code compiles a specialized language selection dialog app.  This is because NSIS only allows
; you to select the language in .onInit. (ahem, KiCHiK?) If just looking at it makes you feel braindead, I'll tell
; you what it does: it detects all of your visual settings that are currently being used, and then it launches
; MakeNSIS with the corresponding compiler flags.

!ifdef XPUI_WANSIS
  !ifdef XPUI_WANSIS_HEADERIMAGE
    !ifdef XPUI_WANSIS_HEADERIMAGE_BMP
      !ifdef XPUI_WANSIS_SKIN
        !execute `"${NSISDIR}\MakeNSIS.exe" /V0 "/DXPUI_ICON=${XPUI_ICON}" "/DXPUI_LEFTLOGO=${XPUI_LEFTLOGO}" "/DXPUI_WANSIS" "/DXPUI_WANSIS_HEADERIMAGE" "/DXPUI_WANSIS_HEADERIMAGE_BMP=${XPUI_WANSIS_HEADERIMAGE_BMP}" "/DXPUI_WANSIS_SKIN=${XPUI_WANSIS_SKIN}" "/DXPUI_LANGINI_LIST_B=${XPUI_LANGINI_LIST}" "/DXPUI_LANGIDLIST_LIST_B=${XPUI_LANGIDLIST_LIST}" "${NSISDIR}\Contrib\ExperienceUI\LangPage.nsi"`
      !else
        !execute `"${NSISDIR}\MakeNSIS.exe" /V0 "/DXPUI_ICON=${XPUI_ICON}" "/DXPUI_LEFTLOGO=${XPUI_LEFTLOGO}" "/DXPUI_WANSIS" "/DXPUI_WANSIS_HEADERIMAGE" "/DXPUI_WANSIS_HEADERIMAGE_BMP=${XPUI_WANSIS_HEADERIMAGE_BMP}" "/DXPUI_LANGINI_LIST_B=${XPUI_LANGINI_LIST}" "/DXPUI_LANGIDLIST_LIST_B=${XPUI_LANGIDLIST_LIST}" "${NSISDIR}\Contrib\ExperienceUI\LangPage.nsi"`
      !endif
    !else
      !ifdef XPUI_WANSIS_SKIN
        !execute `"${NSISDIR}\MakeNSIS.exe" /V0 "/DXPUI_ICON=${XPUI_ICON}" "/DXPUI_LEFTLOGO=${XPUI_LEFTLOGO}" "/DXPUI_WANSIS" "/DXPUI_WANSIS_HEADERIMAGE" "/DXPUI_WANSIS_SKIN=${XPUI_WANSIS_SKIN}" "/DXPUI_LANGINI_LIST_B=${XPUI_LANGINI_LIST}" "/DXPUI_LANGIDLIST_LIST_B=${XPUI_LANGIDLIST_LIST}" "${NSISDIR}\Contrib\ExperienceUI\LangPage.nsi"`
      !else
        !execute `"${NSISDIR}\MakeNSIS.exe" /V0 "/DXPUI_ICON=${XPUI_ICON}" "/DXPUI_LEFTLOGO=${XPUI_LEFTLOGO}" "/DXPUI_WANSIS" "/DXPUI_WANSIS_HEADERIMAGE" "/DXPUI_LANGINI_LIST_B=${XPUI_LANGINI_LIST}" "/DXPUI_LANGIDLIST_LIST_B=${XPUI_LANGIDLIST_LIST}" "${NSISDIR}\Contrib\ExperienceUI\LangPage.nsi"`
      !endif
    !endif
  !else
    !ifdef XPUI_WANSIS_SKIN
      !execute `"${NSISDIR}\MakeNSIS.exe" /V0 "/DXPUI_ICON=${XPUI_ICON}" "/DXPUI_LEFTLOGO=${XPUI_LEFTLOGO}" "/DXPUI_WANSIS" "/DXPUI_WANSIS_SKIN=${XPUI_WANSIS_SKIN}" "/DXPUI_LANGINI_LIST_B=${XPUI_LANGINI_LIST}" "/DXPUI_LANGIDLIST_LIST_B=${XPUI_LANGIDLIST_LIST}" "${NSISDIR}\Contrib\ExperienceUI\LangPage.nsi"`
    !else
      !execute `"${NSISDIR}\MakeNSIS.exe" /V0 "/DXPUI_ICON=${XPUI_ICON}" "/DXPUI_LEFTLOGO=${XPUI_LEFTLOGO}" "/DXPUI_WANSIS" "/DXPUI_LANGINI_LIST_B=${XPUI_LANGINI_LIST}" "/DXPUI_LANGIDLIST_LIST_B=${XPUI_LANGIDLIST_LIST}" "${NSISDIR}\Contrib\ExperienceUI\LangPage.nsi"`
    !endif
  !endif
!else
  !ifdef XPUI_BOTTOMIMAGE
    !ifdef XPUI_BOTTOMIMAGE_BMP
      !ifdef XPUI_SKIN
        !execute `"${NSISDIR}\MakeNSIS.exe" /V0 "/DXPUI_ICON=${XPUI_ICON}" "/DXPUI_LEFTLOGO=${XPUI_LEFTLOGO}" "/DXPUI_TEXT_COLOR=${XPUI_TEXT_COLOR}" "/DXPUI_TEXT_BGCOLOR=${XPUI_TEXT_BGCOLOR}" "/DXPUI_TEXT_LIGHTCOLOR=${XPUI_TEXT_LIGHTCOLOR}" "/DXPUI_HEADERIMAGE=${XPUI_HEADERIMAGE}" "/DXPUI_BOTTOMIMAGE" "/DXPUI_BOTTOMIMAGE_BMP=${XPUI_BOTTOMIMAGE_BMP}" "/DXPUI_SKIN=${XPUI_SKIN}" "/DXPUI_LANGINI_LIST_B=${XPUI_LANGINI_LIST}" "/DXPUI_LANGIDLIST_LIST_B=${XPUI_LANGIDLIST_LIST}" "${NSISDIR}\Contrib\ExperienceUI\LangPage.nsi"`
      !else
        !execute `"${NSISDIR}\MakeNSIS.exe" /V0 "/DXPUI_ICON=${XPUI_ICON}" "/DXPUI_LEFTLOGO=${XPUI_LEFTLOGO}" "/DXPUI_TEXT_COLOR=${XPUI_TEXT_COLOR}" "/DXPUI_TEXT_BGCOLOR=${XPUI_TEXT_BGCOLOR}" "/DXPUI_TEXT_LIGHTCOLOR=${XPUI_TEXT_LIGHTCOLOR}" "/DXPUI_HEADERIMAGE=${XPUI_HEADERIMAGE}" "/DXPUI_BOTTOMIMAGE" "/DXPUI_BOTTOMIMAGE_BMP=${XPUI_BOTTOMIMAGE_BMP}" "/DXPUI_LANGINI_LIST_B=${XPUI_LANGINI_LIST}" "/DXPUI_LANGIDLIST_LIST_B=${XPUI_LANGIDLIST_LIST}" "${NSISDIR}\Contrib\ExperienceUI\LangPage.nsi"`
      !endif
    !else
      !ifdef XPUI_SKIN
        !execute `"${NSISDIR}\MakeNSIS.exe" /V0 "/DXPUI_ICON=${XPUI_ICON}" "/DXPUI_LEFTLOGO=${XPUI_LEFTLOGO}" "/DXPUI_TEXT_COLOR=${XPUI_TEXT_COLOR}" "/DXPUI_TEXT_BGCOLOR=${XPUI_TEXT_BGCOLOR}" "/DXPUI_TEXT_LIGHTCOLOR=${XPUI_TEXT_LIGHTCOLOR}" "/DXPUI_HEADERIMAGE=${XPUI_HEADERIMAGE}" "/DXPUI_BOTTOMIMAGE" "/DXPUI_SKIN=${XPUI_SKIN}" "/DXPUI_LANGINI_LIST_B=${XPUI_LANGINI_LIST}" "/DXPUI_LANGIDLIST_LIST_B=${XPUI_LANGIDLIST_LIST}" "${NSISDIR}\Contrib\ExperienceUI\LangPage.nsi"`
      !else
        !execute `"${NSISDIR}\MakeNSIS.exe" /V0 "/DXPUI_ICON=${XPUI_ICON}" "/DXPUI_LEFTLOGO=${XPUI_LEFTLOGO}" "/DXPUI_TEXT_COLOR=${XPUI_TEXT_COLOR}" "/DXPUI_TEXT_BGCOLOR=${XPUI_TEXT_BGCOLOR}" "/DXPUI_TEXT_LIGHTCOLOR=${XPUI_TEXT_LIGHTCOLOR}" "/DXPUI_HEADERIMAGE=${XPUI_HEADERIMAGE}" "/DXPUI_BOTTOMIMAGE" "/DXPUI_LANGINI_LIST_B=${XPUI_LANGINI_LIST}" "/DXPUI_LANGIDLIST_LIST_B=${XPUI_LANGIDLIST_LIST}" "${NSISDIR}\Contrib\ExperienceUI\LangPage.nsi"`
      !endif
    !endif
  !else
    !ifdef XPUI_SKIN
      !execute `"${NSISDIR}\MakeNSIS.exe" "/V0" "/DXPUI_ICON=${XPUI_ICON}" "/DXPUI_LEFTLOGO=${XPUI_LEFTLOGO}" "/DXPUI_TEXT_COLOR=${XPUI_TEXT_COLOR}" "/DXPUI_TEXT_BGCOLOR=${XPUI_TEXT_BGCOLOR}" "/DXPUI_TEXT_LIGHTCOLOR=${XPUI_TEXT_LIGHTCOLOR}" "/DXPUI_HEADERIMAGE=${XPUI_HEADERIMAGE}" "/DXPUI_SKIN=${XPUI_SKIN}" "/DXPUI_LANGINI_LIST_B=${XPUI_LANGINI_LIST}" "/DXPUI_LANGIDLIST_LIST_B=${XPUI_LANGIDLIST_LIST}" "${NSISDIR}\Contrib\ExperienceUI\LangPage.nsi"`
    !else
      !execute `"${NSISDIR}\MakeNSIS.exe" "/V0" "/DXPUI_ICON=${XPUI_ICON}" "/DXPUI_LEFTLOGO=${XPUI_LEFTLOGO}" "/DXPUI_TEXT_COLOR=${XPUI_TEXT_COLOR}" "/DXPUI_TEXT_BGCOLOR=${XPUI_TEXT_BGCOLOR}" "/DXPUI_TEXT_LIGHTCOLOR=${XPUI_TEXT_LIGHTCOLOR}" "/DXPUI_HEADERIMAGE=${XPUI_HEADERIMAGE}" "/DXPUI_LANGINI_LIST_B=${XPUI_LANGINI_LIST}" "/DXPUI_LANGIDLIST_LIST_B=${XPUI_LANGIDLIST_LIST}" "${NSISDIR}\Contrib\ExperienceUI\LangPage.nsi"`
    !endif
  !endif
!endif
!macroend

!macro XPUI_LANGPAGE_DISPLAY

!verbose push
!verbose 4
!ifndef XPUI_SILENT
!echo `  XPUI: Language page inserted`
!endif
!verbose ${XPUI_VERBOSE}

!ifdef XPUI_LANGDLL_REGISTRY_ROOT & XPUI_LANGDLL_REGISTRY_KEY & XPUI_LANGDLL_REGISTRY_VALUENAME
  ReadRegStr $XPUI_TEMP1 `${XPUI_LANGDLL_REGISTRY_ROOT}` `${XPUI_LANGDLL_REGISTRY_KEY}` `${XPUI_LANGDLL_REGISTRY_VALUENAME}`
  StrCmp $XPUI_TEMP1 `` xpui.langdll_show
  StrCpy $LANGUAGE $XPUI_TEMP1
  !ifndef XPUI_LANGDLL_ALWAYSSHOW
    Goto xpui.langdll_done
  !endif
  xpui.langdll_show:
!endif
!insertmacro XPUI_LANGPAGE_COMPILE
InitPluginsDir
SetOutPath $PLUGINSDIR
File `${NSISDIR}\Contrib\ExperienceUI\LangDialog.exe`
!system `del "${NSISDIR}\Contrib\ExperienceUI\LangDialog.exe"`
ExecWait $PLUGINSDIR\LangDialog.exe $LANGUAGE
Delete $PLUGINSDIR\LangDialog.exe
StrCmp $LANGUAGE 1 `` +2
Abort
!ifdef XPUI_LANGDLL_REGISTRY_ROOT & XPUI_LANGDLL_REGISTRY_KEY & XPUI_LANGDLL_REGISTRY_VALUENAME
  WriteRegStr `${XPUI_LANGDLL_REGISTRY_ROOT}` `${XPUI_LANGDLL_REGISTRY_KEY}` `${XPUI_LANGDLL_REGISTRY_VALUENAME}` $LANGUAGE
!endif
!ifdef XPUI_LANGDLL_REGISTRY_ROOT & XPUI_LANGDLL_REGISTRY_KEY & XPUI_LANGDLL_REGISTRY_VALUENAME
!ifndef XPUI_LANGDLL_ALWAYSSHOW
xpui.langdll_done:
!endif
!endif
!verbose pop
!macroend

!macro XPUI_UNGETLANGUAGE_PAGE
!verbose pop
!ifdef XPUI_LANGDLL_REGISTRY_ROOT & XPUI_LANGDLL_REGISTRY_KEY & XPUI_LANGDLL_REGISTRY_VALUENAME
ReadRegStr $XPUI_TEMP1 `${XPUI_LANGDLL_REGISTRY_ROOT}` `${XPUI_LANGDLL_REGISTRY_KEY}` `${XPUI_LANGDLL_REGISTRY_VALUENAME}`
StrCmp $XPUI_TEMP1 `` 0 xpui.ungetlanguage_setlang
!endif
!insertmacro XPUI_LANGPAGE_DISPLAY
!ifdef XPUI_LANGDLL_REGISTRY_ROOT & XPUI_LANGDLL_REGISTRY_KEY & XPUI_LANGDLL_REGISTRY_VALUENAME
Goto xpui.ungetlanguage_done
xpui.ungetlanguage_setlang:
StrCpy $LANGUAGE $XPUI_TEMP1
xpui.ungetlanguage_done:
!endif
!verbose pop
!macroend

!macro XPUI_UNGETLANGUAGE
!verbose pop
!ifdef XPUI_LANGDLL_REGISTRY_ROOT & XPUI_LANGDLL_REGISTRY_KEY & XPUI_LANGDLL_REGISTRY_VALUENAME
ReadRegStr $XPUI_TEMP1 `${XPUI_LANGDLL_REGISTRY_ROOT}` `${XPUI_LANGDLL_REGISTRY_KEY}` `${XPUI_LANGDLL_REGISTRY_VALUENAME}`
StrCmp $XPUI_TEMP1 `` 0 xpui.ungetlanguage_setlang
!endif
!insertmacro XPUI_LANGDLL_DISPLAY
!ifdef XPUI_LANGDLL_REGISTRY_ROOT & XPUI_LANGDLL_REGISTRY_KEY & XPUI_LANGDLL_REGISTRY_VALUENAME
Goto xpui.ungetlanguage_done
xpui.ungetlanguage_setlang:
StrCpy $LANGUAGE $XPUI_TEMP1
xpui.ungetlanguage_done:
!endif
!verbose pop
!macroend

; FILES
!macro XPUI_LANGUAGE LANGUAGE

!verbose push
  !verbose 4
  !ifndef XPUI_SILENT
    !echo `ExperienceUI language inserted: ${LANGUAGE}$\n`
  !endif
  !verbose ${XPUI_VERBOSE}

!include `${NSISDIR}\Contrib\ExperienceUI\Language files\${LANGUAGE}.nsh`

!verbose pop
!macroend

!macro XPUI_LANGUAGEFILE_BEGIN LANGUAGE
!ifndef `XPUI_LANGUAGEFILE_${LANGUAGE}_USED`
!define `XPUI_LANGUAGEFILE_${LANGUAGE}_USED`
LoadLanguageFile `${NSISDIR}\Contrib\Language files\${LANGUAGE}.nlf`
!else
!error `ExperienceUI language file ${LANGUAGE} included twice!`
!endif
!insertmacro XPUI_UNSET XPUI_LANGNAME
!insertmacro XPUI_UNSET MUI_LANGNAME

!ifndef XPUI_INTERNAL_INTERFACE_INSERTED
  !define XPUI_INTERNAL_INTERFACE_INSERTED
  !insertmacro XPUI_INTERFACE
!endif

!macroend


!macro XPUI_LANGUAGEFILE_LANGSTRING NAME
LangString `${NAME}` 0 `${${NAME}}`
!insertmacro XPUI_UNSET `${NAME}`
!macroend

!macro XPUI_LANGUAGEFILE_UNLANGSTRING NAME
!ifdef XPUI_UNINSTALLER
LangString `${NAME}` 0 `${${NAME}}`
!insertmacro XPUI_UNSET `${NAME}`
!else
!insertmacro XPUI_UNSET `${NAME}`
!endif
!macroend

!macro XPUI_LANGUAGEFILE_LANGSTRING_PAGE NAME
LangString `${NAME}` 0 `${${NAME}}`
!insertmacro XPUI_UNSET `${NAME}`
!macroend

!macro XPUI_LANGUAGEFILE_UNLANGSTRING_PAGE PAGE NAME
!ifdef XPUI_UNINSTALLER
!ifdef XPUI_UN${PAGE}PAGE
LangString `${NAME}` 0 `${${NAME}}`
!insertmacro XPUI_UNSET `${NAME}`
!else
!insertmacro XPUI_UNSET `${NAME}`
!endif
!else
!insertmacro XPUI_UNSET `${NAME}`
!endif
!macroend

!macro XPUI_LANGUAGEFILE_MULTILANGSTRING_PAGE PAGE NAME
LangString `${NAME}` 0 `${${NAME}}`
!insertmacro XPUI_UNSET `${NAME}`
!macroend

!macro XPUI_LANGUAGEFILE_LANGSTRING_DEFINE DEFINE NAME
!ifdef `${DEFINE}`
LangString `${NAME}` 0 `${${NAME}}`
!endif
!insertmacro XPUI_UNSET `${NAME}`
!macroend

!macro XPUI_LANGUAGEFILE_DEFAULT NAME VALUE
!ifndef `${NAME}`
!define `${NAME}` `${VALUE}`
!ifndef MUI_LANGUAGEFILE_DEFAULT_USED
!define MUI_LANGUAGEFILE_DEFAULT_USED
!endif
!endif
!macroend

!macro XPUI_LANGUAGEFILE_DEFINE DEFINE NAME
!ifndef `${DEFINE}`
!define `${DEFINE}` `${${NAME}}`
!endif
!insertmacro XPUI_UNSET `${NAME}`
!macroend

!macro MUI_LANGUAGEFILE_LANGSTRING_PAGE PAGE NAME

  !ifdef MUI_${PAGE}PAGE
  !ifdef ${NAME}
    LangString `${NAME}` 0 `${${NAME}}`
  !endif
    !insertmacro XPUI_UNSET `${NAME}`
  !else
    !insertmacro XPUI_UNSET `${NAME}`
  !endif

!macroend

!macro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE PAGE NAME

  !ifdef MUI_UNINSTALLER
    !ifdef MUI_UN${PAGE}PAGE
    !ifdef ${NAME}
      LangString `${NAME}` 0 `${${NAME}}`
      !insertmacro XPUI_UNSET `${NAME}`
    !endif
    !else
      !insertmacro XPUI_UNSET `${NAME}`
    !endif
  !else
    !insertmacro XPUI_UNSET `${NAME}`
  !endif

!macroend

!macro MUI_LANGUAGEFILE_MULTILANGSTRING_PAGE PAGE NAME

  !ifdef MUI_${PAGE}PAGE | MUI_UN${PAGE}PAGE
    !ifdef ${NAME}
    LangString `${NAME}` 0 `${${NAME}}`
    !insertmacro XPUI_UNSET `${NAME}`
    !endif
  !else
    !insertmacro XPUI_UNSET `${NAME}`
  !endif

!macroend

!macro MUI_LANGUAGEFILE_LANGSTRING_DEFINE DEFINE NAME

  !ifdef `${DEFINE}`
    LangString `${NAME}` 0 `${${NAME}}`
  !endif
  !insertmacro XPUI_UNSET `${NAME}`

!macroend

!macro MUI_LANGUAGEFILE_DEFINE DEFINE NAME

  !ifndef `${DEFINE}`
    !define `${DEFINE}` `${${NAME}}`
  !endif
  !insertmacro XPUI_UNSET `${NAME}`

!macroend

!macro XPUI_LANGUAGEFILE_END

!insertmacro XPUI_LANGUAGE_CONVERT

!include `${NSISDIR}\Contrib\ExperienceUI\Language files\Default.nsh`
  !ifdef XPUI_LANGUAGEFILE_DEFAULT_USED
    !undef XPUI_LANGUAGEFILE_DEFAULT_USED
    !warning `${LANGUAGE} Modern UI language file version doesn't match. Using default English texts for missing strings.`
  !endif
  

  !ifndef XPUI_LANGINI_LIST
    !define XPUI_LANGINI_LIST `${XPUI_${LANGUAGE}_LANGNAME}`
   !else
     !ifdef XPUI_LANGINI_LIST_TEMP
       !undef XPUI_LANGINI_LIST_TEMP
     !endif

     !define XPUI_LANGINI_LIST_TEMP `${XPUI_LANGINI_LIST}`
     !undef XPUI_LANGINI_LIST
     !define XPUI_LANGINI_LIST `${XPUI_LANGINI_LIST_TEMP}|${XPUI_${LANGUAGE}_LANGNAME}`
   !endif
   
   !ifndef XPUI_LANGIDLIST_LIST
    !define XPUI_LANGIDLIST_LIST `${LANG_${LANGUAGE}}`
   !else
     !ifdef XPUI_LANGIDLIST_LIST_TEMP
       !undef XPUI_LANGIDLIST_LIST_TEMP
     !endif

     !define XPUI_LANGIDLIST_LIST_TEMP `${XPUI_LANGIDLIST_LIST}`
     !undef XPUI_LANGIDLIST_LIST
     !define XPUI_LANGIDLIST_LIST `${XPUI_LANGIDLIST_LIST_TEMP}${LANG_${LANGUAGE}}`
   !endif

  !ifdef XPUI_LANGNAME
    !insertmacro XPUI_LANGUAGEFILE_DEFINE `XPUI_${LANGUAGE}_LANGNAME` `XPUI_LANGNAME`
  !else
    !insertmacro XPUI_LANGUAGEFILE_DEFINE `XPUI_${LANGUAGE}_LANGNAME` `MUI_LANGNAME`
  !endif

  !ifndef XPUI_LANGDLL_PUSHLIST
    !ifdef XPUI_${LANGUAGE}_LANGNAME
      !define XPUI_LANGDLL_PUSHLIST `'${XPUI_${LANGUAGE}_LANGNAME}' ${LANG_${LANGUAGE}} `
    !else
      !define XPUI_LANGDLL_PUSHLIST `'${MUI_${LANGUAGE}_LANGNAME}' ${LANG_${LANGUAGE}} `
    !endif
  !else
    !ifdef XPUI_LANGDLL_PUSHLIST_TEMP
      !undef XPUI_LANGDLL_PUSHLIST_TEMP
    !endif
    !define XPUI_LANGDLL_PUSHLIST_TEMP `${XPUI_LANGDLL_PUSHLIST}`
    !undef XPUI_LANGDLL_PUSHLIST

    !ifdef XPUI_${LANGUAGE}_LANGNAME
      !define XPUI_LANGDLL_PUSHLIST `'${XPUI_${LANGUAGE}_LANGNAME}' ${LANG_${LANGUAGE}} ${XPUI_LANGDLL_PUSHLIST_TEMP}`
    !else
      !define XPUI_LANGDLL_PUSHLIST `'${MUI_${LANGUAGE}_LANGNAME}' ${LANG_${LANGUAGE}} ${XPUI_LANGDLL_PUSHLIST_TEMP}`
    !endif
  !endif

; +---------+
; | INSTALL |
; +---------+

; BUTTONS
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_BUTTONTEXT_NEXT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_BUTTONTEXT_BACK
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_BUTTONTEXT_CANCEL
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_BUTTONTEXT_CLOSE

!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_ABORTWARNING_TEXT

; WELCOME PAGE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_WELCOMEPAGE_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_WELCOMEPAGE_TEXT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_WELCOMEPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_WELCOMEPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_WELCOMEPAGE_CAPTION

!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNWELCOMEPAGE_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNWELCOMEPAGE_TEXT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNWELCOMEPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNWELCOMEPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNWELCOMEPAGE_CAPTION

; WELCOME PAGE STYLE 2
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_WELCOMEPAGESTYLE2_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_WELCOMEPAGESTYLE2_TEXT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_WELCOMEPAGESTYLE2_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_WELCOMEPAGESTYLE2_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_WELCOMEPAGESTYLE2_CAPTION

!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNWELCOMEPAGESTYLE2_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNWELCOMEPAGESTYLE2_TEXT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNWELCOMEPAGESTYLE2_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNWELCOMEPAGESTYLE2_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNWELCOMEPAGESTYLE2_CAPTION

; LICENSE PAGE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_LICENSEPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_LICENSEPAGE_RADIOBUTTONS_TEXT_ACCEPT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_LICENSEPAGE_RADIOBUTTONS_TEXT_DECLINE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_LICENSEPAGE_TEXT_CHECKBOX
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_LICENSEPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_LICENSEPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_LICENSEPAGE_TEXT_TOP
!ifndef XPUI_LICENSEPAGE_RADIOBUTTONS
  !ifndef XPUI_LICENSEPAGE_CHECKBOX
    !insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_LICENSEPAGE_TEXT_BOTTOM
  !endif
!endif
!ifndef XPUI_LICENSEPAGE_RADIOBUTTONS
  !ifdef XPUI_LICENSEPAGE_CHECKBOX
    !insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_LICENSEPAGE_TEXT_BOTTOM
  !endif
!endif
!ifdef XPUI_LICENSEPAGE_RADIOBUTTONS
  !ifndef XPUI_LICENSEPAGE_CHECKBOX
    !insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_LICENSEPAGE_TEXT_BOTTOM
  !endif
!endif

!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNLICENSEPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNLICENSEPAGE_RADIOBUTTONS_TEXT_ACCEPT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNLICENSEPAGE_RADIOBUTTONS_TEXT_DECLINE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNLICENSEPAGE_TEXT_CHECKBOX
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNLICENSEPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNLICENSEPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNLICENSEPAGE_TEXT_TOP
!ifndef XPUI_UNLICENSEPAGE_RADIOBUTTONS
  !ifndef XPUI_UNLICENSEPAGE_CHECKBOX
    !insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNLICENSEPAGE_TEXT_BOTTOM
  !endif
!endif
!ifndef XPUI_UNLICENSEPAGE_RADIOBUTTONS
  !ifdef XPUI_UNLICENSEPAGE_CHECKBOX
    !insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNLICENSEPAGE_TEXT_BOTTOM
  !endif
!endif
!ifdef XPUI_UNLICENSEPAGE_RADIOBUTTONS
  !ifndef XPUI_UNLICENSEPAGE_CHECKBOX
    !insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNLICENSEPAGE_TEXT_BOTTOM
  !endif
!endif

; COMPONENTS PAGE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_COMPONENTSPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_COMPONENTSPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_COMPONENTSPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_COMPONENTSPAGE_TEXT_DESCRIPTION_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_COMPONENTSPAGE_TEXT_DESCRIPTION_INFO

!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNCOMPONENTSPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNCOMPONENTSPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNCOMPONENTSPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNCOMPONENTSPAGE_TEXT_DESCRIPTION_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNCOMPONENTSPAGE_TEXT_DESCRIPTION_INFO

; DIRECTORY PAGE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_DIRECTORYPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_DIRECTORYPAGE_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_DIRECTORYPAGE_TEXT_DESTINATION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_DIRECTORYPAGE_TEXT_BROWSE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_DIRECTORYPAGE_TEXT_BROWSEDIALOG
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_DIRECTORYPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_DIRECTORYPAGE_SUBTITLE

!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNDIRECTORYPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNDIRECTORYPAGE_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNDIRECTORYPAGE_TEXT_DESTINATION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNDIRECTORYPAGE_TEXT_BROWSE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNDIRECTORYPAGE_TEXT_BROWSEDIALOG
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNDIRECTORYPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNDIRECTORYPAGE_SUBTITLE

; START MENU PAGE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_STARTMENUPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_STARTMENUPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_STARTMENUPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_STARTMENUPAGE_TEXT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_STARTMENUPAGE_CHECKBOX
;!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_STARTMENUPAGE_FOLDER

!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNSTARTMENUPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNSTARTMENUPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNSTARTMENUPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNSTARTMENUPAGE_TEXT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNSTARTMENUPAGE_CHECKBOX
;!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNSTARTMENUPAGE_FOLDER

; INSTALL CONFIRM PAGE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTCONFIRMPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTCONFIRMPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTCONFIRMPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTCONFIRMPAGE_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTCONFIRMPAGE_TEXT_BOTTOM

!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTCONFIRMPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTCONFIRMPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTCONFIRMPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTCONFIRMPAGE_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTCONFIRMPAGE_TEXT_BOTTOM

; INSTFILES PAGE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTFILESPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTFILESPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTFILESPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTFILESPAGE_DONE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTFILESPAGE_DONE_SUBTITLE

!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTFILESPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTFILESPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTFILESPAGE_TITLE

; INSTALL SUCCESS PAGE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTSUCCESSPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTSUCCESSPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTSUCCESSPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTSUCCESSPAGE_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_INSTSUCCESSPAGE_TEXT_BOTTOM

!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTSUCCESSPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTSUCCESSPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTSUCCESSPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTSUCCESSPAGE_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTSUCCESSPAGE_TEXT_BOTTOM

; FINISH PAGE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_FINISHPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_FINISHPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_FINISHPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_FINISHPAGE_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_FINISHPAGE_TEXT_TOP_ALT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_FINISHPAGE_TEXT_RUN
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_FINISHPAGE_TEXT_REBOOT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_FINISHPAGE_TEXT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_FINISHPAGE_CHECKBOX_RUN
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_FINISHPAGE_CHECKBOX_DOCS
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_FINISHPAGE_RADIOBUTTON_REBOOT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_FINISHPAGE_RADIOBUTTON_NOREBOOT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_FINISHPAGE_REBOOT_MESSAGEBOX

!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNFINISHPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNFINISHPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNFINISHPAGE_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNFINISHPAGE_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNFINISHPAGE_TEXT_TOP_ALT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNFINISHPAGE_TEXT_REBOOT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNFINISHPAGE_TEXT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNFINISHPAGE_TEXT_RUN
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNFINISHPAGE_RADIOBUTTON_REBOOT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNFINISHPAGE_RADIOBUTTON_NOREBOOT

; UNINST CONFIRM PAGE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTCONFIRMPAGE_NSIS_CAPTION
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTCONFIRMPAGE_NSIS_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTCONFIRMPAGE_NSIS_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTCONFIRMPAGE_NSIS_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNINSTCONFIRMPAGE_NSIS_TEXT_FOLDER

; ABORT PAGE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_ABORTPAGE_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_ABORTPAGE_TEXT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_ABORTPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_ABORTPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_ABORTPAGE_CAPTION

!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNABORTPAGE_TEXT_TOP
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNABORTPAGE_TEXT
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNABORTPAGE_TITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNABORTPAGE_SUBTITLE
!insertmacro XPUI_LANGUAGEFILE_LANGSTRING_PAGE XPUI_UNABORTPAGE_CAPTION

  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE WELCOME `MUI_TEXT_WELCOME_INFO_TITLE`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE WELCOME `MUI_TEXT_WELCOME_INFO_TEXT`

  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE LICENSE `MUI_TEXT_LICENSE_TITLE`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE LICENSE `MUI_TEXT_LICENSE_SUBTITLE`
  !insertmacro MUI_LANGUAGEFILE_MULTILANGSTRING_PAGE LICENSE `MUI_INNERTEXT_LICENSE_TOP`

  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE LICENSE `MUI_INNERTEXT_LICENSE_BOTTOM`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE LICENSE `MUI_INNERTEXT_LICENSE_BOTTOM_CHECKBOX`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE LICENSE `MUI_INNERTEXT_LICENSE_BOTTOM_RADIOBUTTONS`

  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE COMPONENTS `MUI_TEXT_COMPONENTS_TITLE`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE COMPONENTS `MUI_TEXT_COMPONENTS_SUBTITLE`
  !insertmacro MUI_LANGUAGEFILE_MULTILANGSTRING_PAGE COMPONENTS `MUI_INNERTEXT_COMPONENTS_DESCRIPTION_TITLE`
  !insertmacro MUI_LANGUAGEFILE_MULTILANGSTRING_PAGE COMPONENTS `MUI_INNERTEXT_COMPONENTS_DESCRIPTION_INFO`

  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE DIRECTORY `MUI_TEXT_DIRECTORY_TITLE`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE DIRECTORY `MUI_TEXT_DIRECTORY_SUBTITLE`

  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE STARTMENU `MUI_TEXT_STARTMENU_TITLE`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE STARTMENU `MUI_TEXT_STARTMENU_SUBTITLE`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE STARTMENU `MUI_INNERTEXT_STARTMENU_TOP`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE STARTMENU `MUI_INNERTEXT_STARTMENU_CHECKBOX`

  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE INSTFILES `MUI_TEXT_INSTALLING_TITLE`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE INSTFILES `MUI_TEXT_INSTALLING_SUBTITLE`

  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE INSTFILES `MUI_TEXT_FINISH_TITLE`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE INSTFILES `MUI_TEXT_FINISH_SUBTITLE`

  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE INSTFILES `MUI_TEXT_ABORT_TITLE`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE INSTFILES `MUI_TEXT_ABORT_SUBTITLE`

  !insertmacro MUI_LANGUAGEFILE_MULTILANGSTRING_PAGE FINISH `MUI_BUTTONTEXT_FINISH`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE FINISH `MUI_TEXT_FINISH_INFO_TITLE`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE FINISH `MUI_TEXT_FINISH_INFO_TEXT`
  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_PAGE FINISH `MUI_TEXT_FINISH_INFO_REBOOT`
  !insertmacro MUI_LANGUAGEFILE_MULTILANGSTRING_PAGE FINISH `MUI_TEXT_FINISH_REBOOTNOW`
  !insertmacro MUI_LANGUAGEFILE_MULTILANGSTRING_PAGE FINISH `MUI_TEXT_FINISH_REBOOTLATER`
  !insertmacro MUI_LANGUAGEFILE_MULTILANGSTRING_PAGE FINISH `MUI_TEXT_FINISH_RUN`
  !insertmacro MUI_LANGUAGEFILE_MULTILANGSTRING_PAGE FINISH `MUI_TEXT_FINISH_SHOWREADME`

  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_DEFINE MUI_ABORTWARNING `MUI_TEXT_ABORTWARNING`


  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE WELCOME `MUI_UNTEXT_WELCOME_INFO_TITLE`
  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE WELCOME `MUI_UNTEXT_WELCOME_INFO_TEXT`

  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE CONFIRM `MUI_UNTEXT_CONFIRM_TITLE`
  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE CONFIRM `MUI_UNTEXT_CONFIRM_SUBTITLE`

  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE LICENSE `MUI_UNTEXT_LICENSE_TITLE`
  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE LICENSE `MUI_UNTEXT_LICENSE_SUBTITLE`

  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE LICENSE `MUI_UNINNERTEXT_LICENSE_BOTTOM`
  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE LICENSE `MUI_UNINNERTEXT_LICENSE_BOTTOM_CHECKBOX`
  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE LICENSE `MUI_UNINNERTEXT_LICENSE_BOTTOM_RADIOBUTTONS`

  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE COMPONENTS `MUI_UNTEXT_COMPONENTS_TITLE`
  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE COMPONENTS `MUI_UNTEXT_COMPONENTS_SUBTITLE`

  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE DIRECTORY `MUI_UNTEXT_DIRECTORY_TITLE`
  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE DIRECTORY  `MUI_UNTEXT_DIRECTORY_SUBTITLE`

  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE INSTFILES `MUI_UNTEXT_UNINSTALLING_TITLE`
  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE INSTFILES `MUI_UNTEXT_UNINSTALLING_SUBTITLE`

  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE INSTFILES `MUI_UNTEXT_FINISH_TITLE`
  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE INSTFILES `MUI_UNTEXT_FINISH_SUBTITLE`

  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE INSTFILES `MUI_UNTEXT_ABORT_TITLE`
  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE INSTFILES `MUI_UNTEXT_ABORT_SUBTITLE`

  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE FINISH `MUI_UNTEXT_FINISH_INFO_TITLE`
  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE FINISH `MUI_UNTEXT_FINISH_INFO_TEXT`
  !insertmacro MUI_LANGUAGEFILE_UNLANGSTRING_PAGE FINISH `MUI_UNTEXT_FINISH_INFO_REBOOT`

  !insertmacro MUI_LANGUAGEFILE_LANGSTRING_DEFINE MUI_UNABORTWARNING `MUI_UNTEXT_ABORTWARNING`

!macroend

#goto FinishUp

!ifdef XPUI_INSTALLOPTIONS_MAXFIELD_TEMP
  !insertmacro XPUI_SET XPUI_INSTALLOPTIONS_MAXFIELD "${XPUI_INSTALLOPTIONS_MAXFIELD_TEMP}"
!endif

!ifndef XPUI_VERBOSE_MD
  !define XPUI_VERBOSE_MD
!endif
!insertmacro XPUI_PAGEMODE_INST

!ifndef XPUI_VAR_HWND
  !define XPUI_VAR_HWND
  Var XPUI_HWND
!endif

!verbose 4
!ifndef XPUI_SILENT
!echo `  XPUI: Finishing up...$\n  `
!endif
!verbose ${XPUI_VERBOSE}

; +-------------------+
; |        END        |
; +-------------------+

!verbose 4
!ifndef XPUI_SILENT
!echo `XPUI Processing Complete.$\n$\n`
!endif ;XPUI_SILENT
!verbose pop
!endif ;XPUI_INCLUDED
