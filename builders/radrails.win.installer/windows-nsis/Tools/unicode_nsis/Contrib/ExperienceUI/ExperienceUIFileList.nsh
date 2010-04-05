${SectionRO} "ExperienceUI Base Files" Base "The files required in order for the ExperienceUI to function.  This component must be installed."
  SectionIn 1 2 3
  ${SetOutPath} $INSTDIR
  ${File} XPUI.nsh XPUI.nsh
  ${File} ExperienceUIFileList.nsh ExperienceUIFileList.nsh
  ${File} License.rtf License.rtf
  ${File} Langpage.nsi Langpage.nsi
  ${File} XPUI-Setup.nsi XPUI-Setup.nsi
  ${SetOutPath} $INSTDIR\INI
  ${File} Confirm.ini INI\Confirm.ini
  ${File} Confirm_rep.ini INI\Confirm_rep.ini
  ${File} Finish.ini INI\Finish.ini
  ${File} Instdir.ini INI\Instdir.ini
  ${File} ioSpecial.ini INI\ioSpecial.ini
  ${File} isWelcome.ini INI\isWelcome.ini
  ${File} LangDlg.ini INI\LangDlg.ini
  ${File} Maint.ini INI\Maint.ini
  ${File} MBSide.ini INI\MBSide.ini
  ${File} repair.ini INI\Repair.ini
  ${File} Welcome.ini INI\Welcome.ini
  ${SetOutPath} "$INSTDIR\Language Files"
  ${File} English.nsh "Language Files\English.nsh"
  ${File} Default.nsh "Language Files\Default.nsh"
  ${File} PortugueseBR.nsh "Language Files\PortugueseBR.nsh"
  ${SetOutPath} $INSTDIR\UIs
  ${File} BGui.exe UIs\bgui.exe
  ${File} Headerui.exe UIs\headerui.exe
  ${File} headerui_btmimg.exe UIs\headerui_btmimg.exe
  ${File} tinyui.exe UIs\tinyui.exe
  ${File} UI.exe UIs\UI.exe
  ${File} WAnsis_ui.exe UIs\WAnsis_ui.exe
  ${SetOutPath} $INSTDIR\..\..\Plugins
  ${File} WAnsis.dll ..\..\Plugins\WAnsis.dll
  ${File} ZipDLL.dll ..\..\Plugins\ZipDLL.dll
  ${SetOutPath} $INSTDIR\Utils
  ${File} XPUIRes.dll Utils\XPUIRes.dll
  ${SetOutPath} $INSTDIR\..\Graphics\Icons
  ${File} XPUI-install.ico ..\Graphics\Icons\XPUI-install.ico
  ${File} XPUI-uninstall.ico ..\Graphics\Icons\XPUI-uninstall.ico
  ${SetOutPath} $INSTDIR\..\..\Include
  ${File} XPUI.nsh ..\..\Include\XPUI.nsh
  FileOpen $1 $INSTDIR\..\..\Include\XPUI.nsh w
    ReadRegStr $0 HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\NSIS" "UninstallString"
    StrCpy $0 $0 "" 1
    StrCpy $0 $0 -17
    FileWrite $1 `!include "$0\Contrib\ExperienceUI\XPUI.nsh"$\r$\n`
  FileClose $1
${SectionEnd}

${Section} "Additional Utilities" Utils "Several utilities that make working with the ExperienceUI easier.  These include the Update Wizard, to make sure you have the latest version, and the patching system, for installing updates and skins"
  SectionIn 1 2
  ${SetOutPath} $INSTDIR\Utils\Source
  ${File} Patcher.ico Utils\Source\patcher.ico
  ${File} Patcher.nsi Utils\Source\patcher.nsi
  ${File} Update.ico  Utils\Source\Update.ico
  ${File} Updater.ini Utils\Source\Updater.ini
  ${File} UpdateWiz.nsi Utils\Source\UpdateWiz.nsi
  ${SetOutPath} $INSTDIR\Utils
  ${File} icon-skin.ico Utils\icon-skin.ico
  ${File} icon-patch.ico Utils\icon-patch.ico
  !ifdef XPUI_SETUP_SPLASH
  ${SetOutPath} $INSTDIR
  ${File} LargeLogo.gif LargeLogo.gif
  !endif
  StrCpy $INSTALL_UTILS 1
${SectionEnd}

${Section} "ExperienceUI SDK Documentation" Docs "The complete documentation suite for the ExperienceUI SDK.  Highly recommended."
  SectionIn 1 2
  ${SetOutPath} "$INSTDIR\..\..\Docs\ExperienceUI"
  ${File} help.ico "..\..\Docs\ExperienceUI\help.ico"
  ${SetOutPath} "$INSTDIR\..\..\Docs\ExperienceUI\Images"
  ${File} minus.gif "..\..\Docs\ExperienceUI\Images\minus.gif"
  ${File} nsis.gif "..\..\Docs\ExperienceUI\Images\nsis.gif"
  ${File} plus.gif "..\..\Docs\ExperienceUI\Images\plus.gif"
  ${File} ScreenShot1.png "..\..\Docs\ExperienceUI\Images\ScreenShot1.png"
  ${File} ScreenShot2.png "..\..\Docs\ExperienceUI\Images\ScreenShot2.png"
  ${File} sourceforge.gif "..\..\Docs\ExperienceUI\Images\sourceforge.gif"
  ${File} spacer.gif "..\..\Docs\ExperienceUI\Images\spacer.gif"
  ${File} XPUILogo.png "..\..\Docs\ExperienceUI\Images\XPUILogo.png"
  ${SetOutPath} "$INSTDIR\..\..\Docs\ExperienceUI"
  ${File} index.htm "..\..\Docs\ExperienceUI\index.htm"
  ${File} open_popup.hta "..\..\Docs\ExperienceUI\open_popup.hta"
  ${SetOutPath} "$INSTDIR\..\..\Docs\ExperienceUI\pages\browser"
  ${File} back.bmp "..\..\Docs\ExperienceUI\pages\browser\back.bmp"
  ${File} back_o.bmp "..\..\Docs\ExperienceUI\pages\browser\back_o.bmp"
  ${File} exit.bmp "..\..\Docs\ExperienceUI\pages\browser\exit.bmp"
  ${File} exit_o.bmp "..\..\Docs\ExperienceUI\pages\browser\exit_o.bmp"
  ${File} forward.bmp "..\..\Docs\ExperienceUI\pages\browser\forward.bmp"
  ${File} forward_o.bmp "..\..\Docs\ExperienceUI\pages\browser\forward_o.bmp"
  ${File} go.bmp "..\..\Docs\ExperienceUI\pages\browser\go.bmp"
  ${File} go_o.bmp "..\..\Docs\ExperienceUI\pages\browser\go_o.bmp"
  ${File} hide.bmp "..\..\Docs\ExperienceUI\pages\browser\hide.bmp"
  ${File} hide_o.bmp "..\..\Docs\ExperienceUI\pages\browser\hide_o.bmp"
  ${File} home.bmp "..\..\Docs\ExperienceUI\pages\browser\home.bmp"
  ${File} home_o.bmp "..\..\Docs\ExperienceUI\pages\browser\home_o.bmp"
  ${File} refresh.bmp "..\..\Docs\ExperienceUI\pages\browser\refresh.bmp"
  ${File} refresh_o.bmp "..\..\Docs\ExperienceUI\pages\browser\refresh_o.bmp"
  ${SetOutPath} "$INSTDIR\..\..\Docs\ExperienceUI\pages"
  ${File} browser.htm "..\..\Docs\ExperienceUI\pages\browser.htm"
  ${File} build_setup.htm "..\..\Docs\ExperienceUI\pages\build_setup.htm"
  ${File} changes.htm "..\..\Docs\ExperienceUI\pages\changes.htm"
  ${File} cmd.js "..\..\Docs\ExperienceUI\pages\cmd.js"
  ${File} custfunc.htm "..\..\Docs\ExperienceUI\pages\custfunc.htm"
  ${File} gui_macros.htm "..\..\Docs\ExperienceUI\pages\gui_macros.htm"
  ${File} history.htm "..\..\Docs\ExperienceUI\pages\history.htm"
  ${File} intro.htm "..\..\Docs\ExperienceUI\pages\intro.htm"
  ${File} io.htm "..\..\Docs\ExperienceUI\pages\io.htm"
  ${File} lang.htm "..\..\Docs\ExperienceUI\pages\lang.htm"
  ${File} legal.htm "..\..\Docs\ExperienceUI\pages\legal.htm"
  ${File} license_agreement.htm "..\..\Docs\ExperienceUI\pages\license_agreement.htm"
  ${File} macros.htm "..\..\Docs\ExperienceUI\pages\macros.htm"
  ${File} pagemode.htm "..\..\Docs\ExperienceUI\pages\pagemode.htm"
  ${File} page_macros.htm "..\..\Docs\ExperienceUI\pages\page_macros.htm"
  ${File} page_settings.htm "..\..\Docs\ExperienceUI\pages\page_settings.htm"
  ${File} patch.htm "..\..\Docs\ExperienceUI\pages\patch.htm"
  ${File} sample.htm "..\..\Docs\ExperienceUI\pages\sample.htm"
  ${File} secdesc.htm "..\..\Docs\ExperienceUI\pages\secdesc.htm"
  ${File} skin.htm "..\..\Docs\ExperienceUI\pages\skin.htm"
  ${File} startmenu.htm "..\..\Docs\ExperienceUI\pages\startmenu.htm"
  ${File} style.css "..\..\Docs\ExperienceUI\pages\style.css"
  ${File} syntax.htm "..\..\Docs\ExperienceUI\pages\syntax.htm"
  ${File} useful_macros.htm "..\..\Docs\ExperienceUI\pages\useful_macros.htm"
  ${File} using.htm "..\..\Docs\ExperienceUI\pages\using.htm"
  ${File} visual_settings.htm "..\..\Docs\ExperienceUI\pages\visual_settings.htm"
  ${File} wansis.htm "..\..\Docs\ExperienceUI\pages\wansis.htm"
  ${File} welcome.htm "..\..\Docs\ExperienceUI\pages\welcome.htm"
  ${SetOutPath} "$INSTDIR\..\..\Docs\ExperienceUI"
  ${File} toc.htm "..\..\Docs\ExperienceUI\toc.htm"
  StrCpy $INSTALL_DOCS 1
${SectionEnd}

${Section} "Example Pack" Examples "Example scripts, to get you started"
  SectionIn 1 2
  ${SetOutPath} "$INSTDIR\..\..\Examples\ExperienceUI\Data"
  ${File} "HEY!!! Put Your Install Data in this folder!!!.txt" "..\..\Examples\ExperienceUI\Data\HEY!!! Put Your Install Data in this folder!!!.txt"
  ${CopyFiles} $WINDIR\Notepad.exe Notepad.exe
  ${SetOutPath} "$INSTDIR\..\..\Examples\ExperienceUI"
  ${File} Basic.nsi "..\..\Examples\ExperienceUI\Basic.nsi"
  ${File} HeaderBitmap.nsi "..\..\Examples\ExperienceUI\HeaderBitmap.nsi"
  ${File} InstallOptions.nsi "..\..\Examples\ExperienceUI\InstallOptions.nsi"
  ${File} ioA.ini "..\..\Examples\ExperienceUI\ioA.ini"
  ${File} ioB.ini "..\..\Examples\ExperienceUI\ioB.ini"
  ${File} ioC.ini "..\..\Examples\ExperienceUI\ioC.ini"
  ${File} MultiLang.nsi "..\..\Examples\ExperienceUI\MultiLang.nsi"
  ${File} Realworld.nsi "..\..\Examples\ExperienceUI\Realworld.nsi"
  ${File} StartMenu.nsi "..\..\Examples\ExperienceUI\StartMenu.nsi"
  ${File} UmuiImport.nsi "..\..\Examples\ExperienceUI\UmuiImport.nsi"
  ${File} WAnsis.nsi "..\..\Examples\ExperienceUI\WAnsis.nsi"
  ${File} WelcomeFinish.nsi "..\..\Examples\ExperienceUI\WelcomeFinish.nsi"
  StrCpy $INSTALL_EX 1
${SectionEnd}

SectionGroup "Extra Skins"
  ${SectionRO} "Default Skin" DefSkin "The default skin.  This must be installed."
    SectionIn 1 2 3
    ${SetOutPath} $INSTDIR\Skins\Default
    ${File} Bottom.bmp Skins\Default\Bottom.bmp
    ${File} Header.bmp Skins\Default\Header.bmp
    ${File} lb_page.bmp Skins\Default\lb_page.bmp
    ${File} LeftBranding.bmp Skins\Default\LeftBranding.bmp
    ${File} Page.bmp Skins\Default\Page.bmp
  ${SectionEnd}

  ${Section} "Windows XP Skin" WinXPSkin "A skin that looks like the annoying OOBE (out of box experience) wizard that shows when you (re)install Windows XP."
    SectionIn 1 2
    ${SetOutPath} "$INSTDIR\Skins\Windows XP"
    ${File} Arrow.bmp "Skins\Windows XP\Arrow.bmp"
    ${File} BtmImg.bmp "Skins\Windows XP\BtmImg.bmp"
    ${File} Header.bmp "Skins\Windows XP\Header.bmp"
    ${File} Icon.ico "Skins\Windows XP\Icon.ico"
    ${File} Key.bmp "Skins\Windows XP\Key.bmp"
    ${File} lb_page.bmp "Skins\Windows XP\lb_page.bmp"
    ${File} LeftBranding.bmp "Skins\Windows XP\leftbranding.bmp"
    ${File} Page.bmp "Skins\Windows XP\Page.bmp"
    ${File} Spotlight.bmp "Skins\Windows XP\Spotlight.bmp"
    ${File} UnIcon.ico "Skins\Windows XP\UnIcon.ico"
    ${SetOutPath} $INSTDIR\Skins
    ${File} "Windows XP.xpuiskin" "Skins\Windows XP.xpuiskin"
  ${SectionEnd}

  ${Section} "Orange Skin" OrangeSkin "This skin has a tropical orange color scheme."
    SectionIn 1
    ${SetOutPath} $INSTDIR\Skins\Orange
    ${File} Header.bmp Skins\Orange\Header.bmp
    ${File} Bottom.bmp Skins\Orange\Bottom.bmp
    ${File} LeftBranding.bmp Skins\Orange\LeftBranding.bmp
    ${SetOutPath} $INSTDIR\Skins
    ${File} orange.xpuiskin Skins\Orange.xpuiskin
  ${SectionEnd}

  ${Section} "Modern Blue Skin" MBSkin "A skin with peaceful blue tones"
    SectionIn 1
    ${SetOutPath} $INSTDIR\Skins\Modern-Blue
    ${File} bottom.bmp skins\modern-blue\bottom.bmp
    ${File} header.bmp skins\modern-blue\header.bmp
    ${File} leftbranding.bmp skins\modern-blue\leftbranding.bmp
    ${File} Icon.ico skins\modern-blue\icon.ico
    ${File} unicon.ico skins\modern-blue\unicon.ico
    ${SetOutPath} $INSTDIR\Skins
    ${File} modern-blue.xpuiskin skins\modern-blue.xpuiskin
  ${SectionEnd}

  SectionGroup "WAnsis Skins"
    ${SectionRO} "Winamp Forum Skin" ForumWANSkin "Looks the the Winamp forums.  This is the default WAnsis skin and as a result must be installed.  The Forum skin was designed by ZmAn3."
      SectionIn 1 2 3
      ${SetOutPath} $INSTDIR\Skins\Forum
      ${File} Bottom.bmp Skins\Forum\Bottom.bmp
      ${File} Checks.bmp Skins\Forum\Checks.bmp
      ${File} Gen.bmp Skins\Forum\Gen.bmp
      ${File} genex.bmp Skins\Forum\Genex.bmp
      ${File} Header.bmp Skins\Forum\Header.bmp
      ${File} LeftLogo.bmp Skins\Forum\LeftLogo.bmp
    ${SectionEnd}

    ${Section} "Bliss Skin" BlissWANSkin "A metallic blue take-off on the InstallShield(R) skin, but with more features"
      SectionIn 1 2
      ${SetOutPath} $INSTDIR\Skins\Bliss
      ${File} Bottom.bmp Skins\Bliss\Bottom.bmp
      ${File} Checks.bmp Skins\Bliss\Checks.bmp
      ${File} Gen.bmp Skins\Bliss\Gen.bmp
      ${File} genex.bmp Skins\Bliss\Genex.bmp
      ${File} Header.bmp Skins\Bliss\Header.bmp
      ${File} LeftLogo.bmp Skins\Bliss\LeftLogo.bmp
    ${SectionEnd}

    ${Section} "LCD Skin" LCDWANSkin "Sort of a pea soup green, but wicked cool :-).  This skin looks good in installers for games and such.  This skin was designed by ZmAn3."
      SectionIn 1
      ${SetOutPath} $INSTDIR\Skins\LCD
      ${File} Bottom.bmp Skins\LCD\Bottom.bmp
      ${File} Checks.bmp Skins\LCD\Checks.bmp
      ${File} Gen.bmp Skins\LCD\Gen.bmp
      ${File} genex.bmp Skins\LCD\Genex.bmp
      ${File} Header.bmp Skins\LCD\Header.bmp
      ${File} LeftLogo.bmp Skins\LCD\LeftLogo.bmp
    ${SectionEnd}
  SectionGroupEnd
SectionGroupEnd

Section -post
  StrCmp $INSTALL_UTILS 1 "" NoUtilsA
    SetDetailsPrint textonly
    DetailPrint "Preparing additional components for use..."
    nsExec::Exec `"$INSTDIR\..\..\MakeNSIS.exe" "$INSTDIR\Utils\Source\UpdateWiz.nsi"`
    nsExec::Exec `"$INSTDIR\..\..\MakeNSIS.exe" "$INSTDIR\Utils\Source\Patcher.nsi"`
    WriteRegStr   HKCR XPUI_P "" "ExperienceUI Patch"
    WriteRegStr   HKCR XPUI_P\Shell "" Install
    WriteRegStr   HKCR XPUI_P\Shell\Install\Command '' '"$INSTDIR\utils\PatchInstaller.exe" %1'
    WriteRegStr   HKCR XPUI_P\DefaultIcon '' '"$INSTDIR\utils\icon-patch.ico"'
    WriteRegStr   HKCR .eup "" XPUI_P

    WriteRegStr   HKCR XPUI_S "" "ExperienceUI Skin"
    WriteRegStr   HKCR XPUI_S\Shell "" Install
    WriteRegStr   HKCR XPUI_S\Shell\Install\Command '' '"$INSTDIR\utils\PatchInstaller.exe" %1'
    WriteRegStr   HKCR XPUI_S\DefaultIcon '' '"$INSTDIR\utils\icon-skin.ico"'
    WriteRegStr   HKCR .eus "" XPUI_S
    System::Call 'shell32.dll::SHChangeNotify(i, i, i, i) v (0x08000000, 0, 0, 0)'
    SetDetailsPrint both
  NoUtilsA:
  ${StartMenu} App $SMFOLDER
    ${CreateShortcut} "ExperienceUI Installation Folder" "$INSTDIR` `` `$INSTDIR\Utils\XPUIRes.dll` `6"
    StrCmp $INSTALL_EX 1 "" NoEx
      ${CreateShortcut} "Example Scripts" "$WINDIR\Explorer.exe` `$INSTDIR\..\..\Examples\ExperienceUI` `$SYSDIR\Shell32.dll` `4"
    NoEx:
    StrCmp $INSTALL_DOCS 1 "" NoDocs
      ${CreateShortcut} "ExperienceUI Documentation" "$INSTDIR\..\..\Docs\ExperienceUI\open_popup.hta` `` `$INSTDIR\..\..\Docs\ExperienceUI\help.ico` `0"
    NoDocs:
    ${CreateShortcut} "Uninstall the ExperienceUI" "$INSTDIR\Uninst.exe"

    StrCmp $INSTALL_UTILS 1 "" NoUtils
      ${CreateShortcut} "Check for Updates" "$INSTDIR\Utils\Updater.exe"
    NoUtils:
  ${StartMenuEnd}
SectionEnd
